<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<html>
<head>
<title>修改员工社保记录</title>
<link href="../resource/css/style.css" rel="stylesheet" type="text/css" />
</head>
<body>
<s:component template="bodyhead">
	<s:param name="pagetitle" value="'修改社保记录'" />
	<s:param name="helpUrl" value="'22'" />
</s:component>

<form id="updateBenefitForm" name="updateBenefitForm" method="POST"
	namespace="/compensation" action="updateBenefit.action"><s:token />
<input type="hidden" id="employeeId" name="employeeId"
	value="<s:property value='emp.id'/>"> <input type="hidden"
	id="singleebfId" name="ebfId" value="<s:property value='ebfId'/>">
<input type="hidden" id="ebfId" name="benefit.ebfId"
	value="<s:property value='emp.benefit.ebfId'/>"> <input
	type="hidden" id="inputstartBtfYear" name="inputstartBtfYear"
	value="<s:property value='inputstartBtfYear'/>"> <input
	type="hidden" id="inputstartBtfMonth" name="inputstartBtfMonth"
	value="<s:property value='inputstartBtfMonth'/>"> <input
	type="hidden" id="hiddenempLocationNo" name="employee.empLocationNo.id"
	value="<s:property value='employee.empLocationNo.id'/>"> <input
	type="hidden" id="hiddenedepartmentNo" name="employee.empDeptNo.id"
	value="<s:property value='employee.empDeptNo.id'/>"> <input
	type="hidden" id="hiddenempIdentificationNo"
	name="employee.empIdentificationNo"
	value="<s:property value='employee.empIdentificationNo'/>"> <input
	type="hidden" id="hiddenempStatus" name="employee.empStatus"
	value="<s:property value='employee.empStatus'/>"> <input
	type="hidden" id="hiddenempBenefitTypeAdv"
	name="employee.empBenefitType.id"
	value="<s:property value='employee.empBenefitType.id'/>"> <input
	type="hidden" id="empNameOrDist" name="empNameOrDist"
	value="<s:property value='empNameOrDist'/>">
