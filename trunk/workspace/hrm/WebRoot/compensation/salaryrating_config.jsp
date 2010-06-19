<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<%  response.setHeader("Pragma","No-cache"); 
	response.setHeader("Cache-Control","no-cache"); 
	response.setDateHeader("Expires", 0); 
%>
<html>
<head>
   <script type="text/javascript" src="../resource/js/compensation/salaryrating_config.js"></script>
</head>
<body>
<table id="table_jobgrade" cellspacing="1" cellspacing="0" width="100%" border="0" class="tablesorter">
	<thead>
		<tr align="center">
			<th width="5%" nowrap="nowrap">н�ʼ�����</th>
			<th width="10%" nowrap="nowrap">н�ʼ���ȼ�</th>
			<th width="10%" nowrap="nowrap">�ο�н��</th>
			<th width="75%" nowrap="nowrap">н�ʼ�������</th>
		</tr>
	</thead>
	<tbody id="tbody_jobgrade">
		<s:iterator value="alljobgrade">
			<tr id='<s:property value="id"/>' align="center" >
				<td align="center"><s:property value="jobGradeNo" /></td>
				<td align="center"><s:property value="jobGradeLevel" /></td>
				<td align="right">
					<s:property value="getDecry('jobGradeMrp',id,(@com.hr.util.MyTools@BIGDECIMAL))" />
				</td>
				<td align="center"><s:property value="jobGradeName" /></td>
			</tr>
		</s:iterator>
	</tbody>
</table>
<div class="btnlink">
	<a id="link_add_jobgrade">����</a>
	<a id="link_delete_jobgrade">ɾ��</a>
	<a id="link_update_jobgrade">�޸�</a>
	<a id="link_sort_jobgrade">��������</a>
</div>
<br>
<div id="dialog_jobgrade" title="н�ʼ�������">
	<table cellpadding="0" cellspacing="1" width="100%">
		<tr>
			<td>н�ʼ�����<font color="red">*</font>��</td>
			<td class="errorMessage">
				<input id="jobGradeNo" size="16" maxlength="16">
				<label id="label_jobGradeNo">&nbsp;</label>
			</td>
		</tr>
		<tr>
			<td>н�ʼ���ȼ�(D1)<font color="red">*</font>��</td>
			<td class="errorMessage">
				<input id="jobGradeLevel" type="text" size="3" maxlength="3" >
				<label id="label_jobGradeLevel">&nbsp;</label>
			</td>
		</tr>
		<tr>
			<td>�ο�н��(D11)<font color="red">*</font>��</td>
			<td class="errorMessage">
				<input id="jobGradeMrp" maxlength="8">
				<label id="label_jobGradeMrp">&nbsp;</label>
			</td>
		</tr>
		<tr>
			<td>н�ʼ�������<font color="red">*</font>��</td>
			<td class="errorMessage">
			    <textarea rows="3" cols="35" id="jobGradeName"></textarea>
			</td>
		</tr>
		<tr>
			<td colspan=2>
			  	<font color="red">��ע��D1��D11��ʾн�������ж�Ӧ�ı����������ơ�</font>
			</td>
		</tr>
		<tr height="35">
			<td>&nbsp;</td>
			<td colspan="2">
				<input id="btn_jobgrade_update" type="button" value="����">
				<input id="btn_jobgrade_create" type="button" value="����">
				<input id="btn_jobgrade_close" type="button" value="�ر�">
				<input id="jobgradeId" type="hidden" />
			</td>
		</tr>
	</table>
</div>

<script type="text/javascript">
    var config={
        tableId     :"table_jobgrade",
        tbodyId     :"tbody_jobgrade",
        dialogId    :"dialog_jobgrade",
        updateButton:"btn_jobgrade_update",
        addButton   :"btn_jobgrade_create",
        dialogHeight:230,
        dialogWidth :400,
        addLink     :"link_add_jobgrade",
        deleteLink  :"link_delete_jobgrade",
        updateLink  :"link_update_jobgrade",
        sortLink    :"link_sort_jobgrade",
        closeButton :"btn_jobgrade_close",
        modal       :true
    };
    jobgradeManager = new JobgradeManager(config);
</script>
</body>
</html>