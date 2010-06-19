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
		<ww:param name="pagetitle" value="%{'�γ�'+ trcName +'��ϸ��Ϣ'}" />
		<ww:param name="helpUrl" value="'55'" />
	</ww:component>

<div id="view_trcourse_detail" align="left">		
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="formtable">
			<TR>
				<TD noWrap align=right colspan="4">
					<A class=tabDetailViewDFLink href="trcourseConfig.action">���ؿγ�����ҳ��</A>						             
				</TD>
			</TR>
			<tr>
			   	<td>
				�γ�����:&nbsp;<ww:property value="trc.trcName"/>								
				</td>				
			    <td>�γ���ѵ����:&nbsp;<ww:property value="trc.trcType.trtName" />
			    </td>
			    </tr>
				<tr>
			    <td>
				״̬:&nbsp;<ww:if test="trc.trcStatus==1">������</ww:if><ww:else>�ر�</ww:else>				
				</td>
				<td>
				
				<ww:if test="(trc.trcFileName != null && !trc.trcFileName.equals(''))">						
				��ѵ����:&nbsp;<a href="#" onClick="downFile('downFile.action?fileName=<ww:property value="trc.trcFileName"/>&contentDisposition=�γ���ѵ����');"><img src="../resource/images/attachment.gif"/></a>
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
<input type="button" class="button" onclick="window.location='trcpAddInit.action?trcNo=<ww:property value="trc.trcNo"/>';" value="�����γ̼ƻ�"/>
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
								alt='��ʼ'>��ʼ</a>
						<a href="#" onclick="splits('previous','updateForm');"><img
								src='../resource/images/previous.gif' width='6' height='9'
								alt='��ҳ'>��ҳ</a> ��
						<ww:property value="page.currentPage+'/'+page.totalPages" />
						ҳ����
						<ww:property value="page.totalRows" />
						����
						<a href="#" onclick="splits('next','updateForm');">��ҳ<img
								src='../resource/images/next.gif' width='6' height='9'></a>
						<a href="#" onclick="splits('last','updateForm');">���<img
								src='../resource/images/end.gif' width='11' height='9' alt='���'></a>
					</td>
				</tr>
			</ww:if>
			</ww:if>
				<tr>
				<th><a href="#" onclick="order_submit('trcpStartDate','updateForm');">��ʼʱ��</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='trcpStartDate_img'></th>
				<th><a href="#" onclick="order_submit('trcpCoordinator.id','updateForm');">����Ա��</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='trcpCoordinator.id_img'></th>
				<th><a href="#" onclick="order_submit('trcpTeacher','updateForm');">��ѵ��ʦ</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='trcpTeacher_img'></th>
				<th><a href="#" onclick="order_submit('trcpLocation','updateForm');">��ѵ�ص�</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='trcpLocation_img'></th>
				
				<th><a href="#" onclick="order_submit('trcpEndDate','updateForm');">����ʱ��</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='trcpEndDate_img'></th>
				<th><a href="#" onclick="order_submit('trcpLastChangeTime','updateForm');">����޸�ʱ��</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='trcpLastChangeTime_img'></th>			
				<th><a href="#" onclick="order_submit('trcpStatus','updateForm');">״̬</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='trcpStatus_img'></th>
				<th>����</th>									
			</tr>
			<ww:if test="!trcpList.isEmpty()">
						<ww:iterator value="trcpList" status="index">
						<ww:hidden id="%{'trpTrcp.trcpId'+(#index.count)}" name="tmp" value="%{trcpId}" />
									<ww:hidden id="%{'trpTrcp.trcpCourseNo.trcName'+(#index.count)}" name="tmp" value="%{trcpCourseNo.trcName}"></ww:hidden>
									<ww:if test="trcpStatus==0">
									<ww:hidden id="%{'trpTrcp.trcpStatus'+(#index.count)}" name="tmp" value="�ر�"></ww:hidden>
									</ww:if>
									<ww:else>
									<ww:hidden id="%{'trpTrcp.trcpStatus'+(#index.count)}" name="tmp" value="��������"></ww:hidden>
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
									<td>��������<a href="#" onclick="redirectPrompt('closeTrcp.action?trcpId=<ww:property value='trcpId'/>', '��ȷ��Ҫ�رոÿγ̼ƻ���')"><img src="../resource/images/unpublish_inline.gif" alt='�ر�' border='0' /></a></td>									
									</ww:if>
									<ww:elseif test="trcpStatus==0">									
									<td>�ر�<a href="#" onclick="redirectPrompt('trcpOpen.action?trcpId=<ww:property value='trcpId'/>', '��ȷ��Ҫ���Ÿÿγ̼ƻ���')"><img src="../resource/images/unpublish_inline.gif" alt='����' border='0' /></a></td>									
									</ww:elseif>								
																		
								   <td align="center">
								   <a href="#" onclick="window.location='trcpUpdateInit.action?trcpId=<ww:property value="trcpId"/>';"><img src="../resource/images/edit.gif" alt='�޸�' border='0'/></a>									
									<a href="#" onclick="redirectPrompt('trcpDelete.action?trcpId=<ww:property value="trcpId"/>','��ȷ��Ҫɾ���ÿγ̼ƻ���');">
									<img src="../resource/images/delete.gif" alt='ɾ��' border='0' /></a>
									<ww:if test="trcpFileName!=null">	
										<a href="#" onclick="downFile('downFile.action?fileName=<ww:property value="trcpFileName"/>&contentDisposition=��ѵ��������');"><img src="../resource/images/attachment.gif" alt="������ѵ��������"/></a>
									</ww:if>
									<ww:else><img src="../resource/images/blankSpace.gif" alt='' border='0' /></ww:else></td>
			</tr>
			</ww:iterator>
		</ww:if>
		<ww:else>
			<tr>
				<!-- �����ڷ��������Ŀγ̼ƻ��� -->
				<td align="center" colspan="11">
					�����ڷ��������Ŀγ̼ƻ�!
				</td>
			</tr>
		</ww:else>
		</table>
	</ww:form> 
	<div id="trcpDetail" title="�γ̼ƻ���ϸ��Ϣ">
		<table borderColor="#b8c0c4" cellSpacing="0" cellPadding="0" width="100%" class="gridview">
			<tr>
				<td width="18%" class="tablefield">�γ����ƣ�</td>
				<td colspan="3" id="tdtrpTrcp.trcpCourseNo.trcName"></td>										
			</tr>
			<tr>
				<td class="tablefield">״̬:</td><td id="tdtrpTrcp.trcpStatus"></td>
				<td class="tablefield">��ѵСʱ��:</td><td id="tdtrpTrcp.trcpBudgetHour"></td>
			</tr>
			<tr>
				<td class="tablefield">���Ʊ�������:</td><td id="tdtrpTrcp.deptNames"></td>		
				<td class="tablefield">���Ʊ�������:</td><td id="tdtrpTrcp.trcpAttendeeNo"></td>		
			</tr>		
			<tr>
				<td class="tablefield">����Ա��:</td><td id="tdtrpTrcp.trcpCoordinator.empName"></td>
				<td class="tablefield">��ѵ��ʼ����:</td><td id="tdtrpTrcp.trcpStartDate"></td>
			</tr>
			<tr>
				<td class="tablefield">��ѵ��ʦ:</td><td id="tdtrpTrcp.trcpTeacher"></td>
				<td class="tablefield">��ѵ��������:</td><td id="tdtrpTrcp.trcpEndDate"></td>
			</tr>
			<tr>
				<td class="tablefield">��ѵ����:</td><td id="tdtrpTrcp.trcpInstitution"></td>
				<td class="tablefield">������ʼ����:</td><td id="tdtrpTrcp.trcpEnrollStartDate"></td>				
			</tr>
			<tr>
				<td class="tablefield">��ѵ�ص�:</td><td id="tdtrpTrcp.trcpLocation"></td>
				<td class="tablefield">������������:</td><td id="tdtrpTrcp.trcpEnrollEndDate"></td>
			</tr>											
			<tr>
				<td class="tablefield">��ע:</td><td colspan="3" id="tdtrpTrcp.trcpComments"></td>
			</tr>	
												
			<tr>
			<td align="center" colspan="4"><input type="button" value="�ر�" id="divCloseBt" class="button" onclick="hrm.common.closeDialog('trcpDetail')"/>
			</td>										
			</tr>
		</table>
	</div>
									
	<script type="text/javascript">
		hrm.common.initDialog('trcpDetail',520);
	</script> 
</body>	
