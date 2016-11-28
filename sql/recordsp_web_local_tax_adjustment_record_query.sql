IF OBJECT_ID('[dbo].[sp_web_local_tax_adjustment_record_query]') IS NOT NULL
BEGIN 
    DROP PROC [dbo].[sp_web_local_tax_adjustment_record_query] 
END
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[sp_web_local_tax_adjustment_record_query]
	@queryId VARCHAR(22),
	
	@queryAdjustmentConfigureId VARCHAR(22),
	@queryAdjustmentConfigureCaption VARCHAR(100),
	@queryDepartmentId VARCHAR(22),
	@queryDepartmentName VARCHAR(100),
	@queryFrequencyType VARCHAR(100),
	@queryFrequencyTypeName VARCHAR(100),
	@queryAdjustmentType VARCHAR(100),
	@queryAdjustmentTypeName VARCHAR(100),
	@queryAdjustmentAmountStart DECIMAL(18,2),
	@queryAdjustmentAmountEnd DECIMAL(18,2),
	@queryUserId VARCHAR(22),
	@queryUserName VARCHAR(100),
	@queryUserDepartmentId VARCHAR(22),
	@queryUserDepartmentName VARCHAR(100),
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

			a.[adjustmentConfigureId],
			a.[adjustmentConfigureCaption],
			a.[departmentId],
			dbo.fjzx_frame_fn_get_system_code_name('DEPARTMENT', a.[departmentId]) AS [departmentIdName],
			a.[departmentName],
			a.[frequencyType],
			a.[frequencyTypeName],
			a.[adjustmentType],
			a.[adjustmentTypeName],
			CAST(a.[adjustmentAmount] AS FLOAT) AS [adjustmentAmount],
			a.[userId],
			a.[userName],
			a.[userDepartmentId],
			dbo.fjzx_frame_fn_get_system_code_name('DEPARTMENT', a.[userDepartmentId]) AS [userDepartmentIdName],
			a.[userDepartmentName],
			dbo.fjzx_frame_fn_get_system_code_name('USER_TREE_FOR_UNIT', a.[userDepartmentName]) AS [userDepartmentNameName],

			a.[beforeCardBalance],
			a.[afterCardBalance],

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
		FROM [dbo].[web_local_tax_adjustment_record] a
		WHERE (@queryId is null or @queryId='' or a.[id]=@queryId) AND a.[recordVersion]>0
		and (@queryAdjustmentConfigureId is null or @queryAdjustmentConfigureId='' or a.[adjustmentConfigureId]=@queryAdjustmentConfigureId or a.[adjustmentConfigureId] like '%'+@queryAdjustmentConfigureId+'%')
		and (@queryAdjustmentConfigureCaption is null or @queryAdjustmentConfigureCaption='' or a.[adjustmentConfigureCaption]=@queryAdjustmentConfigureCaption or a.[adjustmentConfigureCaption] like '%'+@queryAdjustmentConfigureCaption+'%')
		and (@queryDepartmentId is null or @queryDepartmentId='' or a.[departmentId]=@queryDepartmentId or a.[departmentId] like '%'+@queryDepartmentId+'%')
		and (@queryDepartmentName is null or @queryDepartmentName='' or a.[departmentName]=@queryDepartmentName or a.[departmentName] like '%'+@queryDepartmentName+'%')
		and (@queryFrequencyType is null or @queryFrequencyType='' or a.[frequencyType]=@queryFrequencyType or a.[frequencyType] like '%'+@queryFrequencyType+'%')
		and (@queryFrequencyTypeName is null or @queryFrequencyTypeName='' or a.[frequencyTypeName]=@queryFrequencyTypeName or a.[frequencyTypeName] like '%'+@queryFrequencyTypeName+'%')
		and (@queryAdjustmentType is null or @queryAdjustmentType='' or a.[adjustmentType]=@queryAdjustmentType or a.[adjustmentType] like '%'+@queryAdjustmentType+'%')
		and (@queryAdjustmentTypeName is null or @queryAdjustmentTypeName='' or a.[adjustmentTypeName]=@queryAdjustmentTypeName or a.[adjustmentTypeName] like '%'+@queryAdjustmentTypeName+'%')
		and (@queryAdjustmentAmountStart is null or @queryAdjustmentAmountStart<=a.[adjustmentAmount]) and (@queryAdjustmentAmountEnd is null or a.[adjustmentAmount]<=@queryAdjustmentAmountEnd)
		and (@queryUserId is null or @queryUserId='' or a.[userId]=@queryUserId or a.[userId] like '%'+@queryUserId+'%')
		and (@queryUserName is null or @queryUserName='' or a.[userName]=@queryUserName or a.[userName] like '%'+@queryUserName+'%')
		and (@queryUserDepartmentId is null or @queryUserDepartmentId='' or a.[userDepartmentId]=@queryUserDepartmentId or a.[userDepartmentId] like '%'+@queryUserDepartmentId+'%')
		and (@queryUserDepartmentName is null or @queryUserDepartmentName='' or a.[userDepartmentName]=@queryUserDepartmentName or a.[userDepartmentName] like '%'+@queryUserDepartmentName+'%')
		and (@queryUpdateBy is null or @queryUpdateBy='' or a.[updateBy]=@queryUpdateBy or a.[updateBy] like '%'+@queryUpdateBy+'%')
		and (@queryUpdateByAddress is null or @queryUpdateByAddress='' or a.[updateByAddress]=@queryUpdateByAddress or a.[updateByAddress] like '%'+@queryUpdateByAddress+'%')
		and (@queryUpdateTimeStart is null or (@queryUpdateTimeStart<=a.[updateTime])) and (ISNULL(@queryUpdateTimeEnd,'')='' or a.[updateTime]<@queryUpdateTimeEnd)
		and (@queryCreateBy is null or @queryCreateBy='' or a.[createBy]=@queryCreateBy or a.[createBy] like '%'+@queryCreateBy+'%')
		and (@queryCreateByAddress is null or @queryCreateByAddress='' or a.[createByAddress]=@queryCreateByAddress or a.[createByAddress] like '%'+@queryCreateByAddress+'%')
		and (@queryCreateTimeStart is null or (@queryCreateTimeStart<=a.[createTime])) and (ISNULL(@queryCreateTimeEnd,'')='' or a.[createTime]<@queryCreateTimeEnd)

	) AS ResultTable WHERE (@page=0 OR _ROW_NUMBER BETWEEN @rowStart AND @rowEnd)


	SELECT @size=COUNT(*)
	FROM [dbo].[web_local_tax_adjustment_record] a
	WHERE (@queryId is null or @queryId='' or a.[id]=@queryId) AND a.[recordVersion]>0
		and (@queryAdjustmentConfigureId is null or @queryAdjustmentConfigureId='' or a.[adjustmentConfigureId]=@queryAdjustmentConfigureId or a.[adjustmentConfigureId] like '%'+@queryAdjustmentConfigureId+'%')
		and (@queryAdjustmentConfigureCaption is null or @queryAdjustmentConfigureCaption='' or a.[adjustmentConfigureCaption]=@queryAdjustmentConfigureCaption or a.[adjustmentConfigureCaption] like '%'+@queryAdjustmentConfigureCaption+'%')
		and (@queryDepartmentId is null or @queryDepartmentId='' or a.[departmentId]=@queryDepartmentId or a.[departmentId] like '%'+@queryDepartmentId+'%')
		and (@queryDepartmentName is null or @queryDepartmentName='' or a.[departmentName]=@queryDepartmentName or a.[departmentName] like '%'+@queryDepartmentName+'%')
		and (@queryFrequencyType is null or @queryFrequencyType='' or a.[frequencyType]=@queryFrequencyType or a.[frequencyType] like '%'+@queryFrequencyType+'%')
		and (@queryFrequencyTypeName is null or @queryFrequencyTypeName='' or a.[frequencyTypeName]=@queryFrequencyTypeName or a.[frequencyTypeName] like '%'+@queryFrequencyTypeName+'%')
		and (@queryAdjustmentType is null or @queryAdjustmentType='' or a.[adjustmentType]=@queryAdjustmentType or a.[adjustmentType] like '%'+@queryAdjustmentType+'%')
		and (@queryAdjustmentTypeName is null or @queryAdjustmentTypeName='' or a.[adjustmentTypeName]=@queryAdjustmentTypeName or a.[adjustmentTypeName] like '%'+@queryAdjustmentTypeName+'%')
		and (@queryAdjustmentAmountStart is null or @queryAdjustmentAmountStart<=a.[adjustmentAmount]) and (@queryAdjustmentAmountEnd is null or a.[adjustmentAmount]<=@queryAdjustmentAmountEnd)
		and (@queryUserId is null or @queryUserId='' or a.[userId]=@queryUserId or a.[userId] like '%'+@queryUserId+'%')
		and (@queryUserName is null or @queryUserName='' or a.[userName]=@queryUserName or a.[userName] like '%'+@queryUserName+'%')
		and (@queryUserDepartmentId is null or @queryUserDepartmentId='' or a.[userDepartmentId]=@queryUserDepartmentId or a.[userDepartmentId] like '%'+@queryUserDepartmentId+'%')
		and (@queryUserDepartmentName is null or @queryUserDepartmentName='' or a.[userDepartmentName]=@queryUserDepartmentName or a.[userDepartmentName] like '%'+@queryUserDepartmentName+'%')
		and (@queryUpdateBy is null or @queryUpdateBy='' or a.[updateBy]=@queryUpdateBy or a.[updateBy] like '%'+@queryUpdateBy+'%')
		and (@queryUpdateByAddress is null or @queryUpdateByAddress='' or a.[updateByAddress]=@queryUpdateByAddress or a.[updateByAddress] like '%'+@queryUpdateByAddress+'%')
		and (@queryUpdateTimeStart is null or (@queryUpdateTimeStart<=a.[updateTime])) and (ISNULL(@queryUpdateTimeEnd,'')='' or a.[updateTime]<@queryUpdateTimeEnd)
		and (@queryCreateBy is null or @queryCreateBy='' or a.[createBy]=@queryCreateBy or a.[createBy] like '%'+@queryCreateBy+'%')
		and (@queryCreateByAddress is null or @queryCreateByAddress='' or a.[createByAddress]=@queryCreateByAddress or a.[createByAddress] like '%'+@queryCreateByAddress+'%')
		and (@queryCreateTimeStart is null or (@queryCreateTimeStart<=a.[createTime])) and (ISNULL(@queryCreateTimeEnd,'')='' or a.[createTime]<@queryCreateTimeEnd)


	SET @msg=[dbo].fjzx_frame_fn_multi_language_get_resource('000000','查询成功')
END
GO

/**
declare
	@msg varchar(1000),
	@size int
exec [dbo].[sp_web_local_tax_adjustment_record_query]
	null,--@queryId VARCHAR(22),
	
	null,--@queryAdjustmentConfigureId VARCHAR,
	null,--@queryAdjustmentConfigureCaption VARCHAR,
	null,--@queryDepartmentId VARCHAR,
	null,--@queryDepartmentName VARCHAR,
	null,--@queryFrequencyType VARCHAR,
	null,--@queryFrequencyTypeName VARCHAR,
	null,--@queryAdjustmentType VARCHAR,
	null,--@queryAdjustmentTypeName VARCHAR,
	null,--@queryAdjustmentAmountStart DECIMAL,
	null,--@queryAdjustmentAmountEnd DECIMAL,
	null,--@queryUserId VARCHAR,
	null,--@queryUserName VARCHAR,
	null,--@queryUserDepartmentId VARCHAR,
	null,--@queryUserDepartmentName VARCHAR,
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