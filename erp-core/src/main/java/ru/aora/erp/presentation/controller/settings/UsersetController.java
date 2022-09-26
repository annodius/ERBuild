package ru.aora.erp.presentation.controller.settings;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.aora.erp.domain.service.UsersetService;
import ru.aora.erp.presentation.controller.exception.DtoValidationException;
import ru.aora.erp.presentation.controller.settings.UsersetUrl;
import ru.aora.erp.presentation.entity.dto.settings.UsersetDto;
import ru.aora.erp.presentation.entity.dto.settings.UsersetListDto;
import ru.aora.erp.presentation.entity.dto.settings.UsersetDto;
import ru.aora.erp.presentation.entity.dto.settings.UsersetDtoMapper;
import ru.aora.erp.presentation.entity.dto.settings.UsersetListDto;


import javax.validation.Valid;
import java.util.Map;

import static ru.aora.erp.presentation.entity.dto.settings.UsersetDtoMapper.toUserset;
import static ru.aora.erp.presentation.entity.dto.settings.UsersetDtoMapper.toListDto;


@Controller
@RequestMapping(UsersetUrl.MAPPING)
public final class UsersetController {

    private static final String DASHBOARD_TEMPLATE = "fragment/topbar";
    private static final String DTO_MODEL = "usersetDto";
    private final UsersetService usersetService;

    public UsersetController(UsersetService usersetService) {
        this.usersetService = usersetService;
    }
    @GetMapping
    public String UsersetForm( Map<String, Object> model) {
        final UsersetListDto usersetDto = toListDto(usersetService.loadAll());
        model.put(DTO_MODEL, usersetDto);
        return DASHBOARD_TEMPLATE;
    }
    @PutMapping
    public @ResponseBody
    String putUserset(@Valid @RequestBody UsersetDto dto, BindingResult bindingResult) {
        DtoValidationException.throwIfHasErrors(bindingResult);
        return usersetService.update(toUserset(dto)).getMsg();
    }

    @PostMapping
    public @ResponseBody String postUserset(@Valid @RequestBody UsersetDto dto, BindingResult bindingResult) {
        DtoValidationException.throwIfHasErrors(bindingResult);
        usersetService.create(toUserset(dto));
        return "User settings set was created";
    }

    @DeleteMapping("/{id}")
    public @ResponseBody String deleteUserset(@PathVariable String id) {
        return usersetService.delete(id).getMsg();
    }
}
