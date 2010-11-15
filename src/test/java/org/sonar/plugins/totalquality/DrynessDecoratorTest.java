/*
 * Sonar Total Quality Plugin
 * Copyright (C) 2010 Martin (e72636) & Emilio Escobar Reyero (escoem)
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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Measure;
import org.sonar.api.resources.Resource;


public class DrynessDecoratorTest {

  @Test
  public void testDecorate() {
    final Measure dup = mock(Measure.class);
    when(dup.getValue()).thenReturn(45D);

    final Measure ncl = mock(Measure.class);
    when(ncl.getValue()).thenReturn(4500D);
    
    final Resource res = mock(Resource.class);
    when(res.getQualifier()).thenReturn(Resource.QUALIFIER_MODULE);
    
    final DecoratorContextSupport context = spy(new DecoratorContextSupport());
    
    when(context.getMeasure(CoreMetrics.DUPLICATED_LINES_DENSITY)).thenReturn(dup);
    when(context.getMeasure(CoreMetrics.NCLOC)).thenReturn(ncl);
    
    final DrynessDecorator decorator = new DrynessDecorator();
    
    decorator.decorate(res, context);
    
    assertNotNull(context.getD());
    assertTrue(55D == context.getD());
  }
  
  @Test
  public void testNoDecorateNoLines() {
    final Measure dup = mock(Measure.class);
    when(dup.getValue()).thenReturn(45D);

    final Resource res = mock(Resource.class);
    when(res.getQualifier()).thenReturn(Resource.QUALIFIER_PROJECT);
    
    final DecoratorContextSupport context = spy(new DecoratorContextSupport());
    
    when(context.getMeasure(CoreMetrics.DUPLICATED_LINES_DENSITY)).thenReturn(dup);
    
    final DrynessDecorator decorator = new DrynessDecorator();
    
    decorator.decorate(res, context);
    
    assertNull(context.getD());
  }

  @Test
  public void testNoDecorateTestClass() {
    final Measure dup = mock(Measure.class);
    when(dup.getValue()).thenReturn(45D);

    final Measure ncl = mock(Measure.class);
    when(ncl.getValue()).thenReturn(4500D);
    
    final Resource res = mock(Resource.class);
    when(res.getQualifier()).thenReturn(Resource.QUALIFIER_UNIT_TEST_CLASS);
    
    final DecoratorContextSupport context = spy(new DecoratorContextSupport());
    
    when(context.getMeasure(CoreMetrics.DUPLICATED_LINES_DENSITY)).thenReturn(dup);
    when(context.getMeasure(CoreMetrics.NCLOC)).thenReturn(ncl);
    
    final DrynessDecorator decorator = new DrynessDecorator();
    
    decorator.decorate(res, context);
    
    assertNull(context.getD());
  }
  
}
