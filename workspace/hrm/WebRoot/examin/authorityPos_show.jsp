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
        	<th width="20%">职位名称</th>
            <th width="50%">部门名称</th>
            <th width="20%">地区名称</th>
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
    <a href="#" id="link_add_authorityPos">新增</a>
	<a href="#" id="link_delete_authorityPos">删除</a>
	<a href="#" id="link_update_authorityPos">修改</a>
</div>
<br>
<div id="dlgAuthorityPos" title="审批限额范围设置" height="257px" >  
	<form id="tabPageForm" method="post">
	    <table cellpadding="0" cellspacing="1" width="100%">
            <tr>
                <td align="right">职位名称:</td>
                <td>
	                <span id="posName"><s:property value="positionName"/></span>              
                </td> 
            </tr>
            <tr>
            	<td align="right">范围类型:</td>
	            <td>
	            <s:select id="apType" list="#{0:'部门',1:'地区'}" onchange="change(this);"></s:select>
	            </td>
            </tr>
            <tr id="deptTR" style="display:none">
                <td align="right">部门名称:</td>
                <td>
                	<s:orgselector id="orgselector1" name="emp.empDeptNo.departmentName" hiddenFieldName="emp.empDeptNo.id"/><label id="label_orgselector1" style="color:red;">&nbsp;</label>
                </td>
            </tr>
            <tr id="locTR" style="display:none">
                <td align="right">地区名称:</td>
                <td>
                	<s:select id="loc" name="loc" list="locationList" listKey="id" listValue="locationName" multiple="false" emptyOption="true" value="loc" size="1" /><label id="label_loc" style="color:red;">&nbsp;</label>
                </td> 
            </tr>         
            <tr>
               <td colspan="2" align="center">
                   <input id="btn_add_authorityPos" type="button" value="保存" />
				   <input id="btn_update_authorityPos" type="button" value="保存" />
				   <input id="btn_close_authorityPos" type="button" value="关闭" />
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