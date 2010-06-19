<%@page pageEncoding="GBK" contentType="text/html; charset=GBK" %>
<%@taglib prefix="s" uri="webwork"%>
<%@taglib prefix="ty" uri="/tengsource"%>
<!--
 =========================================================
' File:search_attenddaily.jsp
' Auth:Xie
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
<s:head theme="ajax"/>
<link href="../resource/css/style.css" rel="stylesheet" type="text/css" />
<jsp:include flush="true" page="/sitemesh/jsPackage.jsp"></jsp:include>
<script type="text/javascript" src="../dwr/interface/ExaminSchedule.js"></script>
<!-- jQuery 不能删除--> 
<script type="text/javascript" src="../resource/js/jquery/jquery.tablesorter.js"></script>
<script type="text/javascript" src="../resource/js/jquery/jquery.tableEditor.js"></script>
<!-- 颜色选择器 -->
<link rel="stylesheet" href="../resource/js/report/js_color_picker_v2/js_color_picker_v2.css" media="screen">
<script type="text/javascript" src="../resource/js/report/js_color_picker_v2/color_functions.js"></script>
<script type="text/javascript" src="../resource/js/report/js_color_picker_v2/js_color_picker_v2.js"></script>
</head>
<body>
<s:component template="bodyhead">
</s:component>
<span class="errorMessage" id="errMsg" name="errMsg"></span>

<s:form id="examinShiftSearch" name="examinShiftSearch" action="examinShiftSearch" namespace="/examin" method="POST">
<s:token></s:token>
<s:hidden id="empIdString" name="empIdString"/>
<s:hidden id="empNameString" name="empNameString"/>
<s:hidden id="operationId" name="operationId"/>

<!-- 选择某状态的班次(启用与未启用) --> 
<table border="0" width="100%">
  <tr><td align="right" valign="bottom">
      状态：<s:select id="resultStatus" name="resultStatus" value="resultStatus"
		 list="#{1:'启用', 0:'未启用'}" emptyOption="false" onchange="document.forms[0].submit();"/>
      &nbsp;&nbsp;
  </td></tr>
</table>
<!-- 班次信息表格 --> 
<table id="shift_table" cellspacing="1" cellspacing="0" class="tablesorter" width="85%">
	<thead>
	     <tr align="center">    
	        <th nowrap="nowrap" style="display:none">&nbsp;</th>
	     	<th nowrap="nowrap">班次名称</th>
	     	<th nowrap="nowrap">班次代码</th>
	     	<th nowrap="nowrap" style="display:none">&nbsp;</th>
	     	<th nowrap="nowrap">班次时间段</th>
	     	<th nowrap="nowrap">班次工时</th>		     		
	     	<th nowrap="nowrap" style="display:none">&nbsp;</th>
	     	<th nowrap="nowrap" style="display:none">&nbsp;</th>
	     	<th nowrap="nowrap" style="display:none">&nbsp;</th>
	     	<th nowrap="nowrap" style="display:none">&nbsp;</th>
	     	<th nowrap="nowrap">是否隔夜</th>
	     	<th nowrap="nowrap">颜色</th>
	     	<th nowrap="nowrap">默认</th>	
	     	<th nowrap="nowrap">班次状态</th>      		     		 	     		     	 		     	
	   </tr>
	 </thead>
	 <tbody id="examinShiftBody">
	   <s:if test="shiftList!=null">
	   	<s:iterator value="shiftList" status="index">
		<tr onmousedown="editObj=this;" onclick="editObj=this;" ondblclick="editObj=this;showUpdateDiv('update');">
			<td style="display:none;"><s:property value="id"/></td>
			<td align="center"><s:property value="attsName"/></td>
			<td align="center"><s:property value="attsShortName"/></td>
			<td style="display:none;"><s:property value="attsDesc"/></td>
			<td align="center"><s:property value="attsSession"/></td>
			<td align="center"><s:property value="formatBD(attsWorkingHour)"/></td>
			<td style="display:none;"><s:property value="attsNightShift"/></td>
			<td style="display:none;"><s:property value="attsColor"/></td>		
			<td style="display:none;"><s:property value="attsIsDefault"/></td>
			<td style="display:none;"><s:property value="attsSortId"/></td>	
			<td align="center"><s:if test="attsNightShift==0">否</s:if><s:else>是</s:else></td>
			<td align="center"><span id="colorbg" style="background-color:<s:property value="attsColor"/>">&nbsp;</span></td>
			<td align="center">
				<s:if test="attsIsDefault!=null && attsIsDefault==1">
				    <img onclick="convertDefault();" onmouseover="style.cursor='pointer';" id="defaultimg_yes" src="../resource/images/default_yes.png" border="0" ALT="默认">
				</s:if>
				<s:else>
				    <img onclick="convertDefault();" onmouseover="style.cursor='pointer';" id="defaultimg_no" src="../resource/images/default_no.png" border="0" ALT="非默认">
				</s:else>
			</td>
			<td style="display:none"><s:if test="attsStricked==1">1</s:if><s:else>0</s:else></td>
			<td style="display:none"><s:if test="attsStatus==1">1</s:if><s:else>0</s:else></td>
			<td align="center">
			    <s:if test="attsStatus!=null && attsStatus==1">
				    <img onclick="convertStatus();" onmouseover="style.cursor='pointer';" id="defaultimg_yes" src="../resource/images/default_yes.png" border="0" ALT="默认">
				</s:if>
				<s:else>
				    <img onclick="convertStatus();" onmouseover="style.cursor='pointer';" id="defaultimg_no" src="../resource/images/default_no.png" border="0" ALT="非默认">
				</s:else>
			</td>	
		</tr>
		</s:iterator>
		</s:if>
 		<s:else>
			<!--<tr>
				<td align="center" colspan="33">
					
					无符合条件的考情班次记录! 
					
				</td>
			</tr>-->
		</s:else>  
		</tbody>
