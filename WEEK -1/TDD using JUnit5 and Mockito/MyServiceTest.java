public class MyServiceTest {

    private static int passed = 0;
    private static int failed = 0;

    static class MockExternalApi extends ExternalApi {

        private String stubbedGetData = null;
        private String stubbedGetUserData = null;
        private Boolean stubbedIsServiceAvailable = null;
        private Boolean stubbedSendData = null;

        private int getDataCallCount = 0;
        private int getUserDataCallCount = 0;
        private int isServiceAvailableCallCount = 0;
        private int sendDataCallCount = 0;
        private int lastUserIdArg = -1;
        private String lastSendDataArg = null;

        public MockExternalApi stubGetData(String value) {
            this.stubbedGetData = value;
            return this;
        }

        public MockExternalApi stubGetUserData(String value) {
            this.stubbedGetUserData = value;
            return this;
        }

        public MockExternalApi stubIsServiceAvailable(boolean value) {
            this.stubbedIsServiceAvailable = value;
            return this;
        }

        public MockExternalApi stubSendData(boolean value) {
            this.stubbedSendData = value;
            return this;
        }

        @Override
        public String getData() {
            getDataCallCount++;
            return stubbedGetData != null ? stubbedGetData : "default mock data";
        }

        @Override
        public String getUserData(int userId) {
            getUserDataCallCount++;
            lastUserIdArg = userId;
            return stubbedGetUserData != null ? stubbedGetUserData : "default mock user data";
        }

        @Override
        public boolean isServiceAvailable() {
            isServiceAvailableCallCount++;
            return stubbedIsServiceAvailable != null ? stubbedIsServiceAvailable : false;
        }

        @Override
        public boolean sendData(String data) {
            sendDataCallCount++;
            lastSendDataArg = data;
            return stubbedSendData != null ? stubbedSendData : false;
        }

        public int getGetDataCallCount()             { return getDataCallCount; }
        public int getGetUserDataCallCount()         { return getUserDataCallCount; }
        public int getIsServiceAvailableCallCount()  { return isServiceAvailableCallCount; }
        public int getSendDataCallCount()            { return sendDataCallCount; }
        public int getLastUserIdArg()                { return lastUserIdArg; }
        public String getLastSendDataArg()           { return lastSendDataArg; }
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

    private MockExternalApi mockApi;
    private MyService service;

    public void setUp() {
        System.out.println("  [@Before] setUp() — creating mock and service");
        mockApi = new MockExternalApi();   
        service = new MyService(mockApi);  
    }

    public void tearDown() {
        System.out.println("  [@After]  tearDown() — cleaning up");
        mockApi = null;
        service = null;
    }

    public void testExternalApi() {

        mockApi.stubGetData("Mock Data");

        String result = service.fetchData();

        assertEquals("fetchData() should return stubbed 'Mock Data'", "Mock Data", result);
    }

    public void testGetUserDataWithStub() {

        mockApi.stubGetUserData("John Doe");

        String result = service.fetchUserData(42);

        assertEquals("fetchUserData(42) should return 'Processed: John Doe'",
                     "Processed: John Doe", result);
    }

    public void testCheckHealth_ServiceUp() {

        mockApi.stubIsServiceAvailable(true);

        String health = service.checkHealth();

        assertEquals("checkHealth() should return 'Service is UP'",
                     "Service is UP", health);
    }

    public void testCheckHealth_ServiceDown() {

        mockApi.stubIsServiceAvailable(false);

        String health = service.checkHealth();

        assertEquals("checkHealth() should return 'Service is DOWN'",
                     "Service is DOWN", health);
    }

    public void testSubmitData_Success() {

        mockApi.stubSendData(true);

        boolean result = service.submitData("Hello API");

        assertTrue("submitData('Hello API') should return true", result);
    }

    public void testSubmitData_Failure() {

        mockApi.stubSendData(false);

        boolean result = service.submitData("Hello API");

        assertFalse("submitData('Hello API') should return false when API fails", result);
    }

    public void testSubmitData_NullInput() {

        boolean result = service.submitData(null);

        assertFalse("submitData(null) should return false", result);
        assertEquals("sendData() should NOT be called for null input",
                     0, mockApi.getSendDataCallCount());
    }

    public void testSubmitData_EmptyInput() {

        boolean result = service.submitData("");

        assertFalse("submitData('') should return false", result);
        assertEquals("sendData() should NOT be called for empty input",
                     0, mockApi.getSendDataCallCount());
    }

    public void testVerify_GetDataCalledOnce() {

        mockApi.stubGetData("Verified Data");

        service.fetchData();

        assertEquals("getData() should be called exactly 1 time",
                     1, mockApi.getGetDataCallCount());
    }

    public void testVerify_GetUserDataCalledWithCorrectArg() {

        mockApi.stubGetUserData("User Info");

        service.fetchUserData(99);

        assertEquals("getUserData() should be called exactly 1 time",
                     1, mockApi.getGetUserDataCallCount());
        assertEquals("getUserData() should be called with userId = 99",
                     99, mockApi.getLastUserIdArg());
    }

    public void testVerify_SendDataCalledWithCorrectArg() {

        mockApi.stubSendData(true);

        service.submitData("Important Payload");

        assertEquals("sendData() should be called exactly 1 time",
                     1, mockApi.getSendDataCallCount());
        assertEquals("sendData() should receive 'Important Payload'",
                     "Important Payload", mockApi.getLastSendDataArg());
    }

    public void testDefaultMockBehavior() {

        String data = service.fetchData();
        String health = service.checkHealth();

        assertEquals("Unstubbed getData() should return default 'default mock data'",
                     "default mock data", data);
        assertEquals("Unstubbed isServiceAvailable() returns false → 'Service is DOWN'",
                     "Service is DOWN", health);
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
        MyServiceTest tester = new MyServiceTest();

        System.out.println("==============================================================");
        System.out.println("   Exercise 1: Mocking and Stubbing with Mockito");
        System.out.println("==============================================================\n");

        System.out.println("--- Basic Mocking & Stubbing ---");
        tester.runTest("testExternalApi",               tester::testExternalApi);
        tester.runTest("testGetUserDataWithStub",       tester::testGetUserDataWithStub);

        System.out.println("--- Stubbing Boolean Returns ---");
        tester.runTest("testCheckHealth_ServiceUp",     tester::testCheckHealth_ServiceUp);
        tester.runTest("testCheckHealth_ServiceDown",   tester::testCheckHealth_ServiceDown);
        tester.runTest("testSubmitData_Success",        tester::testSubmitData_Success);
        tester.runTest("testSubmitData_Failure",        tester::testSubmitData_Failure);

        System.out.println("--- Input Validation (No API Call) ---");
        tester.runTest("testSubmitData_NullInput",      tester::testSubmitData_NullInput);
        tester.runTest("testSubmitData_EmptyInput",     tester::testSubmitData_EmptyInput);

        System.out.println("--- Verify Mock Interactions ---");
        tester.runTest("testVerify_GetDataCalledOnce",           tester::testVerify_GetDataCalledOnce);
        tester.runTest("testVerify_GetUserDataCalledWithCorrectArg", tester::testVerify_GetUserDataCalledWithCorrectArg);
        tester.runTest("testVerify_SendDataCalledWithCorrectArg",    tester::testVerify_SendDataCalledWithCorrectArg);

        System.out.println("--- Default Mock Behavior ---");
        tester.runTest("testDefaultMockBehavior",       tester::testDefaultMockBehavior);

        System.out.println("==============================================================");
        System.out.println("   TEST RESULTS: " + passed + " passed, " + failed + " failed");
        System.out.println("   Total Assertions: " + (passed + failed));
        System.out.println("==============================================================");
    }
}
