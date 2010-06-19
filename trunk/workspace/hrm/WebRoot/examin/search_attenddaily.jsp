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
<title>ÿ�տ��ڼ�¼</title>
<jsp:include flush="true" page="/sitemesh/jsPackage.jsp"></jsp:include>		 
<script type="text/javascript" src="../dwr/interface/DwrForAttend.js"></script>
</head>
<body onload="hrm.common.check_order();">
<span class="errorMessage" id="message"></span>
<s:component template="bodyhead">
 <s:param name="pagetitle"  value="'ÿ�տ��ڼ�¼('+attendDate + '��' + attendDateTo + ')'" />
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
			<s:textfield label="Ա��" id="empName" name="emp" size="16" maxlength="64"/>
			<s:select label="��������"  list="locations" listKey="id" name="employee.empLocationNo.id"
				listValue="locationName" multiple="false" emptyOption="true" value="employee.empLocationNo.id" size="1" />
			<s:select id="searchType" label="��ʾ����" name="searchType" value="searchType"
			    list="#{0:'����',1:'��������',2:'�쳣����',3:'|--�ٵ�',4:'|--����',5:'|--ȱ��',6:'|--���',7:'|--�Ӱ�',8:'|--�쳣ˢ��'}" emptyOption="false"/>
			<td rowspan="2">
			  <input title="[Alt+F]" accesskey="F" name="sub_button" id="submit_button" type="button" size="4" class="button"  onclick="submitSearch();" value="��ѯ">
			  <input title="[Alt+C]" accesskey="C" name="clear_button" class="button" type="button" onClick="window.location='searchAttenddaily.action';" value="����">
			</td>
		</tr>
		<tr>
		  <td align="right">��֯��Ԫ:</td>
		  <td>
		  	  <s:orgselector id="orgselector1" name="employee.empDeptNo.departmentName" hiddenFieldName="employee.empDeptNo.id"/>
		  </td>
 		  <s:select label="�ù���ʽ" name="employee.empType.id" list="empType" listKey="id"
		    listValue="emptypeName" multiple="false" emptyOption="true" value="employee.empType.id" size="1" />	
		  <td align="right">��������:</td>
		  <td>
		  <s:textfield id="attendDate" name="attendDate"  value="%{attendDate}" required="true" size="10"  />
		    <img onclick="WdatePicker({el:'attendDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">	
		    ��<s:textfield id="attendDateTo" name="attendDateTo"  value="%{attendDateTo}" required="true" size="10" />
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
	    <input class="button" type="button" id="btnOutput" name="DailyExamExport" size="4" value="���ݵ���" alt="���ݵ���" onclick="submitExport();"/>
	  </ty:auth>
    </td>      
    <td align="right">
       ���β�ѯ���õ�<s:property value="page.totalRows"/>��ÿ�տ��ڼ�¼
    </td>
  </tr>
