<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<html>
	<head>
		<title>新增员工社保记录</title>
	</head>
	<body>
		<s:component template="bodyhead">
			<s:param name="pagetitle" value="'新增社保记录'" />
			<s:param name="helpUrl" value="'22'" />
		</s:component>
		<span class="errorMessage" id="message"></span>
		<form id="addBenefitForm" name="addBenefitForm" method="post"
			action="addBenefit.action" namespace="/compensation">

			<input type="hidden" id="employeeId" name="employeeId"
				value="<s:property value='emp.id'/>">
			<input type="hidden" id="hiddenempLocationNo"
				name="employee.empLocationNo.id"
				value="<s:property value='employee.empLocationNo.id'/>">
			<input type="hidden" id="hiddenedepartmentNo"
				name="employee.empDeptNo.id"
				value="<s:property value='employee.empDeptNo.id'/>">
			<input type="hidden" id="hiddenempIdentificationNo"
				name="employee.empIdentificationNo"
				value="<s:property value='employee.empIdentificationNo'/>">
			<input type="hidden" id="hiddenempStatus" name="employee.empStatus"
				value="<s:property value='employee.empStatus'/>">
			<input type="hidden" id="hiddenempBenefitTypeAdv"
				name="employee.empBenefitType.id"
				value="<s:property value='employee.empBenefitType.id'/>">
			<input type="hidden" id="empNameOrDist" name="empNameOrDist"
				value="<s:property value='empNameOrDist'/>">
			<table width="100%" class="formtable" id="employeeTable">
				<tr>
					<s:textfield id="emp.empDistinctNo" name="emp.empDistinctNo" label="员工编号" readonly="true" cssClass="nothinginput" />
					<s:textfield id="emp.empJoinDate" name="emp.empJoinDate" label="入职日期" readonly="true" cssClass="nothinginput" />
					<s:if test="emp.empStatus==1">
						<s:textfield id="empStatus" name="empStatus" label="员工状态" value="在职" readonly="true" cssClass="nothinginput" />
					</s:if>
					<s:elseif test="emp.empStatus==0">
						<s:textfield id="empStatus" name="empStatus" label="员工状态" value="离职" readonly="true" cssClass="nothinginput" />
					</s:elseif>
				</tr>
				<tr>
					<s:textfield id="emp.empName" name="emp.empName" label="员工姓名" readonly="true" cssClass="nothinginput" />
					<s:textfield id="emp.empIdentificationNo" name="emp.empIdentificationNo" label="身份证件" readonly="true" cssClass="nothinginput" />
					<s:textfield id="emp.empType.emptypeName" name="emp.empType.emptypeName" label="用工形式" readonly="true" cssClass="nothinginput" />
				</tr>
				<tr>
					<s:textfield id="emp.empDeptNo.departmentName" name="emp.empDeptNo.departmentName" label="所属部门" readonly="true" cssClass="nothinginput" />
					<s:textfield id="emp.empPbNo.pbName" name="emp.empPbNo.pbName" label="当前职位" readonly="true"	cssClass="nothinginput" />
					<s:textfield id="emp.empLocationNo.locationName" name="emp.empLocationNo.locationName" label="工作地区" readonly="true" cssClass="nothinginput" />
				</tr>
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
						emptyOption="false" />
						<s:select id="startBtfMonth" name="startBtfMonth"
							list="#{'01':'01','02':'02','03':'03','04':'04','05':'05','06':'06','07':'07','08':'08','09':'09','10':'10','11':'11','12':'12'}"
						emptyOption="false" />
						<s:hidden id="ebfStartMonth" name="emp.benefit.ebfStartMonth" />
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
					<td colspan="7" align="left">
						<font color="red">&nbsp;&nbsp;备注：D2、D3、D4表示薪资帐套中对应的变量引用名称。</font>
					</td>
			    </tr>
				<tr height="45">
					<td colspan="7" align="center">
						<input id="doaction" type="submit" name="okbtn"
							onclick="return reCheck();" value="保存">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" name="back" onclick="retSearchebf();"
							value="返回" />
					</td>
				</tr>
			</table>
		</form>

<script type="text/javascript" language="javascript">
//action跳转到查询社保页面
function retSearchebf(){
	var url = "searchEmpbenefit.action";
    document.addBenefitForm.action = url;
    document.addBenefitForm.submit();
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
