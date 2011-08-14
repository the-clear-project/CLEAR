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

package org.theclearproject.clear.property;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Set;

import org.easymock.EasyMock;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.theclearproject.clear.lookup.LookUpKey;
import org.theclearproject.clear.lookup.LookUpKeyResolver;
import org.theclearproject.clear.lookup.ParenthesesLookUpKeyResolver;

/**
 * @author jhumphrey
 */
public class PropertyServiceImplTest {

  @Test
  public void testTranslate() {

    String expectedLookUp = "lookUp";
    String expectedKey = "foo";
    String expectedLookUpKeyStr = "(" + expectedLookUp + ")" + expectedKey;
    LookUpKey expectedLookUpKey = new LookUpKey(expectedLookUp, expectedKey, expectedLookUpKeyStr);
    String expectedValue = "bar";

    Property property = new Property(expectedLookUpKeyStr, expectedValue);

    LookUpKeyResolver lookUpKeyResolver = EasyMock.createStrictMock(LookUpKeyResolver.class);
    EasyMock.expect(lookUpKeyResolver.resolve(property.getKey())).andReturn(expectedLookUpKey);
    EasyMock.replay(lookUpKeyResolver);

    PropertyServiceImpl service = new PropertyServiceImpl(lookUpKeyResolver);

    ClearProperty clearProperty = service.translate(property);

    Assert.assertNotNull(clearProperty);
    Assert.assertNotNull(clearProperty.getLookUpKey());
    Assert.assertNotNull(clearProperty.getKey());
    Assert.assertNotNull(clearProperty.getValue());
    Assert.assertNotNull(clearProperty.getLookUpKey().getKey());
    Assert.assertNotNull(clearProperty.getLookUpKey().getLookUp());

    Assert.assertEquals(clearProperty.getKey(), expectedKey);
    Assert.assertEquals(clearProperty.getValue(), expectedValue);
    Assert.assertEquals(clearProperty.getLookUpKey().getLookUp(), expectedLookUp);
    Assert.assertEquals(clearProperty.getLookUpKey().getLookUpKey(), expectedLookUpKeyStr);
  }

  @Test
  public void testLoadWithOneFileAndNoLookUps() {

    ResourceBundle bundle = PropertyResourceBundle.getBundle("propertyServiceImplTest-noLookUps-1");

    List<ResourceBundle> bundles = new ArrayList<ResourceBundle>();
    bundles.add(bundle);

    LookUpKeyResolver resolver = new LookUpKeyResolver() {
      @Override
      public LookUpKey resolve(String lookUpKey) {
        return new LookUpKey(null, lookUpKey, lookUpKey);
      }
    };

    PropertyServiceImpl service = new PropertyServiceImpl(resolver);

    Set<ClearProperty> properties = service.load(bundles);

    Assert.assertNotNull(properties);
    Assert.assertEquals(properties.size(), 3);

    /*
      Properties in the test file

      foo=bar
      baz=waldo
      qix=qux
     */

    for (ClearProperty property : properties) {
      String key = property.getKey();
      String value = property.getValue();
      String lookUp = property.getLookUpKey().getLookUp();

      Assert.assertNotNull(property.getLookUpKey());
      Assert.assertNotNull(property.getValue());
      Assert.assertNotNull(property.getKey());
      Assert.assertNull(lookUp);

      if (!(key.equals("foo") || key.equals("baz") || key.equals("qix"))) {
        Assert.fail("Key must either be foo, baz, or qix");
      }
      if (!(value.equals("bar") || value.equals("waldo") || value.equals("qux"))) {
        Assert.fail("Key must either be bar, waldo, or qux");
      }
    }
  }

