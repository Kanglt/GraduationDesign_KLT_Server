set ANSI_NULLS ON
set QUOTED_IDENTIFIER ON
go




ALTER PROCEDURE [dbo].[queryDietDetaile_step]
	@dietName VARCHAR(100) ,
	@msg VARCHAR(1000) OUTPUT,--MSG
	@size INT OUTPUT--ROW SIZE

as 
begin

SET NOCOUNT ON
	SET XACT_ABORT ON
	
	SELECT 
		@size=COUNT(*)
	FROM [dbo].[dietDetaile_step]
		WHERE [dietName]=@dietName
	IF(@size <= 0)
		EXEC [dbo].[klt_frame_sp_raise_error] '000000','该记录不存在'

select *
from [dbo].[dietDetaile_step]
where [dietName]=@dietName

SET @msg=[dbo].klt_frame_fn_multi_language_get_resource('000000','读取成功')
	SET @size=1
end
