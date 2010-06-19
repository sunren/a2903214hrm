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
<script type="text/javascript" src="../resource/js/hrm/profile.js"></script>
<script type="text/javascript" src="../dwr/interface/ContractManagement.js"></script>
<title>合同管理</title>
</head>
<body onload="hrm.common.check_order();">
	<s:component template="bodyhead">
		<s:param name="pagetitle" value="'在职员工合同管理'" />
		<s:param name="helpUrl" value="'32'" />
	</s:component>
	<s:form id="listForm" name="listForm" action="manageContract" namespace="/profile" method="post" enctype="multipart/form-data">
	<s:hidden id="output-ommId" name="outmatchModelId"/>
	<s:hidden id="output-ioName" name="outputIoName"/>
	<div id="div1" style="DISPLAY: block;clear : both">
		<s:hidden name="ectId" id="ectId"></s:hidden>
		<table width="100%" border="0" cellspacing="2" cellpadding="0" style="background-color: #ECF6FB;border: 1px #6BB5DA solid">
			<tr>
				<td align="right">&nbsp;&nbsp;&nbsp;&nbsp;员工：</td>
				<td>
				<s:textfield name="employee.empName" size="16" maxlength="64"/>
				</td>
				<td>合同种类：
					<s:select name="searchContract.contractType.id" list="empTypeList" listKey="id" listValue="ectName" emptyOption="true"/>
				</td>
				<td>
					起始日期：
					<s:textfield id="searchContractEctStartDate" name="searchContract.ectStartDate" size="10"/>
					<img onclick="WdatePicker({el:'searchContractEctStartDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
					到：
					<s:textfield id="searchContractEctStartDateTo" name="ectStartDateTo" size="10"/>
					<img onclick="WdatePicker({el:'searchContractEctStartDateTo'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
				</td>
				<td rowspan="3" align="left">
					<input title="[Alt+F]" accesskey="F" id="submit_button" class="button" type="submit" value="查询" onclick="document.getElementById('searchOrExport').value='';">
					<input title="[Alt+C]" accesskey="C" class="button" type="reset" value="重置" onclick="window.location='manageContract.action'">
				</td>
			</tr>
			<tr>
				<td align="right">&nbsp;&nbsp;&nbsp;&nbsp;组织单元：
				</td>
				<td> 
				   <s:orgselector id="orgselector1" name="employee.empDeptNo.departmentName" hiddenFieldName="employee.empDeptNo.id"/>
				</td>
				<td>合同期限：
					<s:select name="searchContract.etcExpire" list="#{'':'请选择','0':'有限期','1':'无限期'}"/>
				</td>
				<td>
					结束日期：
					<s:textfield id="searchContractEctEndDate" name="searchContract.ectEndDate" size="10"/>
					<img onclick="WdatePicker({el:'searchContractEctEndDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
					到：
					<s:textfield id="searchContractEctEndDateTo" name="ectEndDateTo" size="10"/>
					<img onclick="WdatePicker({el:'searchContractEctEndDateTo'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
				</td>
			</tr>
			<tr>
				<td align="right">合同状态：
					
				</td>
				<td>
				<s:select name="searchContract.ectStatus" list="#{'':'请选择','1':'有效','2':'终止','3':'解除','4':'未签','5':'未续签'}"/>
				</td>
				<td>合同到期：
					<s:select name="toExpire" list="#{'':'请选择','-1':'已过期','15':'15天','30':'30天','45':'45天','90':'90天'}"/>
				</td>
				<td>合同备注：<s:textfield name="searchContract.ectComments" size="20" maxlength="128"/></td>
			</tr>
		</table>
	</div>
	<br/>
	<table width="100%">
	  <tr>
		<td align="left">
			<input type="hidden" name="searchOrExport" id="searchOrExport" />
			<ty:auth auths="101">
				<input type="button" value="创建合同" id="create" name="create" onclick="showCreate()" class="button"/>
				<input type="button" value="批量续签" id="continuous" name="continuous" onclick="showContinuous()" class="button"/>
				<input type="button" value="解除合同" id="repeal" name="repeal" onclick="repealContract()" class="button"/>
			</ty:auth>
			<ty:auth auths="101,2 or 101,3">
				<input class="button" type="button" id="btnOutput" value="数据导出" onclick="export_check('export');"/>
			</ty:auth>
		</td>
		<td align="right">本次查询共得到<s:property value="page.totalRows" />名在职员工记录&nbsp;</td>
	  </tr>
	</table>

	<table cellpadding="0" cellspacing="0" width="100%" border="0" class="gridtableList">
		<s:hidden id="order" name="page.order" />
		<s:hidden id="operate" name="page.operate" />
	    <tr>
	    	<th align="center" width="3%"><input id='changIds_choose' name='changIds_choose' class="checkbox" type="checkbox" onclick="hrm.common.checkAllByName('changIds','changIds_choose');checkCanDo()" value="0"></th>
	     	<th nowrap="nowrap"><a href="#" onclick="hrm.common.order_submit('empDistinctNo', 'listForm');">员工编号</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='empDistinctNo_img'></th>
	     	<th nowrap="nowrap"><a href="#" onclick="hrm.common.order_submit('empName', 'listForm');">姓名</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='empName_img'></th>
	     	<th nowrap="nowrap"><a href="#" onclick="hrm.common.order_submit('empDeptNo.departmentName', 'listForm');">部门</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='empDeptNo.departmentName_img'></th>
	     	<th nowrap="nowrap"><a href="#" onclick="hrm.common.order_submit('empJoinDate', 'listForm');">司龄</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='empJoinDate_img'></th>
	     	<th nowrap="nowrap"><a href="#" onclick="hrm.common.order_submit('con.ectStatus', 'listForm');">状态</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='con.ectStatus_img'></th>
	     	<th nowrap="nowrap"><a href="#" onclick="hrm.common.order_submit('con.ectStartDate', 'listForm');">起始日期</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='con.ectStartDate_img'></th>
	     	<th nowrap="nowrap"><a href="#" onclick="hrm.common.order_submit('con.ectEndDate', 'listForm');">结束日期</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='con.ectEndDate_img'></th>
	     	<th nowrap="nowrap"><a href="#" onclick="hrm.common.order_submit('conType.ectName', 'listForm');">合同种类</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='conType.ectName_img'></th>
	     	<th nowrap="nowrap"><a href="#" onclick="hrm.common.order_submit('con.etcExpire', 'listForm');">期限</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='con.etcExpire_img'></th>
	     	<th nowrap="nowrap"><a href="#" onclick="hrm.common.order_submit('con.ectComments', 'listForm');">备注</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='con.ectComments_img'></th>
	     	<ty:auth auths="101">
	     		<th nowrap="nowrap"><a href="#" onclick="hrm.common.order_submit('con.ectLastChangeTime', 'listForm');">操作</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='con.ectLastChangeTime_img'></th>
	     	</ty:auth>
	    </tr>
	    <tbody>
		<s:if test="!contractList.isEmpty()">
		     <s:iterator value="contractList">
				<tr id="<s:property value='id'/>">
					<td align="center" width="3%">
						<input type="hidden" id="pract<s:property value='id'/>" value="<s:property value="practiceMonth"/>"/>
						<input type="hidden" id="john<s:property value='id'/>" value="<s:property value="empJoinDate"/>"/>
						<input type="hidden" id="confirm<s:property value='id'/>" value="<s:property value="empConfirmDate"/>"/>
						<input type="checkbox" onclick="checkCanDo()" name="changIds" class="checkbox" value="<s:property value='id'/>" />
					</td>
					<td nowrap="nowrap">
						<a class="listViewTdLinkS1" href="myInfo.action?empNo=<s:property value="id"/>&empName=<s:property value="empName"/>&tab=3">
						<s:property value="empDistinctNo"/>
						</a>
					</td>
					<td nowrap="nowrap"><s:property value="empName"/></td>
					<td nowrap="nowrap"><s:property value="empDeptNo.departmentName"/></td>
					<td nowrap="nowrap"><s:property value="getYearOrMonth(joinYear)"/></td>
					<s:if test="contract != null&&contract.ectId != ''">
					    <input type="hidden" id="ListectId<s:property value='id'/>" value="<s:property value="contract.ectId"/>"/>
					    <input type="hidden" id="ectNo<s:property value='id'/>" value="<s:property value="contract.ectNo"/>"/><%-- 合同编号--%>
					    <input type="hidden" id="ectStatus<s:property value='id'/>" value="<s:property value="contract.ectStatus"/>"/><%-- 合同状态--%>
						<td nowrap="nowrap">
							<input type="hidden" id="stat<s:property value='id'/>" value="<s:property value="contract.ectStatus"/>"/>
							<s:if test="contract.ectStatus==1">有效</s:if>
							<s:if test="contract.ectStatus==2">终止</s:if>
							<s:if test="contract.ectStatus==3">解除</s:if>
						</td>
						<td id="startDateTD<s:property value='id'/>" nowrap="nowrap"><s:property value="contract.ectStartDate"/></td>
						<td id="endDateTD<s:property value='id'/>" nowrap="nowrap"><s:property value="contract.ectEndDate"/></td>
						<td nowrap="nowrap">
							<input type="hidden" id="type<s:property value='id'/>" value="<s:property value="contract.contractType.id"/>"/>
							<s:property value="contract.contractType.ectName"/>
						</td>
						<td nowrap="nowrap">
							<input type="hidden" id="exp<s:property value='id'/>" value="<s:property value="contract.etcExpire"/>"/>
							<s:if test="contract.etcExpire==0">有限期</s:if>
							<s:if test="contract.etcExpire==1">无限期</s:if>
						</td>
						<td><s:property value="contract.ectComments"/></td>
					
						<td nowrap="nowrap" align="right">
							<s:if test="contract.ectAttatchment!='' && contract.ectAttatchment!=null">
								<a id="href_<s:property value='contract.ectId'/>" href="downProfile.action?fileName=<s:property value="contract.ectAttatchment"/>&contentDisposition=">
								<img src="../resource/images/attachment.gif"/>
								</a>
								<input type="hidden" id="attach<s:property value='id'/>" value="<s:property value="contract.ectAttatchment"/>"/>
								<script type="text/javascript">
									var filename = '<s:property value="empDistinctNo"/>'+"_";
									filename += '<s:property value="contract.ectStartDate"/>'+"_";
									<s:if test="contract.ectEndDate != null">
										filename += '<s:property value="contract.ectEndDate"/>'+"_";
									</s:if>
									var exp = '<s:property value="contract.etcExpire"/>';
									filename += (exp=='0')?'有限期':'无限期';
									var extend = '<s:property value="contract.ectAttatchment"/>';
									filename += "."+extend.split('.')[1];
									var tmp = 'downProfile.action?fileName=<s:property value="contract.ectAttatchment"/>&contentDisposition=';
									document.getElementById("href_"+"<s:property value='contract.ectId'/>").href = tmp + filename;
								</script>
							</s:if>
							<ty:auth auths="101">
								<a href="#" onclick="showUpdate('<s:property value="id"/>','<s:property value="empName"/>')">
								<img src="../resource/images/edit.gif" alt="修改"></img>
								</a>
								<a href="#" onclick="showCopy('<s:property value="id"/>','<s:property value="empName"/>')">
								<img src="../resource/images/ProjectCopy.gif" alt="续签"></img>
								</a>
				   				<a href="#" onclick="ectDel('<s:property value='contract.ectId'/>','<s:property value="empName"/>')">
				   				<img src="../resource/images/delete.gif" alt="删除"></img>
				   				</a>
							</ty:auth>
						</td>
					</s:if>
					<s:else>
						<td>未签</td><td colspan="5"></td>
						<td nowrap="nowrap" align="right">
							<s:if test="#request.hasAuth=='has'">
								<a href="#" onclick="showAdd('<s:property value="id"/>','<s:property value="empName"/>')">
								<img src="../resource/images/CreateProject.gif" alt="签约"></img>
								</a>
							</s:if>
						</td>
					</s:else>
				</tr>
			 </s:iterator>
		</s:if>
		<s:else>
		 	<tr>
		 		<td colspan="12" align="center">无相关记录！</td>
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
</s:form>
<!-- 单个人事合同 -->
<div id="dlgAddContract" style="width:425; display:none;" class="prompt_div_inline">	
  	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  		<tr>
  			<td>
  				<table width="100%" class="prompt_div_body" cellpadding="0" border="0">
					<tr>
						<td align="left" nowrap="nowrap">起始日期<span class="required">*</span>：</td>
						<td>
							<s:textfield id="ectStartDate" name="ectStartDate" required="true" size="10"/>
							<img onclick="WdatePicker({el:'ectStartDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
							&nbsp;
							<span id="confirmMonth2"/>
						</td>
					</tr>
					<tr>
						<td align="left" nowrap="nowrap">终止日期<span class="required">*</span>：</td>
						<td>
							<s:textfield id="ectEndDate" name="ectEndDate" required="true" size="10"/>
							<img onclick="WdatePicker({el:'ectEndDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
							&nbsp;
							<span id="practiseMonth2"/>
						</td>
					</tr>
					<tr>
						<td align="left" nowrap="nowrap">合同编号：</td>
						<td><input id="ectNo" name="ectNo"/></td>
					</tr>
					<tr>
						<td align="left" nowrap="nowrap">合同种类<span class="required">*</span>：</td>
						<td>
							<s:select id="empTypeId" name="empTypeId" list="empTypeList" listKey="id" listValue="ectName"></s:select>
						</td>
					</tr>
					<tr>
						<td align="left" nowrap="nowrap">合同期限<span class="required">*</span>：</td>
						<td>
							<select id="etcExpire" name="etcExpire" onChange="if(this.value=='1')document.getElementById('ectEndDate').disabled=true;else document.getElementById('ectEndDate').disabled=false;">
								<option value="0">有限期</option>
								<option value="1">无限期</option>
							</select>
						</td>
					</tr>
					<tr>
						<td align="left" nowrap="nowrap">合同状态<span class="required">*</span>：</td>
						<td>
							<select id="ectStatus" name="ectStatus">
								<option value="1">有效</option>
								<option value="2">终止</option>
								<option value="3">解除</option>
							</select>
						</td>
					</tr>
					<tr>
						<td align="left" nowrap="nowrap">备注：</td>
						<td>
							<textarea id="ectComments" name="ectComments" rows="8" cols="40"></textarea>
						</td>
					</tr>
					<tr>
						<td align="left" nowrap="nowrap">附件：</td>
						<td>
							<input name="file" type="file" size="25" maxlength="128"/>
							<span id="ectAttach"></span>
						</td>
					</tr>
					<tr height="35">
						<td colspan="2" align="center">
						    <input type="hidden" id="updateEctId" name="updateEctId"/>	
						    <input type="hidden" id="continueEctId" name="continueEctId"/>	
							<input type="hidden" name="input" value="1"/>
							<input type="hidden" id="employeeId" name="employeeId"/>
							<input id="ectAddbtn" type="button" value="增加" onClick="singleContract()"/>
							<input id="ectCopybtn" type="button" value="续签" onClick="singleContinueContract()"/>
							<input id="ectUpdatebtn" type="button" value="修改" onClick="doUpdate()"/>
							<input type="button" value="关闭" onclick="hrm.common.closeDialog('dlgAddContract');"/>
						</td>
					</tr>
				</table>
			</td>
  		</tr>
	</table>
