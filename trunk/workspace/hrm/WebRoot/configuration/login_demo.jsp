<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@taglib prefix="s" uri="webwork"%>

<html>
<head>
<%  response.setHeader("Pragma","No-cache"); 
	response.setHeader("Cache-Control","no-cache"); 
	response.setDateHeader("Expires",0); 
%> 
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
<body>
<br />
<br />
<br />
<table border=0 cellpadding=0 cellspacing=0 width=100% height=80%> 
<tr><td width=100% align=center valign=center> 
<table width="594" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><img src="../resource/images/login_top.gif" width="594" height="59" /></td>
  </tr>
  <tr>
    <td background="../resource/images/login_bg.gif"><form name="loginForm" action="dologin_demo.action" method="post" namespace="/configuration">
<table align="center" class="formtable" cellpadding="5" width="50%">
	<tr>
		<td colspan="3" align="center">
		<span class="errorMessage">
		<s:iterator value="actionErrors">
			<s:property />
		</s:iterator></span></td>
	</tr>
	<tr>
		<td align="left">试用角色：</td>
		<td>
		<select  id="login_user" onclick="fill()">
			<option value="tyhrm0021">HR经理</option>
			<option value="tyhrm0001">总经理</option>		
			<option value="tyhrm0048">技术总监</option>
			<option value="tyhrm0049">产品经理</option>
			<option value="tyhrm0045">HR员工管理</option>
			<option value="tyhrm0007">HR薪资</option>
			<option value="tyhrm0002">HR招聘</option>
			<option value="tyhrm0034">项目经理(招聘/调薪)</option>
			<option value="tyhrm0032">项目组长(招聘/调薪)</option>
			<option value="tyhrm0031">项目组长</option>
			<option value="ali">ali试用</option>
			<option value="tyhrm0015">普通员工01</option>
			<option value="tyhrm0026">普通员工02</option>
			<option value="tyhrm0020">普通员工03</option>
			<option value="tyhrm0030">普通员工04</option>
		</select>
		</td>
	</tr>
    
	<tr>
		<td>用户名<font color="red">*</font>：</td>
		<td>
		<s:textfield id="user.uiUsername" name="user.uiUsername" value="tyhrm0021" size="20" required="true"/>
		</td>
	</tr>
	<tr>
		<td>密 码<font color="red">*</font>：</td>
		<td><s:password  id="uiPassword" name="uiPassword" showPassword="true" value="tengyuan"required="true" /></td>
	</tr>
	<script language="javascript">
        var  url=this.location.search;   
        var str ;
        if(url.indexOf("?")!=-1)   
        {   
 			 str = url.substr(1)   
  			 strs = str.split("&");   
             str =unescape(strs[0].split("=")[1]); 
         }
         if(str == "ali"){
            var zeroObj = document.getElementById("login_user");
            var value = zeroObj.options[0].value;
            var text = zeroObj.options[0].text;
            zeroObj.options[0].value = zeroObj.options[10].value;
            zeroObj.options[0].text = zeroObj.options[10].text;
            zeroObj.options[10].value = value;
            zeroObj.options[10].text = text;
            document.getElementById("user.uiUsername").value="ali";
            document.getElementById("uiPassword").value="tengyuan";
         }  
         
    </script>
	<tr>	
		<td><label>验证码<font color="red">*</font>：</label></td>	
		<td colspan="2"><s:textfield name="checkCode" required="true" size="8" maxlength="4"/>&nbsp;<img src="../servlet/CheckCodeServlet"/></td>	
	</tr>
	<tr>	
		<td colspan="3" align="center"><input type="submit" value=" 登 录 ">&nbsp;&nbsp;&nbsp;&nbsp;<input type="reset" value=" 重 置 "/></td>
	</tr>
</table>
</form></td>
  </tr>
  <tr>
    <td><img src="../resource/images/login_bot.gif" width="594" height="12" /></td>
  </tr>
</table>
<td>
</tr> 
</table>

<script type="text/javascript">
function fill(){
	// set user name
	var userName = document.getElementById("login_user").value;
	document.getElementsByName("user.uiUsername")[0].value = userName;
	
	// set password
	document.getElementsByName("uiPassword")[0].value="tengyuan";
	
}
</script>
</body>
</html>