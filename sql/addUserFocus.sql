set ANSI_NULLS ON
set QUOTED_IDENTIFIER ON
go






ALTER PROCEDURE [dbo].[addUserFocus]
	@userId VARCHAR(22) ,
	@focusId VARCHAR(22) ,
	@msg VARCHAR(1000) OUTPUT,--MSG
	@size INT OUTPUT--ROW SIZE

as 
begin

SET NOCOUNT ON
	SET XACT_ABORT ON
	
	SELECT 
		@size=COUNT(*)
	FROM [dbo].[user]
		WHERE [userId]=@userId
	IF(@size <= 0)
		EXEC [dbo].[klt_frame_sp_raise_error] '000000','你已销户'
	SELECT 
		@size=COUNT(*)
	FROM [dbo].[user]
		WHERE [userId]=@focusId
	IF(@size <= 0)
		EXEC [dbo].[klt_frame_sp_raise_error] '000000','该用户不存在'
	SELECT 
		@size=COUNT(*)
	FROM [dbo].[userFocus]
		WHERE [focusId]=@focusId and [userId]=@userId
	IF(@size > 0)
		EXEC [dbo].[klt_frame_sp_raise_error] '000000','你已关注对方'


INSERT INTO [dbo].[userFocus] VALUES(@userId,@focusId)

SET @msg=[dbo].klt_frame_fn_multi_language_get_resource('000000','添加成功')
	SET @size=1
	SELECT @msg AS msg,@size AS recordSize
	RETURN
end


