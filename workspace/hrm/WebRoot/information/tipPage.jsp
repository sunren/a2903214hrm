<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@taglib prefix="s" uri="webwork"%>
<html>
	<title>
	首页提示
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
	<tr><td align ="center">没有提示信息要显示！</td></tr>
	</s:else>
	</table>
<script type="text/javascript">
	
function toAction(str){
	if(!window.opener||window.opener.closed){
		alert("父窗口已经被关闭！");
		self.close();
	}else{
		window.opener.parent.location = str;
	}
}

</script>
</body>
</html>