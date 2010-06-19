<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<%  response.setHeader("Pragma","No-cache"); 
	response.setHeader("Cache-Control","no-cache"); 
	response.setDateHeader("Expires", 0); 
%> 
<html>
<head>
	<script type='text/javascript' src='../dwr/interface/OrgDeptManageAction.js'></script>
	<script type="text/javascript" src="../resource/js/profile/OrgDeptResManager.js"></script>
</head>
<body>
<table id="table_deptres" cellpadding="0" cellspacing="1" width="45%" class="tablesorter">
    <s:hidden id="currDeptId" name="dept.id"></s:hidden>
    <thead>
        <tr align="center">
        	<th width="80%">职责名称</th>
            <th width="20%">职责权重</th>
        </tr>
    </thead>
    <tbody id="tbody_deptres">
        <s:iterator value="deptResList">
            <tr id='<s:property value="id"/>' ourDept='<s:property value="ourDept.id"/>' ourSortId='<s:property value="ourSortId"/>' align="center">
                <td><s:property value="ourName" /></td>
                <td><s:property value="ourRate" /></td>
            </tr>
        </s:iterator>
    </tbody>
</table>
<div class="btnlink">
    <a href="#" id="link_add_deptres">新增</a>
	<a href="#" id="link_delete_deptres">删除</a>
	<a href="#" id="link_update_deptres">修改</a>
	<a href="#" id="link_sort_deptres">保存排序</a>
</div>
<br>
<div id="dialog_deptres" title="部门职责设置" >  
	<form id="tabPageForm" method="post">
	    <table cellpadding="0" cellspacing="1" width="100%">
            <tr>
                <td>职责名称<font color="red">*</font>：</td>
                <td class="errorMessage">
	                <s:textfield id="ourName" name="ourName" size="35" maxlength="32"></s:textfield>
	                <label id="label_ourName">&nbsp;</label>               
                </td> 
            </tr>
            <tr>
                <td>职责权重：</td>
                <td class="errorMessage">
                	<s:textfield id="ourRate" name="ourRate" onkeydown="HRMCommon.checkPositive(event)"></s:textfield><label id="label_ourRate"></label>
                </td> 
            </tr> 
            <tr>
            	<td></td>
            	<td><font color="red">权重值为0~100的数字</font></td>
            </tr>         
            <tr>
               <td colspan="2" align="center">
                   <input id="btn_add_deptres" type="button" value="保存" />
				   <input id="btn_update_deptres" type="button" value="保存" />
				   <input id="btn_close_deptres" type="button" value="关闭" />
               </td>
              <s:hidden id="id"></s:hidden>
              <s:hidden id="ourDept"></s:hidden>
              <s:hidden id="ourSortId"></s:hidden>
            </tr>
	     </table>
	</form>
</div>
<script type="text/javascript">
	var config={
		tableId		:"table_deptres",
		tbodyId		:"tbody_deptres",
		dialogId	:"dialog_deptres",
		updateButton:"btn_update_deptres",
		addButton	:"btn_add_deptres",
		dialogHeight:140,
		dialogWidth :330,
		addLink		:"link_add_deptres",
		deleteLink	:"link_delete_deptres",
		updateLink	:"link_update_deptres",
		sortLink	:"link_sort_deptres",
		closeButton	:"btn_close_deptres"
	};
	var orgDeptResManager = new OrgDeptResManager(config);
</script>
</body>
</html>