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

package org.theclearproject.clear.init;

import java.util.ResourceBundle;

import org.testng.Assert;
import org.testng.annotations.Test;

import org.theclearproject.clear.ClearContext;

/**
 * @author jhumphrey
 */
public class AbstractInitializerTest {

  @Test
  public void testClearInitWithoutLookUps() {
    AbstractInitializer initializer = new AbstractInitializer() {
      @Override
      public void initClear() {
        ResourceBundle bundle = ResourceBundle.getBundle("clearInitializerTest-no-lookUps");
        withResourceBundle(bundle).withoutLookUps();
      }
    };
    initializer.initClear();
    ClearContext context = initializer.getClearContext();

    Assert.assertNotNull(context);
    Assert.assertNotNull(context.resourceBundles);
    Assert.assertEquals(context.resourceBundles.size(), 1);
    Assert.assertNotNull(context.lookUps);
    Assert.assertEquals(context.lookUps.size(), 0);
  }

  @Test
  public void testClearInitWithLookUps() {
    AbstractInitializer initializer = new AbstractInitializer() {
      @Override
      public void initClear() {
        ResourceBundle bundle = ResourceBundle.getBundle("clearInitializerTest-with-lookUps");
        withResourceBundle(bundle).addLookUp("lookUp1").addLookUp("lookUp2");
      }
    };
    initializer.initClear();
    ClearContext context = initializer.getClearContext();

    Assert.assertNotNull(context);
    Assert.assertNotNull(context.resourceBundles);
    Assert.assertEquals(context.resourceBundles.size(), 1);
    Assert.assertNotNull(context.lookUps);
    Assert.assertEquals(context.lookUps.size(), 2);
    Assert.assertTrue(context.lookUps.contains("lookUp1"));
    Assert.assertTrue(context.lookUps.contains("lookUp2"));
  }

  @Test
  public void testClearInitWithMultipleBundles() {
    AbstractInitializer initializer = new AbstractInitializer() {
      @Override
      public void initClear() {
        ResourceBundle bundle1 = ResourceBundle.getBundle("clearInitializerTest-no-lookUps");
        ResourceBundle bundle2 = ResourceBundle.getBundle("clearInitializerTest-with-lookUps");
        withResourceBundle(bundle1, bundle2).addLookUp("lookUp1").addLookUp("lookUp2");
      }
    };
    initializer.initClear();
    ClearContext context = initializer.getClearContext();

    Assert.assertNotNull(context);
    Assert.assertNotNull(context.resourceBundles);
    Assert.assertEquals(context.resourceBundles.size(), 2);
  }
}
