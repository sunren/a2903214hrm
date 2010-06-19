<%@page pageEncoding="GBK" contentType="text/html; charset=GBK" %>
<%@taglib prefix="s" uri="webwork"%>
<%@taglib prefix="ty" uri="/tengsource"%>
<!--
 =========================================================
' File: my_empprofile.jsp
' Version:1.1.0 standard version
' Date: 2007-2-2
' Script Written by hr.com
'=========================================================
' Copyright   2007 hr.com. All rights reserved.
' Web: http://www.hr.com
' Email: admin@hr.com
'=======================================================
<log:warn message="..........................................."/>
<log:dump scope="request"></log:dump>
 -->
<head>
<link href="../resource/css/style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="../resource/css/edit_select.css" />
<jsp:include flush="true" page="../sitemesh/jsPackage.jsp"></jsp:include>
<script type="text/JavaScript" src="../resource/js/hrm/profile.js"></script>
<title>变动记录</title>
</head>
<body onload="hrm.common.check_order();" align="left">
	<s:component template="bodyhead"/>
	<form action="listTransfer.action" id="listForm" method="post">
		<div id="basic" style="DISPLAY: block;clear : both">
			<table width="100%" border="0" cellspacing="2" cellpadding="0" style="background-color: #ECF6FB; border: 1px #6BB5DA solid">
				<tr>
					<s:textfield id="employeeId" label="员工" name="employeeId" size="16" maxlength="64"/>
					<td align="right">变动前组织:</td>
					<td nowrap="nowrap">
					   <s:orgselector id="selector1" name="eftOldDeptName" hiddenFieldName="eftOldDeptNo" isShowDisable="show" />
					</td>
					<td align="right">变动后组织:</td>
					<td nowrap="nowrap">
					   <s:orgselector id="selector2" name="eftNewDeptName" hiddenFieldName="eftNewDeptNo" isShowDisable="show" />
					</td>
					<td rowspan="2">
						<input title="[Alt+F]" accesskey="F" name="submit_search" id="submit_search" class="button" type="submit" value="查询">
						<input title="[Alt+A]" accesskey="A" name="submit_all" id="submit_all" class="button" type="reset" value="重置" onclick="window.location='listTransfer.action'">
					</td>
				</tr>
				<tr>
					<s:select label="变动种类" name="eftTransferType" list="#{'':'请选择','0':'平调','1':'晋升',2:'降级',3:'转岗',4:'转正'}"/>
					<s:textfield id="eftReason" label="变动原因" name="eftReason" size="22" maxlength="128"/>
				 	<td align="right">变动日期:</td>
					<td>
						<s:textfield id="eftTransferDate" name="eftTransferDate" size="10"/>
						<img onclick="WdatePicker({el:'eftTransferDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
						到:
						<s:textfield id="eftTransferDateTo" name="eftTransferDateTo" size="10"/>
						<img onclick="WdatePicker({el:'eftTransfer DateTo'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
					</td>
				</tr>
			</table>
		</div>
		<table width="100%">
			<tr><br /></tr>
			<tr>
				<td>
					<ty:auth auths="101,2 or 101,3">
						<input type="hidden" name="searchOrExport" id="searchOrExport" />
						<s:hidden id="output-ommId" name="outmatchModelId"/>
						<s:hidden id="output-ioName" name="outputIoName"/>
						<input class="button" type="button" onclick="initDivImmUpload('IEmptransfer');" value="数据导入"/>
						<input class="button" id="btnOutput" type="button" value="数据导出" onclick="export_check('export');"/>
					</ty:auth>
				</td>
				<td align="right">本次查询共得到<s:property value="page.totalRows" />条变动记录&nbsp;</td>
			</tr>
		</table>
	<table id="profiletable" cellpadding="0" cellspacing="0" width="100%" border="0" class="gridtableList">
		<s:hidden id="order" name="page.order" />
		<s:hidden id="operate" name="page.operate" />
		<tr>
			<th nowrap="nowrap">
		     	<a href="#" onclick="hrm.common.order_submit('emp.empDistinctNo', 'listForm');">员工编号</a><img
		     	 src='../resource/images/arrow_.gif' width='8' height='10' id='emp.empDistinctNo_img'>
	     	</th>
	     	<th nowrap="nowrap">
		     	<a href="#" onclick="hrm.common.order_submit('emp.empName', 'listForm');">员工姓名</a><img
		     	 src='../resource/images/arrow_.gif' width='8' height='10' id='emp.empName_img'>
	     	</th>
	     	<th nowrap="nowrap">
		     	<a href="#" onclick="hrm.common.order_submit('eftTransferDate', 'listForm');">变动日期</a><img
		     	 src='../resource/images/arrow_.gif' width='8' height='10' id='eftTransferDate_img'>
	     	</th>
	     	<th nowrap="nowrap">
		     	<a href="#" onclick="hrm.common.order_submit('eftTransferType', 'listForm');">变动种类</a><img
		     	 src='../resource/images/arrow_.gif' width='8' height='10' id='eftTransferType_img'>
	     	</th>
	     	<th nowrap="nowrap">
		     	<a href="#" onclick="hrm.common.order_submit('oldDepartment.departmentName', 'listForm');">变动前部门</a><img
		     	 src='../resource/images/arrow_.gif' width='8' height='10' id='oldDepartment.departmentName_img'>
	     	</th>
	     	<th nowrap="nowrap">
		     	<a href="#" onclick="hrm.common.order_submit('oldPbNo.pbName', 'listForm');">变动前职位</a><img
		     	 src='../resource/images/arrow_.gif' width='8' height='10' id='oldPbNo.pbName_img'>
	     	</th>
	     	<th nowrap="nowrap">
		     	<a href="#" onclick="hrm.common.order_submit('newDepartment.departmentName', 'listForm');">变动后部门</a><img
		     	 src='../resource/images/arrow_.gif' width='8' height='10' id='newDepartment.departmentName_img'>
	     	</th>
	     	<th nowrap="nowrap">
		     	<a href="#" onclick="hrm.common.order_submit('newPbNo.pbName', 'listForm');">变动后职位</a><img
		     	 src='../resource/images/arrow_.gif' width='8' height='10' id='newPbNo.pbName_img'>
	     	</th>
	     	<th nowrap="nowrap">
		     	<a href="#" onclick="hrm.common.order_submit('eftReason', 'listForm');">变动原因</a><img
		     	 src='../resource/images/arrow_.gif' width='8' height='10' id='eftReason_img'>
	     	</th>
	     	<th nowrap="nowrap">
		     	<a href="#" onclick="hrm.common.order_submit('eftComments', 'listForm');">备注</a><img
		     	 src='../resource/images/arrow_.gif' width='8' height='10' id='eftComments_img'>
	     	</th>
		</tr>
		<tbody>
			<s:if test="!transferList.isEmpty()">
			<s:iterator value="transferList" status="transferStatus">
				<tr id="<s:property value='eftId'/>">
					<td nowrap="nowrap"><s:property value="employee.empDistinctNo"/></td>
					<td nowrap="nowrap"><s:property value="employee.empName"/></td>
					<td nowrap="nowrap"><s:property value="eftTransferDate"/></td>
					<td nowrap="nowrap"><input type="hidden" id="type<s:property value='eftId'/>" value="<s:property value='eftTransferType'/>"/>
						<s:if test="eftTransferType==0">平调</s:if>
						<s:if test="eftTransferType==1">晋级</s:if>
						<s:if test="eftTransfedType==2">降级</s:if>
						<s:if test="eftTransferType==3">转岗</s:if>
						<s:if test="eftTransferType==4">转正</s:if>
					</td>
					<td nowrap="nowrap"><input type="hidden" id="deptOld<s:property value='eftId'/>" value="<s:property value='eftOldDeptNo.id'/>"/><s:property value="eftOldDeptNo.departmentName"/></td>
					<td nowrap="nowrap"><s:property value="eftOldPbId.pbName"/></td>
					<td nowrap="nowrap"><input type="hidden" id="deptNew<s:property value='eftId'/>" value="<s:property value='eftNewDeptNo.id'/>"/><s:property value="eftNewDeptNo.departmentName"/></td>
					<td nowrap="nowrap"><s:property value="eftNewPbId.pbName"/></td>
					<td><s:property value="eftReason"/></td>
					<td><s:property value="eftComments"/></td>
				</tr>
			</s:iterator>
			</s:if>
			<s:else>
				<tr>
					<td colspan="10" align="center">无相关变动记录！</td>
				</tr>
			</s:else>
		</tbody>
	</table>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="gridtableList">
		<tr class="listViewPaginationTdS1">
			<td colspan="10" align="center">
				<s:hidden id="page.currentPage" name="page.currentPage" />
				<s:component template="pager">
					<s:param name="pageNo" value="page.currentPage" /><s:param name="totalPages" value="page.totalPages" /><s:param name="totalRows" value="page.totalRows" />
					<s:param name="start" value="page.start" /><s:param name="end" value="page.end" /><s:param name="formId" value="'listForm'" />
				</s:component>
			</td>
		</tr>
	</table>
</form>
<jsp:include flush="true" page="../io/div_omm_select.jsp"></jsp:include>
<script type="text/javascript">
//导出
function export_check(str){
	document.getElementById('searchOrExport').value=str;
	document.getElementById('listForm').submit();
	document.getElementById('searchOrExport').value='';
	return;
}
model.simple.setParentIFrameFull("IFrame1"); // add by 金鑫（计算iframe高度）
</script>
<jsp:include flush="true" page="../io/div_upload.jsp"></jsp:include>
</body>