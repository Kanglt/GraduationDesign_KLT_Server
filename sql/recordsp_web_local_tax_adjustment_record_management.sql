IF OBJECT_ID('[dbo].[sp_web_local_tax_adjustment_record_management]') IS NOT NULL
BEGIN 
    DROP PROC [dbo].[sp_web_local_tax_adjustment_record_management] 
END
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

/**
* C-新增 U-修改 D-删除
*/
CREATE PROCEDURE [dbo].[sp_web_local_tax_adjustment_record_management]
	@action VARCHAR(50),--insert、update、delete
	@id VARCHAR(22),--UD
	
	--CU
	@adjustmentConfigureId VARCHAR(22),
	@adjustmentConfigureCaption VARCHAR(100),
	@departmentId VARCHAR(22),
	@departmentName VARCHAR(100),
	@frequencyType VARCHAR(100),
	@frequencyTypeName VARCHAR(100),
	@adjustmentType VARCHAR(100),
	@adjustmentTypeName VARCHAR(100),
	@adjustmentAmount DECIMAL(18,2),
	@userId VARCHAR(22),
	@userName VARCHAR(100),
	@userDepartmentId VARCHAR(22),
	@userDepartmentName VARCHAR(100),
	
	@beforeCardBalance DECIMAL(18,2),
	@afterCardBalance DECIMAL(18,2),

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
		
		INSERT INTO [dbo].[web_local_tax_adjustment_record](
			[id],

			[adjustmentConfigureId],
			[adjustmentConfigureCaption],
			[departmentId],
			[departmentName],
			[frequencyType],
			[frequencyTypeName],
			[adjustmentType],
			[adjustmentTypeName],
			[adjustmentAmount],
			[userId],
			[userName],
			[userDepartmentId],
			[userDepartmentName],
			
			[beforeCardBalance],
			[afterCardBalance],

			[updateBy],
			[updateTime],
			[updateByAddress],
			[createTime],
			[createBy],
			[createByAddress],
			[recordVersion]
		)VALUES(
			@id,

			@adjustmentConfigureId,
			@adjustmentConfigureCaption,
			@departmentId,
			@departmentName,
			@frequencyType,
			@frequencyTypeName,
			@adjustmentType,
			@adjustmentTypeName,
			@adjustmentAmount,
			@userId,
			@userName,
			@userDepartmentId,
			@userDepartmentName,
			
			@beforeCardBalance,
			@afterCardBalance,

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

			a.[adjustmentConfigureId],
			a.[adjustmentConfigureCaption],
			a.[departmentId],
			dbo.fjzx_frame_fn_get_system_code_name('DEPARTMENT', a.[departmentId]) AS [departmentIdName],
			a.[departmentName],
			a.[frequencyType],
			dbo.fjzx_frame_fn_get_system_code_name('ADJUSTMENT_FREQUENCY_TYPE', a.[frequencyType]) AS [frequencyTypeName],
			a.[frequencyTypeName],
			a.[adjustmentType],
			dbo.fjzx_frame_fn_get_system_code_name('ADJUSTMENT_TYPE', a.[adjustmentType]) AS [adjustmentTypeName],
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
			a.[recordVersion]
		FROM [dbo].[web_local_tax_adjustment_record] a
			WHERE a.[id]=@id
		
		SET @msg=[dbo].fjzx_frame_fn_multi_language_get_resource('000000','新增成功')
		SET @size = 1
		RETURN
	END
	
	IF(@action='update')
	BEGIN
		SELECT @row=COUNT(*) FROM [dbo].[web_local_tax_adjustment_record] WHERE [id]=@id
		IF(@row <= 0)
			EXEC [dbo].[fjzx_frame_sp_raise_error] '000000','该记录已经不存在了'
		
		SELECT @currentRecordVersion=[recordVersion] FROM [dbo].[web_local_tax_adjustment_record] WHERE [id]=@id
		IF(@currentRecordVersion <> @recordVersion)
			EXEC [dbo].[fjzx_frame_sp_raise_error] '000000','该记录已有更新，请刷新页面或重新查询后再进行本操作'
		
		UPDATE [dbo].[web_local_tax_adjustment_record] SET
		
			[adjustmentConfigureId]=@adjustmentConfigureId,
			[adjustmentConfigureCaption]=@adjustmentConfigureCaption,
			[departmentId]=@departmentId,
			[departmentName]=@departmentName,
			[frequencyType]=@frequencyType,
			[frequencyTypeName]=@frequencyTypeName,
			[adjustmentType]=@adjustmentType,
			[adjustmentTypeName]=@adjustmentTypeName,
			[adjustmentAmount]=@adjustmentAmount,
			[userId]=@userId,
			[userName]=@userName,
			[userDepartmentId]=@userDepartmentId,
			[userDepartmentName]=@userDepartmentName,
			
			[beforeCardBalance]=@beforeCardBalance,
			[afterCardBalance]=@afterCardBalance,

			[updateBy]=@operatorId,
			[updateTime]=@currentTime,
			[updateByAddress]=@operatorAddress,
			[recordVersion]=@currentRecordVersion+1
		WHERE [id]=@id

		SELECT 
			a.[id],

			a.[adjustmentConfigureId],
			a.[adjustmentConfigureCaption],
			a.[departmentId],
			dbo.fjzx_frame_fn_get_system_code_name('DEPARTMENT', a.[departmentId]) AS [departmentIdName],
			a.[departmentName],
			a.[frequencyType],
			dbo.fjzx_frame_fn_get_system_code_name('ADJUSTMENT_FREQUENCY_TYPE', a.[frequencyType]) AS [frequencyTypeName],
			a.[frequencyTypeName],
			a.[adjustmentType],
			dbo.fjzx_frame_fn_get_system_code_name('ADJUSTMENT_TYPE', a.[adjustmentType]) AS [adjustmentTypeName],
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
			a.[recordVersion]
		FROM [dbo].[web_local_tax_adjustment_record] a
			WHERE a.[id]=@id
		
		SET @msg=[dbo].fjzx_frame_fn_multi_language_get_resource('000000','修改成功')
		SET @size=1
		RETURN
	END
	
	IF(@action='delete')
	BEGIN
		SELECT @row=COUNT(*) FROM [dbo].[web_local_tax_adjustment_record] WHERE [id]=@id
		IF(@row <= 0)
			EXEC [dbo].[fjzx_frame_sp_raise_error] '000000','该记录已经不存在了'
		
		SELECT @currentRecordVersion=[recordVersion] FROM [dbo].[web_local_tax_adjustment_record] WHERE [id]=@id
		IF(@currentRecordVersion <> @recordVersion)
			EXEC [dbo].[fjzx_frame_sp_raise_error] '000000','该记录已有更新，请刷新页面或重新查询后再进行本操作'
		
		DELETE FROM [dbo].[web_local_tax_adjustment_record] WHERE [id]=@id
		
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

exec [dbo].[sp_web_local_tax_adjustment_record_management]
	'insert',--@action VARCHAR(50),--insert
	@id,--@id VARCHAR(22),--NEEDED
	
	--NEEDED
	'',--@adjustmentConfigureId VARCHAR(22),
	'',--@adjustmentConfigureCaption VARCHAR(100),
	'',--@departmentId VARCHAR(22),
	'',--@departmentName VARCHAR(100),
	'',--@frequencyType VARCHAR(100),
	'',--@frequencyTypeName VARCHAR(100),
	'',--@adjustmentType VARCHAR(100),
	'',--@adjustmentTypeName VARCHAR(100),
	0,--@adjustmentAmount DECIMAL(18,2),
	'',--@userId VARCHAR(22),
	'',--@userName VARCHAR(100),
	'',--@userDepartmentId VARCHAR(22),
	'',--@userDepartmentName VARCHAR(100),

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

exec [dbo].[sp_web_local_tax_adjustment_record_management]
	'update',--@action VARCHAR(50),--delete
	'',--@id VARCHAR(22),--NEEDED
	
	--NOT NEEDED
	'',--@adjustmentConfigureId VARCHAR(22),
	'',--@adjustmentConfigureCaption VARCHAR(100),
	'',--@departmentId VARCHAR(22),
	'',--@departmentName VARCHAR(100),
	'',--@frequencyType VARCHAR(100),
	'',--@frequencyTypeName VARCHAR(100),
	'',--@adjustmentType VARCHAR(100),
	'',--@adjustmentTypeName VARCHAR(100),
	0,--@adjustmentAmount DECIMAL(18,2),
	'',--@userId VARCHAR(22),
	'',--@userName VARCHAR(100),
	'',--@userDepartmentId VARCHAR(22),
	'',--@userDepartmentName VARCHAR(100),

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

exec [dbo].[sp_web_local_tax_adjustment_record_management]
	'delete',--@action VARCHAR(50),--update
	'',--@id VARCHAR(22),--NEEDED
	
	--NEEDED
	'',--@adjustmentConfigureId VARCHAR(22),
	'',--@adjustmentConfigureCaption VARCHAR(100),
	'',--@departmentId VARCHAR(22),
	'',--@departmentName VARCHAR(100),
	'',--@frequencyType VARCHAR(100),
	'',--@frequencyTypeName VARCHAR(100),
	'',--@adjustmentType VARCHAR(100),
	'',--@adjustmentTypeName VARCHAR(100),
	0,--@adjustmentAmount DECIMAL(18,2),
	'',--@userId VARCHAR(22),
	'',--@userName VARCHAR(100),
	'',--@userDepartmentId VARCHAR(22),
	'',--@userDepartmentName VARCHAR(100),

	'',--@operatorId VARCHAR(22),--NEEDED
	'',--@operatorAddress VARCHAR(100),--NEEDED
	0,--@recordVersion INT,--NEEDED
	
	@msg OUTPUT,--@msg VARCHAR(1000) OUTPUT,--MSG
	@size OUTPUT--@size INT OUTPUT--ROW SIZE

SELECT @msg AS msg,@size AS SIZE

rollback tran
*/