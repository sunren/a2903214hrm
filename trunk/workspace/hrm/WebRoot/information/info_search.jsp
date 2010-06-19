<%@page pageEncoding="GBK" contentType="text/html; charset=GBK" %>
<%@taglib prefix="s" uri="webwork"%>
<%@ taglib prefix="ty" uri="/tengsource"%>
<html>
<head>
<script type="text/javascript" src="../dwr/interface/factory.js"></script>
<link href="<s:url value="/resource/css/index.css"/>" rel="stylesheet" type="text/css" />	

<title>公司信息</title>
</head>
<body>
<s:component template="bodyhead">
	<s:param name="pagetitle" value="'公司信息：'+(infoclass==null?'':infoclass.infoclassName)" />
	<s:param name="helpUrl" value="'13'" />
</s:component>	
<div class="tab-page" id="tabPage1">
<table cellpadding="0" cellspacing="0" width="95%">
<tr><td valign="top">
	<!-- 信息列表 bgn -->
	<form name="searchInfo" action="searchInfo.action" namespace="/information" method="POST">
	<s:hidden id="order" name="page.order"/>	
	<s:hidden id="operate" name="page.operate"/>
	<s:hidden id="delInfoId" name="delInfoId"/>
	<s:hidden id="classId" name="classId"/>	
	<table cellpadding="5" cellspacing="5" width="100%" border="0">
		 <s:if test="page.isSplit()">
	     <tr>
	     	<td colspan="8" align="right"  class="listViewPaginationTdS1">
	     	<!--
	     	<s:textfield name="searchKey"/><input type="submit" value="搜索">&nbsp;&nbsp;
	     	-->
	     	&nbsp;
	     		<s:hidden name="page.currentPage"/>
                <a href="#" onclick="splits('first');"><img src='../resource/images/start.gif' width='11' height='9' alt='开始'>开始</a>
                <a href="#" onclick="splits('previous');"><img src='../resource/images/previous.gif' width='6' height='9' alt='上页'>上页</a>
                   （<s:property value="page.currentPage+'/'+page.totalPages"/>页｜共<s:property value="page.totalRows"/>条）
                <a href="#" onclick="splits('next');">下页<img src='../resource/images/next.gif' width='6' height='9'></a>
                <a href="#" onclick="splits('last');">最后<img src='../resource/images/end.gif' width='11' height='9' alt='最后'></a>
			</td>
	     </tr>		
	     </s:if> 
		<tbody id="tabPage1items">
	     <s:if test="!infoList.isEmpty()">        
	     	<s:iterator value="infoList" status="index">
	     		<tr>
					<td>
						<!--<img src=../resource/images/arwnav.gif>-->
					<a href="#" onclick="showDescInfo('<s:property value="id" />')"><span id="infoTitle<s:property value="id"/>" style="font-size: 14px;font-weight: bold;color: #002693;text-decoration: underline;"><s:property value="infoTitle" /></span></a><br>
		     		<div class="infobrief" style="font-size: 11px;">
		     			<s:if test="infoPicName!=null && infoPicName!=''">
		     				<img src="../resource/images/member_face2.gif">&nbsp;
		     			</s:if>
		     			<s:if test="infoFileName!=null && infoFileName!=''">
		     				<img src="../resource/images/attachment.gif">&nbsp;
		     			</s:if>
		     			隶属分类:&nbsp;<s:property value="infoClass.infoclassName" />&nbsp;&nbsp;
		     			提交者：<s:property value="infoCreateBy.empName" />&nbsp;&nbsp;
		     			<ty:auth auths="801">
		     				状态:&nbsp;<span class="orangeFont">
		     				<s:if test="infoStatus==null || infoStatus==0">隐藏</s:if>
		     				<s:else>正常</s:else>    
		     				&nbsp;&nbsp;
		     				</span> 			
		     			</ty:auth>
		     		</div>
		     		<div id="infoBreif<s:property value="id"/>"><s:property value="infoBreif" escape="false"/></div>
	     			<div class="btnlink">
						<ty:auth auths="801">
							<span id='<s:property value="id" />Content' style="display:none;"><s:property value="infoContent" /></span>
						  <a href="#" onclick="showInfoDiv('<s:property value="id" />','<s:property value="infoClass.infoclassName" />',<s:property value="infoStatus" />);">快速修改</a>
						  <a href="#" onclick="updateInfo('<s:property value="id" />')">完全修改</a>
						  <a href="#" onclick="del('<s:property value="id" />')">删除</a>
						  </ty:auth>
						</div>
	     			</td>  
	     		</tr>
	     	</s:iterator>
	    </s:if>
		<s:else>
			<!-- 不存在符合条件的供应商！ -->
			<script>errMsg("","不存在记录");</script>
			<td>
		</s:else>
		</tbody>
	</table>
	</form>
	<!-- 信息列表 end -->
</td>
<td align="center" valign="top" width="150">
	<!-- 信息类别列表 bgn -->
	   
	<table cellpadding="0" cellspacing="0"  width = "150" class="formtable">
		<tr><td>&nbsp;
		<a href="searchInfo.action"><img src='../resource/images/ProspectLists.gif' alt='查看所有' border="0">查看所有</a>
		</td></tr>
		<s:iterator value="classList" status="index">
		<tr><td>&nbsp;
		<a href="searchInfo.action?classId=<s:property value="id"/>"><img src="../resource/images/ProspectLists.gif" border="0"><s:property value="infoclassName"/></a><br>
		</td></tr>
		</s:iterator>
	</table>
</td>
</table>


