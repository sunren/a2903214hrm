<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<html>
	<head>
		<script type="text/javascript" src="../resource/js/configuration/configOthers.js"></script>
	</head>
<body>
<div class="wrapper">
	<div class="tipdiv">
		<p>1. 系统备份缺省每隔3天备份一次，最多保留最新50个备份</p>
		<p>2. 若系统备份频率设置为0,则定时器一年只在年底运行一次</p>
	</div>
	<div class="contentdiv">
		<form id="otherConfigForm" action="otherUpdateConfig.action" name="otherConfigForm" method="post">
			<s:token />
			<p>
				系统备份每隔
				<s:textfield name="sys_backup_frequency" id="sys_backup_frequency"
					size="4" maxlength="4" onkeyup="value=value.replace(/\D/g,'')"></s:textfield>
				天备份一次(0-30天)
			</p>
			<p>
				系统备份不超过
				<s:textfield name="sys_backup_copies" id="sys_backup_copies"
					size="4" maxlength="4" onkeyup="value=value.replace(/\D/g,'')"></s:textfield>
				个拷贝(0-50个)
			</p>
			<p>
				<br>
				<input type="button" id="updateOthersBt" name="submitOthers"
					value=" 更 新 " onClick="doOtherUpdate()" />
			</p>
		</form>
	</div>
</div>
</body>
</html>