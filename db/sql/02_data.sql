INSERT INTO persons (first_name, last_name, dni, email, phone, address) VALUES
-- Futuros Doctores
('Gregory', 'House', '10000001', 'house@clinic.com', '555-0001', '221B Baker St'),
('Meredith', 'Grey', '10000002', 'grey@clinic.com', '555-0002', 'Seattle Grace'),
('Shaun', 'Murphy', '10000003', 'murphy@clinic.com', '555-0003', 'San Jose St'),
('Stephen', 'Strange', '10000004', 'strange@clinic.com', '555-0004', 'Bleecker St'),
('John', 'Watson', '10000005', 'watson@clinic.com', '555-0005', '221B Baker St'),
('Leonard', 'McCoy', '10000006', 'mccoy@clinic.com', '555-0006', 'Enterprise Ship'),
('Dana', 'Scully', '10000007', 'scully@clinic.com', '555-0007', 'FBI HQ'),
('Hannibal', 'Lecter', '10000008', 'lecter@clinic.com', '555-0008', 'Baltimore State'),
('Julius', 'Hibbert', '10000009', 'hibbert@clinic.com', '555-0009', 'Springfield'),
('Doogie', 'Howser', '10000010', 'howser@clinic.com', '555-0010', 'LA Hospital'),

-- Futuros Pacientes
('Marty', 'McFly', '20000001', 'marty@future.com', '555-1001', 'Hill Valley'),
('Sarah', 'Connor', '20000002', 'sarah@skynet.com', '555-1002', 'Los Angeles'),
('Tony', 'Stark', '20000003', 'tony@stark.com', '555-1003', 'Malibu Point'),
('Bruce', 'Wayne', '20000004', 'bruce@wayne.com', '555-1004', 'Gotham Manor'),
('Peter', 'Parker', '20000005', 'peter@dailybugle.com', '555-1005', 'Queens NY'),
('Clark', 'Kent', '20000006', 'clark@planet.com', '555-1006', 'Metropolis'),
('Diana', 'Prince', '20000007', 'diana@themyscira.com', '555-1007', 'Paris Louvre'),
('Wade', 'Wilson', '20000008', 'wade@pool.com', '555-1008', 'Unknown Dump'),
('Walter', 'White', '20000009', 'heisenberg@meth.com', '555-1009', 'Albuquerque'),
('Jesse', 'Pinkman', '20000010', 'jesse@yo.com', '555-1010', 'Albuquerque');


-- B) Insertar 10 Doctores (Vinculados a Personas 1-10)
INSERT INTO doctors (specialty, consultation_price, person_id) VALUES
('Diagnostic Medicine', 500.00, 1), -- House
('General Surgery', 300.00, 2),    -- Grey
('Pediatric Surgery', 350.00, 3),  -- Murphy
('Neurosurgery', 1000.00, 4),      -- Strange
('General Practice', 150.00, 5),   -- Watson
('Space Medicine', 400.00, 6),     -- McCoy
('Forensic Pathology', 250.00, 7), -- Scully
('Psychiatry', 600.00, 8),         -- Lecter
('Family Medicine', 200.00, 9),    -- Hibbert
('Pediatrics', 180.00, 10);        -- Howser


-- C) Insertar 10 Pacientes (Vinculados a Personas 11-20)
INSERT INTO patients (health_insurance_code, blood_type, allergies, person_id) VALUES
('INS-TIME-001', 'A+', 'Penicillin', 11),      -- McFly
('INS-SKY-002', 'O-', 'None', 12),             -- Connor
('INS-STARK-003', 'AB+', 'Shrapnel', 13),      -- Stark
('INS-WAYNE-004', 'B+', 'Bats', 14),           -- Wayne
('INS-SPID-005', 'A-', 'Spider bites', 15),    -- Parker
('INS-KRYP-006', 'O+', 'Kryptonite', 16),      -- Kent
('INS-GOD-007', 'AB-', 'None', 17),            -- Prince
('INS-REGEN-008', 'O+', 'Everything', 18),     -- Wilson
('INS-BLUE-009', 'B-', 'Chemo drugs', 19),     -- White
('INS-YO-010', 'A+', 'Chili powder', 20);      -- Pinkman


INSERT INTO appointments (appointment_date, status, reason, diagnosis, doctor_id, patient_id) VALUES
('2026-03-01 09:00:00', 'SCHEDULED', 'Dolor de espalda tras viaje en el tiempo', NULL, 1, 1),
('2026-03-01 10:00:00', 'COMPLETED', 'Chequeo general post-terminator', 'Paciente saludable pero paranoico', 2, 2),
('2026-03-02 11:00:00', 'CONFIRMED', 'Palpitaciones en el corazón arc reactor', NULL, 3, 3),
('2026-03-02 12:00:00', 'CANCELLED', 'Insomnio crónico y dolor articular', 'Cita cancelada por emergencia en Gotham', 4, 4),
('2026-03-03 09:30:00', 'SCHEDULED', 'Picadura extraña en la mano', NULL, 5, 5),
('2026-03-03 14:00:00', 'COMPLETED', 'Alergia severa a roca verde', 'Se recomienda evitar meteoritos', 1, 6),
('2026-03-04 15:00:00', 'SCHEDULED', 'Revisión anual de inmortalidad', NULL, 6, 7),
('2026-03-04 16:30:00', 'NO_SHOW', 'Control de regeneración celular', 'Paciente no se presentó', 7, 8),
('2026-03-05 10:00:00', 'CONFIRMED', 'Tos persistente', NULL, 8, 9),
('2026-03-05 11:00:00', 'SCHEDULED', 'Ansiedad y estrés post-traumático', NULL, 9, 10);