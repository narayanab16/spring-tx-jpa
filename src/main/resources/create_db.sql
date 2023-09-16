create table if not exists account(
ACCOUNT_NUMBER int PRIMARY KEY,
ACCOUNT_HOLDER_NAME varchar(20),
AMOUNT double,
VERSION int -- timestamp also allowed
);
insert into account values
(1001, 'Narayana Basetty', 100, 0),
(1002, 'Narayana', 200, 0),
(1003, 'Basetty', 100, 0);

