/*
 * Created on 13 juin 2004
 *
 * 
 * 
 */
package fr.mch.mdo.aop;

import fr.mch.mdo.restaurant.exception.MdoDataBeanException;



/**
 * @author Mathieu MA
 * 
 *         To change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Generation - Code and Comments
 */
public aspect AspectTransactionSpecifcLanguage
{
//	pointcut bussinessTest(): call(* fr.mch.mdo.restaurant.services.business.managers.*+.*(..) throws fr.mch.mdo.restaurant.exception.MdoException+);	
//
//	Object around() throws fr.mch.mdo.restaurant.exception.MdoBusinessException: bussinessTest() && !this(fr.mch.mdo.restaurant.services.business.managers.IMdoManager) {
//		System.out.println("START = " + thisJoinPoint.getTarget() + "==>" + thisJoinPoint.getThis());			
//		Object result = null;
//		result = proceed();
//		System.out.println("END");			
//
//		int i = 0;
//		if (i == 0) {
//			throw new MdoBusinessException("");
//		}
//		
//		return result;
//	}
	
}
