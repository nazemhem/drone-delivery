INSERT INTO drone (serial_number, model, state, battery_capacity)

VALUES ('D1', 'Lightweight', 'IDLE', 100),
 ('D2', 'Heavyweight', 'IDLE', 100),
 ('D3', 'Lightweight', 'LOADING', 100),
 ('D4', 'Heavyweight', 'IDLE', 100),
 ('D5', 'Lightweight', 'LOADED', 100),
 ('D6', 'Heavyweight', 'DELIVERING', 100),
 ('D7', 'Heavyweight', 'DELIVERING', 100),
 ('D8', 'Heavyweight', 'LOADING', 100),
 ('D9', 'Lightweight', 'RETURNING', 100),
 ('D10', 'Middleweight', 'DELIVERING', 100);

 INSERT INTO medication (name, code, weight, image_url)
 VALUES ('Acetaminophen', 'M1', 50, 'image-url.com/acetaminophen'),
 ('Adderall', 'M2', 26, 'image-url.com/adderall'),
 ('Amitriptyline', 'M3', 26, 'image-url.com/Amitriptyline'),
 ('Atorvastatin', 'M4', 26, 'image-url.com/Atorvastatin'),
 ('Azithromycin', 'M5', 26, 'image-url.com/Azithromycin');

 INSERT INTO drone_medication(drone_serial_number, medication_code, med_count)
 values ('D1', 'M1', 1), ('D1', 'M2', 2), ('D1', 'M2', 3), ('D2', 'M1', 4);
