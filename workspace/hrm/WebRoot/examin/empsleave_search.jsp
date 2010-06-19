<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<%@taglib prefix="ty" uri="/tengsource"%>
<html>
	<head>		
		<!-- css修饰信息 -->
		<link href="<s:url value="/resource/css/style.css"/>" rel="stylesheet" type="text/css" />	
		<jsp:include flush="true" page="/sitemesh/jsPackage.jsp"></jsp:include>
		<!-- 自己添加 -->
		<script type="text/javascript" src="../resource/js/hrm/examin.js"></script>
		<script type="text/javascript" src="../dwr/interface/MYLR.js"></script>				
		<title>员工请假列表</title>
</head>
	<body onload="hrm.common.check_order();">
	    <jsp:include flush="true" page="dlg_leaveorovertime_examin.jsp"></jsp:include>	
	   	<jsp:include flush="true" page="/configuration/log.jsp"></jsp:include>
		<jsp:include flush="true" page="div_leaveshow.jsp"></jsp:include>
		<jsp:include flush="true" page="div_examincoment.jsp"></jsp:include>
		<br/>
		<s:component template="bodyhead">
	    </s:component>
		
		<!-- 员工请假记录的HR备案 -->		
		<form id="actionSrc" name="actionSrc" action="<s:property value='actionSrc'/>"  method="POST">		
			<!--隐藏信息-->
		 	<s:hidden id="operation" name="page.operate" value=""/>
		 	<s:hidden id="order" name="page.order" />
		 	<s:hidden id="lrUpdateId" name="lrUpdateId" />	
		 	<s:hidden id="infoMeg" name="infoMeg"  />
		 	<s:hidden id="srcAction" name="srcAction"></s:hidden>
		 	<s:hidden id="approveOper" name="approveOper" value=""/>
		 	<s:hidden id="lf_Bean_lrAppComment" name="lf_Bean.lrAppComment" value=""/>
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="formtable">
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0" id="tblSearchCondition">
							<tr>
								<s:textfield label="员工" id="es_Bean.emp" name="es_Bean.emp" size="16" maxlength="64"></s:textfield>
								<s:textfield label="编号" id="es_Bean.no" name="es_Bean.no" size="14" maxlength="14"></s:textfield>							 
								<td align="right">开始日期:</td>
								<td><s:textfield  id="es_Bean_startDate" name="es_Bean.startDate" required="true" size="12" />								
								<img onclick="WdatePicker({el:'es_Bean_startDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle"></td>
								<s:select label="请假种类" id="es_Bean.typeNo" name="es_Bean.typeNo" list="lrTypeList"  listKey="ltNo" listValue="ltName" emptyOption="true" />
							</tr>
							<tr>
								<td align="right">组织单元:</td>
								<td>
								    <s:orgselector id="orgselector1" name="es_Bean.depNos" hiddenFieldName="es_Bean.depNo"/>
								</td>
								<s:textfield label="理由" id="es_Bean.reason" name="es_Bean.reason" size="14" maxlength="128"></s:textfield>
 								<td align="right">结束日期:</td>
								<td><s:textfield  id="es_Bean_endDate" name="es_Bean.endDate" required="true" size="12"  />
								<img onclick="WdatePicker({el:'es_Bean_endDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle"></td>	
							</tr>
						</table>
					</td>
					<td rowspan="2">				
						<input title="[Alt+F]" accesskey="F" name="submit_search" id="submit_search" class="button" type="submit" value="查询">
						<input title="[Alt+A]" accesskey="A" name="submit_all" id="submit_all" class="button" type="button" onclick="window.location='hrLeaveSearch.action'" value="重置">
						<br> 
					</td>
				</tr>
			</table>
			<p align="left">&nbsp;</p> 
			<s:if test="es_Bean!=null&&es_Bean.result!=null&&!es_Bean.result.isEmpty()">
				<table cellpadding="0" cellspacing="0" width="100%" border="0">
					<tr>
						<td rowspan="2">				
						    <input id="batchButton_a"  name="batchButton_a" class="button"   type="button"   onclick="hrm.examin.showRejectDiv('approve','logComents_r');" value="批准">
						    <input id="batchButton_r"  name="batchButton_r" class="button"   type="button"   onclick="hrm.examin.showRejectDiv('reject','logComents_r');" value="拒绝">
                        </td>
                        <td align="right">
						    本次查询共得到<s:property value="page.totalRows" />条请假记录&nbsp;
						</td>
					</tr>
				</table>
			</s:if>
			<s:else>
			    <table cellpadding="0" cellspacing="0" width="100%" border="0">
			        <tr><td align="right">
						    本次查询共得到<s:property value="page.totalRows" />条请假记录&nbsp;
						</td>
				    </tr>
				</table>
			</s:else>
			<table cellpadding="0" cellspacing="0" width="100%" border="0" class="gridtableList">
				<tr>
					<th>
						<input id="changIds" name='changIds' class="checkbox" type="checkbox" onclick="hrm.common.checkAllByName('changIds','changIds');" value="选中">
					</th>
					<th>
						<a href="#" onclick="hrm.common.order_submit('id','actionSrc')">编号<img src='../resource/images/arrow_.gif' width='8' height='10' id='id_img'></a>
					</th>
					<th>
						<a href="#" onclick="hrm.common.order_submit('emp','actionSrc')">员工姓名<img src='../resource/images/arrow_.gif' width='8' height='10' id='emp_img'></a>
					</th>
					<th>
						<a href="#" onclick="hrm.common.order_submit('dep','actionSrc')">部门<img src='../resource/images/arrow_.gif' width='8' height='10' id='dep_img'></a>
					</th>
					<th>
						<a href="#" onclick="hrm.common.order_submit('type','actionSrc')">种类<img src='../resource/images/arrow_.gif' width='8' height='10' id='type_img'></a>
					</th>
					<th>
						<a href="#" onclick="hrm.common.order_submit('reason','actionSrc')">请假理由<img src='../resource/images/arrow_.gif' width='8' height='10' id='reason_img'></a>
					</th>
					<th>
						<a href="#" onclick="hrm.common.order_submit('startT','actionSrc')">开始时间<img src='../resource/images/arrow_.gif' width='8' height='10' id='startT_img'></a>
					</th>
					<th>
						<a href="#" onclick="hrm.common.order_submit('endT','actionSrc')">结束时间<img src='../resource/images/arrow_.gif' width='8' height='10' id='endT_img'></a>
					</th>
					<th>
						<a href="#" onclick="hrm.common.order_submit('total','actionSrc')">合计时间<img src='../resource/images/arrow_.gif' width='8' height='10' id='total_img'></a>
					</th>
					<th> 
						<a href="#" onclick="hrm.common.order_submit('statu','actionSrc')">状态<img src='../resource/images/arrow_.gif' width='8' height='10' id='statu_img'></a>
					</th>
					<th> 
						操作
					</th>
				</tr>
				<s:if test="es_Bean!=null&&es_Bean.result!=null&&!es_Bean.result.isEmpty()">
					
					<s:iterator value="es_Bean.result" status="index">
						<tr>
							<td align="center">
								<s:if test="lrStatus<@com.hr.base.Status@LR_STATUS_HR_APPROVE">
									<s:if test="lrNextApprover==null || lrNextApprover==''">
									    <input id="changIds" type="checkbox" name='changIds' class="checkbox" value="<s:property value='lrId'/>" />
									</s:if>
								</s:if>
								<s:else>
									&nbsp;
								</s:else>
							</td>
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
							
							<!-- 显示天或是小时的逻辑 -->
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
								    <s:property value="lrTotalDays"/>天
								</s:if><s:else>
								    <s:property value="@com.hr.util.MyTools@decimalFormat(lrTotalHours, 4, 1)"/>小时
								</s:else>
						    </td>
							<!-- 显示天或是小时的逻辑 -->
							 
							<td  onclick="LogShowDiv('<s:property value="lrId" />','leaverequest',this);" onMouseOut="hideLog('logTable');">
								<span class="view">
									<s:property value="lrStatusMean"/>
								</span>
							</td>
							<td align="center" nowrap="nowrap">
								<a href="#" onclick="hrm.examin.setId('<s:property value='lrId'/>');hrm.examin.showRejectDiv('approve','logComents_r');" >
									<img src="../resource/images/MgrApprove.gif" alt='批准' border='0' />
								</a>
								<a href="#" onclick="hrm.examin.setId('<s:property value='lrId'/>');hrm.examin.showRejectDiv('reject','logComents_r');" >
									<img src="../resource/images/MgrReject.gif" alt='拒绝' border='0' />
								</a>
							</td>
						</tr>
					</s:iterator>
				</s:if>
				<s:else>
					<tr>
						<td align="center" colspan="11">不存在符合条件的请假单！</td>
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
			<s:if test="es_Bean!=null&&es_Bean.result!=null&&!es_Bean.result.isEmpty()">
				<table cellpadding="0" cellspacing="0" width="100%" border="0">
					<tr>
						<td>
						    <input id="batchButton_a"  name="batchButton_a" class="button"   type="button"   onclick="hrm.examin.showRejectDiv('approve','logComents_r');" value="批准">
						    <input id="batchButton_r"  name="batchButton_r" class="button"   type="button"   onclick="hrm.examin.showRejectDiv('reject','logComents_r');" value="拒绝">
                        </td>
					</tr>
				</table>
			</s:if>
		</form>
	</body>
</html>
 
<script type="text/javascript">  
model.simple.setParentIFrameFull("IFrame1"); // add by 金鑫（计算iframe高度） 
hrm.common.initDialog('dlgLrShow');

//审批动作公用方法
function batchReject(){
   commonbatch('approveOper','logComents_r','lf_Bean_lrAppComment','actionSrc','hrLrApproveOrReject.action')
}
</script>
