insert into users (username, password, enabled) values ('sam','$2a$12$MZG5FaH5nhUqf51tozUAEOsu45JZ6VLw/DgtV3PPWans2adX8NJ/i', 1);
insert into users (username, password, enabled) values ('ria','$2a$12$KoOXIyHvd0uU./ZZNooLJOBlLZtmw3eHcW9mlBnAn50t.Do9kUxDO', 1);
insert into users (username, password, enabled) values ('jack','$2a$12$3F8Q/B8ORVNy1PAo4LnZMO6p475JEDNJHVVSFnJo2ZCdNC2/HDney', 1);

insert into authorities(username, authority) values ('sam', 'ROLE_USER');
insert into authorities(username, authority) values ('ria', 'ROLE_USER');
insert into authorities(username, authority) values ('ria', 'ROLE_ADMIN');
insert into authorities(username, authority) values ('jack', 'ROLE_USER');

