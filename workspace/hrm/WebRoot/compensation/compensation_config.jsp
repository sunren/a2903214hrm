<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<html>
<head>
<title>н������</title>
	<link rel="stylesheet" type="text/css" href="../resource/css/tablesorter.css" />
	
	<script type="text/javascript" src="../dwr/interface/my.js"></script>
	<script type="text/javascript" src="../resource/js/configuration/BaseManager.js"></script>
	<script type="text/javascript">
		$(document).ready(function() { 
			$("#tabs").tabs();
			$("#tabs ul li a").bind('click',function(){
				$("#dialog_jobgrade").dialog('destroy').remove();
				$("#dialog_benefittype").dialog('destroy').remove();
				$("#dialog_acctitems").dialog('destroy').remove();
			});
		}); 
	</script>   
</head>
<body>
<s:component template="bodyhead">
	<s:param name="pagetitle" value="'н������'" />
	<s:param name="helpUrl" value="20" />
</s:component>
<span class="errorMessage" id="errMsg"></span>
<div id="tabs">
	<ul style="list-style: none;">
		<li><a href="salaryRatingConfig.action?date=<%=new java.util.Date().getTime() %>">н�ʼ�������</a></li>
		<li><a href="benefitTypeList.action?date=<%=new java.util.Date().getTime() %>">�籣��������</a></li>
		<li><a href="acctItemDef.action?date=<%=new java.util.Date().getTime() %>&changeIodef=<s:property value="changeIodef"/>">н��������Ŀ</a></li>
	</ul>
</div>
</body>
</html>