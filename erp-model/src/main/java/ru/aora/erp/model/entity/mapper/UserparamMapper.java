package ru.aora.erp.model.entity.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import ru.aora.erp.model.entity.business.Userparam;
import ru.aora.erp.model.entity.db.DbUserparam;

@Mapper(
        unmappedSourcePolicy = ReportingPolicy.WARN,
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface UserparamMapper {

    UserparamMapper INSTANCE = Mappers.getMapper(UserparamMapper.class);

    Userparam toUserparam(DbUserparam dbUserparam);

    DbUserparam toDbUserparam(Userparam userparam);
}
