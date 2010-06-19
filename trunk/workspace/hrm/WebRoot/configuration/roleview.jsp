<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@	taglib prefix="s" uri="webwork"%>
<%@ taglib prefix="ty" uri="/tengsource"%>
<html>
<head>
<title><s:text name="button.base.view" /><s:text
	name="desc.security.roleinfo" /></title>
</head>
<body>
<s:component template="bodyhead">
	<s:param name="pagetitle" value="getText('desc.security.roleinfo')" />
</s:component>
<ty:auth auths="921">
	<div class="btnlink">
		<a href="roleDel.action?roleNo=<s:property value="role.roleNo"/>"><s:text name="button.base.delete" /></a>
		<a href="roleEdit.action?role.id=<s:property value="role.id"/>"><s:text name="button.base.change" /></a>
	</div>
</ty:auth>
<s:if test="role==null">
	<s:text name="msg.security.nothisrole" />
</s:if>
<s:elseif test="role!=null">
	<table class="formtable" width="100%" >
		<tr>
			<td><s:text name="desc.security.rolename" />��<s:property value="role.roleName" /></td>
			<td><s:text name="desc.security.roledesc" />��<s:property value="role.roleDesc" /></td>
			<td>
		</tr>
	</table>
<br />
	
<s:bean name="org.apache.struts2.util.Counter" id="rowcounter">
  		<s:param name="first" value="1"/><s:param name="last" value="10"/>
</s:bean>

<table cellpadding="0" cellspacing="0"  class="gridtable" width="100%">
	<tr>
		<th class="listViewThS1">Ȩ������</th>
		
		<th class="listViewThS1">����</th>
	</tr>
	<s:iterator value="authorities"  status="status">
				<s:if test='#this.authorityModuleNo.length() == 1'>
					<tr>
						<td class="tablefield tda" colspan="3">
						<s:if test='#this.authorityModuleNo=="1"'>Ա��&nbsp;</s:if>
						<s:if test='#this.authorityModuleNo=="2"'>н��&nbsp;</s:if>
						<s:if test='#this.authorityModuleNo=="3"'>��ѵ&nbsp;</s:if>
						<s:if test='#this.authorityModuleNo=="4"'>����&nbsp;</s:if>
						<s:if test='#this.authorityModuleNo=="5"'>��Ч&nbsp;</s:if>
						<s:if test='#this.authorityModuleNo=="6"'>��Ƹ&nbsp;</s:if>
						<s:if test='#this.authorityModuleNo=="7"'>����&nbsp;</s:if>
						<s:if test='#this.authorityModuleNo=="8"'>��ҳ����&nbsp;</s:if>
						<s:if test='#this.authorityModuleNo=="9"'>ϵͳ&nbsp;</s:if>
						</td>
					</tr>
			</s:if>
			<tr>
				<td class="tda ">&nbsp;<s:property value="authorityDesc" /></td>
				
				<s:if test ="authorityConditionNo == 0">
				<td  class="tda">��&nbsp;</td>
				</s:if>	
				<s:if test ="authorityConditionNo == 1">
				<td  class="tda">����&nbsp;</td>
				</s:if>	
				<s:if test ="authorityConditionNo == 2">
				<td  class="tda">������&nbsp;</td>
				</s:if>	
				<s:if test ="authorityConditionNo == 3">
				<td  class="tda">������&nbsp;</td>
				</s:if>				
			</tr>
		</s:iterator>
</table>
</s:elseif>
</body>
</html>
