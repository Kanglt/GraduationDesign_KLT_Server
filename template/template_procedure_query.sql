IF OBJECT_ID('[dbo].[{proc_name}]') IS NOT NULL
BEGIN 
    DROP PROC [dbo].[{proc_name}] 
END
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[{proc_name}]
	@queryId VARCHAR(22),
	
{input_params_list}
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
	
	SELECT * FROM (
		SELECT 
			a.[id],

{select_field_list}
			a.[updateBy],
			[dbo].fjzx_frame_fn_format_date(a.[updateTime]) AS [updateTime],
			a.[updateByAddress],
			[dbo].fjzx_frame_fn_format_date(a.[createTime]) AS [createTime],
			a.[createBy],
			a.[createByAddress],
			a.[recordVersion],
			ROW_NUMBER() OVER (
			ORDER BY a.[createTime] DESC --此处的ORDER BY一定要根据业务进行修改
			) AS _ROW_NUMBER
		FROM [dbo].[{table_name}] a
		WHERE (@queryId is null or @queryId='' or a.[id]=@queryId) AND a.[recordVersion]>0
{query_field_list}
	) AS ResultTable WHERE (@page=0 OR _ROW_NUMBER BETWEEN @rowStart AND @rowEnd)


	SELECT @size=COUNT(*)
	FROM [dbo].[{table_name}] a
	WHERE (@queryId is null or @queryId='' or a.[id]=@queryId) AND a.[recordVersion]>0
{query_field_list}

	SET @msg=[dbo].fjzx_frame_fn_multi_language_get_resource('000000','查询成功')
END
GO

/**
declare
	@msg varchar(1000),
	@size int
exec [dbo].[{proc_name}]
	null,--@queryId VARCHAR(22),
	
{test_params_list}
	1,--@page INT,--值为0则是全页
	10,--@pageSize INT,
	
	null,--@operatorId VARCHAR(22),
	null,--@operatorAddress VARCHAR(100),
	
	@msg output,--@msg VARCHAR(1000) OUTPUT,--MSG
	@size output--@size INT OUTPUT,--ROW SIZE

select @msg,@size
*/