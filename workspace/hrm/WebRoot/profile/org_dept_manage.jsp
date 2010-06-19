<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>部门管理</title>
<script type="text/javascript" src="../dwr/interface/OrgManageAction.js"></script>
	<script type='text/javascript' src='../dwr/interface/OrgDeptManageAction.js'></script>
<script type="text/javascript" src="../resource/js/profile/DHTreeClass.js"></script>
</head>
<body onload="initData()">
<s:component template="bodyhead">
	<s:param name="pagetitle" value="'部门管理：'" />
	<s:param name="helpUrl" value="'32'" />
</s:component>
<s:hidden id="operate" name="operate" />
<!-- move param -->
<s:hidden id="move_chooseOrgId" name="move_chooseOrgId" />
<s:hidden id="move_chooseOrgName" name="move_chooseOrgName" />
<!-- merge param -->
<s:hidden id="merge_chooseOrgId" name="merge_chooseOrgId" />
<s:hidden id="merge_chooseOrgName" name="merge_chooseOrgName" />
<!-- merge param -->
<s:hidden id="start_chooseOrgId" name="start_chooseOrgId" />
<s:hidden id="start_chooseOrgName" name="start_chooseOrgName" />

<table border=0 cellpadding=0 cellspacing=0 width=100%>
	<tr>
		<td valign="top" >
			<div id="divDeptTree" style="width:230px;height:425px;background-color:#f5f5f5;border :1px solid Silver;overflow:auto;"></div>
		</td>
		<td valign="top" align="left" width="100%">
		    <table>
		    <tr nowrap>
		    	<td align="left" nowrap id="td_test">
		        </td>
		        <td align="left" nowrap id="td_rename">
		        	<input type="button" id="btn_rename"      value="更&nbsp;名"    onclick="renameInit();" style="cursor:hand;"/>
		        </td>
		        <td align="left" nowrap id="td_move">
		        	<input type="button" id="btn_move"        value="移&nbsp;动"    onclick="moveNodeInit();"  style="cursor:hand;"/>
		        </td>
		        <td align="left" nowrap id="td_merge">
		        	<input type="button" id="btn_merge"       value="合&nbsp;并"    onclick="mergeNodeInit();"  style="cursor:hand;"/>
		        </td>
		        <td align="left" nowrap id="td_stop">
		        	<input type="button" id="btn_stop"        value="停&nbsp;用"    onclick="disableItem();"  style="cursor:hand;"/>
		        </td>
		        <td align="left" nowrap id="td_start">
		        	<input type="button" id="btn_start"       value="启&nbsp;用"    onclick="enableItem();"  style="cursor:hand;"/>
		        </td>
		        <td align="left" nowrap id="td_delete">
					<input type="button" id="btn_delete"      value="删&nbsp;除"    onclick="deleteItem();"  style="cursor:hand;"/>
				</td>
				<td align="left" nowrap id="td_new_branch">
					<input type="button" id="btn_new_branch"  value="新建分公司"     onclick="newBranchInit();"  style="cursor:hand;"/>
				</td>
				<td align="left" nowrap id="td_new_dept">
					<input type="button" id="btn_new_dept"    value="新建部门"       onclick="newDeptInit();"  style="cursor:hand;"/>
				</td>
			</tr>
			</table>
			<div id="logarea" style="background-color:#f5f5f5;height:400px;width:100%;border :1px solid Silver;overflow:hidden;">
				<IFRAME ID="iframe" name="iframe" marginWidth="0" marginHeight="0" FRAMEBORDER="0" align="left"
	 		 	width="100%" height="400" src="" style="overflow-y:auto;"></IFRAME>
			</div>
		</td>
	</tr>
</table>

