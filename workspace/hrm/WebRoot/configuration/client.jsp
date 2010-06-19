<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<%@ taglib prefix="jscalendar" uri="/jscalendar"%>
<html>
	<head>
		<script type="text/javascript" src="../dwr/interface/ModifyPassword.js"></script>
		<script type="text/javascript" src="../dwr/interface/ActiveClient.js"></script>
		<title>公司注册信息</title>
	</head>
<body onload = "setDateAndCount();">
	<s:component template="bodyhead">
		<s:param name="pagetitle" value="'公司注册信息'" />
		<s:param name="helpUrl" value="'62'" />
	</s:component>
	
	<s:form id="updateclient" name="updateclient" namespace="/configuration" action="updateClient" method="post">
		<s:token />
		<s:hidden id ="clientStatus" name ="clientStatus" />
		<s:hidden id ="clientDate" name ="clientDate" />
		<s:hidden id ="clientCount" name ="clientCount" />
		
		<TABLE width="100%" align="left">
			<tr>
			<s:textfield id="oldClientNo" label="公司编号" name="client.id" required="true" readonly="true" cssClass="textDisabled" size="30" />
			<s:if test ="clientStatus == 'demo'">
　　　　　　　　　  <s:textfield id="cStatus" label="产品状态" name ="clientStatus"  value="未注册" readonly="true" cssClass="textDisabled" />
			</s:if>
			<s:if test ="clientStatus == 'regist'">
　　　　　　　　　　<s:textfield id="cStatus" label="产品状态" name ="clientStatus" value="已注册" readonly="true" cssClass="textDisabled" />
			</s:if>
			<s:if test ="clientStatus == 'active'">
　　　　　　　　　　<s:textfield id="cStatus" label="产品状态" name ="clientStatus" value="已激活" readonly="true" cssClass="textDisabled" />
			</s:if>
			<s:if test ="clientStatus == 'exception'">
　　　　　　　　　 <s:textfield id="cStatus" label="产品状态" name ="clientStatus" value="激活失败" readonly="true" cssClass="textDisabled" />
			</s:if>
			</tr>
			
			<tr>
			<s:textfield id="clientName" label="公司名称" required="true" name="client.clientName" size="30" maxlength="128"/>
			<s:if test ="clientStatus == 'active'">
			<s:textfield id="clientActivateTime" label="激活时间"  readonly="true"  cssClass="textDisabled"/>
			</s:if>
			<s:elseif test="clientStatus != 'active'">
			<s:textfield id="clientActivateTime" label="注册时间"  readonly="true"  cssClass="textDisabled"/>
			</s:elseif>
			</tr>
			
			<tr>
			<s:textfield id="clientShortName" label="公司简称" name="client.clientShortName" size="30" maxlength="64"/>
			<s:textfield id="clientServiceMonths" label="服务月份" name="client.clientServiceMonths" readonly="true" cssClass="textDisabled" />
			</tr>
			
			<tr>
			<s:textfield id="clientAddress" label="公司地址" name="client.clientAddress" size="30" maxlength="128"/>
			<s:textfield id="clientServiceTimes" label="服务次数" name="client.clientServiceTimes"  readonly="true" cssClass="textDisabled" />
			</tr>
			
			<tr>
			<s:textfield id="clientZip" label="公司邮编" name="client.clientZip" size="30" maxlength="8"/>
			<s:textfield id="empLimit" label="限制员工人数" name="empLimit" readonly="true" cssClass="textDisabled" />
			</tr>
			
			<tr>
			<s:textfield id="clientPhone" label="公司联系电话" required="true" name="client.clientPhone" size="30" maxlength="32"/>
			<s:textfield id="userLimit" label="限制用户人数" name="userLimit" readonly="true" cssClass="textDisabled" />
			</tr>
			
			<tr>
			<s:textfield id="clientFax" label="公司传真" name="client.clientFax" size="30" maxlength="32"/>
			<s:if test ="(clientStatus == 'active' ||clientStatus = 'regist') && userLimitADM > 0">
			<s:textfield id="userLimitADM" label="其中HR用户组" name="userLimitADM" readonly="true" cssClass="textDisabled" />
			</s:if>
			<s:elseif test ="clientDate !='none' && clientCount !='none'">
　　　　　　　　　  <s:textfield id="cDate" label="最晚可用日期" name ="clientDate"   readonly="true" cssClass="textDisabled" />
			</s:elseif>
			<s:elseif test="clientDate !='none'">
			<s:textfield id="cDate" label="最晚可用日期" name ="clientDate"  readonly="true" cssClass="textDisabled" />
			</s:elseif>
			<s:elseif test="clientCount !='none'">
			<s:textfield id="cCount" label="限制使用次数" name ="clientCount"  readonly="true" cssClass="textDisabled" />
			</s:elseif>
			</tr>
			
			<tr>
			<s:textfield id="clientContactName" label="公司联系人" required="true" name="client.clientContactName" size="30" maxlength="64"/>
			<s:if test ="(clientStatus == 'active' ||clientStatus = 'regist') && userLimitMGR > 0">
			<s:textfield id="userLimitMGR" label="经理自助用户组" name="userLimitMGR" readonly="true" cssClass="textDisabled" />
			</s:if>
			<s:elseif test ="clientDate !='none' && clientCount !='none'">
