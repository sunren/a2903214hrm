<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<script type="text/javascript" src="../resource/js/message.js"></script>
<script type="text/javascript" language="javascript">
var msgTopList=new Array();
msgTopList[0]=new Array();
msgTopList[1]=new Array();
var msgDetailList=new Array();
msgDetailList[0]=new Array();
msgDetailList[1]=new Array();
var topListLength=0;
var detailListLength=0;
<s:iterator value="msgTopList" status="index">
msgTopList[0][topListLength]=<s:property value="msgType" />;
msgTopList[1][topListLength++]="<s:property value="msgContent" />";
</s:iterator>
<s:iterator value="msgDetailList" status="index">
msgDetailList[0][detailListLength]=<s:property value="msgType" />;
msgDetailList[1][detailListLength++]="<s:property value="msgContent" />";
</s:iterator>
var topMsgContent="";
for(var i=0;i<topListLength;i++){
	if(msgTopList[0][i]==0){
		topMsgContent+="<img src='../resource/images/default_yes.png' /> "+msgTopList[1][i]+"<br />";
	}else if(msgTopList[0][i]>0){
		topMsgContent+="<img src='../resource/images/icon_warning.gif' /> "+msgTopList[1][i]+"<br />";
	}else if(msgTopList[0][i]<0){
		topMsgContent+="<img src='../resource/images/default_no.png' /> "+msgTopList[1][i]+"<br />";
	}
}
var detailMsgContent="";
for(var i=0;i<detailListLength;i++){
	detailMsgContent+=msgDetailList[1][i]+"\n";
}
parent.document.getElementById("immUploadTopMsgShow").innerHTML =  topMsgContent; 
if(detailListLength==0){
	parent.document.getElementById("trImmUploadDetailMsg").style.display= "none"; 
	parent.document.getElementById("immUploadDetailMsgShow").value = ""; 
}else{
	parent.document.getElementById("trImmUploadDetailMsg").style.display= "block"; 
	parent.document.getElementById("immUploadDetailMsgShow").value =  detailMsgContent; 
}
parent.document.getElementById("divWait").style.display= "none"; 
parent.document.getElementById("divImmUploadResult").style.display= "block"; 
</script>
