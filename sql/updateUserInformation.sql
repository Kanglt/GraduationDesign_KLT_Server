set ANSI_NULLS ON
set QUOTED_IDENTIFIER ON
go









ALTER PROCEDURE [dbo].[updateUserInformation]
	@userId VARCHAR(22) ,
	@userName VARCHAR(22) ,
	@userPassword VARCHAR(22) ,
	@userBirthday VARCHAR(22) ,
	@userAge VARCHAR(22) ,
	@userPhoneNumble VARCHAR(22) ,
	@userSex VARCHAR(22) ,
	@userEmail VARCHAR(22) ,
	@userPhoto VARCHAR(200) ,
	@msg VARCHAR(1000) OUTPUT,--MSG
	@size INT OUTPUT--ROW SIZE

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

update [dbo].[user]
set [userId]=@userId,[userName]=@userName,[userPassword]=@userPassword,[userBirthday]=@userBirthday,[userPhoneNumble]=@userPhoneNumble,[userSex]=@userSex,[userEmail]=@userEmail,[userPhoto]=@userPhoto,[userAge]=@userAge
where [userId]=@userId

SET @msg=[dbo].klt_frame_fn_multi_language_get_resource('000000','修改成功')
	SET @size=1
	SELECT @msg AS msg,@size AS recordSize
	RETURN
end





