package com.vt.valuetogether.domain.team.service.impl;

import static java.lang.Boolean.TRUE;

import com.vt.valuetogether.domain.team.repository.TeamRepository;
import com.vt.valuetogether.domain.team.service.TeamSchedulingService;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@EnableScheduling
@RequiredArgsConstructor
public class TeamSchedulingServiceImpl implements TeamSchedulingService {
    private final TeamRepository teamRepository;
    private final long TEAM_RETENTION_PERIOD = 90L;

    @Override
    @Transactional
    @Scheduled(cron = "0 0 1 * * ?")
    public void deleteAllTeam() {
        ZonedDateTime dateTime = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("Asia/Seoul"));
        LocalDateTime localDateTime =
                dateTime.toLocalDate().minusDays(TEAM_RETENTION_PERIOD).atStartOfDay();
        teamRepository.deleteByIsDeletedAndModifiedAtBefore(TRUE, localDateTime);
    }
}
