package ru.aora.erp.domain.service;

import ru.aora.erp.domain.CrudGateway;
import ru.aora.erp.domain.model.MsgServiceResult;
import ru.aora.erp.model.entity.business.Userparam;
import ru.aora.erp.utils.common.CommonUtils;

import java.util.List;
import java.util.Objects;

public class UserparamService {
    private final CrudGateway<Userparam, String> gateway;

    public UserparamService(CrudGateway<Userparam, String> gateway) {
        this.gateway = gateway;
    }

    public List<Userparam> loadAll() {
        return gateway.loadAllActive();
    }

    public MsgServiceResult update(Userparam userparam) {
        Objects.requireNonNull(userparam);
        return gateway.update(userparam)
                .map(c -> MsgServiceResult.success("Userparam updated"))
                .orElseGet(() -> MsgServiceResult.failed("Userparam to update not found"));
    }

    public Userparam create(Userparam userparam) {
        Objects.requireNonNull(userparam);
        return gateway.create(userparam);
    }

    public MsgServiceResult delete(String id) {
        CommonUtils.requiredNotBlank(id);
        return gateway.delete(id)
                .map(c -> MsgServiceResult.success("Userparam deleted"))
                .orElseGet(() -> MsgServiceResult.failed("Userparam to delete not found"));
    }
}



