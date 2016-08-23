/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siap.rasi.service;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author rafael.esquivel
 */
public class UsuarioServiceIT {

    public UsuarioServiceIT() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of login method, of class UsuarioService.
     */
    @Test
    public void testLogin() throws Exception {
        System.out.println("login");
        String username = "admin";
        String password = "admin";
        UsuarioService instance = new UsuarioService();
        boolean expResult = true;
        boolean result = instance.login(username, password);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of sha1 method, of class UsuarioService.
     */
    @Test
    public void testSha1() throws Exception {
        System.out.println("sha1");
        String s = "admin";
        String expResult = "D033E22AE348AEB5660FC2140AEC35850C4DA997";
        String result = UsuarioService.sha1(s);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of logout method, of class UsuarioService.
     */
    @Test
    public void testLogout() throws Exception {
        System.out.println("logout");
        UsuarioService instance = new UsuarioService();
        instance.logout();
        // TODO review the generated test code and remove the default call to fail.
    }

}
