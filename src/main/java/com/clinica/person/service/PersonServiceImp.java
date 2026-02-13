package com.clinica.person.service;


import com.clinica.person.dto.PersonDtoRequest;
import com.clinica.person.dto.PersonDtoResponse;
import com.clinica.person.entity.PersonEntity;
import com.clinica.person.mapper.PersonMapper;
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
public class PersonServiceImp implements PersonService {
    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    @Override
    @Transactional
    public PersonDtoResponse create(PersonDtoRequest request) {
        if (personRepository.existsByDocumentNumber(request.dni()))
            throw new IllegalArgumentException("El DNI " + request.dni() + " ya está registrado.");

        if (personRepository.existsByEmail(request.email()))
            throw new IllegalArgumentException("El email " + request.email() + " ya está registrado.");

        PersonEntity entity = personMapper.toEntity(request);

        return personMapper.toResponse(entity);
    }

    @Override
    public PersonDtoResponse getById(Long id) {
        PersonEntity entity = personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Persona no encontrada con ID: " + id));
        return personMapper.toResponse(entity);
    }

    @Override
    @Transactional
    public PersonDtoResponse update(Long id, PersonDtoRequest request) {
        PersonEntity entity = personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Persona no encontrada con ID: " + id));

        if (!entity.getDni().equals(request.dni()) && personRepository.existsByDocumentNumber(request.dni()))
            throw new IllegalArgumentException("El DNI " + request.dni() + " ya pertenece a otra persona.");

        if (!entity.getEmail().equals(request.email()) && personRepository.existsByEmail(request.email()))
            throw new IllegalArgumentException("El email " + request.email() + " ya pertenece a otra persona.");


        personMapper.updateEntity(entity, request);

        return personMapper.toResponse(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!personRepository.existsById(id))
            throw new EntityNotFoundException("No se puede eliminar. ID " + id + " no encontrado.");

        personRepository.deleteById(id);
    }

    @Override
    public Page<PersonDtoResponse> getAll(Pageable pageable) {
        return personRepository.findAll(pageable)
                .map(personMapper::toResponse);
    }

    @Override
    public Page<PersonDtoResponse> search(String filter, Pageable pageable) {
        return personRepository.search(filter, pageable)
                .map(personMapper::toResponse);
    }

    @Override
    public PersonDtoResponse getByDni(String dni) {
        return personRepository.findByDocumentNumber(dni)
                .map(personMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("No existe persona con DNI: " + dni));
    }

    @Override
    public PersonDtoResponse getByEmail(String email) {
        return personRepository.findByEmail(email)
                .map(personMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("No existe persona con Email: " + email));
    }

    @Override
    public Page<PersonDtoResponse> getAllDeleted(Pageable pageable) {
        return personRepository.findAllDeleted(pageable)
                .map(personMapper::toResponse);
    }

    @Override
    @Transactional
    public void restore(Long id) {
        personRepository.restorePerson(id);
    }
}
