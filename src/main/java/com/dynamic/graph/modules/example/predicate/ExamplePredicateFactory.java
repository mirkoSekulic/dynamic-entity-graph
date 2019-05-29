package com.dynamic.graph.modules.example.predicate;

import com.dynamic.graph.modules.example.domain.QExample;
import com.dynamic.graph.modules.example.filter.ExampleFilter;
import com.dynamic.graph.modules.shared.util.FormattingUtil;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ExamplePredicateFactory {

    public static Predicate getExampleFilterPredicate(ExampleFilter exampleFilter) {
        List<Predicate> predicates = new ArrayList<>();
        QExample qExample = QExample.example;
        if(StringUtils.isNotBlank(exampleFilter.getName())){
            predicates.add(qExample.name.like(FormattingUtil.likeFormat(exampleFilter.getName())));
        }
        if(StringUtils.isNoneBlank(exampleFilter.getDescription())) {
            predicates.add(qExample.description.like(FormattingUtil.likeFormat(exampleFilter.getDescription())));
        }
        return ExpressionUtils.allOf(predicates);
    }
}
