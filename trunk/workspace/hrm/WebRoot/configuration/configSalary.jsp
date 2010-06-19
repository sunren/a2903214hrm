<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<html>
	<head>
		<script type="text/javascript" src="../resource/js/configuration/configSalary.js"></script>
	</head>
<body>
<div class="wrapper">
	<div class="tipdiv">
		<p>1. 缺省情况下，经理查询下属员工的薪资，均按“组织结构图”进行</p>
		<p>2. 缺省情况下，可以通过数据导入修改员工薪资配置，导入每月薪资发放数据不检查错误</p>
		<p>3. 请按照指定格式设置所得税起征点，最新缺省值为 2000</p>
		<p>4. 设置税阶，最新缺省值为 0,500,2000,5000,20000,40000,60000,80000,100000</p>
		<p>5. 设置税阶税率，最新缺省值为 0.05,0.10,0.15,0.20,0.25,0.30,0.35,0.40,0.45</p>
	</div>
	<div class="contentdiv">
		<form id="salaryForm" action="salaryUpdateConfig.action" method="post">
			<p>
				导入薪资配置是否允许修改：
				<s:hidden id="sys_salary_config_update" name="sys_salary_config_update"></s:hidden>
				<s:if test="sys_salary_config_update==0">
					<input type="radio" name="updateConf" value="1"
						class="radio" onclick="changeUpdateConf();" />是&nbsp; 
                    <input type="radio" name="updateConf" value="0"
						onclick="changeUpdateConf();" checked="checked" />否
                </s:if>
				<s:else>
					<input type="radio" name="updateConf" value="1"
						class="radio" onclick="changeUpdateConf();" checked="checked" />是&nbsp; 
                    <input type="radio" name="updateConf" value="0"
						class="radio" class="radio" onclick="changeUpdateConf();" />否
                </s:else>
			</p>
			<p>
				所得税起征点：
				<s:textfield id="sys_salary_tax_min" name="sys_salary_tax_min" size="5" onkeyup="value=value.replace(/\D/g,'')" />
				&nbsp;&nbsp;&nbsp;&nbsp;税阶：
				<s:textfield id="sys_salary_tax_range" name="sys_salary_tax_range"
							size="64" onkeyup="notchinese(this);" />	
			</p>
			<p>
				税阶对应税率：
				<s:textfield id="sys_salary_tax_rate" name="sys_salary_tax_rate" size="60" onkeyup="notchinese(this);" />
			</p>
			<p>
				<br><input type="button" id="updateSalaryBt" onClick="return updateTax();" value=" 更 新 " />
			</p>
			<s:token />		
		</form>
	</div>
</div>
</body>
</html>