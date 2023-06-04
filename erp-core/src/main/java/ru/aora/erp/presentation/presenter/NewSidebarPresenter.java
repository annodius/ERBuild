package ru.aora.erp.presentation.presenter;

import ru.aora.erp.presentation.controller.TestController;
import ru.aora.erp.presentation.controller.garant.GarantUrl;
import ru.aora.erp.presentation.entity.dto.sidebar.SidebarChaneNodeDto;
import ru.aora.erp.presentation.entity.dto.sidebar.SidebarPresenter;

import javax.annotation.PostConstruct;

/**
 * Garant rating Sidebar Presenter
 */
@SuppressWarnings("DeprecatedIsStillUsed")
@Deprecated
public final class NewSidebarPresenter implements SidebarPresenter {

    private static final String CHILD_MAPPING = GarantUrl.GARANT_STORED;

    private SidebarChaneNodeDto firstUiChaneNode;

    @PostConstruct
    private void init() {
        final var PARENT_NAME = "РЕЙТИНГ";
        this.firstUiChaneNode = new SidebarChaneNodeDto(
                PARENT_NAME,
                CHILD_MAPPING
//                Collections.singletonList(
//                        new SidebarChaneNodeDto(
//                                "XXX",
//                                CHILD_MAPPING
//                        )
//                )
        );
    }

    @Override
    public SidebarChaneNodeDto rootElement() {
        return firstUiChaneNode;
    }

}
