<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<%@taglib prefix="ty" uri="/tengsource"%>
<!--
 =========================================================
' File:search_emp.jsp
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
	<title>Ա��н������</title>
	<script type='text/javascript' src='../dwr/interface/DWRforAcctItemDef.js'></script>
	<script type='text/javascript' src='../dwr/interface/UpdateSalaryConfigBatch.js'></script>
	<script type="text/javascript" src="../resource/js/hrm/compensation.js"></script>
</head>
<body onload="HRMCommon.check_order();">
	<s:component template="bodyhead">
		<s:param name="pagetitle" value="'Ա��н������'" />
		<s:param name="helpUrl" value="'22'" />
	</s:component>
	<span class="errorMessage" id="message"></span>

	<s:form id="searchSalary" name="searchSalary" action="searchSalary" namespace="/compensation" method="POST">
		<table width="100%" border="0" cellspacing="2" cellpadding="0" style="background-color: #ECF6FB;border: 1px #6BB5DA solid">
			<tr>
				<td>
					<s:hidden id="id" name="detailid" />
					<s:hidden id="order" name="page.order" />
					<input type="hidden" id="operate" name="page.operate" />

					<div  style="DISPLAY: block; clear: both">
						<table width="100%" border="0" cellspacing="0" cellpadding="3">
							<tr>
								<s:textfield label="Ա��" id="employee" name="emp.empName"
									size="16" maxlength="64" />
								<td align="right">��֯��Ԫ:</td>
								<td>
						             <s:orgselector id="orgselector1" name="emp.empDeptNo.departmentName" hiddenFieldName="emp.empDeptNo.id" isShowDisable="show"/>
								</td>
								<s:select label="�ù���ʽ" id="empType" name="emp.empType.id"
									list="emptype" listKey="id" listValue="emptypeName"
									multiple="false" headerKey="" headerValue="��ѡ��" />
								<s:select label="��������" id="empLocationNo"
									name="emp.empLocationNo.id" list="location" listKey="id"
									listValue="locationName" multiple="false" headerKey=""
									headerValue="��ѡ��" />
							</tr>
							<tr>
								<s:select label="н������" list="acctList" listKey="id"
									listValue="esavEsac.esacName" name="emp.config.escEsavId.id"
									emptyOption="true" />
								<s:select label="н�ʼ���" list="jobgradeList" listKey="id"
									listValue="jobGradeName" name="emp.config.escJobgrade.id"
									emptyOption="true" />
                                <s:select label="�ɱ�����" id="emp.costCenter" name="emp.costCenter"
									list="costCenterList" multiple="false" emptyOption="true" />
								<ty:auth auths="201"> 
									<s:select label="Ա��״̬" id="empStatus" name="emp.empStatus"
										list="empStatus" listKey="id.statusconfNo"
										listValue="statusconfDesc" multiple="false"
										emptyOption="false" value="emp.empStatus" size="1" />
								</ty:auth>
							</tr>
						</table>
					</div>
					
				</td>
				<td align="center">
					<input title="[Alt+F]" accesskey="F" id="submit_button"
						class="button" type="submit"
						onclick="document.getElementById('searchOrExport').value='';"
						style="CURSOR: pointer" value="��ѯ">
					<input title="[Alt+C]" accesskey="C" class="button" type="button"
						value="����" onClick="window.location='searchSalary.action';">
					<br>
					
				</td>
			</tr>
		</table>

		<table width="100%">
		  <tr>
		    <br/>
		  </tr>
		  <tr>
			<td align="left">
				<input type="hidden" name="searchOrExport" id="searchOrExport" />
				<ty:auth auths="201">
				    <input class="button" type="button" value="�����޸�" onclick="return updateBatch()" />
				</ty:auth>
				<ty:auth auths="201,3 or 201,2">
				    <input class="button" type="button" onclick="initDivImmUpload('IEmpSalaryConfig');" value="���ݵ���"/>
					<input class="button" type="button" value="���ݵ���" id="export_button" onClick="HRMCommon.export_check('','','export');" />
				</ty:auth>
			</td>
			<td align="right">���β�ѯ���õ�<s:property value="page.totalRows" />��н�����ü�¼&nbsp;</td>
		  </tr>
		</table>

		<table id="compaplantable" cellpadding="0" cellspacing="0" width="100%" border="0" class="gridtableList">
			<tr>
				<th align="center" width="3%">
					<input id="checkboxforall" name='checkboxforall' class="checkbox"
						type="checkbox" onclick="HRMCommon.checkAllByName('changIds','checkboxforall');" value="ȫѡ">
				</th>
				<th>
					<a href="#" onclick="HRMCommon.order_submit('empDistinctNo');">
					Ա�����</a><img src='../resource/images/arrow_.gif' width='8' height='10'
						id='empDistinctNo_img'>
				</th>
				<th>
					<a href="#" onclick="HRMCommon.order_submit('empName');">
					����</a><img src='../resource/images/arrow_.gif' width='8' height='10'
						id='empName_img'>
				</th>
				<th>
					<a href="#" onclick="HRMCommon.order_submit('empDeptNo.departmentName');">
					����</a><img src='../resource/images/arrow_.gif' width='8' height='10'
						id='empDeptNo.departmentName_img'>
				</th>
				<th>
					<a href="#" onclick="HRMCommon.order_submit('empType.emptypeName');">
					�ù���ʽ</a><img src='../resource/images/arrow_.gif' width='8' height='10'
						id='empType.emptypeName_img'>
				</th>
				<th>
					<a href="#" onclick="HRMCommon.order_submit('empStatus');">
					״̬</a><img src='../resource/images/arrow_.gif' width='8' height='10'
						id='empStatus_img'>
				</th>
				<th>
					<a href="#"	onclick="HRMCommon.order_submit('escJobgrade.jobGradeName');">
					н�ʼ���</a><img src='../resource/images/arrow_.gif' width='8' height='10'
						id='escJobgrade.jobGradeName_img'>
				</th>
				<th>��������</th>
				<th>˰ǰ����</th>
				<th>
					<a href="#" onclick="HRMCommon.order_submit('esavEsac.esacName');">
					н������</a><img src='../resource/images/arrow_.gif' width='8' height='10'
						id='esavEsac.esacName_img'>
				</th>
				<th>
					<a href="#" onclick="HRMCommon.order_submit('config.escLastChangeTime');">
					����޸�</a><img src='../resource/images/arrow_.gif' width='8' height='10'
						id='config.escLastChangeTime_img'>
				</th>
				<ty:auth auths="201">
					<th>����</th>
				</ty:auth>
			</tr>
			<s:if test="!employeeList.isEmpty()">
				<s:iterator value="employeeList" status="index">
					<tr onMouseOver="this.bgColor='#DDF0F8'"
						onMouseOut="this.bgColor='#ffffff'">
						<s:if test="config.escJobgrade!=null">
							<td align="center" width="3%">
								<input id="changIds" type="checkbox" name='changIds'
									class="checkbox" value="<s:property value='id'/>" />
								<input id="acctIds" type="hidden" name='acctIds'
									value="<s:property value='config.escEsavId.id'/>" />
							</td>
							<td align="center">
								<a href="#" onclick="HRMCp.viewDetail('<s:property value='id'/>');" class="listViewTdLinkS1"><s:property value="empDistinctNo" /></a>
							</td>
						</s:if>
						<s:else>
							<td />
							<td align="center">
								<s:property value="empDistinctNo" />
							</td>
						</s:else>

						<td id="name<s:property value='%{#index.count}'/>" align="center">
							<s:property value="empName" />
						</td>
						<td align="center">
							<s:property value="empDeptNo.departmentName" />
						</td>
						<td align="center">
							<s:property value="empType.emptypeName" />
						</td>
						<td align="center">
							<s:if test="empStatus ==0">��ְ</s:if>
							<s:else>��ְ</s:else>
						</td>
						<td id="td_jg<s:property value='%{#index.count}'/>"
							align="center">
							<s:property value="getJobgradeName(config.escJobgrade.id)" />
						</td>
						<td id="td_bs<s:property value='%{#index.count}'/>" align="right">
							<s:property value="config.showColumn1" />
						</td>
						<td id="td_gross<s:property value='%{#index.count}'/>"
							align="right">
							<s:property value="config.showColumn8" />
						</td>
						<td id="td_esac<s:property value='%{#index.count}'/>"
							align="center">
							<s:property value="config.escEsavId.esavEsac.esacName" />
						</td>
						<td id="td_date<s:property value='%{#index.count}'/>"
							align="center">
							<s:date name="config.escLastChangeTime" format="yyyy-MM-dd" />
						</td>
						
						<ty:auth auths="201">
							<s:if test="config.escJobgrade!=null">
								<td align="left" nowrap="nowrap">
									<img src="../resource/images/edit.gif"
										style="CURSOR: pointer" alt="�޸�" border="0"
										onclick="updateSalary('<s:property value='config.id'/>')" />

									<img src="../resource/images/delete.gif"
										style="CURSOR: pointer" alt="ɾ��" border="0"
										onclick="deleteSalaryConfig('<s:property value='id'/>','<s:property value='#index.count'/>')" />
								</td>
							</s:if>
							<s:else>

								<td align="left" nowrap="nowrap">
									<a href="#" onclick="createSalaryconf('<s:property value='id'/>')"><img
											src="../resource/images/CreateProject.gif" alt="����н��"
											border="0" />
									</a>
								</td>
							</s:else>
						</ty:auth>
					</tr>
				</s:iterator>
			</s:if>
			<s:else>
				<tr>
					<!-- �����ڷ���������Ա��н�����ã� -->
					<td align="center" colspan="12">
						�����ڷ���������Ա��н�����ã�
					</td>
				</tr>
			</s:else>
		</table>
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="gridtableList">
  			<tr class="listViewPaginationTdS1">
 			  <td colspan="10" align="center">
 			    <s:hidden id="page.currentPage" name="page.currentPage" />
 			    <s:component template="pager">
 			        <s:param name="pageNo" value="page.currentPage" /><s:param name="totalPages" value="page.totalPages" /><s:param name="totalRows" value="page.totalRows" />
          	  	    <s:param name="start" value="page.start" /><s:param name="end" value="page.end" /><s:param name="formId" value="'searchSalary'" />
          	  	</s:component>
			  </td>
  			</tr>
		</table>
	</s:form>

		<div id="dlgUpdateBatchDiv" class="prompt_div_inline" title="�����޸�н��"
			style="width:350;display:none;">
			<div id="change_stutus_error" class="prompt_div_err"></div>
			<table id="newConfigTable" width="100%" border="0" cellspacing="0"
				cellpadding="0" class="basictable">
				<input type='hidden' name="empIds" id="empIds" />
				<tr>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td align='right'>ѡ��н����Ŀ��</td>
					<td>
						<select id="acctItemSelect" name="acctItemSelect"></select>
					</td>
				</tr>
				<tr>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td align='right'>��Ӧ��Ŀ��ֵ��</td>
					<td>
						<input type="text" id="batchSalary" name="batchSalary"
							onkeydown='HRMCommon.checkOnKeyDownFloat(event)' />
					</td>
				</tr>
				<tr>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td colspan='2' align="center">
						<input type="button" name="batchButton" value="ȷ��"
							onclick="updateSubmit()" />
						<input type="button" name="closeButton" value="ȡ��"
							onclick="HRMCommon.closeDialog('dlgUpdateBatchDiv')" />
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
				</tr>
				<tr>
					<td></td>
					<td></td>
				</tr>
			</table>
		</div>

