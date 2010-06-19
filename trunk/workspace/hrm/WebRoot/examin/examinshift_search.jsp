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
<!-- jQuery ����ɾ��--> 
<script type="text/javascript" src="../resource/js/jquery/jquery.tablesorter.js"></script>
<script type="text/javascript" src="../resource/js/jquery/jquery.tableEditor.js"></script>
<!-- ��ɫѡ���� -->
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

<!-- ѡ��ĳ״̬�İ��(������δ����) --> 
<table border="0" width="100%">
  <tr><td align="right" valign="bottom">
      ״̬��<s:select id="resultStatus" name="resultStatus" value="resultStatus"
		 list="#{1:'����', 0:'δ����'}" emptyOption="false" onchange="document.forms[0].submit();"/>
      &nbsp;&nbsp;
  </td></tr>
</table>
<!-- �����Ϣ��� --> 
<table id="shift_table" cellspacing="1" cellspacing="0" class="tablesorter" width="85%">
	<thead>
	     <tr align="center">    
	        <th nowrap="nowrap" style="display:none">&nbsp;</th>
	     	<th nowrap="nowrap">�������</th>
	     	<th nowrap="nowrap">��δ���</th>
	     	<th nowrap="nowrap" style="display:none">&nbsp;</th>
	     	<th nowrap="nowrap">���ʱ���</th>
	     	<th nowrap="nowrap">��ι�ʱ</th>		     		
	     	<th nowrap="nowrap" style="display:none">&nbsp;</th>
	     	<th nowrap="nowrap" style="display:none">&nbsp;</th>
	     	<th nowrap="nowrap" style="display:none">&nbsp;</th>
	     	<th nowrap="nowrap" style="display:none">&nbsp;</th>
	     	<th nowrap="nowrap">�Ƿ��ҹ</th>
	     	<th nowrap="nowrap">��ɫ</th>
	     	<th nowrap="nowrap">Ĭ��</th>	
	     	<th nowrap="nowrap">���״̬</th>      		     		 	     		     	 		     	
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
			<td align="center"><s:if test="attsNightShift==0">��</s:if><s:else>��</s:else></td>
			<td align="center"><span id="colorbg" style="background-color:<s:property value="attsColor"/>">&nbsp;</span></td>
			<td align="center">
				<s:if test="attsIsDefault!=null && attsIsDefault==1">
				    <img onclick="convertDefault();" onmouseover="style.cursor='pointer';" id="defaultimg_yes" src="../resource/images/default_yes.png" border="0" ALT="Ĭ��">
				</s:if>
				<s:else>
				    <img onclick="convertDefault();" onmouseover="style.cursor='pointer';" id="defaultimg_no" src="../resource/images/default_no.png" border="0" ALT="��Ĭ��">
				</s:else>
			</td>
			<td style="display:none"><s:if test="attsStricked==1">1</s:if><s:else>0</s:else></td>
			<td style="display:none"><s:if test="attsStatus==1">1</s:if><s:else>0</s:else></td>
			<td align="center">
			    <s:if test="attsStatus!=null && attsStatus==1">
				    <img onclick="convertStatus();" onmouseover="style.cursor='pointer';" id="defaultimg_yes" src="../resource/images/default_yes.png" border="0" ALT="Ĭ��">
				</s:if>
				<s:else>
				    <img onclick="convertStatus();" onmouseover="style.cursor='pointer';" id="defaultimg_no" src="../resource/images/default_no.png" border="0" ALT="��Ĭ��">
				</s:else>
			</td>	
		</tr>
		</s:iterator>
		</s:if>
 		<s:else>
			<!--<tr>
				<td align="center" colspan="33">
					
					�޷��������Ŀ����μ�¼! 
					
				</td>
			</tr>-->
		</s:else>  
		</tbody>
</table>

