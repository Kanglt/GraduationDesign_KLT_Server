<%@ page language="java" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="fjzx" tagdir="/WEB-INF/tags/"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<fjzx:basePath />
		<fjzx:meta title="资源不存在" />
		<fjzx:css />
		<fjzx:preScript />
	</head>
	<body>
		<div class="wrapper">
		    <div class="error-box">
		        <div class="error-type">404</div>
		        <div class="error-info">您访问的资源不存在！</div>
		    </div>
		</div>
		<fjzx:postScript />
		<script>
			$(function(){
				$("button").click(function(){
					$.closeWindow();
				});
			});
		</script>
	</body>
</html>