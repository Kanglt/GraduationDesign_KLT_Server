set ANSI_NULLS ON
set QUOTED_IDENTIFIER ON
go

ALTER proc [dbo].[StuProc]
@msg VARCHAR(1000) OUTPUT,--MSG
@size INT OUTPUT--ROW SIZE
as 
begin
select * from [user]
end

