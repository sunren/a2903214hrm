<%@ page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<%@ taglib uri="/WEB-INF/config/jenkov/ajaxtags.tld"		prefix="ajax"%>
<%@ taglib uri="/WEB-INF/config/jenkov/tabbedpanetag.tld"	prefix="tabs"%>
<html>
	<head>
		<script type="text/javascript" src="../resource/js/profile/DHTreeClass.js"></script>
		<script type="text/javascript" src="../resource/js/examin/PositionTree.js"></script>
		<title>审批设置</title>
	</head>
	<body onload="initData()">
<s:component template="bodyhead">
	<s:param name="pagetitle" value="'审批设置：'" />
	<s:param name="helpUrl" value="'0'" />
</s:component>
<table border=0 cellpadding=0 cellspacing=0 width=100%>
	<tr>
		<td valign="top" >
			<div id="divPositionTree" style="width:230px;height:422px;background-color:#f5f5f5;border :1px solid Silver;overflow:auto;"></div>
		</td>
		<td valign="top" align="left" width="100%">
			<div id="logarea" style="background-color:#f5f5f5;height:422px;width:100%;border :1px solid Silver;overflow:hidden;">
				<IFRAME ID="iframe" name="iframe" marginWidth="0" marginHeight="0" FRAMEBORDER="0" align="left"
	 		 	width="100%" height="410" src="" style="overflow-y:auto;"></IFRAME>
			</div>
		</td>
	</tr>
</table>

<script type="text/javascript">
// 1.初始化节点数据，用于页面上抓取使用, type: 0:company, 1:branch, 2:dept, 3:position;
function node(id, parentId, name, type,state){
	this.id = id;
	this.parentId = parentId;
	this.name = name;
	this.type = type;
	this.isResp=state;
}
var nodeArr = {
	<s:iterator value="nodeList" status="index">
	    '<s:property value="nodeId"/>' : new node('<s:property value="nodeId"/>', '<s:property value="nodeParentId"/>', '<s:property value="nodeName"/>', '<s:property value="nodeType"/>','<s:property value="nodeStatus"/>')
	    <s:if test="!#index.isLast()">,</s:if>
	</s:iterator>
};
function getNodeType(id){//获取节点的类型;
	var obj = nodeArr[id];
	return obj.type;
}

// 2.初始化treeArray数据；
var treeArray = new Array(
	<s:iterator value="nodeList" status="index">
	    ['<s:property value="nodeId"/>', '<s:property value="nodeParentId"/>', '<s:property value="nodeName"/>']
	    <s:if test="!#index.isLast()">,</s:if>
	</s:iterator>
);

// 3.初始化tree；
var tree;
var positionTree;
function initData(){
	//组装参数；
	var param = {
		"treeDiv"      : 'divPositionTree',
		"dataSource"   : treeArray,
		"menuXml"      : null,
		"enableChkbox" : false,
		"enableDrag"   : false
	};
	// 子类初始化；
	positionTree = new PositionTree(param);
	positionTree.init();// 初始化树形结构；
	tree = positionTree.tree;
	//设置不同的图片；
	positionTree.setNodesImage(nodeArr);
}

// 刷新右边iframe中的页面；
function refreshIFrame(url){
	var iframe=document.getElementById("iframe");
	if(hrm.common.navigatorIsFF()){
		iframe.contentWindow.document.location=url;
	}else{
		iframe.src=url;
	}
}
</script>
</body>
</html>