<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<%  response.setHeader("Pragma","No-cache"); 
	response.setHeader("Cache-Control","no-cache"); 
	response.setDateHeader("Expires", 0); 
%> 
<html>
<head>
	<script type="text/javascript" src="../resource/js/configuration/overWorkType.js"></script>
</head>
<body>
<table id="table_overtime_type" cellpadding="0" cellspacing="1" width="85%" class="tablesorter">
	<thead>
		<tr align="center">
			<th width="30%" nowrap="nowrap">类型名称</th>
			<th width="50%" nowrap="nowrap">类型描述</th>
			<th width="10%" nowrap="nowrap">加班酬劳</th>
			<th width="10%" nowrap="nowrap">最多累计小时数</th>
		</tr>
	</thead>
	<tbody id="tbody_overtime_type">
		<s:iterator value="allOtType">
			<tr id="<s:property value='otNo'/>" align="center">
				<td align="center"><s:property value="otName" /></td>
				<td align="center"><s:property value="otDesc" /></td>
				<td align="center"><s:property value="otHourRate.toString()" /></td>
				<td align="center"><s:property value="otOverLimit" /></td>
			</tr>
		</s:iterator>
	</tbody>
</table>
<div class="btnlink">
	<a href="#" id="link_add_overtime_type">新增</a>
	<a href="#" id="link_delete_overtime_type">删除</a>
	<a href="#" id="link_update_overtime_type">修改</a>
	<a href="#" id="link_sort_overtime_type">保存排序</a>
</div>
<br>
<div id="dialog_overtime_type" title="加班类型设置">
	<form id="overWorkTypeForm" method="post">
		<table cellpadding="0" cellspacing="1" width="100%">
			<tr>
				<td>类型名称<font color="red">*</font>：</td>
				<td class="errorMessage"><input id="otName" type="text" maxlength="64" /><label id="label_otName">&nbsp;</label></td>
			</tr>
			<tr>
				<td>类型描述<font color="red">*</font>：</td>
				<td class="errorMessage"><textarea id="otDesc" cols="30" rows="3" onkeypress="MKeyTextLength(this,128);"></textarea><label id="label_otDesc">&nbsp;</label></td>
			</tr>
			<tr>
				<td>加班酬劳<font color="red">*</font>：</td>
				<td class="errorMessage">
					<s:select id="otHourRate" name="otHourRate" required="true"
						list="#{1.0:'1.0', 1.5:'1.5',2.0:'2.0',2.5:'2.5',3.0:'3.0'}" /><span>倍</span>
					<label id="label_otHourRate">&nbsp;</label>
				</td>
			</tr>
			<tr>
				<td>最多累计小时数：</td>
				<td class="errorMessage"><input id="otOverLimit" type="text" size="3" maxlength="3" onkeyup="value=value.replace(/[^0-9{\.}]/g,'')"></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input id="btn_add_overtime_type" type="button" value="保存" />
					<input id="btn_update_overtime_type" type="button" value="保存" />
					<input type="reset" value="清空" />
					<input id="btn_overtime_type_close" type="button" value="关闭" />
					<input type="hidden" id="otNo" />
				</td>
			</tr>
		</table>
	</form>
</div>
<script type="text/javascript">
	var config={
		tableId		:"table_overtime_type",
		tbodyId		:"tbody_overtime_type",
		dialogId	:"dialog_overtime_type",
		updateButton:"btn_update_overtime_type",
		addButton	:"btn_add_overtime_type",
		dialogHeight:200,
		addLink		:"link_add_overtime_type",
		deleteLink	:"link_delete_overtime_type",
		updateLink	:"link_update_overtime_type",
		sortLink	:"link_sort_overtime_type",
		closeButton	:"btn_overtime_type_close"
	};
	overtimeTypeManager = new OvertimeTypeManager(config);
</script>
</body>
</html>