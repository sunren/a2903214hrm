<%@page pageEncoding="GBK" contentType="text/html; charset=GBK" %>
<%@taglib prefix="ww" uri="webwork"%>
<!-- ÉóÅúdwr -->
<script type="text/javascript" src="../dwr/interface/TrepDeptApprove.js"></script>
<script type="text/javascript" src="../dwr/interface/TrepHrApprove.js"></script>
<script type="text/javascript" src="../dwr/engine.js"></script>
<script type="text/javascript" src="../dwr/util.js"></script>	

<div id="comment_div" title=""> 
	<table border="0" cellspacing="2" cellpadding="0" class="prompt_div_body" width="100%">
		<tr>			
				<ww:hidden  id="FlagDeptHR" name="FlagDeptHR" />
				<ww:hidden  id="approve_recordId" name="approve_recordId" />		
				<ww:textarea id="comments" name="comments" label="±¸×¢" cols="45" rows="5" />			
		</tr>
		<tr>
			<td colspan="2" align="center"><div id="errorMessage" style="display:none;color: red"></div></td>
		</tr>
		<tr>
		
		<td align="center" colspan="2">
			<input id="btnApprove" type="button"  onclick="disabled='disabled';approveOnePlan(this.value,document.getElementById('approve_recordId').value);" >
			<input type="button" onclick="hrm.common.closeDialog('comment_div');clean();" value="¹Ø±Õ" >
		</td>
		</tr>
	</table>	
</div>
<script type="text/javascript" language="javascript">
	hrm.common.initDialog('comment_div',450);  	
</script>			