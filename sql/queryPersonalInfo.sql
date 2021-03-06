set ANSI_NULLS ON
set QUOTED_IDENTIFIER ON
go




ALTER PROCEDURE [dbo].[queryPersonalInfo]
	@userId VARCHAR(22) ,
	@focusId VARCHAR(22) ,
	@msg VARCHAR(1000) OUTPUT,--MSG
	@size INT OUTPUT--ROW SIZE

as 
begin

SET NOCOUNT ON
	SET XACT_ABORT ON
	
	SELECT 
		@size=COUNT(*)
	FROM [dbo].[user]
		WHERE [userId]=@focusId
	IF(@size <= 0)
		EXEC [dbo].[klt_frame_sp_raise_error] '000000','该用户不存在'

select b.*,ResultTable.[isFocus]
from [dbo].[user] b,(select count(*) as isFocus from [dbo].[userFocus] where [userId]=@userId and [focusId]=@focusId) as ResultTable
where b.[userId]=@focusId 


SET @msg=[dbo].klt_frame_fn_multi_language_get_resource('000000','读取成功')
	SET @size=1
end