</table>       
<table id="salary_paid_table" cellpadding="0" cellspacing="0" width="100%" border="0" class="gridtableList">
  <tr>
	<th nowrap="nowrap">
	  <a href="#" onclick="hrm.common.order_submit('examinDate','searchAttenddaily');">
	  ��������<img src='../resource/images/arrow_.gif' width='8' height='10' id='examinDate_img'></a>
	</th>
	<th nowrap="nowrap">
	  <a href="#" onclick="hrm.common.order_submit('empName','searchAttenddaily');">
	  Ա������<img src='../resource/images/arrow_.gif' width='8' height='10' id='empName_img'></a>
	</th>
	<th nowrap="nowrap">
	  <a href="#" onclick="hrm.common.order_submit('shiftName','searchAttenddaily');">
	  ���<img src='../resource/images/arrow_.gif' width='8' height='10' id='shiftName_img'></a>
    </th>
	<th nowrap="nowrap">
	  <a href="#" onclick="hrm.common.order_submit('onDutyTime','searchAttenddaily');">
	  �ϰ�<img src='../resource/images/arrow_.gif' width='8' height='10' id='onDutyTime_img'></a>
    </th>
	<th nowrap="nowrap">
	  <a href="#" onclick="hrm.common.order_submit('offDutyTime','searchAttenddaily');">
	  �°�<img src='../resource/images/arrow_.gif' width='8' height='10' id='offDutyTime_img'></a>
	</th>
	<th nowrap="nowrap">
	  <a href="#" onclick="hrm.common.order_submit('oughtDutyHours','searchAttenddaily');">
	   Ӧ����<img src='../resource/images/arrow_.gif' width='8' height='10' id='oughtDutyHours_img'></a>
	</th>
	<th nowrap="nowrap">
	   <a href="#" onclick="hrm.common.order_submit('lateMinutes','searchAttenddaily');">
	   �ٵ�<img src='../resource/images/arrow_.gif' width='8' height='10' id='lateMinutes_img'></a>
	</th>
	<th nowrap="nowrap">
	   <a href="#" onclick="hrm.common.order_submit('earlyMinutes','searchAttenddaily');">
	   ����<img src='../resource/images/arrow_.gif' width='8' height='10' id='earlyMinutes_img'></a>
	</th>
	<th nowrap="nowrap">
	  <a href="#" onclick="hrm.common.order_submit('absentHours','searchAttenddaily');">
	  ȱ��<img src='../resource/images/arrow_.gif' width='8' height='10' id='absentHours_img'></a>
	</th>
	<th nowrap="nowrap">
	  <a href="#" onclick="hrm.common.order_submit('leaveHours','searchAttenddaily');">
	  ���<img src='../resource/images/arrow_.gif' width='8' height='10' id='leaveHours_img'></a>
	</th>
	<th nowrap="nowrap">
	  <a href="#" onclick="hrm.common.order_submit('overtimeHours','searchAttenddaily');">
	  �Ӱ�<img src='../resource/images/arrow_.gif' width='8' height='10' id='overtimeHours_img'></a>
	</th>
	<th nowrap="nowrap">
	   �쳣����
	</th>
  </tr>
  <s:if test="!attenddailyList.isEmpty()"> <!--test�ж����-->
    <s:iterator value="attenddailyList" status="index">
     <tr style="color:<s:property value='displayColor'/>">
	  <td align="center">
	   <s:property value="examinDate" />
	  </td>
	  <td align="center">
		 <span 
            TITLE="Ա����ţ�<s:property value='empDistinctNo'/>
             �ù���ʽ��<s:property value='%{getEmpType(empObj.empType.id)}'/>
             ����������<s:property value='%{getLocName(empObj.empLocationNo.id)}'/> 
             �������ţ�<s:property value='%{getDepName(empObj.empDeptNo.id)}'/> "/>
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
		<s:date name="offDutyTime" format="HH:mm" /> <!--format�Ǹ�ʽ����-->
	  </td>
	  <td align="center">
		<s:if test="dayHourMode == 'hour'">
		  <s:property value="formatBD(oughtDutyHours)"/>Сʱ  <!--action�е�formatBD()����-->
		</s:if>
		<s:else>
		  <s:property value="formatBD(oughtDutyDays)"/>��
		</s:else>
	  </td>
	  <td align="center">
		<s:property value="formatBD_m(lateMinutes)"/>����
	  </td> 
	  <td align="center">
		<s:property value="formatBD_m(earlyMinutes)"/>����
	  </td>  
	  <td align="center">
		<s:if test="dayHourMode == 'hour'">
		  <s:property value="formatBD(absentHours)"/>Сʱ
	    </s:if>
        <s:else>
		  <s:property value="formatBD(absentDays)"/>��
		</s:else>
	  </td>
	  <td align="center">
		<s:if test="dayHourMode == 'hour'">
		  <s:property value="formatBD(leaveHours)"/>Сʱ
		</s:if>
		<s:else>
		  <s:property value="formatBD(leaveDays)"/>��
		</s:else>
	  </td> 
	  <td align="center">
		<s:property value="formatBD(overtimeHours)"/>Сʱ
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
		����ѯ��ÿ�ճ�����Ϣ�����ڣ�
	  </td>
    </tr>
  </s:else>         
    <tr>
	 <td colspan="10" align="center">
        <s:hidden id="page.currentPage" name="page.currentPage" />
        <s:component template="pager">
        <s:param name="pageNo"  value="page.currentPage" /><!--currentPage����pageNo,ftl�л�ȡpageNo-->
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
//��ѯ����(�����ѯ��ť)
function submitSearch(){	   
  document.getElementById('searchOrExport').value = "";
  document.forms[0].submit();
}
	
//���ݵ���(������ݵ�����ť)
function submitExport(){
  document.getElementById('searchOrExport').value = "export";
  document.forms[0].submit();
  document.getElementById('searchOrExport').value = "";
}
	
//�ж��Ƿ�����
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

//��鰴���Ƿ�������
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