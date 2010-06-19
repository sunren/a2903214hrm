<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<%  response.setHeader("Pragma","No-cache"); 
	response.setHeader("Cache-Control","no-cache"); 
	response.setDateHeader("Expires", 0); 
%>
<html>
<head>
<link rel="stylesheet" href="../resource/css/style.css" type="text/css" />
<script type="text/javascript" src="../resource/js/hrm/common.js"></script>
<script type="text/javascript" src="../resource/js/error.js"></script>
<!-- jQuery start -->
<link rel="stylesheet" type="text/css" href="../resource/js/jquery/themes/base/ui.all.css" />
<link rel="stylesheet" type="text/css" href="../resource/css/tablesorter.css" />
<script type="text/javascript" src="../resource/js/jquery/jquery-1.3.2.js"></script>
<script type="text/javascript" src="../resource/js/jquery/jquery.tablesorter-2.0.3.js"></script>
<script type="text/javascript" src="../resource/js/jquery/jquery.tablednd_0_5.js"></script>
<script type="text/javascript" src="../resource/js/jquery/ui/ui.core.js"></script>
<script type="text/javascript" src="../resource/js/jquery/ui/ui.tabs.js"></script>
<script type="text/javascript" src="../resource/js/jquery/ui/ui.draggable.js"></script>
<script type="text/javascript" src="../resource/js/jquery/ui/ui.resizable.js"></script>
<script type="text/javascript" src="../resource/js/jquery/ui/ui.dialog.js"></script>
<script type="text/javascript" src="../resource/js/jquery/plugin/jquery.bgiframe.js"></script>
<script type="text/javascript" src="../resource/js/jquery/jquery.inputer.js"></script>
<!-- jQuery end -->
<!-- DWR start -->
<script type="text/javascript" src="../dwr/engine.js"></script>
<script type="text/javascript" src="../dwr/util.js"></script>
<!-- DWR end -->
<script type="text/javascript" src="../dwr/interface/pbManage.js"></script>
<script type="text/javascript" src="../resource/js/configuration/BaseManager.js"></script>
<script type="text/javascript" src="../resource/js/profile/pbQualify.js"></script>
</head>
<body>
<s:hidden id="pbId" name="pbId" />
<table id="table_pbQualify" cellspacing="1" cellspacing="0" width="80%" border="0" class="tablesorter">
	<thead>
		<tr align="center">
			<th width="20%" nowrap="nowrap">�ʸ�����</th>
			<th width="65%" nowrap="nowrap">�ʸ�����</th>
		</tr>
	</thead>
	<tbody id="tbody_pbQualify">
		<s:iterator value="pbQualifyList">
			<tr id='<s:property value="id"/>' align="center" >
				<td align="center"><s:property value="ouqName" /></td>
				<td align="center"><s:property value="ouqDesc" /></td>
			</tr>
		</s:iterator>
	</tbody>
</table>
<div class="btnlink">
	<a id="link_add_pbQualify" style="cursor:pointer">����</a>
	<a id="link_delete_pbQualify" style="cursor:pointer">ɾ��</a>
	<a id="link_update_pbQualify" style="cursor:pointer">�޸�</a>
	<a id="link_sort_pbQualify" style="cursor:pointer">��������</a>
</div>
<br>
<div id="dialog_pbQualify" title="��ְ�ʸ�����" >
	<table cellpadding="0" cellspacing="1" width="100%">
		<tr>
			<td>�ʸ�����<font color="red">*</font>��</td>
			<td class="errorMessage">
				<input id="ouqName" size="38" maxlength="32">
				<label id="label_ouqName">&nbsp;</label>
			</td>
		</tr>
		<tr>
			<td>�ʸ�����<font color="red">*</font>��</td>
			<td class="errorMessage">
			    <textarea rows="3" cols="35" id="ouqDesc" onkeypress="HRMCommon.MKeyTextLength(this,64);"></textarea>
			</td>
		</tr>
		
		<tr height="35">
			<td>&nbsp;</td>
			<td colspan="2">
				<input id="btn_pbQualify_update" type="button" value="����">
				<input id="btn_pbQualify_create" type="button" value="����">
				<input id="btn_pbQualify_close" type="button" value="�ر�">
				<input id="pbQualifyId" type="hidden" />
			</td>
		</tr>
	</table>
</div>

<script type="text/javascript">
    var config={
        tableId     :"table_pbQualify",
        tbodyId     :"tbody_pbQualify",
        dialogId    :"dialog_pbQualify",
        updateButton:"btn_pbQualify_update",
        addButton   :"btn_pbQualify_create",
        dialogHeight:230,
        dialogWidth :400,
        addLink     :"link_add_pbQualify",
        deleteLink  :"link_delete_pbQualify",
        updateLink  :"link_update_pbQualify",
        sortLink    :"link_sort_pbQualify",
        closeButton :"btn_pbQualify_close",
        modal       :true
    };
    pbQualifyManager = new PbQualifyManager(config);
</script>
</body>
</html>