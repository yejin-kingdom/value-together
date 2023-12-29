package com.vt.valuetogether.domain.user.service.impl;

import com.vt.valuetogether.domain.user.entity.InviteCode;
import com.vt.valuetogether.domain.user.repository.InviteRepository;
import com.vt.valuetogether.domain.user.service.InviteCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InviteCodeServiceImpl implements InviteCodeService {

    private final InviteRepository inviteRepository;

    @Override
    public InviteCode findById(String code) {
        return inviteRepository.findById(code);
    }

    @Override
    public InviteCode save(InviteCode inviteCode) {
        return inviteRepository.save(inviteCode);
    }

    @Override
    public void deleteById(String code) {
        inviteRepository.deleteById(code);
    }
}
