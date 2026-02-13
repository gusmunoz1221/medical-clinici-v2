package com.clinica.patient.repository;

import com.clinica.patient.entity.PatientEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<PatientEntity,Long> {
    @EntityGraph(attributePaths = {"person"})
    Optional<PatientEntity> findByPersonEmail(String email);

    @EntityGraph(attributePaths = {"person"})
    Optional<PatientEntity> findByPersonDocumentNumber(String documentNumber);

    @EntityGraph(attributePaths = {"person"})
    Page<PatientEntity> findByPersonLastNameContainingIgnoreCase(String lastName, Pageable pageable);

    @EntityGraph(attributePaths = {"person"})
    Page<PatientEntity> findByPersonFirstNameContainingIgnoreCaseOrPersonLastNameContainingIgnoreCase(String name, String lastName, Pageable pageable);

    // --- VALIDACIONES ---
    boolean existsByPersonEmail(String email);
    boolean existsByPersonDocumentNumber(String documentNumber);

    // --- OPTIMIZACIONES ---
    // EntityGraph reemplaza al JOIN FETCH manual
    @Override
    @EntityGraph(attributePaths = {"person"})
    Page<PatientEntity> findAll(Pageable pageable);

    @Query("SELECT p FROM PatientEntity p JOIN FETCH p.person WHERE p.id = :id")
    Optional<PatientEntity> findByIdWithPerson(@Param("id") Long id);

    // --- PAPELERA ---

    @Query(
            value = "SELECT * FROM patients WHERE is_deleted = true",
            countQuery = "SELECT count(*) FROM patients WHERE is_deleted = true", // Necesario para Page
            nativeQuery = true
    )
    Page<PatientEntity> findAllDeleted(Pageable pageable);

    @Query(value = "SELECT * FROM patients", nativeQuery = true)
    List<PatientEntity> findAllIncludingDeleted();

    @Modifying
    @Query(value = "UPDATE patients SET is_deleted = false WHERE id = :id", nativeQuery = true)
    void restorePatient(@Param("id") Long id);
}

