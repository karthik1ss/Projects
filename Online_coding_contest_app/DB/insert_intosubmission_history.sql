use code_contest_db;

create procedure InsertintoSubmissionHistory(IN teamid int,IN contestid int,IN questionid int,IN filepath varchar(255),OUT result int)

begin

declare @team, @contest, @question int default 0
set @team=teamid;
set @contest=contestid;
set @question=questionid; 

if((select count(*) from submission_history where teamid=@team and contestid=@contest and questionid=@question) = 1 ) then

	update submission_history set submissioncount=submissioncount+1 where teamid=@teamid and contestid=@contestid and questionid=@questionid;
	set result=1;
end

else 
begin

	insert into submission_history values(1,1,now(),1,1,NULL,1,filepath);
    set result=1;
end 

declare exit handler for sqlexception set result=0;

end 

 