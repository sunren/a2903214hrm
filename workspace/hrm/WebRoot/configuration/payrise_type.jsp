<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK" %>
<%@ taglib prefix="s" uri="webwork"%>
<%  response.setHeader("Pragma","No-cache"); 
	response.setHeader("Cache-Control","no-cache"); 
	response.setDateHeader("Expires", 0); 
%> 
<html>
	<head>
		<script type="text/javascript" src="../resource/js/configuration/payRiseType.js"></script>
	</head>
<body>
<table id="payrisetype_table" cellpadding="0" cellspacing="1" 
	width="85%" class="tablesorter">
	<thead>
		<tr align="center">
			<th width="30%" nowrap="nowrap">��������</th>
			<th width="70%" nowrap="nowrap">��������</th>
		</tr>
	</thead>
	<tbody id="payRiseTypeTabPageItems">
		<s:iterator value="allEcptype">
			<tr id='<s:property value="id"/>' align="center" ondblclick="ecptypeManager.showUpdateForm(this);">
				<td align="center"><s:property value="ecptypeName" /></td>
				<td align="center"><s:property value="ecptypeDesc" /></td>
			</tr>
		</s:iterator>
	</tbody>
</table>
<div class="btnlink">
	<a href="#" id="link_add_new_ecp_type">����</a>
	<a href="#" id="link_delete_ecp_type">ɾ��</a>
	<a href="#" id="link_show_update_ecp_type">�޸�</a>
	<a href="#" id="link_sort_ecp_type">��������</a>
</div>

<div id="payRiseTypeDiv" title="��н��������">
	<form id="payRiseTypeForm" method="post">
		<table cellpadding="0" cellspacing="1" width="100%">
			<tr>
				<td>��������<font color="red">*</font>��</td>
				<td class="errorMessage"><input id="ecptypeName" type="text" size="32" onkeypress="MKeyTextLength(this,32);"/><label id="label_ecptypeName">&nbsp;</label></td>
			</tr>
			<tr>
				<td>����������</td>
				<td class="errorMessage"><textarea id="ecptypeDesc" cols="30" rows="3" onkeypress="MKeyTextLength(this,128);"></textarea></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input id="payRiseTypetabPageaddbtm" type="button" value="����">
					<input id="payRiseTypetabPageupdatebtm" type="button" value="����">
					<input type="reset" value="���">
					<input id="btn_payRiseTypeclose" type="button" value="�ر�">
					<input type="hidden" id="ecptypeId">
					<input type="hidden" id="ecptypeSortId"/>
				</td>
			</tr>
		</table>
	</form>
</div>
<script type="text/javascript">
	var config={
		tableId		:"payrisetype_table",
		tbodyId		:"payRiseTypeTabPageItems",
		dialogId	:"payRiseTypeDiv",
		updateButton:"payRiseTypetabPageupdatebtm",
		addButton	:"payRiseTypetabPageaddbtm",
		dialogHeight:200,
		addLink		:"link_add_new_ecp_type",
		deleteLink	:"link_delete_ecp_type",
		updateLink	:"link_show_update_ecp_type",
		sortLink	:"link_sort_ecp_type",
		closeButton	:"btn_payRiseTypeclose"
	};
	ecptypeManager = new EcptypeManager(config);
</script>
</body>
</html>