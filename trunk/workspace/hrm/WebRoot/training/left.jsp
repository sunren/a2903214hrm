<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ taglib prefix="ty" uri="/tengsource"%>
<table id="left_menu" cellpadding="0" cellspacing="0" style="margin:0px; padding:0px" width="100%" border="0">
		<ty:auth auths="311 ">
		<tr>
			<td class="td-r" valign="top">			
				<a href="myTrainingList.action"><img src='../resource/images/MyProject.gif' alt='我的培训计划'></a>
			</td>
			<td class="td-b"><a href="myTrainingList.action">我的培训计划</a><br></td>
		</tr>
		</ty:auth>
		<ty:auth auths="311 ">		
		<tr>
			<td class="td-r">
				<a href="trepAddInit.action"><img src='../resource/images/CreateProject.gif' alt='培训申请'></a>
			</td>
			<td class="td-b"><a href="trepAddInit.action">培训申请</a></td>
		</tr>
		</ty:auth>
		<ty:auth auths="311,2 or 311,3">
		<tr>
			<td class="td-r">
				<a href="trepDeptApprove.action"><img src='../resource/images/DeptApprove.gif' alt='部门经理审批'></a>
			</td>
			<td class="td-b"><a href="trepDeptApprove.action">部门经理审批</a></td>
		</tr>
		</ty:auth>
		<ty:auth auths="301,2" >
		<tr>
			<td class="td-r">
			<a href="trepHrApprove.action"><img src='../resource/images/HRApprove.gif' alt='HR备案'></a>
			</td>
			<td class="td-b"><a href="trepHrApprove.action">HR备案</a></td>
		</tr>
		</ty:auth>
		
		<ty:auth auths="311,2 or 311,3 or 301,2">
		<tr>
			<td class="td-r">
				<a href="trepList.action"><img src='../resource/images/Reports.gif' alt='员工培训计划'></a>
			</td>
			<td class="td-b"><a href="trepList.action">员工培训计划</a></td>
		</tr>
		</ty:auth>
		<ty:auth auths="301 or 701" >
		<tr>
			<td class="td-r">
				<a href=""trReport.action""><img src='../resource/images/CallReports.gif' alt='培训报表'></a>
			</td>
			<td class="td-b"><a href="trReport.action">培训报表</a></td>
		</tr>			
		</ty:auth>
		<ty:auth auths="301 ">
		<tr>
			<td class="td-r">
				<a href="trcourseConfig.action"><img src='../resource/images/Project2Weeks.gif' alt='课程设置'></a>
			</td>
			<td class="td-b"><a href="trcourseConfig.action">课程设置</a></td>
		</tr>
		<tr>
			<td class="td-r">
				<a href="trtConfig.action"><img src='../resource/images/Administration.gif' alt='培训种类'></a>
			</td>
			<td class="td-b"><a href="trtConfig.action">培训种类</a></td>
		</tr>
		</ty:auth>	
</table>
