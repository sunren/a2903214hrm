<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ taglib prefix="ty" uri="/tengsource"%>
<%@ taglib prefix="ww" uri="webwork"%>

<div id="button">
	<ul id="leftMenuForHomePage">
		<!-- ��ݲ˵� -->
		<li id="userinfo" nowrap>
			<p class="blueBgWhiteFont" nowrap>
				�ҵ�����
			</p>
			<img src="../resource/images/20050531_raq.gif" width="148"
				height="80">
			<ww:set name="user" value="#session['userinfo']" />
			<p nowrap>
				<span class="blackblod">����:</span>
				<ww:property value="#session['empName']" />
				<br>
				<span class="blackblod">����:</span>
				<ww:property value="#user.uiUsername" />
			</p>
		</li>
	</ul>
</div>
<script language="javascript">
//��ҳ��˵�html��ʽģ��
function getLeftMenuHtml(){
	return '<li nowrap>'
		+'	<a href="$url$">&gt;&gt; $desc$</a>'
		+'</li>';
}
//��ʼ����˵�html
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
