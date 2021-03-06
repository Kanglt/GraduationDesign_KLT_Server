set ANSI_NULLS ON
set QUOTED_IDENTIFIER ON
go






ALTER PROCEDURE [dbo].[queryUserTraingingTotalRecord]
	@userId VARCHAR(22) ,
	@msg VARCHAR(1000) OUTPUT,--MSG
	@size INT OUTPUT--ROW SIZE

as 
begin

SET NOCOUNT ON
	SET XACT_ABORT ON
	
	SELECT 
		@size=COUNT(*)
	FROM [dbo].[userTrainingRecord]
		WHERE [userId]=@userId
	IF(@size <= 0)
		EXEC [dbo].[klt_frame_sp_raise_error] '000000','该记录不存在'

select sum(trainingCalories) as totalCalories,sum(trainingTime) as totalTime,count(distinct trainingDate) as totalDay,count(userId) as totalNum
from [dbo].[userTrainingRecord]
where [userId]=@userId

SET @msg=[dbo].klt_frame_fn_multi_language_get_resource('000000','读取成功')
	SET @size=1
end


