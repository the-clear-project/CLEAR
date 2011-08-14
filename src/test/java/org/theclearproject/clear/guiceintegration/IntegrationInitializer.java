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

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.theclearproject.clear.init.AbstractInitializer;

/**
 * @author jhumphrey
 */

public class IntegrationInitializer extends AbstractInitializer {
  @Override
  public void initClear() {
    ResourceBundle bundle1 = PropertyResourceBundle.getBundle("clearIntegrationTest-1");
    ResourceBundle bundle2 = PropertyResourceBundle.getBundle("clearIntegrationTest-2");

    withResourceBundle(bundle1, bundle2).addLookUp("lookUp1").addLookUp("lookUp2");
  }
}
