IF OBJECT_ID('[dbo].[fn_web_local_tax_is_operator_can_access_this_record]') IS NOT NULL
BEGIN 
    DROP FUNCTION [dbo].[fn_web_local_tax_is_operator_can_access_this_record] 
END
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE FUNCTION [dbo].[fn_web_local_tax_is_operator_can_access_this_record]
(
	@operatorId VARCHAR(22),
	@requiredPositionId VARCHAR(22)
)
RETURNS BIT
--WITH ENCRYPTION
AS
BEGIN
	IF(EXISTS(
		SELECT id FROM [dbo].fn_web_local_tax_get_operator_position_list(@operatorId)
		INTERSECT
		SELECT id FROM [dbo].fn_web_local_tax_get_all_valid_position_list(@requiredPositionId)
	))
		RETURN 1

	RETURN 0
END