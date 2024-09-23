package com.codingapi.flow.repository;

import com.codingapi.flow.domain.FlowWork;
import com.codingapi.springboot.framework.dto.request.SearchRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FlowWorkQuery {

    Page<FlowWork> list(SearchRequest request);

    List<FlowWork> findAll();


}
