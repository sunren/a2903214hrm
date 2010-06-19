<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@taglib prefix="ww" uri="webwork"%>
<%@ taglib uri="/WEB-INF/config/jenkov/ajaxtags.tld"		prefix="ajax"%>
<%@ taglib uri="/WEB-INF/config/jenkov/tabbedpanetag.tld"	prefix="tabs"%>

<html>
	<head>
		<%
			String tabSessionIndex=(String)session.getAttribute("tab");
			String tabRequestIndex=request.getParameter("tab");
			if(tabRequestIndex!=null&&tabRequestIndex.trim().length()>0){
				if(!(tabRequestIndex.equals("1")||tabRequestIndex.equals("2"))){
					request.getParameterMap().put("tab","1");
				}
			}else{
				if(tabSessionIndex==null||tabSessionIndex.trim().length()==0||(!(tabSessionIndex.equals("1")||tabSessionIndex.equals("2")))){
					session.setAttribute("tab","1");
				}
			}
		%>
		
		<!-- tabøÚº‹–≈œ¢ -->
		<link href="<ww:url value="/resource/css/tab.css"/>" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../resource/js/jenkov/jenkov_ajaxScript.js"></script>
		
		<title>«ÎºŸº”∞‡…Í«Î</title>
	</head>
	<body>
		<ww:component template="bodyhead">
			<ww:param name="pagetitle" value="'«ÎºŸº”∞‡…Í«Î'" />
			<ww:param name="helpUrl" value="15" />
		</ww:component>
		<table width="100%">
			<tr>
				<td valign="top">
					<tabs:tabbedPane tabParam="tab" defaultTab="1" sessionKey="tab">
						<table cellspacing="0" cellpadding="5" width="100%">
						    <tr>
						        <tabs:activeTab   tabId="1"><td class="activeTab"  ><ajax:link targetElement="bodyId"><a href="empexamin_add.jsp?tab=1">«ÎºŸ…Í«Î</a></ajax:link></td></tabs:activeTab>
						        <tabs:inactiveTab tabId="1"><td class="inactiveTab"><ajax:link targetElement="bodyId"><a href="empexamin_add.jsp?tab=1">«ÎºŸ…Í«Î</a></ajax:link></td></tabs:inactiveTab>
						        <tabs:activeTab   tabId="2"><td class="activeTab"  ><ajax:link targetElement="bodyId"><a href="empexamin_add.jsp?tab=2">º”∞‡…Í«Î</a></ajax:link></td></tabs:activeTab>
						        <tabs:inactiveTab tabId="2"><td class="inactiveTab"><ajax:link targetElement="bodyId"><a href="empexamin_add.jsp?tab=2">º”∞‡…Í«Î</a></ajax:link></td></tabs:inactiveTab>
							</tr>
						    <tr>
						    	<td colspan="3" id="tabContent" class="tabContent">    
						    	  
							    	<tabs:tabContent tabId="1">
							    	
							    	    <IFRAME ID="IFrame1" name="IFrame1" marginWidth="0" marginHeight="0" FRAMEBORDER="0" align="left"
								 			 width="100%" height="430" SRC="empLRAddInit.action" style="overflow-y:auto;"></IFRAME>
								 	
								 		</tabs:tabContent>
							    	
							    	
							        <tabs:tabContent tabId="2">
							        	<IFRAME ID="IFrame1" name="IFrame1" marginWidth="0" marginHeight="0" FRAMEBORDER="0" align="left"
								 		 width="100%" height="430" SRC="empOTAddInit.action" style="overflow-y:auto;"></IFRAME>
							        </tabs:tabContent>
						    	</td>
						    </tr>
						</table>
					</tabs:tabbedPane>
				</td>
			</tr>
		</table>
		
		<!-- ajax øÚº‹ bug ¡˜≥ˆø’∏Ò-->
	</body>
</html>