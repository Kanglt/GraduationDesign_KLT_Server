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
	@id VARCHAR(22),
	@operatorId VARCHAR(22),
	@operatorAddress VARCHAR(100),
	
	@msg VARCHAR(1000) OUTPUT,--MSG
	@size INT OUTPUT--ROW SIZE
--WITH ENCRYPTION
AS
BEGIN
	SET NOCOUNT ON
	SET XACT_ABORT ON
	
	SELECT 
		@size=COUNT(*)
	FROM [dbo].[{table_name}]
		WHERE [id]=@id
	IF(@size <= 0)
		EXEC [dbo].[fjzx_frame_sp_raise_error] '000000','该记录不存在'
	
	SELECT 
			a.[id],

{select_field_list}
			a.[updateBy],
			[dbo].fjzx_frame_fn_format_date(a.[updateTime]) AS [updateTime],
			a.[updateByAddress],
			[dbo].fjzx_frame_fn_format_date(a.[createTime]) AS [createTime],
			a.[createBy],
			a.[createByAddress],
			a.[recordVersion]
	FROM [dbo].[{table_name}] a
			WHERE a.[id]=@id AND a.[recordVersion]>0
		
	SET @msg=[dbo].fjzx_frame_fn_multi_language_get_resource('000000','读取成功')
	SET @size=1
END
GO

/**
declare
	@msg varchar(1000),
	@size int

exec [dbo].[{proc_name}]
	'',--@id VARCHAR(22),
	'',--@operatorId VARCHAR(22),
	'',--@operatorAddress VARCHAR(100),
	
	@msg output,--@msg VARCHAR(1000) OUTPUT,--MSG
	@size output--@size INT OUTPUT--ROW SIZE

select @msg,@size
*/