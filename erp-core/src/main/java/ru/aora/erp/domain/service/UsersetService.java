package ru.aora.erp.domain.service;

import ru.aora.erp.domain.CrudGateway;
import ru.aora.erp.domain.model.MsgServiceResult;
import ru.aora.erp.model.entity.business.Userset;
import ru.aora.erp.utils.common.CommonUtils;

import java.util.List;
import java.util.Objects;

public class UsersetService {
    private final CrudGateway<Userset, String> gateway;

    public UsersetService(CrudGateway<Userset, String> gateway) {
        this.gateway = gateway;
    }

    public List<Userset> loadAll() {
        return gateway.loadAllActive();
    }

    public MsgServiceResult update(Userset userset) {
        Objects.requireNonNull(userset);
        return gateway.update(userset)
                .map(c -> MsgServiceResult.success("Userset updated"))
                .orElseGet(() -> MsgServiceResult.failed("Userset to update not found"));
    }

    public Userset create(Userset userset) {
        Objects.requireNonNull(userset);
        return gateway.create(userset);
    }

    public MsgServiceResult delete(String id) {
        CommonUtils.requiredNotBlank(id);
        return gateway.delete(id)
                .map(c -> MsgServiceResult.success("Userset deleted"))
                .orElseGet(() -> MsgServiceResult.failed("Userset to delete not found"));
    }
}



