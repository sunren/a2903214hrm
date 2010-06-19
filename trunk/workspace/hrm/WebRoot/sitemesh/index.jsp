<html>
<head><%@ page contentType="text/html; charset=GBK" errorPage="/error.jsp" language="java" pageEncoding="GBK"%><%@ 
taglib uri="sitemesh-decorator" prefix="decorator"%><%@ 
taglib uri="sitemesh-page" prefix="page"%><%@ 
taglib prefix="s" uri="webwork"%>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<meta name="Keywords" content="sw.365hrm.com, 365HRM - HR�ĺû�顢������" />
<meta name="Author" content="��Դ���, admin@tengsource.com" />
<meta name="Copyright" content="TengSource, All Rights Reserved" />
<title><decorator:title default="��Դ365������Դ�������" /></title>
<link rel="icon" href="../resource/images/logodot.gif" />
<link rel="stylesheet" href="../resource/css/style.css" type="text/css" />

<%@ include file="jsPackage.jsp"%>

<decorator:head />
</head>
<body  <decorator:getProperty property="body.onload"  writeEntireProperty="true" /><decorator:getProperty property="body.onmouseout"   writeEntireProperty="true"/><decorator:getProperty property="body.onclick"  writeEntireProperty="true" /><decorator:getProperty property="body.onpaste"  writeEntireProperty="true" />>
<%@ include file="top.jsp"%>
<div id="mainbox">
	<%@ include file="../homepage/left.jsp"%>
	<decorator:body />
</div>
<%@ include file="foot.jsp"%>
</body>
</html>
