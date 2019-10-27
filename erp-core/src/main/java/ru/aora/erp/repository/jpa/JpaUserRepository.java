package ru.aora.erp.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.aora.erp.model.entity.db.DbUser;

import javax.annotation.Nullable;
import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface JpaUserRepository extends JpaRepository<DbUser, String> {

    @Query(value = "SELECT u FROM DbUser u WHERE u.username = :name")
    Optional<DbUser> findByName(String name);

    @Query(value = "DELETE FROM DbUser u WHERE u.username = :id")
    void deleteById(@Nullable Long id);
}
