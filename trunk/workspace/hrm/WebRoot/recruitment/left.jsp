<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@taglib prefix="ty" uri="/tengsource"%>
<table id="left_menu" cellpadding="0" cellspacing="0"
	style="margin:0px; padding:0px" width="100%" border="0">	
	<ty:auth  auths="611 ">
		<tr>
			<td class="td-r">
				<a href="AddRecruitplanInit.action"><img
						src='../resource/images/MyProject.gif' alt='����ҵ���Ƹ�ƻ�'>
				</a>
			</td>
			<td class="td-b">
				<a href="SearchRecruitplan.action">�ҵ���Ƹ�ƻ�</a>
			</td>
		</tr>	
		<tr>
			<td class="td-r">
				<a href="AddRecruitplanInit.action"><img
						src='../resource/images/CreateProject.gif' alt='����ҵ���Ƹ�ƻ�'>
				</a>
			</td>
			<td class="td-b">
				<a href="AddRecruitplanInit.action">������Ƹ�ƻ�</a>
			</td>
		</tr>
	</ty:auth>
	<ty:auth auths="611,2 or 611,3 " >	
		<tr>
			<td class="td-r">
				<a href="ApproverRecruitplanDept.action"><img
						src='../resource/images/DeptApprove.gif' alt='���ž�������'>
				</a>
			</td>
			<td class="td-b">
				<a href="ApproverRecruitplanDept.action">���ž�������</a>
			</td>
		</tr>
	</ty:auth>
	
	<ty:auth auths="601,2">
		<tr>
			<td class="td-r">
				<a href="ApproverRecruitplanHR.action"><img
						src='../resource/images/HRApprove.gif' alt='HR����'>
				</a>
			</td>
			<td class="td-b">
				<a href="ApproverRecruitplanHR.action">HR����</a>
			</td>
		</tr>
	</ty:auth>
	
	<ty:auth auths="611,2 or 611,3 or 601 "> 
		<tr>
			<td class="td-r">
				<a href="SearchRecruitplanForHR.action"><img src='../resource/images/Reports.gif' alt='��Ƹ�ƻ��б�'/></a>
			</td>
			<td class="td-b"><a href="SearchRecruitplanForHR.action">��Ƹ�ƻ��б�</a></td>
		</tr>
	 </ty:auth>  
	
	<ty:auth auths="601">
		<tr>
			<td class="td-r">
				<a href="recruitapplierCreateInit.action"><img src='../resource/images/Meetings.gif' alt='����ӦƸ��'></a>
			</td>
			<td class="td-b"><a href="recruitapplierSearch.action">ӦƸ�˲ſ�</a></td>
		</tr>	
	</ty:auth>
	<ty:auth auths="601 or 701">
		<tr>
			<td class="td-r">
				<a href="recruitmentReport.action"> <img src='../resource/images/Preview.gif' alt='����'></a>
			</td>
			<td class="td-b"><a href="recruitmentReport.action">��Ƹ����</A></td>
		</tr>	
	</ty:auth>
	<ty:auth auths="601">
		<tr>
			<td class="td-r">
				<a href="#"><img src='../resource/images/Rebuild.gif' alt='�����Ƹ����' onclick="showAddForm();"></a>
			</td>
			<td class="td-b"><a href="recruitchannellist.action">��Ƹ�����б�</a></td>
		</tr>
	</ty:auth>	
	
</table>