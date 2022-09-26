package ru.aora.erp.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.aora.erp.model.entity.db.DbUserset;

import javax.transaction.Transactional;


@Repository
@Transactional
public interface JpaUsersetRepository extends JpaRepository<DbUserset, String> {
}
