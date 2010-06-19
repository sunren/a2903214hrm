/*
 * Compressed by JSA(www.xidea.org)
 */
(function($){$.effects.transfer=function(_){return this.queue(function(){var A=$(this),D=$(_.options.to),C=D.offset(),F={top:C.top,left:C.left,height:D.innerHeight(),width:D.innerWidth()},B=A.offset(),E=$("<div class=\"ui-effects-transfer\"></div>").appendTo(document.body).addClass(_.options.className).css({top:B.top,left:B.left,height:A.innerHeight(),width:A.innerWidth(),position:"absolute"}).animate(F,_.duration,_.options.easing,function(){E.remove();(_.callback&&_.callback.apply(A[0],arguments));A.dequeue()})})}})(jQuery)