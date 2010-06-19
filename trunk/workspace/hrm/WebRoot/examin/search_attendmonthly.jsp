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
	<title>ÿ�¿��ڻ���</title>
	<jsp:include flush="true" page="/sitemesh/jsPackage.jsp"></jsp:include>	
	<script type='text/javascript' src='../dwr/interface/DwrForAttend.js'></script>
</head>
<body onload="hrm.common.check_order();">
<s:component template="bodyhead">
	<s:param name="pagetitle"  value="'ÿ�¿��ڻ���('+startDate+'��'+endDate+')'" />
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
			<s:textfield label="Ա��" id="empName" name="emp" size="16" maxlength="64"/>
			<s:select label="�ù���ʽ" name="employee.empType.id" list="empType" listKey="id"
			    listValue="emptypeName" multiple="false" emptyOption="true" value="employee.empType.id" size="1" />
			<s:select id="searchType" label="����״̬" name="searchType" value="searchType"
				list="#{0:'��ѡ��',2:'�гٵ�',3:'������',5:'�п���',1:'�����',4:'�мӰ�'}"  emptyOption="false"  />
		</tr>
		<tr>
 			<td align="right">��֯��Ԫ:</td>
		    <td>
		  	  <s:orgselector id="orgselector1" name="employee.empDeptNo.departmentName" hiddenFieldName="employee.empDeptNo.id"/>
		    </td>
			<s:select label="��������"  list="locations" listKey="id" name="employee.empLocationNo.id"
				listValue="locationName" multiple="false" emptyOption="true" value="employee.empLocationNo.id" size="1" />
		</tr>
	  </table>
    </td>
    <td>
		<input title="[Alt+F]" accesskey="F" name="sub_button" id="submit_button" type="button" size="4" class="button" onclick="submitSearch();"  value="��ѯ">
		<input title="[Alt+C]" accesskey="C" name="clear_button" class="button" type="button" onClick="window.location='searchAttendmonthly.action';" value="����">
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
			<input type="button" class="button" id="down" name="down" value="ǰһ��" onclick="beforeday();">
			<input type="text" id="attendDate" name="attendDate" value="<s:property value='year' />-<s:property value='month' />" size="7" maxlength="7" onChange="changeDate(this);"/>
			<input type="button" class="button" id="up" name="up" value="��һ��" onclick="nextday();">
			<s:if test="period == null">
				<ty:auth auths="401,2 or 401,3">
					<input type="button" class="button" id="initAttend" name="initAttend" value="���³�ʼ��" onclick="initAttdRecords();">
					<input class="button" type="button" onclick=initDivImmUpload('IExamMonthly',"<s:property value="year+','+month" />") value="���ݵ���"/>
				</ty:auth>
			</s:if><s:elseif test="period.attpStatus==0">
				<ty:auth auths="401,2 or 401,3">
					<input type="button" class="button" id="initAttend" name="initAttend" value="���³�ʼ��" onclick="initAttdRecords();">
					<input type="button" class="button" id="approve" name="approveAttend" value="��������" onclick="applyAttendperiod()">
					<input class="button" type="button" onclick=initDivImmUpload('IExamMonthly',"<s:property value="year+','+month" />") value="���ݵ���"/>
				</ty:auth>
			</s:elseif><s:elseif test="period.attpStatus==1">
				<ty:auth auths="401,3">
					<input type="button" class="button" id="closeRecord" name="closeAttend" value="����" onclick="closeAttendperiod();">
				</ty:auth>
			</s:elseif><s:elseif test="period.attpStatus==2">
				<ty:auth auths="401,3">
					<input type="button" class="button" id="openRecord" name="openAttend" value="���" onclick="reOpenAttendperiod()">
				</ty:auth>
			</s:elseif>
			<ty:auth auths="401,2 or 401,3">
		        <input type="button" id="btnOutput" value="���ݵ���" onclick="submitExport();" class="button"/>
		    </ty:auth>
	</td>
	<td align="right">���β�ѯ���õ�<s:property value="page.totalRows" />��ÿ�¿��ڼ�¼&nbsp;</td>
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
					<th>��������</th>
					<th>���</th>
					<th>�ϰ�ʱ��</th>
					<th>�°�ʱ��</th>
					<th>Ӧ����</th>
					<th>�ٵ�(��)</th>
					<th>����(��)</th>
					<th>ȱ��</th>
					<th>���</th>
					<th>�Ӱ�</th>
					<th>�쳣��Ϣ</th>
				</tr>
			</thead>
			<tbody id="memoryDataArea">
			</tbody>
		</table>
	</div>
	
<script type="text/javascript" language="javascript">
//�ı�ı䵱ǰ�·�����
function changeDate(obj){
	var str = obj.value.split("-");
	document.getElementById("year").value = str[0];
	document.getElementById("month").value = str[1];
}
		
