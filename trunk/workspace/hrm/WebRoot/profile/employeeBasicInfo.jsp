<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<html>
<head>
<title>��֯�ṹ����</title>
<script type="text/javascript" src="../dwr/interface/my.js"></script>
<script type="text/javascript" src="../resource/js/configuration/BaseManager.js"></script>
<script type="text/javascript">
$(document).ready(function() { 
	$("#tabs").tabs();
	$("#chooseManager_div").dialog({bgiframe: false, autoOpen: false,width:400,height: 260,resize: false,modal: false});
	$("#tabs ul li a").bind('click',function(){
	   	$("#dialog_branch").dialog('destroy').remove();
	   	$("#dialog_department").dialog('destroy').remove();
	   	$("#dialog_businessunit").dialog('destroy').remove();
	   	$("#dialog_group").dialog('destroy').remove();
	});
});	
</script>	
</head> 
<body>
<s:component template="bodyhead">
	<s:param name="pagetitle" value="'��֯�ṹ����'" />
	<s:param name="helpUrl" value="'38'" />
</s:component>
<div id="tabs">
	<ul style="list-style: none;">
		<li><a href="branchConfig.action?date=<%=new java.util.Date().getTime() %>">�ֹ�˾����</a></li>
		<li><a href="departmentConfig.action?date=<%=new java.util.Date().getTime() %>">��������</a></li>
		<li><a href="businessunitConfig.action?date=<%=new java.util.Date().getTime() %>">ҵ��Ԫ����</a></li>
		<li><a href="groupConfig.action?date=<%=new java.util.Date().getTime() %>">����С������</a></li>
		
	</ul>
</div>
<%@ include file="search_emp_div.jsp"%>
</body>
</html>
