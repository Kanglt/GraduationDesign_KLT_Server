USE [ICCO]
GO
/****** Object:  UserDefinedFunction [dbo].[fjzx_local_tax_fn_get_reservation_commodity_size]    Script Date: 07/15/2016 17:06:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

/** 获得订单中商品数量**/
ALTER FUNCTION [dbo].[fjzx_local_tax_fn_get_reservation_commodity_size]
(
	@orderId VARCHAR(22) 
)
RETURNS INT
--WITH ENCRYPTION
AS
BEGIN
	
 DECLARE @result INT

	SELECT 
			@result = COUNT(c.id)
		FROM [dbo].[web_local_tax_reservation_commodity_order_detail] a
		LEFT JOIN [dbo].[web_local_tax_reservation_commodity] c on a.commodityId = c.id
		LEFT JOIN [dbo].[web_local_tax_reservation_commodity_order] d on a.orderId = d.id
		WHERE 
     @orderId = d.id
     
RETURN @result
END







