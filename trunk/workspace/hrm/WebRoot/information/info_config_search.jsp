<%@page pageEncoding="GBK" contentType="text/html; charset=GBK" %>
<%@taglib prefix="s" uri="webwork"%>
<html>
<head>
<!-- employee-create -->
<title>��������</title>
</head>
<body> 
	<table cellspacing="0" cellpadding="5" width="90%">
	     <s:if test="!infoList.isEmpty()">
	     	<s:iterator value="profiles" status="index">
	     		<tr>
					<td>&nbsp;<s:property value="epEmpNo.id" /></td>  
					<td>&nbsp;<s:property value="epTitle" /></td>  
					<td>&nbsp;<s:property value="epFileName" /></td>  
	     		</tr>
	     	</s:iterator>
	     </s:if>
		<s:else>
			<tr><!-- �����ڷ��������Ĺ�Ӧ�̣� -->
					<td colspan="10"><script>errMsg("","�����ڼ�¼");</script></td>
			</tr>
		</s:else>
	</table>
</body>
</html>
