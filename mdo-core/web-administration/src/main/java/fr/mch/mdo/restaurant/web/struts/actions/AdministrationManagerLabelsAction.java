package fr.mch.mdo.restaurant.web.struts.actions;

import java.util.HashMap;

import org.apache.commons.lang.xwork.StringUtils;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.Constants;
import fr.mch.mdo.restaurant.beans.IBeanLabelable;
import fr.mch.mdo.restaurant.ui.forms.IMdoForm;
import fr.mch.mdo.restaurant.ui.forms.MdoLabelsForm;

public class AdministrationManagerLabelsAction extends AdministrationManagerAction
{
	/**
     * Default Serial Version UID.
     */
	private static final long serialVersionUID = 1L;

	public AdministrationManagerLabelsAction(ILogger logger, IMdoForm form) {
		super(logger, form);
	}

	@Override
	public String form() throws Exception {
		String result = super.form();
		
		// Set default values for UpdatingLanguage and UpdatingLabel 
		MdoLabelsForm form = (MdoLabelsForm) super.getForm();
		if (form.getUpdatingLanguage() == null && StringUtils.isEmpty(form.getUpdatingLabel())) {
			IBeanLabelable dtoBean = (IBeanLabelable) form.getDtoBean();
			if (dtoBean.getLabels() != null && !dtoBean.getLabels().isEmpty()) {
				Long key = dtoBean.getLabels().keySet().iterator().next();
				form.setUpdatingLanguage(key);
				form.setUpdatingLabel(dtoBean.getLabels().get(key));
			}
		}
		
		return result;
	}
	
	public String labels() throws Exception {
		MdoLabelsForm form = (MdoLabelsForm) super.getForm();
		
		if (StringUtils.isEmpty(form.getUpdatingLabel())) {
			// Could not add empty string for label
			super.addActionMessage(super.getText("language.add.label.empty"));
		} else {
			IBeanLabelable dtoBean = (IBeanLabelable) form.getDtoBean();
			if (dtoBean.getLabels() == null) {
				dtoBean.setLabels(new HashMap<Long, String>());
			}
			dtoBean.getLabels().put(form.getUpdatingLanguage(), form.getUpdatingLabel());
	
			this.save();
		}
		super.form();
		return Constants.ACTION_RESULT_AFTER_CUD_LABELS;
	}
	
	public String removeLabel() throws Exception {
		String labelKeyToRemove = super.getRequest().getParameter("method:removeLabel");
		MdoLabelsForm form = (MdoLabelsForm) super.getForm();
		IBeanLabelable dtoBean = (IBeanLabelable) form.getDtoBean();
		if (dtoBean.getLabels() == null) {
			dtoBean.setLabels(new HashMap<Long, String>());
		}
		dtoBean.getLabels().remove(new Long(labelKeyToRemove));
		
		this.save();
		super.form();
		return Constants.ACTION_RESULT_AFTER_CUD_LABELS;
	}

}
