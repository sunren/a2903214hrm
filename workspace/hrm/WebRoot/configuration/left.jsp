<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@taglib prefix="ty" uri="/tengsource"%>
<%@ taglib prefix="s" uri="webwork"%>
<table id="left_menu" cellpadding="0" cellspacing="0">
	<ty:auth auths="911">
		<tr>
			<td class="td-r b">
				<a href="addUserInit.action"><img
						src='../resource/images/CreateUsers.gif'
						alt='新增用户'>
				</a>
			</td>
			<td class="td-b">
				<a href="userList.action">
					用户管理 </a>
			</td>
		</tr>
	</ty:auth>
	<ty:auth auths="921">
		<tr>
			<td class="td-r b">
				<a href="addRoleInit.action"> <img
						src='../resource/images/CreateRoles.gif'
						alt='新增角色'>
				</a>
			</td>
			<td class="td-b">
				<a href="getRoleList.action">角色管理
					 </a>
			</td>
		</tr>
	</ty:auth>
	
	<ty:auth auths="963">
		<tr>
			<td class="td-r b">
				<a href="backupResume.action"><img
						src='../resource/images/Backups.gif' alt='备份与还原'> </a>
			</td>
			<td class="td-b">
				<a href="backupResume.action">备份与还原</a>
			</td>
		</tr>
	</ty:auth>
	<ty:auth auths="941">
		<tr>
			<td class="td-r b">
				<a href="logscan.action"><img
						src='../resource/images/ConfigureTabs.gif' alt='日志管理'> </a>
			</td>
			<td class="td-b">
				<a href="logscan.action">日志管理</a>
			</td>
		</tr>
	</ty:auth>
	<ty:auth auths="962">
		<tr>
			<td class="td-r b">
				<a href="dataclean.action"><img
						src='../resource/images/DataSets.gif' alt='数据清理'> </a>
			</td>
			<td class="td-b">
				<a href="dataclean.action">数据清理</a>
			</td>
		</tr>
	</ty:auth>
	
	<ty:auth auths="951">
		<tr>
			<td class="td-r b">
				<a href="emailSearch.action"><img
						src='../resource/images/EmailSend.gif' alt='系统邮件管理'> </a>
			</td>
			<td class="td-b">
				<a href="emailSearch.action">系统邮件管理</A>
			</td>
		</tr>
	</ty:auth>
	<ty:auth auths="952">
		<tr>
			<td class="td-r b">
				<a href="config.action"><img
						src='../resource/images/Rebuild.gif' alt='基础表格设置' />
			</td>
			<td class="td-b">
				<a href="config.action">基础表格设置</a>
			</td>
		</tr>
	</ty:auth>
	<ty:auth auths="961">
		<tr>
			<td class="td-r b">
				<a href="systemconfiginit.action"><img
						src='../resource/images/Administration.gif' alt='系统参数设置'> </a>
			</td>
			<td class="td-b">
				<a href="systemconfiginit.action">系统参数设置</A>
			</td>
		</tr>
	</ty:auth>
	<ty:auth auths="702">
		<tr>
			<td class="td-r">
				<a href="<s:url value='/configuration/iodefList.action' includeParams='none'/>"><img src='../resource/images/MigrateFields.gif' alt='数据接口配置'></a>
			</td>
			<td class="td-b"><a href="<s:url value='/configuration/iodefList.action' includeParams='none'/>">数据接口配置</a></td>
		</tr>
	</ty:auth>

	<ty:auth auths="931">
		<tr>
			<td class="td-r b">
				<a href="clientInit.action"> <img
						src='../resource/images/License.gif' alt='公司资料设置' />
			</td>
			<td class="td-b">
				<a href="clientInit.action">公司注册信息</a>
			</td>
		</tr>
	</ty:auth>

	<!--
	<ty:auth auths="911">
		<tr>
			<td class="td-r b">
				<a href="clientscan.action"><img
						src='../resource/images/Meetings.gif' alt='客户管理'> </a>
			</td>
			<td class="td-b">
				<a href="clientscan.action">客户管理</a>
			</td>
		</tr>
	</ty:auth>
	-->
</table>
