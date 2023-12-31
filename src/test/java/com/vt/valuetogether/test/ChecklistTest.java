package com.vt.valuetogether.test;

import com.vt.valuetogether.domain.checklist.entity.Checklist;

public interface ChecklistTest extends CardTest {

    Long TEST_CHECKLIST_ID = 1L;

    String TEST_TITLE = "title";
    String TEST_UPDATED_TITLE = "updatedTitle";

    Checklist TEST_CHECKLIST =
            Checklist.builder().checklistId(TEST_CHECKLIST_ID).title(TEST_TITLE).card(TEST_CARD).build();

    Checklist TEST_UPDATED_CHECKLIST =
            Checklist.builder()
                    .checklistId(TEST_CHECKLIST_ID)
                    .title(TEST_UPDATED_TITLE)
                    .card(TEST_CARD)
                    .build();
}
