package com.jdmv.springboot.backend.apirest.auth;

public class JwtConfig {
	public static final String SECRET_KEY = "lalatina.dustiness.ford";
	
	public static final String RSA_PRIVATE = "-----BEGIN RSA PRIVATE KEY-----\r\n"
			+ "MIIEpAIBAAKCAQEA2quQwIZogP33ZBHSnS9UHd2AvzGGC6wwZBF2zoDQTUjFoiFL\r\n"
			+ "K8uLcXZAwtx2UGvGXVOh9ZRj6T4KOeevZeXlum5RZAX2IIds9uKcWWQiHUpwUKU0\r\n"
			+ "wR4wCqjechuEmCgZv1jXTdga6CrRh+2Wh5XhynTYxp2d3P4u0KDbXsf2WYRpoMwe\r\n"
			+ "FmTWQizHudkPgx3X9ryeQxFTAKoRetzdKd7PGobG3aM453tvdptw9GZTIxr6XFoA\r\n"
			+ "b8ZU6rjNxNBBcebdgPp/c0UF5Xx8Ob4gRfZ7Qm8W6L5LLyBdeKYKp/Ue7YaMKpGu\r\n"
			+ "tkgpyxfDOVxCg5fmTnkp1TA4h7wJcrwu3qyQjQIDAQABAoIBAQCqU4jk7czo7OmO\r\n"
			+ "YPOUzBDRUn++bWzkGrN2vsO0BlI6YLzQlO5gpqNvNZUsM11VNx3EBCjbiUJpo5GD\r\n"
			+ "mRveWzyeAuAS8THOFK6jf9AZMQ4BksH5jzWQV1tEclUSvKyDeHjVuvW6BOKwhOnA\r\n"
			+ "3yepZO4DLRDNZGhagwMkLqexJECXaEOyEOVv2Yxth5Rdc6WOejvkRy0YDJfCUTqJ\r\n"
			+ "hM2Dq3W4GuVHOWK5fthwVjo/FPumFhVVQcRfTQvQ+38YTrOVt57CobwB/IMM96VB\r\n"
			+ "WfXcuuglrvMeotJKV7HbmjGGdxFvS3CGna34ZnZk3f9ls7Gztje1jyDp10n6Ptbj\r\n"
			+ "LjTZhGVhAoGBAPS0i2aux1XsQlYk+LLKrNnJgpzC9UWJn2GWb8FrZvaQl1RGjC5H\r\n"
			+ "fq1JQ562kvtHYdZu6FFn8AMjzwSlWkFCSE7f4kjLcE84voOa81PaHNMyU5ZwW3lz\r\n"
			+ "lIyCZFJWwq/dhYYNQTMBuHsTZKmkIG+j1qEPYiSdsJAKNG3VLQsbuodjAoGBAOTD\r\n"
			+ "Y3ZJpr9TxPaC0qBZwud6Cg6AvjcIq0y0Pxi7kx6wclRKJfS+3wVCpyn0kmqy6dPp\r\n"
			+ "zDlvqIclMlISCDcghLz0nr1bXH3RjtHwwmrl+ArVTB9GFkRyhPmWB+H1HuV2Usma\r\n"
			+ "xpxpeedjoMjDt1p6xoyPLGlZZfZ5ZLLVlHmysONPAoGABMOvIySsBORAtmSp4vFH\r\n"
			+ "REZEIME6/Uucqs2/B0kMDCjsiVuGRzBlKwKOKnzrh7DwGri2FT+s0KLV2QVPKI4d\r\n"
			+ "0ktRoJE1cpVsQCu4KR3KCeKk9fziCw9RCRtQ0LmXuMzbWKzRZCjYaq0J/LORXhsW\r\n"
			+ "hrRB+OyJD4TjWHAaHGlBzHkCgYAGUIJhVH0e0OAh8dCAjpm7q/m1FsapXDrmPh9z\r\n"
			+ "vlG8SiCNxRIxJEehrIpStSaJfXDI4a46c6OSP2uZKlhBtUsUsCflkC0IaA1qLBwh\r\n"
			+ "uUFUgzi7sEKTFxomjYagLRTyPobge745uQudYHmisQxRmgv6+0S9j7Mpz4YBDbYw\r\n"
			+ "ecc6LwKBgQCxzd2yGna6SJiZf9udskyPxk0CMRSLG1Na+EghUSGqXspZmdFeIMiK\r\n"
			+ "8Chg36C+T8iCSzXlMMLXRe2tCSlhDaQwG7digZoApr3qchjN3q1Z0SVb38U6Ch7H\r\n"
			+ "2S8tNyE+otgyhueuLUSjeD1aAGzC9lgLKxVxesT42NVCbSseuZD73Q==\r\n"
			+ "-----END RSA PRIVATE KEY-----";
	
	public static final String RSA_PUBLIC = "-----BEGIN PUBLIC KEY-----\r\n"
			+ "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA2quQwIZogP33ZBHSnS9U\r\n"
			+ "Hd2AvzGGC6wwZBF2zoDQTUjFoiFLK8uLcXZAwtx2UGvGXVOh9ZRj6T4KOeevZeXl\r\n"
			+ "um5RZAX2IIds9uKcWWQiHUpwUKU0wR4wCqjechuEmCgZv1jXTdga6CrRh+2Wh5Xh\r\n"
			+ "ynTYxp2d3P4u0KDbXsf2WYRpoMweFmTWQizHudkPgx3X9ryeQxFTAKoRetzdKd7P\r\n"
			+ "GobG3aM453tvdptw9GZTIxr6XFoAb8ZU6rjNxNBBcebdgPp/c0UF5Xx8Ob4gRfZ7\r\n"
			+ "Qm8W6L5LLyBdeKYKp/Ue7YaMKpGutkgpyxfDOVxCg5fmTnkp1TA4h7wJcrwu3qyQ\r\n"
			+ "jQIDAQAB\r\n"
			+ "-----END PUBLIC KEY-----";

}
