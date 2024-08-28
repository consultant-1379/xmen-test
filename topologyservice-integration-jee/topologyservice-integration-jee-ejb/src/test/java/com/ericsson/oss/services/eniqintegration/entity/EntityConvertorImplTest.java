package com.ericsson.oss.services.eniqintegration.entity;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.ericsson.oss.services.topologyservice.api.AttributeItem;
import com.ericsson.oss.services.topologyservice.api.MoEntity;

public class EntityConvertorImplTest {

    @Test
    public void convertToXMLStringTest() {

        final List<AttributeItem> list = new ArrayList<AttributeItem>();
        list.add(new AttributeItem("123", "123"));
        list.add(new AttributeItem("123", "123"));

        final MoEntity moEntity = new MoEntity("123", list);

        final List<MoEntity> moList = new ArrayList<MoEntity>();
        moList.add(moEntity);
        moList.add(moEntity);

        final MoEntityStoreImpl store = new MoEntityStoreImpl();
        store.list = moList;
        final EntityConvertorImpl factory = new EntityConvertorImpl();
        final String output = factory.convertToXMLString(store);

        final String correctRes = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
                + "<model>\n"
                + "    <mo>\n"
                + "        <fdn>123</fdn>\n"
                + "        <items>\n"
                + "            <key>123</key>\n"
                + "            <value xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xsi:type=\"xs:string\">123</value>\n"
                + "        </items>\n"
                + "        <items>\n"
                + "            <key>123</key>\n"
                + "            <value xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xsi:type=\"xs:string\">123</value>\n"
                + "        </items>\n"
                + "    </mo>\n"
                + "    <mo>\n"
                + "        <fdn>123</fdn>\n"
                + "        <items>\n"
                + "            <key>123</key>\n"
                + "            <value xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xsi:type=\"xs:string\">123</value>\n"
                + "        </items>\n"
                + "        <items>\n"
                + "            <key>123</key>\n"
                + "            <value xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xsi:type=\"xs:string\">123</value>\n"
                + "        </items>\n" + "    </mo>\n" + "</model>\n";
        // verify result
        assertEquals(correctRes, output);
    }

    @Test
    public void convertToXMLStringEmptyObject() {

        final ArrayList<AttributeItem> list = new ArrayList<AttributeItem>();
        list.add(new AttributeItem("123", "123"));
        list.add(new AttributeItem("123", "123"));
        list.add(new AttributeItem("123", "123"));
        list.add(new AttributeItem("123", "123"));

        final MoEntity moEntity = new MoEntity("123", list);

        final ArrayList<MoEntity> moList = new ArrayList<MoEntity>();
        moList.add(moEntity);
        moList.add(moEntity);
        moList.add(moEntity);
        moList.add(moEntity);
        moList.add(moEntity);

        final MoEntityStoreImpl store = new MoEntityStoreImpl();
        store.list = null;
        final EntityConvertorImpl factory = new EntityConvertorImpl();
        final String output = factory.convertToXMLString(store);

        // compared result
        final String correctRes = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n<model/>\n";

        // verify result
        assertEquals(correctRes, output);
    }
}
