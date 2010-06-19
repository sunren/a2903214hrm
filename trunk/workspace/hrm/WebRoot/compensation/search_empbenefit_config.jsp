<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ taglib prefix="s" uri="webwork"%>
<%@ taglib prefix="ty" uri="/tengsource"%>
<head>
	<title>员工社保管理</title>
	<link rel="stylesheet" type="text/css" href="../resource/css/style.css" />
	<script type="text/javascript" src="../dwr/interface/EmpBenefit.js"></script>
</head>
<body onLoad="HRMCommon.check_showHide();HRMCommon.check_order();">
	<s:component template="bodyhead">
		<s:param name="pagetitle" value="'员工社保管理'" />
		<s:param name="helpUrl" value="'32'" />
	</s:component>
	<span class="errorMessage" id="message"></span>
	<form id="searchEmpbenefitForm" name="searchEmpbenefitForm" action="searchEmpbenefit.action" method="post">
		<s:hidden id="year" name="year"/>
		<s:hidden id="output-ommId" name="outmatchModelId"/>
		<s:hidden id="output-ioName" name="outputIoName"/>
		<s:hidden id="month" name="month"/>
	    <s:hidden id="searchType" name="searchType" />
		<input type="hidden" name="searchOrExport" id="searchOrExport" />
		
		<table width="100%" class="formtable">
			<tr>
				<td>
					<s:hidden id="id" name="detailid" />
					<s:hidden id="order" name="page.order" />
					<input type="hidden" id="operate" name="page.operate" />
					<div id="basic" style="display: block; clear: both">
						<table width="100%" border="0" cellspacing="0" cellpadding="3">
							<tr>
								<s:textfield label="员工" id="empName" name="empNameOrDist"
									size="16" maxlength="64" />
								<td align="right">组织单元:</td>
								<td>
									<s:orgselector id="orgselector1" name="employee.empDeptNo.departmentName" hiddenFieldName="employee.empDeptNo.id" isShowDisable="show"/>
								</td>
								<s:select label="社保种类" id="empBenefitType"
									name="employee.empBenefitType.id" list="ebfTypeList"
									listKey="id" listValue="benefitTypeName" multiple="false"
									headerKey="" headerValue="请选择" />
								<s:select label="员工状态" id="empStatus" name="employee.empStatus"
									list="empStatus" listKey="id.statusconfNo"
									listValue="statusconfDesc" multiple="false" emptyOption="true"
									value="employee.empStatus" size="1" />
							</tr>
						</table>
					</div>
					<div id="advanced" style="DISPLAY: none; clear: both">
						<table width="100%" border="0" cellspacing="0" cellpadding="3">
							<tr>
								<s:textfield label="员工姓名" id="empNameAdv" name="employee.empName"
									size="16" maxlength="64" />
								<td align="right">组织单元:</td>
								<td>
									<s:orgselector id="orgselector2" name="employee.empDeptNo.departmentName" hiddenFieldName="employee.empDeptNo.id" isShowDisable="show"/>
								</td>
								<s:select label="社保种类" id="empBenefitTypeAdv"
									name="employee.empBenefitType.id" list="ebfTypeList"
									listKey="id" listValue="benefitTypeName" multiple="false"
									headerKey="" headerValue="请选择" />
								<s:select label="员工状态" id="empStatus" name="employee.empStatus"
									list="empStatus" listKey="id.statusconfNo"
									listValue="statusconfDesc" multiple="false" emptyOption="true"
									value="employee.empStatus" size="1" />
							</tr>
							<tr>
							    <s:textfield id="empNoAdv" label="员工编号"
									name="employee.empDistinctNo" size="15" maxlength="64" />
							    <s:textfield label="身份证号" id="empIdentificationNoAdv" name="employee.empIdentificationNo"
									size="15" maxlength="64" />
								<s:select label="用工形式" id="empType" name="employee.empType.id"
									list="empTypeList" listKey="id" listValue="emptypeName"
									multiple="false" headerKey="" headerValue="请选择" />
							    <s:select label="工作地区" id="empLocationNo"
									name="employee.empLocationNo.id" list="locationList" listKey="id"
									listValue="locationName" multiple="false" headerKey=""
									headerValue="请选择" />
							</tr>
							<tr>
                <s:textfield label="养老保险号" id="benefit.ebfPensionNoAdv" name="employee.benefit.ebfPensionNo"
									size="15" maxlength="64" />
								<s:textfield label="公积金号" id="benefit.ebfHousingNoAdv" name="employee.benefit.ebfHousingNo"
									size="15" maxlength="64" />
								<s:textfield label="医疗保险号" id="benefit.ebfMedicalNoAdv" name="employee.benefit.ebfMedicalNo"
									size="15" maxlength="64" />
								<s:textfield label="生效年月" id="benefit.ebfStartMonth" name="employee.benefit.ebfStartMonth"
								onblur="validateYearMonth(this.value)"	size="6" maxlength="6" />
							</tr>
							<tr>
							    <s:select label="基数种类" id="amountCategory" name="amountCategory" 
							         list="#{'pension':'社保基数', 'house':'公积金基数', 'insurance':'其他基数'}"
									 multiple="false" headerKey="" headerValue="请选择" />
								<s:textfield label="从" id="amountFrom" name="amountFrom" size="12" maxlength="13" 
								    onkeydown="benefitBaseCheck(event,this);"/>
								<s:textfield label="到" id="amountTo" name="amountTo" size="12" maxlength="13" 
								    onkeydown="benefitBaseCheck(event,this);"/>
							</tr>
						</table>
					</div>
				</td>
				<td align="center">
					<input title="[Alt+F]" accesskey="F" id="submit_button"
						class="button" type="submit" style="CURSOR: pointer" value="查询" 
						onclick="HRMCommon.search_check('basic','advanced');">
					<input title="[Alt+C]" accesskey="C" class="button" type="button"
						value="重置" onClick="window.location='searchEmpbenefit.action';">
					<div id="t1" align="center" style="DISPLAY: block; clear: both">
						<img src="../resource/images/basic_search.gif" width="8"
							height="8">
						<a href="#" class="utilsLink"
							onClick="HRMCommon.searchTypeChange();">高级查询</a>
					</div>
					<div id="t2" align="center" style="DISPLAY: none; clear: both">
						<img src="../resource/images/advanced_search.gif" width="8"
							height="8">
						<a href="#" class="utilsLink"
							onClick="HRMCommon.searchTypeChange();">基本查询</a>
					</div>
				</td>
			</tr>
		</table>
		<table width="100%">
		  <tr>
		    <br/>
		  </tr>
		  <tr>
			<td align="left">
				<ty:auth auths="201,3 or 201,2">
				    <input class="button" type="button" onclick="initDivImmUpload('IEmpSalaryBenefit', '');" value="社保初始化导入"/>
				    <input class="button" type="button" onclick="initDivImmUpload('IEmpSalaryBenefitUpd', '');" value="社保调整导入"/>
				    <input class="button" type="button" value="数据导出" id="btnOutput" onClick="HRMCommon.search_check('basic','advanced');HRMCommon.export_check('','','export');" />
				</ty:auth>
			</td>
			<td align="right">本次查询共得到<s:property value="page.totalRows" />名员工记录&nbsp;</td>
		  </tr>
		</table>
		
		<table id="empbenefittable" cellpadding="0" cellspacing="0"
			width="100%" border="0" class="gridtableList">
			<tr>
				<th nowrap="nowrap" align="center">
				<a href="#" onClick="HRMCommon.order_submit('empDistinctNo','searchEmpbenefitForm');">员工编号</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='empDistinctNo_img'></th>
				<th nowrap="nowrap" align="center">
				<a href="#" onClick="HRMCommon.order_submit('empName','searchEmpbenefitForm');">姓名</a><img src='../resource/images/arrow_.gif' width='8' height='10'id='empName_img'></th>
				<th nowrap="nowrap" align="center">
				<a href="#" onClick="HRMCommon.order_submit('empBenefitType','searchEmpbenefitForm');">社保种类</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='empBenefitType_img'></th>
				<th nowrap="nowrap" align="center">
				<a href="#" onClick="HRMCommon.order_submit('benefit.ebfPensionAmount','searchEmpbenefitForm');">社保基数</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='benefit.ebfPensionAmount_img'></th>
				<th nowrap="nowrap" align="center">
				<a href="#" onClick="HRMCommon.order_submit('benefit.ebfHousingAmount','searchEmpbenefitForm');">公积金基数</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='benefit.ebfHousingAmount_img'></th>
				<th nowrap="nowrap" align="center">
				<a href="#" onClick="HRMCommon.order_submit('benefit.ebfInsuranceAmount','searchEmpbenefitForm');">其他基数</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='benefit.ebfInsuranceAmount_img'></th>
				<th nowrap="nowrap" align="center">
				<a href="#" onClick="HRMCommon.order_submit('config.escColumn15','searchEmpbenefitForm');">个人缴社保</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='config.escColumn15_img'></th>
				<th nowrap="nowrap" align="center">
				<a href="#" onClick="HRMCommon.order_submit('config.escColumn16','searchEmpbenefitForm');">公司缴社保</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='config.escColumn16_img'></th>
				<th nowrap="nowrap" align="center">
				<a href="#" onClick="HRMCommon.order_submit('benefit.ebfStartMonth','searchEmpbenefitForm');">生效年月</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='benefit.ebfStartMonth_img'></th>
				<th nowrap="nowrap" align="center">操作</th>
			</tr>
			<s:if test="!empbenefitList.isEmpty()">
				<s:iterator value="empbenefitList">
				<tr id="<s:property value='id'/>">
					<td id="empDistinctNo<s:property value='id'/>" align="center">
						<span title="在职状态：<s:property value='getEmpStatusName(empStatus)'/>
