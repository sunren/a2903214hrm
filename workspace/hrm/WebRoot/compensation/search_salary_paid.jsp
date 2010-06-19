<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<%@taglib prefix="ty" uri="/tengsource"%>
<!--
 =========================================================
' File:search_salary_paid.jsp
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
    <s:head/>
	<title>薪资发放列表</title> 
	<script type='text/javascript' src='../dwr/interface/DWRforSalaryPaid.js'></script>
	<script type='text/javascript' src='../dwr/interface/DWRforAcctItemDef.js'></script>
	<script type="text/javascript" src="../dwr/interface/emailSalary.js"></script>
    <script type='text/javascript' src='../dwr/interface/AddCompaplan.js'></script>
    <script type='text/javascript' src='../dwr/interface/UpdateSalaryConfigBatch.js'></script>
</head>
<body onload="JudgeInit();">
	<span class="errorMessage" id="message"></span>
	<s:component template="bodyhead">
		<s:param name="pagetitle" value="'薪资发放列表('+year+'-'+month+')'" />
		<s:param name="helpUrl" value="'26'" />
	</s:component>
	<s:form id="searchSalaryPaid" name="searchSalaryPaid"
		action="searchSalaryPaid" namespace="/compensation" method="POST">
		<s:token></s:token>
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			class="formtable">
			<tr>
				<s:hidden id="order" name="page.order" />
				<s:hidden id="compaplanCounts" name="compaplanCounts"/>
				<s:hidden id="beneAddtionalCount" name="beneAddtionalCount"/>
				<input type="hidden" id="operate" name="page.operate" />
				<td align="right">员工:</td>
				<td><s:textfield id="empName" name="employee.empName" size="12" maxlength="64" /></td>
				<td align="right">组织单元:</td>
				<td>
				    <s:orgselector id="orgselector1" name="employee.empDeptNo.departmentName" hiddenFieldName="employee.empDeptNo.id" isShowDisable="show"/>
				</td>
				<td align="right">工作地区:</td>
				<td>
				    <s:select list="locationList" listKey="id"
                        name="employee.empLocationNo.id" listValue="locationName" multiple="false"
                        emptyOption="true" value="employee.empLocationNo.id" size="1" />
				</td>
				<td align="right">用工形式:</td>
				<td>
				    <s:select list="empTypeList" listKey="id"
                    listValue="emptypeName" name="employee.empType.id" multiple="false"
                    emptyOption="true" value="employee.empType.id" size="1" />
				</td>
				<td rowspan="2">
					<input title="[Alt+F]" accesskey="F" name="sub_button"
						id="submit_button" type="submit" size="4" class="button" onclick="javascript:document.getElementById('searchOrExport').value='';"
						value="查询">
					<input title="[Alt+C]" accesskey="C" name="clear_button"
						class="button" type="button"
						onClick="window.location='searchSalaryPaid.action';" value="重置">
					<br>
				</td>
			</tr>
			<tr>
			    <td align="right">薪资级别:</td>
			    <td>
			        <s:select list="jobgradeList" listKey="id"
                     listValue="jobGradeName" name="employee.config.escJobgrade.id" emptyOption="true" />
			    </td>
				<td align="right">帐套:</td>
				<td>
				    <s:select list="acctVersionList" listKey="id"
                    listValue="esavEsac.esacName" name="employee.config.escEsavId.id" emptyOption="true" />
				</td>
				<td align="right">发薪年月:</td>
				<td>
					<s:select id="inputyear" name="year" list="years"
						emptyOption="false" />
					<s:select id="inputmonth" name="month"
						list="#{'01':'01','02':'02','03':'03','04':'04','05':'05','06':'06','07':'07','08':'08','09':'09','10':'10','11':'11','12':'12'}"
						emptyOption="false" />
					<s:hidden id="yearmonth" value=""></s:hidden>
				</td>
				<td align="right">显示条件:</td>
				<td>
				    <s:select id="searchEmpSalType" name="searchEmpSalType" 
                        list="#{'paid':'已发薪资', 'newIn':'|--本月入职', 'newOut':'|--本月离职', 'normal':'|--其他', 'noPaid':'未发薪资'}"
                        emptyOption="false" />
				</td>
			</tr>
		</table>
		
		<table width="100%">
		  <tr>
		    <br />
		  </tr>
		  <tr>
		    <td>
				<s:hidden id="output-ommId" name="outmatchModelId"/>
				<s:hidden id="output-ioName" name="outputIoName"/>
				<input type="hidden" name="searchOrExport" id="searchOrExport" />
				
				<ty:auth auths="201,3">
					<s:if test="!salaryPaidList.isEmpty()&&espdStatus==1">
						<input class="button" type="button" value=" 审核 " id="btnsubmit"
							name="btnsubmit" onclick="submitallpaiddata('approve');this.disabled = true;">
					</s:if>
					<s:if test="!salaryPaidList.isEmpty()&&espdStatus==2">
						<input class="button" type="button" value=" 解封 " id="btnsubmit" name="btnsubmit"
							onclick="reopenSalaryPay('<s:property value="year"/>','<s:property value="month"/>');">
					</s:if>
				</ty:auth>
			
			    <ty:auth auths="201">
				    <s:if test="!salaryPaidList.isEmpty()&&espdStatus==0">
						<input class="button" type="button" value="批量修改" id="batchUpdate"
							name="batchUpdate" onclick="updateBatch();">
					</s:if>
				</ty:auth>
				
				<ty:auth auths="201,3 or 201,2">
					<s:if test="!salaryPaidList.isEmpty()&&espdStatus==0">
						<input class="button" type="button" id="btnsubmit" name="btnsubmit"
							value="封帐申请" onclick="submitallpaiddata('submit');this.disabled = true;">
					</s:if>
					<s:if test="espdStatus==0">
						<input class="button" type="button" id="btnReCalculate"
							name="btnReCalculate" value="重新初始化薪资"
							onclick="submitallpaiddata('ReCalculate');this.disabled = true;">
					</s:if>
					<s:if test="espdStatus==2 && searchEmpSalType!='noPaid'">
						<input class="button" type="button" id="exportToBank" name="exportToBank" value="导出到银行" onclick="doExport('exportBank');" />
						<input class="button" type="button" id="exportPayslip" name="exportPayslip" value="导出工资条" onclick="doExport('exportPayslip');" />
	                    <input class="button" type="button" id="btnOutput" name="exportPayroll" value="导出工资表" onclick="doExport('exportPayroll');" />
					</s:if>
					<s:if test="!salaryPaidList.isEmpty()&&espdStatus==0">
					    <input class="button" type="button" id="btnImportSalary" name="btnImportSalary" value="导入薪资" onclick="initDivImmUpload('IEmpSalaryPay', '<s:property value="year" /><s:property value="month" />');"/>
					</s:if>
					<s:if test="(espdStatus==0 || espdStatus==1 || espdStatus==2) && searchEmpSalType!='noPaid'">
						<input class="button" type="button" id="export_button" name="export_button" value="数据导出" onclick="doExport('exportSalary');">
					</s:if>
				</ty:auth>
				
				<ty:auth auths="231">
				    <s:if test="espdStatus==2">
				         <input class="button" type="button" id="btnEmailSalary" name="btnEmailSalary" value="邮件通知" onclick="emailtSalary();">
				    </s:if>
				</ty:auth>
			</td>
			<td align="right">本次查询共得到<s:property value="page.totalRows" />条薪资发放记录&nbsp;</td>
		  </tr>
		</table>
			
		<table id="salary_paid_table" cellpadding="0" cellspacing="0" width="100%" border="0" class="gridtableList">
			<tr>
				<s:if test="espdStatus==0">
					<th align="center" width="3%">
						<input id="checkboxforall" name='checkboxforall' class="checkbox"
							type="checkbox" onclick="HRMCommon.checkAllByName('changIds','checkboxforall');" value="全选">
					</th>
				</s:if>
				<th nowrap="nowrap">
				  <a href="#" onclick="HRMCommon.order_submit('emp.empDistinctNo');">员工编号</a><img 
				  src='../resource/images/arrow_.gif' width='8' height='10' id='emp.empDistinctNo_img'>
				</th>
				<th nowrap="nowrap">
				  <a href="#" onclick="HRMCommon.order_submit('emp.empName');">员工姓名</a><img 
				  src='../resource/images/arrow_.gif' width='8' height='10' id='emp.empName_img'>
				</th>
				<th nowrap="nowrap" colspan="2">基本工资</th>
				<th nowrap="nowrap">税前总收入</th>
				<th nowrap="nowrap" colspan="2">社保总额</th>
				<th nowrap="nowrap">所得税</th>
				<th nowrap="nowrap">税后收入</th>
				<th>
				    <s:if test="searchEmpSalType=='noPaid'">薪资帐套</s:if>
				    <s:else>
					    <a href="#" onclick="HRMCommon.order_submit('esavEsac.esacName');">
						薪资帐套</a><img src='../resource/images/arrow_.gif' width='8' height='10'
							id='esavEsac.esacName_img'>
				    </s:else>
				</th>
				<th nowrap="nowrap">
					操作
					<s:hidden id="listsize" name="listsize"
						value="%{salaryPaidList.size}" />
					<s:hidden id="thisyear" name="thisyear" value="%{year}" />
					<s:hidden id="thismonth" name="thismonth" value="%{month}" />
				</th>
			</tr>
			<s:if test="!salaryPaidList.isEmpty()">
				<s:iterator value="salaryPaidList" status="index">
					<tr>
						<s:hidden id="%{'empName'+(#index.count)}" name="hiddenName"
							value="%{empName}" />
						<%--<s:hidden id="%{'Basesalary'+(#index.count)}"  value="%{config.escBasesalary}"/>
					--%>
						<%--员工信息层的数据--%>
						<input type="hidden" id="salarypayID<s:property value='#index.count'/>"
							name="salarypayID<s:property value='#index.count'/>"
							value="<s:property value='empsalarypay.id'/>" />
							
						<input type="hidden" id="departmentname<s:property value='#index.count'/>"
							name="departmentname<s:property value='#index.count'/>"
							value="%{empDeptNo.id}" />
							
						<input type="hidden" id="emptypename<s:property value='#index.count'/>"
							name="emptypename<s:property value='#index.count'/>"
							value="%{empType.id}" />
							
						<input type="hidden" id="empid<s:property value='#index.count'/>"
							name="empid<s:property value='#index.count'/>"
							value="<s:property value='id' />" />
							
						<input type="hidden" id="empstatus<s:property value='#index.count'/>"
							name="empstatus<s:property value='#index.count'/>"
							value="%{empStatus}" />
							
						<input type="hidden" id="espComment<s:property value='#index.count'/>"
							name="espComment<s:property value='#index.count'/>"
							value="<s:property value='empsalarypay.espComment' />" />
							
						<input type="hidden" id="espEsacName<s:property value='#index.count'/>"
							name="espEsacName<s:property value='#index.count'/>"
							value="<s:property value='empsalarypay.espEsavId.esavEsac.esacName' />" />
							
						<input type="hidden" id="escEsavId<s:property value='#index.count'/>"
							name="escEsavId<s:property value='#index.count'/>"
							value="<s:property value='config.escEsavId.id' />" />

						<input type="hidden" id="espBenefitPlans<s:property value='#index.count'/>"
							name="espBenefitPlans<s:property value='#index.count'/>"
							value="<s:property value='empsalarypay.espBenefitPlans' />" />

						<s:if test="espdStatus==0">
							<td align="center" width="3%">
								<s:if test="empsalarypay.showColumn1!=null">
									<input id="changIds" type="checkbox" name='changIds'
										class="checkbox" value="<s:property value='id'/>" />
									<input id="espEsavIds" type="hidden" name='acctIds'
										value="<s:property value='empsalarypay.espEsavId.id'/>" />
								</s:if>
							</td>
						</s:if>
						<td id="empDistinctNo<s:property value="#index.count"/>" nowrap="nowrap" align="center">
						    <s:property value='empDistinctNo'/>
						</td>
						<td id="name<s:property value="#index.count"/>"  nowrap="nowrap" align="center">
							<span TITLE=
