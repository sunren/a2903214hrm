<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<html>
<head>
<link href="../resource/css/style.css" rel="stylesheet" type="text/css" />
<jsp:include flush="true" page="/sitemesh/jsPackage.jsp"></jsp:include>
<!-- jQuery start -->
<script type="text/javascript" src="../resource/js/jquery/jquery.tablesorter.js"></script>
<script type="text/javascript" src="../resource/js/jquery/jquery.tableEditor.js"></script>
<!-- jQuery end -->
<script type="text/javascript" src="../dwr/interface/pbManage.js"></script>
</head>
<body>
<span class="errorMessage" id="errMsg"></span>
<table id="pbTable" cellspacing="1" cellspacing="0" class="tablesorter" style="width: 650">
  <thead>
  <tr align="center">
  <th nowrap="nowrap">职位名称</th>
  <th nowrap="nowrap">编制人数</th>
  <th nowrap="nowrap">职务</th>
  <th nowrap="nowrap">上级职位</th>
  <th nowrap="nowrap">状态</th>
  <th nowrap="nowrap">所属部门</th>
  <th nowrap="nowrap">是否负责职位</th>
  </tr>
  </thead>
  <tbody id="pbTableBody">
   <s:if test="!pbSelectList.isEmpty()">
    	<s:iterator value="pbSelectList" status="index">
    		<tr id='<s:property value="id"/>' align="center">
				<td align="center"><s:property value="pbName" /></td>  
				<td align="center"><s:property value="pbMaxEmployee" /></td>
				<td align="center"></td>   
			    <td align="center"><s:property value="pbSupId.pbName" /></td>
			    <td align="center">
			    <s:if test="pbStatus!=null && pbStatus==1">
				    <img onclick="convertStatus(this);" onmouseover="style.cursor='pointer';" id="defaultimg_yes" src="../resource/images/default_yes.png" border="0" />
				</s:if>
				<s:else>
				    <img onclick="convertStatus(this);" onmouseover="style.cursor='pointer';" id="defaultimg_no" src="../resource/images/default_no.png" border="0" />
				</s:else>
			    </td>
			    <td align="center"><s:property value="pbDeptId.departmentName" /></td>
			    <td align="center">
				    <s:if test="pbInCharge==0">
				      不是
				    </s:if>
				    <s:else>
				      是
				    </s:else>
			    </td>
    		</tr>
    	</s:iterator>
    </s:if>
	<s:else>
		<tr>
			<td align="center" colspan="8">不存职位信息!</td>
		</tr>
	</s:else>
  </tbody>	
</table>
<div class="btnlink">
	<a href="#" onclick="showNewPage();">新增职位</a>
	<a href="#" onclick="modifySelectPb();">修改</a>
	<a href="#" onclick="delSelectPb();">删除</a>
	<a href="#" onclick="showPbManual();">职位说明书</a>
	<a href="#" onclick="savePbSort();">保存排序</a>
</div>
<br/>
<script type="text/javascript">
var deptId='<s:property value='deptId'/>'.trim();
var tree = window.parent.tree;
var pbTree=window.parent.pbTree;
var nodeArr=window.parent.nodeArr;
function showNewPage(){
   var url="pbManage.action?deptId="+deptId;
   window.parent.refreshIFrame(url);
}
//初始化table
function initTable(){
   $("#pbTable").tablesorter().tableDnD(); //启用表格排序/拖拽
   $("#pbTableBody tr").click(function(){//单击选中每一行
   $("#pbTableBody tr").removeClass('click');
   $(this).addClass("click");
     });
   $("#pbTableBody tr").dblclick(function(){//双击跳转
		var id= this.id;//得到id
		//alert(id);
		var url="pbManage.action?pbId="+id;
		window.parent.refreshIFrame(url);
        tree.selectItem(id);
        tree.focusItem(id);
   });
}
//获取页面上记录的id的集合
function getIdsInTable(){
	    var result = new Array();
	    var trs = $("#pbTableBody > tr");
	    for(var i = 0; i < trs.length; i++){
	        var id = trs[i].getAttribute("id");
	        result[i]=id;
	    }
	    return result;
	}
//保存排序
function savePbSort(){
    var ids=getIdsInTable();
    function savePbOrderCallBack(data){
        if(data == "FAIL"){
            errMsg("errMsg", "操作失败！");
            return;
        }
        successMsg("errMsg", "最新排序保存成功。");
    }
    pbManage.savePbOrder(ids,savePbOrderCallBack);
}
//修改按钮
function modifySelectPb(){
    var selectTr=getSelectedRow();
    if (selectTr == null){
	     errMsg("errMsg", "请选择要设置的行。");
	     return ;
    }
    var id=selectTr.id;
    var url="pbManage.action?pbId="+id;
	window.parent.refreshIFrame(url);
       tree.selectItem(id);
       tree.focusItem(id);
}
//获取鼠标选中的一行
function getSelectedRow(){
	var row = $("#pbTableBody tr").filter(".click");
	if(!row || row ==null || !row[0]){
		return null;
	}
	return row[0];
}
//更改状态
function convertStatus(img){
  var selectTr=$(img).parent().parent()[0];
  var id=selectTr.id;
  var node=nodeArr[id];
  //alert(selectTr.id);
  pbManage.convertPbStatus(id,function (data){
      if(data == "FAIL"){
            errMsg("errMsg", "操作失败！");
            return;
        }
      if(data==1){
         selectTr.cells[4].innerHTML="<img onclick=\"convertStatus(this);\" onmouseover=\"style.cursor='pointer';\" id='defaultimg' src='../resource/images/default_yes.png' border='0'>";
	     node.type=3;
	     pbTree.setNodesImage(nodeArr);
      }
      if(data==0){
        selectTr.cells[4].innerHTML="<img onclick=\"convertStatus(this);\" onmouseover=\"style.cursor='pointer';\" id='defaultimg' src='../resource/images/default_no.png' border='0'>";
        node.type=4;
	    pbTree.setNodesImage(nodeArr);
      }
      successMsg("errMsg", "更改状态成功！");
    });
}
//删除选中职位
function delSelectPb(){
    var selectTr=getSelectedRow();
    if (selectTr == null){
	     errMsg("errMsg", "请选择要设置的行。");
	     return ;
    }
    var id=selectTr.id;
    //alert(id);
    pbManage.delPb(id,function (data){
      if(data == "SUCC"){
            $(selectTr).remove();
            tree.deleteItem(id,false);
            successMsg("errMsg", "删除成功！");
            return;
        }
      
      errMsg("errMsg", data);
    });
}
//查看职位说明书
function showPbManual(){
  var selectTr=getSelectedRow();
    if (selectTr == null){
	     errMsg("errMsg", "请选择要查看职位！");
	     return ;
    }
  var id=selectTr.id.trim();
  var url = "showPbManual.action?pbId="+id;
  window.open(url,'_blank', 'scrollbars=yes','width=100%','height=100%','resizable=yes');
}
//初始化table
initTable();
</script>
</body>
</html>