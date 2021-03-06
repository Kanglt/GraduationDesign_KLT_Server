USE [GraduationDesign_KLT_DB]
GO
/****** Object:  StoredProcedure [dbo].[fjzx_frame_sp_raise_error]    Script Date: 07/11/2016 15:34:54 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[klt_frame_sp_raise_error]
	@errorCode CHAR(6),
	@errorMessage VARCHAR(MAX),
	@param1 VARCHAR(MAX)=NULL,
	@param2 VARCHAR(MAX)=NULL,
	@param3 VARCHAR(MAX)=NULL,
	@param4 VARCHAR(MAX)=NULL,
	@param5 VARCHAR(MAX)=NULL
--WITH ENCRYPTION
AS
BEGIN
	IF(LEN(@errorCode)<>6)
	BEGIN
		IF NOT EXISTS(SELECT * FROM sys.messages WHERE message_id=50001)
		BEGIN
			EXEC sys.sp_addmessage 50001,16,'When execute stored procedure "fjzx_frame_sp_raise_error",the length of the first parameter "@errorCode" must be 6','us_english',false,replace
			EXEC sys.sp_addmessage 50001,16,'使用存储过程 "fjzx_frame_sp_raise_error"时，第一个参数"@errorCode"的长度必须为6','us_english',false,replace
		END
		RAISERROR(50001,16,1)
	END
	DECLARE @message VARCHAR(MAX)
	SELECT @message = @errorCode+@errorMessage
	
	IF(@param1 IS NULL AND @param2 IS NULL AND @param3 IS NULL AND @param4 IS NULL AND @param5 IS NULL)
		RAISERROR(@message,16,1)
	ELSE IF(@param2 IS NULL AND @param3 IS NULL AND @param4 IS NULL AND @param5 IS NULL)
		RAISERROR(@message,16,1,@param1)
	ELSE IF(@param3 IS NULL AND @param4 IS NULL AND @param5 IS NULL)
		RAISERROR(@message,16,1,@param1,@param2)
	ELSE IF(@param4 IS NULL AND @param5 IS NULL)
		RAISERROR(@message,16,1,@param1,@param2,@param3)
	ELSE IF(@param5 IS NULL)
		RAISERROR(@message,16,1,@param1,@param2,@param3,@param4)
	ELSE
		RAISERROR(@message,16,1,@param1,@param2,@param3,@param4,@param5)
END
GO
