package com.directa24.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.directa24.exception.Directa24Exception;

public class SignatureUtil {

   // TODO: agregar date y secretkey al payload
   public static String hmacSha256(String key, Object payload) throws Directa24Exception {
      byte[] hmacSha256 = null;
      try {
         Mac mac = Mac.getInstance("HmacSHA256");
         SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "HmacSHA256");
         mac.init(secretKeySpec);
         hmacSha256 = mac.doFinal(convertToBytes(payload));
      } catch (Exception e) {
         throw new Directa24Exception(e);
      }
      return Base64.getEncoder().encodeToString(hmacSha256);

   }

   private static byte[] convertToBytes(Object object) throws IOException {
      try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(bos)) {
         out.writeObject(object);
         return bos.toByteArray();
      }
   }

}