</table>

<!-- 页面上操作按钮 -->
<div class="btnlink">
	<a href="#" onclick="showUpdateDiv('insert');">新增</a>
	<a href="#" onclick="del();">删除</a>
	<a href="#" onclick="updateRow();">修改</a>
	<a href="#" onclick="saveSort();">保存排序</a>
</div>
</s:form>

<!-- update Shift div begin 班次信息Dialog -->
<div id="update_Shift_div" title="考勤班次信息" align="left" style="width=400;display:none">
    <form id="shiftInfoForm" method="post">
    <input id="id" type="hidden" name="id"/>
    <input type="hidden" id="attsIsDefault" name="attsIsDefault">
    <input type="hidden" id="attsSortId" name="attsSortId">
		<table id="newShiftTable" width="100%"  border="0" cellspacing="1" cellpadding="0">
			<tr>
				<td>
					班次名称：
				</td>
				<td class="errorMessage">	
					<input type="text" id="attsName" name="attsName" value="" size="10" maxlength="32">
				</td>
				<td>
					班次代码：<span style="color:red;">*</span>
				</td>
				<td>
					<input type="text" id="attsShortName" name="attsShortName" value="" size="5" maxlength="2">
				</td>
			</tr>
			<tr>
				<td>
					班次描述：
				</td>
				<td colspan="3">	
					<input type="text" id="attsDesc" name="attsDesc" value="" size="52" maxlength="128">
				</td>				
			</tr>
			<tr>
				<td>
					班次时间段：<span style="color:red;">*</span>
				</td>
				<td class="errorMessage">	
					<input type="text" id="attsSession" name="attsSession" value="" size="25" maxlength="60">
				</td>
				<td>
					班次颜色：<span style="color:red;">*</span>
				</td>
				<td class="errorMessage">	
					<div style="width:103px;width/* */:/**/100px;width: /**/100px;height:16px;border:1px solid #7F9DB9;z-index:0;" lang="">
						<input onblur="this.style.color=this.value" type="text" maxlength="7" style="valign:top;width:73px;font-size:12px;border:0px;" id="attsColor" name="attsColor" size="10" id="rgb">
						<img src="../resource/js/report/js_color_picker_v2/images/select_arrow.gif" onmouseover="this.src='../resource/js/report/js_color_picker_v2/images/select_arrow_over.gif'" onmouseout="this.src='../resource/js/report/js_color_picker_v2/images/select_arrow.gif'" onclick="showColorPicker(this,document.getElementById('attsColor'))">
					</div>
				</td>
				
			</tr>
			<tr>
				<td>
					班次工时：
				</td>
				<td class="errorMessage">
					<input class="nothinginput" type="text" id="attsWorkingHour" name="attsWorkingHour" value="" size="5" maxlength="6" readonly>
				</td>
				<td>
					是否隔夜：
				</td>
				<td id="attsNightShift">

				</td>
			</tr>
			<tr>
			    <td>
			        是否限制：
			    </td>
			    <td>
			       <s:select id="attsStricked" name="attsStricked" value=""
					list="#{0:'否',1:'是'}" emptyOption="false" onchange="addStrict(this.value)" />
			    </td>
			    <td>
			        班次状态：
			    </td>
			    <td>
			       <s:select id="attsStatus" name="attsStatus" value=""
					list="#{0:'未启用',1:'启用'}" emptyOption="false" onchange="checkAllow(this.value)"/>
			    </td>
			</tr>
			<tr>
			   <td align="center" colspan="4">
			    <table border="0" id="restrict" style="display:none">
			    <tr><td valign="bottom">
			        <div class="btnlink">
			            <a href="#" onclick="accordToLoc()">按地区</a>
			            <a href="#" onclick="accordToDept()">按部门</a>
			        </div>
			    </td></tr>
			    <tr><td align="center" valign="top">
			    <s:optiontransferselect 
	             name="leftList"
	             leftTitle="未选中："
	             rightTitle="已选中："
	             list="locations"
	             multiple="true"
	             listKey="id"
	             listValue="%{empName+' '+ empDistinctNo}"
	             addToLeftLabel="向左移动"
	             addToRightLabel="向右移动"
	             cssStyle="width: 200px; height: 150px;"
	             emptyOption="true"
	             addAllToRightLabel=" >> "
	             addAllToLeftLabel=" << "
	             addToLeftLabel=" < " 
	             addToRightLabel=" > "
	             allowAddAllToLeft="true"
	             allowSelectAll="false"
	             allowUpDownOnLeft="false"
	             allowUpDownOnRight="false"
	             ondblclick="moveSelectedOptions(document.getElementById('leftList'), document.getElementById('rightList'), false, '');"
	             emptyOption="false"
	             doubleList="{}"
	             doubleName="rightList"
	             doubleEmptyOption="false"
	             doubleMultiple="true"
	             doubleCssStyle="width: 200px; height: 150px;"
	             doubleOndblclick="moveSelectedOptions(document.getElementById('rightList'), document.getElementById('leftList'), false, '');"
	            />
	            </td></tr>
	            </table>
			    </td>
			</tr>										
			<tr>
				<td align="center" colspan="4" align="center">
					<br>
					<input id="div_btnSubmit" name="hiddenName" type="button" onclick="saveShift();" value="保存">
					<input type="button" name="hiddenName" onclick="hrm.common.closeDialog('update_Shift_div');" value="关闭">
				</td>
			</tr>										
			</table>
	</form>
