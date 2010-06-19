<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<%@taglib prefix="ty" uri="/tengsource"%>
<%  response.setHeader("Pragma","No-cache"); 
	response.setHeader("Cache-Control","no-cache"); 
	response.setDateHeader("Expires", 0); 
%>
<html>
<head>
	<script type="text/javascript" src="../dwr/interface/AcctItemDef.js"></script>
	<script type="text/javascript"src="../resource/js/compensation/accountItemDef.js"></script>
</head>
<body>
<table cellpadding="0" cellspacing="1" width="100%" class="tablesorter" id="table_acctitems">
	<thead>
		<tr>
			<th>��Ŀ����</th>
			<th>��Ŀ����</th>
			<th>��Ŀ����</th>
			<th>��������</th>
			<th>β������</th>
			<th>�����ʽ</th>
			<th>��ӡ����</th>
		</tr>
	</thead>
	<tbody id="tbody_acctitems">
		<s:iterator value="datadefList">
		<tr id='<s:property value="esddId"/>'>
			<td><s:property value="esddName" /></td>
			<td>
				<s:if test="esddDataType==1">��������</s:if>
				<s:elseif test="esddDataType==2">�̶������</s:elseif>
				<s:elseif test="esddDataType==3">�̶���</s:elseif>
				<s:elseif test="esddDataType==4">�̶����ܶ�</s:elseif>
				<s:elseif test="esddDataType==5">���������</s:elseif>
				<s:elseif test="esddDataType==6">������</s:elseif>
				<s:elseif test="esddDataType==7">�������ܶ�</s:elseif>
				<s:elseif test="esddDataType==8">˰ǰ����</s:elseif>
				<s:elseif test="esddDataType==9">�籣����</s:elseif>
				<s:elseif test="esddDataType==10">���˽��籣</s:elseif>
				<s:elseif test="esddDataType==11">��˾�����籣</s:elseif>
				<s:elseif test="esddDataType==12">���˽ɹ�����</s:elseif>
				<s:elseif test="esddDataType==13">��˾���ɹ�����</s:elseif>
				<s:elseif test="esddDataType==14">����������</s:elseif>
				<s:elseif test="esddDataType==15">���˽��籣�ܶ�</s:elseif>
				<s:elseif test="esddDataType==16">��˾�����籣�ܶ�</s:elseif>
				<s:elseif test="esddDataType==17">Ӧ��˰���ö�</s:elseif>
				<s:elseif test="esddDataType==18">����˰</s:elseif>
				<s:elseif test="esddDataType==19">˰������</s:elseif>
				<s:elseif test="esddDataType==20">����</s:elseif>
			</td>
			<td><s:property value="esddDesc" /></td>
			<td>
				<s:if test="esddDataIsCalc==0">�̶�ֵ</s:if>
				<s:elseif test="esddDataIsCalc==1">����ֵ</s:elseif>
				<s:else>���㹫ʽ</s:else>
			</td>
			<td>
				<s:if test="esddDataRounding==0">������</s:if>
				<s:if test="esddDataRounding==1">���ֽ���</s:if>
				<s:if test="esddDataRounding==2">���ǽ�Ԫ</s:if>
				<s:if test="esddDataRounding==3">�����������</s:if>
				<s:if test="esddDataRounding==4">���������Ԫ</s:if>
				<s:if test="esddDataRounding==5">ȥ��</s:if>
				<s:if test="esddDataRounding==6">���ƽǷ�</s:if>
			</td>
			<td>
				<s:if test="esddDataOutput==0"> �����</s:if>
				<s:else>
					<s:if test="esddDataOutput==1">excel���</s:if>
					<s:else>�����</s:else>
				</s:else>
			</td>
			<td><s:property value="esddDataLength" /></td>
		</tr>
	</s:iterator>
	</tbody>
</table>
<div class="btnlink">
	<a href="#" id="link_add_acctitems">����</a>
	<a href="#" id="link_delete_acctitems">ɾ��</a>
	<a href="#" id="link_update_acctitems">�޸�</a>
	<a href="#" id="link_sort_acctitems">��������</a>
	<s:if test="changeIodef.length()>0">
	<a href="#" id="link_refresh_acctitems" onclick="refreshOMConfig('<s:property value="changeIodef" />');">ˢ��</a>
	</s:if>
