package ru.aora.erp.presentation.controller.ks;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.aora.erp.domain.service.ContractService;
import ru.aora.erp.domain.service.CounteragentService;
import ru.aora.erp.model.entity.business.Contract;
import ru.aora.erp.model.entity.business.Counteragent;
import ru.aora.erp.model.entity.business.Ks;
import ru.aora.erp.presentation.controller.contract.ContractController;
import ru.aora.erp.presentation.controller.exception.DtoValidationException;
import ru.aora.erp.presentation.entity.dto.ks.KsDto;
import ru.aora.erp.presentation.entity.dto.ks.KsDtoMapper;
import ru.aora.erp.presentation.entity.dto.ks.KsListDto;
import ru.aora.erp.domain.service.KsService;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;
import java.util.StringJoiner;

import static java.util.Objects.requireNonNull;
import static ru.aora.erp.presentation.entity.dto.ks.KsDtoMapper.toKs;

@Controller
@RequestMapping(KsUrl.MAPPING)
public final class KsController {

    private static final String CONTROLLER_MAPPING = "kss";
    private static final String DTO_MODEL = "ksDto";

    private static final String ID_PAREN = "id_parent";
    private static final String ID_GRAND_PARENT = "id_grand_parent";
    private static final String PARENT_NAME = "parent_name";
    private static final String GRAND_PARENT_NAME = "grand_parent_name";
    private static final String CONTRACT_NAME = "contract_name";
    private static final String COUNTERAGENT_NAME = "counteragent_name";
    private static final String TOTAL_RESULTS = "total_results";
    private final KsService ksService;
    private final ContractService contractService;
    private static String counteragent_select_id;
    private static String contract_select_id;

    public KsController(KsService ksService, ContractService contractService) {
        this.ksService = ksService;
        this.contractService = contractService;
    }

    @SuppressWarnings("Duplicates")
    @GetMapping
    public String ksForm(
            @RequestParam(ID_PAREN) String id_parent,
            @RequestParam(ID_GRAND_PARENT) String id_grand_parent,
            @RequestParam(PARENT_NAME) String contract_name,
            @RequestParam(GRAND_PARENT_NAME) String counteragent_name,
            Map<String, Object> model
    ) {
        final KsListDto ksDto = KsListDto.of(
                KsDtoMapper.toKsDtoList(ksService.loadAll())
        );
        counteragent_select_id=id_grand_parent;
        contract_select_id=id_parent;
        model.put(DTO_MODEL, ksDto);
        model.put(ID_PAREN, id_parent);
        model.put(ID_GRAND_PARENT, id_grand_parent);
        model.put(CONTRACT_NAME, contract_name);
        model.put(COUNTERAGENT_NAME, counteragent_name);
        model.put(TOTAL_RESULTS, ksResult(ksService.loadAll(),contractService.loadAll()));
        return CONTROLLER_MAPPING;
    }

    @SuppressWarnings("WeakerAccess")
    static class KsResultDto {
        public BigDecimal ksContractSum = BigDecimal.ZERO;
        public BigDecimal contractCurrentSum = BigDecimal.ZERO;

        @Override
        public String toString() {
            return new StringJoiner(", ", KsResultDto.class.getSimpleName() + "[", "]")
                    .add("ksContractSum=" + ksContractSum)
                    .add("contractCurrentSum=" + contractCurrentSum)
                    .toString();
        }
    }

    private static KsResultDto ksResult(Collection<Ks> ksList,Collection<Contract> contractList) {
        final KsResultDto ksResult = new KsResultDto();
        for (Contract contract : requireNonNull(contractList)) {
            if (contract!=null && contract.getActiveStatus() == 0) {
                if (contract.getCounteragentId().equals(counteragent_select_id)) {
                    if (contract.getContractSum() != null) {
                        ksResult.contractCurrentSum = ksResult.contractCurrentSum.add(contract.getContractSum());

                    }
                }
            }
        }

        for (Ks ks : requireNonNull(ksList)) {
            if (ks.getContractId().equals(contract_select_id)){
                if (ks != null && ks.getActiveStatus() == 0) {
                    if (ks.getKsSum() != null) {
                        ksResult.ksContractSum = ksResult.ksContractSum.add(ks.getKsSum());
                    }
                }
            }
        }
        return ksResult;
    }

    @PutMapping
    public @ResponseBody String putKs(@Valid @RequestBody KsDto dto, BindingResult bindingResult) {
        DtoValidationException.throwIfHasErrors(bindingResult);
        return ksService.update(toKs(dto)).getMsg();
    }

    @PostMapping
    public @ResponseBody String postKs(@Valid @RequestBody KsDto dto, BindingResult bindingResult) {
        DtoValidationException.throwIfHasErrors(bindingResult);
        ksService.create(toKs(dto));
        return "Ks was created";
    }

    @DeleteMapping("/{id}")
    public @ResponseBody String deleteKs(@PathVariable String id) {
        return ksService.delete(id).getMsg();
    }

}
