<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
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
<script type="text/javascript" src="../resource/js/hrm/profile.js"></script>
<link rel="stylesheet" type="text/css" href="../resource/css/edit_select.css" />
<title>离职管理</title>
</head>
<body align="left">
<s:component template="bodyhead">
	<s:param name="pagetitle" value="'离职员工管理'" />
	<s:param name="helpUrl" value="'32'" />
</s:component>
<s:form id="empQuitManagement" name="empQuitManagement" action="empQuitManagement" namespace="/profile" method="POST">
	<input type="hidden" name="qemp.ids" id="ids" />
	<input type="hidden" name="id" id="delEmpId" />
	<s:hidden id="output-ommId" name="outmatchModelId" />
	<s:hidden id="output-ioName" name="outputIoName" />
	<input type="hidden" name="searchOrExport" id="searchOrExport" />
	<table width="100%" border="0" cellspacing="2" cellpadding="0" style="background-color: #ECF6FB; border: 1px #6BB5DA solid">
		<tr>
			<td>
				<div id="basic" style="DISPLAY: block; clear: both">
					<input id="operate" type="hidden" name="page.operate" />
					<s:hidden id="order" name="page.order" />
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<s:textfield label="员工" id="employeeId" name="employeeId" size="16" maxlength="64" />
							
							<td align="right">组织单元:</td>
							<td>
							     <s:orgselector id="orgselector1" name="departmentName" hiddenFieldName="departmentId" isShowDisable="show"/>
							</td>
							
							<td align="right">离职种类:</td>
							<td><s:select name="eqType" id="eqType" list="eqTypeMap"
								onchange="model.simple.changeSubSelect(this,'eqReason','eqTypeSubMap')"></s:select>
								&nbsp;&nbsp;原因:
								<select name="eqReason" id="eqReason"></select>
							</td>
							<td rowspan="2">
								<input title="[Alt+F]" accesskey="F" id="submit_button" class="button" type="submit" value="查询"
									onclick="document.getElementById('searchOrExport').value='';">
								<input title="[Alt+C]" accesskey="C" class="button" type="reset" value="重置"
									onclick="window.location='empQuitManagement.action'">
							</td>
						</tr>
						<tr>
							<s:textfield id="eqPermission" label="审批人" name="eqPermission" size="16" maxlength="64"/>
							<s:textfield id="eqComments" label="备注" name="eqComments" size="16" maxlength="64"/>
							<td align="right">离职时间:</td>
							<td colspan="3">
								<s:textfield id="eqDate" name="eqDate" size="10"/>
								<img onclick="WdatePicker({el:'eqDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
								到
								<s:textfield id="eqDateTo" name="eqDateTo" size="10"/>
								<img onclick="WdatePicker({el:'eqDateTo'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
							</td>
						</tr>
					</table>
				</div>
			</td>
		</tr>
	</table>
	<br />
	<table width="100%">
		<tr>
			<td align="left">
				<ty:auth auths="101">
					<input type="button" class="button" onclick="batch('delete');" value="员工删除" />
					<input type="button" class="button" onclick="batch('rehab')" value="批量复职" />
				</ty:auth>
				<ty:auth auths="101,2 or 101,3">
					<input class="button" type="button" id="btnOutput" value="数据导出"
						onClick="document.getElementById('searchOrExport').value='export';document.getElementById('empQuitManagement').submit();"/>
				</ty:auth>
			</td>
			<td align="right">本次查询共得到<s:property value="page.totalRows" />名员工记录&nbsp;</td>
		</tr>
	</table>

	<table cellpadding="0" cellspacing="0" width="100%" border="0" class="gridtableList">
		<tr>
			<ty:auth auths="101">
				<th align="center" width="3%">
					<input id="id_check_all" class="checkbox" type="checkbox" onclick="hrm.common.checkAllByName('quitemp.ids','id_check_all');" value="0">
				</th>
			</ty:auth>
			<th nowrap="nowrap">
				<a href="#" onclick="hrm.common.order_submit('empDistinctNo','empQuitManagement');">员工编号</a><img
				 src='../resource/images/arrow_.gif' width='8' height='10' id='empDistinctNo_img'></th>
			<th nowrap="nowrap">
				<a href="#" onclick="hrm.common.order_submit('empName','empQuitManagement');">员工姓名</a><img
				 src='../resource/images/arrow_.gif' width='8' height='10' id='empName_img'></th>
			<th nowrap="nowrap">
				<a href="#" onclick="hrm.common.order_submit('empDeptNo.departmentSortId','empQuitManagement');">部门</a><img
				 src='../resource/images/arrow_.gif' width='8' height='10' id='empDeptNo.departmentSortId_img'></th>
			<th nowrap="nowrap">
				<a href="#" onclick="hrm.common.order_submit('empTerminateDate','empQuitManagement');">离职日期</a><img
				 src='../resource/images/arrow_.gif' width='8' height='10' id='empTerminateDate_img'></th>
			<th nowrap="nowrap">
				<a href="#" onclick="hrm.common.order_submit('manager.empName','empQuitManagement');">审批人</a><img
				 src='../resource/images/arrow_.gif' width='8' height='10' id='manager.empName_img'></th>
			<th nowrap="nowrap">
				<a href="#" onclick="hrm.common.order_submit('quit.eqType','empQuitManagement');">种类</a><img
				 src='../resource/images/arrow_.gif' width='8' height='10' id='quit.eqType_img'></th>
			<th nowrap="nowrap">
				<a href="#" onclick="hrm.common.order_submit('quit.eqReason','empQuitManagement');">离职原因</a><img
				 src='../resource/images/arrow_.gif' width='8' height='10' id='quit.eqReason_img'></th>
			<th nowrap="nowrap">
				<a href="#" onclick="hrm.common.order_submit('quit.erComments','empQuitManagement');">备注</a><img
				 src='../resource/images/arrow_.gif' width='8' height='10' id='quit.erComments_img'></th>
			<th nowrap="nowrap" colspan="2">
				<a href="#" onclick="hrm.common.order_submit('quit.eqLastChangeTime','empQuitManagement');">操作</a><img
				 src='../resource/images/arrow_.gif' width='8' height='10' id='quit.eqLastChangeTime_img'></th>
		</tr>
		<tbody>
			<s:if test="!quitList.isEmpty()">
				<s:iterator value="quitList" status="quitStatus">
					<tr>
						<td align="center" width="3%">
							<input name="quitemp.ids" class="checkbox" type="checkbox" value="<s:property value='id'/>" /></td>
						<td nowrap="nowrap">
							<a class="listViewTdLinkS1" 
								href="myInfo.action?empNo=<s:property value='id'/>&empName=<s:property value='empName'/>">
							<s:property value="empDistinctNo" /></a></td>
						<td nowrap="nowrap">
							<s:property value="empName" /></td>
						<td nowrap="nowrap">
							<s:property value="empDeptNo.departmentName" /></td>
						<td nowrap="nowrap">
							<s:property value="empTerminateDate" /></td>
							<td nowrap="nowrap">
								<s:property value="quit.eqPermission.empName" /></td>
						<s:if test="quit!=null">
							<td nowrap="nowrap">
								<s:if test="quit.eqType==0">辞职</s:if>
								<s:if test="quit.eqType==1">复职</s:if>
								<s:if test="quit.eqType==2">停薪留职</s:if>
								<s:if test="quit.eqType==3">退休</s:if>
								<s:if test="quit.eqType==4">辞退</s:if>
								<s:if test="quit.eqType==5">合同到期</s:if>
								<s:if test="quit.eqType==6">其他</s:if></td>
							<td nowrap="nowrap">
								<s:property value="quit.eqReason" /></td>
							<td>
								<s:property value="quit.erComments" /></td>
						</s:if>
						<s:else>
							<td nowrap="nowrap">&nbsp;</td>
							<td nowrap="nowrap">&nbsp;</td>
							<td>&nbsp;</td>
						</s:else>
							<td align="center" nowrap="nowrap" colspan="2">
								<a href="#" onclick="hrm.profile.showInfoCardInfo('<s:property value='id'/>');">
								<img src="../resource/images/Search.gif" alt="员工资料卡" border='0' /></a>
								<ty:auth auths="101">
									<a onclick="deleteEmp('<s:property value='id'/>');" href="#">
									<img src="../resource/images/delete.gif" alt="删除"></img></a>
								</ty:auth>
							</td>
					</tr>
				</s:iterator>
			</s:if>
			<s:else>
				<tr>
					<td colspan="8" align="center">无相关记录！</td>
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
					<s:param name="formId" value="'empQuitManagement'" />
				</s:component>
			</td>
		</tr>
	</table>
