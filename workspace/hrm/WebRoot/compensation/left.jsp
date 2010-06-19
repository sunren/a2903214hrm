<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ taglib prefix="ww" uri="webwork"%>
<%@taglib prefix="ty" uri="/tengsource"%>
<table id="left_menu" cellpadding="0" cellspacing="0" style="margin:0px; padding:0px" width="100%" border="0">
		<ty:auth auths="201">
		<tr>
			<td class="td-r">
				<a href="searchSalary.action"><img src='../resource/images/Prospects.gif' alt='员工薪资配置'></a>
			</td>
			<td class="td-b"><a href="searchSalary.action">员工薪资配置</a></td>
		</tr>
		</ty:auth> 

		<ty:auth auths="201">
		<tr>
			<td class="td-r">
				<a href="searchSalaryPaid.action"><img src='../resource/images/ProjectPaste.gif' alt='每月薪资发放'></a>
			</td>
			<td class="td-b"><a href="searchSalaryPaid.action">每月薪资发放</a></td>
		</tr>
		</ty:auth>

		<ty:auth auths="201" >
		<tr>
			<td class="td-r b">
				<a href="searchBatchCompaplan.action"><img src='../resource/images/SalaryPaidApprove.gif' alt='员工调薪计划'></a>
			</td>
			<td class="td-b"><a href="searchBatchCompaplan.action">员工调薪计划</a></td>
		</tr>
		</ty:auth>

		<ty:auth auths="201">
		<tr>
			<td class="td-r">
				<a href="searchCompaplan.action"><img src='../resource/images/Reports.gif' alt='调薪历史查询'></a>
			</td>
			<td class="td-b"><a href="searchCompaplan.action">调薪历史查询</a></td>
		</tr>
		</ty:auth>

		<ty:auth auths="221">
		<tr>
			<td class="td-r">
				<a href="searchEmpbenefit.action?employee.empStatus=1"><img src='../resource/images/Documents.gif' alt='员工社保管理'></a>
			</td>
			<td class="td-b"><a href="searchEmpbenefit.action?employee.empStatus=1">员工社保管理</a></td>
		</tr>
		</ty:auth>

		<ty:auth auths="201 or 211">
		<tr>
			<td class="td-r">
				<a href="mySalaryConf.action"><img src='../resource/images/Prospects.gif' alt='我的薪资配置'></a>
			</td>
			<td class="td-b"><a href="mySalaryConf.action">我的薪资配置</a></td>
		</tr>
		</ty:auth>

		<ty:auth auths="201 or 211">
		<tr>
			<td class="td-r">
				<a href="mySalaryPaid.action"><img src='../resource/images/ProjectPaste.gif' alt='我的薪资发放'></a>
			</td>
			<td class="td-b"><a href="mySalaryPaid.action">我的薪资发放</a></td>
		</tr>
		</ty:auth>

		<ty:auth auths="201,2">
		<tr>
			<td class="td-r b">
				<a href="slaryRpInit.action"><img src='../resource/images/CompaReport.gif' alt='薪资综合报表'></a>
			</td>
			<td class="td-b"><a href="salaryRpInit.action">薪资综合报表</a></td>
		</tr>
		</ty:auth>

		<ty:auth auths="201">	
		<tr>
			<td class="td-r b">
				<a href="compensationConfig.action"><img src='../resource/images/Releases.gif' alt='薪资设置'></a>
			</td>
			<td class="td-b"><a href="compensationConfig.action">薪资设置</a></td>
		</tr>
		<tr>
            <td class="td-r b">
                <a href="searchesa.action"><img src='../resource/images/Releases.gif' alt='薪资帐套配置'></a>
            </td>
            <td class="td-b"><a href="searchesa.action">薪资帐套配置</a></td>
        </tr>
		</ty:auth>
		
</table>