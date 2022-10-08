package ru.aora.erp.presentation.controller.userparam;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.aora.erp.domain.service.UserparamService;
import ru.aora.erp.presentation.controller.exception.DtoValidationException;
import ru.aora.erp.presentation.entity.dto.settings.UserparamDto;
import ru.aora.erp.presentation.entity.dto.settings.UserparamDtoMapper;
import ru.aora.erp.presentation.entity.dto.settings.UserparamListDto;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;
import java.util.StringJoiner;

import static java.util.Objects.requireNonNull;
import static ru.aora.erp.presentation.entity.dto.settings.UserparamDtoMapper.toUserparam;

@Controller
@RequestMapping(UserparamUrl.MAPPING)
public final class UserparamController {

    private static final String CONTROLLER_MAPPING = "userparam";
    private static final String DTO_MODEL = "userparamDto";

    private final UserparamService userparamService;

    public UserparamController(UserparamService userparamService) {
        this.userparamService = userparamService;
    }

    @SuppressWarnings("Duplicates")
    @GetMapping
    public String userparamForm(
            Map<String, Object> model
    ) {
        final UserparamListDto userparamDto = UserparamListDto.of(
                UserparamDtoMapper.toUserparamDtoList(userparamService.loadAll())
        );

        model.put(DTO_MODEL, userparamDto);
        return CONTROLLER_MAPPING;
    }

    @PutMapping
    public @ResponseBody String putUserparam(@Valid @RequestBody UserparamDto dto, BindingResult bindingResult) {
        DtoValidationException.throwIfHasErrors(bindingResult);
        return userparamService.update(toUserparam(dto)).getMsg();
    }

    @PostMapping
    public @ResponseBody String postUserparam(@Valid @RequestBody UserparamDto dto, BindingResult bindingResult) {
        DtoValidationException.throwIfHasErrors(bindingResult);
        userparamService.create(toUserparam(dto));
        return "Userparam was created";
    }

    @DeleteMapping("/{id}")
    public @ResponseBody String deleteUserparam(@PathVariable String id) {
        return userparamService.delete(id).getMsg();
    }

}
