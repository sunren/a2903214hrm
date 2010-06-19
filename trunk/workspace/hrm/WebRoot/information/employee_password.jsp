<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<html>
<head>
<title><s:text name="button.base.change" /><s:text name="desc.security.password" /></title>
</head>
<body>
<s:component template="bodyhead">
	<s:param name="pagetitle"
		value="getText('button.base.change')+getText('desc.security.password')" />
    <s:param name="helpUrl" value="'36'"/>
</s:component>
<form name="updateForm" action="passwordUpdate.action" method="post" namespace="/security">
	<s:token></s:token>
	<table class="formtable" width="100%">
		<tr>
			<td align="right">用户名：</td>
			<td><s:property value="#session.userinfo.employee.empName"/></td>
			<td align="right">角色：</td>
			<td colspan="4"><s:property value="roleInfo" escape="false"/></td>
		</tr>
		<tr>
			<s:password name="user.uiPassword" label="原密码" required="true" />
		</tr>
		<tr>
			<s:password name="newpassword" label="新密码" required="true"  onkeyup="isPassword(this);"/>
		</tr>
			<s:password name="confirmpassword" label="重复密码" required="true"  onkeyup="isPassword(this);"/>
		<tr>
			<td align="right"><input type="submit" value="提交"></td>
			<td><input type="reset" value="重置"></td>
		</tr>
	</table>
</form>
</body>
</html>
