<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ taglib prefix="s" uri="webwork"%>
<%@taglib prefix="ty" uri="/tengsource"%><head>
<title>员工社保补缴</title>
<link href="../resource/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../dwr/interface/SearchEbpAction.js"></script>
<script type="text/javascript" src="../resource/js/hrm/compensation.js"></script>
</head>
<body onload="HRMCommon.check_order();">
	<s:component template="bodyhead">
		<s:param name="pagetitle" value="'员工社保补缴('+yearMonth+')'" />
	</s:component>
	<span class="errorMessage" id="errMsg"></span>
	<s:form action="searchEbp" id="searchEbpForm" name="searchEbpForm" namespace="/compensation" method="post">
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
								<s:select label="社保种类" id="empBenefitTypeAdv"
									name="emp.empBenefitType.id" list="ebfTypeList"
									listKey="id" listValue="benefitTypeName" multiple="false"
									headerKey="" headerValue="请选择" />
							</tr>
					</table>
				</td>
				<td align="center">
					<input title="[Alt+F]" accesskey="F" id="submit_button"
						class="button" type="button" style="CURSOR: pointer" onclick="submitForm();" value="查询">
					<input title="[Alt+C]" accesskey="C" class="button" type="button"
						value="重置" onClick="window.location='searchEbp.action';">
				</td>
			</tr>
		</table>
		<table width="100%">
		  <tr>
		    <br/>
		  </tr>
		  <tr>
			<td align="left">
				<input type="button" class="button" id="down" name="down" value="前一月" onclick="gotoMonth(-1);">
		        <input type="text" id="yearMonth" name="yearMonth" value="<s:property value="yearMonth" />" size="8" maxlength="8" style="text-align:center"/>
		        <input type="button" class="button" id="up" name="up" value="后一月" onclick="gotoMonth(1);">
		        <s:if test="canEdit=='true'">
		            <input type="button" class="button" id="showAdd" name="showAdd" value="新增" onclick="showAddDiv();"/>
		        </s:if>
		        <s:else>
		            <input type="button" class="button" id="showAdd" name="showAdd" value="新增" onclick="showAddDiv();" disabled="disabled"/>
		        </s:else>
			</td>
			<td align="right">本次查询共得到<s:property value="page.totalRows" />条补缴记录&nbsp;</td>
		  </tr>
		</table>

		<table id="empbenefittable" cellpadding="0" cellspacing="0"
			width="100%" border="0" class="gridtableList">
			<tr>
				<th nowrap="nowrap" align="center">
					<a href="#" onclick="HRMCommon.order_submit('emp.empDistinctNo','searchEbpForm');">员工编号</a><img src='../resource/images/arrow_.gif' width='8' height='10'
						id='emp.empDistinctNo_img'>
				</th>
				<th nowrap="nowrap" align="center">
					<a href="#" onclick="HRMCommon.order_submit('emp.empName','searchEbpForm');">姓名</a><img src='../resource/images/arrow_.gif' width='8' height='10'id='emp.empName_img'>
				</th>
				<th nowrap="nowrap" align="center">
					<a href="#"
						onclick="HRMCommon.order_submit('ebpBelongYearmonth','searchEbpForm');">补缴年月</a><img src='../resource/images/arrow_.gif' width='8' height='10'
						id='ebpBelongYearmonth_img'>
				</th>
				<th nowrap="nowrap" align="center">
					<a href="#" onclick="HRMCommon.order_submit('beneType.benefitTypeName','searchEbpForm');">社保种类</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='beneType.benefitTypeName_img'>
				</th>
				<th nowrap="nowrap" align="center">
					<a href="#" onclick="HRMCommon.order_submit('ebpPensionAmountb','searchEbpForm');">社保基数</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='ebpPensionAmountb_img'>
				</th>
				<th nowrap="nowrap" align="center">
					<a href="#" onclick="HRMCommon.order_submit('ebpHousingAmountb','searchEbpForm');">公积金基数</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='ebpHousingAmountb_img'>
				</th>
				<th nowrap="nowrap" align="center">
					<a href="#" onclick="HRMCommon.order_submit('ebpInsuranceAmountb','searchEbpForm');">其他基数</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='ebpInsuranceAmountb_img'>
				</th>
				<th nowrap="nowrap" align="center">
					个人缴社保
				</th>
				<th nowrap="nowrap" align="center">
					公司缴社保
				</th>
				<th nowrap="nowrap" align="center">
				    备注
				</th>
				<th nowrap="nowrap" align="center">
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
								title="<s:if test="ebpEmpno.empIdentificationType==0"><s:property value="ebpEmpno.empIdentificationNo"/>;</s:if><s:else>无身份证号;</s:else><s:property value="getEmpStatusName(ebpEmpno.empStatus)"/>;<s:if test="ebpEmpno.empLocationNo!= null && ebpEmpno.empLocationNo.id != ''"><s:property value="ebpEmpno.empLocationNo.locationName"/>;</s:if><s:else>无工作地区;</s:else> <s:property value="ebpEmpno.empDeptNo.departmentName"/>" />
								<s:property value="ebpEmpno.empDistinctNo" />
						</td>
						<td nowrap="nowrap" id="ebpEmpno.empName<s:property value='ebpId'/>" align="center">
							<s:property value="ebpEmpno.empName" />
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
						    <s:if test="canEdit=='true'">
							<a href="#" onclick="getEbpById(this, '<s:property value="ebpId" />', '<s:property value="ebpEmpno.empName" />', '<s:property value="ebpBelongYearmonth" />');"><img
											src="../resource/images/edit.gif" alt="修改"></img></a>
							<a href="#" onclick="deleteOneEbp('<s:property value='ebpId'/>', '<s:property value="ebpBelongYearmonth" />');">
								    <img src="../resource/images/delete.gif" alt="删除"></img></a>
							</s:if>
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

	<div id="dlgAddEmpBenefit" title="社保补缴"
		style="width: 470;display:none;">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td>
					<div id="remark_hand_empbenefit" style="CURSOR: move;"
						class="prompt_div_head">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<TR>
								<td>
									<h3>
										<span id="div_head"></span>
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
						<table width="100%" class="prompt_div_body" cellpadding="0" cellspacing="0" width="100%" border="0">
							<tr>
								<td align="left" colspan="3">
								    <input type="hidden" id="empId" name="empId" />
									<label class="label" for="empName">员工:</label>
									<input type="text" readonly="true" id="empName" name="empName" value="<s:property value='empName'/>"/>
			                        <img src="../resource/images/search_icon.gif" style="CURSOR: pointer" onclick="showChooseEmpDiv(2,1);" alt='点击图标选择员工'/>
								</td>
								<td align="left" colspan="2">
								    缺省基数:<s:select id="defaultType"
										name="defaultType" list="#{0:'当前月', 1:'补缴月'}" 
										multiple="false" emptyOption="false" onchange="changeDefaultType(this.value);"/>
								</td>
							</tr>
							<tr>
							    <td align="center"> 补缴年月： </td>
							    <td align="center"> 社保基数： </td>
							    <td align="center"> 公积金基数： </td>
							    <td align="center"> 其他基数： </td>
							    <td align="center"> 备注： </td>
							</tr>
							<%for(int i=1; i<=6; i++) {%>
							<tr>
							    <td>
							        <input type="text" id="addyearmonth<%=i%>" name="addyearmonth<%=i%>" 
							              onblur="getDefaultData(<%=i%>, this.value);" value="" size="8" maxlength="8"/>
							    </td>
								<td>
									<input id="ebpPensionAmount<%=i%>" name="ebpPensionAmount<%=i%>"
										onkeydown='HRMCommon.checkOnKeyDownFloat(event)' value="" size="9" maxlength="12"/>
								</td>
								<td>
									<input id="ebpHousingAmount<%=i%>" name="ebpHousingAmount<%=i%>"
										onkeydown='HRMCommon.checkOnKeyDownFloat(event)' value=""  size="9" maxlength="12"/>
								</td>
								<td>
									<input id="ebpInsuranceAmount<%=i%>" name="ebpInsuranceAmount<%=i%>"
										onkeydown='HRMCommon.checkOnKeyDownFloat(event)' value=""  size="9" maxlength="12"/>
							    </td>
							    <td>
							        <input id="memo<%=i%>" name="memo<%=i%>" size="25" maxlength="128" value=""/>
							    </td>
							</tr>
							<%} %>
							<tr>
								<td colspan="5"><font style="color:red">*</font>注：日期"200908"表示"2009年8月"。</td>
							</tr>
							<tr>
								<td colspan="5" align="center">
									<input id="ebpAddbtn" type="button" value="保存" onclick="submitAddEbp();" />
									&nbsp;&nbsp;&nbsp;
									<input type="button" value="取消"
										onclick="HRMCommon.closeDialog('dlgAddEmpBenefit');" />
								</td>
							</tr>
						</table>
					</s:form>
				</td>
			</tr>
		</table>
		
	</div>

	</s:form>

	<!-- modify empbenefit -->
	<div id="dlgModifyEmpBenefit"
		style="width: 390;display:none">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td>
					<div id="modify_remark_hand_empbenefit" style="CURSOR: move;"
						class="prompt_div_head">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<h3>
										<span id="div_modify"></span>
									</h3>
								</td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
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
									<input readonly="readonly" type="text" id="modiaddyearmonth" name="modiaddyearmonth" value="" size="8" maxlength="8"/>
								</td>
							</tr>
							<tr>
								<td align="right">备注：</td>
								<td>
								    <textarea id="modibeneComments" cols="26" rows="2" maxlength="127"> 
                                    </textarea>
								</td>
							</tr>
							<tr>
								<td colspan="2" align="center">
									<input id="ebfAddbtn" type="button" value="修改" onClick="modifyEbpSubmit();" />
									&nbsp;&nbsp;&nbsp;
									<input type="button" value="取消" onclick="HRMCommon.closeDialog('dlgModifyEmpBenefit');" />
								</td>
							</tr>
						</table>
				</td>
			</tr>
		</table>
		<iframe scrolling="no" style="position:absolute;z-index:-1;height:1px;width:389px;top:0px;left:0px;" frameborder="0"></iframe>
	</div>
