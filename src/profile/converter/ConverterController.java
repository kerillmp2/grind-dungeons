package profile.converter;

import java.util.ArrayList;
import java.util.List;

import item.Item;
import item.ItemFactory;
import profile.Profile;
import utils.Calculator;
import utils.ControllerState;
import utils.HasName;
import utils.MessageController;
import utils.Pair;
import utils.Selector;
import viewers.ConverterViewer;

public class ConverterController implements HasName {
    private final Profile profile;
    private final String name;
    private final List<Pair<Item, Double>> values;

    public ConverterController(Profile profile, String name, List<Pair<Item, Double>> values) {
        this.profile = profile;
        this.name = name;
        this.values = values;
    }

    public void converterProcessing() {
        ControllerState controllerState = ControllerState.CONVERTER_BASIC;
        Pair<Item, Double> selectedFrom = new Pair<>(ItemFactory.converterFromItem(), 1.0);
        Pair<Item, Double> selectedTo = new Pair<>(ItemFactory.converterToItem(), 1.0);
        while (controllerState != ControllerState.EXIT) {
            MessageController.clearConsole();

            switch (controllerState) {
                case CONVERTER_BASIC -> {
                    MessageController.print(ConverterViewer.getConverterBasicView(name, values));
                    int selectedNumber = Selector.itemSelect(values);
                    if (selectedNumber != 0) {
                        selectedFrom = values.get(selectedNumber - 1);
                        controllerState = ControllerState.CONVERTER_WITH_FROM;
                    } else {
                        controllerState = ControllerState.EXIT;
                    }
                }
                case CONVERTER_WITH_FROM -> {
                    MessageController.print(ConverterViewer.getConverterViewWithFromItem(name, values, selectedFrom.first));
                    int selectedNumber = Selector.itemSelect(values);
                    if (selectedNumber != 0) {
                        selectedTo = values.get(selectedNumber - 1);
                        controllerState = ControllerState.CONVERTER_WITH_FROM_AND_TO;
                    } else {
                        controllerState = ControllerState.CONVERTER_BASIC;
                    }
                }
                case CONVERTER_WITH_FROM_AND_TO -> {
                    List<ConvertOption> convertOptions = getOptionsFromConvertValues(selectedFrom, selectedTo);
                    MessageController.print(ConverterViewer.getConverterViewWithBothItems(
                            name,
                            convertOptions,
                            selectedFrom.first,
                            profile.getInventory().get(selectedFrom.first),
                            selectedTo.first,
                            profile.getInventory().get(selectedTo.first),
                            selectedFrom.second,
                            selectedTo.second
                    ));
                    int selectedNumber = Selector.itemSelect(convertOptions);
                    if (selectedNumber != 0) {
                        ConvertOption selectedOption = convertOptions.get(selectedNumber - 1);
                        selectedOption.resolve(profile);
                    } else {
                        controllerState = ControllerState.CONVERTER_WITH_FROM;
                    }
                }
                default -> controllerState = ControllerState.EXIT;
            }
        }
    }

    private static List<ConvertOption> getOptionsFromConvertValues(Pair<Item, Double> firstValue, Pair<Item, Double> secondValue) {
        Pair<Integer, Integer> convertation = Calculator.calculateConvertation(firstValue.second, secondValue.second);
        List<ConvertOption> convertOptions = new ArrayList<>();
        convertOptions.add(ConvertOption.forItemsAndAmounts(firstValue.first, secondValue.first, convertation.first, convertation.second));
        convertOptions.add(ConvertOption.forItemsAndAmounts(firstValue.first, secondValue.first, convertation.first * 5, convertation.second * 5));
        convertOptions.add(ConvertOption.forItemsAndAmounts(firstValue.first, secondValue.first, convertation.first * 10, convertation.second * 10));
        convertOptions.add(ConvertOption.forItemsAndAmounts(firstValue.first, secondValue.first, convertation.first * 50, convertation.second * 50));
        convertOptions.add(ConvertOption.forItemsAndAmounts(firstValue.first, secondValue.first, convertation.first * 100, convertation.second * 100));
        convertOptions.add(ConvertOption.forItemsAndAmounts(firstValue.first, secondValue.first, convertation.first * 500, convertation.second * 500));
        convertOptions.add(ConvertOption.forItemsAndAmounts(firstValue.first, secondValue.first, convertation.first * 1000, convertation.second * 1000));
        return convertOptions;
    }

    @Override
    public String getName() {
        return name;
    }
}
