package com.github.lucarosellini.response;

import com.amazon.ask.model.Directive;
import com.amazon.ask.model.Intent;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.canfulfill.CanFulfillIntent;
import com.amazon.ask.model.interfaces.alexa.presentation.apl.RenderDocumentDirective;
import com.amazon.ask.model.interfaces.audioplayer.ClearBehavior;
import com.amazon.ask.model.interfaces.display.Template;
import com.amazon.ask.model.ui.Card;
import com.amazon.ask.model.ui.Image;
import com.amazon.ask.model.ui.PlayBehavior;
import com.amazon.ask.response.ResponseBuilder;
import com.github.lucarosellini.util.Constants;
import com.github.lucarosellini.util.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * ResponseBuilder decorator that wraps the standard ASK ResponseBuilder and adds custom logic to
 * withSimpleCard and withStandardCard.
 *
 * To preserve ResponseBuilder's fluent interface all methods had to be overridden to return the current instance.
 */
public class APLHomeCardResponseBuilder extends com.amazon.ask.response.ResponseBuilder {
    private static final Logger logger = LogManager.getLogger(APLHomeCardResponseBuilder.class);

    private ResponseBuilder responseBuilder;
    private Map<String, Object> aplDoc = new HashMap<>();

    public APLHomeCardResponseBuilder(ResponseBuilder responseBuilder) {
        this.responseBuilder = responseBuilder;
        try {
            aplDoc = Utils.resourceToMap("card-apl-doc.json");


        } catch (Exception e) {
            logger.error("Exception", e);
        }
    }

    @Override
    public Optional<Response> build() {
        return responseBuilder.build();
    }

    @Override
    public ResponseBuilder withSpeech(String speechText) {
        responseBuilder.withSpeech(speechText);
        return this;
    }

    @Override
    public ResponseBuilder withSpeech(String speechText, PlayBehavior playBehavior) {
        responseBuilder.withSpeech(speechText, playBehavior);
        return this;
    }

    @Override
    public ResponseBuilder withCard(Card card) {
        responseBuilder.withCard(card);
        return this;
    }

    // TODO: add custom logic
    @Override
    @SuppressWarnings("unchecked")
    public ResponseBuilder withSimpleCard(String cardTitle, String cardText) {
        logger.info("Here the APL logic for withSimpleCard will be wired");
        responseBuilder.withSimpleCard(cardTitle, cardText);

        try {
            Map<String, Object> simpleCardDS = Utils.resourceToMap("card-ds.json");
            Map<String, Object> templateData = ((Map<String, Object>)simpleCardDS.get("templateData"));
            templateData.put("header", cardTitle);
            templateData.put("text", cardText);
            templateData.put("backgroundSmall", Constants.DEFAULT_BACKGROUND_SMALL);
            templateData.put("backgroundLarge", Constants.DEFAULT_BACKGROUND_LARGE);

            RenderDocumentDirective directive = RenderDocumentDirective.builder()
                    .withToken("simple-apl-card")
                    .withDocument(aplDoc)
                    .withDatasources(simpleCardDS)
                    .build();

            responseBuilder.addDirective(directive);
        } catch (Exception e) {
            logger.error(e);
        }

        return this;
    }


    // TODO: add custom logic
    @Override
    @SuppressWarnings("unchecked")
    public ResponseBuilder withStandardCard(String cardTitle, String cardText, Image image) {
        logger.info("Here the APL logic for withStandardCard will be wired");

        responseBuilder.withStandardCard(cardTitle, cardText, image);

        try {
            Map<String, Object> standardCardDS = Utils.resourceToMap("card-ds.json");
            Map<String, Object> templateData = ((Map<String, Object>)standardCardDS.get("templateData"));
            templateData.put("header", cardTitle);
            templateData.put("text", cardText);
            templateData.put("backgroundSmall", image.getSmallImageUrl());
            templateData.put("backgroundLarge", image.getLargeImageUrl());

            RenderDocumentDirective directive = RenderDocumentDirective.builder()
                    .withToken("standard-apl-card")
                    .withDocument(aplDoc)
                    .withDatasources(standardCardDS)
                    .build();

            responseBuilder.addDirective(directive);
        } catch (Exception e) {
            logger.error(e);
        }

        return this;
    }

    @Override
    public ResponseBuilder withLinkAccountCard() {
        responseBuilder.withLinkAccountCard();
        return this;
    }

    @Override
    public ResponseBuilder withAskForPermissionsConsentCard(List<String> permissions) {
        responseBuilder.withAskForPermissionsConsentCard(permissions);
        return this;
    }

    @Override
    public ResponseBuilder withReprompt(String text) {
        responseBuilder.withReprompt(text);
        return this;
    }

    @Override
    public ResponseBuilder withReprompt(String text, PlayBehavior playBehavior) {
        responseBuilder.withReprompt(text, playBehavior);
        return this;
    }

    @Override
    public ResponseBuilder withShouldEndSession(Boolean shouldEndSession) {
        responseBuilder.withShouldEndSession(shouldEndSession);
        return this;
    }

    @Override
    public ResponseBuilder addHintDirective(String hintText) {
        responseBuilder.addHintDirective(hintText);
        return this;
    }

    @Override
    public ResponseBuilder addVideoAppLaunchDirective(String source, String title, String subtitle) {
        responseBuilder.addVideoAppLaunchDirective(source, title, subtitle);
        return this;
    }

    @Override
    public ResponseBuilder addRenderTemplateDirective(Template template) {
        responseBuilder.addRenderTemplateDirective(template);
        return this;
    }

    @Override
    public ResponseBuilder addDelegateDirective(Intent updatedIntent) {
        responseBuilder.addDelegateDirective(updatedIntent);
        return this;
    }

    @Override
    public ResponseBuilder addElicitSlotDirective(String slotName, Intent updatedIntent) {
        responseBuilder.addElicitSlotDirective(slotName, updatedIntent);
        return this;
    }

    @Override
    public ResponseBuilder addConfirmSlotDirective(String slotName, Intent updatedIntent) {
        responseBuilder.addConfirmSlotDirective(slotName, updatedIntent);
        return this;
    }

    @Override
    public ResponseBuilder addConfirmIntentDirective(Intent updatedIntent) {
        responseBuilder.addConfirmIntentDirective(updatedIntent);
        return this;
    }

    @Override
    public ResponseBuilder addAudioPlayerPlayDirective(com.amazon.ask.model.interfaces.audioplayer.PlayBehavior playBehavior, Long offsetInMilliseconds, String expectedPreviousToken, String token, String url) {
        responseBuilder.addAudioPlayerPlayDirective(playBehavior, offsetInMilliseconds, expectedPreviousToken, token, url);
        return this;
    }

    @Override
    public ResponseBuilder addAudioPlayerStopDirective() {
        responseBuilder.addAudioPlayerStopDirective();
        return this;
    }

    @Override
    public ResponseBuilder addAudioPlayerClearQueueDirective(ClearBehavior clearBehavior) {
        responseBuilder.addAudioPlayerClearQueueDirective(clearBehavior);
        return this;
    }

    @Override
    public ResponseBuilder addDirective(Directive directive) {
        responseBuilder.addDirective(directive);
        return this;
    }

    @Override
    public ResponseBuilder withCanFulfillIntent(CanFulfillIntent canFulfillIntent) {
        responseBuilder.withCanFulfillIntent(canFulfillIntent);
        return this;
    }

    Map<String, Object> getAplDoc() {
        return aplDoc;
    }
}
