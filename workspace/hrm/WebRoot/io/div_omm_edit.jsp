<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<%@ taglib prefix="ty" uri="/tengsource"%>
<!-- dwr -->

<div id="divOmListShow" style="display: none;" class="prompt_div_inline">
<form id="formOmListShow" name="formOmListShow" action="#" method="POST" enctype="multipart/form-data">
<input type="hidden" id="outmatchModel-ommIo-ioId" name="ioId" />
<input type="hidden" id="outmatchModel-ommId" name="ommId" />
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="divOmListShowHead">
	<tr>
		<td align="center" valign="middle">
		<h3><label id="formOmListShowTitle"></label></h3>
		</td>
		<td align="right" width="40" valign="middle"><img
			src="../resource/images/close_div.gif"
			onclick="$('#divOmListShow').hide();" style="CURSOR: pointer" /> &nbsp;</td>
	</tr>
</table>
<table id="tableOmmShow1" cellSpacing="0" cellPadding="0" width="100%" class="gridview">
	<TR>
		<td align="right">ģ������:</td><td align="left"><input type="text" id="outmatchModel-ommName" name="ommName" size="20" maxLength="32" value="" notNull="true"/></td>
		<td align="right">ģ������:</td><td align="left"><input type="text" id="outmatchModel-ommDesc" name="ommDesc" size="20" maxLength="64" value="" notNull="true"/></td>
		<td align="right">Ĭ���и�:</td><td align="left"><input type="text" id="outmatchModel-ommRowHeight" name="ommRowHeight" valueType="number" size="4" maxLength="4" value="" /></td>
	</TR>
</table>
<table id="tableOmmShow2" cellSpacing="0" cellPadding="0" width="100%" class="gridview">
	<TR>
		<td align="right">ͳ����ʾλ��:</td><td align="left"><select id="outmatchModel-ommStatisticPlace" name="ommStatisticPlace"></select></td>
		<td align="right">ͳ����ʾ��ʽ:</td><td align="left"><select id="outmatchModel-ommStatisticDisplayType" name="ommStatisticDisplayType"></select></td>
		<td align="right">����ļ�����:</td><td align="left"><select id="outmatchModel-ommOutputType" name="ommOutputType"></select></td>
		<td align="right">��ʾ����:</td><td align="left"><select id="outmatchModel-ommNoTitle" name="ommNoTitle"></select></td>
	</TR>
</table>
<table id="tableOmListShow" cellSpacing="1" cellPadding="0" width="100%" class="gridview" >
	<TR>
		<TH><input type="checkbox" id="checkboxOmListAll" onClick="model.simple.checkAll('tableOmListShow','checkboxOmListAll')" />˳��</TH>
		<TH>Ĭ�ϱ���</TH>
		<TH>����</TH>
		<TH>�����ʽ</TH>
		<TH>�п�</TH>
		<TH>ͳ�Ʒ���</TH>
		<TH>��������</TH>
	</TR>
</table>
<div width="100%" align="center">
	<input type="button" value="ˢ��˳��" class="button" onclick="model.simple.sortTableByNum('tableOmListShow',0,':text');"/>
	<ty:auth auths="702,2">
	<input type="button" value="���沢�ر�" class="button" onclick="omListUpdate();"/>
	</ty:auth>
</div>

</form>
</div>

