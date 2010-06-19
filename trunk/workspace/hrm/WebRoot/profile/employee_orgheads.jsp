<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<html>
<head>
<title>组织结构负责人</title>
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
	<s:param name="pagetitle" value="'组织结构负责人'" />
	<s:param name="helpUrl" value="'38'" />
</s:component>
<div id="tabs">
	<ul>
		<li><a href="branchmanagerConfig.action?date=<%=new java.util.Date().getTime() %>">公司负责人</a></li>
		<li><a href="departmanagerConfig.action?date=<%=new java.util.Date().getTime() %>">部门经理</a></li>
		<li><a href="unitmanagerConfig.action?date=<%=new java.util.Date().getTime() %>">业务主管</a></li>
		<li><a href="groupmanagerConfig.action?date=<%=new java.util.Date().getTime() %>">小组组长</a></li>
		<li><a href="locationmanagerConfig.action?date=<%=new java.util.Date().getTime() %>">地区负责人</a></li>
	</ul>
</div>		
<p>注：有<font color="red">*</font>的表示主要负责人，没<font color="red">*</font>的表示其他负责人</p>
<%@ include file="search_emp_div.jsp"%>
</body>
</html>
