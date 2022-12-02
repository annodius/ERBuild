package ru.aora.erp.presentation.controller.counteragent;

import org.apache.catalina.connector.Request;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.aora.erp.domain.service.ContractService;
import ru.aora.erp.domain.service.KsService;
import ru.aora.erp.model.entity.business.Contract;
import ru.aora.erp.model.entity.business.Counteragent;
import ru.aora.erp.model.entity.business.Ks;
import ru.aora.erp.presentation.controller.contract.ContractController;
import ru.aora.erp.presentation.controller.exception.DtoValidationException;
import ru.aora.erp.presentation.controller.garant.GarantResultController;
import ru.aora.erp.presentation.entity.dto.contract.ContractDto;
import ru.aora.erp.presentation.entity.dto.contract.ContractListDto;
import ru.aora.erp.presentation.entity.dto.counteragent.CounteragentDto;
import ru.aora.erp.presentation.entity.dto.counteragent.CounteragentDtoMapper;
import ru.aora.erp.presentation.entity.dto.counteragent.CounteragentListDto;
import ru.aora.erp.domain.service.CounteragentService;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Map;
import java.util.StringJoiner;

import static java.util.Objects.requireNonNull;
import static ru.aora.erp.presentation.entity.dto.contract.ContractDtoMapper.toContract;
import static ru.aora.erp.presentation.entity.dto.contract.ContractDtoMapper.toListDto;
import static ru.aora.erp.presentation.entity.dto.counteragent.CounteragentDtoMapper.toCounteragent;

@Controller
@RequestMapping(CounteragentUrl.MAPPING)
public final class CounteragentController {

    private static final String GARANT_MAPPING = "counteragents";
    private static final String CONTRACTOR_DTO_MODEL = "counteragentDto";
    private static final String TOTAL_RESULTS = "total_results";

    private static String oldIdCounter="null";
    private static String newIdCounter="null";
    private static String newestIdCounter="null";
    private static String oldestIdCounter="null";
    private final CounteragentService counteragentService;
    private final KsService ksService;
    private final ContractService contractService;

    public CounteragentController(CounteragentService counteragentService, KsService ksService, ContractService contractService) {
        this.counteragentService = counteragentService;
        this.ksService = ksService;
        this.contractService = contractService;
    }

    @GetMapping
    public String counteragentForm(
            Map<String, Object> model) {
        final CounteragentListDto listDto = CounteragentDtoMapper.toListDto(counteragentService.loadAll());
        model.put(CONTRACTOR_DTO_MODEL, listDto);
        model.put(TOTAL_RESULTS, counteragentResult(ksService.loadAll(),contractService.loadAll()));
        CounteragentChildrenFind(ksService.loadAll(),contractService.loadAll(),counteragentService.loadAll());
        return GARANT_MAPPING;
    }

    @SuppressWarnings("WeakerAccess")
    static class CounteragentResultDto {
        public BigDecimal ksTotalCounteragentSum = BigDecimal.ZERO;
        public BigDecimal contractTotalCounteragentSum = BigDecimal.ZERO;

        @Override
        public String toString() {
            return new StringJoiner(", ", CounteragentResultDto.class.getSimpleName() + "[", "]")
                    .add("ksTotalCounteragentSum=" + ksTotalCounteragentSum)
                    .add("contractTotalCounteragentSum=" + contractTotalCounteragentSum)
                    .toString();
        }
    }

    private static CounteragentResultDto counteragentResult(Collection<Ks> ksList,Collection<Contract> contractList) {
        final CounteragentResultDto counteragentResult = new CounteragentResultDto();
        for (Contract contract : requireNonNull(contractList)) {
            if (contract != null && contract.getActiveStatus() == 0) {
                if (contract.getContractSum() != null) {
                    counteragentResult.contractTotalCounteragentSum = counteragentResult.contractTotalCounteragentSum.add(contract.getContractSum());
                }
            }
        }
        for (Ks ks : requireNonNull(ksList)) {
            if (ks != null && ks.getActiveStatus() == 0) {
                if (ks.getKsSum() != null) {
                    counteragentResult.ksTotalCounteragentSum = counteragentResult.ksTotalCounteragentSum.add(ks.getKsSum());
                }
            }
        }
        return counteragentResult;
    }

   public void CounteragentChildrenFind(Collection<Ks>  kssList,Collection<Contract>  contractsList,Collection<Counteragent>  counteragentsList){
        if (!"null".equals(oldIdCounter)) {
            if (!"null".equals(oldestIdCounter)) {
                for (Contract contractum : requireNonNull(contractsList)) {
                    if (contractum != null && contractum.getActiveStatus() == 0) {
                        if ((contractum.getOldId()).equals(oldestIdCounter)) {
                            newestIdCounter=contractum.getId();
                        }
                    }
                }
                for (Ks ks : requireNonNull(kssList)) {
                    if (ks != null && ks.getActiveStatus() == 0) {
                        if (ks.getContractId().equals(oldestIdCounter)) {
                            ks.setContractId(newestIdCounter);
                            ksService.update(ks);
                        }
                    }
                }
                oldestIdCounter="null";
            }
            for (Counteragent counteragent : requireNonNull(counteragentsList)) {
                if (counteragent != null && counteragent.getActiveStatus() == 0) {
                    if ((counteragent.getOldId()).equals(oldIdCounter)) {
                        newIdCounter=counteragent.getId();
                    }
                }
            }
            for (Contract contract : requireNonNull(contractsList)) {
                if (contract != null && contract.getActiveStatus() == 0) {
                    if (contract.getCounteragentId().equals(oldIdCounter)) {
                        contract.setCounteragentId(newIdCounter);
                        oldestIdCounter=contract.getId();
                        contractService.update(contract);

                        //ContractController.ContractChildrenFind(kssList,contractsList);
                    }
                }
            }
            oldIdCounter="null";
        }
    }
    @PutMapping
    public @ResponseBody String putCounteragent(@Valid @RequestBody CounteragentDto dto, BindingResult bindingResult) {

        DtoValidationException.throwIfHasErrors(bindingResult);
        oldIdCounter= dto.getId();
        String Msg=counteragentService.update(toCounteragent(dto)).getMsg();
        return Msg;

    }

    @PostMapping
    public @ResponseBody String postCounteragent(@Valid @RequestBody CounteragentDto dto, BindingResult bindingResult) {
        DtoValidationException.throwIfHasErrors(bindingResult);
        counteragentService.create(toCounteragent(dto));
        return "Counteragent was created";
    }

    @DeleteMapping("/{id}")
    public @ResponseBody String deleteCounteragent(@PathVariable String id) {
        return counteragentService.delete(id).getMsg();
    }
}
