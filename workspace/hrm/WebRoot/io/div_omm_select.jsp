<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<!-- dwr -->
<script type="text/javascript" src="../dwr/interface/OutmatchModelEdit.js"></script>

<div id="divOutmatchModelSelect" style="position: absolute;z-index: 5; display: none;">
	<table id="outmatchModelListTable" width="10" height="0" border="0" cellspacing="0" cellpadding="0" class="contentMenu">
	</table>
</div>

<script type="text/javascript" language="javascript">
	function getOutmatchModelTrHtml(){
		return "<tr onClick='outmatchModelSelect(<n>)'>"
				+"	<input type='hidden' id='ommList_<n>-ommId' name='ommId'/>"
				+"	<td width='20' align='center'><img id='ommList_<n>-ommDefaultMean' src='' style='display:none'/></td>"
				+"	<td width='180' id='ommList_<n>-ommDesc' nowrap></td>"
				+"</tr>";
	}
	$("#btnOutput").bind("contextmenu",function(e){
//		var element=$("#btnOutput");
//		var offset=element.offset();
//		var xx=offset.left+element.width();
//		var yy=offset.top+element.height();
		//得到鼠标右键的位置
		var xx=e.originalEvent.x||e.originalEvent.layerX||0;
		var yy=e.originalEvent.y||e.originalEvent.layerY||0;
		//设定菜单位置
		var ommDiv=$("#divOutmatchModelSelect");
		ommDiv.css("left",xx+"px");
		ommDiv.css("top",yy+"px");
		var ommListLength=$("#outmatchModelListTable").attr("height");
		if(ommListLength==0){
			OutmatchModelEdit.getOutmatchModelList($("#output-ioName").val(),setContextMenu);
		}else if(ommListLength=="1"){
			return;
		}else{
			ommDiv.show();
		}
		return false;
		function setContextMenu(ommList) {
			var trHtml=getOutmatchModelTrHtml();
			var tableHtml="";
			var tableId="outmatchModelListTable";
			var tableOmmList=$("#"+tableId);
			tableOmmList.attr("height",ommList.length+"");
			if(ommList.length==1){
				return; 
			}
			var regS = new RegExp("<n>","gi");
			for(var i=0;i<ommList.length;i++){
				tableHtml+=trHtml.replace(regS,i);
			}
			tableOmmList.html(tableHtml);
			model.simple.initByList(tableId,"ommList",ommList);
			for(var i=0;i<ommList.length;i++){
				if(ommList[i].ommDefault==1){
					$("#ommList_"+i+"-ommDefaultMean").show();
					break;
				}
			}
			$("#divOutmatchModelSelect").show();
			$("#"+tableId+" tr").hover(
				function(){$(this).addClass("contentMenuTrHover")},
				function(){$(this).removeClass("contentMenuTrHover")}
			);
		};
	});
	function outmatchModelSelect(num){
		$("#output-ommId").val($("#ommList_"+num+"-ommId").val());
		var submitFun=eval($("#btnOutput").attr("onclick"));
		submitFun();
	}
	$(document).click( function () { $('#divOutmatchModelSelect').hide(); });
</script>