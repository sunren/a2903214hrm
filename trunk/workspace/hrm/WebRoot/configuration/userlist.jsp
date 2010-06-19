<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<%@ taglib prefix="ty" uri="/tengsource"%>
<html>
<head>
<script type="text/javascript" src="../dwr/interface/SubmitUserRoles.js"></script>
<script type="text/javascript" src="../dwr/interface/ModifyPassword.js"></script>
<script type="text/javascript" src="../dwr/interface/ChangeStatus.js"></script>
<title><s:text name="desc.security.user" /><s:text name="desc.security.list" /></title>
<style type="text/css">
.mySpan{
	font-size: 12px;
	color: #002780;
	text-decoration: underline;
	cursor: pointer
}</style>
</head>
<body onload="hrm.common.check_order();">
<s:component template="bodyhead">
	<s:param name="pagetitle" value="getText('desc.security.user')+getText('desc.security.list')" />
	<s:param name="helpUrl" value="'7'" />
</s:component>
<span id="message"></span>

<form id="pageForm" name="userListForm" action="userList.action" namespace="/configuration" method="post">
	<s:hidden id="order" name="pager.order"/>
	<s:hidden id="operate" name="pager.operate"/>
	<s:hidden id ="role" name ="role"/>
	<s:hidden id ="roleIndex" name ="roleIndex"/>
  	<s:hidden name="pager.currentPage"/>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="formtable">
		<tr>
		    <s:textfield label="员工" name="empName" id ="id" size="20" maxlength="64"/>
			<s:select label="员工角色" name="selectRole" id ="selectRole" value="roleIndex" list="roleList" listKey="roleNo" listValue="roleName" multiple="false" emptyOption="true" size="1" />
			<s:select label="部门" name="depart"  id ="depart" value="depart" list="departList" listKey="id" listValue="departmentName" multiple="false" emptyOption="true" size="1" />
			<s:select label ="用户限制" id="esStatus" name="user.uiLevelRestrict" list="#{'':'请选择', 0:'无限制', 1:'限制到IP', 2:'限制到机器', 3:'限制机器或IP'}" />
			<td><input title="[Alt+F]" accesskey="F" class="button" type="button" name="button2" value="查询" onclick ="searchUserList();"> 
		    <input title="[Alt+C]" accesskey="C" class="button" type="button" name="button22" value="重置" onclick ="resetUserList();"><br>
			</td>
		</tr>
	</table>
</form>

<form id="addNewUserForm" name="addNewUserForm" action="addUserInit.action" namespace="/configuration" method="post">
</form>
<form id="addNewMultiUserForm" name="addNewMultiUserForm" action="addMultiUserInit.action" namespace="/configuration" method="post">
</form>

