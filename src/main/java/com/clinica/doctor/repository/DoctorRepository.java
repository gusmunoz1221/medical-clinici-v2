package com.clinica.doctor.repository;

import com.clinica.doctor.entity.DoctorEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DoctorRepository extends JpaRepository<DoctorEntity, Long> {

    @EntityGraph(attributePaths = {"person"})
    Optional<DoctorEntity> findByPersonDni(String dni);

    @EntityGraph(attributePaths = {"person"})
    Optional<DoctorEntity> findByPersonEmail(String email);

    boolean existsByPersonDni(String dni);
    boolean existsByPersonEmail(String email);

    //  BUSQUEDAS PROPIAS

    Page<DoctorEntity> findBySpecialtyContainingIgnoreCase(String specialty, Pageable pageable);

    @EntityGraph(attributePaths = {"person"})
    @Query("""
        SELECT d FROM DoctorEntity d
        WHERE LOWER(d.person.firstName) LIKE LOWER(CONCAT('%', :filter, '%'))
           OR LOWER(d.person.lastName) LIKE LOWER(CONCAT('%', :filter, '%'))
           OR LOWER(d.specialty) LIKE LOWER(CONCAT('%', :filter, '%'))
    """)
    Page<DoctorEntity> search(@Param("filter") String filter, Pageable pageable);

    @Query(
            value = "SELECT * FROM doctors WHERE is_deleted = true",
            countQuery = "SELECT count(*) FROM doctors WHERE is_deleted = true",
            nativeQuery = true
    )
    Page<DoctorEntity> findAllDeleted(Pageable pageable);

    @Modifying
    @Query(value = "UPDATE doctors SET is_deleted = false WHERE id = :id", nativeQuery = true)
    void restoreDoctor(@Param("id") Long id);
}