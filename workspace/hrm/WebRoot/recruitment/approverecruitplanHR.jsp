<%@page pageEncoding="GBK" contentType="text/html; charset=GBK" %>
<%@taglib prefix="ww" uri="webwork"%>
<%@taglib prefix="ty" uri="/tengsource"%>
<%@taglib prefix="log" uri="http://jakarta.apache.org/taglibs/log-1.0"%>
<%@ taglib uri="/WEB-INF/config/jenkov/ajaxtags.tld" prefix="ajax" %>
<%@ include file="/configuration/log.jsp" %>
<%@ include file="planDetail_div.jsp" %>
<%@ include file="approve_div.jsp" %>
<head>
<title>招聘计划审批</title>
<script language="JavaScript" type="text/JavaScript" src="../resource/js/search.js"></script>
<script type="text/javascript" src="../resource/js/validor.js"></script>
<script language="JavaScript" type="text/JavaScript" src="../resource/js/recruitment/applier.js"></script>
<script language="JavaScript" type="text/JavaScript" src="../resource/js/recruitment/recruitment.js"></script>
<script language="JavaScript" type="text/JavaScript" src="../resource/js/recruitment/searchrecruitment.js"></script>
<script type="text/javascript" src="../resource/js/tmp.js"></script>
</head>
<body>
<ww:component template="bodyhead">
	<ww:param name="pagetitle" value="'招聘计划HR备案'" />
	<ww:param name="helpUrl" value="'43'" />