<script type="text/javascript" language="javascript">
var rowid;
function updateBatch() {
	var checkBoxName = document.getElementsByName('changIds');
	var empIdValue = '';
	judge=false;
	for(var i=0;i<checkBoxName.length;i=i+1){
		if(checkBoxName[i].checked===true){
			judge=true;
			if(empIdValue != '') {
				empIdValue = empIdValue+','+checkBoxName[i].value;
			}else {
				empIdValue = checkBoxName[i].value;
			}
		}
	}
	if(judge===false){
		alert('������ѡ��һ��Ա����');
		return false;
	}
	
	document.getElementById('empIds').value = empIdValue;
	var acctValue = document.getElementsByName('acctIds');
	
	var firstIndex = 0;
	for(var i=0;i<acctValue.length;i++) {
		if(checkBoxName[i].checked===true) {
			if(firstIndex == 0){
				firstIndex = i;
			}
			if(acctValue[i].value!=acctValue[firstIndex].value){
				alert('����ͬʱ�����޸���������Ա����н�ʣ�');
				return false;
			}
		}
	}
	DWRforAcctItemDef.getAcctItemsById(acctValue[firstIndex].value,getItemCallBack);
	function getItemCallBack(msg){
		if(msg == null){
			errMsg("errMsg", "�����쳣����ˢ�º����ԣ�");
			return;
		}
		var index = 0;
		for(var i=0;i<msg.length;i++) {
			if(msg[i].esaiDataIsCalc == 0){
				document.getElementById('acctItemSelect').options[index++]=new Option(msg[i].esaiEsdd.esddName,msg[i].id,true,true);
			}
		}
		HRMCommon.openDialog('dlgUpdateBatchDiv');
	}
}
function updateSubmit(){
	var empIds = document.getElementById('empIds').value;
	var itemId = document.getElementById('acctItemSelect').value;
	var salaryValue = document.getElementById('batchSalary').value;
	
	UpdateSalaryConfigBatch.updateConfigBatch(empIds, itemId, salaryValue, updateCallBack);
	HRMCommon.closeDialog('dlgUpdateBatchDiv');
	function updateCallBack(msg) {
		try{
			var retcode = HRMCommon.comboMsgHandler(msg);
			if(retcode == 'SUCC') {
				setTimeout("window.location='searchSalary.action'",1500);
			}
		}catch(e){alert(e);}
	}
}

//����salaryconf
function createSalaryconf(id)
{
  var editaction = "addSalaryConfigInit.action?id=" + id;
  document.searchSalary.action = editaction;
  document.searchSalary.submit();
}
   //ɾ�� salaryconf
function deleteSalaryConfig(var1, index)
{
  var empname = document.getElementById('name' + index).innerHTML;
  if (confirm("��ȷ��Ҫɾ��" + empname.trim() + "��н��������"))
  {
    var url = "deleteSalaryConfig.action?id=" + var1;
    document.searchSalary.action = url;
    HRMCommon.search_check('basic', 'advanced');
    document.searchSalary.submit();
  }
}
function updateSalary(id) {
		var url='updateSalaryConfigInit.action?id='+id+"&paramString="+"updateSalaryConf";
		var fm = document.getElementById('searchSalary');
		fm.action=url;
		fm.submit();
	}
HRMCommon.initDialog('dlgUpdateBatchDiv');	
</script>
<jsp:include flush="true" page="../io/div_upload.jsp"></jsp:include>
</body>

