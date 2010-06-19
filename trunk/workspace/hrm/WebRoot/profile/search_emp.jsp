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
<title>员工管理</title>
<script type="text/JavaScript" src="../resource/js/hrm/profile.js"></script>
<script type='text/javascript' src='../dwr/interface/OrgMapAction.js'></script>
<link rel="stylesheet" type="text/css" href="../resource/css/edit_select.css" />
</head>
<body onload="hrm.common.check_showHide();hrm.common.check_order();">
<div id="selectcontent" class="selectdiv" style="visibility:hidden;pixelHeight:20px;z-index:9;">
	<iframe id="selframe" frameborder="0" height="100%"></iframe>
	<div id="selecthtml" class="selectcontent">selectdate</div>
</div>
<!-- 可输入的select (js引人不要移动位置)-->
<script type="text/javascript" src="../resource/js/edit_select.js"></script>
<s:component template="bodyhead">
	<s:param name="pagetitle" value="'员工管理：'" />
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
					<s:textfield id="empNo" label="员工" name="emp.empNoName" size="16" maxlength="64"/>
					<td align="right">组织单元:</td>
					<td>
			             <s:orgselector id="orgselector1" name="emp.empDeptNo.departmentName" hiddenFieldName="emp.empDeptNo.id"/>
					</td>
					<s:select label="用工形式" name="emp.empType.id" list="empTypes" listKey="id"
					 listValue="emptypeName" multiple="false" emptyOption="true" value="emp.empType.id" size="1" /> 
					<s:select label="工作地区" name="emp.empLocationNo.id" list="locations" listKey="id" 
					 listValue="locationName" multiple="false" emptyOption="true" value="emp.empLocationNo.id" size="1" />
				</tr>
			</table>
		</div>
		<div id="advanced" style="DISPLAY: none;clear : both">
			<table width="100%" border="0" cellspacing="2" cellpadding="0" >
				<tr>
				    <s:textfield label="员工姓名" id="empName" name="emp.empName" size="16" maxlength="64"/>
					<s:select label="性别" name="emp.empGender"  value="emp.empGender" list="#{'':'请选择',1:'男', 0:'女'}"/>
					<td colspan="2" align="center">
						<table>
							<tr>
								<td>
									出生日期:<s:textfield id="fromBirthDate" name="emp.empBirthDate" size="10"/>
									<img onclick="WdatePicker({el:'fromBirthDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="18" style="cursor:pointer" align="absmiddle">
									到:
									<s:textfield id="tobirthDate" name="emp.birthDate" size="10"/>
									<img onclick="WdatePicker({el:'tobirthDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="18" style="cursor:pointer" align="absmiddle">
								</td>
							</tr>
						</table>
					</td>	
				</tr>
				<tr>
					<s:textfield id="empNo" label="员工编号" name="emp.id" size="16" maxlength="64"/>
					<s:select label="婚否" name="emp.empMarital"  value="emp.empMarital" list="#{'':'请选择',0:'未婚', 1:'已婚'}"/>
					<td colspan="2" align="center">
						<table>
							<tr>
								<td>
									工作日期:<s:textfield id="fromWorkDate" name="emp.empWorkDate" size="10"/>
									<img onclick="WdatePicker({el:'fromWorkDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="18" style="cursor:pointer" align="absmiddle">
									到:
									<s:textfield id="toWorkDate" name="emp.workDate" size="10"/>
									<img onclick="WdatePicker({el:'toWorkDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="18" style="cursor:pointer" align="absmiddle">
								</td>
							</tr>
						</table>
					</td>		
				</tr>
				<tr>
					<s:textfield label="工作电话" name="emp.empWorkPhone" size="16" maxlength="32" onkeypress="javascript:if(event.keyCode==13)document.getElementById('searchEmp').submit();hrm.common.checkPhone(this);"/>
					<s:select label="血型" name="emp.empBlood" value="emp.empBlood" list="#{'':'请选择', 'O ':'O ', 'A ':'A ','B ':'B ', 'AB':'AB'}"/>
					<td colspan="2" align="center">
						<table>
							<tr>
								<td>
									入职日期:<s:textfield id="fromDate" name="emp.empJoinDate" size="10"/>
									<img onclick="WdatePicker({el:'fromDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="18" style="cursor:pointer" align="absmiddle">
									到:
									<s:textfield id="toDate" name="emp.joinDate" size="10"/>
									<img onclick="WdatePicker({el:'toDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="18" style="cursor:pointer" align="absmiddle">
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
				 	<s:textfield label="户口所在地" name="emp.empResidenceLoc" maxlength="128" size="16"></s:textfield>
					<td align="right">籍贯:</td>
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
									转正日期:<s:textfield id="fromConfirmDate" name="emp.empConfirmDate" size="10"/>
									<img onclick="WdatePicker({el:'fromConfirmDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="18" style="cursor:pointer" align="absmiddle">
									到:
									<s:textfield id="toConfirmDate" name="emp.confirmDate" size="10"/>
									<img onclick="WdatePicker({el:'toConfirmDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="18" style="cursor:pointer" align="absmiddle">
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<s:textfield label="档案所在地" name="emp.empProfileLoc" maxlength="128" size="16"></s:textfield>
		          	<td align="right">民族:</td>
		            <td>
		                <s:component template="editselect"  name="emp.empNation">
		                     <s:param name="list" value="@com.hr.base.ComonBeans@getEmpNation()"/>
		                     <s:param name="size" value="8"/>
		                     <s:param name="height" value="200"/>
		                </s:component>
		            </td> 
		            <td align="right">组织单元:</td>
					<td colspan="2" nowrap="nowrap">
					   <s:orgselector id="selector2" name="emp.empDeptNo.departmentName" hiddenFieldName="emp.empDeptNo.id"/>
					</td>
				</tr>
				<tr>
					<s:textfield label="手机" name="emp.empMobile" size="16" maxlength="32"/>
					<s:textfield label="政治面貌" name="emp.empPolitics" size="16" maxlength="30"/>
					<s:select label="用工形式" name="emp.empType.id" list="empTypes" listKey="id"
					  listValue="emptypeName" multiple="false" emptyOption="true" value="emp.empType.id" size="1" />
				</tr>
				<tr>
					<s:textfield label="家庭电话" name="emp.empHomePhone" size="16" maxlength="32" onkeypress="javascript:if(event.keyCode==13)document.getElementById('searchEmp').submit();hrm.common.checkPhone(this);"/>
					<s:textfield label="毕业院校" name="emp.empSchool" size="14" maxlength="32"/>
					<s:select label="工作地区" name="emp.empLocationNo.id" list="locations" listKey="id" 
					 listValue="locationName" multiple="false" emptyOption="true" value="emp.empLocationNo.id" size="1" />
				</tr>
				<tr>
					<s:textfield label="邮箱地址" name="emp.empEmail" size="16" maxlength="64"/>
					<td align="right">专业:</td>
					<td>
					 	<s:component template="editselect" name="emp.empSpeciality">
							<s:param name="list" value="@com.hr.base.ComonBeans@getEmpSpeciality()"/>
							<s:param name="size" value="8"/>
							<s:param name="height" value="220"/>
					 	</s:component>
					</td>
					
					<s:select label="社保种类" id="empBenefitTypeAdv"
						name="emp.empBenefitType.id" list="ebfTypeList" listKey="id" listValue="benefitTypeName" 
						multiple="false" headerKey="" headerValue="请选择" />
				</tr>
				<tr>
					<s:textfield label="证件号码" name="emp.empIdentificationNo" size="16" maxlength="30"/>
					<s:select label="学历"  name="emp.empDiploma" value="emp.empDiploma" emptyOption="true" list="@com.hr.base.ComonBeans@getEmpDiploma()"/>
					<s:if test="@com.hr.base.ComonBeans@getShiftEnable()==1">
		          	<s:select label="考勤方式" name="emp.empShiftType" list="#{'':'请选择',0:'免刷卡',2:'默认班次刷卡',3:'按班次刷卡'}" emptyOption="false"/>
		          	</s:if>
				</tr>
				<tr>
					<s:textfield label="当前住址" name="emp.empCurrAddr" size="16" maxlength="64" />
					<s:textfield label="即时通讯" name="emp.empMsn"size="14" maxlength="64"/>
					<s:if test="@com.hr.base.ComonBeans@getShiftEnable()==1">
					<s:textfield label="考勤卡号" name="emp.empShiftNo" size="14"></s:textfield>
					</s:if>
				</tr>
		        <tr>
		        	<s:textfield label="家庭地址" name="emp.empHomeAddr" size="16" maxlength="64" />
		            <s:textfield label="紧急联系人" name="emp.empUrgentContact" size="14" maxlength="64" />
		       		 <td align="right" >附加资料:</td>
		            <td nowrap="nowrap">
		                <s:select id="additional" list="additionalList" name="additional.eadcSeq" listKey="eadcSeq" listValue="eadcFieldName" emptyOption="true" onchange="changeAdditional();"></s:select>
		            </td>
		        </tr>
		        <tr>
		        	<s:textfield label="备注" name="emp.empComments" size="16" maxlength="128" />
		        	<s:textfield label="紧急联系方式" name="emp.empUrgentConMethod" size="14" maxlength="128" />
	       		 	<td>&nbsp;</td>
		       		 <td colspan="2" align="left">
		                <div id="additionalDiv"></div>
		            </td>
				</tr>
			</table>
		</div>
		</td>
		<td align="center" nowrap="nowrap">
			<input title="[Alt+F]" accesskey="F" id="submit_button" class="button" type="submit" onclick="javascript:if(!doSubmit()){return false;}hrm.common.search_check('basic','advanced');" name="button2" value="查询"> 
			<input title="[Alt+C]" accesskey="C" class="button" type="button" name="button22" value="重置" onclick="window.location='searchEmp.action';"><br>
			<div id="t1" align="center" style="DISPLAY: block;clear : both">
				<img src="../resource/images/basic_search.gif" width="8" height="8">
				<a href="#" class="utilsLink" onClick="hrm.common.searchTypeChange();">高级查询</a>
			</div>				
			<div id="t2" align="center" style="DISPLAY: none;clear : both">
				<img src="../resource/images/advanced_search.gif" width="8" height="8">
				<a href="#" class="utilsLink" onClick="hrm.common.searchTypeChange();">基本查询</a>
			</div>				
		</td>
	</tr>
	</tbody>
