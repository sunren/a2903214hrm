<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<!--
 =========================================================
' File:add_batchCmpaplan.jsp
' Version:1.1.0 standard version
' Date: 2007-2-2
' Script Written by tengsource.com
'=========================================================
' Copyright   2007 tengsource.com. All rights reserved.
' Web: http://www.tengsource.com
' Email: admin@tengsource.com
'=======================================================
 -->
<head>
	<title>Ա����н�ƻ�</title>
	<!-- DWR Start -->
	<script type='text/javascript' src="../dwr/interface/AddCompaplan.js"></script>
	<script type="text/javascript" src="../dwr/interface/DWRforSalaryCalc.js"></script>
	<!-- DWR end -->
	<script type="text/javascript" src="../resource/js/hrm/compensation.js"></script>
</head>

<body onload="HRMCommon.check_order();">
<s:component template="bodyhead">
	<s:param name="pagetitle" value="'Ա����н�ƻ�'" />
	<s:param name="helpUrl" value="'23'" />
</s:component>
<span class="errorMessage" id="message"></span>
<input id="currentDate" type="hidden" value="<s:property value='curEffDate'/>"/>
<s:form id="searchBatchCompaplan" name="searchBatchCompaplan" action="searchBatchCompaplan" namespace="/compensation" method="POST">
	<s:hidden  id="order" name="page.order" />
	<input type="hidden" id="id" name="detailid" />
	<s:hidden  id="rows" name="rows" value="%{salaryconfList.size()}" />
	<s:hidden  id="operate" name="page.operate" />
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="formtable">
		<tr>
			<td>
				<div id="basic">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<s:textfield id="empNo" label="Ա��" name="emp.empName" size="16" maxlength="64"/>
							<td align="right">��֯��Ԫ:</td>
							<td>
								<s:orgselector id="orgselector1" name="emp.empDeptNo.departmentName" hiddenFieldName="emp.empDeptNo.id"/>
							</td>
							<s:select label="�ù���ʽ" name="emp.empType.id" list="empType" listKey="id"
							 listValue="emptypeName" multiple="false" emptyOption="true" value="emp.empType.id" size="1" />
							<s:select label="��������" name="emp.empLocationNo.id"
								list="location" listKey="id" listValue="locationName"
								multiple="false" emptyOption="true"
								value="emp.empLocationNo.id" size="1" />
						</tr>
					</table>
				</div>
			</td>
			<td align="center">
				<input title="[Alt+F]" accesskey="F" id="submit_button"
					class="button" type="submit" style="CURSOR: pointer"
					onclick="HRMCommon.search_check('basic','advanced');" name="search"
					value="��ѯ">
				<input title="[Alt+C]" accesskey="C" class="button" type="button" name="reset"
					style="CURSOR: pointer" value="����"
					onclick="window.location=searchBatchCompaplan.action;">
			</td>
		</tr>
	</table>

		<table width="100%">
		  <tr>
		    <br/>
		  </tr>
		  <tr>
			<td align="right">���β�ѯ���õ�<s:property value="page.totalRows" />��Ա����¼&nbsp;</td>
		  </tr>
		</table>

		<table id="thetable" width="100%" border="0" cellspacing="0" cellpadding="0" class="gridtableList">
		<thead>
			<tr>
				<th nowrap>
					<a href="#" onclick="HRMCommon.order_submit('emp.empName');">����</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='emp.empName_img'>
				</th>
				<th nowrap>
					<a href="#" onclick="HRMCommon.order_submit('escJobgrade');">н�ʼ���</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='escJobgrade_img'>
				</th>
				<th nowrap>��������</th>
				<th nowrap>˰ǰ����</th>
				<th nowrap>��н����</th>
				<th>н�ʼ���</th>
				<th>��Ч����</th>
				<th>��������</th>
				<th>��������</th>
				<th nowrap>��˰ǰ����</th>
				<th>����</th>
			</tr>
			</thead>
		<s:if test="!salaryconfList.isEmpty()">
				<s:iterator value="salaryconfList" status="index">
					<tr>
						<td>
							<span title="Ա����: <s:property value='employee.empDistinctNo'/> 
								����: <s:property value='employee.empDeptNo.departmentName'/>
								<s:if test="employee.empBuNo != null && employee.empBuNo.id!=''">ҵ��Ԫ: <s:property value='employee.empBuNo.businessunitName'/></s:if>
								��������: <s:property value='employee.empLocationNo.locationName'/>
								�ù���ʽ: <s:property value='employee.empType.emptypeName'/>" />
							<a id="name<s:property value='%{#index.count}'/>" href="#" onclick="HRMCp.viewDetail('<s:property value="employee.id"/>');" class="listViewTdLinkS1" nowrap>
								<s:property value="employee.empName" /></a>
						</td>
						<td align="center" width="60" nowrap>
							<s:property value="escJobgrade.jobGradeName" />
						</td>
						<td align="right" nowrap>
							<s:property value="basicSalary" />
							<input type="hidden" name="bs" id="<s:property value="%{'esc_BasesalaryCur'+(#index.count)}"/>" 
							 value="<s:property value='basicSalary'/>" />
						</td>
						<td align="right" nowrap>
							<s:property value="moneybeforetax" />
							<input type="hidden" id="<s:property value="%{'esc_Empname'+(#index.count)}"/>" name="hiddenName"  value="<s:property value="%{employee.empName}"/>" />
							<input type="hidden" id="<s:property value="%{'jobgradecur'+(#index.count)}"/>" name="hiddenName"  value="<s:property value="%{escJobgrade.id}" />"/>
							<input type="hidden" id="<s:property value="%{'empid'+(#index.count)}"/>" name="hiddenName"  value="<s:property value="%{id}" />"/>
							<input type="hidden" id="<s:property value="%{'escEsavId'+(#index.count)}"/>" name="hiddenName" value="<s:property value="%{escEsavId.id}" />"/>
							<input type="hidden" id="<s:property value="%{'escEsacName'+(#index.count)}"/>" name="hiddenName" value="<s:property value="%{escEsavId.esavEsac.esacName}" />"/>
							<input type="hidden" id="<s:property value="%{'esaEsavIdPro'+(#index.count)}"/>" name="hiddenName" value="<s:property value="%{comp.esaEsavIdPro.id}" />"/>
							<input type="hidden" id="<s:property value="%{'esaEsavIdCur'+(#index.count)}"/>" name="hiddenName" value="<s:property value="%{comp.esaEsavIdCur.id}" />"/>
							<input type="hidden" id="<s:property value="%{'escJobgradeCur'+(#index.count)}"/>" name="hiddenName" value="<s:property value="%{escJobgrade.id}" />"/>
							<input type="hidden" id="<s:property value="%{'escJobgradePro'+(#index.count)}"/>" name="hiddenName" value="<s:property value="%{comp.esaJobgradePro.id}" />"/>
		                    <input type="hidden" id="<s:property value="%{'ecpType'+(#index.count)}"/>" name="hiddenName" value="<s:property value="%{comp.esaEcptypeId.id}" />"/>
							<input type="hidden" id="<s:property value="%{'salaryadjId'+(#index.count)}"/>" name="hiddenName" value="<s:property value="%{comp.id}" />"/>
							<input type="hidden" id="<s:property value="%{'ecpEffdatePro'+(#index.count)}"/>" name="hiddenName" value="<s:property value="%{comp.esaCurEffDate}" />"/>
							<input type="hidden" id="<s:property value="%{'esaComments'+(#index.count)}"/>" name="hiddenName" value="<s:property value="%{comp.esaComments}" />"/>
						</td>
						<td align="right">
							<s:property value="comp.esaEcptypeId.ecptypeName" />
						</td>
						<td align="right" >
							<s:property value="comp.esaJobgradePro.jobGradeName" />
						</td>
						<td align="right">
							<s:date name="comp.esaCurEffDate" format="yyyy-MM"/>
						</td>
						<td align="right" >
							<s:property value="newBasicSalary" />
						</td>
						<td align="right" >
						    <s:if test="comp!=null&&comp.id!=null">
							<s:property value="comp.esaCurIncrRate" />%
							</s:if>					
						</td>
						<td align="right" >
							<s:property value="newmoneybeforetax" />
						</td>
                      <s:if test="comp!=null&&comp.id!=null">
                       <td align="left" nowrap>
							<img alt='�޸ĵ�н' src="../resource/images/edit.gif"
								style="CURSOR: pointer" �� border="0"
								�� onclick="myshowFastDiv('<s:property value="#index.count"/>','<s:property value="comp.id" />');" ��/>
							<img src="../resource/images/delete.gif"
								style="CURSOR: pointer" alt='ɾ��' border='0'
								onclick="deleteCompaplan1('<s:property value="#index.count"/>');" />
						</td>
                      </s:if>
                      <s:else>
                      <td align="center" nowrap>
						<a href="#" onclick=myshowFastDiv('<s:property value="#index.count"/>','<s:property value="comp.id" />')><img
							src="../resource/images/CreateProject.gif" alt='������н' border='0' />
						</a>
					 </td>
                      </s:else>
					</tr>
				</s:iterator>
			</s:if>
			<s:else>
				<tr id="nodate">
					<td colspan="15" align="center">
						�����ڷ��������ĵ�н�ƻ���
					</td>
				</tr>
			</s:else>
		</table>

	<%--othersalary_div��Ϊ������ĵ�н�� --%>
	<div id="dlgOtherSalary" style="width:780;display:none;" class="prompt_div_inline"  >
 		<div  id="change_stutus_error" class="prompt_div_err"></div>
		<input id="div_empid" type="hidden" name="esa.esaEmpno.id"/>
			<input id="div_rowID" type="hidden" name="hiddenName"  />
			<table  width="100%"  border="0" cellspacing="0" cellpadding="0" height="222px" class="basictable" >
		  	<tr>
				<td width="100%" colspan="2" nowrap>
					��н����:<span id="div_ecpType"><s:select id="div_ecpTypePro"  value="" name="esa.esaEcptypeId.id" list="ecptypeList" listKey="id" listValue="ecptypeName" emptyOption="false"  /></span>
					<!-- н�ʼ���:<span id="div_jobgradeCur"></span> -->
					�¼���:<span id="div_JG"><s:select id="div_jobgradePro" value="" name="esc.escJobgrade.id" list="jobgradePro" listKey="id" listValue="jobGradeName" /></span>
			         ��Ч����
			        <s:select id="div_ecpEffdatePro" name="curEffDate" list="yearAndMonth"  emptyOption="false"  />	
					��н��ע
					<input type="text" name="esa.esaComments" id="div_escComments" size="36" onkeypress="MKeyTextLength(this,128);" >
				</td>
		  	</tr>
		  	<tr>
				<th width="50%" style="{border-right: 1px solid #555555}">
					<u><span id="div_esacNameCur">н��������</span></u>
				</th>
				<th width="50%">
					<s:select id="acctList" name="esc.escEsavId.id" 
						emptyOption="true" value="" onchange="setAcctItem(this.value,document.getElementById('div_rowID').value)"
						list="esaList" listKey="id" listValue="esavEsac.esacName" required=""/>
				</th>
		  	</tr>
		  	<tr>
				<td>
					<table id="configTable" border="0" cellspacing="0" cellpadding="0" class="basictable" width="100%">
					</table>
				</td>
				<td>
					<table id="newConfigTable" border="0" cellspacing="0" cellpadding="0" class="basictable" width="100%">
					</table>
				</td>
		 	</tr>
		  	<tr>
				<td colspan="2" align="center">
				</td>
		  	</tr>
		  	<tr>
				<td align="center" colspan="9">
					<input id="btnSubmit" name="hiddenName" type="button" onclick="submitDivCompa();" value="�ύ">
					<input id="div_btnSubmit" name="hiddenName" type="button" onclick="viewNewSalaryPaid();" value="���¼���н��">
					<input type="button" name="hiddenName" onclick="HRMCommon.closeDialog('dlgOtherSalary');" value="�ر�">
				</td>
		  	</tr>
		</table>
		<iframe scrolling="no" style="position:absolute;z-index:-1;width:expression(this.parentNode.offsetWidth-2);height:expression(this.parentNode.offsetHeight-2);top:0px;left:0px;width: 779px; height: 246px; " frameborder="0"></iframe>
	</div>
		
	
	
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="gridtableList">
  			<tr class="listViewPaginationTdS1">
 			  <td colspan="10" align="center">
 			    <s:hidden id="page.currentPage" name="page.currentPage" />
 			    <s:component template="pager">
 			        <s:param name="pageNo" value="page.currentPage" /><s:param name="totalPages" value="page.totalPages" /><s:param name="totalRows" value="page.totalRows" />
          	  	    <s:param name="start" value="page.start" /><s:param name="end" value="page.end" /><s:param name="formId" value="'searchBatchCompaplan'" />
          	  	</s:component>
			  </td>
  			</tr>
		</table>
	
	</s:form>
	
	
<script>
//�ύDIV����ĵ�н�ƻ�
function submitDivCompa(){   
  	   isSubmit='1';
	   viewNewSalaryPaid();
	   HRMCommon.closeDialog('dlgOtherSalary');
}

//�򿪵�н�ƻ���DIV��(�����޸ĵĵ�н��)
var acctItemLength;
var rowid;
var configRowLength;

function myshowFastDiv(id,compaId){
	rowIndex = id;
	//��ʼ����ͷԱ����Ϣ
	var empname = document.getElementById('esc_Empname' + id).value;
	document.getElementById("div_empid").value = document.getElementById('empid' + id).value;
	document.getElementById("div_rowID").value = id;
	var jobgradeId = document.getElementById('escJobgradeCur' + id).value;
	
	document.getElementById("btnSubmit").disabled = false;
	document.getElementById("div_esacNameCur").innerHTML = document.getElementById('escEsacName' + id).value;
	if(compaId==''){
		//document.getElementById("h3").innerHTML = "����Ա��(" + empname + ")�ĵ�н�ƻ�" ;
		$('#dlgOtherSalary').dialog("option","title","����Ա��(" + empname + ")�ĵ�н�ƻ�");
		document.getElementById('div_escComments').value="";
		document.getElementById("div_jobgradePro").value =jobgradeId;
		document.getElementById("div_ecpEffdatePro").value =document.getElementById('currentDate').value;
	}else{
		//document.getElementById("h3").innerHTML = "�޸�Ա��(" + empname + ")�ĵ�н�ƻ�" ;
		$('#dlgOtherSalary').dialog("option","title","�޸�Ա��(" + empname + ")�ĵ�н�ƻ�");
		document.getElementById("div_jobgradePro").value = document.getElementById("escJobgradePro"+id).value;
		document.getElementById("div_ecpTypePro").value = document.getElementById("ecpType"+id).value;
		var tmp = document.getElementById("ecpEffdatePro"+id).value;
		tmp = tmp.substring(0,tmp.length-3);
		document.getElementById("div_ecpEffdatePro").value = tmp;
		document.getElementById("div_escComments").value=document.getElementById("esaComments"+id).value;
	}
	
	//$('#dlgOtherSalary').show();
	HRMCommon.openDialog('dlgOtherSalary','searchBatchCompaplan');
	
	document.getElementById("acctList").value = document.getElementById("escEsavId"+id).value;
	//get Cofig for emp salary
	var configId = document.getElementById('empid' + id).value;
		
		rowid = id;
		for(var i=0;i<acctItemLength;i++) {
		document.getElementById('acctItems'+i).parentNode.removeChild(document.getElementById('acctItems'+i));
	}
	for(var i=0;i<configRowLength;i++) {
		document.getElementById('configAcctItems'+i).parentNode.removeChild(document.getElementById('configAcctItems'+i));
	}

		acctItemLength="";
		configRowLength="";
	
	AddCompaplan.getConfigItemsById(configId,configCallBack);
	if(compaId==""){
		setAcctItem(document.getElementById("escEsavId"+id).value, id);
	}else{
		document.getElementById("acctList").value = document.getElementById("esaEsavIdPro"+id).value; //toto
		AddCompaplan.getCompaItemsById(compaId,'pro',compaProCallBack);
	}
	
	
		function compaProCallBack(msg) {
			if(msg == null){
				errMsg("errMsg", "�����쳣����ˢ�º����ԣ�");
				return;
			}
			var trObj;
			acctItemLength = msg.length/2;
			for(var i=0;i<msg.length;i++) {
				if(i%2==0){
					trObj = document.getElementById('newConfigTable').insertRow(i/2);
					trObj.id = 'acctItems'+(i/2);
					trObj.name = 'div_tr';
				}
				var toWrite="onkeydown='HRMCommon.checkOnKeyDownFloat(event)' style='text-align:right'";
				if(msg[i].esaiDataIsCalc != 0) {
					toWrite="readOnly class='nothinginput' style='text-align:right'";
				}
				
				tdObj = document.createElement('td');
				tdObj.align='left';
				tdObj.width='30%';
				tdObj.height='27px';
				tdObj.nowrap='true';
				tdObj.innerHTML="<label for='empsalaryconfig.escColumn"+(i+1)+"'>"+msg[i].esaiEsdd.esddName+":</label>";
				trObj.appendChild(tdObj);
				tdObj = document.createElement('td');
				tdObj.align='left';
				tdObj.height='20px';
				tdObj.width='25%';
				tdObj.innerHTML="<input type='text' "+toWrite+" name='esc.escColumn"+(i+1)+"'"+" id='escColumn"+(i+1)+ "' " + "value='" + msg[i].itemConfigValue + "'" + "size='8' />";
				trObj.appendChild(tdObj);
				if(i==msg.length-1&&msg.length%2==1){
					tdObj = document.createElement('td');
					tdObj.align='left';
					tdObj.width='25%';
					tdObj.height='27px';
					tdObj.nowrap='true';
					trObj.appendChild(tdObj);
					tdObj = document.createElement('td');
					tdObj.width='25%';
					tdObj.height='27px';
					trObj.appendChild(tdObj);
				}
			}
			if(acctItemLength>0) {
				document.getElementById('btnSubmit').disabled=false;
			}else{
				document.getElementById('btnSubmit').disabled=true;
			}
		}
	
	function configCallBack(msg) {
	  	if(msg==null || msg==""){
	  		alert("��Ա����н�����û�н�������ѱ�ɾ������ˢ�º����ԣ�");
	  		return;
	  	}
		var trObj;
		configRowLength = msg.length/2;
		for(var i=0;i<msg.length;i++) {
			if(i%2==0){
				trObj = document.getElementById('configTable').insertRow(i/2);
				trObj.id = 'configAcctItems'+(i/2);
				trObj.name = 'div_tr';
			}
			var toWrite="readOnly class='nothinginput' style='text-align:right'";
			tdObj = document.createElement('td');
			tdObj.align='left';
			tdObj.width='30%';
			tdObj.height='27px';
			tdObj.nowrap='true';
			tdObj.innerHTML="<label for='empsalaryconfig.escColumn"+(i+1)+"'>"+msg[i].esaiEsdd.esddName+":</label>";
			
			trObj.appendChild(tdObj);
			tdObj = document.createElement('td');
			tdObj.align='left';
			tdObj.width='20%';
			tdObj.height='27px';
			tdObj.innerHTML="<input type='text' "+toWrite+" name='escColumnOld' id='escColumnOld" + (i+1) + "' " + "value='" + msg[i].itemConfigValue + "'" + "size='8' />";
			trObj.appendChild(tdObj);
			if(i==msg.length-1&&msg.length%2==1){
				tdObj = document.createElement('td');
				tdObj.align='left';
				tdObj.width='25%';
				tdObj.height='27px';
				tdObj.nowrap='true';
				trObj.appendChild(tdObj);
				tdObj = document.createElement('td');
				tdObj.width='25%';
				tdObj.height='27px';
				trObj.appendChild(tdObj);
			}
		}
	}
	  if(configRowLength>0) {
		document.getElementById('btnSubmit').disabled=false;
	}else{
		document.getElementById('btnSubmit').disabled=true;
	}
}

//ɾ����н�ƻ�
function deleteCompaplan1(id){
	var empname=document.getElementById('esc_Empname'+id).value;
	if (confirm("��ȷ��Ҫɾ��"+empname+"�ĵ�н�ƻ���")){ 
		  document.forms[0].action = 'deleteEmpsalaryadj.action?employeeId='+document.getElementById('empid'+id).value+'&compaplanIds='+document.getElementById('salaryadjId'+id).value;
          HRMCommon.search_check('basic','advanced');
          document.forms[0].submit();		
	}
}

//ѡ���н������µ�������
function setAcctItem(acct,rowId) {
	for(var i=0;i<acctItemLength;i++) {
		document.getElementById('acctItems'+i).parentNode.removeChild(document.getElementById('acctItems'+i));
	}
	if(acct=='') {
		acctItemLength = 0;
		document.getElementById('btnSubmit').disabled=true;
		return;
	}
	var configId = document.getElementById('empid' + rowId).value;
	AddCompaplan.getAcctItemsById(acct,configId,callBack);
	function callBack(msg) {
		if(msg == null){
			errMsg("errMsg", "�����쳣����ˢ�º����ԣ�");
			return;
		}
		var trObj;
		acctItemLength = msg.length/2;
		for(var i=0;i<msg.length;i++) {
			if(i%2==0){
				trObj = document.getElementById('newConfigTable').insertRow(i/2);
				trObj.id = 'acctItems'+(i/2);
				trObj.name = 'div_tr';
			}
			var toWrite="onkeydown='HRMCommon.checkOnKeyDownFloat(event)' style='text-align:right'";
			if(msg[i].esaiDataIsCalc != 0) {
				toWrite="readOnly class='nothinginput' style='text-align:right'";
			}
			
			tdObj = document.createElement('td');
			tdObj.align='left';
			tdObj.width='30%';
			tdObj.height='27px';
			tdObj.nowrap='true';
			tdObj.innerHTML="<label for='empsalaryconfig.escColumn"+(i+1)+"'>"+msg[i].esaiEsdd.esddName+":</label>";
			//alert(msg[i].esaiDataName);
			trObj.appendChild(tdObj);
			tdObj = document.createElement('td');
			tdObj.align='left';
			tdObj.height='20px';
			tdObj.width='25%';
			var strHTML = "<input type='text' "+toWrite+" name='esc.escColumn"+(i+1)+"'"+" id='escColumn"+(i+1) + "' " + "value='";
			if(msg[i].itemValue!=null){
				strHTML = strHTML + msg[i].itemValue;
			}
			strHTML = strHTML + "'" + "size='7' />";
			tdObj.innerHTML=strHTML;
			trObj.appendChild(tdObj);
			if(i==msg.length-1&&msg.length%2==1){
				tdObj = document.createElement('td');
				tdObj.align='left';
				tdObj.width='25%';
				tdObj.height='27px';
				tdObj.nowrap='true';
				trObj.appendChild(tdObj);
				tdObj = document.createElement('td');
				tdObj.width='25%';
				tdObj.height='27px';
				trObj.appendChild(tdObj);
			}
		}
		if(acctItemLength>0) {
			document.getElementById('btnSubmit').disabled=false;
		}else{
			document.getElementById('btnSubmit').disabled=true;
		}
	}
}

//��н�ƻ��������У������¼���н�ʡ���ť��������¼���н�ʣ��ύ��ť�ύ�����н�ʲ��ύ��
//Ϊ1ʱΪ�ύ��0Ϊ���㡣
var isSubmit='0';
function viewNewSalaryPaid() {
	var acct  = document.getElementById('acctList').value;
	var jobGrade = document.getElementById('div_jobgradePro').value;
	var empid = document.getElementById('div_empid').value;
	if(acct==null || acct=='') {
		alert("��ѡ�����ף�");
		return false;
	}
	
	var objetC=new Object;
	for(var i=1;i<acctItemLength*2+1;i++) {
		if(eval("document.getElementById('escColumn"+i+"')").value=='') {
			eval("document.getElementById('escColumn"+i+"')").value='0';
		}
		eval("objetC.escColumn"+i+"=document.getElementById('escColumn"+i+"').value");
	}
	var params = DWRUtil.getValues(objetC);
	DWRforSalaryCalc.calculateSalary(acct,empid,jobGrade,objetC,calculateCallback);
	function calculateCallback(msg) {
		if(msg == null){
			errMsg("errMsg", "�����쳣����ˢ�º����ԣ�");
			return;
		}
		for(var i=1;i<acctItemLength*2+1;i++) {
			eval("document.getElementById('escColumn"+i+"').value=msg.escColumn"+i);
		}
		if(isSubmit=='1'){
		   isSubmit=='0';
		   document.forms[0].action = 'addEmpsalaryadj.action';
           HRMCommon.search_check('basic','advanced');
           document.forms[0].submit();
		}
	}
}
HRMCommon.initDialog('dlgOtherSalary');  
</script>
</body>
