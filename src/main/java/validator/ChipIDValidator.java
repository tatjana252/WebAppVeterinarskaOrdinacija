/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validator;

import java.util.ResourceBundle;
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
@FacesValidator("chipIDValidator")
public class ChipIDValidator implements Validator{
    
       private final ResourceBundle bundle = ResourceBundle.getBundle("internationalization.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
 

	private static final String CHIP_ID_PATTERN = "^[0-9]{15}$";

	private Pattern pattern;
	private Matcher matcher;

	public ChipIDValidator(){
		  pattern = Pattern.compile(CHIP_ID_PATTERN);
	}

	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
            System.out.println(value.toString().length() +" " + value);
            if(value.toString().length() > 0){
		matcher = pattern.matcher(value.toString());
		if(!matcher.matches()){
			FacesMessage msg = new FacesMessage(bundle.getString("chip_id_validation_fail"));
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);

		}

	}
        }
}
