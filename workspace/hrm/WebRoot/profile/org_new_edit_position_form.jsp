<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<table width="80%" border="0" cellspacing="0" cellpadding="0" class="gridtableList" align="center">
    <tr>
		<td width="20%" align="right">职位名称<span class="required">*</span>：</td>
		<td width="20%" align="left" colspan="3">
			<s:textfield id="position.positionName" name="position.positionName" size="16" maxlength="64"/>
		</td>
	</tr>
	<tr>
		<td align="right">职位描述：</td>
		<td align="left" colspan="3">
			<s:textfield id="position.positionDesc" name="position.positionName" size="16" maxlength="64"/>
			&nbsp;
			上传附件：<s:file id="position.positionDescAttach" name="position.positionDescAttach" tabindex="31" size="30"/>
        </td>
	</tr>
	<tr>
		<td align="right">编制数：</td>
		<td align="left">
		    <s:textfield id="position.positionMaxEmployee" name="position.positionMaxEmployee" size="16" maxlength="64" onblur="showHideExceed(this.value);"/>
        </td>
        <td align="right">是否允许超编：</td>
		<td align="left">
		    <s:select id="position.positionMaxAllowExceed" value="position.positionMaxAllowExceed" list="{'':'请选择','1':'是','0':'否'}"></s:select>
        </td>
	</tr>
</table>
<script type="text/javascript">
function showHideExceed(orderNum){
    if(orderNum==null || orderNum.trim()==''){
        document.getElementById('position.positionMaxAllowExceed').style.display = "none";
    }else if(parseInt(orderNum)>0){
    	document.getElementById('position.positionMaxAllowExceed').style.display = "block";
    }
}
</script>