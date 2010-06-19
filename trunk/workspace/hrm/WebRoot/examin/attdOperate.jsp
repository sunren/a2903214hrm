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
<!-- ��Ӳ�����Ϣ -->
<table border="0" cellspacing="2">  
  <tr>
    <td>�������ڣ�</td>
    <td> 
       <s:textfield id="attdDate" name="attdDate" required="true" value="%{attdDate}" size="12"  />
       <img onclick="WdatePicker({el:'attdDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">							
    </td>	    
    <td>ˢ��ʱ��: </td>
    <td><s:textfield id="attdCardTime" name="attdCardTime" required="true" size="21"  />
    <img onclick="WdatePicker({el:'attdCardTime',dateFmt:'yyyy-MM-dd HH:mm:ss'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">								
    </td>
  </tr>
  <tr>
    <td>���ڻ���: </td>
    <td>
    <s:select id="aodTtdMachineNo" list="machineList" listKey="macNo" listValue="macNo" multiple="false" emptyOption="true" size="1" />
    </td>
    <td>��ע��</td>
    <td><s:textarea id="memo" name="memo" cols="19" rows="2"></s:textarea></td>
  </tr>
  <tr>
    <td align="center" colspan="4" >
        <input type="button" name="btn" onclick="saveData();" value="����" id="btnSave">   
        <input type="button" name="btn" onclick="hrm.common.closeDialog('dlgEmpListDiv');" value="�ر�">
    </td>
  </tr>
</table>
</div>
<div id="syncToMachine" style="display: none">
<!-- ���ڻ���ͬ��Ա����Ϣ -->
<table border="0" cellspacing="2"> 
  <tr>
    <td>���ڻ��ţ�</td>
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
        <input type="button" name="btn" onclick="syncToMachine();" value="ͬ��" id="btnSyncToMachine">   
        <input type="button" name="btn" onclick="hrm.common.closeDialog('dlgEmpListDiv');" value="�ر�">
    </td>
  </tr>
</table>
</div>
<div id="syncToProject" style="display: none">
<!-- ͬ�����ڻ���Ա����Ϣ -->
<table border="0" cellspacing="2">
  <tr>
    <td>���ڻ��ţ�</td>
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
        <input type="button" name="btn" onclick="syncToProject();" value="ͬ��" id="btnSyncToProject">   
        <input type="button" name="btn" onclick="hrm.common.closeDialog('dlgEmpListDiv');" value="�ر�">
    </td>
  </tr>
</table>
</div>
<div id="batchRead" style="display: none">
<!-- ������ȡ���ڻ���ˢ����¼ -->
<table border="0" cellspacing="2">
  <tr>
  	<td>���ڻ��ţ�</td>
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
        <input type="button" name="btn" onclick="batchRead();" value="��ȡ" id="btnBatchRead">   
        <input type="button" name="btn" onclick="hrm.common.closeDialog('dlgEmpListDiv');" value="�ر�">
    </td>
  </tr>
</table>
</div>
<div id="batchDelete" style="display: none">
<!-- ����ɾ�����ڻ���Ա����Ϣ -->
<table border="0" cellspacing="2">  
  <tr>
    <td>���ڻ��ţ�</td>
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
        <input type="button" name="btn" onclick="batchDelete();" value="ɾ��" id="btnBatchDelete">   
        <input type="button" name="btn" onclick="hrm.common.closeDialog('dlgEmpListDiv');" value="�ر�">
    </td>
  </tr>
</table>
</div>
<div id="dlgSyncResultDiv" title="ͬ�����" style="display:none;height:480"></div> 
<script type="text/javascript" language="javascript">
//��ѯ����(�����ѯ��ť)
function searchOriginalData(){
    if(document.originalDataShow.attdDateFrom.value == ''){
        alert("��ѡ��ʼ���ڣ�");
        return;
    }
    if(document.originalDataShow.attdDateTo.value == ''){
        alert("��ѡ��������ڣ�");
        return;
    }
    document.forms[0].submit();//�ύ��
}

