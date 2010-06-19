//show one selected contectDiv
function showClassUl(ulObj,hrefObj){
	closeAllClassUl(document.getElementById('classbody'));
	hrefObj.style.backgroundPosition="left -46px";
	hrefObj.style.color="#FFFFFF";
	if(ulObj.style.display=="none")
		ulObj.style.display="block";
}

function closeAllClassUl(parentObj){
	var dtobj=parentObj.childNodes;
	for( i=0;i<dtobj.length;i++){
		var ulobj=dtobj[i].childNodes;
		for( j=0;j<ulobj.length;j++){
			//alert("ulobj.nodeName=="+ulobj[j].nodeName+":::ulobj.id=="+ulobj[j].id);
			if(ulobj[j].nodeName=='UL' && ulobj[j].id!=undefined && ulobj[j].id.indexOf('ulClass')==0 && ulobj[j].style.display=="block"){
				ulobj[j].style.display="none";
			}		
			if(ulobj[j].nodeName=='A'){
				ulobj[j].style.backgroundPosition="";
				ulobj[j].style.color="";
			}
		}
	}

}

function showClassByClassId(classId){
	closeAllClassUl(document.getElementById('classbody'));
	hrefObj.style.backgroundPosition="left -46px";
	hrefObj.style.color="#FFFFFF";
	if(ulObj.style.display=="none")
		ulObj.style.display="block";
}
function showFatherName(id){
	var fathermenu=document.getElementById("ulHref"+id);
	document.getElementById('father_menu').innerHTML=fathermenu.innerHTML;
}