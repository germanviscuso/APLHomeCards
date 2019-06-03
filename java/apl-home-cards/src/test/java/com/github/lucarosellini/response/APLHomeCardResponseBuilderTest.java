package com.github.lucarosellini.response;

import com.amazon.ask.response.ResponseBuilder;
import com.github.lucarosellini.util.Utils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class APLHomeCardResponseBuilderTest {

    @Mock
    private ResponseBuilder responseBuilder;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testResourceToMap() throws IOException, URISyntaxException {
        Map<String, Object> myMap = Utils.resourceToMap("card-apl-doc.json");
        Assert.assertNotNull(myMap);
        Assert.assertFalse(myMap.isEmpty());
    }

    @Test
    public void testInitialization(){
        APLHomeCardResponseBuilder aplHomeCardResponseBuilder = new APLHomeCardResponseBuilder(responseBuilder);
        Assert.assertNotNull(aplHomeCardResponseBuilder.getAplDoc());
    }

    @Test
    public void testSimpleCard(){
        APLHomeCardResponseBuilder aplHomeCardResponseBuilder = new APLHomeCardResponseBuilder(responseBuilder);
        Assert.assertNotNull(aplHomeCardResponseBuilder.getAplDoc());

        aplHomeCardResponseBuilder.withSimpleCard("simplecard title", "simple card text");
    }
}
