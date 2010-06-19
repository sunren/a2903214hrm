<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<%@taglib prefix="ty" uri="/tengsource"%>
<html>
	<head>
		<title>邮件模板修改</title>
		<style type="text/css">@import url("../resource/css/tab.css");</style>
		<script type="text/javascript" src="../dwr/interface/emailtemplate.js"></script>
		<script type="text/javascript" src="../dwr/interface/emailsend.js"></script>
</head>
<body>
<s:component template="bodyhead">
	<s:param name="pagetitle" value="'邮件模版修改'" />
	<s:param name="helpUrl" value="'11'" />
</s:component>
<h4>模板编号：<s:property value="template.etTitleNo" /></h4>
<p><s:property value="template.etNotes" /></p>
<br/>
<form action="modifyTemplate.action" method="post" onsubmit="return check();">
	<s:hidden name="template.id"></s:hidden>
	<s:hidden name="template.etTitleNo"></s:hidden>
	<p>模板状态:<s:select list="#{1:'启用',0:'停用'}" name="template.etStatus" emptyOption="false"/></p>
	<p>发送模式:<s:select list="#{0:'立即发送',1:'立即发送并保存',2:'保存后定时发送'}" emptyOption="false" name="template.etSendMode" /></p>
	<p>模板标题：</p>
	<p><s:textfield id="etTitle" name="template.etTitle" size="115"/></p>
	<p>模板内容：</p>
	<p><s:textarea name="template.etContent" rows="20" cols="115"/></p>
	<p><input type="submit" value="修改"/>&nbsp;<input type="button" value="返回" onclick="history.back(1)"/></p>
</form>

<script type="text/javascript">
	function check(){
		var title = document.getElementById("etTitle");
		if(title.value == ''){
			alert('模板标题不允许为空!');
			return false;
		}
		var content = document.getElementById("etContent");
		if(content.value == ''){
			alert('模板内容不允许为空!');
			return false;
		}
		return true;
	}
</script>
</body>
</html>
