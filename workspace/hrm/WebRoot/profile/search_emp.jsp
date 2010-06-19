<%@page pageEncoding="GBK" contentType="text/html; charset=GBK" %>
<%@taglib prefix="s" uri="webwork"%>
<%@taglib prefix="ty" uri="/tengsource"%>
<!--
 =========================================================
' File:search_emp.jsp
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
<title>Ա������</title>
<script type="text/JavaScript" src="../resource/js/hrm/profile.js"></script>
<script type='text/javascript' src='../dwr/interface/OrgMapAction.js'></script>
<link rel="stylesheet" type="text/css" href="../resource/css/edit_select.css" />
</head>
<body onload="hrm.common.check_showHide();hrm.common.check_order();">
<div id="selectcontent" class="selectdiv" style="visibility:hidden;pixelHeight:20px;z-index:9;">
	<iframe id="selframe" frameborder="0" height="100%"></iframe>
	<div id="selecthtml" class="selectcontent">selectdate</div>
</div>
<!-- �������select (js���˲�Ҫ�ƶ�λ��)-->
<script type="text/javascript" src="../resource/js/edit_select.js"></script>
<s:component template="bodyhead">
	<s:param name="pagetitle" value="'Ա������'" />
	<s:param name="helpUrl" value="'32'" />
