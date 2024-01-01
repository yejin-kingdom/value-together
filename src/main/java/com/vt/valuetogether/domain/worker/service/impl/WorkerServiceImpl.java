package com.vt.valuetogether.domain.worker.service.impl;

import com.vt.valuetogether.domain.card.entity.Card;
import com.vt.valuetogether.domain.card.repository.CardRepository;
import com.vt.valuetogether.domain.team.entity.TeamRole;
import com.vt.valuetogether.domain.team.repository.TeamRoleRepository;
import com.vt.valuetogether.domain.user.entity.User;
import com.vt.valuetogether.domain.user.repository.UserRepository;
import com.vt.valuetogether.domain.worker.dto.request.WorkerAddReq;
import com.vt.valuetogether.domain.worker.dto.request.WorkerDeleteReq;
import com.vt.valuetogether.domain.worker.dto.response.WorkerAddRes;
import com.vt.valuetogether.domain.worker.dto.response.WorkerDeleteRes;
import com.vt.valuetogether.domain.worker.entity.Worker;
import com.vt.valuetogether.domain.worker.repository.WorkerRepository;
import com.vt.valuetogether.domain.worker.service.WorkerService;
import com.vt.valuetogether.domain.worker.service.WorkerServiceMapper;
import com.vt.valuetogether.global.validator.CardValidator;
import com.vt.valuetogether.global.validator.TeamRoleValidator;
import com.vt.valuetogether.global.validator.UserValidator;
import com.vt.valuetogether.global.validator.WorkerValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WorkerServiceImpl implements WorkerService {

    private final UserRepository userRepository;
    private final CardRepository cardRepository;
    private final WorkerRepository workerRepository;
    private final TeamRoleRepository teamRoleRepository;

    @Transactional
    @Override
    public WorkerAddRes addWorker(WorkerAddReq req) {
        Card card = findCard(req.getCardId(), req.getUsername());
        TeamRole teamRole =
                findTeamRole(req.getAddUsername(), card.getCategory().getTeam().getTeamId());

        return WorkerServiceMapper.INSTANCE.toWorkerAddRes(
                workerRepository.save(Worker.builder().teamRole(teamRole).card(card).build()));
    }

    private Card findCard(Long cardId, String username) {
        User user = findUser(username);
        Card card = cardRepository.findByCardId(cardId);
        CardValidator.validate(card);
        TeamRoleValidator.checkIsTeamMember(card.getCategory().getTeam().getTeamRoleList(), user);
        return card;
    }

    private User findUser(String username) {
        User user = userRepository.findByUsername(username);
        UserValidator.validate(user);
        return user;
    }

    private TeamRole findTeamRole(String username, Long teamId) {
        TeamRole teamRole = teamRoleRepository.findByUserUsernameAndTeamTeamId(username, teamId);
        TeamRoleValidator.validate(teamRole);
        return teamRole;
    }

    @Transactional
    @Override
    public WorkerDeleteRes deleteWorker(WorkerDeleteReq req) {
        workerRepository.delete(findWorker(req));
        return new WorkerDeleteRes();
    }

    private Worker findWorker(WorkerDeleteReq req) {
        Card card = findCard(req.getCardId(), req.getUsername());
        TeamRole teamRole =
                findTeamRole(req.getDeleteUsername(), card.getCategory().getTeam().getTeamId());

        Worker worker = workerRepository.findByTeamRoleAndCard(teamRole, card);
        WorkerValidator.validator(worker);
        return worker;
    }
}
