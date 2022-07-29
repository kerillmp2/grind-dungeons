package viewers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import item.Item;
import item.ItemFactory;
import utils.Calculator;
import utils.Constants;
import utils.HasName;
import utils.Pair;

public class ConverterViewer extends Viewer {

    private static final int OFFSET = Constants.BASIC_OFFSET.value;

    public static String getConverterBasicView(String name, List<Pair<Item, Double>> values) {
        Window window = new Window();
        window.add(getHeader(name));
        List<String> valuesString = new ArrayList<>();
        window.add(getBody(ItemFactory.converterFromItem(), ItemFactory.converterToItem(), 0, 0));
        window.lineWithAngles();
        window.centeredText("Choose Item to convert from");
        for (Pair<Item, Double> value : values) {
            valuesString.add(value.first.getName() + " (value: " + value.second + ")");
        }
        window.list(valuesString);
        window.lineWithAngles();
        return window.getView();
    }

    public static String getConverterViewWithFromItem(String name, List<Pair<Item, Double>> values, Item fromItem) {
        Window window = new Window();
        window.add(getHeader(name));
        List<String> valuesString = new ArrayList<>();
        window.add(getBody(fromItem, ItemFactory.converterToItem(), 0, 0));
        window.centeredText("Choose Item to convert to");
        for (Pair<Item, Double> value : values) {
            valuesString.add(value.first.getName() + " (value: " + value.second + ")");
        }
        window.list(valuesString);
        window.lineWithAngles();
        return window.getView();
    }

    public static String getConverterViewWithBothItems(String name, List<? extends HasName> options, Item fromItem, int firstItemAmount, Item toItem, int secondItemAmount, double fromItemValue, double toItemValue) {
        Window window = new Window();
        window.add(getHeader(name));
        Pair<Integer, Integer> valuePair = Calculator.calculateConvertation(fromItemValue, toItemValue);
        window.add(getBody(fromItem, toItem, valuePair.first, valuePair.second));
        window.lineWithAngles();
        window.leftMiddleRight(fromItem.getName() + " X" + firstItemAmount, "", toItem.getName() + " X" + secondItemAmount);
        window.lineWithAngles();
        window.list(options.stream().map(HasName::getName).collect(Collectors.toList()));
        window.lineWithAngles();
        return window.getView();
    }

    private static String getBody(Item from, Item to, int firstAmount, int secondAmount) {
        List<String> fromView = Arrays.stream(ItemViewer.getItemView(from, firstAmount).split("\n")).collect(Collectors.toList());
        List<String> toView = Arrays.stream(ItemViewer.getItemView(to, secondAmount).split("\n")).collect(Collectors.toList());
        Window window = new Window();
        window.emptyLine();
        int size = Math.min(fromView.size(), toView.size());
        for (int i = 0; i < size; i++) {
            String currentString;
            if (i == size / 2) {
                currentString = fromView.get(i) + " ".repeat(OFFSET) + "-".repeat(OFFSET - 1) + ">" + " ".repeat(OFFSET) + toView.get(i);
            } else {
                currentString = fromView.get(i) + " ".repeat(OFFSET * 3) + toView.get(i);
            }
            window.centeredText(currentString);
        }
        window.emptyLine();
        return window.getView();
    }

    private static String getHeader(String converterName) {
        Window header = new Window();
        header.lineWithAngles();
        header.centeredText(converterName);
        header.lineWithAngles();
        return header.getView();
    }
}
