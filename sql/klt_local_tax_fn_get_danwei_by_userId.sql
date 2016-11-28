USE [ICCO]
GO
/****** Object:  UserDefinedFunction [dbo].[fjzx_local_tax_fn_get_danwei_by_type_and_department_id]    Script Date: 07/27/2016 17:36:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO


--通过userId 获得单位信息


CREATE FUNCTION [dbo].[fjzx_local_tax_fn_get_danwei_by_userId](@userId VARCHAR(22))
RETURNS
	@tmp TABLE(
	id VARCHAR(22) COLLATE Chinese_PRC_BIN,
	name VARCHAR(22)
)
--WITH ENCRYPTION
AS
BEGIN
		DECLARE @departmentId VARCHAR(22)
		SELECT @departmentId = a.departmentId FROM dbo.web_local_tax_department_position a 
		WHERE a.userId = @userId
		
		insert into @tmp
		SELECT id,name FROM dbo.[fjzx_local_tax_fn_get_danwei_by_type_and_department_id](@departmentId,'SUB_BUREAU')

RETURN  
END



