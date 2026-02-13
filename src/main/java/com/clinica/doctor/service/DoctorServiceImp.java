package com.clinica.doctor.service;

import com.clinica.doctor.dto.DoctorAdminResponse;
import com.clinica.doctor.dto.DoctorPublicResponse;
import com.clinica.doctor.dto.DoctorRequest;
import com.clinica.doctor.entity.DoctorEntity;
import com.clinica.doctor.mapper.DoctorMapper;
import com.clinica.doctor.repository.DoctorRepository;
import com.clinica.person.repository.PersonRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DoctorServiceImp implements DoctorService{
    private final DoctorRepository doctorRepository;
    private final PersonRepository personRepository;
    private final DoctorMapper doctorMapper;

    @Override
    public Page<DoctorPublicResponse> getAllPublic(Pageable pageable) {
        return doctorRepository.findAll(pageable)
                .map(doctorMapper::toPublicResponse);
    }

    @Override
    public DoctorPublicResponse getByIdPublic(Long id) {
        return doctorRepository.findById(id)
                .map(doctorMapper::toPublicResponse)
                .orElseThrow(() -> new EntityNotFoundException("Doctor no encontrado con ID: " + id));
    }

    @Override
    public Page<DoctorAdminResponse> getAllAdmin(Pageable pageable) {
        return doctorRepository.findAll(pageable)
                .map(doctorMapper::toAdminResponse);
    }

    @Override
    public DoctorAdminResponse getByIdAdmin(Long id) {
        return doctorRepository.findById(id)
                .map(doctorMapper::toAdminResponse)
                .orElseThrow(() -> new EntityNotFoundException("Doctor no encontrado con ID: " + id));
    }

    @Override
    public Page<DoctorAdminResponse> searchAdmin(String filter, Pageable pageable) {
        if (filter == null || filter.isBlank())
            return getAllAdmin(pageable);

        return doctorRepository.search(filter, pageable)
                .map(doctorMapper::toAdminResponse);
    }

    @Override
    @Transactional
    public DoctorAdminResponse create(DoctorRequest request) {
        if (personRepository.existsByEmail(request.person().email()))
            throw new IllegalArgumentException("El email ya está registrado en el sistema");

        if (personRepository.existsByDocumentNumber(request.person().dni()))
            throw new IllegalArgumentException("El DNI ya está registrado");

        DoctorEntity entity = doctorMapper.toEntity(request);
        DoctorEntity saved = doctorRepository.save(entity);

        return doctorMapper.toAdminResponse(saved);
    }

    @Override
    @Transactional
    public DoctorAdminResponse update(Long id, DoctorRequest request) {
        DoctorEntity entity = doctorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Doctor no encontrado"));

        doctorMapper.updateEntity(entity, request);

        DoctorEntity updated = doctorRepository.save(entity);

        return doctorMapper.toAdminResponse(updated);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!doctorRepository.existsById(id))
            throw new EntityNotFoundException("Doctor no encontrado para eliminar");

        doctorRepository.deleteById(id);
    }

    @Override
    public Page<DoctorAdminResponse> getAllDeleted(Pageable pageable) {
        return doctorRepository.findAllDeleted(pageable)
                .map(doctorMapper::toAdminResponse);
    }

    @Override
    @Transactional
    public void restore(Long id) {
        doctorRepository.restoreDoctor(id);
    }
}
