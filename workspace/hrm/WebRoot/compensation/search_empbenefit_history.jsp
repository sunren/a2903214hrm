<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ taglib prefix="s" uri="webwork"%>
<%@taglib prefix="ty" uri="/tengsource"%><head>
<title>员工社保缴费历史</title>
<link href="<s:url value="/resource/css/style.css"/>" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../dwr/interface/SearchEbpAction.js"></script>
</head>
<body onload="HRMCommon.check_order();">
	<s:component template="bodyhead">
		<s:param name="pagetitle" value="'员工社保缴费历史('+startYear+startMonth+'-'+endYear+endMonth+')'" />
	</s:component>
	<span class="errorMessage" id="errMsg"></span>
	<s:form action="searchBeneHistory" id="searchEbpForm" name="searchEbpForm" namespace="/compensation" method="post">
		<s:hidden id="output-ommId" name="outmatchModelId"/>
		<s:hidden id="output-ioName" name="outputIoName"/>
		<s:hidden id="ebpId" name="ebpId" />
		<table width="100%" class="formtable">
			<tr>
				<td>
					<s:hidden id="id" name="detailid" />
					<s:hidden id="order" name="page.order" />
					<input type="hidden" id="operate" name="page.operate" />
					<table width="100%" border="0" cellspacing="0" cellpadding="3">
						<tr>
							<s:textfield label="员工" id="empAdv" name="emp.empName"
								size="15" maxlength="64" />
							<td align="right">组织单元:</td>
							<td>
							    <s:orgselector id="orgselector1" name="emp.empDeptNo.departmentName" hiddenFieldName="emp.empDeptNo.id" isShowDisable="show"/>
							</td>
							<s:select label="工作地区" id="empLocationNo"
								name="emp.empLocationNo.id" list="locationList"
								listKey="id" listValue="locationName" multiple="false"
								headerKey="" headerValue="请选择" />
						</tr>
						<tr>
						    <s:select label="选择年月" id="yearMonthCate" name="yearMonthCate" 
						        list="#{'0':'缴费年月', '1':'所属年月'}" emptyOption="false"/>
						    <td align="right">
						        起始年月:
						    </td>
						    <td>
						    <s:select id="startYear" name="startYear" list="years" emptyOption="false" />
							<s:select id="startMonth" name="startMonth" 
								list="#{'01':'01','02':'02','03':'03','04':'04','05':'05','06':'06','07':'07','08':'08','09':'09','10':'10','11':'11','12':'12'}"
								emptyOption="false" />
						    </td>
						    						    <td align="right">
						         结束年月:
						    </td>
						    <td>
						    <s:select id="endYear" name="endYear" list="years" emptyOption="false" />
							<s:select id="endMonth" name="endMonth" 
								list="#{'01':'01','02':'02','03':'03','04':'04','05':'05','06':'06','07':'07','08':'08','09':'09','10':'10','11':'11','12':'12'}"
								emptyOption="false" />
						    </td>
						</tr>
						<tr>
							<s:select label="社保种类" id="empBenefitTypeAdv"
								name="emp.empBenefitType.id" list="ebfTypeList"
								listKey="id" listValue="benefitTypeName" multiple="false"
								headerKey="" headerValue="请选择" />    
						   
						   <s:select label="缴纳种类" id="beneCategory" name="beneCategory" 
						        list="#{'':'请选择', 0:'正常缴纳', 1:'补缴'}" emptyOption="false"/>
						</tr>
					</table>
				</td>
				<td align="center">
					<input title="[Alt+F]" accesskey="F" id="submit_button"
						class="button" type="button" style="CURSOR: pointer" onclick="submitForm();" value="查询">
					<input title="[Alt+C]" accesskey="C" class="button" type="button"
						value="重置" onClick="window.location='searchBeneHistory.action';">
				</td>
			</tr>
		</table>
		<br>
		<table width="100%">
		  <tr>
			<td align="left">
			  <ty:auth auths="201,3 or 201,2">
			    <input type="hidden" name="searchOrExport" id="searchOrExport" />
			    <input type="hidden" name="exportType" id="exportType" />
				<input class="button" type="button" value="导出明细" onClick="exportDetail();" /> 
				<input class="button" type="button" value="导出汇总" onClick="exportSum();" />
			  </ty:auth>
			</td>
			<td align="right">本次查询共得到<s:property value="page.totalRows" />条缴费历史记录&nbsp;</td>
		  </tr>
		</table>

		<table id="empbenefittable" cellpadding="0" cellspacing="0"
			width="100%" border="0" class="gridtableList">
			<tr>
				<th nowrap="nowrap"  align="center">
					<a href="#" onclick="hrm.common.order_submit('emp.empDistinctNo','searchEbpForm');">员工编号</a><img src='../resource/images/arrow_.gif' width='8' height='10'
						id='emp.empDistinctNo_img'>
				</th>
				<th nowrap="nowrap"  align="center">
					<a href="#" onclick="hrm.common.order_submit('emp.empName','searchEbpForm');">姓名</a><img src='../resource/images/arrow_.gif' width='8' height='10'id='emp.empName_img'>
				</th>
				<th nowrap="nowrap"  align="center">
					<a href="#"
						onclick="hrm.common.order_submit('ebpYearMonth','searchEbpForm');">缴费年月</a><img src='../resource/images/arrow_.gif' width='8' height='10'
						id='ebpYearMonth_img'>
				</th>
				<th nowrap="nowrap"  align="center">
					<a href="#"
						onclick="hrm.common.order_submit('ebpBelongYearmonth','searchEbpForm');">所属年月</a><img src='../resource/images/arrow_.gif' width='8' height='10'
						id='ebpBelongYearmonth_img'>
				</th>
				<th nowrap="nowrap"  align="center">
					<a href="#" onclick="hrm.common.order_submit('beneType.benefitTypeName','searchEbpForm');">社保种类</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='beneType.benefitTypeName_img'>
				</th>
				<th nowrap="nowrap"  align="center">
					<a href="#" onclick="hrm.common.order_submit('ebpPensionAmountb','searchEbpForm');">社保基数</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='ebpPensionAmountb_img'>
				</th>
				<th nowrap="nowrap"  align="center">
					<a href="#" onclick="hrm.common.order_submit('ebpHousingAmountb','searchEbpForm');">公积金基数</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='ebpHousingAmountb_img'>
				</th>
				<th nowrap="nowrap"  align="center">
					<a href="#" onclick="hrm.common.order_submit('ebpInsuranceAmountb','searchEbpForm');">其他基数</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='ebpInsuranceAmountb_img'>
				</th>
				<th nowrap="nowrap"  align="center">
					个人缴社保
				</th>
				<th nowrap="nowrap"  align="center">
					公司缴社保
				</th>
				<th nowrap="nowrap"  align="center">
				    备注
				</th>
				<th nowrap="nowrap"  align="center">
					操作
				</th>
			</tr>
			<s:if test="!ebpList.isEmpty()">
				<s:iterator value="ebpList">
				    <input type="hidden" id="ebpBelongYearmonth<s:property value='ebpId'/>" name="ebpBelongYearmonth<s:property value='ebpId'/>" value="<s:property value='ebpBelongYearmonth' />" />
				    <input type="hidden" id="ebpComments<s:property value='ebpId'/>" name="ebpComments<s:property value='ebpId'/>" value="<s:property value='ebpComments' />" />
					<tr id="<s:property value='ebpId'/>">
						<td id="ebpEmpno.empDistinctNo<s:property value='ebpId'/>" align="center">
							<span 
