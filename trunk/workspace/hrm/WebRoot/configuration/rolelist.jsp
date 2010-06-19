<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<%@taglib prefix="ty" uri="/tengsource"%>
<html>
	<head>
		<title><s:text name="desc.security.role" />
		</title>
	</head>
	<body>
		<s:component template="bodyhead">
			<s:param name="pagetitle"
				value="getText('desc.security.role')+getText('desc.security.list')" />
			<s:param name="helpUrl" value="'8'" />
		</s:component>
		<form id="addRoleForm" action="addRoleInit.action">
			<input type="submit" id="newRoleButton" class="button" value="������ɫ" />
			<input type="button"  class="button"  onclick="viewAuthority();" value="Ȩ���б�" />
		</form>

		<table cellpadding="0" cellspacing="0" name="roleTable"
			class="gridtableList" width="100%">
			<tr>
				<td class="listViewThS1">
					<s:text name="��ɫ˳��" />
				</td>
				<td class="listViewThS1">
					&nbsp;<s:text name="desc.security.rolename" />
				</td>
				<td class="listViewThS1">
					&nbsp;&nbsp;&nbsp;<s:text name="desc.security.roledesc" />
				</td>
				<td class="listViewThS1">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:text name="desc.base.action" />
				</td>
			</tr>
			<s:if test="roleList != null">
				<s:iterator value="roleList">
					<tr>
						<td>
							&nbsp;
							<s:property value="roleSortId" />
						</td>
						<td>							
							<a href="roleView.action?role.id=<s:property value="id"/>"
								class="listViewTdLinkS1"><s:property value="roleName" /> </a>
						</td>
						<td>							
							<s:property value="roleDesc" />
						</td>
						<td>							
							<a href="roleEdit.action?role.id=<s:property value="id"/>"
								class="listViewTdLinkS1"><s:text name="button.base.change"/><s:text name="desc.security.roleinfo"/></a>&nbsp;|
							<span
								style="font-size: 12px; color: #002780; text-decoration: underline;cursor: pointer"
								onclick="deleteRole('<s:property value='roleNo'/>');"><s:text
									name="button.base.delete" /> </span>
						</td>
					</tr>
				</s:iterator>
			</s:if>
		</table>

<script type="text/javascript" language="javascript">
// �鿴����Ȩ�ޣ�
function viewAuthority(){
    window.location="getAuthList.action";
}

// ɾ����ɫ��
function deleteRole(roleNo){
    if(confirm("��ȷ��Ҫɾ����")){ 
	    window.location="roleDel.action?roleNo="+roleNo;
    }
}
</script>
	</body>
</html>
