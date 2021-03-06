USE [ICCO]
GO
/****** Object:  UserDefinedFunction [dbo].[fjzx_local_tax_fn_get_reservation_commodity_order_status_info]    Script Date: 08/04/2016 15:28:49 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

ALTER FUNCTION [dbo].[fjzx_local_tax_fn_get_reservation_commodity_order_status_info](@orderId VARCHAR(22))
RETURNS
	@tmp TABLE(
	id VARCHAR(22) COLLATE Chinese_PRC_BIN,
	statusName varchar(100),
	updateTime DATETIME,
	recordVersion int
)
--WITH ENCRYPTION
AS
BEGIN
     DECLARE
      @id VARCHAR(22),
      @status varchar(100),
	  @updateTime DATETIME,
	  @recordVersion int
  
			SELECT TOP 1
			@id = a.[id],
			@status = a.[status],
			@updateTime = [dbo].fjzx_frame_fn_format_date(a.[updateTime]),
			 @recordVersion = a.[recordVersion]
		FROM [dbo].[web_local_tax_reservation_commodity_order_status]  a
		WHERE  
		a.[recordVersion]>0
		and @orderId = a.[orderId]ORDER BY a.[createTime] DESC
	
	INSERT INTO @tmp  (id,statusName,updateTime,recordVersion)  VALUES (@id,@status,@updateTime,@recordVersion)
		
RETURN
END

