/*
 * Sonar Total Quality Plugin, open source software quality management tool.
 * Copyright (C) 2010
 * mailto:e72636 AT gmail DTO com
 * mailto:escoem AT gmail DTO com
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

import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.resources.Resource;

public abstract class AbstractBaseDecorator extends AbstractDecorator {

  abstract void decorateFile(Resource resource, DecoratorContext context);

  abstract void decorateDir(Resource resource, DecoratorContext context);

  abstract void decorateProj(Resource resource, DecoratorContext context);

  public void decorate(Resource resource, DecoratorContext context) {
    if (shouldDecorateFile(resource, context)) {
      decorateFile(resource, context);
    } else if (shouldDecorateDir(resource, context)) {
      decorateDir(resource, context);
    } else if (shouldDecorateProj(resource, context)) {
      decorateProj(resource, context);
    } else {
      // not hear please!
    }
  }

  public boolean shouldDecorateFile(final Resource resource, final DecoratorContext context) {
    return shouldSaveMeasure(resource) && isFile(resource);
  }

  public boolean shouldDecorateDir(final Resource resource, final DecoratorContext context) {
    return shouldSaveMeasure(resource) && hasCode(context) && isDir(resource);
  }

  public boolean shouldDecorateProj(final Resource resource, final DecoratorContext context) {
    return shouldSaveMeasure(resource) && hasCode(context) && isProj(resource);
  }

}
