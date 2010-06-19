<%@ page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<%@ taglib uri="/WEB-INF/config/jenkov/ajaxtags.tld"		prefix="ajax"%>
<%@ taglib uri="/WEB-INF/config/jenkov/tabbedpanetag.tld"	prefix="tabs"%>
<html>
	<head>
		<!-- tab框架信息 -->   
        <link href="<s:url value="/resource/css/tab.css"/>" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../resource/js/jenkov/jenkov_ajaxScript.js"></script>
		
		<title>考勤设置</title>
	</head>
	<body> 
<s:component template="bodyhead">
	<s:param name="pagetitle" value="'考勤设置'" />
	<s:param name="helpUrl" value="'30'"/>
</s:component>
<table width="100%">
<tr>
    <td valign="top">
		<tabs:tabbedPane tabParam="tab" defaultTab="1" sessionKey="tab">
			<table cellspacing="0" cellpadding="5" width="100%">
			    <tr>
			        <tabs:activeTab   tabId="1"><td class="activeTab"  ><ajax:link targetElement="bodyId"><a href="leavebalance_management.jsp?tab=1">员工休假管理</a></ajax:link></td></tabs:activeTab>
			        <tabs:inactiveTab tabId="1"><td class="inactiveTab"><ajax:link targetElement="bodyId"><a href="leavebalance_management.jsp?tab=1">员工休假管理</a></ajax:link></td></tabs:inactiveTab>
			        <tabs:activeTab   tabId="2"><td class="activeTab"  ><ajax:link targetElement="bodyId"><a href="leavebalance_management.jsp?tab=2">假期日历设置</a></ajax:link></td></tabs:activeTab>
			        <tabs:inactiveTab tabId="2"><td class="inactiveTab"><ajax:link targetElement="bodyId"><a href="leavebalance_management.jsp?tab=2">假期日历设置</a></ajax:link></td></tabs:inactiveTab>
			        <tabs:activeTab   tabId="3"><td class="activeTab"  ><ajax:link targetElement="bodyId"><a href="leavebalance_management.jsp?tab=3">考勤班次设置</a></ajax:link></td></tabs:activeTab>
			        <tabs:inactiveTab tabId="3"><td class="inactiveTab"><ajax:link targetElement="bodyId"><a href="leavebalance_management.jsp?tab=3">考勤班次设置</a></ajax:link></td></tabs:inactiveTab>
			    </tr>
			    <tr>
			    	<td colspan="3" id="tabContent" class="tabContent">          
			    	<tabs:tabContent tabId="1">
			    		<IFRAME ID="IFrame1" name="IFrame1" marginWidth="0" marginHeight="0" FRAMEBORDER="0" align="left"
				 			 width="100%" height="430" SRC="leavebalanceCurYear.action" style="overflow-y:auto;"></IFRAME>
			        </tabs:tabContent>
			        <tabs:tabContent tabId="2">
			        	<IFRAME ID="IFrame1" name="IFrame1" marginWidth="0" marginHeight="0" FRAMEBORDER="0" align="left"
				 		 width="100%" height="430" SRC="leavecalendarManage.action" style="overflow-y:auto;"></IFRAME>
			        </tabs:tabContent>
			        <tabs:tabContent tabId="3">
			        	<IFRAME ID="IFrame1" name="IFrame1" marginWidth="0" marginHeight="0" FRAMEBORDER="0" align="left"
				 		 width="100%" height="430" SRC="examinShiftSearch.action" style="overflow-y:auto;"></IFRAME>
			        </tabs:tabContent>
			    	</td>
			    </tr>
			</table>
		</tabs:tabbedPane>
    </td>
</tr>
</table>
               
    
</body>
</html>