</div>
<!-- 批量人事合同 -->
<div id="dlgBatchContract" style="width:425; display:none;" class="prompt_div_inline" title="批量人事合同">	
  	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  		<tr>
  			<td>
  				<s:hidden name="ids" id="ectBatchForm_ids"/>
  				<s:hidden name="ectIds" id="ectBatchForm_ectIds"/>
  				<table width="100%" class="prompt_div_body" cellpadding="0" border="0">
					<tr id="trRadio">
						<td align="left" nowrap="nowrap">续签方式<span class="required">*</span>：</td>
						<td>
							<input type="radio" class="radio" id="radioSub1" name="radioSub" onclick="changeSelectRadio()" value="1" checked="checked"></input>按年限
							<input type="radio" class="radio" id="radioSub2" name="radioSub" onclick="changeSelectRadio()" value="0"></input>按日期
						</td>
					</tr>
					<tr id="tr3">
						<td align="left" nowrap="nowrap">续签年限<span class="required">*</span>：</td>
						<td>
							<select name="continueYear" id="continueYear">
								<option value="1">1年</option>
								<option value="2">2年</option>
								<option value="3">3年</option>
								<option value="4">4年</option>
								<option value="5">5年</option>
							</select>
						</td>
					</tr>
					<tr id="tr1">
						<td align="left" nowrap="nowrap">起始日期<span class="required">*</span>：</td>
						<td>
							<s:textfield id="batchStartDate" name="batchStartDate" required="true" size="10"/>
							<img onclick="WdatePicker({el:'batchStartDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
							&nbsp;
							<span id="practiseMonth"/>
						</td>
					</tr>
					<tr id="tr2">
						<td align="left" nowrap="nowrap">终止日期<span class="required">*</span>：</td>
						<td>
							<s:textfield id="batchEndDate" name="batchEndDate" required="true" size="10"/>
							<img onclick="WdatePicker({el:'batchEndDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
						</td>
					</tr>
					<tr>
						<td align="left" nowrap="nowrap">合同种类<span class="required">*</span>：</td>
						<td>
							<select name="batchempTypeId" id="batchContractType">
								<s:iterator value="empTypeList">
									<option value="<s:property value='id'/>"><s:property value="ectName"/></option>
								</s:iterator>
							</select>
						</td>
					</tr>
					<tr>
						<td align="left" nowrap="nowrap">合同期限<span class="required">*</span>：</td>
						<td>
							<select name="batchExpire" id="batchExpire" onChange="changeExpire();">
								<option value="0">有限期</option>
								<option value="1">无限期</option>
							</select>
						</td>
					</tr>
					<tr>
						<td align="left" nowrap="nowrap">合同状态<span class="required">*</span>：</td>
						<td>
							<select name="batchStatus" id="batchStatus">
								<option value="1">有效</option>
								<option value="2">终止</option>
								<option value="3">解除</option>
							</select>
						</td>
					</tr>
					<tr>
						<td align="left" nowrap="nowrap">备注：</td>
						<td><textarea name="batchComments" id="batchComments" rows="8" cols="40"></textarea></td>
					</tr>
					<tr height="35">
						<td colspan="2" align="center">
							<input id="batchAddbtn" type="button" value="增加" onClick="batchCreate();"/>
							<input id="batchContinubtn" type="button" value="续签" onClick="batchContinuous()"/>
							<input type="button" value="关闭" onclick="hrm.common.closeDialog('dlgBatchContract');"/>
						</td>
					</tr>
				</table>
  			</td>
  		</tr>
	</table>
