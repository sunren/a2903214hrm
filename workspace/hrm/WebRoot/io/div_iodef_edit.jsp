<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@ taglib prefix="ty" uri="/tengsource"%>
<!-- dwr -->
<script type="text/javascript" src="../dwr/interface/IodefEdit.js"></script>



<div id="divIodefShow" style="display: none;" class="prompt_div_inline">
<form id="formIodefShow" name="formIodefShow" action="#" method="POST" enctype="multipart/form-data">
<input type="hidden" id="iodef-ioId" name="ioId" />
<input type="hidden" id="iodef-ioType" name="ioType" />
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="divIodefShowHead">
	<tr>
		<td align="center" valign="middle">
		<h3><label id="formIodefShowTitle">�鿴�ӿ����ã��ӿ�����:<label id="iodef-ioName"></label>��</label></h3>
		</td>
		<td align="right" width="40" valign="middle"><img id="afafafafasfdadsfa"
			src="../resource/images/close_div.gif"
			onclick="$('#divIodefShow').hide();" style="CURSOR: pointer" /> &nbsp;</td>
	</tr>
</table>
<table width="100%" id="tableOmmShow1" cellSpacing="0" cellPadding="0" width="100%" class="gridview">
	<TR>
		<td width="60" align="right">�ӿ�����:</td><td align="left"><label id="iodef-ioDesc"></label></td>
		<td align="right">�ӿ�����:</td><td align="left"><label id="iodef-ioTypeMean"></label></td>
		<td align="right">����ģ��:</td><td align="left"><label id="iodef-ioModuleMean"></label></td>
		<td align="right">�ӿڱ�׼:</td><td align="left"><label id="iodef-ioIsExtendMean"></label></td>
		<td align="right"></td>
	</TR>
</table>
<table width="100%" id="tableIodefShow" height="0" cellSpacing="1" cellPadding="0" width="100%" class="gridview" >

</table>
<div id="divIodefButton" width="100%" align="center">
<font color="red">ע����������Ϊdate,decimal,string����Ӧ�������ʽ�ֱ�Ϊ�����ڸ�ʽ��С����λ�����ַ�����󳤶�</font>
<br />
	<input type="button" id="iodefRefreshOrder" value="ˢ��˳��" class="button" onClick="model.simple.sortTableByNum('tableIodefShow',0,':text');"/>
	<ty:auth auths="702,2">
	<input type="button" value="���沢�ر�" class="button" onclick="updateIodef();"/>
	</ty:auth>
</div>

