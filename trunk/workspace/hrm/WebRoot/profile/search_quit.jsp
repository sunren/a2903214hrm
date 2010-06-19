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
<title>��ְ��¼</title>
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
				<s:textfield id="employeeId" label="Ա��" name="employeeId" size="16" maxlength="64"/>
				<td align="right">��֯��Ԫ:</td>
				<td>
				     <s:orgselector id="orgselector1" name="departmentName" hiddenFieldName="departmentId" isShowDisable="show"/>
				</td>
				<td align="right">��ְ����:</td>
				<td>
					<s:select name="eqType" id="eqType" list="eqTypeMap"
						onchange="model.simple.changeSubSelect(this,'eqReason','eqTypeSubMap')">
					</s:select>
					&nbsp;&nbsp;ԭ��:
					<select name="eqReason" id="eqReason"></select>
				</td>
				<td rowspan="2">
					<input title="[Alt+F]" accesskey="F" name="submit_search" id="submit_search" class="button" type="submit" value="��ѯ">
					<input title="[Alt+A]" accesskey="A" name="submit_all" id="submit_all" class="button" type="reset" value="����"
						onclick="window.location='listQuit.action'">
				</td>
			</tr>
			<tr>
				<s:textfield id="eqPermission" label="������" name="eqPermission" size="16" maxlength="64"/>
				<s:textfield id="eqComments" label="��ע" name="eqComments" size="16" maxlength="64"/>
				<td align="right">��ְ����:</td>
				<td>
					<s:textfield id="eqDate" name="eqDate" size="10"/>
					<img onclick="WdatePicker({el:'eqDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
					��:
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
					<input class="button" type="button" id="btnOutput" value="���ݵ���" onClick="export_check('export');"/>
				</ty:auth>
			</td>
			<td align="right">���β�ѯ���õ�<s:property value="page.totalRows" />����ְ��¼&nbsp;</td>
		</tr>
	</table>
	<table cellpadding="0" cellspacing="0" width="100%" border="0" class="gridtableList">
		<s:hidden id="order" name="page.order" />
		<s:hidden id="operate" name="page.operate" />
		<tr>
			<th nowrap="nowrap">
				<a href="#" onclick="hrm.common.order_submit('emp.empDistinctNo', 'listForm');">Ա�����</a><img
				 src='../resource/images/arrow_.gif' width='8' height='10' id='emp.empDistinctNo_img'>
			</th>
	     	<th nowrap="nowrap">
		     	<a href="#" onclick="hrm.common.order_submit('emp.empName', 'listForm');">Ա������</a><img
		     	 src='../resource/images/arrow_.gif' width='8' height='10' id='emp.empName_img'>
	     	</th>
	     	<th nowrap="nowrap">
		     	<a href="#" onclick="hrm.common.order_submit('department.departmentName', 'listForm');">����</a><img
		     	 src='../resource/images/arrow_.gif' width='8' height='10' id='department.departmentName_img'>
	     	</th>
	     	<th nowrap="nowrap">
		     	<a href="#" onclick="hrm.common.order_submit('eqDate', 'listForm');">��ְ����</a><img
		     	 src='../resource/images/arrow_.gif' width='8' height='10' id='eqDate_img'>
	     	</th>
	     	<th nowrap="nowrap">
		     	<a href="#" onclick="hrm.common.order_submit('manager.empName', 'listForm');">������</a><img
		     	 src='../resource/images/arrow_.gif' width='8' height='10' id='manager.empName_img'>
	     	</th>
	     	<th nowrap="nowrap">
		     	<a href="#" onclick="hrm.common.order_submit('eqType', 'listForm');">����</a><img
		     	 src='../resource/images/arrow_.gif' width='8' height='10' id='eqType_img'>
	     	</th>
	     	<th nowrap="nowrap">
		     	<a href="#" onclick="hrm.common.order_submit('eqReason', 'listForm');">��ְԭ��</a><img
		     	 src='../resource/images/arrow_.gif' width='8' height='10' id='eqReason_img'>
	     	</th>
	     	<th nowrap="nowrap">
		     	<a href="#" onclick="hrm.common.order_submit('erComments', 'listForm');">��ע</a><img
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
			   				<s:if test="eqType==0">��ְ</s:if>
			   				<s:if test="eqType==1">��ְ</s:if>
			   				<s:if test="eqType==2">ͣн��ְ</s:if>
		   					<s:if test="eqType==3">����</s:if>
		   					<s:if test="eqType==4">����</s:if>
		   					<s:if test="eqType==5">��ͬ����</s:if>
		   					<s:if test="eqType==6">����</s:if>
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
   					<td colspan="8" align="center">�������ְ��¼��</td>
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
//����
function export_check(str){
	document.getElementById('searchOrExport').value=str;
	document.getElementById('listForm').submit();
	document.getElementById('searchOrExport').value='';
	return;
}
model.simple.setParentIFrameFull("IFrame1"); // add by ���Σ�����iframe�߶ȣ�
</script>
</body>	