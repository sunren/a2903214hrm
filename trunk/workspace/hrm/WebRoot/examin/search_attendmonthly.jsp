<%@page pageEncoding="GBK" contentType="text/html; charset=GBK" %>
<%@taglib prefix="s" uri="webwork"%>
<%@taglib prefix="ty" uri="/tengsource"%>
<!--
 =========================================================
' File:search_attenddaily.jsp
' Auth:Yang
' Version:1.1.0 standard version
' Date: 2008-5-12
' Script Written by tengsource.com
'=========================================================
' Copyright   2007 tengsource.com. All rights reserved.
' Web: http://www.tengsource.com
' Email: admin@tengsource.com
'=======================================================
 -->
<head>
	<title>每月考勤汇总</title>
	<jsp:include flush="true" page="/sitemesh/jsPackage.jsp"></jsp:include>	
	<script type='text/javascript' src='../dwr/interface/DwrForAttend.js'></script>
</head>
<body onload="hrm.common.check_order();">
<s:component template="bodyhead">
	<s:param name="pagetitle"  value="'每月考勤汇总('+startDate+'到'+endDate+')'" />
	<s:param name="helpUrl" value="'26'" />
</s:component>

<s:form id="searchAttendmonthly" name="searchAttendmonthly" action="searchAttendmonthly" namespace="/examin" method="POST">
<s:token></s:token>
<input type="hidden" id="status" value="<s:property value='status'/>"/>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="formtable">
  <tr>
    <td>
	  <table width="100%" border="0" cellspacing="2" cellpadding="0">
		<tr>
		    <s:hidden id="output-ommId" name="outmatchModelId"/>
	        <s:hidden id="output-ioName" name="outputIoName"/>
			<s:hidden id="delAttendId" name="delAttendId" />
			<s:hidden id="order" name="page.order"/>
			<input type="hidden" id="operate" name="page.operate"/>
			<s:textfield label="员工" id="empName" name="emp" size="16" maxlength="64"/>
			<s:select label="用工形式" name="employee.empType.id" list="empType" listKey="id"
			    listValue="emptypeName" multiple="false" emptyOption="true" value="employee.empType.id" size="1" />
			<s:select id="searchType" label="考勤状态" name="searchType" value="searchType"
				list="#{0:'请选择',2:'有迟到',3:'有早退',5:'有旷工',1:'有请假',4:'有加班'}"  emptyOption="false"  />
		</tr>
		<tr>
 			<td align="right">组织单元:</td>
		    <td>
		  	  <s:orgselector id="orgselector1" name="employee.empDeptNo.departmentName" hiddenFieldName="employee.empDeptNo.id"/>
		    </td>
			<s:select label="工作地区"  list="locations" listKey="id" name="employee.empLocationNo.id"
				listValue="locationName" multiple="false" emptyOption="true" value="employee.empLocationNo.id" size="1" />
		</tr>
	  </table>
    </td>
    <td>
		<input title="[Alt+F]" accesskey="F" name="sub_button" id="submit_button" type="button" size="4" class="button" onclick="submitSearch();"  value="查询">
		<input title="[Alt+C]" accesskey="C" name="clear_button" class="button" type="button" onClick="window.location='searchAttendmonthly.action';" value="重置">
	</td>
  </tr>
</table>

<table width="100%">
  <tr><td></td></tr>
  <tr>
	<td align="left">
			<input type="hidden" id="year" name="year" value="<s:property value="year" />" />
			<input type="hidden" id="month" name="month" value="<s:property value="month" />" />
			<input type="hidden" name="searchOrExport" id="searchOrExport" />
			<input type="button" class="button" id="down" name="down" value="前一月" onclick="beforeday();">
			<input type="text" id="attendDate" name="attendDate" value="<s:property value='year' />-<s:property value='month' />" size="7" maxlength="7" onChange="changeDate(this);"/>
			<input type="button" class="button" id="up" name="up" value="后一月" onclick="nextday();">
			<s:if test="period == null">
				<ty:auth auths="401,2 or 401,3">
					<input type="button" class="button" id="initAttend" name="initAttend" value="重新初始化" onclick="initAttdRecords();">
					<input class="button" type="button" onclick=initDivImmUpload('IExamMonthly',"<s:property value="year+','+month" />") value="数据导入"/>
				</ty:auth>
			</s:if><s:elseif test="period.attpStatus==0">
				<ty:auth auths="401,2 or 401,3">
					<input type="button" class="button" id="initAttend" name="initAttend" value="重新初始化" onclick="initAttdRecords();">
					<input type="button" class="button" id="approve" name="approveAttend" value="封帐申请" onclick="applyAttendperiod()">
					<input class="button" type="button" onclick=initDivImmUpload('IExamMonthly',"<s:property value="year+','+month" />") value="数据导入"/>
				</ty:auth>
			</s:elseif><s:elseif test="period.attpStatus==1">
				<ty:auth auths="401,3">
					<input type="button" class="button" id="closeRecord" name="closeAttend" value="封帐" onclick="closeAttendperiod();">
				</ty:auth>
			</s:elseif><s:elseif test="period.attpStatus==2">
				<ty:auth auths="401,3">
					<input type="button" class="button" id="openRecord" name="openAttend" value="解封" onclick="reOpenAttendperiod()">
				</ty:auth>
			</s:elseif>
			<ty:auth auths="401,2 or 401,3">
		        <input type="button" id="btnOutput" value="数据导出" onclick="submitExport();" class="button"/>
		    </ty:auth>
	</td>
	<td align="right">本次查询共得到<s:property value="page.totalRows" />条每月考勤记录&nbsp;</td>
  </tr>
