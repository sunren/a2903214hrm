<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@taglib prefix="ty" uri="/tengsource"%>
<%@ taglib prefix="s" uri="webwork"%>
<table id="left_menu" cellpadding="0" cellspacing="0">
	<ty:auth auths="802 or 801">
		<tr>
			<td class="td-r b">
				<a href="searchInfo.action"><img
						src='../resource/images/SugarPortal.gif'
						alt='��˾��Ϣ'>
				</a>
			</td>
			<td class="td-b">
				<a href="searchInfo.action">
					��˾��Ϣ </a>
			</td>
		</tr>
	</ty:auth>	
	<ty:auth auths="801">
		<tr>
			<td class="td-r b">
				<a href="createInfoInit.action"><img
						src='../resource/images/CreateContacts.gif'
						alt='������˾��Ϣ'>
				</a>
			</td>
			<td class="td-b">
				<a href="createInfoInit.action">
					������˾��Ϣ </a>
			</td>
		</tr>

	</ty:auth>	
<!-- 
		<tr>
			<td class="td-r b">
				<img
						src='../resource/images/icon_email_send.gif'
						alt='������Ϣ'>
				
			</td>
			<td class="td-b">
				������Ϣ
			</td>
		</tr>
		<tr>
			<td class="td-r b">
				<img
						src='../resource/images/icon_email_folder_sent.gif'
						alt='��Ϣ����'>
				
			</td>
			<td class="td-b">
				��Ϣ����
			</td>
		</tr>
		 -->
	<!-- ����Ա����Ⱥ���ʼ� --> 
	<ty:auth auths="801">
		<tr>
			<td class="td-r b">
				<a href="emailsend.action"><img
						src='../resource/images/EmailSend.gif'
						alt='�ʼ�֪ͨ'>
				</a>
			</td>
			<td class="td-b">
				<a href="emailsend.action">
					�ʼ�֪ͨ </a>
			</td>
		</tr>
	</ty:auth>
	<!-- ����Ա����ά����Ϣ����--> 
	<ty:auth auths="801">
		<tr>
			<td class="td-r b">
				<a href="NewsInfo.action"><img
						src='../resource/images/Rebuild.gif'
						alt='��˾��Ϣ����'>
				</a>
			</td>
			<td class="td-b">
				<a href="NewsInfo.action">
					��˾��Ϣ���� </a>
			</td>
		</tr>
	</ty:auth>	
	
		<!-- �����˾������޸ı�������--> 
		<s:if test = "#session.loginModel!='alimt'">	
		<tr>
			<td class="td-r">
				<a href="passwordOwn.action"> 
				<img src='../resource/images/edit_inline.gif' alt='�޸�����'>
				</a>
			</td>
			<td class="td-b">
			<a href="passwordOwn.action">�޸�����</a></td>
		</tr>
		</s:if>
		
</table>
