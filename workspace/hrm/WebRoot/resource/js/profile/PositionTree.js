/*
 * Compressed by JSA(www.xidea.org)
 */
var PositionTree=function($){DHTreeClass.call(this,$)};PositionTree.prototype=DHTreeClass.prototype;PositionTree.prototype.lclick=function(A){var $=getNodeType(A),_=null;if($==5||$==6||$==7)_="positionInfo.action?positionId="+A;else _="about:blank";if(_!=null)refreshIFrame(_)}