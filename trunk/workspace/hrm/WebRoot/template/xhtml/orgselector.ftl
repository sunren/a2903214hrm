<#--
/*
 *  选择组织单元的标签。首先弹出一个div，通过dwr获得数据，由dhtmlxtree生成树，用户选择一个节点
 *  标签变量，id 用来标示标签，当页面有多个选择组织单元的标签时，需唯一。
 *          name 显示选择节点的名字，且用于回显
 *          hiddenFieldName  用隐藏域来保存选择节点的id，以字符串形式返回，
 *                           格式为deptId,pbId,isGetAllSubDepts 其中isGetAllSubDepts 可以为0，或1，
 *                           如果选择的是部门，则为'';
 *          isShowDisable    是否显示停用的部门和职位（'show':显示，'hide':隐藏。缺省为隐藏）
 *			isShowPb		  是否显示职位（'show':显示，'hide':隐藏。缺省为显示）
 *  hiddenField的id为"orgselector_returnString_"+id
 *  name 文本框id为"orgselector_orgName_"+id
 *  author tao.J
 */
-->
<#include "/" + parameters.templateDir + "/xhtml/controlheader.ftl">
<link rel="stylesheet" type="text/css" href="../resource/css/edit_select.css" />
<div>
<table cellpadding="0" cellspacing="0" class="select">
<tr><td bgcolor="#FFFFFF">
<input type="hidden" id='orgselector_returnString_${parameters.id}' 
name="${parameters.hiddenFieldName}" 
value="<@s.property value="parameters.hiddenFieldNameValue"/>" />
<input type="text"
 id='orgselector_orgName_${parameters.id}' 
 name="${parameters.name}"
 value="<@s.property value="parameters.nameValue"/>" class='selecttext' readonly size="14" />
<button type="button" class="selectbutton" style="CURSOR: pointer" 
id="orgselector_button_${parameters.id}" 
onclick="showChooseOrgDiv${parameters.id}('orgselector_orgName_${parameters.id}', 'orgselector_orgName_${parameters.id}', 
			'orgselector_returnString_${parameters.id}','${parameters.isShowDisable}','${parameters.isShowPb}');"/>&nbsp;
</td></tr>
</table>
</div>
<script type="text/javascript" src="../dwr/interface/DWROrgSelector.js"></script>
<script type="text/javascript" src="../resource/js/profile/DHTreeClass.js"></script>
<script type="text/javascript" src="../resource/js/profile/ChooseOrgTreeForSearch.js"></script>
<div id="chooseOrgDiv${parameters.id}" style="position:absolute;z-index:30000;top:100px;left:100;solid; width:240px;display:none;" class="prompt_div_inline">	
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	  	<tr>
	  		<td>
	  			<div id="orgTreeDiv${parameters.id}" align="left" style="overflow:auto; border:1px #dddddd solid;height:200px ;background-color: #ECF3F6;"></div>
	  		</td>
	  	</tr>
	  	<tr>
	  	    <td>
	  		    <input class="checkbox" type="checkbox" id="isGetAllSubDepts${parameters.id}" value="1" checked="true"/>&nbsp;包含所有子部门
	  		</td>
	  	</tr>
	</table>
</div>
<script type="text/javascript">
$(function() {
    $("#orgselector_button_${parameters.id}").click(function(e) {
		if ($("#chooseOrgDiv${parameters.id}").is(":hidden")) {
		    $("#chooseOrgDiv${parameters.id}").fadeIn();
			e?e.stopPropagation():event.cancelBubble = true;
		}
	});
	$("#chooseOrgDiv${parameters.id}").click(function(e) {
		e?e.stopPropagation():event.cancelBubble = true;
	});
	$(document).click(function() {
			$("#chooseOrgDiv${parameters.id}").fadeOut();
	});
})
// 显示dept树型结构；
// positionEle：控制treeDiv位置的元素；elementName：显示选中部门名称的元素；elementId：保存returnString的元素；
// isShowDisable 是否显示停用的部门和职位（'show':显示，'hide':隐藏。缺省为隐藏）
// isShowPb 是否显示职位（'show':显示，'hide':隐藏。缺省为显示）
function showChooseOrgDiv${parameters.id}(positionEle, elementName, elementId, isShowDisable, isShowPb) {
	document.getElementById("orgTreeDiv${parameters.id}").innerHTML = "";
	var initElement = document.getElementById(positionEle);
	var	winX=hrm.common.getRelativePoint(initElement).x;
	var winY=hrm.common.getRelativePoint(initElement).y;
	var treeDiv = document.getElementById("chooseOrgDiv${parameters.id}");
	treeDiv.style.top=winY;
	treeDiv.style.left=winX;
	// 获取所有启用的部门及其id
	DWROrgSelector.getOrgTreeList(isShowDisable, isShowPb, showTreeDivCallback);
	function showTreeDivCallback(data){
	    orgTreeNodeArray=data;
		var chooseTreeArray = new Array();
		for(var i=0; data!=null && i<data.length; i++){
			chooseTreeArray[i] = new Array(data[i].nodeId, data[i].nodeParentId, data[i].nodeName);
		}

		// 初始化tree，tree中显示的是所有启用状态的组织结构树；
		//组装参数；
		var chooseTreeParam = {
			"treeDiv"         : 'orgTreeDiv${parameters.id}',
			"dataSource"      : chooseTreeArray,
			"menuXml"         : null,
			"enableChkbox"    : false,
			"enableDrag"      : false     
		};
		
		// 子类初始化；
		ChooseOrgTree.elementName = elementName;
		ChooseOrgTree.elementId = elementId;
		ChooseOrgTree.divId = "chooseOrgDiv${parameters.id}";
		ChooseOrgTree.isGetAllSubDepts="isGetAllSubDepts${parameters.id}";
		var chooseOrgTreeObj = new ChooseOrgTree(chooseTreeParam);
		chooseOrgTreeObj.init();// 初始化树形结构；
		var chooseOrgTree = chooseOrgTreeObj.tree;
		chooseOrgTree.closeAllItems('rootid');
		chooseOrgTree.openItem('rootid');
		chooseOrgTree.setItemImage2('rootid', 'org_company.png','org_company.png', 'org_company.png');
		//设置图片
        for(var i=0; data!=null && i<data.length; i++){
           if(data[i].nodeType==1){
			  chooseOrgTree.setItemImage2(data[i].nodeId, 'org_company.png','org_company.png', 'org_company.png');
		   }else if(data[i].nodeType==3){
		      chooseOrgTree.setItemImage2(data[i].nodeId,'org_pb.png','org_pb.png', 'org_pb.png');
		   }else if(data[i].nodeType==4){
		      chooseOrgTree.setItemImage2(data[i].nodeId, 'org_pb_vacancy.png','org_pb_vacancy.png', 'org_pb_vacancy.png');
		   }
		}
	}
}

</script>
<#include "/" + parameters.templateDir + "/xhtml/controlfooter.ftl">