/*
 * Compressed by JSA(www.xidea.org)
 */
function HashMap(){var _=0,$=new Object();this.put=function(A,B){if(!this.containsKey(A))_++;$[A]=B};this.get=function(_){return this.containsKey(_)?$[_]:null};this.remove=function(A){if(this.containsKey(A)&&(delete $[A]))_--};this.containsKey=function(_){return(_ in $)};this.containsValue=function(_){for(var A in $)if($[A]==_)return true;return false};this.values=function(){var A=new Array();for(var _ in $)A.push($[_]);return A};this.keys=function(){var _=new Array();for(var A in $)_.push(A);return _};this.size=function(){return _};this.clear=function(){_=0;$=new Object()}}