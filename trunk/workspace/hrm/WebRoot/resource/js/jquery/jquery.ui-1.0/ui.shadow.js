/*
 * Compressed by JSA(www.xidea.org)
 */
(function($){$.ui=$.ui||{};$.extend($.expr[":"],{shadowed:"(' '+a.className+' ').indexOf(' ui-shadowed ')"});$.fn.shadowEnable=function(){if($(this[0]).next().is(".ui-shadow"))$(this[0]).next().show()};$.fn.shadowDisable=function(){if($(this[0]).next().is(".ui-shadow"))$(this[0]).next().hide()};$.fn.shadow=function(_){_=_||{};_.offset=_.offset?_.offset:0;_.opacity=_.opacity?_.opacity:0.2;return this.each(function(){var D=$(this),G=$("<div class='ui-shadow'></div>");D.after(G);var B=D.outerWidth(),F=D.outerHeight(),C=D.position();$("<div class=\"ui-shadow-color ui-shadow-layer-1\"></div>").css({opacity:_.opacity-0.05,left:5+_.offset,top:5+_.offset,width:B+1,height:F+1}).appendTo(G);$("<div class=\"ui-shadow-color ui-shadow-layer-2\"></div>").css({opacity:_.opacity-0.1,left:7+_.offset,top:7+_.offset,width:B,height:F-3}).appendTo(G);$("<div class=\"ui-shadow-color ui-shadow-layer-3\"></div>").css({opacity:_.opacity-0.1,left:7+_.offset,top:7+_.offset,width:B-3,height:F}).appendTo(G);$("<div class=\"ui-shadow-color ui-shadow-layer-4\"></div>").css({opacity:_.opacity,left:6+_.offset,top:6+_.offset,width:B-1,height:F-1}).appendTo(G);if(_.color)$("div.ui-shadow-color",G).css("background-color",_.color);if(!D.css("zIndex")||D.css("zIndex")=="auto"){var E=0;D.css("position",(D.css("position")=="static"?"relative":D.css("position"))).css("z-index","1")}else{E=parseInt(D.css("zIndex"));D.css("zIndex",E+1)}G.css({position:"absolute",zIndex:E,left:C.left,top:C.top,width:B,height:F,marginLeft:D.css("marginLeft"),marginRight:D.css("marginRight"),marginBottom:D.css("marginBottom"),marginTop:D.css("marginTop")});function A(A,_){var B=$(A);$(_).css(B.position());$(_).children().css({height:B.outerHeight()+"px",width:B.outerWidth()+"px"})}if($.browser.msie){G[0].style.setExpression("left","parseInt(jQuery(this.previousSibling).css('left'))+'px' || jQuery(this.previousSibling).position().left");G[0].style.setExpression("top","parseInt(jQuery(this.previousSibling).css('top'))+'px' || jQuery(this.previousSibling).position().top")}else this.addEventListener("DOMAttrModified",function(){A(this,G)},false)})}})($)