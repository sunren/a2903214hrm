<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<html>
<head>
<title>��֯�ṹ������</title>
<script type="text/javascript" src="../dwr/interface/org.js"></script>
<script type="text/javascript" src="../resource/js/configuration/BaseManager.js"></script>
<script type="text/javascript"> 
$(document).ready(function() { 
	$("#tabs").tabs();
	$("#chooseManager_div").dialog({bgiframe: false, autoOpen: false,width:400,height: 260,resize: false,modal: false});
	$("#tabs ul li a").bind('click',function(){
		$("#dialog_branchmanager").dialog('destroy').remove();
		$("#dialog_departmentmanager").dialog('destroy').remove();
		$("#dialog_businessunitmanager").dialog('destroy').remove();
		$("#dialog_groupmanager").dialog('destroy').remove();
		$("#dialog_locationmanager").dialog('destroy').remove();
	});
});	
</script>
</head>
<body>
<s:component template="bodyhead">
	<s:param name="pagetitle" value="'��֯�ṹ������'" />
	<s:param name="helpUrl" value="'38'" />
</s:component>
<div id="tabs">
	<ul>
		<li><a href="branchmanagerConfig.action?date=<%=new java.util.Date().getTime() %>">��˾������</a></li>
		<li><a href="departmanagerConfig.action?date=<%=new java.util.Date().getTime() %>">���ž���</a></li>
		<li><a href="unitmanagerConfig.action?date=<%=new java.util.Date().getTime() %>">ҵ������</a></li>
		<li><a href="groupmanagerConfig.action?date=<%=new java.util.Date().getTime() %>">С���鳤</a></li>
		<li><a href="locationmanagerConfig.action?date=<%=new java.util.Date().getTime() %>">����������</a></li>
	</ul>
</div>		
<p>ע����<font color="red">*</font>�ı�ʾ��Ҫ�����ˣ�û<font color="red">*</font>�ı�ʾ����������</p>
<%@ include file="search_emp_div.jsp"%>
</body>
</html>
