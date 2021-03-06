USE [ICCO]
GO
/****** Object:  UserDefinedFunction [dbo].[fjzx_local_tax_fn_get_ishas_positions_by_userid]    Script Date: 07/15/2016 17:03:57 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

/** 查看用户的职位 是否在 @positionIds 多个逗号隔开的字符串里面 如果由 返回 1 没有 返回0  ALTER**/
CREATE FUNCTION [dbo].[fjzx_local_tax_fn_get_ishas_positions_by_userid]
(
	@positionIds VARCHAR(500),
	@userId VARCHAR(22) 
)
RETURNS int
--WITH ENCRYPTION
AS
BEGIN
	DECLARE
		@positionId VARCHAR(22),  --单个职位
		@flag int,
		@num int
	SET @flag=0 --默认为0没有
	
	DECLARE cursorId CURSOR
	FOR
	SELECT [VALUE_STR] FROM [dbo].[fjzx_frame_fn_split](@positionIds,',')
	OPEN cursorId
	FETCH NEXT FROM cursorId INTO @positionId
	WHILE(@@FETCH_STATUS=0)
	BEGIN
	
		select @num=COUNT(*)  from [dbo].[web_local_tax_department_position] a where a.[id]=@positionId and a.[userId] = @userId
		if(@num>0)  --如果由这个职位
			set @flag=1
		FETCH NEXT FROM cursorId INTO @positionId
		
	END
	CLOSE cursorId  --关闭游标
	DEALLOCATE cursorId
	

     
RETURN @flag
END







