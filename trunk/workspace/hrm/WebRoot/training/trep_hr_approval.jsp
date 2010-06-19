<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@taglib prefix="ww" uri="webwork"%>
<%@taglib prefix="ty" uri="/tengsource"%>
<%@ include file="/configuration/log.jsp" %>
<%@ include file="train_approve_div.jsp" %>

<head>
	<script language="JavaScript" type="text/JavaScript" src="../resource/js/training/training.js"></script>	
	<SCRIPT language=javascript src="../resource/js/dom-drag.js"></SCRIPT>
	<script type="text/javascript" src="../resource/js/tmp.js"></script>
</head>
<body onload="check_order();">
	<ww:component template="bodyhead">
		<ww:param name="pagetitle" value="'培训计划HR备案'" />
		<ww:param name="helpUrl" value="'52'" />
	</ww:component>

	<ww:form name="hrApproveForm" id="hrApproveForm" action="trepHrApprove" namespace="/training" method="POST">			
			<ww:token></ww:token>
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="formtable">
				<tr>
					<td>
						<ww:hidden id="order" name="page.order" />
						<ww:hidden id="operate" name="page.operate" />
	                    <ww:hidden  id="idfordetail" name="id" />
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>					
							<ww:textfield label="员工姓名" id="emp" name="emp" size="12" maxlength="32" />							
							<ww:textfield label="课程名称" id="trcName" name="trcName" size="20" maxlength="64" />
							<ww:textfield label="培训地点" name="trcpLocation" size="20" maxlength="64" />
						</tr>
						</table>
					</td>
					<td>
						<input title="[Alt+F]" accesskey="F" id="submit_button" class="button" type="submit"   value="查询">
						<input title="[Alt+C]" accesskey="C" class="button" type="button" onClick="window.location='trepHrApprove.action';" value="重置">
						<br> 
					</td>
				</tr>
			</table>
			<p>
				&nbsp;
			</p>		
 
		<table id="hrApproveTable" cellpadding="0" cellspacing="0" width="100%" border="0" class="gridtableList">
			<ww:hidden id="action" name="action"></ww:hidden>
			<ww:hidden id="trepId" name="trepId"></ww:hidden>
			<ww:if test="page.isSplit()">
				<tr>
					<td colspan="12" align="right" class="listViewPaginationTdS1">
						<ww:hidden name="page.currentPage" />
						<a href="#" onclick="splits('first','hrApproveForm');"><img
								src='../resource/images/start.gif' width='11' height='9'
								alt='开始'>开始</a>
						<a href="#" onclick="splits('previous','hrApproveForm');"><img
								src='../resource/images/previous.gif' width='6' height='9'
								alt='上页'>上页</a> （
						<ww:property value="page.currentPage+'/'+page.totalPages" />
						页｜共
						<ww:property value="page.totalRows" />
						条）
						<a href="#" onclick="splits('next','hrApproveForm');">下页<img
								src='../resource/images/next.gif' width='6' height='9'></a>
						<a href="#" onclick="splits('last','hrApproveForm');">最后<img
								src='../resource/images/end.gif' width='11' height='9' alt='最后'></a>
					</td>
				</tr>
			</ww:if>
				<tr>
				<th><input id='changIds_choose' name='changIds_choose' class="checkbox" type="checkbox" onclick="checkAll('changIds');" value="0"></th>
				<th><a href="#" onclick="order_submit('trpTraineeNo.empName','hrApproveForm');">员工姓名</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='trpTraineeNo.empName_img'></th>
				<th><a href="#" onclick="order_submit('trpTrcp.trcpCourseNo.trcName','hrApproveForm');">课程名称</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='trpTrcp.trcpCourseNo.trcName_img'></th>
				<th><a href="#" onclick="order_submit('trpTrcp.trcpLocation','hrApproveForm');">培训地点</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='trpTrcp.trcpLocation_img'></th>				
				<th><a href="#" onclick="order_submit('trpTrcp.trcpStartDate','hrApproveForm');">开始日期</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='trpTrcp.trcpStartDate_img'></th>
				<th><a href="#" onclick="order_submit('trpTrcp.trcpEndDate','hrApproveForm');">结束日期</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='trpTrcp.trcpEndDate_img'></th>
				<th><a href="#" onclick="order_submit('trpTrcp.trcpBudgetHour','hrApproveForm');">培训小时数</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='trpTrcp.trcpBudgetHour_img'></th>
				<th> 状态 </th>	
				<th> 操作 </th>									
			</tr>
			<ww:if test="!trepList.isEmpty()">
						<ww:iterator value="trepList" status="index">
						<ww:hidden id="%{'trpTrcp.trcpId'+(#index.count)}" name="tmp" value="%{trpTrcp.trcpId}" />
									<ww:hidden id="%{'trpTrcp.trcpCourseNo.trcFileName'+(#index.count)}" name="tmp" value="%{trpTrcp.trcpCourseNo.trcFileName}" />																	
									<ww:hidden id="%{'trpTrcp.trcpCourseNo.trcName'+(#index.count)}" name="tmp" value="%{trpTrcp.trcpCourseNo.trcName}"></ww:hidden>
									<ww:if test="trpTrcp.trcpStatus==0">
									<ww:hidden id="%{'trpTrcp.trcpStatus'+(#index.count)}" name="tmp" value="关闭"></ww:hidden>
									</ww:if>
									<ww:else>
									<ww:hidden id="%{'trpTrcp.trcpStatus'+(#index.count)}" name="tmp" value="允许申请"></ww:hidden>
									</ww:else>
									<ww:hidden id="%{'trpTrcp.trcpBudgetHour'+(#index.count)}" name="tmp" value="%{trpTrcp.trcpBudgetHour}" />
									<ww:hidden id="%{'trpTrcp.deptNames'+(#index.count)}" name="tmp" value="%{trpTrcp.deptNames}" />
									<ww:hidden id="%{'trpTrcp.trcpAttendeeNo'+(#index.count)}" name="tmp" value="%{trpTrcp.trcpAttendeeNo}" />
									<ww:hidden id="%{'trpTrcp.trcpCoordinator.empName'+(#index.count)}" name="tmp" value="%{trpTrcp.trcpCoordinator.empName}" />
									<ww:hidden id="%{'trpTrcp.trcpStartDate'+(#index.count)}" name="tmp" value="%{trpTrcp.trcpStartDate}" />
									<ww:hidden id="%{'trpTrcp.trcpTeacher'+(#index.count)}" name="tmp" value="%{trpTrcp.trcpTeacher}" />
									<ww:hidden id="%{'trpTrcp.trcpEndDate'+(#index.count)}" name="tmp" value="%{trpTrcp.trcpEndDate}" />									
									<ww:hidden id="%{'trpTrcp.trcpInstitution'+(#index.count)}" name="tmp" value="%{trpTrcp.trcpInstitution}" />
									<ww:hidden id="%{'trpTrcp.trcpEnrollStartDate'+(#index.count)}" name="tmp" value="%{trpTrcp.trcpEnrollStartDate}" />
									<ww:hidden id="%{'trpTrcp.trcpLocation'+(#index.count)}" name="tmp" value="%{trpTrcp.trcpLocation}" />
									<ww:hidden id="%{'trpTrcp.trcpEnrollEndDate'+(#index.count)}" name="tmp" value="%{trpTrcp.trcpEnrollEndDate}" />
									<ww:hidden id="%{'trpTrcp.trcpComments'+(#index.count)}" name="tmp" value="%{trpTrcp.trcpComments}" />
						<tr id="trep_hr_tr<ww:property value='trpId'/>">
								<td align="center">
										<input type="checkbox" name="changIds" class="checkbox" value="<ww:property value='trpId'/>" />
									</td>									
							
								<td><ww:property value="trpTraineeNo.empName" /></td>															
								
								<td><span style="font-size: 12px; color: #002780; text-decoration: underline; cursor: pointer"
										onclick="showTrcpDetail('trcpDetail','<ww:property value="#index.count"/>');" class="listViewTdLinkS1"><ty:text counts="17"><ww:property value="trpTrcp.trcpCourseNo.trcName" /></ty:text></span></td>
								<td><ty:text counts="17"><ww:property value="trpTrcp.trcpLocation" /></ty:text></td>								
								<td><ww:date name="trpTrcp.trcpStartDate" format="yyyy-MM-dd" /></td>
								<td><ww:date name="trpTrcp.trcpEndDate" format="yyyy-MM-dd" /></td>						
								<td align="right"><ww:property value="trpTrcp.trcpBudgetHour" /></td>															
									<td onMouseOut="hideLog();">
									<span style="font-size: 12px; color: #002780; text-decoration: underline; cursor: pointer"
											 onClick="LogShowDiv('<ww:property value="trpId"/>', 'tremployeeplan',this);"><ww:property value="getTreStatus(trpStatus)" /></span>
									</td>	
									<td align="center">
									<span class="view" onclick="document.getElementById('FlagDeptHR').value='HR';document.getElementById('approve_recordId').value='<ww:property value='trpId'/>';
											showApproveComment('agree','<ww:property value="trpTraineeNo.empName" />','<ww:property value='trpTrcp.trcpCourseNo.trcName'/>');"><img src="../resource/images/MgrApprove.gif" alt='同意' border='0' /></span>
									<span class="view" onclick="document.getElementById('FlagDeptHR').value='HR';document.getElementById('approve_recordId').value='<ww:property value='trpId'/>';
											showApproveComment('reject','<ww:property value="trpTraineeNo.empName" />','<ww:property value='trpTrcp.trcpCourseNo.trcName'/>');"><img src="../resource/images/MgrReject.gif" alt='拒绝' border='0' /></span>
							</td>
						</tr>
				</ww:iterator>
		</ww:if>
		<ww:else>
			<tr>
				<!-- 不存在符合条件的培训申请！ -->
				<td align="center" colspan="12">
					不存在符合条件的培训申请!
				</td>
			</tr>
		</ww:else>		
		</table>
		<ww:if test="!trepList.isEmpty()">
		<input id="submitBt" class="button" type="button" onclick="document.getElementById('FlagDeptHR').value='HR';batchApprovedPlan();" name="hrApprove" value="同意">
		</ww:if>		
	</ww:form>
	<div id="trcpDetail" title="课程计划详细信息">
		<table borderColor="#b8c0c4" cellSpacing="0" cellPadding="0" width="100%" class="gridview">
			<tr>
				<td width="18%" class="tablefield">课程名称：</td>
				<td colspan="3" id="tdtrpTrcp.trcpCourseNo.trcName"></td>										
			</tr>
			<tr>
				<td class="tablefield">状态:</td><td id="tdtrpTrcp.trcpStatus"></td>
				<td class="tablefield">培训小时数:</td><td id="tdtrpTrcp.trcpBudgetHour"></td>
			</tr>
			<tr>
				<td class="tablefield">限制报名部门:</td><td id="tdtrpTrcp.deptNames"></td>		
				<td class="tablefield">限制报名人数:</td><td id="tdtrpTrcp.trcpAttendeeNo"></td>		
			</tr>		
			<tr>
				<td class="tablefield">负责员工:</td><td id="tdtrpTrcp.trcpCoordinator.empName"></td>
				<td class="tablefield">培训开始日期:</td><td id="tdtrpTrcp.trcpStartDate"></td>
			</tr>
			<tr>
				<td class="tablefield">培训老师:</td><td id="tdtrpTrcp.trcpTeacher"></td>
				<td class="tablefield">培训结束日期:</td><td id="tdtrpTrcp.trcpEndDate"></td>
			</tr>
			<tr>
				<td class="tablefield">培训机构:</td><td id="tdtrpTrcp.trcpInstitution"></td>
				<td class="tablefield">报名开始日期:</td><td id="tdtrpTrcp.trcpEnrollStartDate"></td>				
			</tr>
			<tr>
				<td class="tablefield">培训地点:</td><td id="tdtrpTrcp.trcpLocation"></td>
				<td class="tablefield">报名结束日期:</td><td id="tdtrpTrcp.trcpEnrollEndDate"></td>
			</tr>											
			<tr>
				<td class="tablefield">备注:</td><td colspan="3" id="tdtrpTrcp.trcpComments"></td>
			</tr>
			<tr>
				<td class="tablefield">附件下载:</td><td colspan="3" id="trpTrcp.trcpCourseNo.trcFileName"></td>
			</tr>												
			<tr>
			<td align="center" colspan="4"><input type="button" value="关闭" id="divCloseBt" class="button" onclick="hrm.common.closeDialog('trcpDetail')"/>
			</td>										
			</tr>
		</table>
	</div>
									
	<script type="text/javascript">
		hrm.common.initDialog('trcpDetail',520);
	</script>

</body>
