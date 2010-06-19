<%@ page contentType="text/html; charset=GBK" errorPage="/error.jsp" language="java" pageEncoding="GBK"%>
<%@ taglib prefix="ww" uri="webwork"%>
<html>
<head>
<title>要查看的员工不存在</title>
<link href="<ww:url value="/resource/css/style.css"/>" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<ww:url value="/resource/js/util.js"/>"></script>
</head>
<body>
<div style="clear : both" id="mainbox">
	<table border="0" height="400" cellpadding="0" cellspacing="0" width="100%" style="margin:0px; padding:0px">
		<tr>
			<td id="bodyId" valign="middle" align="center" width="100%" height="100%">
			    <img src="../resource/images/error1.gif" >对不起,您要查看的员工不存在或已经被删除！
			    <a href="#" onclick="back();" class="listViewTdLinkS1">返回上一页!</a>
			    <a href="<ww:url namespace='/homepage' action='index'/>" class="listViewTdLinkS1">返回首页!</a>
			</td>
		</tr>
	</table>
</div>
</body>
</html>
