/*
 * Compressed by JSA(www.xidea.org)
 */
var ChoosePositionTree=function($){DHTreeClass.call(this,$)};ChoosePositionTree.prototype=DHTreeClass.prototype;var treeNodeArray=new Array();ChoosePositionTree.prototype.lclick=function(){var _=this.getSelectedItemId(),$=ChoosePositionTree.posId,A;if(confirm("\u60a8\u786e\u5b9a\u8981\u66f4\u6539\u5417\uff1f"))positionManage.changePosSup($,_,function(B){if(B.indexOf("SUCC,")>=0){var C=B.split(",");_=B.split(",")[1];A=B.split(",")[2];if(A==0)tree.moveItem($,"item_child",_);url="positionInfo.action?positionId="+$;refreshIFrame(url);alert("\u4fee\u6539\u6210\u529f\uff01")}else alert(B);document.getElementById(ChoosePositionTree.divId).style.display="none"})}