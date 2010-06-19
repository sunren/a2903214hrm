<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<%  response.setHeader("Pragma","No-cache"); 
	response.setHeader("Cache-Control","no-cache"); 
	response.setDateHeader("Expires", 0); 
%> 
<html>
<head>
<script type="text/javascript" src="../resource/js/configuration/staffCustom.js"></script>
<style type="text/css">
	input[disabled]{background-color: #D4D0C8;}
</style>
</head>
<body>
<table id="table_employee_additonal" cellpadding="0" cellspacing="1" width="95%" class="tablesorter">
	<thead>
		<tr align="center">
			<th width="5%" nowrap="nowrap">字段编号</th>
			<th width="25%" nowrap="nowrap">字段名称</th>
			<th width="10%" nowrap="nowrap">字段类型</th>
			<th width="30%" nowrap="nowrap">字段参数</th>
			<th width="30%">备注</th>
		</tr>
	</thead>
	<tbody id="tbody_employee_additonal">
		<s:iterator value="allEmpaddconf">
			<tr id='<s:property value="eadcId"/>' align="center" eadcFieldType="<s:property value='eadcFieldType'/>">
				<td align="center"><s:property value="'字段'+(eadcSeq>9?eadcSeq:'0'+eadcSeq)" /></td>
				<td align="center"><s:property value="eadcFieldName" /></td>
				<td align="center">
					<s:if test="eadcFieldType=='number'">数值</s:if>
					<s:elseif test="eadcFieldType=='textarea'">多行文本</s:elseif>
					<s:elseif test="eadcFieldType=='date'">日期</s:elseif>
					<s:elseif test="eadcFieldType=='select'">下拉框</s:elseif>
					<s:else>普通文本</s:else>
				</td>
				<td align="center"><s:property value="eadcFieldValue" /></td>
				<td><s:property value="eadcComments" /></td>
			</tr>
		</s:iterator>
	</tbody>
</table>
<div class="btnlink">
	<a href="#" id="link_add_employee_additional">新增</a>
	<a href="#" id="link_delete_employee_additional">删除</a>
	<a href="#" id="link_update_employee_additional">修改</a>
	<input type="hidden" id="link_sort_employee_additonal"></input><!-- 隐藏批量排序 -->
</div>
<br>
<div id="dialog_employee_additional" title="用户自定义字段设置">
	<form id="staffCustomForm" method="post">
		<table cellpadding="0" cellspacing="1" width="100%">
			<tr align="left">
			   <td nowrap="nowrap">字段编号<font color="red">*</font>：</td>
			   <td><s:select list="fieldList" id="eadcSeq" ></s:select></td>
			</tr>
			<tr>
				<td>字段名称<font color="red">*</font>：</td>
			    <td class="errorMessage"><input type="text" id="eadcFieldName" /><label id="label_eadcFieldName">&nbsp;</label></td>
			</tr>
			<tr>
			    <td nowrap="nowrap">字段类型:</td>
			    <td class="errorMessage" nowrap="nowrap">
					<s:select  id="eadcFieldType"
						list="#{'input':'普通文本','textarea':'多行文本','number':'数值','date':'日期','select':'下拉框'}" ></s:select>
			   		<label id="label_eadcFieldType">在字段值中若要输入多个选项,之间用,号分隔</label>
			   </td>
			</tr>
			<tr>
				<td>字段参数:</td>
				<td class="errorMessage" nowrap="nowrap"><input type="text" id="eadcFieldValue" size="24" disabled="disabled"/><label id="label_eadcFieldValue">字段类型为下拉框时,该项必填</label></td>
			</tr>
			<tr>
				<td>备 注:</td>
				<td><textarea id="eadcComments" cols="30" rows="3"></textarea></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input id="btn_add_employee_additional" type="button" value="保存" />
					<input id="btn_update_employee_additional" type="button" value="保存" />
					<input type="reset" value="清空" />
					<input id="btn_close_employee_additional" type="button" value="关闭" />
					<input type="hidden" id="eadcTableName" value="empadditional" />
					<input type="hidden" id="eadcId" />
				</td>
			</tr>
		</table>
	</form>
</div>
<script type="text/javascript">
	var config={
		tableId		:"table_employee_additonal",
		tbodyId		:"tbody_employee_additonal",
		dialogId	:"dialog_employee_additional",
		updateButton:"btn_update_employee_additional",
		addButton	:"btn_add_employee_additional",
		dialogHeight:240,
		dialogWidth :400,
		addLink		:"link_add_employee_additional",
		deleteLink	:"link_delete_employee_additional",
		updateLink	:"link_update_employee_additional",
		sortLink	:"link_sort_employee_additonal",//无排序功能
		closeButton	:"btn_close_employee_additional",
		seqList		:<s:property value="fieldList" />
	};
	empaddconf = new EmpaddconfManager(config);
</script>
</body>
</html>