</div>

<script type="text/javascript" language="javascript">
model.simple.setParentIFrameFull("IFrame1"); // add by 金鑫（计算iframe高度）

var editObj = null; //操作行tr(公用变量)

//页面加载时初始化样式
function initTableCSS(tableId){
     $(".tablesorter tr").mousedown(function(){ 
     	changeCSS(tableId); 
       	$(this).addClass("click"); 
    }); 
}


//检查是否可以设置班次状态为 ‘停用’
function checkAllow(){
	if(editObj != null ){
		var aTd = editObj.cells;
	}
	var isDefault = aTd[8].innerHTML;
	var isStricted = aTd[13].innerHTML;
	var status = aTd[14].innerHTML;
	var globleDefault = 0;
	if(isStricted==0 && isDefault==1 && status==1){// 全局默认班次，由默认转为非默认时进行检查是否只剩这一个全局默认班次；
	    DWREngine.setAsync(false); // 设置为同步执行；
	    ExaminSchedule.getGlobalDefaultCount(globleDefaultCountCheck);
	    DWREngine.setAsync(true); // 同步执行完之后继续设置为异步执行；
	}

	
	function globleDefaultCountCheck(info){
	    globleDefault = info;
	}
	
	if(globleDefault == 1){
	   alert("该班次是全局默认班次，不能停用！");
	   var sel = document.getElementById('attsStatus');
	   for(var i=0;i<sel.options.length;i++){
			if(sel.options[i].text=="启用"){
				sel.options[i].selected=true;
			}
	   }
	   return false;
	}else return true;
}


