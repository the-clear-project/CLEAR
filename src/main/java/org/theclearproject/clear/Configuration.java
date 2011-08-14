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

import java.util.Set;

import com.google.inject.ImplementedBy;

/**
 * Interface for accessing configuration properties
 *
 * @author jhumphrey
 */
@ImplementedBy(Clear.class)
public interface Configuration {

  /**
   * Returns all the configuration property keys
   *
   * @return the configuration property keys
   */
  Set<String> getKeys();

  /**
   * Returns the string value associated to the key.
   * If no key is defined, then the default value is returned
   *
   * @param key the property key
   * @param defaultValue the default value
   * @return the string value associated to the property key
   */
  String getString(String key, String defaultValue);

  /**
   * Returns the string value associated to the key.
   *
   * @param key the property key
   * @return the string value associated to the property key
   */
  String getString(String key);

  /**
   * Returns the int value associated to the key.
   * If no key is defined, then the default value is returned
   *
   * @param key the property key
   * @param defaultValue the default value
   * @return the int value associated to the property key
   * @throws ClearException thrown if key is undefined and default is null
   */
  int getInt(String key, Integer defaultValue) throws ClearException;

  /**
   * Returns the int value associated to the key.
   *
   * @param key the property key
   * @return the int value associated to the property key
   */
  int getInt(String key);

  /**
   * Returns the long value associated to the key.
   * If no key is defined, then the default value is returned
   *
   * @param key the property key
   * @param defaultValue the default value
   * @return the long value associated to the property key
   * @throws ClearException thrown if key is undefined and default is null
   */
  long getLong(String key, Long defaultValue) throws ClearException;

  /**
   * Returns the long value associated to the key.
   *
   * @param key the property key
   * @return the long value associated to the property key
   */
  long getLong(String key);

  /**
   * Returns the boolean value associated to the key.
   * If no key is defined, then the default value is returned
   *
   * @param key the property key
   * @param defaultValue the default value
   * @return the boolean value associated to the property key
   * @throws ClearException thrown if key is undefined and default is null
   */
  boolean getBoolean(String key, Boolean defaultValue) throws ClearException;

  /**
   * Returns the boolean value associated to the key.
   *
   * @param key the property key
   * @return the boolean value associated to the property key
   */
  boolean getBoolean(String key);

  /**
   * Returns the double value associated to the key.
   * If no key is defined, then the default value is returned
   *
   * @param key the property key
   * @param defaultValue the default value
   * @return the double value associated to the property key
   * @throws ClearException thrown if key is undefined and default is null
   */
  double getDouble(String key, Double defaultValue) throws ClearException;

  /**
   * Returns the double value associated to the key.
   *
   * @param key the property key
   * @return the double value associated to the property key
   */
  double getDouble(String key);

  /**
   * Returns the float value associated to the key.
   * If no key is defined, then the default value is returned
   *
   * @param key the property key
   * @param defaultValue the default value
   * @return the float value associated to the property key
   * @throws ClearException thrown if key is undefined and default is null
   */
  float getFloat(String key, Float defaultValue) throws ClearException;

  /**
   * Returns the float value associated to the key.
   *
   * @param key the property key
   * @return the float value associated to the property key
   */
  float getFloat(String key);

  /**
   * Returns the short value associated to the key.
   * If no key is defined, then the default value is returned
   *
   * @param key the property key
   * @param defaultValue the default value
   * @return the short value associated to the property key
   * @throws ClearException thrown if key is undefined and default is null
   */
  short getShort(String key, Short defaultValue) throws ClearException;

  /**
   * Returns the short value associated to the key.
   *
   * @param key the property key
   * @return the short value associated to the property key
   */
  short getShort(String key);
}
