<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<html>
<head>
<jsp:include flush="true" page="../sitemesh/jsPackage.jsp"></jsp:include>
<script type="text/javascript" src="../resource/js/configuration/BaseManager.js"></script>
<link rel="stylesheet" href="../resource/css/style.css" type="text/css" />
<script type="text/javascript"> 
$(document).ready(function() { 
	$("#tabs").tabs();
    function bindCallBack(){
        var obj=$("#dialog_pbResp")[0];
        if(obj!=undefined){
           $("#dialog_pbResp").dialog('destroy').remove();
        }
		obj=$("#dialog_pbPerfcr")[0];
		if(obj!=undefined){
          $("#dialog_pbPerfcr").dialog('destroy').remove();
        }
		obj=$("#dialog_pbQualify")[0];
		if(obj!=undefined){
          $("#dialog_pbQualify").dialog('destroy').remove();
        }
       
    }
	$("#tabs ul li a").bind('click',bindCallBack);
});	
</script>
</head>
<body>
<span class="errorMessage" id="errMsg"></span>
<div id="tabs">
	<ul>
	    <s:if test="pbId==null">
	    <li><a href="pbBasicInfo.action?parentPbId=<s:property value='parentPbId'/>&deptId=<s:property value='deptId'/>&date=<%=new java.util.Date().getTime() %>">基本信息</a></li>
	    </s:if>
	    <s:else>
		<li><a href="pbBasicInfo.action?pbId=<s:property value='pbId'/>&lastOperate=<s:property value='lastOperate'/>&date=<%=new java.util.Date().getTime() %>">基本信息</a></li>
		<li><a href="pbResponse.action?pbId=<s:property value='pbId'/>&date=<%=new java.util.Date().getTime() %>">职位职责 </a></li>
		<li><a href="pbPerfcr.action?pbId=<s:property value='pbId'/>&date=<%=new java.util.Date().getTime() %>">考核标准</a></li>
		<li><a href="pbQualify.action?pbId=<s:property value='pbId'/>&date=<%=new java.util.Date().getTime() %>">任职资格</a></li>
		<li><a href="showPbHistAction.action?pbId=<s:property value='pbId'/>&date=<%=new java.util.Date().getTime() %>">变动历史</a></li>
	    </s:else>
	</ul>
</div>	
	
</body>
</html>
