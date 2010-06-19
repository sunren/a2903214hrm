<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
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
<link rel="stylesheet" type="text/css" href="../resource/css/edit_select.css" />
<title>��ְ����</title>
</head>
<body align="left">
<s:component template="bodyhead">
	<s:param name="pagetitle" value="'��ְԱ������'" />
	<s:param name="helpUrl" value="'32'" />
</s:component>
<s:form id="empQuitManagement" name="empQuitManagement" action="empQuitManagement" namespace="/profile" method="POST">
	<input type="hidden" name="qemp.ids" id="ids" />
	<input type="hidden" name="id" id="delEmpId" />
	<s:hidden id="output-ommId" name="outmatchModelId" />
	<s:hidden id="output-ioName" name="outputIoName" />
	<input type="hidden" name="searchOrExport" id="searchOrExport" />
	<table width="100%" border="0" cellspacing="2" cellpadding="0" style="background-color: #ECF6FB; border: 1px #6BB5DA solid">
		<tr>
			<td>
				<div id="basic" style="DISPLAY: block; clear: both">
					<input id="operate" type="hidden" name="page.operate" />
					<s:hidden id="order" name="page.order" />
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<s:textfield label="Ա��" id="employeeId" name="employeeId" size="16" maxlength="64" />
							
							<td align="right">��֯��Ԫ:</td>
							<td>
							     <s:orgselector id="orgselector1" name="departmentName" hiddenFieldName="departmentId" isShowDisable="show"/>
							</td>
							
							<td align="right">��ְ����:</td>
							<td><s:select name="eqType" id="eqType" list="eqTypeMap"
								onchange="model.simple.changeSubSelect(this,'eqReason','eqTypeSubMap')"></s:select>
								&nbsp;&nbsp;ԭ��:
								<select name="eqReason" id="eqReason"></select>
							</td>
							<td rowspan="2">
								<input title="[Alt+F]" accesskey="F" id="submit_button" class="button" type="submit" value="��ѯ"
									onclick="document.getElementById('searchOrExport').value='';">
								<input title="[Alt+C]" accesskey="C" class="button" type="reset" value="����"
									onclick="window.location='empQuitManagement.action'">
							</td>
						</tr>
						<tr>
							<s:textfield id="eqPermission" label="������" name="eqPermission" size="16" maxlength="64"/>
							<s:textfield id="eqComments" label="��ע" name="eqComments" size="16" maxlength="64"/>
							<td align="right">��ְʱ��:</td>
							<td colspan="3">
								<s:textfield id="eqDate" name="eqDate" size="10"/>
								<img onclick="WdatePicker({el:'eqDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
								��
								<s:textfield id="eqDateTo" name="eqDateTo" size="10"/>
								<img onclick="WdatePicker({el:'eqDateTo'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
							</td>
						</tr>
					</table>
				</div>
			</td>
		</tr>
	</table>
	<br />
	<table width="100%">
		<tr>
			<td align="left">
				<ty:auth auths="101">
					<input type="button" class="button" onclick="batch('delete');" value="Ա��ɾ��" />
					<input type="button" class="button" onclick="batch('rehab')" value="������ְ" />
				</ty:auth>
				<ty:auth auths="101,2 or 101,3">
					<input class="button" type="button" id="btnOutput" value="���ݵ���"
						onClick="document.getElementById('searchOrExport').value='export';document.getElementById('empQuitManagement').submit();"/>
				</ty:auth>
			</td>
			<td align="right">���β�ѯ���õ�<s:property value="page.totalRows" />��Ա����¼&nbsp;</td>
		</tr>
	</table>

	<table cellpadding="0" cellspacing="0" width="100%" border="0" class="gridtableList">
		<tr>
			<ty:auth auths="101">
				<th align="center" width="3%">
					<input id="id_check_all" class="checkbox" type="checkbox" onclick="hrm.common.checkAllByName('quitemp.ids','id_check_all');" value="0">
				</th>
			</ty:auth>
			<th nowrap="nowrap">
				<a href="#" onclick="hrm.common.order_submit('empDistinctNo','empQuitManagement');">Ա�����</a><img
				 src='../resource/images/arrow_.gif' width='8' height='10' id='empDistinctNo_img'></th>
			<th nowrap="nowrap">
				<a href="#" onclick="hrm.common.order_submit('empName','empQuitManagement');">Ա������</a><img
				 src='../resource/images/arrow_.gif' width='8' height='10' id='empName_img'></th>
			<th nowrap="nowrap">
				<a href="#" onclick="hrm.common.order_submit('empDeptNo.departmentSortId','empQuitManagement');">����</a><img
				 src='../resource/images/arrow_.gif' width='8' height='10' id='empDeptNo.departmentSortId_img'></th>
			<th nowrap="nowrap">
				<a href="#" onclick="hrm.common.order_submit('empTerminateDate','empQuitManagement');">��ְ����</a><img
				 src='../resource/images/arrow_.gif' width='8' height='10' id='empTerminateDate_img'></th>
			<th nowrap="nowrap">
				<a href="#" onclick="hrm.common.order_submit('manager.empName','empQuitManagement');">������</a><img
				 src='../resource/images/arrow_.gif' width='8' height='10' id='manager.empName_img'></th>
			<th nowrap="nowrap">
				<a href="#" onclick="hrm.common.order_submit('quit.eqType','empQuitManagement');">����</a><img
				 src='../resource/images/arrow_.gif' width='8' height='10' id='quit.eqType_img'></th>
			<th nowrap="nowrap">
				<a href="#" onclick="hrm.common.order_submit('quit.eqReason','empQuitManagement');">��ְԭ��</a><img
				 src='../resource/images/arrow_.gif' width='8' height='10' id='quit.eqReason_img'></th>
			<th nowrap="nowrap">
				<a href="#" onclick="hrm.common.order_submit('quit.erComments','empQuitManagement');">��ע</a><img
				 src='../resource/images/arrow_.gif' width='8' height='10' id='quit.erComments_img'></th>
			<th nowrap="nowrap" colspan="2">
				<a href="#" onclick="hrm.common.order_submit('quit.eqLastChangeTime','empQuitManagement');">����</a><img
				 src='../resource/images/arrow_.gif' width='8' height='10' id='quit.eqLastChangeTime_img'></th>
		</tr>
		<tbody>
			<s:if test="!quitList.isEmpty()">
				<s:iterator value="quitList" status="quitStatus">
					<tr>
						<td align="center" width="3%">
							<input name="quitemp.ids" class="checkbox" type="checkbox" value="<s:property value='id'/>" /></td>
						<td nowrap="nowrap">
							<a class="listViewTdLinkS1" 
								href="myInfo.action?empNo=<s:property value='id'/>&empName=<s:property value='empName'/>">
							<s:property value="empDistinctNo" /></a></td>
						<td nowrap="nowrap">
							<s:property value="empName" /></td>
						<td nowrap="nowrap">
							<s:property value="empDeptNo.departmentName" /></td>
						<td nowrap="nowrap">
							<s:property value="empTerminateDate" /></td>
							<td nowrap="nowrap">
								<s:property value="quit.eqPermission.empName" /></td>
						<s:if test="quit!=null">
							<td nowrap="nowrap">
								<s:if test="quit.eqType==0">��ְ</s:if>
								<s:if test="quit.eqType==1">��ְ</s:if>
								<s:if test="quit.eqType==2">ͣн��ְ</s:if>
								<s:if test="quit.eqType==3">����</s:if>
								<s:if test="quit.eqType==4">����</s:if>
								<s:if test="quit.eqType==5">��ͬ����</s:if>
								<s:if test="quit.eqType==6">����</s:if></td>
							<td nowrap="nowrap">
								<s:property value="quit.eqReason" /></td>
							<td>
								<s:property value="quit.erComments" /></td>
						</s:if>
						<s:else>
							<td nowrap="nowrap">&nbsp;</td>
							<td nowrap="nowrap">&nbsp;</td>
							<td>&nbsp;</td>
						</s:else>
							<td align="center" nowrap="nowrap" colspan="2">
								<a href="#" onclick="hrm.profile.showInfoCardInfo('<s:property value='id'/>');">
								<img src="../resource/images/Search.gif" alt="Ա�����Ͽ�" border='0' /></a>
								<ty:auth auths="101">
									<a onclick="deleteEmp('<s:property value='id'/>');" href="#">
									<img src="../resource/images/delete.gif" alt="ɾ��"></img></a>
								</ty:auth>
							</td>
					</tr>
				</s:iterator>
			</s:if>
			<s:else>
				<tr>
					<td colspan="8" align="center">����ؼ�¼��</td>
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
					<s:param name="formId" value="'empQuitManagement'" />
				</s:component>
			</td>
		</tr>
	</table>