<table width="100%" class="formtable" id="employeeTable">

	<tr>
		<s:textfield id="emp.empDistinctNo" name="emp.empDistinctNo"
			label="员工编号" readonly="true" cssClass="nothinginput" />
		<s:textfield id="emp.empJoinDate" name="emp.empJoinDate" label="入职日期"
			readonly="true" cssClass="nothinginput" />
		<s:if test="emp.empStatus==1">
			<s:textfield id="empStatus" name="empStatus" label="员工状态" value="在职"
				readonly="true" cssClass="nothinginput" />
		</s:if>
		<s:elseif test="emp.empStatus==0">
			<s:textfield id="empStatus" name="empStatus" label="员工状态" value="离职"
				readonly="true" cssClass="nothinginput" />
		</s:elseif>
	</tr>
	<tr>
		<s:textfield id="emp.empName" name="emp.empName" label="员工姓名"
			readonly="true" cssClass="nothinginput" />
		<s:textfield id="emp.empIdentificationNo"
			name="emp.empIdentificationNo" label="身份证件" readonly="true"
			cssClass="nothinginput" />
		<s:textfield id="emp.empType.emptypeName"
			name="emp.empType.emptypeName" label="用工形式" readonly="true"
			cssClass="nothinginput" />
	</tr>
	<tr>
		<s:textfield id="emp.empDeptNo.departmentName"
			name="emp.empDeptNo.departmentName" label="所属部门" readonly="true"
			cssClass="nothinginput" />
		<s:textfield id="emp.empPbNo.pbName" name="emp.empPbNo.pbName"
			label="当前职位" readonly="true" cssClass="nothinginput" />
		<s:textfield id="emp.empLocationNo.locationName"
			name="emp.empLocationNo.locationName" label="工作地区" readonly="true"
			cssClass="nothinginput" />
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
	<s:if test="benefitValue[0][0]!=null">
		<tr>
			<s:iterator value="benefitValue" status="status">
				<td align="right"> <s:property value="benefitValue[#status.index][0]"/>: </td>
				<td> <s:property value="benefitValue[#status.index][1]"/> </td>
			<s:if test="(#status.index+1)%3==0">
		</tr>
		<tr>
			</s:if>
			</s:iterator>
		</tr>
	</s:if>
	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<s:select label="社保种类" id="empBenefitType"
			name="emp.empBenefitType.id" list="ebfTypeList" listKey="id"
			listValue="benefitTypeName" multiple="false" emptyOption="true"
			value="emp.empBenefitType.id" />
			<td align="right">
				生效年月:
			</td>
			<td>
				<s:select id="startBtfYear" name="startBtfYear" list="yearList"
				emptyOption="false" value="startBtfYear"/>
				<s:select id="startBtfMonth" name="startBtfMonth"
				list="#{'01':'01','02':'02','03':'03','04':'04','05':'05','06':'06','07':'07','08':'08','09':'09','10':'10','11':'11','12':'12'}"
				emptyOption="false" value="startBtfMonth"/>
				<s:hidden id="ebfStartMonth" name="emp.benefit.ebfStartMonth" value="%{startBtfYear+startBtfMonth}" />
			</td>
	</tr>
	<tr>
		<s:textfield label="养老保险号" id="emp.benefit.ebfPensionNo"
			name="emp.benefit.ebfPensionNo" size="16" maxlength="64"
			onkeyup="value=value.replace( /[^0-9a-zA-Z]/g,'');"
			onkeypress="HRMCommon.MKeyTextLength(this,64);" />
		<s:textfield label="公积金号" id="emp.benefit.ebfHousingNo"
			name="emp.benefit.ebfHousingNo" size="16" maxlength="64"
			onkeyup="value=value.replace( /[^0-9a-zA-Z]/g,'');"
			onkeypress="HRMCommon.MKeyTextLength(this,64);" />
		<s:textfield label="医疗保险号" id="emp.benefit.ebfMedicalNo"
			name="emp.benefit.ebfMedicalNo" size="16" maxlength="64"
			onkeyup="value=value.replace( /[^0-9a-zA-Z]/g,'');"
			onkeypress="HRMCommon.MKeyTextLength(this,64);" />
	</tr>
	<tr>
		<s:textfield label="社保基数(D2)" id="ebfPensionAmount"
			name="emp.benefit.ebfPensionAmount" size="16" maxlength="64"
			onkeyup="value=value.replace(/[^0-9{\.}]/g,'');changeDmalPoint(document.getElementById('ebfPensionAmount'));"
			onkeypress="HRMCommon.MKeyTextLength(this,10);" />
		<s:textfield label="公积金基数(D3)" id="ebfHousingAmount"
			name="emp.benefit.ebfHousingAmount" size="16" maxlength="64"
			onkeyup="value=value.replace(/[^0-9{\.}]/g,'');changeDmalPoint(document.getElementById('ebfHousingAmount'));"
			onkeypress="HRMCommon.MKeyTextLength(this,10);" />
		<s:textfield label="综合保险基数(D4)" id="ebfInsuranceAmount"
			name="emp.benefit.ebfInsuranceAmount" size="16" maxlength="64"
			onkeyup="value=value.replace(/[^0-9{\.}]/g,'');changeDmalPoint(document.getElementById('ebfInsuranceAmount'));"
			onkeypress="HRMCommon.MKeyTextLength(this,10);" />
	</tr>
	<tr>
		<td colspan="7" align="left"><font color="red">&nbsp;&nbsp;备注：D2、D3、D4表示薪资帐套中对应的变量引用名称。</font>
		</td>
	</tr>
	<tr height="45">
		<td colspan="7" align="center"><input id="doaction" type="submit"
			name="okbtn" onClick="return reCheck();" value="修改">
		&nbsp;&nbsp; <input type="button" name="back2"
			onClick="retSearchebf();" value="返回" /></td>
	</tr>
</table>
</form>
<br>
<form id="showEmpbenefitForm" method="POST" namespace="/compensation">
<table>
	<tr>
		<td width="2%"><img src="../resource/images/h3Arrow.gif"></td>
		<td width="82%" class="titlepage">
		<h3>员工社保调整历史</h3>
		</td>
	</tr>