<script type="text/javascript">

// 鼠标离开填写月份的文本框后将默认基数值显示在相应文本框中；
function getDefaultData(index, yearMonthi){
    var empId = document.getElementById("empId").value;
    var yearMonth = document.getElementById("yearMonth").value;
    yearMonth = yearMonth.substring(0,4) + "" + yearMonth.substring(5,7);
    if(!checkDate2(yearMonthi)){
        return;
    }
    var defaultType = document.getElementById("defaultType").value;
    var selectMonth;
    if(defaultType == 0){
        selectMonth = yearMonth;
    }else if(defaultType == 1){
        selectMonth = yearMonthi;
    }
    SearchEbpAction.getEbpByEmpMonth(empId, selectMonth, getDefaultCallback);
    function getDefaultCallback(data){
        document.getElementById("ebpPensionAmount"+index).value = data!=null&&data.ebpPensionAmountb!=null?data.ebpPensionAmountb:"";
        document.getElementById("ebpHousingAmount"+index).value = data!=null&&data.ebpHousingAmountb!=null?data.ebpHousingAmountb:"";
        document.getElementById("ebpInsuranceAmount"+index).value = data!=null&&data.ebpInsuranceAmountb!=null?data.ebpInsuranceAmountb:"";
    }
}

// 默认类型改变，相应基数值改变；
function changeDefaultType(type){
    var yearMonth = document.getElementById("yearMonth").value;
    yearMonth = yearMonth.substring(0,4) + "" + yearMonth.substring(5,7);

    for(var i=1; i<=6; i++){
        var yearMonthi = document.getElementById("addyearmonth" + i).value;
        if(!checkDate2(yearMonthi)){
	        return;
	    }
	    if(yearMonthi!=null && yearMonthi.trim()!=""){// 月份不为空；
	        if(type == 0){// 当前月；
	        	yearMonthi = yearMonth;
	    	}
	        getDefaultData(i, yearMonthi);
	    }
    }
}



