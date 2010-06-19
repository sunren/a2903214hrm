<%@page pageEncoding="GBK" contentType="text/html; charset=GBK" %>
<%@taglib prefix="s" uri="webwork"%>
<%@taglib prefix="ty" uri="/tengsource"%>
<!--
 =========================================================
' File:examinsynchronize_record_dataShow.jsp
' Auth:chenhaibin
' Version:1.5.0 standard version
' Date: 2010-02-09
' Script Written by tengsource.com
'=========================================================
' Copyright   2007 tengsource.com. All rights reserved.
' Web: http://www.tengsource.com
' Email: admin@tengsource.com
'=======================================================
 -->
<head>
<title>考勤机数据同步</title>
<jsp:include flush="true" page="/sitemesh/jsPackage.jsp"></jsp:include>
<script type='text/javascript' src='../dwr/interface/DwrForAttend.js'></script>
<script type='text/javascript' src='../dwr/interface/DwrSyncAttdMachine.js'></script>
<s:head/> 
</head>
<body onload="hrm.common.check_order();"> 
<span class="errorMessage" id="message"></span>
<s:component template="bodyhead">
	<s:param name="pagetitle"  value="'考勤机数据同步'" />
	<s:param name="helpUrl" value="'26'" />
</s:component>
<form id="attdSyncRecordShow" name="attdSyncRecordShow" method="POST" namespace="/examin" action="attdSyncRecordShow.action">
    <!-- 隐藏字段 -->
    <s:hidden name="operate"></s:hidden>
    <s:hidden name="asrId"></s:hidden>
    <s:hidden name="iodefId"></s:hidden>
    <s:hidden id="order" name="page.order"/>
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="formtable">
		<tr>
			<td>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
					    <td align="right">员工:</td>
					    <td><s:textfield id="emp" name="emp" size="16" maxlength="64"/></td>
					    <td align="right">组织单元:</td>
					    <td>
					    	<s:orgselector id="orgselector1" name="deptName" hiddenFieldName="dept"/>
					    </td>
						<td align="right">考勤机号:</td>
						<td><s:select name="machineNo" list="machineList" listKey="macId" listValue="macNo" multiple="false" emptyOption="true" value="machineNo" size="1" /></td>
						<td align="right">是否同步:</td>
						<td><s:select id="sync" name="sync" value="sync" list="#{2:'全部',0:'否',1:'是'}"  emptyOption="false"/></td>
					</tr>
				</table>
			</td>
			<td>				
				<input type="button" class="button" value="查询" onclick="searchData();"/>
			    <input title="[Alt+C]" accesskey="C" name="clear_button" class="button" type="button" onClick="window.location='attdSyncRecordShow.action';" value="重置">
			</td>
		</tr>
	</table>
<table width="100%">
  <tr>
	<td><br/></td>
  </tr>
  <tr>
	<td align="left">
	<ty:auth auths="401,3 or 401,2">
		<input type="button" class="button" value="同步到考勤机" onclick="empSelecotr('syncToMachine');"/>
		<input type="button" class="button" value="检查同步" onclick="empSelecotr('syncToProject');"/>
		<input type="button" class="button" value="删除考勤用户" onclick="empSelecotr('batchDelete');"/>
		<input type="button" class="button" value="读取考勤数据" onclick="empSelecotr('batchRead');"/>
	</ty:auth>
	</td>
	<td align="right">本次查询共得到<s:property value="page.totalRows" />条考勤机数据同步记录&nbsp;</td>
  </tr>
