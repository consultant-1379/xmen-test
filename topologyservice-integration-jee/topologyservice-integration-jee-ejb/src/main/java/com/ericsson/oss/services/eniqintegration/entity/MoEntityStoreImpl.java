package com.ericsson.oss.services.eniqintegration.entity;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.ericsson.oss.services.topologyservice.api.MoEntity;

@XmlRootElement(name = "model")
@XmlAccessorType(XmlAccessType.NONE)
public class MoEntityStoreImpl implements MoEntityStore {

    // @XmlElementWrapper
    @XmlElement(name = "mo")
    public List<MoEntity> list = new ArrayList<MoEntity>();

    @Override
    public List<MoEntity> getList() {
        return list;
    }

    @Override
    public void setList(final List<MoEntity> list) {
        this.list = list;
    }

}
