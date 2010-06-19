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
<script type="text/javascript" src="../dwr/interface/MYOT.js"></script>		
<title>员工加班列表</title>		
</head>
	<body onload="hrm.common.check_order();">
	    <jsp:include flush="true" page="dlg_leaveorovertime_examin.jsp"></jsp:include>	
		<jsp:include flush="true" page="/configuration/log.jsp"></jsp:include>
		<jsp:include flush="true" page="div_overtimeshow.jsp"></jsp:include>
		<jsp:include flush="true" page="div_examincoment.jsp"></jsp:include>		
		<br/>	
		<s:component template="bodyhead">
		</s:component>
		
		<form id="actionSrc" name="actionSrc" action="<s:property value='actionSrc'/>"  method="POST">
		
			<!--隐藏信息-->
			<s:hidden id="srcAction" name="srcAction"/>
		 	<s:hidden id="operation" name="page.operate" value=""/>
		 	<s:hidden id="order" name="page.order" />
		 	<s:hidden id="infoMeg" name="infoMeg" value="" />
		 	<s:hidden id="orIdUp" name="orIdUp"/>
		 	<input type="hidden" name="gmanager" value="<s:property value="gmanager"/>"/>
		 	<s:hidden id="approveOper" name="approveOper" value=""/>
		 	<s:hidden id="of_Bean_orAppComment" name="of_Bean.orAppComment" value=""/>
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
								<s:select label="加班种类" id="es_Bean.typeNo" name="es_Bean.typeNo" list="otTypeList"  listKey="otNo" listValue="otName" emptyOption="true" />
							</tr>
							<tr>
								<td align="right">组织单元:</td>
								<td>
								    <s:orgselector id="orgselector1" name="es_Bean.depNos" hiddenFieldName="es_Bean.depNo"/>
								</td>
								<s:textfield label="理由" id="es_Bean.reason" name="es_Bean.reason" size="14" maxlength="128"></s:textfield>
								<td align="right">结束日期:</td>
								<td><s:textfield  id="es_Bean_endDate" name="es_Bean.endDate" required="true" size="12"   />
								<img onclick="WdatePicker({el:'es_Bean_endDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle"></td>
							</tr>
						</table>
						</td>
						<td rowspan="2">				
							<input title="[Alt+F]" accesskey="F" name="submit_search" id="submit_search" class="button" type="submit" value="查询">
							<input title="[Alt+A]" accesskey="A" name="submit_all" id="submit_all" class="button" type="button"  onclick="window.location='deptOvertimeSearch.action'" value="重置">
					</td>
				</tr>
			</table>
			<p align="left">&nbsp;</p> 
			
			<s:if test="es_Bean!=null&&es_Bean.result!=null&&!es_Bean.result.isEmpty()">
				<table cellpadding="0" cellspacing="0" width="100%" border="0">
					<tr>
						<td><input id="batchButton_a"  name="batchButton_a" class="button"   type="button"   onclick="hrm.examin.showRejectDiv('approve','logComents_r');" value="批准">
						<input id="batchButton_r"  name="batchButton_r" class="button"   type="button"   onclick="hrm.examin.showRejectDiv('reject','logComents_r')  " value="拒绝"></td>
					    <td align="right">
						    本次查询共得到<s:property value="page.totalRows" />条加班记录&nbsp;
						</td>
					</tr>
				</table>
			</s:if>
			<s:else>
			    <table cellpadding="0" cellspacing="0" width="100%" border="0">
			        <tr><td align="right">
						    本次查询共得到<s:property value="page.totalRows" />条加班记录&nbsp;
						</td>
				    </tr>
				</table>
			</s:else>
			<table cellpadding="0" cellspacing="0" width="100%" border="0" class="gridtableList">
				<tr>
					<th>
						<input id="changIds" name='changIds' class="checkbox" type="checkbox" onclick="hrm.common.checkAllByName('changIds','changIds');" value="选中草稿">
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
						<a href="#" onclick="hrm.common.order_submit('reason','actionSrc')">加班理由<img src='../resource/images/arrow_.gif' width='8' height='10' id='reason_img'></a>
					</th>
					<th>
						<a href="#" onclick="hrm.common.order_submit('startT','actionSrc')">加班时间<img src='../resource/images/arrow_.gif' width='8' height='10' id='startT_img'></a>
					</th>
					<th>
						<a href="#" onclick="hrm.common.order_submit('total','actionSrc')">小时数<img src='../resource/images/arrow_.gif' width='8' height='10' id='total_img'></a>
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
								<input id="changIds" type="checkbox" name="changIds" class="checkbox" value="<s:property value='orId'/>" />
							</td>
							<td align="center">
								<strong>
								<span onclick="ShowOT('<s:property value='orId'/>')" class="view">
									<s:property value="orNo" />
								</span>
								</strong>
							</td>
							<td>
								<s:property value="orEmpNo.empName" />
							</td>
							<td>
								<s:property value="orEmpDept.departmentName" />
							</td>
							<td>
								<s:property value="orOtNo.otName" />
							</td>
							<td>
								<s:property value="orReason" />
							</td>
							<td>
								<s:date name="orStartDate" format="yyyy-MM-dd HH:mm" />—<s:property value = "get24Point(orEndDate)"/>
								<!-- <s:date name="orEndDate" format="HH:mm" /> -->
							</td>
							<td>
								<s:property value="@com.hr.util.MyTools@decimalFormat(orTotalHours, 4, 1)"/>
							</td>
							<td onclick="LogShowDiv('<s:property value="orId" />','overtimerequest',this);" onMouseOut="hideLog('logTable');">
								<span class="view">
									<s:property value="orStatusMean"/>
								</span>	
							</td>
							<td align="left" nowrap="nowrap">
								<a href="#" onclick="hrm.examin.setId('<s:property value='orId'/>');hrm.examin.showRejectDiv('approve');" >
									<img src="../resource/images/MgrApprove.gif" alt='审批通过' border='0' />
								</a>
								<a href="#" onclick="hrm.examin.setId('<s:property value='orId'/>');hrm.examin.showRejectDiv('reject');" >
									<img src="../resource/images/MgrReject.gif" alt='拒绝' border='0' />
								</a>
								<s:set name="author" value="@com.hr.util.DatabaseSysConfigManager@getInstance()"/>
								<s:if test="#request.author.getProperty('sys.examin.update.level')==1">
								<a href="#" onclick="hrm.common.submitForm('actionSrc','showOTApprove_M.action','orIdUp','<s:property value='orId'/>');" >
								<!--全部调用公共方法
								showDeptApproveOT('<s:property value='orId'/>','actionSrc');" 								
								document.getElementById("orIdUp").value=orId;
							    document.getElementById(formId).action="showOTApprove_M.action";
							    document.getElementById(formId).submit();
								-->
									<img src="../resource/images/edit.gif" alt='修改' border='0' />
								</a>
								</s:if>
								<!--<a href="#" onclick="deletePlan('<s:property value='orId'/>','<s:property value='orNo'/>','overtime');" >
									<img src="../resource/images/delete.gif" alt='删除' border='0' />
								</a>-->
							</td>
						</tr>
					</s:iterator>
				</s:if>
				<s:else>
					<tr>
						<td align="center" colspan="11">不存在符合条件的加班单！</td>
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
						<td><input id="batchButton_a"  name="batchButton_a" class="button"   type="button"   onclick="hrm.examin.showRejectDiv('approve','logComents_r');" value="批准">
						<input id="batchButton_r"  name="batchButton_r" class="button"   type="button"   onclick="hrm.examin.showRejectDiv('reject','logComents_r')" value="拒绝"></td>
					</tr>
				</table>
			</s:if>
		</form>
	</body>
</html>
 
<script type="text/javascript"> 
model.simple.setParentIFrameFull("IFrame1"); // add by 金鑫（计算iframe高度）

//审批动作公用方法(参数分别为同意或拒接，备注信息Id，接受备注信息Id，表单Id，Action名)
function batchReject(){   
   commonbatch('approveOper','logComents_r','of_Bean_orAppComment','actionSrc','deptOtApproveOrReject.action')
}
    
hrm.common.initDialog('dlgOtShow');
</script>