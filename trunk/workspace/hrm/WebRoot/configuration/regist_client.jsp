<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
		<title>�ͻ�ע��</title>
	</head>

	<body>
		
		<s:form id="regist_client" name="regist_client"
			namespace="/configuration" action="regist_client">
			<s:token />
			<TABLE>
				<tr>
				    <td align=right>��˾����<font color=red>*</font>:</td>
					<td><s:textfield id="client.clientName" name="client.clientName" size="30" maxlength="128" /></td>
				</tr>

				<tr>
					<td align=right>��˾���:</td>
					<td><s:textfield id="client.clientShortName" name="client.clientShortName" size="30" maxlength="64" /></td>
				</tr>

				<tr>
					<td align=right>��˾��ַ:</td>
					<td><s:textfield id="client.clientAddress" name="client.clientAddress" size="30" maxlength="128" /></td>
				</tr>

				<tr>
					<td align=right>��˾�ʱ�:</td>
					<td><s:textfield id="client.clientZip" name="client.clientZip" size="30" maxlength="8" /></td>
				</tr>

				<tr>
					<td align=right>��˾��ϵ�绰<font color=red>*</font>:</td>
					<td><s:textfield id="client.clientPhone" name="client.clientPhone" size="30" maxlength="32" /></td>
				</tr>

				<tr>
					<td align=right>��˾����:</td>
					<td><s:textfield id="client.clientFax" name="client.clientFax" size="30" maxlength="32" /></td>
				</tr>

				<tr>
				    <td align=right>��˾��ϵ��<font color=red>*</font>:</td>
					<td><s:textfield id="client.clientContactName" name="client.clientContactName" size="30" maxlength="64" /></td>
				</tr>
				<tr>
					<td align=right>��˾Email<font color=red>*</font>:</td>
					<td><s:textfield id="client.clientEmail" name="client.clientEmail" size="30" maxlength="64" /></td>
				</tr>
				<tr>
					<td align="right">��˾��ע��</td>
					<td ><s:textarea id="clientRemarks" name="client.clientRemarks" cols="50" rows="4" /></td>
				</tr>
				<tr>
					<td align="right">
						<input type="submit" value="��һ��">
					</td>
					<td align="center">
						<input id="active" name="active" type="button"
							onclick="concalOperation();" value="ȡ��">
					</td>
				</tr>
			</TABLE>
		</s:form>

		<script language="javascript">
	
	function concalOperation(){
	    window.location="login.jsp";
	}
</script>
	</body>
</html>