/***当单击某一行时初始化始化表格样式***/
function changeCSS(tableId){
    var trs = document.getElementById(tableId).getElementsByTagName('tbody')[0].getElementsByTagName('tr');
    for(var i = 0;i < trs.length; i++){
        if(trs[i].className.indexOf('click')!=-1){
          trs[i].className=('');
        }
    }
}

//必填项输入校验
function checkInput(){
    var flag = true;
    if(document.getElementById('attsShortName').value==''){
        valid(document.getElementById('attsShortName'),'必填项!');
        flag = false;
    }
    if(document.getElementById('attsSession').value==''){
        valid(document.getElementById('attsSession'),'必填项!');
        flag = false;
    }
    if(document.getElementById('attsColor').value==''){
        valid(document.getElementById('attsColor'),'必填项');
        flag = false;
    }
    return flag;
}

//赋值"必填项"
function valid(obj,str){
	if (isIE()) {
		obj.replaceAdjacentText('afterEnd',str);
	}
	else{
    	$("#"+obj.id).after('<label class=errorMessage>' + str + '</lable>');
    }
   }

/***Jquery表格排序相关***/
function doSortTable(tableid,unsortcolumn){
  if($("#"+tableid+" tbody tr").length>0){
    $("#"+tableid).tableSorter({
        sortClassAsc: 'headerSortUp',       // class name for ascending sorting action to header
        sortClassDesc: 'headerSortDown',    // class name for descending sorting action to header
        headerClass: 'header',
        disableHeader: unsortcolumn
    },{
	exist: function (cache,index) {
		return false;
	}
 }).tableDnD();}
}


//修改时弹出Dialog
function updateRow(){   
    if(editObj == null){
        errMsg("errMsg", "请选择要修改的行！");
        return false;
    }else{
	    showUpdateDiv('update');
    }
}

// 新增或修改班次信息；
function showUpdateDiv(ope){
	// 清除左右选择框；
	clearList(document.getElementById("leftList")); //clearList()在下面
	clearList(document.getElementById("rightList"));
	//alert('showUpdateDiv');
	try{	
	    clearErr();//清除错误信息					
		var updatebtm=document.getElementById('div_btnSubmit');	//get current page form
		//reset the form  
		if( ope=='update' && editObj != null ){
			var aTd = editObj.cells;
			document.getElementById('id').value=aTd[0].innerHTML.trim();
			document.getElementById('attsName').value=aTd[1].innerHTML.trim();
			document.getElementById('attsShortName').value=aTd[2].innerHTML.trim();
			document.getElementById('attsDesc').value=aTd[3].innerHTML.trim();	
			document.getElementById('attsSession').value=aTd[4].innerHTML.trim();
			document.getElementById('attsWorkingHour').value=aTd[5].innerHTML.trim();
			document.getElementById('attsNightShift').innerHTML=aTd[10].innerHTML.trim();
			document.getElementById('attsColor').value="#"+aTd[7].innerHTML.trim();
			document.getElementById('attsIsDefault').value=aTd[8].innerHTML.trim();
			document.getElementById('attsSortId').value=aTd[9].innerHTML.trim();
			document.getElementById('attsStricked').value=aTd[13].innerHTML.trim();
			document.getElementById('attsStatus').value=aTd[14].innerHTML.trim();
			if(aTd[13].innerHTML.trim() == 1){
			    ExaminSchedule.getRelaString(document.getElementById('id').value, getExistRelaCallback);// 将右边添加已经存在的关联；
			}else{
			    addStrict(0);
			}
			//alert('修改');
			updatebtm.value="修改";
		}else{
		    editObj = null;
			document.getElementById('id').value="";
			document.getElementById('attsName').value="";
			document.getElementById('attsDesc').value="";	
			document.getElementById('attsSession').value="";
			document.getElementById('attsWorkingHour').value="";
			document.getElementById('attsNightShift').innerHTML="";
			document.getElementById('attsColor').value="";
			document.getElementById('attsShortName').value="";		
			document.getElementById('attsIsDefault').value="";
			document.getElementById('attsSortId').value="";
			document.getElementById('attsStricked').value=0;
			document.getElementById('attsStatus').value=1;	
			addStrict(0);					
			updatebtm.value="保存";
		}
		 	 	  
	 	 hrm.common.openDialog('update_Shift_div');
	 	
		}catch(e){
			alert(e);
	}
}


