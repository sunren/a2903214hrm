<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>����/�ֹ�˾��Ϣά��</title>
<link rel="stylesheet" href="../resource/css/style.css" type="text/css" />
<jsp:include flush="true" page="../sitemesh/jsPackage.jsp"></jsp:include>
<script type="text/javascript" src="../resource/js/configuration/BaseManager.js"></script>
</head>
<body>
<span class="errorMessage" id="errMsg"></span>
<div id="tabs">
	<ul style="list-style: none;">
		<s:if test="nodeType==0 || nodeType==1">
			<li><a href="editBranchDeptInit.action?date=<%=new java.util.Date().getTime() %>&dept.id=<s:property value='dept.id'/>&nodeId=<s:property value='nodeId'/>&nodeType=<s:property value='nodeType'/>&operateOver=<s:property value='operateOver'/>">������Ϣ</a></li>
		</s:if>
		<s:elseif test="nodeType==2">
			<li><a href="editBranchDeptInit.action?date=<%=new java.util.Date().getTime() %>&dept.id=<s:property value='dept.id'/>&nodeId=<s:property value='nodeId'/>&nodeType=<s:property value='nodeType'/>&operateOver=<s:property value='operateOver'/>">������Ϣ</a></li>
		</s:elseif>
	    <s:if test="dept.id!=null && dept.id!=''">
	        <li><a href="goDeptResTab.action?date=<%=new java.util.Date().getTime() %>&dept.id=<s:property value='dept.id'/>">����ְ��</a></li>
			<li><a href="goDeptPerfcrTab.action?date=<%=new java.util.Date().getTime() %>&dept.id=<s:property value='dept.id'/>">���˱�׼</a></li>
			<li><a href="goDeptPBsTab.action?date=<%=new java.util.Date().getTime() %>&dept.id=<s:property value='dept.id'/>">ְλ�б�</a></li>
			<li><a href="goDeptHistTab.action?date=<%=new java.util.Date().getTime() %>&dept.id=<s:property value='dept.id'/>">�䶯��ʷ</a></li>
			<li><a href="goDirectSubDeptTab.action?date=<%=new java.util.Date().getTime() %>&dept.id=<s:property value='dept.id'/>">ֱ���¼���֯</a></li>
	    </s:if>
	</ul>
</div>
<script type="text/javascript">
$(document).ready(function() {
	$("#tabs").tabs();
	$("#tabs ul li a").bind('click',function(){
	   	$("#dialog_deptres").dialog('destroy').remove();
	   	$("#dialog_deptperfcr").dialog('destroy').remove();
	});
});	
</script>

</body>
</html>
