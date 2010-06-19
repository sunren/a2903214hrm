<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<%  response.setHeader("Pragma","No-cache"); 
	response.setHeader("Cache-Control","no-cache"); 
	response.setDateHeader("Expires", 0); 
%>
<html>
<head>
<script type="text/javascript" src="../dwr/interface/pbManage.js"></script>
<script type="text/javascript" src="../resource/js/configuration/BaseManager.js"></script>
<script type="text/javascript" src="../resource/js/profile/pbPerfcr.js"></script>
<style>
.over{ background:#00BFFF}
</style>
</head>
<body>
<s:hidden id="pbId" name="pbId" />
<table id="table_pbPerfcr" cellspacing="1" cellspacing="0" width="80%" border="0" class="tablesorter">
	<thead>
		<tr align="center">
			<th width="80%" nowrap="nowrap">考核标准</th>
			<th width="20%" nowrap="nowrap">标准权重</th>
		</tr>
		
	</thead>
	<tbody id="tbody_pbPerfcr">
		<s:iterator value="pbPerfcrList">
			<tr id='<s:property value="id"/>' align="center" >
				<td align="center"><s:property value="oupName" /></td>
				<td align="center"><s:property value="oupRate" /></td>
			</tr>
		</s:iterator>
	</tbody>
</table>
<div class="btnlink">
	<a id="link_add_pbPerfcr" style="cursor:pointer">新增</a>
	<a id="link_delete_pbPerfcr" style="cursor:pointer">删除</a>
	<a id="link_update_pbPerfcr" style="cursor:pointer">修改</a>
	<a id="link_sort_pbPerfcr" style="cursor:pointer">保存排序</a>
</div>
<br>
<div id="dialog_pbPerfcr" title="添加考核标准" style="clear : both;display: none;">
	<table border="0" cellspacing="0" cellpadding="0" width="100%" >
		<tr>
			<td>考核标准<font color="red">*</font>：</td>
			<td class="errorMessage">
				<input id="oupName" size="35" maxlength="32">
				<label id="label_oupName">&nbsp;</label>
			</td>
		</tr>
		<tr>
			<td>标准权重：</td>
			<td class="errorMessage">
				<input id="oupRate" maxlength="8" onkeydown="HRMCommon.checkPositive(event)">
				<label id="label_oupRate">&nbsp;</label>
			</td>
		</tr>
		<tr>
        	<td></td>
        	<td><font color="red">权重值为0~100的数字</font></td>
        </tr>
		<tr height="35">
			<td>&nbsp;</td>
			<td colspan="2">
			    <br/>
				<input id="btn_pbPerfcr_update" type="button" value="保存">
				<input id="btn_pbPerfcr_create" type="button" value="保存">
				<input id="btn_pbPerfcr_close" type="button" value="关闭">
				<input id="pbPerfcrId" type="hidden" />
			</td>
		</tr>
	</table>
</div>


<script type="text/javascript">
	var config={
	    tableId     :"table_pbPerfcr",
	    tbodyId     :"tbody_pbPerfcr",
	    dialogId    :"dialog_pbPerfcr",
	    updateButton:"btn_pbPerfcr_update",
	    addButton   :"btn_pbPerfcr_create",
	    dialogHeight:230,
	    dialogWidth :400,
	    addLink     :"link_add_pbPerfcr",
	    deleteLink  :"link_delete_pbPerfcr",
	    updateLink  :"link_update_pbPerfcr",
	    sortLink    :"link_sort_pbPerfcr",
	    closeButton :"btn_pbPerfcr_close",
	    modal       :true
	};
    pbPerfcrManager = new PbPerfcrManager(config);
</script>
</body>
</html>