<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<html>
	<head>
		<script type="text/javascript" src="../resource/js/configuration/configMail.js"></script>
	</head>
<body>
<div class="wrapper">
	<div class="tipdiv">
		<p>1. �����ʼ����Ͳ������ʼ������ߵ�ַ��URL���Ӹ�Ŀ¼��ϵͳ����Աǩ����ϵͳ����Ա�绰</p>
		<p>2. �����������Ӳ����������ʼ�(SMTP)��������ַ���ʻ����ơ�����ȣ��ڱ���ǰ���û������Ȳ��Դ������Ƿ����</p>
		<p>3. �������Ӹ߼��������������Ƿ���Ҫ��֤���������ӳ�ʱʱ�䡢ÿ�������ʼ�����ʱ������ܴ�����ʼ�����</p>
	</div>
	<div class="contentdiv">
		<form id="mailForm" action="mailUpdateConfig.action" method="post">
		<s:token />
		<table align="left">
		<tr align="left">
			<td>ϵͳ����Ա���䣺</td>
			<td><s:textfield id="email_sys_sender" name="email_sys_sender" size="35" onkeyup="notchinese(this);" /> </td>
			<td>����SMTP��������</td>
			<td><s:textfield id="email_smtp_host" name="email_smtp_host" size="25" onkeyup="notchinese(this);" /> </td>
		</tr>
		<tr>
			<td>ϵͳURL��Ŀ¼��</td>
			<td><s:textfield id="email_sys_url" name="email_sys_url" size="35" onkeyup="notchinese(this);" /></td>
			<td>���������ʻ�����</td>
			<td><s:textfield id="email_user_name" name="email_user_name" size="25" onkeyup="notchinese(this);" /></td>
		</tr>
		<tr>
			<td>ϵͳ����Աǩ����</td>
			<td><s:textfield id="email_sys_mailSystemName" name="email_sys_mailSystemName" size="35" onkeyup="notchinese(this);" /></td>
			<td>�����������룺</td>
			<td><input type="password" id="email_user_password" name="email_user_password" size="25" value="<s:property value="email_user_password"/>" /> </td>
		</tr>
		<tr>
			<td>ϵͳ����Ա�绰��</td>
			<td><s:textfield id="email_sys_mailAdminPhone" name="email_sys_mailAdminPhone" size="35" onkeyup="notchinese(this);" /></td>
			<td>�����Ƿ���Ҫ��֤��</td>
			<td>				    
	            <s:hidden id="email_smtp_auth" name="email_smtp_auth"></s:hidden>
				<s:if test="email_smtp_auth==1">
				     <input type="checkbox" id="emailSmtpAuth" class="checkbox" onClick="changeEmailAmtpAuth()" name="emailSmtpAuth" value="1" checked="checked">
				</s:if>
				<s:else>
				     <input type="checkbox" id="emailSmtpAuth" class="checkbox" onClick="changeEmailAmtpAuth()" name="emailSmtpAuth"  value="1">
				</s:else>
			</td>
		</tr>
		<tr>
			<td> �������ӳ�ʱ��
				<s:textfield id="email_smtp_timeout" name="email_smtp_timeout"
					size="5" onkeyup="value=value.replace(/\D/g,'')" />
				��
			</td>
			<td>ÿ�δ����ʼ�������
				<s:textfield id="email_send_pages" name="email_send_pages" size="5"
					onkeyup="value=value.replace(/\D/g,'')" />
			</td>
			<td nowrap="nowrap">
				email���ͼ����
				<s:textfield id="emailRepeat" name="emailRepeat" size="5" onkeyup="value=value.replace(/\D/g,'')"/>����
			</td>
		</tr>
		<tr>
		    <td align="center" colspan="4">
		    	<input type="button" id="updateMailBt" onclick="return gotoUpdateMail();" value=" �� �� ">
				<input type='button' id="testMailBt" name="check" value="�����ʼ�����"
					onclick="return checkTestMail();" />
		    </td>
		</tr>
		<tr>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
	</table>
	</form>
	<p>&nbsp;</p>
	<p>&nbsp;</p>
	<p>&nbsp;</p>
	<p>&nbsp;</p>
	<p>&nbsp;</p>
	<p>&nbsp;</p>
	<p>&nbsp;</p>
	</div>
</div>
</body>
</html>