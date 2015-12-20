package org.shikato.gradle.android.coverage.check

import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.shikato.gradle.android.coverage.check.coverage.CoverageAll
import org.shikato.gradle.android.coverage.check.coverage.CoverageChecker
import org.shikato.gradle.android.coverage.check.log.CoverageTableLog
import org.shikato.gradle.android.coverage.check.xml.ReportXmlGetter
import org.shikato.gradle.android.coverage.check.xml.ReportXmlParser

class AndroidCoverageCheckPlugin implements Plugin<Project> {

    private static final String TASK_NAME_TYPE = "androidCoverageCheck";
    private static final String DESCRIPTION_TYPE = "Checks coverage reports.";
    private static final String EXTENSIONS_NAME = "androidCoverageCheck";

    @Override
    void apply(Project project) {
        project.extensions.create(EXTENSIONS_NAME, AndroidCoverageCheckExtension);

        project.task(TASK_NAME_TYPE, description: DESCRIPTION_TYPE) << {
            androidCoverageCheck(project);
        }
    }

    private void androidCoverageCheck(Project project) {
        AndroidCoverageCheckExtension extension = project.androidCoverageCheck;

        // getting report.xml
        List<File> reportXmlList = ReportXmlGetter.get(project,
                extension.getReportXmlEntryDir(), extension.getReportXmlPath());

        if (reportXmlList == null || reportXmlList.size() == 0) {
            project.logger.warn("Failed getting coverage reports.")
            return;
        }

        reportXmlList.each {

            // parsing report.xml
            CoverageAll coverage = ReportXmlParser.parse(project, it.text);
            coverage.setReportPath(it.path);
            if (coverage == null || coverage.getSourcefileList().size() == 0) return;

            // checking coverage
            coverage = CoverageChecker.check(project, coverage, extension);

            // output log
            CoverageTableLog.show(project, coverage, extension);

            // isHavingUnsatisfiedCoverage && isBuidFailure → failure build
            if (coverage.getIsHavingUnsatisfiedCoverage() && extension.isBuildFailure) {
                throw new GradleException("Coverage did not satisfy the minimum threshold.");
            }
        }
    }
}