</div>

<div id="dialog_acctitems" title="������Ŀ����">
	<table cellpadding="0" cellspacing="1" width="100%">
		<tr>
			<td>��Ŀ����<font color="red">*</font>��</td>
			<td><input id="esddName" type="text" maxlength="64">
			    <label id="label_esddName">&nbsp;</label>
			</td>
		</tr>
		<tr>
			<td>��Ŀ����<font color="red">*</font>��</td>
			<td>
				<select id="esddDataType">
					<option style="color: red;" value="1">��������</option>
					<option value="2">�̶������</option>
					<option value="3">�̶���</option>
					<option value="4">�̶����ܶ�</option>
					<option value="5">���������</option>
					<option value="6">������</option>
					<option value="7">�������ܶ�</option>
					<option style="color: red;" value="8">˰ǰ����</option>
					<option value="9">�籣����</option>
					<option value="10">���˽��籣</option>
					<option value="11">��˾�����籣</option>
					<option value="12">���˽ɹ�����</option>
					<option value="13">��˾���ɹ�����</option>
					<option value="14">����������</option>
					<option value="15">���˽��籣�ܶ�</option>
					<option value="16">��˾�����籣�ܶ�</option>
					<option value="17">Ӧ��˰���ö�</option>
					<option style="color: red;" value="18">����˰</option>
					<option style="color: red;" value="19">˰������</option>
					<option value="20">����</option>
				</select>
			</td>
		</tr>
		<tr>
			<td>��Ŀ����<font color="red">*</font>��</td>
			<td class="errorMessage">
				<input id="esddDesc" type="text" maxlength="64">
				<label id="label_esddDesc">&nbsp;</label>
			</td>
		</tr>
		<tr>
			<td>��������<font color="red">*</font>��</td>
			<td>
				<s:select id="esddDataIsCalc"
					list="#{0:'�̶�ֵ', 1:'����ֵ',2:'���㹫ʽ'}" />
			</td>
		</tr>
		<tr>
			<td>β������<font color="red">*</font>��</td>
			<td>
				<s:select id="esddDataRounding"
					list="#{0:'������', 1:'���ֽ���',2:'���ǽ�Ԫ',3:'�����������',4:'���������Ԫ',5:'ȥ��',6:'���ƽǷ�'}" />
			</td>
		</tr>
		<tr>
			<td>�����ʽ��</td>
			<td>
				<s:select id="esddDataOutput" list="#{0:'�����',1:'excel���'}" />
			</td>
		</tr>
		<tr>
			<td>��ӡ���ȣ�</td>
			<td>
				<input id="esddDataLength" type="text" maxlength="64"
					onkeyup="value=value.replace(/\D/g,'')">
				<label id="label_esddDataLength">&nbsp;</label>
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td class="right">
				<input id="btn_add_acctitems" type="button" value="����" />
				<input id="btn_update_acctitems" type="button" value="����" />
				<input id="btn_close_acctitems" type="button" value="�ر�" />
				<input type="hidden" id="esddId"/>
			</td>
		</tr>
	</table>
</div>
<script type="text/javascript">
    var config={
        tableId     :"table_acctitems",
        tbodyId     :"tbody_acctitems",
        dialogId    :"dialog_acctitems",
        updateButton:"btn_update_acctitems",
        addButton   :"btn_add_acctitems",
        dialogHeight:280,
        dialogWidth :400,
        addLink     :"link_add_acctitems",
        deleteLink  :"link_delete_acctitems",
        updateLink  :"link_update_acctitems",
        sortLink    :"link_sort_acctitems",
        closeButton :"btn_close_acctitems",
        modal       :true
    };
    dataDefManager = new Empsalarydatadef(config);

// ˢ��н����Ŀ���������ã�
function refreshOMConfig(str){
    AcctItemDef.refreshOMConfig(str,refreshCallback);
    function refreshCallback(info){
        if(info == 'success'){
            successMsg("errMsg", "ˢ�³ɹ���");
        }else{
            errMsg("errMsg", "ˢ��ʧ�ܡ�");
        }
    }
}
</script>
</body>
</html>
