<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@taglib prefix="s" uri="webwork"%>

<div id="dlgVacationUpdate" style="width:430px;display:none;">
	<form id="updateLBForm">
	<table cellSpacing=0 cellPadding=0 width="100%" >
		<tr id="row_lbBalForwardDay">
			<td>上年余额(天)：</td>
			<td class="errorMessage">
				<input id="lbBalForwardDay" type="text" size="4" maxlength="4" onkeyup="value=value.replace(/\D/g,'')"/>
			</td>
		</tr>
		<tr id="row_lbBalForwardHour">
			<td>上年余额(小时)：</td>
			<td class="errorMessage">
				<input id="lbBalForwardHour" type="text" size="4" maxlength="4" onkeyup="value=value.replace(/\D/g,'')"/>
			</td>
		</tr>
		<tr id="row_lbBalEndDate">
			<td>余额到期日：</td>
			<td class="errorMessage"> 
			    <s:textfield   id="lbBalEndDate" name="lbBalEndDate" required="true" size="12" />	
			    <img onclick="WdatePicker({el:'lbBalEndDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">			
				<label id="label_lbBalEndDate">&nbsp;</label>
			</td>
		</tr>
		<tr id="row_lbDaysOfYear">
			<td>本年额度(天)：</td>
			<td class="errorMessage">
				<input id="lbDaysOfYear" onkeyup="value=value.replace(/\D/g,'')"/>
			</td>
		</tr>
		<tr id="row_lbHoursOfYear">
			<td>本年额度(小时)：</td>
			<td class="errorMessage">
				<input id="lbHoursOfYear" onkeyup="value=value.replace(/\D/g,'')"/>
			</td>
		</tr>
		<tr id="row_lbDaysForRelease">
			<td nowrap="nowrap">释放额度(天)：</td>
			<td class="errorMessage">
				<input id="lbDaysForRelease"  onkeyup="value=value.replace(/\D/g,'')"/>
			</td>
		</tr>
		<tr id="row_lbHoursForRelease">
			<td nowrap="nowrap">释放额度(小时)：</td>
			<td class="errorMessage">
				<input id="lbHoursForRelease" onkeyup="value=value.replace(/\D/g,'')" />
			</td>
		</tr>
		<tr id="row_lbReleaseStartDate">
			<td>释放起始日：</td>
			<td class="errorMessage"> 
			    <s:textfield   id="lbReleaseStartDate" name="lbReleaseStartDate" required="true" size="12"  />	
				<img onclick="WdatePicker({el:'lbReleaseStartDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">			
			 	<label id="label_lbReleaseStartDate">&nbsp;</label>
			</td>
		</tr>
		<tr id="row_lbOtherDays">
			<td><label id="lbOtherDays_label">其他参数(天)</label>：</td>
			<td class="errorMessage"><input id="lbOtherDays"  onkeyup="value=value.replace(/\D/g,'')"/></td>
		</tr>
		<tr id="row_lbOtherHours">
			<td>其他参数(小时)：</td>
			<td class="errorMessage"><input id="lbOtherHours"  onkeyup="value=value.replace(/\D/g,'')"/></td>
		</tr>
		<tr>
			<td>备注：</td>
			<td class="errorMessage">                                       
				<textarea id="lbComments" name="lbComments" cols="28" rows="2" onkeypress="hrm.common.MKeyTextLength(this,128);"></textarea>
			</td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<input id="btn_update_leave_type" type="button" value="保存" onclick="doUpdateBalance()"/>
				<input id="btn_leave_type_close" type="button"	value="关闭" onclick="hrm.common.closeDialog('dlgVacationUpdate');"/>
				<input type="hidden" id="lbId" />
			</td>
		</tr>
	</table>
	</form>
</div>

