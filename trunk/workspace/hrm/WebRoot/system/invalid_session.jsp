<%@ page contentType="text/html; charset=GBK" errorPage="/error.jsp" language="java" pageEncoding="GBK"%>
<%@ taglib uri="sitemesh-decorator" prefix="decorator"%>
<%@ taglib uri="sitemesh-page" prefix="page"%>
<%@ taglib prefix="ww" uri="webwork"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<html>
<head>
<%response.setHeader("Cache-Control", "no-cache");
  response.setHeader("Pragma", "no-cache");
%>
<title>SESSION失效</title>
<link href="<ww:url value="/resource/css/style.css"/>" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<ww:url value="/resource/js/util.js"/>"></script>
</head>
<body>
<div style="clear : both" id="mainbox">
	<table border="0" height="400" cellpadding="0" cellspacing="0" width="100%" style="margin:0px; padding:0px">
		<tr>
			<td id="bodyId" valign="middle" align="center" width="100%" height="100%">
			    <img src="../resource/images/error1.gif" >Session失效,请重新登陆!
			</td>
		</tr>
	</table>
</div>
</body>
</html>
