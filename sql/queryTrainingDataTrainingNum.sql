set ANSI_NULLS ON
set QUOTED_IDENTIFIER ON
go





ALTER PROCEDURE [dbo].[queryTrainingDataTrainingNum]
	@category VARCHAR(50) ,
	@userId VARCHAR(50) ,
	@msg VARCHAR(1000) OUTPUT,--MSG
	@size INT OUTPUT--ROW SIZE

as 
begin

SET NOCOUNT ON
	SET XACT_ABORT ON
	
	SELECT 
		@size=COUNT(*)
	FROM [dbo].[trainingData]
		WHERE [category]=@category
	IF(@size <= 0)
		EXEC [dbo].[klt_frame_sp_raise_error] '000000','该记录不存在'

select COUNT(b.[category]) as trainingNum
from [dbo].[userTrainingRecord] b
where b.[category]=@category and b.[userId]=@userId

SET @msg=[dbo].klt_frame_fn_multi_language_get_resource('000000','读取成功')
	SET @size=1
end

