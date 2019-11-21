package pebblegame;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Run all the test classes.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses(value={PebbleGameTest.class, BagTest.class})
public class PebbleTestSuite {}