</table>

    <table width="100%" border="0" cellspacing="0" cellpadding="0" id="dataTable" class="gridtableList">
        <tr>
	     	<th nowrap="nowrap"> 
	     		<a href="#" onclick="hrm.common.order_submit('emp.empDistinctNo','attdSyncRecordShow');">
	     		员工工号<img src='../resource/images/arrow_.gif' width='8' height='10' id='emp.empDistinctNo_img'></a>
	     	</th>
	     	<th nowrap="nowrap">
	     		<a href="#" onclick="hrm.common.order_submit('emp.empName','attdSyncRecordShow');">
	     		姓名<img src='../resource/images/arrow_.gif' width='8' height='10' id='emp.empName_img'></a>
	     	</th>
	     	<th nowrap="nowrap">
	     		<a href="#" onclick="hrm.common.order_submit('emp.empDeptNo','attdSyncRecordShow');">
	     		部门<img src='../resource/images/arrow_.gif' width='8' height='10' id='emp.empDeptNo_img'></a>
	     	</th>
	     	<th nowrap="nowrap">
	     		<a href="#" onclick="hrm.common.order_submit('asrEmpCardNo','attdSyncRecordShow');">
	     		考勤卡号<img src='../resource/images/arrow_.gif' width='8' height='10' id='asrEmpCardNo_img'></a>
	     	</th>
	     	<th nowrap="nowrap">
	     		<a href="#" onclick="hrm.common.order_submit('asrAttdMachine.macNo','attdSyncRecordShow');">
	     		考勤机号<img src='../resource/images/arrow_.gif' width='8' height='10' id='asrAttdMachine.macNo_img'></a>
	     	</th>
	     	<th nowrap="nowrap">
	     		<a href="#" onclick="hrm.common.order_submit('emp.empMachineNo','attdSyncRecordShow');">
	     		考勤号码<img src='../resource/images/arrow_.gif' width='8' height='10' id='emp.empMachineNo_img'></a>
	     	</th>
	     	<th nowrap="nowrap">
	     		<a href="#" onclick="hrm.common.order_submit('asrSync','attdSyncRecordShow');">
	     		是否同步<img src='../resource/images/arrow_.gif' width='8' height='10' id='asrSync_img'></a>
	     	</th>
        </tr>
        <s:if test="!attdSyncRecordList.isEmpty()"> <!--判断是否为空-->
			<s:iterator value="attdSyncRecordList" status="st">
        <tr>
			<td id="srcEmpDistinctNo<s:property value="#st.count"/>" align="center">
				&nbsp;<s:property value="asrEmp.empDistinctNo" />
			</td>
			<td id="asrEmpName<s:property value="#st.count"/>" align="center">
				&nbsp;<s:property value="asrEmp.empName" />
			</td>
			<td id="asrEmpDept<s:property value="#st.count"/>" align="center">
				&nbsp;<s:property value="asrEmp.empDeptNo.departmentName" />
			</td>
			<td id="asrEmpCardNo<s:property value="#st.count"/>" align="center">
				&nbsp;<s:property value="asrEmpCardNo" />
			</td>
			<td id="asrAttdMachineNo<s:property value="#st.count"/>" align="center">
				&nbsp;<s:property value="asrAttdMachine.macNo" />
		    </td>
		    <td id="asrEmpMachineNo<s:property value="#st.count"/>" align="center">
				&nbsp;<s:property value="asrEmp.empMachineNo" />
		    </td>
			<td id="asrSync<s:property value="#st.count"/>" align="center">
				&nbsp;<s:if test="asrSync==1">是</s:if>
				<s:if test="asrSync==0">否</s:if>
			</td>	 
        </tr>
            
            </s:iterator>
        </s:if>
		<s:else>
		    <tr>
		        <td align="center" colspan="17">无符合条件的考勤机数据同步记录！</td>
		    </tr>
		</s:else>
    </table>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="gridtableList">
		<tr class="listViewPaginationTdS1">
		  <td colspan="10" align="center">
		    <s:hidden id="page.currentPage" name="page.currentPage" />
		    <s:component template="pager">
		        <s:param name="pageNo" value="page.currentPage" /><s:param name="totalPages" value="page.totalPages" /><s:param name="totalRows" value="page.totalRows" />
        	  	    <s:param name="start" value="page.start" /><s:param name="end" value="page.end" /><s:param name="formId" value="'attdSyncRecordShow'" />
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
function searchData(){
    document.forms[0].submit();//提交表单
}

//敲击键盘事件
function enterDown(event){
    event = event ? event : (window.event ? window.event : null);
    if(event!=null && event.keyCode==13){
        return;                              
    }
}

hrm.common.initDialog('dlgEmpListDiv',480);

$('#attdDate').val(hrm.common.getCurrentDate()); //给attdDate赋上当天日期


function empSelecotr(operate){
	switchOperate(operate);
	hrm.common.openDialog('dlgEmpListDiv');
}
</script>
<jsp:include flush="true" page="../io/div_upload.jsp"></jsp:include>
</body>	
