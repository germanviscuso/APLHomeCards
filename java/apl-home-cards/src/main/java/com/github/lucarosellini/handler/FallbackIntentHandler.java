package com.github.lucarosellini.handler;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.github.lucarosellini.util.Constants;
import com.github.lucarosellini.util.Messages;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;
import java.util.ResourceBundle;

import static com.amazon.ask.request.Predicates.intentName;
import static com.github.lucarosellini.util.Utils.messageBundle;

/**
 * FallbackIntenet is not yet available in all locales.
 */
public class FallbackIntentHandler implements RequestHandler {
    private static final Logger logger = LogManager.getLogger(FallbackIntentHandler.class);

    @Override
    public boolean canHandle(HandlerInput handlerInput) {
        return handlerInput.matches(intentName(Constants.FALLBACK_INTENT));
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput) {
        ResourceBundle messageBundle = messageBundle(handlerInput);

        return handlerInput.getResponseBuilder()
                .withSpeech(messageBundle.getString(Messages.FALLBACK_SPEECH))
                .build();
    }
}
