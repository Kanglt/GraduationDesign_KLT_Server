USE [ICCO]
GO
/****** Object:  UserDefinedFunction [dbo].[fjzx_local_tax_fn_get_danwei_by_type_and_department_id]    Script Date: 07/11/2016 16:21:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE FUNCTION [dbo].[fjzx_local_tax_fn_get_danwei_by_type_and_department_id](@dept_id VARCHAR(22),@departmentType VARCHAR(100))
RETURNS
	@tmp TABLE(
	id VARCHAR(22) COLLATE Chinese_PRC_BIN,
	parentId VARCHAR(22) COLLATE Chinese_PRC_BIN,
	name varchar(100),
	departmentType varchar(50),
	recordVersion int
)
--WITH ENCRYPTION
AS
BEGIN
		-- 查找所有父节点 
		 with tab as
		(
		 select id,parentId,name,departmentType,recordVersion from [ICCO].[dbo].[web_local_tax_department] where id=@dept_id --子节点
		 union all
		 select b.id,b.ParentId,b.name,b.departmentType,b.recordVersion
		 from
		  tab a,--子节点数据集
		  [ICCO].[dbo].[web_local_tax_department] b  --父节点数据集
		 where a.parentId=b.id  --子节点数据集.parendID=父节点数据集.ID
		)
		insert into @tmp select * from  tab where departmentType=@departmentType and recordVersion > 0;
RETURN  
END
GO
