USE [ICCO]
GO
/****** Object:  UserDefinedFunction [dbo].[fjzx_local_tax_fn_get_reservation_commodity_name]    Script Date: 07/15/2016 17:03:57 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

/** 获得订单中商品名称的拼接串**/
ALTER FUNCTION [dbo].[fjzx_local_tax_fn_get_reservation_commodity_name]
(
	@orderId VARCHAR(22) 
)
RETURNS VARCHAR(100)
--WITH ENCRYPTION
AS
BEGIN
	
 DECLARE @result VARCHAR(100)
 SET @result = '' 
	SELECT
		@result = c.name + ','+ @result 
		FROM [dbo].[web_local_tax_reservation_commodity_order_detail] a
		LEFT JOIN [dbo].[web_local_tax_reservation_commodity] c on a.commodityId = c.id
		LEFT JOIN [dbo].[web_local_tax_reservation_commodity_order] d on a.orderId = d.id
		WHERE 
		@orderId = d.id

		SET	@result = SUBSTRING ( @result, 0, len(@result) )
     
RETURN @result
END







