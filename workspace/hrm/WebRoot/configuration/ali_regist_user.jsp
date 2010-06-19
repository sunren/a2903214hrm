<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="jscalendar" uri="/jscalendar"%>
<html>
	<head>
		<title>用户注册</title>

	</head>
	<body>
		<br>
		<s:form id="regist_userForm" name="regist_userForm"
			namespace="/configuration" action="ali_regist_user">
			<s:token />
			<s:hidden id="app_instance_id" name="app_instance_id" />
			<s:hidden id="user_id" name="user_id" />
			<TABLE>
				<tr>
					<td align=right>用户姓名<font color=red>*</font>:</td>
				    <td><s:textfield id="employee.empName" name="employee.empName"  size="30" maxlength="128" /></td>
				</tr>

				<tr>
					<td align=right>性别<font color=red>*</font>:</td>
					<td><s:select id="employee.empGender" name="employee.empGender" list=" #{1:'男',0:'女'}" /></td>
				</tr>

				<tr>
					<td align=right>出生日期<font color=red>*</font>:</td>
					<td>
					<s:textfield id="employee.empBirthDate" value="1983-01-02" name="employee.empBirthDate" onclick="WdatePicker()" size="10"/>
                    <img onclick="WdatePicker({el:'employee.empBirthDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
					</td>
				</tr>

				<tr>
					<td align=right>职务<font color=red>*</font>:</td>
					<td><s:select id="employee.empJobTitle.jobtitleName" list="jobtitleList" name="employee.empJobTitle.jobtitleName" listKey="jobtitleName" listValue="jobtitleName" multiple="false"  emptyOption="false" size="1" /></td>
				</tr>

				<tr>
					<td align=right>参加工作日期<font color=red>*</font>:</td>
					<td>
					<s:textfield id="employee.empWorkDate" value="2000-11-02" name="employee.empWorkDate" onclick="WdatePicker()" size="10"/>
                    <img onclick="WdatePicker({el:'employee.empWorkDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
					</td>
				</tr>
				
				<tr>
					<td align=right>入职日期<font color=red>*</font>:</td>
					<td>
					<s:textfield id="employee.empJoinDate" value="2007-11-12" name="employee.empJoinDate" onclick="WdatePicker()" size="10"/>
                    <img onclick="WdatePicker({el:'employee.empJoinDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
					</td>
				</tr>
				
				<tr>
					<td align=right>证件种类<font color=red>*:</font></td>
					<td><table><tr>
				    <td><s:select name="employee.empIdentificationType"id="employee.empIdentificationType" required="true"  list="#{0:'身份证', 1:'护照', 2:'驾驶证', 3:'毕业证', 9:'其他'}" /></td>
					<td><s:textfield id="employee.empIdentificationNo" value="310101198301020001" required="true"name="employee.empIdentificationNo" size="30" maxlength="64" /></td>
					</tr></table></td>
				</tr>
				
				<tr>
					<td align=right>用工形式<font color=red>*</font>:</td>
					<td><s:select id="employee.empType.emptypeName" list="emptypeList" name="employee.empType.emptypeName" listKey="emptypeName" listValue="emptypeName" multiple="false" emptyOption="false" size="1" /></td>				
				</tr>
				
				<tr>
					<td align=right>工作地区<font color=red>*</font>:</td>
					<td><s:select id="employee.empLocationNo.locationName" list="locationList" name="employee.empLocationNo.locationName" listKey="locationName" listValue="locationName" multiple="false" emptyOption="false" size="1" /></td>				
				</tr>
				<tr>
					<td align=right>部门<font color=red>*</font>:</td>
					<td><s:select id="employee.empDeptNo.departmentName"  list="departmentList" name="employee.empDeptNo.departmentName" listKey="departmentName" listValue="departmentName" multiple="false" emptyOption="false" size="1" /></td>
				</tr>
				<tr>
					<td colspan=2 align="center"><input type="submit" value="注册"></td>
				</tr>
			</TABLE>
		</s:form>
	</body>
</html>
