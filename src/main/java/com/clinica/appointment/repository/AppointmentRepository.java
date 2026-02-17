package com.clinica.appointment.repository;

import com.clinica.appointment.entity.AppointmentEntity;
import com.clinica.appointment.entity.AppointmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {

    //dctor+Persona y paciente+persona en el mismo viaje.
    @Query(value = "SELECT a FROM AppointmentEntity a " +
            "JOIN FETCH a.doctor d JOIN FETCH d.person " +
            "JOIN FETCH a.patient p JOIN FETCH p.person",
            countQuery = "SELECT count(a) FROM AppointmentEntity a")
    Page<AppointmentEntity> findAll(Pageable pageable);

    //detalle completo
    @Query("SELECT a FROM AppointmentEntity a " +
            "JOIN FETCH a.doctor d JOIN FETCH d.person " +
            "JOIN FETCH a.patient p JOIN FETCH p.person " +
            "WHERE a.id = :id")
    Optional<AppointmentEntity> findByIdWithDetails(@Param("id") Long id);

    //turnos de un DOCTOR específico
    @Query(value = "SELECT a FROM AppointmentEntity a " +
            "JOIN FETCH a.doctor d JOIN FETCH d.person " +
            "JOIN FETCH a.patient p JOIN FETCH p.person " +
            "WHERE d.id = :doctorId",
            countQuery = "SELECT count(a) FROM AppointmentEntity a WHERE a.doctor.id = :doctorId")
    Page<AppointmentEntity> findByDoctorId(@Param("doctorId") Long doctorId, Pageable pageable);

    //turnos de un PACIENTE especifico -> Historial médico
    @Query(value = "SELECT a FROM AppointmentEntity a " +
            "JOIN FETCH a.doctor d JOIN FETCH d.person " +
            "JOIN FETCH a.patient p JOIN FETCH p.person " +
            "WHERE p.id = :patientId",
            countQuery = "SELECT count(a) FROM AppointmentEntity a WHERE a.patient.id = :patientId")
    Page<AppointmentEntity> findByPatientId(@Param("patientId") Long patientId, Pageable pageable);


    boolean existsByDoctorIdAndAppointmentDateAndStatusNot(
            Long doctorId,
            LocalDateTime appointmentDate,
            AppointmentStatus status
    );

    boolean existsByPatientIdAndAppointmentDateAndStatusNot(
            Long patientId,
            LocalDateTime appointmentDate,
            AppointmentStatus status
    );
}