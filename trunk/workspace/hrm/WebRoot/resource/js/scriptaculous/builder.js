/*
 * Compressed by JSA(www.xidea.org)
 */
var Builder={NODEMAP:{AREA:"map",CAPTION:"table",COL:"table",COLGROUP:"table",LEGEND:"fieldset",OPTGROUP:"select",OPTION:"select",PARAM:"object",TBODY:"table",TD:"table",TFOOT:"table",TH:"table",THEAD:"table",TR:"table"},node:function(B){B=B.toUpperCase();var A=this.NODEMAP[B]||"div",_=document.createElement(A);try{_.innerHTML="<"+B+"></"+B+">"}catch(C){}var $=_.firstChild||null;if($&&($.tagName.toUpperCase()!=B))$=$.getElementsByTagName(B)[0];if(!$)$=document.createElement(B);if(!$)return;if(arguments[1])if(this._isStringOrNumber(arguments[1])||(arguments[1]instanceof Array))this._children($,arguments[1]);else{var D=this._attributes(arguments[1]);if(D.length){try{_.innerHTML="<"+B+" "+D+"></"+B+">"}catch(C){}$=_.firstChild||null;if(!$){$=document.createElement(B);for(attr in arguments[1])$[attr=="class"?"className":attr]=arguments[1][attr]}if($.tagName.toUpperCase()!=B)$=_.getElementsByTagName(B)[0]}}if(arguments[2])this._children($,arguments[2]);return $},_text:function($){return document.createTextNode($)},ATTR_MAP:{"className":"class","htmlFor":"for"},_attributes:function($){var _=[];for(attribute in $)_.push((attribute in this.ATTR_MAP?this.ATTR_MAP[attribute]:attribute)+"=\""+$[attribute].toString().escapeHTML()+"\"");return _.join(" ")},_children:function($,_){if(typeof _=="object")_.flatten().each(function(_){if(typeof _=="object")$.appendChild(_);else if(Builder._isStringOrNumber(_))$.appendChild(Builder._text(_))});else if(Builder._isStringOrNumber(_))$.appendChild(Builder._text(_))},_isStringOrNumber:function($){return(typeof $=="string"||typeof $=="number")},build:function(A){var _=this.node("div");$(_).update(A.strip());return _.down()},dump:function(_){if(typeof _!="object"&&typeof _!="function")_=window;var $=("A ABBR ACRONYM ADDRESS APPLET AREA B BASE BASEFONT BDO BIG BLOCKQUOTE BODY "+"BR BUTTON CAPTION CENTER CITE CODE COL COLGROUP DD DEL DFN DIR DIV DL DT EM FIELDSET "+"FONT FORM FRAME FRAMESET H1 H2 H3 H4 H5 H6 HEAD HR HTML I IFRAME IMG INPUT INS ISINDEX "+"KBD LABEL LEGEND LI LINK MAP MENU META NOFRAMES NOSCRIPT OBJECT OL OPTGROUP OPTION P "+"PARAM PRE Q S SAMP SCRIPT SELECT SMALL SPAN STRIKE STRONG STYLE SUB SUP TABLE TBODY TD "+"TEXTAREA TFOOT TH THEAD TITLE TR TT U UL VAR").split(/\s+/);$.each(function($){_[$]=function(){return Builder.node.apply(Builder,[$].concat($A(arguments)))}})}}