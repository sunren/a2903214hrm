<%@page pageEncoding="GBK" contentType="text/html; charset=GBK" %>
<%@taglib prefix="s" uri="webwork"%>
<%@ taglib prefix="ty" uri="/tengsource"%>
<%  response.setHeader("Pragma","No-cache"); 
	response.setHeader("Cache-Control","no-cache"); 
	response.setDateHeader("Expires", 0); 
%> 
<html>
<head>
	<title>公司信息种类</title>
	<script type="text/javascript" src="../dwr/interface/my.js"></script>			
	<script type="text/javascript" src="../resource/js/configuration/BaseManager.js"></script>
	<script type="text/javascript" src="NewsInfo.js"></script>
	<script type="text/javascript">
		$(document).ready(function() { 
			var config={
				tableId		:"table_infoclass",
				tbodyId		:"tbody_infoclass",
				dialogId	:"dialog_infoclass",
				updateButton:"btn_update_infoclass",
				addButton	:"btn_add_infoclass",
				dialogHeight:150,
				dialogWidth :300,
				addLink		:"link_add_infoclass",
				deleteLink	:"link_delete_infoclass",
				updateLink	:"link_update_infoclass",
				sortLink	:"link_sort_infoclass",
				closeButton	:"btn_close_infoclass"
			};
			infoclassManager = new InfoclassManager(config);
		});	
	</script>
   </head>
<body>
<s:component template="bodyhead">
	<s:param name="pagetitle" value="'公司信息种类'" />
	<s:param name="helpUrl" value="'10'" />
</s:component>
<table id="table_infoclass" cellpadding="0" cellspacing="1" width="85%"class="tablesorter">
	<thead>
		<tr>
			<th nowrap="nowrap">名称</th>
			<th width="8%">状态	</th>
		</tr>
	</thead>
	<tbody id="tbody_infoclass">
		<s:iterator value="allInfo">
			<tr id="<s:property value="id"/>" infoclassStatus="<s:property value='infoclassStatus'/>">
				<td><s:property value="infoclassName" /></td>
				<td align="center">
					<s:if test="infoclassStatus==1">
					 正常
					</s:if><s:else>
					 关闭
					</s:else>
				</td>
			</tr>
		</s:iterator>
	</tbody>
</table>
<div class="btnlink">
	<a href="#" id="link_add_infoclass">新增</a>
	<a href="#" id="link_delete_infoclass">删除</a>
	<a href="#" id="link_update_infoclass">修改</a>
	<a href="#" id="link_sort_infoclass">保存排序</a>
</div>
<div id="dialog_infoclass" title="公司信息种类设置">				
	<form id="infoclassForm">
		<table cellpadding="0" cellspacing="1" width="100%" class="prompt_div_body">															
			<tr>
				<td>名称<font color="red">*</font>：</td>								
				<td class="errorMessage">
					<input id="infoclassName" type="text" size="16" maxlength="16" />
					<label id="label_infoclassName">&nbsp;</label>
				</td>															
			</tr>																				
			<tr>
				<td>状态<font color="red">*</font>：</td>
				<td class="errorMessage">
					<select id="infoclassStatus">
						<option value="1">正常</option>
						<option value="0">关闭</option>
					</select>
				</td>								
			</tr>									
			<tr>
				<td align="center" colspan="2">
					<input id="btn_add_infoclass" type="button" value="保存" />
					<input id="btn_update_infoclass" type="button" value="修改" />
					<input id="btn_close_infoclass" type="button" value="关闭" />
					<input type="hidden" id="infoclassId" />
					<input type="hidden" id="infoclassSortId" />
				</td>
			</tr>							
		</table>
	</form>
</div>
</body>
</html>