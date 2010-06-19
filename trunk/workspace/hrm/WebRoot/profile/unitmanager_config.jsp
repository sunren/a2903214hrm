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
<table id="table_businessunitmanager" cellpadding="0" cellspacing="1" width="45%" class="tablesorter">
    <thead>
        <tr align="center">
        	<th width="20%">业务单元</th>
            <th width="50%">业务单元负责人</th>
        </tr>
    </thead>
    <tbody id="tbody_businessunitmanager">
        <s:iterator value="buHeads">
            <tr id='<s:property value="orgheadsId"/>' orgheadsOrgNo="<s:property value='orgheadsOrgNo'/>" orgheadsResponsibility="<s:property value='orgheadsResponsibility'/>" align="center" orgheadsEmpNo='<s:property value="orgheadsEmpNo"/>'>
                <td><s:property value="orgName" /></td>
                <td><s:property value="empName" /><s:if test="orgheadsResponsibility=='buheader'"><font color="red">*</font></s:if></td>
            </tr>
        </s:iterator>
    </tbody>
</table>
<div class="btnlink">
    <a href="#" id="link_add_orghead_bu">新增</a>
	<a href="#" id="link_delete_orghead_bu">删除</a>
	<a href="#" id="link_update_orghead_bu">修改</a>
	<input type="hidden" id="link_sort_orghead_bu" />
</div>
<br>
<div id="dialog_businessunitmanager" title="业务单元经理设置" >  
	<form id="buManagerForm" method="post">
	    <table cellpadding="0" cellspacing="1" width="100%">
            <tr>
                <td>负责人<font color="red">*</font>：</td>
                <td class="errorMessage">
	                <s:textfield id="empName" readonly="true"></s:textfield><label id="label_orgheadsEmpNo">&nbsp;</label>                             
	                <img src="../resource/images/search_icon.gif" style="CURSOR: pointer" 
	                 onclick='showChooseEmpDiv(1,1)'>                    
                </td> 
            </tr>
            <tr>
                <td>负责业务单元：</td>
                <td class="errorMessage">
                	<select id="orgheadsOrgNo">
                    	<s:iterator value="buList">
							<option value="<s:property value="id"/>"><s:property value="businessunitName"/></option>
						</s:iterator>
                     </select><label id="label_orgheadsOrgNo">&nbsp;</label>
                </td> 
            </tr>          
            <tr>
               <td colspan="2" align="center">
                   <input id="btn_add_orghead_bu" type="button" value="保存" />
				   <input id="btn_update_orghead_bu" type="button" value="保存" />
				   <input id="btn_close_orghead_bu" type="button" value="关闭" />
               </td>
              <s:hidden id="orgheadsId"></s:hidden>
              <s:hidden id="orgheadsEmpNo"></s:hidden>
              <s:hidden id="orgheadsResponsibility" value="budeputy"></s:hidden>
            </tr>
	     </table>
	</form>
</div>
<script type="text/javascript">
	var config={
		tableId		:"table_businessunitmanager",
		tbodyId		:"tbody_businessunitmanager",
		dialogId	:"dialog_businessunitmanager",
		updateButton:"btn_update_orghead_bu",
		addButton	:"btn_add_orghead_bu",
		dialogHeight:140,
		dialogWidth :330,
		addLink		:"link_add_orghead_bu",
		deleteLink	:"link_delete_orghead_bu",
		updateLink	:"link_update_orghead_bu",
		sortLink	:"link_sort_orghead_bu",
		closeButton	:"btn_close_orghead_bu",
		orgheadsResponsibility:"budeputy"
	};
	buManager = new OrgheadsManager(config);
</script>
</body>
</html>