"员工编号：<s:property value='empDistinctNo'/>
所属部门：<s:property value='%{getDepName(empDeptNo.id)}'/>
用工形式：<s:property value='%{getEmpType(empType.id)}'/>
工作地区：<s:property value='%{getLocation(empLocationNo.id)}'/>"
                            />
							<s:if test="config!=null">
								&nbsp;<a onclick="showEmpSalaryConfigDiv('<s:property value="#index.count"/>')"
										style="CURSOR: pointer"><font color="blue"><s:property value="empName" /> </font> </a>
							</s:if> <s:else>
								&nbsp;<s:property value="empName" />
							</s:else>
						</td>

						<td id="empBaseSalaryTD<s:property value="#index.count"/>"
							align="right" nowrap="nowrap">
							<span id="Basesalary_span<s:property value="#index.count"/>"><s:property
									value="empsalarypay.showColumn1" /> </span>
							<s:hidden id="%{'Basesalary_Final'+(#index.count)}"
								name="salarypaidForm.espBasesalary"
								value="%{empsalarypay.espBasesalary}" />
						</td>
						<td align="center" nowrap="nowrap">
							<s:if test="config!=null">
								<s:if test="empsalarypay.espChanged==1 && espdStatus!=2">
									<img id="searchImg<s:property value='#index.count'/>"
										src="../resource/images/Search_change.gif" alt="查看详细"
										onclick="myshowconfigDiv('<s:property value="#index.count"/>');"
										border="0" style="CURSOR: pointer">
								</s:if>
								<s:else>
									<img id="searchImg<s:property value='#index.count'/>"
										src="../resource/images/Search.gif" alt="查看详细"
										onclick="myshowconfigDiv('<s:property value="#index.count"/>');"
										border="0" style="CURSOR: pointer">
								</s:else>
							</s:if>
						</td>
						<td id="empGrossamt<s:property value="#index.count"/>"
							align="center" nowrap="nowrap">
							&nbsp;
							<s:property value="empsalarypay.showColumn8" />
						</td>
						<td id="emp4Money<s:property value="#index.count"/>"
							align="right" nowrap="nowrap">
							&nbsp;
							<span id="benefitValue_span<s:property value="#index.count"/>">
							    <s:property value="empsalarypay.showColumn15" />
							</span>
						</td>
						<td align="left" nowrap="nowrap">
							<span id="benefitMirror_span<s:property value="#index.count"/>">
							<s:if test="config!=null && empsalarypay!=null && empsalarypay.espBenefitPlans>0">
								<s:if test="empsalarypay.espBenefitPlans>1 && espdStatus!=2">
									<img id="benesearchImg<s:property value="#index.count"/>"
										src="../resource/images/Search_change.gif" alt="查看详细"
										onclick="showBeneModifyDiv('<s:property value="#index.count"/>');"
										border="0" style="CURSOR: pointer" />
								</s:if>
								<s:else>
									<img id="benesearchImg<s:property value="#index.count"/>"
										src="../resource/images/Search.gif" alt="查看详细"
										onclick="showBeneModifyDiv('<s:property value="#index.count"/>');"
										border="0" style="CURSOR: pointer" />
								</s:else>
							</s:if>
							</span>
						</td>
			
						<td id="empTaxamt<s:property value="#index.count"/>"
							align="center" nowrap="nowrap">
							&nbsp;
							<s:property value="empsalarypay.showColumn18" />
						</td>
						<td id="empNetamt<s:property value="#index.count"/>"
							align="center" nowrap="nowrap">
							&nbsp;
							<s:property value="empsalarypay.showColumn19" />
						</td>
						<td id="td_esac<s:property value='%{#index.count}'/>"
							align="center">
							<s:property value="empsalarypay.espEsavId.esavEsac.esacName" />
						</td>
						<td align="center" id="empOperation<s:property value="#index.count"/>">
						<ty:auth auths="201">
							<s:if test="empsalarypay!=null&&(espdStatus==0)">
								<a href="#"
									onclick="myclearSalary('<s:property value="#index.count"/>','<s:property value="empName"/>','<s:property value="empsalarypay.id"/>');"><img
										src="../resource/images/deletesalaryconf.gif" alt='删除'
										border='0' /> </a>
							</s:if>
								<s:if test="empsalarypay!=null&&(espdStatus==1)">
									<a href="#"
										onclick="myclearSalary('<s:property value="#index.count"/>','<s:property value="empName"/>','<s:property value="empsalarypay.id"/>');"><img
											src="../resource/images/deletesalaryconf.gif" alt='删除'
											border='0' /> </a>
								</s:if>
						</ty:auth>
						&nbsp;
						</td>
					</tr>

				</s:iterator>
			</s:if>
			<s:else>
				<tr>
					<!-- 不存在员工调薪信息 -->
					<td align="center" colspan="17">
						<s:property value="year" />年<s:property value="month" />月<span id="infomation">
						<s:if test="salaryPaidList.size()==0&&espdStatus==3">
					       员工薪资发放数据尚未初始化！
					    </s:if>
					    <s:if test="salaryPaidList.size()==0&&espdStatus<3">
						  无相应查询条件的员工薪资数据！ 
						</s:if>
						</span>
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
          	  	    <s:param name="start" value="page.start" /><s:param name="end" value="page.end" /><s:param name="formId" value="'searchSalaryPaid'" />
          	  	</s:component>
			  </td>
  			</tr>
		</table>

		<div id="tmpletId" style="DISPLAY: none">
			<img src="../resource/images/basic_search.gif"
				onload="HRMCommon.check_order();" />
		</div>
	</s:form>

	<!-- -salary pay div begin -->
	<div id="dlg_emp_salary_pay_div" class="prompt_div_inline"  style="width:420;display:none;">
		<div id="emp_salary_pay_div_header" style="CURSOR: move;">
			<input id="div_empid" type="hidden" name="hiddenName" />
			<input id="employeename" type="hidden" name="hiddenName" />
			<input id="div_rowIDPay" type="hidden" name="hiddenName" />
		</div>

		<div id="change_stutus_error" class="prompt_div_err"></div>
		<table id="newConfigTablePay" width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td colspan="1" align="left" valign="center">
					薪资发放备注:
				</td>
				<td colspan="3" align="left" valign="center">
					<s:if test="(espdStatus==0)">
						<textarea id="div_espComments" cols="26" rows="2"
							onkeydown="MKeyTextLength(this,128);" maxlength="127"> 
				  		</textarea>
					</s:if>
					<s:if test="(espdStatus==2)">
						<textarea id="div_espComments" cols="26" rows="2"
							maxlength="127" disabled="disabled"> 
				  		</textarea>
					</s:if>
					<s:if test="(espdStatus==1)">
						<textarea id="div_espComments" cols="26" rows="2"
							onkeydown="MKeyTextLength(this,128);" maxlength="127">
				  		</textarea>
					</s:if>
				</td>
			</tr>
			<tr>
				<td align="center" colspan="4">
				<ty:auth auths="201">
					<s:if test="(espdStatus==0)">
						<input id="div_btnSubmit" name="hiddenName" type="button"
							onclick="submitSalaryForPaid();" value="提交">
						<input id="div_btnSubmit" name="hiddenName" type="button"
							onclick="viewNewSalaryPaid();" value="重新计算薪资">
						<input id="div_btnSubmit" name="hiddenName" type="button"
							onclick="resetSalaryPaid();" value="重置">
					</s:if>
				</ty:auth>
					<input type="button" name="hiddenName"
						onclick="javascript:HRMCommon.closeDialog('dlg_emp_salary_pay_div');" value="关闭">
				</td>
			</tr>
		</table>
	</div>
	<!-- -salary pay div end -->

	<!-- -salary config div begin -->
	<div id="dlg_emp_salary_config_div" class="prompt_div_inline"  style="width:420;display:none;">
		<div id="emp_salary_config_div_header" style="CURSOR: move;">
			<input id="div_empid" type="hidden" name="hiddenName" />
			<input id="employeename" type="hidden" name="hiddenName" />
			<input id="div_rowID" type="hidden" name="hiddenName" />
		</div>

		<div id="change_stutus_error" class="prompt_div_err"></div>
		<table id="empSalaryConfigTable" width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="center" colspan="4">
					<input type="button"
						onclick="HRMCommon.closeDialog('dlg_emp_salary_config_div');" value="关闭">
				</td>
			</tr>
		</table>
	</div>

	<div id="dlg_updateBatch_div" class="prompt_div_inline" style="width:300;display:none;">
		<div id="updateBatch_div_header" style="CURSOR: move;">
		</div>
		<div id="change_stutus_error" class="prompt_div_err"></div>
		<table id="newConfigTable" width="100%" border="0" cellspacing="0" cellpadding="0">
			<input type='hidden' name="empIds" id="empIds" />
			<tr>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td align='right'>
					选择薪资项目：
				</td>
				<td>
					<select id="acctItemSelect" name="acctItemSelect"></select>
				</td>
			</tr>
			<tr>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td align='right'>
					对应项目数值：
				</td>
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
					<input type="button" name="batchButton" value="确定"
						onclick="updateSubmit()" />
					<input type="button" name="closeButton" value="取消"
						onclick="HRMCommon.closeDialog('dlg_updateBatch_div');" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td></td>
			</tr>
		</table>
	</div>
	
  <div id="dlgEmailSalary" class="prompt_div_inline"
        style="width:460;display:none;">
     <div id="emailSalaryDiv_header" style="CURSOR: move;"
            class="prompt_div_head">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td valign="middle"><h3 id="emailSalaryTitle">薪资邮件通知</h3></td>
                </tr>
            </table>
      </div>