</form>
</div>
<script type="text/javascript">
function getImbTitleTrHtml(){
	return '<TR>'
	+'	<TH noWrap>˳��</TH>'
	+'	<TH noWrap>�ֶ�����</TH>'
	+'	<TH noWrap>����</TH>'
	+'	<TH noWrap>��������</TH>'
	+'	<TH noWrap>���ݸ�ʽ</TH>'
	+'	<TH noWrap>����</TH>'
	+'	<TH noWrap>���</TH>'
	+'	<TH noWrap>����</TH>'
	+'	<TH noWrap>����</TH>'
	+'</TR>';
}
function getImbShowTrHtml(){
	return '<TR id="tr<n>">'
	+'	<TD align="center"><label id="imbList_<n>-imbSortId"></label></TD>'
	+'	<TD><label id="imbList_<n>-imbFieldName"></label></TD>'
	+'	<TD><label id="imbList_<n>-imbFieldDesc"></label></TD>'
	+'	<TD><label id="imbList_<n>-imbDataType"></label></TD>'
	+'	<TD><label id="imbList_<n>-formatDesc"></label> <label id="imbList_<n>-imbFormat"></label></TD>'
	+'	<TD align="center"><img id="imbList_<n>-imbRequiredMean" /></TD>'
	+'	<TD align="center"><img id="imbList_<n>-imbDetectErrorMean" /></TD>'
	+'	<TD align="center"><label id="imbList_<n>-imbSample"></label></TD>'
	+'	<TD align="center">&nbsp;</TD>'
	+'</TR>';
}
function getImbEditTrHtml(){
	return '<TR id="tr<n>"><input type="hidden" id="imbList_<n>-imbId" name="imbId" />'
	+'	<TD align="center"><input type="text" valueType="number" id="imbList_<n>-imbSortId" name="imbSortId" size="4" maxLength="4" /></TD>'
	+'	<TD><label id="imbList_<n>-imbFieldName"></label></TD>'
	+'	<TD><input type="text" id="imbList_<n>-imbFieldDesc" name="imbFieldDesc" size="20" maxLength="64" notNull="true" /></TD>'
	+'	<TD><label id="imbList_<n>-imbDataType"></label></TD>'
	+'	<TD><input type="text" id="imbList_<n>-imbFormat" name="imbFormat" size="10" maxLength="32" onChange="checkImbDataFormat(<n>)"/></TD>'
	+'	<TD><select id="imbList_<n>-imbRequired" name="imbRequired"></select></TD>'
	+'	<TD><select id="imbList_<n>-imbDetectError" name="imbDetectError"></select></TD>'
	+'	<TD><input type="text" id="imbList_<n>-imbSample" name="imbSample" size="20" maxLength="255" onChange="checkImbSample(<n>)" /></TD>'
	+'	<TD><a href="#" onClick="deleteInmatchBasic(<n>);return false;"><img src="../resource/images/delete.gif" alt="ɾ���ֶ�"></img></a> <a href="" onClick="changeIodefTrStatus(<n>,\'EditSingle\');return false;"><img src="../resource/images/edit_inline.gif" alt="�޸��ֶ�"></img></a></TD>'
	+'</TR>';
}
function getImbEditSingleTrHtml(){
	return '<TR id="tr<n>"><input type="hidden" id="imbList_<n>-imbId" name="imbId" />'
	+'	<TD align="center"><input type="text" valueType="number" id="imbList_<n>-imbSortId" name="imbSortId" size="4" maxLength="4" /></TD>'
	+'	<TD><input type="text" id="imbList_<n>-imbFieldName" name="imbFieldName" size="32" maxLength="64" notNull="true" /></TD>'
	+'	<TD><input type="text" id="imbList_<n>-imbFieldDesc" name="imbFieldDesc" size="20" maxLength="64" notNull="true" /></TD>'
	+'	<TD><select id="imbList_<n>-imbDataType" name="imbDataType" onChange="changeImbFormat(<n>)"></select></TD>'
	+'	<TD><input type="text" id="imbList_<n>-imbFormat" name="imbFormat" size="10" maxLength="32" onChange="checkImbDataFormat(<n>)"/></TD>'
	+'	<TD><select id="imbList_<n>-imbRequired" name="imbRequired"></select></TD>'
	+'	<TD><select id="imbList_<n>-imbDetectError" name="imbDetectError"></select></TD>'
	+'	<TD><input type="text" id="imbList_<n>-imbSample" name="imbSample" size="20" maxLength="255" onChange="checkImbSample(<n>)" /></TD>'
	+'	<TD><a href="#" onClick="changeIodefTrStatus(<n>,\'Edit\');return false;"><img src="../resource/images/leftarrow.gif" alt="����"></img></a> <a href="#" onClick="updateInmatchBasic(<n>);return false;"><img src="../resource/images/Submit.gif" alt="�ύ"></img></a></TD>'
	+'</TR>';
}
function getImbAddTrHtml(){
	return '<TR id="tr<n>"><input type="hidden" id="imbList_<n>-imbId" name="imbId" />'
	+'	<TD align="center"><input type="text" valueType="number" id="imbList_<n>-imbSortId" name="imbSortId" size="4" maxLength="4" /></TD>'
	+'	<TD><input type="text" id="imbList_<n>-imbFieldName" name="imbFieldName" size="32" maxLength="64" /></TD>'
	+'	<TD><input type="text" id="imbList_<n>-imbFieldDesc" name="imbFieldDesc" size="20" maxLength="64" /></TD>'
	+'	<TD><select id="imbList_<n>-imbDataType" name="imbDataType" onChange="changeImbFormat(<n>)"></select></TD>'
	+'	<TD><input type="text" id="imbList_<n>-imbFormat" name="imbFormat" size="10" maxLength="32" onChange="checkImbDataFormat(<n>)"/></TD>'
	+'	<TD><select id="imbList_<n>-imbRequired" name="imbRequired"></select></TD>'
	+'	<TD><select id="imbList_<n>-imbDetectError" name="imbDetectError"></select></TD>'
	+'	<TD><input type="text" id="imbList_<n>-imbSample" name="imbSample" size="20" maxLength="255" /></TD>'
	+'	<TD></TD>'
	+'</TR>';
}
function getOmbTitleTrHtml(){
	return '<TR>'
	+'	<TH noWrap>˳��</TH>'
	+'	<TH noWrap>�ֶ�����</TH>'
	+'	<TH noWrap>����</TH>'
	+'	<TH noWrap>��������</TH>'
	+'	<TH noWrap>�����ʽ</TH>'
	+'	<TH noWrap>�п�</TH>'
	+'	<TH noWrap>����</TH>'
	+'	<TH noWrap>����</TH>'
	+'</TR>';
}
function getOmbShowTrHtml(){
	return '<TR id="tr<n>">'
	+'	<TD align="center"><label id="ombList_<n>-ombSortId"></label></TD>'
	+'	<TD><label id="ombList_<n>-ombFieldName"></label></TD>'
	+'	<TD><label id="ombList_<n>-ombFieldDesc"></label></TD>'
	+'	<TD><label id="ombList_<n>-ombDataType"></label></TD>'
	+'	<TD><label id="ombList_<n>-formatDesc"></label> <label id="ombList_<n>-ombFormat"></label></TD>'
	+'	<TD><label id="ombList_<n>-ombColumnWidth"></label></TD>'
	+'	<TD align="center"><img id="ombList_<n>-ombCanGroupMean" /></TD>'
	+'	<TD align="center">&nbsp;</TD>'
	+'</TR>';
}
function getOmbEditTrHtml(){
	return '<TR id="tr<n>"><input type="hidden" id="ombList_<n>-ombId" name="ombId" />'
	+'	<TD align="center"><input type="text" valueType="number" id="ombList_<n>-ombSortId" name="ombSortId" size="4" maxLength="4" /></TD>'
	+'	<TD><label id="ombList_<n>-ombFieldName"></label></TD>'
	+'	<TD><input type="text" id="ombList_<n>-ombFieldDesc" name="ombFieldDesc" size="20" maxLength="64" notNull="true" /></TD>'
	+'	<TD><label id="ombList_<n>-ombDataType"></label></TD>'
	+'	<TD><input type="text" id="ombList_<n>-ombFormat" name="ombFormat" size="10" maxLength="32" onChange="checkOmbDataFormat(<n>)"/></TD>'
	+'	<TD><input type="text" valueType="number" id="ombList_<n>-ombColumnWidth" name="ombColumnWidth" size="3" maxLength="3" /></TD>'
	+'	<TD align="center"><img id="ombList_<n>-ombCanGroupMean" /></TD>'
	+'	<TD align="center"><a href="#" onClick="deleteOutmatchBasic(<n>);return false;"><img src="../resource/images/delete.gif" alt="ɾ���ֶ�"></img></a> <a href="" onClick="changeIodefTrStatus(<n>,\'EditSingle\');return false;"><img src="../resource/images/edit_inline.gif" alt="�޸��ֶ�"></img></a></TD>'
	+'</TR>';
}
function getOmbEditSingleTrHtml(){
	return '<TR id="tr<n>"><input type="hidden" id="ombList_<n>-ombId" name="ombId" />'
	+'	<input type="hidden" id="ombList_<n>-ombSortId" name="ombSortId" /><TD align="center"><label id="ombList_<n>-ombSortId"></label></TD>'
	+'	<TD><input type="text" id="ombList_<n>-ombFieldName" name="ombFieldName" size="32" maxLength="64" /></TD>'
	+'	<TD><input type="text" id="ombList_<n>-ombFieldDesc" name="ombFieldDesc" size="20" maxLength="64" /></TD>'
	+'	<TD><select id="ombList_<n>-ombDataType" name="ombDataType" onChange="changeOmbFormat(<n>)"></select></TD>'
	+'	<TD><input type="text" id="ombList_<n>-ombFormat" name="ombFormat" size="10" maxLength="32" onChange="checkOmbDataFormat(<n>)"/></TD>'
	+'	<TD><input type="text" valueType="number" id="ombList_<n>-ombColumnWidth" name="ombColumnWidth" size="3" maxLength="3" /></TD>'
	+'	<TD align="center"><select id="ombList_<n>-ombCanGroup" name="ombCanGroup"></select></TD>'
	+'	<TD align="center"><a href="#" onClick="changeIodefTrStatus(<n>,\'Edit\');return false;"><img src="../resource/images/leftarrow.gif" alt="����"></img></a> <a href="#" onClick="updateOutmatchBasic(<n>);return false;"><img src="../resource/images/Submit.gif" alt="�ύ"></img></a></TD>'
	+'</TR>';
}
function getOmbAddTrHtml(){
	return '<TR id="tr<n>"><input type="hidden" id="ombList_<n>-ombId" name="ombId" />'
	+'	<TD align="center"><input type="text" valueType="number" id="ombList_<n>-ombSortId" name="ombSortId" size="4" maxLength="4" /></TD>'
	+'	<TD><input type="text" id="ombList_<n>-ombFieldName" name="ombFieldName" size="32" maxLength="64" /></TD>'
	+'	<TD><input type="text" id="ombList_<n>-ombFieldDesc" name="ombFieldDesc" size="20" maxLength="64" /></TD>'
	+'	<TD><select id="ombList_<n>-ombDataType" name="ombDataType" onChange="changeOmbFormat(<n>)"></select></TD>'
	+'	<TD><input type="text" id="ombList_<n>-ombFormat" name="ombFormat" size="10" maxLength="32" onChange="checkOmbDataFormat(<n>)"/></TD>'
	+'	<TD><input type="text" valueType="number" id="ombList_<n>-ombColumnWidth" name="ombColumnWidth" size="3" maxLength="3" /></TD>'
	+'	<TD align="center"><select id="ombList_<n>-ombCanGroup" name="ombCanGroup"></select></TD>'
	+'	<TD></TD>'
	+'</TR>';
}

