package net.shafranov.coolbutton.component;

import java.io.Serializable;

/**
 * Cool Button JavaScript component initialization properties.
 * 
 * @author Artem Shafranov
 */
public class CoolButtonProperties implements Serializable {

    private static final long serialVersionUID = -3689256728342206334L;

    /** Default properties values */
    private static final String DEFAULT_CAPTION = "No caption";
    private static final int DEFAULT_WIDTH = 150;
    private static final int DEFAULT_HEIGHT = 50;
    private static final String DEFAULT_TEXT_COLOR = "white";
    private static final String DEFAULT_BACK_COLOR = "blue";

    /** Button caption */
    private String caption;

    /** Button width */
    private int width;

    /** Button height */
    private int height;

    /** Button caption color (CSS color string or RGB-color string with #) */
    private String textColor;

    /** Button background color (CSS color string or RGB-color string with #) */
    private String backColor;

    /**
     * @param caption
     */
    public CoolButtonProperties(String caption) {
        this.caption = caption;
    }

    /**
     * @return the caption
     */
    public String getCaption() {
        return caption != null ? caption : DEFAULT_CAPTION;
    }

    /**
     * @param caption
     *            the caption to set
     */
    public void setCaption(String caption) {
        this.caption = caption;
    }

    /**
     * @return the width
     */
    public int getWidth() {
        return width > 0 ? width : DEFAULT_WIDTH;
    }

    /**
     * @param width
     *            the width to set
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * @return the height
     */
    public int getHeight() {
        return height > 0 ? height : DEFAULT_HEIGHT;
    }

    /**
     * @param height
     *            the height to set
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * @return the textColor
     */
    public String getTextColor() {
        return textColor != null ? textColor : DEFAULT_TEXT_COLOR;
    }

    /**
     * @param textColor
     *            the textColor to set
     */
    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    /**
     * @return the backColor
     */
    public String getBackColor() {
        return backColor != null ? backColor : DEFAULT_BACK_COLOR;
    }

    /**
     * @param backColor
     *            the backColor to set
     */
    public void setBackColor(String backColor) {
        this.backColor = backColor;
    }

}