</s:component>
<s:form id="searchEmp" name="searchEmp" action="searchEmp" namespace="/profile" method="POST" >
<table width="100%" border="0" cellspacing="2" cellpadding="0" style="background-color: #ECF6FB;border: 1px #6BB5DA solid">
	<tbody>
	<tr>
		<td>
		<s:hidden id="order" name="page.order"/>
		<input id="operate" type="hidden" name="page.operate"/>
		<div id="basic" style="DISPLAY: block;clear : both">
			<table width="100%" border="0" cellspacing="2" cellpadding="0">
				<tr>
					<s:textfield id="empNo" label="Ա��" name="emp.empNoName" size="16" maxlength="64"/>
					<td align="right">��֯��Ԫ:</td>
					<td>
			             <s:orgselector id="orgselector1" name="emp.empDeptNo.departmentName" hiddenFieldName="emp.empDeptNo.id"/>
					</td>
					<s:select label="�ù���ʽ" name="emp.empType.id" list="empTypes" listKey="id"
					 listValue="emptypeName" multiple="false" emptyOption="true" value="emp.empType.id" size="1" /> 
					<s:select label="��������" name="emp.empLocationNo.id" list="locations" listKey="id" 
					 listValue="locationName" multiple="false" emptyOption="true" value="emp.empLocationNo.id" size="1" />
				</tr>
			</table>
		</div>
		<div id="advanced" style="DISPLAY: none;clear : both">
			<table width="100%" border="0" cellspacing="2" cellpadding="0" >
				<tr>
				    <s:textfield label="Ա������" id="empName" name="emp.empName" size="16" maxlength="64"/>
					<s:select label="�Ա�" name="emp.empGender"  value="emp.empGender" list="#{'':'��ѡ��',1:'��', 0:'Ů'}"/>
					<td colspan="2" align="center">
						<table>
							<tr>
								<td>
									��������:<s:textfield id="fromBirthDate" name="emp.empBirthDate" size="10"/>
									<img onclick="WdatePicker({el:'fromBirthDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="18" style="cursor:pointer" align="absmiddle">
									��:
									<s:textfield id="tobirthDate" name="emp.birthDate" size="10"/>
									<img onclick="WdatePicker({el:'tobirthDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="18" style="cursor:pointer" align="absmiddle">
								</td>
							</tr>
						</table>
					</td>	
				</tr>
				<tr>
					<s:textfield id="empNo" label="Ա�����" name="emp.id" size="16" maxlength="64"/>
					<s:select label="���" name="emp.empMarital"  value="emp.empMarital" list="#{'':'��ѡ��',0:'δ��', 1:'�ѻ�'}"/>
					<td colspan="2" align="center">
						<table>
							<tr>
								<td>
									��������:<s:textfield id="fromWorkDate" name="emp.empWorkDate" size="10"/>
									<img onclick="WdatePicker({el:'fromWorkDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="18" style="cursor:pointer" align="absmiddle">
									��:
									<s:textfield id="toWorkDate" name="emp.workDate" size="10"/>
									<img onclick="WdatePicker({el:'toWorkDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="18" style="cursor:pointer" align="absmiddle">
								</td>
							</tr>
						</table>
					</td>		
				</tr>
				<tr>
					<s:textfield label="�����绰" name="emp.empWorkPhone" size="16" maxlength="32" onkeypress="javascript:if(event.keyCode==13)document.getElementById('searchEmp').submit();hrm.common.checkPhone(this);"/>
					<s:select label="Ѫ��" name="emp.empBlood" value="emp.empBlood" list="#{'':'��ѡ��', 'O ':'O ', 'A ':'A ','B ':'B ', 'AB':'AB'}"/>
					<td colspan="2" align="center">
						<table>
							<tr>
								<td>
									��ְ����:<s:textfield id="fromDate" name="emp.empJoinDate" size="10"/>
									<img onclick="WdatePicker({el:'fromDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="18" style="cursor:pointer" align="absmiddle">
									��:
									<s:textfield id="toDate" name="emp.joinDate" size="10"/>
									<img onclick="WdatePicker({el:'toDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="18" style="cursor:pointer" align="absmiddle">
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
				 	<s:textfield label="�������ڵ�" name="emp.empResidenceLoc" maxlength="128" size="16"></s:textfield>
					<td align="right">����:</td>
		            <td>
		                <s:component template="editselect"  name="emp.empCityNo">
		                    <s:param name="list" value="@com.hr.base.ComonBeans@getEmpCityNo()"/>
		                    <s:param name="size" value="8"/>
		                    <s:param name="height" value="220"/>
		                </s:component>
		            </td>
					<td colspan="2" align="center">
						<table>
							<tr>
								<td>
									ת������:<s:textfield id="fromConfirmDate" name="emp.empConfirmDate" size="10"/>
									<img onclick="WdatePicker({el:'fromConfirmDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="18" style="cursor:pointer" align="absmiddle">
									��:
									<s:textfield id="toConfirmDate" name="emp.confirmDate" size="10"/>
									<img onclick="WdatePicker({el:'toConfirmDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="18" style="cursor:pointer" align="absmiddle">
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<s:textfield label="�������ڵ�" name="emp.empProfileLoc" maxlength="128" size="16"></s:textfield>
		          	<td align="right">����:</td>
		            <td>
		                <s:component template="editselect"  name="emp.empNation">
		                     <s:param name="list" value="@com.hr.base.ComonBeans@getEmpNation()"/>
		                     <s:param name="size" value="8"/>
		                     <s:param name="height" value="200"/>
		                </s:component>
		            </td> 
		            <td align="right">��֯��Ԫ:</td>
					<td colspan="2" nowrap="nowrap">
					   <s:orgselector id="selector2" name="emp.empDeptNo.departmentName" hiddenFieldName="emp.empDeptNo.id"/>
					</td>
				</tr>
				<tr>
					<s:textfield label="�ֻ�" name="emp.empMobile" size="16" maxlength="32"/>
					<s:textfield label="������ò" name="emp.empPolitics" size="16" maxlength="30"/>
					<s:select label="�ù���ʽ" name="emp.empType.id" list="empTypes" listKey="id"
					  listValue="emptypeName" multiple="false" emptyOption="true" value="emp.empType.id" size="1" />
				</tr>
				<tr>
					<s:textfield label="��ͥ�绰" name="emp.empHomePhone" size="16" maxlength="32" onkeypress="javascript:if(event.keyCode==13)document.getElementById('searchEmp').submit();hrm.common.checkPhone(this);"/>
					<s:textfield label="��ҵԺУ" name="emp.empSchool" size="14" maxlength="32"/>
					<s:select label="��������" name="emp.empLocationNo.id" list="locations" listKey="id" 
					 listValue="locationName" multiple="false" emptyOption="true" value="emp.empLocationNo.id" size="1" />
				</tr>
				<tr>
					<s:textfield label="�����ַ" name="emp.empEmail" size="16" maxlength="64"/>
					<td align="right">רҵ:</td>
					<td>
					 	<s:component template="editselect" name="emp.empSpeciality">
							<s:param name="list" value="@com.hr.base.ComonBeans@getEmpSpeciality()"/>
							<s:param name="size" value="8"/>
							<s:param name="height" value="220"/>
					 	</s:component>
					</td>
					
					<s:select label="�籣����" id="empBenefitTypeAdv"
						name="emp.empBenefitType.id" list="ebfTypeList" listKey="id" listValue="benefitTypeName" 
						multiple="false" headerKey="" headerValue="��ѡ��" />
				</tr>
				<tr>
					<s:textfield label="֤������" name="emp.empIdentificationNo" size="16" maxlength="30"/>
					<s:select label="ѧ��"  name="emp.empDiploma" value="emp.empDiploma" emptyOption="true" list="@com.hr.base.ComonBeans@getEmpDiploma()"/>
					<s:if test="@com.hr.base.ComonBeans@getShiftEnable()==1">
		          	<s:select label="���ڷ�ʽ" name="emp.empShiftType" list="#{'':'��ѡ��',0:'��ˢ��',2:'Ĭ�ϰ��ˢ��',3:'�����ˢ��'}" emptyOption="false"/>
		          	</s:if>
				</tr>
				<tr>
					<s:textfield label="��ǰסַ" name="emp.empCurrAddr" size="16" maxlength="64" />
					<s:textfield label="��ʱͨѶ" name="emp.empMsn"size="14" maxlength="64"/>
					<s:if test="@com.hr.base.ComonBeans@getShiftEnable()==1">
					<s:textfield label="���ڿ���" name="emp.empShiftNo" size="14"></s:textfield>
					</s:if>
				</tr>
		        <tr>
		        	<s:textfield label="��ͥ��ַ" name="emp.empHomeAddr" size="16" maxlength="64" />
		            <s:textfield label="������ϵ��" name="emp.empUrgentContact" size="14" maxlength="64" />
		       		 <td align="right" >��������:</td>
		            <td nowrap="nowrap">
		                <s:select id="additional" list="additionalList" name="additional.eadcSeq" listKey="eadcSeq" listValue="eadcFieldName" emptyOption="true" onchange="changeAdditional();"></s:select>
		            </td>
		        </tr>
		        <tr>
		        	<s:textfield label="��ע" name="emp.empComments" size="16" maxlength="128" />
		        	<s:textfield label="������ϵ��ʽ" name="emp.empUrgentConMethod" size="14" maxlength="128" />
	       		 	<td>&nbsp;</td>
		       		 <td colspan="2" align="left">
		                <div id="additionalDiv"></div>
		            </td>
				</tr>
			</table>
		</div>
		</td>
		<td align="center" nowrap="nowrap">
			<input title="[Alt+F]" accesskey="F" id="submit_button" class="button" type="submit" onclick="javascript:if(!doSubmit()){return false;}hrm.common.search_check('basic','advanced');" name="button2" value="��ѯ"> 
			<input title="[Alt+C]" accesskey="C" class="button" type="button" name="button22" value="����" onclick="window.location='searchEmp.action';"><br>
			<div id="t1" align="center" style="DISPLAY: block;clear : both">
				<img src="../resource/images/basic_search.gif" width="8" height="8">
				<a href="#" class="utilsLink" onClick="hrm.common.searchTypeChange();">�߼���ѯ</a>
			</div>				
			<div id="t2" align="center" style="DISPLAY: none;clear : both">
				<img src="../resource/images/advanced_search.gif" width="8" height="8">
				<a href="#" class="utilsLink" onClick="hrm.common.searchTypeChange();">������ѯ</a>
			</div>				
		</td>
	</tr>
	</tbody>
