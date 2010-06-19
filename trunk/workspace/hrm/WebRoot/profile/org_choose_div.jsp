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
						    <td colspan="5" align="left" >ѡ��Ŀ�Ľڵ�</td>
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
// ��ʾdept���ͽṹ��
// fromType:0-��ʾ��������3-��ʾְλ�ڵ㣻nodeId:null-��ʾ��������value-��ʾְλ����
// positionEle������treeDivλ�õ�Ԫ�أ�elementName����ʾѡ�нڵ����Ƶ�Ԫ�أ�elementId������ѡ�нڵ�id��Ԫ�أ�
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

	// ��ȡ�������õĲ��ż���id��
	OrgDeptManageAction.getAllEnabledDept(fromType, nodeId, showTreeDivCallback);
	function showTreeDivCallback(data){
		var chooseTreeArray = new Array();
		for(var i=0; data!=null && i<data.length; i++){
			chooseTreeArray[i] = new Array(data[i].nodeId, data[i].nodeParentId, data[i].nodeName);
		}

		// ��ʼ��tree��tree����ʾ������������״̬����֯�ṹ����
		//��װ������
		var chooseTreeParam = {
			"treeDiv"         : 'orgTreeDiv',
			"dataSource"      : chooseTreeArray,
			"menuXml"         : null,
			"enableChkbox"    : false,
			"enableDrag"      : false     
		};
		
		// �����ʼ����
		DivChooseOrgTree.elementName = elementName;
		DivChooseOrgTree.elementId = elementId;
		DivChooseOrgTree.divId = "chooseOrgDiv";
		var divChooseOrgTreeObj = new DivChooseOrgTree(chooseTreeParam);
		
		divChooseOrgTreeObj.init();// ��ʼ�����νṹ��
		var divChooseOrgTree = divChooseOrgTreeObj.tree;
	}
}


//���ඨ�壻
var DivChooseOrgTree = function(param){
	DHTreeClass.call(this, param);
};
DivChooseOrgTree.prototype = DHTreeClass.prototype;

//�����¼�
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