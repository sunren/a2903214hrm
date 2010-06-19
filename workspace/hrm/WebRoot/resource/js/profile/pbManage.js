/*
 * Compressed by JSA(www.xidea.org)
 */
var PbTree=function($){DHTreeClass.call(this,$)};PbTree.prototype=DHTreeClass.prototype;PbTree.prototype.lclick=function(A){var $=getNodeType(A),_=null;if($==3||$==4)_="pbManage.action?pbId="+A;else _="showPbTable.action?deptId="+A;if(_!=null)refreshIFrame(_)}