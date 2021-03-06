USE [ICCO]
GO
/****** Object:  UserDefinedFunction [dbo].[fjzx_local_tax_reservation_device_status]    Script Date: 07/11/2016 16:21:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE FUNCTION [dbo].[fjzx_local_tax_reservation_device_status]
(
	@Clock_id VARCHAR(50)
)
RETURNS VARCHAR(100)
--WITH ENCRYPTION
AS
BEGIN
	
 DECLARE @result VARCHAR(100),
  @count VARCHAR(100)

	SELECT @count = COUNT(*) FROM [dbo].[web_local_tax_reservation_commodity_device_allocate] a
	WHERE a.[deviceId] = @Clock_id
	  BEGIN
		 if(@count>0) 
		    BEGIN
		      set @result = 'HAVE_ALLOCATE'
		     END 
		  else
		     BEGIN
		      set @result = 'NOT_ALLOCATE'
		     END
		END 
RETURN @result
END
GO
