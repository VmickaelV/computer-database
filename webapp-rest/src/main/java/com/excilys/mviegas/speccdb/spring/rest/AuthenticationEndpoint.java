package com.excilys.mviegas.speccdb.spring.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Point pour l'authentification.
 *
 * @author VIEGAS Mickael
 */
@RestController
@RequestMapping("login")
public class AuthenticationEndpoint {

    @RequestMapping
    public boolean login() {
        return true;
    }
}
