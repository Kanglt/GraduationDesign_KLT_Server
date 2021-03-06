USE [GraduationDesign_KLT_DB]
GO
/****** Object:  StoredProcedure [dbo].[fjzx_frame_sp_get_new_id]    Script Date: 07/11/2016 15:34:54 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[klt_frame_sp_get_new_id]
	@newId CHAR(132) OUTPUT
--WITH ENCRYPTION
AS
BEGIN
	DECLARE @uuid CHAR(36)
		,@uuidTrimmed CHAR(32)
		,@resultBin VARCHAR(132)
		,@index INT
		,@hex CHAR(1)
		,@hexBin CHAR(4)
		,@character CHAR(1)
		,@characterBin CHAR(6)
		,@result VARCHAR(22)

	SET @uuid = NEWID()
	SET @uuidTrimmed = REPLACE(@uuid,'-','')
	SET @resultBin = '0000'
	SET @index = 0
	WHILE(@index<32)
	BEGIN
		SET @hex = SUBSTRING(@uuidTrimmed,@index+1,1)
		SELECT @hexBin=[hexBin] FROM dbo.klt_frame_hex_map WHERE [hex]=@hex
		SET @resultBin = @resultBin + @hexBin
		SET @index = @index + 1
	END

	SET @result = ''
	SET @index = 0
	WHILE(@index<132)
	BEGIN
		SET @characterBin = SUBSTRING(@resultBin,@index+1,6)
		SELECT @character = [CHARACTER] FROM dbo.klt_frame_ascii_map WHERE [characterBin]=@characterBin
		SET @result = @result + @character
		SET @index = @index + 6
	END
	
	SET @newId = @result
END
GO
