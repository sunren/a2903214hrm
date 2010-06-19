<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<%  response.setHeader("Pragma","No-cache"); 
	response.setHeader("Cache-Control","no-cache"); 
	response.setDateHeader("Expires", 0); 
%> 
<html>
<head>
	<script type='text/javascript' src='../dwr/interface/OrgDeptManageAction.js'></script>
	<script type="text/javascript" src="../resource/js/profile/OrgDeptPerfcrManager.js"></script>
</head>
<body>
<table id="table_deptperfcr" cellpadding="0" cellspacing="1" width="45%" class="tablesorter">
    <s:hidden id="currDeptId" name="dept.id"></s:hidden>
    <thead>
        <tr align="center">
        	<th width="80%" nowrap="nowrap">���˱�׼</th>
			<th width="20%" nowrap="nowrap">��׼Ȩ��</th>
        </tr>
    </thead>
    <tbody id="tbody_deptperfcr">
        <s:iterator value="deptPerfcrList">
            <tr id='<s:property value="id"/>' oupDept='<s:property value="oupDept.id"/>' oupSortId='<s:property value="oupSortId"/>' align="center">
                <td align="center"><s:property value="oupName" /></td>
				<td align="center"><s:property value="oupRate" /></td>
            </tr>
        </s:iterator>
    </tbody>
</table>
<div class="btnlink">
    <a href="#" id="link_add_deptperfcr">����</a>
	<a href="#" id="link_delete_deptperfcr">ɾ��</a>
	<a href="#" id="link_update_deptperfcr">�޸�</a>
	<a href="#" id="link_sort_deptperfcr">��������</a>
</div>
<br>
<div id="dialog_deptperfcr" title="���˱�׼����" >  
	<form id="tabPageForm" method="post">
	    <table cellpadding="0" cellspacing="1" width="100%">
            <tr>
                <td>���˱�׼<font color="red">*</font>��</td>
                <td class="errorMessage">
	                <input id="oupName" size="35" maxlength="32">
	                <label id="label_oupName">&nbsp;</label>               
                </td> 
            </tr>
            <tr>
                <td>��׼Ȩ�أ�</td>
                <td class="errorMessage">
                	<input id="oupRate" maxlength="8" onkeydown="HRMCommon.checkPositive(event)"/>
                	<label id="label_oupRate">&nbsp;</label>
                </td> 
            </tr>
            <tr>
            	<td></td>
            	<td><font color="red">Ȩ��ֵΪ0~100������</font></td>
            </tr>       
            <tr>
               <td colspan="2" align="center">
                   <input id="btn_add_deptperfcr" type="button" value="����" />
				   <input id="btn_update_deptperfcr" type="button" value="����" />
				   <input id="btn_close_deptperfcr" type="button" value="�ر�" />
               </td>
              <s:hidden id="id"></s:hidden>
              <s:hidden id="oupDept"></s:hidden>
              <s:hidden id="oupSortId"></s:hidden>
            </tr>
	     </table>
	</form>
</div>
<script type="text/javascript">
	var config={
		tableId		:"table_deptperfcr",
		tbodyId		:"tbody_deptperfcr",
		dialogId	:"dialog_deptperfcr",
		updateButton:"btn_update_deptperfcr",
		addButton	:"btn_add_deptperfcr",
		dialogHeight:170,
		dialogWidth :350,
		addLink		:"link_add_deptperfcr",
		deleteLink	:"link_delete_deptperfcr",
		updateLink	:"link_update_deptperfcr",
		sortLink	:"link_sort_deptperfcr",
		closeButton	:"btn_close_deptperfcr"
	};
	var orgDeptPerfcrManager = new OrgDeptPerfcrManager(config);
</script>
</body>
</html>