</table>
<br/>
<!-- ���� -->
<table width="100%">
	<tr>
		<td align="left">
			<input type="hidden" name="searchOrExport" id="searchOrExport" />
			<s:hidden id="output-ommId" name="outmatchModelId"/>
			<s:hidden id="output-ioName" name="outputIoName"/>
			<ty:auth auths="101">
				<input class="button" type="button" onclick="batchQuit();" value="������ְ"/>
				<input class="button" type="button" onclick="doclick();" value="�����䶯"/>
			</ty:auth>
			<ty:auth auths="101,3 or 101,2">
				<input class="button" type="button" onclick="initDivImmUpload('IEmployeeBasic');" value="���ݵ���"/>
				<input class="button" type="button" id="btnOutput" value="���ݵ���" onClick="hrm.common.export_check('basic','advanced','export');"/>
			</ty:auth>
		</td>
		<td align="right">���β�ѯ���õ�<s:property value="page.totalRows" />��Ա����¼&nbsp;</td>
	  </tr>
</table>
<!-- ��ѯ��� -->
<table id="profiletable" cellpadding="0" cellspacing="0" width="100%" border="0" class="gridtableList">	
	<tr>
	   	<ty:auth auths="101">
		<th align="center" width="3%">
			<input id="id_check_all" name='emp.ids' class="checkbox" type="checkbox" onclick="hrm.common.checkAllByName('emp.ids','id_check_all');" value="0">
		</th>
		</ty:auth>
	   	<th nowrap="nowrap">
	   		<a href="#" onclick="hrm.common.order_submit('empDistinctNo','searchEmp');">Ա�����</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='empDistinctNo_img'>
	   	</th>
	   	<th nowrap="nowrap">
	   		<a href="#" onclick="hrm.common.order_submit('empName','searchEmp');">����</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='empName_img'>
	   	</th>
	   	<th nowrap="nowrap">
	   		<a href="#" onclick="hrm.common.order_submit('empOrgDept.departmentSortId','searchEmp');">����</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='empOrgDept.departmentSortId_img'>
	   	</th>
	   	
	   	<th nowrap="nowrap">
	   		<a href="#" onclick="hrm.common.order_submit('empPbNo.pbName','searchEmp');">ְλ</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='empPbNo.pbName_img'>
	   	</th>
	   	<th nowrap="nowrap">
	   		<a href="#" onclick="hrm.common.order_submit('empLocationNo.locationName','searchEmp');">��������</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='empLocationNo.locationName_img'>
	   	</th>
	   	<th nowrap="nowrap">
	   		<a href="#" onclick="hrm.common.order_submit('empType.emptypeName','searchEmp');">�ù���ʽ</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='empType.emptypeName_img'>
	   	</th>
	   	<th nowrap="nowrap">
	   		<a href="#" onclick="hrm.common.order_submit('empJoinDate','searchEmp');">˾��</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='empJoinDate_img'>
	   	</th>
	   	<th nowrap="nowrap">
	   		<a href="#" onclick="hrm.common.order_submit('empLastChangeTime','searchEmp');">����޸�</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='empLastChangeTime_img'>
	   	</th>
	   	<th nowrap="nowrap">
	   		<a href="#" onclick="hrm.common.order_submit('empLastChangeTime','searchEmp');">����</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='empLastChangeTime_img'>
	   	</th>
	</tr>
    <s:if test="!empList.isEmpty()">
    	<s:iterator value="empList" status="index">
    		<tr>
    			<ty:auth auths="101">
		    	<td align="center" width="3%">
			 	<input type="checkbox" name='emp.ids' class="checkbox" value="<s:property value='id'/>" />
		    	</td>
		    	</ty:auth>
				<td><span TITLE="�����绰: <s:property value='empWorkPhone'/>
