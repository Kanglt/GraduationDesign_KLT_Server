set ANSI_NULLS ON
set QUOTED_IDENTIFIER ON
go




ALTER PROCEDURE [dbo].[addUserTrainingRecord]
	@userId VARCHAR(22) ,
	@trainingDate VARCHAR(50) ,
	@trainingCalories int ,
	@trainingTime int ,
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


INSERT INTO [dbo].[userTrainingRecord] VALUES(@userId,@trainingDate,@trainingCalories,@trainingTime,@category)

SET @msg=[dbo].klt_frame_fn_multi_language_get_resource('000000','添加成功')
	SET @size=1
	SELECT @msg AS msg,@size AS recordSize
	RETURN
end
