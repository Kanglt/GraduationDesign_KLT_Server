USE [ICCO]
GO
/****** Object:  UserDefinedFunction [dbo].[fn_web_imperius_get_menu_operation_full_path]    Script Date: 07/11/2016 16:21:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE FUNCTION [dbo].[fn_web_imperius_get_menu_operation_full_path]
(
	@menuId VARCHAR(22)
)
RETURNS VARCHAR(1000)
--WITH ENCRYPTION
AS
BEGIN
	DECLARE @result VARCHAR(1000),
			@name VARCHAR(100),
			@parentId VARCHAR(22)
	SET @result = ''
	SELECT @parentId=parentId,@name=name FROM dbo.web_imperius_menu WHERE id=@menuId
	WHILE(@name IS NOT NULL AND @name<>'')
	BEGIN
		SET @result = @name + '-' + @result
		SET @menuId = @parentId
		SET @name = ''
		SELECT @parentId=parentId,@name=name FROM dbo.web_imperius_menu WHERE id=@menuId
	END
	IF(@result IS NOT NULL AND @result<>'')
		SET @result = SUBSTRING(@result,1,LEN(@result)-1)
	RETURN @result
END
GO
