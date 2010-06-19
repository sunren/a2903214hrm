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
<title>排班管理</title>
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
	<s:param name="pagetitle"  value="'排班管理('+dayFrom1+'到'+ dayTo1+')'" />
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
			<s:textfield label="员工" id="empName" name="emp" size="14" maxlength="64"/>
			<td align="right">组织单元:</td>
		    <td>
		  	  <s:orgselector id="orgselector1" name="employee.empDeptNo.departmentName" hiddenFieldName="employee.empDeptNo.id"/>
		    </td>
			<s:select label="工作地区" list="locations" listKey="id" name="employee.empLocationNo.id"
				listValue="locationName" multiple="false" emptyOption="true" value="employee.empLocationNo.id" size="1" />
			<s:select label="用工形式" name="employee.empType.id" list="empTypes" listKey="id"
					 listValue="emptypeName" multiple="false" emptyOption="true" value="employee.empType.id" size="1" />
		</tr>
	  </table>
    </td>
    
    <td>
		<input title="[Alt+F]" accesskey="F" name="sub_button" id="submit_button" type="button" size="4" class="button"  value="查询" onclick="clearExport();">
		<input title="[Alt+C]" accesskey="C" name="clear_button" class="button" type="button" onClick="window.location='examinScheduleSearch.action';" value="重置">
		<br/> 
	</td>
  </tr>
</table>
<br/>
<table width="100%" border="0" cellspacing="0" cellpadding="0" >
  <tr>
    <td>
    <!-- 为显示日期要对应汇总截止日期添加 -->
    <input type="hidden" id="dayFrom" name="dayFrom" value="<s:property value="dayFrom"/>"/>
    <input type="hidden" id="dayTo" name="dayTo" value="<s:property value="dayTo"/>"/>
    <input type="hidden" id="monthFlag" name="monthFlag" value="<s:property value="monthFlag"/>"/>
    <input type="hidden" id="dayFlag" name="dayFlag" value="<s:property value="dayFlag"/>"/>
    
    <input type="hidden" id="year" name="year" value="<s:property value="year" />" />
	<input type="hidden" id="month" name="month" value="<s:property value="month" />" />
	<input type="button" class="button" id="down" name="down" value="前一月" onclick="beforeday();">
	<input type="text" id="attendDate" name="attendDate" value="<s:property value="year" />-<s:property value="month" />" size="8" maxlength="7" onChange="changeDate(this);"/>
	<input type="button" class="button" id="up" name="up" value="后一月" onclick="nextday();">
	<s:if test="flag=='true'">
	    <input class="button" type="button" value="&nbsp;排班&nbsp;" name="import" onclick="batchUpdateSchedule();"/>
	</s:if>
	<s:else>
	    <input class="button" type="button" value="&nbsp;排班&nbsp;" name="import" onclick="batchUpdateSchedule();" disabled="true"/>
	</s:else>
    
    <input class="button" type="button" value="班次列表" name="import" onclick="hrm.common.openDialog('dlgShiftList')"/>
    
   	<ty:auth auths="401 or 411,3 or 411,2">
		<input class="button" type="button" onclick="initDivImmUpload('IExaminShift','<s:property value="year" />,<s:property value="month" />');" value="数据导入"/>
    	<input type="button" class="button" id="btnOutput" value="数据导出" onclick="export_check('export');"/>
	</ty:auth>
    </td>
    <td align="right">本次查询共得到<s:property value="page.totalRows" />条排班记录&nbsp;</td>
  </tr>
</table>

