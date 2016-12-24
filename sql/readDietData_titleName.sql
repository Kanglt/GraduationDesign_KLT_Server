use[GraduationDesign_KLT_DB]
go
IF OBJECT_ID('[dbo].[readDietData_titleName]') IS NOT NULL
BEGIN 
    DROP FUNCTION [dbo].[readDietData_titleName] 
END
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO



CREATE PROCEDURE [dbo].[readDietData_titleName]
	@msg VARCHAR(1000) OUTPUT,--MSG
	@size INT OUTPUT--ROW SIZE

as 
begin

SET NOCOUNT ON
	SET XACT_ABORT ON
	
	SELECT 
		@size=COUNT(*)
	FROM [dbo].[dietData]

	IF(@size <= 0)
		EXEC [dbo].[klt_frame_sp_raise_error] '000000','该记录不存在'

select distinct titleName
	from [dbo].[dietData]

SET @msg=[dbo].klt_frame_fn_multi_language_get_resource('000000','读取成功')
	SET @size=1
end