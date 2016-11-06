<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="fjzx_data_type" required="true"%>
<%@ attribute name="fjzx_field_name" required="true"%>
<%@ attribute name="fjzx_field_tip_name" required="true"%>
<%@ attribute name="fjzx_nullable" required="true"%>
<%@ attribute name="fjzx_field_placeholder" required="false"%>

<c:if test="${fjzx_nullable=='true'}">
	<c:set var="nullableClass" value=""></c:set>
</c:if>
<c:if test="${fjzx_nullable!='true'}">
	<c:set var="nullableClass" value="fjzx-prog-not-null"></c:set>
</c:if>
<c:if test="${fjzx_field_placeholder!=''}">
	<c:set var="place_holder" value="${fjzx_field_placeholder}"></c:set>
</c:if>
<c:if test="${fjzx_field_placeholder==null||fjzx_field_placeholder==''}">
	<c:set var="place_holder" value="${fjzx_field_tip_name}"></c:set>
</c:if>
<input class="form-control ${fjzx_data_type} ${nullableClass}" fjzx_field_name="${fjzx_field_name}" fjzx_field_tip_name="${fjzx_field_tip_name}" placeholder="${place_holder}"  type="text" />