DROP DATABASE IF EXISTS SuperheroSightingsDB;
CREATE DATABASE SuperheroSightingsDB;

USE SuperheroSightingsDB;

DROP TABLE IF EXISTS Superpower;
CREATE TABLE Superpower (
	SuperpowerID INT PRIMARY KEY AUTO_INCREMENT,
    SuperpowerName VARCHAR(50)
);

DROP TABLE IF EXISTS Superhuman;
CREATE TABLE Superhuman (
	SuperhumanID INT PRIMARY KEY AUTO_INCREMENT,
    SuperhumanName VARCHAR(50),
    SuperhumanDescription VARCHAR(500),
    SuperpowerID INT,
    FOREIGN KEY fk_power(SuperpowerID) REFERENCES Superpower(SuperpowerID)
);

DROP TABLE IF EXISTS Location;
CREATE TABLE Location (
	LocationID INT PRIMARY KEY AUTO_INCREMENT,
    LocationName VARCHAR(100),
    LocationDescription VARCHAR(500),
    LocationAddress VARCHAR(200),
    Latitude DECIMAL(8,6),
    Longitude DECIMAL(9,6)
);

DROP TABLE IF EXISTS Sighting;
CREATE TABLE Sighting (
	SightingID INT PRIMARY KEY AUTO_INCREMENT,
    SightingDate TIMESTAMP,
    LocationID INT,
    FOREIGN KEY fk_location(LocationID) REFERENCES Location(LocationID),
    SuperhumanID INT,
    FOREIGN KEY fk_superhuman(SuperhumanID) REFERENCES Superhuman(SuperhumanID)
);

DROP TABLE IF EXISTS Institution;
CREATE TABLE Institution (
	InstitutionID INT PRIMARY KEY AUTO_INCREMENT,
    InstitutionName VARCHAR(100),
    InstituionDescription VARCHAR(500),
    ContactInformation VARCHAR(500),
    LocationID INT,
    FOREIGN KEY fk_location(LocationID) REFERENCES Location(LocationID)
);

DROP TABLE IF EXISTS SuperhumanInstitution;
CREATE TABLE SuperhumanInstitution (
	SuperhumanID INT NOT NULL,
	InstitutionID INT NOT NULL,
    PRIMARY KEY pk_SuperhumanInstitution(SuperhumanID, InstitutionID),
    FOREIGN KEY fk_superhuman(SuperhumanID)
		REFERENCES Superhuman(SuperhumanID),
	FOREIGN KEY fk_institution(InstitutionID)
		REFERENCES Institution(InstitutionID)
);