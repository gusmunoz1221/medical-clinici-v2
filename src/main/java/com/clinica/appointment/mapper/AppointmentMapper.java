package com.clinica.appointment.mapper;

import com.clinica.appointment.dto.AppointmentDetailResponse;
import com.clinica.appointment.dto.AppointmentListResponse;
import com.clinica.appointment.dto.AppointmentRequest;
import com.clinica.appointment.entity.AppointmentEntity;
import com.clinica.appointment.entity.AppointmentStatus;
import com.clinica.doctor.entity.DoctorEntity;
import com.clinica.patient.entity.PatientEntity;
import org.springframework.stereotype.Component;

@Component
public class AppointmentMapper {

    public AppointmentEntity toEntity(AppointmentRequest request, DoctorEntity doctor, PatientEntity patient) {
        return AppointmentEntity.builder()
                .appointmentDate(request.date())
                .reason(request.reason())
                .status(AppointmentStatus.SCHEDULED)
                .doctor(doctor)
                .patient(patient)
                .build();
    }

    public AppointmentListResponse toListResponse(AppointmentEntity entity) {
        if (entity == null) return null;

        String doctorName = entity.getDoctor().getPerson().getLastName() + ", " + entity.getDoctor().getPerson().getFirstName();
        String patientName = entity.getPatient().getPerson().getLastName() + ", " + entity.getPatient().getPerson().getFirstName();

        return new AppointmentListResponse(
                entity.getId(),
                entity.getAppointmentDate(),
                entity.getStatus().name(),
                doctorName,
                patientName
        );
    }


    public AppointmentDetailResponse toDetailResponse(AppointmentEntity entity) {
        if (entity == null) return null;

        DoctorEntity doc = entity.getDoctor();
        PatientEntity pat = entity.getPatient();

        var docPerson = doc.getPerson();
        var patPerson = pat.getPerson();

        return new AppointmentDetailResponse(
                entity.getId(),
                entity.getAppointmentDate(),
                entity.getStatus().name(),
                entity.getReason(),
                entity.getDiagnosis(),
                doc.getId(),
                docPerson.getFirstName() + " " + docPerson.getLastName(),
                doc.getSpecialty(),
                pat.getId(),
                patPerson.getFirstName() + " " + patPerson.getLastName(),
                patPerson.getDni(),
                // Auditoría
                entity.getCreatedAt()
        );
    }

    public void updateEntityFromDto(AppointmentRequest request, AppointmentEntity entity) {
        if (request.date() != null)
            entity.setAppointmentDate(request.date());
        if (request.reason() != null && !request.reason().isBlank())
            entity.setReason(request.reason());
    }

    public void markAsCompleted(AppointmentEntity entity, String diagnosis) {
        entity.setStatus(AppointmentStatus.COMPLETED);
        entity.setDiagnosis(diagnosis);
    }
}
