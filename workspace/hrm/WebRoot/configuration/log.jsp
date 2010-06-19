<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>	
<script type="text/javascript" src="../dwr/interface/ReadLog.js"></script>
<div style="position:absolute;left:360px;top:220px;z-index:5;solid; width:600px;display:none;" id="log1">
	<TABLE cellSpacing=1 cellPadding=1 width="100%" class="basictable" id="logTable">																		
		<tr>							
			<th nowrap="nowrap" width="15%" align="center">
				操作员工
			</th>
			<th nowrap="nowrap" width="30%" align="center">
				操作时间
			</th>
			<th nowrap="nowrap" width="15%" align="center">
				操作
			</th>
			<th width="40%" align="center">
			<div id="logComm">备注</div><div id="logImg" style="display:none"><img  src="../resource/images/y88.gif"></div>
			</th>
		</tr>
	</table> 
	<iframe scrolling="no" style="position:absolute;z-index:-1;width:expression(this.parentNode.offsetWidth-2);height:0px;top:0px;left:0px;"
			frameborder="0"></iframe>
</div>  
<!-- log读取的代码 -->
<script type="text/javascript">	
function logTrHtml(){ //返回一行数据
	return '<tr>'
		+'<td align="center" id="item_<n>-slChangeEmpno-empName"></td>'
		+'<td align="center" id="item_<n>-slChangeTime"></td>'
		+'<td align="center" id="item_<n>-slAction"></td>'
		+'<td align="center" id="item_<n>-slComments"></td>'
		+'</tr>'; 
}

function LogShowDiv(no,tableName,ele){ //编号,表格,当前对象
	ReadLog.getLogs(no,tableName,callBackLog);//dwr方法
	function callBackLog(data){ //data是list
		var tableId="logTable"; //表格id
		if(model.simple.getObjectType(data)=="Array" && data.length>0){
			for(var i=0;i<data.length;i++){
				data[i]["slChangeTime"]=data[i]["slChangeTime"].toHRMTimeString(); //公用日期格式化方法
			}
			model.simple.initTableByList(tableId,"item",data,logTrHtml()); //调用model(将list装载入table)
		}else{
			var table=$("#"+tableId);
			table.find("tr:gt(0)").remove(); //移除标题以外的行
			table.append('<tr><td align="center" colspan="4">没有此条记录相关信息</td></tr>');
		}
		var position=model.simple.getElementPosition(ele);//当前元素位置
		var div=$("#log1");
	    var div_left=position.left-div.width()-10;
	    var div_top=position.top;
	    div.css("left",div_left+"px");
	    div.css("top",div_top+"px");
	    div.show(); //显示div
	}
}

function hideLog() { //隐藏div
	$("#logTable").find("tr:gt(0)").remove();  
	$("#log1").hide();
}
</script>