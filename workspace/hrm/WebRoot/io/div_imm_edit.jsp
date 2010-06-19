<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<%@ taglib prefix="ty" uri="/tengsource"%>

<div id="divImListShow" style="display: none;" class="prompt_div_inline">
	<form id="formImListShow" name="formImListShow" action="#" method="POST" enctype="multipart/form-data">
		<input type="hidden" id="inmatchModel-immIo-ioId" name="ioId" />
		<input type="hidden" id="inmatchModel-immId" name="immId" />
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="center" valign="middle">
				<h3><label id="formImListShowTitle">�޸ĵ���ģ��</label></h3>
				</td>
				<td align="right" width="40" valign="middle"><img
					src="../resource/images/close_div.gif"
					onclick="$('#divImListShow').hide();" style="CURSOR: pointer" /> &nbsp;</td>
			</tr>
		</table>
		<table id="tableImmShow1" cellSpacing="0" cellPadding="0" width="100%" class="gridview">
			<TR>
				<td align="right">ģ������:</td><td align="left"><input type="text" id="inmatchModel-immName" name="immName" size="20" maxLength="32" value="" notNull="true"/></td>
				<td align="right">ģ������:</td><td align="left"><input type="text" id="inmatchModel-immDesc" name="immDesc" size="20" maxLength="64" value="" notNull="true"/></td>
			</TR>
		</table>
		<table id="tableImmShow2" cellSpacing="0" cellPadding="0" width="100%" class="gridview">
			<TR>
				<td align="right">�����ļ�����:</td><td align="left"><select id="inmatchModel-immInputType" name="immInputType"></select></td>
				<td align="right">����ģʽ:</td><td align="left"><select id="inmatchModel-immImportMode" name="immImportMode"></select></td>
				<td align="right">��������:</td><td align="left"><select id="inmatchModel-immNoTitle" name="immNoTitle"></select></td>
			</TR>
		</table>
		<table id="tableImListShow" cellSpacing="1" cellPadding="0" width="100%" class="gridview" >
			<TR>
				<TH><input type="checkbox" id="checkboxImListAll" onClick="model.simple.checkAll('tableImListShow','checkboxImListAll')" />˳��</TH>
				<TH>Ĭ�ϱ���</TH>
				<TH>����</TH>
				<TH>���ݸ�ʽ</TH>
				<TH>����</TH>
				<TH>���</TH>
				<TH>����</TH>
				<TH>�п�</TH>
			</TR>
		</table>
		<ty:auth auths="702,2">
			<div width="100%" align="center">
				<input type="button" value="ˢ��˳��" class="button" onClick="model.simple.sortTableByNum('tableImListShow',0,':text');"/>
				<input type="button" value="���沢�ر�" class="button" onClick="imListUpdate();"/>
			</div>
		</ty:auth>
	</form>
</div>
<script type="text/javascript">
function getImEditTrHtml(){
	return '<TR id="tr<n>"><input type="hidden" id="imList_<n>-imId" name="imId" /><input type="hidden" id="imList_<n>-imImb-imbId" name="imbId" /><input type="hidden" id="imList_<n>-imImb-imbDataType" name="imbDataType" />'
	+'	<TD align="center"><input type="checkbox" id="imList_<n>-imIsInput" name="imIsInput" class="checkbox" value="1" /> <input type="text" valueType="number" id="imList_<n>-imSortId" name="imSortId" size="4" maxLength="4" /></TD>'
	+'	<TD><label id="imList_<n>-imImb-imbFieldDesc"></label></TD>'
	+'	<TD><input type="text" id="imList_<n>-imFieldDesc" name="imFieldDesc" size="20" maxLength="64" /></TD>'
	+'	<TD><label id="imList_<n>-formatDesc"></label> <input type="text" id="imList_<n>-imFormat" name="imFormat" size="10" maxLength="32" onChange="checkImDataFormat(<n>)"/></TD>'
	+'	<TD><select id="imList_<n>-imRequired" name="imRequired"></select></TD>'
	+'	<TD><select id="imList_<n>-imDetectError" name="imDetectError"></select></TD>'
	+'	<TD><input type="text" id="imList_<n>-imSample" name="imSample" size="20" maxLength="255" onChange="checkImSample(<n>)" /></TD>'
	+'	<TD><input type="text" id="imList_<n>-imSampleWidth" name="imSampleWidth" size="4" maxLength="4"  valueType="number" /></TD>'
	+'</TR>';
}
//��ʼ��inmatchlist��
function initInmatchList(ioId,immId) {
	$('#divImListShow').hide();
	$('#divWait').show();
	InmatchModelEdit.loadInmatchModel(ioId,immId,showInmatchModel);
	function showInmatchModel(inmatchModel){
		$('#divWait').hide();
		var formId="formImListShow";
		var tableId="tableImListShow";
		var trHtml=getImEditTrHtml();
		var inmatchList=inmatchModel.imList;
		//����
		$("#formImListShowTitle").html(inmatchModel.immId==null?"����ģ��":"�޸�ģ��");
		//inmatchModel
		model.simple.initByObject(formId,"inmatchModel",inmatchModel);
		//imList
		model.simple.initTableByList(tableId,"imList",inmatchList,trHtml);
		//�����趨
		for(var i=0;i<inmatchList.length;i++){
			if(inmatchList[i].formatDesc==null){
				$("#imList_"+i+"-imFormat").hide();
			}
		}
		//sortId�Ŵ�
		model.simple.amplifyNum(tableId,0,10,":text");
		$('#divImListShow').show();
		//��text,textarea���ݽ��м��
		$("#"+formId+" :text").blur( function(){ 
			var str=$(this).val().trim();
			var id=$(this).attr("id");
			$(this).val(str);
			if($(this).attr("valueType")=="number"){
				if(!validator.str.isIntOrEmpty(str)){
					alert("������д����");
					window.setTimeout("$('#"+id+"').focus()",0);
					return;
				}
			}
			if($(this).attr("notNull")=="true"){
				if(str.length<1){
					alert("����Ϊ��");
					window.setTimeout("$('#"+id+"').focus()",0);
					return;
				}
			}
		} );
		$("#"+tableId+" :checkbox").click( function(){ 
			var id=$(this).attr("id");
			if(id=="checkboxImListAll"){
				return;
			}
			if($(this).attr("checked")){
				var checkAll=true;
				var checkList=$("#tableImListShow  :checkbox");
				for(var i=0;i<checkList.size();i++){
					if(checkList.get(i).id=="checkboxImListAll"){
						continue;
					}
					if(!checkList.get(i).checked){
						checkAll=false;
						break;
					}
				}
				if(checkAll){
					$("#checkboxImListAll").attr("checked",true);
				}
			}else{
				$("#checkboxImListAll").attr("checked",false);
			}
		} );
	}
}

