<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>���Ź���</title>
<script type="text/javascript" src="../dwr/interface/OrgManageAction.js"></script>
	<script type='text/javascript' src='../dwr/interface/OrgDeptManageAction.js'></script>
<script type="text/javascript" src="../resource/js/profile/DHTreeClass.js"></script>
</head>
<body onload="initData()">
<s:component template="bodyhead">
	<s:param name="pagetitle" value="'���Ź���'" />
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
		        	<input type="button" id="btn_rename"      value="��&nbsp;��"    onclick="renameInit();" style="cursor:hand;"/>
		        </td>
		        <td align="left" nowrap id="td_move">
		        	<input type="button" id="btn_move"        value="��&nbsp;��"    onclick="moveNodeInit();"  style="cursor:hand;"/>
		        </td>
		        <td align="left" nowrap id="td_merge">
		        	<input type="button" id="btn_merge"       value="��&nbsp;��"    onclick="mergeNodeInit();"  style="cursor:hand;"/>
		        </td>
		        <td align="left" nowrap id="td_stop">
		        	<input type="button" id="btn_stop"        value="ͣ&nbsp;��"    onclick="disableItem();"  style="cursor:hand;"/>
		        </td>
		        <td align="left" nowrap id="td_start">
		        	<input type="button" id="btn_start"       value="��&nbsp;��"    onclick="enableItem();"  style="cursor:hand;"/>
		        </td>
		        <td align="left" nowrap id="td_delete">
					<input type="button" id="btn_delete"      value="ɾ&nbsp;��"    onclick="deleteItem();"  style="cursor:hand;"/>
				</td>
				<td align="left" nowrap id="td_new_branch">
					<input type="button" id="btn_new_branch"  value="�½��ֹ�˾"     onclick="newBranchInit();"  style="cursor:hand;"/>
				</td>
				<td align="left" nowrap id="td_new_dept">
					<input type="button" id="btn_new_dept"    value="�½�����"       onclick="newDeptInit();"  style="cursor:hand;"/>
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

<!-- ����dialog -->
<div id="dlgRename">
    <table width="100%">
        <tr>
            <td align="center">
            	<input type="text" size="20" id="newName" name="newName" value=""/>
            </td>
        </tr>
        <tr>
            <td align="center">
            	<input type="button" id="btn_rename" name="btn_rename" value="ȷ��" onclick="rename()"/>
            	<input type="button" id="btn_rename_cancel" name="btn_rename_cancel" value="ȡ��" onclick="hrm.common.closeDialog('dlgRename');"/>
            </td>
        </tr>
    </table>
</div>
<!-- ѡ��ϲ�����dialog -->
<div id="dlgSelectMergeDept" style="display: none;">
 <table width="100%">
        <tr >
           <td>
            <div>
            <table width="100%">
               <tr>
               <td >��ϲ�����</td>
               <td >���뵽����</td>
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
            	<input type="button" id="btn_selectMergeDept" value="ȷ��" onclick="mergeNode()"/>
            	<input type="button"  value="ȡ��" onclick="hrm.common.closeDialog('dlgSelectMergeDept');"/>
            </td>
        </tr>
    </table>
</div>
<!-- �ϲ��ڵ�����У���ʼ�����¸���ְλ�Ĳ��� -->
<div id="dlgMergeFromPB">
    <table width="100%" id="mergePBList">
        <tr>
            <th align="center">ְλ����</th>
            <th align="center">��������</th>
            <th align="center">ȡ������</th>
            <th align="center">����</th>
            <th align="center">����ְλ</th>
        </tr>
    </table>
    <table width="100%">
   		<tr>
            <td align="center">
            	<input type="button" id="btn_rename" name="btn_merge_confirm" value="ȷ��" onclick="mergePBOperate();"/>
            	<input type="button" id="btn_rename" name="btn_merge_cancel" value="ȡ��" onclick="hrm.common.closeDialog('dlgMergeFromPB');"/>
            </td>
        </tr>
    </table>
</div>

