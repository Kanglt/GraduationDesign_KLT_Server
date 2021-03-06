USE [ICCO]
IF OBJECT_ID('[dbo].[fjzx_localtax_get_total_price_by_order_repastid]') IS NOT NULL
BEGIN 
    DROP FUNCTION [dbo].[fjzx_localtax_get_total_price_by_order_repastid] 
END
GO
/****** Object:  UserDefinedFunction [dbo].[fjzx_localtax_get_total_price_by_order_repastid]    Script Date: 07/11/2016 16:21:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE FUNCTION [dbo].[fjzx_localtax_get_total_price_by_order_repastid]
(
	@order_repastid VARCHAR(22)
)
RETURNS DECIMAL(18,2)
--WITH ENCRYPTION
AS
BEGIN
	
	DECLARE @result DECIMAL(18,2)
	SELECT @result= sum(price) FROM dbo.web_local_tax_reservation_restaurant_food_order_detail a
	WHERE a.orderRepastId = @order_repastid
	
	RETURN @result
END
GO
