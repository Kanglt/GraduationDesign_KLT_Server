<%@ tag pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="login" required="false"%>

<link rel="stylesheet" type="text/css" href="${basePath}bootstrap/css/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="${basePath}bootstrap/css/bootstrap-responsive.css" />
<link rel="stylesheet" type="text/css" href="${basePath}css/jquery-ui.min.css" />
<link rel="stylesheet" type="text/css"href="${basePath}css/base.css" />
<link rel="stylesheet" type="text/css" href="${basePath}css/form.css"/>
<link rel="stylesheet" type="text/css" href="${basePath}css/main.css"/>
<link rel="stylesheet" type="text/css" href="${basePath}css/fjzx-frame.css" />
<link rel="stylesheet" type="text/css" href="${basePath}css/custom.animation.css" />
		<link rel="stylesheet" type="text/css" href="${basePath}ztree/css/zTreeStyle/zTreeStyle.css" />
<c:if test="${login==true}">
<link rel="stylesheet" type="text/css" href="${basePath}css/login_case.css" />
</c:if>

<!--[if lte IE 8]>
	<link href="${basePath}css/ie8.css" rel="stylesheet" type="text/css"/>
<![endif]-->