<!-- 更名dialog -->
<div id="dlgRename">
    <table width="100%">
        <tr>
            <td align="center">
            	<input type="text" size="20" id="newName" name="newName" value=""/>
            </td>
        </tr>
        <tr>
            <td align="center">
            	<input type="button" id="btn_rename" name="btn_rename" value="确定" onclick="rename()"/>
            	<input type="button" id="btn_rename_cancel" name="btn_rename_cancel" value="取消" onclick="hrm.common.closeDialog('dlgRename');"/>
            </td>
        </tr>
    </table>
</div>
<!-- 选择合并部门dialog -->
<div id="dlgSelectMergeDept" style="display: none;">
 <table width="100%">
        <tr >
           <td>
            <div>
            <table width="100%">
               <tr>
               <td >需合并部门</td>
               <td >并入到部门</td>
               </tr>
               
               <tr>
               <td><s:orgselector id="mergeDept1" name="" hiddenFieldName="" isShowPb="hide"/></td>
               <td rowspan="3"><s:orgselector id="mergeToDept" name="" hiddenFieldName="" isShowPb="hide"/></td>
               </tr>
               
               <tr>
               <td><s:orgselector id="mergeDept2" name="" hiddenFieldName="" isShowPb="hide"/></td>
               </tr>
               
               <tr>
               <td><s:orgselector id="mergeDept3" name="" hiddenFieldName="" isShowPb="hide"/></td>
               </tr>
               
            </table>
            </div>
            </td>
        </tr>
        <tr>
            <td align="center">
            	<input type="button" id="btn_selectMergeDept" value="确定" onclick="mergeNode()"/>
            	<input type="button"  value="取消" onclick="hrm.common.closeDialog('dlgSelectMergeDept');"/>
            </td>
        </tr>
    </table>
</div>
<!-- 合并节点操作中，起始部门下各个职位的操作 -->
<div id="dlgMergeFromPB">
    <table width="100%" id="mergePBList">
        <tr>
            <th align="center">职位名称</th>
            <th align="center">所属部门</th>
            <th align="center">取消负责</th>
            <th align="center">操作</th>
            <th align="center">并入职位</th>
        </tr>
    </table>
    <table width="100%">
   		<tr>
            <td align="center">
            	<input type="button" id="btn_rename" name="btn_merge_confirm" value="确定" onclick="mergePBOperate();"/>
            	<input type="button" id="btn_rename" name="btn_merge_cancel" value="取消" onclick="hrm.common.closeDialog('dlgMergeFromPB');"/>
            </td>
        </tr>
    </table>
</div>

<script type="text/javascript">
// 初始化dialog；
hrm.common.initDialog("moveItem", 300);
hrm.common.initDialog("dlgRename", 200);
hrm.common.initDialog("dlgMergeFromPB", 500);
hrm.common.initDialog("dlgSelectMergeDept", 473);
//子类定义；
var OrgDeptTree = function(param){
	DHTreeClass.call(this, param);
};
OrgDeptTree.prototype = DHTreeClass.prototype;

//单击事件；
OrgDeptTree.prototype.lclick = function(id){
	var status = getNodeStatus(id);
	var type = getNodeType(id);
	if(id == disableRootId) return;// 废弃部门根节点；
	var url = "goBranchDeptTabs.action?dept.id="+id+"&nodeType="+type;
	
	displayOrShowButton(type, status);
	if(url != null){
		refreshIFrame(url);
	}
};
// 向叶面缓存中添加数据；
OrgDeptTree.prototype.addToMemory = function(id, parentId, name, type, status){
	nodeArr[id] = new node(id, parentId, name, type, status);
	displayOrShowButton(type, status);

	// 设置图片；
	orgDeptTree.setNodesImage(nodeArr);
}

