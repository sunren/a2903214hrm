<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<table width="100%" id="foot_table"  border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td align="center">
    <s:hidden name="emp"/>
	<s:hidden name="attdDateFrom"/>
	<s:hidden name="attdDateTo"/>
	<s:hidden name="dept"/>
	<s:hidden name="machineNo"/>
	<s:hidden name="status"/>
	<s:hidden name="empIdStr"/>
    </td>
    <td id="employeeSelector">
    	<s:empselector id="empselector1" name="emp.empDeptNo.departmentName" condition="empNeedCard" hiddenFieldName="emp.empDeptNo.id"/>
    </td>
  </tr>  
</table>

<div id="addCard" style="display: none">
<!-- 添加补卡信息 -->
<table border="0" cellspacing="2">  
  <tr>
    <td>考勤日期：</td>
    <td> 
       <s:textfield id="attdDate" name="attdDate" required="true" value="%{attdDate}" size="12"  />
       <img onclick="WdatePicker({el:'attdDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">							
    </td>	    
    <td>刷卡时间: </td>
    <td><s:textfield id="attdCardTime" name="attdCardTime" required="true" size="21"  />
    <img onclick="WdatePicker({el:'attdCardTime',dateFmt:'yyyy-MM-dd HH:mm:ss'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">								
    </td>
  </tr>
  <tr>
    <td>考勤机号: </td>
    <td>
    <s:select id="aodTtdMachineNo" list="machineList" listKey="macNo" listValue="macNo" multiple="false" emptyOption="true" size="1" />
    </td>
    <td>备注：</td>
    <td><s:textarea id="memo" name="memo" cols="19" rows="2"></s:textarea></td>
  </tr>
  <tr>
    <td align="center" colspan="4" >
        <input type="button" name="btn" onclick="saveData();" value="保存" id="btnSave">   
        <input type="button" name="btn" onclick="hrm.common.closeDialog('dlgEmpListDiv');" value="关闭">
    </td>
  </tr>
</table>
</div>
<div id="syncToMachine" style="display: none">
<!-- 向考勤机中同步员工信息 -->
<table border="0" cellspacing="2"> 
  <tr>
    <td>考勤机号：</td>
    <td>
    	<s:iterator value="machineList">
		    <input type="checkbox" name="syncToMachine_machineNo" value="<s:property value="macId"/>" /><s:property value="macNo"/>
    	</s:iterator>
    </td>
    <td></td>
    <td></td>
  </tr>
  <tr>
    <td align="center" colspan="4" >
        <input type="button" name="btn" onclick="syncToMachine();" value="同步" id="btnSyncToMachine">   
        <input type="button" name="btn" onclick="hrm.common.closeDialog('dlgEmpListDiv');" value="关闭">
    </td>
  </tr>
</table>
</div>
<div id="syncToProject" style="display: none">
<!-- 同步考勤机中员工信息 -->
<table border="0" cellspacing="2">
  <tr>
    <td>考勤机号：</td>
    <td>							
    	<s:iterator value="machineList">
		    <input type="checkbox" name="syncToProject_machineNo" value="<s:property value="macId"/>" /><s:property value="macNo"/>
    	</s:iterator>
    </td>	    
    <td></td>
    <td></td>
  </tr>
  <tr>
    <td align="center" colspan="4" >
        <input type="button" name="btn" onclick="syncToProject();" value="同步" id="btnSyncToProject">   
        <input type="button" name="btn" onclick="hrm.common.closeDialog('dlgEmpListDiv');" value="关闭">
    </td>
  </tr>
</table>
</div>
<div id="batchRead" style="display: none">
<!-- 批量读取考勤机中刷卡记录 -->
<table border="0" cellspacing="2">
  <tr>
  	<td>考勤机号：</td>
  </tr>
  <s:iterator value="machineList">
  <tr>
  	<td>&nbsp;</td>
    <td align="center" colspan="3">
	    <input type="checkbox" name="batchRead_machineNo" value="<s:property value="macId"/>" /><s:property value="macNo"/>
    </td>
  </tr>
  </s:iterator>				
  <tr>
    <td align="center" colspan="4" >
        <input type="button" name="btn" onclick="batchRead();" value="读取" id="btnBatchRead">   
        <input type="button" name="btn" onclick="hrm.common.closeDialog('dlgEmpListDiv');" value="关闭">
    </td>
  </tr>
