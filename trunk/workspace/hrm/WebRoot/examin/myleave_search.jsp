<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<html>
<head>
<!-- css修饰信息 -->
<link href="<s:url value="/resource/css/style.css"/>" rel="stylesheet" type="text/css" />	
<jsp:include flush="true" page="/sitemesh/jsPackage.jsp"></jsp:include>		
<!-- 自己添加 -->
<script type="text/javascript" src="../resource/js/hrm/examin.js"></script>
<script type="text/javascript" src="../dwr/interface/MYLR.js"></script>		 
<title>我的请假列表</title>
</head>
	<body onload="hrm.common.check_order();">
		<jsp:include flush="true" page="/configuration/log.jsp"></jsp:include>
		<jsp:include flush="true" page="div_leaveshow.jsp"></jsp:include>
		<br/>
		<s:component template="bodyhead">
		</s:component>
		
		<form id="actionSrc" name="actionSrc" action="myLeaveSearch.action"  method="POST">		
			<!--隐藏信息-->
		 	<s:hidden id="operation" name="page.operate" value="" />
		 	<s:hidden id="order" name="page.order" />
		 	<s:hidden id="lrUpdateId" name="lrUpdateId" />
		 	<s:hidden id="srcAction" name="srcAction"></s:hidden>
		 	<s:hidden id="infoMeg" name="infoMeg"  />
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="formtable">
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0" id="tblSearchCondition">
							<tr>
								<td><s:textfield label="编号" id="es_Bean_no" name="es_Bean.no" size="14" maxlength="14"></s:textfield></td>	
								<td align="right">开始日期:</td>		
								<td><s:textfield  id="es_Bean_startDate" name="es_Bean.startDate" required="true" size="12"  />
								  <img onclick="WdatePicker({el:'es_Bean_startDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22"  style="cursor:pointer" align="absmiddle">	
								</td>
								<td><s:select label="请假种类" id="es_Bean_typeNo" name="es_Bean.typeNo" list="lrTypeList"  listKey="ltNo" listValue="ltName" emptyOption="true" /></td>
							</tr>
							<tr>
								<td><s:textfield label="理由" id="es_Bean_reason" name="es_Bean.reason" size="14" maxlength="128"></s:textfield></td>
								<td align="right">结束日期:</td>
								<td><s:textfield id="es_Bean_endDate" name="es_Bean.endDate" required="true" size="12"  /> 
								 <img onclick="WdatePicker({el:'es_Bean_endDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle"></td>
								<td><s:select label="请假状态" id="es_Bean_statu" name="es_Bean.statu"  list="statusMap" listKey="key" listValue="value" emptyOption="true"/></td>
							</tr>
						</table>
					</td>
					<td rowspan="2">		
						<input title="[Alt+F]" accesskey="F" name="submit_search" id="submit_search" class="button" type="submit" value="查询" >
						<input title="[Alt+A]" accesskey="A" name="submit_all" id="submit_all" class="button" type="button" onclick="window.location='myLeaveSearch.action'" value="重置">
					</td>
				</tr>
			</table>
			<p align="left">&nbsp;</p>
			<table cellpadding="0" cellspacing="0" width="100%" border="0">
			    <tr>
			        <td align="left">
				        <input class="button"  type="button" onClick="window.location='empLRAddInit.action';" name="createRequest" value="新增请假">
				        <input class="button" type="button" id="showTotal" onClick="hrm.common.openDialog('dlgLeaveTotal',null,'年度请假汇总')" name="showTotal" value="年度请假汇总"/>
			        </td>
			        <td align="right" colspan="20">
			            	本次查询共得到<s:property value="page.totalRows" />条请假记录&nbsp;
			        </td>
			    </tr>
			</table>
			<table cellpadding="0" cellspacing="0" width="100%" border="0" class="gridtableList">
				<tr>
					<th>
						<a href="#" onclick="hrm.common.order_submit('id','actionSrc')">编号<img src='../resource/images/arrow_.gif' width='8' height='10' id='id_img'></a>
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
								<strong>
								<span onclick="ShowLR('<s:property value='lrId'/>')" class="view">
									<s:property value="lrNo" />
								</span>
								</strong>
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
							
							<td onclick="LogShowDiv('<s:property value="lrId" />','leaverequest',this);" onMouseOut="hideLog('logTable');">
								<span class="view">
		                         	<s:property value="lrStatusMean"/>
								</span>
							</td>
							<td align="center">
								<s:if test="lrStatus==@com.hr.base.Status@LR_STATUS_REJECT">
										<a href="#" onclick="hrm.common.submitForm('actionSrc','LRUpdateInit.action','lrUpdateId','<s:property value='lrId'/>');" >
											<img src="../resource/images/edit.gif" alt='修改' border='0' />
										</a>
								</s:if>
								<s:if test="lrStatus == @com.hr.base.Status@LR_STATUS_REJECT">
										<a href="#" onclick="hrm.examin.deletePlan('<s:property value='lrId'/>','<s:property value='lrNo'/>','leaverequest')" >
											<img src="../resource/images/delete.gif" alt='删除' border='0' />
										</a>
								</s:if>
								&nbsp;
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
		</form>
		
<div id="dlgLeaveTotal" style="display:none;width:600px">
	<table id="table3" cellSpacing="0" cellPadding="0" width="100%" class="gridview" id="logTable">
		<s:action id="leaveTotal" executeResult="true" ignoreContextParams="true" name="leaveTotalCalc" >
	    </s:action>
		<s:iterator value="leaveTotalList" status="rowstatus">
			<tr>
				<td class="tablefield" align="center">
					<s:property value="type"/>
				</td>
				<td align="center"><s:property value="totaldays"/>已用</td>
				<td align="center"><s:property value="toApproveDays"/>天待审</td>
			</tr>
		</s:iterator>
			<tr>
				<td align="center" colspan="5"><input type="button" value="关闭" class="button" onclick="hrm.common.closeDialog('dlgLeaveTotal');"/></td>										
			</tr>
	</table>
</div>
</body>
</html>
<script type="text/javascript">
	model.simple.setParentIFrameFull("IFrame1"); // add by 金鑫（计算iframe高度）  
	hrm.common.initDialog('dlgLrShow');  
	hrm.common.initDialog('dlgLeaveTotal');
</script>