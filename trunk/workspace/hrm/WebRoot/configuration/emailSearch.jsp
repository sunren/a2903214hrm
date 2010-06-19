<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<%@ taglib prefix="ty" uri="/tengsource"%>
<%@ taglib prefix="jscalendar" uri="/jscalendar"%>
<html>
<head>
	<title>ϵͳ�ʼ�����</title>
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
	<s:param name="pagetitle" value="'ϵͳ�ʼ�����'" />
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
			<s:textfield label="�ռ���" id="esTo" name="emailsend.esTo" size="16"
				maxlength="64" />
			<td>
				����ʱ�䣺
				<s:textfield id="begainCreat" name="begainCreat" onclick="WdatePicker()" size="10"/>
                <img onclick="WdatePicker({el:'begainCreat'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
				��
				<s:textfield id="endCreat" name="endCreat" onclick="WdatePicker()" size="10"/>
                <img onclick="WdatePicker({el:'endCreat'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
			</td>
			<td>
				״̬��
			</td>
			<td>
				<s:select id="esStatus" name="emailsend.esStatus"
					list="#{'':'��ѡ��', 1:'�ѷ���', 0:'δ����',2:'����ʧ��'}" />
			</td>
			<td>
				<table>
					<tr>
						<td>
							<input title="[Alt+F]" accesskey="F" name="submit_search"
								id="submit_search" class="button" type="button" value="��ѯ"
								onclick="checkSearch();">
						</td>
						<td>
							<input title="[Alt+A]" accesskey="A" name="submit_all"
								id="submit_all" class="button" type="button"
								onclick="window.location.href='emailSearch.action';" value="����">
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<s:textfield label="����" id="esTitle" name="emailsend.esTitle"
				size="16" maxlength="128" />
			<td>
				����ʱ�䣺
				<s:textfield id="begainSend" name="begainSend" onclick="WdatePicker()" size="10"/>
                <img onclick="WdatePicker({el:'begainSend'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
				��
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
					onclick="sendAllSelected();" name="Submit3" value="�����ط�">
				<input id="viewclose" type="button" class="button"
					onclick="deletAllSelected();" name="Submit3" value="����ɾ��">
				<input id="viewclose" type="button" class="button"
					onclick="setMailModel();" name="Submit3" value="�ʼ�ģ������">
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
							alt='��ʼ'>��ʼ</a>
					<a href="#" onclick="splits('previous','emailSearch');"><img
							src='../resource/images/previous.gif' width='6' height='9'
							alt='��ҳ'>��ҳ</a> ��
					<s:property value="page.currentPage+'/'+page.totalPages" />
					ҳ����
					<s:property value="page.totalRows" />
					����
					<a href="#" onclick="splits('next','emailSearch');">��ҳ<img
							src='../resource/images/next.gif' width='6' height='9'>
					</a>
					<a href="#" onclick="splits('last','emailSearch');">���<img
							src='../resource/images/end.gif' width='11' height='9' alt='���'>
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
				<a href="#" onclick="hrm.common.order_submit('esTo','emailSearch');">�ռ���</a>
				<img src='../resource/images/arrow_.gif' width='8' height='10'
					id='esTo_img'>
			</th>
			<th width="%" NOWRAP>
				<a href="#" onclick="hrm.common.order_submit('esTitle','emailSearch');">����</a>
				<img src='../resource/images/arrow_.gif' width='8' height='10'
					id='esTitle_img'>
			</th>
			<th width="10%" NOWRAP>
				<a href="#" onclick="hrm.common.order_submit('esStatus','emailSearch');">״̬</a>
				<img src='../resource/images/arrow_.gif' width='8' height='10'
					id='esStatus_img'>
			</th>
			<th width="10%" NOWRAP>
				<a href="#" onclick="hrm.common.order_submit('esCreatetime','emailSearch');">����ʱ��</a>
				<img src='../resource/images/arrow_.gif' width='8' height='10'
					id='esCreatetime_img'>
			</th>
			<th width="10%" NOWRAP>
				<a href="#" onclick="hrm.common.order_submit('esSendtime','emailSearch');">����ʱ��</a>
				<img src='../resource/images/arrow_.gif' width='8' height='10'
					id='esSendtime_img'>
			</th>
			<th width="10%">
				����
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
								<img href="#" alt='Ԥ��' class="mySpan" src="../resource/images/Preview.gif"
									onclick="cp='tabPage2';showSendDesc('<s:property value="id"/>','<s:property value="esTo" />');" />
								<img href="#" alt='ɾ��' class="mySpan" src="../resource/images/delete.gif"
									onclick="cp='tabPage2';del('<s:property value="id"/>','<s:property value="esTo" />');" />
								<img href="#" alt='�ط�' class="mySpan" src="../resource/images/EmailResend.gif"
									onclick="sendAgain('<s:property value="id"/>','<s:property value="esTo" />');" />
							</div>
						</td>
					</tr>
				</s:iterator>
			</s:if>
			<s:else>
				<tr>
					<td align="center" colspan="10">
						�����ڷ����������ʼ���¼!
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
				<input id="close" type="button" onclick="hrm.common.closeDialog('dlgViewMail');" name="consol" value="�ر�">
			</td>
		</tr>
	</table>
	<iframe scrolling="no" style="position: absolute; z-index: -1; width: 496px; height: 120px; top: 0px; left: 1px;" frameborder="0"></iframe>