</div>
<script type="text/javascript">
//单个合同dialog初始化
hrm.common.initDialog('dlgAddContract');
//批量创建合同dialog初始化
hrm.common.initDialog('dlgBatchContract');
//检查多选框，确定可执行按钮
function checkCanDo(){
	var flagCreator=0,flagRepeal=0;
	var checkBoxs=document.getElementsByName('changIds');

	for(var i=0;i<checkBoxs.length;i++) {
		if(checkBoxs[i].checked==true) {
			if(document.getElementById('stat'+checkBoxs[i].value)==null) {
				document.getElementById('continuous').disabled = true;
				document.getElementById('repeal').disabled = true;
				flagRepeal = 1;
			}else {
				document.getElementById('create').disabled = true;
				flagCreator = 1;
			}
		}
	}
	if(flagCreator == 0) {
		document.getElementById('create').disabled = false;
	}
	if(flagRepeal == 0) {
		document.getElementById('continuous').disabled = false;
		document.getElementById('repeal').disabled = false;
	}
}
//设置某个标签的值
function setSelectValue(id,value){
	var select = document.getElementById(id);
	for(var i = 0; i < select.options.length; i++){
		if(select.options[i].value == value){
			select.options[i].selected = true;
			break;
		}
	}
}
//表单字段验证
function ectVali() {
    if(!borcVali('ect')) {
		return false;
	}
	return true;
}
//表单字段验证
function borcVali(borc) {
    var cpStartDate = borc+'StartDate';
    var cpEndDate = borc+'EndDate';
	if(!hrm.common.isDate(document.getElementById(cpStartDate).value)) {
		alert('员工合同起始日期不能为空或格式不正确！');
		return false;
	}
	var exp = document.getElementById('etcExpire').value;
	if(exp != '1'){
		if(!hrm.common.isDate(document.getElementById(cpEndDate).value)) {
			alert('员工合同结束日期不能为空或格式不正确！');
			return false;
		}
	}
	startDatevalue = document.getElementById(cpStartDate).value;
	ectEndDate   = document.getElementById(cpEndDate).value;
	var startDate = startDatevalue.replace(/-/g, "/");
	var endDate = ectEndDate.replace(/-/g, "/");
	var dS = new Date(startDate);//开始日期
	var dE = new Date(endDate);//结束日期
	if(dS.getTime()>dE.getTime()){
		alert("员工合同开始时间不能晚于结束时间！");
		return false;
	}
	return true;
}
//改变Radio的操作
function changeSelectRadio(){
	if(document.getElementById('radioSub1').checked){
		document.getElementById('continueYear').disabled=false;
		document.getElementById('batchStartDate').disabled=true;
		document.getElementById('batchEndDate').disabled=true;
	}else if(document.getElementById('radioSub2').checked){
		document.getElementById('continueYear').disabled=true;
		document.getElementById('batchStartDate').disabled=false;
		document.getElementById('batchEndDate').disabled=false;
	}
}
//单个修改合同
function showUpdate(empId,empName) {
	document.getElementById('etcExpire').value=0; //合同期限
	document.getElementById('ectEndDate').disabled=false;
	document.getElementById('practiseMonth2').innerHTML =  "";
	document.getElementById('confirmMonth2').innerHTML =  "";
  	//editObj = document.getElementById(empId);
  	document.getElementById('ectAddbtn').style.display="none";
	document.getElementById('ectCopybtn').style.display="none";
	document.getElementById('ectUpdatebtn').style.display="inline";
	document.getElementById('employeeId').value=empId;
	$('#dlgAddContract').dialog("option","title","人事合同（"+empName+"）");
	hrm.common.openDialog('dlgAddContract','listForm');
	document.getElementById('updateEctId').value=document.getElementById('ListectId'+empId).value;
	document.getElementById('ectStartDate').value = document.getElementById('startDateTD'+empId).innerHTML.trim();//开始日期
	document.getElementById('ectEndDate').value = document.getElementById('endDateTD'+empId).innerHTML.trim();//结束日期
	document.getElementById('ectNo').value = document.getElementById('ectNo'+empId).value;//合同编号
	setSelectValue('empTypeId',document.getElementById('type'+empId).value);//合同种类
	setSelectValue('etcExpire',document.getElementById('exp'+empId).value);//合同期限
	setSelectValue('ectStatus',document.getElementById('ectStatus'+empId).value);//合同状态
	document.getElementById('ectComments').value = document.getElementById(empId).cells[10].innerHTML.trim();//合同备注
}
//提交单个修改合同
function doUpdate(){
	var rs = ectVali();
	
	if(rs){
		document.getElementById('listForm').action = "updateContract.action";
		document.getElementById('listForm').submit();
	}else{
		return;
	}
}
//单个续签合同
function showCopy(empId,empName) {
    document.getElementById('etcExpire').value=0;
    document.getElementById('ectEndDate').disabled=false;
	document.getElementById('practiseMonth2').innerHTML =  "";
	document.getElementById('confirmMonth2').innerHTML =  "";
	document.getElementById('ectAddbtn').style.display="none";
	document.getElementById('ectCopybtn').style.display="inline";
	document.getElementById('ectUpdatebtn').style.display="none";
	document.getElementById('employeeId').value=empId;
	document.getElementById('continueEctId').value=document.getElementById('ListectId'+empId).value;
	hrm.profile.setTimeNextYear(document.getElementById("endDateTD"+empId).innerHTML,document.getElementById('ectStartDate'),document.getElementById('ectEndDate'));
	ContractManagement.checkOldEmployee(empId,checkCallback);
	function checkCallback(msg) {
		if(msg=='success'){
			$('#dlgAddContract').dialog("option","title","人事合同（"+empName+"）");
			hrm.common.openDialog('dlgAddContract','listForm');
		   return;
		}else if(msg=='fail') {
		    alert("该员工已签订无限期合同,不能再续签！");
			return;
		}else{
			alert(msg);
			document.getElementById('etcExpire').value=1;
			document.getElementById('ectEndDate').disabled=true;
			$('#dlgAddContract').dialog("option","title","人事合同（"+empName+"）");
			hrm.common.openDialog('dlgAddContract','listForm');
		}
	}
}
//提交单个续签合同
function singleContinueContract(){
	if(ectVali()){
		document.getElementById('searchOrExport').value='';
		document.getElementById('listForm').action="empContractManContinue.action";
		document.getElementById('listForm').submit();
	}
}
//删除单个合同
function ectDel(ectId,empName) {
	//var aTd = editObj.cells;
	if(!window.confirm('确定删除'+empName+'的最新合同吗？')){ return ;}
	document.getElementById('ectId').value = ectId;
	document.getElementById('searchOrExport').value='';
	document.getElementById('listForm').action='deleteEmpContract.action';
	document.getElementById('listForm').submit();
}
//单个创建合同
function showAdd(empId,empName) {
    document.getElementById('etcExpire').value=0;
    document.getElementById('ectEndDate').disabled=false;
	document.getElementById('practiseMonth2').innerHTML =  "";
	document.getElementById('confirmMonth2').innerHTML =  "";
	document.getElementById('ectAddbtn').style.display="inline";
	document.getElementById('ectCopybtn').style.display="none";
	document.getElementById('ectUpdatebtn').style.display="none";
	document.getElementById('employeeId').value=empId;
	$('#dlgAddContract').dialog("option","title","人事合同（"+empName+"）");
	hrm.common.openDialog('dlgAddContract','listForm');
	var practValue=document.getElementById('pract'+empId).value;
	if(practValue != null && practValue != ''){
		document.getElementById('practiseMonth2').innerHTML =  '试用期：'+practValue+"个月";
		document.getElementById('confirmMonth2').innerHTML =  "转正日期："+document.getElementById('confirm'+empId).value;
	}
	var johnValue=document.getElementById('john'+empId).value;
	if(johnValue != null && johnValue != '') {
		hrm.profile.setTimeNextYear(johnValue,document.getElementById('ectStartDate'),document.getElementById('ectEndDate'));
	}else {
		hrm.profile.setTimeNextYear("",document.getElementById('ectStartDate'),document.getElementById('ectEndDate'));
	}
}
//提交单个创建合同
function singleContract(){
	if(ectVali()){
		document.getElementById('searchOrExport').value='';
		document.getElementById('listForm').action="empContractManAdd.action";
		document.getElementById('listForm').submit();
	}
}
//显示单个和批量创建合同
function showCreate() {
	var flag=0, practFlag = 0 ,johnFlag = 0;
	var checkBoxs=document.getElementsByName('changIds');
	var id = "";
	var empName = "";
	for(var i=0;i<checkBoxs.length;i++){
		if(checkBoxs[i].checked==true){
		   flag++;
		   id = checkBoxs[i].value;
		   empName = document.getElementById(id).cells[2].innerHTML.trim();
		}
 	}
 	if(flag==0){
 		alert("至少选择一个员工！");
			return;
	}else if(flag == 1){//单条创建合同，逻辑等于后面的那个创建合同按钮
		showAdd(id,empName);
		return;
	}

	var practValue = "start";
	var johnValue = "start";
	
	for(var i=0;i<checkBoxs.length;i++){
		if(checkBoxs[i].checked==true){
			if(practValue != "start" && practValue != document.getElementById('pract'+checkBoxs[i].value).value) {
				practFlag = 1;
			}else {
				practValue =  document.getElementById('pract'+checkBoxs[i].value).value;
			}
			if(johnValue != "start" && johnValue != document.getElementById('john'+checkBoxs[i].value).value) {
				johnFlag = 1;
			}else {
				johnValue = document.getElementById('john'+checkBoxs[i].value).value;
			}
		}
	}
 	document.getElementById('practiseMonth').innerHTML="";
 	document.getElementById('tr1').style.display="";
 	document.getElementById('tr2').style.display="";
 	document.getElementById('tr3').style.display="none";
 	document.getElementById('trRadio').style.display="none";
 	document.getElementById('radioSub2').checked=true;
 	changeSelectRadio();
 	hrm.common.openDialog('dlgBatchContract','listForm');
	document.getElementById('batchAddbtn').style.display = "inline";
	document.getElementById('batchContinubtn').style.display = "none";
	
	if(practFlag == 0){
		if(practValue != '') {
			document.getElementById('practiseMonth').innerHTML =  '试用期：'+practValue+"个月";
		}
	}
	if(johnFlag == 0) {
		hrm.profile.setTimeNextYear(johnValue,document.getElementById('batchStartDate'),document.getElementById('batchEndDate'));
	}else {
		hrm.profile.setTimeNextYear("",document.getElementById('batchStartDate'),document.getElementById('batchEndDate'));
	}
}
//提交批量创建合同
function batchCreate() {
	var checkBoxs=document.getElementsByName('changIds');
	var tmp = "";
	for(var i=0;i<checkBoxs.length;i++){
		if(checkBoxs[i].checked==true){
			tmp += checkBoxs[i].value+',';
		}
 	}
 	if(tmp.length==0){
 		alert("至少选择一条合同记录！");
		return;
	}
	if(!borcVali('batch')) {
		return;
	}
	tmp = tmp.substring(0,tmp.length-1);
	document.getElementById('ectBatchForm_ids').value = tmp;
	document.getElementById('searchOrExport').value='';
	document.getElementById('listForm').action="batchCreate.action";
	document.getElementById('listForm').submit();
}
//显示批量续签合同
function showContinuous() {
	document.getElementById('practiseMonth').innerHTML="";
	var flag=0;
	var checkBoxs=document.getElementsByName('changIds');
	var id = "";
	var empName = "";
	for(var i=0;i<checkBoxs.length;i++){
		if(checkBoxs[i].checked==true){
	   		flag++;
	   		id=checkBoxs[i].value;
			empName = document.getElementById(id).cells[2].innerHTML.trim();
		}
	}
 	if(flag==0){
 		alert("至少选择一条合同记录！");
		return;
	}
	else if(flag == 1){
		showCopy(id,empName);
		return;
	}
	document.getElementById('tr1').style.display="";
 	document.getElementById('tr2').style.display="";
 	document.getElementById('tr3').style.display="";
 	document.getElementById('trRadio').style.display="";
 	document.getElementById('continueYear').disabled=false;
	document.getElementById('batchStartDate').disabled=true;
	document.getElementById('batchEndDate').disabled=true;
	hrm.common.openDialog('dlgBatchContract','listForm');
	changeSelectRadio();
	document.getElementById('batchAddbtn').style.display = "none";
	document.getElementById('batchContinubtn').style.display = "inline";
	hrm.profile.setTimeNextYear('',document.getElementById('batchStartDate'),document.getElementById('batchEndDate'));
}
//提交批量续签合同
function batchContinuous() {
	var checkBoxs=document.getElementsByName('changIds');
 		var tmp = "";
 		var tmpectId="";
 		for(var i=0;i<checkBoxs.length;i++){
		if(checkBoxs[i].checked==true){
			tmp += checkBoxs[i].value+',';
			tmpectId+=document.getElementById('ListectId'+checkBoxs[i].value).value+',';
		}
 	}
 	if(tmp.length==0){
 		alert("至少选择一条合同记录！");
			return;
		}
	if(!borcVali('batch')) {
		return;
	}
	tmp = tmp.substring(0,tmp.length-1);
	tmpectId=tmpectId.substring(0,tmpectId.length-1);
	document.getElementById('ectBatchForm_ids').value = tmp;
	document.getElementById('ectBatchForm_ectIds').value = tmpectId;
	document.getElementById('searchOrExport').value='';
	document.getElementById('listForm').action="batchContinuous.action";
	document.getElementById('listForm').submit();
}
//解除合同
function repealContract() {
	var checkBoxs=document.getElementsByName('changIds');
	var tmp = "";
	var tmpectId="";
	for(var i=0;i<checkBoxs.length;i++){
		if(checkBoxs[i].checked==true){
			tmp += checkBoxs[i].value+',';
			tmpectId+=document.getElementById('ListectId'+checkBoxs[i].value).value+',';
		}
 	}
 	if(tmp.length==0){
 		alert("至少选择一条合同记录！");
		return;
	}
	if(window.confirm('确定要解除合同吗？')){ 
		tmp = tmp.substring(0,tmp.length-1);
		tmpectId=tmpectId.substring(0,tmpectId.length-1);
		document.getElementById('searchOrExport').value='';
		document.getElementById('ectBatchForm_ids').value = tmp;
	    document.getElementById('ectBatchForm_ectIds').value = tmpectId;
		document.getElementById('listForm').action='batchRepeal.action?ids='+tmp+'&ectIds='+tmpectId;
		document.getElementById('listForm').submit();
	}
}
//改变合同期限的动作
function changeExpire(){
	if(document.getElementById('batchExpire').value==1){
		document.getElementById('radioSub1').disabled=true;
		document.getElementById('radioSub2').disabled=true;
		document.getElementById('batchStartDate').disabled=false;
		document.getElementById('batchEndDate').disabled=true;
		document.getElementById('continueYear').disabled=true;
		
	}else {
		document.getElementById('radioSub1').disabled=false;
		document.getElementById('radioSub2').disabled=false;
		changeSelectRadio();
	}
}
//导出
function export_check(str){
	document.getElementById('searchOrExport').value=str;
	document.getElementById('listForm').submit();
	return;
}
</script>
<jsp:include flush="true" page="../io/div_omm_select.jsp"></jsp:include>
</body>	