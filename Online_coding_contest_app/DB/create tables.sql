use code_contest_db;

create table authentication (emailId varchar(50),password varchar(20), usertype varchar(10),
currentlogin timestamp,lastlogin timestamp);

alter table authentication add primary key (emailId);

create table participants (emailId varchar(50),name varchar(50),age int, 
CollegeName varchar(50),TeamName varchar(20));

create table questions (questionId int(10), questionDetail varchar(1000),weightage int(10),
filePath varchar(100), contestId int(10));

create table submission_score (teamId int(50), JudgeId int(50), submissionId int(10), 
ContestId int(30),questionId int(30),score int(10));

create table submission_history (submissionId int(10), TeamId int(50), SubmissionTime timestamp,
QuestionId int(30), SubmissionCount int, Score int);

create table contest (ContestId int(30),ContestType varchar(20),Contest_Start_time timestamp,
Contest_End_Time timestamp, ContestDescription varchar(1000));

Create table team (TeamId int(50), TeamName varchar(50),TotalMembers int(10),TeamUserName varchar(50), 
ContestId int(10));

create table admin(id int(50), name varchar(50),email varchar(50),userType varchar(20));

create table Contest_Register( contestId int(30),UserName varchar(50),UserType varchar(20),
scores int(10));



