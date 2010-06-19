<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<%  response.setHeader("Pragma","No-cache"); 
	response.setHeader("Cache-Control","no-cache"); 
	response.setDateHeader("Expires", 0); 
%> 
<html>
<head>
	<script type="text/javascript" src="../resource/js/profile/emptype_config.js"></script>
</head>
<body>
<table id="table_emptype" cellpadding="0" cellspacing="1" width="85%" class="tablesorter" >
   <thead>
       <tr align="center">
           <th width="10%">名称</th>
           <th width="30%">类型描述</th>
           <th width="10%">状态</th>
       </tr>
   </thead>
   <tbody id="tbody_emptype">
       <s:iterator value="emptypeList">
           <tr id="<s:property value="id"/>" emptypeStatus="<s:property value='emptypeStatus'/>" align="center">
               <td><s:property value="emptypeName" /></td>
               <td><s:property value="emptypeDesc" /></td>
           	   <td><s:if test="emptypeStatus==0">停用</s:if><s:else>启用</s:else>
               </td>
           </tr>
       </s:iterator>
    </tbody>
</table>
<div class="btnlink">
    <a href="#" id="link_add_emptype">新增</a>
	<a href="#" id="link_delete_emptype">删除</a>
	<a href="#" id="link_update_emptype">修改</a>
	<a href="#" id="link_sort_emptype">保存排序</a>
</div>
<div id="dialog_emptype" title="用工形式设置" >  
<form id="emptype_form" method="post">
    <table cellpadding="0" cellspacing="1" width="100%">
         <tr>
             <td>用工形式名称<font color="red">*</font>：
             </td>
             <td class="errorMessage">
                 <input id="emptypeName" type="text" size="32" maxlength="64"><label id="label_emptypeName">&nbsp;</label>
             </td>
         </tr>
         <tr>
             <td>用工形式描述：</td>
             <td class="errorMessage"><textarea id="emptypeDesc" cols="30" rows="4"></textarea></td>
         </tr>
         <tr>
         	<td>状态<span class="required">*</span>：</td>
			<td>
				<select id="emptypeStatus" name="emptypeStatus">
					<option value="1">启用</option>
					<option value="0">停用</option>
				</select>
			</td>
		</tr> 
         <tr>
            <td colspan="2" align="center">
                <input id="btn_add_emptype" type="button" value="保存" />
			    <input id="btn_update_emptype" type="button" value="保存" />
			    <input id="btn_close_emptype" type="button" value="关闭" />
			    <input id="emptypeSortId" type="hidden"/>
				<s:hidden id="emptypeId"></s:hidden>
           </td>
		</tr>
    </table>
</form>
</div>
<script type="text/javascript">
	var config={
		tableId		:"table_emptype",
		tbodyId		:"tbody_emptype",
		dialogId	:"dialog_emptype",
		updateButton:"btn_update_emptype",
		addButton	:"btn_add_emptype",
		dialogHeight:220,
		dialogWidth :400,
		addLink		:"link_add_emptype",
		deleteLink	:"link_delete_emptype",
		updateLink	:"link_update_emptype",
		sortLink	:"link_sort_emptype",
		closeButton	:"btn_close_emptype"
	};
	emptypeManager = new EmptypeManager(config);
</script>
</body>
</html>