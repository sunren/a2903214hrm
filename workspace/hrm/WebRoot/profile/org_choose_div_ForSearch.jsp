<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<script type="text/javascript" src="../dwr/interface/OrgManageAction.js"></script>
<script type="text/javascript" src="../resource/js/profile/DHTreeClass.js"></script>
<script type="text/javascript" src="../resource/js/profile/ChooseOrgTreeForSearch.js"></script>

<div id="chooseOrgDiv" style="position:absolute;z-index:10000;top:100px;left:100;solid; width:240px;display:none;" class="prompt_div_inline">	
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	  	<tr>
	  		<td>
	  			<div id="orgTreeDiv" align="left" style="overflow:auto; border:1px #dddddd solid;height:200px ;background-color: #ECF3F6;"></div>
	  		</td>
	  	</tr>
	  	<tr>
	  	    <td>
	  		    �Ƿ�����������Ӳ���:&nbsp;<input type="checkbox" id="isGetAllSubDepts"/>
	  		</td>
	  	</tr>
	</table>
</div>
<script type="text/javascript">
$(function() {
    $("#showOrgDiv").click(function(e) {
		if ($("#chooseOrgDiv").is(":hidden")) {
		    $("#chooseOrgDiv").fadeIn();
			e?e.stopPropagation():event.cancelBubble = true;
		}
	});
    $("#showOrgDivA").click(function(e) {
		if ($("#chooseOrgDiv").is(":hidden")) {
		    $("#chooseOrgDiv").fadeIn();
			e?e.stopPropagation():event.cancelBubble = true;
		}
	});
	
	$("#chooseOrgDiv").click(function(e) {
		e?e.stopPropagation():event.cancelBubble = true;
	});
	$(document).click(function() {
		$("#chooseOrgDiv").fadeOut();
	});
})
// ��ʾdept���ͽṹ��
// positionEle������treeDivλ�õ�Ԫ�أ�elementName����ʾѡ�нڵ����Ƶ�Ԫ�أ�elementId������ѡ�нڵ�id��Ԫ�أ�
// pbSelect ѡ��ְλ�������id
function showChooseOrgDiv(positionEle, elementName, elementId,pbSelect) {	
	document.getElementById("orgTreeDiv").innerHTML = "";
	var initElement = document.getElementById(positionEle);
	var	winX=hrm.common.getPoint(initElement).x;	
	var winY=hrm.common.getPoint(initElement).y;
	var treeDiv = document.getElementById("chooseOrgDiv");
	treeDiv.style.top=winY+20;
	treeDiv.style.left=winX;
	// ��ȡ�������õĲ��ż���id��
	OrgManageAction.getAllEnabledDept(showTreeDivCallback);
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
		ChooseOrgTree.elementName = elementName;
		ChooseOrgTree.elementId = elementId;
		ChooseOrgTree.pbSelect = pbSelect;
		ChooseOrgTree.divId = "chooseOrgDiv";
		var chooseOrgTreeObj = new ChooseOrgTree(chooseTreeParam);
		
		chooseOrgTreeObj.init();// ��ʼ�����νṹ��
		var chooseOrgTree = chooseOrgTreeObj.tree;
		chooseOrgTree.closeAllItems('rootid');
		chooseOrgTree.openItem('rootid');
	}
}

</script>