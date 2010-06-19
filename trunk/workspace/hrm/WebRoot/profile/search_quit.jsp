<%@page pageEncoding="GBK" contentType="text/html; charset=GBK" %>
<%@taglib prefix="s" uri="webwork"%>
<%@taglib prefix="ty" uri="/tengsource"%>
<!--
 =========================================================
' File: my_empprofile.jsp
' Version:1.1.0 standard version
' Date: 2007-2-2
' Script Written by tengsource.com
'=========================================================
' Copyright   2007 tengsource.com. All rights reserved.
' Web: http://www.tengsource.com
' Email: admin@tengsource.com
'=======================================================
 -->
<head>
<link href="../resource/css/style.css" rel="stylesheet" type="text/css" />
<jsp:include flush="true" page="../sitemesh/jsPackage.jsp"></jsp:include>
<script type="text/JavaScript" src="../resource/js/hrm/profile.js"></script>
<title>离职记录</title>
</head>
<body onload="hrm.common.check_order();" align="left">
<s:component template="bodyhead"/>
<ty:auth auths="101">
	<s:set name="hasAuth" value="'has'"/>
</ty:auth>
<form action="listQuit.action" id="listForm" method="post">
	<div id="basic" style="DISPLAY: block;clear : both">
		<table width="100%" border="0" cellspacing="2" cellpadding="0" style="background-color: #ECF6FB; border: 1px #6BB5DA solid">
			<tr>
				<s:textfield id="employeeId" label="员工" name="employeeId" size="16" maxlength="64"/>
				<td align="right">组织单元:</td>
				<td>
				     <s:orgselector id="orgselector1" name="departmentName" hiddenFieldName="departmentId" isShowDisable="show"/>
				</td>
				<td align="right">离职种类:</td>
				<td>
					<s:select name="eqType" id="eqType" list="eqTypeMap"
						onchange="model.simple.changeSubSelect(this,'eqReason','eqTypeSubMap')">
					</s:select>
					&nbsp;&nbsp;原因:
					<select name="eqReason" id="eqReason"></select>
				</td>
				<td rowspan="2">
					<input title="[Alt+F]" accesskey="F" name="submit_search" id="submit_search" class="button" type="submit" value="查询">
					<input title="[Alt+A]" accesskey="A" name="submit_all" id="submit_all" class="button" type="reset" value="重置"
						onclick="window.location='listQuit.action'">
				</td>
			</tr>
			<tr>
				<s:textfield id="eqPermission" label="审批人" name="eqPermission" size="16" maxlength="64"/>
				<s:textfield id="eqComments" label="备注" name="eqComments" size="16" maxlength="64"/>
				<td align="right">离职日期:</td>
				<td>
					<s:textfield id="eqDate" name="eqDate" size="10"/>
					<img onclick="WdatePicker({el:'eqDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
					到:
					<s:textfield id="eqDateTo" name="eqDateTo" size="10"/>
					<img onclick="WdatePicker({el:'eqDateTo'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
				</td>
			</tr>
		</table>
	</div>
	<table width="100%">
		<tr><br /></tr>
		<tr>
			<td>
				<input type="hidden" name="searchOrExport" id="searchOrExport" />
				<ty:auth auths="101,2 or 101,3">
					<s:hidden id="output-ommId" name="outmatchModelId"/>
					<s:hidden id="output-ioName" name="outputIoName"/>
					<input class="button" type="button" id="btnOutput" value="数据导出" onClick="export_check('export');"/>
				</ty:auth>
			</td>
			<td align="right">本次查询共得到<s:property value="page.totalRows" />条离职记录&nbsp;</td>
		</tr>
	</table>
	<table cellpadding="0" cellspacing="0" width="100%" border="0" class="gridtableList">
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
		     	<a href="#" onclick="hrm.common.order_submit('department.departmentName', 'listForm');">部门</a><img
		     	 src='../resource/images/arrow_.gif' width='8' height='10' id='department.departmentName_img'>
	     	</th>
	     	<th nowrap="nowrap">
		     	<a href="#" onclick="hrm.common.order_submit('eqDate', 'listForm');">离职日期</a><img
		     	 src='../resource/images/arrow_.gif' width='8' height='10' id='eqDate_img'>
	     	</th>
	     	<th nowrap="nowrap">
		     	<a href="#" onclick="hrm.common.order_submit('manager.empName', 'listForm');">审批人</a><img
		     	 src='../resource/images/arrow_.gif' width='8' height='10' id='manager.empName_img'>
	     	</th>
	     	<th nowrap="nowrap">
		     	<a href="#" onclick="hrm.common.order_submit('eqType', 'listForm');">种类</a><img
		     	 src='../resource/images/arrow_.gif' width='8' height='10' id='eqType_img'>
	     	</th>
	     	<th nowrap="nowrap">
		     	<a href="#" onclick="hrm.common.order_submit('eqReason', 'listForm');">离职原因</a><img
		     	 src='../resource/images/arrow_.gif' width='8' height='10' id='eqReason_img'>
	     	</th>
	     	<th nowrap="nowrap">
		     	<a href="#" onclick="hrm.common.order_submit('erComments', 'listForm');">备注</a><img
		     	 src='../resource/images/arrow_.gif' width='8' height='10' id='erComments_img'>
	     	</th>
	     	<th nowrap="nowrap"></th>
		</tr>
   		<tbody>
   			<s:if test="!quitList.isEmpty()">
				<s:iterator value="quitList" status="quitStatus">
		   			<tr id="<s:property value='eqId'/>">
		   				<td nowrap="nowrap"><s:property value="employee.empDistinctNo"/></td>
		   				<td nowrap="nowrap"><s:property value="employee.empName"/></td>
		   				<td nowrap="nowrap"><s:property value="employee.empDeptNo.departmentName"/></td>
		   				<td nowrap="nowrap"><s:property value="eqDate"/></td>
		   				<td nowrap="nowrap">
		   					<input type="hidden" id="perm<s:property value="eqId"/>" value="<s:property value="eqPermission.id"/>"/>
		   					<s:property value="eqPermission.empName"/>
		   				</td>
		   				<td nowrap="nowrap">
			   				<s:if test="eqType==0">辞职</s:if>
			   				<s:if test="eqType==1">复职</s:if>
			   				<s:if test="eqType==2">停薪留职</s:if>
		   					<s:if test="eqType==3">退休</s:if>
		   					<s:if test="eqType==4">辞退</s:if>
		   					<s:if test="eqType==5">合同到期</s:if>
		   					<s:if test="eqType==6">其他</s:if>
		   				</td>
		   				<td nowrap="nowrap"><s:property value="eqReason"/></td>
		   				<td><s:property value="erComments"/></td>
		   				<td>
		   					<input type="hidden" id="empId<s:property value="eqId"/>" value="<s:property value="employee.id"/>"/>
		   				</td>
		   			</tr>
	   			</s:iterator>
   			</s:if>
   			<s:else>
   				<tr>
   					<td colspan="8" align="center">无相关离职记录！</td>
   				</tr>
   			</s:else>
   		</tbody>
	</table>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="gridtableList">
		<tr class="listViewPaginationTdS1">
			<td colspan="10" align="center">
				<s:hidden id="page.currentPage" name="page.currentPage" />
				<s:component template="pager">
					<s:param name="pageNo" value="page.currentPage" />
					<s:param name="totalPages" value="page.totalPages" />
					<s:param name="totalRows" value="page.totalRows" />
					<s:param name="start" value="page.start" />
					<s:param name="end" value="page.end" />
					<s:param name="formId" value="'listForm'" />
				</s:component>
			</td>
		</tr>
	</table>
	</form>
<jsp:include flush="true" page="../io/div_omm_select.jsp"></jsp:include>
<script type="text/javascript">
eqTypeSubMap={};
<s:iterator value="eqReasonMap" id="topEntrySet">
	eqTypeSubMap['<s:property value="#topEntrySet.key"/>']={};
	<s:iterator value="#topEntrySet.value" status="index" id="secEntrySet">
		<s:if test="#secEntrySet.key.length()==0">
			eqTypeSubMap['<s:property value="#topEntrySet.key"/>']['']='<s:property value="#secEntrySet.value"/>';
		</s:if><s:else>
			eqTypeSubMap['<s:property value="#topEntrySet.key"/>']['<s:property value="#secEntrySet.value"/>']='<s:property value="#secEntrySet.value"/>';
		</s:else>
	</s:iterator>
</s:iterator>
model.simple.changeSubSelect('eqType','eqReason','eqTypeSubMap','<s:property value="eqReason"/>');
//导出
function export_check(str){
	document.getElementById('searchOrExport').value=str;
	document.getElementById('listForm').submit();
	document.getElementById('searchOrExport').value='';
	return;
}
model.simple.setParentIFrameFull("IFrame1"); // add by 金鑫（计算iframe高度）
</script>
</body>	