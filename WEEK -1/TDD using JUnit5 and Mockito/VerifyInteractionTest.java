public class VerifyInteractionTest {

    private static int passed = 0;
    private static int failed = 0;

    static class VerifiableMockApi extends ExternalApi {

        private int getDataCount = 0;
        private int getUserDataCount = 0;
        private int isServiceAvailableCount = 0;
        private int sendDataCount = 0;

        private java.util.List<Integer> userIdArgs = new java.util.ArrayList<>();
        private java.util.List<String> sendDataArgs = new java.util.ArrayList<>();

        private String stubbedGetData = "mock data";
        private String stubbedGetUserData = "mock user data";
        private boolean stubbedIsAvailable = true;
        private boolean stubbedSendData = true;

        public VerifiableMockApi stubGetData(String val)          { stubbedGetData = val; return this; }
        public VerifiableMockApi stubGetUserData(String val)      { stubbedGetUserData = val; return this; }
        public VerifiableMockApi stubIsServiceAvailable(boolean v){ stubbedIsAvailable = v; return this; }
        public VerifiableMockApi stubSendData(boolean val)        { stubbedSendData = val; return this; }

        @Override
        public String getData() {
            getDataCount++;
            return stubbedGetData;
        }

        @Override
        public String getUserData(int userId) {
            getUserDataCount++;
            userIdArgs.add(userId);
            return stubbedGetUserData;
        }

        @Override
        public boolean isServiceAvailable() {
            isServiceAvailableCount++;
            return stubbedIsAvailable;
        }

        @Override
        public boolean sendData(String data) {
            sendDataCount++;
            sendDataArgs.add(data);
            return stubbedSendData;
        }

        public boolean verifyGetDataCalled() {
            return getDataCount > 0;
        }

        public boolean verifyGetDataCalledTimes(int n) {
            return getDataCount == n;
        }

        public boolean verifyGetDataNeverCalled() {
            return getDataCount == 0;
        }

        public boolean verifyGetUserDataCalledWith(int expectedId) {
            return userIdArgs.contains(expectedId);
        }

        public boolean verifyGetUserDataCalledTimes(int n) {
            return getUserDataCount == n;
        }

        public boolean verifySendDataCalledWith(String expectedData) {
            return sendDataArgs.contains(expectedData);
        }

        public boolean verifySendDataCalledTimes(int n) {
            return sendDataCount == n;
        }

        public boolean verifySendDataNeverCalled() {
            return sendDataCount == 0;
        }

        public boolean verifyIsServiceAvailableCalled() {
            return isServiceAvailableCount > 0;
        }

        public boolean verifyIsServiceAvailableCalledTimes(int n) {
            return isServiceAvailableCount == n;
        }

        public int getTotalInvocations() {
            return getDataCount + getUserDataCount + isServiceAvailableCount + sendDataCount;
        }

        public int getGetDataCount()            { return getDataCount; }
        public int getGetUserDataCount()        { return getUserDataCount; }
        public int getSendDataCount()           { return sendDataCount; }
        public int getIsServiceAvailableCount() { return isServiceAvailableCount; }
        public java.util.List<Integer> getUserIdArgs()   { return userIdArgs; }
        public java.util.List<String> getSendDataArgs()  { return sendDataArgs; }
    }

    private static void assertEquals(String message, Object expected, Object actual) {
        if (expected.equals(actual)) {
            System.out.println("    PASS: " + message);
            passed++;
        } else {
            System.out.println("    FAIL: " + message + " | Expected: " + expected + ", Got: " + actual);
            failed++;
        }
    }

    private static void assertEquals(String message, long expected, long actual) {
        if (expected == actual) {
            System.out.println("    PASS: " + message);
            passed++;
        } else {
            System.out.println("    FAIL: " + message + " | Expected: " + expected + ", Got: " + actual);
            failed++;
        }
    }

    private static void assertTrue(String message, boolean condition) {
        if (condition) {
            System.out.println("    PASS: " + message);
            passed++;
        } else {
            System.out.println("    FAIL: " + message + " | Condition was false");
            failed++;
        }
    }

    private static void assertFalse(String message, boolean condition) {
        if (!condition) {
            System.out.println("    PASS: " + message);
            passed++;
        } else {
            System.out.println("    FAIL: " + message + " | Condition was true");
            failed++;
        }
    }

    private VerifiableMockApi mockApi;
    private MyService service;

    public void setUp() {
        System.out.println("  [@Before] setUp() — creating mock and service");
        mockApi = new VerifiableMockApi();
        service = new MyService(mockApi);
    }

    public void tearDown() {
        System.out.println("  [@After]  tearDown() — cleaning up");
        mockApi = null;
        service = null;
    }

    public void testVerifyInteraction() {

        service.fetchData();

        assertTrue("verify: getData() should be called",
                   mockApi.verifyGetDataCalled());
        assertTrue("verify: getData() called exactly 1 time",
                   mockApi.verifyGetDataCalledTimes(1));
    }

    public void testVerifyMultipleInvocations() {

        mockApi.stubGetData("Data");

        service.fetchData();
        service.fetchData();
        service.fetchData();

        assertTrue("verify: getData() called exactly 3 times",
                   mockApi.verifyGetDataCalledTimes(3));
        assertEquals("getData() invocation count should be 3",
                     3, mockApi.getGetDataCount());
    }

    public void testVerifyNeverCalled() {

        service.checkHealth();

        assertTrue("verify: getData() should NEVER be called by checkHealth()",
                   mockApi.verifyGetDataNeverCalled());
        assertTrue("verify: sendData() should NEVER be called by checkHealth()",
                   mockApi.verifySendDataNeverCalled());
    }

    public void testVerifyWithSpecificArguments() {

        mockApi.stubGetUserData("User Info");

        service.fetchUserData(42);

        assertTrue("verify: getUserData() was called",
                   mockApi.verifyGetUserDataCalledTimes(1));
        assertTrue("verify: getUserData() was called with argument 42",
                   mockApi.verifyGetUserDataCalledWith(42));
        assertFalse("verify: getUserData() was NOT called with argument 99",
                    mockApi.verifyGetUserDataCalledWith(99));
    }

    public void testVerifySendDataArgument() {

        mockApi.stubSendData(true);

        service.submitData("Hello World");

        assertTrue("verify: sendData() was called exactly 1 time",
                   mockApi.verifySendDataCalledTimes(1));
        assertTrue("verify: sendData() received 'Hello World'",
                   mockApi.verifySendDataCalledWith("Hello World"));
    }

    public void testVerifyNoInteractionOnNullInput() {

        service.submitData(null);

        assertTrue("verify: sendData() should NEVER be called for null input",
                   mockApi.verifySendDataNeverCalled());
        assertEquals("Total mock invocations should be 0",
                     0, mockApi.getTotalInvocations());
    }

    public void testVerifyMultipleMethodsCalled() {

        mockApi.stubGetData("Data");
        mockApi.stubIsServiceAvailable(true);
        mockApi.stubSendData(true);

        service.fetchData();          
        service.checkHealth();        
        service.submitData("Payload");

        assertTrue("verify: getData() was called",
                   mockApi.verifyGetDataCalledTimes(1));
        assertTrue("verify: isServiceAvailable() was called",
                   mockApi.verifyIsServiceAvailableCalledTimes(1));
        assertTrue("verify: sendData() was called",
                   mockApi.verifySendDataCalledTimes(1));
        assertEquals("Total invocations should be 3",
                     3, mockApi.getTotalInvocations());
    }

    public void testVerifyArgumentsAcrossMultipleCalls() {

        mockApi.stubGetUserData("User");

        service.fetchUserData(1);
        service.fetchUserData(2);
        service.fetchUserData(3);

        assertEquals("getUserData() should be called 3 times",
                     3, mockApi.getGetUserDataCount());
        assertTrue("verify: getUserData() was called with arg 1",
                   mockApi.verifyGetUserDataCalledWith(1));
        assertTrue("verify: getUserData() was called with arg 2",
                   mockApi.verifyGetUserDataCalledWith(2));
        assertTrue("verify: getUserData() was called with arg 3",
                   mockApi.verifyGetUserDataCalledWith(3));
        assertFalse("verify: getUserData() was NOT called with arg 999",
                    mockApi.verifyGetUserDataCalledWith(999));
    }

    public void testVerifyNoMoreInteractions() {

        mockApi.stubGetData("Data");

        service.fetchData();

        assertEquals("getData() called once", 1, mockApi.getGetDataCount());
        assertEquals("getUserData() never called", 0, mockApi.getGetUserDataCount());
        assertEquals("sendData() never called", 0, mockApi.getSendDataCount());
        assertEquals("isServiceAvailable() never called", 0, mockApi.getIsServiceAvailableCount());
        assertEquals("Total invocations should be exactly 1", 1, mockApi.getTotalInvocations());
    }

    public void testVerifySendDataMultipleArguments() {

        mockApi.stubSendData(true);

        service.submitData("First");
        service.submitData("Second");
        service.submitData("Third");

        assertEquals("sendData() should be called 3 times",
                     3, mockApi.getSendDataCount());
        assertTrue("verify: sendData() received 'First'",
                   mockApi.verifySendDataCalledWith("First"));
        assertTrue("verify: sendData() received 'Second'",
                   mockApi.verifySendDataCalledWith("Second"));
        assertTrue("verify: sendData() received 'Third'",
                   mockApi.verifySendDataCalledWith("Third"));
        assertFalse("verify: sendData() did NOT receive 'Fourth'",
                    mockApi.verifySendDataCalledWith("Fourth"));
    }

    private void runTest(String testName, Runnable test) {
        System.out.println("Running: " + testName);
        try {
            setUp();
            test.run();
            tearDown();
        } catch (Exception e) {
            System.out.println("    ERROR: " + e.getMessage());
            failed++;
            try { tearDown(); } catch (Exception ignored) {}
        }
        System.out.println();
    }

    public static void main(String[] args) {
        VerifyInteractionTest tester = new VerifyInteractionTest();

        System.out.println("==============================================================");
        System.out.println("   Exercise 2: Verifying Interactions");
        System.out.println("==============================================================\n");

        System.out.println("--- Basic Verification ---");
        tester.runTest("testVerifyInteraction",            tester::testVerifyInteraction);
        tester.runTest("testVerifyMultipleInvocations",    tester::testVerifyMultipleInvocations);
        tester.runTest("testVerifyNeverCalled",            tester::testVerifyNeverCalled);

        System.out.println("--- Argument Verification ---");
        tester.runTest("testVerifyWithSpecificArguments",   tester::testVerifyWithSpecificArguments);
        tester.runTest("testVerifySendDataArgument",        tester::testVerifySendDataArgument);

        System.out.println("--- No Interaction Verification ---");
        tester.runTest("testVerifyNoInteractionOnNullInput", tester::testVerifyNoInteractionOnNullInput);
        tester.runTest("testVerifyNoMoreInteractions",       tester::testVerifyNoMoreInteractions);

        System.out.println("--- Multiple Calls & Arguments ---");
        tester.runTest("testVerifyMultipleMethodsCalled",        tester::testVerifyMultipleMethodsCalled);
        tester.runTest("testVerifyArgumentsAcrossMultipleCalls", tester::testVerifyArgumentsAcrossMultipleCalls);
        tester.runTest("testVerifySendDataMultipleArguments",    tester::testVerifySendDataMultipleArguments);

        System.out.println("==============================================================");
        System.out.println("   TEST RESULTS: " + passed + " passed, " + failed + " failed");
        System.out.println("   Total Assertions: " + (passed + failed));
        System.out.println("==============================================================");
    }
}
