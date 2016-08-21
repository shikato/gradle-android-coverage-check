package org.shikato.gradle.android.coverage.check.xml

import groovy.util.slurpersupport.GPathResult
import org.gradle.api.Project
import org.shikato.gradle.android.coverage.check.coverage.All
import org.shikato.gradle.android.coverage.check.coverage.Class
import org.shikato.gradle.android.coverage.check.coverage.Counter
import org.shikato.gradle.android.coverage.check.coverage.Sourcefile
import org.shikato.gradle.android.coverage.check.util.DefaultValue

/**
 * ReportXmlParser.groovy
 *
 * Copyright (c) 2016 shikato
 *
 * This software is released under the MIT license.
 * http://opensource.org/licenses/mit-license.html
 */

class ReportXmlParser {

    public static All parse(Project project, String xmlReport) {

        XmlSlurper parser = new XmlSlurper(false, false, true);
        if (parser == null) {
            project.logger.warn "Failed getting a xml parser..";
            return null
        };

        parser.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        GPathResult node = parser.parseText(xmlReport);
        if (node == null) {
            project.logger.warn "Failed getting a xml node.";
            return null
        };

        All coverage = parseReportXml(node);
        if (coverage == null) {
            project.logger.warn "Failed parsing xml";
            return null
        }

        return coverage;
    }

    private static All parseReportXml(GPathResult node) {

        List<Class> classList = new ArrayList<>();
        List<Sourcefile> sourcefileList = new ArrayList<>();
        String currentPackageName = DefaultValue.STRING;

        node.package.each {
            currentPackageName = it.@name.toString();

            it.class.each {
                Class coverage = new Class();
                coverage.isTarget = isTargetClassTag(it.@name.toString());
                coverage.setPackageName(currentPackageName);
                coverage.setClassName(it.@name.toString());
                coverage.setCounterList(getCounterList(it.counter));
                classList.add(coverage);
            };

            it.sourcefile.each {
                Sourcefile coverage = new Sourcefile();
                coverage.setPackageName(currentPackageName);
                coverage.setFileName(it.@name.toString());
                coverage.setCounterList(getCounterList(it.counter));
                sourcefileList.add(coverage);
            };
        };

        All coverage = new All();
        coverage.setClassList(classList);
        coverage.setSourcefileList(sourcefileList);
        coverage.setCounterList(getCounterList(node.counter));

        return coverage;
    }

    private static getCounterList(GPathResult node) {
        List<Counter> counterList = new ArrayList<>();
        node.each {
            Counter counter = new Counter();
            counter.setType(it.@type.toString());
            counter.setMissed(Integer.parseInt(it.@missed.toString()));
            counter.setCovered(Integer.parseInt(it.@covered.toString()));
            counterList.add(counter);
        }
        return counterList;
    }

    private static boolean isTargetClassTag(String className) {
        return !(className ==~ /.*\$.*$/)
    }
}