��������: <s:property value='empBirthDate'/>
��ְ����: <s:property value='empJoinDate'/>
֤������: <s:property value='empIdentificationNo'/>
����: <s:property value='empEmail'/>" />
					<a class="listViewTdLinkS1" href="myInfo.action?empNo=<s:property value='id'/>&empName=<s:property value='empName'/>"><s:property value="empDistinctNo" /></a>
				</td>
				<td><s:property value="empName" /></td>  
				<td><s:property value="empDeptNo.departmentName" /></td>  
				<td><s:property value="empPbNo.pbName" /></td>    
				<td><s:property value="empLocationNo.locationName" /></td>  
				<td><s:property value="empType.emptypeName" /></td>  
				<td align="center">
					<s:property value="getYearOrMonth(joinYear)"/>
				</td>
				<td align="center"><s:date name="empLastChangeTime" format="yyyy-MM-dd HH:mm" /></td>  
				<td align="center">
				 	<a href="#" onclick="hrm.profile.showInfoCardInfo('<s:property value="id"/>');"><img
					 src="../resource/images/Search.gif" alt="Ա�����Ͽ�" border="0"/></a>
					 <ty:auth auths="101">
					 	<a href="#" onclick="hrm.profile.deleteEmp('<s:property value="id"/>','searchEmp')"><img
						 src="../resource/images/delete.gif" alt="ɾ��" border="0"/></a>
					 </ty:auth>
				</td>  
    		</tr>
    	</s:iterator>
    </s:if>
	<s:else>
		<tr>
			<td align="center" colspan="13">������Ա����Ϣ!</td>
		</tr>
	</s:else>
</table>
<!-- ��ҳ -->
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="gridtableList">
	<tr class="listViewPaginationTdS1">
	  <td colspan="10" align="center">
	    <s:hidden id="page.currentPage" name="page.currentPage" />
	    <s:component template="pager">
	        <s:param name="pageNo" value="page.currentPage" /><s:param name="totalPages" value="page.totalPages" /><s:param name="totalRows" value="page.totalRows" />
      	  	    <s:param name="start" value="page.start" /><s:param name="end" value="page.end" /><s:param name="formId" value="'searchEmp'" />
      	  	</s:component>
	  </td>
	</tr>
