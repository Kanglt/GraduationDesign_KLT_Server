<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="type" required="true"%>
<%@ attribute name="name" required="true"%>
<%@ attribute name="fjzx_field_name" required="true"%>
<%@ attribute name="fjzx_field_tip_name" required="true"%>
<%@ attribute name="htmlType" required="true"%>
<%@ attribute name="value" required="true"%>
<%@ attribute name="extra" required="false"%>

<jsp:useBean id="tagBean" scope="page" class="lyu.klt.frame.module.systemcode.ComponentSystemCodeBean"></jsp:useBean>
<jsp:setProperty name="tagBean" property="request" value="${pageContext.request}"/>
<jsp:setProperty name="tagBean" property="response" value="${pageContext.response}"/>
<jsp:setProperty name="tagBean" property="type" value="${type}"/>
<jsp:setProperty name="tagBean" property="extra" value="${extra}"/>
<jsp:setProperty name="tagBean" property="exec" value="true"/>

<c:if test="${htmlType=='radio'}">
	<c:forEach var="item" items="${tagBean.list}">
		<c:if test="${item.codeValue==value}"><c:set var="checked" value="checked" /></c:if>
		<c:if test="${item.codeValue!=value}"><c:set var="checked" value="" /></c:if>
		<input class="fjzx-prog-string" type="radio" name="${name}" fjzx_field_name="${fjzx_field_name}" fjzx_field_tip_name="${fjzx_field_tip_name}" id="radio_${name}_${item.id}" value="${item.codeValue}" ${checked} style="width: 20px;border: none;"/><label for="radio_${name}_${item.id}">${item.codeName}</label>
	</c:forEach>
</c:if>

<c:if test="${htmlType=='checkbox'}">
	<c:forEach var="item" items="${tagBean.list}">
		<c:if test="${item.codeValue==value}"><c:set var="checked" value="checked" /></c:if>
		<c:if test="${item.codeValue!=value}"><c:set var="checked" value="" /></c:if>
		<input class="fjzx-prog-string" type="checkbox" name="${name}" fjzx_field_name="${fjzx_field_name}" fjzx_field_tip_name="${fjzx_field_tip_name}" id="radio_${name}_${item.id}" value="${item.codeValue}" ${checked} style="width: 20px;border: none;"/><label for="radio_${name}_${item.id}">${item.codeName}</label>
	</c:forEach>
</c:if>