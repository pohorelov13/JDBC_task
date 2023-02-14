create database jdbc__task;

use jdbc__task;

create table Homework(
id int not null auto_increment,
name varchar(30) not NULL,
description text not NULL,
primary KEY(id));


create table Lesson(
id int not null auto_increment,
name varchar(30) not null, 
updatedAt datetime not null,
homework_id int not null unique,
primary key(id),
foreign key(homework_id)
references homework(id));


insert into homework(name, description)
values("name1", "description1"),
	("name2", "description2"),
	("name3", "description3"),
	("name4", "description4"),
	("name5", "description5"),
	("name6", "description6"),
	("name7", "description7"),
	("name8", "description8");

insert into lesson(name, updatedAt, homework_id)
values ("lesson name1", '2023-02-11 10:34:09', 3),
	("lesson name2", '2023-02-10 10:34:09', 1),
	("lesson name3", '2023-02-12 10:34:09', 5),
	("lesson name4", '2023-02-13 10:34:09', 2),
	("lesson name5", '2023-02-14 10:34:09', 6),
	("lesson name6", '2023-02-09 10:34:09', 8);

select *from lesson;
select *from homework;

SELECT lesson.*, homework.id as homeworkId, homework.name as homeworkName, homework.description 
FROM lesson 
JOIN homework 
ON (lesson.homework_id = homework.id);