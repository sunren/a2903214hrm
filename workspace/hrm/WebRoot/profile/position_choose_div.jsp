<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<script type="text/javascript" src="../dwr/interface/OrgManageAction.js"></script>
<script type="text/javascript" src="../resource/js/profile/DHTreeClass.js"></script>
<script type="text/javascript" src="../resource/js/profile/ChoosePositionTree.js"></script>

<div id="choosePositionDiv" style="position:absolute;z-index:10000;width:210px;display:none;" class="prompt_div_inline">	
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	  	<tr>
	  		<td>
	  			<div id="positionTreeDiv" align="left" style="overflow:auto; border:1px #dddddd solid;height:230px ;background-color: #ECF3F6;"></div>
	  		</td>
	  	</tr>
	</table>
	
</div>
<script type="text/javascript">
$(function() {
    $("#showdiv").click(function(e) {
		if ($("#choosePositionDiv").is(":hidden")) {
		    $("#choosePositionDiv").fadeIn();
			e?e.stopPropagation():event.cancelBubble = true;
		}
	});
    $("#showdiv1").click(function(e) {
		if ($("#choosePositionDiv").is(":hidden")) {
		    $("#choosePositionDiv").fadeIn();
			e?e.stopPropagation():event.cancelBubble = true;
		}
	});
    $("#showdiv2").click(function(e) {
		if ($("#choosePositionDiv").is(":hidden")) {
		    $("#choosePositionDiv").fadeIn();
			e?e.stopPropagation():event.cancelBubble = true;
		}
	});

    
	$("#choosePositionDiv").click(function(e) {
		e?e.stopPropagation():event.cancelBubble = true;
	});
	$(document).click(function() {
		$("#choosePositionDiv").fadeOut();
	});
})
//store tree
var positionTree;
// elementName：控制treeDiv位置的元素,显示选中节点名称的元素；elementId：保存选中节点id的元素；
//changDept：显示部门名称,changDeptId  保存部门id
function TreeNode(id, parentId, name, type, status){
	this.id = id;
	this.parentId = parentId;
	this.name = name;
	this.type = type;
	this.status = status;
}
function showPostionTree(elementName, elementId, changDept, changDeptId) {
	document.getElementById("positionTreeDiv").innerHTML ="";
	var initElement = document.getElementById(elementName);
	var	winX=hrm.common.getPoint(initElement).x;	
	var winY=hrm.common.getPoint(initElement).y;
	var treeDiv = document.getElementById("choosePositionDiv");
	treeDiv.style.top=winY+20;
	treeDiv.style.left=winX;
	// treeDiv.style.display="block";	

	// 获取所有启用的部门及其id；
	OrgManageAction.getPositionTreeList(showTreeDivCallback);
	function showTreeDivCallback(data){
	    treeNodeArray=data;
		var nodeArray = new Array();
		for(var i=0; data!=null && i<data.length; i++){
			nodeArray[i] = new Array(data[i].nodeId, data[i].nodeParentId, data[i].nodeName);
		}

		// 页面缓存数据；
		var nodeArr = {};
		for(var i=0; data!=null && i<data.length; i++){
		 	nodeArr[data[i].nodeId] = new TreeNode(data[i].nodeId, data[i].nodeParentId, data[i].nodeName, data[i].nodeType, data[i].nodeStatus);
	    }

		// 初始化tree，tree中显示的是所有启用状态的组织结构树；
		//组装参数；
		var chooseTreeParam = {
			"treeDiv"         : 'positionTreeDiv',
			"dataSource"      : nodeArray,
			"menuXml"         : null,
			"enableChkbox"    : false,
			"enableDrag"      : false     
		};
		
		// 子类初始化；
		ChoosePositionTree.elementName = elementName;
		ChoosePositionTree.elementId = elementId;
		ChoosePositionTree.changDept = changDept;
		ChoosePositionTree.changDeptId = changDeptId;
		ChoosePositionTree.divId = "choosePositionDiv";
		var positionTreeObj = new ChoosePositionTree(chooseTreeParam);
		positionTreeObj.init();// 初始化树形结构；
		positionTree = positionTreeObj.tree;
		positionTree.closeAllItems('rootid');
		positionTree.openItem('rootid');

		//设置图片
		positionTreeObj.setNodesImage(nodeArr);
	}
}
</script>