<!-- ҳ���ϲ�����ť -->
<div class="btnlink">
	<a href="#" onclick="showUpdateDiv('insert');">����</a>
	<a href="#" onclick="del();">ɾ��</a>
	<a href="#" onclick="updateRow();">�޸�</a>
	<a href="#" onclick="saveSort();">��������</a>
</div>
</s:form>

<!-- update Shift div begin �����ϢDialog -->
<div id="update_Shift_div" title="���ڰ����Ϣ" align="left" style="width=400;display:none">
    <form id="shiftInfoForm" method="post">
    <input id="id" type="hidden" name="id"/>
    <input type="hidden" id="attsIsDefault" name="attsIsDefault">
    <input type="hidden" id="attsSortId" name="attsSortId">
		<table id="newShiftTable" width="100%"  border="0" cellspacing="1" cellpadding="0">
			<tr>
				<td>
					������ƣ�
				</td>
				<td class="errorMessage">	
					<input type="text" id="attsName" name="attsName" value="" size="10" maxlength="32">
				</td>
				<td>
					��δ��룺<span style="color:red;">*</span>
				</td>
				<td>
					<input type="text" id="attsShortName" name="attsShortName" value="" size="5" maxlength="2">
				</td>
			</tr>
			<tr>
				<td>
					���������
				</td>
				<td colspan="3">	
					<input type="text" id="attsDesc" name="attsDesc" value="" size="52" maxlength="128">
				</td>				
			</tr>
			<tr>
				<td>
					���ʱ��Σ�<span style="color:red;">*</span>
				</td>
				<td class="errorMessage">	
					<input type="text" id="attsSession" name="attsSession" value="" size="25" maxlength="60">
				</td>
				<td>
					�����ɫ��<span style="color:red;">*</span>
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
					��ι�ʱ��
				</td>
				<td class="errorMessage">
					<input class="nothinginput" type="text" id="attsWorkingHour" name="attsWorkingHour" value="" size="5" maxlength="6" readonly>
				</td>
				<td>
					�Ƿ��ҹ��
				</td>
				<td id="attsNightShift">

				</td>
			</tr>
			<tr>
			    <td>
			        �Ƿ����ƣ�
			    </td>
			    <td>
			       <s:select id="attsStricked" name="attsStricked" value=""
					list="#{0:'��',1:'��'}" emptyOption="false" onchange="addStrict(this.value)" />
			    </td>
			    <td>
			        ���״̬��
			    </td>
			    <td>
			       <s:select id="attsStatus" name="attsStatus" value=""
					list="#{0:'δ����',1:'����'}" emptyOption="false" onchange="checkAllow(this.value)"/>
			    </td>
			</tr>
			<tr>
			   <td align="center" colspan="4">
			    <table border="0" id="restrict" style="display:none">
			    <tr><td valign="bottom">
			        <div class="btnlink">
			            <a href="#" onclick="accordToLoc()">������</a>
			            <a href="#" onclick="accordToDept()">������</a>
			        </div>
			    </td></tr>
			    <tr><td align="center" valign="top">
			    <s:optiontransferselect 
	             name="leftList"
	             leftTitle="δѡ�У�"
	             rightTitle="��ѡ�У�"
	             list="locations"
	             multiple="true"
	             listKey="id"
	             listValue="%{empName+' '+ empDistinctNo}"
	             addToLeftLabel="�����ƶ�"
	             addToRightLabel="�����ƶ�"
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
					<input id="div_btnSubmit" name="hiddenName" type="button" onclick="saveShift();" value="����">
					<input type="button" name="hiddenName" onclick="hrm.common.closeDialog('update_Shift_div');" value="�ر�">
				</td>
			</tr>										
			</table>
	</form>
</div>

<script type="text/javascript" language="javascript">
model.simple.setParentIFrameFull("IFrame1"); // add by ���Σ�����iframe�߶ȣ�

var editObj = null; //������tr(���ñ���)

