use code_contest_db;

insert into Admin values (1,'Admin','admin@utdallas.edu','ADMIN');
insert into Admin values (2,'CSJudge1','csjudge1@utdallas.edu','JUDGE');
insert into Admin values (3,'CSJudge2','csjudge2@utdallas.edu','JUDGE');

insert into authentication  values ('admin@utdallas.edu','password','ADMIN',null,null);
insert into authentication  values ('csjudge1@utdallas.edu','csJudgepassword','JUDGE',null,null);
insert into authentication  values ('csjudge2@utdallas.edu','csJudgepassword','JUDGE',null,null);

insert into contest values (1,'Online','2012-10-10 12:00:00','2012-10-10 16:00:00','');
insert into contest values(2,'Offline','2012-10-11 12:00:00','2012-12-11 12:00:00','');

insert into team values (1,'CSRockers' ,4,'james@gmail.com',1);
insert into participants values ('james@gmail.com','James',19,'Univ of Texas at Dallas','CSRockers');
insert into participants values ('sarah@gmail.com','Sarah',19,'Univ of Texas at Dallas','CSRockers');
insert into participants values ('kathy@gmail.com','Kathy',19,'Univ of Texas at Dallas','CSRockers');
insert into participants values ('issac@gmail.com','Issac',19,'Univ of Texas at Dallas','CSRockers');

insert into submission_history values ( 1, 1, '2012-10-10 15:55:00',1, 1,0);
Insert into questions values (1, 'abc',100, 'abc/a.txt',1);

Insert into contest_register values (1, 'james@gmail.com', 'USER ',0);
Insert into contest_register values (1, 'csjudge1@utdallas.edu', 'JUDGE',NULL);

Insert into submission_score values (1,1, 1,1,1,0);


Update submission_score set score = 50
Where teamid=1 and judgeid=1 and contestid=1 and questionid=1;

Update contest_register set scores= 50
Where contestid =1;

update submission_history set Score=95 where submissionId=1 and TeamId=1
 and submissionCount=1;

update authentication a1 set a1.currentlogin = now() and a1.lastlogin in (select a2.currentlogin from authentication a2 where a2.emailId='james@gmail.com') 
where a1.emailId='james@gmail.com';


SET SQL_SAFE_UPDATES=0;


