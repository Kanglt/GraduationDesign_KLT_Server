set ANSI_NULLS ON
set QUOTED_IDENTIFIER ON
go






ALTER PROCEDURE [dbo].[queryUserFansInfo]
	@userId VARCHAR(22) ,
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

select b.*,ResultTable2.[isFocus]
from (	select [userId] from [dbo].[userFocus] where [focusId]=@userId ) AS ResultTable,[dbo].[user] b,
	 (	select count(*) as isFocus from [dbo].[userFocus] c,
	 (	select [userId] from [dbo].[userFocus] where [focusId]=@userId ) AS ResultTable3
		where c.[userId]=@userId and [focusId]=ResultTable3.[userId]) as ResultTable2
where ResultTable.[userId]=b.[userId]

SET @msg=[dbo].klt_frame_fn_multi_language_get_resource('000000','读取成功')
	SET @size=1
end


