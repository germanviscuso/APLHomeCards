package com.github.lucarosellini.interceptor;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.request.interceptor.GenericRequestInterceptor;
import com.github.lucarosellini.response.APLHomeCardResponseBuilder;
import com.github.lucarosellini.util.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;

/**
 * Request interceptor that, for each call coming from an APL-enabled device, overrides the responseBuilder object
 * with an instance of {@link APLHomeCardResponseBuilder}.
 */
public class APLHomeCardInterceptor implements GenericRequestInterceptor<HandlerInput> {
    private static final Logger logger = LogManager.getLogger(APLHomeCardInterceptor.class);

    @Override
    public void process(HandlerInput handlerInput) {

        if (Utils.supportsAPL(handlerInput)){
            logger.debug("Device supports APL");
            Field field = null;
            try {
                field = Utils.getField(handlerInput.getClass(), "responseBuilder");
                Utils.makePublic(field);

                field.set(handlerInput, new APLHomeCardResponseBuilder(handlerInput.getResponseBuilder()));
            } catch (NoSuchFieldException e) {
                logger.error("Cannot get responseBuilder field using reflection", e);
            } catch (IllegalAccessException e) {
                logger.error("Cannot set responseBuilder on current handlerInput", e);
            }
        } else {
            logger.debug("Device does NOT support APL");
        }


    }
}
