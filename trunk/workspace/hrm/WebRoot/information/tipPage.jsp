<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@taglib prefix="s" uri="webwork"%>
<html>
	<title>
	��ҳ��ʾ
	</title>
<head>
	<link href="<s:url value="/resource/css/style.css"/>" rel="stylesheet" type="text/css" />
</head>
<body bgcolor ="#ECF6FB">
	<table width ="100%" height ="100%" class="formtable">
	<s:if test = "!tipList.isEmpty()">
		<s:iterator value="tipList" status="index">	  
		<tr>
		<td colspan="2" bgcolor="#eff3ff"><img src="../resource/images/dot.gif" alt="" width="3" height="3" style="cursor:pointer" align="absmiddle" />  <s:property escape="false"/></td>
		</tr>
		</s:iterator>
	</s:if>
	<s:else>
	<tr><td align ="center">û����ʾ��ϢҪ��ʾ��</td></tr>
	</s:else>
	</table>
<script type="text/javascript">
	
function toAction(str){
	if(!window.opener||window.opener.closed){
		alert("�������Ѿ����رգ�");
		self.close();
	}else{
		window.opener.parent.location = str;
	}
}

</script>
</body>
</html>