/*****************************************************
 * ��������
 * tdId��tdԪ�ص�id
 * value:Ҫ���õ�����
 *****************************************************
 */
function setTdValue(tdId,value){
	if(value!=null){//�Ӱ���ʱ��
		document.getElementById(tdId).innerHTML = value;
	}else{
		document.getElementById(tdId).innerHTML = "";
	}
}
		
/*****************************************************
 * ����form��ԭ���ֵ
 * fieldId����Ԫ�ص�id
 * value:Ҫ���õ�����
 *****************************************************
 */
function setFieldValue(fieldId,value){
	if(value!=null){//�Ӱ���ʱ��
		document.getElementById(fieldId).value = value;
	}else{
		document.getElementById(fieldId).value = "";
	}
}
		
/*****************************************************
 * ÿ�¿��ڳ�ʼ��
 * ����ֵ�� ��
 *****************************************************
 */
//���³�ʼ��
function initAttdRecords(){ 
	if(!confirm("ȷ��Ҫ��ʼ��"+document.getElementById("attendDate").value+"���տ��ڼ�¼ô�����еĵ��¿��ڻ��ܽ���ɾ����")){
		return false;
	}
	document.searchAttendmonthly.action = "calDailyToAttendmonthly.action";
	document.searchAttendmonthly.submit();
}
		
/*****************************************************
* ɾ��һ��ÿ�¿��ڻ������ݣ��û�ȷ�Ϻ��ύ��������ÿ�¿��ڲ�ѯҳ��
* ������rowId  �к�
* ������attmId ÿ�¿��ڼ�¼uuid
* ����ֵ�� ��(��search_attendmonthly_by_day.jsp��by_hour����)
*****************************************************
*/
function delEmpMonthlyRecords(rowId,attmId){
	if(!confirm("ȷ��Ҫɾ��"+document.getElementById('empName'+rowId).value+document.getElementById("attendDate").value+"�ĵ��¿��ڻ���ô��")){
		return false;
	}
	document.getElementById("delAttendId").value = attmId;
	document.searchAttendmonthly.action = "deleteAttendmonthly.action";
	document.searchAttendmonthly.submit();
}
	
	
/*****************************************************
 * ��鰴���Ƿ������ֻ�С����(1λС��)
 * ������obj  ��������
 * ����ֵ�� ��
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
* ����һ�¡���ť��Ӧ��
* ����ֵ�� ��
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
* ����һ�¡���ť��Ӧ��
* ����ֵ�� ��
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
		
//��attendDate��ֵ
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
* ������Ļ��С���б����
* ����ֵ�� ��
******************************************************/
function resetTableWidth(){
	var width = document.body.scrollWidth-165;
	document.getElementById('scrollDiv').style.width=width;
}
		
/*****************************************************
* ��Ա��ÿ�տ�����ϸ�������е��������
* ����ֵ�� ��  ��by_hour by_day�е���
*****************************************************
*/
function removeContents(){
	if(!document.getElementById('memoryDataArea').childNodes.length)return;
	var ind = document.getElementById('memoryDataArea').childNodes.length;
	for (var i = ind - 1; i >= 0 ; i--) {
	 	document.getElementById('memoryDataArea').removeChild(document.getElementById('memoryDataArea').childNodes[i]);
	 }
}
		
//����һ��TR(��by_day��by_hour����)
function createTr(color){
var tr = document.createElement("tr");
tr.style.color=color;
return tr;
}
		
//����һ��TD��������ʾʱ�䣬��ʽΪhh:ss(��by_day��by_hour����)
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

//����һ��TD��������ͨ�ı�
function createTd(data){
	var td = document.createElement("td");
	if(data == null){
		data = "";
	}
	//data = new Date(data);
	td.innerHTML = data;
	return td;
}
		
//����һ��TD��������ʾ���ڣ���ʽΪyyyy-MM-dd
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
		
/***�������룬�����ύ�����ʣ����***************/
//��������
function applyAttendperiod(){
	document.getElementById('status').value = 1;
	document.searchAttendmonthly.action = "applyAttendperiod.action";
	document.searchAttendmonthly.submit();
}
//����
function closeAttendperiod(){
	document.getElementById('status').value = 2;
	document.searchAttendmonthly.action = "closeAttendperiod.action";
	document.searchAttendmonthly.submit();
}
//���
function reOpenAttendperiod(){
	document.getElementById('status').value = 0;
	document.searchAttendmonthly.action = "reOpenAttendperiod.action";
	document.searchAttendmonthly.submit();
}
		
		
//ҳ����غ�����û��ֱ��ʵ�������С
resetTableWidth();
 
		
//�����ѯ
function submitSearch(){
    document.getElementById('searchOrExport').value = ""; //�����Ϳ�
	document.forms[0].submit();
}

//���ݵ���
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