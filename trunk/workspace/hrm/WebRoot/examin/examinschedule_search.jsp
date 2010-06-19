<%@page pageEncoding="GBK" contentType="text/html; charset=GBK" %>
<%@page import="java.util.*" %>
<%@taglib prefix="s" uri="webwork"%>
<%@taglib prefix="ty" uri="/tengsource"%>
<!--
 =========================================================
' File:examinschedule_search.jsp
' Auth:Xie
' Version:1.1.0 standard version
' Date: 2008-6-12
' Script Written by hr.com
'=========================================================
' Copyright   2007 hr.com. All rights reserved.
' Web: http://www.hr.com
' Email: admin@hr.com
'=======================================================
 -->
<head>
<title>�Ű����</title>
<style type="text/css">
.floating_layer {
	font-size: 12px;
	width:180px;
	border-top:1px solid #999999;
	border-left:1px solid #999999;
	border-right:2px solid #666;
	border-bottom:2px solid #666;
	background:#fff;
background:#e8e8e8;
}
menu,li{margin:0px;padding:0px;
list-style:none;
list-style-position:outside}
.floating_layer li{border-bottom:1px dotted #D8D8D8}
.floating_layer li a{padding:3px;display:block;text-decoration:none;color:#000}
.floating_layer li a:hover{background:#02aac3;color:#fff}
</style>
<jsp:include flush="true" page="/sitemesh/jsPackage.jsp"></jsp:include>	
<script type='text/javascript' src='../dwr/interface/ExaminSchedule.js'></script>	
</head>
<body onload="hrm.common.check_order();">
<span class="errorMessage" id="message"></span>
<s:form id="examinScheduleSearch" name="examinScheduleSearch" action="examinScheduleSearch" namespace="/examin" method="POST">
<s:component template="bodyhead">
	<s:param name="pagetitle"  value="'�Ű����('+dayFrom1+'��'+ dayTo1+')'" />
	<s:param name="helpUrl" value="'26'" />
</s:component>

<s:token></s:token>
<s:hidden id="order" name="page.order" />
<s:hidden id="outmatchModelId" name="outmatchModelId"/>
<input type="hidden" name="searchOrExport" id="searchOrExport" />
<s:hidden id="empIdString" name="empIdString"/>
<s:hidden id="empNameString" name="empNameString"/>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="formtable">
  <tr>
    <td>
	  <table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<s:hidden id="delAttendId" name="delAttendId" />
			<input type="hidden" id="operate" name="page.operate"/>
			<s:textfield label="Ա��" id="empName" name="emp" size="14" maxlength="64"/>
			<td align="right">��֯��Ԫ:</td>
		    <td>
		  	  <s:orgselector id="orgselector1" name="employee.empDeptNo.departmentName" hiddenFieldName="employee.empDeptNo.id"/>
		    </td>
			<s:select label="��������" list="locations" listKey="id" name="employee.empLocationNo.id"
				listValue="locationName" multiple="false" emptyOption="true" value="employee.empLocationNo.id" size="1" />
			<s:select label="�ù���ʽ" name="employee.empType.id" list="empTypes" listKey="id"
					 listValue="emptypeName" multiple="false" emptyOption="true" value="employee.empType.id" size="1" />
		</tr>
	  </table>
    </td>
    
    <td>
		<input title="[Alt+F]" accesskey="F" name="sub_button" id="submit_button" type="button" size="4" class="button"  value="��ѯ" onclick="clearExport();">
		<input title="[Alt+C]" accesskey="C" name="clear_button" class="button" type="button" onClick="window.location='examinScheduleSearch.action';" value="����">
		<br/> 
	</td>
  </tr>
</table>
<br/>
<table width="100%" border="0" cellspacing="0" cellpadding="0" >
  <tr>
    <td>
    <!-- Ϊ��ʾ����Ҫ��Ӧ���ܽ�ֹ������� -->
    <input type="hidden" id="dayFrom" name="dayFrom" value="<s:property value="dayFrom"/>"/>
    <input type="hidden" id="dayTo" name="dayTo" value="<s:property value="dayTo"/>"/>
    <input type="hidden" id="monthFlag" name="monthFlag" value="<s:property value="monthFlag"/>"/>
    <input type="hidden" id="dayFlag" name="dayFlag" value="<s:property value="dayFlag"/>"/>
    
    <input type="hidden" id="year" name="year" value="<s:property value="year" />" />
	<input type="hidden" id="month" name="month" value="<s:property value="month" />" />
	<input type="button" class="button" id="down" name="down" value="ǰһ��" onclick="beforeday();">
	<input type="text" id="attendDate" name="attendDate" value="<s:property value="year" />-<s:property value="month" />" size="8" maxlength="7" onChange="changeDate(this);"/>
	<input type="button" class="button" id="up" name="up" value="��һ��" onclick="nextday();">
	<s:if test="flag=='true'">
	    <input class="button" type="button" value="&nbsp;�Ű�&nbsp;" name="import" onclick="batchUpdateSchedule();"/>
	</s:if>
	<s:else>
	    <input class="button" type="button" value="&nbsp;�Ű�&nbsp;" name="import" onclick="batchUpdateSchedule();" disabled="true"/>
	</s:else>
    
    <input class="button" type="button" value="����б�" name="import" onclick="hrm.common.openDialog('dlgShiftList')"/>
    
   	<ty:auth auths="401 or 411,3 or 411,2">
		<input class="button" type="button" onclick="initDivImmUpload('IExaminShift','<s:property value="year" />,<s:property value="month" />');" value="���ݵ���"/>
    	<input type="button" class="button" id="btnOutput" value="���ݵ���" onclick="export_check('export');"/>
	</ty:auth>
    </td>
    <td align="right">���β�ѯ���õ�<s:property value="page.totalRows" />���Ű��¼&nbsp;</td>
  </tr>
</table>

<div id="scrollDiv" style="overflow:scroll;width:600;">
<table id="attend_schedule_table" cellpadding="0" cellspacing="0" width="100%" border="0" class="gridtableList">
	     <tr>
			<th width="10">
			    <s:if test="flag=='true'">
	                <input id="changIds" name="changIds" class="checkbox" type="checkbox" onclick="hrm.common.checkAllByName('empId','changIds');" value="ѡ������">
	            </s:if>
	            <s:else>
	                <input id="changIds" name="changIds" class="checkbox" type="checkbox" onclick="hrm.common.checkAllByName('empId','changIds');" value="ѡ������" disabled="true">
	            </s:else>
			</th>	     
	     	<th nowrap="nowrap">
	     		<a href="#" onclick="hrm.common.order_submit('empName','examinScheduleSearch');">
	     		Ա������<img src='../resource/images/arrow_.gif' width='8' height='10' id='empName_img'></a>
	     	</th>
	     	<s:if test="dayList!=null">
	     		<s:set name="single" value="1"/>
				<s:iterator value="dayList" status="index">
					<s:if test="#single==0">
						<th><s:property/></th>
						<s:set name="single" value="1"/>
					</s:if>
					<s:else>
						<s:set name="single" value="0"/>
					</s:else>
		     	</s:iterator>
		     </s:if>
	     </tr>
	   <s:if test="empList!=null && dayList!=null">
	   	<s:iterator value="empList" status="index1">
		<tr>
			<td>
			    <s:if test="flag=='true'">
	                <input id="empId" name="empId" class="checkbox" type="checkbox" value="<s:property value="id"/>">
	            </s:if>
	            <s:else>
	                <input id="empId" name="empId" class="checkbox" type="checkbox" value="<s:property value="id"/>" disabled="true">
	            </s:else>
				
				<s:hidden name="empName" id="empName"/>
			</td>		
			<td>
            <span 
TITLE="Ա����ţ�<s:property value='empDistinctNo'/>
�ù���ʽ��<s:property value='%{getEmpType(empType.id)}'/>
����������<s:property value='%{getLocName(empLocationNo.id)}'/> 
�������ţ�<s:property value='%{getDepName(empDeptNo.id)}'/> 
"/>
			<s:property value="empName"/></td>
			<s:set name="single" value="1"/>
			<s:set name="backcolor" value="'FFFFFF'"/>
	     	<s:iterator value="dayList" status="index2">
					<s:if test="#single==0">
						<td style="background-color:#<s:property value='#backcolor'/>">
						<s:if test="flag=='true'">
	                        <input style="background-color: transparent;height:17" class="inputL" type="text" size="2" id="<s:property value="#index1.count-1"/>-<s:property/>" name="schedule" value="" readonly="readonly"/>
	                    </s:if>
	                    <s:else>
	                        <input style="background-color: transparent;height:17" class="inputL" type="text" size="2" id="<s:property value="#index1.count-1"/>-<s:property/>" name="schedule" value="" disabled="true"/>
	                    </s:else>
						</td>
						<s:set name="single" value="1"/>
					</s:if>
					<s:else>
						<s:set name="single" value="0"/>
						<s:if test="top==7 || top==1">
							<s:set name="backcolor" value="'CCCCCC'"/>
						</s:if>
						<s:else>
							<s:set name="backcolor" value="'FFFFFF'"/>
						</s:else>
					</s:else>	     	
		    </s:iterator>			
		</tr>
		</s:iterator>
		</s:if>
		<s:else>
			<tr><!-- ������Ա����н��Ϣ -->
				<td align="center" colspan="33">
					
					�޷����������Ű��¼! 
					
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
       	  	    <s:param name="start" value="page.start" /><s:param name="end" value="page.end" /><s:param name="formId" value="'examinScheduleSearch'" />
       	  	</s:component>
  	  </td>
	</tr>
</table>
</div>

</s:form>

<!-- ���������Ű�DIV    �޸�Ϊ�Ű�Dialogʵ��-->
<div id="dlgbatch_schedule_div" title="�����Ű�" style="display:none;width:360">  
	<table id="batchConfigTable" width="100%"  border="0" cellspacing="2" cellpadding="0" class="gridtableList">
	    <tr>
			<td align="left" colspan="2" id="td_batchEmpName">
				
			</td>
		</tr>
		<tr>
		    <td align="left">
		         ��ѡ���Σ�
		    </td>
		    <td align="left">
		        <select id="batch_attdShift" name="attdShift1.id" onchange="showDaysOfCurrentShift(this.value);">
		            <option value="0" selected="selected">���</option>
		        </select>
		        <span id="currShiftColorSpan" width="10px">&nbsp;</span>
		     </td>
		</tr>
		<tr>
		    <td align="center" colspan="2">
		        <table border="0" cellspacing="1" cellpadding="1">
		            <tr><td align="center">��</td><td align="center">һ</td><td align="center">��</td><td align="center">��</td><td align="center">��</td><td align="center">��</td><td align="center">��</td></tr>
		            <%
		            int day = 0, index = 0;
		            List dayList = (List)request.getAttribute("dayList");  //Action��û�ҵ���ʲô��˼��
		            int temp =  Integer.parseInt(dayList.get(0).toString());
		            int week = 0; 
		            for(int i = 0; i < 6; i++){
		            %>
		            <tr>
		                <%
		                for(int j = 0; j < 7; j++){
		                    if(i==0 && j<temp-1){
		                        out.print("<td>&nbsp;</td>");
		                        continue;
		                    }
		                    week = 0;
		                    if(2*index < dayList.size()){
		                        week = Integer.parseInt(dayList.get(2*index).toString());
		                    }
		                %>
		                <td <%if(week==7||week==1){%>style="background-color:#CCCCCC"<%}%>>
		                    <%
		                    if(2*index+1 < dayList.size()){
		                        day = Integer.parseInt(dayList.get(2*index+1).toString());
		                        index ++;
		                    %>
		                        <input type="text" size="2" id="<%="label"+day %>" value="<%=day %>" onclick="addDayToCurrentShift(this, this.value);" readonly="readonly" style="cursor:hand"/>
		                    <%} %>
		                </td>
		                <%}%>
		            </tr>
		            <%}%>
		        </table>
		    </td>
		    <td align="left" valign="top" style="display:none">
		        <textarea id="selectedDateArea" rows="10" cols="10" onKeyDown="checkAreaInput(event, this);" readonly="readonly"></textarea>
		    </td>
		</tr>
	    <tr>
			<td align="center" colspan="2">
				<input type="button" onclick="batchSaveAttend();" value="����">
				<input type="button" onclick="hrm.common.closeDialog('dlgbatch_schedule_div');" value="�ر�">
			</td>
		</tr>
	</table>
	
	<iframe scrolling="no" style="position:absolute;z-index:-1;width:expression(this.parentNode.offsetWidth-2);height:expression(this.parentNode.offsetHeight-2);top:0px;left:0px;width: 330px; height: 120px; " frameborder="0"></iframe>
</div>

<!-- ����б�̬dialog -->
<div id="dlgShiftList" title="����б�" style="display:none;width:460">	
  <table id="shiftListTable" width="100%"  border="0" cellspacing="2" cellpadding="0" class="gridtableList" >
   <tr>
    <th nowrap="nowrap">�������</th>
    <th nowrap="nowrap">��δ���</th>
    <th nowrap="nowrap">���ʱ���</th>
    <th nowrap="nowrap">��ʱ</th>
    <th nowrap="nowrap">��ʾ��ɫ</th>
  </tr>
  <s:iterator value="shiftList" status="index-shift">
  <tr>
    <td align="center" nowrap="nowrap"><s:property value="attsName"/></td>
    <td align="center" nowrap="nowrap"><s:property value="attsShortName"/></td>
    <td align="center" nowrap="nowrap"><s:property value='attsSession'/><br></td>
    <td align="center" nowrap="nowrap"><s:property value='attsWorkingHour'/></td>
    <td align="center" nowrap="nowrap"><span style="width:10px;background-color:#<s:property value='attsColor'/>">&nbsp;</span></td>
  </tr>
  </s:iterator>
 </table>
 <center>
    <input type="button" name="close" value="�ر�" onclick="hrm.common.closeDialog('dlgShiftList')"/>
 </center>
</div>
<!-- ����б�̬dialog end -->
 
<!-- �Ҽ��Ű��б���ʾ��ʼ -->
<div id ="schedule_shift_list_div" class="floating_layer" style="position:absolute;left:280px;top:220px;z-index:4;solid;width:120px;display:none;"  >
	<input type="hidden" id="oneScheduleRowId" name="oneScheduleRowId"/>
	<input type="hidden" id="oneScheduleEmpId" name="oneScheduleEmpId"/>
	<input type="hidden" id="oneScheduleDate" name="oneScheduleDate"/>
    <menu id="shiftItems">
    
    </menu>
</div>
<!-- �Ҽ��Ű��б���ʾ���� -->


<script type="text/javascript"> 
//�Զ���HashMap���ݽṹ
function HashMap()   
 {   
     /** Map ��С **/  
     var size = 0;   
     /** ���� **/  
     var entry = new Object();   
        
     /** �� **/  
     this.put = function (key , value)   
     {   
         if(!this.containsKey(key))   
         {   
             size ++ ;   
         }   
         entry[key] = value;   
     }           
     /** ȡ **/  
     this.get = function (key)   
     {   
         return this.containsKey(key) ? entry[key] : null;   
     }           
     /** ɾ�� **/  
     this.remove = function ( key )   
     {   
         if( this.containsKey(key) && ( delete entry[key] ) )   
         {   
             size --;   
         }   
     }           
     /** �Ƿ���� Key **/  
     this.containsKey = function ( key )   
     {   
         return (key in entry);   
     }           
     /** �Ƿ���� Value **/  
     this.containsValue = function ( value )   
     {   
         for(var prop in entry)   
         {   
             if(entry[prop] == value)   
             {   
                 return true;   
             }   
         }   
         return false;   
     }           
     /** ���� Value **/  
     this.values = function ()   
     {   
         var values = new Array();   
         for(var prop in entry)   
         {   
             values.push(entry[prop]);   
         }   
         return values;   
     }           
     /** ���� Key **/  
     this.keys = function ()   
     {   
         var keys = new Array();   
         for(var prop in entry)   
         {   
             keys.push(prop);   
         }   
         return keys;   
     }           
     /** Map Size **/  
     this.size = function ()   
     {   
         return size;   
     }           
     /* ��� */  
     this.clear = function ()   
     {   
         size = 0;   
         entry = new Object();   
     }   
 } 


//��ʼ��ÿ����ζ�Ӧ�������ַ����������Ű�ʱ�õ���jet 2008-11-18
var shiftMap = null;
var shiftColorMap = null; //(�����������е��ã������ɫ����)
var shiftNameMap = null; //��������Ϊ���ñ���
function initShiftMap(){
    //alert("init");
    shiftMap = new HashMap();
    shiftColorMap = new HashMap();
    shiftNameMap = new HashMap();
    shiftMap.put("0","");
    shiftColorMap.put("0","FFFFFF");
    shiftNameMap.put("0","");
	<s:iterator value="shiftList" status="st0">
	    shiftMap.put("<s:property value='id'/>", "");
	    shiftColorMap.put("<s:property value='id'/>", "<s:property value='attsColor'/>");
	    shiftNameMap.put("<s:property value='id'/>", "<s:property value='attsShortName'/>");
	</s:iterator>
	//ͬʱ��div�д�����ɫ�����ʼ����
	document.getElementById('currShiftColorSpan').style.backgroundColor = "#FFFFFF";
	for(var i = 1; i < 32; i++){
	    if(document.getElementById("label"+i)!=null && document.getElementById("label"+i)!=undefined){
	        document.getElementById("label"+i).style.backgroundColor = "#CCCCCC";
	    }
	}
}
 
//���������Ű���Ϣ(����Űఴťʱ����)
var rowIndexArray = null;
function batchUpdateSchedule(){
    var idArr = document.getElementsByName("empId");
    var nameArr = document.getElementsByName("empName");
    var idStr = "";
    var nameStr = "";
    rowIndexArray = new Array();
    var count = 0;
    for(var i = 0; idArr!=null && i < idArr.length; i++){
        if(idArr[i].checked){
            rowIndexArray[count] = i;
            count++;
            idStr += idArr[i].value+",";
            if(hrm.common.navigatorIsIE()){  //if(getOs() == 'MSIE')�õ��ù�������         
                nameStr += nameArr[i+1].value+"  ";
            }else{
                nameStr += nameArr[i].value+"  ";
            }
        }
    }
    if(idStr == ""){
        alert("������ѡ��һ��Ա����");
        return false;
    }
    
    // ��ȡѡ��Ա���Ĺ��а�Σ���ʾ�������б��У� ����DWR����
    ExaminSchedule.getCommonShiftList(idStr, getCommonShiftCallback);
    
    initShiftMap();
    document.getElementById('batch_attdShift').value='0';
    document.getElementById('selectedDateArea').value = "";
    
    document.getElementById("td_batchEmpName").innerHTML = "Ա��������" + nameStr;
    //document.getElementById('dlgbatch_schedule_div').style.display="block"; 
    hrm.common.openDialog('dlgbatch_schedule_div');
}


//�޸ĳɹ��󣬿�ʼ�޸�ҳ������Ӧ��ε���ɫ��
function batchUpdateCallbackProc(idStr){
    var idArray = idStr!=null&&idStr.indexOf(',')>-1?idStr.split(','):null;
    var shiftId = "";
    var rowIndex = -1;
    var dayStr = "";
    var dayArr = null;
    var mapKeys = shiftMap.keys();
    var obj = null;
    for(var i=0; mapKeys!=null && i<mapKeys.length; i++){
    if(shiftMap.get(mapKeys[i])!=""){
        shiftId = mapKeys[i];
        dayStr = shiftMap.get(shiftId);
        dayArr = dayStr!=null&&dayStr.indexOf(",")>-1?dayStr.split(","):null;
        for(var j=0; idArray!=null && j<idArray.length; j++){
            if(idArray[j]==null || idArray[j]==''){
                continue;
            }
            rowIndex = rowIndexArray[j];
            for(var k = 0; dayArr!=null && k<dayArr.length; k++){
                if(dayArr[k]==null || dayArr[k]==''){
                    continue;
                }
                obj=document.getElementById(rowIndex+"-"+dayArr[k]);
			    if(mapKeys[i]!='0'){
			        obj.value = shiftNameMap.get(shiftId);
			        obj.style.backgroundColor='#'+shiftColorMap.get(shiftId);
			    }else{
			        obj.value = '';
			        obj.style.backgroundColor='#FFFFFF';
			    }
            }
        }
    }
    }
}

// ����Ա���Ĺ��а�Σ�
function getCommonShiftCallback(data){
    var obj = document.getElementById("batch_attdShift");
    for(var i=obj.options.length; obj!=null && i>0; i--){// ����գ�ֻ���¿հ�Σ� 
        obj.options[i] = null;
    }
    for(var i=0; data!=null&&i<data.length; i++){// �������ŵİ�Ĳ�����ʾ�������б��У�
        obj.options[obj.length] = new Option(data[i].attsName, data[i].id);
    }
}

//select����ѡ�е�ǰ���,����ͬ��ɫ
function showDaysOfCurrentShift(shiftId){
    var dayStr = shiftMap.get(shiftId);
    document.getElementById('selectedDateArea').value = dayStr;
    document.getElementById('currShiftColorSpan').style.background="#"+shiftColorMap.get(shiftId);
}


//���ѡ�и�����ʱ �������ڶ�Ӧ��μ��뵽��Ӧ���Ű���
function addDayToCurrentShift(obj, day){
    //�ȴ����е�shift��dayStr��ɾ��Ҫ��ӵ�day��
    deleteDayFromAllShift(day);
    var currShiftId = document.getElementById('batch_attdShift').value;
    var dayStr = shiftMap.get(currShiftId);
    
    var flag = false;
    var dayArr = dayStr.split(",");
    for(var i = 0; i < dayArr.length; i ++){
        if(dayArr[i] == day){
            flag = true;
            break;
        }
    }
    
    if(!flag){
	    dayStr += day+",";
	    shiftMap.put(currShiftId, dayStr);
	    document.getElementById('selectedDateArea').value = dayStr;
    }
    
    //������ѡ�����ɫ��
    obj.style.backgroundColor = "#"+shiftColorMap.get(currShiftId);
}

//�����е�shift��dayStr��ɾ��Ҫ��ӵ�day��
function deleteDayFromAllShift(day){
    var mapKeys = shiftMap.keys();
    var tempStr = "";
    for(var i = 0; mapKeys!=null && i<mapKeys.length; i++){
        tempStr = shiftMap.get(mapKeys[i]);
        tempStr = deleteNumFormStr(tempStr, day);
        shiftMap.put(mapKeys[i],tempStr);
    }
}

//����valueֵ
function deleteNumFormStr(str, num){
    var tempArr = str!=null&&str!=""&&str.indexOf(",")>-1 ? str.split(",") : null;
    var value = "";
    for(var i = 0; tempArr!=null && i<tempArr.length; i++){
        if(tempArr[i]!=null && tempArr[i]!="" && tempArr[i]!=""+num){
            value += tempArr[i]+","
        }
    }   
    return value;
}

//hrm.common.keyCodeIsNumber(hrm.common.getKeyCode(event),false,false,true);
//��ֵ��֤
function checkAreaInput(event, obj){
	event = event ? event : (window.event ? window.event : null);
	var code = event.keyCode ? event.keyCode : event.which;
	if(!((code >= 48 && code <= 57) || code==46 || code==8 || code==188)){
	    alert("ֻ�������ֻ�,");
	    event.returnValue = false;
	}
}

//�ύ��������(����Ű��еı��水ť)��
function batchSaveAttend(){
    var idArr = document.getElementsByName("empId");
    var nameArr = document.getElementsByName("empName");
    var idStr = "";
    var nameStr = "";
    for(var i = 0; idArr!=null && i < idArr.length; i++){
        if(idArr[i].checked){
            idStr += idArr[i].value+",";
             if(hrm.common.navigatorIsIE()){  //if(getOs() == 'MSIE')�õ��ù�������
                //alert('navigatorIsIE');
                nameStr += nameArr[i+1].value+"  ";
            }else{
                nameStr += nameArr[i].value+"  ";
            }
        }
    }
    
    var yearMonth = document.getElementById('year').value+'-'+document.getElementById('month').value;
    
    var mapKeys = shiftMap.keys();
    var shif_days_Str = "";
    var temp = "";
    for(var i = 0; mapKeys!=null && i<mapKeys.length; i ++){
         if(shiftMap.get(mapKeys[i])!=""){
             temp = mapKeys[i]+":"+shiftMap.get(mapKeys[i]);
             shif_days_Str +=temp+"##";
         }
    }
    function batchUpdateCallback(info){
        if(info != null && info == 'succ'){
            batchUpdateCallbackProc(idStr);
            successMsg("message", "�Ű�ɹ���");
        }else if(info!=null && info != 'succ'){
            //batchUpdateCallbackProc(idStr);
            errMsg("message", info);
        }else if(info!=null && info=='noauth'){
            successMsg("message", "Ȩ�޴���");
        }
    }
    ExaminSchedule.batchUpdateSchedule(document.getElementById('dayFrom').value, document.getElementById('dayTo').value, idStr, shif_days_Str, batchUpdateCallback);
    hrm.common.closeDialog('dlgbatch_schedule_div');
	disSelectAll();	
}

//Ĭ��ȫ����ѡ��(�򹳴����)
function disSelectAll(){
	var obj=document.getElementsByName("empId");
	for(i=0;i<obj.length;i++){
		if(obj[i].checked==true){
			obj[i].checked=false;
		}
	}
}

//��һ�����Ű��ѯ(ҳ���ϵ����һ����)
function nextday(){
	var date = document.getElementById("attendDate").value;
	var dates = date.split("-");
	document.getElementById("attendDate").value = MonthAddDiff(dates[0]*1,dates[1]*1,1);
	document.getElementById("up").disabled = true;
	document.getElementById("down").disabled = true;
	document.getElementById('searchOrExport').value = "";
	document.examinScheduleSearch.submit(); //�ύ��
}
	
//ǰһ�����Ű��ѯ(ҳ���ϵ����һ����)
function beforeday(){
	var date = document.getElementById("attendDate").value;
	var dates = date.split("-");
	
	document.getElementById("attendDate").value = MonthAddDiff(dates[0]*1,dates[1]*1,-1);
	document.getElementById("up").disabled = true;
	document.getElementById("down").disabled = true;
	document.getElementById('searchOrExport').value = "";
	document.examinScheduleSearch.submit();	
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

//��year+month���¸�ֵ(����ϸ��»��¸���ʱ)
function changeDate(obj){
	var str = obj.value.split("-");
	document.getElementById("year").value = str[0];
	document.getElementById("month").value = str[1];
}

//���ݵ�������
function export_check(value){
    document.getElementById('searchOrExport').value = value;
    document.forms[0].submit();
}
 
// ��ѯǰ��յ�����ť��ֵ 
function clearExport(){
    document.getElementById('searchOrExport').value = "";
    document.forms[0].submit();
}

//������Ļ��С���б����
function resetTableWidth(){
	var width = document.body.scrollWidth-165;
	document.getElementById('scrollDiv').style.width=width;
}
resetTableWidth();


//�����Ű��¼��Ա������������ɫ
insertcolor();
function insertcolor(){
	<s:iterator value="scheduleList" status="index3">
		<s:if test="empshiftShiftNo!=null">
		//alert("<s:property value="attdShift.attsName"/>");
		var row=getRow("<s:property value="empshiftEmpNo.id"/>");
		var col="<s:property value="empshiftDate"/>";
		col=col.substr(8);
		if(col.indexOf("0")==0){
			col=col.substr(1);
		}
		var obj=document.getElementById(row+"-"+col);
		obj.value="<s:property value="empshiftShiftNo.attsShortName"/>";			
		obj.style.backgroundColor='#<s:property value="empshiftShiftNo.attsColor"/>';
		//alert(row+"-"+col+":::"+obj.value);
		</s:if>
	</s:iterator>
}
//����Ա���ŵõ��к�
function getRow(empId){
	var obj=document.getElementsByName("empId");
	for(i=0;i<obj.length;i++){
		if(obj[i].value==empId){
			return i;
		}
	}
	return;
}
hrm.common.initDialog('dlgShiftList');//�Զ�����
hrm.common.initDialog('dlgbatch_schedule_div');
	
<!-- ����һϵ�з���Ϊ��������һ��Ű��¼�  ���ҳ���е�С����,��ȡ��β���������Ű�-->
//��굥��ʱ�����Ű��б�
document.onclick = hideShiftList;
function hideShiftList(){
    document.getElementById('schedule_shift_list_div').style.display="none";
}
//�����Ҽ��˵�
document.oncontextmenu = MySchedule;
var shiftCount = 0;
function MySchedule(event){
    event = event ? event : (window.event ? window.event : null);
    var obj;
    obj = isIE() ? event.srcElement : event.target;
    
    if(obj==null ||obj.name!='schedule'){
        return;
    }
    // ������һ��Ű����ڿ�ִ�����²�����
    obj.focus();
    var objId = obj.id;
    var idArr = objId.split("-");
    //��ȡ�������
    var MouseX=event.clientX;
    var MouseY=event.clientY;
    showUpdateDiv(idArr[0], idArr[1], MouseX, MouseY);
    return false;
}
 
//��ʾ�����Ű�div��
function showUpdateDiv(row, col, MouseX, MouseY){
    document.getElementById("oneScheduleRowId").value = row;
	var empId=document.getElementsByName("empId")[row].value;
    if(hrm.common.navigatorIsIE()){  //if(getOs() == 'MSIE')�õ��ù�������        
		row++;
	}
		
	<!-- Ϊ��ʾ����Ҫ��Ӧ���ܽ�ֹ�������,�����յ�ֵ�ж���������һ���� jet, 2008-12-05-->
	var yearMonth = "";
	if(document.getElementById('monthFlag').value == '0'){
		if(parseInt(col) >= parseInt(document.getElementById('dayFlag').value)){
		    yearMonth = document.getElementById('dayFrom').value.substring(0, document.getElementById('dayFrom').value.lastIndexOf("-"));
		}else{yearMonth = document.getElementById('attendDate').value; }
	}
	if(document.getElementById('monthFlag').value == '1'){
		if(parseInt(col) < parseInt(document.getElementById('dayFlag').value)){
		    yearMonth = document.getElementById('dayTo').value.substring(0, document.getElementById('dayTo').value.lastIndexOf("-"));
		}else{yearMonth = document.getElementById('attendDate').value; }
	}
	var scheduleDate;
	if(col<10){
		scheduleDate = yearMonth+"-0" + col;
	}else{
		scheduleDate=yearMonth+"-" + col;
	}
	document.getElementById("oneScheduleDate").value = scheduleDate;
	document.getElementById("oneScheduleEmpId").value = empId;
	
	// ��ȡ��ǰԱ�������ŵİ�Σ���ʾ���б��У�
    ExaminSchedule.getCommonShiftList(empId+",", getShiftCallback);
	
	// �������λ����ʾdiv��
	//��ȡDiv(���������)
    var shiftlist = document.getElementById("schedule_shift_list_div");
    //��ȡ����Ⱥ͸߶�
    var PanelWidth=parseInt(shiftlist.style.width.replace("px",""));
    var PanelHeight=parseInt(shiftlist.style.height.replace("px",""));
    // alert(PanelHeight + " : " + shiftCount);
    // ��ȡ����Ч��֮���x��y���ꣻ
    var scrll_left=document.body.scrollLeft;
	var scrll_top=document.body.scrollTop;
	var positionX = MouseX + scrll_left;
	var positionY = MouseY + scrll_top - 90;
    //��ȡ��ҳ���ڿ�Ⱥ͸߶�
    var WindowWidth=document.body.offsetWidth;
    var WindowHeight=document.body.offsetHeight;
    // alert("positionY+shiftCount*20: " + (positionY+shiftCount*20) + "; WindowHeight:"+WindowHeight);
    positionX = (positionX+PanelWidth)>=WindowWidth ? positionX-PanelWidth : positionX;
    positionY = (positionY+shiftCount*20 + 90)>=WindowHeight ? positionY-shiftCount*20 : positionY;
	
    shiftlist.style.left = positionX;
    shiftlist.style.top = positionY;
    shiftlist.style.display="block";
}

// ��ʾ��Ա�������ŵİ�Σ�
function getShiftCallback(data){
    // �����
    var bodyObj = document.getElementById("shiftItems");
    bodyObj.innerHTML = "";

    var content = "";
    for(var i=0; i<data.length; i++){
        var shiftId = data[i].id;
        var oneRow = "<li><a href=\"#\" onclick=\"scheduleOne('"+shiftId+"');\">";
        oneRow += "<span style='background:#"+data[i].attsColor+"'>"+data[i].attsShortName+"</span>";
        oneRow += "&nbsp;&nbsp;"+""+data[i].attsName;
        oneRow += "</a></li>";
        content += oneRow;
    }
    
    //�������У�
    var clearRow = "<li><a href=\"#\" onclick=\"scheduleOne('0');\">";
    clearRow += "<span>&nbsp;&nbsp</span>";
    clearRow += "&nbsp;&nbsp;"+"���";
    clearRow += "</a></li>";
    content += clearRow;
    
    bodyObj.innerHTML = content;
    shiftCount = data.length+1;
    // document.getElementById("schedule_shift_list_div").style.height=shiftCount*25+"px";
}

function scheduleOne(shiftId){
    var empId = document.getElementById("oneScheduleEmpId").value;
    var scheduleDate = document.getElementById("oneScheduleDate").value;
    var params={id:shiftId};
    ExaminSchedule.updateOneSchedule(empId,scheduleDate,params,updatecallback);
    document.getElementById('schedule_shift_list_div').style.display="none";
}

function updatecallback(info) {
    	var row = document.getElementById('oneScheduleRowId').value;
    	var scheduleDate = document.getElementById("oneScheduleDate").value;
		var col=scheduleDate.substr(8);
		if(col.indexOf("0")==0){
			col=col.substr(1);
		}			
		var obj=document.getElementById(row+"-"+col);
		
	    if(info!=null){
		    var conflicts = info.flag;
		    if(conflicts == null){
		    if(info.id=='0' || info.id==0){ // ��հ��
		        obj.value="";			
				obj.style.backgroundColor="";
		    }else{ // �Ű�����
		        obj.value=info.attsShortName;			
				obj.style.backgroundColor='#'+info.attsColor;
		    }
		    successMsg("message", "�Ű�ɹ���");
		    }else if(conflicts == 'noauth'){
		        errMsg("message", "Ȩ�޴���");
		    }else{
		        errMsg("message", conflicts);
		    }
		 }else{
		        errMsg("message", "�Ű�ʧ�ܣ�");
		 }
}
<!-- ����һ��Ű��¼����� -->
</script>
<jsp:include flush="true" page="../io/div_upload.jsp"></jsp:include>
</body>