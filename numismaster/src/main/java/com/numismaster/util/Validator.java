package com.numismaster.util;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import javafx.scene.control.Label;

public class Validator {
    public static boolean isCpf(String cpf){
        if (cpf.equals("00000000000") || cpf.equals("11111111111") || cpf.equals("22222222222")
				|| cpf.equals("33333333333") || cpf.equals("44444444444") || cpf.equals("55555555555")
				|| cpf.equals("66666666666") || cpf.equals("77777777777") || cpf.equals("88888888888")
				|| cpf.equals("99999999999") || cpf.length() != 11)
			return (false);

		char dig10, dig11;
		int sm, i, r, num, peso;

		try {
			// PRIMEIRO DV
			sm = 0;
			peso = 10;
			for (i = 0; i < 9; i++) {
				num = (int) (cpf.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}
			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig10 = '0';
			else
				dig10 = (char) (r + 48);
			// SEGUNDO DV
			sm = 0;
			peso = 11;
			for (i = 0; i < 10; i++) {
				num = (int) (cpf.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}
			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig11 = '0';
			else
				dig11 = (char) (r + 48);

			if ((dig10 == cpf.charAt(9)) && (dig11 == cpf.charAt(10)))
				return (true);
			else
				return (false);
		} catch (Exception e) {
			return (false);
		}
    }

	public static boolean isEmail(String email){
		boolean result = true;
			try {
				InternetAddress emailAddr = new InternetAddress(email);
				emailAddr.validate();
			} catch (AddressException ex) {
				result = false;
			}
			return result;
	}

	public static boolean passwordRequirements(String password, String passwordConfirmation, Label label) {
		if (password.length() < 8 || password.length() > 32) {
			if(label != null) {
				label.setText("A senha é muito curta!");	    		
			}
			return false;
		}

		boolean hasLetters = false;
		boolean hasNumbers = false;
		boolean hasSpecialChars = false;

		for (int i = 0; i < password.length(); i++) {
			char c = password.charAt(i);
			if (Character.isLetter(c)) {
				hasLetters = true;
			} else if (Character.isDigit(c)) {
				hasNumbers = true;
			} else if (!Character.isWhitespace(c)) {
				hasSpecialChars = true;
			}

			if (hasLetters && hasNumbers && hasSpecialChars) {
				if(label != null) {
					label.setText("");	        		
				}
			} else if(label != null) {
				label.setText("Verifique se sua senha possui: letras, números e caracteres especiais.");
			}
		}

		if(!password.equals(passwordConfirmation)){
			if(label != null) {
				label.setText("As senhas não coincidem!");
			}
			return false;
		}

		if(label != null) {
			label.setText("");	        		
		}
		return true;
	}
}
