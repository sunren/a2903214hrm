<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<%@ taglib prefix="ty" uri="/tengsource"%>
<%@ taglib prefix="jscalendar" uri="/jscalendar"%>
<html>
<head>
	<title>系统邮件管理</title>
	<script type="text/javascript" src="../dwr/interface/emailtemplate.js"></script>
	<script type="text/javascript" src="../dwr/interface/emailsend.js"></script>
	<script type="text/javascript">
</script>
<style type="text/css">
	.mySpan {
		font-size: 12px;
		color: #002780;
		text-decoration: underline;
		cursor: pointer
	}
</style>
</head>
<body onload="hrm.common.check_order();">
<s:component template="bodyhead">
	<s:param name="pagetitle" value="'系统邮件管理'" />
	<s:param name="helpUrl" value="'12'" />
</s:component>
<span class="errorMessage" id="msg"></span>
<form id="emailSearch" name="emailSearch" method="POST">
	<s:hidden id="order" name="page.order" />
	<s:hidden id="begainCreatDate" name="begainCreatDate" />
	<s:hidden id="endCreatDate" name="endCreatDate" />
	<s:hidden id="begainSendDate" name="begainSendDate" />
	<s:hidden id="endSendDate" name="endSendDate" />
	<table width="100%" cellspacing="0" cellpadding="0" class="formtable">
		<tr>
			<s:textfield label="收件人" id="esTo" name="emailsend.esTo" size="16"
				maxlength="64" />
			<td>
				创建时间：
				<s:textfield id="begainCreat" name="begainCreat" onclick="WdatePicker()" size="10"/>
                <img onclick="WdatePicker({el:'begainCreat'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
				到
				<s:textfield id="endCreat" name="endCreat" onclick="WdatePicker()" size="10"/>
                <img onclick="WdatePicker({el:'endCreat'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
			</td>
			<td>
				状态：
			</td>
			<td>
				<s:select id="esStatus" name="emailsend.esStatus"
					list="#{'':'请选择', 1:'已发送', 0:'未发送',2:'发送失败'}" />
			</td>
			<td>
				<table>
					<tr>
						<td>
							<input title="[Alt+F]" accesskey="F" name="submit_search"
								id="submit_search" class="button" type="button" value="查询"
								onclick="checkSearch();">
						</td>
						<td>
							<input title="[Alt+A]" accesskey="A" name="submit_all"
								id="submit_all" class="button" type="button"
								onclick="window.location.href='emailSearch.action';" value="重置">
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<s:textfield label="标题" id="esTitle" name="emailsend.esTitle"
				size="16" maxlength="128" />
			<td>
				发送时间：
				<s:textfield id="begainSend" name="begainSend" onclick="WdatePicker()" size="10"/>
                <img onclick="WdatePicker({el:'begainSend'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
				到
				<s:textfield id="endSend" name="endSend" onclick="WdatePicker()" size="10"/>
                <img onclick="WdatePicker({el:'endSend'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
			</td>
			<td>
				&nbsp;
			</td>
		</tr>
	</table>
	<br />
	<table>
		<tr>
			<td align="right">
				<input id="viewclose" type="button" class="button"
					onclick="sendAllSelected();" name="Submit3" value="批量重发">
				<input id="viewclose" type="button" class="button"
					onclick="deletAllSelected();" name="Submit3" value="批量删除">
				<input id="viewclose" type="button" class="button"
					onclick="setMailModel();" name="Submit3" value="邮件模板设置">
			</td>
		</tr>
	</table>
	<table width="100%" border="0" cellspacing="0" cellpadding="0"
		class="gridtableList">
		<s:if test="page.isSplit()">
			<tr class="listViewPaginationTdS1">
				<td colspan="8" align="right">
					<s:hidden name="page.currentPage" />
					<input id="operate" type="hidden" name="page.operate" />
					<a href="#" onclick="splits('first','emailSearch');"><img
							src='../resource/images/start.gif' width='11' height='9'
							alt='开始'>开始</a>
					<a href="#" onclick="splits('previous','emailSearch');"><img
							src='../resource/images/previous.gif' width='6' height='9'
							alt='上页'>上页</a> （
					<s:property value="page.currentPage+'/'+page.totalPages" />
					页｜共
					<s:property value="page.totalRows" />
					条）
					<a href="#" onclick="splits('next','emailSearch');">下页<img
							src='../resource/images/next.gif' width='6' height='9'>
					</a>
					<a href="#" onclick="splits('last','emailSearch');">最后<img
							src='../resource/images/end.gif' width='11' height='9' alt='最后'>
					</a>
				</td>
			</tr>
		</s:if>
		<tr>
			<th width="5%" NOWRAP>
				<input id="id_check_all" name='esId' class="checkbox" border="0"
					type="checkbox"
					onclick="check_all('emailSearch','id_check_all');" value="0">
			</th>
			<th width="20%" NOWRAP>
				<a href="#" onclick="hrm.common.order_submit('esTo','emailSearch');">收件人</a>
				<img src='../resource/images/arrow_.gif' width='8' height='10'
					id='esTo_img'>
			</th>
			<th width="%" NOWRAP>
				<a href="#" onclick="hrm.common.order_submit('esTitle','emailSearch');">标题</a>
				<img src='../resource/images/arrow_.gif' width='8' height='10'
					id='esTitle_img'>
			</th>
			<th width="10%" NOWRAP>
				<a href="#" onclick="hrm.common.order_submit('esStatus','emailSearch');">状态</a>
				<img src='../resource/images/arrow_.gif' width='8' height='10'
					id='esStatus_img'>
			</th>
			<th width="10%" NOWRAP>
				<a href="#" onclick="hrm.common.order_submit('esCreatetime','emailSearch');">创建时间</a>
				<img src='../resource/images/arrow_.gif' width='8' height='10'
					id='esCreatetime_img'>
			</th>
			<th width="10%" NOWRAP>
				<a href="#" onclick="hrm.common.order_submit('esSendtime','emailSearch');">发送时间</a>
				<img src='../resource/images/arrow_.gif' width='8' height='10'
					id='esSendtime_img'>
			</th>
			<th width="10%">
				操作
			</th>
		</tr>
		<tbody id="tabPage2items">
			<s:if test="!mailList.isEmpty()">
				<s:iterator value="mailList" status="index">
					<input type="hidden" id="esCc<s:property value="id"/>"
						name="esCc<s:property value="id"/>"
						value="<s:property value="esCc" />">
					<tr id="tabPage2<s:property value="id"/>">
						<td align="center">
							<input type="checkbox" name='esId' class="checkbox"
								value="<s:property value="id"/>" />
						</td>
						<td id="esTo<s:property value="id"/>">
							<s:property value="esTo" />
						</td>
						<td id="esTitle<s:property value="id"/>">
							<s:property value="esTitle" />
						</td>
						<td id="esStatus<s:property value="id"/>">
							<s:property value="getStatusString()" />
						</td>
						<td>
							<s:property value="esCreatetime" />
						</td>
						<td id="esSendtime<s:property value="id"/>">
							<s:property value="esSendtime" />
						</td>
						<td>
							<div id="esContent<s:property value="id"/>"
								style="display: none">
								<s:property value="esContent" />
							</div>
							<div align="center" style="DISPLAY: block; clear: both">
								<img href="#" alt='预览' class="mySpan" src="../resource/images/Preview.gif"
									onclick="cp='tabPage2';showSendDesc('<s:property value="id"/>','<s:property value="esTo" />');" />
								<img href="#" alt='删除' class="mySpan" src="../resource/images/delete.gif"
									onclick="cp='tabPage2';del('<s:property value="id"/>','<s:property value="esTo" />');" />
								<img href="#" alt='重发' class="mySpan" src="../resource/images/EmailResend.gif"
									onclick="sendAgain('<s:property value="id"/>','<s:property value="esTo" />');" />
							</div>
						</td>
					</tr>
				</s:iterator>
			</s:if>
			<s:else>
				<tr>
					<td align="center" colspan="10">
						不存在符合条件的邮件记录!
					</td>
				</tr>
			</s:else>
		</tbody>
	</table>
