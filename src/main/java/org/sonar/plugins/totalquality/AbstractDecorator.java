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

import org.sonar.api.batch.Decorator;
import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.resources.Project;
import org.sonar.api.resources.Qualifiers;
import org.sonar.api.resources.Resource;
import org.sonar.api.resources.Scopes;

public abstract class AbstractDecorator implements Decorator {

  public boolean hasCode(final DecoratorContext context) {
    return context.getMeasure(CoreMetrics.NCLOC) != null && context.getMeasure(CoreMetrics.NCLOC).getValue() != null
        && context.getMeasure(CoreMetrics.NCLOC).getValue().doubleValue() > 0;
  }

  public boolean shouldSaveMeasure(final Resource resource) {
    return !Qualifiers.UNIT_TEST_FILE.equals(resource.getQualifier());
  }

  /** Only for java projects. */
  public boolean shouldExecuteOnProject(Project project) {
    return true;
  }

  public boolean isFile(final Resource resource) {
    return Scopes.isFile(resource);
  }

  public boolean isDir(final Resource resource) {
    return Scopes.isDirectory(resource);
  }

  public boolean isProj(final Resource resource) {
    return Scopes.isProject(resource);
    
  }

}
