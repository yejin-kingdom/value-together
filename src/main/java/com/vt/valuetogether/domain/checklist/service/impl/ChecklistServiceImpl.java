package com.vt.valuetogether.domain.checklist.service.impl;

import com.vt.valuetogether.domain.card.entity.Card;
import com.vt.valuetogether.domain.card.repository.CardRepository;
import com.vt.valuetogether.domain.checklist.dto.request.ChecklistDeleteReq;
import com.vt.valuetogether.domain.checklist.dto.request.ChecklistSaveReq;
import com.vt.valuetogether.domain.checklist.dto.request.ChecklistUpdateReq;
import com.vt.valuetogether.domain.checklist.dto.response.ChecklistDeleteRes;
import com.vt.valuetogether.domain.checklist.dto.response.ChecklistSaveRes;
import com.vt.valuetogether.domain.checklist.dto.response.ChecklistUpdateRes;
import com.vt.valuetogether.domain.checklist.entity.Checklist;
import com.vt.valuetogether.domain.checklist.repository.ChecklistRepository;
import com.vt.valuetogether.domain.checklist.service.ChecklistService;
import com.vt.valuetogether.domain.checklist.service.ChecklistServiceMapper;
import com.vt.valuetogether.domain.user.entity.User;
import com.vt.valuetogether.domain.user.repository.UserRepository;
import com.vt.valuetogether.global.validator.CardValidator;
import com.vt.valuetogether.global.validator.ChecklistValidator;
import com.vt.valuetogether.global.validator.TeamRoleValidator;
import com.vt.valuetogether.global.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChecklistServiceImpl implements ChecklistService {
    private final ChecklistRepository checklistRepository;
    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public ChecklistSaveRes saveChecklist(ChecklistSaveReq checklistSaveReq) {
        User user = getUserByUsername(checklistSaveReq.getUsername());
        Card card = cardRepository.findByCardId(checklistSaveReq.getCardId());
        CardValidator.validate(card);
        TeamRoleValidator.checkIsTeamMember(card.getCategory().getTeam().getTeamRoleList(), user);
        return ChecklistServiceMapper.INSTANCE.toChecklistSaveRes(
                checklistRepository.save(
                        Checklist.builder().card(card).title(checklistSaveReq.getTitle()).build()));
    }

    @Override
    @Transactional
    public ChecklistUpdateRes updateChecklist(ChecklistUpdateReq checklistUpdateReq) {
        Checklist prevChecklist =
                checklistRepository.findByChecklistId(checklistUpdateReq.getChecklistId());
        ChecklistValidator.validate(prevChecklist);
        checklistRepository.save(
                Checklist.builder()
                        .checklistId(checklistUpdateReq.getChecklistId())
                        .title(checklistUpdateReq.getTitle())
                        .card(prevChecklist.getCard())
                        .build());
        return new ChecklistUpdateRes();
    }

    @Override
    @Transactional
    public ChecklistDeleteRes deleteChecklist(ChecklistDeleteReq checklistDeleteReq) {
        Checklist checklist =
                checklistRepository.findByChecklistId(checklistDeleteReq.getChecklistId());
        ChecklistValidator.validate(checklist);
        checklistRepository.delete(checklist);
        return new ChecklistDeleteRes();
    }

    private User getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        UserValidator.validate(user);
        return user;
    }
}
