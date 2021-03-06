USE [ICCO]
GO
/****** Object:  UserDefinedFunction [dbo].[fjzx_local_tax_fn_get_all_department_by_dept_id]    Script Date: 07/11/2016 16:21:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE FUNCTION [dbo].[fjzx_local_tax_fn_get_all_department_by_dept_id](@dept_id VARCHAR(22))
RETURNS
	@tmp TABLE(
	id VARCHAR(22) COLLATE Chinese_PRC_BIN,
	parentId VARCHAR(22) COLLATE Chinese_PRC_BIN,
	name varchar(100)
)
--WITH ENCRYPTION
AS
BEGIN
		-- 查找所有子节点
		with tab as
		(
		 select id,parentId,name from [ICCO].[dbo].[web_local_tax_department] where id=@dept_id--父节点
		 union all
		 select b.id,b.parentId,b.name
		 from
		  tab a,--父节点数据集
		  [ICCO].[dbo].[web_local_tax_department] b--子节点数据集 
		 where b.parentId=a.id  --子节点数据集.ID=父节点数据集.parendID
		)
		insert into @tmp select * from  tab;
		
RETURN  
END
GO