<table>
    <tr>
      <td>
      		<s:empselector id="empselector1" name="emp.empDeptNo.departmentName" condition="empHasComp&yearmonth" hiddenFieldName="emp.empDeptNo.id" isShowDisable="show"/>
      </td>
    </tr>
    <tr>
        <td align="center">
            <input id="btnEmail" type="button" value=" 发送 " onclick="sendEmail();">
        </td>
    </tr>
</table>
</div>
<script type="text/javascript" language="javascript">

// onload事件触发，判断是否需要执行初始化操作
function JudgeInit(){
  <s:if test="!hasAuth(201)">
	  return; // 用户没有201权限，不允许执行
  </s:if>
	
  if(<s:property value="salaryPaidList.size()"/>==0&&<s:property value="espdStatus"/>==3){
      var year=document.getElementById('inputyear').value;
      var month=document.getElementById('inputmonth').value;
      var counts=document.getElementById("compaplanCounts").value;
      var beneAddCount = document.getElementById("beneAddtionalCount").value;
      var tempStr = "薪资发放数据不存在！";
      if(counts > 0){
          tempStr += "有"+counts+"条调薪计划，";
      }
      if(beneAddCount > 0){
          tempStr += "有"+beneAddCount+"条社保补缴记录，";
      }
      if (!confirm(tempStr+"您要初始化"+year+"年"+month+"月数据吗？")){ 
          return;
      }
 	  document.searchSalaryPaid.action="initPaid.action";
 	  document.searchSalaryPaid.submit();
  }

}

