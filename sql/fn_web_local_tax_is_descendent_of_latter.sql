IF OBJECT_ID('[dbo].[fn_web_local_tax_is_descendent_of_latter]') IS NOT NULL
BEGIN 
    DROP FUNCTION [dbo].[fn_web_local_tax_is_descendent_of_latter] 
END
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE FUNCTION [dbo].[fn_web_local_tax_is_descendent_of_latter]
(
	@descendentId VARCHAR(22),
	@departmentId VARCHAR(22)
)
RETURNS BIT
--WITH ENCRYPTION
AS
BEGIN
	DECLARE @result BIT
	
	SET @result=0
	
	IF(@descendentId=@departmentId)
		RETURN 1

	--上级部门id
	SELECT @descendentId=parentId FROM dbo.web_local_tax_department WHERE id=@descendentId
	
	WHILE(ISNULL(@descendentId,'')<>'')
	BEGIN
		IF(@descendentId=@departmentId)
		BEGIN
			SET @result=1
			BREAK
		END
		
		SELECT @descendentId=parentId FROM dbo.web_local_tax_department WHERE id=@descendentId
	END

	RETURN @result
END