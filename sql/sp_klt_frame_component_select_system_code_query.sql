set ANSI_NULLS ON
set QUOTED_IDENTIFIER ON
go


ALTER PROCEDURE [dbo].[sp_klt_frame_component_select_system_code_query]
	@selectType VARCHAR(50),
	@queryValue VARCHAR(100),

	@page INT,
	@pageSize INT,
	
	@operatorId VARCHAR(22),
	@operatorAddress VARCHAR(100),
	
	@msg VARCHAR(1000) OUTPUT,--MSG
	@size INT OUTPUT--ROW SIZE
--WITH ENCRYPTION
AS
BEGIN
	SET NOCOUNT ON
	DECLARE
		@rowStart INT,
		@rowEnd INT
	
	SET @rowStart=(@page-1)*@pageSize+1
	SET @rowEnd=@page*@pageSize
	
	SELECT [id],[codeValue],[codeName] FROM (
		SELECT 
			b.id,
			a.[codeValue],

			a.[codeName],
			ROW_NUMBER() OVER (
			ORDER BY a.[seqNo] ASC --此处的ORDER BY一定要根据业务进行修改
			) AS _ROW_NUMBER
		FROM [dbo].[klt_frame_system_code_detail] a
		left outer join dbo.klt_frame_system_code b on a.codeId = b.id
		WHERE b.type = @selectType
			AND (@queryValue is null or @queryValue='' or a.[codeName]=@queryValue or a.[codeName] like '%'+@queryValue+'%')

	) AS ResultTable WHERE (@page=0 OR _ROW_NUMBER BETWEEN @rowStart AND @rowEnd)

	SELECT @size=COUNT(*)
	FROM [dbo].[klt_frame_system_code_detail] a
	left outer join dbo.klt_frame_system_code b on a.codeId = b.id
	WHERE b.type = @selectType
		AND (@queryValue is null or @queryValue='' or a.[codeName]=@queryValue or a.[codeName] like '%'+@queryValue+'%')


	SET @msg=[dbo].klt_frame_fn_multi_language_get_resource('000000','查询成功')
END


