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

import org.theclearproject.clear.ClearContext;

/**
 * DSL-like interface used to initialize the CLEAR system.
 *
 * This interface queries the user for information to build a {@link org.theclearproject.clear.ClearContext}
 *
 * @author jhumphrey
 */
public abstract class AbstractInitializer implements Initializer {

  private ClearContext clearContext;

  /**
   * Initializes with one to many property files
   *
   * @param resourceBundles resource bundles
   * @return the {@link ResourceBundleBuilder}
   */
  public ResourceBundleBuilder withResourceBundle(ResourceBundle... resourceBundles) {

    clearContext = new ClearContext();

    return new ResourceBundleBuilder(clearContext, resourceBundles);
  }

  public ClearContext getClearContext() {
    return clearContext;
  }
}