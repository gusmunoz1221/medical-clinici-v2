package com.clinica.patient.controller;

import com.clinica.patient.dto.PatientDetailResponse;
import com.clinica.patient.dto.PatientListResponse;
import com.clinica.patient.dto.PatientRequest;
import com.clinica.patient.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v2/patients")
@RequiredArgsConstructor
@Tag(name = "Pacientes", description = "API para la gestión integral de pacientes en la clinica")
public class PatientController {

    private final PatientService patientService;

    @Operation(summary = "Crear un nuevo paciente", description = "Registra un paciente validando que el DNI y el Email no existan previamente.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Paciente creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o DNI/Email duplicados")
    })
    @PostMapping
    public ResponseEntity<PatientDetailResponse> createPatient(@Valid @RequestBody PatientRequest request) {
        return new ResponseEntity<>(patientService.createPatient(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Obtener paciente por ID", description = "Retorna el detalle completo del historial médico y datos personales del paciente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Paciente encontrado"),
            @ApiResponse(responseCode = "404", description = "Paciente no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PatientDetailResponse> getPatientById(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.getPatientById(id));
    }

    @Operation(summary = "Actualizar paciente", description = "Actualiza los datos de un paciente existente. Valida duplicidad de DNI/Email solo si han cambiado.")
    @PutMapping("/{id}")
    public ResponseEntity<PatientDetailResponse> updatePatient(@PathVariable Long id, @Valid @RequestBody PatientRequest request) {
        return ResponseEntity.ok(patientService.updatePatient(id, request));
    }

    @Operation(summary = "Eliminar paciente (Soft Delete)", description = "Realiza un borrado lógico del paciente en la base de datos.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar todos los pacientes", description = "Retorna una lista paginada con los datos básicos de todos los pacientes activos.")
    @GetMapping
    public ResponseEntity<Page<PatientListResponse>> getAllPatients(
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(patientService.getAllPatients(pageable));
    }

    @Operation(summary = "Buscar pacientes por nombre o apellido", description = "Búsqueda general que coincide parcialmente con el nombre o apellido (Ignora mayúsculas).")
    @GetMapping("/search")
    public ResponseEntity<Page<PatientListResponse>> searchPatientsByName(
            @RequestParam String query,
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(patientService.searchPatientsByName(query, pageable));
    }

    @Operation(summary = "Buscar paciente por DNI exacto", description = "Retorna el detalle completo de un paciente filtrando por su documento.")
    @GetMapping("/dni/{dni}")
    public ResponseEntity<PatientDetailResponse> getPatientByDni(
            @Parameter(description = "Documento Nacional de Identidad") @PathVariable String dni) {
        return ResponseEntity.ok(patientService.getPatientByDni(dni));
    }

    @Operation(summary = "Buscar paciente por Email exacto", description = "Retorna el detalle completo de un paciente filtrando por su correo.")
    @GetMapping("/email/{email}")
    public ResponseEntity<PatientDetailResponse> getPatientByEmail(
            @Parameter(description = "Correo electrónico del paciente") @PathVariable String email) {
        return ResponseEntity.ok(patientService.getPatientByEmail(email));
    }

    @Operation(summary = "Listar pacientes eliminados (Papelera)", description = "Retorna una lista paginada de los pacientes que fueron eliminados lógicamente.")
    @GetMapping("/deleted")
    public ResponseEntity<Page<PatientListResponse>> getAllDeletedPatients(
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(patientService.getAllDeletedPatients(pageable));
    }

    @Operation(summary = "Restaurar paciente eliminado", description = "Recupera un paciente de la papelera cambiando su estado is_deleted a false.")
    @PatchMapping("/{id}/restore")
    public ResponseEntity<Void> restorePatient(@PathVariable Long id) {
        patientService.restorePatient(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Buscar pacientes por apellido", description = "Retorna una lista paginada de pacientes cuyo apellido coincida parcialmente con el término de búsqueda (Ignora mayúsculas).")
    @GetMapping("/search/lastname")
    public ResponseEntity<Page<PatientListResponse>> searchPatientsByLastName(
            @Parameter(description = "Apellido a buscar") @RequestParam String lastName,
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(patientService.searchPatientsByLastName(lastName, pageable));
    }
}