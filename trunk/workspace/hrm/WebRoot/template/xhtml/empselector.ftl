<#--
/*	ѡ��Ա����ǩ�����ȸ����û���������condition���õ���Ӧ���û��飬��ͨ�����ҿ����ѡ��
 *  ѡ����֯��Ԫ�ı�ǩ�����ȵ���һ��div��ͨ��dwr������ݣ���dhtmlxtree���������û�ѡ��һ���ڵ�
 *	ͨ��Ա������֯��Ԫ�͵�������������condition������������Ա�����飩��
 *  ��ǩ������id ������ʾ��ǩ����ҳ���ж��ѡ����֯��Ԫ�ı�ǩʱ����Ψһ��
 *          name ��ʾѡ��ڵ�����֣������ڻ���
 *          hiddenFieldName  ��������������ѡ��ڵ��id�����ַ�����ʽ���أ�
 *                           ��ʽΪdeptId,pbId,isGetAllSubDepts ����isGetAllSubDepts ����Ϊ0����1��
 *                           ���ѡ����ǲ��ţ���Ϊ'';
 *          isShowDisable    �Ƿ���ʾͣ�õĲ��ź�ְλ��'show':��ʾ��'hide':���ء�ȱʡΪ���أ�
 *			isShowPb		  �Ƿ���ʾְλ��'show':��ʾ��'hide':���ء�ȱʡΪ��ʾ��
 *			condition			 ����ʲô������Ա��
 *  hiddenField��idΪ"empselector_returnString_"+id
 *  name �ı���idΪ"empselector_orgName_"+id
 *  author chenhaibin
 */
-->
<#include "/" + parameters.templateDir + "/xhtml/controlheader.ftl">
<link rel="stylesheet" type="text/css" href="../resource/css/edit_select.css" />
<div>
<table>
	<tr>
		<td align="right">Ա��:</td>
		<td>
			<input id="empSelector_emp_sear_value" name="empSelector_emp_sear_value" size="14" maxlength="32"/>
		</td>
		<td align="right">��֯��Ԫ:</td>
		<td>
			<table cellpadding=0 cellspacing=0 class=select>
			<tr><td bgcolor=#FFFFFF>
			<input type="hidden" id='empSelector_returnString_${parameters.id}' 
			name="${parameters.hiddenFieldName}" 
			value="<@s.property value="parameters.hiddenFieldNameValue"/>" />
			<input type="text"
			 id='empSelector_orgName_${parameters.id}' 
			 name="${parameters.name}"
			 value="<@s.property value="parameters.nameValue"/>" class='selecttext' readonly size="14" />
			<button type="button" class="selectbutton" style="CURSOR: pointer" 
			id="empSelector_button_${parameters.id}" 
			onclick="showChooseOrgDiv${parameters.id}('empSelector_orgName_${parameters.id}', 'empSelector_orgName_${parameters.id}', 'empSelector_returnString_${parameters.id}','${parameters.isShowDisable}','${parameters.isShowPb}');"/>&nbsp;
			</td></tr>
			</table>
		</td>
		<td rowspan="2" align="center">
			<input type="button" id="empSelector_searButton" name="empSelector_searButton" value="��ѯ" onclick="empSelector_searchEmp();"/>
		</td>
	</tr>
	<tr>
		<td align="right">����:</td>
	    <td>${parameters.localSelect}</td>
	    <td>&nbsp;</td>
	    <td>&nbsp;</td>
	</tr>
</table>
<table border="0">
	<tr>
	<td>
	<select multiple size="7" id="empSelector_leftList" style="width:200px;height:150px;" ondblclick="empSelector_move(document.getElementById('empSelector_leftList'),document.getElementById('empSelector_rightList'));">
	</select>
	</td>
	<td>
		<img src="../resource/images/toRightAll.gif" alt="ȫ��ѡ��" onclick="empSelector_moveall(document.getElementById('empSelector_leftList'),document.getElementById('empSelector_rightList'));"/>
	<br/><br/>
		<img src="../resource/images/toRight.gif" alt="ѡ��" onclick="empSelector_move(document.getElementById('empSelector_leftList'),document.getElementById('empSelector_rightList'));"/>
	<br/><br/>
		<img src="../resource/images/toLeft.gif" alt="ɾ��" onclick="empSelector_move(document.getElementById('empSelector_rightList'),document.getElementById('empSelector_leftList'));"/>
	<br/><br/>
		<img src="../resource/images/toLeftAll.gif" alt="ȫ��ɾ��" onclick="empSelector_moveall(document.getElementById('empSelector_rightList'),document.getElementById('empSelector_leftList'));"/>
	</td>
	<td>
	<select multiple size="7" id="empSelector_rightList" style="width:200px;height:150px;" ondblclick="empSelector_move(document.getElementById('empSelector_rightList'),document.getElementById('empSelector_leftList'));">
	</select>
	</td>
	</tr>
