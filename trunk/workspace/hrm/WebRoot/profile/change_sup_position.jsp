<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<script type="text/javascript" src="../dwr/interface/OrgManageAction.js"></script>
<script type="text/javascript" src="../resource/js/profile/DHTreeClass.js"></script>
<script type="text/javascript" src="../resource/js/profile/ChangeSupPosition.js"></script>

<div id="changeSupPosDiv" style="position:absolute;z-index:10000;width:210px;display:none;" class="prompt_div_inline">	
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	  	<tr>
	  	   <td>
	  	 		<div id="titleBar" style="CURSOR: move;"  class="prompt_div_head">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
	  	 				<TR>
						    <td colspan="5" align="left" >选择目的节点</td>
						    <td align="right" valign="middle"><img src="../resource/images/close_div.gif" onclick="document.getElementById('changeSupPosDiv').style.display='none';" style="CURSOR: pointer"/></td>
						 </TR>
					</table>
				</div>	
	  		</td>
	  	</tr>
	  	<tr>
	  		<td>
	  			<div id="changeSupTreeDiv" align="left" style="overflow:auto; border:1px #dddddd solid;height:150px ;background-color: #ECF3F6;"></div>
	  		</td>
	  	</tr>
	</table>
	
</div>
<script type="text/javascript" >
//store tree
var positionTreeInDiv;
// element：控制treeDiv位置的元素,显示选中节点名称的元素
function showPostionTree(posId,element) {
    
	document.getElementById("changeSupTreeDiv").innerHTML ="";
	var initElement = document.getElementById(element);
	var	winX=hrm.common.getPoint(initElement).x;	
	var winY=hrm.common.getPoint(initElement).y;
	var treeDiv = document.getElementById("changeSupPosDiv");
	treeDiv.style.top=winY+20;
	treeDiv.style.left=winX;
	treeDiv.style.display="block";	

	// 获取所有启用的部门及其id；
	OrgManageAction.changeSupPos(posId,showTreeDivCallback);
	function showTreeDivCallback(data){
	    treeNodeArray=data;
		var nodeArray = new Array();
		var rootId;
		for(var i=0; data!=null && i<data.length; i++){
			nodeArray[i] = new Array(data[i].nodeId, data[i].nodeParentId, data[i].nodeName);
			if(data[i].nodeParentId == null) rootId = data[i].nodeId;
		}

		// 初始化tree，tree中显示的是所有启用状态的组织结构树；
		//组装参数；
		var chooseTreeParam = {
			"treeDiv"         : 'changeSupTreeDiv',
			"dataSource"      : nodeArray,
			"menuXml"         : null,
			"enableChkbox"    : false,
			"enableDrag"      : false,
			"rootId"          : rootId
		};
		
		// 子类初始化；
		ChoosePositionTree.divId = "changeSupPosDiv";
		ChoosePositionTree.posId=posId;
		var positionTreeObj = new ChoosePositionTree(chooseTreeParam);
		positionTreeObj.init();// 初始化树形结构；
		positionTreeInDiv = positionTreeObj.tree;
		
		//设置图片
        for(var i=0; data!=null && i<data.length; i++){
			positionTreeInDiv.setItemImage2(data[i].nodeId,'member_face.gif','member_face.gif', 'member_face.gif');
		}
	}
}
</script>