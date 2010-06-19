<%@page pageEncoding="GBK" contentType="text/html; charset=GBK" %>
<%@taglib prefix="s" uri="webwork"%>
<html>
<head>
<link href="<s:url value="/resource/css/style.css"/>" rel="stylesheet" type="text/css" />
<title><s:property value="info.infoClass.infoclassName"/></title>
</head>
<body>
<br>
<table width="580px"  border="0" cellspacing="1" cellpadding="5" class="formtable">
  <tr>
    <td width="100" align="left" class="grayFont">信息标题：</td>
    <td><h2><s:property value="info.infoTitle"/></h2></td>
  </tr>	
  <tr>
    <td width="100" align="left" class="grayFont">提交者：</td>
    <td><s:property value="info.infoCreateBy.empName"/></td>
  </tr>	
  <tr>
    <td width="100" align="left" class="grayFont">创建时间:</td>
    <td><s:date name="info.infoCreateTime" format="yyyy-MM-dd " /></td>
  </tr>	    
	<s:if test="info.infoFileName!=null && info.infoFileName!=''">  
	  <tr>
	    <td colspan="5"   class="grayFont">下载附件:<a href="downloadFile.action?fileName=<s:property value="info.infoFileName"/>">点击下载</a></td>
	  </tr>
	  <s:if test="info.infoFileDesc!=null && info.infoFileDesc!=''"> 
		  <tr>
		    <td width="100" align="right" class="grayFont">说明</td>
		    <td><s:property value="info.infoFileDesc"/></td>
		  </tr>
		</s:if>
	</s:if>
	
</table>
<table width="580px"  border="0" cellspacing="1" cellpadding="5" >  
  <tr>
  <td>
    	<s:if test="info.infoPicName!=null && info.infoPicName!=''">
    	<img src='../servlet/showImage?style=news&fileName=<s:property value="info.infoPicName"/>'><br>
	    	<s:if test="info.infoFileDesc!=null && info.infoFileDesc!=''"> 
			  <s:property value="info.infoPicDesc"/>
		    </s:if>
    	</s:if>  	 		
    </td>
  </tr>
  <tr>
  <td align="left" width="80%"><s:property value="info.infoContent" escape="false"/>  </td>
  </tr>
</table>
<table width="100%"  border="0" cellspacing="1" cellpadding="5" class="formtable"> 
	
</table>

</body>
</html>
