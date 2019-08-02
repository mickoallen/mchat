CREATE DATABASE `mchat`;

USE `mchat`;

CREATE TABLE users(
   uuid UUID PRIMARY KEY,
   username VARCHAR (50) UNIQUE NOT NULL,
   password VARCHAR (50) NOT NULL,
   created_date TIMESTAMP NOT NULL,
   last_active TIMESTAMP
);

CREATE TABLE conversations(
   uuid UUID PRIMARY KEY,
   username VARCHAR (50) UNIQUE NOT NULL,
   password VARCHAR (50) NOT NULL,
   created_date TIMESTAMP NOT NULL,
   last_active TIMESTAMP
);

CREATE TABLE messages(
   uuid UUID PRIMARY KEY,
   conversation_uuid UUID
   username VARCHAR (50) UNIQUE NOT NULL,
   password VARCHAR (50) NOT NULL,
   created_date TIMESTAMP NOT NULL,
   last_active TIMESTAMP
);