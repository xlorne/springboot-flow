package com.example.flow.pojo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;

@Setter
@Getter
public class FlowSearchRequest {

    private String username;
    private int current;
    private int pageSize;


    public PageRequest toPage(){
        return PageRequest.of(current-1,pageSize==0?10:pageSize);
    }
}
