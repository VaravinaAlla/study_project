package helpers;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class BeforeEachSkippedTest {

  @BeforeEach
  void skip() {
    Assumptions.assumeTrue(false, "Skipping ...");
  }

  @Disabled
  @Test
  void test() {

  }
}
