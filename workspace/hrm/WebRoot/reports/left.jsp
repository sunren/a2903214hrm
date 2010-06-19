<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ taglib prefix="s" uri="webwork"%>
<%@taglib prefix="ty" uri="/tengsource"%>
<table id="left_menu" cellpadding="0" cellspacing="0" style="margin:0px; padding:0px" width="100%" border="0">
		<ty:auth auths="101">
		<tr>
			<td class="td-r">
				<a href="empReport.action"><img src='../resource/images/MyReports.gif' alt='员工报表'></a>
			</td>
			<td class="td-b"><a href="empReport.action">员工报表</a></td>
		</tr>
		</ty:auth>
		<ty:auth auths="201,2">
		<tr>
			<td class="td-r">
				<a href="salaryRpInit.action"><img src='../resource/images/CompaReport.gif' alt='薪资报表'></a>
			</td>
			<td class="td-b"><a href="salaryRpInit.action">薪资报表</a></td>
		</tr>
		</ty:auth>
		<ty:auth auths="401">
		<tr>
			<td class="td-r">
				<a href="#"><img src='../resource/images/AttdReports.gif' alt='考勤报表'></a>
			</td>
			<td class="td-b"><a href="#">考勤报表</a></td>
		</tr>
		</ty:auth>
		<ty:auth auths="301">
		<tr>
			<td class="td-r">
				<a href="#"><img src='../resource/images/CallReports.gif' alt='培训报表'></a>
			</td>
			<td class="td-b"><a href="#">培训报表</a></td>
		</tr>
		</ty:auth>
		<ty:auth auths="601">
		<tr>
			<td class="td-r">
				<a href="#"><img src='../resource/images/Preview.gif' alt='招聘报表'></a>
			</td>
			<td class="td-b"><a href="#">招聘报表</a></td>
		</tr>
		</ty:auth>
		<ty:auth auths="701,1 or 701,2 or 701,3">
			<tr>
				<td class="td-r">			
					<a href="listAll.action?report.reportType=0"><img alt="自定义报表" src="../resource/images/ReportMaker.gif"/></a>	
				</td>
				<td class="td-b"><a href="listAll.action?report.reportType=0">自定义报表</a></td>
			</tr>
		</ty:auth>
		<ty:auth auths="701">
		<tr>
			<td class="td-r">
				<a href="#"><img alt="新建预定义报表" src="../resource/images/BirtReports.gif"/></a>
			</td>
			<td class="td-b"><a href="#">预定义报表</a></td>
		</tr>
		</ty:auth>
</table>


