<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@taglib prefix="ty" uri="/tengsource"%>
<%@ taglib prefix="s" uri="webwork"%>
<table id="left_menu" cellpadding="0" cellspacing="0">
	<ty:auth auths="911">
		<tr>
			<td class="td-r b">
				<a href="addUserInit.action"><img
						src='../resource/images/CreateUsers.gif'
						alt='�����û�'>
				</a>
			</td>
			<td class="td-b">
				<a href="userList.action">
					�û����� </a>
			</td>
		</tr>
	</ty:auth>
	<ty:auth auths="921">
		<tr>
			<td class="td-r b">
				<a href="addRoleInit.action"> <img
						src='../resource/images/CreateRoles.gif'
						alt='������ɫ'>
				</a>
			</td>
			<td class="td-b">
				<a href="getRoleList.action">��ɫ����
					 </a>
			</td>
		</tr>
	</ty:auth>
	
	<ty:auth auths="963">
		<tr>
			<td class="td-r b">
				<a href="backupResume.action"><img
						src='../resource/images/Backups.gif' alt='�����뻹ԭ'> </a>
			</td>
			<td class="td-b">
				<a href="backupResume.action">�����뻹ԭ</a>
			</td>
		</tr>
	</ty:auth>
	<ty:auth auths="941">
		<tr>
			<td class="td-r b">
				<a href="logscan.action"><img
						src='../resource/images/ConfigureTabs.gif' alt='��־����'> </a>
			</td>
			<td class="td-b">
				<a href="logscan.action">��־����</a>
			</td>
		</tr>
	</ty:auth>
	<ty:auth auths="962">
		<tr>
			<td class="td-r b">
				<a href="dataclean.action"><img
						src='../resource/images/DataSets.gif' alt='��������'> </a>
			</td>
			<td class="td-b">
				<a href="dataclean.action">��������</a>
			</td>
		</tr>
	</ty:auth>
	
	<ty:auth auths="951">
		<tr>
			<td class="td-r b">
				<a href="emailSearch.action"><img
						src='../resource/images/EmailSend.gif' alt='ϵͳ�ʼ�����'> </a>
			</td>
			<td class="td-b">
				<a href="emailSearch.action">ϵͳ�ʼ�����</A>
			</td>
		</tr>
	</ty:auth>
	<ty:auth auths="952">
		<tr>
			<td class="td-r b">
				<a href="config.action"><img
						src='../resource/images/Rebuild.gif' alt='�����������' />
			</td>
			<td class="td-b">
				<a href="config.action">�����������</a>
			</td>
		</tr>
	</ty:auth>
	<ty:auth auths="961">
		<tr>
			<td class="td-r b">
				<a href="systemconfiginit.action"><img
						src='../resource/images/Administration.gif' alt='ϵͳ��������'> </a>
			</td>
			<td class="td-b">
				<a href="systemconfiginit.action">ϵͳ��������</A>
			</td>
		</tr>
	</ty:auth>
	<ty:auth auths="702">
		<tr>
			<td class="td-r">
				<a href="<s:url value='/configuration/iodefList.action' includeParams='none'/>"><img src='../resource/images/MigrateFields.gif' alt='���ݽӿ�����'></a>
			</td>
			<td class="td-b"><a href="<s:url value='/configuration/iodefList.action' includeParams='none'/>">���ݽӿ�����</a></td>
		</tr>
	</ty:auth>

	<ty:auth auths="931">
		<tr>
			<td class="td-r b">
				<a href="clientInit.action"> <img
						src='../resource/images/License.gif' alt='��˾��������' />
			</td>
			<td class="td-b">
				<a href="clientInit.action">��˾ע����Ϣ</a>
			</td>
		</tr>
	</ty:auth>

	<!--
	<ty:auth auths="911">
		<tr>
			<td class="td-r b">
				<a href="clientscan.action"><img
						src='../resource/images/Meetings.gif' alt='�ͻ�����'> </a>
			</td>
			<td class="td-b">
				<a href="clientscan.action">�ͻ�����</a>
			</td>
		</tr>
	</ty:auth>
	-->
</table>
