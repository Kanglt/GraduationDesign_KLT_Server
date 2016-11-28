IF OBJECT_ID('[dbo].[fjzx_local_tax_fn_get_subsidy_configure_detail_user_list_info]') IS NOT NULL
BEGIN 
    DROP FUNCTION [dbo].[fjzx_local_tax_fn_get_subsidy_configure_detail_user_list_info] 
END
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE FUNCTION [dbo].[fjzx_local_tax_fn_get_subsidy_configure_detail_user_list_info](@subsidyConfigureId VARCHAR(22))
RETURNS
	@tmp TABLE(
		userIdList VARCHAR(max),
		userIdNameList VARCHAR(max)
	)
--WITH ENCRYPTION
AS
BEGIN
	
 DECLARE 
	@userIdList VARCHAR(MAX)='',
	@userIdNameList VARCHAR(MAX)=''
	
	--»ñÈ¡Ãû³Æ
	SELECT 
		@userIdList=@userIdList+','+[userId],
		@userIdNameList=@userIdNameList+','+[dbo].[fjzx_frame_fn_get_system_code_name]('USER_NAME',[userId]) 
	FROM [dbo].[web_local_tax_subsidy_configure_detail] 
	WHERE subsidyConfigureId=@subsidyConfigureId
	
	SET @userIdList=STUFF(@userIdList,1,1,'')			
	SET @userIdNameList=STUFF(@userIdNameList,1,1,'')
	
	INSERT INTO @tmp(userIdList,userIdNameList) VALUES (@userIdList,@userIdNameList)
	
RETURN
END
GO