</table>
<br/>
<!-- 操作 -->
<table width="100%">
	<tr>
		<td align="left">
			<input type="hidden" name="searchOrExport" id="searchOrExport" />
			<s:hidden id="output-ommId" name="outmatchModelId"/>
			<s:hidden id="output-ioName" name="outputIoName"/>
			<ty:auth auths="101">
				<input class="button" type="button" onclick="batchQuit();" value="批量离职"/>
				<input class="button" type="button" onclick="doclick();" value="批量变动"/>
			</ty:auth>
			<ty:auth auths="101,3 or 101,2">
				<input class="button" type="button" onclick="initDivImmUpload('IEmployeeBasic');" value="数据导入"/>
				<input class="button" type="button" id="btnOutput" value="数据导出" onClick="hrm.common.export_check('basic','advanced','export');"/>
			</ty:auth>
		</td>
		<td align="right">本次查询共得到<s:property value="page.totalRows" />名员工记录&nbsp;</td>
	  </tr>
</table>
<!-- 查询结果 -->
<table id="profiletable" cellpadding="0" cellspacing="0" width="100%" border="0" class="gridtableList">	
	<tr>
	   	<ty:auth auths="101">
		<th align="center" width="3%">
			<input id="id_check_all" name='emp.ids' class="checkbox" type="checkbox" onclick="hrm.common.checkAllByName('emp.ids','id_check_all');" value="0">
		</th>
		</ty:auth>
	   	<th nowrap="nowrap">
	   		<a href="#" onclick="hrm.common.order_submit('empDistinctNo','searchEmp');">员工编号</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='empDistinctNo_img'>
	   	</th>
	   	<th nowrap="nowrap">
	   		<a href="#" onclick="hrm.common.order_submit('empName','searchEmp');">姓名</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='empName_img'>
	   	</th>
	   	<th nowrap="nowrap">
	   		<a href="#" onclick="hrm.common.order_submit('empOrgDept.departmentSortId','searchEmp');">部门</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='empOrgDept.departmentSortId_img'>
	   	</th>
	   	
	   	<th nowrap="nowrap">
	   		<a href="#" onclick="hrm.common.order_submit('empPbNo.pbName','searchEmp');">职位</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='empPbNo.pbName_img'>
	   	</th>
	   	<th nowrap="nowrap">
	   		<a href="#" onclick="hrm.common.order_submit('empLocationNo.locationName','searchEmp');">工作地区</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='empLocationNo.locationName_img'>
	   	</th>
	   	<th nowrap="nowrap">
	   		<a href="#" onclick="hrm.common.order_submit('empType.emptypeName','searchEmp');">用工形式</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='empType.emptypeName_img'>
	   	</th>
	   	<th nowrap="nowrap">
	   		<a href="#" onclick="hrm.common.order_submit('empJoinDate','searchEmp');">司龄</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='empJoinDate_img'>
	   	</th>
	   	<th nowrap="nowrap">
	   		<a href="#" onclick="hrm.common.order_submit('empLastChangeTime','searchEmp');">最后修改</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='empLastChangeTime_img'>
	   	</th>
	   	<th nowrap="nowrap">
	   		<a href="#" onclick="hrm.common.order_submit('empLastChangeTime','searchEmp');">操作</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='empLastChangeTime_img'>
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
				<td><span TITLE="工作电话: <s:property value='empWorkPhone'/>
