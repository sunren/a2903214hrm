<%@ page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<%@ taglib prefix="ty" uri="/tengsource"%>
<html>
	<head>		
		<!-- css������Ϣ -->
		<link href="<s:url value="/resource/css/style.css"/>" rel="stylesheet" type="text/css" />	
		<jsp:include flush="true" page="/sitemesh/jsPackage.jsp"></jsp:include>
		<!-- �Լ���� -->
		<script type="text/javascript" src="../resource/js/hrm/examin.js"></script>
		<script type="text/javascript" src="../dwr/interface/MYLR.js"></script>
		<title>Ա������б�</title>
	</head>
	<body onload="hrm.common.check_order();">
		<jsp:include flush="true" page="/configuration/log.jsp"></jsp:include>		 
		<jsp:include flush="true" page="div_leaveshow.jsp"></jsp:include>         
		<jsp:include flush="true" page="div_examincoment.jsp"></jsp:include>
		<br/>
		<s:component template="bodyhead">
		</s:component>
		
		<form id="actionSrc" name="actionSrc" action="allLeaveSearch.action"  method="POST">
			<!--������Ϣ-->
		 	<s:hidden id="operation" name="page.operate" value="" />
		 	<s:hidden id="order" name="page.order"  />
		 	<s:hidden id="lrUpdateId" name="lrUpdateId" />
			<s:hidden id="lf_Bean.lrEmpNo.id" name="lf_Bean.lrEmpNo.id"/>
		 	<s:hidden id="srcAction" name="srcAction"></s:hidden>
		 	<s:hidden id="infoMeg" name="infoMeg"></s:hidden>
		 	<s:hidden id="isXiaojia" name="isXiaojia"></s:hidden>
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="formtable" id="tblSearchCondition">
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0" id="tblSearchCondition">
							<tr>
								<s:textfield label="Ա��" id="es_Bean_emp" name="es_Bean.emp" size="16" maxlength="64"></s:textfield>
								<s:textfield label="���" id="es_Bean_no" name="es_Bean.no" size="14" maxlength="14"></s:textfield>							                              
								<td align="right">��ʼ����:</td>
								<td>
								<s:textfield id="es_Bean_startDate" name="es_Bean.startDate" required="true" size="12" />
								<img onclick="WdatePicker({el:'es_Bean_startDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
								</td>
								<s:select label="�������" id="es_Bean_typeNo" name="es_Bean.typeNo" list="lrTypeList"  listKey="ltNo" listValue="ltName" emptyOption="true" />
								<td rowspan="2">				
									<input title="[Alt+F]" accesskey="F" name="submit_search" id="submit_search" class="button" type="submit" value="��ѯ">
									<input title="[Alt+A]" accesskey="A" name="submit_all" id="submit_all" class="button" type="button" onclick="window.location='allLeaveSearch.action'" value="����">
								</td>
							</tr>
							<tr>
								<td align="right">��֯��Ԫ:</td>
								<td>
								    <s:orgselector id="orgselector1" name="es_Bean.depNos" hiddenFieldName="es_Bean.depNo"/>
								</td>
								<s:textfield label="����" id="es_Bean_reason" name="es_Bean.reason" size="14" maxlength="128"></s:textfield>								 
								<td align="right">��������:</td>
								<td><s:textfield  id="es_Bean_endDate" name="es_Bean.endDate" required="true" size="12"  />
								<img onclick="WdatePicker({el:'es_Bean_endDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle"></td>								
								<s:select label="���״̬" id="es_Bean_statu" name="es_Bean.statu"  list="statusMap" listKey="key" listValue="value" emptyOption="true"/>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			<p align="left">&nbsp;</p>
			<table cellpadding="0" cellspacing="0" width="100%" border="0">
			    <tr><td align="right">���β�ѯ���õ�<s:property value="page.totalRows" />����ټ�¼&nbsp;</td></tr>
			</table>
			<table cellpadding="0" cellspacing="0" width="100%" border="0" class="gridtableList">
				<tr>
					<th>
						<a href="#" onclick="hrm.common.order_submit('id','actionSrc')">���<img src='../resource/images/arrow_.gif' width='8' height='10' id='id_img'></a>
					</th>
					<th>
						<a href="#" onclick="hrm.common.order_submit('emp','actionSrc')">Ա������<img src='../resource/images/arrow_.gif' width='8' height='10' id='emp_img'></a>
					</th>
					<th>
						<a href="#" onclick="hrm.common.order_submit('dep','actionSrc')">����<img src='../resource/images/arrow_.gif' width='8' height='10' id='dep_img'></a>
					</th>
					<th>
						<a href="#" onclick="hrm.common.order_submit('type','actionSrc')">����<img src='../resource/images/arrow_.gif' width='8' height='10' id='type_img'></a>
					</th>
					<th>
						<a href="#" onclick="hrm.common.order_submit('reason','actionSrc')">�������<img src='../resource/images/arrow_.gif' width='8' height='10' id='reason_img'></a>
					</th>
					<th>
						<a href="#" onclick="hrm.common.order_submit('startT','actionSrc')">��ʼʱ��<img src='../resource/images/arrow_.gif' width='8' height='10' id='startT_img'></a>
					</th>
					<th>
						<a href="#" onclick="hrm.common.order_submit('endT','actionSrc')">����ʱ��<img src='../resource/images/arrow_.gif' width='8' height='10' id='endT_img'></a>
					</th>
					<th>
						<a href="#" onclick="hrm.common.order_submit('total','actionSrc')">�ϼ�ʱ��<img src='../resource/images/arrow_.gif' width='8' height='10' id='total_img'></a>
					</th>
					<th> 
						<a href="#" onclick="hrm.common.order_submit('statu','actionSrc')">״̬<img src='../resource/images/arrow_.gif' width='8' height='10' id='statu_img'></a>
					</th>
					<th> 
						����
					</th>
				</tr>
				<s:if test="es_Bean!=null&&es_Bean.result!=null&&!es_Bean.result.isEmpty()">
					<s:iterator value="es_Bean.result" status="index">
						<tr>
							<td align="center">
								<strong>
									<span onclick="ShowLR('<s:property value='lrId'/>')" class="view">
										<s:property value="lrNo" />
									</span>
								</strong>
							</td>
							<td>
								<s:property value="lrEmpNo.empName" />
							</td>
							<td>
								<s:property value="lrEmpDept.departmentName" />
							</td>
							<td>
								<s:property value="lrLtNo.ltName" />
							</td>
							<td>
								<s:property value="lrReason" />
							</td>
							
							<!-- ��ʾ�����Сʱ���߼� -->
							<s:if test="applyLRByDay && lrStartApm != null" >
								<td>
								    <s:date name="lrStartDate" format="yyyy-MM-dd" />
								    <s:property value="@com.hr.examin.action.beans.Hours@getAM_PMDescription(lrStartApm)"/>
								</td>
								<td>
								    <s:date name="lrEndDate" format="yyyy-MM-dd" />
								    <s:property value="@com.hr.examin.action.beans.Hours@getAM_PMDescription(lrEndApm)"/>
								</td>
							</s:if><s:else>
								<td>
								    <s:date name="lrStartDate" format="yyyy-MM-dd HH:mm" />
							    </td>
							    <td>
								    <s:date name="lrEndDate" format="yyyy-MM-dd HH:mm" />
							    </td>
							</s:else>

						    <td>
								<s:if test="applyLRByDay" >
								    <s:property value="lrTotalDays"/>��
								</s:if><s:else>
								    <s:property value="@com.hr.util.MyTools@decimalFormat(lrTotalHours, 4, 1)"/>Сʱ
								</s:else>
						    </td>
							<!-- ��ʾ�����Сʱ���߼� -->
							
							<td onclick="LogShowDiv('<s:property value="lrId" />','leaverequest',this);" onMouseOut="hideLog('logTable');">
								<span class="view">
										<s:property value="lrStatusMean"/>
								</span>
							</td>
							<td align="center">
								<s:if test="lrStatus==@com.hr.base.Status@LR_STATUS_REJECT&&(lrEmpNo.id==#session.userinfo.employee.id||lrCreateBy.id==#session.userinfo.employee.id)">
										<a href="#" onclick="hrm.common.submitForm('actionSrc','LRUpdateInit.action','lrUpdateId','<s:property value='lrId'/>');"  >									 
											<img src="../resource/images/edit.gif" alt='�޸�' border='0' />
										</a>
								</s:if>
								<s:if test="lrStatus==@com.hr.base.Status@LR_STATUS_HR_APPROVE">
									<ty:auth auths="401,2 or 401,3">
										 <a href="#" onclick="hrm.common.submitForm('actionSrc','deptHrApproveLRInit.action','lrUpdateId','<s:property value='lrId'/>');" >
											<img src="../resource/images/reportle.gif" alt='����' border='0' />
										</a>
									</ty:auth>
								</s:if>
								<s:if test="lrStatus==@com.hr.base.Status@LR_STATUS_HR_APPROVE||lrStatus==@com.hr.base.Status@LR_STATUS_CONFIRM">
									<ty:auth auths="401,2 or 401,3">
										<a href="#" onclick="InputComments('<s:property value='lrId'/>','<s:property value='lrNo'/>','cancel','allLeaveSearch.action');" >
											<img src="../resource/images/cancelled.gif" alt='����' border='0' />
										</a>
									</ty:auth>
								</s:if>
								<s:if test="lrStatus==@com.hr.base.Status@LR_STATUS_REJECT&&(lrEmpNo.id==#session.userinfo.employee.id||lrCreateBy.id==#session.userinfo.employee.id)">
										<a href="#" onclick="hrm.examin.deletePlan('<s:property value='lrId'/>','<s:property value='lrNo'/>','leaverequest')" >
											<img src="../resource/images/delete.gif" alt='ɾ��' border='0' />
										</a>
								</s:if>
							&nbsp;
							</td>
						</tr>
					</s:iterator>
				</s:if>
				<s:else>
					<tr>
						<td align="center" colspan="11">�����ڷ���������������룡</td>
					</tr>
				</s:else>
			</table>
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="gridtableList">
	  			<tr class="listViewPaginationTdS1">
	 			  <td colspan="10" align="center">
	 			    <s:hidden id="page.currentPage" name="page.currentPage" />
	 			    <s:component template="pager">
	 			        <s:param name="pageNo" value="page.currentPage" /><s:param name="totalPages" value="page.totalPages" /><s:param name="totalRows" value="page.totalRows" />
	          	  	    <s:param name="start" value="page.start" /><s:param name="end" value="page.end" /><s:param name="formId" value="'actionSrc'" />
	          	  	</s:component>
				  </td>
	  			</tr>
			</table>
		</form>
<script type="text/javascript">
model.simple.setParentIFrameFull("IFrame1"); // add by ���Σ�����iframe�߶ȣ�    
hrm.common.initDialog('dlgLrShow');     
hrm.common.initDialog('comentinput');  
</script>
</body>
</html>