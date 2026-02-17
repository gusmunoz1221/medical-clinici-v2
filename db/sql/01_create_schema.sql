CREATE TABLE IF NOT EXISTS persons (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    dni VARCHAR(20) NOT NULL UNIQUE,
    email VARCHAR(100) UNIQUE,
    phone VARCHAR(20),
    address VARCHAR(150),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_deleted BOOLEAN DEFAULT FALSE
);
CREATE INDEX idx_person_dni ON persons(dni);
CREATE INDEX idx_person_email ON persons(email);


CREATE TABLE IF NOT EXISTS doctors (
    id BIGSERIAL PRIMARY KEY,
    specialty VARCHAR(100) NOT NULL,
    consultation_price NUMERIC(10, 2) NOT NULL,
    person_id BIGINT NOT NULL UNIQUE,
    is_deleted BOOLEAN DEFAULT FALSE,
    CONSTRAINT fk_doctor_person FOREIGN KEY (person_id) REFERENCES persons(id)
);

CREATE TABLE IF NOT EXISTS patients (
    id BIGSERIAL PRIMARY KEY,
    health_insurance_code VARCHAR(50),
    blood_type VARCHAR(5),
    allergies TEXT,
    person_id BIGINT NOT NULL UNIQUE,
    is_deleted BOOLEAN DEFAULT FALSE,
    CONSTRAINT fk_patient_person FOREIGN KEY (person_id) REFERENCES persons(id)
);

CREATE TABLE IF NOT EXISTS appointments (
    id BIGSERIAL PRIMARY KEY,
    appointment_date TIMESTAMP NOT NULL,
    status VARCHAR(20) NOT NULL,
    reason TEXT,
    diagnosis TEXT,
    doctor_id BIGINT NOT NULL,
    patient_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_deleted BOOLEAN DEFAULT FALSE,

    CONSTRAINT fk_appointment_doctor FOREIGN KEY (doctor_id) REFERENCES doctors(id),
    CONSTRAINT fk_appointment_patient FOREIGN KEY (patient_id) REFERENCES patients(id),

    CONSTRAINT chk_appointment_status CHECK (status IN ('SCHEDULED', 'CONFIRMED', 'COMPLETED', 'CANCELLED', 'NO_SHOW'))
);