<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ taglib prefix="ty" uri="/tengsource"%>
<%@ taglib prefix="ww" uri="webwork"%>

<div id="button">
	<ul id="leftMenuForHomePage">
		<!-- 快捷菜单 -->
		<li id="userinfo" nowrap>
			<p class="blueBgWhiteFont" nowrap>
				我的资料
			</p>
			<img src="../resource/images/20050531_raq.gif" width="148"
				height="80">
			<ww:set name="user" value="#session['userinfo']" />
			<p nowrap>
				<span class="blackblod">姓名:</span>
				<ww:property value="#session['empName']" />
				<br>
				<span class="blackblod">工号:</span>
				<ww:property value="#user.uiUsername" />
			</p>
		</li>
	</ul>
</div>
<script language="javascript">
//首页左菜单html格式模板
function getLeftMenuHtml(){
	return '<li nowrap>'
		+'	<a href="$url$">&gt;&gt; $desc$</a>'
		+'</li>';
}
//初始化左菜单html
function initLeftMenu(menuList){
	var subMenuArr=menuList[0]["subMenu"];
	var trHtml=getLeftMenuHtml();
	var menuArr=new Array();
	for(var i=0;i<subMenuArr.length;i++){
		menuArr[i]=model.simple.setParamterValue(trHtml,subMenuArr[i]);
	}
	var ele=model.simple.getElement("leftMenuForHomePage");
	ele.innerHTML+=menuArr.join("");
}
initLeftMenu(hrmAllMenu);
</script>
