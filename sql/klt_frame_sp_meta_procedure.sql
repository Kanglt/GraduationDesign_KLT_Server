USE [ICCO]
GO
/****** Object:  StoredProcedure [dbo].[fjzx_frame_sp_meta_procedure]    Script Date: 07/11/2016 15:34:54 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[fjzx_frame_sp_meta_procedure]
	@procedureName VARCHAR(100),
	
	@msg VARCHAR(1000) OUTPUT,--MSG
	@size INT OUTPUT--ROW SIZE
--WITH ENCRYPTION
AS
BEGIN
	SET NOCOUNT ON
	SET XACT_ABORT ON
	
SELECT
	isnull(param.name,'')as ParamName,isnull(usrt.name,'') AS [DataType],
	ISNULL(baset.name, '') AS [SystemType], CAST(CASE when baset.name is null then 0  WHEN baset.name IN ('nchar', 'nvarchar') AND param.max_length <> -1 THEN param.max_length/2 ELSE param.max_length END AS int) AS [Length],
	'' as ParamReamrk,isnull(parameter_id,0) as SortId
FROM sys.objects AS sp  INNER JOIN sys.schemas b ON sp.schema_id = b.schema_id
	LEFT outer JOIN sys.all_parameters AS param ON param.object_id=sp.object_Id
	LEFT OUTER JOIN sys.types AS usrt ON usrt.user_type_id = param.user_type_id
	LEFT OUTER JOIN sys.types AS baset ON (baset.user_type_id = param.system_type_id and baset.user_type_id = baset.system_type_id) or ((baset.system_type_id = param.system_type_id) and (baset.user_type_id = param.user_type_id) and (baset.is_user_defined = 0) and (baset.is_assembly_type = 1)) 
	LEFT OUTER JOIN sys.extended_properties E ON sp.object_id = E.major_id
WHERE sp.TYPE in ('FN', 'IF', 'TF','P')  AND ISNULL(sp.is_ms_shipped, 0) = 0 AND ISNULL(E.name, '') <> 'microsoft_database_tools_support'
	AND sp.name=@procedureName
ORDER BY sp.name,param.parameter_id ASC
END
GO
