package com.ericsson.oss.services.eniqintegration.entity;

import java.util.List;

import com.ericsson.oss.services.topologyservice.api.MoEntity;

public interface MoEntityStore {

    List<MoEntity> getList();

    void setList(final List<MoEntity> list);
}