//�����������(�������ť)
function deleteOriginalData(){
    if(document.originalDataShow.attdDateFrom.value == ''){
        alert("��ѡ��ʼ���ڣ�");
        return;
    }
    if(document.originalDataShow.attdDateTo.value == ''){
        alert("��ѡ��������ڣ�");
        return;
    }
    var start = document.originalDataShow.attdDateFrom.value;
    var end = document.originalDataShow.attdDateTo.value;
    if(confirm("ȷ��Ҫɾ��" + start + "��" + end + "�����ڵ�����ô��")){ //confirmȷ���Ƿ�ɾ��
    	document.all.operate.value = "deleteSome";
        document.forms[0].submit();
    }
}

//ɾ��һ������
function deleteOneData(aodId){
    if(confirm("ȷ��Ҫɾ����������ô��")){
        document.all.aodId.value = aodId;
        document.all.operate.value = "deleteOne";
        document.forms[0].submit();
    }
}

//ͬ���ڻ�ͬ�����ݲ���
function syncData(option){
	if(option=="addCard"){
		document.getElementById('ui-dialog-title-dlgEmpListDiv').innerHTML="����";
	}
	if(option=="syncToMachine"){
		document.getElementById('ui-dialog-title-dlgEmpListDiv').innerHTML="���ڻ�ͬ��Ա����Ϣ";
	}
	if(option=="syncToProject"){
		document.getElementById('ui-dialog-title-dlgEmpListDiv').innerHTML="���Ա��ͬ����Ϣ";
	}
	if(option=="batchRead"){
		document.getElementById('ui-dialog-title-dlgEmpListDiv').innerHTML="��ȡ���ڻ�ˢ������";
		document.getElementById('employeeSelector').style.display = "none";
	}
	if(option=="batchDelete"){
		document.getElementById('ui-dialog-title-dlgEmpListDiv').innerHTML="ɾ�����ڻ���Ա����Ϣ";
	}
}

//�л��������ڲ���
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

//�����ύ����(���Dialog'���水ť')
function saveData(){
    var empStrId = empSelector_choseEmpIdStr();
    if(empStrId == ''){
        alert("������ѡ��һ��Ա����");
        return false;
    }
    //����������ʽ
    var dateReg = /^\d{4}-\d{1,2}-\d{1,2}$/;
    var datetimeReg = /^\d{4}-\d{1,2}-\d{1,2} \d{1,2}:\d{1,2}(:\d{1,2})?$/;
    if(!dateReg.test(document.getElementById('attdDate').value)){
        alert("�������ڸ�ʽ����");
        return false;
    }
    if(!datetimeReg.test(document.getElementById('attdCardTime').value)){
        alert("ˢ��ʱ���ʽ����");
        return false;
    }
    if(document.getElementById('memo').value == ''){
        alert("����д��ע��");
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
            alert("ˢ��ʱ��Ϳ������ڲ�һ�£�");
            return false;
        }
        if(!window.confirm('ˢ��ʱ���뿼�����ڲ���ͬһ�죬��ȷ���Ƿ��ҹ��Σ�')){
            return false;
        }
    }
    
    document.getElementById('btnSave').disabled='disabled';
    document.getElementById('empIdStr').value = empStrId;
    DwrForAttend.addCardData(document.getElementById('empIdStr').value,document.getElementById('attdDate').value,
    	    document.getElementById('attdCardTime').value,document.getElementById('aodTtdMachineNo').value,document.getElementById('memo').value,'<s:property value="#session['empName']"/>',callback);
    function callback(data){
        if("NOAUTH" == data){
        	errMsg("errMsg","����Ȩִ�д˲����������µ�½��");
        }else if("SUCCESS" == data){
        	successMsg("errMsg","���������ɹ���");
        }else{
        	errMsg("errMsg",data);
        }
        hrm.common.closeDialog('dlgEmpListDiv');
        document.forms[0].submit();
    }
}

