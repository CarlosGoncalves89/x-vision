create database if not exists xvision; 
use xvision; 

create table if not exists movie(

	movie_id int auto_increment not null, 
    title varchar(35) unique not null, 
    description varchar(255) not null, 
    thumbnail varchar(255) default "", 
    available tinyint(1) not null default 1, 
    
    primary key(movie_id)
);

create table if not exists customer(
	card_number varchar(16) unique not null, 
    cvv char(3) not null, 
    email varchar(40), 
    first_rental tinyint(1) default 1, 
    
    primary key(card_number)
);

create table if not exists rental(

	rental_id int auto_increment not null,
	movie_id int not null, 
    card_number varchar(16) not null, 
    offer_code varchar(10), 
    rental_date datetime not null, 
    expected_date datetime not null,
    return_date datetime, 
    finished tinyint(1) not null default 0, 
    
    primary key(rental_id),
    foreign key(movie_id) references movie(movie_id), 
    foreign key(card_number) references customer(card_number)
);

create table if not exists payment(

	rental_id int not null, 
	subtotal decimal(4,2) not null check (subtotal >= 2.99),
    discount decimal(4,2) not null check(discount >= 0.00),
    total decimal(4,2) not null check (total >= 0.00), 
    payment_date datetime not null, 
    
    foreign key(rental_id) references rental(rental_id)
);