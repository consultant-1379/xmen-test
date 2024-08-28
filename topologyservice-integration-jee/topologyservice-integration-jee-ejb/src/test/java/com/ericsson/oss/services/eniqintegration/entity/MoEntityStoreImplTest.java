package com.ericsson.oss.services.eniqintegration.entity;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.ericsson.oss.services.topologyservice.api.MoEntity;

public class MoEntityStoreImplTest {

    @Test
    public void getListTest() {
        final MoEntityStore moEntityStore = new MoEntityStoreImpl();

        final List<MoEntity> moEntityList = new ArrayList<MoEntity>();

        moEntityStore.setList(moEntityList);

        assertEquals(moEntityList, moEntityStore.getList());
    }
}
