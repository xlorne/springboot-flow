package com.codingapi.flow.repository;

import com.codingapi.flow.domain.FlowNode;
import com.codingapi.flow.domain.FlowWork;
import com.codingapi.springboot.framework.dto.request.SearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FlowWorkRepositoryImpl implements FlowWorkRepository,FlowWorkQuery{

    private final Map<Long, FlowWork> cache = new HashMap<>();
    private final Map<Long, FlowNode> flowCache = new HashMap<>();

    @Override
    public void save(FlowWork flowWork) {
        if(flowWork.getId()>0){
            cache.put(flowWork.getId(), flowWork);
        }else{
            if(flowWork.getFlow().getId()==0){
                long id = flowCache.size()+1;
                flowWork.getFlow().setId(id);
                flowCache.put(id, flowWork.getFlow());
            }
            long id = cache.size()+1;
            flowWork.setId(id);
            cache.put(id, flowWork);
        }
    }

    @Override
    public FlowWork get(long workId) {
        return cache.get(workId);
    }

    @Override
    public void delete(long workId) {
        cache.remove(workId);
    }

    @Override
    public Page<FlowWork> list(SearchRequest request) {
        return new PageImpl<>(cache.values().stream().toList());
    }

    @Override
    public List<FlowWork> findAll() {
        return cache.values().stream().toList();
    }
}