</s:form>
<!-- 批量复职 -->
<div id="dlgReturnDiv" style="width:425px; display:none;" title="批量复职">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<table width="100%" class="prompt_div_body" cellpadding="0" cellspacing="0" width="100%" border="0">
				<tr>
					<td>复职日期<span class="required">*</span>：</td>
					<td>
						<s:textfield id="joinDate" name="qemp.empJoinDate" required="true" size="10"/>
						<img onclick="WdatePicker({el:'joinDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="14" height="22" style="cursor:pointer" align="absmiddle">
					</td>
				</tr>
				<tr>
					<td>复职种类<span class="required">*</span>：</td>
					<td>
						<s:select name="empquit.eqType" id="eqRType" list="eqRTypeMap"
							onchange="model.simple.changeSubSelect(this,'empquit.eqReason_id','eqTypeSubMapR')">
						</s:select>
					</td>
				</tr>
				<tr>
					<td>复职审批人<span class="required">*</span>：</td>
					<td>
						<input id="empManagerName" readonly="readonly">
						<input type="hidden" name="empquit.eqPermission.id" id="empManagerId" />
						<ty:auth auths="101,2">
							<img src="../resource/images/search_icon.gif" style="CURSOR: pointer"
								onclick="showChooseEmpDiv(1,1);" alt='点击图标选择经理' />
						</ty:auth>
					</td>
				</tr>
				<tr>
					<td>复职原因<span class="required">*</span>：</td>
					<td><select name="empquit.eqReason" id="empquit.eqReason_id"></select></td>
				</tr>
				<tr>
				    <td>复职部门<span class="required">*</span>:</td>
					<td>					
				    <s:hidden id="chang_dept_id"/>
					 <s:textfield id="chang_dept_name" size="16" readonly="true"/>
					</td>
				</tr>
				<tr>
					<td>复职职位<span class="required">*</span>：</td>
					<td>
						<div><table cellpadding="0" cellspacing="0" class="select"><tr><td bgcolor=#FFFFFF>
		                <s:hidden id="positionId" name="empquit.position.id"/>
		                <input id="empPBName" name="empquit.position.positionPbId.pbName" value='<s:property value="empquit.position.positionPbId.pbName"/>'  class='selecttext' readonly="true" size="12" />
		                <button type="button" class="selectbutton" style="CURSOR: pointer" id="showdiv" onclick="showPostionTree('empPBName', 'positionId', 'chang_dept_name', 'chang_dept_id');"/>&nbsp;
		                </button></td></tr></table></div>
					</td>
				</tr>
				<tr>
					<td>备注：</td>
					<td><textarea name="empquit.erComments" rows="8" cols="40"></textarea></td>
				</tr>
				<tr height="35">
					<td colspan="2" align="center">
						<!-- <input type="hidden" value='<s:property value="#session.curr_oper_no"/>' name="employeeId"> -->
						<input id="eqAddbtn" type="button" onclick="doRehab();" ; value="复职" />
						<input type="button" value="关闭" onclick="hrm.common.closeDialog('dlgReturnDiv');" />
					</td>
				</tr>
			</table>
		</tr>
	</table>
