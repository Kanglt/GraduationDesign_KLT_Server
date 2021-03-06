IF OBJECT_ID('[dbo].[fjzx_frame_fn_split]') IS NOT NULL
BEGIN 
    DROP FUNCTION [dbo].[fjzx_frame_fn_split] 
END
GO

CREATE FUNCTION [dbo].[fjzx_frame_fn_split](@Long_str VARCHAR(MAX),@split_str VARCHAR(100))
RETURNS
	@tmp TABLE(
	ID INT IDENTITY PRIMARY KEY,
	VALUE_STR VARCHAR(MAX)
)
--WITH ENCRYPTION
AS
BEGIN
	DECLARE @short_str VARCHAR(MAX),
			@split_str_length INT,
			@split_str_Position_Begin INT
	
	IF(@Long_str IS NULL OR @Long_str='')
		RETURN

	SET @split_str_length = LEN(@split_str)
	--SET @Long_str=REPLACE(REPLACE(@Long_str,CHAR(10),''),CHAR(13),'')
	SET @Long_str=REPLACE(@Long_str,CHAR(13),'')
	
	IF CHARINDEX(@split_str,@Long_str)=1
		SET @Long_str=STUFF(@Long_str,1,@split_str_length,'')
	
	IF CHARINDEX(@split_str,@Long_str)=0
		INSERT INTO @tmp SELECT @Long_str
	ELSE
	BEGIN
		WHILE 1>0
		BEGIN
			SET @split_str_Position_Begin = CHARINDEX(@split_str,@Long_str)
			SET @short_str=LEFT(@Long_str,@split_str_Position_Begin-1)
			IF @short_str<>'' INSERT INTO @tmp SELECT @short_str
			SET @Long_str=STUFF(@Long_str,1,@split_str_Position_Begin+@split_str_length-1,'')
			SET @split_str_Position_Begin = CHARINDEX(@split_str,@Long_str)
			IF @split_str_Position_Begin=0
			BEGIN
				IF LTRIM(@Long_str)<>''
					INSERT INTO @tmp SELECT @Long_str
				BREAK
			END
		END       
	END
	RETURN 
END