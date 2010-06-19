<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<div id="comentinput" style="width:320px;display:none;">
  <input type="hidden" name="pageTitle6" id="pageTitle6"/>	
	<form id="Content" name="Content" action="#" method="POST" enctype="multipart/form-data">
		<table cellSpacing=0 cellPadding=0 width="100%" class="listView" width="100%">
			<TR>
				<s:textarea id="logComents" name="comments" label="备注" cols="25" rows="5" />	
			</TR>
			<TR id="errorinfoDiv">
				<td colspan="2" align="center"><label id="errorinfo" style="color:red;"></label></td>
			</TR>
			<TR>
				<TD width="10%" align="right">
					&nbsp;
				</TD>
				<TD width="70%"  align="left">
					<input id="butionSub" class="button"  type="button" onclick="sub()" value="">
					<input  class="button"  type="button" onclick="hrm.common.closeDialog('comentinput');" value="取消">
				</TD>
			</TR>
	  </table>
	</form>	 
</div>

<script type="text/javascript" language="javascript">
 var operateID;
 var operateNO;
 var operateOp;
 var operateAction;
 
 function InputComments(opId,opNo,operation,action){ //显示dialog
 	try{
 	if(((action=='deptLeaveSearch.action'||action=='deptOvertimeSearch.action'||action=='hrOvertimeSearch.action'||action=='hrLeaveSearch.action')&&(operation=='approve'||operation=='reject'))||((action=='allLeaveSearch.action'||action=='allOvertimeSearch.action')&&(operation=='cancel'))){
 		
 		DWRUtil.setValue('logComents','');
 		DWRUtil.setValue('errorinfo','');
 		if(action=='deptLeaveSearch.action'){
 			DWRUtil.setValue('pageTitle6','部门请假 ');
 		}else if(action=='deptOvertimeSearch.action'){
 			DWRUtil.setValue('pageTitle6','部门加班 ');
 		}else if(action=='hrOvertimeSearch.action'){
 			DWRUtil.setValue('pageTitle6','HR加班 ');
 		}else if(action=='hrLeaveSearch.action'){
 			DWRUtil.setValue('pageTitle6','HR请假 ');
 		}else if(action=='allLeaveSearch.action'){
 			DWRUtil.setValue('pageTitle6','员工请假 ');
 		}else if(action=='allOvertimeSearch.action'){
 			DWRUtil.setValue('pageTitle6','员工加班 ');
 		}
		if(operation=='approve'){
 			DWRUtil.setValue('pageTitle6',DWRUtil.getValue('pageTitle6')+'批准');//
 			DWRUtil.setValue('butionSub','批准申请');
 		}else if(operation=='reject'){
 			DWRUtil.setValue('pageTitle6',DWRUtil.getValue('pageTitle6')+'拒绝');
 			DWRUtil.setValue('butionSub','拒绝申请');
 		}else if(operation=='cancel'){
 			DWRUtil.setValue('pageTitle6',DWRUtil.getValue('pageTitle6')+'废弃');
 			DWRUtil.setValue('butionSub','废弃申请');
 		}
 		
 		DWRUtil.setValue('pageTitle6',DWRUtil.getValue('pageTitle6')+'   编号:'+opNo);
 		
 		operateID=opId;
 		operateNO=opNo;
 		operateOp=operation;
 		operateAction=action;
 		
 		$('#comentinput').dialog("option","title",$('#pageTitle6').val());
	 	hrm.common.openDialog('comentinput');
 	}
 	}catch(e){alert(e);}
 }
 
 function sub(){
 try{
	var syslogcoment=DWRUtil.getValue('logComents');
 	if(operateAction=='deptLeaveSearch.action'||operateAction=='deptOvertimeSearch.action'||operateAction=='hrLeaveSearch.action'
 	   ||operateAction=='hrOvertimeSearch.action'||operateAction=='allLeaveSearch.action'||operateAction=='allOvertimeSearch.action'){
 		
 		dwr.engine._execute(EmpExaminAction._path, 'EmpExaminAction', 'approveOrReject',operateAction,operateOp,operateID,syslogcoment,'false',operate_callback);
 	}
 	}catch(e){alert(e);}
 }
 
 function operate_callback(result) {
   
		if(result=='suc'){
			hrm.common.closeDialog('comentinput'); //关闭dialog
		if(operateAction=='deptLeaveSearch.action'||operateAction=='hrLeaveSearch.action'){
	 		if(operateOp=='approve'){
	 			$('#infoMeg').val('编号为'+operateNO+'的请假单审批通过。');
	 		}else if(operateOp=='reject'){
	 			$('#infoMeg').val('编号为'+operateNO+'的请假单已被拒绝。');
	 		}
 		}else if(operateAction=='deptOvertimeSearch.action'||operateAction=='hrOvertimeSearch.action'){
	 		if(operateOp=='approve'){
				$('#infoMeg').val('编号为'+operateNO+'的加班单审批通过。');
	 		}else if(operateOp=='reject'){
	 			$('#infoMeg').val('编号为'+operateNO+'的加班单已被拒绝。');
	 		}
 		}else if(operateAction=='allLeaveSearch.action'){
	 		if(operateOp=='cancel'){
				$('#infoMeg').val('编号为'+operateNO+'的请假单已被作废。');
	 		}
 		}else if(operateAction=='allOvertimeSearch.action'){
	 		if(operateOp=='cancel'){
	 			$('#infoMeg').val('编号为'+operateNO+'的加班单已被作废。');
	 		}
 		}
			$('#actionSrc').get(0).submit();
		}else{
			DWRUtil.setValue('errorinfo',result);
		}
	}
</script>


