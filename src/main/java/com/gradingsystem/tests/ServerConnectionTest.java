package com.gradingsystem.tests;

import com.gradingsystem.utils.ServerConnection;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ServerConnectionTest {
    private ServerConnection serverConnection;

    @Before
    public void setUp() {
        serverConnection = new ServerConnection("localhost", 1025);
        serverConnection.connect();
    }

    @After
    public void tearDown() {
        serverConnection.disconnect();
    }

    @Test
    public void testSendRequest_InvalidRequest_ReturnsNull() {
        String request = "INVALID REQUEST";
        String response = serverConnection.sendRequest(request);
        Assert.assertNull(response);
    }
}