// 1.初始化节点数据，用于页面上抓取使用, type: 0:company, 1:branch, 2:dept, 3:position;
function node(id, parentId, name, type, status){
	this.id = id;
	this.parentId = parentId;
	this.name = name;
	this.type = type;
	this.status = status;
}
var nodeArr = {
	<s:iterator value="nodeList" status="index">
	    '<s:property value="nodeId"/>' : new node('<s:property value="nodeId"/>', '<s:property value="nodeParentId"/>', '<s:property value="nodeName"/>', '<s:property value="nodeType"/>', '<s:property value="nodeStatus"/>')
	    <s:if test="!#index.isLast()">,</s:if>
	</s:iterator>
};
//获取节点的状态;
function getNodeStatus(id){
	var obj = nodeArr[id];
	return obj.status;
}
//获取节点的类型;
function getNodeType(id){
	var obj = nodeArr[id];
	return obj.type;
}

// 2.初始化treeArray数据；
var treeArray = new Array(
	<s:iterator value="nodeList" status="index">
	    ['<s:property value="nodeId"/>', '<s:property value="nodeParentId"/>', '<s:property value="nodeName"/>']
	    <s:if test="!#index.isLast()">,</s:if>
	</s:iterator>
);

// 3.初始化tree；
var tree;
var rootId='<s:property value="rootId"/>';
var disableRootId = '<s:property value="disableRootId"/>';
var orgDeptTree;
function initData(){
	//组装参数；
	var param = {
		"treeDiv"      : 'divDeptTree',
		"dataSource"   : treeArray,
		"menuXml"      : null,
		"enableChkbox" : false,
		"enableDrag"   : false
	};
	// 子类初始化；
	orgDeptTree = new OrgDeptTree(param);
	orgDeptTree.init();// 初始化树形结构；
	tree = orgDeptTree.tree;
	// 设置图片；
	orgDeptTree.setNodesImage(nodeArr);
}

// 刷新右边iframe中的页面；
function refreshIFrame(url){
	var iframe=document.getElementById("iframe");
	if(hrm.common.navigatorIsFF()){
		iframe.contentWindow.document.location=url;
	}else{
		iframe.src=url;
	}
}

// 合并节点初始化；
function mergeNodeInit(){
   //initial value,/隐藏checkbox
   $("#"+"orgselector_returnString_"+"mergeDept1").val("");
   $("#"+"orgselector_orgName_"+"mergeDept1").val("");
   $("#"+"isGetAllSubDepts"+"mergeDept1").parent().parent().hide();
   
   $("#"+"orgselector_returnString_"+"mergeDept2").val("");
   $("#"+"orgselector_orgName_"+"mergeDept2").val("");
   $("#"+"isGetAllSubDepts"+"mergeDept2").parent().parent().hide();
   
   $("#"+"orgselector_returnString_"+"mergeDept3").val("");
   $("#"+"orgselector_orgName_"+"mergeDept3").val("");
   $("#"+"isGetAllSubDepts"+"mergeDept3").parent().parent().hide();
   
   $("#"+"orgselector_returnString_"+"mergeToDept").val("");
   $("#"+"orgselector_orgName_"+"mergeToDept").val("");
   $("#"+"isGetAllSubDepts"+"mergeToDept").parent().parent().hide();
   //open dialog
   $("#dlgSelectMergeDept").dialog("option", "title","选择合并部门");
   $("#dlgSelectMergeDept").dialog("option", "resizable",true);
	if(HRMCommon.navigatorIsIE()){
   $("#dlgSelectMergeDept").dialog('option', 'height', 495);
	}else {
	   $("#dlgSelectMergeDept").dialog('option', 'height', 346);
	}
   hrm.common.openDialog("dlgSelectMergeDept");
}

