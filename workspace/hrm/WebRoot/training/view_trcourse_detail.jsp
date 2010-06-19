<%@page pageEncoding="GBK" contentType="text/html; charset=GBK" %>
<%@taglib prefix="ww" uri="webwork"%>
<%@taglib prefix="log" uri="http://jakarta.apache.org/taglibs/log-1.0"%>

<head>
	<script language="JavaScript" type="text/JavaScript" src="../resource/js/training/training.js"></script>
	<SCRIPT language=javascript src="../resource/js/dom-drag.js"></SCRIPT>
	<script language=javascript src="../resource/js/cookie.js"/></script>
</head>
<body onload="check_order();">
	<ww:component template="bodyhead">
		<ww:param name="pagetitle" value="%{'课程'+ trcName +'详细信息'}" />
		<ww:param name="helpUrl" value="'55'" />
	</ww:component>

<div id="view_trcourse_detail" align="left">		
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="formtable">
			<TR>
				<TD noWrap align=right colspan="4">
					<A class=tabDetailViewDFLink href="trcourseConfig.action">返回课程设置页面</A>						             
				</TD>
			</TR>
			<tr>
			   	<td>
				课程名称:&nbsp;<ww:property value="trc.trcName"/>								
				</td>				
			    <td>课程培训种类:&nbsp;<ww:property value="trc.trcType.trtName" />
			    </td>
			    </tr>
				<tr>
			    <td>
				状态:&nbsp;<ww:if test="trc.trcStatus==1">允许开课</ww:if><ww:else>关闭</ww:else>				
				</td>
				<td>
				
				<ww:if test="(trc.trcFileName != null && !trc.trcFileName.equals(''))">						
				培训资料:&nbsp;<a href="#" onClick="downFile('downFile.action?fileName=<ww:property value="trc.trcFileName"/>&contentDisposition=课程培训资料');"><img src="../resource/images/attachment.gif"/></a>
				</ww:if>
				<ww:else>
				&nbsp;
				</ww:else>
				</td>
		</tr>
	</table>
</div> 
<p>&nbsp;</p>
<ww:if test="trc.trcStatus==1">
<input type="button" class="button" onclick="window.location='trcpAddInit.action?trcNo=<ww:property value="trc.trcNo"/>';" value="新增课程计划"/>
</ww:if>																  	
<ww:form id="updateForm" name="updateForm" action="viewTrcTrcpInit" namespace="/training" method="POST" enctype="multipart/form-data">  
	<ww:hidden name="trc.trcNo"></ww:hidden>
	<table id="trainingplantable" cellpadding="0" cellspacing="0" width="100%" border="0" class="gridtableList">
			<ww:hidden id="order" name="page.order" />
			<ww:hidden id="operate" name="page.operate" />
			
			<ww:if test="page.isSplit()">
			<ww:if test="page.totalPages>1">
				<tr>
					<td colspan="10" align="right" class="listViewPaginationTdS1">
						<ww:hidden name="page.currentPage" />
						<a href="#" onclick="splits('first','updateForm');"><img
								src='../resource/images/start.gif' width='11' height='9'
								alt='开始'>开始</a>
						<a href="#" onclick="splits('previous','updateForm');"><img
								src='../resource/images/previous.gif' width='6' height='9'
								alt='上页'>上页</a> （
						<ww:property value="page.currentPage+'/'+page.totalPages" />
						页｜共
						<ww:property value="page.totalRows" />
						条）
						<a href="#" onclick="splits('next','updateForm');">下页<img
								src='../resource/images/next.gif' width='6' height='9'></a>
						<a href="#" onclick="splits('last','updateForm');">最后<img
								src='../resource/images/end.gif' width='11' height='9' alt='最后'></a>
					</td>
				</tr>
			</ww:if>
			</ww:if>
				<tr>
				<th><a href="#" onclick="order_submit('trcpStartDate','updateForm');">开始时间</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='trcpStartDate_img'></th>
				<th><a href="#" onclick="order_submit('trcpCoordinator.id','updateForm');">负责员工</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='trcpCoordinator.id_img'></th>
				<th><a href="#" onclick="order_submit('trcpTeacher','updateForm');">培训老师</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='trcpTeacher_img'></th>
				<th><a href="#" onclick="order_submit('trcpLocation','updateForm');">培训地点</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='trcpLocation_img'></th>
				
				<th><a href="#" onclick="order_submit('trcpEndDate','updateForm');">结束时间</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='trcpEndDate_img'></th>
				<th><a href="#" onclick="order_submit('trcpLastChangeTime','updateForm');">最后修改时间</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='trcpLastChangeTime_img'></th>			
				<th><a href="#" onclick="order_submit('trcpStatus','updateForm');">状态</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='trcpStatus_img'></th>
				<th>操作</th>									
			</tr>
			<ww:if test="!trcpList.isEmpty()">
						<ww:iterator value="trcpList" status="index">
						<ww:hidden id="%{'trpTrcp.trcpId'+(#index.count)}" name="tmp" value="%{trcpId}" />
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
								<td><a href="#" onclick="showTrcpDetailInDetail('trcpDetail','<ww:property value="#index.count"/>');" 
											class="listViewTdLinkS1"><ww:date name="trcpStartDate" format="yyyy-MM-dd" /></a></td>
								<td><ww:property value="trcpCoordinator.empName" />	</td>
								<td><ww:property value="trcpTeacher" /></td>
								<td><ww:property value="trcpLocation" /></td>								
								
								<td><ww:date name="trcpEndDate" format="yyyy-MM-dd" /></td>
								<td><ww:property value="trcpLastChangeTime" /></td>
									<ww:if test="trcpStatus==1">									
									<td>允许申请<a href="#" onclick="redirectPrompt('closeTrcp.action?trcpId=<ww:property value='trcpId'/>', '您确定要关闭该课程计划吗？')"><img src="../resource/images/unpublish_inline.gif" alt='关闭' border='0' /></a></td>									
									</ww:if>
									<ww:elseif test="trcpStatus==0">									
									<td>关闭<a href="#" onclick="redirectPrompt('trcpOpen.action?trcpId=<ww:property value='trcpId'/>', '您确定要开放该课程计划吗？')"><img src="../resource/images/unpublish_inline.gif" alt='开放' border='0' /></a></td>									
									</ww:elseif>								
																		
								   <td align="center">
								   <a href="#" onclick="window.location='trcpUpdateInit.action?trcpId=<ww:property value="trcpId"/>';"><img src="../resource/images/edit.gif" alt='修改' border='0'/></a>									
									<a href="#" onclick="redirectPrompt('trcpDelete.action?trcpId=<ww:property value="trcpId"/>','您确定要删除该课程计划吗？');">
									<img src="../resource/images/delete.gif" alt='删除' border='0' /></a>
									<ww:if test="trcpFileName!=null">	
										<a href="#" onclick="downFile('downFile.action?fileName=<ww:property value="trcpFileName"/>&contentDisposition=培训反馈资料');"><img src="../resource/images/attachment.gif" alt="下载培训反馈资料"/></a>
									</ww:if>
									<ww:else><img src="../resource/images/blankSpace.gif" alt='' border='0' /></ww:else></td>
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
			<td align="center" colspan="4"><input type="button" value="关闭" id="divCloseBt" class="button" onclick="hrm.common.closeDialog('trcpDetail')"/>
			</td>										
			</tr>
		</table>
	</div>
									
	<script type="text/javascript">
		hrm.common.initDialog('trcpDetail',520);
	</script> 
</body>	
