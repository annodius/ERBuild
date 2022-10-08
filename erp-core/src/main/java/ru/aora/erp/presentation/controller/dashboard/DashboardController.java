package ru.aora.erp.presentation.controller.dashboard;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.aora.erp.domain.service.UserparamService;
import ru.aora.erp.presentation.controller.exception.DtoValidationException;
import ru.aora.erp.presentation.entity.dto.settings.UserparamDto;
import ru.aora.erp.presentation.entity.dto.settings.UserparamDtoMapper;
import ru.aora.erp.presentation.entity.dto.settings.UserparamListDto;
import ru.aora.erp.presentation.presenter.AllSidebarPresenter;

import javax.validation.Valid;
import java.util.Map;


import static ru.aora.erp.utils.mvc.MvcUtils.redirectTo;

import static ru.aora.erp.presentation.entity.dto.settings.UserparamDtoMapper.toUserparam;

@Controller
public final class DashboardController {

    private static final String DASHBOARD_TEMPLATE = "dashboard";
    private static final String LOGIN_TEMPLATE = "login";
    private static final String DTO_MODEL = "userparamDto";
    private static final String UI_CHANE_NODE_MODEL = "uiChaneNodeModel";
    private final UserparamService userparamService;
    private final AllSidebarPresenter sidebarPresenter;

    public DashboardController(AllSidebarPresenter sidebarPresenter, UserparamService userparamService) {
        this.sidebarPresenter = sidebarPresenter;
        this.userparamService = userparamService;
    }

    @RequestMapping(DashboardUrl.MAPPING)
    public String dashboard( Map<String, Object> model) {
        final UserparamListDto userparamDto = UserparamListDto.of(
                UserparamDtoMapper.toUserparamDtoList(userparamService.loadAll())
        );

        model.put(
                UI_CHANE_NODE_MODEL,
                sidebarPresenter.allRootNodes()
        );
        model.put(DTO_MODEL, userparamDto);
        return DASHBOARD_TEMPLATE;
    }

    @PutMapping
    public @ResponseBody String putUsers(@Valid @RequestBody UserparamDto userparamDto, BindingResult bindingResult) {
        DtoValidationException.throwIfHasErrors(bindingResult);
        return userparamService.update(toUserparam(userparamDto)).getMsg();
    }

    @RequestMapping(DashboardUrl.ROOT_MAPPING)
    public String redirectToRoot() {
        return redirectTo(DASHBOARD_TEMPLATE);
    }

    @RequestMapping(DashboardUrl.LOGIN_MAPPING)
    public String login() {
        return LOGIN_TEMPLATE;
    }

}
