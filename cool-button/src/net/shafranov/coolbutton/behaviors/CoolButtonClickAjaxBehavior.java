package net.shafranov.coolbutton.behaviors;

import java.util.HashMap;
import java.util.Map;

import net.shafranov.coolbutton.component.CoolButton;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.util.template.PackageTextTemplate;
import org.apache.wicket.util.template.TextTemplate;


/**
 * Cool Button click event behavior that does AJAX call to server.
 * 
 * @author Artem Shafranov
 */
public abstract class CoolButtonClickAjaxBehavior extends AbstractDefaultAjaxBehavior {

    private static final long serialVersionUID = 7196383338036798293L;

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
     * @see org.apache.wicket.ajax.AbstractDefaultAjaxBehavior#respond(org.apache.wicket.ajax.AjaxRequestTarget)
     */
    @Override
    protected void respond(AjaxRequestTarget target) {
        try {
            onClick(target);
        } catch (RuntimeException e) {
            onError(target, e);
        }
    }

    /**
     * Server onClick event code.
     * 
     * @param target
     */
    protected abstract void onClick(AjaxRequestTarget target);

    /**
     * Override to handle any error during execution of server onClick event.
     * 
     * @param target
     * @param e
     */
    protected void onError(AjaxRequestTarget target, RuntimeException e) {
        if (e != null) {
            throw e;
        }
    }

    /*
     * (non-Javadoc)
     * @see org.apache.wicket.ajax.AbstractDefaultAjaxBehavior#renderHead(org.apache.wicket.Component, org.apache.wicket.markup.html.IHeaderResponse)
     */
    @Override
    public void renderHead(Component component, IHeaderResponse response) {
        super.renderHead(component, response);

        renderAddOnClickHandlerJavaScript(response);
    }

    /**
     * Render add onClick event handler for target component.
     * 
     * @param response
     */
    private void renderAddOnClickHandlerJavaScript(IHeaderResponse response) {
        // get add onClick handler JavaScript template from file
        TextTemplate addHandlerJSTemplate = new PackageTextTemplate(CoolButtonClickAjaxBehavior.class,
                "onclick-event.js.template");

        // fill variables map
        Map<String, Object> variables = new HashMap<String, Object>();
        // JavaScript variable name for target component
        variables.put("variableName", coolButton.getJSVariableName());
        // onClick handler JavaScript
        // in our case it's AJAX call to server 
        variables.put("onClickJS", getCallbackScript());

        // render variables into template
        addHandlerJSTemplate.interpolate(variables);

        // render JavaScript as OnDomReady event handler 
        response.renderOnDomReadyJavaScript(addHandlerJSTemplate.asString());
    }
}
