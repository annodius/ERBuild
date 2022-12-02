package ru.aora.erp.presentation.controller.contract;

import org.springframework.beans.factory.annotation.Autowired;
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
import ru.aora.erp.domain.service.CounteragentService;
import ru.aora.erp.domain.service.KsService;
import ru.aora.erp.model.entity.business.Contract;
import ru.aora.erp.model.entity.business.Counteragent;
import ru.aora.erp.model.entity.business.Ks;
import ru.aora.erp.presentation.controller.counteragent.CounteragentController;
import ru.aora.erp.presentation.controller.exception.DtoValidationException;
import ru.aora.erp.presentation.entity.dto.contract.ContractDto;
import ru.aora.erp.presentation.entity.dto.contract.ContractListDto;
import ru.aora.erp.domain.service.ContractService;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;
import java.util.StringJoiner;

import static java.util.Objects.requireNonNull;
import static ru.aora.erp.presentation.entity.dto.contract.ContractDtoMapper.toContract;
import static ru.aora.erp.presentation.entity.dto.contract.ContractDtoMapper.toListDto;

@Controller
@RequestMapping(ContractUrl.MAPPING)
public final class ContractController {

    private static final String CONTROLLER_MAPPING = "contracts";
    private static final String DTO_MODEL = "contractDto";

    private static final String ID_PARENT = "id_parent";
    private static final String PARENT_NAME = "parent_name";
    private static final String COUNTERAGENT_NAME = "counteragent_name";
    private static final String TOTAL_RESULTS = "total_results";
    private static String daOldeIdCounter="null";
    private static String nouveauIdCounter="null";
    private final KsService ksService;
    private final ContractService contractService;
    private static String select_counteragent_id;

    @Autowired
    public ContractController (KsService ksService, ContractService contractService) {
        this.ksService = ksService;
        this.contractService = contractService;
    }

    @GetMapping
    public String contractForm(
            @RequestParam(ID_PARENT) String id_parent,
            @RequestParam(PARENT_NAME) String counteragent_name,
            Map<String, Object> model
    ) {
        select_counteragent_id=id_parent;
        final ContractListDto contractDto = toListDto(contractService.loadAll());
        model.put(DTO_MODEL, contractDto);
        model.put(ID_PARENT, id_parent);
        model.put(COUNTERAGENT_NAME, counteragent_name);
        model.put(TOTAL_RESULTS, contractResult(ksService.loadAll(), contractService.loadAll()));
        ContractChildrenFind(ksService.loadAll(),contractService.loadAll());
        return CONTROLLER_MAPPING;
    }

    @SuppressWarnings("WeakerAccess")
    static class ContractResultDto {
        public BigDecimal ksCounteragentTotalSum = BigDecimal.ZERO;
        public BigDecimal contractCounteragentTotalSum = BigDecimal.ZERO;

        @Override
        public String toString() {
            return new StringJoiner(", ", ContractResultDto.class.getSimpleName() + "[", "]")
                    .add("ksCounteragentTotalSum=" + ksCounteragentTotalSum)
                    .add("contractCounteragentTotalSum=" + contractCounteragentTotalSum)
                    .toString();
        }
    }

    private static ContractResultDto contractResult(Collection<Ks> ksList, Collection<Contract> contractList) {
        final ContractResultDto contractResult = new ContractResultDto();
        for (Contract contract : requireNonNull(contractList)){
            if (contract.getCounteragentId().equals(select_counteragent_id)){
                if (contract!=null && contract.getActiveStatus() == 0) {
                    if (contract.getContractSum() != null) {
                        contractResult.contractCounteragentTotalSum = contractResult.contractCounteragentTotalSum.add(contract.getContractSum());
                        for (Ks ks : requireNonNull(ksList)) {
                            if (ks.getContractId().equals(contract.getId())){
                                if (ks != null && ks.getActiveStatus() == 0) {
                                    if (ks.getKsSum() != null) {
                                        contractResult.ksCounteragentTotalSum = contractResult.ksCounteragentTotalSum.add(ks.getKsSum());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return contractResult;
    }

    public void ContractChildrenFind(Collection<Ks>  kssList,Collection<Contract>  contractsList){
        if (!"null".equals(daOldeIdCounter)) {
            for (Contract contract : requireNonNull(contractsList)) {
                if (contract != null && contract.getActiveStatus() == 0) {
                    if ((contract.getOldId()).equals(daOldeIdCounter)) {
                        nouveauIdCounter=contract.getId();
                    }
                }
            }
            for (Ks ks : requireNonNull(kssList)) {
                if (ks != null && ks.getActiveStatus() == 0) {
                    if (ks.getContractId().equals(daOldeIdCounter)) {
                        ks.setContractId(nouveauIdCounter);
                        ksService.update(ks);
                    }
                }
            }
            daOldeIdCounter="null";
        }
    }

    @PutMapping
    public @ResponseBody String putContract(@Valid @RequestBody ContractDto dto, BindingResult bindingResult) {
        DtoValidationException.throwIfHasErrors(bindingResult);
        daOldeIdCounter= dto.getId();
        return  contractService.update(toContract(dto)).getMsg();
    }

    @PostMapping
    public @ResponseBody String postContract(@Valid @RequestBody ContractDto dto, BindingResult bindingResult) {
        DtoValidationException.throwIfHasErrors(bindingResult);
        contractService.create(toContract(dto));
        return "Contract was created";
    }

    @DeleteMapping("/{id}")
    public @ResponseBody String deleteContract(@PathVariable String id) {
        return contractService.delete(id).getMsg();
    }
}
