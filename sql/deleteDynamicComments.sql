set ANSI_NULLS ON
set QUOTED_IDENTIFIER ON
go




ALTER PROCEDURE [dbo].[deleteDynamicComments]
	@commentsId int,
	@msg VARCHAR(1000) OUTPUT,--MSG
	@size INT OUTPUT--ROW SIZE

as 
begin

SET NOCOUNT ON
	SET XACT_ABORT ON
	
	SELECT 
		@size=COUNT(*)
	FROM [dbo].[userComments]
		WHERE [id]=@commentsId
	IF(@size <= 0)
		EXEC [dbo].[klt_frame_sp_raise_error] '000000','该记录不存在'

DELETE FROM [dbo].[userComments]
WHERE [id]=@commentsId

SET @msg=[dbo].klt_frame_fn_multi_language_get_resource('000000','删除成功')
	SET @size=1
SELECT @msg AS msg,@size AS recordSize
	RETURN
end


