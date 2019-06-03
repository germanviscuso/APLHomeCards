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

    public static Field getField(Class clazz, String fieldName) throws NoSuchFieldException {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            Class superClass = clazz.getSuperclass();
            if (superClass == null) {
                throw e;
            } else {
                return getField(superClass, fieldName);
            }
        }
    }

    public static void makePublic(Field field) {
        if (!Modifier.isPublic(field.getModifiers()) ||
                !Modifier.isPublic(field.getDeclaringClass().getModifiers())) {
            field.setAccessible(true);
        }
    }

    public static boolean supportsAPL(HandlerInput handlerInput) {
        try {
            return handlerInput.getRequestEnvelope().getContext().getSystem().getDevice()
                    .getSupportedInterfaces().getAlexaPresentationAPL() != null;
        } catch (Exception e) {
            logger.error("Cannot detect APL", e);
            return false;
        }
    }

    public static Map<String, Object> resourceToMap(String fileName) throws URISyntaxException, IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                OBJECT_MAPPER.getClass().getResourceAsStream("/" + fileName)
        ))) {
            return OBJECT_MAPPER.readValue(reader, new TypeReference<HashMap<String, Object>>() {
            });
        }
    }

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