</s:form>
<!-- ������ְ -->
<div id="dlgReturnDiv" style="width:425px; display:none;" title="������ְ">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<table width="100%" class="prompt_div_body" cellpadding="0" cellspacing="0" width="100%" border="0">
				<tr>
					<td>��ְ����<span class="required">*</span>��</td>
					<td>
						<s:textfield id="joinDate" name="qemp.empJoinDate" required="true" size="10"/>
						<img onclick="WdatePicker({el:'joinDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="14" height="22" style="cursor:pointer" align="absmiddle">
					</td>
				</tr>
				<tr>
					<td>��ְ����<span class="required">*</span>��</td>
					<td>
						<s:select name="empquit.eqType" id="eqRType" list="eqRTypeMap"
							onchange="model.simple.changeSubSelect(this,'empquit.eqReason_id','eqTypeSubMapR')">
						</s:select>
					</td>
				</tr>
				<tr>
					<td>��ְ������<span class="required">*</span>��</td>
					<td>
						<input id="empManagerName" readonly="readonly">
						<input type="hidden" name="empquit.eqPermission.id" id="empManagerId" />
						<ty:auth auths="101,2">
							<img src="../resource/images/search_icon.gif" style="CURSOR: pointer"
								onclick="showChooseEmpDiv(1,1);" alt='���ͼ��ѡ����' />
						</ty:auth>
					</td>
				</tr>
				<tr>
					<td>��ְԭ��<span class="required">*</span>��</td>
					<td><select name="empquit.eqReason" id="empquit.eqReason_id"></select></td>
				</tr>
				<tr>
				    <td>��ְ����<span class="required">*</span>:</td>
					<td>					
				    <s:hidden id="chang_dept_id"/>
					 <s:textfield id="chang_dept_name" size="16" readonly="true"/>
					</td>
				</tr>
				<tr>
					<td>��ְְλ<span class="required">*</span>��</td>
					<td>
						<div><table cellpadding="0" cellspacing="0" class="select"><tr><td bgcolor=#FFFFFF>
		                <s:hidden id="positionId" name="empquit.position.id"/>
		                <input id="empPBName" name="empquit.position.positionPbId.pbName" value='<s:property value="empquit.position.positionPbId.pbName"/>'  class='selecttext' readonly="true" size="12" />
		                <button type="button" class="selectbutton" style="CURSOR: pointer" id="showdiv" onclick="showPostionTree('empPBName', 'positionId', 'chang_dept_name', 'chang_dept_id');"/>&nbsp;
		                </button></td></tr></table></div>
					</td>
				</tr>
				<tr>
					<td>��ע��</td>
					<td><textarea name="empquit.erComments" rows="8" cols="40"></textarea></td>
				</tr>
				<tr height="35">
					<td colspan="2" align="center">
						<!-- <input type="hidden" value='<s:property value="#session.curr_oper_no"/>' name="employeeId"> -->
						<input id="eqAddbtn" type="button" onclick="doRehab();" ; value="��ְ" />
						<input type="button" value="�ر�" onclick="hrm.common.closeDialog('dlgReturnDiv');" />
					</td>
				</tr>
			</table>
		</tr>
	</table>