</table>
<!-- �������޸�  -->		
<p>&nbsp;</p> 
<ty:auth auths="101">
<s:if test="!empList.isEmpty()">
	<table cellspacing="0" cellpadding="0" border="0" valign="top" width="100%">
		<tr>
			<td nowrap="nowrap"><!-- �����޸� -->
				<h3>
				<img src="../resource/images/h3Arrow.gif" width="11" height="11" border="0">
				 �����޸�
				</h3>
			</td>
		</tr>
		<tr>
			<td valign="top" width="100%">
				<table border="0" width="100%" id="table3" cellspacing="0" cellpadding="0" class="formtable">
					<tr>
						
						<s:textfield label="�������ڵ�" id="chang_epLoc" name="batchemp.empProfileLoc" size="16" maxlength="128"/>
						<s:select label="�ù���ʽ" id="chang_type" name="batchemp.typeNo" list="empTypes" listKey="id" listValue="emptypeName" multiple="false" emptyOption="true" value="emp.empType.id" size="1" />
						
					
					    <s:select label="��������" id="chang_loc" name="batchemp.locationNo" list="locations" listKey="id" listValue="locationName" multiple="false" emptyOption="true" value="emp.empLocationNo.id" size="1" />
						<s:if test="@com.hr.base.ComonBeans@getShiftEnable()==1">
		 				<s:select label="���ڷ�ʽ" id="chang_shift_type" name="batchemp.empShiftType" emptyOption="false" list="#{0:'��ѡ��',1:'��ˢ��',2:'Ĭ�ϰ��ˢ��',3:'�����ˢ��'}"/>
						</s:if>
						<td>
							<!-- �޸�  -->  
							<input title="[Alt+E]" accesskey="E" class="button" type="button" onClick="hrm.profile.del_update('update');" name="button23" value="�����޸�">
						</td>
						</tr>
				</table>
			</td>
		</tr>
	</table>
</s:if>
</ty:auth>
</s:form>
<!-- ������ְ -->
<div id="dlgQuitDiv" style="display:none" title="������ְ">
	<table cellpadding="0" border="0">
		<tr>
			<td align="left" nowrap="nowrap">��ְ����<span class="required">*</span>��</td>
			<td>
				<s:textfield id="joinDate" name="empquit.eqDate" required="true" size="10"/>
				<img onclick="WdatePicker({el:'joinDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="18" style="cursor:pointer" align="absmiddle">
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">��ְ����<span class="required">*</span>��</td>
			<td><s:select name="empquit.eqType" id="eqType" list="eqTypeMap" onchange="model.simple.changeSubSelect(this,'empquit.eqReason_id','eqTypeSubMap')"></s:select></td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">��ְ������<span class="required">*</span>��</td>

			<td>
				 <s:hidden id="permissionId" name="empquit.eqPermission.id" />
			 	 <s:textfield id="permission" size="16" readonly="true"></s:textfield>
				 <img src="../resource/images/search_icon.gif" onclick="showChooseEmpDiv(1,1)"  alt='���ͼ��ѡ��Ա��'/>
			</td>
	
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">��ְԭ��<span class="required">*</span>��</td>
			<td><select name="empquit.eqReason" id="empquit.eqReason_id"></select></td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">��ע��</td>
			<td><textarea name="empquit.erComments" rows="3" cols="30"></textarea></td>
		</tr>
		<tr height="35">
			<td colspan="2" align="center">
				<input type="hidden" name="qemp.ids" id="ids"/>
				<input id="eqAddbtn" type="button" onclick="batchQuitSubmit();" value="��ְ"/>
				<input type="button" value="�ر�" onclick="hrm.common.closeDialog('dlgQuitDiv');"/>
			</td>
		</tr>
	</table>
</div>
<!-- �����䶯 -->
<div id="dlgTransfer" style="display:none" class="prompt_div_inline" title="�䶯��¼">
	<table width="100%"  class="prompt_div_body" cellpadding="0" border="0">
		<tr id="targetTr">
			<td align="left" nowrap="nowrap">&nbsp;�䶯����<span class="required">*</span>��</td>
			<td><select name="transfer.eftTransferType" id="transferType" onchange="changeForm()">
				<option value="0">ƽ��</option>
				<option value="1">����</option>
				<option value="2">����</option>
				<option value="3">ת��</option>
				<option value="4">ת��</option>
			</select></td>
			
		</tr>

		<tr>
			<td align="left" nowrap="nowrap">&nbsp;�䶯ʱ��<span class="required">*</span>��</td>
			<td>
				<s:textfield name="transfer.eftTransferDate" id="transferTime" required="true" size="10"/>
				<img onclick="WdatePicker({el:'transferTime'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="18" style="cursor:pointer" align="absmiddle">
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" id="tdDept">&nbsp;�²���<span class="required">*</span>��</td>
			<td>
			    <s:hidden id="chang_deptId" name="transfer.eftNewDeptNo.id"/>
				<s:textfield id="chang_dept" size="16" readonly="true"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" id="tdPosition">&nbsp;��ְλ<span class="required">*</span>��</td>
			
			<td>
			<div>
            <table cellpadding='0' cellspacing='0' class='select'>
			<tr><td bgcolor='#FFFFFF'>
			<s:hidden id="positionId" name="newPosition.id"/>
			<input type="text" id="changePosition"  class='selecttext' readonly="true" size="12" />
			<button type="button" class="selectbutton" style="CURSOR: pointer" id="showdiv" onclick="showPostionTree('changePosition', 'positionId','chang_dept','chang_deptId');"/>&nbsp;
			</button>
			</td></tr>
			</table>
			</div>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">&nbsp;�䶯ԭ��</td>
			<td colspan="3"><input name="transfer.eftReason" size="28" maxlength="128"/></td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">&nbsp;��ע��</td>
			<td><textarea name="transfer.eftComments" rows="3" cols="30"></textarea></td>
		</tr>
		<tr height="35">
			<td colspan="2" align="center">
				<input type="hidden" name="ids" id="transids"/>
				<input type="hidden" name="transfer.eftNewDeptNo" id="eftNewDeptNo"/>
				<input type="hidden" name="transfer.eftNewJobTitle" id="eftNewJobTitle"/>
				<input id="trAddbtn" type="button" onclick="hrm.profile.del_update('dlgTransfer');"; value="�䶯"/>
				<input type="button" value="�ر�" onclick="hrm.common.closeDialog('dlgTransfer')"/>
			</td>
		</tr>
	</table>
	