出生日期: <s:property value='empBirthDate'/>
入职日期: <s:property value='empJoinDate'/>
证件号码: <s:property value='empIdentificationNo'/>
邮箱: <s:property value='empEmail'/>" />
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
					 src="../resource/images/Search.gif" alt="员工资料卡" border="0"/></a>
					 <ty:auth auths="101">
					 	<a href="#" onclick="hrm.profile.deleteEmp('<s:property value="id"/>','searchEmp')"><img
						 src="../resource/images/delete.gif" alt="删除" border="0"/></a>
					 </ty:auth>
				</td>  
    		</tr>
    	</s:iterator>
    </s:if>
	<s:else>
		<tr>
			<td align="center" colspan="13">不存在员工信息!</td>
		</tr>
	</s:else>
</table>
<!-- 分页 -->
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
<!-- 　批量修改  -->		
<p>&nbsp;</p> 
<ty:auth auths="101">
<s:if test="!empList.isEmpty()">
	<table cellspacing="0" cellpadding="0" border="0" valign="top" width="100%">
		<tr>
			<td nowrap="nowrap"><!-- 批量修改 -->
				<h3>
				<img src="../resource/images/h3Arrow.gif" width="11" height="11" border="0">
				 批量修改
				</h3>
			</td>
		</tr>
		<tr>
			<td valign="top" width="100%">
				<table border="0" width="100%" id="table3" cellspacing="0" cellpadding="0" class="formtable">
					<tr>
						
						<s:textfield label="档案所在地" id="chang_epLoc" name="batchemp.empProfileLoc" size="16" maxlength="128"/>
						<s:select label="用工形式" id="chang_type" name="batchemp.typeNo" list="empTypes" listKey="id" listValue="emptypeName" multiple="false" emptyOption="true" value="emp.empType.id" size="1" />
						
					
					    <s:select label="工作地区" id="chang_loc" name="batchemp.locationNo" list="locations" listKey="id" listValue="locationName" multiple="false" emptyOption="true" value="emp.empLocationNo.id" size="1" />
						<s:if test="@com.hr.base.ComonBeans@getShiftEnable()==1">
		 				<s:select label="考勤方式" id="chang_shift_type" name="batchemp.empShiftType" emptyOption="false" list="#{0:'请选择',1:'免刷卡',2:'默认班次刷卡',3:'按班次刷卡'}"/>
						</s:if>
						<td>
							<!-- 修改  -->  
							<input title="[Alt+E]" accesskey="E" class="button" type="button" onClick="hrm.profile.del_update('update');" name="button23" value="批量修改">
						</td>
						</tr>
				</table>
			</td>
		</tr>
	</table>
