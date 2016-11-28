IF OBJECT_ID('[dbo].[sp_fjzx_frame_system_code_detail_synchronous_query]') IS NOT NULL
BEGIN 
    DROP PROC [dbo].[sp_fjzx_frame_system_code_detail_synchronous_query] 
END
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[sp_fjzx_frame_system_code_detail_synchronous_query]
	
	@msg VARCHAR(1000) OUTPUT,--MSG
	@size INT OUTPUT--ROW SIZE
--WITH ENCRYPTION
AS
BEGIN
	SET NOCOUNT ON
	DECLARE
		@rowStart INT,
		@rowEnd INT
	
	SELECT * FROM (
		SELECT 
			a.[id],

			a.[codeId],
			a.[codeValue],
			a.[codeName],
			a.[seqNo],

			a.[updateBy],
			[dbo].fjzx_frame_fn_format_date(a.[updateTime]) AS [updateTime],
			a.[updateByAddress],
			[dbo].fjzx_frame_fn_format_date(a.[createTime]) AS [createTime],
			a.[createBy],
			a.[createByAddress],
			a.[recordVersion],
			ROW_NUMBER() OVER (
			ORDER BY a.[seqNo] DESC --此处的ORDER BY一定要根据业务进行修改
			) AS _ROW_NUMBER
		FROM [dbo].[fjzx_frame_system_code_detail] a
		
	) AS ResultTable 


	SELECT @size=COUNT(*)
	FROM [dbo].[fjzx_frame_system_code_detail] a
	
	SET @msg=[dbo].fjzx_frame_fn_multi_language_get_resource('000000','查询成功')
END
GO

/**
declare
	@msg varchar(1000),
	@size int
exec [dbo].[sp_fjzx_frame_system_code_detail_synchronous_query]
	null,--@queryId VARCHAR(22),
	
	null,--@queryCodeId VARCHAR,
	null,--@queryCodeValue VARCHAR,
	null,--@queryCodeName VARCHAR,
	null,--@querySeqNoStart INT,
	null,--@querySeqNoEnd INT,
	null,--@queryUpdateBy VARCHAR,
	null,--@queryUpdateByAddress VARCHAR,
	null,--@queryUpdateTimeStart DATETIME,
	null,--@queryUpdateTimeEnd DATETIME,
	null,--@queryCreateBy VARCHAR,
	null,--@queryCreateByAddress VARCHAR,
	null,--@queryCreateTimeStart DATETIME,
	null,--@queryCreateTimeEnd DATETIME,

	1,--@page INT,--值为0则是全页
	10,--@pageSize INT,
	
	null,--@operatorId VARCHAR(22),
	null,--@operatorAddress VARCHAR(100),
	
	@msg output,--@msg VARCHAR(1000) OUTPUT,--MSG
	@size output--@size INT OUTPUT,--ROW SIZE

select @msg,@size
*/