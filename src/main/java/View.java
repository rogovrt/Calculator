package main.java;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class View extends BorderPane {
    private TextArea textField;
    private GridPane buttons;
    private Controller controller;

    public View() {
        textField = new TextArea();
        textField.setEditable(false);
        textField.setMinHeight(80);
        textField.setMaxHeight(80);
        textField.setMinWidth(310);
        textField.setMaxWidth(310);
        textField.setFont(new Font(14));

        buttons = new GridPane();
        initButtons();

        setTop(textField);
        setCenter(buttons);
    }

    private void initButtons() {
        buttons.setPadding(new Insets(10, 10, 10, 10));
        buttons.setVgap(10);
        buttons.setHgap(10);

        buttons.addRow(0, createFigureButton("7"),
                                    createFigureButton("8"),
                                    createFigureButton("9"),
                                    createChangeSignButton(),
                                    createDeleteOneSymbolButton());
        buttons.addRow(1, createFigureButton("4"),
                                    createFigureButton("5"),
                                    createFigureButton("6"),
                                    createOperationButton("×"),
                                    createOperationButton("÷"));
        buttons.addRow(2, createFigureButton("1"),
                                    createFigureButton("2"),
                                    createFigureButton("3"),
                                    createOperationButton("-"));
        buttons.addRow(3, createClearButton(),
                                    createFigureButton("0"),
                                    createDotButton(),
                                    createOperationButton("+"));
        buttons.add(createEqualButton(), 4, 2, 1, 2);
        buttons.getChildren();
    }

    private Button createFigureButton(String text) {
        Button b = new Button(text);
        b.setTextFill(Color.BLUE);
        b.setMinWidth(50);
        b.setMaxWidth(50);
        b.setMinHeight(70);
        b.setMaxHeight(70);
        b.setOnAction(actionEvent -> {
            textField.setText(textField.getText() + text);
        });
        return b;
    }

    private Button createDotButton() {
        Button b = new Button(".");
        b.setTextFill(Color.BLUE);
        b.setMinWidth(50);
        b.setMaxWidth(50);
        b.setMinHeight(70);
        b.setMaxHeight(70);
        b.setOnAction(actionEvent -> {
            if (textField.getText().isEmpty())
                textField.setText("0.");
            else {
                int i = 1;
                char c = textField.getText().charAt(textField.getText().length() - i);
                while (!(c == '-' || c == '+' || c == '×' || c == '÷' || c == '.') && (textField.getText().length() - i - 1 > -1)) {
                    ++i;
                    c = textField.getText().charAt(textField.getText().length() - i);
                }
                if (c != '.') {
                    if (textField.getText().lastIndexOf("\n") == -1)
                        textField.setText(textField.getText() + ".");
                    else {
                        char symbolBeforeNumber = textField.getText().charAt(textField.getText().lastIndexOf("\n") - 1);
                        char previousSymbol = textField.getText().charAt(textField.getText().length() - 1);
                        System.out.println(previousSymbol);
                        if ((symbolBeforeNumber == '-' || symbolBeforeNumber == '+' || symbolBeforeNumber == '×' || symbolBeforeNumber == '÷') && !Character.isDigit(previousSymbol)) {
                            textField.setText(textField.getText() + "0.");
                        } else
                            textField.setText(textField.getText() + ".");
                    }
                }
            }
        });
        return b;
    }

    private Button createOperationButton(String text) {
        Button b = new Button(text);
        b.setTextFill(Color.GREY);
        b.setMinWidth(50);
        b.setMaxWidth(50);
        b.setMinHeight(70);
        b.setMaxHeight(70);
        b.setOnAction(actionEvent -> {
                actionOnOperationsAndEqualButton(text);
        });
        return b;
    }

    private Button createEqualButton() {
        Button equalButton = new Button("=");
        equalButton.setTextFill(Color.RED);
        equalButton.setMinWidth(50);
        equalButton.setMaxWidth(50);
        equalButton.setMinHeight(150);
        equalButton.setMaxHeight(150);
        equalButton.setOnAction(actionEvent -> {
            actionOnOperationsAndEqualButton("=");
            if (!textField.getText().isEmpty()) {
                controller = new Controller(textField.getText().substring(0, textField.getText().length() - 1));
                textField.setText(controller.getResult());
            }
        });
        return equalButton;
    }

    private Button createChangeSignButton() {
        Button b = new Button("+/-");
        b.setTextFill(Color.GREY);
        b.setMinWidth(50);
        b.setMaxWidth(50);
        b.setMinHeight(70);
        b.setMaxHeight(70);
        b.setOnAction(actionEvent -> {
            String currentText = textField.getText();
            int i = currentText.lastIndexOf("\n");
            String updatedText;
            if (i == -1) {
                if (currentText.isEmpty())
                    updatedText = "-";
                else {
                    if (currentText.charAt(0) == '-')
                        updatedText = currentText.substring(1);
                    else
                        updatedText = "-" + currentText;
                }
            }
            else {
                if (currentText.length() == i + 1)
                    updatedText = currentText + "-";
                else {
                    if (currentText.charAt(i + 1) == '-')
                        updatedText = currentText.substring(0, i + 1) + currentText.substring(i + 2);
                    else
                        updatedText = currentText.substring(0, i + 1) + "-" + currentText.substring(i + 1);
                }
            }
            textField.setText(updatedText);
        });
        return b;
    }

    private Button createDeleteOneSymbolButton() {
        Button b = new Button("←");
        b.setTextFill(Color.GREY);
        b.setMinWidth(50);
        b.setMaxWidth(50);
        b.setMinHeight(70);
        b.setMaxHeight(70);
        b.setOnAction(actionEvent -> {
            String updatedText;
            if (textField.getText().isEmpty())
                updatedText = textField.getText();
            else
                updatedText = textField.getText().substring(0, textField.getText().length() - 1);
            textField.setText(updatedText);
        });
        return b;
    }

    private Button createClearButton() {
        Button b = new Button("C");
        b.setTextFill(Color.RED);
        b.setMinWidth(50);
        b.setMaxWidth(50);
        b.setMinHeight(70);
        b.setMaxHeight(70);
        b.setOnAction(actionEvent -> {
            textField.clear();
        });
        return b;
    }

    private void actionOnOperationsAndEqualButton(String text) {
        String currentText = textField.getText();
        if (currentText.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("You can't start expression with operation symbol!");
            alert.showAndWait();
        } else {
            if (currentText.length() - 2 >= 0) {
                char previousSymbol = currentText.charAt(currentText.length() - 2);
                if (previousSymbol == '-' || previousSymbol == '+' || previousSymbol == '×' || previousSymbol == '÷')
                    currentText = currentText.substring(0, currentText.length() - 2);
            }
            char previousSymbol = textField.getText().charAt(currentText.length() - 1);
            System.out.println(previousSymbol);
            if (previousSymbol == '.') {
                currentText = currentText.substring(0, currentText.length() - 1);
                textField.setText(currentText);
            }
            String updatedText;
            int i = currentText.lastIndexOf("\n");
            if (i == -1)
                updatedText = currentText + text + "\n";
            else {
                if (textField.getText().charAt(i + 1) != '-')
                    updatedText = textField.getText().substring(0, i) + textField.getText().substring(i + 1) + text + "\n";
                else
                    updatedText = textField.getText().substring(0, i) + "(" + textField.getText().substring(i + 1) + ")" + text + "\n";
            }
            textField.setText(updatedText);
        }
    }
}
