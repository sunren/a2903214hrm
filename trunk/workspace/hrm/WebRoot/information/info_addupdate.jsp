<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<%@ taglib prefix="ty" uri="/tengsource"%>
<%@ taglib uri="http://fckeditor.net/tags-fckeditor" prefix="FCK"%>
<html>
<head>
	<link href="<s:url value="/resource/css/index.css"/>" rel="stylesheet" type="text/css" />
	<title>创建资料</title>
</head>	
<body>
<s:if test="errMsg!=null && errMsg!=''">
	<script>errMsg("errMsg","<s:property value="errMsg"/>");</script>
</s:if>
	
<s:component template="bodyhead">
	<s:param name="pagetitle" value="info==null?'新增公司信息：':'修改公司信息'" />
	<s:param name="helpUrl" value="'13'" />
</s:component>
<s:form name="createNews" id="createNews" action="createInfo" namespace="/information" method="POST" enctype="multipart/form-data">
<s:token />
<s:hidden id="info_id" name="info.id" />
<s:hidden name="info.infoViewedNum" />
<s:hidden name="info.infoCreateBy.id" />
<s:hidden name="info.infoCreateTime" />
<s:hidden name="info.infoContent" id="infoContent" />
<s:hidden id="info.infoTag" name="info.infoTag" />

<table width="92%">
	<tr>
		<td width="86%">
			<table width="86%" border="0" cellspacing="1" cellpadding="5">
				<tr>
					<td>
						<table>
							<tr>
								<td>
									<table>
										<tr>
											<td align="left" nowrap>
												隶属分类<font color="red">*</font>:
											</td>
											<td>
												<s:select id="info_infoClass_id"
													name="info.infoClass.id" list="classList" listKey="id"
													required="true" listValue="infoclassName"
													multiple="false" emptyOption="true" size="1" />
											</td>
										</tr>
										<tr>
											<td align="left" nowrap>
												状态信息<font color="red">*</font>:
											</td>
											<td>
												<s:select id="info_infoStatus" name="info.infoStatus"
													value="info.infoStatus" required="true"
													list="#{1:'正常', 0:'隐藏'}" />
											</td>
										<tr>
									</table></td>
									<td width="80"></td>
									<td >
										<table >
											<tr>
												<td nowrap valign="center">
													限制部门<font color="red">*</font>:
												</td>
												<td>
													<s:select id="tag_dep" name="tag_dep" cssStyle="width:100;height:70"
														cssClass="formtable" list="departmentList"
														listKey="departmentName" listValue="departmentName"
														ondblclick="fun_select_addany(document.getElementById('tag_dep'))"
														multiple="true" size="5" />
												</td>
												<td nowrap>
													<img src="../resource/images/callt.gif"
														style="cursor: pointer"
														onclick="fun_select_dltany(document.getElementById('infoTag'))">
													<img src="../resource/images/calrt.gif"
														style="cursor: pointer"
														onclick="fun_select_addany(document.getElementById('tag_dep'))">
												</td>
												<td >
													<select id="infoTag" name="infoTag" cssClass="formtable" style="width:100;height:70"
															ondblclick="fun_select_dltany(document.getElementById('infoTag'))" size="5" multiple="true"></select>
												</td>
											</tr>
										</table>
									</td>
								</tr>
						  </table>
						</tr>
						<tr>
							<td align="center">
								<table width="553" height="84">
									<tr>
										<td align="left" nowrap>
											标题<font color="red">*</font>:
										</td>
										<td>
											<s:textfield id="info_infoTitle" name="info.infoTitle"
												required="true" size="70" maxlength="73" />
										</td>
									</tr>
									<tr>
										<td align="left" nowrap>
											简述<font color="red">*</font>:
										</td>
										<td>
											<s:textarea id="info_infoBreif" name="info.infoBreif"
												required="true" cols="58" rows="2" />
										</td>
									</tr>
									<tr>
										<td align="left" valign="top" nowrap>
											正文<font color="red">*</font>:
										</td>
										<td border="2">
											<s:richtexteditor toolbarCanCollapse="false" height="200"
												width="500" id="newsAdd"
												name="newsAdd" value=""
												defaultLanguage="zh-cn" />
											<script>
  												$('#newsAdd').val($('#infoContent').val());
											</script>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td align="left">
								<table width="76%">
									<tr>
										<td nowrap>
											上传附件:<s:file id="fileName"  name="file" />
										</td>
										<td nowrap>
											上传图片:<s:file  id="imgName"  name="imgFile" />
										</td>
									</tr>
									<tr>
										<td nowrap>
											附件说明:
											<s:textfield id="info_infoFileDesc" name="info.infoFileDesc" required="false"
												size="29" maxlength="255" />
										</td>
										<td nowrap>
											图片说明:<s:textfield id="info_infoPicDesc" name="info.infoPicDesc"
											 required="false" size="29" maxlength="255" />
										</td>
									</tr>
								</table>
							</td>
						</tr>
						
						<tr align="center">
							<td height="1" colspan="5">
							<s:if test="info==null">
								<input name="Submit" type="button" class="button"
									onclick="submitForm();" value="增加">
							</s:if>
							<s:else>
								<input name="Submit" type="button" class="button"
									onclick="submitForm();" value="修改">
							</s:else>
								<input name="Submit" type="button" class="button"
									onclick="location.href='searchInfo.action';" value="返回">
							</td>
						</tr>
					</table>
				</td>
				<td align="right" width="10%" valign="top">
					<!-- 信息类别列表 bgn -->
					<table cellpadding="0" cellspacing="0" width="150"
						class="formtable">
						<tr>
							<td>
								<a href="searchInfo.action"><img
										src='../resource/images/ProspectLists.gif' border="0">&nbsp;查看所有</a>
							</td>
						</tr>
						<s:iterator value="classList" status="index">
							<tr>
								<td>
									<a href="searchInfo.action?classId=<s:property value="id"/>"><img
											src="../resource/images/ProspectLists.gif" border="0">
										<s:property value="infoclassName" /> </a>
									<br>
								</td>
							</tr>
						</s:iterator>
					</table>							
				</td>
			</tr>
