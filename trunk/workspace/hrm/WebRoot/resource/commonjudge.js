//disable all the element in the certain form: form should be assigned to "document.formname"
function formDisableAll(form){
  var oForm=form.elements;
  for( var i=0;i<oForm.length;i++ ){
      oForm[i].disabled=true;
  }
}

//judge whether a string is an int
function is_int(str){
	if(isNaN(str))
		return false;
	if( str.indexOf(".")!=-1 )
		return false;
	if(str.length>1 && str.charAt(0)=="0" )
		return false;
	if(str.length>2 && str.charAt(0)=="-" && str.charAt(1)=="0" )
		return false;
	return true;
}

//judge whether a string is an int. 0 can be filled in the prefix.
function is_int_zeorfill(str){
	if(isNaN(str))
		return false;
	if( str.indexOf(".")!=-1 )
		return false;
	return true;
}


//judge whether a string is a float
function is_float(str){
	if(isNaN(str))
		return false;
	if(str.length>1 && str.charAt(0)=="0" && str.charAt(1)!="." )
		return false;
	if(str.length>2 && str.charAt(0)=="-" && str.charAt(1)=="0" && str.charAt(2)!="." )
		return false;
	return true;
}

//judge whether a string is a float. 0 can be filled in the prefix.
function is_float_zeorfill(str){
	if(isNaN(str))
		return false;
	return true;
}

//judge whether a string is an email
function is_email(str)
{
	if(str.length<7)
		return false;
			
	if (str.indexOf("'")!=-1)
		return false;

	if (str.indexOf("@")==-1)
		return false;
	else if(str.charAt(0)=="@")
		return false;
	else if(str.length-1-str.lastIndexOf("@")<5)
		return false;
	
	return true;
}

//judge whether a string is a float
function is_date_ymd(nYear, nMonth, nDay){
	if ((nMonth > 12) || (nMonth < 1) || (nDay < 1) || (nDay > 31))
		return false;
	
	switch (nMonth){
		case 4 :
		case 6 :
		case 9 :
		case 11 :{
			return (nDay < 31);
			break;
		}
		case 2 :{
			if (0 == nYear%4){
				if ((0 == nYear%100) && (0 != nYear%400))
					return (nDay < 29);
				else
					return (nDay < 30);
			}
			return (nDay < 29);
			break;
		}
		default:
			return true;
	}
}

//judge a date which is made of year, month, and day
function is_date_str(str){
	if (str.length<6 || str.length>10 || str.indexOf("-")==-1 || str.indexOf("-")==str.lastIndexOf("-"))
		return false;
	var year=str.substring(0,str.indexOf("-"));
	var month=str.substring(str.indexOf("-")+1,str.lastIndexOf("-"));
	var day=str.substring(str.lastIndexOf("-")+1,str.length);
	if(year.length==2)
		year="20"+year;
	if ( year.length!=4 || !is_int(year) )
		return false;
	if ( !is_int_zeorfill(month) )
		return false;
	if ( !is_int_zeorfill(day) )
		return false;
	return is_date_ymd(year, month, day);
}

//get rid of the character "space" at prefix and suffix
function trim_string(str){
	var i,strlength,t,chartemp,returnstr;
	strlength=str.length;
	t=str;
	for(i=0;i<strlength;i++){
		chartemp=str.substring(i,i+1);		
		if(chartemp==" ")
			t=str.substring(i+1,strlength);
		else
			 break;
	}
	returnstr=t;
	strlength=t.length;
	for(i=strlength;i>=0;i--){
		chartemp=t.substring(i,i-1);
		if(chartemp==" ")
			returnstr=t.substring(i-1,0);
		else
			break;
	}
	return (returnstr);
}

//validate string as a string
//str: str to be validate
//minlen: the min length of the string. 0 means empty
//maxlen: the max length of the string
function check_string_base(str,minlen,maxlen){
	var errmsg="";
	str=trim_string(str);
	if( errmsg.length==0 && str.length==0 ){
		if( minlen==0 ){
			return errmsg;
		}else{
			errmsg="不能为空";
		}
	}
	
	if( errmsg.length==0 && str.length<minlen ){
		errmsg="至少要"+minlen+"个字符";
	}
		
	if( errmsg.length==0 && str.length>maxlen ){
		errmsg="不能超过"+maxlen+"个汉字";
	}
	
	return errmsg;
}



