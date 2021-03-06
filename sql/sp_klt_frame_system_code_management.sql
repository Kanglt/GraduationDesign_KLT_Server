USE [ICCO]

IF OBJECT_ID('[dbo].[sp_fjzx_frame_system_code_management]') IS NOT NULL
BEGIN 
    DROP PROC [dbo].[sp_fjzx_frame_system_code_management] 
END
GO
/****** Object:  StoredProcedure [dbo].[sp_fjzx_frame_system_code_management]    Script Date: 07/11/2016 15:34:54 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
/**
* C-新增 U-修改 D-删除
*/
CREATE PROCEDURE [dbo].[sp_fjzx_frame_system_code_management]
	@action VARCHAR(50),--insert、update、delete
	@id VARCHAR(22),--UD
	
	--CU
	@type VARCHAR(50),
	@typeName VARCHAR(100),
	@seqNo INT,

	@operatorId VARCHAR(22),--CUD
	@operatorAddress VARCHAR(100),--CUD
	@recordVersion INT,--UD
	
	@msg VARCHAR(1000) OUTPUT,--MSG
	@size INT OUTPUT--ROW SIZE
--WITH ENCRYPTION
AS
BEGIN
	SET NOCOUNT ON
	SET XACT_ABORT ON
	DECLARE
		@row INT,
		@currentTime DATETIME,
		@currentRecordVersion INT
	SET @currentTime = GETDATE()
	
	IF(@action='insert')
	BEGIN
	
	   SELECT @row=COUNT(*) FROM [dbo].[fjzx_frame_system_code] WHERE [typeName]=@typeName
		IF(@row > 0)
			EXEC [dbo].[fjzx_frame_sp_raise_error] '000000','该系统代码名称已经存在'
			
	    SELECT @row=COUNT(*) FROM [dbo].[fjzx_frame_system_code] WHERE [type]=@type
		IF(@row > 0)
			EXEC [dbo].[fjzx_frame_sp_raise_error] '000000','该系统代码已经存在'
	
	
		EXEC [dbo].fjzx_frame_sp_get_new_id @id out
		SET @currentRecordVersion = 1
		
		INSERT INTO [dbo].[fjzx_frame_system_code](
			[id],

			[type],
			[typeName],
			[seqNo],

			[updateBy],
			[updateTime],
			[updateByAddress],
			[createTime],
			[createBy],
			[createByAddress],
			[recordVersion]
		)VALUES(
			@id,

			@type,
			@typeName,
			@seqNo,

			@operatorId,
			@currentTime,
			@operatorAddress,
			@currentTime,
			@operatorId,
			@operatorAddress,
			@currentRecordVersion
		)
		
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
			a.[recordVersion]
		FROM [dbo].[fjzx_frame_system_code] a
			WHERE a.[id]=@id
		
		SET @msg=[dbo].fjzx_frame_fn_multi_language_get_resource('000000','新增成功')
		SET @size = 1
		RETURN
	END
	
	IF(@action='update')
	BEGIN
	
	   SELECT @row=COUNT(*) FROM [dbo].[fjzx_frame_system_code] WHERE [typeName]=@typeName and [id]<>@id
		IF(@row > 0)
			EXEC [dbo].[fjzx_frame_sp_raise_error] '000000','该系统代码名称已经存在'
			
	    SELECT @row=COUNT(*) FROM [dbo].[fjzx_frame_system_code] WHERE [type]=@type and [id]<>@id
		IF(@row > 0)
			EXEC [dbo].[fjzx_frame_sp_raise_error] '000000','该系统代码已经存在'
	
	
		SELECT @row=COUNT(*) FROM [dbo].[fjzx_frame_system_code] WHERE [id]=@id
		IF(@row <= 0)
			EXEC [dbo].[fjzx_frame_sp_raise_error] '000000','该记录已经不存在了'
		
		SELECT @currentRecordVersion=[recordVersion] FROM [dbo].[fjzx_frame_system_code] WHERE [id]=@id
		IF(@currentRecordVersion <> @recordVersion)
			EXEC [dbo].[fjzx_frame_sp_raise_error] '000000','该记录已有更新，请刷新页面或重新查询后再进行本操作'
		
		UPDATE [dbo].[fjzx_frame_system_code] SET
		
			[type]=@type,
			[typeName]=@typeName,
			[seqNo]=@seqNo,

			[updateBy]=@operatorId,
			[updateTime]=@currentTime,
			[updateByAddress]=@operatorAddress,
			[recordVersion]=@currentRecordVersion+1
		WHERE [id]=@id

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
			a.[recordVersion]
		FROM [dbo].[fjzx_frame_system_code] a
			WHERE a.[id]=@id
		
		SET @msg=[dbo].fjzx_frame_fn_multi_language_get_resource('000000','修改成功')
		SET @size=1
		RETURN
	END
	
	IF(@action='delete')
	BEGIN
		SELECT @row=COUNT(*) FROM [dbo].[fjzx_frame_system_code] WHERE [id]=@id
		IF(@row <= 0)
			EXEC [dbo].[fjzx_frame_sp_raise_error] '000000','该记录已经不存在了'
		
		SELECT @currentRecordVersion=[recordVersion] FROM [dbo].[fjzx_frame_system_code] WHERE [id]=@id
		IF(@currentRecordVersion <> @recordVersion)
			EXEC [dbo].[fjzx_frame_sp_raise_error] '000000','该记录已有更新，请刷新页面或重新查询后再进行本操作'
		
		IF(EXISTS(SELECT * FROM dbo.fjzx_frame_system_code_detail WHERE codeId=@id))
			EXEC [dbo].[fjzx_frame_sp_raise_error] '000000','该记录下有明细记录，不可以删除'
		
		DELETE FROM [dbo].[fjzx_frame_system_code] WHERE [id]=@id
		
		SET @msg=[dbo].fjzx_frame_fn_multi_language_get_resource('000000','删除成功')
		SET @size=1
		SELECT @msg AS msg,@size AS recordSize
		RETURN
	END
	
	EXEC [dbo].[fjzx_frame_sp_raise_error] '000000','无法识别的命令，调用本存储过程时，参数action的值必须是insert、update或者delete'
END
GO
