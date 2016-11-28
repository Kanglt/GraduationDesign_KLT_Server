IF OBJECT_ID('[dbo].[fjzx_local_tax_fn_get_file_id_list_from_goods_file_by_goods_id]') IS NOT NULL
BEGIN 
    DROP FUNCTION [dbo].[fjzx_local_tax_fn_get_file_id_list_from_goods_file_by_goods_id] 
END
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE FUNCTION [dbo].[fjzx_local_tax_fn_get_file_id_list_from_goods_file_by_goods_id](@goodsId VARCHAR(22))

RETURNS VARCHAR(5000)
--WITH ENCRYPTION
AS
BEGIN
	
 DECLARE @fileIdList VARCHAR(100) = ''
	
	--»ñÈ¡Ãû³Æ
	SELECT 
		@fileIdList=@fileIdList + ',' + a.fileId
	FROM [dbo].[web_local_tax_goods_file] a 
	WHERE a.[recordVersion]>0 
	AND (@goodsId is null or @goodsId='' or a.[goodsId]=@goodsId or a.[goodsId] like '%'+@goodsId+'%')	
	SET @fileIdList=STUFF(@fileIdList,1,1,'')	
	
	
	RETURN @fileIdList
END
GO


