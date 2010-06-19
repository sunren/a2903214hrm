<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ taglib prefix="ty" uri="/tengsource"%>
<%@taglib prefix="ww" uri="webwork"%>
<table id="left_menu" cellpadding="0" cellspacing="0"
	style="margin: 0px; padding: 0px" width="100%" border="0">
	<ty:auth auths="411">
		<tr>
			<td class="td-r">
				<a href="../examin/myExamins.action"> <img
						src='../resource/images/MyProject.gif' alt='我的请假加班'> </a>
			</td>
			<td class="td-b">
				<a href="../examin/myExamins.action"> 我的请假加班 </a>
			</td>
		</tr>
	</ty:auth>
	<ty:auth auths="411 or 401">
		<tr>
			<td class="td-r">
				<a href="../examin/empExaminAdd.action"> <img
						src='../resource/images/CreateProject.gif' alt='请假加班申请'> </a>
			</td>
			<td class="td-b">
				<a href="../examin/empExaminAdd.action"> 请假加班申请 </a>
			</td>
		</tr>
	</ty:auth>
	<ty:auth auths="411,2">
		<tr>
			<td class="td-r">
				<a href="../examin/deptExaminSearch.action"> <img
						src='../resource/images/DeptApprove.gif' alt='经理审批'> </a>
			</td>
			<td class="td-b">
				<a href="../examin/deptExaminSearch.action"> 经理审批 </a>
			</td>
		</tr>
	</ty:auth>

	<ty:auth auths="401">
		<tr>
			<td class="td-r">
				<a href="../examin/hrExaminSearch.action"> <img
						src='../resource/images/HRApprove.gif' alt='HR备案'> </a>
			</td>
			<td class="td-b">
				<a href="../examin/hrExaminSearch.action"> HR备案 </a>
			</td>
		</tr>
	</ty:auth>

	<ty:auth auths="411,2 or 411,3 or 401">
		<tr>
			<td class="td-r">
				<a href="../examin/allExaminSearch.action"> <img
						src='../resource/images/Reports.gif' alt='请假加班列表'> </a>
			</td>
			<td class="td-b">
				<a href="../examin/allExaminSearch.action"> 请假加班列表 </a>
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
							src='../resource/images/Schedulers.gif' alt='排班管理'> </a>
				</td>
				<td class="td-b">
					<a href="../examin/examinScheduleSearch.action"> 排班管理 </a>
				</td>
			</tr>
		</ty:auth>
		<ty:auth auths="421,3">
			<tr>
				<td class="td-r">
					<a href="../examin/originalDataImportShow.action"> <img
							src='../resource/images/Schedulers.gif' alt='刷卡管理'> </a>
				</td>
				<td class="td-b">
					<a href="../examin/originalDataImportShow.action"> 刷卡管理 </a>
				</td>
			</tr>
		</ty:auth>
		<ty:auth auths="421,3 or 421,2 or 421,1">
			<tr>
				<td class="td-r">
					<a href="../examin/searchAttenddaily.action"> <img
							src='../resource/images/Schedulers.gif' alt='每日考勤记录'> </a>
				</td>
				<td class="td-b">
					<a href="../examin/searchAttenddaily.action"> 每日考勤记录 </a>
				</td>
			</tr>
		</ty:auth>
	</ww:if>
	<ty:auth auths="401 or 421,3 or 421,2">
		<tr>
			<td class="td-r">
				<a href="../examin/searchAttendmonthly.action"> <img
						src='../resource/images/Calendar.gif' alt='每月考勤汇总'> </a>
			</td>
			<td class="td-b">
				<a href="../examin/searchAttendmonthly.action"> 每月考勤汇总 </a>
			</td>
		</tr>
	</ty:auth>
	<ty:auth auths="401">
		<tr>
			<td class="td-r">
				<a href="../examin/examinReport.action"> <img
						src='../resource/images/AttdReports.gif' alt='考勤报表'> </a>
			</td>
			<td class="td-b">
				<a href="../examin/examinReport.action"> 考勤报表 </a>
			</td>
		</tr>
	</ty:auth>
	<ty:auth auths="401 ">
		<tr>
			<td class="td-r">
				<a href="../examin/leavebalanceManage.action"> <img
						src='../resource/images/Newsletters.gif' alt='考勤设置'> </a>
			</td>
			<td class="td-b">
				<a href="../examin/leavebalanceManage.action"> 考勤设置 </a>
			</td>
		</tr>
	</ty:auth>
</table>