<%@page pageEncoding="GBK" contentType="text/html; charset=GBK" %>
<%@taglib prefix="s" uri="webwork"%>
<%@taglib prefix="ty" uri="/tengsource"%>
<!--
 =========================================================
' File:search_attenddaily.jsp
' Auth:Yang
' Version:1.1.0 standard version
' Date: 2008-5-12
' Script Written by hr.com
'=========================================================
' Copyright   2007 hr.com. All rights reserved.
' Web: http://www.hr.com
' Email: admin@hr.com
'
 =========================================================
-->
<head>
<title>每日考勤记录</title>
<jsp:include flush="true" page="/sitemesh/jsPackage.jsp"></jsp:include>		 
<script type="text/javascript" src="../dwr/interface/DwrForAttend.js"></script>
</head>
<body onload="hrm.common.check_order();">
<span class="errorMessage" id="message"></span>
<s:component template="bodyhead">
 <s:param name="pagetitle"  value="'每日考勤记录('+attendDate + '到' + attendDateTo + ')'" />
 <s:param name="helpUrl" value="'26'" />
</s:component>

<s:form id="searchAttenddaily" name="searchAttenddaily" action="searchAttenddaily" namespace="/examin" method="POST">
<s:token></s:token>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="formtable">
  <tr>
    <td>
	  <table width="100%" border="0" cellspacing="2" cellpadding="0">
		<tr>
			<input type="hidden" name="searchOrExport" id="searchOrExport"/>
			<s:hidden id="delAttendId" name="delAttendId"/>
			<s:hidden id="order" name="page.order"/>
			<input type="hidden" id="operate" name="page.operate"/>
			<s:textfield label="员工" id="empName" name="emp" size="16" maxlength="64"/>
			<s:select label="工作地区"  list="locations" listKey="id" name="employee.empLocationNo.id"
				listValue="locationName" multiple="false" emptyOption="true" value="employee.empLocationNo.id" size="1" />
			<s:select id="searchType" label="显示条件" name="searchType" value="searchType"
			    list="#{0:'所有',1:'正常出勤',2:'异常出勤',3:'|--迟到',4:'|--早退',5:'|--缺勤',6:'|--请假',7:'|--加班',8:'|--异常刷卡'}" emptyOption="false"/>
			<td rowspan="2">
			  <input title="[Alt+F]" accesskey="F" name="sub_button" id="submit_button" type="button" size="4" class="button"  onclick="submitSearch();" value="查询">
			  <input title="[Alt+C]" accesskey="C" name="clear_button" class="button" type="button" onClick="window.location='searchAttenddaily.action';" value="重置">
			</td>
		</tr>
		<tr>
		  <td align="right">组织单元:</td>
		  <td>
		  	  <s:orgselector id="orgselector1" name="employee.empDeptNo.departmentName" hiddenFieldName="employee.empDeptNo.id"/>
		  </td>
 		  <s:select label="用工形式" name="employee.empType.id" list="empType" listKey="id"
		    listValue="emptypeName" multiple="false" emptyOption="true" value="employee.empType.id" size="1" />	
		  <td align="right">出勤日期:</td>
		  <td>
		  <s:textfield id="attendDate" name="attendDate"  value="%{attendDate}" required="true" size="10"  />
		    <img onclick="WdatePicker({el:'attendDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">	
		    到<s:textfield id="attendDateTo" name="attendDateTo"  value="%{attendDateTo}" required="true" size="10" />
		    <img onclick="WdatePicker({el:'attendDateTo'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">	
		  </td>
		</tr>
	  </table>
    </td>    
  </tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="resulttable">
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td colspan="1" align="left">
	  <ty:auth auths="401,2 or 401,3">
	    <s:hidden id="output-ommId" name="outmatchModelId"/>
		<s:hidden id="output-ioName" name="outputIoName"/>
	    <input class="button" type="button" id="btnOutput" name="DailyExamExport" size="4" value="数据导出" alt="数据导出" onclick="submitExport();"/>
	  </ty:auth>
    </td>      
    <td align="right">
       本次查询共得到<s:property value="page.totalRows"/>条每日考勤记录
    </td>
  </tr>