//validate string as a email
//str: str to be validate
//is_empty: true, the element can be empty; false, the element can't be empty
function check_email_base(str,is_empty){
	var errmsg="";
	str=trim_string(str);
	
	if( errmsg.length==0 && str.length==0 ){
		if(is_empty){
			return errmsg;
		}
		else{
			errmsg="不能为空";
		}
	}
	
	if( errmsg.length==0 && !is_email(str) )
		errmsg="格式不正确";

	return errmsg;
}


//validate string as an int
//str: str to be validate
//is_empty: true, the element can be empty; false, the element can't be empty
//minvalue: the min value of the int
//maxvalue: the max value of the int
function check_int_base(str,minvalue,maxvalue,is_empty){
	var errmsg="";
	str=trim_string(str);
	if( errmsg.length==0 && str.length==0 ){
		if(is_empty){
			return errmsg;
		}
		else{
			errmsg="不能为空";
		}
	}
	
	if( errmsg.length==0 && !is_int(str) ){
		errmsg="必须为数字";
	}
	
	if( errmsg.length==0 && str<minvalue ){
		errmsg="不能小于"+minvalue;
	}
		
	if( errmsg.length==0 && str>maxvalue ){
		errmsg="不能超过"+maxvalue;
	}
	
	return errmsg;
}


//validate string as a float
//str: str to be validate
//is_empty: true, the element can be empty; false, the element can't be empty
//decimal_fraction: the max length of decimal fraction. 0 means the decimal fraction length is no limit
//minvalue: the min value of the element
//maxvalue: the max value of the element
function check_float_base(str,minvalue,maxvalue,is_empty,decimal_fraction){
	var errmsg="";
	str=trim_string(str);
	
	if( errmsg.length==0 && str.length==0 ){
		if(is_empty){
			return errmsg;
		}
		else{
			errmsg="不能为空";
		}
	}
	
	if( errmsg.length==0 && !is_float(str) ){
		errmsg="必须为数字";
	}
	
	if( errmsg.length==0 && decimal_fraction>0 && str.indexOf(".")!=-1 && str.length-1-str.indexOf(".")>decimal_fraction ){
		errmsg="小数位数不能超过"+decimal_fraction+"位";
	}
	
	
	if( errmsg.length==0 && str<minvalue ){
		errmsg="不能小于"+minvalue;
	}
		
	if( errmsg.length==0 && str>maxvalue ){
		errmsg="不能超过"+maxvalue;
	}
	
	return errmsg;
}

//validate string as a date
//str: str to be validate
//is_empty: true, the element can be empty; false, the element can't be empty
function check_date_base(str,is_empty){
	var errmsg="";
	str=trim_string(str);
	
	if( errmsg.length==0 && str.length==0 ){
		if(is_empty){
			return errmsg;
		}
		else{
			errmsg="不能为空";
		}
	}
	
	if( errmsg.length==0 && !is_date_str(str) )
		errmsg="必须为日期，如2006-10-1";

	return errmsg;
}


//validate the form element as a string
//elementid: the element's name in the form
//minlen: the min length of the string. 0 means empty
//maxlen: the max length of the string
//desc: the description of the element
function check_string(elementid,desc,minlen,maxlen){
	var errmsg="";
	var tmpelement=document.getElementById(elementid);
	tmpelement.value=trim_string(tmpelement.value);
	if( errmsg.length==0 && tmpelement.value.length==0 ){
		if( minlen==0 ){
			return true;
		}else{
			errmsg=desc+"不能为空";
		}
	}
	
	if( errmsg.length==0 && tmpelement.value.length<minlen ){
		errmsg=desc+"至少要"+minlen+"个字符";
	}
		
	if( errmsg.length==0 && tmpelement.value.length>maxlen ){
		errmsg=desc+"不能超过"+maxlen+"个汉字";
	}
	
	if( errmsg.length>0 ){
		alert(errmsg);
		tmpelement.focus();
		return false;
	}else{
		return true;
	}
}



