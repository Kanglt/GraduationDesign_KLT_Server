USE [ICCO]
GO
/****** Object:  UserDefinedFunction [dbo].[fjzx_local_tax_fn_get_danwei_by_clockid_or_restaurantid]    Script Date: 08/04/2016 08:41:50 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

/** 查看具体单位 通过 机号 和 restaurantID  ALTER**/
ALTER FUNCTION [dbo].[fjzx_local_tax_fn_get_danwei_by_clockid_or_restaurantid]
(
	@clockid VARCHAR(22) ,
	@restaurantid VARCHAR(22)
	
)
RETURNS VARCHAR(50)
--WITH ENCRYPTION
AS
BEGIN
	DECLARE
		@departmentId VARCHAR(22),  --返回的结果
		@departmentName  VARCHAR(50)
		
		
	set @departmentId=''
	if(@restaurantid is null or @restaurantid='') --如果食堂号为空
		begin
		
		select @departmentId = b.departmentId
		from web_local_tax_reservation_commodity_device_allocate a 
		LEFT JOIN web_local_tax_reservation_restaurant_department b on a.locusId = b.restaurantId
		where a.deviceId=@clockid
		
		end
	else 
		begin
		
		select @departmentId = a.departmentId
		from web_local_tax_reservation_restaurant_department a 
		where a.restaurantId = @restaurantid
		
		end
	
	SELECT @departmentId = id 
	FROM dbo.[fjzx_local_tax_fn_get_danwei_by_type_and_department_id](@departmentId,'SUB_BUREAU')
	
	
	select @departmentName = name 
	from web_local_tax_department a
	where @departmentId = a.id
     
RETURN @departmentId
END







