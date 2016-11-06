<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="fjzx_field_name" required="true"%>
<%@ attribute name="fjzx_field_tip_name" required="true"%>
<%@ attribute name="fjzx_nullable" required="true"%>
<%@ attribute name="fjzx_css" required="false"%>

<c:if test="${fjzx_nullable=='true'}">
	<c:set var="nullableClass" value=""></c:set>
</c:if>
<c:if test="${fjzx_nullable!='true'}">
	<c:set var="nullableClass" value="fjzx-prog-not-null"></c:set>
</c:if>
<textarea class="fjzx-prog-string ${nullableClass} ${fjzx_css}" fjzx_field_name="${fjzx_field_name}" fjzx_field_tip_name="${fjzx_field_tip_name}" placeholder="${fjzx_field_tip_name}"></textarea>