package com.codingapi.flow.repository;

import com.codingapi.flow.domain.FlowWork;
import com.codingapi.springboot.framework.dto.request.SearchRequest;
import org.springframework.data.domain.Page;

public interface FlowWorkQuery {

    Page<FlowWork> list(SearchRequest request);

}
