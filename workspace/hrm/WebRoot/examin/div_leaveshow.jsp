<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<script type="text/javascript" src="../dwr/interface/EmpExaminAction.js"></script>

<div id="dlgLrShow" style="width:560px;display:none;">  	
	<table cellSpacing=1 cellPadding=0 width="100%" class="gridview" width="100%">
		<TR>
			<TD width="15%"  class="tablefield">
					请假单编号
			</TD>
			<TD width="35%" style="word-break:break-all;">
				<label id="lr-lrNo"></label>
			</TD>
			<TD width="15%" class="tablefield">
				种类
			</TD>
			<TD width="35%" >
				<label id="lr-lrLtNo-ltName"></label>
			</TD>
		</TR>
		<TR>
			<TD class="tablefield">
				员工(编号)
			</TD>
			<TD>
				<label id="lr-lrEmpNo-empName"></label>
				(<label id="lr-lrEmpNo-empDistinctNo"></label>)
			</TD>	
			<TD class="tablefield" >
				所在部门
			</TD>
			<TD >
				<label id="lr-lrEmpDept-departmentName"></label>
			</TD>
		</TR>
		<TR>
			<TD  class="tablefield">
				开始时间
			</TD>
			<TD>
				<label id="lr-lrStartDate" ></label>
			</TD>
			<TD class="tablefield" >
				结束时间
			</TD>
			<TD >
				<label id="lr-lrEndDate" ></label>
			</TD>
		</TR>
		<TR>
			<TD  class="tablefield">
				总时间
			</TD>
			<TD>
				<label id="lr-lrTotalTime"></label>
			</TD>
			<TD class="tablefield" >
				状态
			</TD>
			<TD>
				<label id="lr-lrStatusMean"></label>
			</TD>
		</TR>
		<TR>
			<TD class="tablefield">
				创建时间
			</TD>
			<TD>
				<label id="lr-lrCreateBy-empName"></label>于<label id="lr-lrCreateTime"></label>
			</TD>
			<TD class="tablefield" >
				最后修改时间
			</TD>
			<TD>
				<label id="lr-lrLastChangeBy-empName"></label>于<label id="lr-lrLastChangeTime"></label>
			</TD>
		</TR>
		<TR>
			<TD class="tablefield" >
				请假理由
			</TD>
			<TD class="listView" colspan="3" >
				<label id="lr-lrReason"></label>
			</TD>
		</TR>
		<tr>
			<td align="center" colspan="4"><input type="button" value="关闭" class="button" onclick="hrm.common.closeDialog('dlgLrShow');"/></td>										
		</tr>
	</table>
</div>

<script type="text/javascript" language="javascript">  
 var leave_type = '<s:property value="leaveType"/>'; //请假种类
 function ShowLR(var1){  
  	var lrid=var1;  //var1是申请编号
 	EmpExaminAction.getLRContext(lrid,showLr_callback);//dwr调用,获取请假单内容
	function showLr_callback(lr) { //回调函数(lr为请假单)       
		if((leave_type=='2' || leave_type == '0') && lr.lrStartApm != null){//天
 			lr.lrStartDate=lr.lrStartDate.toHRMDateString();
 			lr.lrStartApm.toString()=="0"?(lr.lrStartDate+=" 上午"):(lr.lrStartDate+=" 下午");
 		 	lr.lrEndDate = lr.lrEndDate.toHRMDateString();
 		 	lr.lrEndApm.toString() == "0"?(lr.lrEndDate+=" 上午"):(lr.lrEndDate+=" 下午");
 		 	lr.lrTotalTime=lr.lrTotalDays+" 天";
 		}else if(leave_type=='2' && lr.lrStartApm == null){
 			lr.lrStartDate=lr.lrStartDate.toFormatString("yyyy-MM-dd hh:mm");
 			lr.lrEndDate=lr.lrEndDate.toFormatString("yyyy-MM-dd hh:mm");
 			lr.lrTotalTime=lr.lrTotalDays+" 天";
 	 	}else{
 	 		lr.lrStartDate=lr.lrStartDate.toFormatString("yyyy-MM-dd hh:mm");
 			lr.lrEndDate=lr.lrEndDate.toFormatString("yyyy-MM-dd hh:mm");
 			lr.lrTotalTime=lr.lrTotalHours+" 小时";
 		}
 		lr.lrCreateTime=lr.lrCreateTime.toFormatString('yyyy-MM-dd hh:mm:ss');
 		lr.lrLastChangeTime=lr.lrLastChangeTime.toFormatString('yyyy-MM-dd hh:mm:ss');
 		model.simple.initByObject("dlgLrShow","lr",lr);
 		
 		$("#dlgLrShow").dialog("option","title",'编号为'+lr.lrNo+'的请假单详细信息');
        hrm.common.openDialog('dlgLrShow');  
 	}
 } 
</script>