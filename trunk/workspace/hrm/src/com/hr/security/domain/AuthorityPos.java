package com.hr.security.domain;

import com.hr.profile.domain.Position;
import com.hr.security.domain.base.BaseAuthorityPos;
import com.hr.util.MyTools;
import com.hr.util.StringUtil;
import org.apache.axis.utils.StringUtils;

public class AuthorityPos extends BaseAuthorityPos {
    private static final long serialVersionUID = 1L;

    public AuthorityPos() {
    }

    public AuthorityPos(String id) {
        super(id);
    }

    public AuthorityPos(String id, Position apPos, String apModule, String apAuthValidate) {
        super(id, apPos, apModule, apAuthValidate);
    }

    public void setApAuthValidateEncrypt(String deptId, String locId, String module) {
        String uuid = deptId;
        if (StringUtils.isEmpty(uuid)) {
            uuid = locId;
        }
        String apAuthValidate = MyTools.vigenere(StringUtil.encodePassword(uuid + module), MyTools
                .getUpKey(getId(), MyTools.STRING), MyTools.ENCRYPT_MODE);

        setApAuthValidate(apAuthValidate);
    }

    public boolean hasApAuthValidateDecrypt(String deptId, String locId, String module) {
        String uuid = deptId;
        if (StringUtils.isEmpty(uuid)) {
            uuid = locId;
        }
        String apAuthValidate = MyTools.vigenere(StringUtil.encodePassword(uuid + module), MyTools
                .getUpKey(getId(), MyTools.STRING), MyTools.ENCRYPT_MODE);

        return getApAuthValidate().equals(apAuthValidate);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.security.domain.AuthorityPos JD-Core Version: 0.5.4
 */