function submitForm(){
    var yearMonth = document.getElementById("yearMonth").value;
    if(!checkDate1(yearMonth)){
        return;
    }
    document.forms[0].submit();
}

// 转到上一月或者下一月；
function gotoMonth(value){
	var yearMonth = document.getElementById("yearMonth").value;
	document.getElementById("yearMonth").value = MonthAddDiff(yearMonth, value);
	document.getElementById("up").disabled = true;
	document.getElementById("down").disabled = true;
	// document.getElementById('searchOrExport').value = "";
	document.forms[0].submit();
}

function MonthAddDiff(yearMonth, diff){
    var year = yearMonth.split("-")[0];
    var month = yearMonth.split("-")[1];
    year = year*1;
    month = month*1;
    // alert(month);
	if(month==1 && diff==-1){
		month = 12;
		year = year - 1;
	}else if(month==12 && diff==1){
		month = 1;
		year = year + 1;
	}else{
		month = month + diff;
	}
	if(month < 10){
	    return year + '-'+ '0'+month;
	}
	
	return year + '-' + month;
}

// 显示社保补缴页面；
function showAddDiv(){
    HRMCommon.openDialog("dlgAddEmpBenefit","searchEbpForm");
    document.getElementById("empName").value = "" ;
    for(var i=1; i<=6; i++){
        document.getElementById("addyearmonth" + i).value = "" ;
	    document.getElementById("ebpPensionAmount" + i).value = "" ;
	    document.getElementById("ebpHousingAmount" + i).value = "" ;
	    document.getElementById("ebpInsuranceAmount" + i).value = "" ;
	    document.getElementById("memo" + i).value = "" ;
    }
}

