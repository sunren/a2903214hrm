<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<%@ taglib uri="/WEB-INF/config/jenkov/treetag.tld" prefix="tree"%>
<%@ taglib uri="/WEB-INF/config/jenkov/requesttags.tld" prefix="request" %>
<%@ taglib uri="/WEB-INF/config/jenkov/ajaxtags.tld" prefix="ajax" %>
<%@ taglib uri="/WEB-INF/config/jenkov/templatetag.tld" prefix="tmpl" %>
<%@ taglib uri="/WEB-INF/config/jenkov/logictags.tld" prefix="logic" %>
<html>

<head>
    <title>员工通讯录</title>
    <link rel="stylesheet" href="/prizetagsdemo/stylesheet.css" type="text/css">
    <script type="text/javascript" src="../resource/js/jenkov/jenkov_ajaxScript.js"></script>
    <script type="text/javascript" src="../resource/js/profile/DHTreeClass.js"></script>
</head>

<body onload="initData();">
<s:component template="bodyhead">
	<s:param name="pagetitle" value="'员工通讯录：'" />
	<s:param name="helpUrl" value="'34'"/>
</s:component>
<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr>
		<td valign="top" width="19%">
			<div id="empConnTree" style="width:100%;height:400px;background-color:#f5f5f5;border :1px solid Silver;overflow:auto;"></div>
		</td>
		<td valign="top" align="left" width="75%">
			<div id="logarea" style="background-color:#f5f5f5;height:400px;width:100%;border :1px solid Silver;overflow:hidden;">
				<IFRAME id="iframe" name="iframe" marginWidth="0" marginHeight="0" FRAMEBORDER="0" align="left"
	 		 	width="100%" height="400" src="" style="overflow-y:auto;"></IFRAME>
			</div>
		</td>
	</tr>
</table>

<script type="text/javascript">
//子类定义；
var EmpConnTree = function(param){
	DHTreeClass.call(this, param);
};
EmpConnTree.prototype = DHTreeClass.prototype;
//单击事件；
EmpConnTree.prototype.lclick = function(id){
	var empId = nodeArr[id].empId;
	var url = "detailEmployeeInfo.action?id="+empId;
	refreshIFrame(url);
};


//刷新右边iframe中的页面；
function refreshIFrame(url){
	var iframe=document.getElementById("iframe");
	if(hrm.common.navigatorIsFF()){
		iframe.contentWindow.document.location=url;
	}else{
		iframe.src=url;
	}
}

// 初始化treeArray数据；
var treeArray = new Array(
	<s:iterator value="nodeList" status="index">
	    ['<s:property value="nodeId"/>', '<s:property value="nodeParentId"/>', '<s:property value="nodeName"/>']
	    <s:if test="!#index.isLast()">,</s:if>
	</s:iterator>
);
function node(id, parentId, name, empId, type){
	this.id = id;
	this.parentId = parentId;
	this.name = name;
	this.empId = empId;
	this.type = type;
}
var rootId = '<s:property value="rootId"/>';
var nodeArr = {
	<s:iterator value="nodeList" status="index">
	    '<s:property value="nodeId"/>' : new node('<s:property value="nodeId"/>', '<s:property value="nodeParentId"/>', '<s:property value="nodeName"/>', 
	    	    '<s:property value="empId"/>', '<s:property value="nodeType"/>')
	    <s:if test="!#index.isLast()">,</s:if>
	</s:iterator>
};

//3.初始化tree；
var tree;
var empConnTree;
function initData(){
	//组装参数；
	var param = {
		"treeDiv"      : 'empConnTree',
		"dataSource"   : treeArray,
		"menuXml"      : null,
		"enableChkbox" : false,
		"enableDrag"   : false,
		"rootId" : rootId
	};
	// 子类初始化；
	empConnTree = new EmpConnTree(param);
	empConnTree.init();// 初始化树形结构；
	tree = empConnTree.tree;
	// 设置图片；
	empConnTree.setNodesImage(nodeArr);

	var posId = '<s:property value="posId"/>';
	if(posId != null && posId != "" && posId.trim().length>0){
		tree._selectItem(tree._globalIdStorageFind(posId),event);
	}
}
var empNo = '<s:property value="id"/>';
var url = "detailEmployeeInfo.action?id="+empNo;
refreshIFrame(url);
</script>
</body>

</html>