</div>
<span id="dateSpan" style="display:none">
	<s:textfield id="dateValue" name="additional.value" size="10" maxlength="10"/>
	<img onclick="WdatePicker({el:'dateValue'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="18" style="cursor:pointer" align="absmiddle">
	��
	<s:textfield id="dateValue2" name="additional.value2" size="10" maxlength="10"/>
	<img onclick="WdatePicker({el:'dateValue2'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="18" style="cursor:pointer" align="absmiddle">
</span>
<div style="display:none"><table><tr>
    <td id="srcTd">
	<s:select  id="employmentForm"  name="transfer.eftNewEmpType.id" list="empTypes" listKey="id" listValue="emptypeName" multiple="false"  value="emp.empType.id" size="1" />
	</td></tr></table>
</div>
<script type="text/javascript">
function changeForm(){
  var value = $('#transferType').val();
  if(value==4){
    $('#tdDept').html("&nbsp;�²���:");
    $('#tdPosition').html("&nbsp;��ְλ:");
    
    var typeSelect=$('#employmentForm');
    
    var target=$('#targetTr');
    var tr=$('<tr></tr>');
    tr.attr("id","employmentFormTr");
    var td=$('<td></td>');
	td.html("&nbsp�ù���ʽ<span class=\"required\">*</span>:");
	tr.append(td);
	var empFormTd=$('<td></td>');
	empFormTd.append(typeSelect);
	tr.append(empFormTd);
	
	target.after(tr);
  }else{
    var srcSelect=$('#employmentForm');
    var scrTd=$('#srcTd');
    scrTd.append(srcSelect);
    $('#employmentFormTr').remove();
  }
}

