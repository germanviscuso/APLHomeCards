package com.github.lucarosellini.handler;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.ui.Image;
import com.github.lucarosellini.util.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;
import java.util.ResourceBundle;

import static com.amazon.ask.request.Predicates.intentName;
import static com.github.lucarosellini.util.Messages.HELLOWORLD_SPEECH;
import static com.github.lucarosellini.util.Utils.messageBundle;

public class HelloWorldIntentHandler implements RequestHandler {
    private static final Logger logger = LogManager.getLogger(HelloWorldIntentHandler.class);

    @Override
    public boolean canHandle(HandlerInput handlerInput) {
        return handlerInput.matches(intentName("HelloWorldIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput) {
        logger.info("HelloWorldIntentHandler");

        ResourceBundle messageBundle = messageBundle(handlerInput);

        return handlerInput.getResponseBuilder()
                .withSpeech(messageBundle.getString(HELLOWORLD_SPEECH))
                .withReprompt(messageBundle.getString(HELLOWORLD_SPEECH))
                .withStandardCard("Standard Card / Tarjeta Estandar",
                        "Lorem ipsum dolor sit amet, consectetur adipiscing",
                        Image.builder()
                                .withSmallImageUrl(Constants.SMALL_IMG)
                                .withLargeImageUrl(Constants.LARGE_IMG)
                                .build())
                .build();
    }
}
