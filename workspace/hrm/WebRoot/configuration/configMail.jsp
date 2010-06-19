<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<html>
	<head>
		<script type="text/javascript" src="../resource/js/configuration/configMail.js"></script>
	</head>
<body>
<div class="wrapper">
	<div class="tipdiv">
		<p>1. 设置邮件发送参数：邮件发送者地址、URL链接根目录、系统管理员签名、系统管理员电话</p>
		<p>2. 设置邮箱链接参数：发送邮件(SMTP)服务器地址、帐户名称、密码等；在保存前，用户可以先测试此邮箱是否可用</p>
		<p>3. 设置链接高级参数：此邮箱是否需要验证、邮箱连接超时时间、每次运行邮件发送时，最多能处理的邮件数量</p>
	</div>
	<div class="contentdiv">
		<form id="mailForm" action="mailUpdateConfig.action" method="post">
		<s:token />
		<table align="left">
		<tr align="left">
			<td>系统管理员邮箱：</td>
			<td><s:textfield id="email_sys_sender" name="email_sys_sender" size="35" onkeyup="notchinese(this);" /> </td>
			<td>发送SMTP服务器：</td>
			<td><s:textfield id="email_smtp_host" name="email_smtp_host" size="25" onkeyup="notchinese(this);" /> </td>
		</tr>
		<tr>
			<td>系统URL根目录：</td>
			<td><s:textfield id="email_sys_url" name="email_sys_url" size="35" onkeyup="notchinese(this);" /></td>
			<td>发送邮箱帐户名：</td>
			<td><s:textfield id="email_user_name" name="email_user_name" size="25" onkeyup="notchinese(this);" /></td>
		</tr>
		<tr>
			<td>系统管理员签名：</td>
			<td><s:textfield id="email_sys_mailSystemName" name="email_sys_mailSystemName" size="35" onkeyup="notchinese(this);" /></td>
			<td>发送邮箱密码：</td>
			<td><input type="password" id="email_user_password" name="email_user_password" size="25" value="<s:property value="email_user_password"/>" /> </td>
		</tr>
		<tr>
			<td>系统管理员电话：</td>
			<td><s:textfield id="email_sys_mailAdminPhone" name="email_sys_mailAdminPhone" size="35" onkeyup="notchinese(this);" /></td>
			<td>邮箱是否需要验证：</td>
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
			<td> 邮箱连接超时：
				<s:textfield id="email_smtp_timeout" name="email_smtp_timeout"
					size="5" onkeyup="value=value.replace(/\D/g,'')" />
				秒
			</td>
			<td>每次处理邮件数量：
				<s:textfield id="email_send_pages" name="email_send_pages" size="5"
					onkeyup="value=value.replace(/\D/g,'')" />
			</td>
			<td nowrap="nowrap">
				email发送间隔：
				<s:textfield id="emailRepeat" name="emailRepeat" size="5" onkeyup="value=value.replace(/\D/g,'')"/>分钟
			</td>
		</tr>
		<tr>
		    <td align="center" colspan="4">
		    	<input type="button" id="updateMailBt" onclick="return gotoUpdateMail();" value=" 更 新 ">
				<input type='button' id="testMailBt" name="check" value="测试邮件发送"
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