//validate the form element as a email
//elementid: the element's name in the form
//is_empty: true, the element can be empty; false, the element can't be empty
//desc: the description of the element
function check_email(elementid,desc,is_empty){
	var errmsg="";
	var tmpelement=document.getElementById(elementid);
	tmpelement.value=trim_string(tmpelement.value);
	
	if( errmsg.length==0 && tmpelement.value.length==0 ){
		if(is_empty){
			return true;
		}
		else{
			errmsg=desc+"不能为空";
		}
	}
	
	if( errmsg.length==0 && !is_email(tmpelement.value) )
		errmsg=desc+"格式不正确";

	if( errmsg.length>0 ){
		alert(errmsg);
		tmpelement.focus();
		return false;
	}else{
		return true;
	}
}


//validate the form element as an int
//elementid: the element's name in the form
//is_empty: true, the element can be empty; false, the element can't be empty
//minvalue: the min value of the int
//maxvalue: the max value of the int
//desc: the description of the element
function check_int(elementid,desc,minvalue,maxvalue,is_empty){
	var errmsg="";
	var tmpelement=document.getElementById(elementid);
	tmpelement.value=trim_string(tmpelement.value);
	if( errmsg.length==0 && tmpelement.value.length==0 ){
		if(is_empty){
			return true;
		}
		else{
			errmsg=desc+"不能为空";
		}
	}
	
	if( errmsg.length==0 && !is_int(tmpelement.value) ){
		errmsg=desc+"必须为数字";
	}
	
	if( errmsg.length==0 && tmpelement.value<minvalue ){
		errmsg=desc+"不能小于"+minvalue;
	}
		
	if( errmsg.length==0 && tmpelement.value>maxvalue ){
		errmsg=desc+"不能超过"+maxvalue;
	}
	
	if( errmsg.length>0 ){
		alert(errmsg);
		tmpelement.focus();
		return false;
	}else{
		return true;
	}
}


//validate the form element as a float
//elementid: the element's name in the form
//is_empty: true, the element can be empty; false, the element can't be empty
//decimal_fraction: the max length of decimal fraction. 0 means the decimal fraction length is no limit
//minvalue: the min value of the element
//maxvalue: the max value of the element
//desc: the description of the element
function check_float(elementid,desc,minvalue,maxvalue,is_empty,decimal_fraction){
	var errmsg="";
	var tmpelement=document.getElementById(elementid);
	tmpelement.value=trim_string(tmpelement.value);
	
	if( errmsg.length==0 && tmpelement.value.length==0 ){
		if(is_empty){
			return true;
		}
		else{
			errmsg=desc+"不能为空";
		}
	}
	
	if( errmsg.length==0 && !is_float(tmpelement.value) ){
		errmsg=desc+"必须为数字";
	}
	
	if( errmsg.length==0 && decimal_fraction>0 && tmpelement.value.indexOf(".")!=-1 && tmpelement.value.length-1-tmpelement.value.indexOf(".")>decimal_fraction ){
		errmsg=desc+"小数位数不能超过"+decimal_fraction+"位";
	}
	
	
	if( errmsg.length==0 && tmpelement.value<minvalue ){
		errmsg=desc+"不能小于"+minvalue;
	}
		
	if( errmsg.length==0 && tmpelement.value>maxvalue ){
		errmsg=desc+"不能超过"+maxvalue;
	}
	
	if( errmsg.length>0 ){
		alert(errmsg);
		tmpelement.focus();
		return false;
	}else{
		return true;
	}
}

//validate the form element as a date
//elementid: the element's name in the form
//is_empty: true, the element can be empty; false, the element can't be empty
//desc: the description of the element
function check_date(elementid,desc,is_empty){
	var errmsg="";
	var tmpelement=document.getElementById(elementid);
	tmpelement.value=trim_string(tmpelement.value);
	
	if( errmsg.length==0 && tmpelement.value.length==0 ){
		if(is_empty){
			return true;
		}
		else{
			errmsg=desc+"不能为空";
		}
	}
	
	if( errmsg.length==0 && !is_date_str(tmpelement.value) )
		errmsg=desc+"必须为日期，如2006-10-1";

	if( errmsg.length>0 ){
		alert(errmsg);
		tmpelement.focus();
		return false;
	}else{
		return true;
	}
}