</div>
<script type="text/javascript" language="javascript">
// ��ʼ��dialog��
hrm.common.initDialog('dlgViewMail', 520);

var editObj = null;	//������ tr
var cp = 'tabPage2'; //����ҳ�� integer currentPage

$('#begainCreat').val($('#begainCreatDate').val());
$('#endCreat').val($('#endCreatDate').val());
$('#begainSend').val($('#begainSendDate').val());
$('#endSend').val($('#endSendDate').val());

function setMailModel(){
  	window.location = "etSearch.action";
}

// ��ѯǰ�Ĳ�ѯ������飻
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
		alert ("������ʼ���ڲ��ܴ��ڴ�����������");
		return ;
	}
	if((begainC!=null&&begainC!="")){
		if(!hrm.common.isDate(begainC)){
			alert ("������ʼ���ڸ�ʽ����");
			return ;
		}
	}
	if((endC!=null&&endC !="")){
		if(!hrm.common.isDate(endC)){
			alert ("�����������ڸ�ʽ����");
			return;
		}
	}
	if((begainS!=null && endS !=null)&&(begainS!=""&&endS!="") && begainS > endS){
		alert ("���Ϳ�ʼ���ڲ��ܴ��ڷ��ͽ������ڡ�");
		return ;
	}
	if((begainS!=null&&begainS!="")){
		if(!hrm.common.isDate(begainS)){
			alert ("���Ϳ�ʼ���ڸ�ʽ����");
			return;
		}
	}
	if((endS!=null&&endS !="")){
		if(!hrm.common.isDate(endS)){
			alert("���ͽ������ڸ�ʽ����");
			return ;
		}
	}
	document.getElementById('emailSearch').submit();
}

// ���·����ʼ���
function sendAllSelected(){
	var checkBoxs=document.getElementsByName('esId');
	var flag = -1;
	for(var i=0;i<checkBoxs.length;i++){
	if(checkBoxs[i].checked==true){flag = i;break;}
	}
	if(flag == -1){
		alert("��������ѡ��һ����");
		return ;
	}
	if(!confirm("ȷ���ط���?"))return;
	for(var i=0;i<checkBoxs.length;i++){
		if(checkBoxs[i].checked==true){
			var emailId =checkBoxs[i].value;
			if(emailId!="0"&&emailId!="")
			    sendSelected(emailId);
		}
	}
}

// �����ط�������
function sendSelected(emailId){
	var params={id:emailId,esTo:$('#esTo'+emailId).html(),esCc:$('#esCc'+emailId).html(),esTitle:$('#esTitle'+emailId).html(),esContent:$('#esContent'+emailId).html(),esStatus:'0',esSendtime:new Date()};
	emailsend.updateEmailsend(params,updatecallback);
	function updatecallback(msg) {
		if(msg=='NOAUTH'){errMsg("errMsg","û��Ȩ��.");return;}   
		if(msg!='FAIL'){
			successMsg("errMsg","�����ط��ɹ���");
			if(msg!='SUCC')
			params['id']=msg;
			dowirteEmailsend(params);
		}else{
			errMsg("errMsg","�����ط�ʧ�ܣ�");
		}
	}	
}

