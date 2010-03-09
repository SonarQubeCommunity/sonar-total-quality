package org.sonar.plugins.tq;


import java.util.Arrays;
import java.util.List;

import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.batch.DependedUpon;
import org.sonar.api.batch.DependsUpon;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Metric;
import org.sonar.api.resources.Resource;

public class ArchitectureCOHDecorator extends AbstractBaseDecorator {

	@DependedUpon
	public List<Metric> generatesMetrics() {
		return Arrays.asList(TQMetrics.TQ_ARCHITECTURE_COH);
	}

	@DependsUpon
	public List<Metric> dependsOnMetrics() {
		return Arrays.asList(CoreMetrics.FILE_CYCLES, CoreMetrics.PACKAGE_CYCLES, CoreMetrics.NCLOC);
	}
	
	@Override
	void decorateDir(Resource resource, DecoratorContext context) {
		// TODO Auto-generated method stub

	}

	@Override
	void decorateFile(Resource resource, DecoratorContext context) {
		// TODO Auto-generated method stub

	}

	@Override
	void decorateProj(Resource resource, DecoratorContext context) {
		// TODO Auto-generated method stub

	}

}
