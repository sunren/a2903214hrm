<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<%@ taglib prefix="ty" uri="/tengsource"%>

<head>
<title>����ģ������</title>
<script type="text/javascript" src="../dwr/interface/OutmatchModelEdit.js"></script>

</head>
<body>
<s:component template="bodyhead">
	<s:param name="pagetitle" value="iodef.ioDesc+'��ģ���б�'" />
	<s:param name="helpUrl" value="'22'" />
</s:component>
<form id="ommListForm" name="ommListForm" action="#" method="POST">
	<s:hidden id="iodef-ioId" name="iodef.ioId" />
	<table id="ommListTable" cellpadding="0" cellspacing="0" width="100%" border="0" class="gridtableList">
		<tr>
			<th align="center">ģ������</th>
			<th align="center">ģ������</th>
			<th align="center">�����ļ�����</th>
			<th align="center">Ĭ��ģ��</th>
			<th align="center">��ʾ����</th>
			<th align="center">ͳ�ƽ��λ��</th>
			<th align="center">����ʱ��</th>
			<th align="center">����޸�ʱ��</th>
			<th align="center">����</th>
		</tr>
		<s:if test="!ommList.isEmpty()"><s:iterator value="ommList" status="index">
			<tr id="omListTr_<s:property value="ommId"/>">
				<td align="center"><a id="ommName_<s:property value="ommId"/>" class="listViewTdLinkS1" href="#none" onClick="return initOutmatchList('<s:property value="iodef.ioId" />','<s:property value="ommId"/>')" ><s:property value="ommName" /></a></td>
				<td align="center"><a id="ommDesc_<s:property value="ommId"/>" class="listViewTdLinkS1" href="#none" onClick="return initOutmatchList('<s:property value="iodef.ioId" />','<s:property value="ommId"/>')" ><s:property value="ommDesc" /></a></td>
				<td align="center"><s:property value="ommOutputTypeMean" /></td>
				<td align="center" id="tdOmmDefault_<s:property value="ommId"/>"></td>
				<td align="center"><s:property value="ommNoTitleMean" /></td>
				<td align="center"><s:property value="ommStatisticPlaceMean" /></td>
				<td align="center"><s:property value="ommCreateTime" /></td>
				<td align="center"><s:property value="ommLastChangeTime" /></td>
				<td id="tdOmmOperate_<s:property value="ommId"/>"></td>
			</tr>
		</s:iterator></s:if>
		<s:else>
			<tr>
				<td align="center" colspan="11">��ģ�����</td>
			</tr>
		</s:else>
	</table>
	<div>
	<ty:auth auths="702,2">
	<input type="button" value="����ģ��" class="button" onClick="return initOutmatchList('<s:property value="iodef.ioId" />',null)"/>
	</ty:auth>
	<input type="button" value="����" class="button" onClick="window.location.href='../configuration/iodefList.action'"/>
	</div>
</form>
<script type="text/javascript">
<s:if test="!ommList.isEmpty()"><s:iterator value="ommList" status="index">
setTdOmmDefault('<s:property value="ommId"/>',<s:property value="ommDefault"/>);
</s:iterator></s:if>
function setDefaultOmm(ommId){
	var ioId=$("#iodef-ioId").val();
	OutmatchModelEdit.setDefaultOutmatchModel(ioId,ommId,setDefaultOmmCallback);
	function setDefaultOmmCallback(str){
		if(str=="success"){
			return;
		}
		var ids=str.split(":");
		setTdOmmDefault(ids[0],1);
		setTdOmmDefault(ids[1],0);
	};
}
function deleteOmm(ommId){
	if(!confirm("��ȷ��Ҫɾ����"+$("#ommName_"+ommId).html()+"-"+$("#ommDesc_"+ommId).html()+"����")){
		return;
	}
	var ioId=$("#iodef-ioId").val();
	OutmatchModelEdit.deleteOutmatchModel(ioId,ommId,deleteDefaultOmmCallback);
	function deleteDefaultOmmCallback(str){
		if(str=="success"){
			return;
		}
		$("#omListTr_"+str).remove();
	};
}
function setTdOmmDefault(ommId,ommDefault){
	if(ommDefault==1){
		$("#tdOmmDefault_"+ommId).html('<img src="../resource/images/default_yes.png" />');
		$("#tdOmmOperate_"+ommId).html('&nbsp;');
	}else{
		$("#tdOmmDefault_"+ommId).html('<a href="#" onclick="setDefaultOmm(\''+ommId+'\')"><img src="../resource/images/default_no.png" alt="��ΪĬ��ģ��" /></a>');
		$("#tdOmmOperate_"+ommId).html('<a href="#" onclick="deleteOmm(\''+ommId+'\')"><img src="../resource/images/delete.gif" alt="ɾ��" /></a>');
	}
}
</script>
<jsp:include flush="true" page="../io/div_omm_edit.jsp"></jsp:include>
</body>