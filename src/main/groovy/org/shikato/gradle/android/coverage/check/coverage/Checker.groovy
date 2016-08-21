package org.shikato.gradle.android.coverage.check.coverage

import org.gradle.api.Project
import org.gradle.api.file.FileTree
import org.gradle.api.file.FileVisitDetails
import org.gradle.api.file.FileVisitor
import org.shikato.gradle.android.coverage.check.AndroidCoverageCheckExtension

/**
 * Checker.groovy
 *
 * Copyright (c) 2016 shikato
 *
 * This software is released under the MIT license.
 * http://opensource.org/licenses/mit-license.html
 */

class Checker {

    private static Counter allInstructionsCounter = null;
    private static Counter allBranchCounter = null;

    public synchronized static Coverage check(Project project, All coverageAll,
                                 AndroidCoverageCheckExtension extension) {
        allInstructionsCounter = new Counter();
        allBranchCounter = new Counter();

        List<String> excludes = getExcludes(project, extension.getExcludesEntryDir(),
                extension.getExcludesPath());

        coverageAll.getClassList().each {
            String classNameExceptDollar = getClassNameExceptDollar(it.getClassName());
            it.isTarget = it.getClassName() == classNameExceptDollar;
            it.isExclude = isExclude(excludes, classNameExceptDollar);
            checkCoverageCounter(it, coverageAll, extension, false);
        };

        List<Counter> allCounterList = new ArrayList<>();
        allCounterList.add(allInstructionsCounter);
        allCounterList.add(allBranchCounter);
        coverageAll.setCounterList(allCounterList);

        return checkCoverageCounter(coverageAll, coverageAll, extension, true);
    }

    private static Coverage checkCoverageCounter(Coverage coverage,
                                                 All coverageAll,
                                                 AndroidCoverageCheckExtension extension,
                                                 boolean isAll) {
        coverage.getCounterList().each {
            if (it.getType() != Coverage.INSTRUCTION &&
                    it.getType() !=
                    Coverage.BRANCH) {
                return;
            }

            if (!isSatisfiedMinimumThreshold(it, extension.getInstruction())) {
                if (!isAll) {
                    coverageAll.setHasUnsatisfiedCoverage(true);
                }
                it.setIsSatisfied(false);
            } else {
                it.setIsSatisfied(true);
            }

            it.setRate(getRateOfSatisfiedCoverage(it));
            if (it.getType() == Coverage.INSTRUCTION) {
                if (!isAll && !((Class)coverage).isExclude) {
                    increaseAllCounter(allInstructionsCounter, it)
                };
            } else if (it.getType() == Coverage.BRANCH) {
                if (!isAll && !((Class)coverage).isExclude) {
                    increaseAllCounter(allBranchCounter, it)
                };
            }
        }

        return coverage;
    }

    private static boolean isSatisfiedMinimumThreshold(Counter counter,
                                                       int minimumThreshold) {
        if (Float.compare(getRateOfSatisfiedCoverage(counter), minimumThreshold) < 0) {
            return false;
        }
        return true;
    }

    private static float getRateOfSatisfiedCoverage(Counter counter) {
        float total = counter.getMissed() + counter.getCovered();
        if (Float.compare(total, 0F) <= 0) return 0F;
        return counter.getCovered() / total * 100;
    }

    private static void increaseAllCounter(Counter base, Counter target) {
        base.setType(target.getType());
        base.missed += target.missed;
        base.covered += target.covered;
    }

    private static boolean isExclude(List<String> excludes, String path) {
        for (String exclude : excludes) {
            if (exclude.lastIndexOf(path) != -1) {
                return true;
            }
        }
        return false;
    }

    private static List<String> getExcludes(Project project, String entryPoint, String[] path) {
        List<String> excludes = new ArrayList<>();

        FileTree excludesFileTree = project.fileTree(entryPoint);

        path.each {
            excludesFileTree.include(it).visit(new FileVisitor() {
                @Override
                void visitDir(FileVisitDetails dirDetails) {}

                @Override
                void visitFile(FileVisitDetails fileDetails) {
                    File file = project.file(fileDetails.getFile());
                    if (!file.exists()) return;

                    String filePath = file.getPath()
                    if (filePath == null) return;
                    if (excludes.contains(filePath)) return;

                    excludes.add(getClassNameExceptExtension(filePath))
                }
            });
        }

        return excludes;
    }

    private static getClassNameExceptDollar(String className) {
        int point = className.indexOf("\$");
        if (point != -1) return className.substring(0, point);
        return className;
    }

    private static getClassNameExceptExtension(String className) {
        int point = className.lastIndexOf(".");
        if (point != -1) return className.substring(0, point);
        return className;
    }
}
