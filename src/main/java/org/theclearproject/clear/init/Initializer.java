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

import org.theclearproject.clear.ClearContext;

/**
 * Interface used for initializes CLEAR context
 *
 * @author jhumphrey
 */
public interface Initializer {

  /**
   * Implemented to initialize CLEAR
   */
  void initClear();

  /**
   * Returns the {@link org.theclearproject.clear.ClearContext} created by this initialization
   *
   * @return the {@link org.theclearproject.clear.ClearContext}
   */
  ClearContext getClearContext();
}