title="在职状态：<s:property value='getEmpStatusName(ebpEmpno.empStatus)'/>
所属地区：<s:property value='ebpEmpno.empLocationNo.locationName'/>
所属部门：<s:property value='ebpEmpno.empDeptNo.departmentName'/>
<s:if test="ebpEmpno.empIdentificationType==0">身份证号：<s:property value='ebpEmpno.empIdentificationNo'/></s:if>" />
							<s:property value="ebpEmpno.empDistinctNo" />
						</td>
						<td id="ebpEmpno.empName<s:property value='ebpId'/>" nowrap="nowrap" align="center">
							<s:property value="ebpEmpno.empName" />
						</td>
						<td id="ebpYearMonth<s:property value='ebpId'/>" nowrap="nowrap" align="center">
						    <s:property value="ebpYearMonth" />
						</td>
						<td id="ebpBelongYearmonth<s:property value='ebpId'/>" nowrap="nowrap" align="center">
							<s:property value="ebpBelongYearmonth" />
						</td>
						<td id="ebpEmpno.empBenefitType<s:property value='ebpId'/>" nowrap="nowrap" align="center">
							<s:property value="ebpEmpno.empBenefitType.benefitTypeName" />
						</td>
						<td id="ebpPensionAmountb<s:property value='ebpId'/>" nowrap="nowrap" align="right">
							<s:property value="ebpPensionAmountb" />
						</td>
						<td id="ebpHousingAmountb<s:property value='ebpId'/>" nowrap="nowrap" align="right">
							<s:property value="ebpHousingAmountb" />
						</td>
						<td id="ebpInsuranceAmountb<s:property value='ebpId'/>" nowrap="nowrap" align="right">
							<s:property value="ebpInsuranceAmountb" />
						</td>
						<td id="showColumn15<s:property value='ebpId'/>" nowrap="nowrap" align="right">
							<s:property value="showColumn15" />
						</td>
						<td id="showColumn16<s:property value='ebpId'/>" nowrap="nowrap" align="right">
							<s:property value="showColumn16" />
						</td>
						<td nowrap="nowrap" align="right">
							<s:property value="ebpComments" />
						</td>
						<td align="right">
							<a href="#" onclick="getEbpById(this, '<s:property value="ebpId" />', '<s:property value="ebpEmpno.empName" />', '<s:property value="ebpBelongYearmonth" />');"><img
											src="../resource/images/Search.gif" alt="修改"></img></a>
						</td>
					</tr>
				</s:iterator>
			</s:if>
			<s:else>
				<tr>
					<!-- 不存在符合条件的员工社保资料！ -->
					<td align="center" colspan="12">
						不存在符合条件的员工社保资料！
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
          	  	    <s:param name="start" value="page.start" /><s:param name="end" value="page.end" /><s:param name="formId" value="'searchEbpForm'" />
          	  	</s:component>
			  </td>
  			</tr>
		</table>

    </s:form>
	<!-- modify empbenefit -->
	<div id="dlgModifyEmpBenefit"
		style="width: 390;"
		class="prompt_div_inline">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
		
			<tr>
				<td>
				        <div id="hiddenColumns" style="display:none"></div>
				        <input type="hidden" id="ebpId_modify" name="ebpId_modify" />
						<table id="beneItems" width="100%" class="prompt_div_body" cellpadding="0" cellspacing="0" width="100%" border="0">
							<tr>
								<td align="right">
									年月：
								</td>
								<td>
									<input readonly="readonly" class="nothinginput" type="text" id="modiaddyearmonth" name="modiaddyearmonth" value=""   size="8" maxlength="8"/>
								</td>
							</tr>
							<tr>
								<td align="right">备注：</td>
								<td>
								    <textarea readonly="readonly" id="modibeneComments" cols="26" rows="2" maxlength="127"> 
                                    </textarea>
								</td>
							</tr>
							<tr>
								<td colspan="2" align="center">
									<input type="button" value="关闭" onclick="HRMCommon.closeDialog('dlgModifyEmpBenefit');" />
								</td>
							</tr>
						</table>
				</td>
			</tr>
		</table>
		<iframe scrolling="no" style="position:absolute;z-index:-1;height:1px;width:389px;top:0px;left:0px;" frameborder="0"></iframe>
	</div>
