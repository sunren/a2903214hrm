<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>

<script type="text/javascript" src="../dwr/interface/EmpExaminAction.js"></script>

<div id="dlgOtShow" style="left:210px;top:100px;z-index:5;solid; width:560px;display:none;">	
		<table cellSpacing=0 cellPadding=0 width="100%" class="gridview" width="100%">
			<TR>
				<TD width="15%" class="tablefield">
					加班单编号
				</TD>
				<TD width="35%"  style="word-break:break-all;">
					<label id="ot-orNo"></label>
				</TD>
				<TD width="15%" class="tablefield">
					加班种类
				</TD>
				<TD width="35%" >
					<label id="ot-orOtNo-otName"></label>
				</TD>
			</TR>
			<TR>
				<TD class="tablefield">
					员工(编号)
				</TD>
				<TD>
					<label id="ot-orEmpNo-empName"></label>
					(<label id="ot-orEmpNo-empDistinctNo"></label>)
				</TD>
				<TD class="tablefield">
					所在部门
				</TD>
				<TD>
					<label id="ot-orEmpDept-departmentName"></label>
				</TD>
			</TR>
			<TR>
				<TD class="tablefield">
					加班开始时间
				</TD>
				<TD >
					<label id="ot-orStartDate" lang="Date"></label>
				</TD>
				<TD class="tablefield" >
					加班结束时间
				</TD>
				<TD>
					<label id="ot-orEndDate" ></label>
				</TD>
			</TR>
			<TR>
				<TD class="tablefield">
					总小时
				</TD>
				<TD>
					<label id="ot-orTotalHours"></label>
					小时
				</TD>
				<TD class="tablefield">
					状态
				</TD>
				<TD>
					<label id="ot-orStatusMean"></label>
				</TD>
			</TR>
			<TR>
				<TD class="tablefield">
					创建时间
				</TD>
				<TD>
					<label id="ot-orCreateBy-empName"></label>于<label id="ot-orCreateTime"></label>
				</TD>
				<TD class="tablefield">
					最后修改时间
				</TD>
				<TD>
					<label id="ot-orLastChangeBy-empName"></label>于<label id="ot-orLastChangeTime"></label>
				</TD>
			</TR>	
			<TR>
				<TD  class="tablefield" >
					加班理由
				</TD>
				<TD colspan="3" >
					<label id="ot-orReason"></label>
				</TD>
			</TR>
			<tr>
				<td align="center" colspan="4"><input type="button" value="关闭" class="button" onclick="hrm.common.closeDialog('dlgOtShow');"/></td>										
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
 	  
 	    model.simple.initByObject("dlgOtShow","ot",ot);	//model方法
 		$("#dlgOtShow").dialog("option","title",'编号为'+ot.orNo+'的加班单详细信息');//设置title
        hrm.common.openDialog('dlgOtShow');  
 	}
 }
</script>