所属地区：<s:property value='empLocationNo.locationName'/>
所属部门：<s:property value='empDeptNo.departmentName'/>
<s:if test="empIdentificationType==0">身份证号：<s:property value='empIdentificationNo'/></s:if>" />
						<s:if test="benefit!= null && benefit.ebfId != ''">
							<a href="javascript:showCopyebf('<s:property value='id'/>','<s:property value='benefit.ebfId' />')"
						  	class="listViewTdLinkS1"><s:property value='empDistinctNo' /></a>	
						</s:if>
						<s:else>
							<s:property value="empDistinctNo" />
						</s:else>
					</td>
					<td id="empName<s:property value='id'/>" nowrap="nowrap" align="center">
						<s:property value="empName" />
					</td>
					<td id="empBenefitType<s:property value='id'/>" nowrap="nowrap" align="center">
						<s:property value="empBenefitType.benefitTypeName" />
					</td>
					<s:if test="benefit!= null && benefit.ebfId != ''">
						<td id="ebfPensionA<s:property value='id'/>" nowrap="nowrap" align="right">
							<s:property value="benefit.ebfPensionAmount" />
						</td>
						<td id="ebfHousingA<s:property value='id'/>" nowrap="nowrap" align="right">
							<s:property value="benefit.ebfHousingAmount" />
						</td>
						<td id="ebfInsuranceA<s:property value='id'/>" nowrap="nowrap" align="right">
							<s:property value="benefit.ebfInsuranceAmount" />
						</td>
						<td id="benefit.config.showColumn15<s:property value='id'/>" nowrap="nowrap" align="right">
							<s:property value="config.showColumn15" />
						</td>
						<td id="benefit.config.showColumn16<s:property value='id'/>" nowrap="nowrap" align="right">
							<s:property value="config.showColumn16" />
						</td>
						<td id="ebfStartMonthTD<s:property value='id'/>" nowrap="nowrap" align="center">
							<s:property value="benefit.ebfStartMonth" />
						</td>
						<td align="center">
						  <s:if test="empStatus==1">
							<a href="#" onClick="showCopyebf('<s:property value="id"/>','<s:property value="benefit.ebfId" />')"><img
								src="../resource/images/edit.gif" alt="修改社保"></img></a>
							<a href="#" onClick="showAdjustebf('<s:property value="id"/>')"><img
								src="../resource/images/ProjectCopy.gif" alt="调整基数"></img></a>
							<a href="#" onClick="ebfDel('<s:property value="id"/>','<s:property value="benefit.ebfId"/>')"><img
								src="../resource/images/delete.gif" alt="删除"></img></a>
						  </s:if>
						</td>
					</s:if>
					<s:else>
						<td colspan="6">&nbsp;</td>
						<td nowrap="nowrap" align="center">
							<s:if test="empStatus==1">
								<a href="#" onClick="showAddebf('<s:property value="id"/>')"><img
									src="../resource/images/CreateProject.gif" alt="新增社保"></img></a>
							</s:if>
						</td>
					</s:else>
				</tr>
				</s:iterator>
			</s:if>
			<s:else>
				<tr> 
					<!-- 不存在符合条件的员工社保资料！ -->
					<td align="center" colspan="10">不存在符合条件的员工社保资料！</td>
				</tr>
			</s:else>
		</table>
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="gridtableList">
  			<tr class="listViewPaginationTdS1">
 			  <td colspan="10" align="center">
 			    <s:hidden id="page.currentPage" name="page.currentPage" />
 			    <s:component template="pager">
 			        <s:param name="pageNo" value="page.currentPage" /><s:param name="totalPages" value="page.totalPages" /><s:param name="totalRows" value="page.totalRows" />
          	  	    <s:param name="start" value="page.start" /><s:param name="end" value="page.end" /><s:param name="formId" value="'searchEmpbenefitForm'" />
          	  	</s:component>
			  </td>
  			</tr>
		</table>
	</form>
	<div id="dlgAddEmpBenefit" style="width: 400;" class="prompt_div_inline">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td>
					<div id="remark_hand_empbenefit" style="CURSOR: move;"
						class="prompt_div_head">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<TR>
								<td>
									<h3>
										
										<input type="hidden" id="div_head">
									</h3>
								</td>
							</TR>
						</table>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<s:form id="addBenefitForm" method="post">
						<table class="prompt_div_body" cellpadding="0" cellspacing="0" width="100%" border="0">
							<tr>
								<td align="right">
									生效年月
									<span class="required">*</span>：
								</td>
								<td>
									<select id="startBtfYear" name="startBtfYear">
									</select>
									<select id="startBtfMonth" name="startBtfMonth">
									</select>
								</td>
							</tr>
							<tr>
								<td align="right">
									社保基数：
								</td>
								<td>
									<input id="ebfPensionAmount" name="ebfPensionAmount"
										onkeyup="value=value.replace(/[^0-9{\.}]/g,'');changeDmalPoint(document.getElementById('ebfPensionAmount'));"
										onkeypress="HRMCommon.MKeyTextLength(this,9);" />
								</td>
							</tr>
							<tr>
								<td align="right">
									公积金基数：
								</td>
								<td>
									<input id="ebfHousingAmount" name="ebfHousingAmount"
										onkeyup="value=value.replace(/[^0-9{\.}]/g,'');changeDmalPoint(document.getElementById('ebfHousingAmount'));"
										onkeypress="HRMCommon.MKeyTextLength(this,9);" />
								</td>
							</tr>
							<tr>
							<td align="right">
								其他基数：
							</td>
							<td>
								<input id="ebfInsuranceAmount" name="ebfInsuranceAmount"
									onkeyup="value=value.replace(/[^0-9{\.}]/g,'');changeDmalPoint(document.getElementById('ebfInsuranceAmount'));"
									onkeypress="HRMCommon.MKeyTextLength(this,9);" />
							</td>
							</tr>
							<tr>
								<td></td>
							</tr>
							<tr>
								<td colspan="2" align="center">
									<input type="hidden" id="employeeId" name="employeeId" />
									<input type="hidden" id="ebfStartMonthTD" name="ebfStartMonthTD" />
									<input id="ebfAddbtn" type="button" value="调整"
										onClick="submitAdjustebf();" />
									&nbsp;&nbsp;&nbsp;
									<input type="button" value="取消"
										onclick="HRMCommon.closeDialog('dlgAddEmpBenefit');" />
								</td>
							</tr>
						</table>
					</s:form>
				</td>
			</tr>
		</table>
	</div>
