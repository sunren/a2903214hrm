/*
 * Compressed by JSA(www.xidea.org)
 */
function doOtherUpdate(){var C=(document.getElementById("sys_contract_expire_remind")).value.trim(),_=(document.getElementById("sys_trial_expire_remind")).value.trim(),$=(document.getElementById("sys_birthday_remind")).value.trim(),A=(document.getElementById("sys_backup_frequency")).value.trim(),B=(document.getElementById("sys_backup_copies")).value.trim();if(!isNotNull(C)){errMsg("errMsg","\u5408\u540c\u5230\u671f\u63d0\u9192\u65f6\u95f4\u4e0d\u80fd\u4e3a\u7a7a\uff01");return}if(!isNotNull(_)){errMsg("errMsg","\u8bd5\u7528\u5230\u671f\u63d0\u9192\u65f6\u95f4\u4e0d\u80fd\u4e3a\u7a7a\uff01");return}if(!isNotNull($)){errMsg("errMsg","\u751f\u65e5\u5230\u671f\u63d0\u9192\u65f6\u95f4\u4e0d\u80fd\u4e3a\u7a7a\uff01");return}if(!isNotNull(A))A=0;if(!isNotNull(B)){errMsg("errMsg","\u7cfb\u7edf\u5907\u4efd\u4e2a\u6570\u4e0d\u80fd\u4e3a\u7a7a\uff01");return}if(A>30||A<0){errMsg("errMsg","\u7cfb\u7edf\u5907\u4efd\u65f6\u95f4\u5e94\u4e3a0-30\u5929\uff01");return}if(B>50||B<0){errMsg("errMsg","\u7cfb\u7edf\u5907\u4efd\u4e2a\u6570\u5e94\u57280-50\u4e4b\u95f4\uff01");return}C=parseInt(C);_=parseInt(_);$=parseInt($);A=parseInt(A);B=parseInt(B);if(!window.confirm("\u4f60\u786e\u5b9a\u8981\u66f4\u65b0\u5417\uff1f"))return;document.getElementById("updateOthersBt").disabled=true;document.getElementById("otherConfigForm").submit()}function changeProfileType(){document.getElementById("getValueByName").value=getValueByName("profileType");alert("\u6b64\u65f6\u5458\u5de5\u6a21\u5757 \u67e5\u8be2\u4e0b\u5c5e\u5458\u5de5\u7684\u65b9\u5f0f="+document.getElementById("getValueByName").value)}