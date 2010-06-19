<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@taglib prefix="ty" uri="/tengsource"%>
<table id="left_menu" cellpadding="0" cellspacing="0"
	style="margin:0px; padding:0px" width="100%" border="0">	
	<ty:auth  auths="611 ">
		<tr>
			<td class="td-r">
				<a href="AddRecruitplanInit.action"><img
						src='../resource/images/MyProject.gif' alt='添加我的招聘计划'>
				</a>
			</td>
			<td class="td-b">
				<a href="SearchRecruitplan.action">我的招聘计划</a>
			</td>
		</tr>	
		<tr>
			<td class="td-r">
				<a href="AddRecruitplanInit.action"><img
						src='../resource/images/CreateProject.gif' alt='添加我的招聘计划'>
				</a>
			</td>
			<td class="td-b">
				<a href="AddRecruitplanInit.action">新增招聘计划</a>
			</td>
		</tr>
	</ty:auth>
	<ty:auth auths="611,2 or 611,3 " >	
		<tr>
			<td class="td-r">
				<a href="ApproverRecruitplanDept.action"><img
						src='../resource/images/DeptApprove.gif' alt='部门经理审批'>
				</a>
			</td>
			<td class="td-b">
				<a href="ApproverRecruitplanDept.action">部门经理审批</a>
			</td>
		</tr>
	</ty:auth>
	
	<ty:auth auths="601,2">
		<tr>
			<td class="td-r">
				<a href="ApproverRecruitplanHR.action"><img
						src='../resource/images/HRApprove.gif' alt='HR备案'>
				</a>
			</td>
			<td class="td-b">
				<a href="ApproverRecruitplanHR.action">HR备案</a>
			</td>
		</tr>
	</ty:auth>
	
	<ty:auth auths="611,2 or 611,3 or 601 "> 
		<tr>
			<td class="td-r">
				<a href="SearchRecruitplanForHR.action"><img src='../resource/images/Reports.gif' alt='招聘计划列表'/></a>
			</td>
			<td class="td-b"><a href="SearchRecruitplanForHR.action">招聘计划列表</a></td>
		</tr>
	 </ty:auth>  
	
	<ty:auth auths="601">
		<tr>
			<td class="td-r">
				<a href="recruitapplierCreateInit.action"><img src='../resource/images/Meetings.gif' alt='新增应聘者'></a>
			</td>
			<td class="td-b"><a href="recruitapplierSearch.action">应聘人才库</a></td>
		</tr>	
	</ty:auth>
	<ty:auth auths="601 or 701">
		<tr>
			<td class="td-r">
				<a href="recruitmentReport.action"> <img src='../resource/images/Preview.gif' alt='报表'></a>
			</td>
			<td class="td-b"><a href="recruitmentReport.action">招聘报表</A></td>
		</tr>	
	</ty:auth>
	<ty:auth auths="601">
		<tr>
			<td class="td-r">
				<a href="#"><img src='../resource/images/Rebuild.gif' alt='添加招聘渠道' onclick="showAddForm();"></a>
			</td>
			<td class="td-b"><a href="recruitchannellist.action">招聘渠道列表</a></td>
		</tr>
	</ty:auth>	
	
</table>