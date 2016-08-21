package org.shikato.gradle.android.coverage.check.coverage

import org.gradle.api.Project
import org.gradle.api.file.FileTree
import org.gradle.api.file.FileVisitDetails
import org.gradle.api.file.FileVisitor
import org.shikato.gradle.android.coverage.check.AndroidCoverageCheckExtension

/**
 * CoverageChecker.groovy
 *
 * Copyright (c) 2016 shikato
 *
 * This software is released under the MIT license.
 * http://opensource.org/licenses/mit-license.html
 */

class Checker {

    public static Coverage check(Project project, All coverageAll,
                                 AndroidCoverageCheckExtension extension) {

        List<String> excludes = getExcludes(project, extension.getExcludesEntryDir(),
                extension.getExcludesPath());

        coverageAll.getClassList().each {
            it.setIsExclude(isExclude(excludes, it.getClassName()));
            checkCoverageCounter(it, coverageAll, extension, true);
        };

        return checkCoverageCounter(coverageAll, coverageAll, extension, false);
    }

    private static Coverage checkCoverageCounter(Coverage coverage,
                                                 All coverageAll,
                                                 AndroidCoverageCheckExtension extension,
                                                 boolean isSetIsHavingUnsatisfiedCoverage) {
        boolean isHavingInstruction = false;
        boolean isHavingBranch = false;

        coverage.getCounterList().each {
            // 現状はINSTRUCTIONとBRANCHのみ
            if (it.getType() != Coverage.INSTRUCTION &&
                    it.getType() !=
                    Coverage.BRANCH) {
                return;
            }

            if (!isSatisfiedMinimumThreshold(it, extension.getInstruction())) {
                if (isSetIsHavingUnsatisfiedCoverage) {
                    coverageAll.setIsHavingUnsatisfiedCoverage(true);
                }
                it.setIsSatisfied(false);
            } else {
                it.setIsSatisfied(true);
            }

            it.setRate(getRateOfSatisfiedCoverage(it));
            if (it.getType() == Coverage.INSTRUCTION) {
                isHavingInstruction = true;
            } else if (it.getType() == Coverage.BRANCH) {
                isHavingBranch = true;
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

    private static boolean isExclude(List<String> excludes, String path) {
        for (String exclude : excludes) {
            if (exclude.lastIndexOf(path) != -1) return true;
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

                    int point = filePath.lastIndexOf(".");
                    if (point == -1) return;
                    excludes.add(filePath.substring(0, point));
                }
            });
        }

        return excludes;
    }
}