<script type="text/javascript">
// ��ʼ��dialog��
hrm.common.initDialog("moveItem", 300);
hrm.common.initDialog("dlgRename", 200);
hrm.common.initDialog("dlgMergeFromPB", 500);
hrm.common.initDialog("dlgSelectMergeDept", 473);
//���ඨ�壻
var OrgDeptTree = function(param){
	DHTreeClass.call(this, param);
};
OrgDeptTree.prototype = DHTreeClass.prototype;

//�����¼���
OrgDeptTree.prototype.lclick = function(id){
	var status = getNodeStatus(id);
	var type = getNodeType(id);
	if(id == disableRootId) return;// �������Ÿ��ڵ㣻
	var url = "goBranchDeptTabs.action?dept.id="+id+"&nodeType="+type;
	
	displayOrShowButton(type, status);
	if(url != null){
		refreshIFrame(url);
	}
};
// ��Ҷ�滺����������ݣ�
OrgDeptTree.prototype.addToMemory = function(id, parentId, name, type, status){
	nodeArr[id] = new node(id, parentId, name, type, status);
	displayOrShowButton(type, status);

	// ����ͼƬ��
	orgDeptTree.setNodesImage(nodeArr);
}

// 1.��ʼ���ڵ����ݣ�����ҳ����ץȡʹ��, type: 0:company, 1:branch, 2:dept, 3:position;
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
//��ȡ�ڵ��״̬;
function getNodeStatus(id){
	var obj = nodeArr[id];
	return obj.status;
}
//��ȡ�ڵ������;
function getNodeType(id){
	var obj = nodeArr[id];
	return obj.type;
}

// 2.��ʼ��treeArray���ݣ�
var treeArray = new Array(
	<s:iterator value="nodeList" status="index">
	    ['<s:property value="nodeId"/>', '<s:property value="nodeParentId"/>', '<s:property value="nodeName"/>']
	    <s:if test="!#index.isLast()">,</s:if>
	</s:iterator>
);

// 3.��ʼ��tree��
var tree;
var rootId='<s:property value="rootId"/>';
var disableRootId = '<s:property value="disableRootId"/>';
var orgDeptTree;
function initData(){
	//��װ������
	var param = {
		"treeDiv"      : 'divDeptTree',
		"dataSource"   : treeArray,
		"menuXml"      : null,
		"enableChkbox" : false,
		"enableDrag"   : false
	};
	// �����ʼ����
	orgDeptTree = new OrgDeptTree(param);
	orgDeptTree.init();// ��ʼ�����νṹ��
	tree = orgDeptTree.tree;
	// ����ͼƬ��
	orgDeptTree.setNodesImage(nodeArr);
}

// ˢ���ұ�iframe�е�ҳ�棻
function refreshIFrame(url){
	var iframe=document.getElementById("iframe");
	if(hrm.common.navigatorIsFF()){
		iframe.contentWindow.document.location=url;
	}else{
		iframe.src=url;
	}
}

// �ϲ��ڵ��ʼ����
function mergeNodeInit(){
   //initial value,/����checkbox
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
   $("#dlgSelectMergeDept").dialog("option", "title","ѡ��ϲ�����");
   $("#dlgSelectMergeDept").dialog("option", "resizable",true);
	if(HRMCommon.navigatorIsIE()){
   $("#dlgSelectMergeDept").dialog('option', 'height', 495);
	}else {
	   $("#dlgSelectMergeDept").dialog('option', 'height', 346);
	}
   hrm.common.openDialog("dlgSelectMergeDept");
}

