<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>�ϴ��û�Ԥ���屨��</title>
<link href="../resource/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../resource/js/report/edit.js"></script>
<script type="text/javascript" src="../dwr/interface/customizeReportDWR.js"></script>
</head>
<body>
<s:component template="bodyhead">
	<s:param name="pagetitle" value="'�½�Ԥ���屨��'" />
	<s:param name="helpUrl" value="'32'" />
</s:component>
<span class="errorMessage" id="errors"></span>
<div id="basic" style="DISPLAY: block;clear : both">
	<form action="uploadReport.action" method="post" id="action_form" enctype="multipart/form-data">
	<s:token></s:token>
		<table width="100%" class="formtable">
			<tr>
				<s:textfield label="��������" name="reportPre.reportPreName" id="id_reportPreName" required="true" size="20" maxlength="64"></s:textfield>
				<s:select label="��������ģ��" list="#{1:'Ա��ģ��',2:'н��ģ��',3:'��ѵģ��',4:'����ģ��',6:'��Ƹģ��',9:'ϵͳģ��'}" name="reportPre.reportPreModule" id="id_reportPreModule" onchange="changeModule();" required="true" emptyOption="true"></s:select>
			</tr>
			<tr>
				<s:select label="���Ʊ�������" name="reportPre.reportPreType" id="id_reportPreType" list="#{1:'�ͻ�Ԥ����',0:'365HRMԤ����'}" required="true"></s:select>
				<s:select label="ִ��URL" list="#{0:'��׼URLִ��',1:'����URLִ��'}" name="reportPre.reportPreUrl"></s:select>
			</tr>
			<tr>
				<s:select label="������ʾģʽ" list="#{0:'ֻ��ʾ���',1:'ֻ��ʾͼ��',2:'���߶���ʾ'}" name="reportPre.reportPreDisplayMode"></s:select>
				<s:file label="Ԥ���屨���ļ�" id="reportFile" name="reportFile" required="true" accept="text/rptdesign"></s:file>
			</tr>
			<tr>
				<td align="right">����Ȩ��<span class="errorMessage">*</span>��</td>
				<td>
				<select name="reportPre.reportPreAuthority" id="id_reportPreAuthority" size="3" multiple><option value="">��ѡ��</option></select></td>
				<s:textarea label="��������" name="reportPre.reportPreDescription" cols="40" rows="3" onkeypress="MKeyTextLength(this,128);"></s:textarea>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td align="center">&nbsp;&nbsp;<input id="submit_bt" type="button" onclick="doSubmit();" value="����"/>&nbsp;&nbsp;<input type="reset" value="����"/></td>
			</tr>
		</table>
	</form>
</div>
<script type="text/javascript">
	changeModule();
	function doSubmit(){
		if(validate_pre()){
			document.getElementById('action_form').submit();
		}
	}
</script>
</body>
</html>