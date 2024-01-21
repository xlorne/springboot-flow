package com.codingapi.flow.infrastructure.repository.impl;

import com.codingapi.flow.domain.FlowWork;
import com.codingapi.flow.infrastructure.convert.FlowWorkConvertor;
import com.codingapi.flow.infrastructure.jpa.FlowWorkEntityRepository;
import com.codingapi.flow.repository.FlowWorkQuery;
import com.codingapi.springboot.framework.dto.request.SearchRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;

@AllArgsConstructor
public class FlowWorkQueryImpl implements FlowWorkQuery {

    private final FlowWorkEntityRepository flowWorkEntityRepository;

    @Override
    public Page<FlowWork> list(SearchRequest request) {
        return flowWorkEntityRepository.searchRequest(request).map(FlowWorkConvertor::convert);
    }
}
