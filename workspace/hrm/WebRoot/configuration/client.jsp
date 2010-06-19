<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<%@ taglib prefix="jscalendar" uri="/jscalendar"%>
<html>
	<head>
		<script type="text/javascript" src="../dwr/interface/ModifyPassword.js"></script>
		<script type="text/javascript" src="../dwr/interface/ActiveClient.js"></script>
		<title>��˾ע����Ϣ</title>
	</head>
<body onload = "setDateAndCount();">
	<s:component template="bodyhead">
		<s:param name="pagetitle" value="'��˾ע����Ϣ'" />
		<s:param name="helpUrl" value="'62'" />
	</s:component>
	
	<s:form id="updateclient" name="updateclient" namespace="/configuration" action="updateClient" method="post">
		<s:token />
		<s:hidden id ="clientStatus" name ="clientStatus" />
		<s:hidden id ="clientDate" name ="clientDate" />
		<s:hidden id ="clientCount" name ="clientCount" />
		
		<TABLE width="100%" align="left">
			<tr>
			<s:textfield id="oldClientNo" label="��˾���" name="client.id" required="true" readonly="true" cssClass="textDisabled" size="30" />
			<s:if test ="clientStatus == 'demo'">
������������������  <s:textfield id="cStatus" label="��Ʒ״̬" name ="clientStatus"  value="δע��" readonly="true" cssClass="textDisabled" />
			</s:if>
			<s:if test ="clientStatus == 'regist'">
��������������������<s:textfield id="cStatus" label="��Ʒ״̬" name ="clientStatus" value="��ע��" readonly="true" cssClass="textDisabled" />
			</s:if>
			<s:if test ="clientStatus == 'active'">
��������������������<s:textfield id="cStatus" label="��Ʒ״̬" name ="clientStatus" value="�Ѽ���" readonly="true" cssClass="textDisabled" />
			</s:if>
			<s:if test ="clientStatus == 'exception'">
������������������ <s:textfield id="cStatus" label="��Ʒ״̬" name ="clientStatus" value="����ʧ��" readonly="true" cssClass="textDisabled" />
			</s:if>
			</tr>
			
			<tr>
			<s:textfield id="clientName" label="��˾����" required="true" name="client.clientName" size="30" maxlength="128"/>
			<s:if test ="clientStatus == 'active'">
			<s:textfield id="clientActivateTime" label="����ʱ��"  readonly="true"  cssClass="textDisabled"/>
			</s:if>
			<s:elseif test="clientStatus != 'active'">
			<s:textfield id="clientActivateTime" label="ע��ʱ��"  readonly="true"  cssClass="textDisabled"/>
			</s:elseif>
			</tr>
			
			<tr>
			<s:textfield id="clientShortName" label="��˾���" name="client.clientShortName" size="30" maxlength="64"/>
			<s:textfield id="clientServiceMonths" label="�����·�" name="client.clientServiceMonths" readonly="true" cssClass="textDisabled" />
			</tr>
			
			<tr>
			<s:textfield id="clientAddress" label="��˾��ַ" name="client.clientAddress" size="30" maxlength="128"/>
			<s:textfield id="clientServiceTimes" label="�������" name="client.clientServiceTimes"  readonly="true" cssClass="textDisabled" />
			</tr>
			
			<tr>
			<s:textfield id="clientZip" label="��˾�ʱ�" name="client.clientZip" size="30" maxlength="8"/>
			<s:textfield id="empLimit" label="����Ա������" name="empLimit" readonly="true" cssClass="textDisabled" />
			</tr>
			
			<tr>
			<s:textfield id="clientPhone" label="��˾��ϵ�绰" required="true" name="client.clientPhone" size="30" maxlength="32"/>
			<s:textfield id="userLimit" label="�����û�����" name="userLimit" readonly="true" cssClass="textDisabled" />
			</tr>
			
			<tr>
			<s:textfield id="clientFax" label="��˾����" name="client.clientFax" size="30" maxlength="32"/>
			<s:if test ="(clientStatus == 'active' ||clientStatus = 'regist') && userLimitADM > 0">
			<s:textfield id="userLimitADM" label="����HR�û���" name="userLimitADM" readonly="true" cssClass="textDisabled" />
			</s:if>
			<s:elseif test ="clientDate !='none' && clientCount !='none'">
������������������  <s:textfield id="cDate" label="�����������" name ="clientDate"   readonly="true" cssClass="textDisabled" />
			</s:elseif>
			<s:elseif test="clientDate !='none'">
			<s:textfield id="cDate" label="�����������" name ="clientDate"  readonly="true" cssClass="textDisabled" />
			</s:elseif>
			<s:elseif test="clientCount !='none'">
			<s:textfield id="cCount" label="����ʹ�ô���" name ="clientCount"  readonly="true" cssClass="textDisabled" />
			</s:elseif>
			</tr>
			
			<tr>
			<s:textfield id="clientContactName" label="��˾��ϵ��" required="true" name="client.clientContactName" size="30" maxlength="64"/>
			<s:if test ="(clientStatus == 'active' ||clientStatus = 'regist') && userLimitMGR > 0">
			<s:textfield id="userLimitMGR" label="���������û���" name="userLimitMGR" readonly="true" cssClass="textDisabled" />
			</s:if>
			<s:elseif test ="clientDate !='none' && clientCount !='none'">