<form  id="pageDel" name="userDelForm" action="delUser.action" namespace="/configuration" method="post">
	<s:token/>
	<s:hidden name ="userDelId" id ="userDelId"/>
	<br>
	<ty:auth  auths="911">
	<input type ="button" class ="button" value ="新增用户" onclick ="addUserClicked();">
	<input type ="button" class ="button" value ="批量新增用户" onclick ="addMultiUserClicked();">
	</ty:auth>
	<table name="userTable" cellpadding="0" cellspacing="0" class="gridtableList" width="100%">
		<s:if test="pager.isSplit()">
		     <tr>
		     	<td colspan="11" align="right"  class="listViewPaginationTdS1">
	                    <a href="#" onclick="splits('first');"><img src='../resource/images/start.gif' width='11' height='9' alt='开始'>开始</a>
	                    <a href="#" onclick="splits('previous');"><img src='../resource/images/previous.gif' width='6' height='9' alt='上页'>上页</a>
	                       （<s:property value="pager.currentPage+'/'+pager.totalPages"/>页｜共<s:property value="pager.totalRows"/>条）
	                    <a href="#" onclick="splits('next');">下页<img src='../resource/images/next.gif' width='6' height='9'></a>
	                    <a href="#" onclick="splits('last');">最后<img src='../resource/images/end.gif' width='11' height='9' alt='最后'></a>
				</td>
		     </tr>
		</s:if> 
		<tr>
			<th><a href="#" onclick="hrm.common.order_submit('employee.empName', 'pageForm');">员工姓名</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='employee.empName_img'></th>
			<th><a href="#" onclick="hrm.common.order_submit('employee.empDistinctNo', 'pageForm');">员工编号</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='employee.empDistinctNo_img'></th>
			<th><a href="#" onclick="hrm.common.order_submit('uiUsername', 'pageForm');">用户名</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='uiUsername_img'></th>
			<th><a href="#" onclick="hrm.common.order_submit('uiCurrentLoginTime', 'pageForm');">最近登录时间</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='uiCurrentLoginTime_img'></th>
			<th>登录IP</th>
			<th>登录限制</th>
			
			<th><s:text name="desc.base.status" /></th>
			<th><s:text name="desc.base.action" /></th>
		</tr>
		<s:if test="!userList.isEmpty()">
		   <s:hidden id ="password" name ="password"/>
			<s:iterator value="userList">
			<input type="hidden" id="user_status_<s:property value='id'/>" value="<s:property value='uiStatus'/>">
				<tr>
					<td><span TITLE="工作电话: <s:property value='employee.empWorkPhone'/>  生日: <s:property value='employee.empBirthDate'/>
入职日期: <s:property value='employee.empJoinDate'/>  邮箱: <s:property value='employee.empEmail'/>
证件号码: <s:property value='employee.empIdentificationNo'/>">
<s:property value="employee.empName" /></td>
					<td><s:property value="employee.empDistinctNo"/></td>
					<td id="newUserName<s:property value='id'/>" name="newUserName<s:property value='id'/>"><s:property value="uiUsername"/></td>
					<td><s:date name="uiCurrentLoginTime" format="yyyy-MM-dd HH:mm:ss" /></td>
					<td><s:property value="uiLastLoginIp" /></td>
					<td align ="center"><span id="limitSpan<s:property value="id"/>">
										<s:if test="uiLevelRestrict==0">无限制</s:if>
					                    <s:if test="uiLevelRestrict==1">限制IP</s:if>
					                    <s:if test="uiLevelRestrict==2">限制机器</s:if>
					                    <s:if test="uiLevelRestrict==3">IP或机器</s:if></span>
					 
		            
		            <img class = "mySpan" src = "../resource/images/unpublish_inline.gif" onclick="showModifyLimit_dlg('<s:property value='id'/>','<s:property value='employee.empName'/>');"/>
		          
					
					</td>                    
					<td align ="center">
					<div  align="center" style="DISPLAY: block;clear : both">
		            <span id="f_uiStatus<s:property value='id'/>"><s:property value="uiStatus==0?'停用':'启用'"/></span>
		            <img class = "mySpan" src = "../resource/images/unpublish_inline.gif" onclick="changeStatus('<s:property value='id'/>','<s:property value='employee.empName'/>');"/>
		            </div></td>	
					<td  align ="right">
						<ty:auth auths="911">
						<input type="hidden" id="user_roles_<s:property value='id'/>" value="<s:property value='getUiRoleDecrypt()'/>">						
							<input type="hidden" id="user_limit_<s:property value='id'/>" value="<s:property value='uiLevelRestrict'/>$<s:property value='uiIpRestrict'/>$<s:property value='uiMacRestrict'/>">
							<s:if test = "#session.loginModel!='alimt'">
							<span style="font-size: 12px; color: #002780; text-decoration: underline; cursor: pointer" href="#" onclick="showModifyPassword_dlg('<s:property value='id'/>','<s:property value='uiUsername'/>','<s:property value='employee.empName'/>');">用户密码</span>&nbsp;|
                            </s:if>
							<span style="font-size: 12px; color: #002780; text-decoration: underline; cursor: pointer" href="#" onclick= "showUserRole_dlg('<s:property value='id'/>','<s:property value='employee.empName'/>');">修改角色</span>&nbsp;|
						    <span style="font-size: 12px; color: #002780; text-decoration: underline; cursor: pointer" href="#" onclick="deleteUserinfo('<s:property value='id'/>')">删除</span>&nbsp;
						</ty:auth>						
					</td>
				</tr>
			</s:iterator>
		</s:if>
		<s:else>
			<tr><td colspan="6" align="center">没有符合条件的员工</td></tr>
		</s:else>
	</table>
