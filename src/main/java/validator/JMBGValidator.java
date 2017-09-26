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
        
        String datum = value.toString().substring(0, 7);
        
        try {
            new SimpleDateFormat("ddMM").parse(datum);
        } catch (ParseException ex) {
            FacesMessage msg = new FacesMessage(bundle.getString("jmbg_validation_fail_date"));
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
              
        
        matcher = pattern.matcher(value.toString());
        if (!matcher.matches()) {
            FacesMessage msg = new FacesMessage(bundle.getString("jmbg_validation_fail"));
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }

    }
}