</ww:component>
<ww:form id="ApproverRecruitplanHR" name="ApproverRecruitplanHR" action="ApproverRecruitplanHR" namespace="/recruitment" method="post" >
 <ww:token />
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="formtable">
<tr>
	<td>
	    <ww:hidden  id="idfordetail" name="detailid" />
		<ww:hidden id="order" name="page.order"/>
		<ww:hidden id="operate" name="page.operate"/>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<ww:select label="职位名称" list="jobTitles" listKey="jobtitleNo" listValue="jobtitleName" name="plan.recpJobTitle.jobtitleNo" emptyOption="true"></ww:select>
				<ww:select label="职位所属部门" name="plan.recpDepartmentNo.id" list="allDept" listKey="id" listValue="departmentName" multiple="false"  headerKey="" headerValue="请选择" />
				<ww:select label="工作地区" name="plan.recpWorkLocation.id" list="allLocation" listKey="id" listValue="locationName" multiple="false" headerKey="" headerValue="请选择" />
			</tr>
		</table>
	</td>
	<td>
		<input title="[Alt+F]" accesskey="F" id="submit_button" class="button" type="submit"  value="查询"> 		
		<input title="[Alt+C]" accesskey="C" class="button" type="button" value="重置" onClick="window.location='ApproverRecruitplanHR.action';">
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
                    <a href="#" onclick="splits_recruitment('first','ApproverRecruitplanHR');"><img src='../resource/images/start.gif' width='11' height='9' alt='开始'>开始</a>
                    <a href="#" onclick="splits_recruitment('previous','ApproverRecruitplanHR');"><img src='../resource/images/previous.gif' width='6' height='9' alt='上页'>上页</a>
                       （<ww:property value="page.currentPage+'/'+page.totalPages"/>页｜共<ww:property value="page.totalRows"/>条）
                    <a href="#" onclick="splits_recruitment('next','ApproverRecruitplanHR');">下页<img src='../resource/images/next.gif' width='6' height='9'></a>
                    <a href="#" onclick="splits_recruitment('last','ApproverRecruitplanHR');">最后<img src='../resource/images/end.gif' width='11' height='9' alt='最后'></a>
			</td>
	     </tr>
	</ww:if>     
	     <tr>
	      	<th><input id='changIds_choose' name='changIds_choose' class="checkbox" type="checkbox" onclick="checkAll('changIds');" value="0"></th>
	     	<th><a href="#" onclick="order_submit_recruitment('id','ApproverRecruitplanHR');">编号</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='id_img'></th>
	     	<th><a href="#" onclick="order_submit_recruitment('recpDepartmentNo.id','ApproverRecruitplanHR');">所属部门</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='recpDepartmentNo.id_img'></th>
	     	<th><a href="#" onclick="order_submit_recruitment('recpWorkLocation.id','ApproverRecruitplanHR');">工作地区</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='recpWorkLocation.id_img'></th>
	     	<th><a href="#" onclick="order_submit_recruitment('recpJobTitle','ApproverRecruitplanHR');">职位名称</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='recpJobTitle_img'></th>
	     	<th><a href="#" onclick="order_submit_recruitment('recpNumberExpect','ApproverRecruitplanHR');">人数</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='recpNumberExpect_img'></th>
	     	<th><a href="#" onclick="order_submit_recruitment('recpStartDate','ApproverRecruitplanHR');">职位发布日期</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='recpStartDate_img'></th>
	     	<th><a href="#" onclick="order_submit_recruitment('recpEndDate','ApproverRecruitplanHR');">职位关闭日期</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='recpEndDate_img'></th>
	     	<th><a href="#" onclick="order_submit_recruitment('recpLastChangeTime','ApproverRecruitplanHR');">最后修改时间</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='recpLastChangeTime_img'></th>
	     	<th><a href="#" onclick="order_submit_recruitment('recpStatus','ApproverRecruitplanHR');">状态</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='recpStatus_img'></th>
	     	<th>操作</th>
	     </tr>
	     <ww:if test="!recruitplanList.isEmpty()">
	     	<ww:iterator value="recruitplanList" status="index">
	     		 <tr onMouseOver="this.bgColor='#DDF0F8'" onMouseOut="this.bgColor='#ffffff'" id="approveHRTr<ww:property value='id'/>">
	     		    <td align="center"><input type="checkbox" name='changIds' class="checkbox" value="<ww:property value='id'/>"/></td>
					<td><span class="view" onclick="showplanDetailInSP(
													'<ww:property value="recpJobTitle.jobtitleName"/>',
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
					<td><ww:property value="recpDepartmentNo.departmentName"  /></td>
					<td><ww:property value="recpWorkLocation.locationName" /></td>
					<td><ww:property value="recpJobTitle.jobtitleName" /></td>
					<td><ww:property value="recpNumberExpect" /></td>  
					<td><ww:date name="recpStartDate" format="yyyy-MM-dd"/></td>  
					<td><ww:date name="recpEndDate" format="yyyy-MM-dd"/></td> 					 
					<td><ww:date name="recpLastChangeTime" format="yyyy-MM-dd"/></td> 
					<td onMouseOut="hideLog();"><span class="view" onclick="LogShowDiv('<ww:property value="id" />','recruitplan',this);">部门经理已审</span></td>
					<td align="center">
					  <span class="view"><img src="../resource/images/MgrApprove.gif" alt='同意' onclick="document.getElementById('FlagDeptHR').value='HR';document.getElementById('approve_recordId').value='<ww:property value='id'/>';showApproveComment('agree','<ww:property value='recpNo'/>');" border='0'/></span>
					  <span class="view"><img src="../resource/images/MgrReject.gif" alt='拒绝' onclick="document.getElementById('FlagDeptHR').value='HR';document.getElementById('approve_recordId').value='<ww:property value='id'/>';showApproveComment('reject','<ww:property value='recpNo'/>');" border='0'/></span>
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
<p></p> 
	<table border="0" width="100%" id="table3" cellspacing="0" cellpadding="0" class="formtable">
		<tr>
			<td>	
			<input class="button" type="button"  onclick="document.getElementById('FlagDeptHR').value='HR';batchApprovedPlan();" value="HR备案">
			</td>
		</tr>
	</table>				
</ww:if>
<div id="tmpletId" style="DISPLAY: none"><img src="../resource/images/basic_search.gif" onload="check_order();"/></div>
</ww:form>	
</body>	