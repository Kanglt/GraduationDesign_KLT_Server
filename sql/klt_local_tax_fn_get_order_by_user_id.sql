USE [ICCO]
GO
/****** Object:  UserDefinedFunction [dbo].[fjzx_local_tax_fn_get_order_by_user_id]    Script Date: 08/11/2016 10:38:49 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO


--通过userId获得订单


ALTER FUNCTION [dbo].[fjzx_local_tax_fn_get_order_by_user_id](@userId VARCHAR(22))
RETURNS
	@tmp TABLE(
	orderId VARCHAR(22) COLLATE Chinese_PRC_BIN,
	msg VARCHAR(50)
)
--WITH ENCRYPTION
AS
BEGIN
		DECLARE 
				@departmentId varchar(22),
				@currentStatus varchar(50) = '',
				@userName varchar(50),
				@orderId varchar(22),
				@msg VARCHAR(50)
		
		select @userName = a.name from dbo.web_local_tax_user a where @userId = a.id
		
		SELECT @departmentId = departmentId FROM dbo.[web_local_tax_department_position] where @userId = userId
		SELECT @departmentId=id FROM dbo.fjzx_local_tax_fn_get_danwei_by_type_and_department_id(@departmentId,'SUB_BUREAU')

		SELECT @currentStatus = a.type
		FROM [dbo].[web_local_tax_clocks_param] a
		WHERE a.departmentId = @departmentId 
		and  CONVERT(varchar(100), GETDATE(), 8) BETWEEN a.statTime AND endTime
		

		--是否在消费时间内
		if(ISNULL(@currentStatus,'')='')
		BEGIN		
			--SET @msg = dbo.fjzx_frame_fn_get_system_code_name('ERROR_CODE','100017') --不在刷卡消费时间范围内		
			SET @msg = ''
			insert into @tmp (orderId,msg)  VALUES (@orderId,@msg)
			RETURN
		END
		
		
		--从订餐订单表中获得用户 今天 此时间状态下 的订单
		
		SELECT @orderId = a.id FROM [dbo].[web_local_tax_reservation_restaurant_food_order] a 
		LEFT JOIN [dbo].[web_local_tax_reservation_restaurant_food_order_repast_user] b on a.id = b.orderId
		where  b.repastUserId = @userId
		AND a.diningType = @currentStatus AND a.diningTime = CONVERT(varchar(100), GETDATE(), 23)  
		AND a.status = 'HAVE_ORDERS'

		if(ISNULL(@orderId,'')='')
		BEGIN		
			SET @msg = dbo.fjzx_frame_fn_get_system_code_name('ERROR_CODE','100018')	--未订餐
			insert into @tmp (orderId,msg)  VALUES (@orderId,@msg)
			RETURN
		END

		SET @msg=[dbo].fjzx_frame_fn_multi_language_get_resource('000000',@userName+'已订餐')
			
		insert into @tmp (orderId,msg)  VALUES (@orderId,@msg)

RETURN  
END



