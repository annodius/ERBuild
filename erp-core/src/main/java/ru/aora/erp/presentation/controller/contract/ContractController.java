package ru.aora.erp.presentation.controller.contract;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.aora.erp.domain.service.CounteragentService;
import ru.aora.erp.domain.service.KsService;
import ru.aora.erp.model.entity.business.Contract;
import ru.aora.erp.model.entity.business.Counteragent;
import ru.aora.erp.model.entity.business.Ks;
import ru.aora.erp.presentation.controller.exception.DtoValidationException;
import ru.aora.erp.presentation.controller.xlsout.ExcelGenerator;
import ru.aora.erp.presentation.entity.dto.contract.ContractDto;
import ru.aora.erp.presentation.entity.dto.contract.ContractListDto;
import ru.aora.erp.domain.service.ContractService;
import ru.aora.erp.repository.gateway.SpecContractQuery;

import javax.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;
import java.util.StringJoiner;

import static java.util.Objects.requireNonNull;
import static ru.aora.erp.presentation.entity.dto.compose.KsContractCounteragentDtoUtils.sortByGarantDateNaturalOrder;
import static ru.aora.erp.presentation.entity.dto.compose.KsContractCounteragentDtoUtils.toKsContractCounteragentDtoList;
import static ru.aora.erp.presentation.entity.dto.contract.ContractDtoMapper.toContract;
import static ru.aora.erp.presentation.entity.dto.contract.ContractDtoMapper.toListDto;

@Controller
@RequestMapping
public final class ContractController {
    private static Counteragent selectCounteragent;
    private static BigDecimal oldContractSum;
    private static final String CONTROLLER_MAPPING = "contracts";
    private static final String DTO_MODEL = "contractDto";
    private static String print_counteragent_id;
    private static  String print_counteragent_name;
    private static final String ID_PARENT = "id_parent";
    private static final String PARENT_NAME = "parent_name";
    private static final String PRINT_INDEX = "print_index";
    private static final String COUNTERAGENT_NAME = "counteragent_name";
    private static final String TOTAL_RESULTS = "total_results";
    private final KsService ksService;
    private final CounteragentService counteragentService;
    private final ContractService contractService;
    private static String select_counteragent_id;
    private static String select_counteragent_name;

    @Autowired
    public ContractController (KsService ksService, ContractService contractService, CounteragentService counteragentService) {
        this.ksService = ksService;
        this.contractService = contractService;
        this.counteragentService=counteragentService;
    }

