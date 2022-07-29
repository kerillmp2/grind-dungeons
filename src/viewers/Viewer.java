package viewers;

import java.util.List;

import utils.Constants;

public class Viewer {
    private static final int ROW_SIZE = Constants.WINDOW_SIZE.value;
    private static final int OFFSET = Constants.BASIC_OFFSET.value;

    protected static String emptyLine() {
        return line(" ", "|", "|", true);
    }

    protected static String emptyLine(int length) {
        return line(" ", "|", "|", length, true);
    }

    protected static String lineWithAngles() {
        return line("-", "+", "+", true);
    }

    protected static String lineWithAngles(int length) {
        return line("-", "+", "+", length, true);
    }

    protected static String line() {
        return line("-", "-", "-", true);
    }

    protected static String line(int length) {
        return line("-", "-", "-", length, true);
    }

    protected static String line(String sym, String leftAngle, String rightAngle, boolean endLine) {
        return leftAngle + sym.repeat(ROW_SIZE - 2) + rightAngle + (endLine ? "\n" : "");
    }

    protected static String line(String sym, String leftAngle, String rightAngle, int length, boolean endLine) {
        return leftAngle + sym.repeat(length - 2) + rightAngle + (endLine ? "\n" : "");
    }

    protected static class Window {
        private StringBuilder view = new StringBuilder();
        private int length = ROW_SIZE;
        private int offset = OFFSET;
        private int currentHeight = 0;

        public Window() {
        }

        public Window(int length, int offset) {
            this.length = length;
            this.offset = offset;
        }

        public Window lineWithAngles() {
            view.append(Viewer.lineWithAngles(length));
            currentHeight++;
            return this;
        }

        public Window emptyLine() {
            view.append(Viewer.emptyLine(length));
            currentHeight++;
            return this;
        }

        public Window line() {
            view.append(Viewer.line(length));
            currentHeight++;
            return this;
        }

        public Window centeredText(String text) {
            int textLen = text.length();
            StringBuilder row = new StringBuilder();
            text = clearString(text);
            row.append("|").append(" ".repeat(length / 2 - textLen / 2));
            row.append(text);
            row.append(" ".repeat(length - row.length() - 1)).append("|\n");
            add(row);
            currentHeight++;
            return this;
        }

        public Window leftMiddleRight(String left, String middle, String right) {
            StringBuilder row = new StringBuilder();
            left = clearString(left);
            middle = clearString(middle);
            right = clearString(right);
            row.append("|").append(" ".repeat(offset));
            row.append(left);
            row.append(" ".repeat(length / 2 - middle.length() / 2 - row.length() + 1));
            row.append(middle);
            row.append(" ".repeat(length - row.length() - right.length() - offset - 1));
            row.append(right).append(" ".repeat(offset)).append("|\n");
            add(row);
            currentHeight++;
            return this;
        }

        public Window right(String right) {
            StringBuilder row = new StringBuilder();
            right = clearString(right);
            row.append("|").append(" ".repeat(length - right.length() - offset - 2)).append(right).append(" ".repeat(offset)).append("|\n");
            add(row);
            currentHeight++;
            return this;
        }

        public Window line(String line) {
            line = clearString(line);
            if (line.length() < length - 5) {
                return simpleLine(line);
            }

            String[] nameParts = line.split(" ");
            StringBuilder row = new StringBuilder();
            Window fullLine = new Window(length, offset);

            int curLength = 0;
            for (int i = 0; i < nameParts.length; i++) {
                String currentPart = nameParts[i];
                if (currentPart.length() > length) {
                    continue;
                }
                if (curLength > 0) {
                    if (currentPart.length() + curLength < length - 2 - offset * 2) {
                        row.append(" ").append(currentPart);
                        curLength += currentPart.length() + 1;
                    } else {
                        fullLine.simpleLine(row.toString());
                        row = new StringBuilder();
                        curLength = 0;
                        i--;
                        this.currentHeight++;
                        continue;
                    }
                } else {
                    row.append(currentPart);
                    curLength += currentPart.length();
                    this.currentHeight++;
                }
            }
            fullLine.simpleLine(row.toString());
            add(fullLine.getView());
            return this;
        }

        public Window simpleLine(String line) {
            line = clearString(line);
            String[] lineParts = line.split("\\n");
            for(String s : lineParts) {
                StringBuilder row = new StringBuilder();
                row.append("|").append(" ".repeat(offset));
                row.append(s);
                row.append(" ".repeat(length - row.length() - 1)).append("|\n");
                add(row);
                currentHeight++;
            }
            return this;
        }

        public Window list(List<String> lines) {
            return list(lines, true, 0, true, true);
        }

        public Window list(List<String> lines, boolean enumerate, int from, boolean addEmptyLines, boolean hasBack) {
            return list(lines, enumerate, from, addEmptyLines, hasBack, false);
        }

        public Window list(List<String> lines, boolean enumerate, int from, boolean addEmptyLines, boolean hasBack, boolean centered) {
            int counter = from;
            if (hasBack) {
                if (centered) {
                    centeredText(String.format("%d. %s", counter, "Exit"));
                } else {
                    line(String.format("%d. %s", counter, "Exit"));
                }
                counter++;
                if (addEmptyLines) {
                    emptyLine();
                }
            }
            for (String line : lines) {
                if (enumerate) {
                    if (centered) {
                        centeredText(String.format("%d. %s", counter, line));
                    } else {
                        line(String.format("%d. %s", counter, line));
                    }
                    counter++;
                } else {
                    if (centered) {
                        centeredText(line);
                    } else {
                        line(line);
                    }
                }
                if (addEmptyLines) {
                    emptyLine();
                }
            }
            return this;
        }

        public Window add(Object s) {
            view.append(s);
            return this;
        }

        public Window addWindow(Window window) {
            currentHeight += window.getCurrentHeight();
            view.append(window.getView());
            return this;
        }

        public Window clear() {
            view = new StringBuilder();
            currentHeight = 0;
            return this;
        }

        public static String clearString(String s) {
            return s.replaceAll("\\$", "");
        }

        public String getView() {
            return view.toString();
        }

        public int getCurrentHeight() {
            return currentHeight;
        }
    }
}
