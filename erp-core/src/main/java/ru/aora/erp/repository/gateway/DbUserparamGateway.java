package ru.aora.erp.repository.gateway;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aora.erp.domain.CrudGateway;
import ru.aora.erp.model.entity.business.Userparam;
import ru.aora.erp.model.entity.db.DbUserparam;
import ru.aora.erp.model.entity.db.Deactivatable;
import ru.aora.erp.model.entity.mapper.UserparamMapper;
import ru.aora.erp.utils.common.CommonUtils;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.aora.erp.model.entity.db.Deactivatable.INACTIVE_ENTITY_FLAG;


@Transactional
public class DbUserparamGateway implements CrudGateway<Userparam, String> {

    private final JpaRepository<DbUserparam, String> repository;
    private final UserparamMapper mapper = UserparamMapper.INSTANCE;

    public DbUserparamGateway(JpaRepository<DbUserparam, String> repository) {
        this.repository = repository;
    }

    @Override
    public List<Userparam> loadAllActive() {
        return repository.findAll()
                .stream()
                .filter(Deactivatable::isActive)
                .map(mapper::toUserparam)
                .collect(Collectors.toList());
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public Optional<Userparam> getById(String string) {
        return Optional.ofNullable(repository.getOne(string))
                .map(mapper::toUserparam)
                .or(Optional::empty);
    }

    @Override
    public Userparam create(Userparam userparam) {
        userparam.setId(null);
        DbUserparam res = repository.save(mapper.toDbUserparam(userparam));
        return mapper.toUserparam(res);
    }

    @Override
    public Optional<Userparam> update(Userparam userparam) {
        Optional<DbUserparam> optionalTarget = repository.findById(userparam.getId())
                .filter(Deactivatable::isActive)
                .map(this::setDeactivated);
        if (optionalTarget.isPresent()) {
            repository.save(optionalTarget.get());
            userparam.setId(null);
            DbUserparam res = repository.save(mapper.toDbUserparam(userparam));
            return Optional.ofNullable(mapper.toUserparam(res));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Userparam> delete(String id) {
        CommonUtils.requiredNotBlank(id);
        return repository.findById(id)
                .filter(Deactivatable::isActive)
                .map(this::setDeactivated)
                .map(repository::save)
                .map(mapper::toUserparam)
                .or(Optional::empty);
    }

    private DbUserparam setDeactivated(DbUserparam userparam) {
        return Objects.requireNonNull(userparam)
                .setActiveStatus(INACTIVE_ENTITY_FLAG)
                .setDeactivationDate(LocalDateTime.now());
    }
}
