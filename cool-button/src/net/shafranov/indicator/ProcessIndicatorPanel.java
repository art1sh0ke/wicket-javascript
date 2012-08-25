package net.shafranov.indicator;

import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.resource.PackageResourceReference;

/**
 * Panel for AJAX call indicator.
 * 
 * @author Artem Shafranov
 */
public class ProcessIndicatorPanel extends Panel {

    private static final long serialVersionUID = 1L;

    /** Process indicator container */
    private WebMarkupContainer indicator;

    /**
     * @param message
     *            Message to show while indicating
     */
    public ProcessIndicatorPanel(String wicketId, String message) {
        super(wicketId);

        this.indicator = new WebMarkupContainer("indicator");
        add(this.indicator);
        this.indicator.setOutputMarkupId(true);

        this.indicator.add(new Image("indicator_image", new PackageResourceReference(
                AbstractDefaultAjaxBehavior.class, "indicator.gif")));
        this.indicator.add(new Label("indicator_text", message));
    }

    /**
     * Generate JavaScript to display indicator.
     * 
     * @return
     */
    public String getDisplayJS() {
        return "document.getElementById('" + this.indicator.getMarkupId() + "').style.display = 'block';";
    }

    /**
     * Generate JavaScript to hide indicator.
     * 
     * @return
     */
    public String getHideJS() {
        return "document.getElementById('" + this.indicator.getMarkupId() + "').style.display = 'none';";
    }

}
