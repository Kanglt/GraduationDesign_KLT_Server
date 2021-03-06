IF OBJECT_ID('[dbo].[fjzx_local_tax_fn_get_bureau_id_by_department_id]') IS NOT NULL
BEGIN 
    DROP FUNCTION [dbo].[fjzx_local_tax_fn_get_bureau_id_by_department_id] 
END
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		guoqiang
-- Create date: 2016-07-15 21:45:00
-- Description:	根据部门ID获取单位部门ID
-- =============================================

CREATE FUNCTION [dbo].[fjzx_local_tax_fn_get_bureau_id_by_department_id]
(
	@departmentId VARCHAR(22)
)
RETURNS VARCHAR(22)
--WITH ENCRYPTION
AS 
BEGIN
	
	DECLARE
		@departmentIdBureau VARCHAR(22),
		@errorMsg VARCHAR(50)
	 
	IF('SUB_BUREAU'=(SELECT a.departmentType FROM [dbo].[web_local_tax_department] a WHERE a.id=@departmentId))
	BEGIN
		SET @departmentIdBureau=@departmentId
	END
	ELSE
	BEGIN
		SELECT @departmentId=parentId FROM [dbo].[web_local_tax_department] WHERE [id]=@departmentId
		WHILE('SUB_BUREAU'<>(SELECT departmentType FROM [dbo].[web_local_tax_department] WHERE [id]=@departmentId))
			SELECT @departmentId=parentId FROM [dbo].[web_local_tax_department] WHERE [id]=@departmentId
			
		IF('SUB_BUREAU'<>(SELECT departmentType FROM [dbo].[web_local_tax_department] WHERE [id]=@departmentId))
		BEGIN
			SET @errorMsg = [dbo].[fjzx_frame_fn_get_system_code_name]('ERROR_CODE','100008')
			EXEC [dbo].[fjzx_frame_sp_raise_error] '100008',@errorMsg		--该用户不属于任何分局 
		END
		SET @departmentIdBureau=@departmentId
	END
	RETURN @departmentIdBureau
END
