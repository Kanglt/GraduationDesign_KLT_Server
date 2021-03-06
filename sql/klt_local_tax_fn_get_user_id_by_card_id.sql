USE [ICCO]
GO
/****** Object:  UserDefinedFunction [dbo].[fjzx_local_tax_fn_get_user_id_by_card_id]    Script Date: 07/27/2016 10:08:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		zhangjiannan
-- Create date: 2016-07-26 14:45:00
-- Description:	根据卡ID获取用户ID
-- =============================================

ALTER FUNCTION [dbo].[fjzx_local_tax_fn_get_user_id_by_card_id]
(
	@cardId VARCHAR(22)
)
RETURNS
	@tmp TABLE(
	userId VARCHAR(22),
	errorMsg VARCHAR(1000)
)
--WITH ENCRYPTION
AS 
BEGIN
	
	DECLARE
		@row INT,
		@cardStatus	VARCHAR(50),
		@cardType VARCHAR(50),
		@errorMsg VARCHAR(1000),
		@userId VARCHAR(22),
		@primaryCardId VARCHAR(22)
		
	-- 如果卡号为空 根据用户ID 获取用户卡号
	if(ISNULL(@cardId,'')='')
	BEGIN
		SELECT @cardId = [cardId] FROM [dbo].[web_local_tax_user] WHERE id=@userId
	END
   --判断卡是否存在
		SELECT @row=COUNT(*) FROM [dbo].[web_local_tax_card_relation] WHERE [cardId]=@cardId
		SELECT @cardStatus=[status],@cardType=[isPrimary] FROM [dbo].[web_local_tax_card_relation] WHERE [cardId]=@cardId
		
		IF(@row <= 0)
			BEGIN
				SET @errorMsg = dbo.fjzx_frame_fn_get_system_code_name('ERROR_CODE','100003')	
			END
		--判断卡是否正常
		IF(@cardStatus <> 'NORMAL')
			BEGIN
				SET @errorMsg = dbo.fjzx_frame_fn_get_system_code_name('ERROR_CODE','100004')
			END
		--判断卡的类型并获取卡的使用人ID
		IF(@cardType='PRIMARY ') --主卡
			BEGIN
				SELECT @userId = [id] FROM [dbo].[web_local_tax_user] WHERE [cardId]=@cardId
			END
		ELSE IF(@cardType='SUBSIDIARY')	--附属卡
			BEGIN
				SELECT @primaryCardId=[primaryCardNu] FROM [dbo].[web_local_tax_card_subsidiary_allocate] WHERE [CardNu]=@cardId	
				SELECT @userId = [id] FROM [dbo].[web_local_tax_user] WHERE [cardId]=@cardId	
			END
		ELSE
			BEGIN
				SET @errorMsg = dbo.fjzx_frame_fn_get_system_code_name('ERROR_CODE','100005')
			END

		INSERT INTO @tmp(userId,errorMsg) VALUES (@userId,@errorMsg)
	RETURN
END
