<%@ page pageEncoding="GBK" contentType="text/html; charset=GBK" %>
<%@ taglib prefix="s" uri="webwork"%>
<%@ taglib uri="/WEB-INF/config/jenkov/tabbedpanetag.tld" prefix="tabs"  %>
<%@ taglib uri="/WEB-INF/config/jenkov/ajaxtags.tld" prefix="ajax" %>
<html>
<head>
<%
	String tabRequestIndex=request.getParameter("tab");
	if(tabRequestIndex == null){
		request.getParameterMap().put("tab","1");
	}else if(tabRequestIndex.trim().length()>0){
		int index = Integer.parseInt(tabRequestIndex);
		if(index<1 || index >3){
			request.getParameterMap().put("tab","1");
		}
	}
	String empNo = request.getParameter("empNo");
%>
<script type="text/javascript" src="../resource/js/jenkov/jenkov_ajaxScript.js"></script>
<link href="<s:url value="/resource/css/tab.css"/>" rel="stylesheet" type="text/css" />
<title>员工资料</title>
</head>
<body id="<%=empNo %>"> 
<table width="100%" cellspacing="0" cellpadding="0" border="0">
	<tr>
		<td width="2%">
			<img src="../resource/images/h3Arrow.gif">
		</td>
		<td width="82%" class="titlepage">
			<h3><span id="parentEmpName"></span>的详细资料</h3>
		</td>
		<td width="2%" style="cursor: help;">
			<img src="../resource/images/help.gif">
		</td>
		<td width="6%">
			<a target="_help" class="utilsLink" href="../help/searchHelp.action?classId=30">帮助</a>     
		</td>
	</tr>
</table>
<tabs:tabbedPane tabParam="tab" defaultTab="1" sessionKey="tab">
	<table cellspacing="0" cellpadding="5" width="100%">
        <tabs:activeTab   tabId="1"><td class="activeTab"  ><ajax:link targetElement="bodyId"><a href="my_basic_info.jsp?tab=1&empNo=<%=empNo %>&date=<%=new java.util.Date().getTime() %>">基本资料</a></ajax:link></td></tabs:activeTab>
        <tabs:inactiveTab tabId="1"><td class="inactiveTab"><ajax:link targetElement="bodyId"><a href="my_basic_info.jsp?tab=1&empNo=<%=empNo %>&date=<%=new java.util.Date().getTime() %>">基本资料</a></ajax:link></td></tabs:inactiveTab>
        <tabs:activeTab   tabId="2"><td class="activeTab"  ><ajax:link targetElement="bodyId"><a href="my_basic_info.jsp?tab=2&empNo=<%=empNo %>&date=<%=new java.util.Date().getTime() %>">附加资料</a></ajax:link></td></tabs:activeTab>
        <tabs:inactiveTab tabId="2"><td class="inactiveTab"><ajax:link targetElement="bodyId"><a href="my_basic_info.jsp?tab=2&empNo=<%=empNo %>&date=<%=new java.util.Date().getTime() %>">附加资料</a></ajax:link></td></tabs:inactiveTab>
        <tabs:activeTab   tabId="3"><td class="activeTab"  ><ajax:link targetElement="bodyId"><a href="my_basic_info.jsp?tab=3&empNo=<%=empNo %>&date=<%=new java.util.Date().getTime() %>">人事档案</a></ajax:link></td></tabs:activeTab>
        <tabs:inactiveTab tabId="3"><td class="inactiveTab"><ajax:link targetElement="bodyId"><a href="my_basic_info.jsp?tab=3&empNo=<%=empNo %>&date=<%=new java.util.Date().getTime() %>">人事档案</a></ajax:link></td></tabs:inactiveTab>
        <tr id="currEmpId" name="<%=empNo %>">
			<td colspan="3" id="tabContent" class="tabContent">          
				<tabs:tabContent tabId="1">
		    		<IFRAME ID="IFrame1" name="IFrame1" marginWidth="0" marginHeight="0" FRAMEBORDER="0" align="left"
			 		width="100%" height="450" SRC="updateEmpInit.action?empNo=<%=empNo %>&createFlag=<s:property value='createFlag'/>" style="overflow-y:auto;"></IFRAME>
		        </tabs:tabContent>
		        <tabs:tabContent tabId="2">
		        	<IFRAME ID="IFrame1" name="IFrame1" marginWidth="0" marginHeight="0" FRAMEBORDER="0" align="left"
			 		 width="100%" height="450" SRC="empAdditional.action?empNo=<%=empNo %>" style="overflow-y:auto;"></IFRAME>
		        </tabs:tabContent>
		        <tabs:tabContent tabId="3">
		        	<IFRAME ID="IFrame1" name="IFrame1" marginWidth="0" marginHeight="0" FRAMEBORDER="0" align="left"
			 		 width="100%" height="450" SRC="empProfile.action?empNo=<%=empNo %>" style="overflow-y:auto;"></IFRAME>
		        </tabs:tabContent>
		      </td>
		  </tr>
	</table>
</tabs:tabbedPane>
<SCRIPT type="text/javascript">
</SCRIPT>
</body>
</html>
