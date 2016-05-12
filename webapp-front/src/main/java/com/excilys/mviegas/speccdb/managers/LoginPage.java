package com.excilys.mviegas.speccdb.managers;

import com.excilys.mviegas.speccdb.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Bean lié à la gestion du login
 *
 * @author Mickael
 */
@Component
public class LoginPage {

	//=============================================================
	// Constantes
	//=============================================================
	public static final Logger LOGGER = LoggerFactory.getLogger(LoginPage.class);

	//===========================================================
	// Attribute - private
	//===========================================================
	private String mUsername;

	private String mPassword;

	private String mAction;

	@Autowired
	private UserService mUserService;

	//===========================================================
	// Constructeurs
	//===========================================================
	public LoginPage() {
		init();
	}

	//===========================================================
	// Getters & Setters
	//===========================================================
	public String getUsername() {
		return mUsername;
	}

	public void setUsername(String pUsername) {
		mUsername = pUsername;
	}

	public String getPassword() {
		return mPassword;
	}

	public void setPassword(String pPassword) {
		mPassword = pPassword;
	}

	public String getAction() {
		return mAction;
	}

	public void setAction(String pAction) {
		mAction = pAction;
	}

	public UserService getUserService() {
		return mUserService;
	}

	public void setUserService(UserService pUserService) {
		mUserService = pUserService;
	}

	//===========================================================
	// Functions
	//===========================================================
	public boolean hasValidUsername() {
		// TODO refvoir ce début de condition
		return (mAction == null || mAction.equals("")) || (mUsername != null && !mUsername.isEmpty());
	}

	public boolean hasValidPassword() {
		// TODO refvoir ce début de condition
		return (mAction == null || mAction.equals("")) || (mPassword != null && !mPassword.isEmpty());
	}

	public boolean isValidForm() {
		return hasValidUsername() && hasValidPassword();
	}

	// ============================================================
	//	Méthodes - Callback
	// ============================================================
	public void init() {

	}

	public void reset() {
		mAction = null;
		mPassword = null;
		mUsername = null;
	}
}
