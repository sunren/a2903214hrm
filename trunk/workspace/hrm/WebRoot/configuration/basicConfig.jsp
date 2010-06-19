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
	<s:param name="pagetitle" value="'�����������'" />
	<s:param name="helpUrl" value="'38'" />
</s:component>
<span class="errorMessage" id="msg"></span>
<div id="tabs">
	<ul style="list-style: none;">
		<li><a href="contractType.action?date=<%=new java.util.Date().getTime() %>">��ͬ����</a></li>
		<li><a href="payriseType.action?date=<%=new java.util.Date().getTime() %>">��н����</a></li>
		<li><a href="holidayType.action?date=<%=new java.util.Date().getTime() %>">�ݼ�����</a></li>
		<li><a href="overworkType.action?date=<%=new java.util.Date().getTime() %>">�Ӱ�����</a></li>
		<li><a href="staffCustom.action?date=<%=new java.util.Date().getTime() %>">Ա���Զ���</a></li>
		<li><a href="attendanceCustom.action?date=<%=new java.util.Date().getTime() %>">�����Զ���</a></li>
		<li><a href="emptypeConfig.action?date=<%=new java.util.Date().getTime() %>">�ù���ʽ����</a></li>
		<li><a href="locationConfig.action?date=<%=new java.util.Date().getTime() %>">��������</a></li>
		<li><a href="attdMachine.action?date=<%=new java.util.Date().getTime() %>">���ڻ�����</a></li>
		<li><a href="jobtitleConfig.action?date=<%=new java.util.Date().getTime() %>">ְ������</a></li>
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
		