<script type="text/javascript">
// 提交查询
function submitForm(){
    var startYearMonth = document.getElementById("startYear").value+""+document.getElementById("startMonth").value;
    var endYearMonth = document.getElementById("endYear").value+""+document.getElementById("endMonth").value;
    if(startYearMonth>endYearMonth){
        alert("查询结束年月小于起始年月！");
        return;
    }
    
    document.getElementById("searchOrExport").value = "";
    document.forms[0].submit();
}

// 导出明细；
function exportDetail(){
    var startYearMonth = document.getElementById("startYear").value+""+document.getElementById("startMonth").value;
    var endYearMonth = document.getElementById("endYear").value+""+document.getElementById("endMonth").value;
    if(startYearMonth>endYearMonth){
        alert("查询结束年月小于起始年月！");
        return;
    }
    
    document.getElementById("searchOrExport").value = "export";
    document.getElementById("exportType").value = "all";
    document.forms[0].submit();
}

// 导出汇总；
function exportSum(){
    var startYearMonth = document.getElementById("startYear").value+""+document.getElementById("startMonth").value;
    var endYearMonth = document.getElementById("endYear").value+""+document.getElementById("endMonth").value;
    if(startYearMonth>endYearMonth){
        alert("查询结束年月小于起始年月！");
        return;
    }
    
    document.getElementById("searchOrExport").value = "export";
    var sumType = document.getElementById("yearMonthCate").value;
    if(sumType == 0){
        document.getElementById("exportType").value = "yearMonth";
    }else{
        document.getElementById("exportType").value = "belongYearMonth";
    }
    document.forms[0].submit();
}