function updateBatch() {
	var checkBoxName = document.getElementsByName('changIds');
	var empIdValue = '';
	judge=false;
	for(var i=0;i<checkBoxName.length;i=i+1)
	{
		if(checkBoxName[i].checked===true)
		{
			judge=true;
			if(empIdValue != '') {
				empIdValue = empIdValue+','+checkBoxName[i].value;
			}else {
				empIdValue = checkBoxName[i].value;
			}
		}
	}
	if(judge===false){
		alert('请至少选择一个员工！');
		return false;
	}
	
	document.getElementById('empIds').value = empIdValue;
	var versionValue = document.getElementsByName('acctIds');
	
	var firstIndex = 0;
	for(var i=0;i<versionValue.length;i++) {
		if(checkBoxName[i].checked===true) {
			if(firstIndex == 0){
				firstIndex = i;
			}
			if(versionValue[i].value!=versionValue[firstIndex].value){
				alert('不能同时批量修改属于两个帐套或不同版本帐套的员工薪资！');
				return false;
			}
		}
	}
	DWRforAcctItemDef.getAcctItemsById(versionValue[firstIndex].value,getItemCallBack);
	function getItemCallBack(msg){
		if(msg == null){
			errMsg("errMsg", "数据异常，请刷新后再试！");
			return;
		}
		var index = 0;
		for(var i=0;i<msg.length;i++) { // 固定项、浮动项允许修改
			if(msg[i].esaiDataIsCalc == 1 || msg[i].esaiDataIsCalc ==0){
				document.getElementById('acctItemSelect').options[index++]=new Option(msg[i].esaiEsdd.esddName,msg[i].id,true,true);
			}
		}
		$('#dlg_updateBatch_div').dialog("option", "title", "批量修改薪资发放信息");
		HRMCommon.openDialog('dlg_updateBatch_div');
	}
}