������������������  <s:textfield id="cCount" label="����ʹ�ô���" name ="clientCount"  readonly="true" cssClass="textDisabled" />
			</s:elseif>
			</tr>
			<tr>
			<s:textfield id="clientEmail" label="��˾EMAIL" required="true" name="client.clientEmail" size="30" maxlength="64"/>
			<s:if test ="(clientStatus == 'active' ||clientStatus = 'regist') && userLimitEMP > 0">
			<s:textfield id="userLimitEMP" label="Ա�������û���" name="userLimitEMP" readonly="true" cssClass="textDisabled" />
			</s:if>
			</tr>
			<tr>
			<td align="right">��˾��ע��</td>
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
								<input id="sub" name="sub" type="submit" value="ע��">
							</td>
							</s:if>
							<td >
								<input id="active" name="active" type="button" value="����"
									onclick="showActive_div();">
							</td>
							<td  colspan="3">
								<input id="testConnect" name="testConnect" type="button"
									value="��������" onclick="tryConnect();">
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
			<tr><td>�û����:</td>
			<td><span class="errorMessage"><input type="textfield" name="clientNo" id="clientNo" size="24" maxlength="32"></span></td>
			</tr>
			
			<tr><td>������:</td>
			<td><span class="errorMessage"><input type="textfield" name="clientSerial" id="clientSerial" size="24" maxlength="36"></span></td>
			</tr>
			
			
			<tr>
				<td colspan="2" align="center">
					<input type="button" onclick="checkActive();" value="����">
					<input type="button" onclick="hrm.common.closeDialog('dlgActiveDiv');" value="ȡ��">
				</td>
			</tr>
		</table>
		<iframe scrolling="no"
			style="position:absolute;z-index:-1;width:308px;height:114px;top:0px;left:0px;"
			frameborder="0"></iframe>
	</div>

<script type="text/javascript" language="javascript">
// ��ʼ��dialog��
hrm.common.initDialog('dlgActiveDiv', 320);

document.getElementById("clientActivateTime").value = '<s:date name="client.clientActivateTime" format="yyyy-MM-dd HH:mm:ss" />';
function showActive_div(){
	$("#clientNo").val("");
	$("#clientSerial").val("");
	$('#dlgActiveDiv').dialog('option', 'title', '���ܼ���');
	hrm.common.openDialog('dlgActiveDiv');
}

// ��ʼ��ʱ�䡢�·ݺͷ��������
function setDateAndCount(){
	if($("#clientDate").val() != "none")
		$("#cDate").val($("#clientDate").val());
	if($("#clientCount").val() != "none")
		$("#cCount").val($("#clientCount").val());
}

// �������ӣ�
function tryConnect(){
    ActiveClient.testConnection(callback_status);    
}
function callback_status(str) {
    if(str == "yes"){
        successMsg("errMsg","�������ӳɹ���");
    }else {
        errMsg("errMsg","��������ʧ�ܣ�");
    }
}

// ���������
function checkActive(){
	var clientNo = $("#clientNo").val();
	var clientSerial = $("#clientSerial").val();
	if(isNull(clientNo) || isNull(clientSerial)){
		if(isNull(clientNo)){
			hrm.common.appendEnd('clientNo','�����');
		}
		if(isNull(clientSerial)){
			hrm.common.appendEnd('clientSerial','�����');
		}
		return;
	}
   	ActiveClient.activeOperate(clientNo,clientSerial,callback_active);
   	hrm.common.closeDialog('dlgActiveDiv');
}
function callback_active(str) {
    if(str == "yes"){
        successMsg("errMsg","����ɹ��������µ�¼��");
    }else if(str == ":AR101"){
        errMsg("errMsg","�����к��Ѿ���ע�ᣬ�빺��������������ߺ�365hrm�ͷ���ϵ���绰021-50905715��");
    }else if(str == "demo"){
        errMsg("errMsg","demo�汾����ִ�д˹��ܣ��빺��������������ߺ�365hrm�ͷ���ϵ���绰021-50905715��");
    }else if(str == ":S901"){
        errMsg("errMsg","���кŴ������������룬���ȷ�������������365hrm�ͷ���ϵ���绰021-50905715��");
    }else if(str == ":A801"){
        errMsg("errMsg","����ʧ�ܣ������ԣ�");
    }else if(str == ":C101"){
        errMsg("errMsg","���ӷ�����ʧ�ܣ���������æ�����������쳣�����Ժ����ԣ�");
    }else if(str == ":NA301"){
        errMsg("errMsg","��δ����Ȩע�ᣬ���빺��������������ߺ�365hrm�ͷ���ϵ���绰021-50905715��");
    }else {
        errMsg("errMsg","����ʧ�ܣ������ԣ�");
    }
}
</script>
</body>
</html>
	