// 添加提交；
function submitAddEbp(){
    var empId = document.getElementById("empId").value;
    var yearMonth = document.getElementById("yearMonth").value;
    var addyearMonth = '';
    if(empId==null || empId==''){
        alert("请选择员工！");
        return;
    }
    
    var objList = new Array();
    for(var i=1; i<=6; i++){
        var addyearmonth = document.getElementById("addyearmonth"+i).value.trim();
	    var ebpPensionAmount = document.getElementById("ebpPensionAmount"+i).value.trim();
	    var ebpHousingAmount = document.getElementById("ebpHousingAmount"+i).value.trim();
	    var ebpInsuranceAmount = document.getElementById("ebpInsuranceAmount"+i).value.trim();
	    var memo = document.getElementById("memo"+i).value.trim();
	    var obj=null;
	    if(addyearmonth!='' && (ebpPensionAmount!='' || ebpHousingAmount!='' || ebpInsuranceAmount!='')){
	        addyearMonth += addyearmonth + ',';
	        obj = {ebpBelongYearmonth:addyearmonth, ebpPensionAmountb:ebpPensionAmount, 
	        ebpHousingAmountb:ebpHousingAmount, ebpInsuranceAmountb:ebpInsuranceAmount, ebpComments:memo};
	    }
	    if(obj!=null){
	        objList[i] = obj;
	    }
    }
    if(objList.length == 0){
        alert("请将数据填写完整后再保存！");
        return ;
    }
    // 检查输入年月
    for(var i=0; i<objList.length; i++){
        if(objList[i]!=null && objList[i]!=undefined && !checkDate2(objList[i].ebpBelongYearmonth)){
            return;
        }
    }
    SearchEbpAction.checkAddData(empId, yearMonth, addyearMonth, addCallback);
}

// 社保补缴回调函数；
function addCallback(info){
    if(info == 'succ'){
        document.forms[0].action = "saveBeneAddData.action";
        HRMCommon.closeDialog("dlgAddEmpBenefit");
        document.forms[0].submit();
    }else{
        alert(info);
        return ;
    }
}

