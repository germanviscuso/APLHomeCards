# -*- coding: utf-8 -*-

import json

from ask_sdk_core.utils.request_util import get_supported_interfaces
from ask_sdk_model.interfaces.alexa.presentation.apl import RenderDocumentDirective


def add_apl_home_card(handler_input, response, images=None, apl_document_name=None):
    if response.card and get_supported_interfaces(handler_input).alexa_presentation_apl:
        if response.card.object_type == 'Simple':
            local_card_content = response.card.content
            background_small = images['Simple']['small']
            background_large = images['Simple']['large']
        elif response.card.object_type == 'Standard':
            local_card_content = response.card.text
            background_small = images['Standard']['small']
            background_large = images['Standard']['large']
        else:
            pass

        handler_input.response_builder.add_directive(RenderDocumentDirective(
            document=_load_apl_document(apl_document_name), datasources={
                'templateData': {
                    'header': response.card.title,
                    'text': local_card_content,
                    'backgroundSmall': background_small,
                    'backgroundLarge': background_large
                }
            }))


def _load_apl_document(file_path):
    try:
        with open(file_path) as f:
            return json.load(f)
    except IOError as e:
        print(f'Error: {e}')
        print(f'Error Code: {e.errno}')
        raise e

