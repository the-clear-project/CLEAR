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
import java.util.List;
import java.util.ResourceBundle;
import java.util.SortedSet;

import com.google.inject.ImplementedBy;

/**
 * Interface for interacting with CLEAR properties
 *
 * @author jhumphrey
 */
@ImplementedBy(PropertyServiceImpl.class)
public interface PropertyService {

  /**
   * Translates a property to a clear property
   *
   * @param property the {@link org.theclearproject.clear.property.Property} to resolve
   * @return the {@link org.theclearproject.clear.property.ClearProperty}
   */
  ClearProperty translate(Property property);

  /**
   * Loads all properties from the provided resource bundles
   *
   * @param resourceBundles a list of resource bundles
   * @return a list of {@link org.theclearproject.clear.property.ClearProperty} objects
   */
  Collection<ClearProperty> load(List<ResourceBundle> resourceBundles);

  /**
   * Filters a collection of properties against the list of lookUps
   *
   * @param properties a set of {@link org.theclearproject.clear.property.ClearProperty} objects
   * @param lookUps the list of lookUps
   * @return a set of filtered {@link org.theclearproject.clear.property.ClearProperty} objects
   */
  Collection<ClearProperty> filter(Collection<ClearProperty> properties, List<String> lookUps);

  /**
   * Sorts the collection of properties into a sorted set
   *
   * @param properties the properties to sort
   * @return a set of sorted properties
   */
  SortedSet<ClearProperty> sort(Collection<ClearProperty> properties);
}
