set ANSI_NULLS ON
set QUOTED_IDENTIFIER ON
go








ALTER PROCEDURE [dbo].[updateUserDynamicThumbUpNum]
	@userId VARCHAR(22) ,
	@id int ,
	@type VARCHAR(22),
	@msg VARCHAR(1000) OUTPUT,--MSG
	@size INT OUTPUT,--ROW SIZE
	@isThumbUp INT OUTPUT

as 
begin

SET NOCOUNT ON
	SET XACT_ABORT ON
	
	SELECT 
		@size=COUNT(*)
	FROM [dbo].[user]
		WHERE [userId]=@userId
	IF(@size <= 0)
		EXEC [dbo].[klt_frame_sp_raise_error] '000000','该记录不存在'

	if(@type=0)
		begin
			select  @isThumbUp=count(*)
			from [dbo].[userThumbUp]
			where [userId]=@userId and [dynamicId]=@id
			
			if(@isThumbUp=0)
				begin 
					INSERT INTO [dbo].[userThumbUp] VALUES(@userId,@id)
					update [dbo].[userDynamic] set [dynamicThumbUpNum]=[dynamicThumbUpNum]+1 where [id]=@id
				end
		end
	else if(@type=1)
		begin
			select  @isThumbUp=count(*)
			from [dbo].[userThumbUp]
			where [userId]=@userId and [dynamicId]=@id
			
			if(@isThumbUp=1)
				begin 
					DELETE FROM [dbo].[userThumbUp]
					WHERE [userId]=@userId and [dynamicId]=@id

					update [dbo].[userDynamic] set [dynamicThumbUpNum]=[dynamicThumbUpNum]-1 where [id]=@id

				end
			
		end

SET @msg=[dbo].klt_frame_fn_multi_language_get_resource('000000','修改成功')
	SET @size=1
	SELECT @msg AS msg,@size AS recordSize
	RETURN
end