//����inmatchlist
function imListUpdate(){
	if($("#inmatchModel-immName").val().length<1){
		alert("ģ�����Ʋ���Ϊ��");
		window.setTimeout('$("#inmatchModel-immName").focus();',10);
		return;
	}
	if($("#inmatchModel-immDesc").val().length<1){
		alert("ģ����������Ϊ��");
		window.setTimeout('$("#inmatchModel-immDesc").focus()',10);
		return;
	}
	var fieldNum=$("#tableImListShow tr:gt(0)").size();
	var checked=false;
	for(var i=0;i<fieldNum;i++){
		if($("#imList_"+i+"-imIsInput").attr("checked")){
			checked=true;
			break;
		}
	}
	if(!checked && fieldNum>0){
		alert("������ѡ��һ������");
		window.setTimeout('$("#inmatchModel-immDesc").focus()',10);
		return;
	}
	var inmatchModel=model.simple.formIdValueToObject("formImListShow","inmatchModel");
	var imList=model.simple.formIdValueToObject("formImListShow","imList");
	var element=$("#imList_0-imIsInput").get(0);
	$('#divWait').show();
	InmatchModelEdit.saveOrUpdateInmatchModel(inmatchModel,imList,callBack);
	function callBack(result){
		$('#divWait').hide();
		if(result=="success"){
			location.reload();
		}else{
			alert(result);
		}
	}
}

//���imFormat��ʽ�Ƿ���ȷ
function checkImDataFormat(index){
	var format=model.simple.getElementValue("imList_"+index+"-imFormat");
	var dataType=model.simple.getElementValue("imList_"+index+"-imImb-imbDataType");
	IodefEdit.checkImDataTypeFormat(dataType,format,callBack);
	function callBack(str){
		if(str=="success"){
			return;
		}
		alert("��"+$("#imList_"+index+"-imFieldDesc").val()+"����Ӧ�ĵ������ʽ����ȷ");
		window.setTimeout("$('#imList_"+index+"-imFormat').focus()",50);
		$("#imList_"+index+"-imFormat").val(str);
	}
}
//���imSample��ʽ�Ƿ���ȷ
function checkImSample(index){
	var format=model.simple.getElementValue("imList_"+index+"-imFormat");
	var dataType=model.simple.getElementValue("imList_"+index+"-imImb-imbDataType");
	var sample=model.simple.getElementValue("imList_"+index+"-imSample");
	IodefEdit.checkImSample(dataType,format,sample,callBack);
	function callBack(str){
		if(str=="success"){
			return;
		}
		if(str=="error2"){
			alert("��"+$("#imList_"+index+"-imFieldDesc").val()+"����Ӧ�ĵ����Ӳ���ȷ");
			window.setTimeout("$('#imList_"+index+"-imSample').focus()",50);
			$("#imList_"+index+"-imSample").val("");
			return;
		}
		alert("��"+$("#imList_"+index+"-imFieldDesc").val()+"����Ӧ�ĵ������ʽ����ȷ");
		window.setTimeout("$('#imList_"+index+"-imFormat').focus()",50);
		$("#imList_"+index+"-imFormat").val(str);
	}
}
</script>
<jsp:include page="../sitemesh/div_wait.jsp" flush="true"></jsp:include>