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

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.ResourceBundle;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Contains context for initializing CLEAR
 *
 * @author jhumphrey
 */
public class ClearContext {

  @NotEmpty(message = "{clearContext.resourceBundles.empty}")
  public List<ResourceBundle> resourceBundles;

  @NotNull(message = "{clearContext.lookUps.null}")
  public List<String> lookUps;
}
