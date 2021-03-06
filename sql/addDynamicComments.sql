set ANSI_NULLS ON
set QUOTED_IDENTIFIER ON
go






ALTER PROCEDURE [dbo].[addDynamicComments]
	@dynamicId int,
	@commentsUserId VARCHAR(22),
	@commentsText VARCHAR(200),
	@replyId VARCHAR(22),
	@replyName VARCHAR(50),
	@commentsUserName VARCHAR(50),
	@msg VARCHAR(1000) OUTPUT,--MSG
	@size INT OUTPUT--ROW SIZE

as 
begin

SET NOCOUNT ON
	SET XACT_ABORT ON
	
	SELECT 
		@size=COUNT(*)
	FROM [dbo].[userDynamic]
		WHERE [id]=@dynamicId
	IF(@size <= 0)
		EXEC [dbo].[klt_frame_sp_raise_error] '000000','该记录不存在'

INSERT INTO [dbo].[userComments] ([dynamicId],[commentsUserId],[commentsText],[replyId],[replyName],[commentsUserName]) VALUES(@dynamicId,@commentsUserId,@commentsText,@replyId,@replyName,@commentsUserName)

SET @msg=[dbo].klt_frame_fn_multi_language_get_resource('000000','添加成功')
	SET @size=1
	SELECT @msg AS msg,@size AS recordSize
	RETURN
end




