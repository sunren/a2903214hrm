<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<html>
<head>
<title><s:text name="button.base.change" /><s:text
	name="desc.security.password" /></title>
</head>
<body>
<s:component template="bodyhead">
	<s:param name="pagetitle"
		value="getText('button.base.change')+getText('desc.security.password')" />
</s:component>
<form name="updateForm" action="passwordUpdate.action" method="post" namespace="/security">
	<table class="formtable" width="100%">
		<tr>
			<s:if test="ownPass!=null">
				<input type="hidden" name="ownPass" value="ownPass" />
				<s:password name="user.uiPassword" label="%{getText('desc.security.oldpassword')}" required="true" />
			</s:if>
			<s:else>
				<td><s:hidden name="user.id" /></td>
			</s:else>
			<s:password name="password2" label="%{getText('desc.security.newpassword')}" required="true" />
			<s:password name="password2_" label="%{getText('desc.security.confirmpassword')}" required="true" />
			<td><input type="submit" value="<s:text name="button.base.submit"/>"></td>
		</tr>
	</table>
</form>
</body>
</html>
