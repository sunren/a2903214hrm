<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<div id="comentinput" style="width:320px;display:none;">
  <input type="hidden" name="pageTitle6" id="pageTitle6"/>	
	<form id="Content" name="Content" action="#" method="POST" enctype="multipart/form-data">
		<table cellSpacing=0 cellPadding=0 width="100%" class="listView" width="100%">
			<TR>
				<s:textarea id="logComents" name="comments" label="��ע" cols="25" rows="5" />	
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
					<input  class="button"  type="button" onclick="hrm.common.closeDialog('comentinput');" value="ȡ��">
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
 
 function InputComments(opId,opNo,operation,action){ //��ʾdialog
 	try{
 	if(((action=='deptLeaveSearch.action'||action=='deptOvertimeSearch.action'||action=='hrOvertimeSearch.action'||action=='hrLeaveSearch.action')&&(operation=='approve'||operation=='reject'))||((action=='allLeaveSearch.action'||action=='allOvertimeSearch.action')&&(operation=='cancel'))){
 		
 		DWRUtil.setValue('logComents','');
 		DWRUtil.setValue('errorinfo','');
 		if(action=='deptLeaveSearch.action'){
 			DWRUtil.setValue('pageTitle6','������� ');
 		}else if(action=='deptOvertimeSearch.action'){
 			DWRUtil.setValue('pageTitle6','���żӰ� ');
 		}else if(action=='hrOvertimeSearch.action'){
 			DWRUtil.setValue('pageTitle6','HR�Ӱ� ');
 		}else if(action=='hrLeaveSearch.action'){
 			DWRUtil.setValue('pageTitle6','HR��� ');
 		}else if(action=='allLeaveSearch.action'){
 			DWRUtil.setValue('pageTitle6','Ա����� ');
 		}else if(action=='allOvertimeSearch.action'){
 			DWRUtil.setValue('pageTitle6','Ա���Ӱ� ');
 		}
		if(operation=='approve'){
 			DWRUtil.setValue('pageTitle6',DWRUtil.getValue('pageTitle6')+'��׼');//
 			DWRUtil.setValue('butionSub','��׼����');
 		}else if(operation=='reject'){
 			DWRUtil.setValue('pageTitle6',DWRUtil.getValue('pageTitle6')+'�ܾ�');
 			DWRUtil.setValue('butionSub','�ܾ�����');
 		}else if(operation=='cancel'){
 			DWRUtil.setValue('pageTitle6',DWRUtil.getValue('pageTitle6')+'����');
 			DWRUtil.setValue('butionSub','��������');
 		}
 		
 		DWRUtil.setValue('pageTitle6',DWRUtil.getValue('pageTitle6')+'   ���:'+opNo);
 		
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
			hrm.common.closeDialog('comentinput'); //�ر�dialog
		if(operateAction=='deptLeaveSearch.action'||operateAction=='hrLeaveSearch.action'){
	 		if(operateOp=='approve'){
	 			$('#infoMeg').val('���Ϊ'+operateNO+'����ٵ�����ͨ����');
	 		}else if(operateOp=='reject'){
	 			$('#infoMeg').val('���Ϊ'+operateNO+'����ٵ��ѱ��ܾ���');
	 		}
 		}else if(operateAction=='deptOvertimeSearch.action'||operateAction=='hrOvertimeSearch.action'){
	 		if(operateOp=='approve'){
				$('#infoMeg').val('���Ϊ'+operateNO+'�ļӰ൥����ͨ����');
	 		}else if(operateOp=='reject'){
	 			$('#infoMeg').val('���Ϊ'+operateNO+'�ļӰ൥�ѱ��ܾ���');
	 		}
 		}else if(operateAction=='allLeaveSearch.action'){
	 		if(operateOp=='cancel'){
				$('#infoMeg').val('���Ϊ'+operateNO+'����ٵ��ѱ����ϡ�');
	 		}
 		}else if(operateAction=='allOvertimeSearch.action'){
	 		if(operateOp=='cancel'){
	 			$('#infoMeg').val('���Ϊ'+operateNO+'�ļӰ൥�ѱ����ϡ�');
	 		}
 		}
			$('#actionSrc').get(0).submit();
		}else{
			DWRUtil.setValue('errorinfo',result);
		}
	}
</script>


