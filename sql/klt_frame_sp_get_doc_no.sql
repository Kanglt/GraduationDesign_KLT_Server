USE [ICCO]
GO
/****** Object:  StoredProcedure [dbo].[fjzx_frame_sp_get_doc_no]    Script Date: 07/11/2016 15:34:54 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[fjzx_frame_sp_get_doc_no]
	@type VARCHAR(50),
	@docNo VARCHAR(100) OUTPUT
--WITH ENCRYPTION
AS
BEGIN
	DECLARE @result VARCHAR(100)
		,@currentTime DATETIME
		,@year VARCHAR(4)
		,@month VARCHAR(2)
		,@day VARCHAR(2)
		,@docNoDate VARCHAR(8)
		,@docNoRule VARCHAR(100)
		,@docNoSequence INT
		,@docNoSequenceStr VARCHAR(20)
		,@docNoLength INT
		,@loop INT
	
	SET @currentTime = GETDATE()
	SET @year = CAST(YEAR(@currentTime) AS VARCHAR(4))
	SET @month = CAST(MONTH(@currentTime) AS VARCHAR(2))
	SET @day = CAST(DAY(@currentTime) AS VARCHAR(2))
	IF(LEN(@month)<2)
		SET @month = '0' + @month 
	IF(LEN(@day)<2)
		SET @day = '0' + @day 
	
	SELECT
		@docNoDate = docNoDate
		,@docNoSequence = docNoSequence
		,@docNoLength = docNoLength
		,@docNoRule = docNoRule 
		FROM dbo.fjzx_frame_system_doc_number_rule
		WHERE [TYPE] = @type
	IF @docNoDate IS NULL OR @docNoDate = '' OR @docNoDate = @year + @month + @day
	BEGIN
		UPDATE dbo.fjzx_frame_system_doc_number_rule SET docNoSequence = @docNoSequence + 1 WHERE [TYPE] = @type 
	END
	ELSE
	BEGIN
		SET @docNoSequence = 1
		UPDATE dbo.fjzx_frame_system_doc_number_rule
			SET
			docNoSequence = @docNoSequence + 1,
			docNoDate = @year + @month + @day
			WHERE [TYPE] = @type 
	END
	SET @docNoSequenceStr = CAST(@docNoSequence AS VARCHAR(20))
	
	SET @loop = @docNoLength - LEN(@docNoSequencestr)
	SET @docNoSequenceStr = REPLICATE('0',@loop) + @docNoSequenceStr 

	SET @docNoRule = REPLACE(@docNoRule,'YYYY',@year)
	SET @docNoRule = REPLACE(@docNoRule,'MM',@month)
	SET @docNoRule = REPLACE(@docNoRule,'DD',@day)
	SET @docNoRule = REPLACE(@docNoRule,'SEQNO',@docNoSequenceStr)
	
	SET @docNo = @docNoRule
END
GO
