USE [GraduationDesign_KLT_DB]
GO
/****** Object:  StoredProcedure [dbo].[sp_klt_frame_system_code_read]    Script Date: 07/11/2016 15:34:54 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[sp_klt_frame_system_code_read]
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
	FROM [dbo].[system_code]
		WHERE [id]=@id
	IF(@size <= 0)
		EXEC [dbo].[klt_frame_sp_raise_error] '000000','该记录不存在'
	
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
			WHERE a.[id]=@id AND a.[recordVersion]>0
		
	SET @msg=[dbo].fjzx_frame_fn_multi_language_get_resource('000000','读取成功')
	SET @size=1
END
GO
