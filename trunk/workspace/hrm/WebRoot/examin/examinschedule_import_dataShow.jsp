<%@page pageEncoding="GBK" contentType="text/html; charset=GBK" %>
<%@taglib prefix="s" uri="webwork"%>
<%@taglib prefix="ty" uri="/tengsource"%>
<!--
 =========================================================
' File:examainschedule_import_dataShow.jsp
' Auth:jet miao
' Version:1.1.0 standard version
' Date: 2008-11-28
' Script Written by tengsource.com
'=========================================================
' Copyright   2007 tengsource.com. All rights reserved.
' Web: http://www.tengsource.com
' Email: admin@tengsource.com
'=======================================================
 -->
<head>
<title>ˢ������</title>
<jsp:include flush="true" page="/sitemesh/jsPackage.jsp"></jsp:include>
<script type='text/javascript' src='../dwr/interface/OrgMapAction.js'></script>
<script type='text/javascript' src='../dwr/interface/DwrSyncAttdMachine.js'></script>
<script type='text/javascript' src='../dwr/interface/DwrForAttend.js'></script>
<s:head/> 
</head>
<body onload="hrm.common.check_order();"> 
<span class="errorMessage" id="message"></span>
<s:component template="bodyhead">
	<s:param name="pagetitle"  value="'ˢ������'" />
	<s:param name="helpUrl" value="'26'" />
</s:component>
<form id="originalDataShow" name="originalDataShow" method="POST" namespace="/examin" action="originalDataImportShow.action">
    <!-- �����ֶ� -->
    <s:hidden name="operate"></s:hidden>
    <s:hidden name="aodId"></s:hidden>
    <s:hidden name="iodefId"></s:hidden>
    <s:hidden id="order" name="page.order"/>
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="formtable">
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
							    <td align="right">Ա��:</td>
							    <td><s:textfield id="emp" name="emp" size="16" maxlength="64"/></td>
								<td align="right">��ʼ����:</td>
								<td> 
								<s:textfield id="attdDateFrom"   value="%{attdDateFrom}" name="attdDateFrom" required="true" size="12"  />
								<img onclick="WdatePicker({el:'attdDateFrom'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">											
								</td>
								<td align="right">���ڻ���:</td>
								<td>
								<s:select name="machineNo" list="machineList" listKey="macNo" listValue="macNo" multiple="false" emptyOption="true" value="machineNo" size="1" />
								</td>
							</tr>
							<tr>
							    <td align="right">��֯��Ԫ:</td>
							    <td>
							    <s:orgselector id="orgselector1" name="deptName" hiddenFieldName="dept"/>
							    </td>
								<td align="right">��������:</td>
								<td>
								<s:textfield id="attdDateTo" value="%{attdDateTo}" name="attdDateTo" required="true" size="12" />
								<img onclick="WdatePicker({el:'attdDateTo'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
								</td>									
								<td align="right">״̬:</td>
								<td><s:select id="status" name="status" value="status" list="#{2:'ȫ��',0:'����',1:'����'}"  emptyOption="false"/></td>
							</tr>
						</table>
					</td>
					<td>				
						<input type="button" class="button" name="searchData" value="��ѯ" onclick="searchOriginalData();"/>
					    <input title="[Alt+C]" accesskey="C" name="clear_button" class="button" type="button" onClick="window.location='originalDataImportShow.action';" value="����">
					</td>
				</tr>
			</table>
<table width="100%">
  <tr>
	<td><br/></td>
  </tr>
  <tr>
	<td align="left">
	    <ty:auth auths="401 or 411,3 or 411,2">
			<input class="button" type="button" onclick="initDivImmUpload('IExaminCardData', '');" value="���ݵ���"/>
			<input type="button" class="button" value="����" onclick="empSelecotr('addCard');"/>
		</ty:auth>
		<ty:auth auths="401,3 or 401,2">
		    <input type="button" class="button" name="deleteData" value="��������" onclick="deleteOriginalData();"/>
		</ty:auth>
	</td>
	<td align="right">���β�ѯ���õ�<s:property value="page.totalRows" />��ˢ����¼&nbsp;</td>
  </tr>
