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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;
import java.util.Random;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

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

	public static String formatCpf(String oldCpf) {
		return String.format(oldCpf, oldCpf.substring(0, 2)+ "." +  oldCpf.substring(3, 5) + "." + oldCpf.substring(6, 8) + "-" +  oldCpf.substring(9, 10));
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

	public static Date localDateToDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	public static String localDateTimeFormatter(LocalDateTime localDateTime){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");
        return localDateTime.format(formatter);
	}

	public static String localDateFormatter(LocalDate localDate){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return localDate.format(formatter);
	}

	public static String generateCode() {
		String code = "";
		int i = 0;
		while (i < 4) {
			Random rand = new Random();
			code = code + rand.nextInt(9);
			i++;
		}
		return code;
	}

	public static Blob convertToBlob(FileInputStream imageFis) throws SerialException, SQLException {
		try {
			byte[] bytes = new byte[imageFis.available()];
			imageFis.read(bytes);
			Blob image = new SerialBlob(bytes);
			return image;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static FileInputStream convertFromBlob(Blob image) throws Exception {
		File tempFile = File.createTempFile("tempfile", null);
		try (InputStream inputStream = image.getBinaryStream();
				FileOutputStream outputStream = new FileOutputStream(tempFile)) {
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

	public static String mockEmail(String originalEmail) {
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

	public static void addTextListener(String regex, int size, TextField textField) {
		textField.textProperty().addListener((observable, oldValue, newValue) -> {
            int maxLenght = size;
			if(regex.length() > 0) {
				String filteredValue = newValue.replaceAll(regex, "");
				if(filteredValue.length() <= maxLenght){
					if (!newValue.equals(filteredValue)) {
						textField.setText(filteredValue);
					}
				} else {
					textField.setText(oldValue);
				}
			} else {
				if(newValue.length() <= maxLenght){
					textField.setText(newValue);
				} else {
					textField.setText(oldValue);
				}
			}
        });
	}
}
