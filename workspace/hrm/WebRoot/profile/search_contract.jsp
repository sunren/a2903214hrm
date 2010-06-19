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
<jsp:include flush="true" page="../sitemesh/jsPackage.jsp"></jsp:include>
<link href="../resource/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/JavaScript" src="../resource/js/hrm/profile.js"></script>
<title>搜索档案</title>
</head>
<body onload="hrm.common.check_order();" align="left">
<form action="listContract.action" id="listForm" method="post">
	<table  width="100%" border="0" cellspacing="2" cellpadding="0" style="background-color: #ECF6FB; border: 1px #6BB5DA solid">
		<tr>
			<s:textfield label="员工" id="employeeId" name="employeeId" size="16" maxlength="64"/>
			<s:select label="合同种类" name="empTypeId" list="empTypeList" listKey="id" listValue="ectName" multiple="false" emptyOption="true"/>
			<td align="right">起始日期:</td>
			<td>
				<s:textfield id="ectStartDate" name="ectStartDate" size="10"/>
				<img onclick="WdatePicker({el:'ectStartDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
				到:
				<s:textfield id="ectStartDateTo" name="ectStartDateTo" size="10"/>
				<img onclick="WdatePicker({el:'ectStartDateTo'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
			</td>
			<td rowspan="3" align="left">
				<input title="[Alt+F]" accesskey="F" name="submit_search" id="submit_search" class="button" type="submit" value="查询">
				<input title="[Alt+A]" accesskey="A" name="submit_all" id="submit_all" class="button" type="reset" value="重置" onclick="window.location='listContract.action'">
			</td>
		</tr>
		<tr>
			<td align="right">组织单元:</td>
			<td>
			     <s:orgselector id="orgselector1" name="departmentName" hiddenFieldName="departmentId" isShowDisable="show"/>
			</td>
			<s:select label="合同期限" name="etcExpire" list="#{'':'请选择','0':'有限期','1':'无限期'}"/>
			<td align="right">结束日期:</td>
			<td>
				<s:textfield id="ectEndDate" name="ectEndDate" size="10"/>
				<img onclick="WdatePicker({el:'ectEndDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
				到:
				<s:textfield id="ectEndDateTo" name="ectEndDateTo" size="10"/>
				<img onclick="WdatePicker({el:'ectEndDateTo'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
			</td>
		</tr>
		<tr>
			<s:textfield id="ectNo" label="合同编号" name="ectNo" size="16" maxlength="64"/>
			<s:select label="合同状态" name="ectStatus" list="#{'':'请选择','1':'有效','2':'终止','3':'解除'}"/>
			<s:textfield label="合同备注" id="ectComments" name="ectComments" size="20" maxlength="128"/>
		</tr>
 	 </table>
	<s:component template="bodyhead"/>
	<table width="100%">
		<tr><br /></tr>
		<tr>
			<td>
				<ty:auth auths="101,2 or 101 3">
					<s:hidden id="output-ommId" name="outmatchModelId"/>
					<s:hidden id="output-ioName" name="outputIoName"/>
					<input type="hidden" name="searchOrExport" id="searchOrExport" />
					<input class="button" type="button" onclick="initDivImmUpload('IEmpContract');" value="数据导入"/>
					<input class="button" type="button" id="btnOutput" value="数据导出" onclick="export_checka('export');"/>
				</ty:auth>
			</td>
			<td align="right">本次查询共得到<s:property value="page.totalRows" />条人事合同记录&nbsp;</td>
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
		     	<a href="#" onclick="hrm.common.order_submit('ectStatus', 'listForm');">状态</a><img
		     	 src='../resource/images/arrow_.gif' width='8' height='10' id='ectStatus_img'></th>
	     	<th nowrap="nowrap">
	     		<a href="#" onclick="hrm.common.order_submit('ectStartDate', 'listForm');">起始日期</a><img
	     		 src='../resource/images/arrow_.gif' width='8' height='10' id='ectStartDate_img'>
	     	</th>
	     	<th nowrap="nowrap">
		     	<a href="#" onclick="hrm.common.order_submit('ectEndDate', 'listForm');">结束日期</a><img
		     	 src='../resource/images/arrow_.gif' width='8' height='10' id='ectEndDate_img'>
	     	</th>
	     	<th nowrap="nowrap">
		     	<a href="#" onclick="hrm.common.order_submit('ectNo', 'listForm');">合同编号</a><img
		     	 src='../resource/images/arrow_.gif' width='8' height='10' id='ectNo_img'>
	     	</th>
	     	<th nowrap="nowrap">
		     	<a href="#" onclick="hrm.common.order_submit('contractType.ectName', 'listForm');">合同种类</a><img
		     	 src='../resource/images/arrow_.gif' width='8' height='10' id='contractType.ectName_img'></th>
	     	<th nowrap="nowrap">
		     	<a href="#" onclick="hrm.common.order_submit('etcExpire', 'listForm');">合同期限</a><img
		     	 src='../resource/images/arrow_.gif' width='8' height='10' id='etcExpire_img'></th>
	     	<th nowrap="nowrap">
		     	<a href="#" onclick="hrm.common.order_submit('ectComments', 'listForm');">备注</a><img
		     	 src='../resource/images/arrow_.gif' width='8' height='10' id='ectComments_img'></th>
	     	<th>附件</th>
		</tr>
     	<tbody>
			<s:if test="!contractList.isEmpty()">
				<s:iterator value="contractList">
					<tr id="<s:property value='ectId'/>">
						<td nowrap="nowrap">
							<s:property value="employee.empDistinctNo"/>
						</td>
						<td nowrap="nowrap">
							<s:property value="employee.empName"/>
						</td>
						<td nowrap="nowrap">
							<s:if test="ectStatus==1">有效</s:if>
							<s:if test="ectStatus==2">终止</s:if>
							<s:if test="ectStatus==3">解除</s:if>
						</td>
						<td nowrap="nowrap">
							<s:property value="ectStartDate"/>
						</td>
						<td nowrap="nowrap">
							<s:property value="ectEndDate"/>
						</td>
						<td nowrap="nowrap">
							<s:property value="ectNo"/>
						</td>
						<td nowrap="nowrap">
							<s:property value="contractType.ectName"/>
						</td>
						<td nowrap="nowrap">
							<s:if test="etcExpire==0">有限期</s:if>
							<s:if test="etcExpire==1">无限期</s:if>
						</td>
						<td>
							<s:property value="ectComments"/>
						</td>
						<td nowrap="nowrap" align="right">
							<s:if test="ectAttatchment!='' && ectAttatchment!=null">
								<a id="href_<s:property value='ectId'/>" 
									href="downProfile.action?fileName=<s:property value="ectAttatchment"/>&contentDisposition=<s:property value="ectAttatchment"/>">
								<img src="../resource/images/attachment.gif"/></a>
								<input type="hidden" id="attach<s:property value="ectId"/>" value="<s:property value="ectAttatchment"/>"/>
								<script type="text/javascript">
									var filename = '<s:property value="employee.empDistinctNo"/>'+"_";
									filename += '<s:property value="ectStartDate"/>'+"_";
									<s:if test="ectEndDate != null">
										filename += '<s:property value="ectEndDate"/>'+"_";
									</s:if>
									var exp = '<s:property value="etcExpire"/>';
									filename += (exp=='0')?'有限期':'无限期';
									var extend = '<s:property value="ectAttatchment"/>';
									filename += "."+extend.split('.')[1];
									var tmp = 'downProfile.action?fileName=<s:property value="ectAttatchment"/>&contentDisposition=';
									document.getElementById("href_"+"<s:property value='ectId'/>").href = tmp + filename;
								</script>
							</s:if>				
   						</td>
					</tr>
		 		</s:iterator>
			</s:if>
			<s:else>
			 	<tr>
			 		<td colspan="12" align="center">无相关合同记录！</td>
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
function export_checka(str){
	document.getElementById('searchOrExport').value=str;
	document.getElementById('listForm').submit();
	document.getElementById('searchOrExport').value='';
	return;
}
model.simple.setParentIFrameFull("IFrame1"); // add by 金鑫（计算iframe高度）
</script>
<jsp:include flush="true" page="../io/div_upload.jsp"></jsp:include>
</body>	