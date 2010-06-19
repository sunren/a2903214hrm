<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<script type="text/javascript" src="../dwr/interface/InmatchModelEdit.js"></script>
<!--begin: 上传文件div -->
<div id="divImmUpload"
	style="position: absolute; left: 350px; top: 120px; z-index: 5; solid; width: 500px; display: none;"
	class="prompt_div_inline">
	<form id="formUpload" name="formUpload" action="../io/import.action" method="POST" enctype="multipart/form-data" onSubmit="return uploadCheck();" target="uploadResult">
	<input type="hidden" id="importParameter" name="importParameter" value="0"/>
	<div id="divImmUploadHead" style="CURSOR: move; height: 25px;"
		class="prompt_div_head">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td><h3>导入</h3></td>
				<td align="right" valign="middle"><img
					src="../resource/images/close_div.gif"
					onclick="$('#divImmUpload').hide();" style="CURSOR: pointer" /></td>
			</tr>
		</table>
	</div>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr><td colspan="2" align="center">&nbsp;</td></tr>
		<tr>
			<td align="right">选择模板：</td>
			<td align="left">
				<select id="immId" name="immId"></select>
			</td>
		</tr>
		<tr>
			<td align="right">&nbsp;</td>
			<td align="left">
				<a id="downloadImmHref" href="#" onClick="return downloadModel();" class="listViewTdLinkS1">点击下载模板文件</a>（模板中黄色字体为必填选项）
			</td>
		</tr>
		<tr><td colspan="2" align="center">&nbsp;</td></tr>
		<tr>
			<td align="right">上传文件：</td>
			<td align="left"><input type="file" id="upload" name="upload"></td>
		</tr>
		<tr><td colspan="2" align="center">&nbsp;</td></tr>
		<tr>
			<td colspan="2" align="center">
				<input type="submit" value="导入" class="button" />
				<input type="button" value="关闭" class="button" onclick="$('#divImmUpload').hide();"/>
			</td>
		</tr>
		<tr><td colspan="2" align="center">&nbsp;</td></tr>
	</table>
	</form>
	<iframe scrolling="yes" resizable="no" id="uploadResult" name="uploadResult" style="z-index: -1; width: 0px; height: 0px;" frameborder="0"></iframe>
</div>
<!--end: 上传文件div -->
<!--begin: 导入结果div -->
<div id="divImmUploadResult"
	style="position: absolute; left: 350px; top: 120px; z-index: 5; solid; width: 500px; display: none;"
	class="prompt_div_inline" align="center">
	<div id="divImmUploadResultHead" style="CURSOR: move; height: 25px;" class="prompt_div_head">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td><h3>导入结果</h3></td>
				<td align="right" valign="middle"><img
					src="../resource/images/close_div.gif"
					onclick="closeResultDiv()" style="CURSOR: pointer" /></td>
			</tr>
		</table>
	</div>
	<table width="90%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="100%" align="left" id="immUploadTopMsgShow"></td>
		</tr>
		<tr id="trImmUploadDetailMsg">
			<td width="100%" align="left">
			<textarea id="immUploadDetailMsgShow" name="immUploadDetailMsgShow" cols ="50" rows = "10"></textarea>
			</td>
		</tr>
		<tr>
			<td width="100%" align="center">
				<input type="button" value="关闭" class="button" onClick="closeResultDiv()"/>
			</td>
		</tr>
	</table>
</div>
<!--end: 导入结果div -->
<script type="text/javascript" language="javascript">
	invoke_drag("divImmUploadHead", "divImmUpload");
	invoke_drag("divImmUploadResultHead", "divImmUploadResult");
	//关闭导入结果div
	function closeResultDiv(){
		if($('#immUploadDetailMsgShow').val().length==0){
			document.location.href=document.location.href;
		}else{
			$('#divImmUploadResult').hide();
		}
		
	}
	//初始化上传文件div，和里面的outmatchlist
	function initDivImmUpload(ioName,importParameter) {
		$('#divImmUpload').hide();
		$('#divWait').hide();
		$('#divImmUploadResult').hide();
		if(importParameter){
			$("#importParameter").val(importParameter);
		}
		if(!ioName){
			return;
		}
		InmatchModelEdit.getInmatchModelList(ioName,callBack);
		function callBack(immList){
			if(!immList || !immList.length){
				return;
			}
			DWRUtil.removeAllOptions("immId");
			DWRUtil.addOptions("immId",immList,"immId","immName"); 
			var options='';
			for(var i=0;i<immList.length;i++){
				if(immList[i].immDefault==1){
					$("#immId").val(immList[i].immId);
					break;
				}
			}
			$('#divImmUpload').show();
		}
	}
	function uploadCheck(){
		var fileName=$("#upload").val();
		if(!fileName){
			alert("请上传文件");
			return false;
		}
		$('#divImmUpload').hide();
		$('#divWait').show();
		//$('#divImmUploadResult').show();
		return true;
	}
	function downloadModel(){
		$("#downloadImmHref").attr("href","../io/downloadModel.action?immId="+$("#immId").val());
		return true;
	}
</script>
<jsp:include page="../sitemesh/div_wait.jsp" flush="true"></jsp:include>