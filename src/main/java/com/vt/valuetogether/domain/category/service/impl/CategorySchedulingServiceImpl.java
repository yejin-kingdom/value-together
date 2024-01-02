package com.vt.valuetogether.domain.category.service.impl;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

import com.vt.valuetogether.domain.category.entity.Category;
import com.vt.valuetogether.domain.category.repository.CategoryRepository;
import com.vt.valuetogether.domain.category.service.CategorySchedulingService;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
@EnableScheduling
@RequiredArgsConstructor
public class CategorySchedulingServiceImpl implements CategorySchedulingService {
    private final CategoryRepository categoryRepository;
    private final double ADD_SEQUENCE = 1.0;
    private final long CATEGORY_RETENTION_PERIOD = 90L;

    @Override
    @Transactional
    @Scheduled(cron = "0 30 0 * * ?")
    public void resetSequence() {
        List<Category> categories =
                categoryRepository.findByIsDeletedOrderByTeamTeamIdAscSequenceAsc(FALSE);
        if (CollectionUtils.isEmpty(categories)) {
            return;
        }

        Long prevTeamId = categories.get(0).getTeam().getTeamId();
        double sequence = 0.0;
        List<Category> newCategories = new ArrayList<>();
        for (Category category : categories) {
            sequence = getNewSequence(sequence, prevTeamId, category.getTeam().getTeamId());
            newCategories.add(
                    Category.builder()
                            .categoryId(category.getCategoryId())
                            .name(category.getName())
                            .sequence(sequence)
                            .isDeleted(category.getIsDeleted())
                            .team(category.getTeam())
                            .build());
            prevTeamId = category.getTeam().getTeamId();
        }
        categoryRepository.saveAll(newCategories);
    }

    private double getNewSequence(double sequence, Long prevTeamId, Long nowTeamId) {
        if (!prevTeamId.equals(nowTeamId)) {
            return ADD_SEQUENCE;
        }
        return sequence + ADD_SEQUENCE;
    }

    @Override
    @Transactional
    @Scheduled(cron = "0 0 1 * * ?")
    public void deleteAllCategory() {
        ZonedDateTime dateTime = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("Asia/Seoul"));
        LocalDateTime localDateTime =
                dateTime.toLocalDate().minusDays(CATEGORY_RETENTION_PERIOD).atStartOfDay();
        categoryRepository.deleteByIsDeletedAndModifiedAtBefore(TRUE, localDateTime);
    }
}
