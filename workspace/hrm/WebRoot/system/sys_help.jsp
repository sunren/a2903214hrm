<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<%@taglib prefix="ty" uri="/tengsource"%>
<html>
<head>
<title>系统-帮助文件</title>
<style type="text/css">@import url("../resource/css/style.css");</style>
<link rel="stylesheet" type="text/css" href="../resource/css/tab.css" />
<script type="text/javascript" src="../resource/js/dwr/tabpane.js"></script>
<script type="text/javascript" src="<s:url value="/resource/js/prototype.js"/>?ver=1.5"></script>
<script type="text/javascript" src="<s:url value="/resource/js/util.js"/>"></script>
<script type="text/javascript" src="../configuration/inc.js"></script>
<script type="text/javascript">
var help_index='<%=request.getParameter("index")%>';
if(help_index==null){help_index=0;}
if(/^\d+$/.test(help_index)){
	WebFXTabPane.setCookie( "webfxtab_tabPane1", help_index);
}else{
	WebFXTabPane.setCookie( "webfxtab_tabPane1", help_index);
}
</script>
<base target="_self">
</head>
<body>
<div class="tab-pane" id="tabPane1">
   <div class="tab-page" id="tabPage1">
      <h2 class="tab">员工信息</h2>
        <table cellpadding="0" cellspacing="1" width="100%" class="basictable">
	      <thead>
		    <tr><th>员工资料</th></tr>
	      </thead>
	      <tbody id="tabPage1items">
		    <tr>
		    	<td>帮助信息
				</td>
			</tr>
	      </tbody>
         </table>
        <table cellpadding="0" cellspacing="1" width="100%" class="basictable">
	      <thead>
		    <tr><th>查询</th></tr>
	      </thead>
	      <tbody id="tabPage1items">
		    <tr>
		    	<td>帮助信息
				</td>
			</tr>
	      </tbody>
         </table>
        <table cellpadding="0" cellspacing="1" width="100%" class="basictable">
	      <thead>
		    <tr><th>员工通讯录</th></tr>
	      </thead>
	      <tbody id="tabPage1items">
		    <tr>
		    	<td>帮助信息
				</td>
			</tr>
	      </tbody>
         </table>
   </div>

   <div class="tab-page" id="tabPage2">
      <h2 class="tab">公司信息</h2>
        <table cellpadding="0" cellspacing="1" width="100%" class="basictable">
	      <thead>
		    <tr><th>新增公司信息</th></tr>
	      </thead>
	      <tbody id="tabPage1items">
		    <tr>
		    	<td>帮助信息
				</td>
			</tr>
	      </tbody>
         </table>
        <table cellpadding="0" cellspacing="1" width="100%" class="basictable">
	      <thead>
		    <tr><th>公司信息列表</th></tr>
	      </thead>
	      <tbody id="tabPage1items">
		    <tr>
		    	<td>帮助信息
				</td>
			</tr>
	      </tbody>
         </table>
   </div>

   <div class="tab-page" id="tabPage3">
      <h2 class="tab">薪资</h2>
        <table cellpadding="0" cellspacing="1" width="100%" class="basictable">
	      <thead>
		    <tr><th>员工薪资维护</th></tr>
	      </thead>
	      <tbody id="tabPage1items">
		    <tr>
		    	<td>帮助信息
				</td>
			</tr>
	      </tbody>
         </table>
        <table cellpadding="0" cellspacing="1" width="100%" class="basictable">
	      <thead>
		    <tr><th>调薪单维护</th></tr>
	      </thead>
	      <tbody id="tabPage1items">
		    <tr>
		    	<td>帮助信息
				</td>
			</tr>
	      </tbody>
         </table>
        <table cellpadding="0" cellspacing="1" width="100%" class="basictable">
	      <thead>
		    <tr><th>调薪单审批</th></tr>
	      </thead>
	      <tbody id="tabPage1items">
		    <tr>
		    	<td>帮助信息
				</td>
			</tr>
	      </tbody>
         </table>
        <table cellpadding="0" cellspacing="1" width="100%" class="basictable">
	      <thead>
		    <tr><th>员工薪资的发放</th></tr>
	      </thead>
	      <tbody id="tabPage1items">
		    <tr>
		    	<td>帮助信息
				</td>
			</tr>
	      </tbody>
         </table>
   </div>

   <div class="tab-page" id="tabPage4">
      <h2 class="tab">培训</h2>
        <table cellpadding="0" cellspacing="1" width="100%" class="basictable">
	      <thead>
		    <tr><th>培训日历</th></tr>
	      </thead>
	      <tbody id="tabPage1items">
		    <tr>
		    	<td>帮助信息
				</td>
			</tr>
	      </tbody>
         </table>
        <table cellpadding="0" cellspacing="1" width="100%" class="basictable">
	      <thead>
		    <tr><th>培训新闻</th></tr>
	      </thead>
	      <tbody id="tabPage1items">
		    <tr>
		    	<td>帮助信息
				</td>
			</tr>
	      </tbody>
         </table>
        <table cellpadding="0" cellspacing="1" width="100%" class="basictable">
	      <thead>
		    <tr><th>资源中心</th></tr>
	      </thead>
	      <tbody id="tabPage1items">
		    <tr>
		    	<td>帮助信息
				</td>
			</tr>
	      </tbody>
         </table>
        <table cellpadding="0" cellspacing="1" width="100%" class="basictable">
	      <thead>
		    <tr><th>在线测试</th></tr>
	      </thead>
	      <tbody id="tabPage1items">
		    <tr>
		    	<td>帮助信息
				</td>
			</tr>
	      </tbody>
         </table>
        <table cellpadding="0" cellspacing="1" width="100%" class="basictable">
	      <thead>
		    <tr><th>调查问卷</th></tr>
	      </thead>
	      <tbody id="tabPage1items">
		    <tr>
		    	<td>帮助信息
				</td>
			</tr>
	      </tbody>
         </table>
        <table cellpadding="0" cellspacing="1" width="100%" class="basictable">
	      <thead>
		    <tr><th>培训配置</th></tr>
	      </thead>
	      <tbody id="tabPage1items">
		    <tr>
		    	<td>帮助信息
				</td>
			</tr>
	      </tbody>
         </table>
        <table cellpadding="0" cellspacing="1" width="100%" class="basictable">
	      <thead>
		    <tr><th>试卷配置</th></tr>
	      </thead>
	      <tbody id="tabPage1items">
		    <tr>
		    	<td>帮助信息
				</td>
			</tr>
	      </tbody>
         </table>
   </div>

   <div class="tab-page" id="tabPage5">
      <h2 class="tab">考勤</h2>
        <table cellpadding="0" cellspacing="1" width="100%" class="basictable">
	      <thead>
		    <tr><th>考勤</th></tr>
	      </thead>
	      <tbody id="tabPage1items">
		    <tr>
		    	<td>帮助信息
				</td>
			</tr>
	      </tbody>
         </table>
	</div>

   <div class="tab-page" id="tabPage6">
   	   <h2 class="tab">绩效</h2>
        <table cellpadding="0" cellspacing="1" width="100%" class="basictable">
	      <thead>
		    <tr><th>绩效</th></tr>
	      </thead>
	      <tbody id="tabPage1items">
		    <tr>
		    	<td>帮助信息
				</td>
			</tr>
	      </tbody>
         </table>
  </div>

   <div class="tab-page" id="tabPage7">
   	   <h2 class="tab">招聘</h2>
        <table cellpadding="0" cellspacing="1" width="100%" class="basictable">
	      <thead>
		    <tr><th>招聘渠道维护</th></tr>
	      </thead>
	      <tbody id="tabPage1items">
		    <tr>
		    	<td>帮助信息
				</td>
			</tr>
	      </tbody>
         </table>
        <table cellpadding="0" cellspacing="1" width="100%" class="basictable">
	      <thead>
		    <tr><th>招聘方案维护</th></tr>
	      </thead>
	      <tbody id="tabPage1items">
		    <tr>
		    	<td>帮助信息
				</td>
			</tr>
	      </tbody>
         </table>
        <table cellpadding="0" cellspacing="1" width="100%" class="basictable">
	      <thead>
		    <tr><th>面试人员信息维护</th></tr>
	      </thead>
	      <tbody id="tabPage1items">
		    <tr>
		    	<td>帮助信息
				</td>
			</tr>
	      </tbody>
         </table>
  </div>

   <div class="tab-page" id="tabPage8">
   	   <h2 class="tab">报表</h2>
        <table cellpadding="0" cellspacing="1" width="100%" class="basictable">
	      <thead>
		    <tr><th>员工基础资料报表</th></tr>
	      </thead>
	      <tbody id="tabPage1items">
		    <tr>
		    	<td>帮助信息
				</td>
			</tr>
	      </tbody>
         </table>
        <table cellpadding="0" cellspacing="1" width="100%" class="basictable">
	      <thead>
		    <tr><th>员工薪资报表</th></tr>
	      </thead>
	      <tbody id="tabPage1items">
		    <tr>
		    	<td>帮助信息
				</td>
			</tr>
	      </tbody>
         </table>
        <table cellpadding="0" cellspacing="1" width="100%" class="basictable">
	      <thead>
		    <tr><th>员工培训资料报表</th></tr>
	      </thead>
	      <tbody id="tabPage1items">
		    <tr>
		    	<td>帮助信息
				</td>
			</tr>
	      </tbody>
         </table>
  </div>

   <div class="tab-page" id="tabPage9">
   	   <h2 class="tab">系统</h2>
        <table cellpadding="0" cellspacing="1" width="100%" class="basictable">
	      <thead>
		    <tr><th>添加用户</th></tr>
	      </thead>
	      <tbody id="tabPage1items">
		    <tr>
		    	<td>帮助信息
				</td>
			</tr>
	      </tbody>
         </table>
        <table cellpadding="0" cellspacing="1" width="100%" class="basictable">
	      <thead>
		    <tr><th>角色管理</th></tr>
	      </thead>
	      <tbody id="tabPage1items">
		    <tr>
		    	<td>帮助信息
				</td>
			</tr>
	      </tbody>
         </table>
        <table cellpadding="0" cellspacing="1" width="100%" class="basictable">
	      <thead>
		    <tr><th>基础资料配置</th></tr>
	      </thead>
	      <tbody id="tabPage1items">
		    <tr>
		    	<td>帮助信息
				</td>
			</tr>
	      </tbody>
         </table>
        <table cellpadding="0" cellspacing="1" width="100%" class="basictable">
	      <thead>
		    <tr><th>薪资配置</th></tr>
	      </thead>
	      <tbody id="tabPage1items">
		    <tr>
		    	<td>薪资配置帮助</td>
			</tr>
	      </tbody>
         </table>
  </div>
                                                                                                                               
                                                                                                     
                                                                                                    
                                                                
                                                                            
</div>        
</body>
</html>