</form>

<div id="dlgModifyLimit" style="width:310;" class="prompt_div_inline">
	<table width="100%" cellspacing="0" cellpadding="0" class="formtable">
	 	<tr>
	 	<td nowrap>登录限制:</td>
	 	<td nowrap>
	 		<input type="radio" class="checkbox" name="uiLevelRestrict0" id="user_uiLevelRestrict0" value="0"  onclick="selectIPorMAC('0');">无限制
	 		<input type="radio" class="checkbox" name="uiLevelRestrict1" id="user_uiLevelRestrict1" value="1"  onclick="selectIPorMAC('1');">限制到IP
	 		<input type="radio" class="checkbox" name="uiLevelRestrict2" id="user_uiLevelRestrict2" value="2"  onclick="selectIPorMAC('2');">限制到机器
	 	    <input type="radio" class="checkbox" name="uiLevelRestrict3" id="user_uiLevelRestrict3" value="3"  onclick="selectIPorMAC('3');">限制到机器或IP
	 	</td>
	    </tr>   
	    <tr>
	    <td rowspan=2>IP地址(段):</td>
	    <td colspan=3><input id="user_uiIpRestrict"  name="user_uiIpRestrict" size="28" maxlength="64" /></td>
	    </tr>
	     
	    <tr>   
	    <td colspan=3>如:192.168.0.1-192.168.0.118 或者 192.168.0.* </td>
	    </tr>
	    <tr>
	    <td colspan="3" align="center"><span>
	    <input type="button" name="limitButton" value ="确定" onclick="limitUpdate();"/>
	    <input type="button" name="concalButton" value ="取消" onclick="closeLimit();"/>
	    </span></td>
	    
	    </tr>      
	</table>
	<iframe scrolling="no" style="position:absolute;z-index:-1;width:308px;height:114px;top:0px;left:0px;" frameborder="0" ></iframe>
</div>

<div id="dlgModifyPassword" style="width:310;" class="prompt_div_inline">
	<table width="100%" border="0" cellspacing="0" cellpadding="0"  class="formtable">
		<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
		<tr>
	    <td>用户名:</td>
	    <td><span class="errorMessage"><input type="textfield"  id="div_name"   maxlength="64" size="24" maxlength="32" ></td>
	  	</tr>
		<tr>
	    <td>新密码:</td>
	    <td><span class="errorMessage"><input type="password" id="div_password"  size="24" maxlength="32" onkeyup="hrm.common.checkPassword(this);"></span>
	    </td>
	    <td>
	    &nbsp;
	    </td>
	  	</tr>
	  	<tr>
	    <td>确认密码:</td>
	    <td><span class="errorMessage"><input type="password" id="div_password2"  size="24" maxlength="32" onkeyup="hrm.common.checkPassword(this);"></span></td>
	  	</tr>
	  	<tr><td>&nbsp;</td></tr>
	  	<tr><td colspan="3" align="center">
	  	<input type="button" onclick="makeRandomPassword();" value="随机密码">
	    <input type="button" onclick="makeChoice();" value="提交">
	    <input type="button" onclick="closePassword();" value="取消">
	  	</td></tr>
	</table>
	<iframe scrolling="no" style="position:absolute;z-index:-1;width:308px;height:114px;top:0px;left:0px;" frameborder="0" ></iframe>
</div>

