/*
 * Compressed by JSA(www.xidea.org)
 */
var OrgTree=function($){DHTreeClass.call(this,$)};OrgTree.prototype=DHTreeClass.prototype;OrgTree.prototype.lclick=function(A){var $=getNodeType(A),_=null;if($==0);else if($==1||$==2)_="editBranchDept.action?dept.id="+A;else if($==3)_="posManage.action?posId="+A;displayOrShowButton($);if(_!=null)refreshIFrame(_)}