// 保存班次(修改后点击按钮,按钮值动态变化)
function saveShift(){
    var id = document.getElementById('id').value;
    var attsName = document.getElementById('attsName').value;
    var attsDesc = document.getElementById('attsDesc').value;
    var attsSession = document.getElementById('attsSession').value;
    var attsWorkingHour = document.getElementById('attsWorkingHour').value;
    //if(attsWorkingHour==null || attsWorkingHour.trim()==""){
      //  attsWorkingHour = getWorkingHour(attsSession);  //改在后台操作  
    var attsColor = document.getElementById('attsColor').value;
    var attsShortName = document.getElementById('attsShortName').value;
    var attsIsDefault = document.getElementById('attsIsDefault').value;
    var attsSortId = document.getElementById('attsSortId').value;
    var attsStricked = document.getElementById('attsStricked').value;
    if(document.getElementById("rightList").options.length == 0 && attsStricked == 1){
		attsStricked = 0;
	}
    var attsStatus = document.getElementById('attsStatus').value;
    var relaArr = getRelaArr();
    var locIdString = relaArr[0];
    var deptIdString = relaArr[1];
	//alert('saveShift');
	if(checkInput()){
		var params={id:id,attsName:attsName, attsDesc:attsDesc,
		attsSession:attsSession, attsWorkingHour:attsWorkingHour,
		attsColor:attsColor.substring(1),
		attsShortName:attsShortName, attsIsDefault:attsIsDefault, attsSortId:attsSortId, 
		attsStricked:attsStricked, attsStatus:attsStatus, locIdString: locIdString, deptIdString:deptIdString};
		function updatecallback(info) {
		    var flag = false;
		    var workingHour = "";
		    var nightShift;
		    var nightShiftStr = "";
		 	if(info.indexOf('succ') < 0){
		 		errMsg("errMsg", info);
		 		return;
		 	}else{
		 	    workingHour = info.split(",")[1];
		 	    nightShift = info.split(",")[2];
		 	    nightShiftStr = nightShift==0?"否":"是";
		 	    successMsg("errMsg", "操作成功！");
		 	}
		 	if(editObj==null){
		 		editObj=insertNewRow();
		 		flag = true;
		 	}
				var aTd = editObj.cells;
				aTd[0].innerHTML=params['id'];
				aTd[1].innerHTML=params['attsName'];
				aTd[2].innerHTML=params['attsShortName'];
				aTd[3].innerHTML=params['attsDesc'];	
				aTd[4].innerHTML=params['attsSession'];
				aTd[5].innerHTML=workingHour;
				aTd[6].innerHTML=nightShift;
				aTd[7].innerHTML=params['attsColor'];	
				aTd[8].innerHTML=params['attsIsDefault'];		
				aTd[9].innerHTML=params['attsSortId'];
				aTd[13].innerHTML=params['attsStricked'];
				aTd[14].innerHTML=params['attsStatus'];	
				aTd[10].innerHTML=nightShiftStr;
				if(params['attsIsDefault'] == 1){
				    aTd[12].innerHTML="<img onclick=\"convertDefault();\" onmouseover=\"style.cursor='pointer';\" id='defaultimg' src='../resource/images/default_yes.png' border='0' ALT='默认'>";
				}else{
				    aTd[12].innerHTML="<img onclick=\"convertDefault();\" onmouseover=\"style.cursor='pointer';\" id='defaultimg' src='../resource/images/default_no.png' border='0' ALT='非默认'>";
				}		
				if(params['attsStatus']==1){
				    aTd[15].innerHTML="<img onclick=\"convertStatus();\" onmouseover=\"style.cursor='pointer';\" id='defaultimg' src='../resource/images/default_yes.png' border='0' ALT='启用'>";
				}else{
				    aTd[15].innerHTML="<img onclick=\"convertStatus();\" onmouseover=\"style.cursor='pointer';\" id='defaultimg' src='../resource/images/default_no.png' border='0' ALT='未启用'>";
				}
				
				aTd[11].getElementsByTagName("span")[0].style.backgroundColor="#"+params['attsColor'];
				
				if(flag){
				    //alert(flag);
				    doSortTable('shift_table','操作');
			        initTableCSS('shift_table');
				}
		}		
		ExaminSchedule.insertShift(params,updatecallback);        
        hrm.common.closeDialog('update_Shift_div');
	}
}


