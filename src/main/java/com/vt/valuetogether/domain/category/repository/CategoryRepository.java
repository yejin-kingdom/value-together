package com.vt.valuetogether.domain.category.repository;

import com.vt.valuetogether.domain.category.entity.Category;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = Category.class, idClass = Long.class)
public interface CategoryRepository {

    Category save(Category category);

    @Query(
            value =
                    "select ifnull(max(c.sequence), 0) + 1.0 from Category c "
                            + "where c.team.teamId=:teamId")
    Double getMaxSequence(Long teamId);

    Category findByCategoryId(Long categoryId);

    List<Category> findByTeamTeamIdOrderBySequenceAsc(Long teamId);

    List<Category> findByOrderByTeamTeamIdAscSequenceAsc();

    List<Category> saveAll(Iterable<Category> categories);
}
