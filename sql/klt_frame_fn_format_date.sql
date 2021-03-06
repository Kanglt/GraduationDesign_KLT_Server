IF OBJECT_ID('[dbo].[fjzx_frame_fn_format_date]') IS NOT NULL
BEGIN 
    DROP FUNCTION [dbo].[fjzx_frame_fn_format_date] 
END
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO


CREATE FUNCTION [dbo].[fjzx_frame_fn_format_date]
(
	@date DATETIME
)
RETURNS VARCHAR(20)
--WITH ENCRYPTION
AS
BEGIN
	RETURN CONVERT(VARCHAR(20), @date, 120)
END
GO
