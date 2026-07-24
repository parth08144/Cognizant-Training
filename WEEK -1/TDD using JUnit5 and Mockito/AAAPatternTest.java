public class AAAPatternTest {

    private static int passed = 0;
    private static int failed = 0;

    private Calculator calculator;
    private int[] sampleArray;
    private String sampleString;

    public void setUp() {
        System.out.println("  [@Before] setUp() — initializing test fixtures");
        calculator   = new Calculator();
        sampleArray  = new int[]{10, 20, 30, 40, 50};
        sampleString = "JUnit Testing";
    }

    public void tearDown() {
        System.out.println("  [@After]  tearDown() — cleaning up test fixtures");
        calculator   = null;
        sampleArray  = null;
        sampleString = null;
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

    private static void assertEquals(String message, double expected, double actual, double delta) {
        if (Math.abs(expected - actual) <= delta) {
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

    private static void assertNotNull(String message, Object obj) {
        if (obj != null) {
            System.out.println("    PASS: " + message);
            passed++;
        } else {
            System.out.println("    FAIL: " + message + " | Object was null");
            failed++;
        }
    }

    private static void assertNull(String message, Object obj) {
        if (obj == null) {
            System.out.println("    PASS: " + message);
            passed++;
        } else {
            System.out.println("    FAIL: " + message + " | Object was not null: " + obj);
            failed++;
        }
    }

    public void testAddition_AAA() {

        int a = 15;
        int b = 25;

        int result = calculator.add(a, b);

        assertEquals("15 + 25 should equal 40", 40, result);
    }

    public void testSubtraction_AAA() {

        int a = 50;
        int b = 18;

        int result = calculator.subtract(a, b);

        assertEquals("50 - 18 should equal 32", 32, result);
    }

    public void testMultiplication_AAA() {

        int a = 7;
        int b = 8;

        int result = calculator.multiply(a, b);

        assertEquals("7 * 8 should equal 56", 56, result);
    }

    public void testDivision_AAA() {

        int a = 22;
        int b = 7;

        double result = calculator.divide(a, b);

        assertEquals("22 / 7 should be approximately 3.1428", 3.1428, result, 0.001);
    }

    public void testDivisionByZero_AAA() {

        int a = 10;
        int b = 0;
        boolean exceptionThrown = false;
        String exceptionMessage = "";

        try {
            calculator.divide(a, b);
        } catch (ArithmeticException e) {
            exceptionThrown = true;
            exceptionMessage = e.getMessage();
        }

        assertTrue("Dividing by zero should throw ArithmeticException", exceptionThrown);
        assertEquals("Exception message should be 'Cannot divide by zero'",
                     "Cannot divide by zero", exceptionMessage);
    }

    public void testFixtureInitialization_AAA() {

        int arrayLength = sampleArray.length;
        int stringLength = sampleString.length();

        assertNotNull("Calculator fixture should not be null", calculator);
        assertNotNull("Sample array fixture should not be null", sampleArray);
        assertNotNull("Sample string fixture should not be null", sampleString);
        assertEquals("Sample array should have 5 elements", 5, arrayLength);
        assertEquals("Sample string should be 'JUnit Testing'", "JUnit Testing", sampleString);
        assertEquals("Sample string length should be 13", 13, stringLength);
    }

    public void testChainedOperations_AAA() {

        int a = 10;
        int b = 5;
        int c = 3;

        int addResult  = calculator.add(a, b);        
        int mulResult  = calculator.multiply(addResult, c); 
        int finalResult = calculator.subtract(mulResult, a); 

        assertEquals("(10 + 5) * 3 - 10 should equal 35", 35, finalResult);
    }

    public void testNegativeNumbers_AAA() {

        int a = -15;
        int b = -25;

        int sum  = calculator.add(a, b);
        int diff = calculator.subtract(a, b);

        assertEquals("-15 + (-25) should equal -40", -40, sum);
        assertEquals("-15 - (-25) should equal 10",  10, diff);
    }

    public void testMultiplyByZero_AAA() {

        int a = 12345;
        int b = 0;

        int result = calculator.multiply(a, b);

        assertEquals("Any number multiplied by 0 should equal 0", 0, result);
    }

    public void testTeardownCleansUp() {

        tearDown();

        assertNull("Calculator should be null after tearDown", calculator);
        assertNull("Sample array should be null after tearDown", sampleArray);
        assertNull("Sample string should be null after tearDown", sampleString);

        setUp();
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
        AAAPatternTest tester = new AAAPatternTest();

        System.out.println("==============================================================");
        System.out.println("   Exercise 4: AAA Pattern, Test Fixtures, Setup & Teardown");
        System.out.println("==============================================================\n");

        tester.runTest("testAddition_AAA",            tester::testAddition_AAA);
        tester.runTest("testSubtraction_AAA",         tester::testSubtraction_AAA);
        tester.runTest("testMultiplication_AAA",       tester::testMultiplication_AAA);
        tester.runTest("testDivision_AAA",            tester::testDivision_AAA);
        tester.runTest("testDivisionByZero_AAA",      tester::testDivisionByZero_AAA);
        tester.runTest("testFixtureInitialization_AAA", tester::testFixtureInitialization_AAA);
        tester.runTest("testChainedOperations_AAA",   tester::testChainedOperations_AAA);
        tester.runTest("testNegativeNumbers_AAA",     tester::testNegativeNumbers_AAA);
        tester.runTest("testMultiplyByZero_AAA",      tester::testMultiplyByZero_AAA);
        tester.runTest("testTeardownCleansUp",        tester::testTeardownCleansUp);

        System.out.println("==============================================================");
        System.out.println("   TEST RESULTS: " + passed + " passed, " + failed + " failed");
        System.out.println("   Total Assertions: " + (passed + failed));
        System.out.println("==============================================================");
    }
}
