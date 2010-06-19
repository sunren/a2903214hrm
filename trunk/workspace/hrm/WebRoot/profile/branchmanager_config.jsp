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
<table id="table_branchmanager" cellpadding="0" cellspacing="1" width="45%" class="tablesorter">
    <thead>
        <tr align="center">
        	<th width="20%">公司</th>
            <th width="50%">公司负责人</th>
        </tr>
    </thead>
    <tbody id="tbody_branchmanager">
        <s:iterator value="branchHeads">
            <tr id='<s:property value="orgheadsId"/>' orgheadsOrgNo="<s:property value='orgheadsOrgNo'/>" orgheadsResponsibility="<s:property value='orgheadsResponsibility'/>" align="center" orgheadsEmpNo='<s:property value="orgheadsEmpNo"/>'>
                <td><s:property value="orgName" /></td>
                <td><s:property value="empName" /><s:if test="orgheadsResponsibility=='braheader'"><font color="red">*</font></s:if></td>
            </tr>
        </s:iterator>
    </tbody>
</table>
<div class="btnlink">
    <a href="#" id="link_add_orghead">新增</a>
	<a href="#" id="link_delete_orghead">删除</a>
	<a href="#" id="link_update_orghead">修改</a>
	<input type="hidden" id="link_sort_orghead" />
</div>
<br>
<div id="dialog_branchmanager" title="公司总经理设置" >  
	<form id="tabPageForm" method="post">
	    <table cellpadding="0" cellspacing="1" width="100%">
            <tr>
                <td>负责人<font color="red">*</font>：</td>
                <td class="errorMessage">
	                <s:textfield id="empName" readonly="true"></s:textfield><label id="label_orgheadsEmpNo">&nbsp;</label>                             
	                <img src="../resource/images/search_icon.gif" style="CURSOR: pointer" 
	                 onclick='showChooseEmpOrManagerDiv()'>                    
                </td> 
            </tr>
            <tr>
                <td>负责公司<font color="red">*</font>：</td>
                <td class="errorMessage">
                	<select id="orgheadsOrgNo">
                    	<s:iterator value="branchList">
                        	<option value="<s:property value="id"/>"><s:property value="branchName"/></option>
                        </s:iterator>
                     </select><label id="label_orgheadsOrgNo">&nbsp;</label>
                </td> 
            </tr>          
            <tr>
               <td colspan="2" align="center">
                   <input id="btn_add_orghead" type="button" value="保存" />
				   <input id="btn_update_orghead" type="button" value="保存" />
				   <input id="btn_close_orghead" type="button" value="关闭" />
               </td>
              <s:hidden id="orgheadsId"></s:hidden>
              <s:hidden id="orgheadsEmpNo"></s:hidden>
              <s:hidden id="orgheadsResponsibility" value="bradeputy"></s:hidden>
            </tr>
	     </table>
	</form>
</div>
<script type="text/javascript">
	var config={
		tableId		:"table_branchmanager",
		tbodyId		:"tbody_branchmanager",
		dialogId	:"dialog_branchmanager",
		updateButton:"btn_update_orghead",
		addButton	:"btn_add_orghead",
		dialogHeight:140,
		dialogWidth :330,
		addLink		:"link_add_orghead",
		deleteLink	:"link_delete_orghead",
		updateLink	:"link_update_orghead",
		sortLink	:"link_sort_orghead",
		closeButton	:"btn_close_orghead",
		orgheadsResponsibility:"bradeputy"
	};
	orgheadsManager = new OrgheadsManager(config);
</script>
</body>
</html>