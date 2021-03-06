USE [ICCO]
GO
/****** Object:  StoredProcedure [dbo].[sp_fjzx_frame_system_code_query]    Script Date: 07/11/2016 15:34:54 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[sp_fjzx_frame_system_code_query]
	@queryId VARCHAR(22),
	
	@queryType VARCHAR(50),
	@queryTypeName VARCHAR(100),
	@querySeqNoStart INT,
	@querySeqNoEnd INT,
	@queryUpdateBy VARCHAR(22),
	@queryUpdateByAddress VARCHAR(100),
	@queryUpdateTimeStart DATETIME,
	@queryUpdateTimeEnd DATETIME,
	@queryCreateBy VARCHAR(22),
	@queryCreateByAddress VARCHAR(100),
	@queryCreateTimeStart DATETIME,
	@queryCreateTimeEnd DATETIME,

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
			
	SET @queryUpdateTimeEnd=DATEADD(DAY,1,@queryUpdateTimeEnd)
	SET @queryCreateTimeEnd=DATEADD(DAY,1,@queryCreateTimeEnd)

	SELECT * FROM (
		SELECT 
			a.[id],

			a.[type],
			a.[typeName],
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
		FROM [dbo].[fjzx_frame_system_code] a
		WHERE (@queryId is null or @queryId='' or a.[id]=@queryId) AND a.[recordVersion]>0
		and (@queryType is null or @queryType='' or a.[type]=@queryType or a.[type] like '%'+@queryType+'%')
		and (@queryTypeName is null or @queryTypeName='' or a.[typeName]=@queryTypeName or a.[typeName] like '%'+@queryTypeName+'%')
		and (@querySeqNoStart is null or @querySeqNoStart<=a.[seqNo]) and (@querySeqNoEnd is null or a.[seqNo]<=@querySeqNoEnd)
		and (@queryUpdateBy is null or @queryUpdateBy='' or a.[updateBy]=@queryUpdateBy or a.[updateBy] like '%'+@queryUpdateBy+'%')
		and (@queryUpdateByAddress is null or @queryUpdateByAddress='' or a.[updateByAddress]=@queryUpdateByAddress or a.[updateByAddress] like '%'+@queryUpdateByAddress+'%')
		and (@queryUpdateTimeStart is null or (@queryUpdateTimeStart<=a.[updateTime])) and (ISNULL(@queryUpdateTimeEnd,'')='' or a.[updateTime]<@queryUpdateTimeEnd)
		and (@queryCreateBy is null or @queryCreateBy='' or a.[createBy]=@queryCreateBy or a.[createBy] like '%'+@queryCreateBy+'%')
		and (@queryCreateByAddress is null or @queryCreateByAddress='' or a.[createByAddress]=@queryCreateByAddress or a.[createByAddress] like '%'+@queryCreateByAddress+'%')
		and (@queryCreateTimeStart is null or (@queryCreateTimeStart<=a.[createTime])) and (ISNULL(@queryCreateTimeEnd,'')='' or a.[createTime]<@queryCreateTimeEnd)

	) AS ResultTable WHERE (@page=0 OR _ROW_NUMBER BETWEEN @rowStart AND @rowEnd)


	SELECT @size=COUNT(*)
	FROM [dbo].[fjzx_frame_system_code] a
	WHERE (@queryId is null or @queryId='' or a.[id]=@queryId) AND a.[recordVersion]>0
		and (@queryType is null or @queryType='' or a.[type]=@queryType or a.[type] like '%'+@queryType+'%')
		and (@queryTypeName is null or @queryTypeName='' or a.[typeName]=@queryTypeName or a.[typeName] like '%'+@queryTypeName+'%')
		and (@querySeqNoStart is null or @querySeqNoStart<=a.[seqNo]) and (@querySeqNoEnd is null or a.[seqNo]<=@querySeqNoEnd)
		and (@queryUpdateBy is null or @queryUpdateBy='' or a.[updateBy]=@queryUpdateBy or a.[updateBy] like '%'+@queryUpdateBy+'%')
		and (@queryUpdateByAddress is null or @queryUpdateByAddress='' or a.[updateByAddress]=@queryUpdateByAddress or a.[updateByAddress] like '%'+@queryUpdateByAddress+'%')
		and (@queryUpdateTimeStart is null or (@queryUpdateTimeStart<=a.[updateTime])) and (ISNULL(@queryUpdateTimeEnd,'')='' or a.[updateTime]<@queryUpdateTimeEnd)
		and (@queryCreateBy is null or @queryCreateBy='' or a.[createBy]=@queryCreateBy or a.[createBy] like '%'+@queryCreateBy+'%')
		and (@queryCreateByAddress is null or @queryCreateByAddress='' or a.[createByAddress]=@queryCreateByAddress or a.[createByAddress] like '%'+@queryCreateByAddress+'%')
		and (@queryCreateTimeStart is null or (@queryCreateTimeStart<=a.[createTime])) and (ISNULL(@queryCreateTimeEnd,'')='' or a.[createTime]<@queryCreateTimeEnd)


	SET @msg=[dbo].fjzx_frame_fn_multi_language_get_resource('000000','查询成功')
END
GO
