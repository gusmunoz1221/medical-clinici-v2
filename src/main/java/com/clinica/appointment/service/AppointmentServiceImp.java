package com.clinica.appointment.service;

import com.clinica.appointment.dto.AppointmentDetailResponse;
import com.clinica.appointment.dto.AppointmentListResponse;
import com.clinica.appointment.dto.AppointmentRequest;
import com.clinica.appointment.entity.AppointmentEntity;
import com.clinica.appointment.entity.AppointmentStatus;
import com.clinica.appointment.mapper.AppointmentMapper;
import com.clinica.appointment.repository.AppointmentRepository;
import com.clinica.doctor.entity.DoctorEntity;
import com.clinica.doctor.repository.DoctorRepository;
import com.clinica.exception.BusinessException;
import com.clinica.exception.ResourceNotFoundException;
import com.clinica.patient.entity.PatientEntity;
import com.clinica.patient.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AppointmentServiceImp implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final AppointmentMapper appointmentMapper;

    @Override
    @Transactional
    public AppointmentDetailResponse createAppointment(AppointmentRequest request) {

        DoctorEntity doctor = doctorRepository.findById(request.doctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor no encontrado con ID: " + request.doctorId()));

        PatientEntity patient = patientRepository.findById(request.patientId())
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado con ID: " + request.patientId()));

        if (appointmentRepository.existsByDoctorIdAndAppointmentDateAndStatusNot(
                request.doctorId(), request.date(), AppointmentStatus.CANCELLED))
            throw new BusinessException("El Doctor ya tiene un turno asignado en ese horario.");
        if (appointmentRepository.existsByPatientIdAndAppointmentDateAndStatusNot(
                request.patientId(), request.date(), AppointmentStatus.CANCELLED))
            throw new BusinessException("El Paciente ya tiene un turno reservado en ese horario.");

        AppointmentEntity appointment = appointmentMapper.toEntity(request, doctor, patient);
        AppointmentEntity savedAppointment = appointmentRepository.save(appointment);

        return appointmentMapper.toDetailResponse(savedAppointment);
    }

    @Override
    @Transactional
    public AppointmentDetailResponse updateAppointment(Long id, AppointmentRequest request) {
        AppointmentEntity appointment = appointmentRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new ResourceNotFoundException("Turno no encontrado con ID: " + id));

        if (appointment.getStatus() == AppointmentStatus.COMPLETED || appointment.getStatus() == AppointmentStatus.CANCELLED)
            throw new BusinessException("No se puede modificar un turno que ya fue completado o cancelado.");

        if (!appointment.getAppointmentDate().isEqual(request.date())) {
            if (appointmentRepository.existsByDoctorIdAndAppointmentDateAndStatusNot(
                    appointment.getDoctor().getId(), request.date(), AppointmentStatus.CANCELLED)) {
                throw new BusinessException("El Doctor ya tiene un turno asignado en el nuevo horario.");
            }
        }

        appointmentMapper.updateEntityFromDto(request, appointment);
        AppointmentEntity updated = appointmentRepository.save(appointment);

        return appointmentMapper.toDetailResponse(updated);
    }

    @Override
    @Transactional
    public void cancelAppointment(Long id) {
        AppointmentEntity appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Turno no encontrado con ID: " + id));

        if (appointment.getStatus() == AppointmentStatus.COMPLETED)
            throw new BusinessException("No se puede cancelar un turno que ya fue completado.");

        appointment.setStatus(AppointmentStatus.CANCELLED);
        appointmentRepository.save(appointment);
    }

    @Override
    @Transactional
    public void completeAppointment(Long id, String diagnosis) {
        AppointmentEntity appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Turno no encontrado con ID: " + id));

        if (appointment.getStatus() == AppointmentStatus.CANCELLED)
            throw new BusinessException("No se puede completar un turno que estaba cancelado.");

        appointmentMapper.markAsCompleted(appointment, diagnosis);
        appointmentRepository.save(appointment);
    }

    @Override
    public AppointmentDetailResponse getAppointmentById(Long id) {
        return appointmentRepository.findByIdWithDetails(id)
                .map(appointmentMapper::toDetailResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Turno no encontrado con ID: " + id));
    }

    @Override
    public Page<AppointmentListResponse> getAllAppointments(Pageable pageable) {
        return appointmentRepository.findAll(pageable)
                .map(appointmentMapper::toListResponse);
    }

    @Override
    public Page<AppointmentListResponse> getAppointmentsByDoctor(Long doctorId, Pageable pageable) {
        if (!doctorRepository.existsById(doctorId))
            throw new ResourceNotFoundException("Doctor no encontrado con ID: " + doctorId);
        return appointmentRepository.findByDoctorId(doctorId, pageable)
                .map(appointmentMapper::toListResponse);
    }

    @Override
    public Page<AppointmentListResponse> getAppointmentsByPatient(Long patientId, Pageable pageable) {
        if (!patientRepository.existsById(patientId))
            throw new ResourceNotFoundException("Paciente no encontrado con ID: " + patientId);

        return appointmentRepository.findByPatientId(patientId, pageable)
                .map(appointmentMapper::toListResponse);
    }

    @Override
    @Transactional
    public void deleteAppointment(Long id) {
        if (!appointmentRepository.existsById(id))
            throw new ResourceNotFoundException("No se puede eliminar. Turno con ID " + id + " no encontrado.");
        appointmentRepository.deleteById(id);
    }
}