<div id="dlgModifyUserRole" style="width:310;" class="prompt_div_inline">
	<table width="100%" cellspacing="0" cellpadding="0" class="formtable">
		<s:if test="roleList != null">
			<s:iterator value="roleList" status="index">
			<td width ="8%" ><input class="checkbox" type="checkbox" id="roleid_<s:property value='roleNo'/>" name="uiRole" value="<s:property value='roleNo'/>" /><s:property value="roleName" /></td>
			<s:if test="#index.modulus(2)==0">
			<tr>
			</s:if>
			<s:if test="#index.last==true">
			<tr>
			</s:if>
			</s:iterator>
		</s:if>
		<tr>
			<td align ="right"><input type="button" onclick="checkUserRole()" value="提交"></td>
			<td align="left"><input type="button" onclick="closeUserRole()" value="取消"></td>
		</tr>
	</table>
	<iframe scrolling="no" style="position:absolute;z-index:-1;width:308px;height:198px;top:0px;left:0px;" frameborder="0" ></iframe>
</div>

<script type="text/javascript" language="javascript">
// 初始化dialog；
hrm.common.initDialog('dlgModifyPassword', 300);
hrm.common.initDialog('dlgModifyLimit', 500);
hrm.common.initDialog('dlgModifyUserRole', 300);

var nameR;
// 显示修改角色对话框；
function showUserRole_dlg(uId,name){
	nameR = name;
	clearRolesDiv();

	$('#dlgModifyUserRole').dialog({title:"修改角色(" + name + ")" .trim()});
	hrm.common.openDialog('dlgModifyUserRole');
	getUserRoles(uId);
}
	
// 关闭修改角色对话框；
function closeUserRole(){
	clearRolesDiv();
	hrm.common.closeDialog('dlgModifyUserRole');
}

// 角色修改检查；
function checkUserRole(){
	submitRoles(nameR);
	clearRolesDiv();
	hrm.common.closeDialog('dlgModifyUserRole');
}

function getUserRoles(userId){
	UserId = userId;
	var roleId='user_roles_'+userId;
	var roles=$('#'+roleId).val();
	var rol_arr=roles.split(',');
	for(i = 0;i< rol_arr.length;i++){
	    if(document.getElementById('roleid_'+rol_arr[i]) != null)
	    	document.getElementById('roleid_'+rol_arr[i]).checked = true;
	}
}

//修改角色
var nameOR = "";
function submitRoles(nameR){
	nameOR = nameR;
    var allRoles = document.getElementsByName('uiRole');
    var user_roles = "";
    var flag = -1;
    for(i=0;i<allRoles.length;i++)
        if(allRoles[i].checked == true)flag=i;
    if(flag ==-1){
	    alert("必须选择一个角色！");
	    return;
    }
    if(confirm ("您要修改此用户的角色吗？")){
    for(j=0;j<allRoles.length;j++){
        if(allRoles[j].checked == true){
            if(j != flag){user_roles = user_roles + allRoles[j].value + ',';}
            else{user_roles = user_roles + allRoles[j].value;}
        }
    }
    SubmitUserRoles.updateUserRoles(UserId,user_roles,callback_role);  
	}
    
	function callback_role(userroles) {
		if(userroles=="no"){
		 errMsg("errMsg","修改失败，此用户为唯一的系统管理员，修改后会导致系统无人能管理！");
		}else if(userroles=="error"){
	     errMsg("errMsg","修改用户("+nameOR+")角色失败！");
	    }else if(userroles=="noauth"){
	     errMsg("errMsg","您没有权限执行此操作！");
	    }else if(userroles=="pro"){
	     errMsg("errMsg","TYHRMXXX属于受保护的用户，Demo版不能操作！");
	    }else if(userroles=="roleOver"){
	     errMsg("errMsg","此用户所关联的角色过多！");
	    }else {
	     $('#user_roles_'+UserId).val(userroles);
	     successMsg("errMsg","修改用户("+nameOR+")角色成功。");
	    } 
	}
}

function clearRolesDiv(){
    var _e=document.getElementsByTagName('input');
    for(i=0;i<_e.length;i++){
		if(_e[i].type=='text'){
			_e[i].value='';	
		}
		if(_e[i].type=='checkbox'){
			_e[i].checked=false;
		}
    }
}