<div id="scrollDiv" style="overflow:scroll;width:600;">
<table id="attend_schedule_table" cellpadding="0" cellspacing="0" width="100%" border="0" class="gridtableList">
	     <tr>
			<th width="10">
			    <s:if test="flag=='true'">
	                <input id="changIds" name="changIds" class="checkbox" type="checkbox" onclick="hrm.common.checkAllByName('empId','changIds');" value="选中所有">
	            </s:if>
	            <s:else>
	                <input id="changIds" name="changIds" class="checkbox" type="checkbox" onclick="hrm.common.checkAllByName('empId','changIds');" value="选中所有" disabled="true">
	            </s:else>
			</th>	     
	     	<th nowrap="nowrap">
	     		<a href="#" onclick="hrm.common.order_submit('empName','examinScheduleSearch');">
	     		员工姓名<img src='../resource/images/arrow_.gif' width='8' height='10' id='empName_img'></a>
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
TITLE="员工编号：<s:property value='empDistinctNo'/>
用工形式：<s:property value='%{getEmpType(empType.id)}'/>
所属地区：<s:property value='%{getLocName(empLocationNo.id)}'/> 
所属部门：<s:property value='%{getDepName(empDeptNo.id)}'/> 
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
			<tr><!-- 不存在员工调薪信息 -->
				<td align="center" colspan="33">
					
					无符合条件的排班记录! 
					
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

<!-- 批量设置排班DIV    修改为排班Dialog实现-->
<div id="dlgbatch_schedule_div" title="批量排班" style="display:none;width:360">  
	<table id="batchConfigTable" width="100%"  border="0" cellspacing="2" cellpadding="0" class="gridtableList">
	    <tr>
			<td align="left" colspan="2" id="td_batchEmpName">
				
			</td>
		</tr>
		<tr>
		    <td align="left">
		         请选择班次：
		    </td>
		    <td align="left">
		        <select id="batch_attdShift" name="attdShift1.id" onchange="showDaysOfCurrentShift(this.value);">
		            <option value="0" selected="selected">清空</option>
		        </select>
		        <span id="currShiftColorSpan" width="10px">&nbsp;</span>
		     </td>
		</tr>
		<tr>
		    <td align="center" colspan="2">
		        <table border="0" cellspacing="1" cellpadding="1">
		            <tr><td align="center">日</td><td align="center">一</td><td align="center">二</td><td align="center">三</td><td align="center">四</td><td align="center">五</td><td align="center">六</td></tr>
		            <%
		            int day = 0, index = 0;
		            List dayList = (List)request.getAttribute("dayList");  //Action中没找到？什么意思？
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
				<input type="button" onclick="batchSaveAttend();" value="保存">
				<input type="button" onclick="hrm.common.closeDialog('dlgbatch_schedule_div');" value="关闭">
			</td>
		</tr>
	</table>
	
	<iframe scrolling="no" style="position:absolute;z-index:-1;width:expression(this.parentNode.offsetWidth-2);height:expression(this.parentNode.offsetHeight-2);top:0px;left:0px;width: 330px; height: 120px; " frameborder="0"></iframe>
</div>

<!-- 班次列表静态dialog -->
<div id="dlgShiftList" title="班次列表" style="display:none;width:460">	
  <table id="shiftListTable" width="100%"  border="0" cellspacing="2" cellpadding="0" class="gridtableList" >
   <tr>
    <th nowrap="nowrap">班次名称</th>
    <th nowrap="nowrap">班次代码</th>
    <th nowrap="nowrap">班次时间段</th>
    <th nowrap="nowrap">工时</th>
    <th nowrap="nowrap">显示颜色</th>
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
    <input type="button" name="close" value="关闭" onclick="hrm.common.closeDialog('dlgShiftList')"/>
 </center>
</div>
<!-- 班次列表静态dialog end -->
 
<!-- 右键排班列表显示开始 -->
<div id ="schedule_shift_list_div" class="floating_layer" style="position:absolute;left:280px;top:220px;z-index:4;solid;width:120px;display:none;"  >
	<input type="hidden" id="oneScheduleRowId" name="oneScheduleRowId"/>
	<input type="hidden" id="oneScheduleEmpId" name="oneScheduleEmpId"/>
	<input type="hidden" id="oneScheduleDate" name="oneScheduleDate"/>
    <menu id="shiftItems">
    
    </menu>
</div>
<!-- 右键排班列表显示结束 -->