//插入行(将添加的班次插入到表格)
function insertNewRow(){
	var ntr=shift_table.insertRow(- 1);//相应table
	/**ntr.onmousedown = "editObj=this;";
	ntr.onclick = "editObj=this;";
	ntr.ondblclick = "editObj=this;showUpdateDiv('update');";*/
	var ntd = ntr.insertCell(-1);
	ntd.style.display="none";	
	var ntd = ntr.insertCell(-1);
	ntd.style.textAlign='center';
	var ntd = ntr.insertCell(-1);
	ntd.style.textAlign='center';
	var ntd = ntr.insertCell(-1);
	ntd.style.display="none";
	var ntd = ntr.insertCell(-1);
	ntd.style.textAlign='center';	
	var ntd = ntr.insertCell(-1);
	ntd.style.textAlign='center';
	var ntd = ntr.insertCell(-1);
	ntd.style.display='none';		
	var ntd = ntr.insertCell(-1);
	ntd.style.display='none';	
	var ntd = ntr.insertCell(-1);
	ntd.style.display='none';
	var ntd = ntr.insertCell(-1);
	ntd.style.display='none';	
	
	var ntd = ntr.insertCell(-1);
	ntd.style.textAlign='center';
	var ntd = ntr.insertCell(-1);
	ntd.style.textAlign='center';
	ntd.innerHTML="<span style='background-color:#FFFFFF'>&nbsp</span>";
	var ntd = ntr.insertCell(-1);
	ntd.style.textAlign='center';
	var ntd = ntr.insertCell(-1);
	ntd.style.display='none';
	var ntd = ntr.insertCell(-1);
	ntd.style.display='none';
	var ntd = ntr.insertCell(-1);
	ntd.style.textAlign='center';
	return ntr;	
} 


// 删除班次
function del(){
	if (editObj == null){
	    errMsg("errMsg", "请选择要删除的行。");
	    return false;
	}
	var aTd = editObj.cells;
	var editid=aTd[0].innerHTML;
	var isDefault = aTd[8].innerHTML;
	var isStricted = aTd[13].innerHTML;
	if(isDefault==1 && isStricted==0){
		errMsg("errMsg", "该班次是全局默认班次，不能被删除！");
		return;
	}
	if(!confirm("确定删除选中的记录？")){
		return false;
	}
	
	function delcallback(info) {
		if(info=='isused'){
			errMsg("errMsg", "该班次正在使用，删除失败！");
			return;
		}
		if(info!='succ'){
		 	errMsg("errMsg", "删除失败！");
		 	return;
		}
		editObj.parentNode.removeChild(editObj);
	    successMsg("errMsg", "删除成功！");
	}
	ExaminSchedule.delShift(editid,delcallback);
}


