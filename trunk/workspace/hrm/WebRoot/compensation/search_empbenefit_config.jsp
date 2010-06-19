<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ taglib prefix="s" uri="webwork"%>
<%@ taglib prefix="ty" uri="/tengsource"%>
<head>
	<title>Ա���籣����</title>
	<link rel="stylesheet" type="text/css" href="../resource/css/style.css" />
	<script type="text/javascript" src="../dwr/interface/EmpBenefit.js"></script>
</head>
<body onLoad="HRMCommon.check_showHide();HRMCommon.check_order();">
	<s:component template="bodyhead">
		<s:param name="pagetitle" value="'Ա���籣����'" />
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
								<s:textfield label="Ա��" id="empName" name="empNameOrDist"
									size="16" maxlength="64" />
								<td align="right">��֯��Ԫ:</td>
								<td>
									<s:orgselector id="orgselector1" name="employee.empDeptNo.departmentName" hiddenFieldName="employee.empDeptNo.id" isShowDisable="show"/>
								</td>
								<s:select label="�籣����" id="empBenefitType"
									name="employee.empBenefitType.id" list="ebfTypeList"
									listKey="id" listValue="benefitTypeName" multiple="false"
									headerKey="" headerValue="��ѡ��" />
								<s:select label="Ա��״̬" id="empStatus" name="employee.empStatus"
									list="empStatus" listKey="id.statusconfNo"
									listValue="statusconfDesc" multiple="false" emptyOption="true"
									value="employee.empStatus" size="1" />
							</tr>
						</table>
					</div>
					<div id="advanced" style="DISPLAY: none; clear: both">
						<table width="100%" border="0" cellspacing="0" cellpadding="3">
							<tr>
								<s:textfield label="Ա������" id="empNameAdv" name="employee.empName"
									size="16" maxlength="64" />
								<td align="right">��֯��Ԫ:</td>
								<td>
									<s:orgselector id="orgselector2" name="employee.empDeptNo.departmentName" hiddenFieldName="employee.empDeptNo.id" isShowDisable="show"/>
								</td>
								<s:select label="�籣����" id="empBenefitTypeAdv"
									name="employee.empBenefitType.id" list="ebfTypeList"
									listKey="id" listValue="benefitTypeName" multiple="false"
									headerKey="" headerValue="��ѡ��" />
								<s:select label="Ա��״̬" id="empStatus" name="employee.empStatus"
									list="empStatus" listKey="id.statusconfNo"
									listValue="statusconfDesc" multiple="false" emptyOption="true"
									value="employee.empStatus" size="1" />
							</tr>
							<tr>
							    <s:textfield id="empNoAdv" label="Ա�����"
									name="employee.empDistinctNo" size="15" maxlength="64" />
							    <s:textfield label="���֤��" id="empIdentificationNoAdv" name="employee.empIdentificationNo"
									size="15" maxlength="64" />
								<s:select label="�ù���ʽ" id="empType" name="employee.empType.id"
									list="empTypeList" listKey="id" listValue="emptypeName"
									multiple="false" headerKey="" headerValue="��ѡ��" />
							    <s:select label="��������" id="empLocationNo"
									name="employee.empLocationNo.id" list="locationList" listKey="id"
									listValue="locationName" multiple="false" headerKey=""
									headerValue="��ѡ��" />
							</tr>
							<tr>
                <s:textfield label="���ϱ��պ�" id="benefit.ebfPensionNoAdv" name="employee.benefit.ebfPensionNo"
									size="15" maxlength="64" />
								<s:textfield label="�������" id="benefit.ebfHousingNoAdv" name="employee.benefit.ebfHousingNo"
									size="15" maxlength="64" />
								<s:textfield label="ҽ�Ʊ��պ�" id="benefit.ebfMedicalNoAdv" name="employee.benefit.ebfMedicalNo"
									size="15" maxlength="64" />
								<s:textfield label="��Ч����" id="benefit.ebfStartMonth" name="employee.benefit.ebfStartMonth"
								onblur="validateYearMonth(this.value)"	size="6" maxlength="6" />
							</tr>
							<tr>
							    <s:select label="��������" id="amountCategory" name="amountCategory" 
							         list="#{'pension':'�籣����', 'house':'���������', 'insurance':'��������'}"
									 multiple="false" headerKey="" headerValue="��ѡ��" />
								<s:textfield label="��" id="amountFrom" name="amountFrom" size="12" maxlength="13" 
								    onkeydown="benefitBaseCheck(event,this);"/>
								<s:textfield label="��" id="amountTo" name="amountTo" size="12" maxlength="13" 
								    onkeydown="benefitBaseCheck(event,this);"/>
							</tr>
						</table>
					</div>
				</td>
				<td align="center">
					<input title="[Alt+F]" accesskey="F" id="submit_button"
						class="button" type="submit" style="CURSOR: pointer" value="��ѯ" 
						onclick="HRMCommon.search_check('basic','advanced');">
					<input title="[Alt+C]" accesskey="C" class="button" type="button"
						value="����" onClick="window.location='searchEmpbenefit.action';">
					<div id="t1" align="center" style="DISPLAY: block; clear: both">
						<img src="../resource/images/basic_search.gif" width="8"
							height="8">
						<a href="#" class="utilsLink"
							onClick="HRMCommon.searchTypeChange();">�߼���ѯ</a>
					</div>
					<div id="t2" align="center" style="DISPLAY: none; clear: both">
						<img src="../resource/images/advanced_search.gif" width="8"
							height="8">
						<a href="#" class="utilsLink"
							onClick="HRMCommon.searchTypeChange();">������ѯ</a>
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
				    <input class="button" type="button" onclick="initDivImmUpload('IEmpSalaryBenefit', '');" value="�籣��ʼ������"/>
				    <input class="button" type="button" onclick="initDivImmUpload('IEmpSalaryBenefitUpd', '');" value="�籣��������"/>
				    <input class="button" type="button" value="���ݵ���" id="btnOutput" onClick="HRMCommon.search_check('basic','advanced');HRMCommon.export_check('','','export');" />
				</ty:auth>
			</td>
			<td align="right">���β�ѯ���õ�<s:property value="page.totalRows" />��Ա����¼&nbsp;</td>
		  </tr>
		</table>
		
		<table id="empbenefittable" cellpadding="0" cellspacing="0"
			width="100%" border="0" class="gridtableList">
			<tr>
				<th nowrap="nowrap" align="center">
				<a href="#" onClick="HRMCommon.order_submit('empDistinctNo','searchEmpbenefitForm');">Ա�����</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='empDistinctNo_img'></th>
				<th nowrap="nowrap" align="center">
				<a href="#" onClick="HRMCommon.order_submit('empName','searchEmpbenefitForm');">����</a><img src='../resource/images/arrow_.gif' width='8' height='10'id='empName_img'></th>
				<th nowrap="nowrap" align="center">
				<a href="#" onClick="HRMCommon.order_submit('empBenefitType','searchEmpbenefitForm');">�籣����</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='empBenefitType_img'></th>
				<th nowrap="nowrap" align="center">
				<a href="#" onClick="HRMCommon.order_submit('benefit.ebfPensionAmount','searchEmpbenefitForm');">�籣����</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='benefit.ebfPensionAmount_img'></th>
				<th nowrap="nowrap" align="center">
				<a href="#" onClick="HRMCommon.order_submit('benefit.ebfHousingAmount','searchEmpbenefitForm');">���������</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='benefit.ebfHousingAmount_img'></th>
				<th nowrap="nowrap" align="center">
				<a href="#" onClick="HRMCommon.order_submit('benefit.ebfInsuranceAmount','searchEmpbenefitForm');">��������</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='benefit.ebfInsuranceAmount_img'></th>
				<th nowrap="nowrap" align="center">
				<a href="#" onClick="HRMCommon.order_submit('config.escColumn15','searchEmpbenefitForm');">���˽��籣</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='config.escColumn15_img'></th>
				<th nowrap="nowrap" align="center">
				<a href="#" onClick="HRMCommon.order_submit('config.escColumn16','searchEmpbenefitForm');">��˾���籣</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='config.escColumn16_img'></th>
				<th nowrap="nowrap" align="center">
				<a href="#" onClick="HRMCommon.order_submit('benefit.ebfStartMonth','searchEmpbenefitForm');">��Ч����</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='benefit.ebfStartMonth_img'></th>
				<th nowrap="nowrap" align="center">����</th>
			</tr>
			<s:if test="!empbenefitList.isEmpty()">
				<s:iterator value="empbenefitList">
				<tr id="<s:property value='id'/>">
					<td id="empDistinctNo<s:property value='id'/>" align="center">
						<span title="��ְ״̬��<s:property value='getEmpStatusName(empStatus)'/>