<script type="text/javascript">
//outmatchÿ�����ݵ�ģ��
function getOmEditTrHtml(){
	return '<TR id="tr<n>">'
	+'	<input type="hidden" id="omList_<n>-omId" name="omId" />'
	+'	<input type="hidden" id="omList_<n>-omOmb-ombId" name="ombId" />'
	+'	<input type="hidden" id="omList_<n>-omOmb-ombDataType" name="ombDataType" />'
	+'	<TD align="center"><input type="checkbox" id="omList_<n>-omIsOutput" name="omIsOutput" class="checkbox" value="1" checked />'
	+'	<input type="text" valueType="number" id="omList_<n>-omSortId" name="omSortId" size="4" maxLength="4" /></TD>'
	+'	<TD><label id="omList_<n>-omOmb-ombFieldDesc"></label></TD>'
	+'	<TD><input type="text" id="omList_<n>-omFieldDesc" name="omFieldDesc" size="20" maxLength="64" /></TD>'
	+'	<TD><label id="omList_<n>-formatDesc"></label> <input type="text" id="omList_<n>-omFormat" name="omFormat" size="10" maxLength="32" onChange="checkDataFormat(<n>)"/></TD>'
	+'	<TD><input type="text" valueType="number" id="omList_<n>-omColumnWidth" name="omColumnWidth" size="3" maxLength="3" /></TD>'
	+'	<TD><select id="omList_<n>-omStatistic" name="omStatistic"></select></TD>'
	+'	<TD><select id="omList_<n>-omIsGroup" name="omIsGroup"></select></TD>'
	+'</TR>';
}
//��ʼ��outmatchlist��
function initOutmatchList(ioId,ommId) {
	$('#divOmListShow').hide();
	$('#divWait').show();
	OutmatchModelEdit.loadOutmatchModel(ioId,ommId,showOutmatchModel);
	function showOutmatchModel(outmatchModel){
		$('#divWait').hide();
		var tableId="tableOmListShow";
		var formId="formOmListShow";
		var trHtml=getOmEditTrHtml();
		var outmatchList=outmatchModel.omList;
		//����
		$("#formOmListShowTitle").html(outmatchModel.ommId==null?"����ģ��":"�޸�ģ��");
		//outmatchModel
		model.simple.initByObject(formId,"outmatchModel",outmatchModel);
		//omList
		model.simple.initTableByList(tableId,"omList",outmatchList,trHtml);
		//�����趨
		for(var i=0;i<outmatchList.length;i++){
			if(outmatchList[i].formatDesc==null){
				$("#omList_"+i+"-omFormat").hide();
			}
		}
		//sortId�Ŵ�
		model.simple.amplifyNum(tableId,0,10,":text");
		$('#divOmListShow').show();
		$(":text, textarea").blur( function(){ 
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
		} );
		$("#tableOmListShow :checkbox").click( function(){ 
			var id=$(this).attr("id");
			if(id=="checkboxOmListAll"){
				return;
			}
			if($(this).attr("checked")){
				var checkAll=true;
				var checkList=$("#tableOmListShow  :checkbox");
				for(var i=0;i<checkList.size();i++){
					if(checkList.get(i).id=="checkboxOmListAll"){
						continue;
					}
					if(!checkList.get(i).checked){
						checkAll=false;
						break;
					}
				}
				if(checkAll){
					$("#checkboxOmListAll").attr("checked",true);
				}
			}else{
				$("#checkboxOmListAll").attr("checked",false);
			}
		} );
	}
}
//����outmatchlist
function omListUpdate(){
	if($("#outmatchModel-ommName").val().length<1){
		alert("ģ�����Ʋ���Ϊ��");
		window.setTimeout('$("#outmatchModel-ommName").focus();',10);
		return;
	}
	if($("#outmatchModel-ommDesc").val().length<1){
		alert("ģ����������Ϊ��");
		window.setTimeout('$("#outmatchModel-ommDesc").focus()',10);
		return;
	}
	var fieldNum=$("#tableOmListShow tr:gt(0)").size();
	var checked=false;
	for(var i=0;i<fieldNum;i++){
		if($("#omList_"+i+"-omIsOutput").attr("checked")){
			checked=true;
			break;
		}
	}
	if(!checked && fieldNum>0){
		alert("������ѡ��һ������");
		window.setTimeout('$("#outmatchModel-ommDesc").focus()',10);
		return;
	}
	var outmatchModel=model.simple.formIdValueToObject("formOmListShow","outmatchModel");
	var omList=model.simple.formIdValueToObject("formOmListShow","omList");
	$('#divWait').show();
	OutmatchModelEdit.saveOrUpdateOutmatchModel(outmatchModel,omList,callBack);
	function callBack(result){
		$('#divWait').hide();
		if(result=="success"){
			location.reload();
		}else{
			alert(result);
		}
	}
}

//���omFormat��ʽ�Ƿ���ȷ
function checkDataFormat(index){
	var df=$("#ombDataType").val();
	var format=$("#omList_"+index+"-omFormat").val().trim();
	OutmatchModelEdit.checkDataTypeFormat(df,format,index,callBack);
	function callBack(str){
		if(str=="error"){
			alert("index����Ϊ��");
		}
		var isError=false;
		if(str.charAt(0)=="-"){
			isError=true;
			str=str.substring(1);
		}
		var place=str.indexOf(":");
		var index=parseInt(str.substring(0,place));
		var format=str.substring(place+1);
		if(format=="null"){
			//format="";
		}
		if(isError){
			alert("\""+$("#omList_"+index+"-omFieldDesc").val()+"\"��Ӧ�ĵ������ʽ����ȷ");
			window.setTimeout("$('#omList_"+index+"-omFormat').focus()",50);
		}
		$("#omList_"+index+"-omFormat").val(format);
	}
}
</script>
<jsp:include page="../sitemesh/div_wait.jsp" flush="true"></jsp:include>