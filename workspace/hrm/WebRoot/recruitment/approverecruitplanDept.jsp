<%@page pageEncoding="GBK" contentType="text/html; charset=GBK" %>
<%@taglib prefix="ww" uri="webwork"%>
<%@taglib prefix="ty" uri="/tengsource"%>
<%@taglib prefix="log" uri="http://jakarta.apache.org/taglibs/log-1.0"%>
<%@ taglib uri="/WEB-INF/config/jenkov/ajaxtags.tld" prefix="ajax" %>
<%@ include file="/configuration/log.jsp" %>
<%@ include file="planDetail_div.jsp" %>
<%@ include file="approve_div.jsp" %>
<head>
<title>招聘计划部门审批</title>
<script language="JavaScript" type="text/JavaScript" src="../resource/js/search.js"></script>
<script type="text/javascript" src="../resource/js/validor.js"></script>
<script language="JavaScript" type="text/JavaScript" src="../resource/js/recruitment/applier.js"></script>
<script language="JavaScript" type="text/JavaScript" src="../resource/js/recruitment/recruitment.js"></script>
<script language="JavaScript" type="text/JavaScript" src="../resource/js/recruitment/searchrecruitment.js"></script>
<script type="text/javascript" src="../resource/js/tmp.js"></script>

</head>
<body>
<ww:component template="bodyhead">
	<ww:param name="pagetitle" value="'招聘计划部门审批'" />
	<ww:param name="helpUrl" value="'42'" />
</ww:component>
<ww:form id="ApproverRecruitplanDept" name="ApproverRecruitplanDept" action="ApproverRecruitplanDept" namespace="/recruitment" method="post" >
 <ww:token />
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="formtable">
<tr>
	<td>
	    <ww:hidden  id="idfordetail" name="detailid" />
		<ww:hidden id="order" name="page.order"/>
		<ww:hidden id="operate" name="page.operate"/>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<ww:select label="职位名称" list="jobTitles" name="plan.recpJobTitle" listKey="jobtitleNo" listValue="jobtitleName" emptyOption="true"></ww:select>
				<ww:select label="职位所属部门" name="plan.recpDepartmentNo.id" list="allDept" listKey="id" listValue="departmentName" multiple="false" headerKey="" headerValue="请选择" />
				<ww:select label="工作地区" name="plan.recpWorkLocation.id" list="allLocation" listKey="id" listValue="locationName" multiple="false" headerKey="" headerValue="请选择" />
			</tr>
		</table>
	</td>

	<td>
		<input title="[Alt+F]" accesskey="F" id="submit_button" class="button" type="submit"  value="查询"> 		
		<input title="[Alt+C]" accesskey="C" class="button" type="button" value="重置" onClick="window.location='ApproverRecruitplanDept.action';">
		<br />					
	</td>