</table>
</div>
<div id="batchDelete" style="display: none">
<!-- 批量删除考勤机中员工信息 -->
<table border="0" cellspacing="2">  
  <tr>
    <td>考勤机号：</td>
    <td>
    	<s:iterator value="machineList">
		    <input type="checkbox" name="batchDelete_machineNo" value="<s:property value="macId"/>" /><s:property value="macNo"/>
    	</s:iterator>
    </td>	    
    <td></td>
    <td></td>
  </tr>
  <tr>
    <td align="center" colspan="4" >
        <input type="button" name="btn" onclick="batchDelete();" value="删除" id="btnBatchDelete">   
        <input type="button" name="btn" onclick="hrm.common.closeDialog('dlgEmpListDiv');" value="关闭">
    </td>
  </tr>
</table>
</div>
<div id="dlgSyncResultDiv" title="同步结果" style="display:none;height:480"></div> 
<script type="text/javascript" language="javascript">
//查询方法(点击查询按钮)
function searchOriginalData(){
    if(document.originalDataShow.attdDateFrom.value == ''){
        alert("请选择开始日期！");
        return;
    }
    if(document.originalDataShow.attdDateTo.value == ''){
        alert("请选择结束日期！");
        return;
    }
    document.forms[0].submit();//提交表单
}

//数据清理操作(点击清理按钮)
function deleteOriginalData(){
    if(document.originalDataShow.attdDateFrom.value == ''){
        alert("请选择开始日期！");
        return;
    }
    if(document.originalDataShow.attdDateTo.value == ''){
        alert("请选择结束日期！");
        return;
    }
    var start = document.originalDataShow.attdDateFrom.value;
    var end = document.originalDataShow.attdDateTo.value;
    if(confirm("确定要删除" + start + "到" + end + "区间内的数据么？")){ //confirm确认是否删除
    	document.all.operate.value = "deleteSome";
        document.forms[0].submit();
    }
}

//删除一条数据
function deleteOneData(aodId){
    if(confirm("确定要删除该条数据么？")){
        document.all.aodId.value = aodId;
        document.all.operate.value = "deleteOne";
        document.forms[0].submit();
    }
}

//同考勤机同步数据操作
function syncData(option){
	if(option=="addCard"){
		document.getElementById('ui-dialog-title-dlgEmpListDiv').innerHTML="补卡";
	}
	if(option=="syncToMachine"){
		document.getElementById('ui-dialog-title-dlgEmpListDiv').innerHTML="向考勤机同步员工信息";
	}
	if(option=="syncToProject"){
		document.getElementById('ui-dialog-title-dlgEmpListDiv').innerHTML="检查员工同步信息";
	}
	if(option=="batchRead"){
		document.getElementById('ui-dialog-title-dlgEmpListDiv').innerHTML="读取考勤机刷卡数据";
		document.getElementById('employeeSelector').style.display = "none";
	}
	if(option=="batchDelete"){
		document.getElementById('ui-dialog-title-dlgEmpListDiv').innerHTML="删除考勤机中员工信息";
	}
}

//切换弹出窗口操作
function switchOperate(operate){
	document.getElementById('employeeSelector').style.display = "";
	document.getElementById("addCard").style.display="none";
	document.getElementById("syncToMachine").style.display="none";
	document.getElementById("syncToProject").style.display="none";
	document.getElementById("batchRead").style.display="none";
	document.getElementById("batchDelete").style.display="none";
	document.getElementById(operate).style.display="";
	syncData(operate);
} 

