# APL Home Cards
A way to wrap the response builder to transparently send home card like responses to multimodal devices using APL

## Node
- Enable APL in the voice interaction model Interfaces section
- Add aplcards.js to the project
- Add a request interceptor like this: *.addRequestInterceptors(require('./aplcard').APLHomeCardRequestInterceptor)*
- Just use home cards as usual! (*withSimpleCard(), withStandardCard()*)
- Recommended resolutions and size limitations for small and large images in home cards are described [here](https://developer.amazon.com/docs/custom-skills/include-a-card-in-your-skills-response.html#image_size)
- In the file aplcard.js you'll find the APL document that gets rendered. There's a default background image for simpple cards (where no images are passed as parameters). Please replace with your own default background
