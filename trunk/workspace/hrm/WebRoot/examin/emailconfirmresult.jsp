<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<html>
<head>
<title>请假加班邮件审批</title>
<style type="text/css">
body {
	background-color: #f9f9f5;
	text-align:center;
}
.formtable td {
	font-size: 12px;height: 20px;
}
.required,.errorMessage {
	color: red;font-weight: normal;text-align: left;
}
</style>
<style type="text/css">
<!--
body {
    font-size: 12px;
    line-height: 1.5;
    text-align: center;
}
.shenpi h1{font-size:14px;border-bottom:1px solid #4fa3d1;width:96%;padding:4px 0px}
.shenpi textarea{width:400px;height:80px}
.shenpi p{text-align:left;color:#999999;width:400px}
.suc {
    width:400px;
    height:50px;
    margin-top:12px;
    padding:24px 0px 0px 0px;
    border:1px solid #5da60f;
    color:#009900;
    font-size:14px;
    background-color: #E4F9CE;
}
.suc ul li{
    list-style-image: url(../resource/images/success.gif);
}
.fail {
    width:350px;
    height:25px;
    margin-top:12px;
    padding:24px;
    border:1px solid #e69a0a;
    color:#e71d02;
    font-size:14px;
    background-color: #fff3ce;
}
.fail ul li{
    list-style-image: url(../resource/images/error1.gif);
}
-->
</style>
<script type="text/javascript" src="dwr/engine.js"></script>
<script type="text/javascript" src="dwr/util.js"></script>
<script type="text/javascript" src="dwr/interface/EmpExaminAction.js"></script>
<script type="text/javascript" src="resource/js/hrm/common.js"></script>
<script type="text/javascript" src="resource/js/error.js"></script>
<script type="text/javascript">
function operat(oper){
	var comments = document.getElementById("comments").value;
	if(oper == 'reject' && (comments == null || comments.length ==0)){
		alert("拒绝时必须填写备注！");
		return;
	}

	var operate = oper;
	var employeeId = '<%=(String)request.getAttribute("employeeId")%>';
	var objId = '<%=(String)request.getAttribute("objId")%>';
	var securityNo = '<%=(String)request.getAttribute("securityNo")%>';
	var flowType = '<%=(String)request.getAttribute("flowType")%>';

	EmpExaminAction.DWRwfObjOpSingle(operate, comments, employeeId, objId, securityNo, flowType, approveCallback);
	function approveCallback(info){
		if(HRMCommon.actionMsgHandler(info)=='SUCC'){
			successMsg("errMsg", "操作成功！");
			document.getElementById("btn_approve").disabled = true;
			document.getElementById("btn_reject").disabled = true;
		}
		return;
	}
}
</script>
</head>
<body>
<table width="594" border="0" cellspacing="0" cellpadding="0" class="shenpi">
  <tr>
    <td><img src="resource/images/login_top.gif" width="594" height="59" /></td>
  </tr>
  <tr><td align="center" id="message"></td></tr>
  <tr>
    <td align="center" background="resource/images/login_bg.gif">
    <%if("leaveRequest".equals((String)request.getAttribute("flowType"))){ %><h1>请假审批</h1><%} %>
    <%if("overtimeRequest".equals((String)request.getAttribute("flowType"))){ %><h1>加班审批</h1><%} %>
    <span id="errMsg"></span>
    <p></p>
          备注：<textarea  id="comments" rows="4" cols="35" name="comments"></textarea>
    <p></p>
    <span>
          <input id="btn_approve" type="button" value=" 同 意 " onclick="operat('approve')" />  
          <input id="btn_reject" type="button" value=" 拒 绝 " onclick="operat('reject')"/>
    </span>
    </td>
  </tr>
  <tr>
    <td><img src="resource/images/login_bot.gif" width="594" height="12" />
    </td>
  </tr>
</table>
</body>
</html>