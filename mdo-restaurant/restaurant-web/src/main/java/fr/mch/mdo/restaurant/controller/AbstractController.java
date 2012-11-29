package fr.mch.mdo.restaurant.controller;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.core.JsonParseException;

/**
 * This class is the base class for injecting common required object and spring handling exception.
 * @author m.ma
 *
 */
public abstract class AbstractController {

	/**
	 * This is the logger of this class.
	 */
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * This field is used for i18n message. 
	 */
	@Autowired
	protected MessageSource messageSource;

	/**
	 * This method is called when NoResultException exception is thrown.
	 * @param e the NoResultException exception.
	 * @return the view page. 
	 */
	@ExceptionHandler(NoResultException.class)
	public String handleNotFound(NoResultException e) {
		logger.error(e.getMessage(), e);
		return "error";
	}

	/**
	 * This method is called when IllegalArgumentException, JsonParseException, ServletException or TypeMismatchException exception is thrown.
	 * @param e the IllegalArgumentException, JsonParseException, ServletException or TypeMismatchException exception.
	 * @return the view page. 
	 */
	@ExceptionHandler({ IllegalArgumentException.class, JsonParseException.class, ServletException.class,
			TypeMismatchException.class })
	public String handleBadRequest(Exception e) {
		logger.error(e.getMessage(), e);
		return "error";
	}

	/**
	 * This method is called when IllegalStateException or PersistenceException exception is thrown.
	 * @param e the IllegalStateException or PersistenceException exception.
	 * @return the view page. 
	 */
	@ExceptionHandler({ IllegalStateException.class, PersistenceException.class })
	public String handleConflict(Exception e) {
		logger.error(e.getMessage(), e);
		return "error";
	}

	/**
	 * This method is called when HttpClientErrorException exception is thrown.
	 * @param e the HttpClientErrorException exception.
	 * @return the view page. 
	 */
	@ExceptionHandler(HttpClientErrorException.class)
	public String handleHttpClientError(HttpClientErrorException e) {
		logger.error(e.getStatusText(), e);
		return "error";
	}

	/**
	 * This method is called when Exception exception is thrown.
	 * @param e the Exception exception.
	 * @return the view page. 
	 */
	@ExceptionHandler(Exception.class)
	public String handleException(Exception e) {
		logger.error(e.getMessage(), e);
		return "error";
	}
}