</table>       
<table id="salary_paid_table" cellpadding="0" cellspacing="0" width="100%" border="0" class="gridtableList">
  <tr>
	<th nowrap="nowrap">
	  <a href="#" onclick="hrm.common.order_submit('examinDate','searchAttenddaily');">
	  考勤日期<img src='../resource/images/arrow_.gif' width='8' height='10' id='examinDate_img'></a>
	</th>
	<th nowrap="nowrap">
	  <a href="#" onclick="hrm.common.order_submit('empName','searchAttenddaily');">
	  员工姓名<img src='../resource/images/arrow_.gif' width='8' height='10' id='empName_img'></a>
	</th>
	<th nowrap="nowrap">
	  <a href="#" onclick="hrm.common.order_submit('shiftName','searchAttenddaily');">
	  班次<img src='../resource/images/arrow_.gif' width='8' height='10' id='shiftName_img'></a>
    </th>
	<th nowrap="nowrap">
	  <a href="#" onclick="hrm.common.order_submit('onDutyTime','searchAttenddaily');">
	  上班<img src='../resource/images/arrow_.gif' width='8' height='10' id='onDutyTime_img'></a>
    </th>
	<th nowrap="nowrap">
	  <a href="#" onclick="hrm.common.order_submit('offDutyTime','searchAttenddaily');">
	  下班<img src='../resource/images/arrow_.gif' width='8' height='10' id='offDutyTime_img'></a>
	</th>
	<th nowrap="nowrap">
	  <a href="#" onclick="hrm.common.order_submit('oughtDutyHours','searchAttenddaily');">
	   应出勤<img src='../resource/images/arrow_.gif' width='8' height='10' id='oughtDutyHours_img'></a>
	</th>
	<th nowrap="nowrap">
	   <a href="#" onclick="hrm.common.order_submit('lateMinutes','searchAttenddaily');">
	   迟到<img src='../resource/images/arrow_.gif' width='8' height='10' id='lateMinutes_img'></a>
	</th>
	<th nowrap="nowrap">
	   <a href="#" onclick="hrm.common.order_submit('earlyMinutes','searchAttenddaily');">
	   早退<img src='../resource/images/arrow_.gif' width='8' height='10' id='earlyMinutes_img'></a>
	</th>
	<th nowrap="nowrap">
	  <a href="#" onclick="hrm.common.order_submit('absentHours','searchAttenddaily');">
	  缺勤<img src='../resource/images/arrow_.gif' width='8' height='10' id='absentHours_img'></a>
	</th>
	<th nowrap="nowrap">
	  <a href="#" onclick="hrm.common.order_submit('leaveHours','searchAttenddaily');">
	  请假<img src='../resource/images/arrow_.gif' width='8' height='10' id='leaveHours_img'></a>
	</th>
	<th nowrap="nowrap">
	  <a href="#" onclick="hrm.common.order_submit('overtimeHours','searchAttenddaily');">
	  加班<img src='../resource/images/arrow_.gif' width='8' height='10' id='overtimeHours_img'></a>
	</th>
	<th nowrap="nowrap">
	   异常描述
	</th>
  </tr>
  <s:if test="!attenddailyList.isEmpty()"> <!--test判断真假-->
    <s:iterator value="attenddailyList" status="index">
     <tr style="color:<s:property value='displayColor'/>">
	  <td align="center">
	   <s:property value="examinDate" />
	  </td>
	  <td align="center">
		 <span 
            TITLE="员工编号：<s:property value='empDistinctNo'/>
             用工形式：<s:property value='%{getEmpType(empObj.empType.id)}'/>
             所属地区：<s:property value='%{getLocName(empObj.empLocationNo.id)}'/> 
             所属部门：<s:property value='%{getDepName(empObj.empDeptNo.id)}'/> "/>
		   <s:property value="empName" />
		</span>
	  </td>  
	  <td align="center">
		<s:property value="shiftName" />
	  </td>
	  <td align="center">
		<s:date name="onDutyTime" format="HH:mm" /> 
	  </td> 
	  <td align="center">
		<s:date name="offDutyTime" format="HH:mm" /> <!--format是格式化？-->
	  </td>
	  <td align="center">
		<s:if test="dayHourMode == 'hour'">
		  <s:property value="formatBD(oughtDutyHours)"/>小时  <!--action中的formatBD()方法-->
		</s:if>
		<s:else>
		  <s:property value="formatBD(oughtDutyDays)"/>天
		</s:else>
	  </td>
	  <td align="center">
		<s:property value="formatBD_m(lateMinutes)"/>分钟
	  </td> 
	  <td align="center">
		<s:property value="formatBD_m(earlyMinutes)"/>分钟
	  </td>  
	  <td align="center">
		<s:if test="dayHourMode == 'hour'">
		  <s:property value="formatBD(absentHours)"/>小时
	    </s:if>
        <s:else>
		  <s:property value="formatBD(absentDays)"/>天
		</s:else>
	  </td>
	  <td align="center">
		<s:if test="dayHourMode == 'hour'">
		  <s:property value="formatBD(leaveHours)"/>小时
		</s:if>
		<s:else>
		  <s:property value="formatBD(leaveDays)"/>天
		</s:else>
	  </td> 
	  <td align="center">
		<s:property value="formatBD(overtimeHours)"/>小时
	  </td>
	  <td align="center" nowrap="nowrap">
		<s:property value="comments"/>
	  </td>
   	 </tr>
	</s:iterator>
  </s:if>
  <s:else>
    <tr>
      <td align="center" colspan="20">
		您查询的每日出勤信息不存在！
	  </td>
    </tr>
  </s:else>         
    <tr>
	 <td colspan="10" align="center">
        <s:hidden id="page.currentPage" name="page.currentPage" />
        <s:component template="pager">
        <s:param name="pageNo"  value="page.currentPage" /><!--currentPage传给pageNo,ftl中获取pageNo-->
		<s:param name="totalPages" value="page.totalPages" />
	    <s:param name="totalRows" value="page.totalRows" />
        <s:param name="start"  value="page.start" />
        <s:param name="end"    value="page.end" />
        <s:param name="formId" value="'searchAttenddaily'" />
        </s:component>
      </td>
    </tr> 		
