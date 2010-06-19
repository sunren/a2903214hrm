<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>预定义报表修改</title>
<link href="../resource/css/style.css" rel="stylesheet" type="text/css" />
</head>
<body>
<s:component template="bodyhead">
	<s:param name="pagetitle" value="'修改预定义报表'" />
	<s:param name="helpUrl" value="'32'" />
</s:component>
<div id="basic" style="DISPLAY: block;clear : both">
	<form action="updateUploadReport.action" method="post" enctype="multipart/form-data">
		<s:hidden name="report.reportId"></s:hidden>
		<table width="100%" class="formtable">
			<tr>
				<td nowrap>报表名称<span class="errorMessage">*</span>：</td>
				<td nowrap><s:textfield name="report.reportName" required="true" size="20" maxlength="64"></s:textfield></td>
			</tr>
			<tr>
				<td nowrap width="8%">报表所在模块<span class="errorMessage">*</span>：</td>
				<td nowrap><s:select list="#{1:'员工模块',2:'薪资模块',3:'培训模块',4:'考勤模块',6:'招聘模块',9:'系统模块'}" name="report.reportModule" required="true" emptyOption="true"></s:select></td>
			</tr>
			<tr>
				<td nowrap>运行权限<span class="errorMessage">*</span>：</td>
				<td><s:select name="report.reportAuthority" list="authList" listKey="id" listValue="authorityDesc" emptyOption="true"/></td>
			</tr>
			<tr>
				<td nowrap>排序号<span class="errorMessage">*</span>：</td>
				<td nowrap><s:textfield name="report.reportSortId" required="true" value="1" onkeyup="value=value.replace(/\D/g,'')"/></td>
			</tr>
			<tr>
				<td nowrap>报表访问连接<span class="errorMessage">*</span>：</td>
				<td nowrap><s:textfield name="report.reportUrl" required="true"/></td>
			</tr>
			<tr>
				<td nowrap>预定义报表文件：</td>
				<td nowrap><s:file name="reportFile"></s:file></td>
			</tr>
			<tr>
				<td nowrap>报表描述：</td>
				<td><s:textarea name="report.reportDescription" cols="40" rows="3" onkeypress="MKeyTextLength(this,128);"></s:textarea></td>
			</tr>
			<tr>
				<td><input type="submit" value="修改"/></td>
				<td><input type="reset" value="重置"/></td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>