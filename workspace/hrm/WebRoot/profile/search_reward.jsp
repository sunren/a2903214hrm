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
 -->
<head>
<link href="../resource/css/style.css" rel="stylesheet" type="text/css" />
<jsp:include flush="true" page="../sitemesh/jsPackage.jsp"></jsp:include>
<script type="text/JavaScript" src="../resource/js/hrm/profile.js"></script>
<title>奖惩记录</title>
</head>
<body onload="hrm.common.check_order();" align="left">
<ty:auth auths="101">
	<s:set name="hasAuth" value="'has'"/>
</ty:auth>
<s:component template="bodyhead"/>

<form action="" id="listForm" method="post">
	<div id="basic" style="DISPLAY: block;clear : both">
		<table width="100%" border="0" cellspacing="2" cellpadding="0" style="background-color: #ECF6FB; border: 1px #6BB5DA solid">
			<tr>
				<s:textfield id="employeeId" label="员工" name="employeeId" size="16" maxlength="64"/>
				<td align="right">执行组织:</td>
				<td>
				     <s:orgselector id="orgselector2" name="departmentName" hiddenFieldName="departmentId" isShowDisable="show"/>
				</td>
				<td align="right">奖惩种类:</td>
				<td>
					<s:select name="erType" id="erType" list="erTypeMap"
						onchange="model.simple.changeSubSelect(this,'erForm','erTypeSubMap')">
					</s:select>
					&nbsp;&nbsp;形式:
					<select name="erForm" id="erForm"></select>
				</td>
				<td rowspan="2" >
					<input title="[Alt+F]" accesskey="F" name="submit_search" id="submit_search" class="button"
						type="submit" value="查询">
					<input title="[Alt+A]" accesskey="A" name="submit_all" id="submit_all" class="button"
						type="reset" value="重置" onclick="window.location='listReward.action'">
				</td>
			</tr>
			<tr>
				<td align="right">组织单元:</td>
				<td>
				     <s:orgselector id="orgselector1" name="deptName" hiddenFieldName="deptId" isShowDisable="show"/>
				</td>
				<s:textfield id="erContent" label="备注" name="erContent" size="16" maxlength="64"/>
				<td align="right">奖惩日期:</td>
				<td>
					<s:textfield id="erExeDate" name="erExeDate" size="10"/>
					<img onclick="WdatePicker({el:'erExeDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
					到:
					<s:textfield id="erExeDateTo" name="erExeDateTo" size="10"/>
					<img onclick="WdatePicker({el:'erExeDateTo'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
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
					<input class="button" type="button" onclick="initDivImmUpload('IEmpreward');" value="数据导入"/>
					<input class="button" id="btnOutput" type="button" value="数据导出" onclick="export_check('export');"/>
				</ty:auth>
			</td>
			<td align="right">本次查询共得到<s:property value="page.totalRows" />条奖惩记录&nbsp;</td>
		</tr>
	</table>
	<table cellpadding="0" cellspacing="0" width="100%" border="0" class="gridtableList">
    	<s:hidden id="order" name="page.order" />
		<s:hidden id="operate" name="page.operate" />
		<tr>
	     	<th nowrap="nowrap">
		     	<a href="#" onclick="hrm.common.order_submit('emp.empDistinctNo', 'listForm');">员工编号</a><img
		     	 src='../resource/images/arrow_.gif' width='8' height='10' id='emp.empDistinctNo_img'></th>
	     	<th nowrap="nowrap">
		     	<a href="#" onclick="hrm.common.order_submit('emp.empName', 'listForm');">员工姓名</a><img
		     	 src='../resource/images/arrow_.gif' width='8' height='10' id='emp.empName_img'></th>
	     	<th nowrap="nowrap">
		     	<a href="#" onclick="hrm.common.order_submit('erExeDate', 'listForm');">奖惩日期</a><img
		     	 src='../resource/images/arrow_.gif' width='8' height='10' id='erExeDate_img'></th>
	     	<th nowrap="nowrap">
		     	<a href="#" onclick="hrm.common.order_submit('department.departmentName', 'listForm');">执行部门</a><img
		     	 src='../resource/images/arrow_.gif' width='8' height='10' id='department.departmentName_img'></th>
	     	<th nowrap="nowrap">
		     	<a href="#" onclick="hrm.common.order_submit('erPbNo.pbName', 'listForm');">职位</a><img
		     	 src='../resource/images/arrow_.gif' width='8' height='10' id='erPbNo.pbName_img'></th>
	     	<th nowrap="nowrap">
		     	<a href="#" onclick="hrm.common.order_submit('erType', 'listForm');">种类</a><img
		     	 src='../resource/images/arrow_.gif' width='8' height='10' id='erType_img'></th>
	     	<th nowrap="nowrap">
		     	<a href="#" onclick="hrm.common.order_submit('erForm', 'listForm');">形式</a><img
		     	 src='../resource/images/arrow_.gif' width='8' height='10' id='erForm_img'></th>
	     	<th nowrap="nowrap">
		     	<a href="#" onclick="hrm.common.order_submit('erContent', 'listForm');">备注</a><img
		     	 src='../resource/images/arrow_.gif' width='8' height='10' id='erContent_img'></th>
     	</tr>
     	<tbody>
	     	<s:if test="!rewardList.isEmpty()">
	     		<s:iterator value="rewardList">
			     	<tr id="<s:property value='erId'/>">
			     		<td><s:property value="employee.empDistinctNo"/></td>
		   				<td><s:property value="employee.empName"/></td>
			     		<td><s:property value="erExeDate"/></td>
			     		<td><s:property value="department.departmentName"/></td>
			     		<td><s:property value="erPbNo.pbName"/></td>
			     		<td><s:property value="erTypeMean"/></td>
			     		<td><s:property value="erForm"/></td>
			     		<td><s:property value="erContent"/></td>
			     	</tr>
     			</s:iterator>
     		</s:if>
     		<s:else>
	     		<tr>
	     			<td colspan="10" align="center">无相关奖惩记录！</td>
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
erTypeSubMap={};
erTypeSubMap={};
<s:iterator value="erFormMap" id="topEntrySet">
	erTypeSubMap['<s:property value="#topEntrySet.key"/>']={};
	<s:iterator value="#topEntrySet.value" status="index" id="secEntrySet">
		<s:if test="#secEntrySet.key.length()==0">
			erTypeSubMap['<s:property value="#topEntrySet.key"/>']['']='<s:property value="#secEntrySet.value"/>';
		</s:if><s:else>
			erTypeSubMap['<s:property value="#topEntrySet.key"/>']['<s:property value="#secEntrySet.value"/>']=
				'<s:property value="#secEntrySet.value"/>';
		</s:else>
	</s:iterator>
</s:iterator>
model.simple.changeSubSelect('erType','erForm','erTypeSubMap','<s:property value="erForm"/>');
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