<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<%  response.setHeader("Pragma","No-cache"); 
	response.setHeader("Cache-Control","no-cache"); 
	response.setDateHeader("Expires", 0); 
%> 
<html>
<head>
<script type="text/javascript" src="../resource/js/profile/jobtitle_config.js"></script>
</head>
<body>
<table id="table_jobtitle" cellpadding="0" cellspacing="1" width="85%" class="tablesorter">
  <thead>
      <tr align="center">
          <th nowrap="nowrap">职务名称</th>
          <th nowrap="nowrap">缺省帐套</th>
          <th nowrap="nowrap"> 缺省薪资级别</th>
          <th nowrap="nowrap">招聘需总经理审批</th>
          <th nowrap="nowrap">描述</th>
          <th nowrap="nowrap">状态</th>
      </tr>
  </thead>
  <tbody id="tbody_jobtitle">
      <s:iterator value="allJobTitle">
          <tr id="<s:property value="jobtitleNo"/>" jobtitleStatus="<s:property value='jobtitleStatus'/>" acctId="<s:property value='jobtitleDefaultAcct.id'/>" needgm="<s:property value='jobtitleNeedGmApprove'/>" jobgradeId="<s:property value='jobtitleDefaultJg.id'/>" align="center">
              <td><s:property value="jobtitleName" /></td>
              <td><s:property value="jobtitleDefaultAcct.esacName" /></td>
              <td><s:property value="jobtitleDefaultJg.jobGradeName" /></td>
              <td><s:property value="%{jobtitleNeedGmApprove==0?'不需要':'需要'}" /></td>
              <td><s:property value="jobtitleNameDesc" /></td>
              <td><s:if test="jobtitleStatus==0">停用</s:if><s:else>启用</s:else>
              </td>
          </tr>
      </s:iterator>
    </tbody>
</table>
<div class="btnlink">
    <a href="#" id="link_add_jobtitle">新增</a>
	<a href="#" id="link_delete_jobtitle">删除</a>
	<a href="#" id="link_update_jobtitle">修改</a>
	<a href="#" id="link_sort_jobtitle">保存排序</a>
</div>
<div id="dialog_jobtitle" title="职务设置">
<form id="jobtitleForm" method="post">  
    <table cellpadding="0" cellspacing="1" width="100%">
            <tr>
                <td>职务名称<font color="red">*</font>：
                </td>
                <td class="errorMessage">
                    <input id="jobtitleName" type="text" size="32" maxlength="64"><label id="label_jobtitleName">&nbsp;</label>
                </td>
            </tr>
            <tr>
            	<td>缺省帐套：</td>
				<td class="errorMessage">
					<select id="jobtitleDefaultAcct">
						<option value="">请选择</option>
							<s:iterator value="empsalaryacctList">
								<option value='<s:property value="id"/>'><s:property value="esacName"/></option>
							</s:iterator>
					</select>
				</td>
            </tr>
            <tr>
				<td>缺省薪资级别：</td>
				<td class="errorMessage">
					<select id="jobtitleDefaultJg">
						<option value="">请选择</option>
							<s:iterator value="jobgradeList">
								<option value='<s:property value="id"/>'><s:property value="jobGradeName"/></option>
							</s:iterator>
					</select>
				</td>
			</tr>
			<tr>
				<td nowrap="nowrap">招聘审批要求<font color="red">*</font>：</td>
				<td class="errorMessage">
					<select id="jobtitleNeedGmApprove">
						<option value="0" selected="selected">不需要总经理审批</option>
						<option value="1">需要总经理审批</option>
					</select>
				</td>
			</tr>
            <tr>
                <td>职务描述：</td>
                <td class="errorMessage"><textarea id="jobtitleNameDesc" cols="30" rows="4"></textarea></td>
            </tr> 
            <tr>
            	<td>状态<span class="required">*</span>：</td>
				<td>
					<select id="jobtitleStatus" name="jobtitleStatus">
						<option value="1">启用</option>
						<option value="0">停用</option>
					</select>
				</td>
			</tr>          
            <tr>
               <td colspan="2" align="center">
                   <input id="btn_add_jobtitle" type="button" value="保存" />
				   <input id="btn_update_jobtitle" type="button" value="保存" />
				   <input id="btn_close_jobtitle" type="button" value="关闭" />
				   <input id='jobtitleSortId' type="hidden" />
				   <s:hidden id="jobtitleNo"></s:hidden>
                </td>
			</tr>
     </table>
</form>
</div>
<script type="text/javascript">
	var config={
		tableId		:"table_jobtitle",
		tbodyId		:"tbody_jobtitle",
		dialogId	:"dialog_jobtitle",
		updateButton:"btn_update_jobtitle",
		addButton	:"btn_add_jobtitle",
		dialogHeight:280,
		dialogWidth :400,
		addLink		:"link_add_jobtitle",
		deleteLink	:"link_delete_jobtitle",
		updateLink	:"link_update_jobtitle",
		sortLink	:"link_sort_jobtitle",
		closeButton	:"btn_close_jobtitle"
	};
	jobtitleManager = new JobtitleManager(config);
</script>
</body>
</html>