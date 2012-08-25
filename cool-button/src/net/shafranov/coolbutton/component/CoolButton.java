/**
 * 
 */
package net.shafranov.coolbutton.component;

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.util.template.PackageTextTemplate;
import org.apache.wicket.util.template.TextTemplate;

/**
 * Cool Button JavaScript component.
 * 
 * @author Artem Shafranov
 */
public class CoolButton extends WebComponent {

    private static final long serialVersionUID = -8256747527947224951L;

    /** Initialization properties */
    private CoolButtonProperties properties;

    /**
     * @param id
     * @param properties
     */
    public CoolButton(String id, CoolButtonProperties properties) {
        super(id);
        // render markup id for JavaScript part of component
        setOutputMarkupId(true);

        if (properties == null) {
            throw new IllegalArgumentException("CoolButton component properties can't be null.");
        }
        this.properties = properties;
    }

    /*
     * (non-Javadoc)
     * @see org.apache.wicket.MarkupContainer#onComponentTagBody(org.apache.wicket.markup.MarkupStream, org.apache.wicket.markup.ComponentTag)
     */
    @Override
    public void onComponentTagBody(MarkupStream markupStream, ComponentTag openTag) {
        // making component tag empty
        replaceComponentTagBody(markupStream, openTag, "");
    }

    /*
     * (non-Javadoc)
     * @see org.apache.wicket.Component#onComponentTag(org.apache.wicket.markup.ComponentTag)
     */
    @Override
    protected void onComponentTag(ComponentTag tag) {
        super.onComponentTag(tag);
        // make sure that our JS button will render in the container of right tag
        tag.setName("div");
    }

    /*
     * (non-Javadoc)
     * @see org.apache.wicket.Component#renderHead(org.apache.wicket.markup.html.IHeaderResponse)
     */
    @Override
    public void renderHead(IHeaderResponse response) {
        renderReferences(response);
        renderInitJavaScript(response);
    }

    /**
     * @return this Cool Button JavaScript variable name
     */
    public String getJSVariableName() {
        // we can use markup id as component JavaScript variable name 
        // because it's unique on the page
        return getJSNamespace() + "." + this.getMarkupId();
    }

    /**
     * Generate call to function of the JavaScript component.
     * 
     * @param functionName
     * @param objects
     * @return
     */
    public String getJSFunctionCall(String functionName, Object... parameters) {
        StringBuilder functionCallBuilder = new StringBuilder(getJSVariableName());
        // call to function...
        functionCallBuilder.append(".").append(functionName).append("(");
        // with parameters...
        for (Object parameter : parameters) {
            if (parameter == null || parameter instanceof Number || parameter instanceof Boolean) {
                // in case of null, numbers and booleans simply append parameter's value
                functionCallBuilder.append(parameter);
            } else {
                // otherwise, name should be in quotes
                functionCallBuilder.append("\"").append(parameter).append("\"");
            }
        }
        functionCallBuilder.append(");");
        return functionCallBuilder.toString();
    }

    /**
     * Render CSS and JavaScript references for this component.
     * 
     * @param response
     */
    private void renderReferences(IHeaderResponse response) {
        // render reference to Cool Button JavaScript file
        response.renderJavaScriptReference(new PackageResourceReference(CoolButton.class, "cool-button.js"));
    }

    /**
     * Render initialization JavaScript for this component.
     * 
     * @param response
     */
    private void renderInitJavaScript(IHeaderResponse response) {
        // get initialization JavaScript template from file
        TextTemplate initJSTemplate = new PackageTextTemplate(CoolButton.class, "init.js.template");

        // fill variables map
        Map<String, Object> variables = new HashMap<String, Object>();
        // id of HTML block to render component
        variables.put("elementId", this.getMarkupId());
        // JavaScript namespace for this component
        variables.put("namespace", this.getJSNamespace());
        // JavaScript variable name for this component
        variables.put("variableName", this.getJSVariableName());
        // component properties
        variables.put("caption", this.properties.getCaption());
        variables.put("width", this.properties.getWidth());
        variables.put("height", this.properties.getHeight());
        variables.put("textColor", this.properties.getTextColor());
        variables.put("backColor", this.properties.getBackColor());

        // render variables into template
        initJSTemplate.interpolate(variables);

        // render initialization JavaScript as OnDomReady event handler 
        response.renderOnDomReadyJavaScript(initJSTemplate.asString());
    }

    /**
     * @return JavaScript namespace for this component
     */
    private String getJSNamespace() {
        return "Wicket.CoolComponents";
    }
}