����������<s:property value='empLocationNo.locationName'/>
�������ţ�<s:property value='empDeptNo.departmentName'/>
<s:if test="empIdentificationType==0">���֤�ţ�<s:property value='empIdentificationNo'/></s:if>" />
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
								src="../resource/images/edit.gif" alt="�޸��籣"></img></a>
							<a href="#" onClick="showAdjustebf('<s:property value="id"/>')"><img
								src="../resource/images/ProjectCopy.gif" alt="��������"></img></a>
							<a href="#" onClick="ebfDel('<s:property value="id"/>','<s:property value="benefit.ebfId"/>')"><img
								src="../resource/images/delete.gif" alt="ɾ��"></img></a>
						  </s:if>
						</td>
					</s:if>
					<s:else>
						<td colspan="6">&nbsp;</td>
						<td nowrap="nowrap" align="center">
							<s:if test="empStatus==1">
								<a href="#" onClick="showAddebf('<s:property value="id"/>')"><img
									src="../resource/images/CreateProject.gif" alt="�����籣"></img></a>
							</s:if>
						</td>
					</s:else>
				</tr>
				</s:iterator>
			</s:if>
			<s:else>
				<tr> 
					<!-- �����ڷ���������Ա���籣���ϣ� -->
					<td align="center" colspan="10">�����ڷ���������Ա���籣���ϣ�</td>
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
									��Ч����
									<span class="required">*</span>��
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
									�籣������
								</td>
								<td>
									<input id="ebfPensionAmount" name="ebfPensionAmount"
										onkeyup="value=value.replace(/[^0-9{\.}]/g,'');changeDmalPoint(document.getElementById('ebfPensionAmount'));"
										onkeypress="HRMCommon.MKeyTextLength(this,9);" />
								</td>
							</tr>
							<tr>
								<td align="right">
									�����������
								</td>
								<td>
									<input id="ebfHousingAmount" name="ebfHousingAmount"
										onkeyup="value=value.replace(/[^0-9{\.}]/g,'');changeDmalPoint(document.getElementById('ebfHousingAmount'));"
										onkeypress="HRMCommon.MKeyTextLength(this,9);" />
								</td>
							</tr>
							<tr>
							<td align="right">
								����������
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
									<input id="ebfAddbtn" type="button" value="����"
										onClick="submitAdjustebf();" />
									&nbsp;&nbsp;&nbsp;
									<input type="button" value="ȡ��"
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
//action��ת���������籣ҳ��
function showAddebf(employeeId){
	document.getElementById('searchOrExport').value='';
	var url = "addBenefitInit.action?employeeId="+employeeId;
    document.searchEmpbenefitForm.action = url;
    HRMCommon.search_check('basic','advanced');
    document.searchEmpbenefitForm.submit();
}
//action��ת���޸�Ա���籣ҳ��
function showCopyebf(employeeId,ebfId){
	document.getElementById('searchOrExport').value='';
	var url = "updateBenefitInit.action?employeeId="+employeeId+"&ebfId="+ebfId;
    document.searchEmpbenefitForm.action = url;
    HRMCommon.search_check('basic','advanced');
    document.searchEmpbenefitForm.submit();
}
//action��ת��ɾ���籣ҳ��
function ebfDel(employeeId,ebfId){
 	var empname = document.getElementById('empName' + employeeId).innerHTML;
  	if (confirm("ȷ��ɾ��" + empname.trim() + "�ĵ�ǰ�籣��¼��")){
  		document.getElementById('searchOrExport').value='';
    	var url = "delEmpbenefit.action?ebfId=" + ebfId+"&employeeId="+employeeId;
    	document.searchEmpbenefitForm.action = url;
    	HRMCommon.search_check('basic','advanced');
    	document.searchEmpbenefitForm.submit();
	}
}
//�ɷ��������������˵�����
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
	$('#dlgAddEmpBenefit').dialog("option","title","��������("+titleStr+")");
	document.getElementById('employeeId').value=employeeId;
	document.getElementById('ebfStartMonthTD').value=document.getElementById('ebfStartMonthTD'+employeeId).innerHTML.trim();
	document.getElementById('ebfPensionAmount').value=document.getElementById('ebfPensionA'+employeeId).innerHTML.trim();
	document.getElementById('ebfHousingAmount').value=document.getElementById('ebfHousingA'+employeeId).innerHTML.trim();
	document.getElementById('ebfInsuranceAmount').value=document.getElementById('ebfInsuranceA'+employeeId).innerHTML.trim();
	HRMCommon.openDialog('dlgAddEmpBenefit');	
}
//�ύ�������� modified by Steven 2010-02-07
function submitAdjustebf(){
	var oldstartDate = $('#ebfStartMonthTD').val(); 
	var newstartDate = $('#startBtfYear').val()+$('#startBtfMonth').val(); 
	if(oldstartDate>=newstartDate)
		{alert("�¼�¼����ʼ���±��������ϼ�¼����ʼ���£�");return;}
	if(hrm.common.isEmpty($('#ebfPensionAmount').val()) &&
			hrm.common.isEmpty($('#ebfHousingAmount').val()) &&
			hrm.common.isEmpty($('#ebfInsuranceAmount').val()))
		{alert("����һ���������Ϊ�գ�����д��");return;}

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


//�籣������ѯ��飻
function benefitBaseCheck(event, obj) {
    if(document.getElementById("amountCategory").value == ""){
        alert("����ѡ��������࣡");
        return;
    }
    var temp = obj.value;
	event = event ? event : (window.event ? window.event : null);
    var code = event.keyCode ? event.keyCode : event.which;
	/**���֣���.�����˸�delete��Ctrl+C, Ctrl+V, Ctrl+shift,Ctrl+�ո�*/
	if(!((code >= 48 && code <= 57) ||  code == 190 ||  code == 8 || code==46 
		  || code==17 || code==16 || code==32 || code==67 || code==86)){
		    alert("ֻ�������ֻ�.");
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
 alert("���±���Ϊ\"yyyyMM\"��ʽ,��200906,200912");
 document.getElementById('benefit.ebfStartMonth').value='';
 }
}

HRMCommon.initDialog('dlgAddEmpBenefit');
</script>
<jsp:include flush="true" page="../io/div_omm_select.jsp"></jsp:include>
<jsp:include flush="true" page="../io/div_upload.jsp"></jsp:include>
</body>