-- ================================================
-- ˢ������ �����豸�� �ж��û��Ƿ���Ҫ���ѱ���
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
	set @isConsumeRate = 1 -- Ĭ������Ҫ����	
	-- �����ű����ʿ�ʱ  �����û������ѯ�û���������
	IF(ISNULL(@departmentId,'')='')
	BEGIN
		select top 1 @departmentId = departmentId from web_local_tax_department_position where userId=@userId
	END
	--�����û����Ų�ѯ�û������־�
	SELECT @departmentPasentId=id FROM dbo.fjzx_local_tax_fn_get_danwei_by_type_and_department_id(@departmentId,'SUB_BUREAU')
	
	if(ISNULL(@clockId,'')='')
	BEGIN
		if(@consumeOrderType='FOOD')
		BEGIN
			-- ��ѯ��������ʳ�ñ���
			SELECT @restaurantId = restaurantId
			FROM  [dbo].[web_local_tax_reservation_restaurant_food_order] WHERE id = @orderId
			
			-- ��ѯ��������ʳ���Ƿ������������ŵ�ʳ�ã���@row=0ʱ˵���Ƕ�������ض���
			SELECT  @row=COUNT(*) FROM  [dbo].[web_local_tax_reservation_restaurant_department] AS d INNER  join  [dbo].[web_local_tax_reservation_restaurant] AS c  on c.id = d.restaurantId
			WHERE 
			d.[recordVersion]>0 AND
			d.departmentId in 
			(SELECT id FROM [dbo].[fjzx_local_tax_fn_get_all_department_by_dept_id](@departmentPasentId)) and c.id= @restaurantId
			IF(@row<=0)  
			BEGIN
				set @isConsumeRate=0; -- @isConsumeRate=0���û����Ѳ��豶��
			END
		END
		ELSE  -- ��Ʒ���Ѳ��豶��
		BEGIN
			set @isConsumeRate=0; -- @isConsumeRate=0���û����Ѳ��豶��
		END
		
	END
	ELSE
	BEGIN
		select @locusStatusId=locusStatusId,@locusId=locusId from web_local_tax_reservation_commodity_device_allocate where deviceId=@clockId
		if(@locusStatusId='RESTAURANT')
		BEGIN
			set @restaurantId = @locusId 
			-- ��ѯ��������ʳ���Ƿ������������ŵ�ʳ�ã���@row=0ʱ˵���Ƕ�������ض���
			SELECT  @row=COUNT(*) FROM  [dbo].[web_local_tax_reservation_restaurant_department] AS d INNER  join  [dbo].[web_local_tax_reservation_restaurant] AS c  on c.id = d.restaurantId
			WHERE 
			d.[recordVersion]>0 AND
			d.departmentId in 
			(SELECT id FROM [dbo].[fjzx_local_tax_fn_get_all_department_by_dept_id](@departmentPasentId)) and c.id= @restaurantId
			IF(@row<=0)  
			BEGIN
				set @isConsumeRate=0; -- @isConsumeRate=0���û����Ѳ��豶��
			END
			
		END
		ELSE
		BEGIN
			SET @isConsumeRate=0  -- ��Ʒ����Ҫ����
		END 
	END
	
	-- Return the result of the function
	RETURN @isConsumeRate

END
GO

