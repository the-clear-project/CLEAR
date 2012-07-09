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

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.theclearproject.clear.lookup.LookUpKey;
import org.theclearproject.clear.lookup.LookUpKeyResolver;

import com.google.inject.Inject;

/**
 * @author jhumphrey
 */
public class PropertyServiceImpl implements PropertyService {

  private static final Logger logger = Logger.getLogger(PropertyServiceImpl.class);

  private LookUpKeyResolver lookUpKeyResolver;

  @Inject
  public PropertyServiceImpl(LookUpKeyResolver lookUpKeyResolver) {
    this.lookUpKeyResolver = lookUpKeyResolver;
  }

  @Override
  public ClearProperty translate(Property property) {
    LookUpKey lookUpKey = lookUpKeyResolver.resolve(property.getKey());

    ClearProperty clearProperty = new ClearProperty(lookUpKey, property.getValue());

    if (logger.isTraceEnabled()) {
      logger.trace("Successfully translated property '" + property.toString() + "' to '" + clearProperty.toString() + "'");
    }

    return clearProperty;
  }

  /**
   * Resource bundle order matters.  Bundles at the front of the list have higher priority.
   *
   * To accomplish this, this methods adds properties into a Set so that those
   * added first have priority
   *
   * @param resourceBundles a list of resource bundles
   * @return a set of properties
   */
  @Override
  public Set<ClearProperty> load(List<ResourceBundle> resourceBundles) {
    Set<ClearProperty> props = new HashSet<ClearProperty>();

    // iterates through all bundles, loads the key-value pairs,
    // and then translate the properties to clear properties
    for (ResourceBundle resourceBundle : resourceBundles) {
      Set<String> resourceBundleKeys = resourceBundle.keySet();
      for (String resourceBundleKey : resourceBundleKeys) {
        Property property = new Property(resourceBundleKey, resourceBundle.getString(resourceBundleKey));
        ClearProperty clearProperty = translate(property);
        props.add(clearProperty);
      }
    }

    if (logger.isTraceEnabled()) {
      logger.trace("Loaded the following properties: " + props.toString());
    }

    return props;
  }

  /**
   * Filters out all properties that don't map to any of the lookUps provided in the lookUps list.
   *
   * In other words, only those properties that map to a lookUp are returned in the collection.
   *
   * If the lookUp list is empty, then all provided properties will be returned
   *
   * Also prioritizes properties by lookUp.  LookUps who are at the front of the list have higher priority
   *
   * @param properties a set of {@link org.theclearproject.clear.property.ClearProperty} objects
   * @param lookUps the list of lookUps
   * @return a collection of properties that are filtered by lookUp
   */
  @Override
  public Collection<ClearProperty> filter(Collection<ClearProperty> properties, List<String> lookUps) {

    Map<String, ClearProperty> filteredProperties = new HashMap<String, ClearProperty>();

    // if no lookUps are defined, then return all properties
    if (lookUps.isEmpty()) {
      return properties;
    }

    for (String lookUp : lookUps) {
      for (ClearProperty prop : properties) {
        String thisLookUp = prop.getLookUpKey().getLookUp();
        if (thisLookUp != null && thisLookUp.equals(lookUp)) {
          String key = prop.getKey();
          if (!filteredProperties.containsKey(key)) {
            filteredProperties.put(key, prop);
          }
        }
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("Filtered the following properties against lookUps " + lookUps.toString() + ": " + filteredProperties.values());
    }

    return filteredProperties.values();
  }

  @Override
  public SortedSet<ClearProperty> sort(Collection<ClearProperty> properties) {
    return new TreeSet<ClearProperty>(properties);
  }
}
