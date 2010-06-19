<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<%@taglib prefix="ty" uri="/tengsource"%>
<html>
	<head>
		<title>�ʼ�ģ���޸�</title>
		<style type="text/css">@import url("../resource/css/tab.css");</style>
		<script type="text/javascript" src="../dwr/interface/emailtemplate.js"></script>
		<script type="text/javascript" src="../dwr/interface/emailsend.js"></script>
</head>
<body>
<s:component template="bodyhead">
	<s:param name="pagetitle" value="'�ʼ�ģ���޸�'" />
	<s:param name="helpUrl" value="'11'" />
</s:component>
<h4>ģ���ţ�<s:property value="template.etTitleNo" /></h4>
<p><s:property value="template.etNotes" /></p>
<br/>
<form action="modifyTemplate.action" method="post" onsubmit="return check();">
	<s:hidden name="template.id"></s:hidden>
	<s:hidden name="template.etTitleNo"></s:hidden>
	<p>ģ��״̬:<s:select list="#{1:'����',0:'ͣ��'}" name="template.etStatus" emptyOption="false"/></p>
	<p>����ģʽ:<s:select list="#{0:'��������',1:'�������Ͳ�����',2:'�����ʱ����'}" emptyOption="false" name="template.etSendMode" /></p>
	<p>ģ����⣺</p>
	<p><s:textfield id="etTitle" name="template.etTitle" size="115"/></p>
	<p>ģ�����ݣ�</p>
	<p><s:textarea name="template.etContent" rows="20" cols="115"/></p>
	<p><input type="submit" value="�޸�"/>&nbsp;<input type="button" value="����" onclick="history.back(1)"/></p>
</form>

<script type="text/javascript">
	function check(){
		var title = document.getElementById("etTitle");
		if(title.value == ''){
			alert('ģ����ⲻ����Ϊ��!');
			return false;
		}
		var content = document.getElementById("etContent");
		if(content.value == ''){
			alert('ģ�����ݲ�����Ϊ��!');
			return false;
		}
		return true;
	}
</script>
</body>
</html>
