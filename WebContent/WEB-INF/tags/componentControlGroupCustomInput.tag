<%@ tag pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="fjzx_field_name" required="true"%>

<span class="fjzx-prog-custom-info-container" fjzx_custom_input_field_name="${fjzx_field_name}"></span>

<%--
<div class="control-group" style="display: none;">
	<label class="control-label">自定义信息模板</label>
	<div class="controls">
		<div class="fjzx-prog-dropdown-custom-input">
			<div class="fjzx-prog-component-custom-input-select-wrapper custom-input-group custom-input-control">
				<input type="text" class="fjzx-custom-input form-control" />
				<span class="custom-input-group-addon">
					<span class="icon fa-ellipsis-horizontal"></span>
				</span>
			</div>
			<div class="fjzx-prog-dropdown-custom-input-select" style="display: none;">
				<ul class="fjzx-prog-dropdown-custom-input-select-list">
					<li>白色</li>
					<li>黑色</li>
					<li>蓝色</li>
				</ul>
				<table style="width: 100%;">
					<tr>
						<td nowrap>
							<div class="fjzx-prog-dropdown-custom-input-select-pagination pagination pagination-small">
								<ul>
									<li><a class="fjzx-prog-prev" href="javascript: void(0);">&laquo;</a></li><!--上一页-->
									<li><input class="fjzx-prog-page-input" type="text" value="1" readonly style="cursor: default;" /></li><!--当前页数-->
									<li><a class="fjzx-prog-next" href="javascript: void(0);">&raquo;</a></li><!--下一页-->
								</ul>
							</div>
						</td>
						<td nowrap style="text-align: right;padding-right: 10px;">
							<button class="fjzx-prog-dropdown-custom-input-select-reset btn" type="button" style="padding: 2px 6px;vertical-align: baseline;">清空</button>
							<button class="fjzx-prog-dropdown-custom-input-select-close btn" type="button" style="padding: 2px 6px;vertical-align: baseline;">关闭</button>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</div>
--%>