</form>

<div id="dlgViewMail" class="prompt_div_inline">
	<table width="500" cellpadding="0" cellspacing="1" border="0" width="100%" class="formtable">
		<tr>
			<td>
				<div id="viewTemplate" escape="false"></div>
			</td>
		</tr>
		<tr>
			<td align="center">
				<input id="close" type="button" onclick="hrm.common.closeDialog('dlgViewMail');" name="consol" value="关闭">
			</td>
		</tr>
	</table>
	<iframe scrolling="no" style="position: absolute; z-index: -1; width: 496px; height: 120px; top: 0px; left: 1px;" frameborder="0"></iframe>
</div>
<script type="text/javascript" language="javascript">
// 初始化dialog；
hrm.common.initDialog('dlgViewMail', 520);

var editObj = null;	//操作行 tr
var cp = 'tabPage2'; //操作页面 integer currentPage

$('#begainCreat').val($('#begainCreatDate').val());
$('#endCreat').val($('#endCreatDate').val());
$('#begainSend').val($('#begainSendDate').val());
$('#endSend').val($('#endSendDate').val());

function setMailModel(){
  	window.location = "etSearch.action";
}

// 查询前的查询条件检查；
function checkSearch(){
	var begainC =$('#begainCreat').val();
	var endC =$('#endCreat').val();
	var begainS =$('#begainSend').val();
	var endS =$('#endSend').val();
	$('#begainCreatDate').val(begainC);
	$('#endCreatDate').val(endC);
	$('#begainSendDate').val(begainS);
	$('#endSendDate').val(endS);
	if((begainC!=null && endC !=null)&&(begainC!=""&&endC!="") && begainC > endC){
		alert ("创建开始日期不能大于创建结束日期");
		return ;
	}
	if((begainC!=null&&begainC!="")){
		if(!hrm.common.isDate(begainC)){
			alert ("创建开始日期格式错误！");
			return ;
		}
	}
	if((endC!=null&&endC !="")){
		if(!hrm.common.isDate(endC)){
			alert ("创建结束日期格式错误！");
			return;
		}
	}
	if((begainS!=null && endS !=null)&&(begainS!=""&&endS!="") && begainS > endS){
		alert ("发送开始日期不能大于发送结束日期。");
		return ;
	}
	if((begainS!=null&&begainS!="")){
		if(!hrm.common.isDate(begainS)){
			alert ("发送开始日期格式错误！");
			return;
		}
	}
	if((endS!=null&&endS !="")){
		if(!hrm.common.isDate(endS)){
			alert("发送结束日期格式错误！");
			return ;
		}
	}
	document.getElementById('emailSearch').submit();
}