//ҳ�����ʱ��ʼ����ʽ
function initTableCSS(tableId){
     $(".tablesorter tr").mousedown(function(){ 
     	changeCSS(tableId); 
       	$(this).addClass("click"); 
    }); 
}


//����Ƿ�������ð��״̬Ϊ ��ͣ�á�
function checkAllow(){
	if(editObj != null ){
		var aTd = editObj.cells;
	}
	var isDefault = aTd[8].innerHTML;
	var isStricted = aTd[13].innerHTML;
	var status = aTd[14].innerHTML;
	var globleDefault = 0;
	if(isStricted==0 && isDefault==1 && status==1){// ȫ��Ĭ�ϰ�Σ���Ĭ��תΪ��Ĭ��ʱ���м���Ƿ�ֻʣ��һ��ȫ��Ĭ�ϰ�Σ�
	    DWREngine.setAsync(false); // ����Ϊͬ��ִ�У�
	    ExaminSchedule.getGlobalDefaultCount(globleDefaultCountCheck);
	    DWREngine.setAsync(true); // ͬ��ִ����֮���������Ϊ�첽ִ�У�
	}

	
	function globleDefaultCountCheck(info){
	    globleDefault = info;
	}
	
	if(globleDefault == 1){
	   alert("�ð����ȫ��Ĭ�ϰ�Σ�����ͣ�ã�");
	   var sel = document.getElementById('attsStatus');
	   for(var i=0;i<sel.options.length;i++){
			if(sel.options[i].text=="����"){
				sel.options[i].selected=true;
			}
	   }
	   return false;
	}else return true;
}


/***������ĳһ��ʱ��ʼ��ʼ�������ʽ***/
function changeCSS(tableId){
    var trs = document.getElementById(tableId).getElementsByTagName('tbody')[0].getElementsByTagName('tr');
    for(var i = 0;i < trs.length; i++){
        if(trs[i].className.indexOf('click')!=-1){
          trs[i].className=('');
        }
    }
}

//����������У��
function checkInput(){
    var flag = true;
    if(document.getElementById('attsShortName').value==''){
        valid(document.getElementById('attsShortName'),'������!');
        flag = false;
    }
    if(document.getElementById('attsSession').value==''){
        valid(document.getElementById('attsSession'),'������!');
        flag = false;
    }
    if(document.getElementById('attsColor').value==''){
        valid(document.getElementById('attsColor'),'������');
        flag = false;
    }
    return flag;
}

//��ֵ"������"
function valid(obj,str){
	if (isIE()) {
		obj.replaceAdjacentText('afterEnd',str);
	}
	else{
    	$("#"+obj.id).after('<label class=errorMessage>' + str + '</lable>');
    }
   }

/***Jquery����������***/
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


//�޸�ʱ����Dialog
function updateRow(){   
    if(editObj == null){
        errMsg("errMsg", "��ѡ��Ҫ�޸ĵ��У�");
        return false;
    }else{
	    showUpdateDiv('update');
    }
}

// �������޸İ����Ϣ��
function showUpdateDiv(ope){
	// �������ѡ���
	clearList(document.getElementById("leftList")); //clearList()������
	clearList(document.getElementById("rightList"));
	//alert('showUpdateDiv');
	try{	
	    clearErr();//���������Ϣ					
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
			    ExaminSchedule.getRelaString(document.getElementById('id').value, getExistRelaCallback);// ���ұ�����Ѿ����ڵĹ�����
			}else{
			    addStrict(0);
			}
			//alert('�޸�');
			updatebtm.value="�޸�";
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
			updatebtm.value="����";
		}
		 	 	  
	 	 hrm.common.openDialog('update_Shift_div');
	 	
		}catch(e){
			alert(e);
	}
}


