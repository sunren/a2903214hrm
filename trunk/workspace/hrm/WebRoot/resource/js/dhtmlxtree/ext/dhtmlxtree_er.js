/*
 * Compressed by JSA(www.xidea.org)
 */
var _all_used_trees=new Array();dhtmlXTreeObject.prototype._createSelfA2=dhtmlXTreeObject.prototype._createSelf;dhtmlXTreeObject.prototype._createSelf=function(){_all_used_trees[_all_used_trees.length]=this;return this._createSelfA2()};window.onerror=function(_,C,A,$){var $=document.createElement("DIV");$.style.cssText="position:absolute;background-color:white;top:10px;left:10px;z-index:20;width:500px;border: 2px silver outset;";var B="<div style='width:100%;color:red;font-size:8pt;font-family:Arial;font-weight:bold;'>Javascript Error</div>";B+="<div style='width:100%;font-size:8pt;font-family:Arial;'>The next error ocured :<br/> <strong>"+_+"</strong> in <strong>"+C+"</strong> at line <strong>"+A+"</strong></div>";B+="<div style='width:100%;font-size:8pt;font-family:Arial;'>If you think that error can be caused by dhtmlxtree press the 'Generate report' button and send generated report to <a href='email:support@dhtmlx.com'>support@dhtmlx.com</a> </div>";B+="<input style='font-size:8pt;font-family:Arial;' onclick='dhtmlxtreeReport(this)' type='button' value='Generate report'/><input style='font-size:8pt;font-family:Arial;' type='button' value='Close' onclick='this.parentNode.parentNode.removeChild(this.parentNode);'/>";B+="<div/>";$.innerHTML=B;document.body.appendChild($);return true};function dhtmlxtreeErrorReport($,A,_){var B=$+" ["+A+"]";if($=="LoadXML")B+="<br/>"+_[0].responseText+"</br>"+_[0].status;window.onerror(B,"none","none")}function dhtmlxtreeReport(D){var A=D.parentNode;A.lastChild.innerHTML="<textarea style='width:100%;height:300px;'></textarea>";var B=A.childNodes[1].innerHTML;for(var C=0;C<_all_used_trees.length;C++){var H=_all_used_trees[C];B+="\n\n Tree "+C+"\n";for(b in H){if(typeof(H[b])=="function")continue;B+=b+"="+H[b]+"\n"}B+="---------------------\n";if(H.XMLLoader){try{var $=H.XMLLoader.getXMLTopNode("tree");if(document.all)B+=$.xml+"\n";else{var G=new XMLSerializer();B+=G.serializeToString($)+"\n"}}catch(I){B+="XML not recognised\n"}}B+="---------------------\n";for(var _ in H._idpull){var F=H._idpull[_];if(typeof(F)!="object")continue;B+="Node: "+F.id;B+=" Childs: "+F.childsCount;for(var E=0;E<F.childsCount;E++)B+=" ch"+E+":"+F.childNodes[E].id;B+="\n"}}A.lastChild.childNodes[0].value=B}dhtmlxError.catchError("ALL",dhtmlxtreeErrorReport)