</s:if>
</ty:auth>
</s:form>
<!-- 批量离职 -->
<div id="dlgQuitDiv" style="display:none" title="批量离职">
	<table cellpadding="0" border="0">
		<tr>
			<td align="left" nowrap="nowrap">离职日期<span class="required">*</span>：</td>
			<td>
				<s:textfield id="joinDate" name="empquit.eqDate" required="true" size="10"/>
				<img onclick="WdatePicker({el:'joinDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="18" style="cursor:pointer" align="absmiddle">
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">离职种类<span class="required">*</span>：</td>
			<td><s:select name="empquit.eqType" id="eqType" list="eqTypeMap" onchange="model.simple.changeSubSelect(this,'empquit.eqReason_id','eqTypeSubMap')"></s:select></td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">离职审批人<span class="required">*</span>：</td>

			<td>
				 <s:hidden id="permissionId" name="empquit.eqPermission.id" />
			 	 <s:textfield id="permission" size="16" readonly="true"></s:textfield>
				 <img src="../resource/images/search_icon.gif" onclick="showChooseEmpDiv(1,1)"  alt='点击图标选择员工'/>
			</td>
	
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">离职原因<span class="required">*</span>：</td>
			<td><select name="empquit.eqReason" id="empquit.eqReason_id"></select></td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">备注：</td>
			<td><textarea name="empquit.erComments" rows="3" cols="30"></textarea></td>
		</tr>
		<tr height="35">
			<td colspan="2" align="center">
				<input type="hidden" name="qemp.ids" id="ids"/>
				<input id="eqAddbtn" type="button" onclick="batchQuitSubmit();" value="离职"/>
				<input type="button" value="关闭" onclick="hrm.common.closeDialog('dlgQuitDiv');"/>
			</td>
		</tr>
	</table>
