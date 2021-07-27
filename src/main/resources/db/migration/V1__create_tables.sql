create table user(
     id int not null auto_increment,
     name varchar(50) not null,
     password varchar(100) not null,
     role varchar(10) not null,
     primary key (id),
     constraint unique_entry unique (id,name)
);

create table book(
     id int not null auto_increment,
     name varchar(50) not null,
     rentedTo int ,
     primary key(id),
     foreign key(rentedTo) references user(id)
);