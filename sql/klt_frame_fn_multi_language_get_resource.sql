USE [GraduationDesign_KLT_DB]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE FUNCTION [dbo].[klt_frame_fn_multi_language_get_resource]
(
	@errorCode VARCHAR(6),
	@verifyString VARCHAR(MAX)
)
RETURNS VARCHAR(11)
--WITH ENCRYPTION
AS
BEGIN
	--此函数是为多语言接口预留，应根据@errorCode查询多语言表，
	--将得到的中文字符串资源与@verifyString进行核对，如果核对不正确，则很可能程序员写错了；在部署到生产环境时，可以关闭核对功能，以提高效率；
	--最后返回对应其他语言的字符串资源
	--现在仅仅是返回原字符串
	RETURN @verifyString
END
GO