</table>
</s:form>

<div id="tmpletId" style="DISPLAY: none">   
   <img src="../resource/images/basic_search.gif" onload="hrm.common.check_order();"/>  
</div>

<script type="text/javascript" language="javascript">	
//查询操作(点击查询按钮)
function submitSearch(){	   
  document.getElementById('searchOrExport').value = "";
  document.forms[0].submit();
}
	
//数据导出(点击数据导出按钮)
function submitExport(){
  document.getElementById('searchOrExport').value = "export";
  document.forms[0].submit();
  document.getElementById('searchOrExport').value = "";
}
	
//判断是否整数
function MKeyIsNumber(){
  if(!((_event_code() >= 48 && _event_code() <= 57) ||  _event_code() == 8 ||  _event_code() == 9)){
	if(isIE()){
	  event.returnValue = false;
	}
	else{
	  event.preventDefault();
    }
  }
}

//检查按键是否是数字
function MKeyIsNumberOrDot(obj){
  var text=obj.value;
  var code=window.event.keyCode;
  if(code>47&&code<58) {
  }
  else if(code==190){          
  }
  else if(code==8 || code==9){
  }
  else{
    if(isIE()){
      event.returnValue = false;
	}
	else{
	  event.preventDefault();
	}
  }
} 
 
</script>
<jsp:include flush="true" page="../io/div_omm_select.jsp"></jsp:include>
</body>	