<script type="text/javascript">
//action跳转到增加新社保页面
function showAddebf(employeeId){
	document.getElementById('searchOrExport').value='';
	var url = "addBenefitInit.action?employeeId="+employeeId;
    document.searchEmpbenefitForm.action = url;
    HRMCommon.search_check('basic','advanced');
    document.searchEmpbenefitForm.submit();
}
//action跳转到修改员工社保页面
function showCopyebf(employeeId,ebfId){
	document.getElementById('searchOrExport').value='';
	var url = "updateBenefitInit.action?employeeId="+employeeId+"&ebfId="+ebfId;
    document.searchEmpbenefitForm.action = url;
    HRMCommon.search_check('basic','advanced');
    document.searchEmpbenefitForm.submit();
}
//action跳转到删除社保页面
function ebfDel(employeeId,ebfId){
 	var empname = document.getElementById('empName' + employeeId).innerHTML;
  	if (confirm("确定删除" + empname.trim() + "的当前社保记录吗？")){
  		document.getElementById('searchOrExport').value='';
    	var url = "delEmpbenefit.action?ebfId=" + ebfId+"&employeeId="+employeeId;
    	document.searchEmpbenefitForm.action = url;
    	HRMCommon.search_check('basic','advanced');
    	document.searchEmpbenefitForm.submit();
	}
}
//缴费日期年月下拉菜单设置
function setSelectValue() {
	var year = document.getElementById("year").value;
	var month = document.getElementById("month").value;
	year = parseInt(year);
	month = parseInt(month);
	var index=0;
	for(var i=year+1;i>year-2;i--) {
		if(i==year) {
			document.getElementById('startBtfYear').options[index]=new Option(i,i,true,true);
		}
		else{
			document.getElementById('startBtfYear').options[index]=new Option(i,i,false,false);
		}
		index++;
	}
	var temp = 1;
	for(var m=0; m<3; m++){
	    temp = document.getElementById('startBtfYear').options[m].value;
	    if(temp == year){
	        document.getElementById('startBtfYear').value = temp;
	    }
	}
	for(var i=1;i<13;i++) {
		var tmp;
		if(i==month){
			if(i<10){tmp='0'+i;}
			else {tmp=''+i;}
			document.getElementById('startBtfMonth').options[i-1]=new Option(tmp,tmp,true,true);
		}
		else{
			if(i<10){tmp='0'+i;}
			else {tmp=''+i;}
			document.getElementById('startBtfMonth').options[i-1]=new Option(tmp,tmp,false,false);
		}
	}
	var temp1 = 1;
	for(var m=0; m<12; m++){
	    temp1 = document.getElementById('startBtfMonth').options[m].value;
	    if(temp1 == month){
	        document.getElementById('startBtfMonth').value = temp1;
	    }
	}
}
function showAdjustebf(employeeId) {
	setSelectValue();
	document.getElementById('div_head').value=document.getElementById('empName'+employeeId).innerHTML;
	var titleStr =document.getElementById('empName'+employeeId).innerHTML;
	$('#dlgAddEmpBenefit').dialog("option","title","基数调整("+titleStr+")");
	document.getElementById('employeeId').value=employeeId;
	document.getElementById('ebfStartMonthTD').value=document.getElementById('ebfStartMonthTD'+employeeId).innerHTML.trim();
	document.getElementById('ebfPensionAmount').value=document.getElementById('ebfPensionA'+employeeId).innerHTML.trim();
	document.getElementById('ebfHousingAmount').value=document.getElementById('ebfHousingA'+employeeId).innerHTML.trim();
	document.getElementById('ebfInsuranceAmount').value=document.getElementById('ebfInsuranceA'+employeeId).innerHTML.trim();
	HRMCommon.openDialog('dlgAddEmpBenefit');	
}
//提交基数调整 modified by Steven 2010-02-07
function submitAdjustebf(){
	var oldstartDate = $('#ebfStartMonthTD').val(); 
	var newstartDate = $('#startBtfYear').val()+$('#startBtfMonth').val(); 
	if(oldstartDate>=newstartDate)
		{alert("新记录的起始年月必须晚于老记录的起始年月！");return;}
	if(hrm.common.isEmpty($('#ebfPensionAmount').val()) &&
			hrm.common.isEmpty($('#ebfHousingAmount').val()) &&
			hrm.common.isEmpty($('#ebfInsuranceAmount').val()))
		{alert("至少一项基数不能为空，请填写！");return;}

	objetC={ebfId:null,ebfStartMonth:DWRUtil.getValue('startBtfYear')+DWRUtil.getValue('startBtfMonth'),ebfEndMonth:null,ebfPensionAmount:DWRUtil.getValue('ebfPensionAmount'),ebfHousingAmount:DWRUtil.getValue('ebfHousingAmount'),ebfInsuranceAmount:DWRUtil.getValue('ebfInsuranceAmount')};
	var params = DWRUtil.getValues(objetC);
	var EmpNo=document.getElementById('employeeId').value;
	
	HRMCommon.closeDialog('dlgAddEmpBenefit');
	EmpBenefit.empBenefitAdd(params,EmpNo,ebfaddcallback);
	function ebfaddcallback(msg) {
		try{
			if (HRMCommon.actionMsgHandler(msg)=='SUCC') {
				document.getElementById('ebfStartMonthTD'+EmpNo).innerHTML=DWRUtil.getValue('startBtfYear')+DWRUtil.getValue('startBtfMonth');
				document.getElementById('ebfPensionA'+EmpNo).innerHTML=document.getElementById('ebfPensionAmount').value;
				document.getElementById('ebfHousingA'+EmpNo).innerHTML=document.getElementById('ebfHousingAmount').value;
				document.getElementById('ebfInsuranceA'+EmpNo).innerHTML=document.getElementById('ebfInsuranceAmount').value;
			}
		}catch(e){alert(e);}
	}
}
//判断小数点及整数部分的长度
function changeDmalPoint(Decimal){
	if(!Decimal || Decimal.value.length == 0){
        return;
	}
 	var l = Decimal.value.length;
 	var flag=0;
 	if(Decimal.value.charAt(0)=="."){
 		Decimal.value='';
 		return;
 	}
 	for(var i=0; i<l; i++){
  		var digit = Decimal.value.charAt(i);
  		if(digit=="."){
  			if(flag>0){
  				Decimal.value=Decimal.value.substring(0,i);
  				
  			}
  			flag++;
  		}
 	}
 	if(flag==0){
  			if(l>7){
  				Decimal.value=Decimal.value.substring(0,7);
  			}
  		} 
	return;
}


