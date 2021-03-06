USE [ICCO]
GO
/****** Object:  StoredProcedure [dbo].[fjzx_frame_sp_meta_table]    Script Date: 07/11/2016 15:34:54 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[fjzx_frame_sp_meta_table]
	@tableName VARCHAR(100),
	
	@msg VARCHAR(1000) OUTPUT,--MSG
	@size INT OUTPUT--ROW SIZE
--WITH ENCRYPTION
AS
BEGIN
	SET NOCOUNT ON
	SET XACT_ABORT ON
	
	SELECT 
	(CASE
		WHEN a.colorder=1 THEN d.name
		ELSE ''
	END) tableName,
	a.colorder fieldSeqNo,
	a.name fieldName,
	(CASE
		WHEN COLUMNPROPERTY( a.id,a.name,'IsIdentity')=1 THEN 'Y'
		ELSE ''
	END) fieldSignal,
	(CASE 
		WHEN
			(SELECT COUNT(*) FROM sysobjects WHERE (name IN
						(
							SELECT name FROM sysindexes WHERE (id = a.id) AND (indid IN 
								(
									SELECT indid FROM sysindexkeys WHERE (id = a.id) AND (colid IN 
										(
											SELECT colid FROM syscolumns WHERE (id = a.id) AND (name = a.name)
										)
									)
								)
							)
						)
					) AND (xtype = 'PK')
			) > 0 THEN 'Y'
		ELSE ''
	END) primaryKey,
	b.name fieldType,
	a.length fieldBytes,
	COLUMNPROPERTY(a.id,a.name,'PRECISION') AS fieldLength,
	ISNULL(COLUMNPROPERTY(a.id,a.name,'Scale'),0) AS decimalLength,
	(CASE 
		WHEN a.isnullable=1 THEN 'Y'
		ELSE ''
	END) nullAble,
	ISNULL(e.text,'') defaultValue,
	CAST(ISNULL(g.[value],'') AS VARCHAR(MAX)) AS fieldDesc
	FROM  syscolumns a 
	LEFT JOIN systypes b ON a.xtype=b.xusertype
	INNER JOIN sysobjects d ON a.id=d.id  AND  d.xtype='U' AND d.name<>'dtproperties'
	LEFT JOIN syscomments e ON a.cdefault=e.id
	LEFT JOIN sys.extended_properties g ON a.id=g.major_id AND a.colid = g.minor_id  
	WHERE d.name = @tableName---查询具体的表，注释掉后就是查询整个数据库了
	ORDER BY a.id,a.colorder
END
GO
