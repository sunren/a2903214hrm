<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
		<title>客户注册</title>
	</head>

	<body>
		
		<s:form id="regist_client" name="regist_client"
			namespace="/configuration" action="regist_client">
			<s:token />
			<TABLE>
				<tr>
				    <td align=right>公司名称<font color=red>*</font>:</td>
					<td><s:textfield id="client.clientName" name="client.clientName" size="30" maxlength="128" /></td>
				</tr>

				<tr>
					<td align=right>公司简称:</td>
					<td><s:textfield id="client.clientShortName" name="client.clientShortName" size="30" maxlength="64" /></td>
				</tr>

				<tr>
					<td align=right>公司地址:</td>
					<td><s:textfield id="client.clientAddress" name="client.clientAddress" size="30" maxlength="128" /></td>
				</tr>

				<tr>
					<td align=right>公司邮编:</td>
					<td><s:textfield id="client.clientZip" name="client.clientZip" size="30" maxlength="8" /></td>
				</tr>

				<tr>
					<td align=right>公司联系电话<font color=red>*</font>:</td>
					<td><s:textfield id="client.clientPhone" name="client.clientPhone" size="30" maxlength="32" /></td>
				</tr>

				<tr>
					<td align=right>公司传真:</td>
					<td><s:textfield id="client.clientFax" name="client.clientFax" size="30" maxlength="32" /></td>
				</tr>

				<tr>
				    <td align=right>公司联系人<font color=red>*</font>:</td>
					<td><s:textfield id="client.clientContactName" name="client.clientContactName" size="30" maxlength="64" /></td>
				</tr>
				<tr>
					<td align=right>公司Email<font color=red>*</font>:</td>
					<td><s:textfield id="client.clientEmail" name="client.clientEmail" size="30" maxlength="64" /></td>
				</tr>
				<tr>
					<td align="right">公司备注：</td>
					<td ><s:textarea id="clientRemarks" name="client.clientRemarks" cols="50" rows="4" /></td>
				</tr>
				<tr>
					<td align="right">
						<input type="submit" value="下一步">
					</td>
					<td align="center">
						<input id="active" name="active" type="button"
							onclick="concalOperation();" value="取消">
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