</table>

<div id="scrollDiv" style="overflow:scroll;width:600;">
	<s:if test="displayByDay">
		<jsp:include page="search_attendmonthly_by_day.jsp" flush="true"></jsp:include>
	</s:if><s:else>
		<jsp:include page="search_attendmonthly_by_hour.jsp" flush="true"></jsp:include>
	</s:else>
	
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="gridtableList">
		<tr class="listViewPaginationTdS1">
		  <td colspan="10" align="center">
		    <s:hidden id="page.currentPage" name="page.currentPage" />
		    <s:component template="pager">
		        <s:param name="pageNo" value="page.currentPage" /><s:param name="totalPages" value="page.totalPages" /><s:param name="totalRows" value="page.totalRows" />
        	  	    <s:param name="start" value="page.start" /><s:param name="end" value="page.end" /><s:param name="formId" value="'searchAttendmonthly'" />
        	  	</s:component>
	  	  </td>
		</tr>
	</table>
</div>
</s:form>	
	<div id="dlgExaminDetail"   style="width:750px;display:none;"  >
		<table id="examinDetailTable" width="100%"  border="0" cellspacing="0" cellpadding="0" class="basictable" >
			<thead>
				<tr>
					<th>考勤日期</th>
					<th>班次</th>
					<th>上班时间</th>
					<th>下班时间</th>
					<th>应出勤</th>
					<th>迟到(分)</th>
					<th>早退(分)</th>
					<th>缺勤</th>
					<th>请假</th>
					<th>加班</th>
					<th>异常信息</th>
				</tr>
			</thead>
			<tbody id="memoryDataArea">
			</tbody>
		</table>
	</div>
	
<script type="text/javascript" language="javascript">
//改变改变当前月份日期
function changeDate(obj){
	var str = obj.value.split("-");
	document.getElementById("year").value = str[0];
	document.getElementById("month").value = str[1];
}
		
/*****************************************************
 * 设置文字
 * tdId：td元素的id
 * value:要设置的内容
 *****************************************************
 */
function setTdValue(tdId,value){
	if(value!=null){//加班总时间
		document.getElementById(tdId).innerHTML = value;
	}else{
		document.getElementById(tdId).innerHTML = "";
	}
}
		
/*****************************************************
 * 设置form表单原书的值
 * fieldId：表单元素的id
 * value:要设置的内容
 *****************************************************
 */
function setFieldValue(fieldId,value){
	if(value!=null){//加班总时间
		document.getElementById(fieldId).value = value;
	}else{
		document.getElementById(fieldId).value = "";
	}
}
		
/*****************************************************
 * 每月考勤初始化
 * 返回值： 无
 *****************************************************
 */
//重新初始化
function initAttdRecords(){ 
	if(!confirm("确定要初始化"+document.getElementById("attendDate").value+"的日考勤记录么？已有的当月考勤汇总将被删除。")){
		return false;
	}
	document.searchAttendmonthly.action = "calDailyToAttendmonthly.action";
	document.searchAttendmonthly.submit();
}
		
/*****************************************************
* 删除一条每月考勤汇总数据，用户确认后提交表单，返回每月考勤查询页面
* 参数：rowId  行号
* 参数：attmId 每月考勤记录uuid
* 返回值： 无(在search_attendmonthly_by_day.jsp和by_hour调用)
*****************************************************
*/
function delEmpMonthlyRecords(rowId,attmId){
	if(!confirm("确定要删除"+document.getElementById('empName'+rowId).value+document.getElementById("attendDate").value+"的当月考勤汇总么？")){
		return false;
	}
	document.getElementById("delAttendId").value = attmId;
	document.searchAttendmonthly.action = "deleteAttendmonthly.action";
	document.searchAttendmonthly.submit();
}
	
	
/*****************************************************
 * 检查按键是否是数字或小数点(1位小数)
 * 参数：obj  输入框对象
 * 返回值： 无
 *****************************************************
 */
function MKeyIsNumberOrDot(obj){  
	//alert(obj);
    var text=obj.value;
    //alert(text);
       var tempStr=text.indexOf(".");
       if(tempStr!=-1)
           var index=tempStr+2;
       var code=window.event.keyCode;
       if(code>47&&code<58) {
           if(text.length==index){
           	if(isIE()){
				event.returnValue = false;
			}else{
				event.preventDefault();
			}
           }
       }else if(code==190 && tempStr==-1){
       }else if(code==8 || code==9){
       }else{
           if(isIE()){
			event.returnValue = false;
		}else{
			event.preventDefault();
		}
       }
}
	
