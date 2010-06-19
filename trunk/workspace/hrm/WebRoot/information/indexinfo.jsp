<%@page pageEncoding="GBK" contentType="text/html; charset=GBK" %>
<%@taglib prefix="s" uri="webwork"%>
<%@ taglib uri="/WEB-INF/config/jenkov/tabbedpanetag.tld" prefix="tabs"  %>
<%@ taglib uri="/WEB-INF/config/jenkov/ajaxtags.tld" prefix="ajax" %>

<html>
<head>
<link href="<s:url value="../resource/css/subNav.css"/>" rel="stylesheet" type="text/css" />
<title>��˾��Ϣ</title>
</head>
<body> 


<tabs:tabbedPane tabParam="tab" defaultTab="1" sessionKey="tab">
	 <div id="tabs9">
		<ul><!-- CSS Tabs -->
			<tabs:activeTab   tabId="1"><li><ajax:link targetElement="bodyId"><a href="indexinfo.jsp?tab=1"><span><b>��˾��Ϣ�б�</b></span></a></ajax:link></li></tabs:activeTab>
			<tabs:inactiveTab   tabId="1"><li><ajax:link targetElement="bodyId"><a href="indexinfo.jsp?tab=1"><span>��˾��Ϣ�б�</span></a></ajax:link></li></tabs:inactiveTab>			
			<tabs:activeTab   tabId="2"><li><ajax:link targetElement="bodyId"><a href="indexinfo.jsp?tab=2"><span><b>������˾��Ϣ</b></span></a></ajax:link></li></tabs:activeTab>
			<tabs:inactiveTab   tabId="2"><li><ajax:link targetElement="bodyId"><a href="indexinfo.jsp?tab=2"><span>������˾��Ϣ</span></a></ajax:link></li></tabs:inactiveTab>			
			<tabs:activeTab   tabId="3"><li><ajax:link targetElement="bodyId"><a href="indexinfo.jsp?tab=3"><span><b>��˾��Ϣ����</b></span></a></ajax:link></li></tabs:activeTab>
			<tabs:inactiveTab   tabId="3"><li><ajax:link targetElement="bodyId"><a href="indexinfo.jsp?tab=3"><span>��˾��Ϣ����</span></a></ajax:link></li></tabs:inactiveTab>			
							
		 </ul>
	</div>	

	<table cellspacing="0" cellpadding="5" width="100%" class="frameblue">	
    <tr>
    	<td id="tabContent" valign="top">
    	<tabs:tabContent tabId="1">
        	<tabs:include uri="searchInfo.action"/>
        </tabs:tabContent>
        <tabs:tabContent tabId="2">
        	<tabs:include uri="createInfoInit.action"/>
        </tabs:tabContent>
        <tabs:tabContent tabId="3">
        	<tabs:include uri="info_config_search.jsp"/>
        </tabs:tabContent>        
    	</td>
    </tr>
	</table>
</tabs:tabbedPane>
</body>
</html>
