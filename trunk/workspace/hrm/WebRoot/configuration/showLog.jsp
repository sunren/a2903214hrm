<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<html>
<%@ taglib prefix="s" uri="webwork"%>
<head>
<title>日志管理</title>
</head>
<body>
<s:component template="bodyhead">
<s:param name="pagetitle"value="'日志管理'" />
<s:param name="helpUrl" value="'60'" />
</s:component>
<br>
<form name="logScanForm" id="logScanForm" namespace="/configuration"action="logscan.action" method="POST">
   <s:hidden id="order" name="pager.order"/>
   <s:hidden id="operate" name="pager.operate"/>
   <s:hidden name="pager.currentPage"/>
   <span id ="tipspan"><input type ="button" value ="清除日志" class ="button" onclick="deleteDate();"> </span>
   <br>
   <table name="userTable" cellpadding="0" cellspacing="0" class="gridtableList" width="100%">
	<tr><td colspan="11" align="right"  class="listViewPaginationTdS1">
	<a href="#" onclick="splits('first');"><img src='../resource/images/start.gif' width='11' height='9' alt='开始'>开始</a>
	<a href="#" onclick="splits('previous');"><img src='../resource/images/previous.gif' width='6' height='9' alt='上页'>上页</a>
    （<s:property value="pager.currentPage+'/'+pager.totalPages"/>页｜共<s:property value="pager.totalRows"/>条）
	<a href="#" onclick="splits('next');">下页<img src='../resource/images/next.gif' width='6' height='9'></a>
	<a href="#" onclick="splits('last');">最后<img src='../resource/images/end.gif' width='11' height='9' alt='最后'></a>
	</td></tr>  
   <s:if test="!recorderList.isEmpty()">
   <s:iterator value="recorderList">
   <tr><td ><s:property/></td></tr>
   </s:iterator>
   </s:if>
   <s:else>
   <tr><td align ="center">-- 日志为空。--</td></tr>
   </s:else>
   </table>
</form>

<form name="logResetForm" id="logResetForm" namespace="/configuration"action="logclean.action" method="POST">
</form>
<script>
function splits(var1){
	$('#operate').val(var1);
	document.getElementById("logScanForm").submit();
}
function deleteDate(){
	if(confirm("您确定要删除日志吗？"))
		document.getElementById("logResetForm").submit();
}
</script>
</body>
</html>