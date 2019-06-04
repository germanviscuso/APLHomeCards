# APL Home Cards
A way to wrap the response builder to transparently send home card like responses to multimodal devices using APL

## Node
- Enable APL in the voice interaction model Interfaces section
- Add aplcards.js to the project
- Add a request interceptor like this: *.addRequestInterceptors(require('./aplcard').APLHomeCardRequestInterceptor)*
- Just use home cards as usual! (*withSimpleCard(), withStandardCard()*)
- Recommended resolutions and size limitations for small and large images in home cards are described [here](https://developer.amazon.com/docs/custom-skills/include-a-card-in-your-skills-response.html#image_size)
- In the file aplcard.js you'll find the APL document that gets rendered. There's a default background image for simpple cards (where no images are passed as parameters). Please replace with your own default background

## Python
- Enable APL in the voice interaction model Interfaces section.
- Add *APLHomeCard.py* to the project.
- Add an APL Document to the project directory.
- Add the following import:
	```
	from APLHomeCard import add_apl_home_card
	```
	
- Add a response interceptor that calls:
	```
	add_apl_home_card(handler_input, response, images, apl_document_name)
	``` 
	
	where:
	
	- *images* is a dictionary that contains the URLS of each image size of each card type you would like to display. Recommended resolutions and size limitations for small and large images in home cards are described [here](https://developer.amazon.com/docs/custom-skills/include-a-card-in-your-skills-response.html#image_size).

	- *apl_document_name* is the path and filename of the APL Document JSON you would like to use.

- Just use home cards as usual! (*.set_card(SimpleCard(…))*, *.set_card(StandardCard(…))*).