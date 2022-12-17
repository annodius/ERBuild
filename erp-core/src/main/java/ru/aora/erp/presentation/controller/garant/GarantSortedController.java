package ru.aora.erp.presentation.controller.garant;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.aora.erp.presentation.controller.xlsout.ExcelGenerator;
import ru.aora.erp.presentation.entity.dto.compose.KsContractCounteragentCollectionDto;

import ru.aora.erp.presentation.entity.dto.compose.KsContractCounteragentDto;
import ru.aora.erp.domain.service.ContractService;
import ru.aora.erp.domain.service.CounteragentService;
import ru.aora.erp.domain.service.KsService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static ru.aora.erp.presentation.entity.dto.compose.KsContractCounteragentDtoUtils.sortByGarantDateNaturalOrder;
import static ru.aora.erp.presentation.entity.dto.compose.KsContractCounteragentDtoUtils.toKsContractCounteragentDtoList;

@Controller
@RequestMapping
public final class GarantSortedController {

    private static final String CONTROLLER_TEMPLATE = "garantsorted";
    private static final String DTO_MODEL = "ksSortDto";

    private final KsService ksService;
    private final ContractService contractService;
    private final CounteragentService counteragentService;

    public GarantSortedController(KsService ksService,
                                  ContractService contractService,
                                  CounteragentService counteragentService
    ) {
        this.ksService = ksService;
        this.contractService = contractService;
        this.counteragentService = counteragentService;
    }

    @RequestMapping(path = "/garantsorted", method = RequestMethod.GET)
    public String ksSortForm(Map<String, Object> model) {
        final List<KsContractCounteragentDto> ksDtoList = sortByGarantDateNaturalOrder(
                toKsContractCounteragentDtoList(
                        ksService.loadAll(),
                        contractService.loadAll(),
                        counteragentService.loadAll())
        );
        model.put(DTO_MODEL, KsContractCounteragentCollectionDto.of(ksDtoList));
        return CONTROLLER_TEMPLATE;
    }
    @RequestMapping(path = "/garantsortedprint", method = RequestMethod.GET)
    public String ksPrintSortForm(Map<String, Object> model) throws IOException {
        final List<KsContractCounteragentDto> ksDtoList = sortByGarantDateNaturalOrder(
                toKsContractCounteragentDtoList(
                        ksService.loadAll(),
                        contractService.loadAll(),
                        counteragentService.loadAll())
        );
        ExcelGenerator generator = new ExcelGenerator(contractService.loadAll(),ksService.loadAll(), sortByGarantDateNaturalOrder(
                toKsContractCounteragentDtoList(
                        ksService.loadAll(),
                        contractService.loadAll(),
                        counteragentService.loadAll())));
        generator.generateGarantExcelFile();
        model.put(DTO_MODEL, KsContractCounteragentCollectionDto.of(ksDtoList));
        return CONTROLLER_TEMPLATE;
    }
}