package common.enums;

import helpers.EnumUtils;

public enum Browser {
    CHROME("Chrome"),
        FIREFOX("firfox"),
            EDGE("edge");

    private final String value;

    Browser(final String value){this.value = value;}

    public static Browser fromValue(final String value)
    {
        return EnumUtils.find(Browser.class, browser -> browser.value, value);
    }

    public String getType(){
        return value;
    }
}
