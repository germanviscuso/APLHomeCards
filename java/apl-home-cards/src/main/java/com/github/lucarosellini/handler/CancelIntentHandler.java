package com.github.lucarosellini.handler;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.github.lucarosellini.util.Messages;

import java.util.Optional;
import java.util.ResourceBundle;

import static com.amazon.ask.request.Predicates.intentName;
import static com.github.lucarosellini.util.Constants.CANCEL_INTENT;
import static com.github.lucarosellini.util.Utils.messageBundle;

/**
 * Handles the Cancel mandatory intent.
 */
public class CancelIntentHandler implements RequestHandler {

    @Override
    public boolean canHandle(HandlerInput handlerInput) {
        return handlerInput.matches(intentName(CANCEL_INTENT));
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput) {
        ResourceBundle messageBundle = messageBundle(handlerInput);

        return handlerInput.getResponseBuilder()
                .withSpeech(messageBundle.getString(Messages.CANCEL_SPEECH))
                .build();
    }
}
