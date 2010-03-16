/*
 * Sonar Total Quality Plugin, open source software quality management tool.
 * Copyright (C) 2010 
 * mailto:e72636 AT gmail DTO com
 *
 * Sonar Total Quality Plugin is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * Sonar Total Quality Plugin is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Sonar; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.plugins.totalquality;

import org.sonar.api.batch.Decorator;
import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.resources.Java;
import org.sonar.api.resources.Project;
import org.sonar.api.resources.Resource;

public abstract class AbstractDecorator implements Decorator {

  public boolean hasCode(final DecoratorContext context) {
    return context.getMeasure(CoreMetrics.NCLOC) != null && context.getMeasure(CoreMetrics.NCLOC).getValue() != null
        && context.getMeasure(CoreMetrics.NCLOC).getValue().doubleValue() > 0;
  }

  public boolean shouldSaveMeasure(final Resource resource) {
    return !Resource.QUALIFIER_UNIT_TEST_CLASS.equals(resource.getQualifier());
  }

  /** Only for java projects. */
  public boolean shouldExecuteOnProject(Project project) {
    return Java.INSTANCE.equals(project.getLanguage());
  }
}
