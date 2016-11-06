<%@ tag pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="select_type" required="true"%>

<%-- fjzx_options="{'chkStyle':'radio'}"，此处全部使用单引号 --%>
<%-- 注意：属性名和属性值都要用单引号
{
	'caption':'xxx',//默认'选择'
	'chkStyle': 'checkbox',//'checkbox'、'radio'，默认'radio'
	'radioType': 'all',//chkStyle='radio'时起作用，'all'整棵树单选一个，'level'各级内单选一个，默认'all'
	'chkboxType': {'Y':'ps','N':'ps'},//Y表示选中，N表示取消选中，'p'表示对父级联动状态，'s'表示对子级联动状态，默认{'Y':'ps','N':'ps'}
}
--%>
<%@ attribute name="fjzx_options" required="true"%>

<%@ attribute name="fjzx_select_field_name" required="true"%>
<%@ attribute name="fjzx_field_name" required="true"%>
<%@ attribute name="fjzx_field_tip_name" required="true"%>
<%@ attribute name="fjzx_nullable" required="true"%>
<%@ attribute name="fjzx_extra_params" required="false"%>
<%-- 以下是默认值配置，非必填 --%>
<%@ attribute name="fjzx_default_select_field_value" required="false"%><%-- 默认值 --%>
<%@ attribute name="fjzx_default_display_value" required="false"%><%-- 默认显示 --%>

<c:if test="${fjzx_nullable=='true'}">
	<c:set var="nullableClass" value=""></c:set>
</c:if>
<c:if test="${fjzx_nullable!='true'}">
	<c:set var="nullableClass" value="fjzx-prog-not-null"></c:set>
</c:if>

<div class="fjzx-prog-dropdown-tree-select">
	<div class="fjzx-prog-component-tree-select-wrapper btn btn-default">
			<input class="fjzx-prog-component-tree-select fjzx-prog-string ${nullableClass}" fjzx_select_type="${select_type}" fjzx_options="<c:out value="${fjzx_options}"/>" fjzx_select_tree_field_name="${fjzx_select_field_name}" fjzx_select_tree_field_value="${fjzx_default_select_field_value}" fjzx_select_tree_field_value_origin="${fjzx_default_select_field_value}" value="${fjzx_default_display_value}" fjzx_field_name="${fjzx_field_name}" fjzx_field_tip_name="${fjzx_field_tip_name}" fjzx_extra_params="${fjzx_extra_params}" placeholder="${fjzx_field_tip_name}" type="text" style="cursor: pointer;" readonly />
	</div>
</div>

<%--
示例：
<fjzx:componentTreeSelect fjzx_nullable="false" select_type="DEPARTMENT" fjzx_options="{'chkStyle':'radio','chkboxType': {'Y':'','N':''}}" fjzx_extra_params="LEAF" fjzx_field_name="departmentIdName" fjzx_field_tip_name="部门" fjzx_select_field_name="departmentId" />
--%>