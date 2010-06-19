<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@taglib prefix="s" uri="webwork"%>

<div id="dlgVacationUpdate" style="width:430px;display:none;">
	<form id="updateLBForm">
	<table cellSpacing=0 cellPadding=0 width="100%" >
		<tr id="row_lbBalForwardDay">
			<td>�������(��)��</td>
			<td class="errorMessage">
				<input id="lbBalForwardDay" type="text" size="4" maxlength="4" onkeyup="value=value.replace(/\D/g,'')"/>
			</td>
		</tr>
		<tr id="row_lbBalForwardHour">
			<td>�������(Сʱ)��</td>
			<td class="errorMessage">
				<input id="lbBalForwardHour" type="text" size="4" maxlength="4" onkeyup="value=value.replace(/\D/g,'')"/>
			</td>
		</tr>
		<tr id="row_lbBalEndDate">
			<td>�����գ�</td>
			<td class="errorMessage"> 
			    <s:textfield   id="lbBalEndDate" name="lbBalEndDate" required="true" size="12" />	
			    <img onclick="WdatePicker({el:'lbBalEndDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">			
				<label id="label_lbBalEndDate">&nbsp;</label>
			</td>
		</tr>
		<tr id="row_lbDaysOfYear">
			<td>������(��)��</td>
			<td class="errorMessage">
				<input id="lbDaysOfYear" onkeyup="value=value.replace(/\D/g,'')"/>
			</td>
		</tr>
		<tr id="row_lbHoursOfYear">
			<td>������(Сʱ)��</td>
			<td class="errorMessage">
				<input id="lbHoursOfYear" onkeyup="value=value.replace(/\D/g,'')"/>
			</td>
		</tr>
		<tr id="row_lbDaysForRelease">
			<td nowrap="nowrap">�ͷŶ��(��)��</td>
			<td class="errorMessage">
				<input id="lbDaysForRelease"  onkeyup="value=value.replace(/\D/g,'')"/>
			</td>
		</tr>
		<tr id="row_lbHoursForRelease">
			<td nowrap="nowrap">�ͷŶ��(Сʱ)��</td>
			<td class="errorMessage">
				<input id="lbHoursForRelease" onkeyup="value=value.replace(/\D/g,'')" />
			</td>
		</tr>
		<tr id="row_lbReleaseStartDate">
			<td>�ͷ���ʼ�գ�</td>
			<td class="errorMessage"> 
			    <s:textfield   id="lbReleaseStartDate" name="lbReleaseStartDate" required="true" size="12"  />	
				<img onclick="WdatePicker({el:'lbReleaseStartDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">			
			 	<label id="label_lbReleaseStartDate">&nbsp;</label>
			</td>
		</tr>
		<tr id="row_lbOtherDays">
			<td><label id="lbOtherDays_label">��������(��)</label>��</td>
			<td class="errorMessage"><input id="lbOtherDays"  onkeyup="value=value.replace(/\D/g,'')"/></td>
		</tr>
		<tr id="row_lbOtherHours">
			<td>��������(Сʱ)��</td>
			<td class="errorMessage"><input id="lbOtherHours"  onkeyup="value=value.replace(/\D/g,'')"/></td>
		</tr>
		<tr>
			<td>��ע��</td>
			<td class="errorMessage">                                       
				<textarea id="lbComments" name="lbComments" cols="28" rows="2" onkeypress="hrm.common.MKeyTextLength(this,128);"></textarea>
			</td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<input id="btn_update_leave_type" type="button" value="����" onclick="doUpdateBalance()"/>
				<input id="btn_leave_type_close" type="button"	value="�ر�" onclick="hrm.common.closeDialog('dlgVacationUpdate');"/>
				<input type="hidden" id="lbId" />
			</td>
		</tr>
	</table>
	</form>
</div>

<script type="text/javascript">  
function showUpdateBalanceDiv(id){ //��_currentYear.jsp�е���
	//��̨��ѯ��¼
	LeaveBalanceDWR.getLeavebalanceById(id,callback);
	//����ѯ�����䵽����
	function callback(data){
		if(data == null){
			alert("���ݲ����ڻ��Ѿ���ɾ��,��ˢ�º�����!");
			return;
		}
		//��ʾ/����
		changeCat(data.lbLeaveType);
		//form����ֵ
		setFieldValues(data);
		//��ʾdiv
		//document.getElementById('dlgVacationUpdate').style.display="block";
		hrm.common.openDialog('dlgVacationUpdate');	
	}
}

