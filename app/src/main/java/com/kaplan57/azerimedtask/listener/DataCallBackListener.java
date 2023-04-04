package com.kaplan57.azerimedtask.listener;

import com.kaplan57.azerimedtask.local_db.entity.PhonesEntity;

import java.util.List;

public interface DataCallBackListener {
    void invoke(List<PhonesEntity> list);

    // No operation
    static final DataCallBackListener NO_OP = (list) -> {
    };
}
