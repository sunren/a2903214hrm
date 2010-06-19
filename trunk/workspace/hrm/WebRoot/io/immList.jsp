<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<%@ taglib prefix="ty" uri="/tengsource"%>

<head>
<title>导入模板配置</title>
<script type="text/javascript" src="../dwr/interface/InmatchModelEdit.js"></script>
<script type="text/javascript" src="../dwr/interface/IodefEdit.js"></script>
</head>
<body>
<s:component template="bodyhead">
	<s:param name="pagetitle" value="iodef.ioDesc+'：模板列表'" />
	<s:param name="helpUrl" value="'22'" />
</s:component>
<form id="immListForm" name="immListForm" action="#" method="POST">
	<s:hidden id="iodef-ioId" name="ioId" />
	<table id="immListTable" cellpadding="0" cellspacing="0" width="100%" border="0" class="gridtableList">
		<tr>
			<th align="center">模板名称</th>
			<th align="center">模板描述</th>
			<th align="center">导入文件类型</th>
			<th align="center">默认模板</th>
			<th align="center">包含标题</th>
			<th align="center">导入模式</th>
			<th align="center">创建时间</th>
			<th align="center">最后修改时间</th>
			<ty:auth auths="702,2">
			<th align="center">操作</th>
			</ty:auth>
		</tr>
		<s:if test="!immList.isEmpty()"><s:iterator value="immList" status="index">
			<tr id="imListTr_<s:property value="immId"/>">
				<td align="center"><a id="immName_<s:property value="immId"/>" class="listViewTdLinkS1" href="#none" onClick="return initInmatchList('<s:property value="iodef.ioId" />','<s:property value="immId"/>')" ><s:property value="immName" /></a></td>
				<td align="center"><a id="immDesc_<s:property value="immId"/>" class="listViewTdLinkS1" href="#none" onClick="return initInmatchList('<s:property value="iodef.ioId" />','<s:property value="immId"/>')" ><s:property value="immDesc" /></a></td>
				<td align="center"><s:property value="immInputTypeMean" /></td>
				<td align="center" id="tdImmDefault_<s:property value="immId"/>"></td>
				<td align="center"><s:property value="immNoTitleMean" /></td>
				<td align="center"><s:property value="immImportModeMean" /></td>
				<td align="center"><s:property value="immCreateTime" /></td>
				<td align="center"><s:property value="immLastChangeTime" /></td>
				<ty:auth auths="702,2">
				<td id="tdImmOperate_<s:property value="immId"/>"></td>
				</ty:auth>
			</tr>
		</s:iterator></s:if>
		<s:else>
			<tr>
				<td align="center" colspan="11">无模板存在</td>
			</tr>
		</s:else>
	</table>
	<div>
	<ty:auth auths="702,2">
	<input type="button" value="增加模板" class="button" onClick="return initInmatchList('<s:property value="iodef.ioId" />',null)"/>
	</ty:auth>
	<input type="button" value="返回" class="button" onClick="window.location.href='../configuration/iodefList.action'"/>
	</div>
</form>

<script type="text/javascript">
<s:if test="!immList.isEmpty()"><s:iterator value="immList" status="index">
setTdImmDefault('<s:property value="immId"/>',<s:property value="immDefault"/>);
</s:iterator></s:if>
function setDefaultImm(immId){
	var ioId=$("#iodef-ioId").val();
	InmatchModelEdit.setDefaultInmatchModel(ioId,immId,callBack);
	function callBack(str){
		if(str=="success"){
			return;
		}
		var ids=str.split(":");
		setTdImmDefault(ids[0],1);
		setTdImmDefault(ids[1],0);
	};
}
function deleteImm(immId){
	if(!confirm("您确定要删除『"+$("#immName_"+immId).html()+"-"+$("#immDesc_"+immId).html()+"』吗？")){
		return;
	}
	var ioId=$("#iodef-ioId").val();
	InmatchModelEdit.deleteInmatchModel(ioId,immId,callBack);
	function callBack(str){
		if(str=="success"){
			$("#imListTr_"+immId).remove();
			alert("删除成功");
			return;
		}
	};
}
function setTdImmDefault(immId,immDefault){
	if(immDefault==1){
		$("#tdImmDefault_"+immId).html('<img src="../resource/images/default_yes.png" />');
		$("#tdImmOperate_"+immId).html('&nbsp;');
	}else{
		$("#tdImmDefault_"+immId).html('<a href="#" onclick="setDefaultImm(\''+immId+'\')"><img src="../resource/images/default_no.png" alt="设为默认模板" /></a>');
		$("#tdImmOperate_"+immId).html('<a href="#" onclick="deleteImm(\''+immId+'\')"><img src="../resource/images/delete.gif" alt="删除" /></a>');
	}
}
</script>
<jsp:include flush="true" page="../io/div_imm_edit.jsp"></jsp:include>
</body>