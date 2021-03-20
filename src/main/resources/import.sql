insert into role (id, name) values (1, 'ADMIN');
insert into role (id, name) values (2, 'USER');

insert into user (id, first_name, last_name, email, password)values (1, 'Malwina', 'Klocek', 'admin@mail.com', '$2a$10$pjv4Kep8.lERXeR6XFFTvuHnOtaJa3l5HFQKcNJqmCj8HyHknEKwC');
insert into user (id, first_name, last_name, email, password)values (2, 'Malwina', 'Klocek', 'user@mail.com', '$2a$10$pjv4Kep8.lERXeR6XFFTvuHnOtaJa3l5HFQKcNJqmCj8HyHknEKwC');

insert into user_role (user_id, role_id)values (1, 1);
insert into user_role (user_id, role_id)values (2, 2);