// 合并节点；
var fromId="";
var toId="";
function mergeNode(){
    //需合并部门的String以","分割
	fromId="";
    toId="";
    var mergeDept1=$("#"+"orgselector_returnString_"+"mergeDept1").val();
    if(mergeDept1!=""){
       mergeDept1=mergeDept1.split(",")[0];
       fromId=fromId+mergeDept1+",";
    }
    var mergeDept2=$("#"+"orgselector_returnString_"+"mergeDept2").val();
    if(mergeDept2!=""){
       mergeDept2=mergeDept2.split(",")[0];
       fromId=fromId+mergeDept2+",";
    }
    var mergeDept3=$("#"+"orgselector_returnString_"+"mergeDept3").val();
    if(mergeDept3!=""){
       mergeDept3=mergeDept3.split(",")[0];
       fromId=fromId+mergeDept3+",";
    }
    var mergeToDept=$("#"+"orgselector_returnString_"+"mergeToDept").val();
	if(mergeToDept!=""){
	   toId=mergeToDept.split(",")[0];
	}else{
	  alert("请选择合并到的部门！");
	  return;
	}
    if(fromId==""){
        alert("请至少选择一个需合并部门！");
	    return;
    }else{
       if(fromId.charAt(fromId.length-1)==','){
          fromId = fromId.substring(0,fromId.length-1);
       }
    }
    //检查需合并部门是否有重复项
    var fromIdArray=fromId.split(",");
    if(/(\x0f[^\x0f]+)\x0f[\s\S]*\1/.test("\x0f"+fromIdArray.join("\x0f\x0f")+"\x0f")){
        alert("需合并部门中有重复项！");
        return;
    }
    //检查需合并部门中是否包含合并到部门
    for(var i=0;i<fromIdArray.length;i++){
       var deptId=fromIdArray[i];
       if(deptId== toId){
          alert("需合并部门中不能包含要合并到部门！");
	      return;
       }
    }
	OrgDeptManageAction.getAllPBofDept(fromId, toId, getPBListCallback);
	
	// 显示PB列表回调函数；
	function getPBListCallback(dataList){
		if(typeof(dataList) == "string") {// 返回报错信息；
			alert(dataList);
			return;
		}
		
		var trHtml = getPBTrHtml();
		model.simple.initTableByList("mergePBList","pbList",dataList,trHtml);
		var inChargeElementArr = document.getElementsByName("pbInCharge");
		var pbCancelRespElementArr=document.getElementsByName("pbCancelResp");
		for(var i=0;i<inChargeElementArr.length;i++){
		   var inChargeElement=inChargeElementArr[i];
		   if(inChargeElement.value=='0'){
		      $(pbCancelRespElementArr[i]).hide();
		   }
		}
		
	    $("#dlgMergeFromPB").dialog("option", "title", "需合并部门的职位列表");
	    hrm.common.closeDialog('dlgSelectMergeDept');
		hrm.common.openDialog("dlgMergeFromPB");
	}
}

// 执行职位的合并操作；
function mergePBOperate(){

	var pbsOperateStr="";// 保存起始部门节点操作的字符串；
    var idArr = document.getElementsByName("pbId");
    var pbDeptIdArr=document.getElementsByName("pbDeptId");
    var nameArr = document.getElementsByName("pbName");
    var inChargeArr = document.getElementsByName("pbInCharge");
    var pbCancelRespArr=document.getElementsByName("pbCancelResp");
    var operateArr = document.getElementsByName("pbOperate");
    var toPBIdArr = document.getElementsByName("pbMoveTo");
    for(var i=0; i<idArr.length; i++){
        if(operateArr[i].value=='0_1' || operateArr[i].value==null){
        	alert("请选择 "+nameArr[i].value+" 的操作！");
            return ;
        }
        var pbCancelResp;
        if(pbCancelRespArr[i].checked){
           pbCancelResp="1";
        }else{
           pbCancelResp="0";
        }
        pbsOperateStr += idArr[i].value+","+inChargeArr[i].value+","+operateArr[i].value+","+toPBIdArr[i].value+","+pbCancelResp+","+pbDeptIdArr[i].value+"#";
    }
    
	OrgDeptManageAction.mergeNode(fromId, toId, pbsOperateStr, mergeNodeCallback);
	// 执行merge操作之后回调函数；
	function mergeNodeCallback(data){
		if(typeof(data) == "string") {// 返回报错信息；
			alert(data);
		    hrm.common.closeDialog("dlgMergeFromPB");
			return;
		}
		alert("合并成功！");
		hrm.common.closeDialog("dlgMergeFromPB");
		//alert(fromId+"---"+toId);
		var fromIdArray=fromId.split(",");
		for(var i=0;i<fromIdArray.length;i++){
            var fromDeptId=fromIdArray[i];
            //1. 将起始节点下子节点移动到目的节点下；
			var children = tree.getAllSubItems(fromDeptId);
	        var childrenArr = children!=null&&children!="" ? children.split(",") : null;
	        for(var j=0; childrenArr!=null && j<childrenArr.length; j++){
	            if(childrenArr[j] == '') continue;
	        	tree.moveItem(childrenArr[j], 'item_child', toId);
	
	        	// 修改缓冲数据；
	        	nodeArr[childrenArr[j]].parentId = toId;
	        }
	        //2. 将起始节点停用；
	        tree.moveItem(fromDeptId, 'item_child', 'disabled');
	     	// 修改缓冲数据；
	        nodeArr[fromDeptId].status = 0;
        }
	}
}

