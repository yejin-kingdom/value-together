package com.vt.valuetogether.domain.worker.service.impl;

import com.vt.valuetogether.domain.card.entity.Card;
import com.vt.valuetogether.domain.card.repository.CardRepository;
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

    @Transactional
    @Override
    public WorkerAddRes addWorker(WorkerAddReq req) {
        User user = findUser(req.getUsername());
        Card card = findCard(req.getCardId());

        return WorkerServiceMapper.INSTANCE.toWorkerAddRes(
                workerRepository.save(Worker.builder().user(user).card(card).build()));
    }

    private Card findCard(Long cardId) {
        Card card = cardRepository.findByCardId(cardId);
        CardValidator.validate(card);
        return card;
    }

    private User findUser(String username) {
        User user = userRepository.findByUsername(username);
        UserValidator.validate(user);
        return user;
    }

    @Transactional
    @Override
    public WorkerDeleteRes deleteWorker(WorkerDeleteReq req) {
        workerRepository.delete(findWorker(req));
        return new WorkerDeleteRes();
    }

    private Worker findWorker(WorkerDeleteReq req) {
        User user = findUser(req.getUsername());
        Card card = findCard(req.getCardId());

        Worker worker = workerRepository.findByUserAndCard(user, card);
        WorkerValidator.validator(worker);
        return worker;
    }
}
