<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<html>
	<head>
		<script type="text/javascript" src="../resource/js/configuration/configSalary.js"></script>
	</head>
<body>
<div class="wrapper">
	<div class="tipdiv">
		<p>1. ȱʡ����£������ѯ����Ա����н�ʣ���������֯�ṹͼ������</p>
		<p>2. ȱʡ����£�����ͨ�����ݵ����޸�Ա��н�����ã�����ÿ��н�ʷ������ݲ�������</p>
		<p>3. �밴��ָ����ʽ��������˰�����㣬����ȱʡֵΪ 2000</p>
		<p>4. ����˰�ף�����ȱʡֵΪ 0,500,2000,5000,20000,40000,60000,80000,100000</p>
		<p>5. ����˰��˰�ʣ�����ȱʡֵΪ 0.05,0.10,0.15,0.20,0.25,0.30,0.35,0.40,0.45</p>
	</div>
	<div class="contentdiv">
		<form id="salaryForm" action="salaryUpdateConfig.action" method="post">
			<p>
				����н�������Ƿ������޸ģ�
				<s:hidden id="sys_salary_config_update" name="sys_salary_config_update"></s:hidden>
				<s:if test="sys_salary_config_update==0">
					<input type="radio" name="updateConf" value="1"
						class="radio" onclick="changeUpdateConf();" />��&nbsp; 
                    <input type="radio" name="updateConf" value="0"
						onclick="changeUpdateConf();" checked="checked" />��
                </s:if>
				<s:else>
					<input type="radio" name="updateConf" value="1"
						class="radio" onclick="changeUpdateConf();" checked="checked" />��&nbsp; 
                    <input type="radio" name="updateConf" value="0"
						class="radio" class="radio" onclick="changeUpdateConf();" />��
                </s:else>
			</p>
			<p>
				����˰�����㣺
				<s:textfield id="sys_salary_tax_min" name="sys_salary_tax_min" size="5" onkeyup="value=value.replace(/\D/g,'')" />
				&nbsp;&nbsp;&nbsp;&nbsp;˰�ף�
				<s:textfield id="sys_salary_tax_range" name="sys_salary_tax_range"
							size="64" onkeyup="notchinese(this);" />	
			</p>
			<p>
				˰�׶�Ӧ˰�ʣ�
				<s:textfield id="sys_salary_tax_rate" name="sys_salary_tax_rate" size="60" onkeyup="notchinese(this);" />
			</p>
			<p>
				<br><input type="button" id="updateSalaryBt" onClick="return updateTax();" value=" �� �� " />
			</p>
			<s:token />		
		</form>
	</div>
</div>
</body>
</html>