function updateSubmit(){
	var updateIds = document.getElementById('empIds').value;
	var itemId = document.getElementById('acctItemSelect').value;
	var yearmonth = $('#inputyear').val() + $('#inputmonth').val();
	var salaryValue = document.getElementById('batchSalary').value;
	if(salaryValue == null || salaryValue.trim()==""){
		alert("项目数值不能为空！");
		return;
	}
	UpdateSalaryConfigBatch.updatePayBatch(updateIds, itemId, salaryValue, yearmonth, updateCallBack);
	HRMCommon.closeDialog('dlg_updateBatch_div');
	function updateCallBack(msg) {
		try{
			var retcode = HRMCommon.alertMsgHandler(msg);
			if(retcode == 'SUCC') {
				//成功更新员工薪资！
				var data = msg.substring(5); // 截取成功信息（去掉前缀“SUCC:”）
				alert(data);
			 	document.searchSalaryPaid.action="searchSalaryPaid.action";
			 	document.searchSalaryPaid.submit();
			}
		}catch(e){alert(e);}
	}
}

function submitallpaiddata(action){
	if(action=="ReCalculate"){
		  if(<s:property value="espdStatus"/>!=1||<s:property value="espdStatus"/>!=2){
		      var year=document.getElementById('inputyear').value;
		      var month=document.getElementById('inputmonth').value;
		      var counts=document.getElementById("compaplanCounts").value;
		      var beneAddCount = document.getElementById("beneAddtionalCount").value;
		      var tempStr = "您确定要重新初始化"+year+"年"+month+"月薪资发放数据吗？";
		      if(counts > 0){
		          tempStr += "有"+counts+"条调薪计划，";
		      }
		      if(beneAddCount > 0){
		          tempStr += "有"+beneAddCount+"条社保补缴记录，";
		      }
		      if (!confirm(tempStr+"原薪资发放数据将丢失！")){ 
		          return;
		      }
		 	  document.searchSalaryPaid.action="initPaid.action";
		 	  document.searchSalaryPaid.submit();
		  }
		return;
	}
	document.getElementById('searchSalaryPaid').action='viewAllSalarypaid.action';
	document.getElementById('searchSalaryPaid').submit();
}

