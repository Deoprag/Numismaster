package com.numismaster.util;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.Random;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class Util {

	public static void openWebpage(String urlString) {
		try {
			Desktop.getDesktop().browse(new URL(urlString).toURI());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String capitalizeString(String input) {
		StringBuilder output = new StringBuilder();
		String[] words = input.split(" ");
		for (String word : words) {
			if (word.length() > 0) {
				output.append(Character.toUpperCase(word.charAt(0)));
				if (word.length() > 1) {
					output.append(word.substring(1));
				}
			}
			output.append(" ");
		}
		return output.toString().trim();
	}

	public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Erro ao gerar hash da senha: " + e.getMessage());
            return null;
        }
    }
    
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        String hashedPlainPassword = hashPassword(plainPassword);
        return hashedPlainPassword.equals(hashedPassword);
    }

	public static Date localDateToDate(LocalDate localDate){
		return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	public static String generateCode() {
		String code = "";
		int i = 0;
		while(i < 4) {
			Random rand = new Random();
			code = code + rand.nextInt(9);
			i++;
		}
		return code;
	}

	public static Blob convertToBlob(FileInputStream imageFis) throws SerialException, SQLException{
		try{
			byte[] bytes = new byte[imageFis.available()];
        	imageFis.read(bytes);
			Blob image = new SerialBlob(bytes);
			return image;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static FileInputStream convertFromBlob(Blob image) throws Exception{
		File tempFile = File.createTempFile("tempfile", null);
        try (InputStream inputStream = image.getBinaryStream(); FileOutputStream outputStream = new FileOutputStream(tempFile)) {
            byte[] buffer = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            return new FileInputStream(tempFile);
        } catch (Exception e) {
            tempFile.delete();
            throw e;
        }
    }

	public static String mockEmail(String originalEmail){
		char maskChar = '*';

		int atIndex = originalEmail.indexOf('@');

		if (atIndex != -1) {
			String username = originalEmail.substring(0, Math.min(originalEmail.length(), 5));
			String domain = originalEmail.substring(atIndex);

			String maskedUsername = username + repeatChar(maskChar, Math.max(0, atIndex - 5));
			String maskedEmail = maskedUsername + domain;

			return maskedEmail;
		}
		return null;
	}

	public static String repeatChar(char c, int n) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < n; i++) {
			sb.append(c);
		}
		return sb.toString();
	}

	public static void addTextLimiter(final TextField tf, final int maxLength) {
		tf.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
				if (tf.getText().length() > maxLength) {
					String s = tf.getText().substring(0, maxLength);
					tf.setText(s);
				}
			}
		});
	}
}
