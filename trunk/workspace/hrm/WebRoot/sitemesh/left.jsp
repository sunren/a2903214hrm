<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>

<script language="javascript">
//隐藏/显示左菜单
function showHideLeftCol(){
	var divEle=document.getElementById('leftCol');
	var imgSrc=document.getElementById('HideHandle');
	if(divEle.style.display=='none'){
		divEle.style.display='inline';
		imgSrc.src = '../resource/images/hide.gif';
		hrm.common.setCookie('showLeftCol','true',30,'/');
	}else{
		divEle.style.display='none';
		imgSrc.src = '../resource/images/show.gif';	
		hrm.common.setCookie('showLeftCol','false',30,'/');
	}
}
//左菜单html格式模板
function getLeftMenuHtml(){
	var tableHtml={};
	tableHtml["head"]='<table id="left_menu" cellpadding="0" cellspacing="0">';
	tableHtml["tail"]='</table>';
	tableHtml["tr"]='<tr>'
		+'<td class="td-r b"><a href="$url$"><img src="$icon$" alt="$desc$" /></a></td>'
		+'<td class="td-b"><a href="$url$">$desc$</a></td>'
		+'</tr>';
	return tableHtml;
}
//初始化左菜单html
function initLeftMenu(menuList,selectIndex){
	var subMenuArr=menuList[selectIndex]["subMenu"];
	var tableHtml=getLeftMenuHtml();
	var menuArr=new Array();
	for(var i=0;i<subMenuArr.length;i++){
		menuArr[i]=model.simple.setParamterValue(tableHtml["tr"],subMenuArr[i]);
	}
	var divEle=model.simple.getElement("leftCol");
	divEle.innerHTML+=tableHtml["head"]+menuArr.join("")+tableHtml["tail"];
}
</script>
<table width="100%" cellpadding="0" cellspacing="1" style="margin:0px; padding:0px">
<tr>
	<td valign="top" nowrap="nowrap">
		<img id="HideHandle" name="HideHandle" src="../resource/images/show.gif" alt="<s:text name='desc.menu'/>" onclick="showHideLeftCol();">
	</td>
	<td valign="top">
		<div id="leftCol">
			<table cellpadding="0" cellspacing="0" style="margin:0px; padding:0px" width="140" >
				<tr>
					<th width="5"><img src="../resource/images/moduleTab_left.gif"></th>
					<th style="background-image : url(../resource/images/moduleTab_middle.gif);" width="100%">菜单导航</th>
					<th width="5"><img src="../resource/images/moduleTab_right.gif"></th>
				</tr>
			</table>
			<table id="left_menu" cellpadding="0" cellspacing="0">
			</table>
		</div>
	</td>
</tr>
</table>
<script language="Javascript">
//判断是显示还是隐藏左菜单
var show=hrm.common.getCookie('showLeftCol');
if(show==null){
	hrm.common.setCookie('showLeftCol','true',30,'/','','');
	show='true';
}
if (show == 'true') {
	this.document.getElementById('leftCol').style.display='inline';
	document['HideHandle'].src = '../resource/images/hide.gif';
} else {
	this.document.getElementById('leftCol').style.display='none';
	document['HideHandle'].src = '../resource/images/show.gif';		
}
//初始化左边的菜单列表
initLeftMenu(hrmAllMenu,currMenuIndex);
</script>