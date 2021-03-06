USE [ICCO]
GO
/****** Object:  UserDefinedFunction [dbo].[fjzx_local_tax_fn_get_material_in_quantity_by_time_and_warehouse]    Script Date: 07/15/2016 17:03:57 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

/** 查看一段时间内的物品的进货数量  ALTER**/
ALTER FUNCTION [dbo].[fjzx_local_tax_fn_get_material_in_quantity_by_time_and_warehouse]
(
	@materialId VARCHAR(22),  --物品编号
	@warehouseId VARCHAR(22),  --仓库号
	@timeStart DATETIME, --开始时间
	@timeEnd DATETIME	  --结束时间	
)
RETURNS INT  --进货数量
--WITH ENCRYPTION
AS
BEGIN
	DECLARE
		@num int,  --返回的结果
		@row int
	set @num=0	
	select  @num = SUM(a.[quantity]) from [web_local_tax_material_warehouse_in_detail] a 
	inner join [web_local_tax_material_warehouse_in] b on a.inId = b.id
	where a.materialId=@materialId and b.warehouseId = @warehouseId
  	and (@timeStart is null or (@timeStart<=a.[updateTime])) and (ISNULL(@timeEnd,'')='' or a.[updateTime]<@timeEnd)   
  	    
    if(@num is null)
		set @num =0
		
RETURN @num
END







