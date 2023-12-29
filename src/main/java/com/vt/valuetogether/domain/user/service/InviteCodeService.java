package com.vt.valuetogether.domain.user.service;

import com.vt.valuetogether.domain.user.entity.InviteCode;

public interface InviteCodeService {
    InviteCode findById(String code);

    InviteCode save(InviteCode inviteCode);
}
