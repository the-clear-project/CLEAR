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

package org.theclearproject.clear.guiceintegration;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.theclearproject.clear.init.Initializer;
import org.theclearproject.clear.lookup.LookUpKeyResolver;
import org.theclearproject.clear.lookup.ParenthesesLookUpKeyResolver;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

/**
 * @author jhumphrey
 */
public class ClearIntegrationGuiceModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(Initializer.class).to(IntegrationInitializer.class);
    bind(LookUpKeyResolver.class).to(ParenthesesLookUpKeyResolver.class);
  }

  @Provides
  public Validator provideValidator() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    return factory.getValidator();
  }
}