　　　　　　　　　  <s:textfield id="cCount" label="限制使用次数" name ="clientCount"  readonly="true" cssClass="textDisabled" />
			</s:elseif>
			</tr>
			<tr>
			<s:textfield id="clientEmail" label="公司EMAIL" required="true" name="client.clientEmail" size="30" maxlength="64"/>
			<s:if test ="(clientStatus == 'active' ||clientStatus = 'regist') && userLimitEMP > 0">
			<s:textfield id="userLimitEMP" label="员工自助用户组" name="userLimitEMP" readonly="true" cssClass="textDisabled" />
			</s:if>
			</tr>
			<tr>
			<td align="right">公司备注：</td>
			<td colspan ="3">	<s:textarea id="clientRemarks" 
					name="client.clientRemarks" cols="50" rows="4" /></td>
			</tr>
			<s:if test = "#session.loginModel!='alimt'">
			<tr>
				<td></td>
				<td>
					<table>
						<tr>
						 <s:if test="client.clientStatus == 0 || client.clientStatus == 7">
							<td height="1" colspan="3">
								<input id="sub" name="sub" type="submit" value="注册">
							</td>
							</s:if>
							<td >
								<input id="active" name="active" type="button" value="激活"
									onclick="showActive_div();">
							</td>
							<td  colspan="3">
								<input id="testConnect" name="testConnect" type="button"
									value="测试连接" onclick="tryConnect();">
							</td>
						</tr>
					</table>
				</td>
			</tr>
			</s:if>
		</TABLE>
	</s:form>
	
	<div id="dlgActiveDiv" class="prompt_div_inline">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="formtable">
			<tr><td>&nbsp;</td></tr>
			<tr><td>用户编号:</td>
			<td><span class="errorMessage"><input type="textfield" name="clientNo" id="clientNo" size="24" maxlength="32"></span></td>
			</tr>
			
			<tr><td>激活码:</td>
			<td><span class="errorMessage"><input type="textfield" name="clientSerial" id="clientSerial" size="24" maxlength="36"></span></td>
			</tr>
			
			
			<tr>
				<td colspan="2" align="center">
					<input type="button" onclick="checkActive();" value="激活">
					<input type="button" onclick="hrm.common.closeDialog('dlgActiveDiv');" value="取消">
				</td>
			</tr>
		</table>
		<iframe scrolling="no"
			style="position:absolute;z-index:-1;width:308px;height:114px;top:0px;left:0px;"
			frameborder="0"></iframe>
	</div>

<script type="text/javascript" language="javascript">
// 初始化dialog；
hrm.common.initDialog('dlgActiveDiv', 320);

document.getElementById("clientActivateTime").value = '<s:date name="client.clientActivateTime" format="yyyy-MM-dd HH:mm:ss" />';
function showActive_div(){
	$("#clientNo").val("");
	$("#clientSerial").val("");
	$('#dlgActiveDiv').dialog('option', 'title', '功能激活');
	hrm.common.openDialog('dlgActiveDiv');
}

// 初始化时间、月份和服务次数；
function setDateAndCount(){
	if($("#clientDate").val() != "none")
		$("#cDate").val($("#clientDate").val());
	if($("#clientCount").val() != "none")
		$("#cCount").val($("#clientCount").val());
}

// 测试连接；
function tryConnect(){
    ActiveClient.testConnection(callback_status);    
}
function callback_status(str) {
    if(str == "yes"){
        successMsg("errMsg","测试连接成功。");
    }else {
        errMsg("errMsg","测试连接失败！");
    }
}

// 激活操作；
function checkActive(){
	var clientNo = $("#clientNo").val();
	var clientSerial = $("#clientSerial").val();
	if(isNull(clientNo) || isNull(clientSerial)){
		if(isNull(clientNo)){
			hrm.common.appendEnd('clientNo','必填项！');
		}
		if(isNull(clientSerial)){
			hrm.common.appendEnd('clientSerial','必填项！');
		}
		return;
	}
   	ActiveClient.activeOperate(clientNo,clientSerial,callback_active);
   	hrm.common.closeDialog('dlgActiveDiv');
}
function callback_active(str) {
    if(str == "yes"){
        successMsg("errMsg","激活成功，请重新登录。");
    }else if(str == ":AR101"){
        errMsg("errMsg","该序列号已经被注册，请购买正版软件，或者和365hrm客服联系，电话021-50905715！");
    }else if(str == "demo"){
        errMsg("errMsg","demo版本不能执行此功能，请购买正版软件，或者和365hrm客服联系，电话021-50905715！");
    }else if(str == ":S901"){
        errMsg("errMsg","序列号错误，请重新输入，如果确定输入无误，请和365hrm客服联系，电话021-50905715！");
    }else if(str == ":A801"){
        errMsg("errMsg","激活失败，请重试！");
    }else if(str == ":C101"){
        errMsg("errMsg","链接服务器失败，服务器正忙或网络连接异常，请稍后重试！");
    }else if(str == ":NA301"){
        errMsg("errMsg","您未被授权注册，请请购买正版软件，或者和365hrm客服联系，电话021-50905715！");
    }else {
        errMsg("errMsg","激活失败，请重试！");
    }
}
</script>
</body>
</html>
	