<script type="text/javascript"> 
//自定义HashMap数据结构
function HashMap()   
 {   
     /** Map 大小 **/  
     var size = 0;   
     /** 对象 **/  
     var entry = new Object();   
        
     /** 存 **/  
     this.put = function (key , value)   
     {   
         if(!this.containsKey(key))   
         {   
             size ++ ;   
         }   
         entry[key] = value;   
     }           
     /** 取 **/  
     this.get = function (key)   
     {   
         return this.containsKey(key) ? entry[key] : null;   
     }           
     /** 删除 **/  
     this.remove = function ( key )   
     {   
         if( this.containsKey(key) && ( delete entry[key] ) )   
         {   
             size --;   
         }   
     }           
     /** 是否包含 Key **/  
     this.containsKey = function ( key )   
     {   
         return (key in entry);   
     }           
     /** 是否包含 Value **/  
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
     /** 所有 Value **/  
     this.values = function ()   
     {   
         var values = new Array();   
         for(var prop in entry)   
         {   
             values.push(entry[prop]);   
         }   
         return values;   
     }           
     /** 所有 Key **/  
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
     /* 清空 */  
     this.clear = function ()   
     {   
         size = 0;   
         entry = new Object();   
     }   
 } 


//初始化每个班次对应的日期字符串；批量排班时用到；jet 2008-11-18
var shiftMap = null;
var shiftColorMap = null; //(在其他方法中调用，班次颜色设置)
var shiftNameMap = null; //必须设置为公用变量
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
	//同时将div中带有颜色的项初始化；
	document.getElementById('currShiftColorSpan').style.backgroundColor = "#FFFFFF";
	for(var i = 1; i < 32; i++){
	    if(document.getElementById("label"+i)!=null && document.getElementById("label"+i)!=undefined){
	        document.getElementById("label"+i).style.backgroundColor = "#CCCCCC";
	    }
	}
}
 
//批量设置排班信息(点击排班按钮时调用)
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
            if(hrm.common.navigatorIsIE()){  //if(getOs() == 'MSIE')该调用公共方法         
                nameStr += nameArr[i+1].value+"  ";
            }else{
                nameStr += nameArr[i].value+"  ";
            }
        }
    }
    if(idStr == ""){
        alert("请至少选择一名员工！");
        return false;
    }
    
    // 获取选中员工的共有班次，显示在下拉列表中， 调用DWR方法
    ExaminSchedule.getCommonShiftList(idStr, getCommonShiftCallback);
    
    initShiftMap();
    document.getElementById('batch_attdShift').value='0';
    document.getElementById('selectedDateArea').value = "";
    
    document.getElementById("td_batchEmpName").innerHTML = "员工姓名：" + nameStr;
    //document.getElementById('dlgbatch_schedule_div').style.display="block"; 
    hrm.common.openDialog('dlgbatch_schedule_div');
}


//修改成功后，开始修改页面上相应班次的颜色；
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

// 回显员工的共有班次；
function getCommonShiftCallback(data){
    var obj = document.getElementById("batch_attdShift");
    for(var i=obj.options.length; obj!=null && i>0; i--){// 先清空，只留下空班次； 
        obj.options[i] = null;
    }
    for(var i=0; data!=null&&i<data.length; i++){// 将允许排的班的并集显示在下拉列表中；
        obj.options[obj.length] = new Option(data[i].attsName, data[i].id);
    }
}

//select下拉选中当前班次,赋不同颜色
function showDaysOfCurrentShift(shiftId){
    var dayStr = shiftMap.get(shiftId);
    document.getElementById('selectedDateArea').value = dayStr;
    document.getElementById('currShiftColorSpan').style.background="#"+shiftColorMap.get(shiftId);
}


//点击选中该日期时 将该日期对应班次加入到相应的排班中
function addDayToCurrentShift(obj, day){
    //先从所有的shift的dayStr中删除要添加的day；
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
    
    //设置所选框的颜色；
    obj.style.backgroundColor = "#"+shiftColorMap.get(currShiftId);
}

