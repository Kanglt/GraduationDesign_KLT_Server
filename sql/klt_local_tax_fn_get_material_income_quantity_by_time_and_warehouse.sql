USE [ICCO]
GO
/****** Object:  UserDefinedFunction [dbo].[fjzx_local_tax_fn_get_material_income_quantity_by_time_and_warehouse]    Script Date: 07/15/2016 17:03:57 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

/** 查看一段时间内的物品的期初盘存  ALTER**/
ALTER FUNCTION [dbo].[fjzx_local_tax_fn_get_material_income_quantity_by_time_and_warehouse]
(
	@warehouseId VARCHAR(22) , --仓库编号编号
	@materialId VARCHAR(22)  --物品编号
)
RETURNS INT  --进货数量
--WITH ENCRYPTION
AS
BEGIN
	DECLARE
		@num int,  --返回的结果
		@row int,
		@checkId VARCHAR(22)
	set @num=0	
	select top 1 @checkId = a.[id] from [web_local_tax_material_warehouse_check] a  where a.[warehouseId]=@warehouseId order by createTime desc
    if(@checkId is null)
		set @num =0
	else
		begin
			select @num=a.realNum  from [web_local_tax_material_warehouse_check_detail] a where a.checkId=@checkId and a.materialId=@materialId
			if(@num is null)
				set @num =0
		end
		
RETURN @num
END







