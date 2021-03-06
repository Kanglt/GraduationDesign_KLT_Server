USE [GraduationDesign_KLT_DB]
IF OBJECT_ID('[dbo].[sp_klt_frame_system_code_detail_management]') IS NOT NULL
BEGIN 
    DROP PROC [dbo].[sp_klt_frame_system_code_detail_management] 
END
GO
/****** Object:  StoredProcedure [dbo].[sp_fjzx_frame_system_code_detail_management]    Script Date: 07/11/2016 15:34:54 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
/**
* C-新增 U-修改 D-删除
*/
CREATE PROCEDURE [dbo].[sp_klt_frame_system_code_detail_management]
	@action VARCHAR(50),--insert、update、delete
	@id VARCHAR(22),--UD
	
	--CU
	@codeId VARCHAR(22),
	@codeValue VARCHAR(50),
	@codeName VARCHAR(100),
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
	
	    SELECT @row=COUNT(*) FROM [dbo].[klt_frame_system_code_detail] WHERE [codeId]=@codeId and [codeName]=@codeName
		IF(@row > 0)
			EXEC [dbo].[klt_frame_sp_raise_error] '000000','该系统代码明细名称已经存在'
			
		  SELECT @row=COUNT(*) FROM [dbo].[klt_frame_system_code_detail] WHERE [codeId]=@codeId and [codeValue]=@codeValue
		IF(@row > 0)
			EXEC [dbo].[klt_frame_sp_raise_error] '000000','该系统代码明细已经存在'
	
	
		EXEC [dbo].klt_frame_sp_get_new_id @id out
		SET @currentRecordVersion = 1
		
		INSERT INTO [dbo].[klt_frame_system_code_detail](
			[id],

			[codeId],
			[codeValue],
			[codeName],
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

			@codeId,
			@codeValue,
			@codeName,
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

			a.[codeId],
			a.[codeValue],
			a.[codeName],
			a.[seqNo],

			a.[updateBy],
			[dbo].klt_frame_fn_format_date(a.[updateTime]) AS [updateTime],
			a.[updateByAddress],
			[dbo].klt_frame_fn_format_date(a.[createTime]) AS [createTime],
			a.[createBy],
			a.[createByAddress],
			a.[recordVersion]
		FROM [dbo].[fjzx_frame_system_code_detail] a
			WHERE a.[id]=@id
		
		SET @msg=[dbo].klt_frame_fn_multi_language_get_resource('000000','新增成功')
		SET @size = 1
		RETURN
	END
	
	IF(@action='update')
	BEGIN
	
	   SELECT @row=COUNT(*) FROM [dbo].[klt_frame_system_code_detail] WHERE [codeName]=@codeName and codeId = @codeId and [id]!=@id
		IF(@row > 0)
			EXEC [dbo].[klt_frame_sp_raise_error] '000000','该系统代码明细名称已经存在'
			
		  SELECT @row=COUNT(*) FROM [dbo].[klt_frame_system_code_detail] WHERE [codeValue]=@codeValue and codeId = @codeId and [id]!=@id
		IF(@row > 0)
			EXEC [dbo].[klt_frame_sp_raise_error] '000000','该系统代码明细已经存在'
	
	
		SELECT @row=COUNT(*) FROM [dbo].[klt_frame_system_code_detail] WHERE [id]=@id
		IF(@row <= 0)
			EXEC [dbo].[klt_frame_sp_raise_error] '000000','该记录已经不存在了'
		
		SELECT @currentRecordVersion=[recordVersion] FROM [dbo].[klt_frame_system_code_detail] WHERE [id]=@id
		IF(@currentRecordVersion <> @recordVersion)
			EXEC [dbo].[klt_frame_sp_raise_error] '000000','该记录已有更新，请刷新页面或重新查询后再进行本操作'
		
		UPDATE [dbo].[klt_frame_system_code_detail] SET
		
			--[codeId]=@codeId,
			[codeValue]=@codeValue,
			[codeName]=@codeName,
			[seqNo]=@seqNo,

			[updateBy]=@operatorId,
			[updateTime]=@currentTime,
			[updateByAddress]=@operatorAddress,
			[recordVersion]=@currentRecordVersion+1
		WHERE [id]=@id

		SELECT 
			a.[id],

			a.[codeId],
			a.[codeValue],
			a.[codeName],
			a.[seqNo],

			a.[updateBy],
			[dbo].klt_frame_fn_format_date(a.[updateTime]) AS [updateTime],
			a.[updateByAddress],
			[dbo].klt_frame_fn_format_date(a.[createTime]) AS [createTime],
			a.[createBy],
			a.[createByAddress],
			a.[recordVersion]
		FROM [dbo].[klt_frame_system_code_detail] a
			WHERE a.[id]=@id
		
		SET @msg=[dbo].klt_frame_fn_multi_language_get_resource('000000','修改成功')
		SET @size=1
		RETURN
	END
	
	IF(@action='delete')
	BEGIN
		SELECT @row=COUNT(*) FROM [dbo].[klt_frame_system_code_detail] WHERE [id]=@id
		IF(@row <= 0)
			EXEC [dbo].[klt_frame_sp_raise_error] '000000','该记录已经不存在了'
		
		SELECT @currentRecordVersion=[recordVersion] FROM [dbo].[fjzx_frame_system_code_detail] WHERE [id]=@id
		IF(@currentRecordVersion <> @recordVersion)
			EXEC [dbo].[klt_frame_sp_raise_error] '000000','该记录已有更新，请刷新页面或重新查询后再进行本操作'
		
		DELETE FROM [dbo].[klt_frame_system_code_detail] WHERE [id]=@id
		
		SET @msg=[dbo].fjzx_frame_fn_multi_language_get_resource('000000','删除成功')
		SET @size=1
		SELECT @msg AS msg,@size AS recordSize
		RETURN
	END
	
	EXEC [dbo].[klt_frame_sp_raise_error] '000000','无法识别的命令，调用本存储过程时，参数action的值必须是insert、update或者delete'
END
GO
