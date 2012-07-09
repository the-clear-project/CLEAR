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

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.theclearproject.clear.init.Initializer;
import org.theclearproject.clear.property.ClearProperty;
import org.theclearproject.clear.property.PropertyService;

import com.google.inject.Inject;

/**
 * Core class of the CLEAR system.  Implements {@link org.theclearproject.clear.Configuration}
 *
 * @author jhumphrey
 */
public class Clear implements Configuration {

  private static final Logger logger = Logger.getLogger(Clear.class);

  private Initializer initializer;
  private Validator validator;
  private PropertyService propertyService;

  private Map<String, ClearProperty> properties;

  @Inject
  public Clear(Initializer initializer, Validator validator, PropertyService propertyService) {
    this.initializer = initializer;
    this.validator = validator;
    this.propertyService = propertyService;
    init();
  }

  private void init() {
    initializer.initClear();

    ClearContext context = initializer.getClearContext();

    validate(context);

    // stores all clear properties in all bundles
    Collection<ClearProperty> allProps = propertyService.load(context.resourceBundles);

    // stores all filtered properties.  A filtered property is one that
    // contains a lookup key matching lookUps added during the initialization step
    Collection<ClearProperty> filteredProps = propertyService.filter(allProps, context.lookUps);

    properties = new LinkedHashMap<String, ClearProperty>();
    for (ClearProperty filteredProp : filteredProps) {
      properties.put(filteredProp.getKey(), filteredProp);
    }

    printLookUps(context.lookUps);
    printProperties();
  }

  /**
   * Utility method for validating the {@link org.theclearproject.clear.ClearContext} object using
   * javax.validation
   *
   * @param context the {@link org.theclearproject.clear.ClearContext}
   */
  void validate(ClearContext context) {
    Set<ConstraintViolation<ClearContext>> violations = validator.validate(context);
    if (!violations.isEmpty()) {
      StringBuilder validationMessages = new StringBuilder("\n");
      for (ConstraintViolation<ClearContext> violation : violations) {
        validationMessages.append(violation.getMessage()).append("\n");
      }
      throw new ClearException(validationMessages.toString());
    }
  }

  /**
   * Prints properties to logs
   */
  private void printProperties() {
    Set<ClearProperty> sortedProperties = propertyService.sort(properties.values());
    if (logger.isInfoEnabled()) {
      logger.info("CLEAR properties loaded:");
      for (ClearProperty property : sortedProperties) {
        logger.info(property);
      }
    }
  }

  /**
   * Helper method for printing out the lookUps
   *
   * @param lookUps the lookUps
   */
  private void printLookUps(List<String> lookUps) {
    if (logger.isInfoEnabled()) {
      if (lookUps.isEmpty()) {
        logger.info("No CLEAR lookUps initialized");
      } else {
        for (String lookUp : lookUps) {
          logger.info("CLEAR lookUp initialized: " + lookUp);
        }

      }
    }
  }

  /**
   * Returns the entire map of properties
   *
   * @return the map of properties
   */
  public Map<String, ClearProperty> getProperties() {
    return properties;
  }

  @Override
  public Set<String> getKeys() {
    return properties.keySet();
  }

  @Override
  public String getString(String key, String defaultValue) {
    ClearProperty property = properties.get(key);

    if (property == null) {
      return defaultValue;
    } else {
      return property.getValue();
    }
  }

  @Override
  public String getString(String key) {
    return getString(key, null);
  }

  @Override
  public int getInt(String key, Integer defaultValue) {
    String anIntStr = getString(key);

    if (anIntStr == null) {
      if (defaultValue == null) {
        throw new ClearException("No int value exist for key '" + key + "' and no default value was specified.");
      }
      return defaultValue;
    }

    try {
      return new Integer(anIntStr);
    } catch (NumberFormatException e) {
      throw new ClearException(e);
    }
  }

  @Override
  public int getInt(String key) {
    return getInt(key, null);
  }

  @Override
  public long getLong(String key, Long defaultValue) {
    String aLongStr = getString(key);

    if (aLongStr == null) {
      if (defaultValue == null) {
        throw new ClearException("No long value exist for key '" + key + "' and no default value was specified.");
      }
      return defaultValue;
    }

    try {
      return new Long(aLongStr);
    } catch (NumberFormatException e) {
      throw new ClearException(e);
    }
  }

  @Override
  public long getLong(String key) {
    return getLong(key, null);
  }

  @Override
  public boolean getBoolean(String key, Boolean defaultValue) {
    String aBooleanStr = getString(key);

    if (aBooleanStr == null) {
      if (defaultValue == null) {
        throw new ClearException("No boolean value exist for key '" + key + "' and no default value was specified.");
      }
      return defaultValue;
    }

    return Boolean.parseBoolean(aBooleanStr);
  }

  @Override
  public boolean getBoolean(String key) {
    return getBoolean(key, null);
  }

  @Override
  public double getDouble(String key, Double defaultValue) {
    String aDblStr = getString(key);

    if (aDblStr == null) {
      if (defaultValue == null) {
        throw new ClearException("No double value exist for key '" + key + "' and no default value was specified.");
      }
      return defaultValue;
    }

    try {
      return new Double(aDblStr);
    } catch (NumberFormatException e) {
      throw new ClearException(e);
    }
  }

  @Override
  public double getDouble(String key) {
    return getDouble(key, null);
  }

  @Override
  public float getFloat(String key, Float defaultValue) throws ClearException {
    String aFloatStr = getString(key);

    if (aFloatStr == null) {
      if (defaultValue == null) {
        throw new ClearException("No double value exist for key '" + key + "' and no default value was specified.");
      }
      return defaultValue;
    }

    try {
      return new Float(aFloatStr);
    } catch (NumberFormatException e) {
      throw new ClearException(e);
    }
  }

  @Override
  public float getFloat(String key) {
    return getFloat(key, null);
  }

  @Override
  public short getShort(String key, Short defaultValue) throws ClearException {
    String aShortStr = getString(key);

    if (aShortStr == null) {
      if (defaultValue == null) {
        throw new ClearException("No double value exist for key '" + key + "' and no default value was specified.");
      }
      return defaultValue;
    }

    try {
      return new Short(aShortStr);
    } catch (NumberFormatException e) {
      throw new ClearException(e);
    }
  }

  @Override
  public short getShort(String key) {
    return getShort(key, null);
  }

  /**
   * Used to destroy all objects used by Clear
   */
  public void destroy() {
    if (logger.isInfoEnabled()) {
      logger.info("Destroying CLEAR object model");
    }
    initializer = null;
    validator = null;
    propertyService = null;
    properties.clear();
    properties = null;
  }
}