// ������(�޸ĺ�����ť,��ťֵ��̬�仯)
function saveShift(){
    var id = document.getElementById('id').value;
    var attsName = document.getElementById('attsName').value;
    var attsDesc = document.getElementById('attsDesc').value;
    var attsSession = document.getElementById('attsSession').value;
    var attsWorkingHour = document.getElementById('attsWorkingHour').value;
    //if(attsWorkingHour==null || attsWorkingHour.trim()==""){
      //  attsWorkingHour = getWorkingHour(attsSession);  //���ں�̨����  
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
		 	    nightShiftStr = nightShift==0?"��":"��";
		 	    successMsg("errMsg", "�����ɹ���");
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
				    aTd[12].innerHTML="<img onclick=\"convertDefault();\" onmouseover=\"style.cursor='pointer';\" id='defaultimg' src='../resource/images/default_yes.png' border='0' ALT='Ĭ��'>";
				}else{
				    aTd[12].innerHTML="<img onclick=\"convertDefault();\" onmouseover=\"style.cursor='pointer';\" id='defaultimg' src='../resource/images/default_no.png' border='0' ALT='��Ĭ��'>";
				}		
				if(params['attsStatus']==1){
				    aTd[15].innerHTML="<img onclick=\"convertStatus();\" onmouseover=\"style.cursor='pointer';\" id='defaultimg' src='../resource/images/default_yes.png' border='0' ALT='����'>";
				}else{
				    aTd[15].innerHTML="<img onclick=\"convertStatus();\" onmouseover=\"style.cursor='pointer';\" id='defaultimg' src='../resource/images/default_no.png' border='0' ALT='δ����'>";
				}
				
				aTd[11].getElementsByTagName("span")[0].style.backgroundColor="#"+params['attsColor'];
				
				if(flag){
				    //alert(flag);
				    doSortTable('shift_table','����');
			        initTableCSS('shift_table');
				}
		}		
		ExaminSchedule.insertShift(params,updatecallback);        
        hrm.common.closeDialog('update_Shift_div');
	}
}


