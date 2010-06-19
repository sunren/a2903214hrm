<html>
<head><%@ page contentType="text/html; charset=GBK" errorPage="/error.jsp" language="java" pageEncoding="GBK"%><%@ 
taglib uri="sitemesh-decorator" prefix="decorator"%><%@ 
taglib uri="sitemesh-page" prefix="page"%><%@ 
taglib prefix="s" uri="webwork"%>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<meta name="Keywords" content="www.365hrm.com, 365HRM - HR的好伙伴、好助手" />
<meta name="Author" content="腾源软件, admin@tengsource.com" />
<meta name="Copyright" content="TengSource, All Rights Reserved" />
<title><decorator:title default="腾源365人力资源管理软件" /></title>
<link rel="icon" href="../resource/images/logodot.gif" />
<link rel="stylesheet" href="../resource/css/style.css" type="text/css" />

<%@ include file="jsPackage.jsp"%>

<!-- 时间日期控件 -->
<link rel="stylesheet" type="text/css" media="all" href="../resource/js/jscalendar/skins/aqua/theme.css" title="Aqua" />
<script type="text/javascript" src="../resource/js/jscalendar/calendar.js"></script>
<script type="text/javascript" src="../resource/js/jscalendar/calendar-setup.js"></script>
<script type="text/javascript" src="../resource/js/jscalendar/lang/zh.js"></script>

<!-- 颜色选择器 -->
<link rel="stylesheet" href="../resource/js/report/js_color_picker_v2/js_color_picker_v2.css" media="screen">
<script type="text/javascript" src="../resource/js/report/js_color_picker_v2/color_functions.js"></script>
<script type="text/javascript" src="../resource/js/report/js_color_picker_v2/js_color_picker_v2.js"></script>

<decorator:head />
</head>
<body <decorator:getProperty property="body.onload"  writeEntireProperty="true" /><decorator:getProperty property="body.onmouseout"   writeEntireProperty="true"/><decorator:getProperty property="body.onclick"  writeEntireProperty="true" /><decorator:getProperty property="body.onpaste"  writeEntireProperty="true" />>
<%@ include file="top.jsp"%>
<div id="mainbox1">
	<table border="0" cellpadding="0" cellspacing="0" width="100%" style="margin:0px; padding:0px">
		<tr>
			<td id="leftId" valign="top" ><%@ include file="left.jsp"%></td>
			<td id="bodyId" valign="top" width="100%" style=" padding-right:8px; padding-left:8px; padding-top:3px;">
				<decorator:body />
			</td>
		</tr>
	</table>
</div>
<%@ include file="foot.jsp"%>
</body>
</html>
