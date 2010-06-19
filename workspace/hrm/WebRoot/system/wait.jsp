<%@ page contentType="text/html; charset=GBK" errorPage="/error.jsp" language="java" pageEncoding="GBK"%>
<%@ taglib uri="sitemesh-decorator" prefix="decorator"%>
<%@ taglib uri="sitemesh-page" prefix="page"%>
<%@ taglib prefix="ww" uri="webwork"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<html>
<head>
<%response.setHeader("Cache-Control", "no-cache");
  response.setHeader("Pragma", "no-cache");
%>
<title>页面载入中...</title>
<meta http-equiv="refresh" content="2;url=<ww:url includeParams='all'/>"/>
<link href="<ww:url value="/resource/css/style.css"/>" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<ww:url value="/resource/js/util.js"/>"></script>
<STYLE type=text/css>
TD {
    FONT-FAMILY: 宋体; FONT-SIZE: 12px; LETTER-SPACING: 2px; LINE-HEIGHT: 150%; font-color: #000000
}
FORM {
    FONT-FAMILY: 宋体; FONT-SIZE: 12px; LETTER-SPACING: 2px; LINE-HEIGHT: 150%; font-color: #000000
}
SELECT {
    FONT-FAMILY: 宋体; FONT-SIZE: 12px; LETTER-SPACING: 2px; LINE-HEIGHT: 150%; font-color: #000000
}
INPUT {
    FONT-FAMILY: 宋体; FONT-SIZE: 12px; LETTER-SPACING: 2px; LINE-HEIGHT: 150%; font-color: #000000
}
TEXTAREA {
    FONT-FAMILY: 宋体; FONT-SIZE: 12px; LETTER-SPACING: 2px; LINE-HEIGHT: 150%; font-color: #000000
}
BODY {
    FONT-FAMILY: 宋体; FONT-SIZE: 12px; LETTER-SPACING: 2px; LINE-HEIGHT: 150%; font-color: #000000
}
A:link {
    COLOR: #666666; FONT-SIZE: 10.5pt; TEXT-DECORATION: none
}
A:visited {
    COLOR: #666666; FONT-SIZE: 10.5pt; TEXT-DECORATION: none
}
A:hover {
    COLOR: #666666; FONT-SIZE: 10.5pt; TEXT-DECORATION: none
}
A:active {
    COLOR: #666666; FONT-SIZE: 10.5pt; TEXT-DECORATION: none
}
</STYLE>
<META content="Microsoft FrontPage 4.0" name=GENERATOR>
<STYLE>
.proccess {
    BACKGROUND: #ffffff; BORDER-BOTTOM: 1px solid; BORDER-LEFT: 1px solid; BORDER-RIGHT: 1px solid; BORDER-TOP: 1px solid; HEIGHT: 8px; MARGIN: 3px; WIDTH: 8px
}
</STYLE>
</HEAD>
<BODY style="OVERFLOW: hidden; OVERFLOW-Y: hidden">
<DIV align=center>
<TABLE align=center height="70%" valign="middle">
  <TBODY>
  <TR>
    <TD align=middle><p></p>
     	<DIV align=center> 
            <FORM method=post name=proccess>
              <SCRIPT language=javascript>
				for(i=0;i<30;i++)document.write("<input class=proccess>")
			  </SCRIPT>
            </FORM>
          </DIV>
          </font></TD>
    </TR>
  </TBODY>
</TABLE>
<DIV align=center>
	<SCRIPT language=JavaScript>
	<!--
	var p=0,j=0;
	var c=new Array("lightskyblue","white")
	setInterval('proccess();',100)
	function proccess(){
	    document.forms.proccess.elements[p].style.background=c[j];
	    p+=1;
	    if(p==30){p=0;j=1-j;}}
	-->
	</SCRIPT>
</DIV>
</DIV>
</BODY>
</html>
