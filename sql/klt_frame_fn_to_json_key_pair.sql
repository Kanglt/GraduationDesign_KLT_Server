IF OBJECT_ID('[dbo].[fjzx_frame_fn_to_json_key_pair]') IS NOT NULL
BEGIN 
    DROP FUNCTION [dbo].[fjzx_frame_fn_to_json_key_pair] 
END
GO

CREATE FUNCTION [dbo].[fjzx_frame_fn_to_json_key_pair](@key VARCHAR(100),@value VARCHAR(MAX))
RETURNS VARCHAR(MAX)
--WITH ENCRYPTION
AS
BEGIN
	SET @key=REPLACE(@key,'"','\"')
	SET @value=REPLACE(@value,'"','\"')
	RETURN '"'+@key+'":"'+@value+'"'
END