</div>
<!-- 批量变动 -->
<div id="dlgTransfer" style="display:none" class="prompt_div_inline" title="变动记录">
	<table width="100%"  class="prompt_div_body" cellpadding="0" border="0">
		<tr id="targetTr">
			<td align="left" nowrap="nowrap">&nbsp;变动种类<span class="required">*</span>：</td>
			<td><select name="transfer.eftTransferType" id="transferType" onchange="changeForm()">
				<option value="0">平调</option>
				<option value="1">晋升</option>
				<option value="2">降级</option>
				<option value="3">转岗</option>
				<option value="4">转正</option>
			</select></td>
			
		</tr>

		<tr>
			<td align="left" nowrap="nowrap">&nbsp;变动时间<span class="required">*</span>：</td>
			<td>
				<s:textfield name="transfer.eftTransferDate" id="transferTime" required="true" size="10"/>
				<img onclick="WdatePicker({el:'transferTime'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="18" style="cursor:pointer" align="absmiddle">
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" id="tdDept">&nbsp;新部门<span class="required">*</span>：</td>
			<td>
			    <s:hidden id="chang_deptId" name="transfer.eftNewDeptNo.id"/>
				<s:textfield id="chang_dept" size="16" readonly="true"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" id="tdPosition">&nbsp;新职位<span class="required">*</span>：</td>
			
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
			<td align="left" nowrap="nowrap">&nbsp;变动原因：</td>
			<td colspan="3"><input name="transfer.eftReason" size="28" maxlength="128"/></td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">&nbsp;备注：</td>
			<td><textarea name="transfer.eftComments" rows="3" cols="30"></textarea></td>
		</tr>
		<tr height="35">
			<td colspan="2" align="center">
				<input type="hidden" name="ids" id="transids"/>
				<input type="hidden" name="transfer.eftNewDeptNo" id="eftNewDeptNo"/>
				<input type="hidden" name="transfer.eftNewJobTitle" id="eftNewJobTitle"/>
				<input id="trAddbtn" type="button" onclick="hrm.profile.del_update('dlgTransfer');"; value="变动"/>
				<input type="button" value="关闭" onclick="hrm.common.closeDialog('dlgTransfer')"/>
			</td>
		</tr>
	</table>
	
