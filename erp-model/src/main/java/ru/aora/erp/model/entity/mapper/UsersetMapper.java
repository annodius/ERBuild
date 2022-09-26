package ru.aora.erp.model.entity.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import ru.aora.erp.model.entity.business.Userset;
import ru.aora.erp.model.entity.db.DbUserset;

@Mapper(
        unmappedSourcePolicy = ReportingPolicy.WARN,
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface UsersetMapper {

    UsersetMapper INSTANCE = Mappers.getMapper(UsersetMapper.class);

    Userset toUserset(DbUserset dbUserset);

    DbUserset toDbUserset(Userset userset);
}
