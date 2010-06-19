<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<%  response.setHeader("Pragma","No-cache"); 
	response.setHeader("Cache-Control","no-cache"); 
	response.setDateHeader("Expires", 0); 
%> 
<html>
	<head>
		<title>合同种类设置</title>
		<script type="text/javascript" src="../resource/js/configuration/contractType.js"></script>
	</head>
<body>
<table id="table_contract_type" cellpadding="0" cellspacing="1"
	width="70%" class="tablesorter">
	<thead>
		<tr align="center">
			<th width="30%" nowrap="nowrap">合同种类名称</th>
			<th width="70%" nowrap="nowrap">合同种类描述</th>
		</tr>
	</thead>
	<tbody id="tbody_contract_type">
		<s:iterator value="allContractType">
			<tr id='<s:property value="id"/>' align="center"
				ondblclick="contractTypeManager.showUpdateForm(this);">
				<td align="center"><s:property value="ectName" /></td>
				<td align="center"><s:property value="ectDescription" /></td>
			</tr>
		</s:iterator>
	</tbody>
</table>
<div class="btnlink">
	<a href="#" id="link_add_contract_type">新增</a>
	<a href="#" id="link_delete_contract_type">删除</a>
	<a href="#" id="link_update_contract_type">修改</a>
	<a href="#" id="link_sort_contract_type">保存排序</a>
</div>

<div id="dialog_contract_type" title="合同种类设置">
	<form id="contractTypeForm" method="post">
		<table cellpadding="0" cellspacing="1" width="100%">
			<tr>
				<td>合同种类名称<font color="red">*</font>：</td>
				<td class="errorMessage"><input id="ectName" TYPE="text" size="20"><label id="label_ectName">&nbsp;</label><br></td>
			</tr>
			<tr>
				<td>合同种类描述：</td>
				<td class="errorMessage"><textarea id="ectDescription" cols="30" rows="3"></textarea></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td colspan="2">
					<input id="btn_add_contract_type" type="button" value="保存">
					<input id="btn_update_contract_type" type="button" value="保存">
					<input type="reset" value="清空">
					<input type="button" id="btn_contract_type_close" value="关闭">
					<input type="hidden" id="ectId"/>
					<input type="hidden" id="ectSortId"/>
				</td>
			</tr>
		</table>
	</form>
</div>
<script type="text/javascript">
	var config={
		tableId		:"table_contract_type",
		tbodyId		:"tbody_contract_type",
		dialogId	:"dialog_contract_type",
		updateButton:"btn_update_contract_type",
		addButton	:"btn_add_contract_type",
		dialogHeight:152,
		addLink		:"link_add_contract_type",
		deleteLink	:"link_delete_contract_type",
		updateLink	:"link_update_contract_type",
		sortLink	:"link_sort_contract_type",
		closeButton	:"btn_contract_type_close"
	};
	contractTypeManager = new ContractTypeManager(config);
</script>
</body>
</html>