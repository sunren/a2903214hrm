<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ taglib prefix="ww" uri="webwork"%>
<%@taglib prefix="ty" uri="/tengsource"%>
<table id="left_menu" cellpadding="0" cellspacing="0" style="margin:0px; padding:0px" width="100%" border="0">
		<!-- 101 HRԱ������Ա HR����ӵ�е�������֯�ṹ��/��������Ȩ�ޣ���111 �ܾ������ž�����ͨԱ��  -->
		<!-- ����Ա���ܾ������ž�����Բ鿴��ְԱ�����ܾ���Ͳ��ž������ض������£������޸�Ա����Ϣ -->
		<ty:auth auths="101 or 111,2 or 111,3" >	
		<tr>
			<td class="td-r">
				<a href="<ww:url value='/profile/searchEmp.action'/>"><img src='../resource/images/Teams.gif' alt='Ա������'></a>
			</td>
			<td class="td-b"><a href="<ww:url value='/profile/searchEmp.action'/>">Ա������</a></td>
		</tr>
		</ty:auth>
		<!-- ����Ա���Զ���Ա������ְ��������ְʱ�����̴����������ϡ��������ϡ����µ��� -->
		<ty:auth auths="101">
		<tr>
			<td class="td-r">
				<a href="empCreateInit.action"><img src='../resource/images/CreateEmployees.gif' alt='��Ա����ְ'></a>
			</td>
			<td class="td-b"><a href="empCreateInit.action">��Ա����ְ</a></td>
		</tr>
		</ty:auth>
		<!-- ����Ա���Թ���Ա����ͬ�������˿���ȥ�����������棬�鿴��ͬ������ -->
		<ty:auth auths="101" >	
		<tr>
			<td class="td-r">
				<a href="<ww:url value='/profile/searchEmp.action?emp.empStatus=1'/>"><img src='../resource/images/Documents.gif' alt='��ͬ����'></a>
			</td>
			<td class="td-b"><a href="<ww:url value='/profile/manageContract.action'/>">��ͬ����</a></td>
		</tr>
		</ty:auth>
		<!-- ����Ա���Թ�����ְ��Ա��������ֻ�ܲ鿴���޸���ְԱ������ -->
		<ty:auth auths="101" >	
		<tr>
			<td class="td-r">
				<a href="<ww:url value='/profile/empQuitManagement.action'/>"><img src='../resource/images/Employees_quit.gif' alt='��ְԱ������'></a>
			</td>
			<td class="td-b"><a href="<ww:url value='/profile/empQuitManagement.action'/>">��ְԱ������</a></td>
		</tr>
		</ty:auth>
		<!-- ����Ա���Թ���������������Ա����ֻ�ܲ鿴���� -->
		<ty:auth auths="101 or 111,3 or 111,2">
		<tr>
			<td class="td-r">
				<a href="<ww:url value='/profile/profileManagement.action'/>"><img src='../resource/images/search_icon.gif' alt='��������'></a>
			</td>
			<td class="td-b"><a href="<ww:url value='/profile/profileManagement.action'/>">��������</a></td>
		</tr>
		</ty:auth>
		<!-- ����Ϊ����Ա������ӵ�б���ִ��Ȩ�ޣ����п������б��� -->
		<ty:auth auths="101">
		<tr>
			<td class="td-r">
				<a href="<ww:url value='/profile/empReport.action'/>"> <img src='../resource/images/MeetingReports.gif' alt='Ա������'></a>
			</td>
			<td class="td-b"><a href="<ww:url value='/profile/empReport.action'/>">Ա������</A></td>
		</tr>
		</ty:auth>
		<!-- ����Ա������Ա�������Բ鿴Ա��ͨѶ¼ -->
		<ty:auth auths="101 or 111">
		<tr>
			<td class="td-r">
				<a href="empTreeInit.action?id=<ww:property value='#session.userinfo.id'/>"> <img src='../resource/images/users.gif' alt='Ա��ͨѶ¼'></a>
			</td>
			<td class="td-b"><a href="empTreeInit.action?id=<ww:property value='#session.userinfo.id'/>">Ա��ͨѶ¼</A></td>
		</tr>
		</ty:auth>		
		<!-- ����Ա�������Բ鿴���˵�Ա�����ϣ�����������-������д����������-������д�����µ���-ֻ���� -->
		<ty:auth auths="111" >	
		<tr>
			<td class="td-r">
				<a href="<ww:url value='/profile/myInfo.action?tab=1' includeParams='none'/>"><img src='../resource/images/member_face2.gif' alt='�ҵ�����'></a>
			</td>
			<td class="td-b"><a href="myInfo.action?tab=1">�ҵ�����</a></td>
		</tr>
		</ty:auth>
		<!-- ����ΪHR�����ſ��Թ�����֯�ṹ -->
		<ty:auth auths="101,2">
		<tr>
			<td class="td-r">
				<a href="orgDeptManage.action"> <img src='../resource/images/QueryBuilder.gif' alt='��֯�ṹ����'></a>
			</td>
			<td class="td-b"><a href="orgDeptManage.action">��֯�ṹ����</A></td>
		</tr>
		</ty:auth>
		<!-- ����ΪHR�����ſ���������֯�ṹ������ -->
		<ty:auth auths="101,2">
		<tr>
			<td class="td-r">
				<a href="<ww:url value='/profile/orgHeads.action'/>"> <img src='../resource/images/MyProject.gif' alt='��֯�ṹ������'></a>
			</td>
			<td class="td-b"><a href="<ww:url value='/profile/orgHeads.action'/>">��֯�ṹ������</A></td>
		</tr>
		</ty:auth>
</table>