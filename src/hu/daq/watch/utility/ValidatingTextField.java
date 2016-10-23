/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.daq.watch.utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.UnaryOperator;
import javafx.scene.control.IndexRange;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.Region;

/**
 *
 * @author DAQ
 */
public class ValidatingTextField extends TextField {

    private static List<Character> reservedchars = new ArrayList(Arrays.asList(":", "."));
    String pattern = "";

    public ValidatingTextField() {
    }

    public ValidatingTextField(String pattern) {
        this(pattern, null);

    }

    public ValidatingTextField(String pattern, String text) {
        super(text);
        this.pattern = pattern;
        this.installFormatter();
    }

    public void setValidationPattern(String pattern) {
        this.pattern = pattern;
        this.installFormatter();
        this.setMinWidth(Region.USE_PREF_SIZE);
    }

    private void installFormatter() {
        UnaryOperator<TextFormatter.Change> filter;
        filter = (TextFormatter.Change change) -> {
            System.out.println(change);

            if (change.isContentChange()) {
                if (change.isAdded() && !change.getControlText().isEmpty()) {
                    change.setRange(change.getControlCaretPosition(), change.getControlCaretPosition() + change.getText().length());
                    System.out.println("new range:"+ change);
                    //If the text is too long -> no change
                    if (change.getControlNewText().length() > this.pattern.length()) {
                        System.out.println("Pattern length");
                        return null;
                    }

                    if (!(Character.isDigit(change.getControlNewText().charAt(change.getControlCaretPosition())) && this.pattern.charAt(change.getControlCaretPosition()) == new Character('0'))) {
                        System.out.println("Check digit:"+change.getControlNewText().charAt(change.getControlCaretPosition())+":"+this.pattern.charAt(change.getControlCaretPosition()));
                        return null;
                    }
                    /*
                    if (!(reservedchars.contains(change.getControlNewText().charAt(change.getControlCaretPosition())) && change.getControlNewText().charAt(change.getControlCaretPosition()) == this.pattern.charAt(change.getControlCaretPosition()))) {
                        System.out.println("reserved");
                        return null;
                    }
                            */
                    //this.setText(newtext);
                    //this.positionCaret(change.getCaretPosition());

                }
                if (change.isDeleted()){
                    return null;
                }
            } else {
                if (!change.getControlText().isEmpty() && change.getCaretPosition()<=change.getControlText().length()-1) {
                    if (reservedchars.contains(change.getControlText().charAt(change.getCaretPosition()))) {
                        if (change.getCaretPosition() > change.getControlCaretPosition()) {
                            ((TextInputControl) change.getControl()).positionCaret(change.getCaretPosition() + 1);
                        } else if (change.getCaretPosition() < change.getControlCaretPosition()) {
                            ((TextInputControl) change.getControl()).positionCaret(change.getCaretPosition() - 1);
                        }

                    }
                }

            }
            return change;
        };
        this.setTextFormatter(new TextFormatter<String>(filter));
    }

    private String replaceString(String orig, String replace, int start) {
        if (orig == null) {
            return null;
        }
        if (orig.length() == 0) {
            return replace;
        }
        String replaced;
        replaced = orig.substring(0, start) + replace + orig.substring(start + replace.length());
        return replaced;
    }

}
