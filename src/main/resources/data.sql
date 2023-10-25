insert into users (ID, USERNAME, PASSWORD , NAME)
values (1, 'admin', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', 'admin');

insert into AUTHORITY (AUTHORITY_NAME) values ('ROLE_USER');
insert into AUTHORITY (AUTHORITY_NAME) values ('ROLE_ADMIN');

insert into USER_AUTHORITY (ID, AUTHORITY_NAME) values (1, 'ROLE_USER');
insert into USER_AUTHORITY (ID, AUTHORITY_NAME) values (1, 'ROLE_ADMIN');