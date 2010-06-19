<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<%@ taglib prefix="ty" uri="/tengsource"%>
<html>
<head>
<title><s:text name="desc.security.auth" /><s:text
	name="desc.security.list" /></title>
</head>
<body>
<s:component template="bodyhead">
	<s:param name="pagetitle"
		value="getText('button.base.change')+getText('desc.security.auth')" />
</s:component>
<s:bean name="org.apache.struts2.util.Counter" id="rowcounter">
  		<s:param name="first" value="1"/><s:param name="last" value="9"/>
</s:bean>
<s:if test="auth!=null">
	<form name="editauth" action="updateAuth.action" method="post">
		<table name="authTable" cellpadding="0" cellspacing="0"
			class="formtable" width="100%">
			<input type="hidden" name="auth.id" value="<s:property value='auth.id'/>">
			<tr>
				<s:textfield label="所在模块" name="auth.authorityModuleNo" readonly="true" size="20" maxlength="64"/>
				<s:textfield label="子模块所属权限编号" name="auth.authoritySequence" readonly="true" size="20" maxlength="64"/>
			</tr>
			<tr>
				<s:select label="过滤条件" name="auth.authorityConditionNo" value="auth.authorityConditionNo" list="#{0:'0',1:'1', 2:'2', 3:'3'}"/>
				<s:textfield label="权限描述" name="auth.authorityDesc" size="20" maxlength="64"/>
			</tr>
			<tr>
				<td align="right"><s:text name="拥有权限&#65306;"/></td>
				<td align="left">
					<s:iterator value="#rowcounter" status="rowstatus">
						<s:if test="#rowstatus.count != 5 && #rowstatus.count != 6">
							<input type="checkbox" class="checkbox" name="actionIds" <s:if test="getAuth(#rowstatus.count-1)">checked</s:if> value="<s:property value='#rowstatus.count-1'/>" /><s:property value="#rowstatus.count-1"/>
						</s:if>
					</s:iterator>
				</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td colspan="4" align="center"><input type="submit" value="提交">&nbsp;<input type="reset" value="重置"></td>
			</tr>
		</table>
	</form>
</s:if>
<br />
<table cellpadding="0" cellspacing="0" class="listView" width="100%">
	<tr>
		<td class="listViewThS1"><s:text
			name="desc.security.authexplain" />：</td>
	</tr>
	<tr>
		<td>Action：0=查询 | 1=新增 | 2=修改 | 3=删除 | 6=打印 | 7=数据导出 | 8=数据导入</td>
	</tr>
</table>
<br />
</body>
</html>