//form����ֵ
function setFieldValues(data){
	var row = document.getElementById("row_"+data.lbId);
	var empName = row.cells[2].innerHTML.trim();
	//document.getElementById("updateLBTitle").innerHTML = "�޸�Ա��("+empName+")"+data.lbYear+"��ļ������";
	$('#dlgVacationUpdate').dialog("option","title","�޸�Ա��("+empName+")"+data.lbYear+"��ļ������");
	
	document.getElementById("lbId").value = (data.lbId);
	document.getElementById("lbBalForwardDay").value = (data.lbBalForwardDay==null?"":data.lbBalForwardDay);
	document.getElementById("lbBalForwardHour").value = (data.lbBalForwardHour==null?"":data.lbBalForwardHour);
	document.getElementById("lbComments").value = (data.lbComments==null?"":data.lbComments);
	document.getElementById("lbDaysForRelease").value = (data.lbDaysForRelease==null?"":data.lbDaysForRelease);
	document.getElementById("lbBalEndDate").value = (formatDate(data.lbBalEndDate));
	document.getElementById("lbDaysOfYear").value = (data.lbDaysOfYear==null?"":data.lbDaysOfYear);
	document.getElementById("lbHoursForRelease").value = (data.lbHoursForRelease==null?"":data.lbHoursForRelease);
	document.getElementById("lbReleaseStartDate").value = (formatDate(data.lbReleaseStartDate));
	document.getElementById("lbHoursOfYear").value = (data.lbHoursOfYear==null?"":data.lbHoursOfYear);
	document.getElementById("lbOtherDays").value = (data.lbOtherDays==null?'0':data.lbOtherDays);
	document.getElementById("lbOtherHours").value = (data.lbOtherHours==null?"":data.lbOtherHours);
}
	
//��ʾ������	
function changeCat(value){
	var va = ""+value;
	switch(va){
		case '0'://��ͨ���,ֻ��ʾ�����ֶ�
		case '2'://�¼�
		case '3'://����
		case '4'://����סԺ
		case '6'://���
		case '7'://����
		case '8'://����
		case '9'://�����
			hideAll();
			break;
		case '1'://���
			hideAll();
			document.getElementById('row_lbBalEndDate').style.display='';//������
			document.getElementById('row_lbBalForwardDay').style.display='';//����Я��(��)
			document.getElementById('row_lbBalForwardHour').style.display='';//����Я��(Сʱ)
			document.getElementById('row_lbDaysOfYear').style.display='';//������(��)
			document.getElementById('row_lbHoursOfYear').style.display='';//������(Сʱ)
			document.getElementById('row_lbDaysForRelease').style.display='';//�ͷŶ��(��)
			document.getElementById('row_lbHoursForRelease').style.display='';//�ͷŶ��(Сʱ)
			document.getElementById('row_lbReleaseStartDate').style.display='';//�ͷ���ʼ��
			document.getElementById('row_lbOtherDays').style.display='';//��������
			//document.getElementById('row_lbOtherHours').style.display='';//��������(Сʱ)
			document.getElementById('lbOtherDays_label').innerHTML="�����������";
			break;
		case '5'://��н����
			hideAll();
			document.getElementById('row_lbDaysOfYear').style.display='';//������(��)
			document.getElementById('row_lbHoursOfYear').style.display='';//������(Сʱ)
		    document.getElementById('row_lbDaysForRelease').style.display='';//�ͷŶ��(��)
			document.getElementById('row_lbHoursForRelease').style.display='';//�ͷŶ��(Сʱ)
			document.getElementById('row_lbReleaseStartDate').style.display='';//�ͷ���ʼ��
			break;
		case '10'://����(������)
			hideAll();
			document.getElementById('row_lbBalEndDate').style.display='';//������
			document.getElementById('row_lbBalForwardDay').style.display='';//����Я��(��)
			document.getElementById('row_lbBalForwardHour').style.display='';//����Я��(Сʱ)
			break;
		case '11'://����(����)
			hideAll();
			document.getElementById('row_lbOtherDays').style.display='';//��������(��)
			//document.getElementById('row_lbOtherHours').style.display='';//��������(Сʱ)
			document.getElementById('lbOtherDays_label').innerHTML="���ݹ�������";
			break;
		default :
			break;
	}
};
	
