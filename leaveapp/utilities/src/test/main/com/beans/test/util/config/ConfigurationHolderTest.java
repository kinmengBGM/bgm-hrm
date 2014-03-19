package com.beans.test.util.config;

import org.apache.commons.configuration.ConfigurationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.beans.util.config.ConfigurationHolder;

public class ConfigurationHolderTest {
	
	ConfigurationHolder configurationHolder = null;
	@Before
	public void init() throws ConfigurationException{
		configurationHolder = new ConfigurationHolder();
	}
	
	@Test
	public void testGetString() {
		Assert.assertNotNull("Testing for mail.smtp.host", configurationHolder.getString("mail.smtp.host"));
	}
	
	@Test
	public void testGetInt() {
		Assert.assertNotNull("Testing for mail.smtp.port", configurationHolder.getInt("mail.smtp.socketFactory.port"));
	}
	
	@Test
	public void testGetBoolean() {
		Assert.assertNotNull("Testing for mail.smtp.ssl", configurationHolder.getBoolean("mail.smtp.auth"));
	}
}