// 重新发送邮件；
function sendAllSelected(){
	var checkBoxs=document.getElementsByName('esId');
	var flag = -1;
	for(var i=0;i<checkBoxs.length;i++){
	if(checkBoxs[i].checked==true){flag = i;break;}
	}
	if(flag == -1){
		alert("必须至少选择一个！");
		return ;
	}
	if(!confirm("确定重发吗?"))return;
	for(var i=0;i<checkBoxs.length;i++){
		if(checkBoxs[i].checked==true){
			var emailId =checkBoxs[i].value;
			if(emailId!="0"&&emailId!="")
			    sendSelected(emailId);
		}
	}
}

// 批量重发函数；
function sendSelected(emailId){
	var params={id:emailId,esTo:$('#esTo'+emailId).html(),esCc:$('#esCc'+emailId).html(),esTitle:$('#esTitle'+emailId).html(),esContent:$('#esContent'+emailId).html(),esStatus:'0',esSendtime:new Date()};
	emailsend.updateEmailsend(params,updatecallback);
	function updatecallback(msg) {
		if(msg=='NOAUTH'){errMsg("errMsg","没有权限.");return;}   
		if(msg!='FAIL'){
			successMsg("errMsg","批量重发成功。");
			if(msg!='SUCC')
			params['id']=msg;
			dowirteEmailsend(params);
		}else{
			errMsg("errMsg","批量重发失败！");
		}
	}	
}

// 批量删除；
function deletAllSelected(){
	var checkBoxs=document.getElementsByName('esId');
	var flag = -1;
	for(var i=0;i<checkBoxs.length;i++){
		if(checkBoxs[i].checked==true){flag = i; break;}
	}
	
	if(flag == -1){
		alert("必须至少选择一个！");
		return ;
	}
	if(!confirm("确定删除吗?"))return;
	
	for(var i=1;i<checkBoxs.length;i++){
		if(checkBoxs[i].checked==true){
			delSelect(checkBoxs[i].value);
			checkBoxs[i].checked=false;
		}
	}
}

