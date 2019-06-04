# -*- coding: utf-8 -*-


import logging


from ask_sdk_core.skill_builder import SkillBuilder
from ask_sdk_model.ui import SimpleCard, StandardCard, Image
from ask_sdk_runtime.dispatch_components import (AbstractExceptionHandler,
                                              AbstractRequestHandler,
                                              AbstractResponseInterceptor,
                                              AbstractRequestInterceptor)
from ask_sdk_core.utils import is_request_type, is_intent_name
from APLHomeCard import add_apl_home_card


standard_small_img = 'https://s3-eu-west-1.amazonaws.com/happybirthday-alexa/garlands_720x480_card_small.png'
standard_large_img = 'https://s3-eu-west-1.amazonaws.com/happybirthday-alexa/garlands_1200x800_card_large.png'

simple_small_img = 'https://s3-eu-west-1.amazonaws.com/happybirthday-alexa' \
                  '/garlands_720x480_card_small.png'
simple_large_img = 'https://s3-eu-west-1.amazonaws.com/happybirthday-alexa/' \
                   'papers_1200x800_card_large.png'


sb = SkillBuilder()


logger = logging.getLogger(__name__)
logger.setLevel(logging.INFO)


class LaunchRequestHandler(AbstractRequestHandler):
    def can_handle(self, handler_input):
        return is_request_type("LaunchRequest")(handler_input)

    def handle(self, handler_input):
        logger.info("In LaunchRequest")
        speechText = '<lang xml:lang="en-US">Say hello</lang>, <lang xml:lang="es-ES">Dime hola</lang>'
        handler_input.response_builder.speak(speechText).set_card(
            SimpleCard('Simple Card / Tarjeta Simple',
                       'Lorem ipsum dolor sit amet, consectetur adipiscing')).\
            ask(speechText).\
            set_should_end_session(False)
        return handler_input.response_builder.response


class HelloWorldIntentHandler(AbstractRequestHandler):
    def can_handle(self, handler_input):
        return is_intent_name("HelloWorldIntent")(handler_input)

    def handle(self, handler_input):
        logger.info("In HelloWorld")
        speechText = '<lang xml:lang="en-US">Hello World</lang>, <lang xml:lang="es-ES">Hola ' \
                     'Mundo</lang>'
        handler_input.response_builder.speak(speechText).set_card(
            StandardCard(title='Standard Card / Tarjeta Estandar',
                         text='Lorem ipsum dolor sit amet, consectetur adipiscing',
                         image=Image(small_image_url=standard_small_img,
                                     large_image_url=standard_large_img))).\
            ask(speechText).\
            set_should_end_session(False)
        return handler_input.response_builder.response


class HelpIntentHandler(AbstractRequestHandler):
    def can_handle(self, handler_input):
        return is_intent_name("AMAZON.HelpIntent")(handler_input)

    def handle(self, handler_input):
        logger.info("In HelpIntent")
        outputSpeech = '<lang xml:lang="en-US">You can say hello. How can I help you?</lang> <lang xml:lang="es-ES">Puedes decir hola. Cómo te puedo ayudar?</lang>'
        handler_input.response_builder.speak(outputSpeech).ask(outputSpeech)
        return handler_input.response_builder.response


class CancelAndStopIntentHandler(AbstractRequestHandler):
    def can_handle(self, handler_input):
        return is_intent_name("AMAZON.CancelIntent")(handler_input) or \
               is_intent_name("AMAZON.StopIntent")(handler_input)

    def handle(self, handler_input):
        logger.info("In CancelAndStopIntent")
        outputSpeech = '<lang xml:lang="en-US">Bye bye!</lang>, ' \
                       '<lang xml:lang="es-ES">¡Hasta Luego!</lang>'

        handler_input.response_builder.speak(outputSpeech)
        return handler_input.response_builder.set_should_end_session(True).response


class SessionEndedRequestHandler(AbstractRequestHandler):
    def can_handle(self, handler_input):
        return is_request_type("SessionEndedRequest")(handler_input)

    def handle(self, handler_input):
        return handler_input.response_builder.response


class AllExceptionHandler(AbstractExceptionHandler):
    def can_handle(self, handler_input, exception):
        return True

    def handle(self, handler_input, exception):
        logger.info("In AllExceptionHandler")
        logger.info(f'~~~~ Error handled: {exception}')

        return handler_input.response_builder.speak('EXCEPTION_MESSAGE').response


class APLHomeCardRequestInterceptor(AbstractRequestInterceptor):
    def process(self, handler_input):
        logger.info(f'Alexa Request: {handler_input.request_envelope.request}')


class APLHomeCardResponseInterceptor(AbstractResponseInterceptor):
    def process(self, handler_input, response):
        logger.info(f'Alexa Response: {response}')

        images = {
            'Simple': {
                'small' : simple_small_img,
                'large' : simple_large_img
            },
            'Standard': {
                'small': standard_small_img,
                'large': standard_large_img
            }
        }

        add_apl_home_card(handler_input, response, images=images, apl_document_name='aplDoc.json')


sb.add_request_handler(LaunchRequestHandler())
sb.add_request_handler(HelloWorldIntentHandler())
sb.add_request_handler(HelpIntentHandler())
sb.add_request_handler(SessionEndedRequestHandler())
sb.add_request_handler(CancelAndStopIntentHandler())
sb.add_exception_handler(AllExceptionHandler())

sb.add_global_request_interceptor(APLHomeCardRequestInterceptor())
sb.add_global_response_interceptor(APLHomeCardResponseInterceptor())

handler = sb.lambda_handler()