  @Test
  public void testLoadMultipleFilesAndNoLookUps() {

    ResourceBundle bundle1 = PropertyResourceBundle.getBundle("propertyServiceImplTest-noLookUps-1");
    ResourceBundle bundle2 = PropertyResourceBundle.getBundle("propertyServiceImplTest-noLookUps-2");

    List<ResourceBundle> bundles = new ArrayList<ResourceBundle>();
    bundles.add(bundle1);
    bundles.add(bundle2);

    LookUpKeyResolver resolver = new LookUpKeyResolver() {
      @Override
      public LookUpKey resolve(String lookUpKey) {
        return new LookUpKey(null, lookUpKey, lookUpKey);
      }
    };

    PropertyServiceImpl service = new PropertyServiceImpl(resolver);

    Set<ClearProperty> properties = service.load(bundles);

    Assert.assertNotNull(properties);
    Assert.assertEquals(properties.size(), 5);

    for (ClearProperty property : properties) {
      String key = property.getKey();
      String value = property.getValue();
      String lookUp = property.getLookUpKey().getLookUp();

      Assert.assertNotNull(property.getLookUpKey());
      Assert.assertNotNull(property.getValue());
      Assert.assertNotNull(property.getKey());
      Assert.assertNull(lookUp);

      if (!(key.equals("foo") || key.equals("baz") || key.equals("qix") || key.equals("stux") || key.equals("flix"))) {
        Assert.fail("Key must either be foo, baz, or qix");
      }
      if (!(value.equals("bar") || value.equals("waldo") || value.equals("qux") || value.equals("flux") || value.equals("glux"))) {
        Assert.fail("Key must either be bar, waldo, or qux");
      }
    }
  }

  @Test
  public void testLoadOneFileWithLookUps() {

    ResourceBundle bundle1 = PropertyResourceBundle.getBundle("propertyServiceImplTest-withLookUps-1");

    List<ResourceBundle> bundles = new ArrayList<ResourceBundle>();
    bundles.add(bundle1);

    // using the parentheses resolver for this unit test
    LookUpKeyResolver resolver = new ParenthesesLookUpKeyResolver();

    PropertyServiceImpl service = new PropertyServiceImpl(resolver);

    Set<ClearProperty> properties = service.load(bundles);

    Assert.assertNotNull(properties);
    Assert.assertEquals(properties.size(), 5);

    for (ClearProperty property : properties) {
      Assert.assertNotNull(property.getLookUpKey());

      String key = property.getKey();
      String value = property.getValue();
      String lookUp = property.getLookUpKey().getLookUp();

      Assert.assertNotNull(key);
      Assert.assertNotNull(value);

      // assert keys
      if (!(key.equals("foo") || key.equals("qix") || key.equals("baz"))) {
        Assert.fail("invalid key set");
      }

      // assert values
      if (!(value.equals("boo") || value.equals("bar") || value.equals("waldo") || value.equals("qux") || value.equals("fred"))) {
        Assert.fail("invalid value set");
      }

      // assert lookUps
      if (lookUp != null) {
        if (!(lookUp.equals("lookUp1") || lookUp.equals("lookUp2") || lookUp.equals("lookUp3"))) {
          Assert.fail("LookUp must either be lookUp1, lookUp2, lookUp3");
        }
      }

    }
  }

  @Test
  public void testFilterWithOneFileNoLookUps() {

    ResourceBundle bundle = PropertyResourceBundle.getBundle("propertyServiceImplTest-noLookUps-1");

    Set<ClearProperty> properties = new HashSet<ClearProperty>();

    Set<String> keys = bundle.keySet();
    for (String key : keys) {
      String value = bundle.getString(key);
      LookUpKey lookUpKey = new LookUpKey(null, key, key);
      ClearProperty clearProperty = new ClearProperty(lookUpKey, value);
      properties.add(clearProperty);
    }

    PropertyServiceImpl service = new PropertyServiceImpl(null);

    Collection<ClearProperty> filteredProperties = service.filter(properties, new ArrayList<String>());

    Assert.assertNotNull(filteredProperties);
    Assert.assertEquals(properties.size(), 3);

    for (ClearProperty filteredProperty : filteredProperties) {
      String key = filteredProperty.getKey();
      String value = filteredProperty.getValue();
      String lookUp = filteredProperty.getLookUpKey().getLookUp();

      Assert.assertNotNull(filteredProperty.getLookUpKey());
      Assert.assertNotNull(filteredProperty.getValue());
      Assert.assertNotNull(filteredProperty.getKey());
      Assert.assertNull(lookUp);

      if (!(key.equals("foo") || key.equals("baz") || key.equals("qix"))) {
        Assert.fail("Key must either be foo, baz, or qix");
      }
      if (!(value.equals("bar") || value.equals("waldo") || value.equals("qux"))) {
        Assert.fail("Key must either be bar, waldo, or qux");
      }
    }
  }

