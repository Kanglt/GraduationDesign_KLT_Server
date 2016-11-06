<%@ tag pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="select_type" required="true"%>
<%@ attribute name="fjzx_select_field_name" required="true"%>
<%@ attribute name="fjzx_field_name" required="true"%>
<%@ attribute name="fjzx_field_tip_name" required="true"%>
<%@ attribute name="fjzx_nullable" required="true"%>
<%-- 以下是默认值配置，非必填 --%>
<%@ attribute name="fjzx_default_select_field_value" required="false"%><%-- 默认值 --%>
<%@ attribute name="fjzx_default_display_value" required="false"%><%-- 默认显示 --%>
<%-- 以下是下拉框多级选择配置，非必填，多选的机制是：子孙级组件会取父级值作为查询条件，父级值发生变动时，所有子孙级值都会被清空 --%>
<%@ attribute name="fjzx_select_field_name_parent" required="false"%><%-- 指定父级ComponentSelect组件的fjzx_select_field_name，如"queryStatus" --%>
<%@ attribute name="fjzx_select_field_name_descendent" required="false"%><%-- 指定所有子孙级ComponentSelect组件的fjzx_select_field_anme，多个的话用英文逗号“,”分开，如“subWarehouseId,locationId” --%>

<c:if test="${fjzx_nullable=='true'}">
	<c:set var="nullableClass" value=""></c:set>
</c:if>
<c:if test="${fjzx_nullable!='true'}">
	<c:set var="nullableClass" value="fjzx-prog-not-null"></c:set>
</c:if>

<div class="fjzx-prog-dropdown">
	<div class="fjzx-prog-component-select-wrapper btn btn-default">
			<input class="fjzx-prog-component-select fjzx-prog-string ${nullableClass}" fjzx_select_type="${select_type}" fjzx_select_field_name="${fjzx_select_field_name}" fjzx_select_field_value="${fjzx_default_select_field_value}" fjzx_select_field_value_origin="${fjzx_default_select_field_value}" value="${fjzx_default_display_value}" fjzx_field_name="${fjzx_field_name}" fjzx_field_tip_name="${fjzx_field_tip_name}" fjzx_select_field_name_parent="${fjzx_select_field_name_parent}" fjzx_select_field_name_descendent="${fjzx_select_field_name_descendent}" placeholder="${fjzx_field_tip_name}" type="text" style="cursor: pointer;" readonly />
	</div>
</div>