var nameP = null;
var userId = null;
var status = 0;
var macLimit = '00-00-00-00-00-00';
var oldUserName = '';
// 显示修改密码窗口；
function showModifyPassword_dlg(id,name,uId){
	userId = id;
	oldUserName = $("#newUserName"+userId).html();
	nameP  = uId;
	
	$("#div_name").val(oldUserName);
	$("#div_password").val('');
	$("#div_password2").val('');
	$('#div_name').next();
	hrm.common.appendEnd('div_name','');
	hrm.common.appendEnd('div_password','');
	hrm.common.appendEnd('div_password2','');
	
	$("#dlgModifyPassword").dialog({title:"修改用户名密码(" + uId + ")".trim()});
	hrm.common.openDialog('dlgModifyPassword');
}

// 产生随机密码；
function makeRandomPassword(){
	$("#password").val(hrm.common.randomStr(6,true,true,true));
	$("#div_password").val($("#password").val());
	$("#div_password2").val($("#password").val());
}

//提交修改密码；
function makeChoice(){
	 var newName=$("#div_name").val();
	 var newPassword = $("#div_password").val();
	 var newPassword2 = $("#div_password2").val();
	 
	 if(hrm.common.isNull(newName)){
	    hrm.common.appendEnd('div_name','必填项！');
	    return;
	 }
	 
	 if(hrm.common.isNull(newPassword) || hrm.common.isNull(newPassword2)){
		if(hrm.common.isNull(newPassword)){
			  hrm.common.appendEnd('div_password','必填项！');
		}else{hrm.common.appendEnd('div_password','');}
		
		if(hrm.common.isNull(newPassword2)){
			  hrm.common.appendEnd('div_password2','必填项！');
		}else{hrm.common.appendEnd('div_password2','');}
		
		return;
	}

	if(newPassword.length<4 || newPassword.length>32) {
		hrm.common.appendEnd('div_password','至少4位！');
		return;
	}
	 
	if(newPassword != newPassword2){
	    hrm.common.appendEnd('div_password','');
	    hrm.common.appendEnd('div_password2','密码不一致！');
	    return;
	}else{
	    hrm.common.appendEnd('div_password2','');
	}
	var mention = '修改密码是否同时发送通知邮件？';
	var value = getSelect(mention);
	if(value==null || value==undefined || value=='cancel'){
	    return;
	}
	if(value!=null && value=='yes'){
	    checkPassword(1);
	}
	if(value!=null && value=='no'){
	    checkPassword(0);
	}
}

// 选择是否需要发送邮件；
function getSelect(mention){
	var value = showModal('../examin/dialogPage.jsp', mention, 'dialogWidth:300px;dialogHeight:120px;dialogLeft:350;dialogTop:320;scrollbars:0;status:no;location:no;');
	return value;
}
function showModal(sURL,vArgs,sFeatures){
   if (window.showModalDialog){
       return window.showModalDialog(sURL,vArgs,sFeatures);
   }else{  
       var ret = window.open(sURL,vArgs,sFeatures);
       window.onfocus=function (){ret.focus()}; 
       return ret;
   }
}

// 修改密码操作；
function checkPassword(param){
	 modifyUserPassword(userId,nameP,param);
	 hrm.common.closeDialog('dlgModifyPassword');
}

//修改密码
var  userName ='';
function modifyUserPassword(userId,nameP,str){
    UserId = userId;
    userName= $("#div_name").val();
    var pwd = $("#div_password").val();
    
    if(userId != "" && pwd != ""&&(pwd.length<4||pwd.length>32)){
	    alert ("密码长度必须4－32位！")
	    return;
    }   
    if(userId != "" && pwd != ""){
           ModifyPassword.updateUserPassword(userId,pwd,userName,oldUserName,str,callback_password);
    }
}

