package net.shafranov.coolbutton.behaviors;

import java.util.HashMap;
import java.util.Map;

import net.shafranov.coolbutton.component.CoolButton;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.util.template.PackageTextTemplate;
import org.apache.wicket.util.template.TextTemplate;


/**
 * Behavior that adds mouse events handlers to Cool Button component.
 * 
 * @author Artem Shafranov
 */
public abstract class CoolButtonMouseEventsBehavior extends Behavior {

    private static final long serialVersionUID = -3168970070789302820L;

    /** Target component */
    private CoolButton coolButton;

    /*
     * (non-Javadoc)
     * @see org.apache.wicket.behavior.Behavior#onConfigure(org.apache.wicket.Component)
     */
    @Override
    public void onConfigure(Component component) {
        if (!(component instanceof CoolButton)) {
            throw new RuntimeException("Behavior should be attached to component of class "
                    + CoolButton.class.getCanonicalName());
        }
        this.coolButton = (CoolButton) component;
    }

    /*
     * (non-Javadoc)
     * @see org.apache.wicket.behavior.Behavior#renderHead(org.apache.wicket.Component, org.apache.wicket.markup.html.IHeaderResponse)
     */
    @Override
    public void renderHead(Component component, IHeaderResponse response) {
        super.renderHead(component, response);

        renderAddMouseEventHandlersJavaScript(response);
    }

    /**
     * Get onMouseOver event handler JavaScript.
     * 
     * @param coolButton
     *            event target component
     * @return
     */
    protected abstract String getMouseOverJavaScript(CoolButton coolButton);

    /**
     * Get onMouseOut event handler JavaScript.
     * 
     * @param coolButton
     *            event target component
     * @return
     */
    protected abstract String getMouseOutJavaScript(CoolButton coolButton);

    /**
     * Render add mouse events handlers JavaScript for target component.
     * 
     * @param response
     */
    private void renderAddMouseEventHandlersJavaScript(IHeaderResponse response) {
        // get add onClick handler JavaScript template from file
        TextTemplate mouseEventsJSTemplate = new PackageTextTemplate(CoolButtonMouseEventsBehavior.class,
                "mouse-events.js.template");

        // fill variables map
        Map<String, Object> variables = new HashMap<String, Object>();
        // JavaScript variable name for target component
        variables.put("variableName", coolButton.getJSVariableName());
        // mouse events handlers JavaScript
        variables.put("onMouseOverJS", getMouseOverJavaScript(coolButton));
        variables.put("onMouseOutJS", getMouseOutJavaScript(coolButton));

        // render variables into template
        mouseEventsJSTemplate.interpolate(variables);

        // render JavaScript as OnDomReady event handler 
        response.renderOnDomReadyJavaScript(mouseEventsJSTemplate.asString());
    }

}
