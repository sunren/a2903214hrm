<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="jscalendar" uri="/jscalendar"%>
<html>
	<head>
		<title>�û�ע��</title>
	</head>
	<body>
	<s:form id="regist_userForm" name="regist_userForm"
			namespace="/configuration" action="regist_user">
			<s:token />
			<s:hidden id="clientId" name="clientId" />
			<TABLE>
				<tr>
				    <td align=right>�ͻ����<font color=red>*</font>:</td>
					<td><s:textfield id="clientIdTemp" name="client.id" readonly="true" size="30" /></td>
				    <td><Font color=red>���뱣������Ŀͻ���ţ�</Font></td>
				</tr>
				
				<tr>
				    <td align=right>Ա�����<font color=red>*</font>:</td>
					<td><s:textfield id="employee.empDistinctNo" name="employee.empDistinctNo" size="30" /></td>
					<td>����ty0001��</td>
				</tr>

				<tr>
				    <td align=right>��¼����<font color=red>*</font>:</td>
					<td><s:password id="password" name="password" size="30" maxlength="128" /></td>
					<td>��6λ��32λ���������֡�Ӣ����ĸ��</td>
				</tr>

				<tr>
				    <td align=right>ȷ������<font color=red>*</font>:</td>
					<td><s:password id="confirmPassword"  name="confirmPassword"  size="30" maxlength="64" /></td>
				</tr>

				<tr>
				    <td align=right>�û�����<font color=red>*</font>:</td>
					<td><s:textfield id="employee.empName" name="employee.empName" size="30" maxlength="128" /></td>
				</tr>

				<tr>
					<td align=right>�Ա�<font color=red>*</font>:</td>
					<td><s:select id="employee.empGender" name="employee.empGender" list=" #{1:'��',0:'Ů'}" /></td>
				</tr>

				<tr>
					<td align=right>��������<font color=red>*</font>:</td>
					<td>
					<s:textfield id="employee.empBirthDate" name="employee.empBirthDate" value="1983-01-02" onclick="WdatePicker()" size="10"/>
                    <img onclick="WdatePicker({el:'employee.empBirthDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
					</td>
				</tr>

				<tr>
				    <td align=right>ְ��<font color=red>*</font>:</td>
					<td><s:select id="employee.empJobTitle.jobtitleName" list="jobtitleList"
							name="employee.empJobTitle.jobtitleName" listKey="jobtitleName"
							listValue="jobtitleName" multiple="false"
					        emptyOption="false" size="1" /></td>
				</tr>

				<tr>
					<td align=right>
						�μӹ�������<font color=red>*</font>:
					</td>
					<td>
					<s:textfield id="employee.empWorkDate" name="employee.empWorkDate" value="2000-11-02" onclick="WdatePicker()" size="10"/>
                    <img onclick="WdatePicker({el:'employee.empWorkDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
					</td>
				</tr>
				<tr>
					<td align=right>
						��ְ����<font color=red>*</font>:
					</td>
					<td>
					<s:textfield id="employee.empJoinDate" name="employee.empJoinDate" value="2007-11-12" onclick="WdatePicker()" size="10"/>
                    <img onclick="WdatePicker({el:'employee.empJoinDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
					</td>

				</tr>
				<tr>
				<td align=right>
						֤������<font color=red>*</font>:
				</td>
				<td>
				<table><tr>
				    <td>
					<s:select name="employee.empIdentificationType"
						id="employee.empIdentificationType" 
						list="#{0:'���֤', 1:'����', 2:'��ʻ֤', 3:'��ҵ֤', 9:'����'}" />
					</td>
					<td>
						<s:textfield id="employee.empIdentificationNo" value="310101198301020001" name="employee.empIdentificationNo" size="30" maxlength="64" />
					</td>
				</tr></table>
				</td>
				</tr>
				<tr>
				<td align=right>�ù���ʽ<font color=red>*</font>:</td>
				<td><s:select id="employee.empType.emptypeName" list="emptypeList"
							name="employee.empType.emptypeName" listKey="emptypeName"
							listValue="emptypeName"  multiple="false"
					        emptyOption="false" size="1" /></td>
				</tr>
				<tr>
				<td align=right>��������<font color=red>*</font>:</td>
				<td><s:select id="employee.empLocationNo.locationName"list="locationList"
							name="employee.empLocationNo.locationName" listKey="locationName"
							listValue="locationName" multiple="false"
					        emptyOption="false" size="1" /></td>
				</tr>
				<tr>
				<td align=right>����<font color=red>*</font>:</td>
				<td><s:select id="employee.empDeptNo.departmentName" list="departmentList"
							name="employee.empDeptNo.departmentName" listKey="departmentName"
							listValue="departmentName"multiple="false"
					        emptyOption="false" size="1" /></td>
				</tr>
				<tr>
					<td align="right">
						<input type="button" onclick="checkRegistFunction();" value="ע��">
					</td>
					<td align="center">
						<input id="active" name="active" type="button"
							onclick="concalOperation();" value="ȡ��">
					</td>
				</tr>
			</TABLE>
		</s:form>
		<script language="javascript">
	   function checkRegistFunction(){
	    document.getElementById("clientId").value= document.getElementById("clientIdTemp").value;
		document.getElementById("regist_userForm").submit();
	   }
	
	   function concalOperation(){
	    window.location="login.jsp";
	}
</script>
	</body>
</html>
