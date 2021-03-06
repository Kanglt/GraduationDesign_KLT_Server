IF OBJECT_ID('[dbo].[fjzx_frame_fn_to_json_array]') IS NOT NULL
BEGIN 
    DROP FUNCTION [dbo].[fjzx_frame_fn_to_json_array] 
END
GO

CREATE FUNCTION [dbo].[fjzx_frame_fn_to_json_array](@valueString VARCHAR(MAX),@splitter VARCHAR(10))
RETURNS VARCHAR(MAX)
--WITH ENCRYPTION
AS
BEGIN
	DECLARE @result VARCHAR(MAX)
	SET @result=''
	SELECT @result='['+(SELECT STUFF((SELECT ',"'+REPLACE(VALUE_STR,'"','\"')+'"' FROM dbo.fjzx_frame_fn_split(@valueString,@splitter) FOR XML PATH('')),1,1,''))+']'
	IF(@result IS NULL)
		SET @result='[]'
	RETURN @result
END