<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<%  response.setHeader("Pragma","No-cache"); 
	response.setHeader("Cache-Control","no-cache"); 
	response.setDateHeader("Expires", 0); 
%> 
<html>
<head>
<script type="text/javascript" src="../resource/js/configuration/staffCustom.js"></script>
<style type="text/css">
	input[disabled]{background-color: #D4D0C8;}
</style>
</head>
<body>
<table id="table_employee_additonal" cellpadding="0" cellspacing="1" width="95%" class="tablesorter">
	<thead>
		<tr align="center">
			<th width="5%" nowrap="nowrap">�ֶα��</th>
			<th width="25%" nowrap="nowrap">�ֶ�����</th>
			<th width="10%" nowrap="nowrap">�ֶ�����</th>
			<th width="30%" nowrap="nowrap">�ֶβ���</th>
			<th width="30%">��ע</th>
		</tr>
	</thead>
	<tbody id="tbody_employee_additonal">
		<s:iterator value="allEmpaddconf">
			<tr id='<s:property value="eadcId"/>' align="center" eadcFieldType="<s:property value='eadcFieldType'/>">
				<td align="center"><s:property value="'�ֶ�'+(eadcSeq>9?eadcSeq:'0'+eadcSeq)" /></td>
				<td align="center"><s:property value="eadcFieldName" /></td>
				<td align="center">
					<s:if test="eadcFieldType=='number'">��ֵ</s:if>
					<s:elseif test="eadcFieldType=='textarea'">�����ı�</s:elseif>
					<s:elseif test="eadcFieldType=='date'">����</s:elseif>
					<s:elseif test="eadcFieldType=='select'">������</s:elseif>
					<s:else>��ͨ�ı�</s:else>
				</td>
				<td align="center"><s:property value="eadcFieldValue" /></td>
				<td><s:property value="eadcComments" /></td>
			</tr>
		</s:iterator>
	</tbody>
</table>
<div class="btnlink">
	<a href="#" id="link_add_employee_additional">����</a>
	<a href="#" id="link_delete_employee_additional">ɾ��</a>
	<a href="#" id="link_update_employee_additional">�޸�</a>
	<input type="hidden" id="link_sort_employee_additonal"></input><!-- ������������ -->
</div>
<br>
<div id="dialog_employee_additional" title="�û��Զ����ֶ�����">
	<form id="staffCustomForm" method="post">
		<table cellpadding="0" cellspacing="1" width="100%">
			<tr align="left">
			   <td nowrap="nowrap">�ֶα��<font color="red">*</font>��</td>
			   <td><s:select list="fieldList" id="eadcSeq" ></s:select></td>
			</tr>
			<tr>
				<td>�ֶ�����<font color="red">*</font>��</td>
			    <td class="errorMessage"><input type="text" id="eadcFieldName" /><label id="label_eadcFieldName">&nbsp;</label></td>
			</tr>
			<tr>
			    <td nowrap="nowrap">�ֶ�����:</td>
			    <td class="errorMessage" nowrap="nowrap">
					<s:select  id="eadcFieldType"
						list="#{'input':'��ͨ�ı�','textarea':'�����ı�','number':'��ֵ','date':'����','select':'������'}" ></s:select>
			   		<label id="label_eadcFieldType">���ֶ�ֵ����Ҫ������ѡ��,֮����,�ŷָ�</label>
			   </td>
			</tr>
			<tr>
				<td>�ֶβ���:</td>
				<td class="errorMessage" nowrap="nowrap"><input type="text" id="eadcFieldValue" size="24" disabled="disabled"/><label id="label_eadcFieldValue">�ֶ�����Ϊ������ʱ,�������</label></td>
			</tr>
			<tr>
				<td>�� ע:</td>
				<td><textarea id="eadcComments" cols="30" rows="3"></textarea></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input id="btn_add_employee_additional" type="button" value="����" />
					<input id="btn_update_employee_additional" type="button" value="����" />
					<input type="reset" value="���" />
					<input id="btn_close_employee_additional" type="button" value="�ر�" />
					<input type="hidden" id="eadcTableName" value="empadditional" />
					<input type="hidden" id="eadcId" />
				</td>
			</tr>
		</table>
	</form>
</div>
<script type="text/javascript">
	var config={
		tableId		:"table_employee_additonal",
		tbodyId		:"tbody_employee_additonal",
		dialogId	:"dialog_employee_additional",
		updateButton:"btn_update_employee_additional",
		addButton	:"btn_add_employee_additional",
		dialogHeight:240,
		dialogWidth :400,
		addLink		:"link_add_employee_additional",
		deleteLink	:"link_delete_employee_additional",
		updateLink	:"link_update_employee_additional",
		sortLink	:"link_sort_employee_additonal",//��������
		closeButton	:"btn_close_employee_additional",
		seqList		:<s:property value="fieldList" />
	};
	empaddconf = new EmpaddconfManager(config);
</script>
</body>
</html>