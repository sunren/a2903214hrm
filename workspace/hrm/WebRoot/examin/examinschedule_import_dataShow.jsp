<%@page pageEncoding="GBK" contentType="text/html; charset=GBK" %>
<%@taglib prefix="s" uri="webwork"%>
<%@taglib prefix="ty" uri="/tengsource"%>
<!--
 =========================================================
' File:examainschedule_import_dataShow.jsp
' Auth:jet miao
' Version:1.1.0 standard version
' Date: 2008-11-28
' Script Written by tengsource.com
'=========================================================
' Copyright   2007 tengsource.com. All rights reserved.
' Web: http://www.tengsource.com
' Email: admin@tengsource.com
'=======================================================
 -->
<head>
<title>刷卡管理</title>
<jsp:include flush="true" page="/sitemesh/jsPackage.jsp"></jsp:include>
<script type='text/javascript' src='../dwr/interface/OrgMapAction.js'></script>
<script type='text/javascript' src='../dwr/interface/DwrSyncAttdMachine.js'></script>
<script type='text/javascript' src='../dwr/interface/DwrForAttend.js'></script>
<s:head/> 
</head>
<body onload="hrm.common.check_order();"> 
<span class="errorMessage" id="message"></span>
<s:component template="bodyhead">
	<s:param name="pagetitle"  value="'刷卡管理'" />
	<s:param name="helpUrl" value="'26'" />
</s:component>
<form id="originalDataShow" name="originalDataShow" method="POST" namespace="/examin" action="originalDataImportShow.action">
    <!-- 隐藏字段 -->
    <s:hidden name="operate"></s:hidden>
    <s:hidden name="aodId"></s:hidden>
    <s:hidden name="iodefId"></s:hidden>
    <s:hidden id="order" name="page.order"/>
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="formtable">
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
							    <td align="right">员工:</td>
							    <td><s:textfield id="emp" name="emp" size="16" maxlength="64"/></td>
								<td align="right">开始日期:</td>
								<td> 
								<s:textfield id="attdDateFrom"   value="%{attdDateFrom}" name="attdDateFrom" required="true" size="12"  />
								<img onclick="WdatePicker({el:'attdDateFrom'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">											
								</td>
								<td align="right">考勤机号:</td>
								<td>
								<s:select name="machineNo" list="machineList" listKey="macNo" listValue="macNo" multiple="false" emptyOption="true" value="machineNo" size="1" />
								</td>
							</tr>
							<tr>
							    <td align="right">组织单元:</td>
							    <td>
							    <s:orgselector id="orgselector1" name="deptName" hiddenFieldName="dept"/>
							    </td>
								<td align="right">结束日期:</td>
								<td>
								<s:textfield id="attdDateTo" value="%{attdDateTo}" name="attdDateTo" required="true" size="12" />
								<img onclick="WdatePicker({el:'attdDateTo'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
								</td>									
								<td align="right">状态:</td>
								<td><s:select id="status" name="status" value="status" list="#{2:'全部',0:'导入',1:'补卡'}"  emptyOption="false"/></td>
							</tr>
						</table>
					</td>
					<td>				
						<input type="button" class="button" name="searchData" value="查询" onclick="searchOriginalData();"/>
					    <input title="[Alt+C]" accesskey="C" name="clear_button" class="button" type="button" onClick="window.location='originalDataImportShow.action';" value="重置">
					</td>
				</tr>
			</table>
<table width="100%">
  <tr>
	<td><br/></td>
  </tr>
  <tr>
	<td align="left">
	    <ty:auth auths="401 or 411,3 or 411,2">
			<input class="button" type="button" onclick="initDivImmUpload('IExaminCardData', '');" value="数据导入"/>
			<input type="button" class="button" value="补卡" onclick="empSelecotr('addCard');"/>
		</ty:auth>
		<ty:auth auths="401,3 or 401,2">
		    <input type="button" class="button" name="deleteData" value="数据清理" onclick="deleteOriginalData();"/>
		</ty:auth>
	</td>
	<td align="right">本次查询共得到<s:property value="page.totalRows" />条刷卡记录&nbsp;</td>
  </tr>
