<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<html>
<%@ taglib prefix="s" uri="webwork"%>
<head>
<title>��־����</title>
</head>
<body>
<s:component template="bodyhead">
<s:param name="pagetitle"value="'��־����'" />
<s:param name="helpUrl" value="'60'" />
</s:component>
<br>
<form name="logScanForm" id="logScanForm" namespace="/configuration"action="logscan.action" method="POST">
   <s:hidden id="order" name="pager.order"/>
   <s:hidden id="operate" name="pager.operate"/>
   <s:hidden name="pager.currentPage"/>
   <span id ="tipspan"><input type ="button" value ="�����־" class ="button" onclick="deleteDate();"> </span>
   <br>
   <table name="userTable" cellpadding="0" cellspacing="0" class="gridtableList" width="100%">
	<tr><td colspan="11" align="right"  class="listViewPaginationTdS1">
	<a href="#" onclick="splits('first');"><img src='../resource/images/start.gif' width='11' height='9' alt='��ʼ'>��ʼ</a>
	<a href="#" onclick="splits('previous');"><img src='../resource/images/previous.gif' width='6' height='9' alt='��ҳ'>��ҳ</a>
    ��<s:property value="pager.currentPage+'/'+pager.totalPages"/>ҳ����<s:property value="pager.totalRows"/>����
	<a href="#" onclick="splits('next');">��ҳ<img src='../resource/images/next.gif' width='6' height='9'></a>
	<a href="#" onclick="splits('last');">���<img src='../resource/images/end.gif' width='11' height='9' alt='���'></a>
	</td></tr>  
   <s:if test="!recorderList.isEmpty()">
   <s:iterator value="recorderList">
   <tr><td ><s:property/></td></tr>
   </s:iterator>
   </s:if>
   <s:else>
   <tr><td align ="center">-- ��־Ϊ�ա�--</td></tr>
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
	if(confirm("��ȷ��Ҫɾ����־��"))
		document.getElementById("logResetForm").submit();
}
</script>
</body>
</html>