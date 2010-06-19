<#--
/*	选择员工标签。首先根据用户传进来的condition来得到相应的用户组，再通过左右框进行选择。
 *  选择组织单元的标签。首先弹出一个div，通过dwr获得数据，由dhtmlxtree生成树，用户选择一个节点
 *	通过员工、组织单元和地区来搜索符合condition和上述条件的员工（组）。
 *  标签变量，id 用来标示标签，当页面有多个选择组织单元的标签时，需唯一。
 *          name 显示选择节点的名字，且用于回显
 *          hiddenFieldName  用隐藏域来保存选择节点的id，以字符串形式返回，
 *                           格式为deptId,pbId,isGetAllSubDepts 其中isGetAllSubDepts 可以为0，或1，
 *                           如果选择的是部门，则为'';
 *          isShowDisable    是否显示停用的部门和职位（'show':显示，'hide':隐藏。缺省为隐藏）
 *			isShowPb		  是否显示职位（'show':显示，'hide':隐藏。缺省为显示）
 *			condition			 符合什么条件的员工
 *  hiddenField的id为"empselector_returnString_"+id
 *  name 文本框id为"empselector_orgName_"+id
 *  author chenhaibin
 */
-->
<#include "/" + parameters.templateDir + "/xhtml/controlheader.ftl">
<link rel="stylesheet" type="text/css" href="../resource/css/edit_select.css" />
<div>
<table>
	<tr>
		<td align="right">员工:</td>
		<td>
			<input id="empSelector_emp_sear_value" name="empSelector_emp_sear_value" size="14" maxlength="32"/>
		</td>
		<td align="right">组织单元:</td>
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
			<input type="button" id="empSelector_searButton" name="empSelector_searButton" value="查询" onclick="empSelector_searchEmp();"/>
		</td>
	</tr>
	<tr>
		<td align="right">地区:</td>
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
		<img src="../resource/images/toRightAll.gif" alt="全部选择" onclick="empSelector_moveall(document.getElementById('empSelector_leftList'),document.getElementById('empSelector_rightList'));"/>
	<br/><br/>
		<img src="../resource/images/toRight.gif" alt="选择" onclick="empSelector_move(document.getElementById('empSelector_leftList'),document.getElementById('empSelector_rightList'));"/>
	<br/><br/>
		<img src="../resource/images/toLeft.gif" alt="删除" onclick="empSelector_move(document.getElementById('empSelector_rightList'),document.getElementById('empSelector_leftList'));"/>
	<br/><br/>
		<img src="../resource/images/toLeftAll.gif" alt="全部删除" onclick="empSelector_moveall(document.getElementById('empSelector_rightList'),document.getElementById('empSelector_leftList'));"/>
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
	  		    <input class="checkbox" type="checkbox" id="isGetAllSubDepts${parameters.id}" value="1" checked="true"/>&nbsp;包含所有子部门
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
	// 获取所有启用的部门及其id；
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
//往左侧添加一个员工选项
function empSelector_addOneToSelect(select,text,value){
	var no = new Option();
	no.value = value;
	no.text = text;
	select.options[select.options.length] = no;
}
//判断两侧是否已存在该员工
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

//选择的员工Id字符串
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