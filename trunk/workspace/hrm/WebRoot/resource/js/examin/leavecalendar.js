/*
 * Compressed by JSA(www.xidea.org)
 */
function yearSubmit(){if(!isNum($("yearSelect").value)){alert("\u8f93\u5165\u5e74\u4efd\u4e0d\u7b26\u5408\u89c4\u5219!");return false}}function yearchange(_){if(_=="next"){if(!isNum($("yearSelect").value)){alert("\u8f93\u5165\u5e74\u4efd\u4e0d\u7b26\u5408\u89c4\u5219!");return false}$("yearSelect").value=parseInt($("yearSelect").value)+1}else if(_=="prev"){if(!isNum($("yearSelect").value)){alert("\u8f93\u5165\u5e74\u4efd\u4e0d\u7b26\u5408\u89c4\u5219!");return false}$("yearSelect").value=parseInt($("yearSelect").value)-1}}