//������(����ӵİ�β��뵽���)
function insertNewRow(){
	var ntr=shift_table.insertRow(- 1);//��Ӧtable
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


// ɾ�����
function del(){
	if (editObj == null){
	    errMsg("errMsg", "��ѡ��Ҫɾ�����С�");
	    return false;
	}
	var aTd = editObj.cells;
	var editid=aTd[0].innerHTML;
	var isDefault = aTd[8].innerHTML;
	var isStricted = aTd[13].innerHTML;
	if(isDefault==1 && isStricted==0){
		errMsg("errMsg", "�ð����ȫ��Ĭ�ϰ�Σ����ܱ�ɾ����");
		return;
	}
	if(!confirm("ȷ��ɾ��ѡ�еļ�¼��")){
		return false;
	}
	
	function delcallback(info) {
		if(info=='isused'){
			errMsg("errMsg", "�ð������ʹ�ã�ɾ��ʧ�ܣ�");
			return;
		}
		if(info!='succ'){
		 	errMsg("errMsg", "ɾ��ʧ�ܣ�");
		 	return;
		}
		editObj.parentNode.removeChild(editObj);
	    successMsg("errMsg", "ɾ���ɹ���");
	}
	ExaminSchedule.delShift(editid,delcallback);
}


// �Ƿ�Ĭ���л���	
function convertDefault(){
    if (editObj == null){
	     errMsg("errMsg", "��ѡ��Ҫ���õ��С�");
	     return false;
    }
	var aTd = editObj.cells;
	var editid=aTd[0].innerHTML;
	var isDefault = aTd[8].innerHTML;
	var isStricted = aTd[13].innerHTML;
	var status = aTd[14].innerHTML;
	var globleDefault = 0;
	
	if(isStricted==0 && isDefault==1 && status==1){// ȫ��Ĭ�ϰ�Σ���Ĭ��תΪ��Ĭ��ʱ���м���Ƿ�ֻʣ��һ��ȫ��Ĭ�ϰ�Σ�
	    DWREngine.setAsync(false); // ����Ϊͬ��ִ�У�
	    ExaminSchedule.getGlobalDefaultCount(globleDefaultCountCheck);
	    DWREngine.setAsync(true); // ͬ��ִ����֮���������Ϊ�첽ִ�У�
	}
	function globleDefaultCountCheck(info){
	    globleDefault = info;
	}
	if(globleDefault == 1){
	   alert("��ʣ�µ�ǰ��һ��ȫ��Ĭ�ϰ�Σ�������Ϊ��Ĭ�ϣ�");
	   return false;
	}
	
	if(isStricted==0 && isDefault==0){ // �����ư�Σ��ɷ�Ĭ�ϸ�ΪĬ�ϣ�
	    if(!confirm("��ȷʵҪ��\""+aTd[1].innerHTML+"-"+aTd[2].innerHTML+"\"����Ϊȫ��Ĭ�ϰ����")){
	        return ;
	    }
	}
	if(isStricted==1 && isDefault==0){ // ���ư�Σ��ɷ�Ĭ�ϸ�ΪĬ�ϣ� 
	    if(!confirm("��ȷʵҪ��\""+aTd[1].innerHTML+"-"+aTd[2].innerHTML+"\"����ΪĬ�ϰ����")){
	        return ;
	    }
	}
	function delcallback(info) {
		 if(info.indexOf('notDefaultHours') > -1){
		 	errMsg("errMsg", "Ĭ�ϰ�ι�ʱ����Ϊ"+info.split(",")[1]+"Сʱ��");
		 	return;
		 }
		 if(info=='fail'){
		 	errMsg("errMsg", "����ʧ�ܣ�");
		 	return;
		 }
		 if(isDefault == 0){
		 	 aTd[12].innerHTML="<img onclick=\"convertDefault();\" onmouseover=\"style.cursor='pointer';\" id='defaultimg' src='../resource/images/default_yes.png' border='0' ALT='Ĭ��'>";
		     aTd[8].innerHTML = 1;
		 }
		 if(isDefault == 1){
		 	 aTd[12].innerHTML="<img onclick=\"convertDefault();\" onmouseover=\"style.cursor='pointer';\" id='defaultimg' src='../resource/images/default_no.png' border='0' ALT='��Ĭ��'>";
		     aTd[8].innerHTML = 0;
		 }
		 successMsg("errMsg", "���óɹ���");
	}
	ExaminSchedule.setDefault(editid, isDefault, delcallback);
}
	
// ���״̬�л���
function convertStatus(){
    if (editObj == null){
	     errMsg("errMsg", "��ѡ��Ҫ���õ��С�");
	     return false;
    }
    var aTd = editObj.cells;
	var editid=aTd[0].innerHTML;
	var isDefault = aTd[8].innerHTML;
	var isStricted = aTd[13].innerHTML;
	var status = aTd[14].innerHTML;
	if(isDefault==1 && isStricted==0 && status==1){
		errMsg("errMsg", "�ð����ȫ��Ĭ�ϰ�Σ�����ͣ�ã�");
		return;
	}
	function statuscallback(info) {
	     if(info!='succ'){
	         alert("����ʧ�ܣ���ˢ�º����ԣ�");
	         return ;
	     }
		 if(status == 0){
		 	 aTd[15].innerHTML="<img onclick=\"convertStatus();\" onmouseover=\"style.cursor='pointer';\" id='defaultimg' src='../resource/images/default_yes.png' border='0' ALT='Ĭ��'>";
		     aTd[14].innerHTML = 1;
		 }
		 if(status == 1){
		 	 aTd[15].innerHTML="<img onclick=\"convertStatus();\" onmouseover=\"style.cursor='pointer';\" id='defaultimg' src='../resource/images/default_no.png' border='0' ALT='��Ĭ��'>";
		     aTd[14].innerHTML = 0;
		 }
		 successMsg("errMsg", "���óɹ���");
	}
	ExaminSchedule.setStatus(editid, status, statuscallback);
}


//�ر�Dialog   ��ȥ��
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
	
//���浱ǰ����
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
	//alert('�ر�');
	hrm.common.initDialog('update_Shift_div');
}	
	
