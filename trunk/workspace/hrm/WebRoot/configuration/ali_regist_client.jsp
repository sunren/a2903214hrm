<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="jscalendar" uri="/jscalendar"%>
<html>
	<head>
		<title>客户注册</title>
	<style type="text/css">
	<!--
	body {
		background-color: #f9f9f5;
		text-align:center;
	}
	
	.formtable td {font-size: 12px;height: 20px;}
	.required,.errorMessage {color: red;font-weight: normal;text-align: left;}
	-->
</style>			
	</head>
    <br>
	<body>
	<s:form id="regist_client" name="regist_client"
			namespace="/configuration" action="ali_regist_client">
			<s:token />
			<s:hidden id="app_instance_id" name="app_instance_id" />
			<s:hidden id="user_id" name="user_id" />	
	<table border=0 cellpadding=0 cellspacing=0 width=100%> 
	<tr>
		<td align="center"><img src="../resource/images/login_top_mt.GIF" height="59" /></td>
	</tr>
	<tr><td width="100%" align="center"> 
			<TABLE width="800" class="formtable" cellpadding=3 cellspacing=0 background="../resource/images/login_bg_mt.GIF">
				<tr>
				    <td align=right>公司名称<font color=red>*</font>:</td>
				    <td><s:textfield id="client.clientName" name="client.clientName" size="30" maxlength="128" /></td>
				    <td align=right width="80">用户姓名<font color=red>*</font>:</td>
				    <td><s:textfield id="employee.empName" name="employee.empName"  size="14" maxlength="128" /></td>
				</tr>

				<tr>
					<td align=right>公司简称:</td>
					<td><s:textfield id="client.clientShortName" name="client.clientShortName" size="30" maxlength="64" /></td>
					<td align=right width="80">性别<font color=red>*</font>:</td>
					<td><s:select id="employee.empGender" name="employee.empGender" list=" #{1:'男',0:'女'}" /></td>
				</tr>

				<tr>
				 	<td align=right>公司地址:</td>
					<td><s:textfield id="client.clientAddress" name="client.clientAddress" size="30" maxlength="128" /></td>
					<td align=right width="80">出生日期<font color=red>*</font>:</td>
					<td>
					<s:textfield id="employee.empBirthDate" name="employee.empBirthDate" onclick="WdatePicker()" size="10"/>
                    <img onclick="WdatePicker({el:'employee.empBirthDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
					</td>
				</tr>

				<tr>
					<td align=right>公司邮编:</td>
					<td><s:textfield id="client.clientZip" name="client.clientZip" size="30" maxlength="8" /></td>
					<td align=right width="80">职务<font color=red>*</font>:</td>
					<td><s:select id="employee.empJobTitle.jobtitleName" list="jobtitleList" name="employee.empJobTitle.jobtitleName" listKey="jobtitleName" listValue="jobtitleName" multiple="false"  emptyOption="false" size="1" /></td>
				</tr>

				<tr>
					<td align=right>公司联系电话<font color=red>*</font>:</td>
					<td><s:textfield id="client.clientPhone" name="client.clientPhone" size="30" maxlength="32" /></td>						
					<td align=right width="80">参加工作日期<font color=red>*</font>:</td>
					<td>
					<s:textfield id="employee.empWorkDate" value="2000-11-02" name="employee.empWorkDate" onclick="WdatePicker()" size="10"/>
                    <img onclick="WdatePicker({el:'employee.empWorkDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
					</td>
				</tr>

				<tr>
					<td align=right>公司传真:</td>
					<td><s:textfield id="client.clientFax" name="client.clientFax" size="30" maxlength="32" /></td>					
					<td align=right width="80">入职日期<font color=red>*</font>:</td>
					<td>
					<s:textfield id="employee.empJoinDate" value="2007-11-12" name="employee.empJoinDate" onclick="WdatePicker()" size="10"/>
                    <img onclick="WdatePicker({el:'employee.empJoinDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
					</td>
				</tr>

				<tr>
					<td align=right>公司联系人<font color=red>*</font>:</td>
					<td><s:textfield id="client.clientContactName"  name="client.clientContactName" size="30" maxlength="64" /></td>
					<td align=right width="80">用工形式<font color=red>*</font>:</td>
					<td><s:select id="employee.empType.emptypeName" list="emptypeList" name="employee.empType.emptypeName" listKey="emptypeName" listValue="emptypeName" multiple="false" emptyOption="false" size="1" /></td>				
				</tr>
				
				<tr>
					<td align=right>公司Email<font color=red>*</font>:</td>
					<td><s:textfield id="client.clientEmail" name="client.clientEmail" size="30" maxlength="64" /></td>
					<td align=right width="80">工作地区<font color=red>*</font>:</td>
					<td><s:select id="employee.empLocationNo.locationName" list="locationList" name="employee.empLocationNo.locationName" listKey="locationName" listValue="locationName" multiple="false" emptyOption="false" size="1" /></td>				
				</tr>
				
				<tr>
					<td align="right">公司备注：</td>
					<td><s:textarea id="clientRemarks" name="client.clientRemarks" cols="30" rows="4" /></td>
					<td align=right width="80">部门<font color=red>*</font>:</td>
					<td><s:select id="employee.empDeptNo.departmentName"  list="departmentList" name="employee.empDeptNo.departmentName" listKey="departmentName" listValue="departmentName" multiple="false" emptyOption="false" size="1" /></td>
				</tr>
				
				<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td align=right width="80">证件种类<font color=red>*:</font></td>
				<td><table><tr>
				    <td><s:select name="employee.empIdentificationType"id="employee.empIdentificationType" required="true"  list="#{0:'身份证', 1:'护照', 2:'驾驶证', 3:'毕业证', 9:'其他'}" /></td>
					<td><s:textfield id="employee.empIdentificationNo" value="310101198301020001" required="true"name="employee.empIdentificationNo"/></td>
				</tr></table></td>
				</tr>
				
		        <tr><td colspan="4" align="center"><font color="red">注：</font>为了正常使用365HRM系统，请您在录入公司资料的同时，也录入您的个人资料。<br>
您的个人资料将成为系统中的第一个员工资料被导入，您可以在系统中予以修改。
				</td></tr>
				
				<tr>
					<td colspan=4 align="center"><input type="submit" value=" 注册 "></td>
				</tr>
			</TABLE>
		</td></tr>
		<tr>
			<td align="center"><img src="../resource/images/login_bot_mt.GIF" height="12" /></td>
		</tr>		
		</table>
	</s:form>		
	</body>
</html>