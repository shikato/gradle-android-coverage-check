package org.shikato.gradle.android.coverage.check.coverage

import org.gradle.api.Project
import org.gradle.api.file.FileTree
import org.gradle.api.file.FileVisitDetails
import org.gradle.api.file.FileVisitor
import org.shikato.gradle.android.coverage.check.AndroidCoverageCheckExtension

class CoverageChecker {

    public static def check(Project project, CoverageAll coverage,
                            AndroidCoverageCheckExtension extension) {
        List<String> excludes = getExcludes(project, extension.getExcludesEntryDir(),
                extension.getExcludesPath());

        coverage.getSourcefileList().each {
            it.setIsExclude(isExclude(excludes,
                    it.getPackageName() + "/" + it.getFileName()));
            checkCoverageCounter(it, coverage, extension);
        };

        checkCoverageCounter(coverage, coverage, extension);
    }

    // TODO: 整理（ALlとsourcefileでoverloadするか、このまま合わせて処理するか）
    private static
    def checkCoverageCounter(Coverage coverage, CoverageAll coverageAll, AndroidCoverageCheckExtension extension) {
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
                coverageAll.setIsHavingUnsatisfiedCoverage(true);
                it.setIsSatisfied(false)
            } else {
                it.setIsSatisfied(true)
            }

            it.setRate(getRateOfSatisfiedCoverage(it));
            if (it.getType() == Coverage.INSTRUCTION) {
                isHavingInstruction = true;
            } else if (it.getType() == Coverage.BRANCH) {
                isHavingBranch = true;
            }
        }

        setDefaultCounter(coverage, isHavingInstruction, isHavingBranch);
    }

    private static boolean isSatisfiedMinimumThreshold(CoverageCounter counter,
                                                       int minimumThreshold) {
        if (Float.compare(getRateOfSatisfiedCoverage(counter), minimumThreshold) < 0) {
            return false;
        }
        return true;
    }

    private static float getRateOfSatisfiedCoverage(CoverageCounter counter) {
        float total = counter.getMissed() + counter.getCovered();
        if (Float.compare(total, 0F) <= 0) return 0F;
        return counter.getCovered() / total * 100;
    }

    private static boolean isExclude(List<String> excludes, String path) {
        for (String exclude : excludes) {
            if (exclude.lastIndexOf(path)) return true;
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
                    if (excludes.contains(file.getPath())) return;
                    excludes.add(file.getPath());
                }
            });
        }

        return excludes;
    }

    private
    static Coverage setDefaultCounter(Coverage coverage, boolean isHavingInstruction, boolean isHavingBranch) {
        List<CoverageCounter> counterList = coverage.getCounterList();
        if (!isHavingInstruction) {
            counterList.add(getInitCounter(Coverage.INSTRUCTION))
        } else if (!isHavingBranch) {
            counterList.add(getInitCounter(Coverage.BRANCH))
        }
        coverage.setCounterList(counterList);
        return coverage;
    }

    private static CoverageCounter getInitCounter(String type) {
        CoverageCounter counter = new CoverageCounter();
        counter.setType(type);
        return counter;
    }
}