// ����ɾ����
function deletAllSelected(){
	var checkBoxs=document.getElementsByName('esId');
	var flag = -1;
	for(var i=0;i<checkBoxs.length;i++){
		if(checkBoxs[i].checked==true){flag = i; break;}
	}
	
	if(flag == -1){
		alert("��������ѡ��һ����");
		return ;
	}
	if(!confirm("ȷ��ɾ����?"))return;
	
	for(var i=1;i<checkBoxs.length;i++){
		if(checkBoxs[i].checked==true){
			delSelect(checkBoxs[i].value);
			checkBoxs[i].checked=false;
		}
	}
}

// ����ɾ����
function delSelect(id){
	delOId = id;
	if(id!=null && id.length>0 && cp.trim().length>0){
		emailsend.delEmailsend(id,delcallback);
    }
	function delcallback(msg) {
		if(msg=='related'){errMsg("errMsg","��������ʹ��,�޷�ɾ����");return;}  
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
			successMsg("errMsg","����ɾ���ʼ��ɹ���");
		}else{
		    successMsg("errMsg","����ɾ���ʼ�ʧ�ܣ�");
	    }
	}
}

// ��������ʾ�ʼ����ݣ�
function showSendDesc(id,str){
	$("#dlgViewMail").dialog("option", 'title', "�鿴�ʼ�(" + str + ")" .trim());
    hrm.common.openDialog("dlgViewMail");
	
    var innerHTML = "�ռ���:";
    innerHTML+=$('#esTo'+id).html()+"<br>";
    innerHTML+="CC  &nbsp; :";
    innerHTML+=$('#esCc'+id).val()+"<br>";
    innerHTML+="�����⣺";
    innerHTML+=$('#esTitle'+id).html()+"<br>";
    innerHTML+="�ڡ��ݣ�";
    innerHTML+=DWRUtil.unescapeHtml($('#esContent'+id).html())+"<br>";
    $('#viewTemplate').html(innerHTML);
}

//ɾ������
function del(id,mailName){
	if(!confirm("ȷ��ɾ����?"))return;
	if(id!=null && id.length>0 && cp.trim().length>0){	
		if(cp=='tabPage1'){
			emailtemplate.delTemplate(id,delcallback);
		}else if(cp=='tabPage2'){
			emailsend.delEmailsend(id,delcallback);
		}
	}
	function delcallback(msg) {
		if(msg=='related'){errMsg("errMsg","��������ʹ��,�޷�ɾ��!");return;}  
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
				successMsg("errMsg","ɾ���ʼ�ģ��("+mailName+")�ɹ���");
			}else if(cp=='tabPage2'){
				successMsg("errMsg","ɾ���ʼ�("+mailName+")�ɹ���");
			}
		}else{
			if(cp=='tabPage1'){
				successMsg("errMsg","ɾ���ʼ�ģ��("+mailName+")ʧ�ܣ�");
			}else if(cp=='tabPage2'){
				successMsg("errMsg","ɾ���ʼ�("+mailName+")ʧ�ܣ�");
			}	
		}	
	}
}

// ���·����ʼ���
function sendAgain(emailId,str){
	if(!confirm("ȷ���ط���?"))return;
	var params={id:emailId,esTo:$('#esTo'+emailId).html(),esCc:$('#esCc'+emailId).html(),esTitle:$('#esTitle'+emailId).html(),esContent:$('#esContent'+emailId).html(),esStatus:'0',esSendtime:new Date()};
	emailsend.updateEmailsend(params,updatecallback);
	function updatecallback(msg) {
		if(msg=='NOAUTH'){errMsg("errMsg","û��Ȩ��.");return;}   
		if(msg!='FAIL'){
			successMsg("errMsg","�ط��ʼ�("+str+")�ɹ���");
			if(msg!='SUCC')
			params['id']=msg;
			dowirteEmailsend(params);
		}else{
			errMsg("errMsg","�ط��ʼ�("+str+")ʧ�ܣ�");
		}
    }
}

// ���û���ط��ɹ�������ҳ���ϸ��е�ֵ��
function dowirteEmailsend(params){
	var divId=0;
	for(var pro in params){
		if(pro=='id'){
			divId=params[pro] ;
		}
	}
	$('#esStatus'+divId).html("δ����");
	$('#esSendtime'+divId).html(new Date().toHRMDateString());
}


// ��ҳ��ѯ��
function splits(var1,split_form){
	$('#operate').val(var1);
	document.getElementById(split_form).submit();
}

</script>
	</body>
</html>
