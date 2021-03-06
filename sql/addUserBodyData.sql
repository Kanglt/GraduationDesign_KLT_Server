set ANSI_NULLS ON
set QUOTED_IDENTIFIER ON
go




ALTER PROCEDURE [dbo].[addUserBodyData]
	@userId VARCHAR(22) ,
	@dataType VARCHAR(50) ,
	@data VARCHAR(10) ,
	@height VARCHAR(10) ,
	@weight VARCHAR(10) ,
	@date VARCHAR(10) ,
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


INSERT INTO [dbo].[userBodyData] VALUES(@userId,@dataType,@data,@height,@weight,@date)

SET @msg=[dbo].klt_frame_fn_multi_language_get_resource('000000','添加成功')
	SET @size=1
	SELECT @msg AS msg,@size AS recordSize
	RETURN
end