<!--信息修改表单-->	
<ty:auth auths="961">
<div id="dlgModifyInfo" style="width:460">
	<FORM id="tabPage1Form" METHOD=POST>
	<s:hidden id="id"/>
	<TABLE cellpadding="0" cellspacing="1" border="0" width="100%" class="formtable">
		<tr>
			<td nowrap>状态<font color="red">*</font>:</td>
			<td nowrap>
			<s:select id="info_infoStatus" name="info.infoStatus" required="true" list="#{1:'正常', 0:'隐藏'}" disabled="true"/>
			分类<font color="red">*</font>:
			<s:textfield id="info_infoClass_infoclassName" name="info.infoClass" disabled="true" size="34"/>
			</td>
		</tr>
		 
		<tr>
			<td nowrap>标题<font color="red">*</font>:</td>
			<td><s:textfield id="info_infoTitle" name="info.infoTitle" required="true" size="40" maxlength="64"/></td>
		</tr>
		<tr>
			<td nowrap>简述<font color="red">*</font>:</td>
			<td><s:textarea id="info_infoBreif" name="info.infoBreif" required="true" cols="40" rows="2"/></td>
		</tr>
		<tr>
			<td nowrap valign="center">正文<font color="red">*</font>:</td>
			<td><s:textarea id="info_infoContent" name="info.infoContent" required="true" cols="40" rows="8"/></td>
		</tr>
		  
		<tr><td></td></tr>  
		<TR>
		<TD align="center" colspan="2">
		<INPUT id="tabPage1updatebtm" TYPE="button" NAME="btn" onclick="save();" value="修改">
		<input id="formbutton" type="button" onclick="resetForm();" name="Submit2" value="清空">
		<input id="tabPage1close" type="button" onclick="closeDiag();"name="Submit3" value="关闭"></TD>
		</TR>
	</TABLE>
</FORM>
</div>	
</ty:auth>
<script type="text/javascript" language="javascript">
// 初始化dialog信息；
hrm.common.initDialog("dlgModifyInfo", 460);

var editObj = null;				//操作行 tr
var cp = 'tabPage1';			//操作页面 integer  currentPage

// ==============================快速修改=================================================

//显示当前行TR  信息
function showInfoDiv(infoId,infoClassName,infoStatus){									//get current page form
	$('#id').val(infoId);
	$('#info_infoClass_infoclassName').val(infoClassName);
	$('#info_infoTitle').val($('#infoTitle'+infoId).html());
	$('#info_infoBreif').val($('#infoBreif'+infoId).html());
	$('#info_infoContent').val($('#'+infoId+'Content').html());
	$('#info_infoStatus').val(infoStatus);
	hrm.common.openDialog('dlgModifyInfo');
}


/*	显示修改页面 (修改状态) 	*/
function showUpdate(){
	var updatebtm=document.getElementById(cp+'updatebtm');
	if(updatebtm!=null){
		updatebtm.onclick=save;
		updatebtm.value="保存";
	}
}

function resetForm(){
	$('#info_infoTitle').val("");
	$('#info_infoBreif').val("");
	$('#info_infoContent').val("");
}


/**		修改保存操作		**/
function save(){
  if(!docheck()){return false;}
  var params={id:$('#id').val(),infoTitle:$('#info_infoTitle').val(),infoBreif:$('#info_infoBreif').val(),infoContent:$('#info_infoContent').val(),infoStatus:$('#info_infoStatus').val()};
  factory.updateInformation(params,updatecallback);
  function updatecallback(msg) {
	  if(msg=='NOAUTH'){errMsg("errMsg","您无权执行此操作！");return;}   
	  if(msg=='SUCC'){successMsg("errMsg","修改成功。");dowirte($('#id').val(),$('#info_infoTitle').val(),$('#info_infoBreif').val(),$('#info_infoContent').val());}
	  else{errMsg("errMsg","修改失败！");}
  }
}

//检查验证
function docheck(){
	var dep=document.getElementById(cp+'Form'),isture = true, _es=dep.elements;
	//clearErr();												//clear error message.清除错误信息
	for(var i=1;i<_es.length;i++){
		if(_es[i].value==''){ 
			alert('必填项不能为空！');
			isture = false; 
			_es[i].focus();
			break;
		}
	}
	return isture;
}

/***  修改后刷新数据		**/
function dowirte(a,b,c,d){
  $('#infoTitle'+a).html(b);
  $('#infoBreif'+a).html(c);
  $('#'+a+'Content').html(d);
  hrm.common.closeDialog('dlgModifyInfo');
}

// ==============================快速 修改=================================================
//完全修改
function updateInfo(infoId){
	var tmpUrl="updateInfo.action?infoId="+infoId;
	location.href=tmpUrl;
}

function closeDiag(){
	hrm.common.closeDialog('dlgModifyInfo');
}
//改变textarea高度
function changHeight(elementId,goalHeight){
	if(elementId==null || elementId.length==0 || goalHeight<0)
		return null;
	
	document.getElementById(elementId).style.height=goalHeight;
}
//分页操作
function splits(var1){
	 var element1=document.getElementById('operate');
	 element1.value=var1;
	 document.searchInfo.submit();
}
//删除操作
function del(var1){
	 if(confirm("确定要删除该纪录吗？")){
		 $('#delInfoId').val(var1);
	 }
	 document.searchInfo.submit();
}
//弹出详细页面
function showDescInfo(idStr){
	url = "../information/openInfo.action?infoId="+idStr;
	window.open(url, '_blank', 'width=680px, height=460px, left=200px, top=150px, scrollbars =yes');
}	

//
function showContent(infoContent){
	var strContent=infoContent;
	strContent=strContent.replace(chr(10),'/n/n');
	
	return strContent;
}
</script>

</div>
</body>
</html>
