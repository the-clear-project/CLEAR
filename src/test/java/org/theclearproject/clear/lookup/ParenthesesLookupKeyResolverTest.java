/*
 * Copyright (c) 2011 www.theclearproject.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.theclearproject.clear.lookup;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author jhumphrey
 */
public class ParenthesesLookupKeyResolverTest {

  @Test
  public void testWithLookUp() {
    ParenthesesLookUpKeyResolver resolver = new ParenthesesLookUpKeyResolver();

    String expectedLookUp = "foo";
    String expectedKey = "bar";
    String expectedLookUpKey = "(" + expectedLookUp + ")" + expectedKey;

    LookUpKey actualLookUpKey = resolver.resolve(expectedLookUpKey);

    Assert.assertNotNull(actualLookUpKey);
    Assert.assertEquals(actualLookUpKey.getLookUp(), expectedLookUp);
    Assert.assertEquals(actualLookUpKey.getKey(), expectedKey);
    Assert.assertEquals(actualLookUpKey.getLookUpKey(), expectedLookUpKey);
  }

  @Test
  public void testWithEmptyStringLookup() {
    ParenthesesLookUpKeyResolver resolver = new ParenthesesLookUpKeyResolver();

    String expectedKey = "bar";

    LookUpKey actualLookUpKey = resolver.resolve(expectedKey);

    Assert.assertNotNull(actualLookUpKey);
    Assert.assertNull(actualLookUpKey.getLookUp());
    Assert.assertEquals(actualLookUpKey.getKey(), expectedKey);
    Assert.assertEquals(actualLookUpKey.getLookUpKey(), expectedKey);
  }

  @Test
  public void testWithSingleLeftParentheses() {
    ParenthesesLookUpKeyResolver resolver = new ParenthesesLookUpKeyResolver();

    String expectedKey = "(bar";

    LookUpKey actualLookUpKey = resolver.resolve(expectedKey);

    Assert.assertNotNull(actualLookUpKey);
    Assert.assertNull(actualLookUpKey.getLookUp());
    Assert.assertEquals(actualLookUpKey.getKey(), expectedKey);
    Assert.assertEquals(actualLookUpKey.getLookUpKey(), expectedKey);
  }

  @Test
  public void testWithSingleRightParentheses() {
    ParenthesesLookUpKeyResolver resolver = new ParenthesesLookUpKeyResolver();

    String expectedKey = "bar)";

    LookUpKey actualLookUpKey = resolver.resolve(expectedKey);

    Assert.assertNotNull(actualLookUpKey);
    Assert.assertNull(actualLookUpKey.getLookUp());
    Assert.assertEquals(actualLookUpKey.getKey(), expectedKey);
    Assert.assertEquals(actualLookUpKey.getLookUpKey(), expectedKey);
  }

  @Test
  public void testWithJustALookUp() {
    ParenthesesLookUpKeyResolver resolver = new ParenthesesLookUpKeyResolver();

    String expectedKey = "(bar)";

    LookUpKey actualLookUpKey = resolver.resolve(expectedKey);

    Assert.assertNotNull(actualLookUpKey);
    Assert.assertNull(actualLookUpKey.getLookUp());
    Assert.assertEquals(actualLookUpKey.getKey(), expectedKey);
    Assert.assertEquals(actualLookUpKey.getLookUpKey(), expectedKey);
  }
}