//������ְ�ļ��
function batchQuit(){
	var checkBoxs=document.getElementsByName('emp.ids');
	var flag=0;
	var error="";
	var ids = "";
	for(var i=1;i<checkBoxs.length;i++){
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
		alert(error);
		return ;
	}
	else{
		alert("��ȷ�ϸ�Ա������ְ�����Ѱ�����ϣ�");
		ids = ids.trim();
		if(',' == ids.charAt(ids.length-1)){
			ids = ids.substring(0,ids.length-1);
		}
		document.getElementById('ids').value = ids;
		//��ʼ��������ְ����
		document.getElementById('joinDate').value=(new Date()).toHRMDateString();
		hrm.common.openDialog('dlgQuitDiv','searchEmp');
	}
}
//������ְ���ύ
function batchQuitSubmit(){
	if(!hrm.common.isDate(document.getElementById('joinDate').value)) {
		alert('��ְ���ڲ���Ϊ�ջ����ڸ�ʽ����ȷ��');
		return;
	}
	if(hrm.common.isNull(document.getElementById('empquit.eqReason_id').value)){
		alert("��ְԭ����Ϊ�գ�");
		return;
	}
	if(hrm.common.isNull(document.getElementById('permissionId').value)){
		alert("��ְ�����˲���Ϊ�գ�");
		return;
	}
	hrm.common.search_check('basic','advanced');
	$('#empName').val("");
	document.getElementById('searchEmp').action="batchQuit.action";
	document.getElementById('searchEmp').submit();
}
//�����䶯
var selectedNum;
var selectedIds;
function doclick(){
    selectedNum=0;
    selectedIds='';
    document.getElementById('changePosition').value='';
    $('#positionId').val('');
    $('#chang_deptId').val('');
	$('#chang_dept').val('');	
	var checkBoxs=document.getElementsByName('emp.ids');
	var flag=0;
	var error="";
	for(var i=1;i<checkBoxs.length;i++){
		if(checkBoxs[i].checked&&checkBoxs[i].checked==true){
		flag=1;
		selectedNum=selectedNum+1;
		selectedIds+= checkBoxs[i].value;
		selectedIds+=',';
		}
	}
	selectedIds = selectedIds.trim();
    if(selectedIds.charAt(selectedIds.length-1)==','){
       selectedIds = selectedIds.substring(0,selectedIds.length-1);
    }
	if(flag==0){
		error="������ѡ��һ��Ա����";
 		alert(error);
		return ;
 	 }
	else{
		//��ʼ�������䶯����
		document.getElementById('transferTime').value=(new Date()).toHRMDateString();
		$('#dlgTransfer').dialog('option', 'draggable', false);
		hrm.common.openDialog('dlgTransfer','searchEmp');
	}
}
//��֤��ѯ�����е����ڸ�ʽ�Ƿ���ȷ
function doSubmit(){
	var result = true;
	var errormsg = "<span class='errorMessage'>��ʽ����.</span>";
	if(document.getElementById('fromBirthDate') && document.getElementById('fromBirthDate').value != ''){
		if(!hrm.common.isDate(document.getElementById('fromBirthDate').value)){
            var html = document.getElementById("fromBirthDate").offsetParent.innerHTML;
            if(html.indexOf('span') < 0){
                document.getElementById("fromBirthDate").offsetParent.innerHTML += errormsg;
            }
            else{
            	document.getElementById("fromBirthDate").value="";
			}
			result = false;
		}
	}
	if(document.getElementById('tobirthDate') && document.getElementById('tobirthDate').value != ''){
		if(!hrm.common.isDate(document.getElementById('tobirthDate').value)){
			var html = document.getElementById("tobirthDate").offsetParent.innerHTML;
            if(html.indexOf('span') < 0){
            	document.getElementById("tobirthDate").offsetParent.innerHTML+=errormsg;
            }
            else{
            	document.getElementById("tobirthDate").value="";
			}
			result = false;
		}
	}
	if(document.getElementById('fromWorkDate') && document.getElementById('fromWorkDate').value != ''){
		if(!hrm.common.isDate(document.getElementById('fromWorkDate').value)){
			var html = document.getElementById("fromWorkDate").offsetParent.innerHTML;
            if(html.indexOf('span') < 0){
            	document.getElementById("fromWorkDate").offsetParent.innerHTML+=errormsg;
            }
            else{
            	document.getElementById("fromWorkDate").value="";
			}
			result = false;
		}
	}
	if(document.getElementById('toWorkDate') && document.getElementById('toWorkDate').value != ''){
		if(!hrm.common.isDate(document.getElementById('toWorkDate').value)){
			var html = document.getElementById("toWorkDate").offsetParent.innerHTML;
            if(html.indexOf('span') < 0){
            	document.getElementById("toWorkDate").offsetParent.innerHTML+=errormsg;
            }
            else{
            	document.getElementById("toWorkDate").value="";
			}
			result = false;
		}
	}
	if(document.getElementById('fromDate') && document.getElementById('fromDate').value != ''){
		if(!hrm.common.isDate(document.getElementById('fromDate').value)){
			var html = document.getElementById("fromDate").offsetParent.innerHTML;
            if(html.indexOf('span') < 0){
            	document.getElementById("fromDate").offsetParent.innerHTML+=errormsg;
            }
            else{
            	document.getElementById("fromDate").value="";
			}
			result = false;
		}
	}
	if(document.getElementById('toDate') && document.getElementById('toDate').value != ''){
		if(!hrm.common.isDate(document.getElementById('toDate').value)){
			var html = document.getElementById("toDate").offsetParent.innerHTML;
            if(html.indexOf('span') < 0){
            	document.getElementById("toDate").offsetParent.innerHTML+=errormsg;
            }
            else{
            	document.getElementById("toDate").value="";
			}
			result = false;
		}
	}
	if(document.getElementById('fromConfirmDate') && document.getElementById('fromConfirmDate').value != ''){
		if(!hrm.common.isDate(document.getElementById('fromConfirmDate').value)){
			var html = document.getElementById("fromConfirmDate").offsetParent.innerHTML;
            if(html.indexOf('span') < 0){
            	document.getElementById("fromConfirmDate").offsetParent.innerHTML+=errormsg;
            }
            else{
            	document.getElementById("fromConfirmDate").value="";
			}
			result = false;
		}
	}
	if(document.getElementById('toConfirmDate') && document.getElementById('toConfirmDate').value != ''){
		if(!hrm.common.isDate(document.getElementById('toConfirmDate').value)){
			var html = document.getElementById("toConfirmDate").offsetParent.innerHTML;
            if(html.indexOf('span') < 0){
            	document.getElementById("toConfirmDate").offsetParent.innerHTML+=errormsg;
            }
            else{
            	document.getElementById("toConfirmDate").value="";
			}
			result = false;
		}
	}
	return result;
}
//��ʼ���������ϵ��Զ����ֶζ�������
function initAdditionalList(){
	var addArr=new Array();
	<s:iterator value="additionalList" status="rowstatus">
		var obj = new Object();
		obj.type="<s:property value='eadcFieldType'/>";
		obj.value="<s:property value='eadcFieldValue'/>";
		addArr[<s:property value="eadcSeq"/>] = obj;
	</s:iterator>
	return addArr;
}
var fieldIndex=document.getElementById('additional').value;//������һ�β�ѯʱ��ѡ�񸽼�����ѡ���index
//������ѡ��ĸ�������ѡ����ʾ��Ӧ���͵������
function changeAdditional(){
	var value = "<s:property value='additional.value'/>";
	var value2 = "<s:property value='additional.value2'/>";
	document.getElementById('additionalDiv').innerHTML = "";
	var index = document.getElementById('additional').value;
	if(fieldIndex!=index){
		value="";
		value2="";
	}
	if(index ==0)return;
	var obj = ADDITIONAL_ARRAY[index];
	switch(obj.type){
	case 'select':
		var arr = obj.value.split(',');
		var html = "<select name='additional.value'>";
		for(var i =0; i < arr.length; i++){
			var tmp = "";
			if(arr[i].value == value){
				tmp= "selected";
			}
			html += "<option value='"+arr[i]+"' "+tmp+">"+arr[i]+"</option>";
		}
		html +="</select><input type='hidden' name='additional.eadcFieldType' value='select'/>";
		document.getElementById('additionalDiv').innerHTML =html;
		break;
	case 'input':
	case 'textarea':
		var html = "<input size=14 value='"+value+"' name='additional.value'/><input type='hidden' name='additional.eadcFieldType' value='input'/>";
		document.getElementById('additionalDiv').innerHTML =html;
		break;
	case 'date':
		var html = document.getElementById('dateSpan').innerHTML;
		html +="<input type='hidden' name='additional.eadcFieldType' value='date'/>";
		document.getElementById('additionalDiv').innerHTML =html;
		document.getElementById('dateValue').value=value;
		document.getElementById('dateValue2').value=value2;
		break;
	case 'number':
		var html = "��<input size=14 value='"+value+"' name='additional.value' onkeypress='hrm.common.checkOnKeyDownFloat(event);'/>��<input size=14 value='"+value2+"' name='additional.value2' onkeyup='hrm.common.checkOnKeyUpFloat(event,this);'/><input type='hidden' name='additional.eadcFieldType' value='number'/>";
		document.getElementById('additionalDiv').innerHTML =html;
		break;
	}
}
hrm.common.initDialog('dlgQuitDiv',350);//������ְdialog��ʼ��
hrm.common.initDialog('dlgTransfer',380);//�����䶯dialog��ʼ��

