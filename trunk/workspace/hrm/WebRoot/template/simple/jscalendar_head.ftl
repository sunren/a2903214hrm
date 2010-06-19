<script type="text/javascript" src="<@s.url value='/struts/jscalendar/' encode='false' includeParams='none'/>calendar.js"></script>
<script type="text/javascript" src="<@s.url value='/struts/jscalendar/lang/' encode='false' includeParams='none'/>calendar-${parameters.language?default("en")}.js"></script>
<script type="text/javascript" src="<@s.url value='/struts/jscalendar/' encode='false' includeParams='none'/>calendar-setup.js"></script>
<#if parameters.calendarcss?exists>
<link rel="stylesheet" href="<@s.url value='/struts/jscalendar/${parameters.calendarcss?html}' encode='false' includeParams='none'/>" type="text/css"/>
</#if>