</table>

    <table width="100%" border="0" cellspacing="0" cellpadding="0" id="dataTable" class="gridtableList">
        <tr>
	     	<th nowrap="nowrap"> 
	     		<a href="#" onclick="hrm.common.order_submit('emp.empDistinctNo','originalDataShow');">
	     		员工编号<img src='../resource/images/arrow_.gif' width='8' height='10' id='emp.empDistinctNo_img'></a>
	     	</th>
	     	<th nowrap="nowrap">
	     		<a href="#" onclick="hrm.common.order_submit('emp.empName','originalDataShow');">
	     		姓名<img src='../resource/images/arrow_.gif' width='8' height='10' id='emp.empName_img'></a>
	     	</th>
	     	<th nowrap="nowrap">
	     		<a href="#" onclick="hrm.common.order_submit('emp.empDeptNo','originalDataShow');">
	     		部门<img src='../resource/images/arrow_.gif' width='8' height='10' id='emp.empDeptNo_img'></a>
	     	</th>
	     	<th nowrap="nowrap">
	     		<a href="#" onclick="hrm.common.order_submit('aodAttdDate','originalDataShow');">
	     		考勤日期<img src='../resource/images/arrow_.gif' width='8' height='10' id='aodAttdDate_img'></a>
	     	</th>
	     	<th nowrap="nowrap">
	     		<a href="#" onclick="hrm.common.order_submit('aodCardTime','originalDataShow');">
	     		刷卡时间<img src='../resource/images/arrow_.gif' width='8' height='10' id='aodCardTime_img'></a>
	     	</th>
	     	<th nowrap="nowrap">
	     		<a href="#" onclick="hrm.common.order_submit('aodTtdMachineNo','originalDataShow');">
	     		考勤机号<img src='../resource/images/arrow_.gif' width='8' height='10' id='aodTtdMachineNo_img'></a>
	     	</th>
	     	<th nowrap="nowrap">
	     		<a href="#" onclick="hrm.common.order_submit('aodStatus','originalDataShow');">
	     		状态<img src='../resource/images/arrow_.gif' width='8' height='10' id='aodStatus_img'></a>
	     	</th>
	     	<th nowrap="nowrap">备注</th>
	     	<th nowrap="nowrap">操作</th>
        </tr>
        <s:if test="!originalDataList.isEmpty()"> <!--判断是否为空-->
			<s:iterator value="originalDataList" status="st">
		        <tr>
					<td id="aodEmpDistinctNo<s:property value="#st.count"/>" align="center">
						&nbsp;<s:property value="attdEmp.empDistinctNo" />
					</td>
					<td id="aodEmpName<s:property value="#st.count"/>" align="center">
						&nbsp;<s:property value="attdEmp.empName" />
					</td>
					<td id="aodEmpDept<s:property value="#st.count"/>" align="center">
						&nbsp;<s:property value="attdEmp.empDeptNo.departmentName" />
					</td>
							
					<td id="aodAttdDate<s:property value="#st.count"/>" align="center">
						&nbsp;<s:property value="aodAttdDate" />
				    </td>
					<td id="aodCardTime<s:property value="#st.count"/>" align="center">
						&nbsp;<s:date name="aodCardTime" format="yyyy-MM-dd HH:mm:ss" />
					</td>
					<td id="aodTtdMachineNo<s:property value="#st.count"/>" align="center">
						&nbsp;<s:property value="aodTtdMachineNo" />
					</td>
					<td id="aodStatus<s:property value="#st.count"/>" align="center">
						&nbsp;
						<s:if test="aodStatus==0">
						    导入
						</s:if><s:else>
						    补卡
						</s:else>
					</td> 		 
					<td id="aodMemo<s:property value="#st.count"/>" align="center">
						&nbsp;<s:property value="aodMemo" />
					</td>
					<td align="center" id="dataOperatin<s:property value="#st.count"/>" align="center">
					  <s:if test="aodId!=null&&aodId!=''">
					    <a href="#" onclick="deleteOneData('<s:property value="aodId"/>')"><img src="../resource/images/deletesalaryconf.gif" alt='删除'  border='0'/></a>
					  </s:if>
					</td>
		        </tr>
            </s:iterator>
        </s:if>
		<s:else>
		    <tr>
		        <td align="center" colspan="17">无符合条件的考勤机刷卡数据！</td>
		    </tr>
		</s:else>
    </table>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="gridtableList">
		<tr class="listViewPaginationTdS1">
		  <td colspan="10" align="center">
		    <s:hidden id="page.currentPage" name="page.currentPage" />
		    <s:component template="pager">
		        <s:param name="pageNo" value="page.currentPage" /><s:param name="totalPages" value="page.totalPages" /><s:param name="totalRows" value="page.totalRows" />
        	  	    <s:param name="start" value="page.start" /><s:param name="end" value="page.end" /><s:param name="formId" value="'originalDataShow'" />
        	  	</s:component>
	  	  </td>
		</tr>
	</table>
</form> 
<div id="tmpletId" style="DISPLAY: none">
<img src="../resource/images/basic_search.gif" onload="hrm.common.check_order();"/> <!--改为调用common中方法-->
</div>

<div id="dlgEmpListDiv" title="用户选择列表">
<jsp:include flush="true" page="../examin/attdOperate.jsp"></jsp:include>
</div>

<script type="text/javascript" language="javascript">
//查询方法(点击查询按钮)
function searchOriginalData(){
    if(document.originalDataShow.attdDateFrom.value == ''){
        alert("请选择开始日期！");
        return;
    }
    if(document.originalDataShow.attdDateTo.value == ''){
        alert("请选择结束日期！");
        return;
    }
    document.forms[0].submit();//提交表单
}

//数据清理操作(点击清理按钮)
function deleteOriginalData(){
    if(document.originalDataShow.attdDateFrom.value == ''){
        alert("请选择开始日期！");
        return;
    }
    if(document.originalDataShow.attdDateTo.value == ''){
        alert("请选择结束日期！");
        return;
    }
    var start = document.originalDataShow.attdDateFrom.value;
    var end = document.originalDataShow.attdDateTo.value;
    if(confirm("确定要删除" + start + "到" + end + "区间内的数据么？")){ //confirm确认是否删除
    	document.all.operate.value = "deleteSome";
        document.forms[0].submit();
    }
}

//删除一条数据
function deleteOneData(aodId){
    if(confirm("确定要删除该条数据么？")){
        document.all.aodId.value = aodId;
        document.all.operate.value = "deleteOne";
        document.forms[0].submit();
    }
}

//敲击键盘事件
function enterDown(event){
    event = event ? event : (window.event ? window.event : null);
    if(event!=null && event.keyCode==13){
        searchEmp(document.getElementById('emp_sear_value').value);  
        return;                              
    }
}

$('#attdDate').val(hrm.common.getCurrentDate()); //给attdDate赋上当天日期
</script>
<jsp:include flush="true" page="../io/div_upload.jsp"></jsp:include>
</body>	
