USE [GraduationDesign_KLT_DB]
GO

DECLARE	@return_value int,
		@msg varchar(1000),
		@size int

EXEC	@return_value = [dbo].[queryTotalTraining]
		@msg = @msg OUTPUT,
		@size = @size OUTPUT

SELECT	@msg as N'@msg',
		@size as N'@size'

SELECT	'Return Value' = @return_value

GO