function callback_password(info) {
	if(info=="noauth"){
	    errMsg("errMsg","您没有权限！");
	}else if(info=="nouser"){
        errMsg("errMsg","没有此用户！");
    }else if(info=="demo"){
        errMsg("errMsg","TYHRMXXX属于受保护的用户，Demo版不能操作！");
    }else if(info =="success"){
        successMsg("errMsg","修改"+nameP+"用户密码成功。");
        $("#newUserName"+userId).html(userName);
    }else if(info=="error"){
        errMsg("errMsg","修改"+nameP+"用户密码失败！");
    }else if(info=="exist"){
        errMsg("errMsg","修改"+nameP+"用户名和密码失败，该用户名已经存在数据库中！");
    }
}

function closePassword(){
	$("#div_password").val("");
	$("#div_password2").val("");
	hrm.common.closeDialog('dlgModifyPassword');
}

//设置登录限制；
function showModifyLimit_dlg(id,uId){
	userId = id;
	nameP  = uId;
	var seeMessage = $("#user_limit_"+id).val();
	var message = seeMessage.split("$");
	status = message[0];
	macLimit = message[2];
	$("#user_uiIpRestrict").val(message[1]);
	
	$('#dlgModifyLimit').dialog({title:"修改限制(" + uId + ")".trim()});
	hrm.common.openDialog('dlgModifyLimit');
	
	if(status == 0){
		$("#user_uiLevelRestrict0").attr('checked', true);
		$("#user_uiLevelRestrict1").attr('checked', false);
		$("#user_uiLevelRestrict2").attr('checked', false);
		$("#user_uiLevelRestrict3").attr('checked', false);
		$("#user_uiIpRestrict").attr('disabled', true);
	}else if(status == 1){
		$("#user_uiLevelRestrict0").attr('checked', false);
		$("#user_uiLevelRestrict1").attr('checked', true);
		$("#user_uiLevelRestrict2").attr('checked', false);
		$("#user_uiLevelRestrict3").attr('checked', false);
		$("user_uiIpRestrict").attr('disabled', true);
	}else if(status == 2){
		$("#user_uiLevelRestrict0").attr('checked', false);
		$("#user_uiLevelRestrict1").attr('checked', false);
		$("#user_uiLevelRestrict2").attr('checked', true);
		$("#user_uiLevelRestrict3").attr('checked', false);
		$("#user_uiIpRestrict").attr('disabled', true);
	}else if(status == 3){
		$("#user_uiLevelRestrict0").attr('checked', false);
		$("#user_uiLevelRestrict1").attr('checked', false);
		$("#user_uiLevelRestrict2").attr('checked', false);
		$("#user_uiLevelRestrict3").attr('checked', true);
		$("#user_uiIpRestrict").attr('disabled', true);
	}
}

function closeLimit(){
	hrm.common.closeDialog('dlgModifyLimit');
}

// 修改访问限制；
function limitUpdate(){
    var ip=$("#user_uiIpRestrict").val();
	if(status == 1 && !hrm.common.checkIP(ip)){
	  return ;
	}else if(status == 2 && macLimit.length != 17){
	  return ;
	}else if(status == 3 && (macLimit.length != 17 || !hrm.common.checkIP(ip))){
	  return ;
	}
    SubmitUserRoles.doLimitUpdate(userId,status,ip,macLimit,callback_limit);     
}
function callback_limit(strs) {
	if(strs == "yes"){    
		$('#user_limit_'+userId).val(status+'$'+$("#user_uiIpRestrict").val()+'$'+macLimit);  
		var limitString;
		if(status == 0) {
			limitString = "无限制";
		}else if(status == 1) {
			limitString = "限制IP";
		}else if(status == 2) {
			limitString = "限制机器";
		}else {
			limitString = "IP或机器";
		}
		$('#limitSpan'+userId).html(limitString);
		successMsg("errMsg","修改用户("+nameP+")限制成功。"); 
	}
	else if(strs == "error"){
		errMsg("errMsg","修改用户("+nameP+")限制失败！");
	}else if(strs == "noauth"){
		$('#user_limit_'+userId).val(status+'$'+$("user_uiIpRestrict").value+'$'+macLimit);
		errMsg("errMsg","您无权执行此操作！");
	}else{
	    errMsg("errMsg","修改用户("+nameP+")限制失败！");
	} 
	closeLimit();  
}


