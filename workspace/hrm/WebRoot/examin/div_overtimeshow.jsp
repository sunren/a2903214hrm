<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>

<script type="text/javascript" src="../dwr/interface/EmpExaminAction.js"></script>

<div id="dlgOtShow" style="left:210px;top:100px;z-index:5;solid; width:560px;display:none;">	
		<table cellSpacing=0 cellPadding=0 width="100%" class="gridview" width="100%">
			<TR>
				<TD width="15%" class="tablefield">
					�Ӱ൥���
				</TD>
				<TD width="35%"  style="word-break:break-all;">
					<label id="ot-orNo"></label>
				</TD>
				<TD width="15%" class="tablefield">
					�Ӱ�����
				</TD>
				<TD width="35%" >
					<label id="ot-orOtNo-otName"></label>
				</TD>
			</TR>
			<TR>
				<TD class="tablefield">
					Ա��(���)
				</TD>
				<TD>
					<label id="ot-orEmpNo-empName"></label>
					(<label id="ot-orEmpNo-empDistinctNo"></label>)
				</TD>
				<TD class="tablefield">
					���ڲ���
				</TD>
				<TD>
					<label id="ot-orEmpDept-departmentName"></label>
				</TD>
			</TR>
			<TR>
				<TD class="tablefield">
					�Ӱ࿪ʼʱ��
				</TD>
				<TD >
					<label id="ot-orStartDate" lang="Date"></label>
				</TD>
				<TD class="tablefield" >
					�Ӱ����ʱ��
				</TD>
				<TD>
					<label id="ot-orEndDate" ></label>
				</TD>
			</TR>
			<TR>
				<TD class="tablefield">
					��Сʱ
				</TD>
				<TD>
					<label id="ot-orTotalHours"></label>
					Сʱ
				</TD>
				<TD class="tablefield">
					״̬
				</TD>
				<TD>
					<label id="ot-orStatusMean"></label>
				</TD>
			</TR>
			<TR>
				<TD class="tablefield">
					����ʱ��
				</TD>
				<TD>
					<label id="ot-orCreateBy-empName"></label>��<label id="ot-orCreateTime"></label>
				</TD>
				<TD class="tablefield">
					����޸�ʱ��
				</TD>
				<TD>
					<label id="ot-orLastChangeBy-empName"></label>��<label id="ot-orLastChangeTime"></label>
				</TD>
			</TR>	
			<TR>
				<TD  class="tablefield" >
					�Ӱ�����
				</TD>
				<TD colspan="3" >
					<label id="ot-orReason"></label>
				</TD>
			</TR>
			<tr>
				<td align="center" colspan="4"><input type="button" value="�ر�" class="button" onclick="hrm.common.closeDialog('dlgOtShow');"/></td>										
			</tr>
		</table>
</div>

<script type="text/javascript" language="javascript"> 
function ShowOT(var1){
  	var otid=var1;
 	EmpExaminAction.getOTContext(otid,showLr_callback);
	function showLr_callback(ot) {
	    ot.orStartDate=ot.orStartDate.toHRMTimeString();
 		ot.orEndDate=ot.orEndDate.toHRMTimeString();
 		ot.orCreateTime=ot.orCreateTime.toHRMTimeString();
 		ot.orLastChangeTime=ot.orLastChangeTime.toHRMTimeString();
 	  
 	    model.simple.initByObject("dlgOtShow","ot",ot);	//model����
 		$("#dlgOtShow").dialog("option","title",'���Ϊ'+ot.orNo+'�ļӰ൥��ϸ��Ϣ');//����title
        hrm.common.openDialog('dlgOtShow');  
 	}
 }
</script>




