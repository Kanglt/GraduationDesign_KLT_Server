IF OBJECT_ID('[dbo].[fjzx_local_tax_fn_get_reservation_restaurant_food_order_info]') IS NOT NULL
BEGIN 
    DROP FUNCTION [dbo].[fjzx_local_tax_fn_get_reservation_restaurant_food_order_info] 
END
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE FUNCTION [dbo].[fjzx_local_tax_fn_get_reservation_restaurant_food_order_info](@orderId VARCHAR(22))
RETURNS
	@tmp TABLE(
	name VARCHAR(500),
	quantity INT
)
--WITH ENCRYPTION
AS
BEGIN
	
 DECLARE @name VARCHAR(100) = '',
	@quantity INT=0
	
	--获取名称
	SELECT 
		@name=@name + ',' + b.name
	FROM [dbo].[web_local_tax_reservation_restaurant_food_order_detail] a 
	join dbo.web_local_tax_reservation_restaurant_food_order_repast_user u on a.orderRepastId = u.id
	JOIN dbo.[web_local_tax_reservation_restaurant_food] b ON b.id=a.foodId
	WHERE a.[recordVersion]>0 
	AND (@orderId is null or @orderId='' or u.[orderId]=@orderId or u.[orderId] like '%'+@orderId+'%')	
	SET @name=STUFF(@name,1,1,'')	
	
	--获取数量
	SELECT 
		@quantity=@quantity + a.quantity
	FROM [dbo].[web_local_tax_reservation_restaurant_food_order_detail] a 
	join dbo.web_local_tax_reservation_restaurant_food_order_repast_user u on a.orderRepastId = u.id
	WHERE a.[recordVersion]>0 
	AND (@orderId is null or @orderId='' or u.[orderId]=@orderId or u.[orderId] like '%'+@orderId+'%')	
	
	INSERT INTO @tmp(name,quantity) VALUES (@name,@quantity)
		
RETURN
END
GO