<script type="text/javascript">  
function showUpdateBalanceDiv(id){ //在_currentYear.jsp中调用
	//后台查询记录
	LeaveBalanceDWR.getLeavebalanceById(id,callback);
	//将查询结果填充到层中
	function callback(data){
		if(data == null){
			alert("数据不存在或已经被删除,请刷新后重试!");
			return;
		}
		//显示/隐藏
		changeCat(data.lbLeaveType);
		//form表单赋值
		setFieldValues(data);
		//显示div
		//document.getElementById('dlgVacationUpdate').style.display="block";
		hrm.common.openDialog('dlgVacationUpdate');	
	}
}

//form表单赋值
function setFieldValues(data){
	var row = document.getElementById("row_"+data.lbId);
	var empName = row.cells[2].innerHTML.trim();
	//document.getElementById("updateLBTitle").innerHTML = "修改员工("+empName+")"+data.lbYear+"年的假期余额";
	$('#dlgVacationUpdate').dialog("option","title","修改员工("+empName+")"+data.lbYear+"年的假期余额");
	
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
	
//显示或隐藏	
function changeCat(value){
	var va = ""+value;
	switch(va){
		case '0'://普通请假,只显示基本字段
		case '2'://事假
		case '3'://病假
		case '4'://病假住院
		case '6'://婚假
		case '7'://产假
		case '8'://出差
		case '9'://因公外出
			hideAll();
			break;
		case '1'://年假
			hideAll();
			document.getElementById('row_lbBalEndDate').style.display='';//余额到期日
			document.getElementById('row_lbBalForwardDay').style.display='';//上年携带(天)
			document.getElementById('row_lbBalForwardHour').style.display='';//上年携带(小时)
			document.getElementById('row_lbDaysOfYear').style.display='';//本年额度(天)
			document.getElementById('row_lbHoursOfYear').style.display='';//本年额度(小时)
			document.getElementById('row_lbDaysForRelease').style.display='';//释放额度(天)
			document.getElementById('row_lbHoursForRelease').style.display='';//释放额度(小时)
			document.getElementById('row_lbReleaseStartDate').style.display='';//释放起始日
			document.getElementById('row_lbOtherDays').style.display='';//其他参数
			//document.getElementById('row_lbOtherHours').style.display='';//其他参数(小时)
			document.getElementById('lbOtherDays_label').innerHTML="法定年假天数";
			break;
		case '5'://带薪病假
			hideAll();
			document.getElementById('row_lbDaysOfYear').style.display='';//本年额度(天)
			document.getElementById('row_lbHoursOfYear').style.display='';//本年额度(小时)
		    document.getElementById('row_lbDaysForRelease').style.display='';//释放额度(天)
			document.getElementById('row_lbHoursForRelease').style.display='';//释放额度(小时)
			document.getElementById('row_lbReleaseStartDate').style.display='';//释放起始日
			break;
		case '10'://调休(不过期)
			hideAll();
			document.getElementById('row_lbBalEndDate').style.display='';//余额到期日
			document.getElementById('row_lbBalForwardDay').style.display='';//上年携带(天)
			document.getElementById('row_lbBalForwardHour').style.display='';//上年携带(小时)
			break;
		case '11'://调休(过期)
			hideAll();
			document.getElementById('row_lbOtherDays').style.display='';//其他参数(天)
			//document.getElementById('row_lbOtherHours').style.display='';//其他参数(小时)
			document.getElementById('lbOtherDays_label').innerHTML="调休过期天数";
			break;
		default :
			break;
	}
};
	
//隐藏所有	
function hideAll(){
	document.getElementById('row_lbBalEndDate').style.display='none';//余额到期日
	document.getElementById('row_lbBalForwardDay').style.display='none';//上年携带(天)
	document.getElementById('row_lbBalForwardHour').style.display='none';//上年携带(小时)
	document.getElementById('row_lbDaysOfYear').style.display='none';//本年额度(天)
	document.getElementById('row_lbHoursOfYear').style.display='none';//本年额度(小时)
	document.getElementById('row_lbDaysForRelease').style.display='none';//释放额度(天)
	document.getElementById('row_lbHoursForRelease').style.display='none';//释放额度(小时)
	document.getElementById('row_lbReleaseStartDate').style.display='none';//释放起始日
	document.getElementById('row_lbOtherDays').style.display='none';//其他参数(天)
	document.getElementById('row_lbOtherHours').style.display='none';//其他参数(小时)
	
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
	
//保存(点击Dialog中的保存按钮)
function doUpdateBalance(){
	//验证lbBalEndDate
	var date = document.getElementById("lbBalEndDate");
	if(!validate(date)){
		return;
	}
	//验证lbReleaseStartDate
	var date = document.getElementById("lbReleaseStartDate");
	if(!validate(date)){
		return;
	}
	//组装leavebalance对象
	var leaveBalance = DWRUtil.getValues("updateLBForm");
	leaveBalance.lbBalEndDate = createDate(document.getElementById('lbBalEndDate').value);
	leaveBalance.lbReleaseStartDate = createDate(document.getElementById('lbReleaseStartDate').value);
	//dwr方法调用
	LeaveBalanceDWR.updateLeaveBalance(leaveBalance,callback);
	//回调将结果写回到页面
	function callback(data){
		if(data != null){
			alert(data);
			return;
		}
		var row = document.getElementById("row_"+leaveBalance.lbId);
		var lbType = document.getElementById("lbType").value;
		if(lbType == '1'){
			//上年余额
			row.cells[4].innerHTML = leaveBalance.lbBalForwardDay;
			//row.cells[5].innerHTML = leaveBalance.lbBalForwardHour;
			//余额到期日
			row.cells[5].innerHTML = formatDate(leaveBalance.lbBalEndDate);
			//本年额度
			row.cells[6].innerHTML = leaveBalance.lbDaysOfYear;
			//释放额度
			row.cells[7].innerHTML = leaveBalance.lbDaysForRelease;
			//释放起始日
			row.cells[8].innerHTML = formatDate(leaveBalance.lbReleaseStartDate);
			//其他
			row.cells[9].innerHTML = leaveBalance.lbOtherDays;
			//备注
			row.cells[10].innerHTML = leaveBalance.lbComments;
		} else if(lbType == '5'){
			//本年额度
			row.cells[4].innerHTML = leaveBalance.lbDaysOfYear;
			//释放额度
			row.cells[5].innerHTML = leaveBalance.lbDaysForRelease;
			//释放起始日
			row.cells[6].innerHTML = formatDate(leaveBalance.lbReleaseStartDate);
			//备注
			row.cells[7].innerHTML = leaveBalance.lbComments;
		}else if(lbType == '10'){
			//上年余额
			row.cells[4].innerHTML = leaveBalance.lbBalForwardDay;
			//余额到期日
			row.cells[5].innerHTML =  formatDate(leaveBalance.lbBalEndDate);
			//本年额度
			row.cells[6].innerHTML = leaveBalance.lbDaysOfYear;
			//备注
			row.cells[7].innerHTML = leaveBalance.lbComments;
		}
		successMsg("errMsg", "假期余额修改成功。");
		hrm.common.closeDialog('dlgVacationUpdate');	//关闭Dialog
	}
}

//在currentYear中调用
function deleteBalance(id){
	if(!confirm("确定要删除吗？")){
		return;
	}
	LeaveBalanceDWR.deleteLeaveBalanceById(id,callback);
	function callback(data){
		if(data != null){
			alert(data);
			return;
		}
		successMsg("errMsg", "假期余额删除成功。");
		document.getElementById("row_"+id).style.display="none";
	}	
}

function validate(date){
	if(!date || date.value == ''){
		return true;
	}
	var reg = /^\d{4}-\d{1,2}-\d{1,2}$/;
	if(! reg.exec(date.value)){
		alert("日期格式错误!");
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