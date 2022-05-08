import org.apache.commons.codec.binary.Base64;

public class Test {

	public static void main(String[] args) {

		byte[] encodedBytes = Base64.encodeBase64("Zebra@123".getBytes());
		System.out.println("encodedBytes " + new String(encodedBytes));

		byte[] decodedBytes = Base64.decodeBase64(encodedBytes);
		System.out.println("decodedBytes " + decodedBytes.toString());
	}

}
