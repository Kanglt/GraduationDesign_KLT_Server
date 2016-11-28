IF OBJECT_ID('[dbo].[fn_web_loal_tax_get_operator_position_list]') IS NOT NULL
BEGIN 
    DROP FUNCTION [dbo].[fn_web_loal_tax_get_operator_position_list] 
END
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE FUNCTION [dbo].[fn_web_loal_tax_get_operator_position_list]
(
	@operatorId VARCHAR(22)
)
RETURNS
@position TABLE(
	id VARCHAR(22) COLLATE Chinese_PRC_BIN
)
--WITH ENCRYPTION
AS
BEGIN
	INSERT INTO @position
	        ( id )
	SELECT id FROM dbo.web_local_tax_department_position WHERE userId=@operatorId
	
	RETURN
END