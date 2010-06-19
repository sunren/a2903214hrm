<%@page pageEncoding="GBK" contentType="text/html; charset=GBK" %>
<%@taglib prefix="ww" uri="webwork"%>
<head>
<SCRIPT language=javascript src="../resource/js/validator.js"></SCRIPT>
</head>
<body>
	<ww:component template="bodyhead">
		<ww:param name="pagetitle" value="'增加培训类型'" />
		<ww:param name="helpUrl" value="'56'" />
	</ww:component>
	
<div id="create_trtype" align="left">
<ww:form name="createTrt" action="trtAdd" namespace="/training" method="POST">
	<ww:token></ww:token>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
				<td width="10%" align="right">类型编号<font color="red">*</font>：</td><td width="35%"><ww:textfield id="trtNo" name="trt.trtNo" size="16" required="true" maxlength="16" onkeyup="notchinese(this);"/></td>
				<td width="10%" align="right">类型名称<font color="red">*</font>：</td><td width="35%"><ww:textfield id="trtName" name="trt.trtName" size="20" required="true" maxlength="64"/></td>	
		</tr>
		<tr>		
				<ww:textarea id="trtDesc" label="类型描述" name="trt.trtDesc" required="false" rows="3" cols="43"/>
		</tr>
 		<tr>
				 <td colspan="3" align="center">
				 	<input type="submit" value="确定" refreshId="tabContent">
				 	&nbsp;<input type="reset" value="取消">
				 </td>
				 <td>                                                      
				 </td>     
		</tr>
	</table>
</ww:form>
</div> 
</body>	