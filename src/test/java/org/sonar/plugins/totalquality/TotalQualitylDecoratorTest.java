/*
 * Sonar Total Quality Plugin
 * Copyright (C) 2010 Martin (e72636) and Emilio Escobar Reyero (escoem)
 * dev@sonar.codehaus.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.plugins.totalquality;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import org.sonar.api.config.Settings;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Measure;
import org.sonar.api.resources.Project;
import org.sonar.api.resources.Qualifiers;
import org.sonar.api.resources.Resource;
import org.sonar.api.resources.Scopes;

public class TotalQualitylDecoratorTest {

  private final static double ARCH = 80D;
  private final static double DESIGN = 60D;
  private final static double CODE = 100D;
  private final static double TESTS = 50D;
  private final static double IT_TESTS = 30D;
  private final static double TQ_VAL = (0.25 * ARCH) + (0.25 * DESIGN) + (0.25 * CODE) + (0.25 * TESTS);
  private final static double TQ_VAL_WITH_IT = (0.25 * ARCH) + (0.25 * DESIGN) + (0.25 * CODE) + (0.25 * IT_TESTS);
  private final Resource res = mock(Resource.class);
  private final Measure ncl = mock(Measure.class);
  private final Measure mockArch = mock(Measure.class);
  private final Measure mockDesign = mock(Measure.class);
  private final Measure mockCode = mock(Measure.class);
  private final Measure mockTests = mock(Measure.class);
  private final Measure mockItTests = mock(Measure.class);
  private final DecoratorContextSupport context = spy(new DecoratorContextSupport());
  private final Settings settings = new Settings();
  private TotalQualityDecorator decorator;

  @Before
  public void setupMocks() {
    when(res.getQualifier()).thenReturn(Qualifiers.MODULE);
    when(res.getScope()).thenReturn(Scopes.PROJECT);

    when(ncl.getValue()).thenReturn(4500D);

    when(mockArch.getValue()).thenReturn(ARCH);

    when(mockDesign.getValue()).thenReturn(DESIGN);

    when(mockCode.getValue()).thenReturn(CODE);

    when(mockTests.getValue()).thenReturn(TESTS);

    when(mockItTests.getValue()).thenReturn(IT_TESTS);

    when(context.getMeasure(CoreMetrics.NCLOC)).thenReturn(ncl);
    when(context.getMeasure(TQMetrics.TQ_ARCHITECTURE)).thenReturn(mockArch);
    when(context.getMeasure(TQMetrics.TQ_DESIGN)).thenReturn(mockDesign);
    when(context.getMeasure(TQMetrics.TQ_CODE)).thenReturn(mockCode);
    when(context.getMeasure(TQMetrics.TQ_TS)).thenReturn(mockTests);
    when(context.getMeasure(TQMetrics.TQ_OVERALL_TS)).thenReturn(mockItTests);

    settings.setProperty(TQPlugin.TQ_INCLUDE_IT_TESTS, Boolean.FALSE);
    settings.setProperty(TQPlugin.TQ_TQ_FORMULA_WITH_IT, TQPlugin.TQ_TQ_FORMULA_WITH_IT_DEFAULT);
    settings.setProperty(TQPlugin.TQ_TQ_FORMULA, TQPlugin.TQ_TQ_FORMULA_DEFAULT);

    decorator = new TotalQualityDecorator(settings);
  }

  @Test
  public void testDecoratorWithoutITCoverage() {

    decorator.decorate(res, context);

    assertNotNull(context.getD());
    assertTrue(TQ_VAL == context.getD());

  }

  @Test
  public void testDecoratorWithITCoverage() {
    settings.setProperty(TQPlugin.TQ_INCLUDE_IT_TESTS, Boolean.TRUE);
    decorator = new TotalQualityDecorator(settings);
    decorator.decorate(res, context);

    assertNotNull(context.getD());
    assertTrue(TQ_VAL_WITH_IT == context.getD());

  }

  @Test
  public void testShouldExecute() {
    assertTrue(decorator.shouldExecuteOnProject(new Project(null)));
  }
}
