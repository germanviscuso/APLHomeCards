package com.github.lucarosellini.handler;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.Predicates;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;
import java.util.ResourceBundle;

import static com.github.lucarosellini.util.Messages.WELCOME_SPEECH;
import static com.github.lucarosellini.util.Utils.messageBundle;

public class LaunchRequestHandler implements RequestHandler {
    private static final Logger logger = LogManager.getLogger(LaunchRequestHandler.class);


    @Override
    public boolean canHandle(HandlerInput handlerInput) {
        return handlerInput.matches(Predicates.requestType(LaunchRequest.class));
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput) {
        logger.info("LaunchRequestHandler");
        ResourceBundle messageBundle = messageBundle(handlerInput);

        return handlerInput.getResponseBuilder()
                .withSpeech(messageBundle.getString(WELCOME_SPEECH))
                .withReprompt(messageBundle.getString(WELCOME_SPEECH))
                .withSimpleCard("Standard Card / Tarjeta Estandar",
                        "Lorem ipsum dolor sit amet, consectetur adipiscing")
                .build();
    }
}
