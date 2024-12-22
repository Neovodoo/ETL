
-- user
drop table if exists UserDB cascade;
create table UserDB(
	id serial primary key, 
	login varchar not null,
	password varchar not null,
	address varchar not null,
	name varchar not null,
	email varchar not null,
	role varchar not null check (role in ('admin', 'client', 'artisan'))
);

insert into UserDB(login, password, address, name, email, role) values 
('admin', 'admin', 'admin', 'admin', 'admin', 'admin'),
('artisan', 'artisan', 'artisan', 'artisan', 'artisan', 'artisan'),
('client', 'client', 'client', 'client', 'client', 'client');

drop table if exists ItemDB cascade;
create table ItemDB(
	id serial primary key,
	name varchar not null,
	id_artisan int not null,
	foreign key (id_artisan) references UserDB(id),
	description varchar not null,
	price int not null,
	quantity int not null
);

insert into ItemDB(name, id_artisan, description, price, quantity) values 
('Product 1', 2, 'Product 1', 1500, 100),
('Product 2', 2, 'Product 2', 1600, 100);

drop table if exists OrderDB cascade;
create table if not exists OrderDB(
	id serial primary key,
	id_client int not null,
	foreign key (id_client) references UserDB(id),
	date timestamp not null,
	status varchar not null check (status in ('Working', 'Done', 'Delivering', 'Delivered'))
);

drop table if exists DetailOrderDB cascade;
create table if not exists DetailOrderDB(
	id serial primary key,
	id_order int not null,
	foreign key (id_order) references OrderDB(id),
	id_item int not null,
	foreign key (id_item) references ItemDB(id),
	id_artisan int not null,
	foreign key (id_artisan) references UserDB(id),
	status varchar not null check (status in ('Working', 'Done')),
	color varchar DEFAULT 'white',
	diameter varchar not null,
	pattern varchar,
	number int not null
);


drop table if exists CartDB cascade;
create table if not exists CartDB(
	id serial primary key,
	id_item int not null,
	foreign key (id_item) references itemDB(id),
	id_artisan int not null,
	foreign key (id_artisan) references itemDB(id),
	color varchar DEFAULT 'white',
	diameter varchar not null,
	pattern varchar,
	number int not null
);


