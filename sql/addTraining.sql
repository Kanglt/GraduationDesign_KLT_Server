set ANSI_NULLS ON
set QUOTED_IDENTIFIER ON
go




ALTER PROCEDURE [dbo].[addTraining]
	@userId VARCHAR(22) ,
	@category VARCHAR(50) ,
	@msg VARCHAR(1000) OUTPUT,--MSG
	@size INT OUTPUT--ROW SIZE

as 
begin

SET NOCOUNT ON
	SET XACT_ABORT ON
	
	SELECT 
		@size=COUNT(*)
	FROM [dbo].[user] a,[dbo].[trainingData] b
		WHERE a.[userId]=@userId and b.[category]=@category
	IF(@size <= 0)
		EXEC [dbo].[klt_frame_sp_raise_error] '000000','该记录不存在'

	SELECT 
		@size=COUNT(*)
	FROM [dbo].[userTrainingData]
		WHERE [userId]=@userId and [category]=@category
	IF(@size > 0)
		EXEC [dbo].[klt_frame_sp_raise_error] '000000','该记录已存在'

INSERT INTO [dbo].[userTrainingData] VALUES(@userId,@category)

SET @msg=[dbo].klt_frame_fn_multi_language_get_resource('000000','添加成功')
	SET @size=1
	SELECT @msg AS msg,@size AS recordSize
	RETURN
end
