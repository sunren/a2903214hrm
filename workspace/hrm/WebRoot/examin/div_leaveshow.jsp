<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<script type="text/javascript" src="../dwr/interface/EmpExaminAction.js"></script>

<div id="dlgLrShow" style="width:560px;display:none;">  	
	<table cellSpacing=1 cellPadding=0 width="100%" class="gridview" width="100%">
		<TR>
			<TD width="15%"  class="tablefield">
					��ٵ����
			</TD>
			<TD width="35%" style="word-break:break-all;">
				<label id="lr-lrNo"></label>
			</TD>
			<TD width="15%" class="tablefield">
				����
			</TD>
			<TD width="35%" >
				<label id="lr-lrLtNo-ltName"></label>
			</TD>
		</TR>
		<TR>
			<TD class="tablefield">
				Ա��(���)
			</TD>
			<TD>
				<label id="lr-lrEmpNo-empName"></label>
				(<label id="lr-lrEmpNo-empDistinctNo"></label>)
			</TD>	
			<TD class="tablefield" >
				���ڲ���
			</TD>
			<TD >
				<label id="lr-lrEmpDept-departmentName"></label>
			</TD>
		</TR>
		<TR>
			<TD  class="tablefield">
				��ʼʱ��
			</TD>
			<TD>
				<label id="lr-lrStartDate" ></label>
			</TD>
			<TD class="tablefield" >
				����ʱ��
			</TD>
			<TD >
				<label id="lr-lrEndDate" ></label>
			</TD>
		</TR>
		<TR>
			<TD  class="tablefield">
				��ʱ��
			</TD>
			<TD>
				<label id="lr-lrTotalTime"></label>
			</TD>
			<TD class="tablefield" >
				״̬
			</TD>
			<TD>
				<label id="lr-lrStatusMean"></label>
			</TD>
		</TR>
		<TR>
			<TD class="tablefield">
				����ʱ��
			</TD>
			<TD>
				<label id="lr-lrCreateBy-empName"></label>��<label id="lr-lrCreateTime"></label>
			</TD>
			<TD class="tablefield" >
				����޸�ʱ��
			</TD>
			<TD>
				<label id="lr-lrLastChangeBy-empName"></label>��<label id="lr-lrLastChangeTime"></label>
			</TD>
		</TR>
		<TR>
			<TD class="tablefield" >
				�������
			</TD>
			<TD class="listView" colspan="3" >
				<label id="lr-lrReason"></label>
			</TD>
		</TR>
		<tr>
			<td align="center" colspan="4"><input type="button" value="�ر�" class="button" onclick="hrm.common.closeDialog('dlgLrShow');"/></td>										
		</tr>
	</table>
</div>

<script type="text/javascript" language="javascript">  
 var leave_type = '<s:property value="leaveType"/>'; //�������
 function ShowLR(var1){  
  	var lrid=var1;  //var1��������
 	EmpExaminAction.getLRContext(lrid,showLr_callback);//dwr����,��ȡ��ٵ�����
	function showLr_callback(lr) { //�ص�����(lrΪ��ٵ�)       
		if((leave_type=='2' || leave_type == '0') && lr.lrStartApm != null){//��
 			lr.lrStartDate=lr.lrStartDate.toHRMDateString();
 			lr.lrStartApm.toString()=="0"?(lr.lrStartDate+=" ����"):(lr.lrStartDate+=" ����");
 		 	lr.lrEndDate = lr.lrEndDate.toHRMDateString();
 		 	lr.lrEndApm.toString() == "0"?(lr.lrEndDate+=" ����"):(lr.lrEndDate+=" ����");
 		 	lr.lrTotalTime=lr.lrTotalDays+" ��";
 		}else if(leave_type=='2' && lr.lrStartApm == null){
 			lr.lrStartDate=lr.lrStartDate.toFormatString("yyyy-MM-dd hh:mm");
 			lr.lrEndDate=lr.lrEndDate.toFormatString("yyyy-MM-dd hh:mm");
 			lr.lrTotalTime=lr.lrTotalDays+" ��";
 	 	}else{
 	 		lr.lrStartDate=lr.lrStartDate.toFormatString("yyyy-MM-dd hh:mm");
 			lr.lrEndDate=lr.lrEndDate.toFormatString("yyyy-MM-dd hh:mm");
 			lr.lrTotalTime=lr.lrTotalHours+" Сʱ";
 		}
 		lr.lrCreateTime=lr.lrCreateTime.toFormatString('yyyy-MM-dd hh:mm:ss');
 		lr.lrLastChangeTime=lr.lrLastChangeTime.toFormatString('yyyy-MM-dd hh:mm:ss');
 		model.simple.initByObject("dlgLrShow","lr",lr);
 		
 		$("#dlgLrShow").dialog("option","title",'���Ϊ'+lr.lrNo+'����ٵ���ϸ��Ϣ');
        hrm.common.openDialog('dlgLrShow');  
 	}
 } 
</script>