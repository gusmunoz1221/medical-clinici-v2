package com.clinica.patient.entity;

import com.clinica.person.entity.PersonEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SoftDelete;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "patients")
@Entity
@SoftDelete(columnName = "is_deleted")
public class PatientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "health_insurance_code", length = 50)
    private String healthInsuranceCode;

    @Column(name = "blood_type", length = 5)
    private String bloodType;

    @Column(columnDefinition = "TEXT")
    private String allergies;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", referencedColumnName = "id", nullable = false, unique = true)
    private PersonEntity personEntity;
}
