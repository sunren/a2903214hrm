<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ taglib prefix="ww" uri="webwork"%>
<%@taglib prefix="ty" uri="/tengsource"%>
<table id="left_menu" cellpadding="0" cellspacing="0" style="margin:0px; padding:0px" width="100%" border="0">
		<!-- 101 HR员工管理员 HR经理（拥有调整“组织结构”/“经理”的权限）；111 总经理、部门经理、普通员工  -->
		<!-- 管理员、总经理、部门经理可以查看在职员工，总经理和部门经理在特定条件下，允许修改员工信息 -->
		<ty:auth auths="101 or 111,2 or 111,3" >	
		<tr>
			<td class="td-r">
				<a href="<ww:url value='/profile/searchEmp.action'/>"><img src='../resource/images/Teams.gif' alt='员工管理'></a>
			</td>
			<td class="td-b"><a href="<ww:url value='/profile/searchEmp.action'/>">员工管理</a></td>
		</tr>
		</ty:auth>
		<!-- 管理员可以对新员工做入职动作，入职时，立刻创建基础资料、附加资料、人事档案 -->
		<ty:auth auths="101">
		<tr>
			<td class="td-r">
				<a href="empCreateInit.action"><img src='../resource/images/CreateEmployees.gif' alt='新员工入职'></a>
			</td>
			<td class="td-b"><a href="empCreateInit.action">新员工入职</a></td>
		</tr>
		</ty:auth>
		<!-- 管理员可以管理员工合同，其他人可以去档案搜索里面，查看合同等资料 -->
		<ty:auth auths="101" >	
		<tr>
			<td class="td-r">
				<a href="<ww:url value='/profile/searchEmp.action?emp.empStatus=1'/>"><img src='../resource/images/Documents.gif' alt='合同管理'></a>
			</td>
			<td class="td-b"><a href="<ww:url value='/profile/manageContract.action'/>">合同管理</a></td>
		</tr>
		</ty:auth>
		<!-- 管理员可以管理离职人员，其他人只能查看或修改在职员工资料 -->
		<ty:auth auths="101" >	
		<tr>
			<td class="td-r">
				<a href="<ww:url value='/profile/empQuitManagement.action'/>"><img src='../resource/images/Employees_quit.gif' alt='离职员工管理'></a>
			</td>
			<td class="td-b"><a href="<ww:url value='/profile/empQuitManagement.action'/>">离职员工管理</a></td>
		</tr>
		</ty:auth>
		<!-- 管理员可以管理档案，其他所有员工均只能查看档案 -->
		<ty:auth auths="101 or 111,3 or 111,2">
		<tr>
			<td class="td-r">
				<a href="<ww:url value='/profile/profileManagement.action'/>"><img src='../resource/images/search_icon.gif' alt='档案管理'></a>
			</td>
			<td class="td-b"><a href="<ww:url value='/profile/profileManagement.action'/>">档案管理</a></td>
		</tr>
		</ty:auth>
		<!-- 必须为管理员，或者拥有报表执行权限，才有可能运行报表 -->
		<ty:auth auths="101">
		<tr>
			<td class="td-r">
				<a href="<ww:url value='/profile/empReport.action'/>"> <img src='../resource/images/MeetingReports.gif' alt='员工报表'></a>
			</td>
			<td class="td-b"><a href="<ww:url value='/profile/empReport.action'/>">员工报表</A></td>
		</tr>
		</ty:auth>
		<!-- 管理员和所有员工均可以查看员工通讯录 -->
		<ty:auth auths="101 or 111">
		<tr>
			<td class="td-r">
				<a href="empTreeInit.action?id=<ww:property value='#session.userinfo.id'/>"> <img src='../resource/images/users.gif' alt='员工通讯录'></a>
			</td>
			<td class="td-b"><a href="empTreeInit.action?id=<ww:property value='#session.userinfo.id'/>">员工通讯录</A></td>
		</tr>
		</ty:auth>		
		<!-- 所有员工均可以查看本人的员工资料（含基本资料-有条件写、附加资料-有条件写、人事档案-只读） -->
		<ty:auth auths="111" >	
		<tr>
			<td class="td-r">
				<a href="<ww:url value='/profile/myInfo.action?tab=1' includeParams='none'/>"><img src='../resource/images/member_face2.gif' alt='我的资料'></a>
			</td>
			<td class="td-b"><a href="myInfo.action?tab=1">我的资料</a></td>
		</tr>
		</ty:auth>
		<!-- 必须为HR经理，才可以管理组织结构 -->
		<ty:auth auths="101,2">
		<tr>
			<td class="td-r">
				<a href="orgDeptManage.action"> <img src='../resource/images/QueryBuilder.gif' alt='组织结构管理'></a>
			</td>
			<td class="td-b"><a href="orgDeptManage.action">组织结构管理</A></td>
		</tr>
		</ty:auth>
		<!-- 必须为HR经理，才可以配置组织结构负责人 -->
		<ty:auth auths="101,2">
		<tr>
			<td class="td-r">
				<a href="<ww:url value='/profile/orgHeads.action'/>"> <img src='../resource/images/MyProject.gif' alt='组织结构负责人'></a>
			</td>
			<td class="td-b"><a href="<ww:url value='/profile/orgHeads.action'/>">组织结构负责人</A></td>
		</tr>
		</ty:auth>
</table>