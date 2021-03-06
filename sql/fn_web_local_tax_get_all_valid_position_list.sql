IF OBJECT_ID('[dbo].[fn_web_local_tax_get_all_valid_position_list]') IS NOT NULL
BEGIN 
    DROP FUNCTION [dbo].[fn_web_local_tax_get_all_valid_position_list] 
END
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE FUNCTION [dbo].[fn_web_local_tax_get_all_valid_position_list]
(
	@requiredPositionId VARCHAR(22)
)
RETURNS
@position TABLE(
	id VARCHAR(22) COLLATE Chinese_PRC_BIN
)
--WITH ENCRYPTION
AS
BEGIN
	DECLARE @positionId VARCHAR(22),
			@departmentId VARCHAR(22)
	
	SET @positionId=@requiredPositionId

	INSERT INTO @position
		(id)
	VALUES
		(@positionId)
	
	--所处部门id
	SELECT @departmentId=departmentId FROM dbo.web_local_tax_department_position WHERE id=@positionId
	--上级部门id
	SELECT @departmentId=parentId FROM dbo.web_local_tax_department WHERE id=@departmentId
	
	WHILE(ISNULL(@departmentId,'')<>'')
	BEGIN
		INSERT INTO @position
			(id)
		SELECT id FROM dbo.web_local_tax_department_position WHERE departmentId=@departmentId
		
		SELECT @departmentId=parentId FROM dbo.web_local_tax_department WHERE id=@departmentId
	END
	
	RETURN
END