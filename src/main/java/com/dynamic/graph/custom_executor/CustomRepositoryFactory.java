package com.dynamic.graph.custom_executor;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.querydsl.EntityPathResolver;
import org.springframework.data.querydsl.QuerydslUtils;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryComposition;
import org.springframework.data.repository.core.support.RepositoryFragment;

import javax.persistence.EntityManager;
import java.io.Serializable;

public class CustomRepositoryFactory<T, I extends Serializable>
        extends JpaRepositoryFactory {

    private final EntityManager em;
    private EntityPathResolver entityPathResolver;

    public CustomRepositoryFactory(EntityManager em) {
        super(em);
        this.em = em;
        this.entityPathResolver = SimpleEntityPathResolver.INSTANCE;
    }

    @Override
    protected JpaRepositoryImplementation<?, ?> getTargetRepository(RepositoryInformation information, EntityManager entityManager) {
        return new CustomSimpleRepository<T, I>((Class<T>) information.getDomainType(), em);
    }

    @Override
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
        return CustomSimpleRepository.class;
    }

    @Override
    protected RepositoryComposition.RepositoryFragments getRepositoryFragments(RepositoryMetadata metadata) {
        RepositoryComposition.RepositoryFragments fragments = super.getRepositoryFragments(metadata);
        if (QuerydslUtils.QUERY_DSL_PRESENT && QuerydslPredicateExecutorWithEntityGraph.class.isAssignableFrom(
                metadata.getRepositoryInterface())) {

            JpaEntityInformation<?, Serializable> entityInformation =
                    getEntityInformation(metadata.getDomainType());

            Object querydslFragment = this.getTargetRepositoryViaReflection(QuerydslPredicateExecutorWithEntityGraphImpl.class,
                    new Object[]{entityInformation, this.em, this.entityPathResolver, null});

            fragments = fragments.append(RepositoryFragment.implemented(querydslFragment));
        }

        return fragments;
    }
}