//保存提交操作(点击Dialog'保存按钮')
function saveData(){
    var empStrId = empSelector_choseEmpIdStr();
    if(empStrId == ''){
        alert("请至少选择一个员工！");
        return false;
    }
    //日期正则表达式
    var dateReg = /^\d{4}-\d{1,2}-\d{1,2}$/;
    var datetimeReg = /^\d{4}-\d{1,2}-\d{1,2} \d{1,2}:\d{1,2}(:\d{1,2})?$/;
    if(!dateReg.test(document.getElementById('attdDate').value)){
        alert("考勤日期格式错误！");
        return false;
    }
    if(!datetimeReg.test(document.getElementById('attdCardTime').value)){
        alert("刷卡时间格式错误！");
        return false;
    }
    if(document.getElementById('memo').value == ''){
        alert("请填写备注！");
        return false;
    }
    if(document.getElementById('attdCardTime').value.split(':').length ==2){
        document.getElementById('attdCardTime').value = document.getElementById('attdCardTime').value +":00";
    }
    var examinDateArr = document.getElementById('attdDate').value.split('-');
    var examinDate = new Date(examinDateArr[0],examinDateArr[1]-1,examinDateArr[2]);
    var cardDateArr = document.getElementById('attdCardTime').value.split(' ')[0].split('-');
    var cardDate = new Date(cardDateArr[0],cardDateArr[1]-1,cardDateArr[2]);
    if(examinDate.getTime() != cardDate.getTime()){
        if(cardDate.getTime()-examinDate.getTime()!=86400000){
            alert("刷卡时间和考勤日期不一致！");
            return false;
        }
        if(!window.confirm('刷卡时间与考勤日期不是同一天，请确认是否隔夜班次？')){
            return false;
        }
    }
    
    document.getElementById('btnSave').disabled='disabled';
    document.getElementById('empIdStr').value = empStrId;
    DwrForAttend.addCardData(document.getElementById('empIdStr').value,document.getElementById('attdDate').value,
    	    document.getElementById('attdCardTime').value,document.getElementById('aodTtdMachineNo').value,document.getElementById('memo').value,'<s:property value="#session['empName']"/>',callback);
    function callback(data){
        if("NOAUTH" == data){
        	errMsg("errMsg","您无权执行此操作，请重新登陆！");
        }else if("SUCCESS" == data){
        	successMsg("errMsg","补卡操作成功。");
        }else{
        	errMsg("errMsg",data);
        }
        hrm.common.closeDialog('dlgEmpListDiv');
        document.forms[0].submit();
    }
}

//向考勤机中同步员工信息
//同步提交操作(点击Dialog'同步按钮')
function syncToMachine(){
	document.getElementById('btnSyncToMachine').disabled='disabled';
	document.getElementById('empIdStr').value = empSelector_choseEmpIdStr();
    if(document.getElementById('empIdStr').value==""||document.getElementById('empIdStr').value.length<=0){
		alert("请选择你要同步的员工");
		document.getElementById('btnSyncToMachine').disabled='';
		return false;
    }
    var machineNo = "";
    var machineArray = document.getElementsByName("syncToMachine_machineNo");
    if(machineArray == null){
		alert("请先添加考勤机");
		document.getElementById('btnSyncToMachine').disabled='';
		return false;
    }
    for(var i=0;i<machineArray.length;i++){
		if(machineArray[i].checked){
			if(machineNo != ""){
				machineNo += ";";
			}
			machineNo += machineArray[i].value;
		}
    }
    if(machineNo == ""){
		alert("请选择考勤机");
    	document.getElementById('btnSyncToMachine').disabled='';
		return false;
    }
	DwrSyncAttdMachine.syncToMachine(document.getElementById('empIdStr').value,machineNo,callback);
    function callback(data){
        var closeBtn = "<br/><input value='关闭' type=\"button\" onclick='hrm.common.closeDialog(\"dlgSyncResultDiv\");window.location=\"attdSyncRecordShow.action\";'/>";
        var resultField = "<textarea rows='10' cols='70' readonly='readonly'>"+data+"</textarea>";
    	$('#dlgSyncResultDiv').html(resultField+closeBtn);
    	document.getElementById('btnSyncToMachine').disabled='';
		hrm.common.openDialog('dlgSyncResultDiv');
    }
	hrm.common.closeDialog('dlgEmpListDiv');
}

