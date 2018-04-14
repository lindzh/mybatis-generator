package com.lindzh.mybatis.generator.loader;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;

public class SimpleCipher {

	private SecretKey key = null;

	private String source;
	
	private static Base64 base64 = new Base64();

	public SimpleCipher() {
		this(generateKey());
	}

	public SimpleCipher(String in) {
		this.source = in;
		try {
			char[] data = in.toCharArray();
			final int len = data.length;
			if ((len & 0x01) != 0) {
				throw new DecoderException("Odd number of characters.");
			}
			final byte[] bin = new byte[len >> 1];
			for (int i = 0, j = 0; j < len; i++) {
				int f = toDigit(data[j], j) << 4;
				j++;
				f = f | toDigit(data[j], j);
				j++;
				bin[i] = (byte) (f & 0xFF);
			}
			DESedeKeySpec keyspec = new DESedeKeySpec(bin);
			SecretKeyFactory keyfactory = SecretKeyFactory
					.getInstance("DESede");
			key = keyfactory.generateSecret(keyspec);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getKey() {
		return source;
	}

	public static String generateKey() {
		try {
			KeyGenerator keygen = KeyGenerator.getInstance("DESede");
			SecretKey key = keygen.generateKey();
			SecretKeyFactory keyfactory = SecretKeyFactory
					.getInstance("DESede");
			DESedeKeySpec keyspec = (DESedeKeySpec) keyfactory.getKeySpec(key,
					DESedeKeySpec.class);
			byte[] rawkey = keyspec.getKey();
			final char[] toDigits = { '0', '1', '2', '3', '4', '5', '6', '7',
					'8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
			final int l = rawkey.length;
			final char[] chs = new char[l << 1];
			// two characters form the hex value.
			for (int i = 0, j = 0; i < l; i++) {
				chs[j++] = toDigits[(0xF0 & rawkey[i]) >>> 4];
				chs[j++] = toDigits[0x0F & rawkey[i]];
			}
			return new String(chs);
		} catch (Exception e) {
			return null;
		}
	}

	private int toDigit(final char ch, final int index) throws DecoderException {
		final int digit = Character.digit(ch, 16);
		if (digit == -1) {
			throw new DecoderException("Illegal hexadecimal character " + ch
					+ " at index " + index);
		}
		return digit;
	}

	public String encodeString(String in) {
		if (in == null) {
			return null;
		} else {
			try {
				Cipher cipher = Cipher.getInstance("DESede");
				cipher.init(Cipher.ENCRYPT_MODE, key);
				byte[] ein = cipher.doFinal(in.getBytes());
				if (ein == null)
					return null;
				final char[] toDigits = { '0', '1', '2', '3', '4', '5', '6',
						'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
				final int l = ein.length;
				final char[] chs = new char[l << 1];
				// two characters form the hex value.
				for (int i = 0, j = 0; i < l; i++) {
					chs[j++] = toDigits[(0xF0 & ein[i]) >>> 4];
					chs[j++] = toDigits[0x0F & ein[i]];
				}
				return new String(chs);
			} catch (Exception e) {
				// e.printStackTrace();
			}
			return null;
		}
	}

	public String decodeString(String in) {
		if (in == null) {
			return null;
		}
		try {
			char[] data = in.toCharArray();
			final int len = data.length;
			if ((len & 0x01) != 0) {
				throw new DecoderException("Odd number of characters.");
			}
			final byte[] bin = new byte[len >> 1];
			for (int i = 0, j = 0; j < len; i++) {
				int f = toDigit(data[j], j) << 4;
				j++;
				f = f | toDigit(data[j], j);
				j++;
				bin[i] = (byte) (f & 0xFF);
			}
			Cipher cipher = Cipher.getInstance("DESede");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] bts = cipher.doFinal(bin);
			if (bts == null) {
				return null;
			} else {
				return new String(bts);
			}
		} catch (Exception e) {
		}
		return null;
	}

	public byte[] encodeBytes(byte[] bytes) {
		try {
			Cipher cipher = Cipher.getInstance("DESede");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] content =  cipher.doFinal(bytes);
			return base64.encode(content);
		} catch (Exception e) {
			return null;
		}
	}

	public byte[] decodeBytes(byte[] bytes) {
		try {
			byte[] content = base64.decode(bytes);
			Cipher cipher = Cipher.getInstance("DESede");
			cipher.init(Cipher.DECRYPT_MODE, key);
			return cipher.doFinal(content);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static void main(String[] args) {
		SimpleCipher cipher = new SimpleCipher("cf89fecbdfafdcff04d6b9f7835bca2661c8d02968aeb9c9");
		System.out.println(cipher.getKey());
		byte[] encodeBytes = cipher.encodeBytes("123456".getBytes());
		System.out.println(new String(encodeBytes));
		System.out.println(new String(cipher.decodeBytes(encodeBytes)));
	}
}
