<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="../resource/js/jquery/themes/base/ui.all.css" />
		<link rel="stylesheet" type="text/css" href="../resource/css/tablesorter.css" />
	    <script type="text/javascript" src="../dwr/interface/my.js"></script>
	    <script type="text/javascript" src="../dwr/interface/EmpAddConf.js"></script>
	    <script type="text/javascript" src="../resource/js/configuration/BaseManager.js"></script>
    </head>
<body>
<s:component template="bodyhead">
	<s:param name="pagetitle" value="'基础表格设置'" />
	<s:param name="helpUrl" value="'38'" />
</s:component>
<span class="errorMessage" id="msg"></span>
<div id="tabs">
	<ul style="list-style: none;">
		<li><a href="contractType.action?date=<%=new java.util.Date().getTime() %>">合同种类</a></li>
		<li><a href="payriseType.action?date=<%=new java.util.Date().getTime() %>">调薪种类</a></li>
		<li><a href="holidayType.action?date=<%=new java.util.Date().getTime() %>">休假种类</a></li>
		<li><a href="overworkType.action?date=<%=new java.util.Date().getTime() %>">加班类型</a></li>
		<li><a href="staffCustom.action?date=<%=new java.util.Date().getTime() %>">员工自定义</a></li>
		<li><a href="attendanceCustom.action?date=<%=new java.util.Date().getTime() %>">考勤自定义</a></li>
		<li><a href="emptypeConfig.action?date=<%=new java.util.Date().getTime() %>">用工形式设置</a></li>
		<li><a href="locationConfig.action?date=<%=new java.util.Date().getTime() %>">地区设置</a></li>
		<li><a href="attdMachine.action?date=<%=new java.util.Date().getTime() %>">考勤机设置</a></li>
		<li><a href="jobtitleConfig.action?date=<%=new java.util.Date().getTime() %>">职务设置</a></li>
	</ul>
</div>

<script type="text/javascript">
$(document).ready(function() { 
       $("#tabs").tabs();
       $("#tabs ul li a").bind('click',function(){
       	$("#dialog_contract_type").dialog('destroy').remove();
       	$("#payRiseTypeDiv").dialog('destroy').remove();
       	$("#dialog_leave_type").dialog('destroy').remove();
       	$("#dialog_overtime_type").dialog('destroy').remove();
       	$("#dialog_employee_additional").dialog('destroy').remove();
       	$("#dialog_examin_additional").dialog('destroy').remove();
       	$("#dialog_emptype").dialog('destroy').remove();
	   	$("#dialog_location").dialog('destroy').remove();
	   	$("#dialog_attdMachine").dialog('destroy').remove();
	   	$("#dialog_jobtitle").dialog('destroy').remove();
	});
});	
</script>
<%@ include file="../profile/search_emp_div.jsp"%>
</body>
</html>
		