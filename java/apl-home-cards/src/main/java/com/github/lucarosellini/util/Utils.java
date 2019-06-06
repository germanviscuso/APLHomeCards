package com.github.lucarosellini.util;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Request;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.lucarosellini.handler.LaunchRequestHandler;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import static com.github.lucarosellini.util.Constants.LOCALE_SEPARATOR;
import static java.util.ResourceBundle.getBundle;

public class Utils {
    private static final Logger logger = LogManager.getLogger(LaunchRequestHandler.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @SuppressWarnings("unchecked")
    private static <T extends Request> T castRequest(HandlerInput handlerInput, Class<T> clazz) {
        return (T) handlerInput.getRequestEnvelope().getRequest();
    }

    /**
     * Returns a localized resource bundle.
     *
     * @param input the handler input request. Needed to get the locale.
     * @return
     */
    public static ResourceBundle messageBundle(HandlerInput input) {
        String rawLocale = "us-US";

        try {

            rawLocale = (String) MethodUtils.invokeExactMethod(castRequest(input,
                    input.getRequestEnvelope().getRequest().getClass()), "getLocale");
        } catch (Exception ex) {
            logger.error(ex);
        }
        String[] locale = rawLocale.split(LOCALE_SEPARATOR);

        return getBundle("messages", new Locale(locale[0], locale[1]));
    }
}
