<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<html>
	<head>
		<script type="text/javascript" src="../resource/js/configuration/configOthers.js"></script>
	</head>
<body>
<div class="wrapper">
	<div class="tipdiv">
		<p>1. ϵͳ����ȱʡÿ��3�챸��һ�Σ���ౣ������50������</p>
		<p>2. ��ϵͳ����Ƶ������Ϊ0,��ʱ��һ��ֻ���������һ��</p>
	</div>
	<div class="contentdiv">
		<form id="otherConfigForm" action="otherUpdateConfig.action" name="otherConfigForm" method="post">
			<s:token />
			<p>
				ϵͳ����ÿ��
				<s:textfield name="sys_backup_frequency" id="sys_backup_frequency"
					size="4" maxlength="4" onkeyup="value=value.replace(/\D/g,'')"></s:textfield>
				�챸��һ��(0-30��)
			</p>
			<p>
				ϵͳ���ݲ�����
				<s:textfield name="sys_backup_copies" id="sys_backup_copies"
					size="4" maxlength="4" onkeyup="value=value.replace(/\D/g,'')"></s:textfield>
				������(0-50��)
			</p>
			<p>
				<br>
				<input type="button" id="updateOthersBt" name="submitOthers"
					value=" �� �� " onClick="doOtherUpdate()" />
			</p>
		</form>
	</div>
</div>
</body>
</html>