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

public class TestDecoratorTest {

  @Test
  public void testDecorator() {
    final double cov = 45D;
    final double suc = 80D;

    final double val = (0.80 * cov) + (0.20 * suc);

    final Resource res = mock(Resource.class);
    when(res.getQualifier()).thenReturn(Qualifiers.MODULE);
    when(res.getScope()).thenReturn(Scopes.PROJECT);

    final Measure ncl = mock(Measure.class);
    when(ncl.getValue()).thenReturn(4500D);

    final Measure mcov = mock(Measure.class);
    when(mcov.getValue()).thenReturn(cov);

    final Measure msuc = mock(Measure.class);
    when(msuc.getValue()).thenReturn(suc);


    final DecoratorContextSupport context = spy(new DecoratorContextSupport());
    when(context.getMeasure(CoreMetrics.NCLOC)).thenReturn(ncl);
    when(context.getMeasure(CoreMetrics.TEST_SUCCESS_DENSITY)).thenReturn(msuc);
    when(context.getMeasure(CoreMetrics.COVERAGE)).thenReturn(mcov);


    final TestDecorator decorator = new TestDecorator(new Settings()) {
      @Override
      protected String getLine(DecoratorContext context) {
        return TQPlugin.TQ_TEST_FORMULA_DEFAULT;
      }
    };

    decorator.decorate(res, context);

    assertNotNull(context.getD());
    assertTrue(val == context.getD());

  }
}
