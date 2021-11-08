insert into user values (9001, sysdate(), 'USER1', 'test1', '123456-1234566');
insert into user values (9002, sysdate(), 'USER2', 'test2', '123456-1234566');
insert into user values (9003, sysdate(), 'USER3', 'test3', '123456-1234566');

insert into post values(10001, 'My first Post', 9001);
insert into post values(10002, 'My second Post', 9002);