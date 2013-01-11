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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.config.Settings;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Measure;
import org.sonar.api.resources.Qualifiers;
import org.sonar.api.resources.Resource;
import org.sonar.api.resources.Scopes;

public class OveralTestlDecoratorTest {

  private final static double IT_COV = 30D;
  private final static double QT_TESTS = 52;
  private final static double VAL_IT = (0.40 * IT_COV) + (0.60 * QT_TESTS);
  private final static double VAL_NOIT = 40 + (0.60 * QT_TESTS);
  private final Resource res = mock(Resource.class);
  private final Measure ncl = mock(Measure.class);
  private final Measure mitcov = mock(Measure.class);
  private final Measure mockQtTests = mock(Measure.class);
  private final DecoratorContextSupport context = spy(new DecoratorContextSupport());

  @Before
  public void setupMocks() {
    when(res.getQualifier()).thenReturn(Qualifiers.MODULE);
    when(res.getScope()).thenReturn(Scopes.PROJECT);

    when(ncl.getValue()).thenReturn(4500D);

    when(mitcov.getValue()).thenReturn(null);

    when(mockQtTests.getValue()).thenReturn(QT_TESTS);

    when(context.getMeasure(CoreMetrics.NCLOC)).thenReturn(ncl);
    when(context.getMeasure(TQMetrics.TQ_TS)).thenReturn(mockQtTests);
    when(context.getMeasure(CoreMetrics.IT_COVERAGE)).thenReturn(mitcov);
  }
  private final OverallTestDecorator decorator = new OverallTestDecorator(new Settings()) {
    @Override
    protected String getLine(DecoratorContext context) {
      return TQPlugin.TQ_OVERALL_TEST_FORMULA_DEFAULT;
    }
  };

  @Test
  public void testDecoratorWithoutITCoverage() {

    decorator.decorate(res, context);

    assertNotNull(context.getD());
    assertTrue(VAL_NOIT == context.getD());

  }

  @Test
  public void testDecoratorWithITCoverage() {
    when(mitcov.getValue()).thenReturn(IT_COV);

    decorator.decorate(res, context);

    assertNotNull(context.getD());
    assertTrue(VAL_IT == context.getD());

  }
}
