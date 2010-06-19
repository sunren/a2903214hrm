<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<html>
	<head>
		<title>����Ա���籣��¼</title>
	</head>
	<body>
		<s:component template="bodyhead">
			<s:param name="pagetitle" value="'�����籣��¼'" />
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
					<s:textfield id="emp.empDistinctNo" name="emp.empDistinctNo" label="Ա�����" readonly="true" cssClass="nothinginput" />
					<s:textfield id="emp.empJoinDate" name="emp.empJoinDate" label="��ְ����" readonly="true" cssClass="nothinginput" />
					<s:if test="emp.empStatus==1">
						<s:textfield id="empStatus" name="empStatus" label="Ա��״̬" value="��ְ" readonly="true" cssClass="nothinginput" />
					</s:if>
					<s:elseif test="emp.empStatus==0">
						<s:textfield id="empStatus" name="empStatus" label="Ա��״̬" value="��ְ" readonly="true" cssClass="nothinginput" />
					</s:elseif>
				</tr>
				<tr>
					<s:textfield id="emp.empName" name="emp.empName" label="Ա������" readonly="true" cssClass="nothinginput" />
					<s:textfield id="emp.empIdentificationNo" name="emp.empIdentificationNo" label="���֤��" readonly="true" cssClass="nothinginput" />
					<s:textfield id="emp.empType.emptypeName" name="emp.empType.emptypeName" label="�ù���ʽ" readonly="true" cssClass="nothinginput" />
				</tr>
				<tr>
					<s:textfield id="emp.empDeptNo.departmentName" name="emp.empDeptNo.departmentName" label="��������" readonly="true" cssClass="nothinginput" />
					<s:textfield id="emp.empPbNo.pbName" name="emp.empPbNo.pbName" label="��ǰְλ" readonly="true"	cssClass="nothinginput" />
					<s:textfield id="emp.empLocationNo.locationName" name="emp.empLocationNo.locationName" label="��������" readonly="true" cssClass="nothinginput" />
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<s:select label="�籣����" id="empBenefitType"
						name="emp.empBenefitType.id" list="ebfTypeList" listKey="id"
						listValue="benefitTypeName" multiple="false" emptyOption="true"
						value="emp.empBenefitType.id" />
					<td align="right">
						��Ч����:
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
					<s:textfield label="���ϱ��պ�" id="emp.benefit.ebfPensionNo"
						name="emp.benefit.ebfPensionNo" size="16" maxlength="64"
						onkeyup="value=value.replace( /[^0-9a-zA-Z]/g,'');"
						onkeypress="HRMCommon.MKeyTextLength(this,64);" />
					<s:textfield label="�������" id="emp.benefit.ebfHousingNo"
						name="emp.benefit.ebfHousingNo" size="16" maxlength="64"
						onkeyup="value=value.replace( /[^0-9a-zA-Z]/g,'');"
						onkeypress="HRMCommon.MKeyTextLength(this,64);" />
					<s:textfield label="ҽ�Ʊ��պ�" id="emp.benefit.ebfMedicalNo"
						name="emp.benefit.ebfMedicalNo" size="16" maxlength="64"
						onkeyup="value=value.replace( /[^0-9a-zA-Z]/g,'');"
						onkeypress="HRMCommon.MKeyTextLength(this,64);" />
				</tr>
				<tr>
					<s:textfield label="�籣����(D2)" id="ebfPensionAmount"
						name="emp.benefit.ebfPensionAmount" size="16" maxlength="64"
						onkeyup="value=value.replace(/[^0-9{\.}]/g,'');changeDmalPoint(document.getElementById('ebfPensionAmount'));"
						onkeypress="HRMCommon.MKeyTextLength(this,10);" />
					<s:textfield label="���������(D3)" id="ebfHousingAmount"
						name="emp.benefit.ebfHousingAmount" size="16" maxlength="64"
						onkeyup="value=value.replace(/[^0-9{\.}]/g,'');changeDmalPoint(document.getElementById('ebfHousingAmount'));"
						onkeypress="HRMCommon.MKeyTextLength(this,10);" />
					<s:textfield label="�ۺϱ��ջ���(D4)" id="ebfInsuranceAmount"
						name="emp.benefit.ebfInsuranceAmount" size="16" maxlength="64"
						onkeyup="value=value.replace(/[^0-9{\.}]/g,'');changeDmalPoint(document.getElementById('ebfInsuranceAmount'));"
						onkeypress="HRMCommon.MKeyTextLength(this,10);" />
				</tr>
				<tr>
					<td colspan="7" align="left">
						<font color="red">&nbsp;&nbsp;��ע��D2��D3��D4��ʾн�������ж�Ӧ�ı����������ơ�</font>
					</td>
			    </tr>
				<tr height="45">
					<td colspan="7" align="center">
						<input id="doaction" type="submit" name="okbtn"
							onclick="return reCheck();" value="����">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" name="back" onclick="retSearchebf();"
							value="����" />
					</td>
				</tr>
			</table>
		</form>

<script type="text/javascript" language="javascript">
//action��ת����ѯ�籣ҳ��
function retSearchebf(){
	var url = "searchEmpbenefit.action";
    document.addBenefitForm.action = url;
    document.addBenefitForm.submit();
}

//�ж�С���㼰�������ֵĳ���
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
  				//alert("���ݸ�ʽ����");
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
		alert("�籣���಻��Ϊ�գ�");
		return false;
	}
	if($('#ebfPensionAmount').val()=='' && $('#ebfHousingAmount').val()==''&& $('#ebfInsuranceAmount').val()==''){
		alert("����д����һ�������");
		return false;
	}

	var yearmonth = $('#startBtfYear').val() + $('#startBtfMonth').val();
	$('#ebfStartMonth').val(yearmonth);
	return true;
}
</script>
</body>
</html>
