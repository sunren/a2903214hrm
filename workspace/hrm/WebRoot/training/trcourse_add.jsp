<%@page pageEncoding="GBK" contentType="text/html; charset=GBK" %>
<%@taglib prefix="ww" uri="webwork"%>
<%@taglib prefix="log" uri="http://jakarta.apache.org/taglibs/log-1.0"%>
<%@ taglib uri="/WEB-INF/config/jenkov/ajaxtags.tld" prefix="ajax" %>

<head>
<link href="<ww:url value="/resource/css/style.css"/>" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<ww:url value="/resource/js/prototype.js"/>"></script>
<script type="text/javascript" src="../resource/js/scriptaculous/scriptaculous.js"></script>
<script language="javascript" src="../resource/js/validator.js"></script>

	<script type="text/javascript" src="../resource/js/jscalendar/calendar.js"></script>
	<script type="text/javascript" src="../resource/js/jscalendar/calendar-setup.js"></script>
	<script type="text/javascript" src="../resource/js/jscalendar/lang/zh.js"></script>
	<link rel="stylesheet" type="text/css" media="all" href="../resource/js/jscalendar/skins/aqua/theme.css" title="Aqua" />

</head>
<body>
	<ww:component template="bodyhead">
		<ww:param name="pagetitle" value="'���ӿγ�'" />
		<ww:param name="helpUrl" value="'49'" />
	</ww:component>	
<div id="create_trcourse" align="left">
<ww:form name="createTrcourse" action="trcourseAdd" namespace="/training" method="POST" enctype="multipart/form-data">
	<ww:token></ww:token>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
				<ww:textfield id="trcName" label="�γ�����" name="trc.trcName" size="32" required="true" maxlength="64"/>								
		</tr>
		
		<tr>
			    <ww:select id="trcType" label="�γ���ѵ����" name="trc.trcType.trtNo" value="trc.trcType.trtNo" list="trTypeList" listKey="trtNo" listValue="trtName" emptyOption="true" required="true" />
				<ww:select name="trc.trcStatus" label="״̬" required="true" value="trc.trcstatus" list="#{1:'������', 0:'�ر�'}"/>
		</tr>
		
		<tr>
				<ww:file label="��ѵ����" id="trcFileName" name="file"/>
		</tr>
		<tr>
				<td align="right">�γ�����<font color="red">*</font>��</td><td colspan="3"><ww:textarea id="trcInfo" name="trc.trcInfo" required="true" rows="6" cols="90"/></td>
		</tr>
			
 		<tr>
 				<td></td>
				 <td align="left" >
				 	<input type="submit" value="ȷ��" refreshId="tabContent">
				 	&nbsp;<input type="reset" value="����">
				 	<A class=tabDetailViewDFLink href="trcourseConfig.action">���ؿγ�����ҳ��&nbsp;</A>&nbsp;&nbsp;&nbsp;&nbsp;
				 </td>
				 <td colspan="1">                                                      
				 </td>     
		</tr>
	</table>
</ww:form>
</div>      
  </table>                                  
<div id="errorMsg" style="display:none;border:1px solid #e00;background-color:#fee;padding:2px;margin-top:8px;width:300px;font:normal 12px Arial;color:#900"></div>
</body>	