</div>
<script type="text/javascript">
//��ʼ����ְ������
eqRTypeSubMap={};
<s:iterator value="eqRReasonMap" id="topEntrySetR">
	eqRTypeSubMap['<s:property value="#topEntrySetR.key"/>']={};
	<s:iterator value="#topEntrySetR.value" status="index" id="secEntrySetR">
		<s:if test="#secEntrySetR.key.length()==0">
		eqRTypeSubMap['<s:property value="#topEntrySetR.key"/>']['']='<s:property value="#secEntrySetR.value"/>';
		</s:if>
		<s:else>
			eqRTypeSubMap['<s:property value="#topEntrySetR.key"/>']['<s:property value="#secEntrySetR.value"/>']='<s:property value="#secEntrySetR.value"/>';
		</s:else>
	</s:iterator>
</s:iterator>
model.simple.changeSubSelect('eqRType','empquit.eqReason_id','eqRTypeSubMap','<s:property value="quit.eqReason"/>');

//��ʼ����ְ������
eqTypeSubMap={};
<s:iterator value="eqReasonMap" id="topEntrySet">
	eqTypeSubMap['<s:property value="#topEntrySet.key"/>']={};
	<s:iterator value="#topEntrySet.value" status="index" id="secEntrySet">
		<s:if test="#secEntrySet.key.length()==0">
			eqTypeSubMap['<s:property value="#topEntrySet.key"/>']['']='<s:property value="#secEntrySet.value"/>';
		</s:if>
		<s:else>
			eqTypeSubMap['<s:property value="#topEntrySet.key"/>']['<s:property value="#secEntrySet.value"/>']='<s:property value="#secEntrySet.value"/>';
		</s:else>
	</s:iterator>
