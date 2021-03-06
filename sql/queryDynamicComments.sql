set ANSI_NULLS ON
set QUOTED_IDENTIFIER ON
go





ALTER PROCEDURE [dbo].[queryDynamicComments]
	@dynamicId int,
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

select a.*,b.[dynamicForwardingNum],b.[dynamicCommentsNum],b.[dynamicThumbUpNum]
from [dbo].[userComments] a,[dbo].[userDynamic] b
where a.[dynamicId]=@dynamicId and b.[id]=@dynamicId
order by id asc

SET @msg=[dbo].klt_frame_fn_multi_language_get_resource('000000','读取成功')
	SET @size=1
end



