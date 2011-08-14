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

/**
 * @author jhumphrey
 */
public class LookUpKey {

  private String lookUpKey;
  private String lookUp;
  private String key;

  public LookUpKey(String lookUp, String key, String lookUpKey) {
    this.lookUp = lookUp;
    this.key = key;
    this.lookUpKey = lookUpKey;
  }

  public String getLookUp() {
    return lookUp;
  }

  public String getKey() {
    return key;
  }

  public String getLookUpKey() {
    return lookUpKey;
  }

  @Override
  public String toString() {
    if (lookUp.isEmpty()) {
      return key;
    } else {
      return lookUp + ":" + key;
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof LookUpKey)) return false;

    LookUpKey lookUpKey1 = (LookUpKey) o;

    return lookUpKey.equals(lookUpKey1.lookUpKey);

  }

  @Override
  public int hashCode() {
    return lookUpKey.hashCode();
  }
}
