<%@page pageEncoding="GBK" contentType="text/html; charset=GBK" %>
<%@taglib prefix="s" uri="webwork"%>
<%@taglib uri="/WEB-INF/config/jenkov/tabbedpanetag.tld" prefix="tabs"  %>
<%@taglib uri="/WEB-INF/config/jenkov/logictags.tld"     prefix="logic" %>
<%@taglib uri="/WEB-INF/config/jenkov/requesttags.tld"   prefix="request" %>
<%@taglib uri="/WEB-INF/config/jenkov/ajaxtags.tld" prefix="ajax" %>
<%@taglib uri="/WEB-INF/config/jenkov/templatetag.tld"   prefix="tmpl" %>
<html>
<head>
<script type="text/javascript" src="../resource/js/jenkov/jenkov_ajaxScript.js"></script>
<!-- employee-create   此jsp可以去掉-->
<link href="../resource/css/tab.css" rel="stylesheet" type="text/css" />
<title>员工档案</title>
</head>
<body> 
<s:component template="bodyhead">
	<s:param name="pagetitle" value="'员工档案管理'" />
	<s:param name="helpUrl" value="'30'"/>
</s:component>
<table width="100%">
<tr>
    <td valign="top">
		<tabs:tabbedPane tabParam="tab" defaultTab="1" sessionKey="tab">
			<table cellspacing="0" cellpadding="5" width="100%">
			    <tr>
			        <tabs:activeTab   tabId="1"><td class="activeTab"  ><ajax:link targetElement="bodyId"><a href="profile_management.jsp?tab=1">人事合同</a></ajax:link></td></tabs:activeTab>
			        <tabs:inactiveTab tabId="1"><td class="inactiveTab"><ajax:link targetElement="bodyId"><a href="profile_management.jsp?tab=1">人事合同</a></ajax:link></td></tabs:inactiveTab>
			        <tabs:activeTab   tabId="2"><td class="activeTab"  ><ajax:link targetElement="bodyId"><a href="profile_management.jsp?tab=2">变动记录</a></ajax:link></td></tabs:activeTab>
			        <tabs:inactiveTab tabId="2"><td class="inactiveTab"><ajax:link targetElement="bodyId"><a href="profile_management.jsp?tab=2">变动记录</a></ajax:link></td></tabs:inactiveTab>
			        <tabs:activeTab   tabId="3"><td class="activeTab"  ><ajax:link targetElement="bodyId"><a href="profile_management.jsp?tab=3">考评记录</a></ajax:link></td></tabs:activeTab>
			        <tabs:inactiveTab tabId="3"><td class="inactiveTab"><ajax:link targetElement="bodyId"><a href="profile_management.jsp?tab=3">考评记录</a></ajax:link></td></tabs:inactiveTab>
			        <tabs:activeTab   tabId="4"><td class="activeTab"  ><ajax:link targetElement="bodyId"><a href="profile_management.jsp?tab=4">奖惩记录</a></ajax:link></td></tabs:activeTab>
			        <tabs:inactiveTab tabId="4"><td class="inactiveTab"><ajax:link targetElement="bodyId"><a href="profile_management.jsp?tab=4">奖惩记录</a></ajax:link></td></tabs:inactiveTab>
			        <tabs:activeTab   tabId="5"><td class="activeTab"  ><ajax:link targetElement="bodyId"><a href="profile_management.jsp?tab=5">离职记录</a></ajax:link></td></tabs:activeTab>
			        <tabs:inactiveTab tabId="5"><td class="inactiveTab"><ajax:link targetElement="bodyId"><a href="profile_management.jsp?tab=5">离职记录</a></ajax:link></td></tabs:inactiveTab>
			    </tr>
			    <tr>
			    	<td colspan="5" id="tabContent" class="tabContent">          
			    	<tabs:tabContent tabId="1">
			    		<IFRAME ID="IFrame1" name="IFrame1" marginWidth="0" marginHeight="0" FRAMEBORDER="0" align="left"
				 			 width="100%" height="480" SRC="listContract.action" style="overflow-y:auto;"></IFRAME>
			        </tabs:tabContent>
			        <tabs:tabContent tabId="2">
			        	<IFRAME ID="IFrame1" name="IFrame1" marginWidth="0" marginHeight="0" FRAMEBORDER="0" align="left"
				 		 width="100%" height="450" SRC="listTransfer.action" style="overflow-y:auto;"></IFRAME>
			        </tabs:tabContent>
			        <tabs:tabContent tabId="3">
			        	<IFRAME ID="IFrame1" name="IFrame1" marginWidth="0" marginHeight="0" FRAMEBORDER="0" align="left"
				 		 width="100%" height="450" SRC="listEval.action" style="overflow-y:auto;"></IFRAME>
			        </tabs:tabContent>
			        <tabs:tabContent tabId="4">
			        	<IFRAME ID="IFrame1" name="IFrame1" marginWidth="0" marginHeight="0" FRAMEBORDER="0" align="left"
				 		 width="100%" height="450" SRC="listReward.action" style="overflow-y:auto;"></IFRAME>
			        </tabs:tabContent>
			        <tabs:tabContent tabId="5">
			        	<IFRAME ID="IFrame1" name="IFrame1" marginWidth="0" marginHeight="0" FRAMEBORDER="0" align="left"
				 		 width="100%" height="450" SRC="listQuit.action" style="overflow-y:auto;"></IFRAME>
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
