/**
 * Cool Button JavaScript component.
 * 
 * @param properties
 *            Component properties
 */
var CoolButton = function(properties) {
	
	// check the using of 'new' in user code
	if (!(this instanceof CoolButton)) {
		throw new Error("CoolButton should be invoked with the 'new' operator.");
	}
	
	// component variables
	var that = this;
	var caption = properties.caption ? properties.caption : "";
	var width = properties.width ? properties.width : 200;
	var height = properties.height ? properties.height : 100;
	var textColor = properties.textColor ? properties.textColor : "white";
	var backColor = properties.backColor ? properties.backColor : "blue";	
	var buttonElem = document.getElementById(properties.elementId);
	
	if (!buttonElem) {
		throw new Error("Target HTML element for CoolButton doesn't exist.");
	} else {
		// caption getter / setter
		this.caption = function(newCaption) {
			if (newCaption) {
				caption = newCaption; 
				buttonElem.innerHTML = newCaption;
				return that;
			} else {
				return caption;
			}
		};
		
		// width getter / setter
		this.width = function(newWidth) {
			if (newWidth) {
				width = newWidth;
				buttonElem.style.width = newWidth + "px";
				return that;
			} else {
				return width;
			}
		};

		// height getter / setter
		this.height = function(newHeight) {
			if (newHeight) {
				height = newHeight;
				buttonElem.style.height = newHeight + "px";
				buttonElem.style.lineHeight = newHeight + "px";
				return that;
			} else {
				return height;
			}
		};

		// text color getter / setter
		this.textColor = function(newTextColor) {
			if (newTextColor) {
				textColor = newTextColor;
				buttonElem.style.color = newTextColor;
				return that;
			} else {
				return textColor;
			}
		};

		// background color getter / setter
		this.backColor = function(newBackColor) {
			if (newBackColor) {
				backColor = newBackColor;
				buttonElem.style.backgroundColor = newBackColor;
				buttonElem.style.borderColor = newBackColor;
				return that;
			} else {
				return backColor;
			}
		};

		// button styles
		buttonElem.style.fontSize = "14pt";
		buttonElem.style.textAlign = "center";
		buttonElem.style.cursor = "pointer";
		buttonElem.style.borderStyle = "outset";
		buttonElem.style.borderWidth = "5px";
		buttonElem.style.borderRadius = "10px";
		// set styles through properties
		this.width(width);
		this.height(height);
		this.textColor(textColor);
		this.backColor(backColor);

		// button text
		this.caption(caption);
		
		// method that adds onClick handler to button
		this.addOnClickHandler = function(handler) {
			addHandler(buttonElem, "click", handler, false);
		};
		
		// method that adds onMouseOver handler to button
		this.addOnMouseOverHandler = function(handler) {
			addHandler(buttonElem, "mouseover", handler, false);
		};

		// method that adds onMouseOut handler to button
		this.addOnMouseOutHandler = function(handler) {
			addHandler(buttonElem, "mouseout", handler, false);
		};
	}

	// add event handler to element
	function addHandler(element, event, handler, useCapture) {
		if (element.addEventListener) {
			element.addEventListener(event, handler, useCapture ? useCapture
					: false);
		} else if (element.attachEvent) {
			element.attachEvent("on" + event, handler);
		} else {
			throw new Error("Adding handlers is not supported");
		}
	}
};