// 批量删除；
function delSelect(id){
	delOId = id;
	if(id!=null && id.length>0 && cp.trim().length>0){
		emailsend.delEmailsend(id,delcallback);
    }
	function delcallback(msg) {
		if(msg=='related'){errMsg("errMsg","数据正在使用,无法删除！");return;}  
		if(msg=='SUCC'){
			try{
				var editObj = document.getElementById(cp+id);
				var nextobj = $("#"+cp+id).next().get(0);
				editObj.parentNode.removeChild(editObj);
				if(editObj!=null){editObj=null;}
				if (nextobj != null) {
					editObj = nextobj;
					// editObj.addClassName('tr2');
				}
			}catch(e){alert(e);}
			successMsg("errMsg","批量删除邮件成功。");
		}else{
		    successMsg("errMsg","批量删除邮件失败！");
	    }
	}
}

// 弹出框显示邮件内容；
function showSendDesc(id,str){
	$("#dlgViewMail").dialog("option", 'title', "查看邮件(" + str + ")" .trim());
    hrm.common.openDialog("dlgViewMail");
	
    var innerHTML = "收件人:";
    innerHTML+=$('#esTo'+id).html()+"<br>";
    innerHTML+="CC  &nbsp; :";
    innerHTML+=$('#esCc'+id).val()+"<br>";
    innerHTML+="主　题：";
    innerHTML+=$('#esTitle'+id).html()+"<br>";
    innerHTML+="内　容：";
    innerHTML+=DWRUtil.unescapeHtml($('#esContent'+id).html())+"<br>";
    $('#viewTemplate').html(innerHTML);
}

//删除操作
function del(id,mailName){
	if(!confirm("确定删除吗?"))return;
	if(id!=null && id.length>0 && cp.trim().length>0){	
		if(cp=='tabPage1'){
			emailtemplate.delTemplate(id,delcallback);
		}else if(cp=='tabPage2'){
			emailsend.delEmailsend(id,delcallback);
		}
	}
	function delcallback(msg) {
		if(msg=='related'){errMsg("errMsg","数据正在使用,无法删除!");return;}  
		if(msg=='SUCC'){
			try{
				var editObj=document.getElementById(cp+id);
				var nextobj = $("#"+cp+id).next().get(0);
				editObj.parentNode.removeChild(editObj);
				if(editObj!=null){editObj=null;}
				if (nextobj != null) {
					editObj = nextobj;
					// editObj.style = 'tr2';
			    }
			}catch(e){alert(e);}
			if(cp=='tabPage1'){
				successMsg("errMsg","删除邮件模版("+mailName+")成功。");
			}else if(cp=='tabPage2'){
				successMsg("errMsg","删除邮件("+mailName+")成功。");
			}
		}else{
			if(cp=='tabPage1'){
				successMsg("errMsg","删除邮件模版("+mailName+")失败！");
			}else if(cp=='tabPage2'){
				successMsg("errMsg","删除邮件("+mailName+")失败！");
			}	
		}	
	}
}

// 重新发送邮件；
function sendAgain(emailId,str){
	if(!confirm("确定重发吗?"))return;
	var params={id:emailId,esTo:$('#esTo'+emailId).html(),esCc:$('#esCc'+emailId).html(),esTitle:$('#esTitle'+emailId).html(),esContent:$('#esContent'+emailId).html(),esStatus:'0',esSendtime:new Date()};
	emailsend.updateEmailsend(params,updatecallback);
	function updatecallback(msg) {
		if(msg=='NOAUTH'){errMsg("errMsg","没有权限.");return;}   
		if(msg!='FAIL'){
			successMsg("errMsg","重发邮件("+str+")成功。");
			if(msg!='SUCC')
			params['id']=msg;
			dowirteEmailsend(params);
		}else{
			errMsg("errMsg","重发邮件("+str+")失败！");
		}
    }
}

// 如果没有重发成功，设置页面上该行的值；
function dowirteEmailsend(params){
	var divId=0;
	for(var pro in params){
		if(pro=='id'){
			divId=params[pro] ;
		}
	}
	$('#esStatus'+divId).html("未发送");
	$('#esSendtime'+divId).html(new Date().toHRMDateString());
}


// 分页查询；
function splits(var1,split_form){
	$('#operate').val(var1);
	document.getElementById(split_form).submit();
}

</script>
	</body>
</html>
