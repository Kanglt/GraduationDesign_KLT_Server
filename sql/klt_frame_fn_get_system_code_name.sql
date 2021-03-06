IF OBJECT_ID('[dbo].[fjzx_frame_fn_get_system_code_name]') IS NOT NULL
BEGIN 
    DROP FUNCTION [dbo].[fjzx_frame_fn_get_system_code_name] 
END
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE FUNCTION [dbo].[fjzx_frame_fn_get_system_code_name]
(
	@type VARCHAR(50),
	@codeValue VARCHAR(50)
)
RETURNS VARCHAR(100)
--WITH ENCRYPTION
AS
BEGIN
	DECLARE @result VARCHAR(100)
	
	IF(@TYPE='DEPARTMENT')
	BEGIN
		SELECT @result=name FROM dbo.web_local_tax_department WHERE id=@codeValue
		RETURN @result
	END

 IF(@TYPE='COMODITY_TYPE')
	BEGIN
		SELECT @result=name FROM dbo.web_local_tax_reservation_commodity_type WHERE id=@codeValue
		RETURN @result
	END
 IF(@TYPE='COMMODITY_NAME')
	BEGIN
		SELECT @result=name FROM dbo.web_local_tax_reservation_commodity WHERE id=@codeValue
		RETURN @result
	END	
 IF(@TYPE='STORE_NAME')
	BEGIN
		SELECT @result=name FROM dbo.web_local_tax_reservation_commodity_store WHERE id=@codeValue
		RETURN @result
	END	
 IF(@TYPE='USER_NAME')
	BEGIN
		SELECT @result=name FROM dbo.web_local_tax_user WHERE id=@codeValue
		RETURN @result
	END	
 IF(@TYPE='MATERIAL_WAREHOUSE_NAME')
	BEGIN
		SELECT @result=name FROM dbo.web_local_tax_material_warehouse WHERE id=@codeValue
		RETURN @result
	END	
 IF(@TYPE='RESTAURANT_NAME')
	BEGIN
		SELECT @result=name FROM dbo.web_local_tax_reservation_restaurant WHERE id=@codeValue
		RETURN @result
	END	
 IF(@TYPE='MATERIAL_NAME')
	BEGIN
		SELECT @result=name FROM dbo.web_local_tax_material WHERE id=@codeValue
		RETURN @result
	END	
 IF(@TYPE='FOOD_NAME')
	BEGIN
		SELECT @result=name FROM  dbo.web_local_tax_reservation_restaurant_food WHERE id=@codeValue
		RETURN @result
	END
 IF(@TYPE='MATERIAL_TYPE_NAME')
	BEGIN
		SELECT @result=name FROM dbo.web_local_tax_material_type WHERE id=@codeValue
		RETURN @result
	END	
		
	 IF(@TYPE='FOOD_TYPE')
	BEGIN
		SELECT @result=name FROM dbo.web_local_tax_reservation_restaurant_food_type WHERE id=@codeValue
		RETURN @result
	END	
	 IF(@TYPE='DEVICE_NAME')
	BEGIN
		SELECT @result=Clock_name FROM dbo.Clocks WHERE Clock_id=@codeValue
		RETURN @result
	END	
	 IF(@TYPE='UNIT_NAME')
	BEGIN
		SELECT @result=name FROM dbo.web_local_tax_department WHERE id=@codeValue
		RETURN @result
	END	
	 IF(@TYPE='USER_CASH_MONEY')
	BEGIN
		SELECT @result=lastCardbalance FROM dbo.web_local_tax_card_settlement WHERE joinId=@codeValue and  accountKind='CASH_ACCOUNT'
		RETURN @result
	END	
	 IF(@TYPE='USER_SUBSIDY_MONEY')
	BEGIN
		SELECT @result=lastCardbalance FROM dbo.web_local_tax_card_settlement WHERE joinId=@codeValue and  accountKind='SUBSIDY_ACCOUNT'
		RETURN @result
	END	
	IF(@TYPE='EQUIPMENT_TYPE')
	BEGIN
		SELECT @result=ISNULL(name,'') FROM dbo.web_imperius_equipment_type WHERE id=@codeValue
		IF(@result IS NULL)
			SET @result=''
		RETURN @result
	END
	IF(@TYPE='EQUIPMENT')
	BEGIN
		SELECT @result=ISNULL(name,'') FROM dbo.web_imperius_equipment WHERE id=@codeValue
		IF(@result IS NULL)
			SET @result=''
		RETURN @result
	END
	
	IF(@TYPE='EQUIPMENT_IDENTITY')
	BEGIN
		SELECT @result=ISNULL(identityCode,'') FROM dbo.web_imperius_equipment_identity WHERE id=@codeValue
		IF(@result IS NULL)
			SET @result=''
		RETURN @result
	END
	
	IF(@TYPE='WAREHOUSE')
	BEGIN
		SELECT @result=ISNULL(name,'') FROM dbo.web_imperius_warehouse WHERE id=@codeValue
		IF(@result IS NULL)
			SET @result=''
		RETURN @result
	END
	IF(@TYPE='WAREHOUSE_STORAGE_LOCATION')
	BEGIN
		SELECT @result=ISNULL(name,'') FROM dbo.web_imperius_warehouse_storage_location WHERE id=@codeValue
		IF(@result IS NULL)
			SET @result=''
		RETURN @result
	END
	IF(@TYPE='ADJUSTMENT_CONFIGURE_NAME')
	BEGIN
		SELECT @result=ISNULL(caption,'') FROM [dbo].[web_local_tax_adjustment_configure] WHERE id=@codeValue
		IF(@result IS NULL)
			SET @result=''
		RETURN @result
	END
	IF(@TYPE='SUBSIDY_CONFIGURE_CAPTION')
	BEGIN
		SELECT @result=ISNULL(caption,'') FROM [dbo].[web_local_tax_subsidy_configure] WHERE id=@codeValue
		IF(@result IS NULL)
			SET @result=''
		RETURN @result
	END

	SELECT @result=ISNULL(a.codeName,'Error(不存在)') FROM dbo.fjzx_frame_system_code_detail a
		LEFT OUTER JOIN dbo.fjzx_frame_system_code b ON a.codeId = b.id
			WHERE  b.[type]=@type AND a.codeValue = @codeValue

	RETURN @result
END
GO
