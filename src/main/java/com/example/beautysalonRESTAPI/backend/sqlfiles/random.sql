

create table employees(
    ID int primary key identity(1,1),
    emri nvarchar(20) not null,
    mbiemri nvarchar(20) not null,
    pershkrimi nvarchar(255),
    username nvarchar(20) not null unique,
    userpassword nvarchar(100) not null,
    data_regjistrimit datetime default getdate(),
    is_active bit not null default 1
);

select * from employees;