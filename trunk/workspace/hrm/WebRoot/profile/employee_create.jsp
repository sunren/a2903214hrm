<%@page pageEncoding="GBK" contentType="text/html; charset=GBK" %>
<%@taglib prefix="s" uri="webwork"%>
<%@taglib prefix="ty" uri="/tengsource"%>
<%@taglib prefix="jscalendar" uri="/jscalendar" %>
<!--
 =========================================================
' File: employee_create.jsp
' Version:1.1.0 standard version
' Date: 2007-6-2
' Script Written by tengsource.com
'=========================================================
' Copyright   2007 tengsource.com. All rights reserved.
' Web: http://www.tengsource.com
' Email: admin@tengsource.com
'=======================================================
 -->
<head>
<link href="../resource/css/style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="../resource/css/edit_select.css" />
<!-- DWR�ļ����� -->
<script type="text/javascript" src="../dwr/interface/my.js"></script>
<script type="text/javascript" src="../dwr/interface/EmpAddUpdate.js"></script>
<script type="text/javascript" src="../dwr/interface/EditEmpInit.js"></script>

<script type="text/javascript" src="../resource/js/hrm/profile.js"></script>
<title>����Ա����������</title>
</head>
<body onload="display_practice_yearOrMonth();">

<div id="selectcontent" class="selectdiv" style="visibility:hidden;pixelHeight:20px;z-index:9;">
	<iframe id="selframe" frameborder="0" height="100%"></iframe>
	<div id="selecthtml" class="selectcontent">selectdate</div>
</div>
<!-- �������select -->
<script type="text/javascript" src="../resource/js/edit_select.js"></script>
<link rel="stylesheet" type="text/css" href="../resource/css/edit_select.css" />

<div id="create_emp" align="left">
	<s:component template="bodyhead">
		<s:param name="pagetitle" value="'����Ա���������ϣ�'" />
		<s:param name="helpUrl" value="'31'"/>
	</s:component>
	<s:form name="createEmployee" action="empCreate" namespace="/profile" method="POST" enctype="multipart/form-data">
	    <table width="100%" border="0" cellspacing="0" cellpadding="0">
	        <tr>
				<s:textfield id="empNo" label="Ա�����" name="emp.empDistinctNo" size="20" required="true" maxlength="32" tabindex="1" onkeyup="hrm.profile.createAutoNoOnKeyUp(event,this,'autoCreateEmpNo')"/>
				<s:textfield label="�����ַ" name="emp.empEmail" id="email" size="23" maxlength="64" tabindex="24" onkeyup="hrm.common.notChinese(event,this);" onblur="hrm.common.checkEmail(this);"/>
				<td rowspan="5" colspan="2">
					<s:token/>
					<img border="1" id="showPicture" style="border: 1px darkslategray outset;" name="showPicture" src="../resource/images/None.JPG" align="left" width="88" height="112"/>
				</td>
	        </tr>
		    <%@ include file="employee_form.jsp"%>
		    <tr>
				<s:textfield label="�ʱ�" name="emp.empHomeZip" size="8" maxlength="6" tabindex="23" onkeyup="hrm.common.notChinese(event,this);" onblur="hrm.common.checkZip(this)"/>
				<td colspan="2"><s:hidden name="emp.empLastChangeTime"/></td>
	        </tr>
	        <tr>
	            <td colspan="6"></td>
	        </tr>
		    <tr>
				<td colspan="5" align="center">
					<input type="submit" value="ȷ��" refreshId="tabContent" tabindex="43" onclick="return confirmCreate();">
	                &nbsp;<input type="reset" value="����">
					<ty:auth auths="911">&nbsp;&nbsp;
						<input class="checkbox" type="checkbox" id="createUser" tabindex="44" name="createUser" value="true">ͬʱ����ϵͳ�û� 
					</ty:auth>
				</td>
				<td>&nbsp;</td>
	        </tr>
	        <tr>
	            <td colspan="6"></td>
	        </tr>
		</table>
	</s:form>
</div>      
                                
<div id="errorMsg" style="display:none;border:1px solid #e00;background-color:#fee;padding:2px;margin-top:8px;width:300px;font:normal 12px Arial;color:#900"></div>

<!-- �Զ���������ʾ��div -->
<div id="autoCreateEmpNo" style="position:absolute;z-index:5;width:146px;display:none;solid;text-align:left;" class="prompt_div_inline"></div>
<!-- �Զ������� -->
<script type="text/javascript">
function confirmCreate(){
	if(document.getElementById('createUser').checked){
		if(confirm("��Ա���ĵ�¼�û��������뽫���͵������ַ<"+document.getElementById('email').value+">����ȷ�ϴ������ַ������ȷ��"))
			return true;
		else
		   return false;
	}
	
	return true;
}
</script>
<jsp:include flush="true" page="position_choose_div.jsp"></jsp:include>
</body>	