</table>
</s:form>
<script type="text/javascript">
function getEditorHTMLContents(EditorName) {
    var oEditor = FCKeditorAPI.GetInstance(EditorName);
    return(oEditor.GetXHTML(true));
}
// 获取编辑器中文字内容
function getEditorTextContents(EditorName) {
    var oEditor = FCKeditorAPI.GetInstance(EditorName);
    return(oEditor.EditorDocument.body.innerHTML);
}
// 设置编辑器中内容
function SetEditorContents(EditorName, ContentStr) {
    var oEditor = FCKeditorAPI.GetInstance(EditorName) ;
    oEditor.SetHTML(ContentStr) ;
}

//显示已有tag,删除列表框中相应tag
var tagObj=document.getElementById('info.infoTag');
var tagContent=tagObj.value.trim();
if(tagContent.length>0){
	var tagArray=tagContent.split(",");
	for(k=0;k<tagArray.length;k++){
		if(tagArray[k].trim()!=''){
			document.getElementById('infoTag').options[document.getElementById('infoTag').length]=new Option(tagArray[k].trim());
			for (i=0;i<document.getElementById('tag_dep').length;i++){   
               if (document.getElementById('tag_dep').options[i].text == tagArray[k].trim()){   
            	   document.getElementById('tag_dep').remove(i);   
                    i--;   
               }
          	} 	
		}
	}
}else {
	document.getElementById('infoTag').options[0]=new Option("所有部门");
}
//提交form
function submitForm(){
    var content =getEditorTextContents('newsAdd');
    if(content == null || content.trim().length ==0 || content=='<br>'){
        new Insertion.After("newsAdd","<font color='red'>必填项！</font>");
        return false;
    }
    $('#infoContent').val(content);
	var infoTag="";
   for(i=0;i<document.getElementById('infoTag').length;i++){   
       infoTag+=document.getElementById('infoTag').options[i].text+",";
    }
   
   tagObj.value=infoTag;
   document.getElementById('createNews').submit();
}

//==========================select one to another select=====================================
//tag属于哪个列表
function whereSelect(exp){
  if(exp=='') return document.getElementById('tag_dep');
  	//return $('tag_dep');
  	<s:if test="!departmentList.isEmpty()"> 
	  	<s:iterator value="departmentList" status="index">
		  	var listexp='<s:property value="departmentName"/>';
		  	if(listexp==exp){
		  		return document.getElementById('tag_dep');
		  	}
	  	</s:iterator>
  	</s:if>  
}
/**
  * 选择一个删除任何一个。
  */
function fun_select_dltany(theselect){  
    var i;     
    for(i=0;i < theselect.length;i++){   
       if(theselect.options[i].selected   ==   true){
          var select = theselect.options[i].text;  
          if(select == "所有部门"){return;}
          var left_select=whereSelect(select);               		
          left_select.options[left_select.length] = new Option(theselect.options[i].text);   
          theselect.options[i] = new Option("");   
          theselect.remove(i);   
          i--;   
        }     
      } 
    if(document.getElementById('infoTag').length == 0){document.getElementById('infoTag').options[0] = new Option("所有部门");}
    sort_select(theselect);   
}
  
 /**
   * 选择一个添加任何一个。
   */
function  fun_select_addany(theselect){   
   var infoTag=document.getElementById('infoTag');
    for(i=0;i<theselect.length;i++){   
      if(theselect.options[i].selected == true){
        if(infoTag.options[0].text == "所有部门"){infoTag.remove(0);}   
	    infoTag.options[infoTag.length]=new Option(theselect.options[i].text);   
	    theselect.remove(i);   
	    i--;   
        }     
      }   
    sort_select(theselect);   
}  

 /**
  * 选择一个添加所有。
  */      
function fun_select_addall(theform){   
     var i;         
     for(i=0;i<theform.left_select.length;i++){   
         theform.right_select.options[theform.right_select.length] = new Option(theform.left_select.options[i].text);           
     }   
     theform.left_select.length=0;             
     sort_select(document.myform);     
 }
   
 /**
   * 选择一个删除所有。
   */
function fun_select_dltall(theform){
     var i;
     for(i=0;i<theform.right_select.length;i++){   
         theform.left_select.options[theform.left_select.length] = new Option(theform.right_select.options[i].text);   
     }
     theform.right_select.length=0;                           
     sort_select(document.myform);   
}
     
 /**
   * 按顺序选择。
   */
function sort_select(theselect){   
     var i;
     var left_array = new Array();                                   
     for(i=0;i<theselect.length;i++){   
         left_array[i] = new Array(theselect.options[i].text);   
     }
     
   	left_array.sort();   
     theselect.length=0;                       
             
     for(i=0;i<left_array.length;i++){   
         theselect.options[theselect.length] = new Option(left_array[i]);   
     }   
     
     left_array = new Array();       
}
</script>
</body>
</html>
