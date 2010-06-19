<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib prefix="jscalendar" uri="/jscalendar" %>
<%@ taglib prefix="s" uri="webwork"%>
<%  response.setHeader("Pragma","No-cache"); 
	response.setHeader("Cache-Control","no-cache"); 
	response.setDateHeader("Expires", 0); 
%> 
<html>
	<head>
		<script type="text/javascript" src="../resource/js/configuration/holidayType.js"></script>
	</head>
<body>
<table id="table_leave_type" cellpadding="0" cellspacing="1" class="tablesorter">
	<thead>
		<tr align="center">
			<th nowrap="nowrap">休假种类名称</th>
			<th nowrap="nowrap">休假种类描述</th>
			<th nowrap="nowrap">休假类别</th>
			<th nowrap="nowrap">最小申请天数</th>
			<th nowrap="nowrap">最大申请天数</th>
			<th nowrap="nowrap">包含节假日</th>
			<th nowrap="nowrap">请假理由</th>
		</tr>
	</thead>
	<tbody id="tbody_leave_type">
		<s:iterator value="allLeaveType">
			<tr id='<s:property value="ltNo"/>' align="center" ltInclHoliday="<s:property value='ltInclHoliday' />">
				<td align="center"><s:property value="ltName" /></td>
				<td align="center"><s:property value="ltDesc" /></td>
				<td align="center">
					<s:if test="ltSpecialCat==0">事假</s:if>
					<s:elseif test="ltSpecialCat==1">年假</s:elseif>
					<s:elseif test="ltSpecialCat==3">病假</s:elseif>
					<s:elseif test="ltSpecialCat==4">病假住院</s:elseif>
					<s:elseif test="ltSpecialCat==5">带薪病假</s:elseif>
					<s:elseif test="ltSpecialCat==6">婚假</s:elseif>
					<s:elseif test="ltSpecialCat==7">产假</s:elseif>
					<s:elseif test="ltSpecialCat==8">出差</s:elseif>
					<s:elseif test="ltSpecialCat==9">因公外出</s:elseif>
					<s:elseif test="ltSpecialCat==10">调休(不过期)</s:elseif>
					<s:elseif test="ltSpecialCat==11">调休(过期)</s:elseif>
					<s:elseif test="ltSpecialCat==20">其他</s:elseif>
				</td>
				<td align="center"><s:property value="ltMinApplyDays" /></td>
				<td align="center"><s:property value="ltMaxApplyDays" /></td>
				<td align="center">
					<s:if test="ltInclHoliday==1">包含</s:if>
					<s:else>不包含</s:else>
				</td>
				<td align="center">
					<s:if test="ltNeedComments==0">非必填</s:if>
					<s:else>必填</s:else>
				</td>
			</tr>
		</s:iterator>
	</tbody>
</table>
<div class="btnlink">
	<a href="#" id="link_add_leave_type">新增</a>
	<a href="#" id="link_delete_leave_type">删除</a>
	<a href="#" id="link_update_leave_type">修改</a>
	<a href="#" id="link_sort_leave_type">保存排序</a>
