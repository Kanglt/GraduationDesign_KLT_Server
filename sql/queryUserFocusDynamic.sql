set ANSI_NULLS ON
set QUOTED_IDENTIFIER ON
go




ALTER PROCEDURE [dbo].[queryUserFocusDynamic]
	@userId VARCHAR(22) ,
	@msg VARCHAR(1000) OUTPUT,--MSG
	@size INT OUTPUT--ROW SIZE

as 
begin

SET NOCOUNT ON
	SET XACT_ABORT ON
	
	SELECT 
		@size=COUNT(*)
	FROM [dbo].[userDynamic]
		WHERE [userId]=@userId
	IF(@size <= 0)
		EXEC [dbo].[klt_frame_sp_raise_error] '000000','该记录不存在'
select a.*,b.[userName],b.[userPhoto]
from (	select [focusId]
		from [dbo].[userFocus]
		where [userId]=@userId
		UNION ALL
		select [userId]
		from [dbo].[user]
		where [userId]=@userId ) AS ResultTable,[dbo].[userDynamic] a,[dbo].[user] b
where ResultTable.[focusId]=a.[userId] and b.[userId]=ResultTable.[focusId]
order by a.[id] desc

SET @msg=[dbo].klt_frame_fn_multi_language_get_resource('000000','读取成功')
	SET @size=1
end