// 是否默认切换；	
function convertDefault(){
    if (editObj == null){
	     errMsg("errMsg", "请选择要设置的行。");
	     return false;
    }
	var aTd = editObj.cells;
	var editid=aTd[0].innerHTML;
	var isDefault = aTd[8].innerHTML;
	var isStricted = aTd[13].innerHTML;
	var status = aTd[14].innerHTML;
	var globleDefault = 0;
	
	if(isStricted==0 && isDefault==1 && status==1){// 全局默认班次，由默认转为非默认时进行检查是否只剩这一个全局默认班次；
	    DWREngine.setAsync(false); // 设置为同步执行；
	    ExaminSchedule.getGlobalDefaultCount(globleDefaultCountCheck);
	    DWREngine.setAsync(true); // 同步执行完之后继续设置为异步执行；
	}
	function globleDefaultCountCheck(info){
	    globleDefault = info;
	}
	if(globleDefault == 1){
	   alert("仅剩下当前这一个全局默认班次，不能设为非默认！");
	   return false;
	}
	
	if(isStricted==0 && isDefault==0){ // 非限制班次，由非默认改为默认；
	    if(!confirm("您确实要将\""+aTd[1].innerHTML+"-"+aTd[2].innerHTML+"\"设置为全局默认班次吗？")){
	        return ;
	    }
	}
	if(isStricted==1 && isDefault==0){ // 限制班次，由非默认改为默认； 
	    if(!confirm("您确实要将\""+aTd[1].innerHTML+"-"+aTd[2].innerHTML+"\"设置为默认班次吗？")){
	        return ;
	    }
	}
	function delcallback(info) {
		 if(info.indexOf('notDefaultHours') > -1){
		 	errMsg("errMsg", "默认班次工时必须为"+info.split(",")[1]+"小时！");
		 	return;
		 }
		 if(info=='fail'){
		 	errMsg("errMsg", "设置失败！");
		 	return;
		 }
		 if(isDefault == 0){
		 	 aTd[12].innerHTML="<img onclick=\"convertDefault();\" onmouseover=\"style.cursor='pointer';\" id='defaultimg' src='../resource/images/default_yes.png' border='0' ALT='默认'>";
		     aTd[8].innerHTML = 1;
		 }
		 if(isDefault == 1){
		 	 aTd[12].innerHTML="<img onclick=\"convertDefault();\" onmouseover=\"style.cursor='pointer';\" id='defaultimg' src='../resource/images/default_no.png' border='0' ALT='非默认'>";
		     aTd[8].innerHTML = 0;
		 }
		 successMsg("errMsg", "设置成功！");
	}
	ExaminSchedule.setDefault(editid, isDefault, delcallback);
}
	
// 班次状态切换；
function convertStatus(){
    if (editObj == null){
	     errMsg("errMsg", "请选择要设置的行。");
	     return false;
    }
    var aTd = editObj.cells;
	var editid=aTd[0].innerHTML;
	var isDefault = aTd[8].innerHTML;
	var isStricted = aTd[13].innerHTML;
	var status = aTd[14].innerHTML;
	if(isDefault==1 && isStricted==0 && status==1){
		errMsg("errMsg", "该班次是全局默认班次，不能停用！");
		return;
	}
	function statuscallback(info) {
	     if(info!='succ'){
	         alert("设置失败，请刷新后重试！");
	         return ;
	     }
		 if(status == 0){
		 	 aTd[15].innerHTML="<img onclick=\"convertStatus();\" onmouseover=\"style.cursor='pointer';\" id='defaultimg' src='../resource/images/default_yes.png' border='0' ALT='默认'>";
		     aTd[14].innerHTML = 1;
		 }
		 if(status == 1){
		 	 aTd[15].innerHTML="<img onclick=\"convertStatus();\" onmouseover=\"style.cursor='pointer';\" id='defaultimg' src='../resource/images/default_no.png' border='0' ALT='非默认'>";
		     aTd[14].innerHTML = 0;
		 }
		 successMsg("errMsg", "设置成功！");
	}
	ExaminSchedule.setStatus(editid, status, statuscallback);
}


//关闭Dialog   可去掉
function showDialog_attshift(dialogId){
    // $("#"+dialogId).dialogClose();
    hrm.common.closeDialog(dialogId);
    $("#"+dialogId).dialog({
        width:500,
        height:'auto',
        position:'left',
        resize: false
       });
}
	
//保存当前排序
function saveSort(){
	var row =shift_table.rows;
	var sortIdArray="";
	if (row.length == 1){return null;}
	for (var i=1;i<row.length;i++){
		sortIdArray+=row[i].cells[0].innerHTML+",";
	}
  	document.getElementById('operationId').value=sortIdArray;
  	document.examinShiftSearch.action = "sortShift.action";
	document.examinShiftSearch.submit();
	//alert('关闭');
	hrm.common.initDialog('update_Shift_div');
}	
	
