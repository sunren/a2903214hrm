<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<html>
<head>
<title><s:text name="desc.add" /><s:text name="desc.security.user" /></title>
</head>

<body onload ="changed_emp();init_password();">
<s:component template="bodyhead">
	<s:param name="pagetitle" value="getText('desc.security.user')+getText('desc.security.list')" />
	<s:param name="helpUrl" value="'7'" />
</s:component>
<s:form id="addForm" action="addUser.action" method="post" namespace="/security">
<s:token/>
 <s:hidden id="roleIds" name="roleIds"/>
 <s:hidden id="status" name="user.uiLevelRestrict"/>
 <s:hidden id="macLimit" name="user.uiMacRestrict"/>
 <s:hidden id="username" name="userNameString"/>
 <s:hidden id="userChangeName" name="userChangeName"/>
<table class="formtable" width="100%" >
	<tr>
	    <td align ="left">员工姓名<font color="red">*</font>:</td>
		  <td><s:select id="emp_name"  list="empList" name="user.id" listKey="id" listValue="empName" required="true" onchange="changed_emp();"/></td>
		<span class="errorMessage"><s:password id = "uiPassword" name="uiPassword" label="密码" onkeyup="isPassword(this);" maxlength="32" required="true" /></span>
	</tr>
	<tr>
		<td>用户名<font color="red">*</font>:</td>
		<td>
			<s:textfield id="user_name" name="user.uiUsername"  maxlength="64" required="true" />
		</td>
		<span class="errorMessage"><s:password id = "confirmPassword"  name="confirmPassword" label="重复密码" onkeyup="isPassword(this);" maxlength="32" required="true" /></span>
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
	 <td align = "left">登录限制:</td>
     <td nowrap>
 		<input type="radio" class="checkbox" name="user_uiLevelRestrict0" id="user_uiLevelRestrict0" value="0"  onclick="selectIPorMAC(this);">无限制
 		<input type="radio" class="checkbox" name="user_uiLevelRestrict1" id="user_uiLevelRestrict1" value="1"  onclick="selectIPorMAC(this);">限制到IP
 		<input type="radio" class="checkbox" name="user_uiLevelRestrict2" id="user_uiLevelRestrict2" value="2"  onclick="selectIPorMAC(this);">限制到机器
 		<input type="radio" class="checkbox" name="user_uiLevelRestrict3" id="user_uiLevelRestrict3" value="3"  onclick="selectIPorMAC(this);">限制到机器或IP
 	</td>
    </tr>
   
    <tr>
     <td align = "left">允许访问的IP地址(段):</td>
     <td><s:textfield id="user_uiIpRestrict" name="user.uiIpRestrict"  maxlength="64" required="false" />
     如:192.168.0.1-192.168.0.118 或者 192.168.0.* </td>
    </tr>
    <tr><td>&nbsp;</td></tr>
    <tr>
    <td></td>
     <td><span><input type="button" value="提交" onclick ="checkAddUser();">
	 <input type="button" value="返回" onclick ="returnBack();"></span></td>
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
		$("#macLimit").value = '00-00-00-00-00-00';
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

var uiUserName ="";
function changed_emp(){
	var str = $("#emp_name").val();	
	var str2 = "";
	<s:iterator value="empList" status="index">
		var test = "<s:property value ='getId()'/>";
		if(test == str)
		str2 = "<s:property value ='getEmpDistinctNo()'/>";
	</s:iterator>
	$('#user_name').val(str2);
	uiUserName = $("#user_name").val();
}

//初始化password - Steven 20100223
function init_password(){
	var password = hrm.common.randomStr(6,true,true,true);
	$("#uiPassword").val(password);
	$("#confirmPassword").val(password);
}

//add for addUser
function checkAddUser(){
	if($("#emp_name").val()== null||$("#emp_name").val().trim().length <1){
		alert("必须先创建一个员工!");
		return ;
	}
	if($("#user_name").val()== null||$("#user_name").val().trim().length <1){
		alert("必须为用户设置一个用户名!");
		return ;
	}

	var newPassword = $("#uiPassword").val();
	var newPassword2 = $("#confirmPassword").val();
	 
	if(hrm.common.isNull(newPassword) || hrm.common.isNull(newPassword2)){
		if(hrm.common.isNull(newPassword)){
			  hrm.common.appendEnd('uiPassword','必填项！');
		}else{hrm.common.appendEnd('uiPassword','');}
		
		if(hrm.common.isNull(newPassword2)){
			  hrm.common.appendEnd('confirmPassword','必填项！');
		}else{hrm.common.appendEnd('confirmPassword','');}
		
		return;
	}

	if(newPassword.length<4 || newPassword.length>32) {
		hrm.common.appendEnd('uiPassword','至少4位！');
		return;
	}
	 
	if(newPassword != newPassword2){
	    hrm.common.appendEnd('uiPassword','');
	    hrm.common.appendEnd('confirmPassword','密码不一致！');
	    return;
	}else{
	    hrm.common.appendEnd('confirmPassword','');
	}
	
	$('#username').val($("#emp_name").val());
	$('#userChangeName').val($("#user_name").val());
	var allRoles = document.getElementsByName('roleId');
	var flag = -1;
	var testRoles = "";
	for(i=0;i<allRoles.length;i++){
		if(allRoles[i].checked == true)flag = i;
		if(allRoles[i].checked == true)testRoles += allRoles[i].value+',';
		else testRoles+='0,';
	}
	if(flag == -1){
		alert("必须选择一个角色！");
		return;
	}
	if(testRoles.length > 1){
		testRoles = testRoles.substring(0,testRoles.length -1);
	}
	$('#roleIds').val(testRoles);
	
	
	
	if($("#status").val() == 1){
	  if($("#user_uiIpRestrict").val()==""){
	     alert("请输入限制的IP地址(段)！");
	     return;
	  }else if(!hrm.common.checkIP($("#user_uiIpRestrict").val())){
	  	return;
	  }
	}
	document.getElementById('addForm').submit();
}

function returnBack (){
 	window.location="userList.action";	
}
</script>
</s:form>
</body>
</html>
