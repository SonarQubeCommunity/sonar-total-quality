package org.sonar.plugins.tq;

import org.sonar.api.batch.Decorator;
import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.resources.Java;
import org.sonar.api.resources.Project;
import org.sonar.api.resources.Resource;

public abstract class AbstractBaseDecorator implements Decorator {

	abstract void decorateFile(Resource resource, DecoratorContext context);
	abstract void decorateDir(Resource resource, DecoratorContext context);
	abstract void decorateProj(Resource resource, DecoratorContext context);
	
	public void decorate(Resource resource, DecoratorContext context) {
		if (isFile(resource, context)) {
			decorateFile(resource, context);
		} else if (isDir(resource, context)) {
			decorateDir(resource, context);
		} else if (isProj(resource, context)) {
			decorateProj(resource, context);
		} else {
			// not hear please!
		}
	}
	
	
	public boolean isFile(final Resource resource, final DecoratorContext context) {
		return shouldSaveMeasure(resource)
			&& (Resource.QUALIFIER_FILE.equals(resource.getQualifier()) || Resource.QUALIFIER_CLASS.equals(resource
				.getQualifier()));
	}

	public boolean isDir(final Resource resource, final DecoratorContext context) {
		return shouldSaveMeasure(resource) && hasCode(context)
			&& (Resource.QUALIFIER_DIRECTORY.equals(resource.getQualifier()) || Resource.QUALIFIER_PACKAGE
				.equals(resource.getQualifier()));
	}

	public boolean isProj(final Resource resource, final DecoratorContext context) {
		return shouldSaveMeasure(resource) && hasCode(context)
			&& (Resource.QUALIFIER_MODULE.equals(resource.getQualifier())
				|| Resource.QUALIFIER_PROJECT.equals(resource.getQualifier())
				|| Resource.QUALIFIER_SUBVIEW.equals(resource.getQualifier()) || Resource.QUALIFIER_VIEW
				.equals(resource.getQualifier()));
	}

	public boolean hasCode(final DecoratorContext context) {
		return context.getMeasure(CoreMetrics.NCLOC) != null
			&& context.getMeasure(CoreMetrics.NCLOC).getValue() != null
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
