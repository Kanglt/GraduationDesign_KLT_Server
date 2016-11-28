USE [ICCO]
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date, ,>
-- Description:	<Description, ,>
-- =============================================
CREATE FUNCTION fjzx_local_tax_card_trading_reverse_status
(
	-- Add the parameters for the function here
	@mealRecordId varchar(22)
)
RETURNS varchar(50)
AS
BEGIN
	-- Declare the return variable here
	DECLARE @result varchar(50),
	@row int

	-- Add the T-SQL statements to compute the return value here
	SELECT @row = COUNT(*) from  dbo.web_local_tax_card_trading_reverse where mealRecordId=@mealRecordId
	if(@row>0)
	begin
		set @result='EXIST'
	end
	else
	begin
		SET @result='NOTEXIST'
	end

	-- Return the result of the function
	RETURN @result

END
GO

