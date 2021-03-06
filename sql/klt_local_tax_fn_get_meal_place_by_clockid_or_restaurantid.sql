USE [ICCO]
GO
/****** Object:  UserDefinedFunction [dbo].[fjzx_local_tax_fn_get_meal_place_by_clockid_or_restaurantid]    Script Date: 07/15/2016 17:03:57 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

/** 查看具体消费地点 通过 机号 和 restaurantID  ALTER**/
ALTER FUNCTION [dbo].[fjzx_local_tax_fn_get_meal_place_by_clockid_or_restaurantid]
(
	@clockid VARCHAR(22) ,
	@restaurantid VARCHAR(22)
	
)
RETURNS VARCHAR(50)
--WITH ENCRYPTION
AS
BEGIN
	DECLARE
		@place VARCHAR(50),  --返回的结果
		@locustype VARCHAR(50),  --返回的结果
		@locusId VARCHAR(50)
	set @place=''
	if(@restaurantid is null or @restaurantid='') --如果食堂号为空
		begin
		select @locustype=a.locusStatusId,@locusId=a.locusId from web_local_tax_reservation_commodity_device_allocate a where a.deviceId=@clockid
		if(@locustype='RESTAURANT')
			select @place=a.name from web_local_tax_reservation_restaurant a where a.[id]=@locusId
		else
			select @place=a.name from web_local_tax_reservation_commodity_store a where a.[id]=@locusId
		end
	else 
		select @place=a.name from web_local_tax_reservation_restaurant a where a.[id]=@restaurantid
	set @place=@place+' 消费'
     
RETURN @place
END







