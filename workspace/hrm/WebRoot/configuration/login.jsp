<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=GBK"	pageEncoding="GBK"%>
<%@ page import="java.net.InetAddress" %>
<%@taglib prefix="s" uri="webwork"%>
<script type="text/javascript" src="../resource/js/hrm/common.js"></script>
<html>
<head>


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

<title>登录</title>
</head>
<body onload="onloadFunction();"> 
<table border=0 cellpadding=0 cellspacing=0 width=100% height=80%> 
<tr><td width=100% align=center valign=center> 

<table width="594" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><img src="../resource/images/login_top.gif" width="594" height="59" /></td>
  </tr> 
  <tr>
    <td background="../resource/images/login_bg.gif">
    <form id = "loginForm" name="loginForm" action="dologin_single.action" method="post" namespace="/configuration">
       <s:hidden id = "macAddress" name="macAddress"/>
       
		<table align="center" class="formtable" cellpadding="5" width="50%">
			<tr>
				<td colspan="3" align="center">
				<span class="errorMessage">
				<s:iterator value="actionErrors">
					<s:property />
				</s:iterator></span></td>
			</tr>
				<tr>
					<td class="tdLabel" align="right" nowrap="nowrap">
						<label for="user_uiUsername" class="label">用户名<span class="required">*</span>:</label>
					</td>
					<td>
						<s:textfield id="name" name="user.uiUsername" required="true" size="20"/>
					</td>
				</tr>
				<tr>
					<s:password  name="uiPassword" showPassword="true" label="密码"  required="true" size="21"/>
				</tr>
			
			<tr>	
				<td colspan="3" align="center"><input id="login" type="submit" onclick="return checkMac();" value=" 登 录 ">&nbsp;&nbsp;&nbsp;
				<input type="reset" value=" 重 置 "/></td>
			</tr>
			
			<%	
			   String remote = null;
			   try{	
				  remote = request.getRemoteAddr();
				
				  if(remote == null) remote ="noIp";
				
				  else if("127.0.0.1".equals(remote))
				  {
				    remote = InetAddress.getLocalHost().toString().substring(InetAddress.getLocalHost().toString().lastIndexOf("/")+1) ;
				  }
				  else remote = "clientIp";
				} catch(Exception e){ remote ="noIp"; }
            %>
		</table>
		<table align="center" class="formtable" cellpadding="5" width="50%"/>            
		            <tbody id="localTable"></tbody>
		 </table>
	</form>
	</td>
  </tr>
  <tr>
    <td><img src="../resource/images/login_bot.gif" width="594" height="12" /></td>
  </tr>
</table>
</td>
</tr> 
</table>
<script language="javascript">
     var allowLogin = '1';
	/*******************************************************
     * author: jiangtao
     * date  : 2008.04.24
     * reason: 解决计算机名为中文问题。
     * begin :***********************************************/
	function onloadFunction(){
	   var completeBody = document.getElementById("localTable");
	   document.getElementById("name").focus();
	   var localIp = "<%=remote%>";
	   if(localIp=='clientIp'){ 
          allowLogin='1';
          return;
       }
	   else if(localIp=='noIp'){
           var tr1 = document.createElement("tr");
	       var td1 = document.createElement("td");
	       td1.innerHTML="<input type='textfield' name='url' id='url' value='http://0.0.0.0:8080/hr/' size='33' />";
	       td1.setAttribute("align","center");
           tr1.appendChild(td1);

	       var tr2 = document.createElement("tr");
	       var td2 = document.createElement("td");
	       td2.innerHTML="<span style='cursor: pointer'><font color ='red'>您的机器名称为中文，请改为英文！</font></span>";
           td2.setAttribute("align","center");
	       tr2.appendChild(td2);
	       completeBody.appendChild(tr1);
           completeBody.appendChild(tr2);
           allowLogin ='1';	       
	   }
	   else{
           allowLogin ='1';
	       var tr1 = document.createElement("tr");
	       var td1 = document.createElement("td");
	       td1.innerHTML="<input type='textfield' name='url' id='url' value='http://"+localIp+":8080/hr/' size='33' onclick='doCopy();'/>";
	       tr1.appendChild(td1);
           td1.setAttribute("align","center");
	       var tr2 = document.createElement("tr");
	       var td2 = document.createElement("td");
	       td2.innerHTML="<span style='cursor: pointer'><font color ='blue'>请复制上面的登录URL，通过email、MSN或QQ传送给局域网中其他用户使用</font></span>";
           td2.setAttribute("align","center");
	       tr2.appendChild(td2);
	       completeBody.appendChild(tr1);
	       completeBody.appendChild(tr2);
       }
	}
	/* end  :*******************************************************/
	
	function doCopy(){
		if(window.clipboardData){
			window.clipboardData.setData('Text',document.getElementById('url').value);
		}
	}
		
	function checkMac(){
		if(allowLogin =='0') return false;
		var myName = document.getElementById("name").value;
		
		if(myName == null||myName == '')return false;
		
		var theName = myName +'macAddress';
		
	    var oldMac =  hrm.common.getCookie(theName);
	    
	   
	    var generateMac = document.getElementById("macAddress").value;
	    
	    if(generateMac !=null && generateMac.length == 17 && oldMac == null){
	    	hrm.common.setCookie(theName,generateMac,1068,'/');
	    }
	    var mac = hrm.common.getCookie(theName);
	   
	    document.getElementById("macAddress").value = mac;
	    
	    return true;
	}			
	// 刷新父页面，如果已经登出，用户仍旧调用数据导入功能，出错时只刷新隐藏页面，始终为“正在处理”，看不到登陆页面
	if(parent.document!=document && parent.document.location.host==document.location.host){
		parent.document.location.href=parent.document.location.href;
	}
</script>

</body>
</html>