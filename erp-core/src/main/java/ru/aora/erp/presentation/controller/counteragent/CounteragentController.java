package ru.aora.erp.presentation.controller.counteragent;

import org.apache.catalina.connector.Request;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.aora.erp.domain.service.ContractService;
import ru.aora.erp.domain.service.KsService;
import ru.aora.erp.model.entity.business.Contract;
import ru.aora.erp.model.entity.business.Ks;
import ru.aora.erp.presentation.controller.exception.DtoValidationException;
import ru.aora.erp.presentation.entity.dto.counteragent.CounteragentDto;
import ru.aora.erp.presentation.entity.dto.counteragent.CounteragentDtoMapper;
import ru.aora.erp.presentation.entity.dto.counteragent.CounteragentListDto;
import ru.aora.erp.domain.service.CounteragentService;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;
import java.util.StringJoiner;

import static java.util.Objects.requireNonNull;
import static ru.aora.erp.presentation.entity.dto.counteragent.CounteragentDtoMapper.toCounteragent;

@Controller
@RequestMapping(CounteragentUrl.MAPPING)
public final class CounteragentController {

    private static final String GARANT_MAPPING = "counteragents";
    private static final String CONTRACTOR_DTO_MODEL = "counteragentDto";
    private static final String TOTAL_RESULTS = "total_results";

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



    @PutMapping
    public @ResponseBody String putCounteragent(@Valid @RequestBody CounteragentDto dto, BindingResult bindingResult) {
        DtoValidationException.throwIfHasErrors(bindingResult);
        return counteragentService.update(toCounteragent(dto)).getMsg();

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
