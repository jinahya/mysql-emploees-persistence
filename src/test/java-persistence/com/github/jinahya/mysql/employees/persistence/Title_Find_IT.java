package com.github.jinahya.mysql.employees.persistence;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("entityManager.find(entityClass, primaryKey)")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Title_Find_IT
        extends Title__IT {

    private static List<TitleId> selectTitleIdList1(final EntityManager entityManager, final Integer maxResults) {
        return entityManager.createQuery("""
                                                 SELECT e.id
                                                 FROM Title AS e
                                                 ORDER BY e.id.empNo ASC,
                                                          e.id.title ASC,
                                                          e.id.fromDate ASC""",
                                         TitleId.class
                            )
                            .setMaxResults(Optional.ofNullable(maxResults).orElse(Integer.MAX_VALUE))
                            .getResultList();
    }

    private static List<TitleId> selectTitleIdList2(final EntityManager entityManager, final Integer maxResults) {
        final var builder = entityManager.getCriteriaBuilder();
        final var query = builder.createQuery(TitleId.class);
        final var title = query.from(Title.class);                                                // FROM Title AS e
        final var id = title.get(Title_.id);
        query.select(id);                                                                         // SELECT e.id
        query.orderBy(                                                                            // ORDER BY
                                                                                                  builder.asc(
                                                                                                          id.get(TitleId_.empNo)),
                                                                                                  //   e.id.empNo ASC,
                                                                                                  builder.asc(
                                                                                                          id.get(TitleId_.title)),
                                                                                                  //   e.id.title ASC,
                                                                                                  builder.asc(
                                                                                                          id.get(TitleId_.fromDate))
                                                                                                  //   e.id.fromDate ASC
        );
        return entityManager.createQuery(query)
                            .setMaxResults(Optional.ofNullable(maxResults).orElse(Integer.MAX_VALUE))
                            .getResultList();
    }

    private static List<TitleId> selectTitleIdList(final EntityManager entityManager, final Integer maxResults) {
        return switch (ThreadLocalRandom.current().nextInt(2)) {
            case 0 -> selectTitleIdList1(entityManager, maxResults);
            default -> selectTitleIdList2(entityManager, maxResults);

        };
    }

    // -----------------------------------------------------------------------------------------------------------------
    private List<TitleId> getTitleIdList() {
        return applyEntityManager(em -> selectTitleIdList(em, 8));
    }

    @MethodSource({"getTitleIdList"})
    @ParameterizedTest
    void __(final TitleId id) {
        // -------------------------------------------------------------------------------------------------------- when
        final var found = applyEntityManager(em -> em.find(Title.class, id));
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(found)
                .isNotNull()
                .extracting(Title::getId)
                .isEqualTo(id);
    }
}
