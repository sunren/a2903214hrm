<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ taglib prefix="ty" uri="/tengsource"%>
<table id="left_menu" cellpadding="0" cellspacing="0" style="margin:0px; padding:0px" width="100%" border="0">
		<ty:auth auths="311 ">
		<tr>
			<td class="td-r" valign="top">			
				<a href="myTrainingList.action"><img src='../resource/images/MyProject.gif' alt='�ҵ���ѵ�ƻ�'></a>
			</td>
			<td class="td-b"><a href="myTrainingList.action">�ҵ���ѵ�ƻ�</a><br></td>
		</tr>
		</ty:auth>
		<ty:auth auths="311 ">		
		<tr>
			<td class="td-r">
				<a href="trepAddInit.action"><img src='../resource/images/CreateProject.gif' alt='��ѵ����'></a>
			</td>
			<td class="td-b"><a href="trepAddInit.action">��ѵ����</a></td>
		</tr>
		</ty:auth>
		<ty:auth auths="311,2 or 311,3">
		<tr>
			<td class="td-r">
				<a href="trepDeptApprove.action"><img src='../resource/images/DeptApprove.gif' alt='���ž�������'></a>
			</td>
			<td class="td-b"><a href="trepDeptApprove.action">���ž�������</a></td>
		</tr>
		</ty:auth>
		<ty:auth auths="301,2" >
		<tr>
			<td class="td-r">
			<a href="trepHrApprove.action"><img src='../resource/images/HRApprove.gif' alt='HR����'></a>
			</td>
			<td class="td-b"><a href="trepHrApprove.action">HR����</a></td>
		</tr>
		</ty:auth>
		
		<ty:auth auths="311,2 or 311,3 or 301,2">
		<tr>
			<td class="td-r">
				<a href="trepList.action"><img src='../resource/images/Reports.gif' alt='Ա����ѵ�ƻ�'></a>
			</td>
			<td class="td-b"><a href="trepList.action">Ա����ѵ�ƻ�</a></td>
		</tr>
		</ty:auth>
		<ty:auth auths="301 or 701" >
		<tr>
			<td class="td-r">
				<a href=""trReport.action""><img src='../resource/images/CallReports.gif' alt='��ѵ����'></a>
			</td>
			<td class="td-b"><a href="trReport.action">��ѵ����</a></td>
		</tr>			
		</ty:auth>
		<ty:auth auths="301 ">
		<tr>
			<td class="td-r">
				<a href="trcourseConfig.action"><img src='../resource/images/Project2Weeks.gif' alt='�γ�����'></a>
			</td>
			<td class="td-b"><a href="trcourseConfig.action">�γ�����</a></td>
		</tr>
		<tr>
			<td class="td-r">
				<a href="trtConfig.action"><img src='../resource/images/Administration.gif' alt='��ѵ����'></a>
			</td>
			<td class="td-b"><a href="trtConfig.action">��ѵ����</a></td>
		</tr>
		</ty:auth>	
</table>
