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
<title>员工考评档案</title>
</head>
<body onload="hrm.common.check_order();" align="left">
<s:component template="bodyhead"/>
<div id="selectcontent" class="selectdiv" style="visibility:hidden;pixelHeight:20px;z-index:9;">
	<iframe id="selframe" frameborder="0" height="100%"></iframe>
	<div id="selecthtml" class="selectcontent">selectdate</div>
</div>
<!-- 可输入的select -->
<script type="text/javascript" src="../resource/js/edit_select.js"></script>
<link rel="stylesheet" type="text/css" href="../resource/css/edit_select.css" />

<form action="listEval.action" id="listForm" method="post">
	<div id="basic" style="DISPLAY: block;clear : both">
	<table width="100%" border="0" cellspacing="2" cellpadding="0" style="background-color: #ECF6FB; border: 1px #6BB5DA solid">
		<tr>
			<s:textfield id="employeeId" label="员工" name="employeeId" size="16" maxlength="64"/>
			<s:select label="考评种类" name="eeType" list="#{'':'请选择','月度':'月度','季度':'季度','半年':'半年','全年':'全年','项目':'项目'}"/>
			<td align="right">起始日期:</td>
			<td>
				<s:textfield id="eeStartDate" name="eeStartDate" size="10"/>
				<img onclick="WdatePicker({el:'eeStartDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
				到:
				<s:textfield id="eeStartDateTo" name="eeStartDateTo" size="10"/>
				<img onclick="WdatePicker({el:'eeStartDateTo'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
			</td>
			<td rowspan="3">
				<input title="[Alt+F]" accesskey="F" name="submit_search" id="submit_search" class="button" type="submit" value="查询">
				<input title="[Alt+A]" accesskey="A" name="submit_all" id="submit_all" class="button" type="reset" value="重置" 
					onclick="window.location='listEval.action'">
			</td>
		</tr>
		<tr>
			<td align="right">组织单元:</td>
			<td>
			     <s:orgselector id="orgselector1" name="departmentName" hiddenFieldName="departmentId" isShowDisable="show"/>
			</td>
			<td align="right">考评组织单元:</td>
			<td>
			     <s:orgselector id="orgselector2" name="eeDepartmentName" hiddenFieldName="eeDepartmentId" isShowDisable="show"/>
			</td>
			
			<td align="right">结束日期:</td>
			<td>
				<s:textfield id="eeEndDate" name="eeEndDate" size="10"/>
				<img onclick="WdatePicker({el:'eeEndDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
				到:
				<s:textfield id="eeEndDateTo" name="eeEndDateTo" size="10"/>
				<img onclick="WdatePicker({el:'eeEndDateTo'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
			</td>
		</tr>
		<tr>
			<td align="right">考评结果:</td>
			<td>
				<s:component template="editselect"  name="eeRateSelect">
					<s:param name="list" value="{'A','B','C','D','E','通过','未通过'}"/>
					<s:param name="size" value="10"/>
					<s:param name="height" value="100"/>
				</s:component>
			</td>
			<s:textfield id="eeManager" label="考评经理" name="eeManager" size="16" maxlength="64"/>
			<s:textfield id="eeComments" label="考评备注" name="eeComments" size="20" maxlength="64"/>
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
					<input class="button" type="button" onclick="initDivImmUpload('IEmpeval');" value="数据导入"/>
					<input class="button" id="btnOutput" type="button" value="数据导出" onclick="export_check('export');"/>
				</ty:auth>
			</td>
			<td align="right">本次查询共得到<s:property value="page.totalRows" />条考评记录&nbsp;</td>
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
		     	<a href="#" onclick="hrm.common.order_submit('eeStartDate', 'listForm');">起始日期</a><img
		     	 src='../resource/images/arrow_.gif' width='8' height='10' id='eeStartDate_img'>
	     	</th>
	     	<th nowrap="nowrap">
		     	<a href="#" onclick="hrm.common.order_submit('eeEndDate', 'listForm');">结束日期</a><img
		     	 src='../resource/images/arrow_.gif' width='8' height='10' id='eeEndDate_img'>
	     	</th>
	     	<th nowrap="nowrap">
		     	<a href="#" onclick="hrm.common.order_submit('eeType', 'listForm');">种类</a><img
		     	 src='../resource/images/arrow_.gif' width='8' height='10' id='eeType_img'>
	     	</th>
	     	<th nowrap="nowrap">
		     	<a href="#" onclick="hrm.common.order_submit('department.departmentName', 'listForm');">考评部门</a><img
		     	 src='../resource/images/arrow_.gif' width='8' height='10' id='department.departmentName_img'>
	     	</th>
	     	<th nowrap="nowrap">
		     	<a href="#" onclick="hrm.common.order_submit('eePbNo.pbName', 'listForm');">职位</a><img
		     	 src='../resource/images/arrow_.gif' width='8' height='10' id='eePbNo.pbName_img'>
	     	</th>
	     	<th nowrap="nowrap">
		     	<a href="#" onclick="hrm.common.order_submit('manager.empName', 'listForm');">考评经理</a><img
		     	 src='../resource/images/arrow_.gif' width='8' height='10' id='manager.empName_img'>
	     	</th>
	     	<th nowrap="nowrap">
		     	<a href="#" onclick="hrm.common.order_submit('eeRate', 'listForm');">结果</a><img
		     	 src='../resource/images/arrow_.gif' width='8' height='10' id='eeRate_img'>
	     	</th>
	     	<th nowrap="nowrap">
		     	<a href="#" onclick="hrm.common.order_submit('eeComments', 'listForm');">备注</a><img
		     	 src='../resource/images/arrow_.gif' width='8' height='10' id='eeComments_img'>
	     	</th>
	    	<th nowrap="nowrap">操作</th>
	    </tr>
	  		<tbody>
	  			<s:if test="!evalList.isEmpty()">
		   			<s:iterator value="evalList">
			   			<tr id="<s:property value='eeId'/>">
			   				<td nowrap="nowrap">
			   					<s:property value="employeeByEeEmpNo.empDistinctNo"/>
			   				</td>
			   				<td nowrap="nowrap">
			   					<s:property value="employeeByEeEmpNo.empName"/>
			   				</td>
			   				<td nowrap="nowrap">
			   					<s:property value="eeStartDate"/>
			   				</td>
			   				<td nowrap="nowrap">
			   					<s:property value="eeEndDate"/>
			   				</td>
			   				<td nowrap="nowrap">
			   					<s:property value="eeType"/>
			   				</td>
			   				<td nowrap="nowrap">
			   					<s:property value="department.departmentName"/>
			   				</td>
			   				<td nowrap="nowrap">
			   					<s:property value="eePbNo.pbName"/>
			   				</td>
			   				<td nowrap="nowrap">
			   					<s:property value="employeeByEeManager.empName"/>
			   				</td>
			   				<td nowrap="nowrap">
			   					<s:property value="eeRate"/>
			   				</td>
			   				<td>
			   					<s:property value="eeComments"/>
			   				</td>
			   				<td nowrap="nowrap" align="right">
								<s:if test="eeAttachment!='' && eeAttachment!=null">
									<a href="downProfile.action?fileName=<s:property value='eeAttachment'/>&contentDisposition=<s:property value='eeAttachment'/>">
			   				  		<img src="../resource/images/attachment.gif"/></a>
			   				  </s:if>
			   				</td>
			   			</tr>
		   			</s:iterator>
	  			</s:if>
	  			<s:else>
	  				<tr>
	  					<td colspan="12" align="center">无相关考评记录！</td>
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