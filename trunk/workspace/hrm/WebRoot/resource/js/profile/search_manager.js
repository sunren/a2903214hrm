/*
 * Compressed by JSA(www.xidea.org)
 */
eval(function(E,I,A,D,J,K,L,H){function C(A){return String.fromCharCode(A+=A<26?65:A<52?71:-4)}while(A>0)K[C(D--)]=I[--A];function N(A){return K[A]==L[A]?A:K[A]}if(''.replace(/^/,String)){var M=E.match(J),B=M[0],F=E.split(J),G=0;if(E.indexOf(F[0]))F=[''].concat(F);do{H[A++]=F[G++];H[A++]=N(B)}while(B=M[G]);H[A++]=F[G]||'';return H.join('')}return E.replace(J,N)}('R y;h showChooseManager_div(N){R A=i(g.x("0")).I,M=i(g.x("0")).J;g.x(N).W.top=M+20;g.x(N).W.left=A;g.x(N).W.f="block";u();invoke_drag("remark_hand","2")}h i(B){R A=B.p,N=B.k;while(B=B.offsetParent){A+=B.p;N+=B.k}R M=d.screen.height;O(N+500>M)N=N-300;return{"I":A,"J":N}}h v(){O(d.w)y=T w("Microsoft.XMLHTTP");else y=T XMLHttpRequest()}h 3(){O(y.readyState==5)O(y.status==200){l();1()}}h 1(){R C=y.responseXML,H=C.4("manager");S(R N=K;N<H.b;N++){R D=H[N],G=D.4("P")[K].m.V,M=D.4("name")[K].m.V,F=D.4("department")[K].m.V,B=D.4("j")[K].m.V,A=g.t("tr");A.onmouseover=h(){U.e="#DDF0F8";U.W.c="hand"};A.onmouseout=h(){U.e="#ECF3F6";U.W.c="pointer"};A.s("P",B);A.onclick=h(){q(U)};R E=g.t("Q");E.s("Z","o");E.s("a","a");E.r(g.z(G));A.r(E);E=g.t("Q");E.s("Z","o");E.s("a","a");E.r(g.z(M));A.r(E);E=g.t("Q");E.s("Z","o");E.s("a","a");E.r(g.z(F));A.r(E);g.x("Y").r(A)}}h u(){v();R N="../profile/managerListAction.action?searchConditon="+g.x("searchCondition").X;y.open("GET",N,true);y.onreadystatechange=3;y.send(null)}h q(N){R M=N.attributes["P"].X,A=N.n[L].m.V;g.x("empManagerId").X=M;O(g.x("j"))g.x("j").X=M;g.x("0").X=A;g.x("2").W.f="none"}h l(){R M=g.x("Y").n.b;S(R N=M-L;N>=K;N--)g.x("Y").removeChild(g.x("Y").n[N])}','x|y|0|1|_|$|if|id|td|var|for|new|this|data|style|value|items|class|nowrap|length|cursor|window|bgColor|display|document|function|getPoint|managerId|offsetTop|clearList|firstChild|childNodes|tablefield|offsetLeft|setManager|appendChild|setAttribute|createElement|searchManager|createRequest|ActiveXObject|getElementById|xmlHttpRequest|createTextNode|empManagerName|fillManagerList|chooseManager_div|handleManagerList|getElementsByTagName|4'.split('|'),50,57,/[\w\$]+/g,{},{},[]))