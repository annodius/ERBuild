package ru.aora.erp.repository.gateway;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aora.erp.domain.CrudGateway;
import ru.aora.erp.model.entity.business.Userset;
import ru.aora.erp.model.entity.db.DbUserset;
import ru.aora.erp.model.entity.db.Deactivatable;
import ru.aora.erp.model.entity.mapper.UsersetMapper;
import ru.aora.erp.utils.common.CommonUtils;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.aora.erp.model.entity.db.Deactivatable.INACTIVE_ENTITY_FLAG;


@Transactional
public class DbUsersetGateway implements CrudGateway<Userset, String> {

    private final JpaRepository<DbUserset, String> repository;
    private final UsersetMapper mapper = UsersetMapper.INSTANCE;

    public DbUsersetGateway(JpaRepository<DbUserset, String> repository) {
        this.repository = repository;
    }

    @Override
    public List<Userset> loadAllActive() {
        return repository.findAll()
                .stream()
                .filter(Deactivatable::isActive)
                .map(mapper::toUserset)
                .collect(Collectors.toList());
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public Optional<Userset> getById(String string) {
        return Optional.ofNullable(repository.getOne(string))
                .map(mapper::toUserset)
                .or(Optional::empty);
    }

    @Override
    public Userset create(Userset userset) {
        userset.setId(null);
        DbUserset res = repository.save(mapper.toDbUserset(userset));
        return mapper.toUserset(res);
    }

    @Override
    public Optional<Userset> update(Userset userset) {
        Optional<DbUserset> optionalTarget = repository.findById(userset.getId())
                .filter(Deactivatable::isActive)
                .map(this::setDeactivated);
        if (optionalTarget.isPresent()) {
            repository.save(optionalTarget.get());
            userset.setId(null);
            DbUserset res = repository.save(mapper.toDbUserset(userset));
            return Optional.ofNullable(mapper.toUserset(res));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Userset> delete(String id) {
        CommonUtils.requiredNotBlank(id);
        return repository.findById(id)
                .filter(Deactivatable::isActive)
                .map(this::setDeactivated)
                .map(repository::save)
                .map(mapper::toUserset)
                .or(Optional::empty);
    }

    private DbUserset setDeactivated(DbUserset userset) {
        return Objects.requireNonNull(userset)
                .setActiveStatus(INACTIVE_ENTITY_FLAG)
                .setDeactivationDate(LocalDateTime.now());
    }
}
