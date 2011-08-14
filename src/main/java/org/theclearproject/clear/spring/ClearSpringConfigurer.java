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

package org.theclearproject.clear.spring;

import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.theclearproject.clear.Clear;
import org.theclearproject.clear.guice.ClearGuiceModule;
import org.theclearproject.clear.init.Initializer;
import org.theclearproject.clear.lookup.LookUpKeyResolver;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Extends the PropertyPlaceholderConfigurer so that CLEAR properties
 * can be referenced within Spring xml files to configure Spring
 *
 * @author jhumphrey
 */
public class ClearSpringConfigurer extends PropertyPlaceholderConfigurer {

  private static final Logger logger = Logger.getLogger(ClearSpringConfigurer.class);

  private Initializer initializer;
  private LookUpKeyResolver lookUpKeyResolver;

  public ClearSpringConfigurer() {
    super();
  }

  public void setInitializer(Initializer initializer) {
    this.initializer = initializer;
  }

  public void setLookUpKeyResolver(LookUpKeyResolver lookUpKeyResolver) {
    this.lookUpKeyResolver = lookUpKeyResolver;
  }

  /**
   * Overriden to put CLEAR properties
   *
   * Guice is used to load CLEAR classes.  The reason this is necessary is because the
   * PropertyPlaceholderConfigurer is constructed very early in the spring container life cycle.
   * So early that Spring IoC and can't be used to autowire in CLEAR classes.
   *
   * @param beanFactory a spring ConfigurableListableBeanFactory factory
   * @throws org.springframework.beans.BeansException if there's an exception during bean creation and autowiring
   */
  @Override
  public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    if (logger.isInfoEnabled()) {
      logger.info("Initializing CLEAR for Spring property placeholder configuration");
    }

    Injector injector = Guice.createInjector(
      new ClearGuiceModule(),
      new AbstractModule() {
        @Override
        protected void configure() {
          bind(Initializer.class).to(initializer.getClass());
          bind(LookUpKeyResolver.class).to(lookUpKeyResolver.getClass());
        }
      });

    Clear clear = injector.getInstance(Clear.class);

    Properties props = new Properties();

    Set<String> keys = clear.getKeys();

    for (String key : keys) {
      if (!(key == null || key.length() == 0)) {
        String value = clear.getString(key);
        props.put(key, value);
      }
    }

    setProperties(props);

    clear.destroy();

    super.postProcessBeanFactory(beanFactory);
  }
}
