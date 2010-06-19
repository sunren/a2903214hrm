<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<%  response.setHeader("Pragma","No-cache"); 
	response.setHeader("Cache-Control","no-cache"); 
	response.setDateHeader("Expires", 0); 
%> 
<html>
<head>
	<script type="text/javascript" src="../resource/js/configuration/attdMachine.js"></script>
</head>
<body>
<table id="table_attdMachine" cellpadding="0" cellspacing="1" width="85%" class="tablesorter">
	<thead>
		<tr align="center">
			<th width="20%" nowrap="nowrap">���ڻ���</th>
			<th width="20%" nowrap="nowrap">���ڻ�IP</th>
			<th width="10%" nowrap="nowrap">���ڻ��ӿ�</th>
			<th width="10%" nowrap="nowrap">���ڻ���������</th>
			<th width="10%" nowrap="nowrap">���ڻ���������</th>
			<th width="10%" nowrap="nowrap">��ע</th>
			<th width="10%" nowrap="nowrap">״̬</th>
		</tr>
	</thead>
	<tbody id="tbody_attdMachine">
		<s:iterator value="allAttdMachine">
			<tr id="<s:property value='macId'/>" align="center" macStatus="<s:property value="macStatus"/>" macType="<s:property value="macType" />">
				<td align="center"><s:property value="macNo" /></td>
				<td align="center"><s:property value="macIP" /></td>
				<td align="center"><s:property value="macPort" /></td>
				<td align="center"><s:property value="macPassword" /></td>
				<td align="center"><s:if test="macType==0">IP</s:if><s:else>�ӿ�</s:else></td>
				<td align="center"><s:property value="macDesc" /></td>
				<td align="center"><s:if test="macStatus==1">����</s:if><s:else>δ����</s:else></td>
			</tr>
		</s:iterator>
	</tbody>
</table>
<div class="btnlink">
	<a href="#" id="link_add_attdMachine">����</a>
	<a href="#" id="link_delete_attdMachine">ɾ��</a>
	<a href="#" id="link_update_attdMachine">�޸�</a>
	<a href="#" id="link_sort_attdMachine">��������</a>
</div>
<br>
<div id="dialog_attdMachine" title="���ڻ�����">
	<form id="attdMachineForm" method="post">
		<table cellpadding="0" cellspacing="1" width="100%">
			<tr>
				<td>���ڻ���<font color="red">*</font>��</td>
				<td class="errorMessage"><input id="macNo" type="text" maxlength="64" /><label id="label_macNo">&nbsp;</label></td>
			</tr>
			<tr>
				<td>���ڻ�IP<font color="red">*</font>��</td>
				<td class="errorMessage"><input id="macIP" type="text" maxlength="64" /><label id="label_macIP">&nbsp;</label></td>
			</tr>
			<tr>
				<td>���ڻ��ӿ�<font color="red">*</font>��</td>
				<td class="errorMessage"><input id="macPort" type="text" maxlength="64" /><label id="label_macPort">&nbsp;</label></td>
			</tr>
			<tr>
				<td>���ڻ���������<font color="red">*</font>��</td>
				<td class="errorMessage"><input id="macPassword" type="text" maxlength="64" /><label id="label_password">&nbsp;</label></td>
			</tr>
			<tr>
				<td>���ڻ���������<font color="red">*</font>��</td>
				<td class="errorMessage">
					<s:select id="macType" name="macType" required="true"
						list="#{0:'IP', 1:'�ӿ�'}" /><span>��ʽ</span>
					<label id="label_macType">&nbsp;</label>
				</td>
			</tr>
			<tr>
				<td>״̬<font color="red">*</font>��</td>
				<td class="errorMessage">
					<s:select id="macStatus" name="macStatus" required="true"
						list="#{1:'����',0:'ͣ��'}" />
					<label id="label_macStatus">&nbsp;</label>
				</td>
			</tr>
			<tr>
				<td>����������</td>
				<td class="errorMessage"><textarea id="macDesc" cols="30" rows="3"></textarea><label id="label_macDesc">&nbsp;</label></td>
			</tr>			
			<tr>
				<td colspan="2" align="center">
					<input id="btn_add_attdMachine" type="button" value="����" />
					<input id="btn_update_attdMachine" type="button" value="����" />
					<input type="reset" value="���" />
					<input id="btn_attdMachine_close" type="button" value="�ر�" />
					<input type="hidden" id="macId" />
					<input type="hidden" id="macSortId" />
				</td>
			</tr>
		</table>
	</form>
</div>
<script type="text/javascript">
	var config={
		tableId		:"table_attdMachine",
		tbodyId		:"tbody_attdMachine",
		dialogId	:"dialog_attdMachine",
		updateButton:"btn_update_attdMachine",
		addButton	:"btn_add_attdMachine",
		dialogHeight:200,
		addLink		:"link_add_attdMachine",
		deleteLink	:"link_delete_attdMachine",
		updateLink	:"link_update_attdMachine",
		sortLink	:"link_sort_attdMachine",
		closeButton	:"btn_attdMachine_close"
	};
	attdMachineManager = new AttdMachineManager(config);
</script>
</body>
</html>