<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@taglib prefix="ww" uri="webwork"%>
<%@taglib prefix="ty" uri="/tengsource"%>
<%@ include file="/configuration/log.jsp" %>
<head>
	<SCRIPT language=javascript src="../resource/js/dom-drag.js"></SCRIPT>
	<script language="JavaScript" type="text/JavaScript" src="../resource/js/training/training.js"></script>
</head>
<body onload="check_order();">
	<ww:component template="bodyhead">
		<ww:param name="pagetitle" value="'我的培训计划'" />
		<ww:param name="helpUrl" value="'49'" />
	</ww:component>
		
	<ww:form name="myTrainingListForm" id="myTrainingListForm" action="myTrainingList" namespace="/training" method="POST">

		 <table width="100%" border="0" cellspacing="0" cellpadding="0" class="formtable">
				<tr>
					<td>
						<ww:hidden id="order" name="page.order" />
						<ww:hidden id="operate" name="page.operate" />	                    
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<ww:textfield label="课程名称" name="trcName" size="16" maxlength="64" />	
							<ww:textfield label="培训老师" name="trcpTeacher" size="12" maxlength="64" />
							<ww:textfield label="培训地点" name="trcpLocation" size="16" maxlength="64" />																																			
							<ww:select label="状态" name="trpStatus" list="#{0:'请选择', 2:'已提交', 11:'部门已审', 12:'HR已审', 21:'已拒绝', 22:'已作废', 31:'已反馈'}"></ww:select>
						</tr>
						</table>
					</td>
					<td>
						<input title="[Alt+F]" accesskey="F" id="submit_button" class="button" type="submit"   value="查询">
						<input title="[Alt+C]" accesskey="C" class="button" type="button" onClick="window.location='myTrainingList.action';" value="重置">
					</td>
				</tr>
			</table>
			<p>&nbsp;</p>
		<table id="trainingplantable" cellpadding="0" cellspacing="0" width="100%" border="0" class="gridtableList">

			<ww:if test="page.isSplit()">
				<tr>
					<td colspan="11" align="right" class="listViewPaginationTdS1">
						<ww:hidden name="page.currentPage" />
						<a href="#" onclick="splits('first','myTrainingListForm');"><img
								src='../resource/images/start.gif' width='11' height='9'
								alt='开始'>开始</a>
						<a href="#" onclick="splits('previous','myTrainingListForm');"><img
								src='../resource/images/previous.gif' width='6' height='9'
								alt='上页'>上页</a> （
						<ww:property value="page.currentPage+'/'+page.totalPages" />
						页｜共
						<ww:property value="page.totalRows" />
						条）
						<a href="#" onclick="splits('next','myTrainingListForm');">下页<img
								src='../resource/images/next.gif' width='6' height='9'></a>
						<a href="#" onclick="splits('last','myTrainingListForm');">最后<img
								src='../resource/images/end.gif' width='11' height='9' alt='最后'>
						</a>
					</td>
				</tr>
			</ww:if>
				<tr>
				<th><a href="#" onclick="order_submit('trpTrcp.trcpCourseNo.trcName','myTrainingListForm');">课程名称</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='trpTrcp.trcpCourseNo.trcName_img'></th>
				<th><a href="#" onclick="order_submit('trpTrcp.trcpCoordinator.empName','myTrainingListForm');">负责员工</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='trpTrcp.trcpCoordinator.empName_img'></th>
				<th><a href="#" onclick="order_submit('trpTrcp.trcpTeacher','myTrainingListForm');">培训老师</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='trpTrcp.trcpTeacher_img'></th>
				<th><a href="#" onclick="order_submit('trpTrcp.trcpLocation','myTrainingListForm');">培训地点</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='trpTrcp.trcpLocation_img'></th>				
				<th><a href="#" onclick="order_submit('trpTrcp.trcpStartDate','myTrainingListForm');">开始日期</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='trpTrcp.trcpStartDate_img'></th>
				<th><a href="#" onclick="order_submit('trpTrcp.trcpEndDate','myTrainingListForm');">结束日期</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='trpTrcp.trcpEndDate_img'></th>
				<th><a href="#" onclick="order_submit('trpTrcp.trcpBudgetHour','myTrainingListForm');">培训小时数</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='trpTrcp.trcpBudgetHour_img'></th>			
				<th><a href="#" onclick="order_submit('trpStatus','myTrainingListForm');">状态</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='trpStatus_img'></th>
				<th>操作</th>
									
			</tr>
			<ww:if test="!myTrainingList.isEmpty()">
						<ww:iterator value="myTrainingList" status="index">
						<ww:hidden id="%{'trpTrcp.trcpId'+(#index.count)}" name="tmp" value="%{trpTrcp.trcpId}" />
									<ww:hidden id="%{'trpTrcp.trcpCourseNo.trcFileName'+(#index.count)}" name="tmp" value="%{trpTrcp.trcpCourseNo.trcFileName}" />																	
									<ww:hidden id="%{'trpTrcp.trcpCourseNo.trcName'+(#index.count)}" name="tmp" value="%{trpTrcp.trcpCourseNo.trcName}"></ww:hidden>
									
									
									<ww:if test="trpTrcp.trcpStatus==0">
									<ww:hidden id="%{'trpTrcp.trcpStatus'+(#index.count)}" name="tmp" value="关闭"></ww:hidden>
									</ww:if>
									<ww:else>
									<ww:hidden id="%{'trpTrcp.trcpStatus'+(#index.count)}" name="tmp" value="允许申请"></ww:hidden>
									</ww:else>
									<ww:hidden id="%{'trpTrcp.trcpBudgetHour'+(#index.count)}" name="tmp"  value="%{trpTrcp.trcpBudgetHour}" />
									<ww:hidden id="%{'trpTrcp.deptNames'+(#index.count)}" name="tmp"  value="%{trpTrcp.deptNames}" />
									<ww:hidden id="%{'trpTrcp.trcpAttendeeNo'+(#index.count)}" name="tmp"  value="%{trpTrcp.trcpAttendeeNo}" />
									<ww:hidden id="%{'trpTrcp.trcpCoordinator.empName'+(#index.count)}" name="tmp" value="%{trpTrcp.trcpCoordinator.empName}" />
									<ww:hidden id="%{'trpTrcp.trcpStartDate'+(#index.count)}" name="tmp" value="%{trpTrcp.trcpStartDate}" />
									<ww:hidden id="%{'trpTrcp.trcpTeacher'+(#index.count)}" name="tmp" value="%{trpTrcp.trcpTeacher}" />
									<ww:hidden id="%{'trpTrcp.trcpEndDate'+(#index.count)}" name="tmp" value="%{trpTrcp.trcpEndDate}" />									
									<ww:hidden id="%{'trpTrcp.trcpInstitution'+(#index.count)}" name="tmp" value="%{trpTrcp.trcpInstitution}" />
									<ww:hidden id="%{'trpTrcp.trcpEnrollStartDate'+(#index.count)}" name="tmp" value="%{trpTrcp.trcpEnrollStartDate}" />
									<ww:hidden id="%{'trpTrcp.trcpLocation'+(#index.count)}" name="tmp" value="%{trpTrcp.trcpLocation}" />
									<ww:hidden id="%{'trpTrcp.trcpEnrollEndDate'+(#index.count)}" name="tmp" value="%{trpTrcp.trcpEnrollEndDate}" />
									<ww:hidden id="%{'trpTrcp.trcpComments'+(#index.count)}" name="tmp" value="%{trpTrcp.trcpComments}" />	
									
									
						<tr>	
								<td><a href="#" onclick="showTrcpDetail('trcpDetail','<ww:property value="#index.count"/>');" class="listViewTdLinkS1"><ty:text counts="17"><ww:property value="trpTrcp.trcpCourseNo.trcName" /></ty:text></a></td>
								<td><ww:property value="trpTrcp.trcpCoordinator.empName" /></td>
								<td><ww:property value="trpTrcp.trcpTeacher" /></td>								
								<td><ty:text counts="17"><ww:property value="trpTrcp.trcpLocation" /></ty:text></td>
								
								<td><ww:date name="trpTrcp.trcpStartDate" format="yyyy-MM-dd" /></td>
								<td><ww:date name="trpTrcp.trcpEndDate" format="yyyy-MM-dd" /></td>						
								<td align="right"><ww:property value="trpTrcp.trcpBudgetHour" /></td>
								<td onMouseOut="hideLog();">
									<span class="view" onClick="LogShowDiv('<ww:property value="trpId"/>', 'tremployeeplan',this);">
									<ww:property value="getTreStatus(trpStatus)" /></span>
								</td>
												
								<td align="left" width="60"><ww:if test="trpStatus==1||trpStatus==21">
									<a href="#" onclick="redirectPrompt('trepDelete.action?trpId=<ww:property value="trpId"/>', '您确定要删除该培训申请吗？');">
									<img src="../resource/images/delete.gif" alt='删除' border='0' /></a>
									</ww:if>
									<ww:else><img src="../resource/images/blankSpace.gif" alt='' border='0' /></ww:else>
									<ww:if test="trpStatus==21">
									<a href="#" onclick="showComment('comment', 'trpId', '<ww:property value="trpId" />', '');", '您确定要重新提交该培训申请吗？');">
									<img src="../resource/images/Submit.gif" alt='重新提交' border='0' /></a>
									</ww:if>
									<ww:else><img src="../resource/images/blankSpace.gif" alt='' border='0' /></ww:else>	
									</td>
							
			</tr>
			</ww:iterator>
		</ww:if>
		<ww:else>
			<tr>
				<!-- 不存在符合条件的培训计划！ -->
				<td align="center" colspan="11">
					不存在符合条件的培训计划!
				</td>
			</tr>
		</ww:else>
		</table>		
	</ww:form>	
	<div id="comment" title="重新提交培训申请">
			<ww:form id="commentForm" action="reSubmit" method="post">
			<table border="0" cellspacing="2" cellpadding="0" class="prompt_div_body" width="100%">
					<tr>
						<td width="65" align="right">备注：</td><td><ww:textarea name="comments" cols="45" rows="5" id="comments"/></td>		
						<ww:hidden name="trpId" id="trpId"></ww:hidden>	
					</tr>					
					<tr>					
				    	<td></td>
				    	<td align="left"><input type="button" onClick="resubmit();" value="提交" id="reSubmitBtn">&nbsp;<input type="button" onclick="hrm.common.closeDialog('comment');" value="取消"></td>
				    </tr>
			</table>
			</ww:form>
		</div>
			<div id="trcpDetail" title="课程计划详细信息">
					<table borderColor="#b8c0c4" cellSpacing="0" cellPadding="0" width="100%" class="gridview">

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
		hrm.common.initDialog('trcpDetail',500);
		hrm.common.initDialog('comment',450);
	</script>
</body>