//从所有的shift的dayStr中删除要添加的day；
function deleteDayFromAllShift(day){
    var mapKeys = shiftMap.keys();
    var tempStr = "";
    for(var i = 0; mapKeys!=null && i<mapKeys.length; i++){
        tempStr = shiftMap.get(mapKeys[i]);
        tempStr = deleteNumFormStr(tempStr, day);
        shiftMap.put(mapKeys[i],tempStr);
    }
}

//返回value值
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
//数值验证
function checkAreaInput(event, obj){
	event = event ? event : (window.event ? window.event : null);
	var code = event.keyCode ? event.keyCode : event.which;
	if(!((code >= 48 && code <= 57) || code==46 || code==8 || code==188)){
	    alert("只允许数字或,");
	    event.returnValue = false;
	}
}

//提交批量更新(点击排班中的保存按钮)；
function batchSaveAttend(){
    var idArr = document.getElementsByName("empId");
    var nameArr = document.getElementsByName("empName");
    var idStr = "";
    var nameStr = "";
    for(var i = 0; idArr!=null && i < idArr.length; i++){
        if(idArr[i].checked){
            idStr += idArr[i].value+",";
             if(hrm.common.navigatorIsIE()){  //if(getOs() == 'MSIE')该调用公共方法
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
            successMsg("message", "排班成功！");
        }else if(info!=null && info != 'succ'){
            //batchUpdateCallbackProc(idStr);
            errMsg("message", info);
        }else if(info!=null && info=='noauth'){
            successMsg("message", "权限错误！");
        }
    }
    ExaminSchedule.batchUpdateSchedule(document.getElementById('dayFrom').value, document.getElementById('dayTo').value, idStr, shif_days_Str, batchUpdateCallback);
    hrm.common.closeDialog('dlgbatch_schedule_div');
	disSelectAll();	
}

//默认全部不选中(打钩处清空)
function disSelectAll(){
	var obj=document.getElementsByName("empId");
	for(i=0;i<obj.length;i++){
		if(obj[i].checked==true){
			obj[i].checked=false;
		}
	}
}

//下一个月排班查询(页面上点击下一个月)
function nextday(){
	var date = document.getElementById("attendDate").value;
	var dates = date.split("-");
	document.getElementById("attendDate").value = MonthAddDiff(dates[0]*1,dates[1]*1,1);
	document.getElementById("up").disabled = true;
	document.getElementById("down").disabled = true;
	document.getElementById('searchOrExport').value = "";
	document.examinScheduleSearch.submit(); //提交表单
}
	
//前一个月排班查询(页面上点击上一个月)
function beforeday(){
	var date = document.getElementById("attendDate").value;
	var dates = date.split("-");
	
	document.getElementById("attendDate").value = MonthAddDiff(dates[0]*1,dates[1]*1,-1);
	document.getElementById("up").disabled = true;
	document.getElementById("down").disabled = true;
	document.getElementById('searchOrExport').value = "";
	document.examinScheduleSearch.submit();	
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

//对year+month重新赋值(点击上个月或下个月时)
function changeDate(obj){
	var str = obj.value.split("-");
	document.getElementById("year").value = str[0];
	document.getElementById("month").value = str[1];
}

//数据导出方法
function export_check(value){
    document.getElementById('searchOrExport').value = value;
    document.forms[0].submit();
}
 
// 查询前清空导出按钮的值 
function clearExport(){
    document.getElementById('searchOrExport').value = "";
    document.forms[0].submit();
}

//根据屏幕大小计列表框宽度
function resetTableWidth(){
	var width = document.body.scrollWidth-165;
	document.getElementById('scrollDiv').style.width=width;
}
resetTableWidth();


//对有排班记录的员工填上配置颜色
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
//根据员工号得到行号
function getRow(empId){
	var obj=document.getElementsByName("empId");
	for(i=0;i<obj.length;i++){
		if(obj[i].value==empId){
			return i;
		}
	}
	return;
}
hrm.common.initDialog('dlgShiftList');//自动加载
hrm.common.initDialog('dlgbatch_schedule_div');
	
<!-- 以下一系列方法为处理鼠标右击排班事件  点击页面中的小窗口,获取班次并可以添加排班-->
//鼠标单击时隐藏排班列表；
document.onclick = hideShiftList;
function hideShiftList(){
    document.getElementById('schedule_shift_list_div').style.display="none";
}
//禁用右键菜单
document.oncontextmenu = MySchedule;
var shiftCount = 0;
function MySchedule(event){
    event = event ? event : (window.event ? window.event : null);
    var obj;
    obj = isIE() ? event.srcElement : event.target;
    
    if(obj==null ||obj.name!='schedule'){
        return;
    }
    // 如果是右击排班日期框，执行以下操作；
    obj.focus();
    var objId = obj.id;
    var idArr = objId.split("-");
    //获取鼠标坐标
    var MouseX=event.clientX;
    var MouseY=event.clientY;
    showUpdateDiv(idArr[0], idArr[1], MouseX, MouseY);
    return false;
}
 
//显示单个排班div；
function showUpdateDiv(row, col, MouseX, MouseY){
    document.getElementById("oneScheduleRowId").value = row;
	var empId=document.getElementsByName("empId")[row].value;
    if(hrm.common.navigatorIsIE()){  //if(getOs() == 'MSIE')该调用公共方法        
		row++;
	}
		
	<!-- 为显示日期要对应汇总截止日期添加,根据日的值判断其属于哪一个月 jet, 2008-12-05-->
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
	
	// 获取当前员工可以排的班次，显示在列表中；
    ExaminSchedule.getCommonShiftList(empId+",", getShiftCallback);
	
	// 在鼠标点击位置显示div；
	//获取Div(下面简称面板)
    var shiftlist = document.getElementById("schedule_shift_list_div");
    //获取面板宽度和高度
    var PanelWidth=parseInt(shiftlist.style.width.replace("px",""));
    var PanelHeight=parseInt(shiftlist.style.height.replace("px",""));
    // alert(PanelHeight + " : " + shiftCount);
    // 获取滚动效果之后的x、y坐标；
    var scrll_left=document.body.scrollLeft;
	var scrll_top=document.body.scrollTop;
	var positionX = MouseX + scrll_left;
	var positionY = MouseY + scrll_top - 90;
    //获取网页窗口宽度和高度
    var WindowWidth=document.body.offsetWidth;
    var WindowHeight=document.body.offsetHeight;
    // alert("positionY+shiftCount*20: " + (positionY+shiftCount*20) + "; WindowHeight:"+WindowHeight);
    positionX = (positionX+PanelWidth)>=WindowWidth ? positionX-PanelWidth : positionX;
    positionY = (positionY+shiftCount*20 + 90)>=WindowHeight ? positionY-shiftCount*20 : positionY;
	
    shiftlist.style.left = positionX;
    shiftlist.style.top = positionY;
    shiftlist.style.display="block";
}

// 显示该员工可以排的班次；
function getShiftCallback(data){
    // 先清空
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
    
    //添加清空行；
    var clearRow = "<li><a href=\"#\" onclick=\"scheduleOne('0');\">";
    clearRow += "<span>&nbsp;&nbsp</span>";
    clearRow += "&nbsp;&nbsp;"+"清空";
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
		    if(info.id=='0' || info.id==0){ // 清空班次
		        obj.value="";			
				obj.style.backgroundColor="";
		    }else{ // 排班或调班
		        obj.value=info.attsShortName;			
				obj.style.backgroundColor='#'+info.attsColor;
		    }
		    successMsg("message", "排班成功！");
		    }else if(conflicts == 'noauth'){
		        errMsg("message", "权限错误！");
		    }else{
		        errMsg("message", conflicts);
		    }
		 }else{
		        errMsg("message", "排班失败！");
		 }
}
<!-- 鼠标右击排班事件结束 -->
</script>
<jsp:include flush="true" page="../io/div_upload.jsp"></jsp:include>
</body>