//同步考勤机中员工信息
//同步提交操作(点击Dialog'同步按钮')
function syncToProject(){
	document.getElementById('btnSyncToProject').disabled='disabled';
	document.getElementById('empIdStr').value = empSelector_choseEmpIdStr();
    var machineNo = "";
    var machineArray = document.getElementsByName("syncToProject_machineNo");
    if(machineArray == null){
		alert("请先添加考勤机");
    	document.getElementById('btnSyncToProject').disabled='';
		return false;
    }
    for(var i=0;i<machineArray.length;i++){
		if(machineArray[i].checked){
			if(machineNo != ""){
				machineNo += ";";
			}
			machineNo += machineArray[i].value;
		}
    }
    if(machineNo == ""){
		alert("请选择考勤机");
    	document.getElementById('btnSyncToProject').disabled='';
		return false;
    }
    DwrSyncAttdMachine.syncToProject(document.getElementById('empIdStr').value,machineNo,callback);
    function callback(data){
    	successMsg("errMsg",data);
    }
	
	hrm.common.closeDialog('dlgEmpListDiv');
}
//批量读取考勤机中员工刷卡记录
//读取提交操作(点击Dialog'读取按钮')
function batchRead(){
	document.getElementById('btnBatchRead').disabled='disabled';
    var machineNo = "";
    var machineArray = document.getElementsByName("batchRead_machineNo");
    if(machineArray == null){
		alert("请先添加考勤机");
    	document.getElementById('btnBatchRead').disabled='';
		return false;
    }
    for(var i=0;i<machineArray.length;i++){
		if(machineArray[i].checked){
			if(machineNo != ""){
				machineNo += ";";
			}
			machineNo += machineArray[i].value;
		}
    }
    if(machineNo == ""){
		alert("请选择考勤机");
    	document.getElementById('btnBatchRead').disabled='';
		return false;
    }
    DwrSyncAttdMachine.batchRead(null,machineNo,null,null,null,null,callback);
    function callback(data){
    	successMsg("errMsg",data);
       	document.getElementById('btnBatchRead').disabled='';
    }
	
	hrm.common.closeDialog('dlgEmpListDiv');
}
//批量删除考勤机中员工信息
//删除提交操作(点击Dialog'删除按钮')
function batchDelete(){
	document.getElementById('btnBatchDelete').disabled='disabled';
	document.getElementById('empIdStr').value = empSelector_choseEmpIdStr();
    if(document.getElementById('empIdStr').value=="" || document.getElementById('empIdStr').value.length<=0){
		alert("请你选择要在考勤机中删除的用户");
		document.getElementById('btnBatchDelete').disabled='';
		return false;
    }
    var machineNo = "";
    var machineArray = document.getElementsByName("batchDelete_machineNo");
    if(machineArray == null){
		alert("请先添加考勤机");
		document.getElementById('btnBatchDelete').disabled='';
		return false;
    }
    for(var i=0;i<machineArray.length;i++){
		if(machineArray[i].checked){
			if(machineNo != ""){
				machineNo += ";";
			}
			machineNo += machineArray[i].value;
		}
    }
    if(machineNo == ""){
		alert("请选择考勤机");
		document.getElementById('btnBatchDelete').disabled='';
		return false;
    }
    if(!confirm("你确定要删除这些员工信息吗？")){
    	document.getElementById('btnBatchDelete').disabled='';
		return false;
	}
    DwrSyncAttdMachine.batchDelete(document.getElementById('empIdStr').value,machineNo,callback);
    function callback(data){
    	if("SUCCESS" == data){
        	successMsg("errMsg","向考勤机中批量删除考勤机中员工信息操作成功！");
        }else{
        	var closeBtn = "<br/><input value='关闭' type=\"button\" onclick='hrm.common.closeDialog(\"dlgSyncResultDiv\");window.location=\"attdSyncRecordShow.action\";'/>";
        	var resultField = "<textarea rows='10' cols='70' readonly='readonly'>"+data+"</textarea>";
        	$('#dlgSyncResultDiv').html(resultField+closeBtn);
    		hrm.common.openDialog('dlgSyncResultDiv');
		}
    }
	
	hrm.common.closeDialog('dlgEmpListDiv');
   	document.getElementById('btnBatchDelete').disabled='';
}
function empSelecotr(operate){
	switchOperate(operate);
	empSelector_searchEmp();
	hrm.common.openDialog('dlgEmpListDiv');
} 
hrm.common.initDialog('dlgSyncResultDiv',480);
hrm.common.initDialog('dlgEmpListDiv',480);
</script>