</div>
<br>
<div id="dialog_leave_type" title="休假种类设置">
	<form id="holidayTypeForm" method="post">
		<table cellpadding="0" cellspacing="1" width="100%">
			<tr>
				<td>休假种类<font color="red">*</font>：</td>
				<td>
					<select id="ltSpecialCat">
						<option value="0">事假</option>
						<option value="1">年假</option>
						<option value="3">病假</option>
						<option value="4">病假住院</option>
						<option value="5">带薪病假</option>
						<option value="6">婚假</option>
						<option value="7">产假</option>
						<option value="8">出差</option>
						<option value="9">因公外出</option>
						<option value="10">调休(不过期)</option>
						<option value="11">调休(过期)</option>
						<option value="20">其他</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>休假种类名称<font color="red">*</font>：</td>
				<td class="errorMessage"><input id="ltName" type="text" maxlength="64"><label id="label_ltName">&nbsp;</label></td>
			</tr>
			<tr>
				<td>单次申请数(天)：</td>
				<td class="errorMessage">
					<input id="ltMinApplyDays" type="text" size="4" maxlength="4" onkeyup="value=value.replace(/[^0-9.]/g,'')">
					<= 申请天数 <=
					<input id="ltMaxApplyDays" type="text" size="4" maxlength="4" onkeyup="value=value.replace(/[^0-9.]/g,'')">
				</td>
			</tr>
			<tr>
				<td>单次申请数(小时)：</td>
				<td class="errorMessage">
					<input id="ltMinApplyHours" type="text" size="4" maxlength="4" onkeyup="value=value.replace(/[^0-9.]/g,'')">
					<=申请小时数<=
					<input id="ltMaxApplyHours" type="text" size="4" maxlength="4" onkeyup="value=value.replace(/[^0-9.]/g,'')">
				</td>
			</tr>
			<tr>
				<td>计算是否包含假日：</td>
				<td>
					<select id="ltInclHoliday">
						<option value="0">不包含</option>
						<option value="1">包含</option>
					</select>
				</td>
			</tr>
			<tr id="row_balForward">
				<td>上年余额携带到期日：</td>
				<td class="errorMessage">
				    <s:textfield id="balForward" name="balForward" onclick="WdatePicker()" size="10"/>
                    <img onclick="WdatePicker({el:'balForward'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
					<label id="label_balForward"></label>
				</td>
			</tr>
			<tr id="row_ltBalForwardDayLimit">
				<td>上年余额携带上限(天)：</td>
				<td class="errorMessage">
					<input id="ltBalForwardDayLimit" type="text" size="4" maxlength="4" onkeyup="value=value.replace(/[^0-9.]/g,'')">
				</td>
			</tr>
			<tr id="row_ltBalForwardHourLimit">
				<td>上年余额携带上限(小时)：</td>
				<td class="errorMessage">
					<input id="ltBalForwardHourLimit" type="text" size="4" maxlength="4" onkeyup="value=value.replace(/[^0-9.]/g,'')">
				</td>
			</tr>
			<tr id="row_ltBalForwardPerLimit">
				<td>上年余额携带上限(百分比)：</td>
				<td class="errorMessage"><input id="ltBalForwardPerLimit" type="text" size="4" maxlength="4" onkeyup="value=value.replace(/[^0-9.]/g,'')">
				70表示70%。</td>
			</tr>
			<tr id="row_ltBalForwardRounding">
				<td>余额携带尾数舍入：</td>
				<td>
					<select id="ltBalForwardRounding">
						<option value="">请选择</option>
						<option value="1">取整1</option>
						<option value="2">四舍五入1</option>
						<option value="3">进位1</option>
						<option value="4">取整0.5</option>
						<option value="5">四舍五入0.5</option>
						<option value="6">进位0.5</option>
					</select>
				</td>
			</tr>
			<tr id="row_ltDaysOfYear">
				<td>本年休假额度(天)：</td>
				<td class="errorMessage">
					<input id="ltDaysOfYear"> <red>支持公式。</red>
				</td>
			</tr>
			<tr id="row_ltHoursOfYear">
				<td>本年休假额度(小时)：</td>
				<td class="errorMessage">
					<input id="ltHoursOfYear"> <red>支持公式。</red>
				</td>
			</tr>
			<tr id="row_ltDaysForRelease">
				<td>释放额度(天)：</td>
				<td class="errorMessage">
					<input id="ltDaysForRelease" > <red>支持公式。</red>
				</td>
			</tr>
			<tr id="row_ltHoursForRelease">
				<td nowrap="nowrap">释放额度(小时)：</td>
				<td class="errorMessage">
					<input id="ltHoursForRelease" > <red>支持公式。</red>
				</td>
			</tr>
			<tr id="row_ltReleaseMethod">
				<td>额度释放办法：</td>
				<td>
					<select id="ltReleaseMethod">
						<option value="">请选择</option>
						<option value="M">按月释放</option>
						<option value="D">按天释放</option>
					</select>
				</td>
			</tr>
			<tr id="row_ltReleaseRounding">
				<td>额度释放尾数舍入：</td>
				<td>
					<select id="ltReleaseRounding">
						<option value="0">请选择</option>
						<option value="1">取整1</option>
						<option value="2">四舍五入1</option>
						<option value="3">进位1</option>
						<option value="4">取整0.5</option>
						<option value="5">四舍五入0.5</option>
						<option value="6">进位0.5</option>
					</select>
				</td>
			</tr>
			<tr id="row_ltOtherParameter">
				<td><label id="ltOtherParameter_label">其他参数</td>
				<td class="errorMessage"><input id="ltOtherParameter" > 支持公式。</td>
			</tr>
			<tr>
				<td>请假理由是否必填：</td>
				<td>
					<select id="ltNeedComments">
						<option value="1">必填</option>
						<option value="0">非必填</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>休假种类描述：</td>
				<td class="errorMessage"><textarea id="ltDesc" name="ltDesc" cols="28" rows="2" onkeypress="MKeyTextLength(this,128);"></textarea></td>
			</tr>
			<tr>
				<td colspan="2">
					<font color="red">备注：一般只需配置参数(天)，也可配置参数(小时)！</font>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input id="btn_add_leave_type" type="button" name="btn" value="保存">
					<input id="btn_update_leave_type" type="button" value="保存">
					<input type="reset" value="清空">
					<input id="btn_leave_type_close" type="button"	value="关闭">
					<input type="hidden" id="ltNo" />
					<input type="hidden" id="ltSortId" />
				</td>
			</tr>
		</table>
	</form>
</div>
<script type="text/javascript">
	$(document).ready(function() {
		var config={
			tableId		:"table_leave_type",
			tbodyId		:"tbody_leave_type",
			dialogId	:"dialog_leave_type",
			updateButton:"btn_update_leave_type",
			addButton	:"btn_add_leave_type",
			dialogHeight:555,
			dialogWidth :440,
			addLink		:"link_add_leave_type",
			deleteLink	:"link_delete_leave_type",
			updateLink	:"link_update_leave_type",
			sortLink	:"link_sort_leave_type",
			closeButton	:"btn_leave_type_close"
		};
		leaveTypeManager = new LeaveTypeManager(config);
	});
</script>
</body>
</html>