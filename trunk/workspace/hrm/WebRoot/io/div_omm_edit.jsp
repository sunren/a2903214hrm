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
		<td align="right">模板名称:</td><td align="left"><input type="text" id="outmatchModel-ommName" name="ommName" size="20" maxLength="32" value="" notNull="true"/></td>
		<td align="right">模板描述:</td><td align="left"><input type="text" id="outmatchModel-ommDesc" name="ommDesc" size="20" maxLength="64" value="" notNull="true"/></td>
		<td align="right">默认行高:</td><td align="left"><input type="text" id="outmatchModel-ommRowHeight" name="ommRowHeight" valueType="number" size="4" maxLength="4" value="" /></td>
	</TR>
</table>
<table id="tableOmmShow2" cellSpacing="0" cellPadding="0" width="100%" class="gridview">
	<TR>
		<td align="right">统计显示位置:</td><td align="left"><select id="outmatchModel-ommStatisticPlace" name="ommStatisticPlace"></select></td>
		<td align="right">统计显示方式:</td><td align="left"><select id="outmatchModel-ommStatisticDisplayType" name="ommStatisticDisplayType"></select></td>
		<td align="right">输出文件类型:</td><td align="left"><select id="outmatchModel-ommOutputType" name="ommOutputType"></select></td>
		<td align="right">显示标题:</td><td align="left"><select id="outmatchModel-ommNoTitle" name="ommNoTitle"></select></td>
	</TR>
</table>
<table id="tableOmListShow" cellSpacing="1" cellPadding="0" width="100%" class="gridview" >
	<TR>
		<TH><input type="checkbox" id="checkboxOmListAll" onClick="model.simple.checkAll('tableOmListShow','checkboxOmListAll')" />顺序</TH>
		<TH>默认标题</TH>
		<TH>标题</TH>
		<TH>输出格式</TH>
		<TH>列宽</TH>
		<TH>统计方法</TH>
		<TH>分组排序</TH>
	</TR>
</table>
<div width="100%" align="center">
	<input type="button" value="刷新顺序" class="button" onclick="model.simple.sortTableByNum('tableOmListShow',0,':text');"/>
	<ty:auth auths="702,2">
	<input type="button" value="保存并关闭" class="button" onclick="omListUpdate();"/>
	</ty:auth>
</div>

</form>
</div>

<script type="text/javascript">
//outmatch每行内容的模板
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
//初始化outmatchlist层
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
		//标题
		$("#formOmListShowTitle").html(outmatchModel.ommId==null?"增加模板":"修改模板");
		//outmatchModel
		model.simple.initByObject(formId,"outmatchModel",outmatchModel);
		//omList
		model.simple.initTableByList(tableId,"omList",outmatchList,trHtml);
		//特殊设定
		for(var i=0;i<outmatchList.length;i++){
			if(outmatchList[i].formatDesc==null){
				$("#omList_"+i+"-omFormat").hide();
			}
		}
		//sortId放大
		model.simple.amplifyNum(tableId,0,10,":text");
		$('#divOmListShow').show();
		$(":text, textarea").blur( function(){ 
			var str=$(this).val().trim();
			var id=$(this).attr("id");
			$(this).val(str);
			if($(this).attr("valueType")=="number"){
				if(!validator.str.isIntOrEmpty(str)){
					alert("必须填写数字");
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
//更新outmatchlist
function omListUpdate(){
	if($("#outmatchModel-ommName").val().length<1){
		alert("模板名称不能为空");
		window.setTimeout('$("#outmatchModel-ommName").focus();',10);
		return;
	}
	if($("#outmatchModel-ommDesc").val().length<1){
		alert("模板描述不能为空");
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
		alert("请至少选中一个标题");
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

//检查omFormat格式是否正确
function checkDataFormat(index){
	var df=$("#ombDataType").val();
	var format=$("#omList_"+index+"-omFormat").val().trim();
	OutmatchModelEdit.checkDataTypeFormat(df,format,index,callBack);
	function callBack(str){
		if(str=="error"){
			alert("index不能为空");
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
			alert("\""+$("#omList_"+index+"-omFieldDesc").val()+"\"对应的的输出格式不正确");
			window.setTimeout("$('#omList_"+index+"-omFormat').focus()",50);
		}
		$("#omList_"+index+"-omFormat").val(format);
	}
}
</script>
<jsp:include page="../sitemesh/div_wait.jsp" flush="true"></jsp:include>