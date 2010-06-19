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
			<th nowrap="nowrap">�ݼ���������</th>
			<th nowrap="nowrap">�ݼ���������</th>
			<th nowrap="nowrap">�ݼ����</th>
			<th nowrap="nowrap">��С��������</th>
			<th nowrap="nowrap">�����������</th>
			<th nowrap="nowrap">�����ڼ���</th>
			<th nowrap="nowrap">�������</th>
		</tr>
	</thead>
	<tbody id="tbody_leave_type">
		<s:iterator value="allLeaveType">
			<tr id='<s:property value="ltNo"/>' align="center" ltInclHoliday="<s:property value='ltInclHoliday' />">
				<td align="center"><s:property value="ltName" /></td>
				<td align="center"><s:property value="ltDesc" /></td>
				<td align="center">
					<s:if test="ltSpecialCat==0">�¼�</s:if>
					<s:elseif test="ltSpecialCat==1">���</s:elseif>
					<s:elseif test="ltSpecialCat==3">����</s:elseif>
					<s:elseif test="ltSpecialCat==4">����סԺ</s:elseif>
					<s:elseif test="ltSpecialCat==5">��н����</s:elseif>
					<s:elseif test="ltSpecialCat==6">���</s:elseif>
					<s:elseif test="ltSpecialCat==7">����</s:elseif>
					<s:elseif test="ltSpecialCat==8">����</s:elseif>
					<s:elseif test="ltSpecialCat==9">�����</s:elseif>
					<s:elseif test="ltSpecialCat==10">����(������)</s:elseif>
					<s:elseif test="ltSpecialCat==11">����(����)</s:elseif>
					<s:elseif test="ltSpecialCat==20">����</s:elseif>
				</td>
				<td align="center"><s:property value="ltMinApplyDays" /></td>
				<td align="center"><s:property value="ltMaxApplyDays" /></td>
				<td align="center">
					<s:if test="ltInclHoliday==1">����</s:if>
					<s:else>������</s:else>
				</td>
				<td align="center">
					<s:if test="ltNeedComments==0">�Ǳ���</s:if>
					<s:else>����</s:else>
				</td>
			</tr>
		</s:iterator>
	</tbody>
</table>
<div class="btnlink">
	<a href="#" id="link_add_leave_type">����</a>
	<a href="#" id="link_delete_leave_type">ɾ��</a>
	<a href="#" id="link_update_leave_type">�޸�</a>
	<a href="#" id="link_sort_leave_type">��������</a>
