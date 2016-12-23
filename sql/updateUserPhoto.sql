use[GraduationDesign_KLT_DB]
go
IF OBJECT_ID('[dbo].[updateUserPhoto]') IS NOT NULL
BEGIN 
    DROP FUNCTION [dbo].[updateUserPhoto] 
END
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO



CREATE PROCEDURE [dbo].[updateUserPhoto]
	@userId VARCHAR(22) ,
	@userPhoto VARCHAR(200),
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
		EXEC [dbo].[klt_frame_sp_raise_error] '000000','该记录不存在'

update [dbo].[user]
set [userPhoto]=@userPhoto
where [userId]=@userId

SET @msg=[dbo].klt_frame_fn_multi_language_get_resource('000000','读取成功')
	SET @size=1
	SELECT @msg AS msg,@size AS recordSize
	RETURN
end