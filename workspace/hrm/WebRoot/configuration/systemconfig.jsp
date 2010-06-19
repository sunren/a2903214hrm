<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>        
<html>
<head> 
    <script type="text/javascript" src="../dwr/interface/mailDWR.js"></script>
    
	<style type="text/css">
		.wrapper{border: 1px;line-height: 180%;border: 1px #6BB5DA solid;}
		.contentdiv{background-color: #ECF6FB;border-top: 1px #6BB5DA solid;}
		.contentdiv p{margin-left: 5px;}
	</style>
</head>
<body>
<s:component template="bodyhead">
	<s:param name="pagetitle" value="'ϵͳ��������'" />
	<s:param name="helpUrl" value="'63'" >
    </s:param>
</s:component>
<span class="errorMessage" id="errorMessage"></span> 
<div id="tabs">
	<ul>
		<li><a href="showConfigProfile.action?date=<%=new java.util.Date().getTime() %>">Ա��ģ��</a></li>
		<li><a href="showConfigSalary.action?date=<%=new java.util.Date().getTime() %>">н��ģ��</a></li>
		<li><a href="showConfigExamin.action?date=<%=new java.util.Date().getTime() %>">���ڻ���</a></li>
		<li><a href="showExaminShift.action?date=<%=new java.util.Date().getTime() %>">���ڸ߼�</a></li>
		<li><a href="showConfigMail.action?date=<%=new java.util.Date().getTime() %>">�������</a></li>
		<li><a href="showConfigOthers.action?date=<%=new java.util.Date().getTime() %>">ϵͳģ��</a></li>
	</ul>
</div>
<script type="text/javascript">
	$(document).ready(function() { 
        $("#tabs").tabs();
	});	
	function isNotNull(str){
		return (str && str.trim().length != 0);
	}
	function getValueByName(name) {
		var temp = document.getElementsByName(name);
		for (i = 0; i < temp.length; i++) {
			if (temp[i].checked) {
				return temp[i].value;
			}
		}
		return "";
	}
	function isDigit(data){
	    var objErgExp=new RegExp("[1-9]([0-9]*)","ig");
	    return data && objErgExp.test(data);
	}
</script>
</body>
</html>
