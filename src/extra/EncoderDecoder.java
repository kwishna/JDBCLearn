package extra;

import java.util.Base64;

public class EncoderDecoder {

	public static void main(String[] args) {
		
		Base64.Encoder encrypter = Base64.getEncoder(); // Encoder Object
		
		byte[] b1 = "My Name Is Krishna".getBytes();
		byte[] b1Enc = encrypter.encode(b1);
		System.out.println("Encrypted - "+encrypter.encode(b1));
		
		Base64.Decoder decrypter = Base64.getDecoder(); // Decoder Object
		
		byte[] b1Dec = decrypter.decode(b1Enc);
		System.out.println("Decrypted From Encoder - "+new String(b1Dec));
		
		//**********************************************//
		
		String enc = encrypter.encodeToString("vishnu".getBytes());
		System.out.println(":: Encypted String - "+enc);
		
		String dec = new String(decrypter.decode(enc));
		System.out.println(":: Decypted String - "+dec);
		
		//**********************************************//
		
		Base64.Encoder encrr = Base64.getEncoder(); // URL Encoder Object
		String urlEnc = encrr.encodeToString("ara".getBytes());
		System.out.println(":: URL Encoded - "+urlEnc);
		
		Base64.Decoder decrr = Base64.getUrlDecoder(); // URL Decoder Object
		String urlDec = new String(decrr.decode(urlEnc));
		System.out.println(":: URL Decoded - "+urlDec);
	}
}
