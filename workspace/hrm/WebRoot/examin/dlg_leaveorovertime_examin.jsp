<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<div id="dlgRejectComm" style="display:none;width:320;"> 	 
	<table cellSpacing=0 cellPadding=0 width="100%" class="listView" width="100%">
		<TR>
			<s:textarea id="logComents_r" name="logComments_r" label="��ע" cols="25" rows="5" />	
		</TR>
		<TR id="errorinfoDiv">
			<td colspan="2" align="center"><label id="errorinfo" style="color:red;"></label></td>
		</TR>
		<TR>
			<TD width="10%" align="right">
				&nbsp;
			</TD>
			<TD width="70%"  align="left">
				<input id="butionSub" class="button"  type="button" onclick="batchReject();" value="�ύ">
				<input  class="button"  type="button" onclick="hrm.common.closeDialog('dlgRejectComm');" value="ȡ��">
			</TD>
		</TR>
	</table>	 
</div>
<script type="text/javascript" language="javascript">  
hrm.common.initDialog('dlgRejectComm');
function commonbatch(approveOper,logComment,beanComment,formId,actionName){       
    var comment=$('#'+logComment); 
    if( $('#'+approveOper).val() == "reject" && comment.val() == ""){        
        alert("�ܾ�ʱ������д��ע��");        
        return false;   
    }
    $('#'+beanComment).val(comment.val()); //��ע��Ϣ�����
    var formElement=$('#'+formId).get(0); //��ȡ��ǰ��һ��������
    formElement.action = actionName; //js��ʽ�ύ��           
	formElement.submit(); 
    hrm.common.closeDialog('dlgRejectComm'); //common.js�ر�dialog    
}     
</script>