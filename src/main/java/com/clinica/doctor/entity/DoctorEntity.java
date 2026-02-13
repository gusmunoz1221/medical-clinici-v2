package com.clinica.doctor.entity;

import com.clinica.person.entity.PersonEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SoftDelete;
import java.math.BigDecimal;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "doctors")
@Entity
@SoftDelete(columnName = "is_deleted")
public class DoctorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String specialty;

    @Column(name = "consultation_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal consultationPrice;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id", unique = true, nullable = false)
    @ToString.Exclude
    private PersonEntity person;
}
