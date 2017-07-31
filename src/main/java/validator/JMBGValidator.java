/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author hp
 */
@FacesValidator("JMBGValidator")
public class JMBGValidator implements Validator {

    private final ResourceBundle bundle = ResourceBundle.getBundle("internationalization.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());

    private static final String JMBG_PATTERN = "^[0-9]{13}$";

    private Pattern pattern;
    private Matcher matcher;

    public JMBGValidator() {
        pattern = Pattern.compile(JMBG_PATTERN);
    }

    @Override
    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        if(value == null || value.toString().length() < 13){
            FacesMessage msg = new FacesMessage(bundle.getString("jmbg_validation_fail"));
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
        
        String datum = value.toString().substring(0, 8);
        
        try {
            Date date = new SimpleDateFormat("ddMMyyy").parse(datum);
            if(date.after(new Date())){
             throw new ParseException(datum, 0);
            }
        } catch (ParseException ex) {
            FacesMessage msg = new FacesMessage("Datum nije dobar");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
        
       
        
        matcher = pattern.matcher(value.toString());
        if (!matcher.matches()) {
            FacesMessage msg = new FacesMessage(bundle.getString("jmbg_validation_fail"));
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
        
         int i = 11 -( ( 7*(Character.getNumericValue(value.toString().charAt(0))+Character.getNumericValue(value.toString().charAt(6)))
                +6*(Character.getNumericValue(value.toString().charAt(1))+Character.getNumericValue(value.toString().charAt(7)))
                +5*(Character.getNumericValue(value.toString().charAt(2))+Character.getNumericValue(value.toString().charAt(8)))
                +4*(Character.getNumericValue(value.toString().charAt(3))+Character.getNumericValue(value.toString().charAt(9)))
                +3*(Character.getNumericValue(value.toString().charAt(4))+Character.getNumericValue(value.toString().charAt(10)))
                +2*(Character.getNumericValue(value.toString().charAt(5))+Character.getNumericValue(value.toString().charAt(11)))
                  )%11 );
        if(i>9){
            i = 0;
        }
        
        if(Character.getNumericValue(value.toString().charAt(12)) != i){
            FacesMessage msg = new FacesMessage("Nije dobra kontrolna cifra!");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }
}