// 根据是否负责职位返回PB行的html
function getPBTrHtml(){
	var rowHtml = '<TR id="tr<n>"><input type="hidden" id="pbList_<n>-id" name="pbId" /><input type="hidden" id="pbList_<n>-pbDeptId-id" name="pbDeptId" /><input type="hidden" id="pbList_<n>-pbName" name="pbName" /><input type="hidden" id="pbList_<n>-pbInCharge" name="pbInCharge" />'
	+'	<TD align="center"><label id="pbList_<n>-pbName"></label></TD>'
	+'	<TD align="center"><label id="pbList_<n>-pbDeptId-departmentName"></label></TD>'
	+'	<TD align="center"><input type="checkbox"  name="pbCancelResp"/></TD>'
	+'	<TD align="center"><select id="pbList_<n>-pbOperate" name="pbOperate" onchange="changePBOperate(this);"></select></TD>'
	+'	<TD align="center"><select id="pbList_<n>-pbMoveTo" name="pbMoveTo" style="display:none"></select></TD>'
	+'  </TR>';

	return rowHtml
}

// 根据操作判断是否显示并入节点列表；
function changePBOperate(obj){
	var objId = obj.id;
	var moveToObj = document.getElementById(objId.split("-")[0]+"-pbMoveTo");
	if(obj.value == 2){
		moveToObj.style.display = "inline";
	}else{
		moveToObj.style.display = "none";
	}
}

// 移动节点初始化；
function moveNodeInit(){
	var selectedId = tree.getSelectedItemId();
	if(selectedId==null || selectedId==''){
		alert("请选择要移动的节点!");
		return;
	}
	var fromType = nodeArr[selectedId].type;
	document.getElementById("move_chooseOrgId").value="";
	document.getElementById("move_chooseOrgName").value="";
	document.getElementById("operate").value = "move";
	showChooseOrgDiv(fromType, selectedId, 'btn_move', 'move_chooseOrgName', 'move_chooseOrgId');
}

// 移动节点；
function moveNode(){
	var toName = document.getElementById("move_chooseOrgName").value;
	var selectedName = tree.getSelectedItemText();
	var selectedId = tree.getSelectedItemId();
	var toId = document.getElementById("move_chooseOrgId").value;
	var fromType = nodeArr[selectedId].nodeType;
	if(confirm("您确定要将 "+selectedName+" 移动到 "+toName+" 吗？")){
		OrgDeptManageAction.moveNode(selectedId, toId, moveNodeCallback);
    }
	function moveNodeCallback(info){
	    if("SUCC" != info){
	        alert(info);
	        return;
	    }
	    // 执行树操作，移动节点；
	    alert("移动成功！");
	    tree.moveItem(selectedId, 'item_child', toId);
	    // 修改缓存数据；
	    nodeArr[selectedId].parentId = toId;
	}
}

