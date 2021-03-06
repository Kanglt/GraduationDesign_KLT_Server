set ANSI_NULLS ON
set QUOTED_IDENTIFIER ON
go





ALTER PROCEDURE [dbo].[addUserDynamic]
	@userId VARCHAR(22) ,
	@dynamicDate VARCHAR(20) ,
	@dynamicText VARCHAR(1000) ,
	@dynamicImage varchar(200),
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

INSERT INTO [dbo].[userDynamic] (userId,dynamicDate,dynamicText,dynamicImage) VALUES(@userId,@dynamicDate,@dynamicText,@dynamicImage)

SET @msg=[dbo].klt_frame_fn_multi_language_get_resource('000000','添加成功')
	SET @size=1
	SELECT @msg AS msg,@size AS recordSize
	RETURN
end