/*****************************************************
* “下一月”按钮响应函
* 返回值： 无
******************************************************/
function nextday(){
	var date = document.getElementById("attendDate").value;
	var dates = date.split("-");
	document.getElementById("attendDate").value = MonthAddDiff(dates[0]*1,dates[1]*1,1);
	document.getElementById("up").disabled = true;
	document.getElementById("down").disabled = true;
	document.getElementById('searchOrExport').value = "";
	document.searchAttendmonthly.submit();
}
		
/*****************************************************
* “上一月”按钮响应函
* 返回值： 无
******************************************************/
function beforeday(){
	var date = document.getElementById("attendDate").value;
	var dates = date.split("-");
			
	document.getElementById("attendDate").value = MonthAddDiff(dates[0]*1,dates[1]*1,-1);
	document.getElementById("up").disabled = true;
	document.getElementById("down").disabled = true;
	document.getElementById('searchOrExport').value = "";
	document.searchAttendmonthly.submit();
}
		
//给attendDate赋值
function MonthAddDiff(year, month, diff){
	if(month==1 && diff==-1){
		month = 12;
		year = year - 1;
	}else if(month==12 && diff==1){
		month = 1;
		year = year + 1;
	}else{
		month = month + diff;
	}
	var strmonth = month;
	if(month < 10){
		strmonth = "0" + month;
	}
	document.getElementById("year").value = year;
	document.getElementById("month").value = strmonth;
	return year + "-" + strmonth;
} 
		
/*****************************************************
* 根据屏幕大小计列表框宽度
* 返回值： 无
******************************************************/
function resetTableWidth(){
	var width = document.body.scrollWidth-165;
	document.getElementById('scrollDiv').style.width=width;
}
		
/*****************************************************
* 将员工每日考勤明细弹出层中的内容清空
* 返回值： 无  在by_hour by_day中调用
*****************************************************
*/
function removeContents(){
	if(!document.getElementById('memoryDataArea').childNodes.length)return;
	var ind = document.getElementById('memoryDataArea').childNodes.length;
	for (var i = ind - 1; i >= 0 ; i--) {
	 	document.getElementById('memoryDataArea').removeChild(document.getElementById('memoryDataArea').childNodes[i]);
	 }
}
		
//创建一个TR(被by_day和by_hour调用)
function createTr(color){
var tr = document.createElement("tr");
tr.style.color=color;
return tr;
}
		
//创建一个TD，用来显示时间，格式为hh:ss(被by_day和by_hour调用)
function createTimeTd(data){
var td = document.createElement("td");
if(data == null){
		data = "";
		td.innerHTML = data;
		return td;
}
			
	var date = new Date(data);
	var hour = date.getHours();
	var minute = date.getMinutes();
	if(hour <10){
		hour = "0"+hour;
	}
	if(minute < 10){
		minute = "0"+minute;
	}
	td.innerHTML = hour+":"+minute;
	return td;
}

//创建一个TD，用来普通文本
function createTd(data){
	var td = document.createElement("td");
	if(data == null){
		data = "";
	}
	//data = new Date(data);
	td.innerHTML = data;
	return td;
}
		
//创建一个TD，用来显示日期，格式为yyyy-MM-dd
function createDateTd(data){
	var td = document.createElement("td");
	var date = new Date(data);
	var year = date.getYear();
	if(!isIE()){
		year =year+1900;
	}
	var month = date.getMonth()+1;
	if(month <10){
		month = "0"+month;
	}
	var day = date.getDate();
	if(day < 10){
		day = "0"+day;
	}
	td.innerHTML = year+"-"+month+"-"+day;
	return td;
}
		
/***封帐申请，封帐提交，封帐，解封***************/
//封帐申请
function applyAttendperiod(){
	document.getElementById('status').value = 1;
	document.searchAttendmonthly.action = "applyAttendperiod.action";
	document.searchAttendmonthly.submit();
}
//封帐
function closeAttendperiod(){
	document.getElementById('status').value = 2;
	document.searchAttendmonthly.action = "closeAttendperiod.action";
	document.searchAttendmonthly.submit();
}
//解封
function reOpenAttendperiod(){
	document.getElementById('status').value = 0;
	document.searchAttendmonthly.action = "reOpenAttendperiod.action";
	document.searchAttendmonthly.submit();
}
		
		
//页面加载后根据用户分辨率调整表格大小
resetTableWidth();
 
		
//点击查询
function submitSearch(){
    document.getElementById('searchOrExport').value = ""; //导出滞空
	document.forms[0].submit();
}

//数据导出
function submitExport(){
	document.getElementById('searchOrExport').value = "export";
	document.forms[0].submit();
	document.getElementById('searchOrExport').value = "";
}

hrm.common.initDialog('dlgExaminDetail'); 
hrm.common.initDialog('dlgExaminHourInfo');
hrm.common.initDialog('dlgExaminDayInfo');  
</script>
<jsp:include flush="true" page="../io/div_omm_select.jsp"></jsp:include>
<jsp:include flush="true" page="../io/div_upload.jsp"></jsp:include>
</body>	