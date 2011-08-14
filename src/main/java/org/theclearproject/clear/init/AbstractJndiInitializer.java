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

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.theclearproject.clear.ClearException;

/**
 * <p>Abstract initializer that initializes CLEAR using a lookUp path defined within the JNDI tree</p>
 *
 * <p>The lookUp path must conform to the following format:</p>
 *
 * <p>X[.Y[.Z]</p>]
 *
 * <p>Examples:</p>
 *    <ul>
 *      <li>- X</li>
 *      <li>- X.Y</li>
 *      <li>- X.Y.Z</li>
 *    </ul>
 *
 * @author jhumphrey
 */

public abstract class AbstractJndiInitializer extends AbstractInitializer {

  @Override
  public void initClear() {
    String clearLookUpPath = resolveLookupPath();

    List<String> lookUps = extractLookUps(clearLookUpPath);

    // load the resource bundle
    ResourceBundle[] resourceBundles = getResourceBundles();

    ResourceBundleBuilder builder = withResourceBundle(resourceBundles);

    // add all lookUps
    for (String lookup : lookUps) {
      builder.addLookUp(lookup);
    }

    // add the default
    builder.addLookUp("default");
  }

  /**
   * Helper method to extract the lookUps from the lookup path.  Each lookUp must be followed
   * by a decimal delimiter and only 3 lookUps are supported.
   *
   * Format: X[.Y[.Z]
   *
   * Examples:
   *  - X
   *  - X.Y
   *  - X.Y.Z
   *
   *  The intent of this path is to model an enterprise system.  For instance:
   *
   *  environment.cluster.server:
   *
   * @param lookupPath the lookup path
   * @return a list of lookUps
   */
  List<String> extractLookUps(String lookupPath) {

    List<String> lookupPaths = new ArrayList<String>();

    if (lookupPath != null) {

      lookupPaths.add(lookupPath);

      String[] lookups = lookupPath.split("\\.");

      // todo: This is nasty.  Create a recursive algorithm to provide for infinitely nested paths
      if (lookups.length == 1) {
        return lookupPaths;
      } else if (lookups.length == 2) {
        lookupPaths.add(lookups[1]);
        lookupPaths.add(lookups[0]);
      } else if (lookups.length == 3) {
        lookupPaths.add(lookups[1] + "." + lookups[2]);
        lookupPaths.add(lookups[2]);
        lookupPaths.add(lookups[0] + "." + lookups[1]);
        lookupPaths.add(lookups[1]);
        lookupPaths.add(lookups[0]);
      } else {
        throw new ClearException("JNDI lookUp path invalid.  Path must conform to the following format: X[\\.Y[\\.Z]]\n\tEx:\n\t\t- X\n\t\t- X.Y\n\t\t- X.Y.Z");
      }
    }

    return lookupPaths;
  }

  /**
   * Helper method to resolve the lookup path from JNDI
   *
   * @return the lookup path.  null if not defined in the JNDI tree
   */
  String resolveLookupPath() {
    try {
      return (String) new InitialContext().lookup("java:comp/env/" + getLookUpPathName());
    } catch (NamingException e) {
      return null;
    }
  }

  /**
   * Returns the JNDI lookUp path name.  The path name is the name you give the JNDI Environment variable.
   *
   * Ex:
   *
   *  &lt;Environment name="clear.lookUp.path" value="production" type="java.lang.String" override="false"/&gt;
   *
   *   This equates for a JNDI lookup: java:comp/env/clear.lookUp.path
   *   where the lookUp path name equals 'clear.lookUp.path"
   *
   * @return the JNDI lookUp path name
   */
  protected abstract String getLookUpPathName();

  /**
   * Returns the resource bundles containing key-value pair configuration properties
   *
   * @return a list of resource bundles
   */
  protected abstract ResourceBundle[] getResourceBundles();
}
