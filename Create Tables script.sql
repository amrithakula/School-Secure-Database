create table Student
(studentID varchar (5),
lastName varchar(20) not null,
firstName varchar (20) not null,
middleInitial char(1) null,
streetAddress varchar (50) not null,
city varchar(20) not null,
zipcode numeric(5) not null,
birthday date not null,
sex char(1) not null,
age smallint null,
primary key (studentID));

create table Tuition
(year numeric(4) not null,
month numeric(2,1) not null,
amount float not null,
primary key(year, month)
);

create table Staff(
staffID varchar (5),
lastName varchar(20) not null,
firstName varchar (20) not null,
middleInitial char(1) null,
streetAddress varchar (50) not null,
city varchar(20) not null,
zipcode numeric(5) not null,
birthday date not null,
sex char(1) not null,
phone numeric(15,11) null,
jobType varchar(10),
managerID varchar(5),
salary float,
primary key (staffID)
);

create table Pay(
_date date not null,
studentID varchar(5) not null,
year numeric(4) not null,
month numeric(2,1) not null,
payment_type varchar(5), 
firstName varchar(20),
primary key(_date,studentID),
foreign key(studentID) references Student(studentID),
foreign key(firstName) references Parent(firstName),
foreign key(year, month) references Tuition (year,month)
);

create table Parent(
firstName varchar(20) not null,
lastName varchar(20) not null,
phone numeric(15,5) not null, 
job varchar(20),
studentID varchar(5) not null,
primary key(studentID, firstname, lastname),
foreign key(studentID) references Student(studentID)
);

create table Teach(
staffID varchar(5),
studentID varchar(5),
levelName varchar(20),
grade varchar(2),
semester varchar(6),
year numeric(4,0),
primary key(staffID, studentID),
foreign key(staffID) references Staff(staffID),
foreign key(studentID) references Student(studentID)
);