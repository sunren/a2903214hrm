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
						    <td colspan="5" align="left" >ѡ��Ŀ�Ľڵ�</td>
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
// element������treeDivλ�õ�Ԫ��,��ʾѡ�нڵ����Ƶ�Ԫ��
function showPostionTree(posId,element) {
    
	document.getElementById("changeSupTreeDiv").innerHTML ="";
	var initElement = document.getElementById(element);
	var	winX=hrm.common.getPoint(initElement).x;	
	var winY=hrm.common.getPoint(initElement).y;
	var treeDiv = document.getElementById("changeSupPosDiv");
	treeDiv.style.top=winY+20;
	treeDiv.style.left=winX;
	treeDiv.style.display="block";	

	// ��ȡ�������õĲ��ż���id��
	OrgManageAction.changeSupPos(posId,showTreeDivCallback);
	function showTreeDivCallback(data){
	    treeNodeArray=data;
		var nodeArray = new Array();
		var rootId;
		for(var i=0; data!=null && i<data.length; i++){
			nodeArray[i] = new Array(data[i].nodeId, data[i].nodeParentId, data[i].nodeName);
			if(data[i].nodeParentId == null) rootId = data[i].nodeId;
		}

		// ��ʼ��tree��tree����ʾ������������״̬����֯�ṹ����
		//��װ������
		var chooseTreeParam = {
			"treeDiv"         : 'changeSupTreeDiv',
			"dataSource"      : nodeArray,
			"menuXml"         : null,
			"enableChkbox"    : false,
			"enableDrag"      : false,
			"rootId"          : rootId
		};
		
		// �����ʼ����
		ChoosePositionTree.divId = "changeSupPosDiv";
		ChoosePositionTree.posId=posId;
		var positionTreeObj = new ChoosePositionTree(chooseTreeParam);
		positionTreeObj.init();// ��ʼ�����νṹ��
		positionTreeInDiv = positionTreeObj.tree;
		
		//����ͼƬ
        for(var i=0; data!=null && i<data.length; i++){
			positionTreeInDiv.setItemImage2(data[i].nodeId,'member_face.gif','member_face.gif', 'member_face.gif');
		}
	}
}
</script>