// �ϲ��ڵ㣻
var fromId="";
var toId="";
function mergeNode(){
    //��ϲ����ŵ�String��","�ָ�
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
	  alert("��ѡ��ϲ����Ĳ��ţ�");
	  return;
	}
    if(fromId==""){
        alert("������ѡ��һ����ϲ����ţ�");
	    return;
    }else{
       if(fromId.charAt(fromId.length-1)==','){
          fromId = fromId.substring(0,fromId.length-1);
       }
    }
    //�����ϲ������Ƿ����ظ���
    var fromIdArray=fromId.split(",");
    if(/(\x0f[^\x0f]+)\x0f[\s\S]*\1/.test("\x0f"+fromIdArray.join("\x0f\x0f")+"\x0f")){
        alert("��ϲ����������ظ��");
        return;
    }
    //�����ϲ��������Ƿ�����ϲ�������
    for(var i=0;i<fromIdArray.length;i++){
       var deptId=fromIdArray[i];
       if(deptId== toId){
          alert("��ϲ������в��ܰ���Ҫ�ϲ������ţ�");
	      return;
       }
    }
	OrgDeptManageAction.getAllPBofDept(fromId, toId, getPBListCallback);
	
	// ��ʾPB�б�ص�������
	function getPBListCallback(dataList){
		if(typeof(dataList) == "string") {// ���ر�����Ϣ��
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
		
	    $("#dlgMergeFromPB").dialog("option", "title", "��ϲ����ŵ�ְλ�б�");
	    hrm.common.closeDialog('dlgSelectMergeDept');
		hrm.common.openDialog("dlgMergeFromPB");
	}
}