//��ʼ��outmatchBasicList��
function initOutmatchBasicList(ioId) {
	$('#divIodefShow').hide();
	$('#divWait').show();
	IodefEdit.loadIodef(ioId,$("#changeIodef").val(),showIodef);
}
var outmatchBasicList;
var inmatchBasicList;
function showIodef(iodef){
	$('#divWait').hide();
	if(!iodef){
		alert("û������");
		return;
	}
	var tableId="tableIodefShow";
	var formId="formIodefShow";
	//�趨form��iodef���ֵ
	model.simple.initByObject(formId,"iodef",iodef);
	//�趨basic������
	var matchList;
	var matchType;
	var displayType;
	var listName;
	if(iodef.ioType==0){
		matchList=iodef.imbList;
		inmatchBasicList=iodef.imbList;
		matchType="Imb";
		listName="imb";
	}else if(iodef.ioType==1){
		matchList=iodef.ombList;
		outmatchBasicList=iodef.ombList;
		matchType="Omb";
		listName="omb";
	}else{
		alert("�ӿ��������");
		return;
	}
	if(matchList==null){
		alert("�ӿ����ô���");
		return;
	}
	
	var matchNum=matchList.length-1;
	if(iodef.ioId==null){
		displayType="Show";
		$("#divIodefButton").hide();
	}else{
		displayType="Edit";
	}
	var trTitleHtmlFunction=eval("get"+matchType+"TitleTrHtml");
	var tableHtml=trTitleHtmlFunction();

	var trHtmlFunction=eval("get"+matchType+displayType+"TrHtml");
	var trHtml=trHtmlFunction();
	var regS = new RegExp("<n>","gi");
	for(var i=0;i<matchNum;i++){
		tableHtml+=trHtml.replace(regS,i);
	}
	//�趨trAdd������
	if(iodef.ioId!=null){
		var trAddHtmlFunction=eval("get"+matchType+"AddTrHtml");
		var trAddHtml=trAddHtmlFunction();
		//trAdd������
		var newTRNum=10;
		//�����µı������
		var matchNew=matchList[matchNum];
		ombNewTmp=matchNew;
		for(var i=matchNum;i<matchNum+newTRNum;i++){
			matchList[i]=matchNew;
		}
		for(var i=matchNum;i<matchNum+newTRNum;i++){
			tableHtml+=trAddHtml.replace(regS,i);
		}
		matchNum+=newTRNum;
	}
	$("#"+tableId).html(tableHtml);
	for(var i=0;i<matchNum;i++){
		if(matchList[i].formatDesc==null){
			$("#ombList_"+i+"-ombFormat").hide();
		}
	}
	//�趨table�����Ԫ�صĳ�ʼֵ
	model.simple.initByList(formId,listName+"List",matchList);
	model.simple.amplifyNum(tableId,0,10,":text");
	//��ʾdiv
	$('#divIodefShow').show();
	//��text,textarea���ݽ��м��
	$(":text, textarea").blur( function(){ 
		var str=$(this).val().trim();
		var id=$(this).attr("id");
		$(this).val(str);
		if($(this).attr("valueType")=="number"){
			if(!validator.str.isInt(str)){
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
}


//���ombFormat��ʽ�Ƿ���ȷ
function checkOmbDataFormat(index){
	var format=model.simple.getElementValue("ombList_"+index+"-ombFormat");
	var dataType=model.simple.getElementValue("ombList_"+index+"-ombDataType");
	IodefEdit.checkOmDataTypeFormat(dataType,format,callBack);
	function callBack(str){
		if(str=="success"){
			return;
		}
		alert("��"+model.simple.getElementValue("ombList_"+index+"-ombFieldDesc")+"����Ӧ�ĵ������ʽ����ȷ");
		window.setTimeout("$('#ombList_"+index+"-ombFormat').focus()",50);
		model.simple.setElementValue("ombList_"+index+"-ombFormat",str);
	}
}

//���imbFormat��ʽ�Ƿ���ȷ
function checkImbDataFormat(index){
	var format=model.simple.getElementValue("imbList_"+index+"-imbFormat");
	var dataType=model.simple.getElementValue("imbList_"+index+"-imbDataType");
	IodefEdit.checkImDataTypeFormat(dataType,format,callBack);
	function callBack(str){
		if(str=="success"){
			return;
		}
		alert("��"+$("#imbList_"+index+"-imbFieldDesc").val()+"����Ӧ�ĵ������ʽ����ȷ");
		window.setTimeout("$('#imbList_"+index+"-imbFormat').focus()",50);
		$("#imbList_"+index+"-imbFormat").val(str);
	}
}
//���imbSample��ʽ�Ƿ���ȷ
function checkImbSample(index){
	var format=model.simple.getElementValue("imbList_"+index+"-imbFormat");
	var dataType=model.simple.getElementValue("imbList_"+index+"-imbDataType");
	var sample=model.simple.getElementValue("imbList_"+index+"-imbSample");
	IodefEdit.checkImSample(dataType,format,sample,callBack);
	function callBack(str){
		if(str=="success"){
			return;
		}
		if(str=="error"){
			alert("��"+$("#imbList_"+index+"-imbFieldDesc").val()+"����Ӧ�ĵ����Ӳ���ȷ");
			window.setTimeout("$('#imbList_"+index+"-imbSample').focus()",50);
			$("#imbList_"+index+"-imbSample").val("");
			return;
		}
		alert("��"+$("#imbList_"+index+"-imbFieldDesc").val()+"����Ӧ�ĵ������ʽ����ȷ");
		$("#imbList_"+index+"-imbFormat").val(str);
	}
}
//change ombFormat
function changeOmbFormat(index){
	if($("#ombList_"+index+"-ombDataType").val()=="date"){
		$("#ombList_"+index+"-ombFormat").val("yyyy-MM-dd");
	}else{
		$("#ombList_"+index+"-ombFormat").val("");
	}
}
function changeImbFormat(index){
	if($("#imbList_"+index+"-imbDataType").val()=="date"){
		$("#imbList_"+index+"-imbFormat").val("yyyy-MM-dd");
	}else{
		$("#imbList_"+index+"-imbFormat").val("");
	}
}

/**
 * @author zchen, sjinxin
 * @see �����޸�imb����omb��ر�������������¼��ͬʱ���im����om��¼
 * @see ����д�����alert��ʽ���ش�����Ϣ���û����޸����ݺ��������
 */
function updateIodef(){
	var ioId=$("#iodef-ioId").val();
	var ioType=$("#iodef-ioType").val();
	var ombList=null;
	var imbList=null;

	if(ioType=="1") { // ����
		ombList=model.simple.formIdValueToObject("formIodefShow","ombList");
		for(var i=0;i<ombList.length;i++){
			if(!ombList[i]){
				continue;
			}
			if(ombList[i]["ombFieldName"]){ 
				// �����ⲻ��Ϊ��
				if(ombList[i]["ombFieldDesc"]==null || ombList[i]["ombFieldDesc"].length==0) {
					alert(ombList[i]["ombFieldName"]+"��Ӧ�ı��ⲻ��Ϊ��");
					return false;
				}
			}
		}
	} else if(ioType=="0") { // ����
		imbList=model.simple.formIdValueToObject("formIodefShow","imbList");
		for(var i=0;i<imbList.length;i++){
			if(!imbList[i]){
				continue;
			}

			if(imbList[i]["imbFieldName"]){ 
				// �����ⲻ��Ϊ��
				if(imbList[i]["imbFieldDesc"]==null || imbList[i]["imbFieldDesc"].length==0) {
					alert(imbList[i]["imbFieldName"]+"��Ӧ�ı��ⲻ��Ϊ��");
					return false;
				}
			}
			if(imbList[i]["imbRequired"]==1){ 
				// ������Ӳ���Ϊ��
				if(imbList[i]["imbSample"]==null || imbList[i]["imbSample"].length==0) {
					alert(imbList[i]["imbFieldName"]+"��Ӧ�����Ӳ���Ϊ��");
					return false;
				}
			}
		}
	}
	
	$('#divWait').show();
	IodefEdit.updateIodef(ioId,ombList,imbList,callBack);
	function callBack(str){
		$('#divWait').hide();
		if(str=="success"){
			$('#divIodefShow').hide();
		}else{
			alert(str);
		}
	}
}

//ɾ��omb
function deleteOutmatchBasic(index){
	if(!confirm("��ȷ��Ҫɾ����"+$("#ombList_"+index+"-ombFieldName").html()+"����")){
		return false;
	}
	var ioId=$("#iodef-ioId").val();
	var ommId=$("#ombList_"+index+"-ombId").val();
	IodefEdit.deleteOutmatchBasic(ioId,ommId,callBack);
	function callBack(str){
		if(str=="success"){
			alert("ɾ���ɹ�");
			$("#tr"+index).remove();
		}else{
			alert("error");
		}
	}
}
//ɾ��imb
function deleteInmatchBasic(index){
	if(!confirm("��ȷ��Ҫɾ����"+$("#imbList_"+index+"-imbFieldName").html()+"����")){
		return false;
	}
	var ioId=$("#iodef-ioId").val();
	var ommId=$("#imbList_"+index+"-imbId").val();
	IodefEdit.deleteInmatchBasic(ioId,ommId,callBack);
	function callBack(str){
		if(str=="success"){
			alert("ɾ���ɹ�");
			$("#tr"+index).remove();
		}else{
			alert("error");
		}
	}
}
function changeIodefTrStatus(index,displayType){
	var ioType=$("#iodef-ioType").val();
	var itemType;
	var itemName;
	var object;
	if(ioType==1){
		itemType="Omb";
		itemName="omb";
		object=outmatchBasicList[index];
	}else if(ioType==0){
		itemType="Imb";
		itemName="imb";
		object=inmatchBasicList[index];
	}else{
		return false;
	}
	var trHtmlFucntion=eval("get"+itemType+displayType+"TrHtml");
	var trHtml=trHtmlFucntion();
	var regS = new RegExp("<n>","gi");
	trHtml=trHtml.replace(regS,index);
	$("#tr"+index).replaceWith(trHtml);
	model.simple.initByObject("tr"+index, itemName+"List_"+index, object);
	$("#"+itemName+"List_"+index+"-"+itemName+"SortId").val($("#"+itemName+"List_"+index+"-"+itemName+"SortId").val()*10);
}

function updateOutmatchBasic(index){
	var ioId=$("#iodef-ioId").val();
	var omb=model.simple.formIdValueToObject("formIodefShow","ombList_"+index);
	IodefEdit.updateOutmatchBasic(ioId,omb,callBack);
	function callBack(omb){
		if(omb==null){
			alert("error");
		}else{
			outmatchBasicList[index]=omb;
			changeIodefTrStatus(index,"Edit");
		}
	}	
}

function updateInmatchBasic(index){
	var ioId=$("#iodef-ioId").val();
	var imb=model.simple.formIdValueToObject("formIodefShow","imbList_"+index);
	IodefEdit.updateInmatchBasic(ioId,imb,callBack);
	function callBack(imb){
		if(imb==null){
			alert("error");
		}else{
			inmatchBasicList[index]=imb;
			changeIodefTrStatus(index,"Edit");
		}
	}	
}
</script>
<jsp:include page="../sitemesh/div_wait.jsp" flush="true"></jsp:include>