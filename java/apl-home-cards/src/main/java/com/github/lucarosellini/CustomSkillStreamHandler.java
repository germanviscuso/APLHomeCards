package com.github.lucarosellini;

import com.amazon.ask.Skill;
import com.amazon.ask.SkillStreamHandler;
import com.amazon.ask.Skills;
import com.github.lucarosellini.handler.*;
import com.github.lucarosellini.interceptor.APLHomeCardInterceptor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Lambda initializer.
 */
public class CustomSkillStreamHandler extends SkillStreamHandler {
    private static final Logger logger = LogManager.getLogger(CustomSkillStreamHandler.class);

    public CustomSkillStreamHandler() {
        super(getSkill());
    }

    @SuppressWarnings("unchecked")
    private static Skill getSkill() {
        logger.info("Initializing " + CustomSkillStreamHandler.class.getName());
        return Skills.standard()
                .addRequestHandlers(
                        new CancelIntentHandler(),
                        new StopIntentHandler(),
                        new SessionEndedRequestHandler(),
                        new NavigateHomeIntentHandler(),
                        new HelloWorldIntentHandler(),
                        new HelpIntentHandler(),
                        new LaunchRequestHandler())
                .addRequestInterceptor(new APLHomeCardInterceptor())
                .addExceptionHandler(new ExceptionHandler())
                .build();
    }
}