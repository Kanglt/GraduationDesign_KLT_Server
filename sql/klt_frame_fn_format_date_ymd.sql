IF OBJECT_ID('[dbo].[fjzx_frame_fn_format_date_ymd]') IS NOT NULL
BEGIN 
    DROP FUNCTION [dbo].[fjzx_frame_fn_format_date_ymd] 
END
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO


CREATE FUNCTION [dbo].[fjzx_frame_fn_format_date_ymd]
(
	@date DATETIME
)
RETURNS VARCHAR(10)
--WITH ENCRYPTION
AS
BEGIN
	RETURN CONVERT(VARCHAR(10), @date, 120)
END
GO
