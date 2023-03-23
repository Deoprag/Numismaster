package com.numismaster.util;

import java.text.ParseException;
import javafx.scene.control.TextField;
import javax.swing.text.MaskFormatter;

public class MaskTextField {

    private final MaskFormatter mf;
    private TextField tf;
    private String ValidCharacters;
    private String mask;

    public MaskTextField() {
        mf = new MaskFormatter();
    }

    public void formatter(TextField tf, String ValidCharacters, String mask) {
        try {
            mf.setMask(mask);
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
        }

        mf.setValidCharacters(ValidCharacters);
        mf.setValueContainsLiteralCharacters(false);
        String text = tf.getText().replaceAll("[\\W]", "");
        
        boolean repeat = true;
        while (repeat) {

            char lastCharacter;

            if (text.equals("")) {
                break;
            } else {
                lastCharacter = text.charAt(text.length()); // - 1
            }

            try {
                text = mf.valueToString(text);
                repeat = false;
            } catch (ParseException ex) {
                text = text.replace(lastCharacter + "", "");
                repeat = true;
            }

        }

        tf.setText(text);

        if (!text.equals("")) {
            for (int i = 0; tf.getText().charAt(i) != ' ' && i < tf.getLength(); i++) {
                tf.forward();
            }
        }
    }

    public void formatter(){
        formatter(this.tf, this.ValidCharacters, this.mask);
    }

    /**
     * @return the tf
     */
    public TextField getTf() {
        return tf;
    }

    /**
     * @param tf the tf to set
     */
    public void setTf(TextField tf) {
        this.tf = tf;
    }

    /**
     * @return the ValidCharacters
     */
    public String getValidCharacters() {
        return ValidCharacters;
    }

    /**
     * @param ValidCharacters the ValidCharacters to set
     */
    public void setValidCharacters(String ValidCharacters) {
        this.ValidCharacters = ValidCharacters;
    }
    
    public String getMask() {
        return mask;
    }

    /**
     * @param mask the mask to set
     */
    public void setMask(String mask) {
        this.mask = mask;
    }
}