// 删除一条补缴记录；
function deleteOneEbp(ebpId, byearMonth){
    if(!confirm("您确定要删除该记录吗？")){
        return;
    }
    document.getElementById("ebpId").value = ebpId;
    document.forms[0].action = "deleteOneEbp.action";
    document.forms[0].submit();
}

// 显示修改页面；
var trObj;
var insertRowCount = 0;
function getEbpById(obj, ebpId, empName, byearMonth){
     trObj = obj.parentNode.parentNode;
     document.getElementById("ebpId_modify").value = ebpId;
    //document.getElementById("div_modify").innerHTML = empName;
     $('#dlgModifyEmpBenefit').dialog("option","title","社保补缴修改"+empName);
     SearchEbpAction.getEbpItemsById(ebpId, getByIdCallback);
     function getByIdCallback(data){
         if(data == null){
             alert("没有权限，或数据出错！");
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
			 tdObj.innerHTML="<input type='text' "+toWrite+" name='ebpColumn' id='ebpColumn"+ itemObj.esaiDataSeq + "' " + "value='" + itemObj.itemValue.toFixed(2) + "'" + " size='12' maxlength='12'/>";
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
         HRMCommon.openDialog("dlgModifyEmpBenefit");
     }
}

// 提交修改；
function modifyEbpSubmit(){
    var ebpId = document.getElementById("ebpId_modify").value;// 要修改的社保补缴数据的id；
    //找到薪资发放弹出层的所有输入框
	var eles = document.getElementsByName("ebpColumn");
	var columns = "";
	for(var i = 1 ;i<=eles.length;i++){
	    var obj = document.getElementById("ebpColumn" + i);
		if(isNaN(obj.value)){
			alert("薪资项只能输入数字！");
			obj.focus();
			return;
		}
		if(obj.value>=1000000000){
			alert("薪资项不能超过9位整数！");
			obj.focus();
			return;
		}
		if(obj.value!=""){
			columns += obj.value + ",";
		}else{
			columns += "0.00" + ",";
		}
	}
	
	// alert(columns);
    var modiComments = document.getElementById("modibeneComments").value;
    SearchEbpAction.modifyEbp(ebpId, columns, modiComments, modifyCallback);
    function modifyCallback(info){
         if(info==null){
             alert("没有权限！");
             return;
         }
         
        HRMCommon.closeDialog("dlgModifyEmpBenefit");
         var tds = trObj.cells;
         tds[7].innerHTML = info.showColumn15;
         tds[8].innerHTML = info.showColumn16;
         tds[9].innerHTML = info.ebpComments;
         var yearMonth = document.getElementById("modiaddyearmonth").value;
         document.getElementById("ebpComments" + ebpId).value = modiComments;
         yearMonth = yearMonth.substring(0,4)+"年"+yearMonth.substring(4)+"月";
         successMsg("errMsg", "您已修改" + tds[1].innerHTML + " 的 " + yearMonth + "社保，改动将在重新初始化薪资后生效。");
    }
}

// 检查'yyyy-MM'格式；
function checkDate1(date){
    if(date==null || date=="") return;
    var regExp = /^(\d{4})\-(\d{2})$/;
    if(!regExp.test(date)){
        alert("查询日期 "+date+" 输入不正确，格式应如：2009-08, 请重新输入!");
        return false;
    }
    return true;
}

// 检查'yyyyMM'格式；
function checkDate2(date){
    if(date==null || date.trim()=="") return;
    var regExp = /^(\d{4})(\d{2})$/;
    if(!regExp.test(date)){
        alert("补缴年月 "+date+" 输入不正确，格式应如：200908, 请重新输入!");
        return false;
    }
    return true;
}

//判断小数点及整数部分的长度
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


HRMCommon.initDialog('dlgAddEmpBenefit');
HRMCommon.initDialog('dlgChooseManager');
HRMCommon.initDialog('dlgModifyEmpBenefit');
</script>
<%@ include file="../profile/search_emp_div.jsp"%>
</body>