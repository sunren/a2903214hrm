<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<html>
<head>
<title><s:text name="desc.add" /><s:text name="desc.security.user" /></title>
</head>

<body>
<s:set name="params" value="11" />
<s:component template="bodyhead">
	<s:param name="pagetitle" value="���������û�" />
</s:component>
<s:form id="addForm" action="addUser.action" method="post" namespace="/security">
<s:token/>
 <s:hidden id="roleIds" name="roleIds"/>
 <s:hidden id="status" name="user.uiLevelRestrict"/>
 <s:hidden id="macLimit" name="user.uiMacRestrict"/>
 <s:hidden id="username" name="userNameString"/>
<table  class="formtable" width="100%">
	<tr><td>&nbsp;</td></tr>
	<tr>
	<td nowrap valign="center">�������Ա��<font color="red">*</font>:</td>
	      <td>
	       <s:empselector id="empselector1" name="emp.empDeptNo.departmentName" condition="empNotUser" hiddenFieldName="emp.empDeptNo.id"/>
	 </td>
	</tr>
	<tr>
		<td colspan="5">&nbsp;</td>
	</tr>
	<tr>
		<td colspan="5"><s:text name="desc.security.userhasrole" /> (<s:text name="desc.security.multiple" />)</td>
	</tr>
	<tr>
		<td colspan="5"><s:if test="roleList!=null">
			<table width="100%">
				<s:iterator value="roleList" status="index">
					<s:if test="#index.first==true">
						<tr>
						<td><input class="checkbox" type="checkbox" id = "roleId" name="roleId"  checked ="true"
						value="<s:property value="roleNo"/>" /><s:property value="roleName" />&nbsp;&nbsp;
					    </td>
					</s:if>
					<s:else >
					<td><input class="checkbox" type="checkbox" name="roleId" id = "roleId"
						value="<s:property value="roleNo"/>" /><s:property value="roleName" />&nbsp;&nbsp;
					</td>
					</s:else >
					<s:if test="#index.modulus(4)==0">		
					<tr>
					</s:if>
					<s:if test="#index.last==true">
					<tr>
					</s:if>
				</s:iterator>
			</table>
		</s:if></td>
	</tr>
	<tr><td>&nbsp;</td></tr>
	<tr>
	 <td align = "left">��¼����:</td>
     <td nowrap>
 		<input type="radio" class="checkbox" name="user_uiLevelRestrict0" id="user_uiLevelRestrict0" value="0"  onclick="selectIPorMAC(this);">������
 		<input type="radio" class="checkbox" name="user_uiLevelRestrict1" id="user_uiLevelRestrict1" value="1"  onclick="selectIPorMAC(this);">���Ƶ�IP
 		<input type="radio" class="checkbox" name="user_uiLevelRestrict2" id="user_uiLevelRestrict2" value="2"  onclick="selectIPorMAC(this);">���Ƶ�����
 		<input type="radio" class="checkbox" name="user_uiLevelRestrict3" id="user_uiLevelRestrict3" value="3"  onclick="selectIPorMAC(this);">���Ƶ�������IP
 	</td>
    </tr>
   
    <tr>
		<td align = "left">������ʵ�IP��ַ(��):</td>
		<td><s:textfield id="user_uiIpRestrict" name="user.uiIpRestrict"  maxlength="64" required="false" />
		                   ��:192.168.0.1-192.168.0.118 ���� 192.168.0.* </td>
		<td></td>
    </tr>
    <tr><td>&nbsp;</td></tr>
    <tr>
		<td></td>
		<td><span><input type="button" value="�ύ" onclick ="checkAddUser();">
		<input type="button" value="����" onclick ="returnBack();"></span></td>
		<td></td>
    </tr>
	
</table>
<script type="text/javascript" language="javascript">
$("#status").val(0);
$("#user_uiIpRestrict").attr('disabled', true);
$("#user_uiLevelRestrict0").attr('checked', true);
$("#user_uiLevelRestrict1").attr('checked', false);
$("#user_uiLevelRestrict2").attr('checked', false);
$("#user_uiLevelRestrict3").attr('checked', false);
// ���÷������ƣ�
function selectIPorMAC(obj){
	var level = obj.value;
	if(level == 0){
		$("#user_uiIpRestrict").attr('disabled', true);
		$("#user_uiLevelRestrict1").attr('checked', false);
		$("#user_uiLevelRestrict2").attr('checked', false);
		$("#user_uiLevelRestrict3").attr('checked', false);
		$("#status").val(0);
	}else if(level == 1){
		$("#user_uiIpRestrict").attr('disabled', false);
		$("#user_uiLevelRestrict0").attr('checked', false);
		$("#user_uiLevelRestrict2").attr('checked', false);
		$("#user_uiLevelRestrict3").attr('checked', false);
		$("#status").val(1);
	}else if(level == 2){
		$("#user_uiIpRestrict").attr('disabled', true);
		$("#user_uiLevelRestrict1").attr('checked', false);
		$("#user_uiLevelRestrict0").attr('checked', false);
		$("#user_uiLevelRestrict3").attr('checked', false);
		$("#macLimit").val('00-00-00-00-00-00');
		$("#status").val(2);
	}else if(level == 3){
		$("#user_uiIpRestrict").attr('disabled', false);
		$("#user_uiLevelRestrict1").attr('checked', false);
		$("#user_uiLevelRestrict0").attr('checked', false);
		$("#user_uiLevelRestrict2").attr('checked', false);
		$("#macLimit").val('00-00-00-00-00-00');
		$("#status").val(3);
	}
}

// ���Ҫ��ӵ��û���
function checkAddUser(){
    var str2 = empSelector_choseEmpIdStr();
		if(str2==""){
		alert("�û�����Ϊ��");
		return;
	}
	$('#username').val(str2);
	
	
	var allRoles = document.getElementsByName('roleId');
	var flag = -1;
	var testRoles = "";
	for(i=0;i<allRoles.length;i++){
		if(allRoles[i].checked == true)flag = i;
		if(allRoles[i].checked == true)testRoles += allRoles[i].value+',';
		else testRoles+='0,';
	}
	if(flag == -1){
		alert("����ѡ��һ����ɫ��");
		return;
	}
	if(testRoles.length > 1){
		testRoles = testRoles.substring(0,testRoles.length -1);
	}
	$('#roleIds').val(testRoles);
	
	if($("#status").val() ==1){
		if($("#user_uiIpRestrict").val()==""){
		   alert("���������Ƶ�IP��ַ(��)��");
		   return;
		}else if(!hrm.common.checkIP($("#user_uiIpRestrict").val())){
			return;
		}
	}
	
	document.getElementById('addForm').submit();
}
//��������ѡ�����Ա��
empSelector_searchEmp();

function returnBack (){
 	window.location="userList.action";	
}
</script>
</s:form>
</body>
</html>