    @RequestMapping(path = "/contract", method = RequestMethod.GET)
    public String contractForm(
            @RequestParam(ID_PARENT) String id_parent,
            @RequestParam(PARENT_NAME) String counteragent_name,

            Map<String, Object> model
    ) throws IOException {
        select_counteragent_id=id_parent;
        select_counteragent_name=counteragent_name;

        final ContractListDto contractDto = toListDto(contractService.loadAll());
        model.put(DTO_MODEL, contractDto);
        model.put(ID_PARENT, id_parent);
        model.put(COUNTERAGENT_NAME, counteragent_name);
        model.put(TOTAL_RESULTS, contractResult(counteragentService.loadAll()));
        return CONTROLLER_MAPPING;
    }
    @RequestMapping(path = "/printercontract", method = RequestMethod.GET)
    public String contractPrintForm(
            @RequestParam(ID_PARENT) String id_parent,
            @RequestParam(PARENT_NAME) String counteragent_name,

            Map<String, Object> model
    ) throws IOException {
        select_counteragent_id=id_parent;
        select_counteragent_name=counteragent_name;

        ExcelGenerator generator = new ExcelGenerator(contractService.loadAll(),ksService.loadAll(), sortByGarantDateNaturalOrder(
                toKsContractCounteragentDtoList(
                        ksService.loadAll(),
                        contractService.loadAll(),
                        counteragentService.loadAll())));
        generator.generateContractExcelFile(counteragent_name,id_parent);




        final ContractListDto contractDto = toListDto(contractService.loadAll());
        model.put(DTO_MODEL, contractDto);
        model.put(ID_PARENT, id_parent);
        model.put(COUNTERAGENT_NAME, counteragent_name);
        model.put(TOTAL_RESULTS, contractResult(counteragentService.loadAll()));
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

    private static ContractResultDto contractResult(Collection<Counteragent> counteragentList) {
        final ContractResultDto contractResult = new ContractResultDto();
        contractResult.ksCounteragentTotalSum= BigDecimal.valueOf(0);
        contractResult.contractCounteragentTotalSum= BigDecimal.valueOf(0);
        for (Counteragent counteragent : requireNonNull(counteragentList)){
            if (counteragent.getOldId().equals(select_counteragent_id)){
                if (counteragent!=null && counteragent.getActiveStatus() == 0) {
                    if(counteragent.getContractSumValue()!=null) {
                        contractResult.contractCounteragentTotalSum = counteragent.getContractSumValue();
                    }
                    if(counteragent.getKSSumValue()!=null) {
                        contractResult.ksCounteragentTotalSum = counteragent.getKSSumValue();
                    }
                }
            }
        }
        return contractResult;
    }

    private static Counteragent contractAddCycle (BigDecimal newContractSum, Collection<Counteragent> counteragentList, String counteragent_id) {

        for (Counteragent counteragent : requireNonNull(counteragentList)){
            if (counteragent.getOldId().equals(counteragent_id)){
                if (counteragent!=null && counteragent.getActiveStatus() == 0) {
                    selectCounteragent=counteragent;
                    if(selectCounteragent.getContractSumValue()!=null) {
                        selectCounteragent.setContractSumValue(selectCounteragent.getContractSumValue().add(newContractSum));
                    }
                    else {
                        selectCounteragent.setContractSumValue(newContractSum);
                    }
                }

            }
        }
        return selectCounteragent;
    }
    private static Counteragent contractEditCycle (BigDecimal newContractSum, Collection<Counteragent> counteragentList, Collection<Contract> contractList,String counteragent_id, String contract_id) {

        for (Contract contract : requireNonNull(contractList)) {
            if (contract.getId().equals(contract_id)) {
                if (contract != null && contract.getActiveStatus() == 0) {
                    oldContractSum=contract.getContractValue();
                }
            }
        }
        for (Counteragent counteragent : requireNonNull(counteragentList)){
            if (counteragent.getOldId().equals(counteragent_id)){
                if (counteragent!=null && counteragent.getActiveStatus() == 0) {
                    selectCounteragent=counteragent;
                    selectCounteragent.setContractSumValue(selectCounteragent.getContractSumValue().add(newContractSum.add(oldContractSum.negate())));
                }
            }
        }
        return selectCounteragent;
    }


    @RequestMapping(path = "/contract", method = RequestMethod.PUT)
    public @ResponseBody String putContract(@Valid @RequestBody ContractDto dto, BindingResult bindingResult) {
        DtoValidationException.throwIfHasErrors(bindingResult);
        Counteragent counteragent=contractEditCycle(dto.getContractSum(),counteragentService.loadAll(), contractService.loadAll(),dto.getCounteragentId(), dto.getId());
        counteragentService.update(counteragent);
        dto.setContractValue(dto.getContractSum());

        String Msg=contractService.update(toContract(dto)).getMsg();
        return  Msg;
    }

    @RequestMapping(path = "/contract", method = RequestMethod.POST)
    public @ResponseBody String postContract(@Valid @RequestBody ContractDto dto, BindingResult bindingResult) {
        DtoValidationException.throwIfHasErrors(bindingResult);
        Counteragent counteragent=contractAddCycle(dto.getContractSum(),counteragentService.loadAll(),toContract(dto).getCounteragentId());
        counteragentService.update(counteragent);
        dto.setContractValue(dto.getContractSum());
        contractService.create(toContract(dto));
        return "Contract was created";
    }
//    @RequestMapping(path = "/printercontract", method = RequestMethod.GET)
//    public @ResponseBody String postPrintContract(@Valid @RequestBody BindingResult bindingResult, @RequestParam(ID_PARENT) String id_parent,
//                                                  @RequestParam(PARENT_NAME) String counteragent_name) throws IOException {
//        DtoValidationException.throwIfHasErrors(bindingResult);
//        print_counteragent_id=id_parent;
//        print_counteragent_name=counteragent_name;
//        ExcelGenerator generator = new ExcelGenerator(contractService.loadAll(),ksService.loadAll());
//        generator.generateExcelFile(print_counteragent_name,print_counteragent_id);
//        return "redirect:/dashboard";
//    }

    @DeleteMapping(value="/contract/{id}")
    public @ResponseBody String deleteContract(@PathVariable String id) {
        return contractService.delete(id).getMsg();
    }
}