</tr>
</table>
<p>&nbsp;</p> 
<table id="recruitplantable" cellpadding="0" cellspacing="0" width="100%" border="0" class="gridtableList">	
	<ww:if test="page.isSplit()">
	     <tr>
	     	<td colspan="11" align="right"  class="listViewPaginationTdS1">
	     			<ww:hidden name="page.currentPage"/>
                    <a href="#" onclick="splits_recruitment('first','ApproverRecruitplanDept');"><img src='../resource/images/start.gif' width='11' height='9' alt='开始'>开始</a>
                    <a href="#" onclick="splits_recruitment('previous','ApproverRecruitplanDept');"><img src='../resource/images/previous.gif' width='6' height='9' alt='上页'>上页</a>
                       （<ww:property value="page.currentPage+'/'+page.totalPages"/>页｜共<ww:property value="page.totalRows"/>条）
                    <a href="#" onclick="splits_recruitment('next','ApproverRecruitplanDept');">下页<img src='../resource/images/next.gif' width='6' height='9'></a>
                    <a href="#" onclick="splits_recruitment('last','ApproverRecruitplanDept');">最后<img src='../resource/images/end.gif' width='11' height='9' alt='最后'></a>
			</td>
	     </tr>
	</ww:if>     
	      <tr>
	        <th><input id='changIds_choose' name='changIds_choose' class="checkbox" type="checkbox" onclick="checkAll('changIds');" value="0"></th>
	     	<th><a href="#" onclick="order_submit_recruitment('recpNo','ApproverRecruitplanDept');">编号</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='recpNo_img'></th>
	     	<th><a href="#" onclick="order_submit_recruitment('recpDepartmentNo.id','ApproverRecruitplanDept');">所属部门</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='recpDepartmentNo.id_img'></th>
	     	<th><a href="#" onclick="order_submit_recruitment('recpWorkLocation.id','ApproverRecruitplanDept');">工作地区</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='recpWorkLocation.id_img'></th>
	     	<th><a href="#" onclick="order_submit_recruitment('recpJobTitle','ApproverRecruitplanDept');">职位名称</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='recpJobTitle_img'></th>
	     	<th><a href="#" onclick="order_submit_recruitment('recpNumberExpect','ApproverRecruitplanDept');">人数</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='recpNumberExpect_img'></th>
	     	<th><a href="#" onclick="order_submit_recruitment('recpStartDate','ApproverRecruitplanDept');">职位发布日期</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='recpStartDate_img'></th>
	     	<th><a href="#" onclick="order_submit_recruitment('recpEndDate','ApproverRecruitplanDept');">职位关闭日期</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='recpEndDate_img'></th>
	     	<th><a href="#" onclick="order_submit_recruitment('recpLastChangeTime','ApproverRecruitplanDept');">最后修改时间</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='recpLastChangeTime_img'></th>
	     	<th><a href="#" onclick="order_submit_recruitment('recpStatus','ApproverRecruitplanDept');">状态</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='recpStatus_img'></th>
	     	<th>操作</th>
	     </tr>
	   
	     <ww:if test="!recruitplanList.isEmpty()">
	     	<ww:iterator value="recruitplanList" status="index">
					  <tr onMouseOver="this.bgColor='#DDF0F8'" onMouseOut="this.bgColor='#ffffff'" id="approveDeptTr<ww:property value='id'/>" >
	     		    <td align="center"><input type="checkbox" name='changIds' class="checkbox" value="<ww:property value='id'/>" /></td>
					<td><span class="view" onclick="showplanDetailInSP(
													'<ww:property value="recpJobTitle.jobtitleNo"/>',
													'<ww:property value="recpNumberExpect" />',
													'<ww:property value="recpDepartmentNo.departmentName" />',
													'<ww:property value="recpNumberActual" />',
													'<ww:property value="recpWorkLocation.locationName" />',
													'<ww:property value="recpType.emptypeName" />',
													'<ww:property value="recpDegree" />',
													'<ww:date name="recpStartDate" format="yyyy-MM-dd" />',
													'<ww:property value="recpLanguageSkill" />',
													'<ww:date name="recpEndDate" format="yyyy-MM-dd" />',
													'<ww:property value="recpJobSkill" />',
													'<ww:property value="getRecpStatus(recpStatus)"/>',
													'<ww:date name="recpCreateTime" format="yyyy-MM-dd"/>',
													'<ww:date name="recpLastChangeTime" format="yyyy-MM-dd"/>',
													'<ww:property value="recpJobDesc" escape="false"/>',
													'<ww:property value="recpComments" />','planDetail');">
													<ww:property value="recpNo" /></span></td>	
					<td><ww:property value="recpDepartmentNo.departmentName" /></td>
					<td><ww:property value="recpWorkLocation.locationName" /></td>
					<td><ww:property value="recpJobTitle.jobtitleName" /></td>
					<td><ww:property value="recpNumberExpect" /></td>  
					<td><ww:date name="recpStartDate" format="yyyy-MM-dd"/></td>  
					<td><ww:date name="recpEndDate" format="yyyy-MM-dd"/></td> 					 
					<td><ww:date name="recpLastChangeTime" format="yyyy-MM-dd"/></td> 									
					<td  onMouseOut="hideLog();"><span class="view" onclick="LogShowDiv('<ww:property value="id" />','recruitplan',this);">已提交</span></td>
				    <td align="center">
					 <span class="view"><img src="../resource/images/MgrApprove.gif" alt='同意' onclick="document.getElementById('FlagDeptHR').value='Dept';document.getElementById('approve_recordId').value='<ww:property value='id'/>';showApproveComment('agree','<ww:property value='recpNo'/>');" border='0'/></span>
					 <span class="view"><img src="../resource/images/MgrReject.gif" alt='拒绝' onclick="document.getElementById('FlagDeptHR').value='Dept';document.getElementById('approve_recordId').value='<ww:property value='id'/>';showApproveComment('reject','<ww:property value='recpNo'/>');" border='0'/></span>
					</td> 

				
	     		</tr>
	     	</ww:iterator>
	     </ww:if>
	     <ww:else>
				<tr>					
					<td align="center" colspan="11">
						不存在符合条件的招聘计划!
					</td>
				</tr>
			</ww:else>
</table>
<ww:if test="!recruitplanList.isEmpty()">
	<table height="40" align="left">
		<tr>
			<td>
			<input class="button" type="button"  onclick="document.getElementById('FlagDeptHR').value='Dept';batchApprovedPlan();" value="批量审批">
			</td>
		</tr>
	</table>				
</ww:if>
<div id="tmpletId" style="DISPLAY: none"><img src="../resource/images/basic_search.gif" onload="check_order();"/></div>
</ww:form>	
</body>	