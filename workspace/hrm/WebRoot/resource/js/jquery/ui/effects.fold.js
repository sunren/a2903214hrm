/*
 * Compressed by JSA(www.xidea.org)
 */
(function($){$.effects.fold=function(_){return this.queue(function(){var J=$(this),D=["position","top","left"],F=$.effects.setMode(J,_.options.mode||"hide"),K=_.options.size||15,H=!(!_.options.horizFirst),M=_.duration?_.duration/2:$.fx.speeds._default/2;$.effects.save(J,D);J.show();var G=$.effects.createWrapper(J).css({overflow:"hidden"}),B=((F=="show")!=H),I=B?["width","height"]:["height","width"],A=B?[G.width(),G.height()]:[G.height(),G.width()],E=/([0-9]+)%/.exec(K);if(E)K=parseInt(E[1],10)/100*A[F=="hide"?0:1];if(F=="show")G.css(H?{height:0,width:K}:{height:K,width:0});var L={},C={};L[I[0]]=F=="show"?A[0]:K;C[I[1]]=F=="show"?A[1]:0;G.animate(L,M,_.options.easing).animate(C,M,_.options.easing,function(){if(F=="hide")J.hide();$.effects.restore(J,D);$.effects.removeWrapper(J);if(_.callback)_.callback.apply(J[0],arguments);J.dequeue()})})}})(jQuery)