package dwss.nv.gov;

import java.util.Locale;

import com.vaadin.data.util.converter.Converter;
import com.vaadin.server.FontAwesome;


/**
 * A converter that allows displaying a Boolean as a comma separated list of
 * strings.
 */
public class BooleanToStringConverter implements
        Converter<String, Boolean> {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1984626801018697357L;

	@Override
    public Boolean convertToModel(String value,
            Class<? extends Boolean> targetType, Locale locale)
            throws com.vaadin.data.util.converter.Converter.ConversionException {
        throw new UnsupportedOperationException(
                "Can only convert from Boolean to String");
    }

    
    public String convertToPresentation(Boolean value,
            java.lang.Class<? extends String> targetType, Locale locale)
            throws Converter.ConversionException {

        String color = "#ffffff";
        String text = "NO";
        String returnString = new String();
        
        if (value == true) {
            color = "#2dd085";
            text = "YES";
            returnString = "<span class=\"v-icon\" style=\"font-family: "
                    + FontAwesome.CHECK_SQUARE_O.getFontFamily() + ";color:" + color
                    + "\">&#x"
                    + Integer.toHexString(FontAwesome.CHECK_SQUARE_O.getCodepoint())
                    + ";</span>";


        } else if (value == false) {
            color = "#f54993";
            returnString = "<span class=\"v-icon\" style=\"font-family: "
                    + FontAwesome.SQUARE_O.getFontFamily() + ";color:" + color
                    + "\">&#x"
                    + Integer.toHexString(FontAwesome.SQUARE_O.getCodepoint())
                    + ";</span>";
        	

        }
        
        return returnString + " " + text;

    };

    
    @Override
    public Class<Boolean> getModelType() {
        return Boolean.class;
    }

    @Override
    public Class<String> getPresentationType() {
        return String.class;
    }



}