function reopenSalaryPay(year, month){
	if(!confirm("确定要解封"+year+"年"+month+"月"+"的薪资发放吗？")){
		return;
	}
	document.getElementById('btnsubmit').disabled = true;
	document.getElementById('searchSalaryPaid').action='approve.action?status=0';
	document.getElementById('searchOrExport').value='';
	document.getElementById('searchSalaryPaid').submit();	
}

function submitSalaryForPaid(){
	var rowId = document.getElementById("div_rowIDPay").value;
	var espId = document.getElementById('salarypayID'+rowId).value;
	var empId = document.getElementById("empid"+rowId).value;
	var empName = document.getElementById('empName'+rowId).value;
	if(!confirm("确定要修改"+empName+"的薪资吗？")){
		return;
	}
	
	//找到薪资发放弹出层的所有输入框
	var eles = document.getElementsByName("espColumn");
	var salary = "";
	for(var i = 0 ;i<eles.length;i++){
		if(isNaN(eles[i].value)){
			alert("薪资项只能输入数字！");
			eles[i].focus();
			return;
		}
		if(eles[i].value>=1000000000){
			alert("薪资项不能超过9位整数！");
			eles[i].focus();
			return;
		}
		if(eles[i].value!=""){
			salary = salary + eles[i].value + ",";
		}else{
			salary = salary + "0.00" + ",";
		}
	}

	var yearmonth = $('#thisyear').val() + $('#thismonth').val();
	var comment = document.getElementById("div_espComments").value.trim();

	DWRforSalaryPaid.updatePayOne(salary,espId,empId,comment,yearmonth,updateSalaryPaycallback);
	function updateSalaryPaycallback(msg){
		try{
			var retcode = HRMCommon.alertMsgHandler(msg);
			if(retcode == 'SUCC') {
				successMsg("errMsg", "调整"+empName+"的薪资发放成功。"); //成功更新员工薪资！
				
				var data = msg.substring(5); // 截取成功信息（去掉前缀“SUCC:”）
				var money = data.split(",");
				document.getElementById("Basesalary_span"+rowId).innerHTML = Number(money[0]).toFixed(2);
				document.getElementById("td_esac"+rowId).innerHTML = document.getElementById("espEsacName"+rowId).value;
				document.getElementById("empGrossamt"+rowId).innerHTML = Number(money[3]).toFixed(2);
				document.getElementById("benefitValue_span"+rowId).innerHTML = Number(money[4]).toFixed(2);
				document.getElementById("empTaxamt"+rowId).innerHTML = Number(money[5]).toFixed(2);
				document.getElementById("empNetamt"+rowId).innerHTML = Number(money[6]).toFixed(2);
				document.getElementById("espComment"+rowId).value = comment;
				//重新设置薪资发放的id；
				document.getElementById('salarypayID'+rowId).value = money[8];
				
				// 新增薪资发放时添加社保查看图标；
				if(money[9] > 0){// 当本月有社保缴费记录时才显示查看按钮；
				    var beneMirror = "<img id=\"benesearchImg"+rowId+"\" src=\"../resource/images/Search.gif\" ";
					beneMirror += " alt=\"查看详细\" onclick=\"showBeneModifyDiv('"+rowId+"');\" "; 
					beneMirror += " border=\"0\" style=\"CURSOR:pointer\">";
					document.getElementById("benefitMirror_span"+rowId).innerHTML = beneMirror;
				}else{
				    document.getElementById("benefitMirror_span"+rowId).innerHTML = "";
				}
				
				var string = "<a href=\"#\" onclick=\"myclearSalary(";
				string = string + "'" + rowId + "',";
				string = string + "'" + empName + "',";
				string = string + "'" + money[8] + "'";
				string = string + ");\"><img src=\"../resource/images/deletesalaryconf.gif\" alt='删除'  border='0'/></a>";
				document.getElementById("empOperation"+rowId).innerHTML = string;
				// 显示查看薪资小图标颜色
				if(money[7]=="1"){
					document.getElementById("searchImg"+rowId).src = '../resource/images/Search_change.gif';
				}else if(money[7]=="0"){
					document.getElementById("searchImg"+rowId).src = '../resource/images/Search.gif';
				}
				// 显示查看社保小图标颜色
				if(money[9]>1){
					document.getElementById("benesearchImg"+rowId).src = '../resource/images/Search_change.gif';
				}else {
					document.getElementById("benesearchImg"+rowId).src = '../resource/images/Search.gif';
				}
				HRMCommon.closeDialog('dlg_emp_salary_pay_div');
			}
		}catch(e){alert(e);}
	}
}

