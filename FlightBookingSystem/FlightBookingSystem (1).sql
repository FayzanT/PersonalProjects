SET SQL_SAFE_UPDATES = 0;
DROP DATABASE IF EXISTS AIR_TRAVEL;
CREATE DATABASE AIR_TRAVEL; 
USE AIR_TRAVEL;

DROP TABLE IF EXISTS Login;
CREATE TABLE Login(
  Username        varchar(255),
  Password 		  varchar(255),
  Email 		  varchar(255),
  Type 			  varchar(255),
  primary key (Username)	
); 

DROP TABLE IF EXISTS Aircrafts;
CREATE TABLE Aircrafts(
ID    int not null,
Size   varchar(255),
primary key(ID)
);

DROP TABLE IF EXISTS Flight;
CREATE TABLE Flight(
ID     int not null,
Destination varchar (3),
Time datetime not null,
Aircraft_ID int not null,
primary key(ID),
foreign key (Aircraft_ID) references Aircrafts(ID)
) ;



DROP TABLE IF EXISTS Seats;
CREATE TABLE Seats(
SeatNum varchar (10),
Class varchar (255),
Seat_Row int not null,
Seat_Column int not null,
Passenger_Name varchar (255),
Flight_ID int not null,
Aircraft_ID int not null,
primary key(SeatNum),
foreign key(Flight_ID) references  Flight(ID),
foreign key(AirCraft_ID) references  Aircrafts(ID),
foreign key (Passenger_Name) references Login(Username)
) ;

Drop trigger if exists delete_flight;

	DELIMITER $$
create trigger delete_flight
before delete on Flight 
for each row
begin
	delete from Seats
    where Flight_ID = old.ID;
end;
$$    

Drop trigger if exists delete_aircraft;

	DELIMITER $$
create trigger delete_aircraft
before delete on Aircrafts 
for each row
begin
	delete from Seats
    where Aircraft_ID = old.ID;
end;
$$    

Drop trigger if exists delete_user;

	DELIMITER $$
create trigger delete_user
before delete on Login 
for each row
begin
	update Seats
    set Passenger_Name = null;
end;
$$    

INSERT INTO Login ( Username, Password, Email, Type)
values 
("MoussaviMan", "Moo", "moo@gmail.com" , "Member"),
("SofelaSofa", "Sofa", "sofa@gmail.com", "Admin"),
("Random101", "Random", "random@gmail.com", "Member"),
("Mario", "Luigi", "mario@gmail.com", "Agent")
;

 
INSERT INTO Aircrafts ( ID, size)
values 
(1001, "Large"),
(1002, "Small"),
(1003, "Medium"),
(1004, "Medium")
;
 
 
INSERT INTO Flight ( ID, Destination, Time, Aircraft_ID)
values 
(100, "YYC", '2023-11-02', 1001),
(200, "CWL", "2023-12-28", 1002),
(300, "BJZ", "2024-01-01", 1003),
(400, "YYC", "2023-11-02", 1004)
;

INSERT INTO Seats ( SeatNum, Class, Seat_Row, Seat_Column, Passenger_Name, Flight_ID, Aircraft_ID)
values 
("1A", "First", 1, 1, null, 100, 1001),
("2A", "First", 1, 2, "MoussaviMan", 100, 1001),
("3A", "First", 1, 3, null, 100, 1001),
("4A", "First", 1, 4, null, 100, 1001),
("5A", "First", 1, 5, null, 100, 1001),
("6A", "Business", 2, 1, null, 100, 1001),
("7A", "Business", 2, 2, null, 100, 1001),
("8A", "Business", 2, 3, null, 100, 1001),
("9A", "Business", 2, 4, null, 100, 1001),
("10A", "Business", 2, 5, null, 100, 1001),
("11A", "Economy", 3, 1, null, 100, 1001),
("12A", "Economy", 3, 2, null, 100, 1001),
("13A", "Economy", 3, 3, null, 100, 1001),
("14A", "Economy", 3, 4, "Random101", 100, 1001),
("15A", "Economy", 3, 5, null, 100, 1001),
("1B", "First", 1, 1, null, 200, 1002),
("2B", "First", 2, 1, null, 200, 1002),
("3B", "Business", 3, 1, "Mario", 200, 1002),
("4B", "Business", 1, 2, null, 200, 1002),
("5B", "Economy", 2, 2, null, 200, 1002),
("6B", "Economy", 3, 2, null, 200, 1002),
("1C", "First", 1, 1, null, 300, 1003),
("2C", "First", 1, 2, "SofelaSofa", 300, 1003),
("3C", "First", 1, 3, null, 300, 1003),
("4C", "Business", 2, 1, null, 300, 1003),
("5C", "Business", 2, 2, null, 300, 1003),
("6C", "Business", 2, 3, null, 300, 1003),
("7C", "Economy", 3, 1, null, 300, 1003),
("8C", "Economy", 3, 2, null, 300, 1003),
("9C", "Economy", 3, 3, null, 300, 1003),
("1D", "First", 1, 1, null, 400, 1004),
("2D", "First", 1, 2, null, 400, 1004),
("3D", "First", 1, 3, null, 400, 1004),
("4D", "Business", 2, 1, null, 400, 1004),
("5D", "Business", 2, 2, null, 400, 1004),
("6D", "Business", 2, 3, null, 400, 1004),
("7D", "Economy", 3, 1, null, 400, 1004),
("8D", "Economy", 3, 2, null, 400, 1004),
("9D", "Economy", 3, 3, null, 400, 1004)
;




select * from Seats;
SET SQL_SAFE_UPDATES = 1;
 
