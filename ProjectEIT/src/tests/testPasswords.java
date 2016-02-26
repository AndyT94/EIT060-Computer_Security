package tests;

import password.*;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class testPasswords {
	private String password;
	private byte[] salt;
	private byte[] Hpwssd;

	// @Before
	// public void setUp(){
	//
	// }
//	@Test
//	public void testPasswordHashed(){
//		StringBuilder sb = new StringBuilder();
//		StringBuilder sb1 = new StringBuilder();
//		password = Passwords.generateRandomPassword(8);
//		System.out.println("Input password: " + password );
//		salt = Passwords.getNextSalt();
//		for(int i = 0; i < salt.length; i++){
//			sb.append(salt[i]);
//		
//		}
//		
//		System.out.println("Salt:" + sb.toString());
//		Hpwssd = Passwords.hash(password, salt);
//		for(int i = 0; i < Hpwssd.length; i++){
//			sb1.append(Hpwssd[i]);
//		
//		}
//		System.out.println("Hashed password:" + sb1.toString());
//		boolean result = Passwords.isExpectedPassword(password, salt, Hpwssd);
//		System.out.println("is i true ?" + " " + result);
//		assertTrue(result);
//		
//	}
	
	@Test
	public void testPassword(){
		StringBuilder sb = new StringBuilder();
		StringBuilder sb1 = new StringBuilder();
		password = "password";
		System.out.println("Input password: " + password );
		salt = Passwords.getNextSalt();
		for(int i = 0; i < salt.length; i++){
			sb.append(salt[i]);
		
		}
		System.out.println("Salt:" + sb.toString());
		Hpwssd = Passwords.hash(password, salt);
		for(int i = 0; i < Hpwssd.length; i++){
			sb1.append(Hpwssd[i]);
		
		}
		System.out.println("Hashed password:" + sb1.toString());
		boolean result = Passwords.isExpectedPassword(password, salt, Hpwssd);
		System.out.println("is i true ?" + " " + result);
		assertTrue(result);
		
		
	}

}