//清除错误信息
function clearErr(){
    if (isIE()){
        //alert('isIE');
    	document.getElementById('attsName').replaceAdjacentText('afterEnd', '');
    	document.getElementById('attsSession').replaceAdjacentText('afterEnd', '');
    	document.getElementById('attsColor').replaceAdjacentText('afterEnd', '');
  	}
  	else{   	  
		$("#label").remove();  //$("label").remove();  $("#label").empty();
		//alert('remove11');
	}
}
	
	
	
	
// 所有的地区；
var locArray = new Array();
<s:iterator value="locations" status="index">
	var loc = new Object();
	loc.id = '<s:property value="id"/>';
	loc.name = '<s:property value="locationName"/>';
	
	locArray.push(loc);
</s:iterator>

// 所有的部门；
var deptArray = new Array();
<s:iterator value="departs" status="index">
    var dept = new Object();
    dept.id = '<s:property value="id"/>';
    dept.name = '<s:property value="departmentName"/>';
    
    deptArray.push(dept);
</s:iterator>

// 添加一个对象到列表中；
function addOneToList(obj, text, value){
    // 判断在左，右边列表中是否已经存在；不存在，才添加；
    if(!oneExist(document.getElementById('rightList'), value) && !oneExist(document.getElementById('leftList'), value)){
        obj.options[obj.length] = new Option(text, value);
    }
}

// 按地区；
function accordToLoc(){
    clearList(document.getElementById("leftList")); // 清除左边列表；
    var currShiftId = document.getElementById("id").value;
    // 将所有地区添加到左边列表中，以供选择；
    var loc;
    for(var i=0; i<locArray.length; i++){
        loc = locArray[i];
        addOneToList(document.getElementById('leftList'), loc.name, loc.id);
    }
}

//按部门；           
function accordToDept(){
    clearList(document.getElementById("leftList")); // 清除左边列表；
    var currShiftId = document.getElementById("id").value;
    // 将所有部门添加到左边列表中，以供选择；
    var dept;
    for(var i=0; i<deptArray.length; i++){
        dept = deptArray[i];
        addOneToList(document.getElementById('leftList'), dept.name, dept.id);
    }
}

// 回调函数；
function getExistRelaCallback(info){
    if(info==null || info==""){
        addStrict(1); // 默认打开是按地区；
        return;
    }
    var existRelaArr = info.split("##");
    for(var i=0; i<existRelaArr.length-1; i++){
        var temp = existRelaArr[i].split(",");
        addOneToList(document.getElementById('rightList'), temp[1], temp[0]);
    }
    addStrict(1); // 默认打开是按地区；
}

//添加限制；
function addStrict(value){
    if(value==0){
        document.getElementById("restrict").style.display = "none";
        clearList(document.getElementById("leftList")); // 清除左边列表；
        clearList(document.getElementById("rightList")); // 清除右边列表；
    }
    if(value==1){
        document.getElementById("restrict").style.display = "block";
        clearList(document.getElementById("leftList")); // 清除左边列表；
        accordToLoc();// 默认按地区；
    }
}

/**清除列表框中的项；*/
function clearList(obj){
    for(var i=0; obj!=null&&i<obj.length; i++){
        obj.options[i] = null;
        i--;
    }   
}

/**判断一个选项在列表中是否已经存在*/
function oneExist(obj, value){
    for(var i=0; obj!=null&&i<obj.length; i++){
        if(obj.options[i].value == value){
            return true;
        }
    }
    return false;
}

// 获取关联的字符串；
function getRelaArr(){
    var relaArr = new Array();
    var locIds = "";
    var deptIds = "";
    var rightObj = document.getElementById("rightList");
    for(var i=0; rightObj!=null&&i<rightObj.length; i++){
        var temp = rightObj.options[i].value;
        if(checkValueInArr(locArray, temp)){
            locIds += temp + ",";
        }
        if(checkValueInArr(deptArray, temp)){
            deptIds += temp + ",";
        }
    }
    relaArr[0] = locIds;
    relaArr[1] = deptIds;
    return relaArr;
}

// 判断value是否在arr中；
function checkValueInArr(arr, value){
    for(var i=0; arr!=null&&i<arr.length; i++){
        if(value == arr[i].id){
            return true;
        }
    }
    return false;
}
 
//表格排序
doSortTable('shift_table','操作');
//样式
initTableCSS('shift_table');

$("#update_Shift_div").dialog({autoOpen:false,width:600,height:400,modal:false,resizable:true,zIndex:0});

</script>
</body>	
