<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ taglib prefix="ty" uri="/tengsource"%>
<%@taglib prefix="ww" uri="webwork"%>
<table id="left_menu" cellpadding="0" cellspacing="0"
	style="margin: 0px; padding: 0px" width="100%" border="0">
	<ty:auth auths="411">
		<tr>
			<td class="td-r">
				<a href="../examin/myExamins.action"> <img
						src='../resource/images/MyProject.gif' alt='�ҵ���ټӰ�'> </a>
			</td>
			<td class="td-b">
				<a href="../examin/myExamins.action"> �ҵ���ټӰ� </a>
			</td>
		</tr>
	</ty:auth>
	<ty:auth auths="411 or 401">
		<tr>
			<td class="td-r">
				<a href="../examin/empExaminAdd.action"> <img
						src='../resource/images/CreateProject.gif' alt='��ټӰ�����'> </a>
			</td>
			<td class="td-b">
				<a href="../examin/empExaminAdd.action"> ��ټӰ����� </a>
			</td>
		</tr>
	</ty:auth>
	<ty:auth auths="411,2">
		<tr>
			<td class="td-r">
				<a href="../examin/deptExaminSearch.action"> <img
						src='../resource/images/DeptApprove.gif' alt='��������'> </a>
			</td>
			<td class="td-b">
				<a href="../examin/deptExaminSearch.action"> �������� </a>
			</td>
		</tr>
	</ty:auth>

	<ty:auth auths="401">
		<tr>
			<td class="td-r">
				<a href="../examin/hrExaminSearch.action"> <img
						src='../resource/images/HRApprove.gif' alt='HR����'> </a>
			</td>
			<td class="td-b">
				<a href="../examin/hrExaminSearch.action"> HR���� </a>
			</td>
		</tr>
	</ty:auth>

	<ty:auth auths="411,2 or 411,3 or 401">
		<tr>
			<td class="td-r">
				<a href="../examin/allExaminSearch.action"> <img
						src='../resource/images/Reports.gif' alt='��ټӰ��б�'> </a>
			</td>
			<td class="td-b">
				<a href="../examin/allExaminSearch.action"> ��ټӰ��б� </a>
			</td>
		</tr>
	</ty:auth>
	<ww:set name="auth"
		value="@com.hr.util.DatabaseSysConfigManager@getInstance()" />
	<ww:if test="#request.auth.getProperty('sys.examin.shift.enable')==1">
		<ty:auth auths="421,3 or 421,2">
			<tr>
				<td class="td-r">
					<a href="../examin/examinScheduleSearch.action"> <img
							src='../resource/images/Schedulers.gif' alt='�Ű����'> </a>
				</td>
				<td class="td-b">
					<a href="../examin/examinScheduleSearch.action"> �Ű���� </a>
				</td>
			</tr>
		</ty:auth>
		<ty:auth auths="421,3">
			<tr>
				<td class="td-r">
					<a href="../examin/originalDataImportShow.action"> <img
							src='../resource/images/Schedulers.gif' alt='ˢ������'> </a>
				</td>
				<td class="td-b">
					<a href="../examin/originalDataImportShow.action"> ˢ������ </a>
				</td>
			</tr>
		</ty:auth>
		<ty:auth auths="421,3 or 421,2 or 421,1">
			<tr>
				<td class="td-r">
					<a href="../examin/searchAttenddaily.action"> <img
							src='../resource/images/Schedulers.gif' alt='ÿ�տ��ڼ�¼'> </a>
				</td>
				<td class="td-b">
					<a href="../examin/searchAttenddaily.action"> ÿ�տ��ڼ�¼ </a>
				</td>
			</tr>
		</ty:auth>
	</ww:if>
	<ty:auth auths="401 or 421,3 or 421,2">
		<tr>
			<td class="td-r">
				<a href="../examin/searchAttendmonthly.action"> <img
						src='../resource/images/Calendar.gif' alt='ÿ�¿��ڻ���'> </a>
			</td>
			<td class="td-b">
				<a href="../examin/searchAttendmonthly.action"> ÿ�¿��ڻ��� </a>
			</td>
		</tr>
	</ty:auth>
	<ty:auth auths="401">
		<tr>
			<td class="td-r">
				<a href="../examin/examinReport.action"> <img
						src='../resource/images/AttdReports.gif' alt='���ڱ���'> </a>
			</td>
			<td class="td-b">
				<a href="../examin/examinReport.action"> ���ڱ��� </a>
			</td>
		</tr>
	</ty:auth>
	<ty:auth auths="401 ">
		<tr>
			<td class="td-r">
				<a href="../examin/leavebalanceManage.action"> <img
						src='../resource/images/Newsletters.gif' alt='��������'> </a>
			</td>
			<td class="td-b">
				<a href="../examin/leavebalanceManage.action"> �������� </a>
			</td>
		</tr>
	</ty:auth>
</table>