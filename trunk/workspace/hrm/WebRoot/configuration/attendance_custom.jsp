<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<%  response.setHeader("Pragma","No-cache"); 
	response.setHeader("Cache-Control","no-cache"); 
	response.setDateHeader("Expires", 0); 
%> 
<html>
<head>
	<script type="text/javascript" src="../resource/js/configuration/attendCustom.js"></script>
</head>
<body>
<table id="table_examin_additional" cellpadding="0" cellspacing="1" width="85%" class="tablesorter">
	<thead>
		<tr align="center">
			<th width="5%" nowrap="nowrap">字段编号</th>
			<th width="10%" nowrap="nowrap">字段名称</th>
			<th width="30%" nowrap="nowrap">备注	</th>
		</tr>
	</thead>
	<tbody id="tbody_examin_additional">
		<s:iterator value="allAttdaddconf">
			<tr id='<s:property value="eadcId"/>' align="center" eadcFieldType='<s:property value="eadcFieldType"/>'>
				<td align="center"><s:property value="'字段'+(eadcSeq>9?eadcSeq:'0'+eadcSeq)" /></td>
				<td align="center"><s:property value="eadcFieldName" /></td>
				<td><s:property value="eadcComments"/></td>
			</tr>
		</s:iterator>
	</tbody>
</table>
<div class="btnlink">
	<a href="#" id="link_add_examin_additional">新增</a>
	<a href="#" id="link_delete_examin_additional">删除</a>
	<a href="#" id="link_update_examin_additional">修改</a>
	<input type="hidden" id="link_sort_examin_additional" />
</div>
<br>
<div id="dialog_examin_additional" title="考勤自定义字段设置">
	<form id="attendCustomForm" method="post">
		<table cellpadding="0" cellspacing="1" width="100%" >
			<tr align="left">
				<td>字段编号<font color="red">*</font>：</td>
				<td>
					<s:select list="attdFreeList"  id="eadcSeq"
						required="true" emptyOption="false"></s:select><label id="label_eadcSeq">&nbsp;</label>
				</td>
			</tr>
			<tr>
				<td>字段名称<font color="red">*</font>：</td>
				<td class="errorMessage"><input type="text" id="eadcFieldName" /><label id="label_eadcFieldName">&nbsp;</label></td>
			</tr>
			<tr>
				<td>备 注:</td>
				<td class="errorMessage"><textarea id="eadcComments" cols="30" rows="3" onkeypress="MKeyTextLength(this,128);"></textarea></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input id="btn_add_examin_additional" type="button" value="保存" />
					<input id="btn_update_examin_additional" type="button" value="保存" />
					<input type="reset" value="清空" />
					<input id="btn_close_examin_additional" type="button" value="关闭" />
					<input type="hidden" id="eadcTableName" value="attend" />
					<input type="hidden" id="eadcId" />
					<input type="hidden" id="eadcFieldType" value="number"/>
				</td>
			</tr>
		</table>
	</form>
</div>
<script type="text/javascript">
	var config={
		tableId		:"table_examin_additional",
		tbodyId		:"tbody_examin_additional",
		dialogId	:"dialog_examin_additional",
		updateButton:"btn_update_examin_additional",
		addButton	:"btn_add_examin_additional",
		dialogHeight:200,
		addLink		:"link_add_examin_additional",
		deleteLink	:"link_delete_examin_additional",
		updateLink	:"link_update_examin_additional",
		sortLink	:"link_sort_examin_additional",//无排序功能
		closeButton	:"btn_close_examin_additional",
		seqList		:<s:property value="attdFreeList" />
	};
	attendconf = new AttendConfManager(config);
</script>
</body>
</html>