//���������Ϣ
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
	
	
	
	
// ���еĵ�����
var locArray = new Array();
<s:iterator value="locations" status="index">
	var loc = new Object();
	loc.id = '<s:property value="id"/>';
	loc.name = '<s:property value="locationName"/>';
	
	locArray.push(loc);
</s:iterator>

// ���еĲ��ţ�
var deptArray = new Array();
<s:iterator value="departs" status="index">
    var dept = new Object();
    dept.id = '<s:property value="id"/>';
    dept.name = '<s:property value="departmentName"/>';
    
    deptArray.push(dept);
</s:iterator>

// ���һ�������б��У�
function addOneToList(obj, text, value){
    // �ж������ұ��б����Ƿ��Ѿ����ڣ������ڣ�����ӣ�
    if(!oneExist(document.getElementById('rightList'), value) && !oneExist(document.getElementById('leftList'), value)){
        obj.options[obj.length] = new Option(text, value);
    }
}

// ��������
function accordToLoc(){
    clearList(document.getElementById("leftList")); // �������б�
    var currShiftId = document.getElementById("id").value;
    // �����е�����ӵ�����б��У��Թ�ѡ��
    var loc;
    for(var i=0; i<locArray.length; i++){
        loc = locArray[i];
        addOneToList(document.getElementById('leftList'), loc.name, loc.id);
    }
}

//�����ţ�           
function accordToDept(){
    clearList(document.getElementById("leftList")); // �������б�
    var currShiftId = document.getElementById("id").value;
    // �����в�����ӵ�����б��У��Թ�ѡ��
    var dept;
    for(var i=0; i<deptArray.length; i++){
        dept = deptArray[i];
        addOneToList(document.getElementById('leftList'), dept.name, dept.id);
    }
}

// �ص�������
function getExistRelaCallback(info){
    if(info==null || info==""){
        addStrict(1); // Ĭ�ϴ��ǰ�������
        return;
    }
    var existRelaArr = info.split("##");
    for(var i=0; i<existRelaArr.length-1; i++){
        var temp = existRelaArr[i].split(",");
        addOneToList(document.getElementById('rightList'), temp[1], temp[0]);
    }
    addStrict(1); // Ĭ�ϴ��ǰ�������
}

//������ƣ�
function addStrict(value){
    if(value==0){
        document.getElementById("restrict").style.display = "none";
        clearList(document.getElementById("leftList")); // �������б�
        clearList(document.getElementById("rightList")); // ����ұ��б�
    }
    if(value==1){
        document.getElementById("restrict").style.display = "block";
        clearList(document.getElementById("leftList")); // �������б�
        accordToLoc();// Ĭ�ϰ�������
    }
}

/**����б���е��*/
function clearList(obj){
    for(var i=0; obj!=null&&i<obj.length; i++){
        obj.options[i] = null;
        i--;
    }   
}

/**�ж�һ��ѡ�����б����Ƿ��Ѿ�����*/
function oneExist(obj, value){
    for(var i=0; obj!=null&&i<obj.length; i++){
        if(obj.options[i].value == value){
            return true;
        }
    }
    return false;
}

// ��ȡ�������ַ�����
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

// �ж�value�Ƿ���arr�У�
function checkValueInArr(arr, value){
    for(var i=0; arr!=null&&i<arr.length; i++){
        if(value == arr[i].id){
            return true;
        }
    }
    return false;
}
 
//�������
doSortTable('shift_table','����');
//��ʽ
initTableCSS('shift_table');

$("#update_Shift_div").dialog({autoOpen:false,width:600,height:400,modal:false,resizable:true,zIndex:0});

</script>
</body>	
