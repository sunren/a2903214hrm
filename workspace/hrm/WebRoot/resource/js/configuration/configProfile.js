/*
 * Compressed by JSA(www.xidea.org)
 */
function doProfileUpdate(){var A=(document.getElementById("sys_contract_expire_remind")).value.trim(),_=(document.getElementById("sys_trial_expire_remind")).value.trim(),$=(document.getElementById("sys_birthday_remind")).value.trim();if(!isNotNull(A)){errMsg("errMsg","\u5408\u540c\u5230\u671f\u63d0\u9192\u65f6\u95f4\u4e0d\u80fd\u4e3a\u7a7a\uff01");return}if(!isNotNull(_)){errMsg("errMsg","\u8bd5\u7528\u5230\u671f\u63d0\u9192\u65f6\u95f4\u4e0d\u80fd\u4e3a\u7a7a\uff01");return}if(!isNotNull($)){errMsg("errMsg","\u751f\u65e5\u5230\u671f\u63d0\u9192\u65f6\u95f4\u4e0d\u80fd\u4e3a\u7a7a\uff01");return}document.getElementById("sys_contract_expire_remind").value=parseInt(A);document.getElementById("sys_trial_expire_remind").value=parseInt(_);document.getElementById("sys_birthday_remind").value=parseInt($);if(!window.confirm("\u4f60\u786e\u5b9a\u8981\u66f4\u65b0\u5417\uff1f"))return;document.getElementById("updateProfileBt").disabled=true;document.getElementById("otherProfileForm").submit()}function changeProfileType(){document.getElementById("sys_profile_sub_type").value=getValueByName("profileType")}