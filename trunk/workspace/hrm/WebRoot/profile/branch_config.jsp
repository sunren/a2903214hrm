<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib prefix="ww" uri="webwork"%>
<%  response.setHeader("Pragma","No-cache"); 
	response.setHeader("Cache-Control","no-cache"); 
	response.setDateHeader("Expires", 0); 
%> 
<html>
	<head>
		<title>组织结构管理</title>
		<script type="text/javascript" src="../resource/js/profile/branch_config.js"></script>
	</head>
<body>
<span class="errorMessage" id="msg"></span>
<table id="table_branch" cellpadding="0" cellspacing="1" width="85%" class="tablesorter">
    <thead>
        <tr align="center">
            <th width="10%">公司名称</th>
            <th width="50%">描述</th>
            <th width="10%" >公司总经理</th>
            <th width="10%" >状态</th>
        </tr>
    </thead>
    <tbody id="tbody_branch">
        <ww:iterator value="allBranch">
            <tr id='<ww:property value="id"/>' branchStatus="<ww:property value='branchStatus'/>" headNo="<ww:property value='headNo'/>" align="center" >
                <td><ww:property value="branchName" /></td>
                <td><ww:property value="branchDesc" /></td>
                <td nowrap="nowrap"><ww:property value="headName" />
	             </td>
              	<td><ww:if test="branchStatus==0">停用</ww:if><ww:else>启用</ww:else>
              		<input type="hidden" value="<ww:property value='branchStatus'/>" />
              	</td>
            </tr>
        </ww:iterator>
    </tbody>
</table>
<div class="btnlink">
    <a href="#" id="link_add_branch">新增</a>
	<a href="#" id="link_delete_branch">删除</a>
	<a href="#" id="link_update_branch">修改</a>
	<a href="#" id="link_sort_branch">保存排序</a>
</div>
<div id="dialog_branch" title="公司设置">  
<form id="tabPage7Form" method="post">
    <table>
        <tr>
            <td>公司名称<font color="red">*</font>：
            </td>
            <td class="errorMessage">
                <input id="branchName"  size="32" maxlength="64"><label id="label_branchName">&nbsp;</label>
            </td>
        </tr>
        <tr>
            <td>公司描述：</td>
            <td class="errorMessage">
                <textarea id="branchDesc" cols="30" rows="4"></textarea>
            </td>
        </tr>
        <tr>
            <td>公司总经理：</td>
            <td>
            <ww:textfield name="managerName" id="empName" readonly="true"></ww:textfield>                             
             <img src="../resource/images/search_icon.gif" style="CURSOR: pointer" 
             onclick='showChooseEmpOrManagerDiv()'>                    
            </td>                                                                                           
        </tr>
         <tr>
         	<td>状态<span class="required">*</span>：</td>
			<td><select id="branchStatus" name="branchStatus">
					<option value="1">启用</option>
					<option value="0">停用</option>
			</select>
			</td>
		</tr>         
        <tr height="35">
           <td colspan="2" align="center">
               <input id="btn_branch_create" type="button" value="保存">
			   <input id="btn_branch_update" type="button" value="保存">
			   <input id="btn_branch_close" type="button" value="关闭">
           	   <ww:hidden id="branchId"></ww:hidden>
	           <ww:hidden id="orgheadsEmpNo"></ww:hidden>
	           <ww:hidden id="branchSortId"></ww:hidden>
            </td>
        </tr>
     </table>
</form>
</div>
<script type="text/javascript">
	var config={
		tableId		:"table_branch",
		tbodyId		:"tbody_branch",
		dialogId	:"dialog_branch",
		updateButton:"btn_branch_update",
		addButton	:"btn_branch_create",
		dialogHeight:240,
		dialogWidth :400,
		addLink		:"link_add_branch",
		deleteLink	:"link_delete_branch",
		updateLink	:"link_update_branch",
		sortLink	:"link_sort_branch",
		closeButton	:"btn_branch_close",
		modal       :true
	};
	orgheadsManager = new BranchManager(config);
</script>
</body>
</html>