</div>
<span id="dateSpan" style="display:none">
	<s:textfield id="dateValue" name="additional.value" size="10" maxlength="10"/>
	<img onclick="WdatePicker({el:'dateValue'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="18" style="cursor:pointer" align="absmiddle">
	到
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
    $('#tdDept').html("&nbsp;新部门:");
    $('#tdPosition').html("&nbsp;新职位:");
    
    var typeSelect=$('#employmentForm');
    
    var target=$('#targetTr');
    var tr=$('<tr></tr>');
    tr.attr("id","employmentFormTr");
    var td=$('<td></td>');
	td.html("&nbsp用工形式<span class=\"required\">*</span>:");
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

//批量离职的检查
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
		error="请至少选择一个员工！";
		alert(error);
		return ;
	}
	else{
		alert("请确认该员工的离职手续已办理完毕！");
		ids = ids.trim();
		if(',' == ids.charAt(ids.length-1)){
			ids = ids.substring(0,ids.length-1);
		}
		document.getElementById('ids').value = ids;
		//初始化批量离职日期
		document.getElementById('joinDate').value=(new Date()).toHRMDateString();
		hrm.common.openDialog('dlgQuitDiv','searchEmp');
	}
}
//批量离职的提交
function batchQuitSubmit(){
	if(!hrm.common.isDate(document.getElementById('joinDate').value)) {
		alert('复职日期不能为空或日期格式不正确！');
		return;
	}
	if(hrm.common.isNull(document.getElementById('empquit.eqReason_id').value)){
		alert("离职原因不能为空！");
		return;
	}
	if(hrm.common.isNull(document.getElementById('permissionId').value)){
		alert("离职审批人不能为空！");
		return;
	}
	hrm.common.search_check('basic','advanced');
	$('#empName').val("");
	document.getElementById('searchEmp').action="batchQuit.action";
	document.getElementById('searchEmp').submit();
}
//批量变动
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
		error="请至少选择一个员工！";
 		alert(error);
		return ;
 	 }
	else{
		//初始化批量变动日期
		document.getElementById('transferTime').value=(new Date()).toHRMDateString();
		$('#dlgTransfer').dialog('option', 'draggable', false);
		hrm.common.openDialog('dlgTransfer','searchEmp');
	}
}
//验证查询条件中的日期格式是否正确
function doSubmit(){
	var result = true;
	var errormsg = "<span class='errorMessage'>格式错误.</span>";
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
//初始化附加资料的自定义字段对象数组
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
var fieldIndex=document.getElementById('additional').value;//保存上一次查询时所选择附加资料选项的index
//根据所选择的附加资料选项显示对应类型的输入框
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
		var html = "从<input size=14 value='"+value+"' name='additional.value' onkeypress='hrm.common.checkOnKeyDownFloat(event);'/>到<input size=14 value='"+value2+"' name='additional.value2' onkeyup='hrm.common.checkOnKeyUpFloat(event,this);'/><input type='hidden' name='additional.eadcFieldType' value='number'/>";
		document.getElementById('additionalDiv').innerHTML =html;
		break;
	}
}
hrm.common.initDialog('dlgQuitDiv',350);//批量离职dialog初始化
hrm.common.initDialog('dlgTransfer',380);//批量变动dialog初始化

var ADDITIONAL_ARRAY = initAdditionalList();//初始化附加资料下拉框
changeAdditional();//保存上次所选的附加资料类型

//初始化离职下拉框
var eqTypeSubMap={};//二级下拉框Map
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