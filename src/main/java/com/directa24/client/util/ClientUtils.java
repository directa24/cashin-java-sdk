package com.directa24.client.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Formatter;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.directa24.exception.Directa24Exception;

public class ClientUtils {

   public static final String D24_AUTHORIZATION_SCHEME = "D24 ";

   public static final String BEARER_AUTHORIZATION_SCHEME = "Bearer ";

   private static final String HMAC_SHA256 = "HmacSHA256";

   private static final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'";

   private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);

   public static String buildDepositSignature(String secretKey, String date, String depositKey, String payload) throws Directa24Exception {
      byte[] hmacSha256 = null;
      try {
         if (secretKey == null || depositKey == null) {
            throw new Directa24Exception("Missing credentials (maybe you're using a read only client?)");
         }
         Mac mac = Mac.getInstance(HMAC_SHA256);
         SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), HMAC_SHA256);
         mac.init(secretKeySpec);
         hmacSha256 = mac.doFinal(buildByteArray(date, depositKey, payload));
      } catch (IOException | NoSuchAlgorithmException | InvalidKeyException e) {
         throw new Directa24Exception(e);
      }
      return D24_AUTHORIZATION_SCHEME + toHexString(hmacSha256);
   }

   public static String buildReadOnlySignature(String readOnlyKey) throws Directa24Exception {
      if (readOnlyKey == null) {
         throw new Directa24Exception("Missing credentials (maybe you're using a deposit client?)");
      }
      return BEARER_AUTHORIZATION_SCHEME + readOnlyKey;
   }

   public static String now() {
      return LocalDateTime.now(ZoneOffset.UTC).format(DATE_TIME_FORMATTER);
   }

   private static byte[] buildByteArray(String date, String login, String payload) throws IOException {
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      bos.write(date.getBytes(StandardCharsets.UTF_8));
      bos.write(login.getBytes(StandardCharsets.UTF_8));
      if (payload != null) {
         bos.write(payload.getBytes(StandardCharsets.UTF_8));
      }
      return bos.toByteArray();
   }

   private static String toHexString(byte[] bytes) {
      Formatter formatter = new Formatter();
      for (byte b : bytes) {
         formatter.format("%02x", b);
      }
      return formatter.toString();
   }

}
