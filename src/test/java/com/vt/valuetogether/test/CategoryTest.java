package com.vt.valuetogether.test;

import com.vt.valuetogether.domain.category.entity.Category;

public interface CategoryTest extends TeamTest {

    Long TEST_CATEGORY_ID = 1L;
    String TEST_CATEGORY_NAME = "name";
    Double TEST_CATEGORY_SEQUENCE = 1.0;
    Boolean TEST_CATEGORY_IS_DELETED = Boolean.FALSE;

    Long TEST_ANOTHER_CATEGORY_ID = 2L;
    String TEST_ANOTHER_CATEGORY_NAME = "name2";
    Double TEST_ANOTHER_CATEGORY_SEQUENCE = 2.0;
    Boolean TEST_ANOTHER_CATEGORY_IS_DELETED = Boolean.FALSE;

    Category TEST_CATEGORY =
            Category.builder()
                    .categoryId(TEST_CATEGORY_ID)
                    .name(TEST_CATEGORY_NAME)
                    .sequence(TEST_CATEGORY_SEQUENCE)
                    .isDeleted(TEST_CATEGORY_IS_DELETED)
                    .team(TEST_TEAM)
                    .build();

    Category TEST_ANOTHER_CATEGORY =
            Category.builder()
                    .categoryId(TEST_ANOTHER_CATEGORY_ID)
                    .name(TEST_ANOTHER_CATEGORY_NAME)
                    .sequence(TEST_ANOTHER_CATEGORY_SEQUENCE)
                    .isDeleted(TEST_ANOTHER_CATEGORY_IS_DELETED)
                    .team(TEST_TEAM)
                    .build();
}
