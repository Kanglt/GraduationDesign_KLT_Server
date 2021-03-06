USE [ICCO]
GO
/****** Object:  UserDefinedFunction [dbo].[fjzx_local_tax_fn_get_position_names_by_ids]    Script Date: 07/15/2016 17:03:57 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

/** 获得多个职位名称的拼接串**/
ALTER FUNCTION [dbo].[fjzx_local_tax_fn_get_position_names_by_ids]
(
	@positionIds VARCHAR(500) 
)
RETURNS VARCHAR(500)
--WITH ENCRYPTION
AS
BEGIN
	DECLARE
		@positionId VARCHAR(22),
		@positionName VARCHAR(500),
		@positionNames VARCHAR(500)
	SET @positionNames=''
	
	DECLARE cursorId CURSOR
	FOR
	SELECT [VALUE_STR] FROM [dbo].[fjzx_frame_fn_split](@positionIds,',')
	OPEN cursorId
	FETCH NEXT FROM cursorId INTO @positionId
	WHILE(@@FETCH_STATUS=0)
	BEGIN
		select @positionName=a.[description]  from [dbo].[web_local_tax_department_position] a where a.[id]=@positionId
		set @positionNames=@positionNames+@positionName+','
		FETCH NEXT FROM cursorId INTO @positionId
	END
	CLOSE cursorId  --关闭游标
	DEALLOCATE cursorId
	
	if(@positionNames<>'')
		set @positionNames =substring(@positionNames,1,Len(@positionNames)-1)
     
RETURN @positionNames
END







