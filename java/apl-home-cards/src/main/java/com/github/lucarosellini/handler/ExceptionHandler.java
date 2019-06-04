package com.github.lucarosellini.handler;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.exception.handler.GenericExceptionHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class ExceptionHandler implements GenericExceptionHandler<HandlerInput, Optional<Response>> {
    private static final Logger logger = LogManager.getLogger(ExceptionHandler.class);

    @Override
    public boolean canHandle(HandlerInput handlerInput, Throwable throwable) {
        return true;
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput, Throwable t) {
        logger.error("Caught exception at processing the request", t);
        return Optional.empty();
    }
}
