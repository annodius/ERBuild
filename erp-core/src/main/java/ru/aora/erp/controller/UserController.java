package ru.aora.erp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.aora.erp.entity.dto.UserIdModuleAuthorityDto;
import ru.aora.erp.model.entity.business.User;
import ru.aora.erp.entity.dto.UsersDto;
import ru.aora.erp.service.AuthorityModulesIdentifiersService;
import ru.aora.erp.service.UserService;
import ru.aora.erp.utils.common.CommonUtils;
import ru.aora.erp.utils.result.OperationResult;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public final class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private static final String USERS_MAPPING = "users";
    private static final String USERS_DTO_MODEL = "usersDto";
    private static final String MODULE_AUTHORITY_DTO_LIST_MODEL = "moduleAuthorityDtoList";

    private UserService userService;
    private AuthorityModulesIdentifiersService authorityModulesIdentifiersService;

    public UserController(UserService userService,
                          AuthorityModulesIdentifiersService authorityModulesIdentifiersService
    ) {
        this.userService = userService;
        this.authorityModulesIdentifiersService = authorityModulesIdentifiersService;
    }

    @GetMapping
    public String userForm(Map<String, Object> model) {
        OperationResult<List<User>, Exception> operationResult = OperationResult.lift(() -> userService.loadAll()).get();
        operationResult.getResult().ifPresent(result -> {
            UsersDto usersDto = UsersDto.of(result);
            Collection<UserIdModuleAuthorityDto> userModuleAuthorityDtoList = UserIdModuleAuthorityDto.of(
                    usersDto.getUsers(),
                    authorityModulesIdentifiersService.modulesAuthorities()
            );
            model.put(USERS_DTO_MODEL, usersDto);
            model.put(MODULE_AUTHORITY_DTO_LIST_MODEL, userModuleAuthorityDtoList);
        });
        operationResult.getFailure().ifPresent(ex -> LOGGER.debug(CommonUtils.getStackTrace(ex)));

        return USERS_MAPPING;
    }

    @PutMapping
    public @ResponseBody
    String putUser(@RequestBody User user) {
        OperationResult<User, Exception> operationResult = OperationResult.get(() -> userService.updateUser(user));
        operationResult.getFailure().ifPresent(ex -> LOGGER.debug(CommonUtils.getStackTrace(ex)));
        return "update";
    }

    @DeleteMapping("/{id}")
    public @ResponseBody
    String deleteUser(@PathVariable long id) {
        OperationResult<Long, Exception> operationResult = OperationResult.get(() -> userService.deleteUser(id));
        operationResult.getFailure().ifPresent(ex -> LOGGER.debug(CommonUtils.getStackTrace(ex)));
        return "delete";
    }
}