// 新建分公司初始化；
function newBranchInit(){
	var selectedId = tree.getSelectedItemId();
	if(selectedId==null || selectedId=='' || selectedId!=rootId){
		alert("请选中根节点!");
		return;
	}
	url = "goBranchDeptTabs.action?nodeId="+selectedId+"&nodeType=1";
	refreshIFrame(url);
}

// 新建部门初始化；
function newDeptInit(){
	var selectedId = tree.getSelectedItemId();
	if(selectedId==null || selectedId==''){
		alert("请选中父节点!");
		return;
	}
	url = "goBranchDeptTabs.action?nodeId="+selectedId+"&nodeType=2";
	refreshIFrame(url);
}

// 修改名称初始化；
function renameInit(){
	var selectedId = tree.getSelectedItemId();
	if(selectedId==null || selectedId==''){
		alert("请选择要更名的节点!");
		return;
	}
    $("#dlgRename").dialog("option", "title", "修改名称");
    $("#dlgRename").dialog("option", "height", 150);
    document.getElementById("newName").value = tree.getSelectedItemText();
	hrm.common.openDialog("dlgRename");
}

// 修改名称；
function rename(){
	var nodeId = tree.getSelectedItemId();
	var newName = document.getElementById("newName").value;
	
	if(!confirm("您确定要修改名称吗？")) return;

    var nodeType = getNodeType(nodeId);
    OrgDeptManageAction.deptRename(nodeId, newName, renameCallback);
    function renameCallback(info){
        if(info == 'SUCC'){
            alert("名称修改成功！");
        	tree.setItemText(nodeId,newName);
        	nodeArr[nodeId].name = newName;// 修改页面上的缓存信息；
        }else{
        	alert(info);
        }
        hrm.common.closeDialog("dlgRename");

        // 刷新右边页面；
    	var url = "goBranchDeptTabs.action?dept.id="+nodeId+"&nodeType="+nodeType;
    	refreshIFrame(url);
    }
}

// 停用部门；
function disableItem(){
	var selectedId = tree.getSelectedItemId();
	if(selectedId==null || selectedId==''){
		alert("请选择要停用的节点!");
		return;
	}

	var nodeName = nodeArr[selectedId].name;
	if(!confirm("您确定要停用 "+nodeName+" 吗？")){
		return;
	}

	OrgDeptManageAction.disableDept(selectedId, stopCallback);
	function stopCallback(info){
		if(info != 'SUCC'){
			alert(info);
			return;
		}

		alert("停用成功。");
		// 停用成功后执行树操作:
		// 子节点的操作；
		var ids = tree.getAllSubItems(selectedId);
		var idsArr = ids.split(",");
		for(var i=0; idsArr!=null&&i<idsArr.length; i++){
			if(idsArr[i] == '') continue;
			tree.disableCheckbox(idsArr[i],true);
			tree.moveItem(idsArr[i], 'item_child', "disabled");
			nodeArr[idsArr[i]].status = 0;
		}
		
		// 当前节点的操作；
		tree.disableCheckbox(selectedId,true);
		tree.moveItem(selectedId, 'item_child', "disabled");// 将该节点及其下属节点移动到停用部门下；
		nodeArr[selectedId].status = 0;// 修改缓存数据；
		displayOrShowButton(nodeArr[selectedId].type, nodeArr[selectedId].status);// 修改按钮的显示；
	}
}

// 启用部门；??是否要选择启用后的父节点；
function enableItemInit(){
	var selectedId = tree.getSelectedItemId();
	if(selectedId==null || selectedId==''){
		alert("请选择要启用的节点!");
		return;
	}

	var fromType = nodeArr[selectedId].type;
	document.getElementById("start_chooseOrgId").value="";
	document.getElementById("start_chooseOrgName").value="";
	document.getElementById("operate").value = "start";
	showChooseOrgDiv(fromType, null, 'btn_start', 'start_chooseOrgName', 'start_chooseOrgId');
}