// ִ��ְλ�ĺϲ�������
function mergePBOperate(){

	var pbsOperateStr="";// ������ʼ���Žڵ�������ַ�����
    var idArr = document.getElementsByName("pbId");
    var pbDeptIdArr=document.getElementsByName("pbDeptId");
    var nameArr = document.getElementsByName("pbName");
    var inChargeArr = document.getElementsByName("pbInCharge");
    var pbCancelRespArr=document.getElementsByName("pbCancelResp");
    var operateArr = document.getElementsByName("pbOperate");
    var toPBIdArr = document.getElementsByName("pbMoveTo");
    for(var i=0; i<idArr.length; i++){
        if(operateArr[i].value=='0_1' || operateArr[i].value==null){
        	alert("��ѡ�� "+nameArr[i].value+" �Ĳ�����");
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
	// ִ��merge����֮��ص�������
	function mergeNodeCallback(data){
		if(typeof(data) == "string") {// ���ر�����Ϣ��
			alert(data);
		    hrm.common.closeDialog("dlgMergeFromPB");
			return;
		}
		alert("�ϲ��ɹ���");
		hrm.common.closeDialog("dlgMergeFromPB");
		//alert(fromId+"---"+toId);
		var fromIdArray=fromId.split(",");
		for(var i=0;i<fromIdArray.length;i++){
            var fromDeptId=fromIdArray[i];
            //1. ����ʼ�ڵ����ӽڵ��ƶ���Ŀ�Ľڵ��£�
			var children = tree.getAllSubItems(fromDeptId);
	        var childrenArr = children!=null&&children!="" ? children.split(",") : null;
	        for(var j=0; childrenArr!=null && j<childrenArr.length; j++){
	            if(childrenArr[j] == '') continue;
	        	tree.moveItem(childrenArr[j], 'item_child', toId);
	
	        	// �޸Ļ������ݣ�
	        	nodeArr[childrenArr[j]].parentId = toId;
	        }
	        //2. ����ʼ�ڵ�ͣ�ã�
	        tree.moveItem(fromDeptId, 'item_child', 'disabled');
	     	// �޸Ļ������ݣ�
	        nodeArr[fromDeptId].status = 0;
        }
	}
}

// �����Ƿ���ְλ����PB�е�html
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

// ���ݲ����ж��Ƿ���ʾ����ڵ��б�
function changePBOperate(obj){
	var objId = obj.id;
	var moveToObj = document.getElementById(objId.split("-")[0]+"-pbMoveTo");
	if(obj.value == 2){
		moveToObj.style.display = "inline";
	}else{
		moveToObj.style.display = "none";
	}
}

// �ƶ��ڵ��ʼ����
function moveNodeInit(){
	var selectedId = tree.getSelectedItemId();
	if(selectedId==null || selectedId==''){
		alert("��ѡ��Ҫ�ƶ��Ľڵ�!");
		return;
	}
	var fromType = nodeArr[selectedId].type;
	document.getElementById("move_chooseOrgId").value="";
	document.getElementById("move_chooseOrgName").value="";
	document.getElementById("operate").value = "move";
	showChooseOrgDiv(fromType, selectedId, 'btn_move', 'move_chooseOrgName', 'move_chooseOrgId');
}

// �ƶ��ڵ㣻
function moveNode(){
	var toName = document.getElementById("move_chooseOrgName").value;
	var selectedName = tree.getSelectedItemText();
	var selectedId = tree.getSelectedItemId();
	var toId = document.getElementById("move_chooseOrgId").value;
	var fromType = nodeArr[selectedId].nodeType;
	if(confirm("��ȷ��Ҫ�� "+selectedName+" �ƶ��� "+toName+" ��")){
		OrgDeptManageAction.moveNode(selectedId, toId, moveNodeCallback);
    }
	function moveNodeCallback(info){
	    if("SUCC" != info){
	        alert(info);
	        return;
	    }
	    // ִ�����������ƶ��ڵ㣻
	    alert("�ƶ��ɹ���");
	    tree.moveItem(selectedId, 'item_child', toId);
	    // �޸Ļ������ݣ�
	    nodeArr[selectedId].parentId = toId;
	}
}

// �½��ֹ�˾��ʼ����
function newBranchInit(){
	var selectedId = tree.getSelectedItemId();
	if(selectedId==null || selectedId=='' || selectedId!=rootId){
		alert("��ѡ�и��ڵ�!");
		return;
	}
	url = "goBranchDeptTabs.action?nodeId="+selectedId+"&nodeType=1";
	refreshIFrame(url);
}

// �½����ų�ʼ����
function newDeptInit(){
	var selectedId = tree.getSelectedItemId();
	if(selectedId==null || selectedId==''){
		alert("��ѡ�и��ڵ�!");
		return;
	}
	url = "goBranchDeptTabs.action?nodeId="+selectedId+"&nodeType=2";
	refreshIFrame(url);
}

// �޸����Ƴ�ʼ����
function renameInit(){
	var selectedId = tree.getSelectedItemId();
	if(selectedId==null || selectedId==''){
		alert("��ѡ��Ҫ�����Ľڵ�!");
		return;
	}
    $("#dlgRename").dialog("option", "title", "�޸�����");
    $("#dlgRename").dialog("option", "height", 150);
    document.getElementById("newName").value = tree.getSelectedItemText();
	hrm.common.openDialog("dlgRename");
}

// �޸����ƣ�
function rename(){
	var nodeId = tree.getSelectedItemId();
	var newName = document.getElementById("newName").value;
	
	if(!confirm("��ȷ��Ҫ�޸�������")) return;

    var nodeType = getNodeType(nodeId);
    OrgDeptManageAction.deptRename(nodeId, newName, renameCallback);
    function renameCallback(info){
        if(info == 'SUCC'){
            alert("�����޸ĳɹ���");
        	tree.setItemText(nodeId,newName);
        	nodeArr[nodeId].name = newName;// �޸�ҳ���ϵĻ�����Ϣ��
        }else{
        	alert(info);
        }
        hrm.common.closeDialog("dlgRename");

        // ˢ���ұ�ҳ�棻
    	var url = "goBranchDeptTabs.action?dept.id="+nodeId+"&nodeType="+nodeType;
    	refreshIFrame(url);
    }
}

// ͣ�ò��ţ�
function disableItem(){
	var selectedId = tree.getSelectedItemId();
	if(selectedId==null || selectedId==''){
		alert("��ѡ��Ҫͣ�õĽڵ�!");
		return;
	}

	var nodeName = nodeArr[selectedId].name;
	if(!confirm("��ȷ��Ҫͣ�� "+nodeName+" ��")){
		return;
	}

	OrgDeptManageAction.disableDept(selectedId, stopCallback);
	function stopCallback(info){
		if(info != 'SUCC'){
			alert(info);
			return;
		}

		alert("ͣ�óɹ���");
		// ͣ�óɹ���ִ��������:
		// �ӽڵ�Ĳ�����
		var ids = tree.getAllSubItems(selectedId);
		var idsArr = ids.split(",");
		for(var i=0; idsArr!=null&&i<idsArr.length; i++){
			if(idsArr[i] == '') continue;
			tree.disableCheckbox(idsArr[i],true);
			tree.moveItem(idsArr[i], 'item_child', "disabled");
			nodeArr[idsArr[i]].status = 0;
		}
		
		// ��ǰ�ڵ�Ĳ�����
		tree.disableCheckbox(selectedId,true);
		tree.moveItem(selectedId, 'item_child', "disabled");// ���ýڵ㼰�������ڵ��ƶ���ͣ�ò����£�
		nodeArr[selectedId].status = 0;// �޸Ļ������ݣ�
		displayOrShowButton(nodeArr[selectedId].type, nodeArr[selectedId].status);// �޸İ�ť����ʾ��
	}
}

// ���ò��ţ�??�Ƿ�Ҫѡ�����ú�ĸ��ڵ㣻
function enableItemInit(){
	var selectedId = tree.getSelectedItemId();
	if(selectedId==null || selectedId==''){
		alert("��ѡ��Ҫ���õĽڵ�!");
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
		alert("��ѡ��Ҫ���õĽڵ�!");
		return;
	}
	var nodeName = nodeArr[selectedId].name;
	if(!confirm("��ȷ��Ҫ���� "+nodeName+" ��")){
		return;
	}

	OrgDeptManageAction.enableDept(selectedId, enableCallback);
	function enableCallback(info){
		if(info.indexOf('SUCC') < 0){
			alert(info);
			return;
		}

		var parentId = info.split(",")[1];
		alert("���óɹ���");
		// ���óɹ���ִ����������
		tree.moveItem(selectedId, 'item_child', parentId);
		tree.disableCheckbox(selectedId,false);
		// �޸Ļ������ݣ�
		nodeArr[selectedId].status = 1;
		nodeArr[selectedId].parentId = parentId;
		// �޸İ�ť����ʾ��
		displayOrShowButton(nodeArr[selectedId].type, nodeArr[selectedId].status);

		// ����ͼƬ��
		orgDeptTree.setNodesImage(nodeArr);
	}
}

// ɾ���ڵ㣻
function deleteItem(){
	var selectedId = tree.getSelectedItemId();
	if(selectedId==null || selectedId==''){
		alert("��ѡ��Ҫɾ���Ľڵ�!");
		return;
	}

	var nodeName = nodeArr[selectedId].name;
	if(!confirm("��ȷ��Ҫɾ�� "+nodeName+" ��")){
		return;
	}

	OrgDeptManageAction.deleteDept(selectedId, deleteCallback);
	function deleteCallback(info){
		if(info != 'SUCC'){
			alert(info);
			return;
		}

		alert("ɾ���ɹ���");
		// ɾ���ɹ���ִ����������
		tree.deleteItem(selectedId, true);	
		// �޸Ļ������ݣ�
		nodeArr[selectedId] = null;
	}
}

//����ѡ�еĽڵ��ж�Ҫ��ʾ�İ�ť��
function displayOrShowButton(type, status){
	if(type == 0){// �ܹ�˾;
		document.getElementById("td_rename").style.display="inline";
		document.getElementById("td_move").style.display="inline";
		document.getElementById("td_merge").style.display="inline";
		document.getElementById("td_stop").style.display="none";
		document.getElementById("td_start").style.display="none";
		document.getElementById("td_delete").style.display="none";
		document.getElementById("td_new_branch").style.display="inline";
	    document.getElementById("td_new_dept").style.display="inline";
    }else if(type==1 || type==2){// �ֹ�˾���ţ�
    	if(status == 0){// ͣ��״̬����ʾ������ť��
    		document.getElementById("td_rename").style.display="none";
    		document.getElementById("td_move").style.display="none";
    		document.getElementById("td_merge").style.display="none";
    		document.getElementById("td_stop").style.display="none";
    		document.getElementById("td_new_branch").style.display="none";
    		document.getElementById("td_new_dept").style.display="none";
    		document.getElementById("td_start").style.display="inline";
    		document.getElementById("td_delete").style.display="inline";
    	}else if(status == 1){// ����״̬����ʾ������ť��
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
