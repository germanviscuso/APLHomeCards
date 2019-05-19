# APL Home Cards
A way to wrap the response builder to send home card like responses to multimodal devices using APL

## Node
- Enable APL in the voice interaction model Interfaces section
- Add aplcards.js to the project
- Add a request interceptor like this: *.addRequestInterceptors(require('./aplcard').APLHomeCardRequestInterceptor)*
- Just use home cards as usual! (*withSimpleCard(), withStandardCard()*)
