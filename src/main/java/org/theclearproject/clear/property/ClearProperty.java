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

import org.theclearproject.clear.lookup.LookUpKey;

/**
 * @author jhumphrey
 */
public class ClearProperty extends Property implements Comparable<ClearProperty> {

  private LookUpKey lookUpKey;

  public ClearProperty(LookUpKey lookUpKey, String value) {
    super(lookUpKey.getKey(), value);
    this.lookUpKey = lookUpKey;
  }

  public LookUpKey getLookUpKey() {
    return lookUpKey;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    String lookUp = lookUpKey.getLookUp();
    if (lookUp != null && !lookUp.isEmpty()) {
      builder.append(lookUp).append(": ");
    }

    builder.append(getKey()).append("=").append(getValue());

    return builder.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ClearProperty)) return false;

    ClearProperty that = (ClearProperty) o;

    return lookUpKey.equals(that.lookUpKey);

  }

  @Override
  public int hashCode() {
    return lookUpKey.hashCode();
  }

  @Override
  public int compareTo(ClearProperty thatProperty) {
    return lookUpKey.getLookUpKey().compareTo(thatProperty.getLookUpKey().getLookUpKey());
  }
}