//薪资发放层（放大镜查看）
var acctItemLength;
function myshowconfigDiv(index){
	var payId = document.getElementById("salarypayID"+index).value;
	var empId = document.getElementById("empid"+index).value;
	var empName=document.getElementById('empName'+index).value;
	var espEsavId = document.getElementById("escEsavId"+index).value;
	var acctName = document.getElementById("espEsacName" + index).value;
	document.getElementById("div_rowIDPay").value = index;
	var yearmonth = $('#inputyear').val() + $('#inputmonth').val();

	// 获得员工薪资帐套的名称
	if(acctName==null || acctName==""){
	    DWRforSalaryPaid.getEsacNameByVersionId(espEsavId,acctnamecallBack);
	}
	function acctnamecallBack(data){
		acctName = data;
	}

	// 保留备注，删除所有的薪资项目
	document.getElementById("div_espComments").value = document.getElementById("espComment"+index).value.trim();
	for(var i=0;i<acctItemLength;i++) {
		document.getElementById('acctItems'+i).parentNode.removeChild(document.getElementById('acctItems'+i));
	}

	// 调用dwr程序，获得所有薪资项目参数和值
	DWRforSalaryPaid.initCalcPay(payId, empId, yearmonth, callBack);
	function callBack(msg) {
		if (msg == null) return; // 异常处理，如权限或状态错误
		var trObj;
		acctItemLength = msg.length/2;
		
		for(var i=0;i<msg.length;i++) {
			if(i%2==0){
				trObj = document.getElementById('newConfigTablePay').insertRow(i/2);
				trObj.id = 'acctItems'+(i/2);
				trObj.name = 'div_tr';
			}
			
			var toWrite="onkeydown='HRMCommon.checkOnKeyDownFloat(event)'";
			if(msg[i].esaiDataIsCalc == 2) {
				toWrite="readOnly class='nothinginput'";
			}
			if(<s:property value="espdStatus"/>==2){
				toWrite="readOnly class='nothinginput'";
			}
			if(msg[i].itemChanged == 1) {
				toWrite = toWrite + " style='color:red;text-align:right' ";
			}else{
				toWrite = toWrite + " style='text-align:right'";
			}

			tdObj = document.createElement('td');
			tdObj.align='left';
			tdObj.height='27px';
			tdObj.nowrap='true';
			tdObj.innerHTML="<label for='empsalaryconfig.escColumn"+(i+1)+"'>"+msg[i].esaiEsdd.esddName+":</label>";
			trObj.appendChild(tdObj);
			tdObj = document.createElement('td');
			tdObj.align='left';
			tdObj.height='27px';
			tdObj.innerHTML="<input type='text' "+toWrite+" name='espColumn' id='espColumn"+(i+1) + "' " + "value='" + msg[i].itemConfigValue.toFixed(2) + "'" + " size='9'/>";
			trObj.appendChild(tdObj);
			if(i==msg.length-1&&msg.length%2==1){
				tdObj = document.createElement('td');
				tdObj.height='27px';
				trObj.appendChild(tdObj);
				tdObj = document.createElement('td');
				tdObj.height='27px';
				trObj.appendChild(tdObj);
			}
		}
		$('#dlg_emp_salary_pay_div').dialog("option", "title", empName+"的薪资发放信息—"+acctName+"("+yearmonth+")");
		HRMCommon.openDialog('dlg_emp_salary_pay_div');
	}
}

//薪资配置层（查看）
var configacctItemLength;
function showEmpSalaryConfigDiv(index){
	var size= document.getElementById("listsize").value;
	var empname=document.getElementById('empName'+index).value;
	var configId = document.getElementById("empid"+index).value;
	var escEsavId = document.getElementById("escEsavId"+index).value;
	var acctName = "";
	document.getElementById("div_rowID").value = index;
	
	DWRforSalaryPaid.getEsacNameByVersionId(escEsavId,namecallBack);
	function namecallBack(data){
		acctName = data;
	}

	for(var i=0;i<configacctItemLength;i++) {
		document.getElementById('configacctItems'+i).parentNode.removeChild(document.getElementById('configacctItems'+i));
	}
	
	AddCompaplan.getConfigItemsById(configId,configCallBack);
	function configCallBack(msg) {
		if(msg==null){
	  		alert("无薪资配置或薪资配置已被删除，请刷新后重试！");
	  		return;
	  	}
		var trObj;
		configacctItemLength = msg.length/2;
		
		var toWrite="readOnly class='nothinginput' style='text-align:right'";
		
		for(var i=0;i<msg.length;i++) {
			if(i%2==0){
				trObj = document.getElementById('empSalaryConfigTable').insertRow(i/2);
				trObj.id = 'configacctItems'+(i/2);
				trObj.name = 'div_tr';
			}
			
			tdObj = document.createElement('td');
			tdObj.align='left';
			tdObj.height='27px';
			tdObj.nowrap='true';
			tdObj.innerHTML="<label for='empsalaryconfig.escColumn"+(i+1)+"'>"+msg[i].esaiEsdd.esddName+":</label>";
			trObj.appendChild(tdObj);
			tdObj = document.createElement('td');
			tdObj.align='left';
			tdObj.height='27px';
			tdObj.innerHTML="<input type='text' "+toWrite+" name='escColumn' id='escColumn"+(i+1) + "' " + "value='" + msg[i].itemConfigValue.toFixed(2) + "'" + "size='9'/>";
			trObj.appendChild(tdObj);
			if(i==msg.length-1&&msg.length%2==1){
				tdObj = document.createElement('td');
				tdObj.height='27px';
				trObj.appendChild(tdObj);
				tdObj = document.createElement('td');
				tdObj.height='27px';
				trObj.appendChild(tdObj);
			}
		}
		$('#dlg_emp_salary_config_div').dialog("option", "title", empname+"的薪资配置信息—"+acctName);
		HRMCommon.openDialog('dlg_emp_salary_config_div');
	}
}

