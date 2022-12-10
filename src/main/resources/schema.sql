CREATE TABLE drone (serial_number VARCHAR PRIMARY KEY, model VARCHAR NOT NULL, battery_capacity INTEGER DEFAULT 100, state VARCHAR)
CREATE TABLE medication(code VARCHAR PRIMARY KEY, name VARCHAR NOT NULL, weight DOUBLE NOT NULL, image_url VARCHAR)
CREATE TABLE drone_medication (drone_serial_number VARCHAR, medication_code VARCHAR, med_count INTEGER)