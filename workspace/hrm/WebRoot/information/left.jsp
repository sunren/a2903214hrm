<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@taglib prefix="ty" uri="/tengsource"%>
<%@ taglib prefix="s" uri="webwork"%>
<table id="left_menu" cellpadding="0" cellspacing="0">
	<ty:auth auths="802 or 801">
		<tr>
			<td class="td-r b">
				<a href="searchInfo.action"><img
						src='../resource/images/SugarPortal.gif'
						alt='公司信息'>
				</a>
			</td>
			<td class="td-b">
				<a href="searchInfo.action">
					公司信息 </a>
			</td>
		</tr>
	</ty:auth>	
	<ty:auth auths="801">
		<tr>
			<td class="td-r b">
				<a href="createInfoInit.action"><img
						src='../resource/images/CreateContacts.gif'
						alt='新增公司信息'>
				</a>
			</td>
			<td class="td-b">
				<a href="createInfoInit.action">
					新增公司信息 </a>
			</td>
		</tr>

	</ty:auth>	
<!-- 
		<tr>
			<td class="td-r b">
				<img
						src='../resource/images/icon_email_send.gif'
						alt='个人消息'>
				
			</td>
			<td class="td-b">
				个人消息
			</td>
		</tr>
		<tr>
			<td class="td-r b">
				<img
						src='../resource/images/icon_email_folder_sent.gif'
						alt='信息管理'>
				
			</td>
			<td class="td-b">
				消息管理
			</td>
		</tr>
		 -->
	<!-- 管理员可以群发邮件 --> 
	<ty:auth auths="801">
		<tr>
			<td class="td-r b">
				<a href="emailsend.action"><img
						src='../resource/images/EmailSend.gif'
						alt='邮件通知'>
				</a>
			</td>
			<td class="td-b">
				<a href="emailsend.action">
					邮件通知 </a>
			</td>
		</tr>
	</ty:auth>
	<!-- 管理员可以维护信息种类--> 
	<ty:auth auths="801">
		<tr>
			<td class="td-r b">
				<a href="NewsInfo.action"><img
						src='../resource/images/Rebuild.gif'
						alt='公司信息种类'>
				</a>
			</td>
			<td class="td-b">
				<a href="NewsInfo.action">
					公司信息种类 </a>
			</td>
		</tr>
	</ty:auth>	
	
		<!-- 所有人均可以修改本人密码--> 
		<s:if test = "#session.loginModel!='alimt'">	
		<tr>
			<td class="td-r">
				<a href="passwordOwn.action"> 
				<img src='../resource/images/edit_inline.gif' alt='修改密码'>
				</a>
			</td>
			<td class="td-b">
			<a href="passwordOwn.action">修改密码</a></td>
		</tr>
		</s:if>
		
</table>