</div>
<br>
<div id="dialog_leave_type" title="�ݼ���������">
	<form id="holidayTypeForm" method="post">
		<table cellpadding="0" cellspacing="1" width="100%">
			<tr>
				<td>�ݼ�����<font color="red">*</font>��</td>
				<td>
					<select id="ltSpecialCat">
						<option value="0">�¼�</option>
						<option value="1">���</option>
						<option value="3">����</option>
						<option value="4">����סԺ</option>
						<option value="5">��н����</option>
						<option value="6">���</option>
						<option value="7">����</option>
						<option value="8">����</option>
						<option value="9">�����</option>
						<option value="10">����(������)</option>
						<option value="11">����(����)</option>
						<option value="20">����</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>�ݼ���������<font color="red">*</font>��</td>
				<td class="errorMessage"><input id="ltName" type="text" maxlength="64"><label id="label_ltName">&nbsp;</label></td>
			</tr>
			<tr>
				<td>����������(��)��</td>
				<td class="errorMessage">
					<input id="ltMinApplyDays" type="text" size="4" maxlength="4" onkeyup="value=value.replace(/[^0-9.]/g,'')">
					<= �������� <=
					<input id="ltMaxApplyDays" type="text" size="4" maxlength="4" onkeyup="value=value.replace(/[^0-9.]/g,'')">
				</td>
			</tr>
			<tr>
				<td>����������(Сʱ)��</td>
				<td class="errorMessage">
					<input id="ltMinApplyHours" type="text" size="4" maxlength="4" onkeyup="value=value.replace(/[^0-9.]/g,'')">
					<=����Сʱ��<=
					<input id="ltMaxApplyHours" type="text" size="4" maxlength="4" onkeyup="value=value.replace(/[^0-9.]/g,'')">
				</td>
			</tr>
			<tr>
				<td>�����Ƿ�������գ�</td>
				<td>
					<select id="ltInclHoliday">
						<option value="0">������</option>
						<option value="1">����</option>
					</select>
				</td>
			</tr>
			<tr id="row_balForward">
				<td>�������Я�������գ�</td>
				<td class="errorMessage">
				    <s:textfield id="balForward" name="balForward" onclick="WdatePicker()" size="10"/>
                    <img onclick="WdatePicker({el:'balForward'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
					<label id="label_balForward"></label>
				</td>
			</tr>
			<tr id="row_ltBalForwardDayLimit">
				<td>�������Я������(��)��</td>
				<td class="errorMessage">
					<input id="ltBalForwardDayLimit" type="text" size="4" maxlength="4" onkeyup="value=value.replace(/[^0-9.]/g,'')">
				</td>
			</tr>
			<tr id="row_ltBalForwardHourLimit">
				<td>�������Я������(Сʱ)��</td>
				<td class="errorMessage">
					<input id="ltBalForwardHourLimit" type="text" size="4" maxlength="4" onkeyup="value=value.replace(/[^0-9.]/g,'')">
				</td>
			</tr>
			<tr id="row_ltBalForwardPerLimit">
				<td>�������Я������(�ٷֱ�)��</td>
				<td class="errorMessage"><input id="ltBalForwardPerLimit" type="text" size="4" maxlength="4" onkeyup="value=value.replace(/[^0-9.]/g,'')">
				70��ʾ70%��</td>
			</tr>
			<tr id="row_ltBalForwardRounding">
				<td>���Я��β�����룺</td>
				<td>
					<select id="ltBalForwardRounding">
						<option value="">��ѡ��</option>
						<option value="1">ȡ��1</option>
						<option value="2">��������1</option>
						<option value="3">��λ1</option>
						<option value="4">ȡ��0.5</option>
						<option value="5">��������0.5</option>
						<option value="6">��λ0.5</option>
					</select>
				</td>
			</tr>
			<tr id="row_ltDaysOfYear">
				<td>�����ݼٶ��(��)��</td>
				<td class="errorMessage">
					<input id="ltDaysOfYear"> <red>֧�ֹ�ʽ��</red>
				</td>
			</tr>
			<tr id="row_ltHoursOfYear">
				<td>�����ݼٶ��(Сʱ)��</td>
				<td class="errorMessage">
					<input id="ltHoursOfYear"> <red>֧�ֹ�ʽ��</red>
				</td>
			</tr>
			<tr id="row_ltDaysForRelease">
				<td>�ͷŶ��(��)��</td>
				<td class="errorMessage">
					<input id="ltDaysForRelease" > <red>֧�ֹ�ʽ��</red>
				</td>
			</tr>
			<tr id="row_ltHoursForRelease">
				<td nowrap="nowrap">�ͷŶ��(Сʱ)��</td>
				<td class="errorMessage">
					<input id="ltHoursForRelease" > <red>֧�ֹ�ʽ��</red>
				</td>
			</tr>
			<tr id="row_ltReleaseMethod">
				<td>����ͷŰ취��</td>
				<td>
					<select id="ltReleaseMethod">
						<option value="">��ѡ��</option>
						<option value="M">�����ͷ�</option>
						<option value="D">�����ͷ�</option>
					</select>
				</td>
			</tr>
			<tr id="row_ltReleaseRounding">
				<td>����ͷ�β�����룺</td>
				<td>
					<select id="ltReleaseRounding">
						<option value="0">��ѡ��</option>
						<option value="1">ȡ��1</option>
						<option value="2">��������1</option>
						<option value="3">��λ1</option>
						<option value="4">ȡ��0.5</option>
						<option value="5">��������0.5</option>
						<option value="6">��λ0.5</option>
					</select>
				</td>
			</tr>
			<tr id="row_ltOtherParameter">
				<td><label id="ltOtherParameter_label">��������</td>
				<td class="errorMessage"><input id="ltOtherParameter" > ֧�ֹ�ʽ��</td>
			</tr>
			<tr>
				<td>��������Ƿ���</td>
				<td>
					<select id="ltNeedComments">
						<option value="1">����</option>
						<option value="0">�Ǳ���</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>�ݼ�����������</td>
				<td class="errorMessage"><textarea id="ltDesc" name="ltDesc" cols="28" rows="2" onkeypress="MKeyTextLength(this,128);"></textarea></td>
			</tr>
			<tr>
				<td colspan="2">
					<font color="red">��ע��һ��ֻ�����ò���(��)��Ҳ�����ò���(Сʱ)��</font>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input id="btn_add_leave_type" type="button" name="btn" value="����">
					<input id="btn_update_leave_type" type="button" value="����">
					<input type="reset" value="���">
					<input id="btn_leave_type_close" type="button"	value="�ر�">
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