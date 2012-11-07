package fr.mch.mdo.restaurant.web;

public abstract class AbstractControllerIntegrationTest extends AbstractControllerTest 
{
    @Override
    public void afterPropertiesSet() throws Exception {
    	isIntegrationTest = true; 
    	super.afterPropertiesSet();
    }
}