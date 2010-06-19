<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<%  response.setHeader("Pragma","No-cache"); 
	response.setHeader("Cache-Control","no-cache"); 
	response.setDateHeader("Expires", 0); 
%> 
<html>
<head>
	<script type='text/javascript' src='../dwr/interface/AuthorityPosAction.js'></script>
	<script type="text/javascript" src="../resource/js/examin/authorityPos_show.js"></script>
</head>
<body>
<table id="table_authorityPos" cellpadding="0" cellspacing="1" width="45%" class="tablesorter">
    <thead>
        <tr align="center">
        	<th width="20%">ְλ����</th>
            <th width="50%">��������</th>
            <th width="20%">��������</th>
        </tr>
    </thead>
    <tbody id="tbody_authorityPos">
        <s:iterator value="authList">
            <tr id="<s:property value='id'/>" deptId="<s:property value='apDept.id'/>" locId="<s:property value='apLoc.id'/>" align="center">
                <td><s:property value="apPos.positionPbId.pbName" /></td>
                <td><s:property value="apDept.departmentName" /></td>
                <td><s:property value="apLoc.locationName" /></td>
            </tr>
        </s:iterator>
    </tbody>
</table>
<div class="btnlink">
    <a href="#" id="link_add_authorityPos">����</a>
	<a href="#" id="link_delete_authorityPos">ɾ��</a>
	<a href="#" id="link_update_authorityPos">�޸�</a>
</div>
<br>
<div id="dlgAuthorityPos" title="�����޶Χ����" height="257px" >  
	<form id="tabPageForm" method="post">
	    <table cellpadding="0" cellspacing="1" width="100%">
            <tr>
                <td align="right">ְλ����:</td>
                <td>
	                <span id="posName"><s:property value="positionName"/></span>              
                </td> 
            </tr>
            <tr>
            	<td align="right">��Χ����:</td>
	            <td>
	            <s:select id="apType" list="#{0:'����',1:'����'}" onchange="change(this);"></s:select>
	            </td>
            </tr>
            <tr id="deptTR" style="display:none">
                <td align="right">��������:</td>
                <td>
                	<s:orgselector id="orgselector1" name="emp.empDeptNo.departmentName" hiddenFieldName="emp.empDeptNo.id"/><label id="label_orgselector1" style="color:red;">&nbsp;</label>
                </td>
            </tr>
            <tr id="locTR" style="display:none">
                <td align="right">��������:</td>
                <td>
                	<s:select id="loc" name="loc" list="locationList" listKey="id" listValue="locationName" multiple="false" emptyOption="true" value="loc" size="1" /><label id="label_loc" style="color:red;">&nbsp;</label>
                </td> 
            </tr>         
            <tr>
               <td colspan="2" align="center">
                   <input id="btn_add_authorityPos" type="button" value="����" />
				   <input id="btn_update_authorityPos" type="button" value="����" />
				   <input id="btn_close_authorityPos" type="button" value="�ر�" />
               </td>
              <s:hidden id="apId"></s:hidden>
              <s:hidden id="positionId" name="positionId"></s:hidden>
              <s:hidden id="deptId"></s:hidden>
              <s:hidden id="locId"></s:hidden>
            </tr>
	     </table>
	</form>
</div>
<script type="text/javascript">

	var config={
		tableId		:"table_authorityPos",
		tbodyId		:"tbody_authorityPos",
		dialogId	:"dlgAuthorityPos",
		updateButton:"btn_update_authorityPos",
		addButton	:"btn_add_authorityPos",
		dialogHeight:400,
		dialogWidth :330,
		addLink		:"link_add_authorityPos",
		deleteLink	:"link_delete_authorityPos",
		updateLink	:"link_update_authorityPos",
		sortLink	:"link_sort_authorityPos",
		closeButton	:"btn_close_authorityPos"
	};
	var AuthorityPosManager = new AuthorityPosManager(config);

	AuthorityPosManager._initDialog();

	function change(obj){
		document.getElementById("deptTR").style.display="none";
		document.getElementById("locTR").style.display="none";
		if(obj.value==0){
			document.getElementById("deptTR").style.display="";
		}else{
			document.getElementById("locTR").style.display="";
		}
	}
</script>
</body>
</html>