//社保基数查询检查；
function benefitBaseCheck(event, obj) {
    if(document.getElementById("amountCategory").value == ""){
        alert("请先选择基数种类！");
        return;
    }
    var temp = obj.value;
	event = event ? event : (window.event ? window.event : null);
    var code = event.keyCode ? event.keyCode : event.which;
	/**数字，“.”，退格，delete，Ctrl+C, Ctrl+V, Ctrl+shift,Ctrl+空格；*/
	if(!((code >= 48 && code <= 57) ||  code == 190 ||  code == 8 || code==46 
		  || code==17 || code==16 || code==32 || code==67 || code==86)){
		    alert("只允许数字或.");
			obj.value = temp;
			event.returnValue = false;
	}
}
function validateYearMonth(str){
 if(hrm.common.isNull(str)) return;
 var patrn=/^(\d{4})(\d{2})$/; 
 if(patrn.test(str)){
   return;
 }else{
 alert("年月必须为\"yyyyMM\"格式,如200906,200912");
 document.getElementById('benefit.ebfStartMonth').value='';
 }
}

HRMCommon.initDialog('dlgAddEmpBenefit');
</script>
<jsp:include flush="true" page="../io/div_omm_select.jsp"></jsp:include>
<jsp:include flush="true" page="../io/div_upload.jsp"></jsp:include>
</body>