package helpers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class BeforeEachFailedTest {

  @BeforeEach
  void exc() {
    throw new ArithmeticException("Exception!");
  }

  @Disabled
  @Test
  void test() {

  }
}