// 显示修改页面；
var trObj;
var insertRowCount = 0;
function getEbpById(obj, ebpId, empName, byearMonth){
     trObj = obj.parentNode.parentNode;
     document.getElementById("ebpId_modify").value = ebpId;
     SearchEbpAction.getEbpItemsById(ebpId, getByIdCallback);
     function getByIdCallback(data){
         if(data == null){
             alert("您没有权限，也可能数据已被修改或删除，请刷新后重试！");
         }
         //在beneItems表中显示社保项目，并且全能修改；
         document.getElementById("hiddenColumns").innerHTML = ""; //清空保存隐藏域值得div；
         for(var i=1; i<insertRowCount; i++){// 删除社保项目行；
             document.getElementById('beneItems' + i).parentNode.removeChild(document.getElementById('beneItems' + i));
         }
         var hiddenColumns = "";
         var trObj = null;
         var tdObj = null;
         insertRowCount = 1;
         var itemObj = null;
         for(var i=0; i<data.length; i++){
             itemObj = data[i];
             if(itemObj.esaiEsdd.esddDataType<10 || itemObj.esaiEsdd.esddDataType>16){// 非社保项目值；
                 hiddenColumns += "<input type='hidden' name='ebpColumn' id='ebpColumn"+ itemObj.esaiDataSeq + "' value='"+ (itemObj.itemValue==null?0.00:itemObj.itemValue) +"'/>";
                 continue;
             }
             // 是社保项目值，插入一行，显示并修改；
             trObj = document.getElementById('beneItems').insertRow(insertRowCount);
             trObj.id = 'beneItems'+insertRowCount;
             
             var toWrite="onkeydown='HRMCommon.checkOnKeyDownFloat(event)'";
             tdObj = document.createElement('td');
             tdObj.align='right';
			 tdObj.width='40%';
			 tdObj.height='27px';
			 tdObj.nowrap='true';
			 tdObj.innerHTML=itemObj.esaiEsdd.esddName + ":";
			 trObj.appendChild(tdObj);
			 tdObj = document.createElement('td');
			 tdObj.align='left';
			 tdObj.height='27px';
			 tdObj.width='60%';
			 tdObj.innerHTML="<input readonly type='text' "+toWrite+" name='ebpColumn' id='ebpColumn"+ itemObj.esaiDataSeq + "' " + "value='" + itemObj.itemValue.toFixed(2) + "'" + " size='12' style='text-align:right'/>";
			 trObj.appendChild(tdObj);
			 
             insertRowCount ++;
         }
         
         // 设置不需要修改的值；
         document.getElementById("hiddenColumns").innerHTML = hiddenColumns; 
         // 显示年月，备注；
         var belongyearMonthId = "ebpBelongYearmonth"+ebpId;
         var commentsId = "ebpComments"+ebpId;
         document.getElementById("modibeneComments").value = document.getElementById(commentsId).value;
         document.getElementById("modiaddyearmonth").value = document.getElementById(belongyearMonthId).value;

         $('#dlgModifyEmpBenefit').dialog("option","title","查看"+empName+"的社保缴费历史");
         HRMCommon.openDialog("dlgModifyEmpBenefit");
     }
}

HRMCommon.initDialog('dlgModifyEmpBenefit');
</script>
<jsp:include flush="true" page="../io/div_omm_select.jsp"></jsp:include>
</body>