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
<table id="table_locationmanager" cellpadding="0" cellspacing="1" width="45%" class="tablesorter">
    <thead>
        <tr align="center">
        	<th width="20%">地区</th>
            <th width="50%">地区负责人</th>
        </tr>
    </thead>
    <tbody id="tbody_locationmanager">
        <s:iterator value="locationHeads">
            <tr id='<s:property value="orgheadsId"/>' orgheadsOrgNo="<s:property value='orgheadsOrgNo'/>" orgheadsResponsibility="<s:property value='orgheadsResponsibility'/>" align="center" orgheadsEmpNo='<s:property value="orgheadsEmpNo"/>'>
                <td><s:property value="orgName" /></td>
                <td><s:property value="empName" /><s:if test="orgheadsResponsibility=='locationheader'"><font color="red">*</font></s:if></td>
            </tr>
        </s:iterator>
    </tbody>
</table>
<div class="btnlink">
    <a href="#" id="link_add_orghead_location">新增</a>
	<a href="#" id="link_delete_orghead_location">删除</a>
	<a href="#" id="link_update_orghead_location">修改</a>
	<input type="hidden" id="link_sort_orghead_location" />
</div>
<br>
<div id="dialog_locationmanager" title="地区负责人设置" >  
	<form id="deptManagerForm" method="post">
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
                <td>负责部门：</td>
                <td class="errorMessage">
                	<select id="orgheadsOrgNo">
                    	<s:iterator value="locationList">
							<option value="<s:property value="id"/>"><s:property value="locationName"/></option>
						</s:iterator>
                     </select><label id="label_orgheadsOrgNo">&nbsp;</label>
                </td> 
            </tr>          
            <tr>
               <td colspan="2" align="center">
                   <input id="btn_add_orghead_location" type="button" value="保存" />
				   <input id="btn_update_orghead_location" type="button" value="保存" />
				   <input id="btn_close_orghead_location" type="button" value="关闭" />
               </td>
              <s:hidden id="orgheadsId"></s:hidden>
              <s:hidden id="orgheadsEmpNo"></s:hidden>
              <s:hidden id="orgheadsResponsibility" value="locationdeputy"></s:hidden>
            </tr>
	     </table>
	</form>
</div>
<script type="text/javascript">
	var config={
		tableId		:"table_locationmanager",
		tbodyId		:"tbody_locationmanager",
		dialogId	:"dialog_locationmanager",
		updateButton:"btn_update_orghead_location",
		addButton	:"btn_add_orghead_location",
		dialogHeight:140,
		dialogWidth :330,
		addLink		:"link_add_orghead_location",
		deleteLink	:"link_delete_orghead_location",
		updateLink	:"link_update_orghead_location",
		sortLink	:"link_sort_orghead_location",
		closeButton	:"btn_close_orghead_location",
		orgheadsResponsibility:"locationdeputy"
	};
	locationManager = new OrgheadsManager(config);
</script>
</body>
</html>