</table>

    <table width="100%" border="0" cellspacing="0" cellpadding="0" id="dataTable" class="gridtableList">
        <tr>
	     	<th nowrap="nowrap"> 
	     		<a href="#" onclick="hrm.common.order_submit('emp.empDistinctNo','originalDataShow');">
	     		Ա�����<img src='../resource/images/arrow_.gif' width='8' height='10' id='emp.empDistinctNo_img'></a>
	     	</th>
	     	<th nowrap="nowrap">
	     		<a href="#" onclick="hrm.common.order_submit('emp.empName','originalDataShow');">
	     		����<img src='../resource/images/arrow_.gif' width='8' height='10' id='emp.empName_img'></a>
	     	</th>
	     	<th nowrap="nowrap">
	     		<a href="#" onclick="hrm.common.order_submit('emp.empDeptNo','originalDataShow');">
	     		����<img src='../resource/images/arrow_.gif' width='8' height='10' id='emp.empDeptNo_img'></a>
	     	</th>
	     	<th nowrap="nowrap">
	     		<a href="#" onclick="hrm.common.order_submit('aodAttdDate','originalDataShow');">
	     		��������<img src='../resource/images/arrow_.gif' width='8' height='10' id='aodAttdDate_img'></a>
	     	</th>
	     	<th nowrap="nowrap">
	     		<a href="#" onclick="hrm.common.order_submit('aodCardTime','originalDataShow');">
	     		ˢ��ʱ��<img src='../resource/images/arrow_.gif' width='8' height='10' id='aodCardTime_img'></a>
	     	</th>
	     	<th nowrap="nowrap">
	     		<a href="#" onclick="hrm.common.order_submit('aodTtdMachineNo','originalDataShow');">
	     		���ڻ���<img src='../resource/images/arrow_.gif' width='8' height='10' id='aodTtdMachineNo_img'></a>
	     	</th>
	     	<th nowrap="nowrap">
	     		<a href="#" onclick="hrm.common.order_submit('aodStatus','originalDataShow');">
	     		״̬<img src='../resource/images/arrow_.gif' width='8' height='10' id='aodStatus_img'></a>
	     	</th>
	     	<th nowrap="nowrap">��ע</th>
	     	<th nowrap="nowrap">����</th>
        </tr>
        <s:if test="!originalDataList.isEmpty()"> <!--�ж��Ƿ�Ϊ��-->
			<s:iterator value="originalDataList" status="st">
		        <tr>
					<td id="aodEmpDistinctNo<s:property value="#st.count"/>" align="center">
						&nbsp;<s:property value="attdEmp.empDistinctNo" />
					</td>
					<td id="aodEmpName<s:property value="#st.count"/>" align="center">
						&nbsp;<s:property value="attdEmp.empName" />
					</td>
					<td id="aodEmpDept<s:property value="#st.count"/>" align="center">
						&nbsp;<s:property value="attdEmp.empDeptNo.departmentName" />
					</td>
							
					<td id="aodAttdDate<s:property value="#st.count"/>" align="center">
						&nbsp;<s:property value="aodAttdDate" />
				    </td>
					<td id="aodCardTime<s:property value="#st.count"/>" align="center">
						&nbsp;<s:date name="aodCardTime" format="yyyy-MM-dd HH:mm:ss" />
					</td>
					<td id="aodTtdMachineNo<s:property value="#st.count"/>" align="center">
						&nbsp;<s:property value="aodTtdMachineNo" />
					</td>
					<td id="aodStatus<s:property value="#st.count"/>" align="center">
						&nbsp;
						<s:if test="aodStatus==0">
						    ����
						</s:if><s:else>
						    ����
						</s:else>
					</td> 		 
					<td id="aodMemo<s:property value="#st.count"/>" align="center">
						&nbsp;<s:property value="aodMemo" />
					</td>
					<td align="center" id="dataOperatin<s:property value="#st.count"/>" align="center">
					  <s:if test="aodId!=null&&aodId!=''">
					    <a href="#" onclick="deleteOneData('<s:property value="aodId"/>')"><img src="../resource/images/deletesalaryconf.gif" alt='ɾ��'  border='0'/></a>
					  </s:if>
					</td>
		        </tr>
            </s:iterator>
        </s:if>
		<s:else>
		    <tr>
		        <td align="center" colspan="17">�޷��������Ŀ��ڻ�ˢ�����ݣ�</td>
		    </tr>
		</s:else>
    </table>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="gridtableList">
		<tr class="listViewPaginationTdS1">
		  <td colspan="10" align="center">
		    <s:hidden id="page.currentPage" name="page.currentPage" />
		    <s:component template="pager">
		        <s:param name="pageNo" value="page.currentPage" /><s:param name="totalPages" value="page.totalPages" /><s:param name="totalRows" value="page.totalRows" />
        	  	    <s:param name="start" value="page.start" /><s:param name="end" value="page.end" /><s:param name="formId" value="'originalDataShow'" />
        	  	</s:component>
	  	  </td>
		</tr>
	</table>
</form> 
<div id="tmpletId" style="DISPLAY: none">
<img src="../resource/images/basic_search.gif" onload="hrm.common.check_order();"/> <!--��Ϊ����common�з���-->
</div>

<div id="dlgEmpListDiv" title="�û�ѡ���б�">
<jsp:include flush="true" page="../examin/attdOperate.jsp"></jsp:include>
</div>

<script type="text/javascript" language="javascript">
//��ѯ����(�����ѯ��ť)
function searchOriginalData(){
    if(document.originalDataShow.attdDateFrom.value == ''){
        alert("��ѡ��ʼ���ڣ�");
        return;
    }
    if(document.originalDataShow.attdDateTo.value == ''){
        alert("��ѡ��������ڣ�");
        return;
    }
    document.forms[0].submit();//�ύ��
}

//�����������(�������ť)
function deleteOriginalData(){
    if(document.originalDataShow.attdDateFrom.value == ''){
        alert("��ѡ��ʼ���ڣ�");
        return;
    }
    if(document.originalDataShow.attdDateTo.value == ''){
        alert("��ѡ��������ڣ�");
        return;
    }
    var start = document.originalDataShow.attdDateFrom.value;
    var end = document.originalDataShow.attdDateTo.value;
    if(confirm("ȷ��Ҫɾ��" + start + "��" + end + "�����ڵ�����ô��")){ //confirmȷ���Ƿ�ɾ��
    	document.all.operate.value = "deleteSome";
        document.forms[0].submit();
    }
}

//ɾ��һ������
function deleteOneData(aodId){
    if(confirm("ȷ��Ҫɾ����������ô��")){
        document.all.aodId.value = aodId;
        document.all.operate.value = "deleteOne";
        document.forms[0].submit();
    }
}

//�û������¼�
function enterDown(event){
    event = event ? event : (window.event ? window.event : null);
    if(event!=null && event.keyCode==13){
        searchEmp(document.getElementById('emp_sear_value').value);  
        return;                              
    }
}

$('#attdDate').val(hrm.common.getCurrentDate()); //��attdDate���ϵ�������
</script>
<jsp:include flush="true" page="../io/div_upload.jsp"></jsp:include>
</body>	
