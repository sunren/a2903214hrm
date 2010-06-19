<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<div id="dlgRejectComm" style="display:none;width:320;"> 	 
	<table cellSpacing=0 cellPadding=0 width="100%" class="listView" width="100%">
		<TR>
			<s:textarea id="logComents_r" name="logComments_r" label="备注" cols="25" rows="5" />	
		</TR>
		<TR id="errorinfoDiv">
			<td colspan="2" align="center"><label id="errorinfo" style="color:red;"></label></td>
		</TR>
		<TR>
			<TD width="10%" align="right">
				&nbsp;
			</TD>
			<TD width="70%"  align="left">
				<input id="butionSub" class="button"  type="button" onclick="batchReject();" value="提交">
				<input  class="button"  type="button" onclick="hrm.common.closeDialog('dlgRejectComm');" value="取消">
			</TD>
		</TR>
	</table>	 
</div>
<script type="text/javascript" language="javascript">  
hrm.common.initDialog('dlgRejectComm');
function commonbatch(approveOper,logComment,beanComment,formId,actionName){       
    var comment=$('#'+logComment); 
    if( $('#'+approveOper).val() == "reject" && comment.val() == ""){        
        alert("拒绝时必须填写备注！");        
        return false;   
    }
    $('#'+beanComment).val(comment.val()); //备注信息填入表单
    var formElement=$('#'+formId).get(0); //获取当前第一个表单对象
    formElement.action = actionName; //js方式提交表单           
	formElement.submit(); 
    hrm.common.closeDialog('dlgRejectComm'); //common.js关闭dialog    
}     
</script>