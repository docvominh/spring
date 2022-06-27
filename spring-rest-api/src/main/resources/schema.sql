CREATE TABLE device
(
    id          IDENTITY NOT NULL PRIMARY KEY,
    device_name VARCHAR(100),
    manufacture VARCHAR(50),
    price       INT
);

INSERT INTO device(device_name,manufacture,price) values ('Dell xps 13', 'Dell', 1300);
INSERT INTO device(device_name,manufacture,price) values ('Dell xps 15', 'Dell', 1500);
INSERT INTO device(device_name,manufacture,price) values ('Dell xps 17', 'Dell', 1700);
INSERT INTO device(device_name,manufacture,price) values ('Mac Book pro M1', 'Apple', 1600);
INSERT INTO device(device_name,manufacture,price) values ('Mac Book pro M2', 'Apple', 1800);