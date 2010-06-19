<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<script type="text/javascript" src="../dwr/interface/OrgManageAction.js"></script>
<script type="text/javascript" src="../resource/js/profile/DHTreeClass.js"></script>

<div id="chooseOrgDiv" style="position:absolute;z-index:10000;top:100px;left:100;solid; width:350px;display:none;" class="prompt_div_inline">	
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	  		<td>
	  	 		<div id="titleBar" style="CURSOR: move;" NOWRAP class="prompt_div_head">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
	  	 				<TR>
						    <td colspan="5" align="left" >选择目的节点</td>
						    <td align="right" valign="middle"><img src="../resource/images/close_div.gif" onclick="document.getElementById('chooseOrgDiv').style.display='none';" style="CURSOR: pointer"/></td>
						 </TR>
					</table>
				</div>	
	  		</td>
	  	<tr>
	  		<td>
	  			<div id="orgTreeDiv" align="left" style="overflow:auto; border:1px #dddddd solid;height:150px ;background-color: #ECF3F6;"></div>
	  		</td>
	  	</tr>
	</table>
	<iframe scrolling="no" style="position:absolute;z-index:-1;width:expression(this.parentNode.offsetWidth-2);height:expression(this.parentNode.offsetHeight);top:0px;left:0px;" frameborder="0" ></iframe>
</div>
<script type="text/javascript">
// 显示dept树型结构；
// fromType:0-显示部门树，3-显示职位节点；nodeId:null-显示部门树，value-显示职位树；
// positionEle：控制treeDiv位置的元素；elementName：显示选中节点名称的元素；elementId：保存选中节点id的元素；
function showChooseOrgDiv(fromType, nodeId, positionEle, elementName, elementId) {	
	document.getElementById("orgTreeDiv").innerHTML = "";
	var initElement = document.getElementById(positionEle);
	var	winX=hrm.common.getPoint(initElement).x;	
	var winY=hrm.common.getPoint(initElement).y;
	var treeDiv = document.getElementById("chooseOrgDiv");
	treeDiv.style.top=winY+20;
	treeDiv.style.left=winX;
	treeDiv.style.display="block";	
	// invoke_drag('titleBar','chooseOrgDiv');

	// 获取所有启用的部门及其id；
	OrgDeptManageAction.getAllEnabledDept(fromType, nodeId, showTreeDivCallback);
	function showTreeDivCallback(data){
		var chooseTreeArray = new Array();
		for(var i=0; data!=null && i<data.length; i++){
			chooseTreeArray[i] = new Array(data[i].nodeId, data[i].nodeParentId, data[i].nodeName);
		}

		// 初始化tree，tree中显示的是所有启用状态的组织结构树；
		//组装参数；
		var chooseTreeParam = {
			"treeDiv"         : 'orgTreeDiv',
			"dataSource"      : chooseTreeArray,
			"menuXml"         : null,
			"enableChkbox"    : false,
			"enableDrag"      : false     
		};
		
		// 子类初始化；
		DivChooseOrgTree.elementName = elementName;
		DivChooseOrgTree.elementId = elementId;
		DivChooseOrgTree.divId = "chooseOrgDiv";
		var divChooseOrgTreeObj = new DivChooseOrgTree(chooseTreeParam);
		
		divChooseOrgTreeObj.init();// 初始化树形结构；
		var divChooseOrgTree = divChooseOrgTreeObj.tree;
	}
}


//子类定义；
var DivChooseOrgTree = function(param){
	DHTreeClass.call(this, param);
};
DivChooseOrgTree.prototype = DHTreeClass.prototype;

//单击事件
DivChooseOrgTree.prototype.lclick = function(){
	var selectId = this.getSelectedItemId();
	var selectName = this.getSelectedItemText();
	document.getElementById(DivChooseOrgTree.elementName).value = selectName;
	document.getElementById(DivChooseOrgTree.elementId).value = selectId;
	document.getElementById(DivChooseOrgTree.divId).style.display = "none";
	
	var operate = document.getElementById("operate").value;
	if(operate == 'move') moveNode();
	if(operate == 'merge') mergeNode();
	if(operate == 'start') enableNode();
};
</script>