<%@page pageEncoding="GBK" contentType="text/html; charset=GBK" %>
<%@taglib prefix="ww" uri="webwork"%>

<body>
	<ww:component template="bodyhead">
		<ww:param name="pagetitle" value="'������ѵ����'" />
		<ww:param name="helpUrl" value="'56'" />
	</ww:component>
	
<div id="create_trtype" align="left">
<ww:form name="createTrt" action="trtUpdate" namespace="/training" method="POST">
	<ww:token></ww:token>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
				<ww:hidden name="trt.trtNo"></ww:hidden>
				<td align="right" width="15%">���ͱ�ţ�</td><td align="left" width="25%"><ww:property value="trt.trtNo"/></td>
				<td align="right" width="15%">�������ƣ�</td><td align="left" width="35%"><ww:textfield id="trtName" name="trt.trtName" size="20" required="true" maxlength="64"/></td>
		</tr>
		
		<tr>		
				<td align="right">������</td><td colspan="3"><ww:textarea id="trtDesc" name="trt.trtDesc" required="false" rows="3" cols="40"/>
		</tr>
 		<tr>
				 <td colspan="3" align="center">
				 	<input type="submit" value="����" refreshId="tabContent">
				 	&nbsp;<input type="reset" value="����">
				 </td>
				 <td>                                                      
				 </td>     
		</tr>
	</table>
</ww:form>
</div>   
</body>	