function enableItem(){
	var selectedId = tree.getSelectedItemId();
	if(selectedId==null || selectedId==''){
		alert("请选择要启用的节点!");
		return;
	}
	var nodeName = nodeArr[selectedId].name;
	if(!confirm("您确定要启用 "+nodeName+" 吗？")){
		return;
	}

	OrgDeptManageAction.enableDept(selectedId, enableCallback);
	function enableCallback(info){
		if(info.indexOf('SUCC') < 0){
			alert(info);
			return;
		}

		var parentId = info.split(",")[1];
		alert("启用成功。");
		// 启用成功后执行树操作；
		tree.moveItem(selectedId, 'item_child', parentId);
		tree.disableCheckbox(selectedId,false);
		// 修改缓存数据；
		nodeArr[selectedId].status = 1;
		nodeArr[selectedId].parentId = parentId;
		// 修改按钮的显示；
		displayOrShowButton(nodeArr[selectedId].type, nodeArr[selectedId].status);

		// 设置图片；
		orgDeptTree.setNodesImage(nodeArr);
	}
}

// 删除节点；
function deleteItem(){
	var selectedId = tree.getSelectedItemId();
	if(selectedId==null || selectedId==''){
		alert("请选择要删除的节点!");
		return;
	}

	var nodeName = nodeArr[selectedId].name;
	if(!confirm("您确定要删除 "+nodeName+" 吗？")){
		return;
	}

	OrgDeptManageAction.deleteDept(selectedId, deleteCallback);
	function deleteCallback(info){
		if(info != 'SUCC'){
			alert(info);
			return;
		}

		alert("删除成功。");
		// 删除成功后执行树操作；
		tree.deleteItem(selectedId, true);	
		// 修改缓存数据；
		nodeArr[selectedId] = null;
	}
}

//根据选中的节点判断要显示的按钮；
function displayOrShowButton(type, status){
	if(type == 0){// 总公司;
		document.getElementById("td_rename").style.display="inline";
		document.getElementById("td_move").style.display="inline";
		document.getElementById("td_merge").style.display="inline";
		document.getElementById("td_stop").style.display="none";
		document.getElementById("td_start").style.display="none";
		document.getElementById("td_delete").style.display="none";
		document.getElementById("td_new_branch").style.display="inline";
	    document.getElementById("td_new_dept").style.display="inline";
    }else if(type==1 || type==2){// 分公司或部门；
    	if(status == 0){// 停用状态下显示操作按钮；
    		document.getElementById("td_rename").style.display="none";
    		document.getElementById("td_move").style.display="none";
    		document.getElementById("td_merge").style.display="none";
    		document.getElementById("td_stop").style.display="none";
    		document.getElementById("td_new_branch").style.display="none";
    		document.getElementById("td_new_dept").style.display="none";
    		document.getElementById("td_start").style.display="inline";
    		document.getElementById("td_delete").style.display="inline";
    	}else if(status == 1){// 启用状态下显示操作按钮；
    		document.getElementById("td_rename").style.display="inline";
			document.getElementById("td_move").style.display="inline";
			document.getElementById("td_merge").style.display="inline";
    		document.getElementById("td_start").style.display="none";
    		document.getElementById("td_new_branch").style.display="none";
    		document.getElementById("td_delete").style.display="inline";
    		document.getElementById("td_stop").style.display="inline";
    		document.getElementById("td_new_dept").style.display="inline";
    	}
    }
}

function test(){
	var objs = document.getElementsByName("testInupt");
	alert(objs.length);
	alert(objs[0].value);
	alert(objs[1].value);
	alert(objs[2].value);
	alert(objs[3].value);
}
</script>
<jsp:include flush="true" page="org_choose_div.jsp"></jsp:include>
</body>
</html>