</div>
<script type="text/javascript">
//初始化复职下拉框
eqRTypeSubMap={};
<s:iterator value="eqRReasonMap" id="topEntrySetR">
	eqRTypeSubMap['<s:property value="#topEntrySetR.key"/>']={};
	<s:iterator value="#topEntrySetR.value" status="index" id="secEntrySetR">
		<s:if test="#secEntrySetR.key.length()==0">
		eqRTypeSubMap['<s:property value="#topEntrySetR.key"/>']['']='<s:property value="#secEntrySetR.value"/>';
		</s:if>
		<s:else>
			eqRTypeSubMap['<s:property value="#topEntrySetR.key"/>']['<s:property value="#secEntrySetR.value"/>']='<s:property value="#secEntrySetR.value"/>';
		</s:else>
	</s:iterator>
</s:iterator>
model.simple.changeSubSelect('eqRType','empquit.eqReason_id','eqRTypeSubMap','<s:property value="quit.eqReason"/>');

//初始化离职下拉框
eqTypeSubMap={};
<s:iterator value="eqReasonMap" id="topEntrySet">
	eqTypeSubMap['<s:property value="#topEntrySet.key"/>']={};
	<s:iterator value="#topEntrySet.value" status="index" id="secEntrySet">
		<s:if test="#secEntrySet.key.length()==0">
			eqTypeSubMap['<s:property value="#topEntrySet.key"/>']['']='<s:property value="#secEntrySet.value"/>';
		</s:if>
		<s:else>
			eqTypeSubMap['<s:property value="#topEntrySet.key"/>']['<s:property value="#secEntrySet.value"/>']='<s:property value="#secEntrySet.value"/>';
		</s:else>
	</s:iterator>
