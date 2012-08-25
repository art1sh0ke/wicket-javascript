package net.shafranov.coolbutton.page;

import net.shafranov.coolbutton.behaviors.CoolButtonClickAjaxBehavior;
import net.shafranov.coolbutton.behaviors.CoolButtonMouseEventsBehavior;
import net.shafranov.coolbutton.component.CoolButton;
import net.shafranov.coolbutton.component.CoolButtonProperties;
import net.shafranov.indicator.ProcessIndicatorPanel;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.ajax.calldecorator.AjaxCallDecorator;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.PropertyModel;


/**
 * Page for CoolButton component testing.
 * 
 * @author Artem Shafranov
 */
public class CoolButtonPage extends WebPage {

    private static final long serialVersionUID = 1826445996202750874L;

    /** Cool button colors */
    private static final String FONT_COLOR = "#e3c3c6";
    private static final String BACK_COLOR_NORMAL = "#721f61";
    private static final String BACK_COLOR_OVER = "#aa6a87";

    /** Result label */
    private Label resultLabel;

    /** Container with AJAX call process indicator */
    private ProcessIndicatorPanel processIndicator;

    /** Initial text of the cool label */
    private String resultText = "";

    /**
     * 
     */
    public CoolButtonPage() {
        // Cool Button component
        CoolButtonProperties theCoolestButtonProps = new CoolButtonProperties("Сделать все красиво");
        theCoolestButtonProps.setWidth(280);
        theCoolestButtonProps.setHeight(80);
        theCoolestButtonProps.setTextColor(FONT_COLOR);
        theCoolestButtonProps.setBackColor(BACK_COLOR_NORMAL);

        CoolButton theCoolestButton = new CoolButton("the_coolest_button", theCoolestButtonProps);
        add(theCoolestButton);

        // mouse events handlers
        theCoolestButton.add(new CoolButtonMouseEventsBehavior() {

            private static final long serialVersionUID = -7387648564640026842L;

            @Override
            protected String getMouseOverJavaScript(CoolButton coolButton) {
                return coolButton.getJSFunctionCall("backColor", BACK_COLOR_OVER);
            }

            @Override
            protected String getMouseOutJavaScript(CoolButton coolButton) {
                return coolButton.getJSFunctionCall("backColor", BACK_COLOR_NORMAL);
            }
        });

        // onClick event handler
        theCoolestButton.add(new CoolButtonClickAjaxBehavior() {

            private static final long serialVersionUID = -4778770471950983548L;

            @Override
            protected void onClick(AjaxRequestTarget target) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                }

                resultText = "Все сделано по высшему разряду!";

                target.add(resultLabel);
            }

            @Override
            protected IAjaxCallDecorator getAjaxCallDecorator() {
                return new AjaxCallDecorator() {

                    private static final long serialVersionUID = 1L;

                    @Override
                    public CharSequence decorateScript(Component c, CharSequence script) {
                        return processIndicator.getDisplayJS() + super.decorateScript(c, script);
                    }

                    @Override
                    public CharSequence decorateOnSuccessScript(Component c, CharSequence script) {
                        return decorateFinishScript(super.decorateOnSuccessScript(c, script));
                    }

                    @Override
                    public CharSequence decorateOnFailureScript(Component c, CharSequence script) {
                        return decorateFinishScript(super.decorateOnFailureScript(c, script));
                    }

                    private CharSequence decorateFinishScript(CharSequence script) {
                        return processIndicator.getHideJS() + script;
                    }
                };
            }

        });

        // AJAX call process indicator
        this.processIndicator = new ProcessIndicatorPanel("process_indicator", "Делаем красиво...");
        add(this.processIndicator);

        // Result Label
        this.resultLabel = new Label("result_label", new PropertyModel<String>(this, "resultText"));
        add(this.resultLabel);
        this.resultLabel.setOutputMarkupId(true);
    }

    /**
     * @return the coolLabelText
     */
    public String getCoolLabelText() {
        return resultText;
    }

    /**
     * @param coolLabelText
     *            the coolLabelText to set
     */
    public void setCoolLabelText(String coolLabelText) {
        this.resultText = coolLabelText;
    }

}
