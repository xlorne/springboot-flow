package com.codingapi.flow.repository;

import com.codingapi.flow.data.BindDataSnapshot;

import java.util.ArrayList;
import java.util.List;

public class BindDataSnapshotRepositoryImpl implements BindDataSnapshotRepository{

    private final List<BindDataSnapshot> snapshots = new ArrayList<>();

    @Override
    public void save(BindDataSnapshot snapshot) {
        if(snapshot.getId()==0){
            snapshots.add(snapshot);
            snapshot.setId(snapshots.size());
        }

    }
}
