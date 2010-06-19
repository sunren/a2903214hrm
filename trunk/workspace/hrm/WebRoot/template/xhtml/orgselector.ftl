<#--
/*
 *  ѡ����֯��Ԫ�ı�ǩ�����ȵ���һ��div��ͨ��dwr������ݣ���dhtmlxtree���������û�ѡ��һ���ڵ�
 *  ��ǩ������id ������ʾ��ǩ����ҳ���ж��ѡ����֯��Ԫ�ı�ǩʱ����Ψһ��
 *          name ��ʾѡ��ڵ�����֣������ڻ���
 *          hiddenFieldName  ��������������ѡ��ڵ��id�����ַ�����ʽ���أ�
 *                           ��ʽΪdeptId,pbId,isGetAllSubDepts ����isGetAllSubDepts ����Ϊ0����1��
 *                           ���ѡ����ǲ��ţ���Ϊ'';
 *          isShowDisable    �Ƿ���ʾͣ�õĲ��ź�ְλ��'show':��ʾ��'hide':���ء�ȱʡΪ���أ�
 *			isShowPb		  �Ƿ���ʾְλ��'show':��ʾ��'hide':���ء�ȱʡΪ��ʾ��
 *  hiddenField��idΪ"orgselector_returnString_"+id
 *  name �ı���idΪ"orgselector_orgName_"+id
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
	  		    <input class="checkbox" type="checkbox" id="isGetAllSubDepts${parameters.id}" value="1" checked="true"/>&nbsp;���������Ӳ���
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
// ��ʾdept���ͽṹ��
// positionEle������treeDivλ�õ�Ԫ�أ�elementName����ʾѡ�в������Ƶ�Ԫ�أ�elementId������returnString��Ԫ�أ�
// isShowDisable �Ƿ���ʾͣ�õĲ��ź�ְλ��'show':��ʾ��'hide':���ء�ȱʡΪ���أ�
// isShowPb �Ƿ���ʾְλ��'show':��ʾ��'hide':���ء�ȱʡΪ��ʾ��
function showChooseOrgDiv${parameters.id}(positionEle, elementName, elementId, isShowDisable, isShowPb) {
	document.getElementById("orgTreeDiv${parameters.id}").innerHTML = "";
	var initElement = document.getElementById(positionEle);
	var	winX=hrm.common.getRelativePoint(initElement).x;
	var winY=hrm.common.getRelativePoint(initElement).y;
	var treeDiv = document.getElementById("chooseOrgDiv${parameters.id}");
	treeDiv.style.top=winY;
	treeDiv.style.left=winX;
	// ��ȡ�������õĲ��ż���id
	DWROrgSelector.getOrgTreeList(isShowDisable, isShowPb, showTreeDivCallback);
	function showTreeDivCallback(data){
	    orgTreeNodeArray=data;
		var chooseTreeArray = new Array();
		for(var i=0; data!=null && i<data.length; i++){
			chooseTreeArray[i] = new Array(data[i].nodeId, data[i].nodeParentId, data[i].nodeName);
		}

		// ��ʼ��tree��tree����ʾ������������״̬����֯�ṹ����
		//��װ������
		var chooseTreeParam = {
			"treeDiv"         : 'orgTreeDiv${parameters.id}',
			"dataSource"      : chooseTreeArray,
			"menuXml"         : null,
			"enableChkbox"    : false,
			"enableDrag"      : false     
		};
		
		// �����ʼ����
		ChooseOrgTree.elementName = elementName;
		ChooseOrgTree.elementId = elementId;
		ChooseOrgTree.divId = "chooseOrgDiv${parameters.id}";
		ChooseOrgTree.isGetAllSubDepts="isGetAllSubDepts${parameters.id}";
		var chooseOrgTreeObj = new ChooseOrgTree(chooseTreeParam);
		chooseOrgTreeObj.init();// ��ʼ�����νṹ��
		var chooseOrgTree = chooseOrgTreeObj.tree;
		chooseOrgTree.closeAllItems('rootid');
		chooseOrgTree.openItem('rootid');
		chooseOrgTree.setItemImage2('rootid', 'org_company.png','org_company.png', 'org_company.png');
		//����ͼƬ
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