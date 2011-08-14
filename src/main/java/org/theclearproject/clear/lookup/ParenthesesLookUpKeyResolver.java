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

import org.apache.log4j.Logger;

/**
 * Resolves lookUps in parentheses.
 *
 * Example:
 *
 * (foo)waldo
 *
 * The lookUp in the example above would be 'foo'
 *
 * @author jhumphrey
 */
public class ParenthesesLookUpKeyResolver implements LookUpKeyResolver {

  private static final Logger logger = Logger.getLogger(ParenthesesLookUpKeyResolver.class);

  @Override
  public LookUpKey resolve(String lookUpKey) {
    if (lookUpKey == null) {
      return null;
    }

    String[] tokens = lookUpKey.split("\\(");

    if (tokens.length == 1) {
      return new LookUpKey(null, lookUpKey, lookUpKey);
    }

    String token = tokens[1];

    tokens = token.split("\\)");

    if (tokens.length == 1) {
      return new LookUpKey(null, lookUpKey, lookUpKey);
    }

    String lookUp = tokens[0];
    String key = tokens[1];

    if (logger.isDebugEnabled()) {
      logger.debug("Resolved lookUpKey '" + lookUpKey + "' to lookUp '" + lookUp + "' and key '" + key + "'");
    }

    return new LookUpKey(lookUp, key, lookUpKey);
  }
}
