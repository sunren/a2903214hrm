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
// elementName������treeDivλ�õ�Ԫ��,��ʾѡ�нڵ����Ƶ�Ԫ�أ�elementId������ѡ�нڵ�id��Ԫ�أ�
//changDept����ʾ��������,changDeptId  ���沿��id
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

	// ��ȡ�������õĲ��ż���id��
	OrgManageAction.getPositionTreeList(showTreeDivCallback);
	function showTreeDivCallback(data){
	    treeNodeArray=data;
		var nodeArray = new Array();
		for(var i=0; data!=null && i<data.length; i++){
			nodeArray[i] = new Array(data[i].nodeId, data[i].nodeParentId, data[i].nodeName);
		}

		// ҳ�滺�����ݣ�
		var nodeArr = {};
		for(var i=0; data!=null && i<data.length; i++){
		 	nodeArr[data[i].nodeId] = new TreeNode(data[i].nodeId, data[i].nodeParentId, data[i].nodeName, data[i].nodeType, data[i].nodeStatus);
	    }

		// ��ʼ��tree��tree����ʾ������������״̬����֯�ṹ����
		//��װ������
		var chooseTreeParam = {
			"treeDiv"         : 'positionTreeDiv',
			"dataSource"      : nodeArray,
			"menuXml"         : null,
			"enableChkbox"    : false,
			"enableDrag"      : false     
		};
		
		// �����ʼ����
		ChoosePositionTree.elementName = elementName;
		ChoosePositionTree.elementId = elementId;
		ChoosePositionTree.changDept = changDept;
		ChoosePositionTree.changDeptId = changDeptId;
		ChoosePositionTree.divId = "choosePositionDiv";
		var positionTreeObj = new ChoosePositionTree(chooseTreeParam);
		positionTreeObj.init();// ��ʼ�����νṹ��
		positionTree = positionTreeObj.tree;
		positionTree.closeAllItems('rootid');
		positionTree.openItem('rootid');

		//����ͼƬ
		positionTreeObj.setNodesImage(nodeArr);
	}
}
</script>