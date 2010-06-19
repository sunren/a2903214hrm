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
  <th nowrap="nowrap">ְλ����</th>
  <th nowrap="nowrap">��������</th>
  <th nowrap="nowrap">ְ��</th>
  <th nowrap="nowrap">�ϼ�ְλ</th>
  <th nowrap="nowrap">״̬</th>
  <th nowrap="nowrap">��������</th>
  <th nowrap="nowrap">�Ƿ���ְλ</th>
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
				      ����
				    </s:if>
				    <s:else>
				      ��
				    </s:else>
			    </td>
    		</tr>
    	</s:iterator>
    </s:if>
	<s:else>
		<tr>
			<td align="center" colspan="8">����ְλ��Ϣ!</td>
		</tr>
	</s:else>
  </tbody>	
</table>
<div class="btnlink">
	<a href="#" onclick="showNewPage();">����ְλ</a>
	<a href="#" onclick="modifySelectPb();">�޸�</a>
	<a href="#" onclick="delSelectPb();">ɾ��</a>
	<a href="#" onclick="showPbManual();">ְλ˵����</a>
	<a href="#" onclick="savePbSort();">��������</a>
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
//��ʼ��table
function initTable(){
   $("#pbTable").tablesorter().tableDnD(); //���ñ������/��ק
   $("#pbTableBody tr").click(function(){//����ѡ��ÿһ��
   $("#pbTableBody tr").removeClass('click');
   $(this).addClass("click");
     });
   $("#pbTableBody tr").dblclick(function(){//˫����ת
		var id= this.id;//�õ�id
		//alert(id);
		var url="pbManage.action?pbId="+id;
		window.parent.refreshIFrame(url);
        tree.selectItem(id);
        tree.focusItem(id);
   });
}
//��ȡҳ���ϼ�¼��id�ļ���
function getIdsInTable(){
	    var result = new Array();
	    var trs = $("#pbTableBody > tr");
	    for(var i = 0; i < trs.length; i++){
	        var id = trs[i].getAttribute("id");
	        result[i]=id;
	    }
	    return result;
	}
//��������
function savePbSort(){
    var ids=getIdsInTable();
    function savePbOrderCallBack(data){
        if(data == "FAIL"){
            errMsg("errMsg", "����ʧ�ܣ�");
            return;
        }
        successMsg("errMsg", "�������򱣴�ɹ���");
    }
    pbManage.savePbOrder(ids,savePbOrderCallBack);
}
//�޸İ�ť
function modifySelectPb(){
    var selectTr=getSelectedRow();
    if (selectTr == null){
	     errMsg("errMsg", "��ѡ��Ҫ���õ��С�");
	     return ;
    }
    var id=selectTr.id;
    var url="pbManage.action?pbId="+id;
	window.parent.refreshIFrame(url);
       tree.selectItem(id);
       tree.focusItem(id);
}
//��ȡ���ѡ�е�һ��
function getSelectedRow(){
	var row = $("#pbTableBody tr").filter(".click");
	if(!row || row ==null || !row[0]){
		return null;
	}
	return row[0];
}
//����״̬
function convertStatus(img){
  var selectTr=$(img).parent().parent()[0];
  var id=selectTr.id;
  var node=nodeArr[id];
  //alert(selectTr.id);
  pbManage.convertPbStatus(id,function (data){
      if(data == "FAIL"){
            errMsg("errMsg", "����ʧ�ܣ�");
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
      successMsg("errMsg", "����״̬�ɹ���");
    });
}
//ɾ��ѡ��ְλ
function delSelectPb(){
    var selectTr=getSelectedRow();
    if (selectTr == null){
	     errMsg("errMsg", "��ѡ��Ҫ���õ��С�");
	     return ;
    }
    var id=selectTr.id;
    //alert(id);
    pbManage.delPb(id,function (data){
      if(data == "SUCC"){
            $(selectTr).remove();
            tree.deleteItem(id,false);
            successMsg("errMsg", "ɾ���ɹ���");
            return;
        }
      
      errMsg("errMsg", data);
    });
}
//�鿴ְλ˵����
function showPbManual(){
  var selectTr=getSelectedRow();
    if (selectTr == null){
	     errMsg("errMsg", "��ѡ��Ҫ�鿴ְλ��");
	     return ;
    }
  var id=selectTr.id.trim();
  var url = "showPbManual.action?pbId="+id;
  window.open(url,'_blank', 'scrollbars=yes','width=100%','height=100%','resizable=yes');
}
//��ʼ��table
initTable();
</script>
</body>
</html>