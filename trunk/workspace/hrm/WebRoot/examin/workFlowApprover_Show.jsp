<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>部门/分公司信息维护</title>
<link rel="stylesheet" href="../resource/css/style.css" type="text/css" />
<jsp:include flush="true" page="../sitemesh/jsPackage.jsp"></jsp:include>
<script type="text/javascript" src="../resource/js/configuration/BaseManager.js"></script>
</head>
<body>
<span class="errorMessage" id="errMsg"></span>
<div id="tabs">
	<ul style="list-style: none;">
	        <li><a href="workFlowApproveManage.action?date=<%=new java.util.Date().getTime() %>&positionId=<s:property value='positionId'/>">权限设置</a></li>
			<li><a href="authorityPosManage.action?date=<%=new java.util.Date().getTime() %>&positionId=<s:property value='positionId'/>">范围设置</a></li>
	</ul>
</div>
<script type="text/javascript">
$(document).ready(function() {
	$("#tabs").tabs();
	$("#tabs ul li a").bind('click',function(){
	   	
	});
});	
</script>

</body>
</html>
