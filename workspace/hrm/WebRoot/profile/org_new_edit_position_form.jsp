<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<table width="80%" border="0" cellspacing="0" cellpadding="0" class="gridtableList" align="center">
    <tr>
		<td width="20%" align="right">ְλ����<span class="required">*</span>��</td>
		<td width="20%" align="left" colspan="3">
			<s:textfield id="position.positionName" name="position.positionName" size="16" maxlength="64"/>
		</td>
	</tr>
	<tr>
		<td align="right">ְλ������</td>
		<td align="left" colspan="3">
			<s:textfield id="position.positionDesc" name="position.positionName" size="16" maxlength="64"/>
			&nbsp;
			�ϴ�������<s:file id="position.positionDescAttach" name="position.positionDescAttach" tabindex="31" size="30"/>
        </td>
	</tr>
	<tr>
		<td align="right">��������</td>
		<td align="left">
		    <s:textfield id="position.positionMaxEmployee" name="position.positionMaxEmployee" size="16" maxlength="64" onblur="showHideExceed(this.value);"/>
        </td>
        <td align="right">�Ƿ������ࣺ</td>
		<td align="left">
		    <s:select id="position.positionMaxAllowExceed" value="position.positionMaxAllowExceed" list="{'':'��ѡ��','1':'��','0':'��'}"></s:select>
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