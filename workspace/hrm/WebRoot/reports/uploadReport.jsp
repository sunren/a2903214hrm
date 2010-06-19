<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>上传用户预定义报表</title>
<link href="../resource/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../resource/js/report/edit.js"></script>
<script type="text/javascript" src="../dwr/interface/customizeReportDWR.js"></script>
</head>
<body>
<s:component template="bodyhead">
	<s:param name="pagetitle" value="'新建预定义报表'" />
	<s:param name="helpUrl" value="'32'" />
</s:component>
<span class="errorMessage" id="errors"></span>
<div id="basic" style="DISPLAY: block;clear : both">
	<form action="uploadReport.action" method="post" id="action_form" enctype="multipart/form-data">
	<s:token></s:token>
		<table width="100%" class="formtable">
			<tr>
				<s:textfield label="报表名称" name="reportPre.reportPreName" id="id_reportPreName" required="true" size="20" maxlength="64"></s:textfield>
				<s:select label="报表所在模块" list="#{1:'员工模块',2:'薪资模块',3:'培训模块',4:'考勤模块',6:'招聘模块',9:'系统模块'}" name="reportPre.reportPreModule" id="id_reportPreModule" onchange="changeModule();" required="true" emptyOption="true"></s:select>
			</tr>
			<tr>
				<s:select label="定制报表种类" name="reportPre.reportPreType" id="id_reportPreType" list="#{1:'客户预定义',0:'365HRM预定义'}" required="true"></s:select>
				<s:select label="执行URL" list="#{0:'标准URL执行',1:'特殊URL执行'}" name="reportPre.reportPreUrl"></s:select>
			</tr>
			<tr>
				<s:select label="报表显示模式" list="#{0:'只显示表格',1:'只显示图表',2:'两者都显示'}" name="reportPre.reportPreDisplayMode"></s:select>
				<s:file label="预定义报表文件" id="reportFile" name="reportFile" required="true" accept="text/rptdesign"></s:file>
			</tr>
			<tr>
				<td align="right">运行权限<span class="errorMessage">*</span>：</td>
				<td>
				<select name="reportPre.reportPreAuthority" id="id_reportPreAuthority" size="3" multiple><option value="">请选择</option></select></td>
				<s:textarea label="报表描述" name="reportPre.reportPreDescription" cols="40" rows="3" onkeypress="MKeyTextLength(this,128);"></s:textarea>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td align="center">&nbsp;&nbsp;<input id="submit_bt" type="button" onclick="doSubmit();" value="保存"/>&nbsp;&nbsp;<input type="reset" value="重置"/></td>
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