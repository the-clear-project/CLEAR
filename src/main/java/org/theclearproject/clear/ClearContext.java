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

package org.theclearproject.clear;

import java.util.List;
import java.util.ResourceBundle;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Contains context for initializing CLEAR
 *
 * @author jhumphrey
 */
public class ClearContext {

  @NotEmpty(message = "{clearContext.resourceBundles.empty}")
  List<ResourceBundle> resourceBundles;

  @NotNull(message = "{clearContext.lookUps.null}")
  List<String> lookUps;

  public List<ResourceBundle> getResourceBundles() {
    return resourceBundles;
  }

  public void setResourceBundles(List<ResourceBundle> resourceBundles) {
    this.resourceBundles = resourceBundles;
  }

  public List<String> getLookUps() {
    return lookUps;
  }

  public void setLookUps(List<String> lookUps) {
    this.lookUps = lookUps;
  }
}