</s:iterator>
model.simple.changeSubSelect('eqType','eqReason','eqTypeSubMap','<s:property value="quit.eqReason"/>');
//删除离职员工
function deleteEmp(id){
	if(confirm("确定要删除吗？")){
		document.getElementById('delEmpId').value=id;
		document.getElementById('empQuitManagement').action="deleteEmp.action";
		document.getElementById('searchOrExport').value = "";
		document.getElementById('empQuitManagement').submit();
	}
}
//批量删除/复职
function batch(operate){
	var checkBoxs=document.getElementsByName('quitemp.ids');
	var flag=0;
	error="";
	var ids = "";
	for(var i=0;i<checkBoxs.length;i++){
		if(checkBoxs[i].checked==true){
			flag=1;
			ids+= checkBoxs[i].value;
			if(i != checkBoxs.length - 1){
    	   		ids+= ",";
			}
		}
	}
	if(flag==0){
		error="请至少选择一个员工！";
	}
	if(error.length>1){
		alert(error);
		return ;
	}
	if('delete' == operate){
		if (confirm("您确定要删除吗？")){
			document.getElementById('operate').value=operate;
			document.getElementById('ids').value = ids;
			document.getElementById('empQuitManagement').action="batchDeleteEmp.action";
			document.getElementById('searchOrExport').value = "";
			document.getElementById('empQuitManagement').submit();
		}
	}
	else if("rehab" == operate){
		document.getElementById('joinDate').value = (new Date()).toHRMDateString();
		hrm.common.openDialog('dlgReturnDiv','empQuitManagement');
	}
}
//复职
function doRehab(){
	checkBoxs=document.getElementsByName('quitemp.ids');
	var flag=0;
	error="";
	var ids = "";
	for(var i=0;i<checkBoxs.length;i++){
		if(checkBoxs[i].checked&&checkBoxs[i].checked==true){
			flag=1;
			ids+= checkBoxs[i].value;
			if(i != checkBoxs.length - 1){
				ids+= ",";
			}
		}
	}
	if(flag==0){
		error="请至少选择一个员工！";
	}
	if(error.length>1){
		alert(error);
		return ;
	}
	if(!hrm.common.isDate(document.getElementById('joinDate').value)) {
		alert('复职日期不能为空或日期格式不正确！');
		return;
	}
	if(hrm.common.isNull(document.getElementById('empManagerName').value)){
		alert("复职审批人不能为空！");
		return;
	}
	if(hrm.common.isNull(document.getElementById('empquit.eqReason_id').value)){
		alert("复职原因不能为空！");
		return;
	}
	if(hrm.common.isNull(document.getElementById('chang_dept_name').value)){
		alert("复职部门不能为空！");
		return;
	}
	if(hrm.common.isNull(document.getElementById('empPBName').value)){
		alert("复职职位不能为空！");
		return;
	}
	ids = ids.trim();
	if(',' == ids.charAt(ids.length-1)){
		ids = ids.substring(0,ids.length-1);
	}
	document.getElementById('ids').value = ids;
	document.getElementById('empQuitManagement').action="batchRehabEmp.action";
	document.getElementById('searchOrExport').value = "";
	document.getElementById('empQuitManagement').submit();
}
hrm.common.check_order();
hrm.common.initDialog('dlgReturnDiv');
</script>
<%@ include file="search_emp_div.jsp"%>
<jsp:include flush="true" page="../io/div_omm_select.jsp"></jsp:include>
<jsp:include flush="true" page="position_choose_div.jsp"></jsp:include>
</body>