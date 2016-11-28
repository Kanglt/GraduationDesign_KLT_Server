USE [ICCO]
GO
/****** Object:  UserDefinedFunction [dbo].[fjzx_local_tax_fn_get_actual_money_by_record_id]    Script Date: 08/09/2016 14:05:03 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

/** 获得订单中商品数量**/
CREATE FUNCTION [dbo].[fjzx_local_tax_fn_get_actual_money_by_record_id]
(
	@recordId VARCHAR(22),
	@currentMoney DECIMAL(18,2)	 
)
RETURNS DECIMAL(18,2)

AS
BEGIN
	
 DECLARE @result DECIMAL(18,2)	 
		
		set @result = @currentMoney
		
		SELECT 
			@result = @result + a.reverseMoney
		FROM [dbo].[web_local_tax_card_trading_reverse] a
		WHERE a.mealRecordId = @recordId
     
RETURN @result
END







