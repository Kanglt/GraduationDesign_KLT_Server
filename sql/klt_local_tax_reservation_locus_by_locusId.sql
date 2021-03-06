USE [ICCO]
GO
/****** Object:  UserDefinedFunction [dbo].[fjzx_local_tax_reservation_locus_by_locusId]    Script Date: 07/11/2016 16:21:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE FUNCTION [dbo].[fjzx_local_tax_reservation_locus_by_locusId]
(
	@locusStatusId VARCHAR(22),
	@locusId VARCHAR(22)
)
RETURNS VARCHAR(100)
--WITH ENCRYPTION
AS
BEGIN
	
 DECLARE @result VARCHAR(100)

 IF(@locusStatusId='COMMODITY')
	BEGIN
		SELECT @result=name FROM dbo.web_local_tax_reservation_commodity_store WHERE id=@locusId
		RETURN @result
	END
 IF(@locusStatusId='RESTAURANT')
	BEGIN
		SELECT @result=name FROM dbo.web_local_tax_reservation_restaurant WHERE id=@locusId
		RETURN @result
	END
RETURN @result
END
GO
