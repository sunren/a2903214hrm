<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Ԥ���屨���޸�</title>
<link href="../resource/css/style.css" rel="stylesheet" type="text/css" />
</head>
<body>
<s:component template="bodyhead">
	<s:param name="pagetitle" value="'�޸�Ԥ���屨��'" />
	<s:param name="helpUrl" value="'32'" />
</s:component>
<div id="basic" style="DISPLAY: block;clear : both">
	<form action="updateUploadReport.action" method="post" enctype="multipart/form-data">
		<s:hidden name="report.reportId"></s:hidden>
		<table width="100%" class="formtable">
			<tr>
				<td nowrap>��������<span class="errorMessage">*</span>��</td>
				<td nowrap><s:textfield name="report.reportName" required="true" size="20" maxlength="64"></s:textfield></td>
			</tr>
			<tr>
				<td nowrap width="8%">��������ģ��<span class="errorMessage">*</span>��</td>
				<td nowrap><s:select list="#{1:'Ա��ģ��',2:'н��ģ��',3:'��ѵģ��',4:'����ģ��',6:'��Ƹģ��',9:'ϵͳģ��'}" name="report.reportModule" required="true" emptyOption="true"></s:select></td>
			</tr>
			<tr>
				<td nowrap>����Ȩ��<span class="errorMessage">*</span>��</td>
				<td><s:select name="report.reportAuthority" list="authList" listKey="id" listValue="authorityDesc" emptyOption="true"/></td>
			</tr>
			<tr>
				<td nowrap>�����<span class="errorMessage">*</span>��</td>
				<td nowrap><s:textfield name="report.reportSortId" required="true" value="1" onkeyup="value=value.replace(/\D/g,'')"/></td>
			</tr>
			<tr>
				<td nowrap>�����������<span class="errorMessage">*</span>��</td>
				<td nowrap><s:textfield name="report.reportUrl" required="true"/></td>
			</tr>
			<tr>
				<td nowrap>Ԥ���屨���ļ���</td>
				<td nowrap><s:file name="reportFile"></s:file></td>
			</tr>
			<tr>
				<td nowrap>����������</td>
				<td><s:textarea name="report.reportDescription" cols="40" rows="3" onkeypress="MKeyTextLength(this,128);"></s:textarea></td>
			</tr>
			<tr>
				<td><input type="submit" value="�޸�"/></td>
				<td><input type="reset" value="����"/></td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>