</table>
<table id="empbenefittable" cellpadding="0" cellspacing="0" width="100%"
	border="0" class="gridtableList">
	<tr>
		<th nowrap="nowrap"><a>缴费起始年月</a></th>
		<th nowrap="nowrap"><a>缴费结束年月</a></th>
		<th nowrap="nowrap"><a>养老保险号</a></th>
		<th nowrap="nowrap"><a>医疗保险号</a></th>
		<th nowrap="nowrap"><a>公积金号</a></th>
		<th nowrap="nowrap"><a>社保基数</a></th>
		<th nowrap="nowrap"><a>公积金基数</a></th>
		<th nowrap="nowrap"><a>综合保险基数</a></th>
	</tr>
	<s:if test="!BenefitList.isEmpty()">
		<s:iterator value="BenefitList">
			<tr>
				<td id="ebfStartMonthTD<s:property value="ebfId"/>" nowrap="nowrap"
					align="center"><s:property value="ebfStartMonth" /></td>
				<td id="ebfStartMonthTD<s:property value="ebfId"/>" nowrap="nowrap"
					align="center"><s:property value="ebfEndMonth" /></td>
				<td id="ebfPensionNo<s:property value="ebfId"/>" nowrap="nowrap"
					align="center"><s:property value="ebfPensionNo" /></td>
				<td id="ebfMedicalNo<s:property value="ebfId"/>" nowrap="nowrap"
					align="center"><s:property value="ebfMedicalNo" /></td>
				<td id="ebfHousing<s:property value="ebfId"/>" nowrap="nowrap"
					align="center"><s:property value="ebfHousingNo" /></td>
				<td id="ebfPensionA<s:property value="ebfId"/>" nowrap="nowrap"
					align="right"><s:property value="ebfPensionAmount" /></td>
				<td id="ebfHousingA<s:property value="ebfId"/>" nowrap="nowrap"
					align="right"><s:property value="ebfHousingAmount" /></td>
				<td id="ebfInsuranceA<s:property value="ebfId"/>" nowrap="nowrap"
					align="right"><s:property value="ebfInsuranceAmount" /></td>
			</tr>
		</s:iterator>
	</s:if>
	<s:else>
		<tr>
			<!-- 该员工不存在社保资料！ -->
			<td align="center" colspan="12">该员工不存在社保调整记录！</td>
		</tr>
	</s:else>
</table>
</form>
<br>
<form id="showEmpbenefitValue" method="POST"><s:hidden
	id="order" name="page.order" />
<table>
	<tr>
		<td width="2%"><img src="../resource/images/h3Arrow.gif"></td>
		<td width="82%" class="titlepage">
		<h3>历史缴费记录</h3>
		</td>
	</tr>
</table>
<table id="benefit_paid_table" cellpadding="0" cellspacing="0"
	width="100%" border="0" class="gridtableList">
	<tr>
		<th nowrap="nowrap">历史缴费年月</th>
		<th nowrap="nowrap">个人缴社保总额</th>
		<th nowrap="nowrap">公司代缴社保总额</th>
		<th nowrap="nowrap">备注</th>
	</tr>
	<s:if test="!benefitPayvalueList.isEmpty()">
		<s:iterator value="benefitPayvalueList" status="index">
			<tr>
				<td align="center"><s:property value="yearAndMonth" /></td>
				<td align="center">&nbsp;<s:property value="showColumn15" />
				</td>
				<td align="center"><s:property value="showColumn16" /></td>
				<td align="center"><s:property value="espComment" /></td>
			</tr>
		</s:iterator>
	</s:if>
	<s:else>
		<tr>
			<td align="center" colspan="4"><span id="infomation">
			该员工不存在社保历史缴费记录! </span></td>
		</tr>
	</s:else>
</table>
<input type="button" name="back" onclick="retSearchebf();" value="返 回" />
</form>
		<s:if test="emp.empStatus==0">
			<script type="text/javascript">
				document.getElementById('updateBenefitForm').action="";
				document.getElementById('updateBenefitForm').onsubmit=function(){return false;};
				document.getElementById('doaction').disabled=true;
			</script>
		</s:if>
<script type="text/javascript">
//action跳转到增加新社保页面
function showAddebf(employeeId){
	var url = "addBenefit.action";
    document.updateBenefitForm.action = url;
    document.updateBenefitForm.submit();
}
//action跳转到查询社保页面
function retSearchebf(){
	var url = "searchEmpbenefit.action";
    document.updateBenefitForm.action = url;
    document.updateBenefitForm.submit();
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
  				//alert("数据格式错误");
  				//return;
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
function reCheck(){
	if($('#empBenefitType').val()==''){
		alert("社保种类不能为空！");
		return false;
	}
	if($('#ebfPensionAmount').val()=='' && $('#ebfHousingAmount').val()==''&& $('#ebfInsuranceAmount').val()==''){
		alert("请填写至少一项基数！");
		return false;
	}

	var yearmonth = $('#startBtfYear').val() + $('#startBtfMonth').val();
	$('#ebfStartMonth').val(yearmonth);
	return true;
}
</script>
</body>
</html>