  @Test
  public void testFilterWithMultipleFilesAndOneLookUp() {

    String expectedLookUp = "lookUp1";

    ResourceBundle bundle1 = PropertyResourceBundle.getBundle("propertyServiceImplTest-withLookUps-1");
    ResourceBundle bundle2 = PropertyResourceBundle.getBundle("propertyServiceImplTest-withLookUps-2");

    List<ResourceBundle> bundles = new ArrayList<ResourceBundle>();
    bundles.add(bundle1);
    bundles.add(bundle2);

    PropertyServiceImpl service = new PropertyServiceImpl(new ParenthesesLookUpKeyResolver());

    Set<ClearProperty> properties = service.load(bundles);

    // test with just one lookUp 'lookUp1'
    List<String> lookUps = new ArrayList<String>();
    lookUps.add(expectedLookUp);

    Collection<ClearProperty> filteredProperties = service.filter(properties, lookUps);

    Assert.assertNotNull(filteredProperties);
    Assert.assertEquals(filteredProperties.size(), 3);

    for (ClearProperty filteredProperty : filteredProperties) {
      String key = filteredProperty.getKey();
      String value = filteredProperty.getValue();
      String lookUp = filteredProperty.getLookUpKey().getLookUp();

      Assert.assertNotNull(filteredProperty.getLookUpKey());
      Assert.assertNotNull(filteredProperty.getValue());
      Assert.assertNotNull(filteredProperty.getKey());
      Assert.assertNotNull(lookUp);
      Assert.assertEquals(lookUp, expectedLookUp);

      if (!(key.equals("foo") || key.equals("stux") || key.equals("flix"))) {
        Assert.fail("Key must either be foo, stux, or flix");
      }
      if (!(value.equals("bar") || value.equals("flux") || value.equals("glux"))) {
        Assert.fail("Key must either be bar, flux, or glux");
      }
    }
  }

  @Test
  public void testFilterWithMultipleFilesAndMultipleLookUps() {

    String expectedLookUp1 = "lookUp1";
    String expectedLookUp2 = "lookUp2";

    ResourceBundle bundle1 = PropertyResourceBundle.getBundle("propertyServiceImplTest-withLookUps-1");
    ResourceBundle bundle2 = PropertyResourceBundle.getBundle("propertyServiceImplTest-withLookUps-2");

    List<ResourceBundle> bundles = new ArrayList<ResourceBundle>();
    bundles.add(bundle1);
    bundles.add(bundle2);

    PropertyServiceImpl service = new PropertyServiceImpl(new ParenthesesLookUpKeyResolver());

    Set<ClearProperty> properties = service.load(bundles);

    // test with just one lookUp 'lookUp1'
    List<String> lookUps = new ArrayList<String>();
    lookUps.add(expectedLookUp1);
    lookUps.add(expectedLookUp2);

    Collection<ClearProperty> filteredProperties = service.filter(properties, lookUps);

    Assert.assertNotNull(filteredProperties);
    Assert.assertEquals(filteredProperties.size(), 4);

    for (ClearProperty filteredProperty : filteredProperties) {
      String key = filteredProperty.getKey();
      String value = filteredProperty.getValue();
      String lookUp = filteredProperty.getLookUpKey().getLookUp();

      Assert.assertNotNull(filteredProperty.getLookUpKey());
      Assert.assertNotNull(filteredProperty.getValue());
      Assert.assertNotNull(filteredProperty.getKey());
      Assert.assertNotNull(lookUp);

      if (!(key.equals("foo") || key.equals("stux") || key.equals("flix") || key.equals("baz"))) {
        Assert.fail("Key must either be foo, stux, flix, or baz");
      }
      if (!(value.equals("bar") || value.equals("flux") || value.equals("glux") || value.equals("waldo"))) {
        Assert.fail("Key must either be bar, flux, glux, or waldo");
      }
      if (!(lookUp.equals(expectedLookUp1) || lookUp.equals(expectedLookUp2))) {
        Assert.fail("lookUp either be lookUp1 or lookUp2");
      }

    }
  }

}
