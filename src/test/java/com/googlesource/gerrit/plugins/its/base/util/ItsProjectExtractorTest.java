// Copyright (C) 2018 The Android Open Source Project
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.googlesource.gerrit.plugins.its.base.util;

import static org.easymock.EasyMock.expect;

import com.google.gerrit.entities.Project;
import com.google.gerrit.extensions.config.FactoryModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.googlesource.gerrit.plugins.its.base.its.ItsConfig;
import com.googlesource.gerrit.plugins.its.base.testutil.MockingTestCase;
import java.util.Optional;

public class ItsProjectExtractorTest extends MockingTestCase {

  private static final String PROJECT = "project";
  private static final String ITS_PROJECT = "itsProject";

  private Injector injector;
  private ItsConfig itsConfig;

  public void test() {
    ItsProjectExtractor projectExtractor = injector.getInstance(ItsProjectExtractor.class);

    expect(itsConfig.getItsProjectName(Project.nameKey(PROJECT)))
        .andReturn(Optional.of(ITS_PROJECT))
        .once();

    replayMocks();

    String ret = projectExtractor.getItsProject(PROJECT).orElse(null);
    assertEquals(ret, ITS_PROJECT);
  }

  @Override
  public void setUp() throws Exception {
    super.setUp();

    injector = Guice.createInjector(new TestModule());
  }

  private class TestModule extends FactoryModule {
    @Override
    protected void configure() {
      itsConfig = createMock(ItsConfig.class);
      bind(ItsConfig.class).toInstance(itsConfig);
    }
  }
}
