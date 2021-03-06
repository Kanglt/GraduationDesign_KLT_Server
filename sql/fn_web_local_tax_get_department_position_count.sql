USE [ICCO]
GO
/****** Object:  UserDefinedFunction [dbo].[fn_web_local_tax_get_department_position_count]    Script Date: 07/11/2016 16:21:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE FUNCTION [dbo].[fn_web_local_tax_get_department_position_count]
(
	@departmentId VARCHAR(22)
)
RETURNS VARCHAR(20)
--WITH ENCRYPTION
AS
BEGIN
	DECLARE @result VARCHAR(20),
			@count INT

	SELECT @count=COUNT(*) FROM dbo.web_local_tax_department_position WHERE recordVersion>0 AND departmentId=@departmentId
	IF(@count>0)
	BEGIN
		SET @result = '(' + CAST(@count AS VARCHAR(10)) + ')'
	END
	ELSE
	BEGIN
		SET @result = ''
	END		

	RETURN @result
END
GO