var ADDITIONAL_ARRAY = initAdditionalList();//��ʼ����������������
changeAdditional();//�����ϴ���ѡ�ĸ�����������

//��ʼ����ְ������
var eqTypeSubMap={};//����������Map
<s:iterator value="eqReasonMap" id="topEntrySet">
	eqTypeSubMap['<s:property value="#topEntrySet.key"/>']={};
	<s:iterator value="#topEntrySet.value" status="index" id="secEntrySet">
		<s:if test="#secEntrySet.key.length()==0">
			eqTypeSubMap['<s:property value="#topEntrySet.key"/>']['']='<s:property value="#secEntrySet.value"/>';
		</s:if><s:else>
			eqTypeSubMap['<s:property value="#topEntrySet.key"/>']['<s:property value="#secEntrySet.value"/>']='<s:property value="#secEntrySet.value"/>';
		</s:else>
	</s:iterator>
</s:iterator>
model.simple.changeSubSelect('eqType','empquit.eqReason_id','eqTypeSubMap','<s:property value="empquit.eqReason"/>');
</script>

<%@ include file="search_emp_div.jsp"%>
<jsp:include flush="true" page="position_choose_div.jsp"></jsp:include>
<jsp:include flush="true" page="../io/div_omm_select.jsp"></jsp:include>
<jsp:include flush="true" page="../io/div_upload.jsp"></jsp:include>
</body>