//���ڻ���ͬ��Ա����Ϣ
//ͬ���ύ����(���Dialog'ͬ����ť')
function syncToMachine(){
	document.getElementById('btnSyncToMachine').disabled='disabled';
	document.getElementById('empIdStr').value = empSelector_choseEmpIdStr();
    if(document.getElementById('empIdStr').value==""||document.getElementById('empIdStr').value.length<=0){
		alert("��ѡ����Ҫͬ����Ա��");
		document.getElementById('btnSyncToMachine').disabled='';
		return false;
    }
    var machineNo = "";
    var machineArray = document.getElementsByName("syncToMachine_machineNo");
    if(machineArray == null){
		alert("������ӿ��ڻ�");
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
		alert("��ѡ���ڻ�");
    	document.getElementById('btnSyncToMachine').disabled='';
		return false;
    }
	DwrSyncAttdMachine.syncToMachine(document.getElementById('empIdStr').value,machineNo,callback);
    function callback(data){
        var closeBtn = "<br/><input value='�ر�' type=\"button\" onclick='hrm.common.closeDialog(\"dlgSyncResultDiv\");window.location=\"attdSyncRecordShow.action\";'/>";
        var resultField = "<textarea rows='10' cols='70' readonly='readonly'>"+data+"</textarea>";
    	$('#dlgSyncResultDiv').html(resultField+closeBtn);
    	document.getElementById('btnSyncToMachine').disabled='';
		hrm.common.openDialog('dlgSyncResultDiv');
    }
	hrm.common.closeDialog('dlgEmpListDiv');
}

//ͬ�����ڻ���Ա����Ϣ
//ͬ���ύ����(���Dialog'ͬ����ť')
function syncToProject(){
	document.getElementById('btnSyncToProject').disabled='disabled';
	document.getElementById('empIdStr').value = empSelector_choseEmpIdStr();
    var machineNo = "";
    var machineArray = document.getElementsByName("syncToProject_machineNo");
    if(machineArray == null){
		alert("������ӿ��ڻ�");
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
		alert("��ѡ���ڻ�");
    	document.getElementById('btnSyncToProject').disabled='';
		return false;
    }
    DwrSyncAttdMachine.syncToProject(document.getElementById('empIdStr').value,machineNo,callback);
    function callback(data){
    	successMsg("errMsg",data);
    }
	
	hrm.common.closeDialog('dlgEmpListDiv');
}
//������ȡ���ڻ���Ա��ˢ����¼
//��ȡ�ύ����(���Dialog'��ȡ��ť')
function batchRead(){
	document.getElementById('btnBatchRead').disabled='disabled';
    var machineNo = "";
    var machineArray = document.getElementsByName("batchRead_machineNo");
    if(machineArray == null){
		alert("������ӿ��ڻ�");
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
		alert("��ѡ���ڻ�");
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
//����ɾ�����ڻ���Ա����Ϣ
//ɾ���ύ����(���Dialog'ɾ����ť')
function batchDelete(){
	document.getElementById('btnBatchDelete').disabled='disabled';
	document.getElementById('empIdStr').value = empSelector_choseEmpIdStr();
    if(document.getElementById('empIdStr').value=="" || document.getElementById('empIdStr').value.length<=0){
		alert("����ѡ��Ҫ�ڿ��ڻ���ɾ�����û�");
		document.getElementById('btnBatchDelete').disabled='';
		return false;
    }
    var machineNo = "";
    var machineArray = document.getElementsByName("batchDelete_machineNo");
    if(machineArray == null){
		alert("������ӿ��ڻ�");
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
		alert("��ѡ���ڻ�");
		document.getElementById('btnBatchDelete').disabled='';
		return false;
    }
    if(!confirm("��ȷ��Ҫɾ����ЩԱ����Ϣ��")){
    	document.getElementById('btnBatchDelete').disabled='';
		return false;
	}
    DwrSyncAttdMachine.batchDelete(document.getElementById('empIdStr').value,machineNo,callback);
    function callback(data){
    	if("SUCCESS" == data){
        	successMsg("errMsg","���ڻ�������ɾ�����ڻ���Ա����Ϣ�����ɹ���");
        }else{
        	var closeBtn = "<br/><input value='�ر�' type=\"button\" onclick='hrm.common.closeDialog(\"dlgSyncResultDiv\");window.location=\"attdSyncRecordShow.action\";'/>";
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
