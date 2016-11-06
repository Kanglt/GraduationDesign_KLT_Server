<%@ page language="java" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="fjzx" tagdir="/WEB-INF/tags/"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<fjzx:basePath />
		<fjzx:meta title="您没有权限" />
		<fjzx:css />
		<fjzx:preScript />
	</head>
	<body>
		<p>您没有权限访问该资源</p>
		<p><a href="userController?login" target="_top">登录&gt;&gt;</a></p>
		<fjzx:postScript />
	</body>
</html>