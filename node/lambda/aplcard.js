const APLHomeCardRequestInterceptor = {
    process(handlerInput) {
        const withSimpleCard = handlerInput.responseBuilder.withSimpleCard;
        const withStandardCard = handlerInput.responseBuilder.withStandardCard;
        function withSimpleAPLCard(cardTitle, cardContent){
            if(supportsAPL(handlerInput)){
                handlerInput.responseBuilder.addDirective({
                    type: 'Alexa.Presentation.APL.RenderDocument',
                    version: '1.0',
                    document: APLDoc,
                    datasources: {
                        templateData: {
                            "header": cardTitle,
                            "text": cardContent,
                            // default background for simple card
                            "backgroundSmall": "https://s3-eu-west-1.amazonaws.com/happybirthday-alexa/papers_720x480_card_small.png",
                            "backgroundLarge": "https://s3-eu-west-1.amazonaws.com/happybirthday-alexa/papers_1200x800_card_large.png"
                        }
                    }
                })
            }
            withSimpleCard(cardTitle, cardContent);
            return handlerInput.responseBuilder;
        }
        function withStandardAPLCard(cardTitle, cardContent, smallImageUrl, largeImageUrl){
            if(supportsAPL(handlerInput)){
                handlerInput.responseBuilder.addDirective({
                    type: 'Alexa.Presentation.APL.RenderDocument',
                    version: '1.0',
                    document: APLDoc,
                    datasources: {
                        templateData: {
                            "header": cardTitle,
                            "text": cardContent,
                            "backgroundSmall": smallImageUrl,
                            "backgroundLarge": largeImageUrl
                        }
                    }
                })
            }
            withStandardCard(cardTitle, cardContent, smallImageUrl, largeImageUrl);
            return handlerInput.responseBuilder;
        }
        handlerInput.responseBuilder.withSimpleCard = (...args) => withSimpleAPLCard(...args);
        handlerInput.responseBuilder.withStandardCard = (...args) => withStandardAPLCard(...args);
    }
}

function supportsAPL(handlerInput){
    const {supportedInterfaces} = handlerInput.requestEnvelope.context.System.device;
    return supportedInterfaces['Alexa.Presentation.APL'];
}

function deviceType(handlerInput){
    if(supportsAPL(handlerInput)){
        const {Viewport} = handlerInput.requestEnvelope.context;
        const resolution = Viewport.pixelWidth + 'x' + Viewport.pixelHeight;
        switch(resolution){
            case "480x480": return "EchoSpot";
            case "960x480": return "EchoShow5";
            case "1024x600": return "EchoShow";
            case "1200x800": return "FireHD8";
            case "1280x800": return "EchoShow2";
            case "1920x1080": return "FireTV";
            case "1920x1200": return "FireHD10";
            default: return "unknown";
        }
    } else {
        return "screenless";
    }
}

const APLDoc = 
{
    "type": "APL",
    "version": "1.0",
    "import": [
        {
            "name": "alexa-styles",
            "version" : "1.0.0"
        },
        {
            "name": "alexa-layouts",
            "version" : "1.0.0"
        },
        {
            "name": "alexa-viewport-profiles",
            "version" : "1.0.0"
        }
    ],
    "resources": [
        {
            "when": "${viewport.shape == 'round'}",
            "dimensions": {
            "myTextTopPadding": "0dp"
            }
        },
        {
            "when": "${@viewportProfile == @hubLandscapeSmall || @viewportProfile == @hubLandscapeMedium || @viewportProfile == @hubLandscapeLarge || @viewportProfile == @tvLandscapeXLarge}",
            "dimensions": {
            "myTextTopPadding": "50dp"
            }
        }
    ],
    "styles": {},
    "layouts": {
        "CentralLayout": {
            "description": "A basic central screen layout with an image and a text",
            "parameters": [
                {
                    "name": "image",
                    "type": "string"
                },
                {
                    "name": "text",
                    "type": "string"
                }
            ],
            "items": [
                {
                    "type": "Container",
                    "width": "100vw",
                    "height": "100vh",
                    "alignItems": "center",
                    "justifyContent": "center",
                    "item": [
                        {
                            "type": "Image",
                            "source": "${image}",
                            "width": "50vw",
                            "height": "50vh"
                        },
                        {
                            "type": "Text",
                            "text": "${text}",
                            "color" : "#FFFFFF",
                            "textAlign": "center",
                            "style" : "textStyleDisplay4",
                            "paddingTop" : "@myTextTopPadding"
                        }
                    ]
                }
            ]
        }
    },
    "mainTemplate": {
        "parameters": [
            "payload"
        ],
        "items": [
            {
                "type": "Container",
                "items": [
                    {
                        "type": "Image",
                        "source": "${viewport.shape == 'round' || !payload.templateData.backgroundLarge ? payload.templateData.backgroundSmall : payload.templateData.backgroundLarge}",
                        "scale": "best-fill",
                        "width": "100vw",
                        "height": "100vh",
                        "opacity": 0.8,
                        "filters": [
                            {
                            "type": "Blur",
                            "radius": "5dp"
                            }
                        ]
                    },
                    {
                        "type": "Container",
                        "position": "absolute",
                        "width": "100vw",
                        "height": "100vh",
                        "items": [
                            {
                                "type": "AlexaHeader",
                                "headerTitle": "${payload.templateData.header}"
                            },
                            {
                                "when": "${viewport.shape == 'round'}",
                                "type": "Container",
                                "width": "100vw",
                                "height": "60vh",
                                "alignItems": "center",
                                "justifyContent": "center",
                                "items": [
                                    {
                                        "type": "CentralLayout",
                                        "image": "${viewport.shape == 'round' || !payload.templateData.backgroundLarge ? payload.templateData.backgroundSmall : payload.templateData.backgroundLarge}",
                                        "text": "${payload.templateData.text}"
                                    }
                                ]
                            },
                            {
                                "when": "${@viewportProfile == @hubLandscapeSmall || @viewportProfile == @hubLandscapeMedium || @viewportProfile == @hubLandscapeLarge || @viewportProfile == @tvLandscapeXLarge}",
                                "type": "Container",
                                "width": "100vw",
                                "height": "90vh",
                                "alignItems": "center",
                                "justifyContent": "center",
                                "items": [
                                    {
                                        "type": "CentralLayout",
                                        "image": "${viewport.shape == 'round' || !payload.templateData.backgroundLarge ? payload.templateData.backgroundSmall : payload.templateData.backgroundLarge}",
                                        "text": "${payload.templateData.text}"
                                    }
                                ]
                            }
                        ]
                    }
                ]
            }
        ]
    }
}

module.exports = {
    APLHomeCardRequestInterceptor
}