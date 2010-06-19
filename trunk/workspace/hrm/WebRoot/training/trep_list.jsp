<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@taglib prefix="ww" uri="webwork"%>
<%@ taglib prefix="ty" uri="/tengsource"%>
<%@ include file="/configuration/log.jsp" %>
<head>
	<SCRIPT language=javascript src="../resource/js/dom-drag.js"></SCRIPT>
	<script language="JavaScript" type="text/JavaScript" src="../resource/js/training/training.js"></script>
	<script type="text/javascript" src="../resource/js/tmp.js"></script>
	<script>
	function MKeyTextLength(obj,len)
	{
		if(obj==null||obj=='undefined'){
			obj=$E();
		}
		var currentKey=_event_code();
		if(currentKey!=8 && currentKey!=46 && obj.value.length>(len+0-1))
		{
			if(isIE()){
				event.returnValue = false;
			}else{
				event.preventDefault();
			}
		}
	} 
	</script>
</head>
<body onload="check_order();">
	<ww:component template="bodyhead">
		<ww:param name="pagetitle" value="'Ա����ѵ�ƻ�'" />
		<ww:param name="helpUrl" value="'53'" />
	</ww:component>

	<ww:form name="trepListForm" id="trepListForm" action="trepList" namespace="/training" method="POST">			
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="formtable">
				<tr>
					<td>
						<ww:hidden id="order" name="page.order" />
						<ww:hidden id="operate" name="page.operate" />
	                    
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<tr><ww:textfield label="Ա��" id="emp" name="emp" size="10" maxlength="32" />
							<ww:textfield label="�γ�����" id="trcName" name="trcName" size="16" maxlength="64" />
							<ww:textfield label="��ѵ�ص�" name="trcpLocation" size="20" maxlength="64" />																						
							<ww:select label="״̬" name="trpStatus" list="#{0:'��ѡ��', 2:'���ύ', 11:'��������ͨ��', 12:'HR����ͨ��', 21:'�Ѿܾ�', 22:'������', 31:'�ѷ���'}"></ww:select>
						</tr>
						</table>
					</td>
					<td>
						<input title="[Alt+F]" accesskey="F" id="submit_button" class="button" type="submit" value="��ѯ">
						<input title="[Alt+C]" accesskey="C" class="button" type="button" onClick="window.location='trepList.action'" value="����">
						<br>  
					</td>
				</tr>
			</table>
			<p>
				&nbsp;
			</p>		
 
		<table id="deptaApprovalTable" cellpadding="0" cellspacing="0" width="100%" border="0" class="gridtableList">

			<ww:if test="page.isSplit()">
				<tr>
					<td colspan="12" align="right" class="listViewPaginationTdS1">
						<ww:hidden name="page.currentPage" />
						<a href="#" onclick="splits('first','trepListForm');"><img
								src='../resource/images/start.gif' width='11' height='9'
								alt='��ʼ'>��ʼ</a>
						<a href="#" onclick="splits('previous','trepListForm');"><img
								src='../resource/images/previous.gif' width='6' height='9'
								alt='��ҳ'>��ҳ</a> ��
						<ww:property value="page.currentPage+'/'+page.totalPages" />
						ҳ����
						<ww:property value="page.totalRows" />
						����
						<a href="#" onclick="splits('next','trepListForm');">��ҳ<img
								src='../resource/images/next.gif' width='6' height='9'></a>
						<a href="#" onclick="splits('last','trepListForm');">���<img
								src='../resource/images/end.gif' width='11' height='9' alt='���'></a>
					</td>
				</tr>
			</ww:if>
				<tr>
				<th><a href="#" onclick="order_submit('trpTraineeNo.empName','trepListForm');">Ա������</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='trpTraineeNo.empName_img'></th>
				<th><a href="#" onclick="order_submit('trpTrcp.trcpCourseNo.trcName','trepListForm');">�γ�����</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='trpTrcp.trcpCourseNo.trcName_img'></th>
				<th><a href="#" onclick="order_submit('trpTrcp.trcpLocation','trepListForm');">��ѵ�ص�</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='trpTrcp.trcpLocation_img'></th>				
				<th><a href="#" onclick="order_submit('trpTrcp.trcpStartDate','trepListForm');">��ʼ����</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='trpTrcp.trcpStartDate_img'></th>
				<th><a href="#" onclick="order_submit('trpTrcp.trcpEndDate','trepListForm');">��������</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='trpTrcp.trcpEndDate_img'></th>
				<th><a href="#" onclick="order_submit('trpTrcp.trcpBudgetHour','trepListForm');">��ѵСʱ��</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='trpTrcp.trcpBudgetHour_img'></th>
				<th><a href="#" onclick="order_submit('trpStatus','trepListForm');">״̬</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='trpStatus_img'></th>	
				<th>����</th>									
			</tr>
			<ww:if test="!trepList.isEmpty()">
						<ww:iterator value="trepList" status="index">
						<ww:hidden id="%{'trpTrcp.trcpId'+(#index.count)}" name="tmp" value="%{trpTrcp.trcpId}" />
									<ww:hidden id="%{'trpTrcp.trcpCourseNo.trcFileName'+(#index.count)}" name="tmp" value="%{trpFileName}" />																	
									<ww:hidden id="%{'trpTrcp.trcpCourseNo.trcName'+(#index.count)}" name="tmp" value="%{trpTrcp.trcpCourseNo.trcName}"></ww:hidden>
									<ww:if test="trpTrcp.trcpStatus==0">
									<ww:hidden id="%{'trpTrcp.trcpStatus'+(#index.count)}" name="tmp" value="�ر�"></ww:hidden>
									</ww:if>
									<ww:else>
									<ww:hidden id="%{'trpTrcp.trcpStatus'+(#index.count)}" name="tmp" value="��������"></ww:hidden>
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
								
							<tr>								
								<td><ww:property value="trpTraineeNo.empName" /></td>															
								<td><a href="#" onclick="showTrcpDetail('trcpDetail','<ww:property value="#index.count"/>');" class="listViewTdLinkS1"><ty:text counts="17"><ww:property value="trpTrcp.trcpCourseNo.trcName" /></ty:text></a></td>
								<td><ty:text counts="17"><ww:property value="trpTrcp.trcpLocation" /></ty:text></td>																
								<td><ww:date name="trpTrcp.trcpStartDate" format="yyyy-MM-dd" /></td>
								<td><ww:date name="trpTrcp.trcpEndDate" format="yyyy-MM-dd" /></td>	
								<td align="right"><ww:property value="trpTrcp.trcpBudgetHour" /></td>														
									<td onMouseOut="hideLog();">									
									<span style="font-size: 12px; color: #002780; text-decoration: underline; cursor: pointer" 
										 onClick="LogShowDiv('<ww:property value="trpId"/>', 'tremployeeplan',this);"><ww:property value="getTreStatus(trpStatus)" /></span></td>
																		
								   <td align="left">		   
								   <ww:if test="trpStatus==12">
								   <ty:auth auths="301 ">
								   <span onclick="redirectPrompt('trepCancel.action?trepId=<ww:property value="trpId"/>', '��ȷ��Ҫ���ϴ���ѵ������');"/>
											<img src="../resource/images/close_dashboard.gif" alt='����' border='0' /></span>
									<span onclick="showComment('comment', 'trepId','<ww:property value="trpId"/>','')"/>
											<img src="../resource/images/feedback.gif" alt='����' border='0' /></span>
									</ty:auth>
									</ww:if>	
									<ww:if test="trpFileName!=null">
								   <a href="#" onclick="window.location='downFile.action?fileName=<ww:property value="trpFileName"/>&type=\'feedback\'';"><img src="../resource/images/attachment.gif" alt='���ط�������'/></a>
								   </ww:if>	
								   <ww:if test="trpStatus==31">
								    <span onclick="showComment('comment', 'trepId','<ww:property value="trpId"/>','')"/>
											<img src="../resource/images/feedback.gif" alt='����' border='0' /></span>
								   
								   </ww:if>		
				</td>
			</tr>
			</ww:iterator>
		</ww:if>
		<ww:else>
			<tr>
				<!-- �����ڷ�����������ѵ���룡 -->
				<td align="center" colspan="12">
					�����ڷ�����������ѵ����!
				</td>
			</tr>
		</ww:else>
		</table>	
	</ww:form>
	<div id="comment" title="��ѵ����">
		<ww:form id="feedbackForm" action="feedback" method="post" enctype="multipart/form-data">
		<ww:token />
		<table border="0" cellspacing="2" cellpadding="0" class="prompt_div_body" width="100%">
				<tr>
					<td width="65" align="right">��ע��</td>
					<td>	
						<ww:hidden id="trepId" name="trepId"></ww:hidden>												
						<ww:textarea id="comments" name="comments" cols="45" rows="5" onkeypress="MKeyTextLength(this,128);"/>			
					</td>
				</tr>					
				<tr>
					<ww:file label="����" id="attachment" name="file" accept="text/doc,text/xls,text/txt"/>
				</tr>
				<tr>					
			    <td align="center" colspan="2"><input type="submit" value="�ύ" id="trepAddSubmitBtn">&nbsp;<input type="button" onclick="document.getElementById('comments').value='';hrm.common.closeDialog('comment');" value=" ȡ�� ">
			    </td>
			    </tr>
		</table>
		</ww:form>
	</div>
		<div id="trcpDetail" title="�γ̼ƻ���ϸ��Ϣ">
		<table borderColor="#b8c0c4" cellSpacing="0" cellPadding="0" width="100%" class="gridview">
			<tr>
				<input type="hidden" name="tmp" id="tdtrpTrcp.trcpId" />
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
				<td class="tablefield">��������:</td><td colspan="3" id="trpTrcp.trcpCourseNo.trcFileName"></td>
			</tr>											
			<tr>
			<td align="center" colspan="4"><input type="button" value="�ر�" id="divCloseBt" class="button" onclick="hrm.common.closeDialog('trcpDetail')"/>
			</td>										
			</tr>
		</table>
		</div>
									
	<script type="text/javascript">
		hrm.common.initDialog("trcpDetail",520);
		hrm.common.initDialog("comment",450);
	</script>
</body>
