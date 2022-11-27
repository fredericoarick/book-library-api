create table if not exists Customer
(
    cpf       char(11),
    name      varchar(50) not null,
    birth_date date        not null,
    primary key (cpf)
);

create table if not exists Book
(
    id               integer,
    title            varchar(200) not null,
    author           varchar(50) not null,
    publication_date date not null,
    primary key (id)
);

create sequence if not exists Book_Seq owned by Book.id;

create table if not exists Book_Copy
(
    id        integer,
    book_id   integer not null,
    available bool not null,
    primary key (id),
    foreign key (book_id) references Book
);

create sequence if not exists Book_Copy_Seq owned by Book_Copy.id;


create table if not exists Book_Withdrawal
(
    id                integer,
    book_copy_id      integer,
    customer_cpf      char(11),
    withdraw_date     timestamp,
    return_limit_date date check ( return_limit_date < Book_Withdrawal.withdraw_date + interval '2 months'),
    primary key (id),
    foreign key (book_copy_id) references Book_Copy,
    foreign key (customer_cpf) references Customer
);

create sequence if not exists Book_Withdrawal_Seq owned by Book_Withdrawal.id;

create table if not exists Book_Withdrawal_History
(
    id                integer,
    book_copy_id      integer,
    customer_cpf      char(11),
    withdraw_date     timestamp,
    return_limit_date date,
    return_date       timestamp,
    primary key (id),
    foreign key (book_copy_id) references Book_Copy,
    foreign key (customer_cpf) references Customer
);


create or replace function retrive_book()
returns trigger as '
    begin
        update book_copy
        set available = false
        where id = new.book_copy_id;
        return null;
    end;
' language plpgsql;

create or replace trigger Book_Withrawal_Insert after insert on Book_Withdrawal
for each row
execute procedure  retrive_book();

create or replace function return_book()
returns trigger as '
    begin
        update book_copy
        set available = true
        where id = old.book_copy_id;
        insert into Book_Withdrawal_History (id, book_copy_id, customer_cpf, withdraw_date, return_limit_date, return_date)
            values (old.id, old.book_copy_id, old.customer_cpf, old.withdraw_date, old.return_limit_date, NOW());
        return null;
    end;
' language plpgsql;

create or replace trigger Book_Withrawal_Delete after delete on Book_Withdrawal
for each row
execute procedure  return_book();


create or replace view Book_And_Withdrawal_View as
select
    bw.id as withdrawal_id,
    b.title,
    b.author,
    b.id as book_id,
    bc.id as book_copy_id,
    bw.customer_cpf,
    bw.withdraw_date,
    bw.return_limit_date,
    now() > bw.return_limit_date as late_to_return
from book_withdrawal bw
         inner join book_copy bc on bc.id = bw.book_copy_id
         inner join book b on b.id = bc.book_id;

create or replace view Book_And_Withdrawal_History_View as
select
    bw.id as withdrawal_id,
    b.title,
    b.author,
    b.id as book_id,
    bc.id as book_copy_id,
    bw.customer_cpf,
    bw.withdraw_date,
    bw.return_limit_date,
    bw.return_date,
    bw.return_date > bw.return_limit_date as returned_late
from book_withdrawal_history bw
         inner join book_copy bc on bc.id = bw.book_copy_id
         inner join book b on b.id = bc.book_id;