//��������	
function hideAll(){
	document.getElementById('row_lbBalEndDate').style.display='none';//������
	document.getElementById('row_lbBalForwardDay').style.display='none';//����Я��(��)
	document.getElementById('row_lbBalForwardHour').style.display='none';//����Я��(Сʱ)
	document.getElementById('row_lbDaysOfYear').style.display='none';//������(��)
	document.getElementById('row_lbHoursOfYear').style.display='none';//������(Сʱ)
	document.getElementById('row_lbDaysForRelease').style.display='none';//�ͷŶ��(��)
	document.getElementById('row_lbHoursForRelease').style.display='none';//�ͷŶ��(Сʱ)
	document.getElementById('row_lbReleaseStartDate').style.display='none';//�ͷ���ʼ��
	document.getElementById('row_lbOtherDays').style.display='none';//��������(��)
	document.getElementById('row_lbOtherHours').style.display='none';//��������(Сʱ)
	
	document.getElementById('lbBalEndDate').value = ("");
	document.getElementById('lbBalForwardDay').value = ("");
	document.getElementById('lbBalForwardHour').value = ("");
	document.getElementById('lbDaysOfYear').value = ("");
	document.getElementById('lbHoursOfYear').value = ("");
	document.getElementById('lbDaysForRelease').value = ("");
	document.getElementById('lbHoursForRelease').value = ("");
	document.getElementById('lbReleaseStartDate').value = ("");
	document.getElementById('lbOtherDays').value = ("");
	document.getElementById('lbOtherHours').value = ("");
};
	
//����(���Dialog�еı��水ť)
function doUpdateBalance(){
	//��֤lbBalEndDate
	var date = document.getElementById("lbBalEndDate");
	if(!validate(date)){
		return;
	}
	//��֤lbReleaseStartDate
	var date = document.getElementById("lbReleaseStartDate");
	if(!validate(date)){
		return;
	}
	//��װleavebalance����
	var leaveBalance = DWRUtil.getValues("updateLBForm");
	leaveBalance.lbBalEndDate = createDate(document.getElementById('lbBalEndDate').value);
	leaveBalance.lbReleaseStartDate = createDate(document.getElementById('lbReleaseStartDate').value);
	//dwr��������
	LeaveBalanceDWR.updateLeaveBalance(leaveBalance,callback);
	//�ص������д�ص�ҳ��
	function callback(data){
		if(data != null){
			alert(data);
			return;
		}
		var row = document.getElementById("row_"+leaveBalance.lbId);
		var lbType = document.getElementById("lbType").value;
		if(lbType == '1'){
			//�������
			row.cells[4].innerHTML = leaveBalance.lbBalForwardDay;
			//row.cells[5].innerHTML = leaveBalance.lbBalForwardHour;
			//������
			row.cells[5].innerHTML = formatDate(leaveBalance.lbBalEndDate);
			//������
			row.cells[6].innerHTML = leaveBalance.lbDaysOfYear;
			//�ͷŶ��
			row.cells[7].innerHTML = leaveBalance.lbDaysForRelease;
			//�ͷ���ʼ��
			row.cells[8].innerHTML = formatDate(leaveBalance.lbReleaseStartDate);
			//����
			row.cells[9].innerHTML = leaveBalance.lbOtherDays;
			//��ע
			row.cells[10].innerHTML = leaveBalance.lbComments;
		} else if(lbType == '5'){
			//������
			row.cells[4].innerHTML = leaveBalance.lbDaysOfYear;
			//�ͷŶ��
			row.cells[5].innerHTML = leaveBalance.lbDaysForRelease;
			//�ͷ���ʼ��
			row.cells[6].innerHTML = formatDate(leaveBalance.lbReleaseStartDate);
			//��ע
			row.cells[7].innerHTML = leaveBalance.lbComments;
		}else if(lbType == '10'){
			//�������
			row.cells[4].innerHTML = leaveBalance.lbBalForwardDay;
			//������
			row.cells[5].innerHTML =  formatDate(leaveBalance.lbBalEndDate);
			//������
			row.cells[6].innerHTML = leaveBalance.lbDaysOfYear;
			//��ע
			row.cells[7].innerHTML = leaveBalance.lbComments;
		}
		successMsg("errMsg", "��������޸ĳɹ���");
		hrm.common.closeDialog('dlgVacationUpdate');	//�ر�Dialog
	}
}

//��currentYear�е���
function deleteBalance(id){
	if(!confirm("ȷ��Ҫɾ����")){
		return;
	}
	LeaveBalanceDWR.deleteLeaveBalanceById(id,callback);
	function callback(data){
		if(data != null){
			alert(data);
			return;
		}
		successMsg("errMsg", "�������ɾ���ɹ���");
		document.getElementById("row_"+id).style.display="none";
	}	
}

function validate(date){
	if(!date || date.value == ''){
		return true;
	}
	var reg = /^\d{4}-\d{1,2}-\d{1,2}$/;
	if(! reg.exec(date.value)){
		alert("���ڸ�ʽ����!");
		return false;
	}
	return true;
}

function createDate(dateStr){
	if(dateStr == null || dateStr == ''){
		return null;
	}
	var dateArr = dateStr.split("-");
	var date = new Date(dateArr[0],dateArr[1]-1,dateArr[2]);
	return date;
 	};
	
function formatDate(data){
	if(data == null){
		return "";
	}
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
	return year+"-"+month+"-"+day;
}	

hrm.common.initDialog('dlgVacationUpdate');	
</script>