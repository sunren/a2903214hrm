<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<%@taglib prefix="ty" uri="/tengsource"%>
<!--
 =========================================================
' File:search_compaplan.jsp
' Version:1.1.0 standard version
' Date: 2007-2-2
' Script Written by hr.com
'=========================================================
' Copyright   2007 hr.com. All rights reserved.
' Web: http://www.hr.com
' Email: admin@hr.com
'=======================================================
 -->
<head>
	<title>��н��ʷ��ѯ</title>
	<script type="text/javascript" src="../resource/js/hrm/compensation.js"></script>
	
</head>
<body onload="HRMCommon.check_order();">
<s:component template="bodyhead">
	<s:param name="pagetitle" value="'��н��ʷ��ѯ'" />
	<s:param name="helpUrl" value="'25'" />
</s:component>
	<form id="searchCompaplan" name="searchCompaplan" action="searchCompaplan.action" method="POST">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="formtable">
			<tr>
				<td>
					<s:hidden id="rows" name="rows" value="%{compaplanList.size()}" />
					<s:hidden id="id" name="detailid" />
					<s:hidden id="order" name="page.order" />
					<input type="hidden" id="operate" name="page.operate" />
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<s:textfield label="Ա��" id="empName" name="compaplan.esaEmpno.empName" size="16"
								maxlength="64" />
							<s:select label="�ù���ʽ" name="compaplan.esaEmpno.empType.id"
								list="empType" listKey="id" listValue="emptypeName" emptyOption="true"  />
							<s:select label="��������"
								name="compaplan.esaEmpno.empLocationNo.id" list="locations"
								listKey="id" listValue="locationName"  emptyOption="true" />
						</tr>
						<tr>
							<td align="right">��֯��Ԫ:</td>
							<td>
					             <s:orgselector id="orgselector1" name="compaplan.esaEmpno.empDeptNo.departmentName" hiddenFieldName="compaplan.esaEmpno.empDeptNo.id" isShowDisable="show"/>
							</td>
							<s:select label="Ա��״̬" name="compaplan.esaEmpno.empStatus"
								list="empStatus" listKey="id.statusconfNo"
								listValue="statusconfDesc" emptyOption="true" />
							<s:select label="�ƻ�״̬" name="compaplan.esaStatus" list="status"
								listKey="id.statusconfNo" listValue="statusconfDesc"  emptyOption="true" />
						</tr>
					</table>
				</td>
				<td align="center">
					<input title="[Alt+F]" accesskey="F" id="submit_button"
						class="button" type="submit" style="CURSOR: pointer"
						onclick="HRMCommon.search_check('','');" name="search"
						value="��ѯ">
					<input title="[Alt+C]" accesskey="C" class="button" type="button"
						name="reset" style="CURSOR: pointer" value="����"
						onclick="window.location=searchCompaplan.action;">
					<br>
				</td>
			</tr>
		</table>

		<table width="100%">
		  <tr>
		    <br />
		  </tr>
		  <tr>
			<td align="left">
				<input type="hidden" name="searchOrExport" id="searchOrExport" />
				<ty:auth auths="201,3 or 201,2">
				    <input class="button" type="button" value="���ݵ���" id="searchOrExport" onClick="HRMCommon.export_check('','','export');" />
				</ty:auth>
			</td>
			<td align="right">���β�ѯ���õ�<s:property value="page.totalRows" />����н��ʷ��¼&nbsp;</td>
		  </tr>
		</table>

		<table id="thetable" cellpadding="0" cellspacing="0" width="100%" border="0" class="gridtableList">
			<tr>
				<th nowrap="nowrap">
					<a href="#" onclick="HRMCommon.order_submit('emp.empName');">Ա������</a><img
					   src='../resource/images/arrow_.gif' width='8' height='10'
						id='emp.empName_img'>
				</th>
				<th nowrap="nowrap">
					<a href="#" onclick="HRMCommon.order_submit('esaEcptypeId');">��н����</a><img
					   src='../resource/images/arrow_.gif' width='8' height='10'
						id='esaEcptypeId_img'>
				</th>

				<th nowrap="nowrap">
					<a href="#" onclick="HRMCommon.order_submit('esaJobgradeCur');">н�ʼ���</a><img
					   src='../resource/images/arrow_.gif' width='8' height='10'
						id='esaJobgradeCur_img'>
				</th>
				<th nowrap="nowrap">��������</th>
				<th nowrap="nowrap">˰ǰ����</th>
				<th nowrap="nowrap">
					<a href="#" onclick="HRMCommon.order_submit('esaJobgradePro');">��н�ʼ���</a><img
					   src='../resource/images/arrow_.gif' width='8' height='10'
						id='esaJobgradePro_img'>
				</th>
				<th nowrap="nowrap">�»�������</th>
				<th nowrap="nowrap">��������</th>
				<th nowrap="nowrap">��˰ǰ����</th>
				<th nowrap="nowrap">
					<a href="#" onclick="HRMCommon.order_submit('esaCurEffDate');">��Ч����</a><img
					   src='../resource/images/arrow_.gif' width='10' height='10'
						id='esaCurEffDate_img'>
				</th>
				<th nowrap="nowrap">
					<a href="#" onclick="HRMCommon.order_submit('esaStatus');">״̬</a><img
					   src='../resource/images/arrow_.gif' width='8' height='10'
						id='esaStatus_img'>
				</th>
			</tr>
			<s:if test="!compaplanList.isEmpty()">
				<s:iterator value="compaplanList" status="index">
					<input type="hidden"
						id="<s:property value="%{'salarybefortax'+(#index.count)}"/>"
						name="salarybefortax"
						value="<s:property value="%{moneybeforetax}"/>" />
					<input type="hidden"
						id="<s:property value="%{'newsalarybefortax'+(#index.count)}"/>"
						name="newsalarybefortax"
						value="<s:property value="%{newmoneybeforetax}"/>" />
					<input type="hidden"
						id="<s:property value="%{'esc_BasesalaryCur'+(#index.count)}"/>"
						name="esc_BasesalaryCur"
						value="<s:property value="%{basicSalary}"/>" />
					<input type="hidden"
						id="<s:property value="%{'ecp_BasesalaryPro'+(#index.count)}"/>"
						name="ecp_BasesalaryPro"
						value="<s:property value="%{newBasicSalary}"/>" />
					<tr>
						<s:hidden id="%{'empName'+(#index.count)}" name="empName"
							value="%{esaEmpno.empName}" />
						<s:hidden id="%{'esacCur'+(#index.count)}" name="esacCur"
							value="%{esaEsavIdCur.esavEsac.esacName}" />
						<s:hidden id="%{'esacPro'+(#index.count)}" name="esacPro"
							value="%{esaEsavIdPro.esavEsac.esacName}" />
						<s:hidden id="%{'mysalaryadjId'+(#index.count)}"
							name="mysalaryadjId" value="%{id}" />

						<td align="center">
							<span title="Ա�����: <s:property value='esaEmpno.empDistinctNo'/>
��������: <s:property value='esaEmpno.empDeptNo.departmentName'/>
��������: <s:property value='esaEmpno.empLocationNo.locationName'/>
�ù���ʽ: <s:property value='esaEmpno.empType.emptypeName'/>"/>
								<span name="" id="name<s:property value="%{#index.count}"/>"
								onclick="HRMCp.viewDetail('<s:property value="esaEmpno.id"/>');"
								class="view"><s:property value="esaEmpno.empName" /></span><input
									type="hidden" name="esc_Empname"
									id="esc_Empname<s:property value="%{#index.count}"/>"
									value="<s:property value="esaEmpno.empName" />">
						</td>
						<td align="center"
							id="esaEcptypeName<s:property value="%{#index.count}"/>">
							<s:property value="esaEcptypeId.ecptypeName" />
						</td>

						<td align="center"
							id="jobgradeCurName<s:property value="%{#index.count}"/>">
							<s:property value="esaJobgradeCur.jobGradeName" />
						</td>
						<td align="right">
							<s:property value="basicSalary" />
						</td>
						<td align="right">
							<s:property value="moneybeforetax" />
						</td>
						<td align="center"
							id="jobgradeProName<s:property value="%{#index.count}"/>">
							<s:property value="esaJobgradePro.jobGradeName" />
						</td>
						<td align="right">
							<s:property value="newBasicSalary" />
							<img src="../resource/images/search_icon0.gif"
								id="openDetail<s:property value="%{#index.count}"/>"
								onclick="myshowEsaDiv('<s:property value="#index.count"/>');"
								border="0" style="CURSOR: pointer" />
						</td>
						<td align="right">
							<s:property value="esaCurIncrRate" />%
						</td>
						<td align="right">
							<s:property value="newmoneybeforetax" />
						</td>
						<td align="center"
							id="<s:property value="%{'td_eff_date'+(#index.count)}"/>">
							<s:date name="esaCurEffDate" format="yyyy-MM"/>
						</td>
						<td align="center">
							<s:property value="getEcpStatus(esaStatus)" />
						</td>
					</tr>
				</s:iterator>
			</s:if>
			<s:else>
				<tr>
					<!-- ������Ա����н�ƻ�! -->
					<td align="center" colspan="12">
						������Ա����н�ƻ���
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
          	  	    <s:param name="start" value="page.start" /><s:param name="end" value="page.end" /><s:param name="formId" value="'searchCompaplan'" />
          	  	</s:component>
			  </td>
  			</tr>
		</table>

	</form>
    
		<!-- pop up Empsalaryadj div code begin -->
		<%--empsalaryadj_div��Ϊֻ�ܲ鿴�ĵ�н�� --%>
		<div id="dlgEmpSalaryadjDiv"
			style="width:780;display:none;">

			<div id="esa_change_stutus_error" class="prompt_div_err"></div>
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				height="222px" class="basictable">
				<tr>
					<td width="50%" nowrap="nowrap">
						��н����:
						<span id="div_esa_ecpType"></span> н�ʵȼ�:
						<span id="div_esa_jobgradeCur"></span> �µȼ�:
						<span id="div_esa_jobgradePro"></span>
					</td>

					<td id="div_comment_td" width="50%" nowrap="nowrap">
						��Ч����
						<span id="div_esa_effdatePro"></span>
					</td>
				</tr>
				<tr>
					<th width="50%" style="{border-right: 1px solid #555555}">
						<u><span id="div_esa_esacNameCur">н��������</span> </u>
					</th>
					<th id="div_newEsac" width="50%">
						<u><span id="div_esa_esacNamePro">н��������</span> </u>
					</th>
				</tr>
				<tr>
					<td>
						<table id="esa_CurTable" border="0" cellspacing="0"
							cellpadding="0" class="basictable" width="100%"></table>
					</td>
					<td>
						<table id="esa_ProTable" border="0" cellspacing="0"
							cellpadding="0" class="basictable" width="100%"></table>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
					</td>
				</tr>
				<tr>
					<td align="center" colspan="2" align="center">
						<input type="button" onclick="HRMCommon.closeDialog('dlgEmpSalaryadjDiv');"
							value="�ر�">
					</td>
				</tr>
			</table>
			<iframe scrolling="no"
				style="position:absolute;z-index:-1;width:expression(this.parentNode.offsetWidth-2);height:expression(this.parentNode.offsetHeight-2);top:0px;left:0px;width: 779px; height: 246px; "
				frameborder="0"></iframe>
		</div>
		

		<!-- pop up Empsalaryadj div code end -->

   
	<script type='text/javascript' src="../dwr/interface/AddCompaplan.js"></script>
	
		
	
<script type="text/javascript">
//��ʾ��н�ƻ���DIV��(�����޸ĵĵ�н��)
var esaCurRowLength;
var esaProRowLength;
function myshowEsaDiv(id){
rowIndex = id;
//��ʼ����ͷԱ����Ϣ
var empname = document.getElementById('esc_Empname' + id).value;
//document.getElementById("esa_h3").innerHTML = "Ա��(" + empname + ")�ĵ�н�ƻ�" ;
      $('#dlgEmpSalaryadjDiv').dialog("option","title","Ա��(" + empname + ")�ĵ�н�ƻ�");
document.getElementById("div_esa_esacNameCur").innerHTML = document.getElementById('esacCur' + id).value;
document.getElementById("div_esa_esacNamePro").innerHTML = document.getElementById('esacPro' + id).value;

document.getElementById("div_esa_ecpType").innerHTML = document.getElementById("esaEcptypeName"+id).innerHTML;

document.getElementById("div_esa_jobgradeCur").innerHTML = document.getElementById("jobgradeCurName"+id).innerHTML;
document.getElementById("div_esa_jobgradePro").innerHTML = document.getElementById("jobgradeProName"+id).innerHTML;

document.getElementById("div_esa_effdatePro").innerHTML = document.getElementById("td_eff_date"+id).innerHTML;

document.getElementById('dlgEmpSalaryadjDiv').style.top=event.clientY - 70;
document.getElementById('dlgEmpSalaryadjDiv').style.left=event.clientX - 500;
//$('#dlgEmpSalaryadjDiv').show();
HRMCommon.openDialog('dlgEmpSalaryadjDiv');
//get Cofig for emp salary
var compaId = document.getElementById('mysalaryadjId'+id).value;
	rowid = id;
	for(var i=0;i<esaCurRowLength;i++) {
	document.getElementById('esa_CurItems'+i).parentNode.removeChild(document.getElementById('esa_CurItems'+i));
}
for(var i=0;i<esaProRowLength;i++) {
	document.getElementById('esa_ProItems'+i).parentNode.removeChild(document.getElementById('esa_ProItems'+i));
}

	esaCurRowLength="";
esaProRowLength="";
AddCompaplan.getCompaItemsById(compaId,'cur',curCallBack);
 
function curCallBack(msg) {	  	
  	if(msg==null || msg==""){
  		alert("��Ա����н�����û�н�������ѱ�ɾ������ˢ�º����ԣ�");
  		return;
  	}
	var trObj;
	esaCurRowLength = msg.length/2;
	//alert(acctItemLength);
	for(var i=0;i<msg.length;i++) {
		if(i%2==0){
			trObj = document.getElementById('esa_CurTable').insertRow(i/2);
			trObj.id = 'esa_CurItems'+(i/2);
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
		tdObj.width='20%';
		tdObj.height='27px';
		tdObj.innerHTML="<input type='text' "+toWrite+" name='esaColumn' id='esa_CurColumn" + (i+1) + "' " + "value='" + msg[i].itemConfigValue + "'" + "size='8' />";
		trObj.appendChild(tdObj);
		if(i==msg.length-1&&msg.length%2==1){
			tdObj = document.createElement('td');
			tdObj.align='left';
			tdObj.width='30%';
			tdObj.height='27px';
			tdObj.nowrap='true';
			trObj.appendChild(tdObj);
			tdObj = document.createElement('td');
			tdObj.width='20%';
			tdObj.height='27px';
			trObj.appendChild(tdObj);
		}
	}
}
AddCompaplan.getCompaItemsById(compaId,'pro',proCallBack);
 
function proCallBack(msg) {	  	
  	if(msg==null || msg==""){
  		alert("��Ա����н�����û�н�������ѱ�ɾ������ˢ�º����ԣ�");
  		return;
  	}
	var trObj;
	esaProRowLength = msg.length/2;
	//alert(acctItemLength);
	for(var i=0;i<msg.length;i++) {
		if(i%2==0){
			trObj = document.getElementById('esa_ProTable').insertRow(i/2);
			trObj.id = 'esa_ProItems'+(i/2);
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
		tdObj.width='20%';
		tdObj.align='right';
		tdObj.height='27px';
		tdObj.innerHTML="<input type='text' "+toWrite+" name='esaColumn' id='esa_ProColumn" + (i+1) + "' " + "value='" + msg[i].itemConfigValue + "'" + "size='8' />";
		trObj.appendChild(tdObj);
		if(i==msg.length-1&&msg.length%2==1){
			tdObj = document.createElement('td');
			tdObj.align='left';
			tdObj.width='30%';
			tdObj.height='27px';
			tdObj.nowrap='true';
			trObj.appendChild(tdObj);
			tdObj = document.createElement('td');
			tdObj.width='20%';
			tdObj.height='27px';
			trObj.appendChild(tdObj);
		 }
	  }
    }
}
HRMCommon.initDialog('dlgEmpSalaryadjDiv',780);
</script>
</body>
