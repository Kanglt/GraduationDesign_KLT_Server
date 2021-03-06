USE [ICCO]
GO
/****** Object:  UserDefinedFunction [dbo].[fjzx_local_tax_get_card_id]    Script Date: 07/11/2016 16:21:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE FUNCTION [dbo].[fjzx_local_tax_get_card_id]
(
	@joinId VARCHAR(22)
)
RETURNS VARCHAR(100)
--WITH ENCRYPTION
AS
BEGIN
	
 DECLARE @result VARCHAR(100),
	@maxCardId BIGINT,
	@cardIdNum BIGINT
	
	SELECT  @maxCardId=MAX(CAST(cardId AS BIGINT))  from dbo.web_local_tax_card_relation
	if @maxCardId is null 
		BEGIN
			set @result='001000000000'
		END
	ELSE 
		BEGIN
			SET @result='00'+CAST((@maxCardId+1) AS VARCHAR)  -- 读卡后是12位卡号,卡机最多显示10位 这两位后期可以当标志位
		END
	
	select @cardIdNum = COUNT(*) from dbo.web_local_tax_card_relation  where cardId=@result
	if @cardIdNum>0 begin
		SET @result='00'+CAST((@maxCardId+1) AS VARCHAR)
	end
		
RETURN @result
END
GO
