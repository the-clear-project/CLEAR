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

package org.theclearproject.clear.guiceintegration;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.theclearproject.clear.Clear;
import org.theclearproject.clear.ClearException;
import org.theclearproject.clear.property.ClearProperty;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * This is an integration level test using GUICE dependency injection
 *
 * @author jhumphrey
 */
public class ClearIntegrationTest {

  private Injector injector = Guice.createInjector(new ClearIntegrationGuiceModule());

  private Clear clear;

  @Inject
  public void setClear(Clear clear) {
    this.clear = clear;
  }

  @BeforeClass
  public void initGuice() {
    injector.injectMembers(this);
  }

  @Test
  public void testMapSize() {
    Map<String, ClearProperty> properties = clear.getProperties();

    /*
     Verify Properties.  Initializer only loaded lookUp1 and lookUp2
     so only those properties should be within the property map

     Defined in clearIntegrationTest-1.properties:
       (default)stux=stix
       (default)flix=flux
       (default)glix=glux
       (lookUp1)foo=bark
       (lookUp1)blas=1
       (lookUp1)gorp=true
       (lookUp2)baz=bizz
       (lookUp2)frank=2.34
       (lookUp2)fred=1234123512342323341
       (lookUp3)waldo=qixx

     Defined in clearIntegrationTest-2.properties
       (default)foo=bar
       (default)baz=biz
       (default)waldo=qix
     */

    Assert.assertEquals(properties.size(), 6);
  }

  /**
   * Test getString calls
   */
  @Test
  public void testGetString() {
    String foo = clear.getString("foo");
    String baz = clear.getString("baz");
    String waldo = clear.getString("waldo", "hux"); // this one is not defined, but it should return the default
    Assert.assertEquals(foo, "bark");
    Assert.assertEquals(baz, "bizz");
    Assert.assertEquals(waldo, "hux");
  }

  /**
   * Test getInt
   */
  @Test
  public void testGetInt() {
    int blas = clear.getInt("blas");
    int intDefault = clear.getInt("intDefault", 24);
    Assert.assertEquals(blas, 1);
    Assert.assertEquals(intDefault, 24);
    try {
      clear.getInt("foo");
      Assert.fail("Should have thrown an exception, key 'foo' maps to a string that can't be converted to an int");
    } catch (ClearException e) {
      // no-op
    }
    try {
      clear.getInt("foo", 0);
      Assert.fail("Should have thrown an exception, key 'foo' maps to a string that can't be converted to an int");
    } catch (ClearException e) {
      // no-op
    }
    try {
      clear.getInt("nonExistentInt");
      Assert.fail("Should have thrown an exception because key is non-existent and default value isn't specified");
    } catch (ClearException e) {
      // no op
    }
  }

  /**
   * Test getLong
   */
  @Test
  public void testGetLong() {
    long fred = clear.getLong("fred");
    long longDefault = clear.getLong("longDefault", 4321123512342323341L);
    Assert.assertEquals(fred, 1234123512342323341L);
    Assert.assertEquals(longDefault, 4321123512342323341L);
    try {
      clear.getLong("foo");
      Assert.fail("Should have thrown an exception, key 'foo' maps to a string that can't be converted to a long");
    } catch (ClearException e) {
      // no op
    }
    try {
      clear.getLong("foo", 0L);
      Assert.fail("Should have thrown an exception, key 'foo' maps to a string that can't be converted to a long");
    } catch (ClearException e) {
      // no op
    }
    try {
      clear.getLong("nonExistentLong");
      Assert.fail("Should have thrown an exception because key is non-existent and default value isn't specified");
    } catch (ClearException e) {
      // no op
    }
  }

  @Test
  public void testGetDouble() {
    double frank = clear.getDouble("frank");
    double doubleDefault = clear.getDouble("doubleDefault", 3.14);
    Assert.assertEquals(frank, 2.34);
    Assert.assertEquals(doubleDefault, 3.14);
    try {
      clear.getDouble("foo");
      Assert.fail("Should have thrown an exception, key 'foo' maps to a string that can't be converted to a double");
    } catch (ClearException e) {
      // no op
    }
    try {
      clear.getDouble("foo", 3.14);
      Assert.fail("Should have thrown an exception, key 'foo' maps to a string that can't be converted to a double");
    } catch (ClearException e) {
      // no op
    }
    try {
      clear.getDouble("nonExistentDouble");
      Assert.fail("Should have thrown an exception because key is non-existent and default value isn't specified");
    } catch (ClearException e) {
      // no op
    }
  }

  @Test
  public void testGetBoolean() {
    boolean gorp = clear.getBoolean("gorp");
    boolean booleanDefault = clear.getBoolean("doubleDefault", true);
    Assert.assertEquals(gorp, true);
    Assert.assertEquals(booleanDefault, true);
    try {
      clear.getBoolean("nonExistentBoolean");
      Assert.fail("Should have thrown an exception because key is non-existent and default value isn't specified");
    } catch (ClearException e) {
      // no op
    }
  }


  @Test
  public void testGetFloat() {
    float frank = clear.getFloat("frank");
    float floatDefault = clear.getFloat("floatDefault", 3.14F);
    Assert.assertEquals(frank, 2.34F);
    Assert.assertEquals(floatDefault, 3.14F);
    try {
      clear.getFloat("foo");
      Assert.fail("Should have thrown an exception, key 'foo' maps to a string that can't be converted to a float");
    } catch (ClearException e) {
      // no op
    }
    try {
      clear.getFloat("foo", 3.14F);
      Assert.fail("Should have thrown an exception, key 'foo' maps to a string that can't be converted to a float");
    } catch (ClearException e) {
      // no op
    }
    try {
      clear.getFloat("nonExistentFloat");
      Assert.fail("Should have thrown an exception because key is non-existent and default value isn't specified");
    } catch (ClearException e) {
      // no op
    }
  }

  @Test
  public void testGetShort() {
    short expectedValue1 = 1;
    short expectedValue24 = 24;
    short blas = clear.getShort("blas");
    short shortDefault = clear.getShort("shortDefault", expectedValue24);
    Assert.assertEquals(blas, expectedValue1);
    Assert.assertEquals(shortDefault, expectedValue24);
    try {
      clear.getShort("foo");
      Assert.fail("Should have thrown an exception, key 'foo' maps to a string that can't be converted to an short");
    } catch (ClearException e) {
      // no-op
    }
    try {
      clear.getShort("foo", new Short("34"));
      Assert.fail("Should have thrown an exception, key 'foo' maps to a string that can't be converted to an short");
    } catch (ClearException e) {
      // no-op
    }
    try {
      clear.getShort("nonExistentShort");
      Assert.fail("Should have thrown an exception because key is non-existent and default value isn't specified");
    } catch (ClearException e) {
      // no op
    }
  }
}