//访问限制种类选择；
function selectIPorMAC(str){
	var level = str;
	if(level == 0){
		$("#user_uiIpRestrict").attr("disabled", true);
		$("#user_uiLevelRestrict1").attr("checked", false);
		$("#user_uiLevelRestrict2").attr("checked", false);
		$("#user_uiLevelRestrict3").attr("checked", false);
		status = 0;
	}else if(level == 1){
		$("#user_uiIpRestrict").attr("disabled", false);
		$("#user_uiLevelRestrict0").attr("checked", false);
		$("#user_uiLevelRestrict2").attr("checked", false);
		$("#user_uiLevelRestrict3").attr("checked", false);
		status = 1;
	}else if(level == 2){
		$("#user_uiIpRestrict").attr("disabled", true);
		macLimit = '00-00-00-00-00-00';
		$("#user_uiLevelRestrict1").attr("checked", false);
		$("#user_uiLevelRestrict0").attr("checked", false);
		$("#user_uiLevelRestrict3").attr("checked", false);
		status = 2;
	}else if(level == 3){
		$("#user_uiIpRestrict").attr("disabled", false);
		macLimit = '00-00-00-00-00-00';
		$("#user_uiLevelRestrict1").attr("checked", false);
		$("#user_uiLevelRestrict0").attr("checked", false);
		$("#user_uiLevelRestrict2").attr("checked", false);
		status = 3;
	}
}

function addUserClicked(){
    document.getElementById("addNewUserForm").submit();
}

function addMultiUserClicked(){
    document.getElementById("addNewMultiUserForm").submit();
}

// 修改状态
var nameOS = "";
function changeStatus(userId,nameS){
    UserId = userId;
    nameOS = nameS;
    var userStatus = $('#user_status_'+userId).val();
    if(userStatus == 1){
        if (confirm("您确定要停用该用户吗？")){ 
            ChangeStatus.updateUserStatus(userId,0,callback_status);
		  }
    }else{
        if (confirm("您确定要启用该用户吗？")){ 
            ChangeStatus.updateUserStatus(userId,1,callback_status);
		  }
    }
}

function callback_status(status) {
    if(status == 0){
    	 $('#f_uiStatus'+UserId).html("停用");
         $('#user_status_'+UserId).val(status);
         successMsg("errMsg","关闭用户("+nameOS+")成功。");
    }else if(status == 1){
        $('#f_uiStatus'+UserId).html("启用");
        $('#user_status_'+UserId).val(status);
        successMsg("errMsg","激活用户("+nameOS+")成功。");
    }else if(status == 2){
    	 errMsg("errMsg","不能修改您自己的状态！");
    }else if(status == 3){
    	 errMsg("errMsg","这是最后一个管理员，不能停用！");       
    }else if(status == 4){
    	 errMsg("errMsg","您没有权限执行此操作！");       
    }else if(status == 5){
    	 errMsg("errMsg","修改用户("+nameOS+")状态失败！");       
    }else if(status == 6){
    	 errMsg("errMsg","TYHRMXXX属于受保护的用户，Demo版不能操作！");       
    }      
}

function deleteUserinfo(userId){
    if (confirm("您确定要删除吗？")){
      $('#userDelId').val(userId);
	  document.getElementById('pageDel').submit();
    }
}
 
function splits(var1){
 $('#operate').val(var1);
 document.forms[0].submit();
}

function searchUserList(){
	$('#role').val($('#selectRole').val());
	document.getElementById('pageForm').submit();
}

function resetUserList(){
	$('#role').val('');
	$('#id').val('');
	$('#depart').val('');
	$('#manager').val('');
	document.getElementById('pageForm').submit();
}
</script>
</body>
</html>