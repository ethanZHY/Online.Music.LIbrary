
Drop table if exists Accreditor;
create table Accreditor
(
	id int primary key
    auto_increment
);
Drop table if exists Users;
create table Users
(
	id int primary key
    auto_increment,
	username varchar(255) not null,
	unique(username),
	password varchar(255) not null,
	assignedBy int not null,
	foreign key(assignedBy) references Accreditor(id) 
		on update cascade on delete no action
);
Drop table if exists Administrator;
create table Administrator
(
	id int primary key auto_increment,
	foreign key(id)
		references Users(id) on update cascade on delete cascade
);
Drop table if exists Playlist;
create table PlayList
(
	id int primary key
    auto_increment,
	name varchar(255) not null,
	createdBy int not null,
	foreign key(createdBy) references Playlist(id) 
		on update cascade on delete no action
);
Drop table if exists Resources;
create table Resources
(
	id int primary key auto_increment,
	name varchar(255) not null,
	artist varchar(255) not null,
	album varchar(255),
	genre enum('Pop','R&B','Rock','Classical','Blues','Electronic','Dance'),
	length decimal(3,2) not null,
	releaseYear int not null
);
Drop table if exists ContainPlayList;
create table ContainPlaylist
(
	contains int,
	foreign key(contains) references Resources(id)
		on update cascade on delete cascade,
	listedIn int,
	foreign key(listedIn) references Playlist(id)
		on update cascade on delete cascade,
	primary key(contains,listedIn)
);
Drop table if exists Comments;
create table Comments
(
	content varchar(500) not null,
	comments int,
	foreign key(comments) references Resources(id) 
		on update cascade on delete cascade,
	commentedBy int,
	foreign key(commentedBy) references Users(id)
		on update cascade on delete cascade,
	primary key(comments,commentedBy)
);