</table>
</div>
<script type="text/javascript" src="../dwr/interface/DWROrgSelector.js"></script>
<script type="text/javascript" src="../resource/js/profile/DHTreeClass.js"></script>
<script type="text/javascript" src="../resource/js/profile/ChooseOrgTreeForSearch.js"></script>
<script type='text/javascript' src='../dwr/interface/DwrForSearchEmp.js'></script>
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
    $("#empSelector_button_${parameters.id}").click(function(e) {
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
	// ��ȡ�������õĲ��ż���id��
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


function empSelector_move(fbox,tbox) {
	for(var i=0; i<fbox.options.length; i++) {
		if(fbox.options[i].selected && fbox.options[i].value != "") {
			 empSelector_addOneToSelect(tbox,fbox.options[i].text,fbox.options[i].value);
			 fbox.removeChild(fbox.options[i]);
			 i--;
		}
   }
}
function empSelector_moveall(fbox,tbox) {
	for(var i=0; i<fbox.options.length; i++) {
		if(fbox.options[i].value != "") {
			 empSelector_addOneToSelect(tbox,fbox.options[i].text,fbox.options[i].value);
			 fbox.removeChild(fbox.options[i]);
			 i--;
		}
	}
}
//��������һ��Ա��ѡ��
function empSelector_addOneToSelect(select,text,value){
	var no = new Option();
	no.value = value;
	no.text = text;
	select.options[select.options.length] = no;
}
//�ж������Ƿ��Ѵ��ڸ�Ա��
function empSelector_selected(value){
	var leftList = document.getElementById('empSelector_leftList');
	for(i=0;i<leftList.length;i++){
		if(leftList.options[i].value==value){
			return true;
		}
	}
	var rightList = document.getElementById('empSelector_rightList');
	for(i=0;i<rightList.length;i++){
		if(rightList.options[i].value==value){
			return true;
		}
	}
	return false;
}

//ѡ���Ա��Id�ַ���
function empSelector_choseEmpIdStr(){
	var rightList = document.getElementById('empSelector_rightList');
	var empIdStr = "";
	for(i=0;i<rightList.length;i++){ 
		empIdStr +=rightList.options[i].value +";";   
	}   
	if(empIdStr!="") empIdStr=empIdStr.substring(0,empIdStr.length - 1);
	return empIdStr.trim();
}
function empSelector_clearLeftEmp(){
	var leftList = document.getElementById('empSelector_leftList');
	for(i=0;i<leftList.length;i++){
		leftList.removeChild(leftList.options[i]);
		i--;
	}
}
function empSelector_searchEmp(){
	var empStr = document.getElementById('empSelector_emp_sear_value').value;
	var deptStr = document.getElementById('empSelector_returnString_${parameters.id}').value;
	var loc = document.getElementById('empSelector_location').value;
    var leftSelect = document.getElementById('empSelector_leftList');
    empSelector_clearLeftEmp();
    var condition = "${parameters.condition}";
    var conArray = condition.split('&');
    if(conArray.length > 1) {
    	condition = conArray[0]+"&"+document.getElementById(conArray[1]).value;
    }
	DwrForSearchEmp.searchEmp(empStr,deptStr,loc,condition,callback);
	function callback(data) {
		if(data.trim() != '') {
			var empList = data.split("&");
			for(var i=0;i<empList.length;i++) {
				var emp = empList[i].split(";");
				if(!empSelector_selected(emp[2])){
					empSelector_addOneToSelect(leftSelect, emp[1]+" " +emp[0], emp[2]);
				}
			}
		}
	}
}
</script>
<#include "/" + parameters.templateDir + "/xhtml/controlfooter.ftl">