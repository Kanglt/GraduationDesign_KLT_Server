set ANSI_NULLS ON
set QUOTED_IDENTIFIER ON
go








ALTER PROCEDURE [dbo].[queryUserBodyData]
	@userId VARCHAR(22) ,
	@dataType VARCHAR(50) ,
	@msg VARCHAR(1000) OUTPUT,--MSG
	@size INT OUTPUT--ROW SIZE

as 
begin

SET NOCOUNT ON
	SET XACT_ABORT ON
	
	SELECT 
		@size=COUNT(*)
	FROM [dbo].[user] a ,[dbo].[userBodyData] b
		WHERE a.[userId]=@userId and b.[userId]=@userId and b.[dataType]=@dataType
	IF(@size <= 0)
		EXEC [dbo].[klt_frame_sp_raise_error] '000000','该记录不存在'

select  a.[userSex],a.[userAge],b.*
FROM [dbo].[user] a ,[dbo].[userBodyData] b
where a.[userId]=@userId and b.[dataType]=@dataType order by b.[id] asc
SET @msg=[dbo].klt_frame_fn_multi_language_get_resource('000000','读取成功')
	SET @size=1
end




