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
<script type="text/javascript" src="../resource/js/profile/pbResponse.js"></script>
</head>
<body>
<s:hidden id="pbId" name="pbId" />
<table id="table_pbResp" cellspacing="1" cellspacing="0" width="80%" border="0" class="tablesorter">
	<thead>
		<tr align="center">
			<th width="80%" nowrap="nowrap">职责名称</th>
			<th width="20%" nowrap="nowrap">职责权重</th>
		</tr>
	</thead>
	<tbody id="tbody_pbResp">
		<s:iterator value="pbRespList">
			<tr id='<s:property value="id"/>' align="center" >
				<td align="center"><s:property value="ourName" /></td>
				<td align="center"><s:property value="ourRate" /></td>
			</tr>
		</s:iterator>
	</tbody>
</table>
<div class="btnlink">
	<a id="link_add_pbResp" style="cursor:pointer">新增</a>
	<a id="link_delete_pbResp" style="cursor:pointer">删除</a>
	<a id="link_update_pbResp" style="cursor:pointer">修改</a>
	<a id="link_sort_pbResp" style="cursor:pointer">保存排序</a>
</div>
<br>
<div id="dialog_pbResp" title="职位职责设置" >
	<table cellpadding="0" cellspacing="1" width="100%">
		<tr>
			<td>职责名称<font color="red">*</font>：</td>
			<td class="errorMessage">
				<input id="ourName" size="35" maxlength="32">
				<label id="label_ourName">&nbsp;</label>
			</td>
		</tr>
		<tr>
			<td>职责权重<font color="red">*</font>：</td>
			<td class="errorMessage">
				<input id="ourRate" maxlength="8" onkeydown="HRMCommon.checkPositive(event)">
				<label id="label_ourRate">&nbsp;</label>
			</td>
		</tr>
		<tr>
        	<td></td>
        	<td><font color="red">权重值为0~100的数字</font></td>
        </tr>
		<tr height="35">
			<td>&nbsp;</td>
			<td colspan="2">
				<input id="btn_pbResp_update" type="button" value="保存">
				<input id="btn_pbResp_create" type="button" value="保存">
				<input id="btn_pbResp_close" type="button" value="关闭">
				<input id="pbRespId" type="hidden" />
			</td>
		</tr>
	</table>
</div>

<script type="text/javascript">
    var config={
        tableId     :"table_pbResp",
        tbodyId     :"tbody_pbResp",
        dialogId    :"dialog_pbResp",
        updateButton:"btn_pbResp_update",
        addButton   :"btn_pbResp_create",
        dialogHeight:230,
        dialogWidth :400,
        addLink     :"link_add_pbResp",
        deleteLink  :"link_delete_pbResp",
        updateLink  :"link_update_pbResp",
        sortLink    :"link_sort_pbResp",
        closeButton :"btn_pbResp_close",
        modal       :true
    };
    pbRespManager = new PbRespManager(config);
</script>
</body>
</html>