</s:iterator>
model.simple.changeSubSelect('eqType','eqReason','eqTypeSubMap','<s:property value="quit.eqReason"/>');
//ɾ����ְԱ��
function deleteEmp(id){
	if(confirm("ȷ��Ҫɾ����")){
		document.getElementById('delEmpId').value=id;
		document.getElementById('empQuitManagement').action="deleteEmp.action";
		document.getElementById('searchOrExport').value = "";
		document.getElementById('empQuitManagement').submit();
	}
}
//����ɾ��/��ְ
function batch(operate){
	var checkBoxs=document.getElementsByName('quitemp.ids');
	var flag=0;
	error="";
	var ids = "";
	for(var i=0;i<checkBoxs.length;i++){
		if(checkBoxs[i].checked==true){
			flag=1;
			ids+= checkBoxs[i].value;
			if(i != checkBoxs.length - 1){
    	   		ids+= ",";
			}
		}
	}
	if(flag==0){
		error="������ѡ��һ��Ա����";
	}
	if(error.length>1){
		alert(error);
		return ;
	}
	if('delete' == operate){
		if (confirm("��ȷ��Ҫɾ����")){
			document.getElementById('operate').value=operate;
			document.getElementById('ids').value = ids;
			document.getElementById('empQuitManagement').action="batchDeleteEmp.action";
			document.getElementById('searchOrExport').value = "";
			document.getElementById('empQuitManagement').submit();
		}
	}
	else if("rehab" == operate){
		document.getElementById('joinDate').value = (new Date()).toHRMDateString();
		hrm.common.openDialog('dlgReturnDiv','empQuitManagement');
	}
}
//��ְ
function doRehab(){
	checkBoxs=document.getElementsByName('quitemp.ids');
	var flag=0;
	error="";
	var ids = "";
	for(var i=0;i<checkBoxs.length;i++){
		if(checkBoxs[i].checked&&checkBoxs[i].checked==true){
			flag=1;
			ids+= checkBoxs[i].value;
			if(i != checkBoxs.length - 1){
				ids+= ",";
			}
		}
	}
	if(flag==0){
		error="������ѡ��һ��Ա����";
	}
	if(error.length>1){
		alert(error);
		return ;
	}
	if(!hrm.common.isDate(document.getElementById('joinDate').value)) {
		alert('��ְ���ڲ���Ϊ�ջ����ڸ�ʽ����ȷ��');
		return;
	}
	if(hrm.common.isNull(document.getElementById('empManagerName').value)){
		alert("��ְ�����˲���Ϊ�գ�");
		return;
	}
	if(hrm.common.isNull(document.getElementById('empquit.eqReason_id').value)){
		alert("��ְԭ����Ϊ�գ�");
		return;
	}
	if(hrm.common.isNull(document.getElementById('chang_dept_name').value)){
		alert("��ְ���Ų���Ϊ�գ�");
		return;
	}
	if(hrm.common.isNull(document.getElementById('empPBName').value)){
		alert("��ְְλ����Ϊ�գ�");
		return;
	}
	ids = ids.trim();
	if(',' == ids.charAt(ids.length-1)){
		ids = ids.substring(0,ids.length-1);
	}
	document.getElementById('ids').value = ids;
	document.getElementById('empQuitManagement').action="batchRehabEmp.action";
	document.getElementById('searchOrExport').value = "";
	document.getElementById('empQuitManagement').submit();
}
hrm.common.check_order();
hrm.common.initDialog('dlgReturnDiv');
</script>
<%@ include file="search_emp_div.jsp"%>
<jsp:include flush="true" page="../io/div_omm_select.jsp"></jsp:include>
<jsp:include flush="true" page="position_choose_div.jsp"></jsp:include>
</body>