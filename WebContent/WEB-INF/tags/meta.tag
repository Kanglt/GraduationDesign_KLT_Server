<%@ tag pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="klt" uri="http://cn.fjzxdz.tags.core" %>

<%@ attribute name="title" required="false"%>
<%@ attribute name="robots" required="false"%>
<%@ attribute name="author" required="false"%>
<%@ attribute name="keywords" required="false"%>
<%@ attribute name="description" required="false"%>

<title><klt:isEmpty value="${title}" defaultValue="" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible"content="IE=8">
<meta name="robots" content="<klt:isEmpty value="${robots}" defaultValue="noindex,nofollow" />" />
<meta name="Author" content="<klt:isEmpty value="${author}" defaultValue="" />" />
<meta name="Keywords" content="<klt:isEmpty value="${keywords}" defaultValue="" />" />
<meta name="Description" content="<klt:isEmpty value="${description}" defaultValue="" />" />
<link rel="shortcut icon" href="favicon.ico" />