IF OBJECT_ID('[dbo].[fjzx_local_tax_fn_get_account_info]') IS NOT NULL
BEGIN 
    DROP FUNCTION [dbo].[fjzx_local_tax_fn_get_account_info] 
END
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO


CREATE FUNCTION [dbo].[fjzx_local_tax_fn_get_account_info](@userId VARCHAR(22))
RETURNS
	@tmp TABLE(
	cashAccount DECIMAL(18,2),
	subsidyAccount DECIMAL(18,2)
)
--WITH ENCRYPTION
AS
BEGIN
		-- 查找所有子节点
		with tab as
		(
		 select
			STUFF(
			(SELECT ','+CAST(lastCardbalance AS VARCHAR(20)) FROM web_local_tax_card_settlement WHERE joinId=@userId AND accountKind='CASH_ACCOUNT' FOR XML PATH(''))
			,1,1,'') AS cashAccount
			,STUFF(
			(SELECT ','+CAST(lastCardbalance AS VARCHAR(20)) FROM web_local_tax_card_settlement WHERE joinId=@userId AND accountKind='SUBSIDY_ACCOUNT' FOR XML PATH(''))
			,1,1,'') AS subsidyAccount
		)
		insert into @tmp select * from  tab;
		
RETURN  
END
GO
