package org.shikato.gradle.android.coverage.check.log

import org.gradle.api.Project
import org.shikato.gradle.android.coverage.check.AndroidCoverageCheckExtension
import org.shikato.gradle.android.coverage.check.coverage.Coverage
import org.shikato.gradle.android.coverage.check.coverage.CoverageAll
import org.shikato.gradle.android.coverage.check.util.DefaultValue
import org.shikato.gradle.android.coverage.check.util.ErrorValue

import java.text.NumberFormat

/**
 * CoverageTableLog.groovy
 *
 * Copyright (c) 2016 shikato
 *
 * This software is released under the MIT license.
 * http://opensource.org/licenses/mit-license.html
 */

class CoverageTableLog {

    private static final String INSTRUCTION = "  % INSTR ";
    private static final String BRANCH      = " % BRANCH ";
    private static final String LINE        = "   % LINE ";
    private static final String METHOD      = " % METHOD ";
    private static final String COMPLEXITY  = "  % COMPL ";

    private static final String FILE        = "FILE                              ";
    private static final String ALL_FILES   = "All files                         ";

    private static final String IGNORE_RATE = "n/a";

    private static final enum Color {
        GREEN,
        YELLOW,
        RED
    }

    private static final int TRIM_SIZE = 1;

    private static int maxFileColumnLength = DefaultValue.INT;
    private static int maxInstructionColumnLength = DefaultValue.INT;
    private static int maxBranchColumnLength = DefaultValue.INT;

    public static void show(Project project, CoverageAll coverage,
                           AndroidCoverageCheckExtension extension) {
        setMaxLength();

        String message = coverage.getReportPath() + "\n";
        message += getBar();
        message += getTitle();
        message += getBar();

        coverage.getSourcefileList().each() {
            if (it.getIsExclude()) return;
            String fileName = getShorteningName(it.getFileName());
            if (fileName ==~ "") return;
            message += getLine(it, extension, " " + fileName + " ");
        };

        message += getBar();
        message += getLine(coverage, extension, ALL_FILES);
        message += getBar();

        project.logger.lifecycle(message);
    }

    private static void setMaxLength() {
        maxFileColumnLength = FILE.length();
        maxInstructionColumnLength = BRANCH.length();
        maxBranchColumnLength = BRANCH.length();
    }

    private static String padBeforeSpace(String target, int size) {
        if (target.length() >= size) return target;
        int difference = size - target.length();
        return " " * difference + target;
    }

    private static String padAfterSpace(String target, int size) {
        if (target.length() >= size) return target;
        int difference = size - target.length();
        return target + " " * difference;
    }

    private static String trimFileName(String target, int size) {
        if (target.length() <= size) return target;
        int difference = target.length() - size;
        // +1はspace
        target = target.substring(difference + TRIM_SIZE + 1, target.length());
        return " " + "~" * TRIM_SIZE + target;
    }

    private static String getBar() {
        return "-" * maxFileColumnLength +
                "|" +
                "-" *
                maxInstructionColumnLength +
                "|" +
                "-" *
                maxBranchColumnLength +
                "|" +
                "\n";
    }

    private static String getTitle() {
        return FILE + "|" + INSTRUCTION + "|" + BRANCH + "|" + "\n";
    }

    private static String getLine(Coverage coverage,
                                  AndroidCoverageCheckExtension extension, String fileName) {

        float instructionRate = ErrorValue.FLOAT;
        float branchRate = ErrorValue.FLOAT;

        coverage.getCounterList().each {
            if (it.getType() == CoverageAll.INSTRUCTION) {
                instructionRate = it.getRate();
            } else if (it.getType() == CoverageAll.BRANCH) {
                branchRate = it.getRate();
            }
        }

        Color instructionColor = getColorType(instructionRate, extension.instruction);
        Color branchColor = getColorType(branchRate, extension.branch);
        Color fileColor;
        if (instructionColor == Color.RED || branchColor == Color.RED) {
            fileColor = Color.RED;
        } else if (instructionColor == Color.YELLOW || branchColor == Color.YELLOW) {
            fileColor = Color.YELLOW;
        } else {
            fileColor = Color.GREEN;
        }

        NumberFormat format = NumberFormat.getInstance();
        format.setMaximumFractionDigits(2);

        String file = setColor(padAfterSpace(
                trimFileName(fileName,
                        maxFileColumnLength), maxFileColumnLength), fileColor);

        String instruction;
        if (Float.compare(instructionRate, ErrorValue.FLOAT) == 0) {
            instruction = setColor(padBeforeSpace(IGNORE_RATE, maxInstructionColumnLength), instructionColor);
        } else {
            instruction = setColor(padBeforeSpace(format.format(instructionRate), maxInstructionColumnLength), instructionColor);
        }

        String branch;
        if (Float.compare(branchRate, ErrorValue.FLOAT) == 0) {
            branch = setColor(padBeforeSpace(IGNORE_RATE, maxBranchColumnLength), branchColor);
        } else {
            branch = setColor(padBeforeSpace(format.format(branchRate), maxBranchColumnLength), branchColor);
        }

        return file + "|" + instruction + "|" + branch + "|" + "\n";

    }

    private static Color getColorType(float rate, int threshold) {
        if (Float.compare(rate, ErrorValue.FLOAT) == 0 || threshold < 0 || Float.compare(rate, threshold) >= 0) return Color.GREEN;
        if (Float.compare((float) (rate / threshold), 0.5F) > 0) return Color.YELLOW;
        return Color.RED;
    }

    private static String setColor(String message, Color color) {
        if (color == Color.RED) {
            return ColorLogMessage.boldRed(message);
        } else if (color == Color.YELLOW) {
            return ColorLogMessage.boldYellow(message);
        }
        return ColorLogMessage.boldGreen(message);
    }

    private static String getShorteningName(String name) {
        List<String> nameList = new ArrayList<>();
        name.split("/").each {
            nameList.add(it);
        }
        String result = "";
        nameList.eachWithIndex { val,i ->
            if (i != (nameList.size() - 1)) {
                result += val[0] + ".";
            } else {
                result += val;
            }
        }
        return result;
    }
}
