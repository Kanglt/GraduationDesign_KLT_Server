-- ================================================
-- 刷卡消费 根据设备号 判断用户是否需要消费倍率
-- ================================================
USE ICCO
GO

IF OBJECT_ID('[dbo].fjzx_local_tax_fn_get_is_consume_rate_by_clock_id') IS NOT NULL
BEGIN
	DROP FUNCTION dbo.fjzx_local_tax_fn_get_is_consume_rate_by_clock_id
END

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO


CREATE FUNCTION dbo.fjzx_local_tax_fn_get_is_consume_rate_by_clock_id
(
	@clockId varchar(22),
	@userId varchar(22),
	@orderId varchar(22),
	@consumeOrderType VARCHAR(50)
)
RETURNS bit
--WITH ENCRYPTION
AS
BEGIN
	-- Declare the return variable here
	DECLARE 
		@locusStatusId varchar(50),
		@locusId varchar(22),
		@isConsumeRate bit,
		@departmentId varchar(22),
		@departmentPasentId varchar(22),
		@restaurantId VARCHAR(22),
		@row INT
	set @isConsumeRate = 1 -- 默认是需要倍率	
	-- 当部门编码问空时  根据用户编码查询用户所属部门
	IF(ISNULL(@departmentId,'')='')
	BEGIN
		select top 1 @departmentId = departmentId from web_local_tax_department_position where userId=@userId
	END
	--根据用户部门查询用户所属分局
	SELECT @departmentPasentId=id FROM dbo.fjzx_local_tax_fn_get_danwei_by_type_and_department_id(@departmentId,'SUB_BUREAU')
	
	if(ISNULL(@clockId,'')='')
	BEGIN
		if(@consumeOrderType='FOOD')
		BEGIN
			-- 查询订单所属食堂编码
			SELECT @restaurantId = restaurantId
			FROM  [dbo].[web_local_tax_reservation_restaurant_food_order] WHERE id = @orderId
			
			-- 查询订单所属食堂是否属于所属部门的食堂，当@row=0时说明是订单是异地订餐
			SELECT  @row=COUNT(*) FROM  [dbo].[web_local_tax_reservation_restaurant_department] AS d INNER  join  [dbo].[web_local_tax_reservation_restaurant] AS c  on c.id = d.restaurantId
			WHERE 
			d.[recordVersion]>0 AND
			d.departmentId in 
			(SELECT id FROM [dbo].[fjzx_local_tax_fn_get_all_department_by_dept_id](@departmentPasentId)) and c.id= @restaurantId
			IF(@row<=0)  
			BEGIN
				set @isConsumeRate=0; -- @isConsumeRate=0是用户消费不设倍率
			END
		END
		ELSE  -- 商品消费不设倍率
		BEGIN
			set @isConsumeRate=0; -- @isConsumeRate=0是用户消费不设倍率
		END
		
	END
	ELSE
	BEGIN
		select @locusStatusId=locusStatusId,@locusId=locusId from web_local_tax_reservation_commodity_device_allocate where deviceId=@clockId
		if(@locusStatusId='RESTAURANT')
		BEGIN
			set @restaurantId = @locusId 
			-- 查询订单所属食堂是否属于所属部门的食堂，当@row=0时说明是订单是异地订餐
			SELECT  @row=COUNT(*) FROM  [dbo].[web_local_tax_reservation_restaurant_department] AS d INNER  join  [dbo].[web_local_tax_reservation_restaurant] AS c  on c.id = d.restaurantId
			WHERE 
			d.[recordVersion]>0 AND
			d.departmentId in 
			(SELECT id FROM [dbo].[fjzx_local_tax_fn_get_all_department_by_dept_id](@departmentPasentId)) and c.id= @restaurantId
			IF(@row<=0)  
			BEGIN
				set @isConsumeRate=0; -- @isConsumeRate=0是用户消费不设倍率
			END
			
		END
		ELSE
		BEGIN
			SET @isConsumeRate=0  -- 商品不需要倍率
		END 
	END
	
	-- Return the result of the function
	RETURN @isConsumeRate

END
GO

