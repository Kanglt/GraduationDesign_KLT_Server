IF OBJECT_ID('[dbo].[{proc_name}]') IS NOT NULL
BEGIN 
    DROP PROC [dbo].[{proc_name}] 
END
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

/**
* C-新增 U-修改 D-删除
*/
CREATE PROCEDURE [dbo].[{proc_name}]
	@action VARCHAR(50),--insert、update、delete
	@id VARCHAR(22),--UD
	
	--CU
{input_params_list}
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
		EXEC [dbo].fjzx_frame_sp_get_new_id @id out
		SET @currentRecordVersion = 1
		
		INSERT INTO [dbo].[{table_name}](
			[id],

{insert_field_list}
			[updateBy],
			[updateTime],
			[updateByAddress],
			[createTime],
			[createBy],
			[createByAddress],
			[recordVersion]
		)VALUES(
			@id,

{insert_input_field_list}
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

{select_field_list}
			a.[updateBy],
			[dbo].fjzx_frame_fn_format_date(a.[updateTime]) AS [updateTime],
			a.[updateByAddress],
			[dbo].fjzx_frame_fn_format_date(a.[createTime]) AS [createTime],
			a.[createBy],
			a.[createByAddress],
			a.[recordVersion]
		FROM [dbo].[{table_name}] a
			WHERE a.[id]=@id
		
		SET @msg=[dbo].fjzx_frame_fn_multi_language_get_resource('000000','新增成功')
		SET @size = 1
		RETURN
	END
	
	IF(@action='update')
	BEGIN
		SELECT @row=COUNT(*) FROM [dbo].[{table_name}] WHERE [id]=@id
		IF(@row <= 0)
			EXEC [dbo].[fjzx_frame_sp_raise_error] '000000','该记录已经不存在了'
		
		SELECT @currentRecordVersion=[recordVersion] FROM [dbo].[{table_name}] WHERE [id]=@id
		IF(@currentRecordVersion <> @recordVersion)
			EXEC [dbo].[fjzx_frame_sp_raise_error] '000000','该记录已有更新，请刷新页面或重新查询后再进行本操作'
		
		UPDATE [dbo].[{table_name}] SET
		
{update_field_list}
			[updateBy]=@operatorId,
			[updateTime]=@currentTime,
			[updateByAddress]=@operatorAddress,
			[recordVersion]=@currentRecordVersion+1
		WHERE [id]=@id

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
			WHERE a.[id]=@id
		
		SET @msg=[dbo].fjzx_frame_fn_multi_language_get_resource('000000','修改成功')
		SET @size=1
		RETURN
	END
	
	IF(@action='delete')
	BEGIN
		SELECT @row=COUNT(*) FROM [dbo].[{table_name}] WHERE [id]=@id
		IF(@row <= 0)
			EXEC [dbo].[fjzx_frame_sp_raise_error] '000000','该记录已经不存在了'
		
		SELECT @currentRecordVersion=[recordVersion] FROM [dbo].[{table_name}] WHERE [id]=@id
		IF(@currentRecordVersion <> @recordVersion)
			EXEC [dbo].[fjzx_frame_sp_raise_error] '000000','该记录已有更新，请刷新页面或重新查询后再进行本操作'
		
		DELETE FROM [dbo].[{table_name}] WHERE [id]=@id
		
		SET @msg=[dbo].fjzx_frame_fn_multi_language_get_resource('000000','删除成功')
		SET @size=1
		SELECT @msg AS msg,@size AS recordSize
		RETURN
	END
	
	EXEC [dbo].[fjzx_frame_sp_raise_error] '000000','无法识别的命令，调用本存储过程时，参数action的值必须是insert、update或者delete'
END
GO

/** insert
begin tran

declare
	@msg varchar(1000),
	@size int

declare
	@id varchar(22)

exec [dbo].[{proc_name}]
	'insert',--@action VARCHAR(50),--insert
	@id,--@id VARCHAR(22),--NEEDED
	
	--NEEDED
{test_params_list}
	'',--@operatorId VARCHAR(22),--NEEDED
	'',--@operatorAddress VARCHAR(100),--NEEDED
	0,--@recordVersion INT,--NOT NEEDED
	
	@msg OUTPUT,--@msg VARCHAR(1000) OUTPUT,--MSG
	@size OUTPUT--@size INT OUTPUT--ROW SIZE

SELECT @msg AS msg,@size AS SIZE

rollback tran
*/

/** update
begin tran

declare
	@msg varchar(1000),
	@size int

exec [dbo].[{proc_name}]
	'update',--@action VARCHAR(50),--delete
	'',--@id VARCHAR(22),--NEEDED
	
	--NOT NEEDED
{test_params_list}
	'',--@operatorId VARCHAR(22),--NEEDED
	'',--@operatorAddress VARCHAR(100),--NEEDED
	0,--@recordVersion INT,--NEEDED
	
	@msg OUTPUT,--@msg VARCHAR(1000) OUTPUT,--MSG
	@size OUTPUT--@size INT OUTPUT--ROW SIZE

SELECT @msg AS msg,@size AS SIZE

rollback tran
*/

/** delete
begin tran

declare
	@msg varchar(1000),
	@size int

exec [dbo].[{proc_name}]
	'delete',--@action VARCHAR(50),--update
	'',--@id VARCHAR(22),--NEEDED
	
	--NEEDED
{test_params_list}
	'',--@operatorId VARCHAR(22),--NEEDED
	'',--@operatorAddress VARCHAR(100),--NEEDED
	0,--@recordVersion INT,--NEEDED
	
	@msg OUTPUT,--@msg VARCHAR(1000) OUTPUT,--MSG
	@size OUTPUT--@size INT OUTPUT--ROW SIZE

SELECT @msg AS msg,@size AS SIZE

rollback tran
*/