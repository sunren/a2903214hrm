<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<%  response.setHeader("Pragma","No-cache"); 
	response.setHeader("Cache-Control","no-cache"); 
	response.setDateHeader("Expires", 0); 
%> 
<html>
<head>
	<script type="text/javascript" src="../resource/js/profile/OrgHeadsManager.js"></script>
</head>
<body>
<table id="table_groupmanager" cellpadding="0" cellspacing="1" width="45%" class="tablesorter">
    <thead>
        <tr align="center">
        	<th width="20%">����С��</th>
            <th width="50%">����С�鸺����</th>
        </tr>
    </thead>
    <tbody id="tbody_groupmanager">
        <s:iterator value="groupHeads">
            <tr id='<s:property value="orgheadsId"/>' orgheadsOrgNo="<s:property value='orgheadsOrgNo'/>" orgheadsResponsibility="<s:property value='orgheadsResponsibility'/>" align="center" orgheadsEmpNo='<s:property value="orgheadsEmpNo"/>'>
                <td><s:property value="orgName" /></td>
                <td><s:property value="empName" /><s:if test="orgheadsResponsibility=='grpheader'"><font color="red">*</font></s:if></td>
            </tr>
        </s:iterator>
    </tbody>
</table>
<div class="btnlink">
    <a href="#" id="link_add_orghead_group">����</a>
	<a href="#" id="link_delete_orghead_group">ɾ��</a>
	<a href="#" id="link_update_orghead_group">�޸�</a>
	<input type="hidden" id="link_sort_orghead_group" />
</div>
<br>
<div id="dialog_groupmanager" title="С���鳤����" >  
	<form id="buManagerForm" method="post">
	    <table cellpadding="0" cellspacing="1" width="100%">
            <tr>
                <td>������<font color="red">*</font>��</td>
                <td class="errorMessage">
	                <s:textfield id="empName" readonly="true"></s:textfield><label id="label_orgheadsEmpNo">&nbsp;</label>                             
	                <img src="../resource/images/search_icon.gif" style="CURSOR: pointer" 
	                 onclick='showChooseEmpOrManagerDiv()'>                    
                </td> 
            </tr>
            <tr>
                <td>������С�飺</td>
                <td class="errorMessage">
                	<select id="orgheadsOrgNo">
                    	<s:iterator value="grouplist">
							<option value="<s:property value="groupNo"/>"><s:property value="groupName"/></option>
						</s:iterator>
                     </select><label id="label_orgheadsOrgNo">&nbsp;</label>
                </td> 
            </tr>          
            <tr>
               <td colspan="2" align="center">
                   <input id="btn_add_orghead_group" type="button" value="����" />
				   <input id="btn_update_orghead_group" type="button" value="����" />
				   <input id="btn_close_orghead_group" type="button" value="�ر�" />
               </td>
              <s:hidden id="orgheadsId"></s:hidden>
              <s:hidden id="orgheadsEmpNo"></s:hidden>
              <s:hidden id="orgheadsResponsibility" value="grpdeputy"></s:hidden>
            </tr>
	     </table>
	</form>
</div>
<script type="text/javascript">
	var config={
		tableId		:"table_groupmanager",
		tbodyId		:"tbody_groupmanager",
		dialogId	:"dialog_groupmanager",
		updateButton:"btn_update_orghead_group",
		addButton	:"btn_add_orghead_group",
		dialogHeight:140,
		dialogWidth :330,
		addLink		:"link_add_orghead_group",
		deleteLink	:"link_delete_orghead_group",
		updateLink	:"link_update_orghead_group",
		sortLink	:"link_sort_orghead_group",
		closeButton	:"btn_close_orghead_group",
		orgheadsResponsibility:"grpdeputy"
	};
	groupManager = new OrgheadsManager(config);
</script>
</body>
</html>