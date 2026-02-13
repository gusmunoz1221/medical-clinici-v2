package com.clinica.person.repository;

import com.clinica.person.entity.PersonEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<PersonEntity, Long> {

    Optional<PersonEntity> findByEmail(String email);

    Optional<PersonEntity> findByDocumentNumber(String documentNumber);

    boolean existsByEmail(String email);

    boolean existsByDocumentNumber(String documentNumber);

    @Query("""
        SELECT p FROM PersonEntity p
        WHERE LOWER(p.firstName) LIKE LOWER(CONCAT('%', :filter, '%'))
           OR LOWER(p.lastName) LIKE LOWER(CONCAT('%', :filter, '%'))
           OR p.documentNumber LIKE CONCAT('%', :filter, '%')
           OR LOWER(p.email) LIKE LOWER(CONCAT('%', :filter, '%'))
    """)
    Page<PersonEntity> search(@Param("filter") String filter, Pageable pageable);

    // --- PAPELERA ---
    @Query(
            value = "SELECT * FROM persons WHERE is_deleted = true",
            countQuery = "SELECT count(*) FROM persons WHERE is_deleted = true",
            nativeQuery = true
    )
    Page<PersonEntity> findAllDeleted(Pageable pageable);

    @Modifying
    @Query(value = "UPDATE persons SET is_deleted = false WHERE id = :id", nativeQuery = true)
    void restorePerson(@Param("id") Long id);

}