//重新计算薪资按钮处理逻辑
function viewNewSalaryPaid(){
	var rowId = document.getElementById("div_rowIDPay").value;
	var espId = document.getElementById('salarypayID'+rowId).value;
	var empId = document.getElementById("empid"+rowId).value;
	var empName = document.getElementById('empName'+rowId).value;

	var eles = document.getElementsByName("espColumn");
	var salary = "";
	for(var i = 0 ;i<eles.length;i++){
		if(isNaN(eles[i].value)){
			alert("薪资项只能输入数字！");
			eles[i].focus();
			return;
		}
		if(eles[i].value!=""){
			salary = salary + eles[i].value + ",";
		}else{
			salary = salary + "0.00" + ",";
		}
	}

	var yearmonth = $('#thisyear').val() + $('#thismonth').val();
	var comment = document.getElementById("div_espComments").value.trim();
	//重新计算薪资DWR;
	DWRforSalaryPaid.reCalcPayOne(salary,espId,empId,yearmonth,preCalPayItemscallback);
	function preCalPayItemscallback(data){
		if(data==null){
			alert("数据异常，请刷新后重试！");
		}else{
			for(var i=0;i<data.length;i++){
				document.getElementById("espColumn"+(i+1)).value = data[i].itemConfigValue.toFixed(2);
			}
		}
	}
}

//重置按钮
function resetSalaryPaid(){
	var rowId = document.getElementById("div_rowIDPay").value;
	var configId = document.getElementById("empid"+rowId).value;
	AddCompaplan.getConfigItemsById(configId,payItemscallBack);
	function payItemscallBack(data){
		if(data==null){
			alert("数据异常，请刷新后重试！");
		}else{
			for(var i=0;i<data.length;i++){
				document.getElementById("espColumn"+(i+1)).value = data[i].itemConfigValue.toFixed(2);
			}
		}
	}
}

//删除某员工的薪资发放记录，输入参数：当前行号，员工姓名，薪资发放记录id
function myclearSalary(index,empName,id){
	if(!confirm("您确实要删除" + empName + "的薪资发放数据吗？")){
		return false;
	}
	DWRforSalaryPaid.deleteSalaryPaid(id,deleteCompaplancallback);
	function deleteCompaplancallback(msg){
		try{
			var retcode = HRMCommon.comboMsgHandler(msg);
			if(retcode == 'SUCC') {
				document.getElementById('Basesalary_span'+index).innerHTML = "";
				document.getElementById("td_esac"+index).innerHTML = "";
				document.getElementById('empGrossamt'+index).innerHTML = "";
				document.getElementById('benefitValue_span'+index).innerHTML = "";
				document.getElementById('benefitMirror_span'+index).innerHTML = "";
				document.getElementById('empTaxamt'+index).innerHTML = "";
				document.getElementById('empNetamt'+index).innerHTML = "";
				document.getElementById('empOperation'+index).innerHTML = "";
				document.getElementById('salarypayID'+index).value = "";
				document.getElementById('searchImg'+index).src = '../resource/images/Search.gif';
			}
		}catch(e){alert(e);}
	}
}

function emailtSalary(){
	document.getElementById('yearmonth').value = document.getElementById('inputyear').value+document.getElementById('inputmonth').value;
	empSelector_searchEmp();
	var tempStr = document.getElementById('thisyear').value+"年"+document.getElementById('thismonth').value+"月薪资邮件通知";
   $('#dlgEmailSalary').dialog("option","title",tempStr);
    HRMCommon.openDialog('dlgEmailSalary');
    document.getElementById('btnEmail').disabled='';
}
</script>
<script type="text/javascript">
   
   /************************************
   ** 批量发送邮件
   *************************************/
   function sendEmail(){
       var select = empSelector_choseEmpIdStr();
       if(select.trim().length == 0){
           errMsg("errMsg","请至少选择一个员工！");
           return;
       }
       var len = select.split(";").length;
       if(!confirm('您本次共选择了'+len+'名员工，是否确认发送？')){
           return;
       }
       document.getElementById('btnEmail').disabled='disabled';
       var year = document.getElementById('thisyear').value;
       var month = document.getElementById('thismonth').value;
       var ids = select.split(";");
       emailSalary.sendEmail(ids,year,month,callback);
       function callback(data){
           var size = data.length;
           if(size ==0){
               successMsg("errMsg","邮件发送成功。");
           }else{
               var error = "";
               for(var i = 0;i < size; i++){
                   error += data[i]+"&nbsp;";
               }
               errMsg("errMsg","发送邮件失败："+error);
           }
          HRMCommon.closeDialog('dlgEmailSalary');
       }
   }
   function enterDown(event){    
       event = event ? event : (window.event ? window.event : null);
       if(event!=null && event.keyCode==13){
           searchEmp(document.getElementById('emp_sear_value').value);  
           return;                              
       }
   }
   function doExport(exportName){
   	document.getElementById('searchOrExport').value=exportName;
   	document.getElementById('searchSalaryPaid').submit();
   }
HRMCommon.initDialog('dlg_emp_salary_pay_div');
HRMCommon.initDialog('dlg_emp_benefit_div');
HRMCommon.initDialog('dlg_updateBatch_div');
HRMCommon.initDialog('dlg_emp_salary_config_div');
HRMCommon.initDialog('dlgEmailSalary');
</script>
<jsp:include flush="true" page="search_salary_paid_benediv.jsp"></jsp:include>
<jsp:include flush="true" page="../io/div_omm_select.jsp"></jsp:include>
<jsp:include flush="true" page="../io/div_upload.jsp"></jsp:include>
</body>
