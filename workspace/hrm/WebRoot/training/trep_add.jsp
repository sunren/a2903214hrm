<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@taglib prefix="ww" uri="webwork"%>
<%@ taglib prefix="ty" uri="/tengsource"%>

<head>
	<script language="JavaScript" type="text/JavaScript" src="../resource/js/training/training.js"></script>
	<script language="JavaScript" type="text/JavaScript" src="../resource/js/validator.js"></script>
	<SCRIPT language="JavaScript" type="text/JavaScript" src="../resource/js/dom-drag.js"></SCRIPT>	
    <SCRIPT language="JavaScript" type="text/JavaScript" src="../resource/js/tmp.js"></SCRIPT> 
</head>
<body onload="check_order();">
	<ww:component template="bodyhead">
		<ww:param name="pagetitle" value="'培训申请'" />
		<ww:param name="helpUrl" value="'50'" />
	</ww:component>

	<ww:form name="trcpForm" id="trcpForm" namespace="/training" action="trepAddInit" method="POST">

			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="formtable">
				<tr>
					<td>
						<ww:hidden id="order" name="page.order" />
						<ww:hidden id="operate" name="page.operate" />
	                    
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<ww:textfield label="课程名称" id="trcName" name="trcName" size="16" maxlength="64" />								
								<ww:textfield label="培训老师" id="trcpTeacher" name="trcpTeacher" size="16" maxlength="64" />
								<ww:textfield label="培训地点" id="trcpLocation" name="trcpLocation" size="16" maxlength="128" />								
							</tr>
						</table>
					</td>
					<td>
						<input title="[Alt+F]" accesskey="F" id="submit_button" class="button" type="submit"   value="查询">
						<input title="[Alt+C]" accesskey="C" class="button" type="button" onClick="window.location='trepAddInit.action';" value="重置">
						<br> 
					</td>
				</tr>
			</table>
			<p>
				&nbsp;
			</p> 
		<table id="trainingplantable" cellpadding="0" cellspacing="0" width="100%" border="0" class="gridtableList">
			<ww:hidden id="action" name="action"></ww:hidden>
			<ww:if test="page.isSplit()">
			<ww:if test="page.totalRows>1">
				<tr>
					<td colspan="10" align="right" class="listViewPaginationTdS1">
						<ww:hidden name="page.currentPage" />
						<a href="#" onclick="splits('first','trcpForm');"><img
								src='../resource/images/start.gif' width='11' height='9'
								alt='开始'>开始</a>
						<a href="#" onclick="splits('previous','trcpForm');"><img
								src='../resource/images/previous.gif' width='6' height='9'
								alt='上页'>上页</a> （
						<ww:property value="page.currentPage+'/'+page.totalPages" />
						页｜共
						<ww:property value="page.totalRows" />
						条）
						<a href="#" onclick="splits('next','trcpForm');">下页<img
								src='../resource/images/next.gif' width='6' height='9'></a>
						<a href="#" onclick="splits('last','trcpForm');">最后<img
								src='../resource/images/end.gif' width='11' height='9' alt='最后'></a>
					</td>
				</tr>
			</ww:if>
			</ww:if>
				<tr>							
				
				<th>
					<a href="#" onclick="order_submit('trcpCourseNo.trcName','trcpForm');">课程名称</a><img 
								src='../resource/images/arrow_.gif' width='8' height='10' id='trcpCourseNo.trcName_img'>
				</th>
				<th>
					<a href="#" onclick="order_submit('trcpCoordinator.empName','trcpForm');">负责员工</a><img 
								src='../resource/images/arrow_.gif' width='8' height='10' id='trcpCoordinator.empName_img'>				
				</th>
				<th>
					<a href="#" onclick="order_submit('trcpTeacher','trcpForm');">培训老师</a><img 
								src='../resource/images/arrow_.gif' width='8' height='10' id='trcpTeacher_img'>				
				</th>
				<th>
					<a href="#" onclick="order_submit('trcpLocation','trcpForm');">培训地点</a><img 
								src='../resource/images/arrow_.gif' width='8' height='10' id='trcpLocation_img'>
				</th>
				<th>
					<a href="#" onclick="order_submit('trcpStartDate','trcpForm');">开始日期</a><img 
								src='../resource/images/arrow_.gif' width='8' height='10' id='trcpStartDate_img'>
				</th>
				<th>
					<a href="#" onclick="order_submit('trcpEndDate','trcpForm');">结束日期</a><img 
								src='../resource/images/arrow_.gif' width='8' height='10' id='trcpEndDate_img'>
				</th>
				<th>
					<a href="#" onclick="order_submit('trcpBudgetHour','trcpForm');">培训小时数</a><img 
								src='../resource/images/arrow_.gif' width='8' height='10' id='trcpBudgetHour_img'>
				</th>				
				<th>
					操作
				</th>									
			</tr>
			<ww:if test="!trcpList.isEmpty()">
						<ww:iterator value="trcpList" status="index">	
						<ww:hidden id="%{'trpTrcp.trcpId'+(#index.count)}" name="tmp" value="%{trcpId}" />
									<ww:hidden id="%{'trpTrcp.trcpCourseNo.trcFileName'+(#index.count)}" name="tmp" value="%{trcpCourseNo.trcFileName}" />																	
									<ww:hidden id="%{'trpTrcp.trcpCourseNo.trcName'+(#index.count)}" name="tmp" value="%{trcpCourseNo.trcName}"></ww:hidden>
									<ww:if test="trcpStatus==0">
									<ww:hidden id="%{'trpTrcp.trcpStatus'+(#index.count)}" name="tmp" value="关闭"></ww:hidden>
									</ww:if>
									<ww:else>
									<ww:hidden id="%{'trpTrcp.trcpStatus'+(#index.count)}" name="tmp" value="允许申请"></ww:hidden>
									</ww:else>
									<ww:hidden id="%{'trpTrcp.trcpBudgetHour'+(#index.count)}" name="tmp" value="%{trcpBudgetHour}" />
									<ww:hidden id="%{'trpTrcp.deptNames'+(#index.count)}" name="tmp" value="%{deptNames}" />
									<ww:hidden id="%{'trpTrcp.trcpAttendeeNo'+(#index.count)}" name="tmp" value="%{trcpAttendeeNo}" />
									<ww:hidden id="%{'trpTrcp.trcpCoordinator.empName'+(#index.count)}" name="tmp" value="%{trcpCoordinator.empName}" />
									<ww:hidden id="%{'trpTrcp.trcpStartDate'+(#index.count)}" name="tmp" value="%{trcpStartDate}" />
									<ww:hidden id="%{'trpTrcp.trcpTeacher'+(#index.count)}" name="tmp" value="%{trcpTeacher}" />
									<ww:hidden id="%{'trpTrcp.trcpEndDate'+(#index.count)}" name="tmp" value="%{trcpEndDate}" />									
									<ww:hidden id="%{'trpTrcp.trcpInstitution'+(#index.count)}" name="tmp" value="%{trcpInstitution}" />
									<ww:hidden id="%{'trpTrcp.trcpEnrollStartDate'+(#index.count)}" name="tmp" value="%{trcpEnrollStartDate}" />
									<ww:hidden id="%{'trpTrcp.trcpLocation'+(#index.count)}" name="tmp" value="%{trcpLocation}" />
									<ww:hidden id="%{'trpTrcp.trcpEnrollEndDate'+(#index.count)}" name="tmp" value="%{trcpEnrollEndDate}" />
									<ww:hidden id="%{'trpTrcp.trcpComments'+(#index.count)}" name="tmp" value="%{trcpComments}" />													
						<tr>
								<td><a href="#" onclick="showTrcpDetail('trcpDetail','<ww:property value="#index.count"/>');" class="listViewTdLinkS1"><ty:text counts="17"><ww:property value="trcpCourseNo.trcName" /></ty:text></a></td>
								<td><ww:property value="trcpCoordinator.empName" /></td>
								<td><ww:property value="trcpTeacher" /></td>	
								<td><ty:text counts="17"><ww:property value="trcpLocation" /></ty:text></td>								
								<td><ww:date name="trcpStartDate" format="yyyy-MM-dd" /></td>
								<td><ww:date name="trcpEndDate" format="yyyy-MM-dd" /></td>
								<td align="right"><ww:property value="trcpBudgetHour" /></td>						
								   <td align="center">
								   <ww:if test="trcpStatus==1">								   
								   		<a href="#" onclick="showComment('comment', 'trcpAddId', '<ww:property value="trcpId" />', 'apply');">
										<img src="../resource/images/Submit.gif" alt='申请' border='0' /></a>
								  	</ww:if>								  	
									
				</td>
			</tr>		
			</ww:iterator>
		</ww:if>
		<ww:else>
			<tr>
				<!-- 不存在符合条件的课程计划！ -->
				<td align="center" colspan="11">
					不存在符合条件的课程计划!
				</td>
			</tr>
		</ww:else>
		</table>
		</ww:form>
		
		<div id="comment" title="培训申请">
			<ww:form id="commentForm" action="trepAdd" method="post">
			<table border="0" cellspacing="2" cellpadding="0" class="prompt_div_body" width="100%">
					<tr>
						<td width="65" align="right">备注：</td><td><ww:textarea name="comments" cols="45" rows="5" id="comments" onkeypress="MKeyTextLength(this,128);"/></td>		
						<ww:hidden name="trcpAddId" id="trcpAddId"></ww:hidden>	
					</tr>					
					<tr>					
				    	<td></td>
				    	<td align="left"><input type="button" onClick="trepAddSubmit();" value="提交" id="trepAddSubmitBtn">&nbsp;<input type="button" onclick="hrm.common.closeDialog('comment');" value="取消"></td>
				    </tr>
			</table>
			</ww:form>
		</div>
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
			hrm.common.initDialog('comment',450);
		</script> 
</body>
