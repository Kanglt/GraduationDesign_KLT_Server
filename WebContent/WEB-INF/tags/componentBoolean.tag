<%@ tag pageEncoding="UTF-8" import="java.util.Random,lyu.klt.frame.utils.IdGenerator"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="fjzx_field_name" required="true"%>
<%@ attribute name="fjzx_field_tip_name" required="true"%>
<%@ attribute name="allName" required="true"%>
<%@ attribute name="trueName" required="true"%>
<%@ attribute name="falseName" required="true"%>
<%@ attribute name="value" required="true"%>

<%
	String fixId =IdGenerator.getNewId();
	request.setAttribute("fixId",fixId);
%>

<c:if test="${allName!=''}">
	<input class="fjzx-prog-string" type="radio" name="${fjzx_field_name}" fjzx_field_name="${fjzx_field_name}" fjzx_field_tip_name="${fjzx_field_tip_name}" id="radio_${fjzx_field_name}_all${fixId}" value="" <c:if test="${value==''||value==null}">checked</c:if> style="width: 20px;"/><label for="radio_${fjzx_field_name}_all${fixId}">${allName}</label>
</c:if>
<input class="fjzx-prog-string" type="radio" name="${fjzx_field_name}" fjzx_field_name="${fjzx_field_name}" fjzx_field_tip_name="${fjzx_field_tip_name}" id="radio_${fjzx_field_name}_true${fixId}" value="true" <c:if test="${value=='true'}">checked</c:if> style="width: 20px;"/><label for="radio_${fjzx_field_name}_true${fixId}">${trueName}</label>
<input class="fjzx-prog-string" type="radio" name="${fjzx_field_name}" fjzx_field_name="${fjzx_field_name}" fjzx_field_tip_name="${fjzx_field_tip_name}" id="radio_${fjzx_field_name}_false${fixId}" value="false" <c:if test="${value=='false'}">checked</c:if> style="width: 20px;"/><label for="radio_${fjzx_field_name}_false${fixId}">${falseName}</label>
