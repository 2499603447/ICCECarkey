/*
 * Copyright 2020. Huawei Technologies Co., Ltd. All rights reserved.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.huawei.walletkit.tool.security.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

/**
 * JWE utility class.
 *
 * @since 2020-03-02
 */
public class JweUtil {
    private static final String TAG = "JweUtil";

    /**
     * Generate a thin JWE. This JWEs contains only instanceId information. It's used to bind an existing instance
     * to a user. You should generate a thin JWE with an instanceId that has already been added to HMS wallet server.
     */
    public static String generateThinJWEToBindUser(String instanceId) {
        LogUtil.info(TAG, "generateThinJWEToBindUser with instanceId begin.\n");

        // This is the app ID registered on Huawei AGC website.
        String appId = ConfigUtil.instants().getValue("gw.appid");
        // Bind existing pass instances to a user. Construct a list of instance IDs to be bound.
        String instanceIdListStr = "{\"instanceIds\": [\""+ instanceId + "\"]}";
        JSONObject instanceIdList = JSONObject.parseObject(instanceIdListStr);
        instanceIdList.put("iss", appId);
        String payload = instanceIdList.toJSONString();

        // You generated a pair of RSA keys while applying for services on AGC. Use that private key here.
        String jweSignPrivateKey = ConfigUtil.instants().getValue("servicePrivateKey");

        // Generate a thin JWE.
        String jwe = JweUtil.generateJwe(jweSignPrivateKey, payload);
        LogUtil.info(TAG, "JWE String: " + jwe + "\n");
        String jweEncoded = "";
        try {
            // 1.If you access with website, such as Chrome Web browser, IE, Microsoft Edge; please encode jwe by URLEncoder.encode(jwe, "UTF-8");
            // 2.If you access with android mobile phone and interact with Huawei Wallet Server directly; please do not encode it;
            // 3.If you access with android mobile phone and pass jwe to Huawei Pay Application by android schema; please encode jwe by URLEncoder.encode(jwe, "UTF-8");
            jweEncoded = URLEncoder.encode(jwe, "UTF-8");
            LogUtil.info(TAG, "JWE String after encode: " + jweEncoded + "\n");
            LogUtil.info(TAG, "JWE link for browser: " + ConfigUtil.instants().getValue("walletWebsiteBaseUrl")
                    + "?content=" + jweEncoded);
        } catch (UnsupportedEncodingException e) {
            LogUtil.info(TAG, "Encode JWE String error.");
        }

        return jweEncoded;
    }

    /**
     * Generate a JSON Web Encryption (JWE).
     *
     * @param jwePrivateKey private key used to sign JWE content.
     * @param payload information plain text. It can be a list of instance IDs or an instance.
     * @return return a map containing a content string and a signature string.
     */
    public static String generateJwe(String jwePrivateKey, String payload) {
        // Part 1: JWE Header.
        // This header contains information about encryption and compression algorithms. It's a constant String.
        LogUtil.info(TAG, "Part 1:");
        Map<String, String> jweHeader = getHeader();
        String jweHeaderEncode = getEncodeHeader(jweHeader);
        LogUtil.info(TAG, "Encoded header: " + jweHeaderEncode);

        // Part 2: JWE Encrypted Key
        // Generate a 16-byte session key to encrypt payload data. Then convert it to a Hex String.
        LogUtil.info(TAG, "Part 2:");
        String sessionKey = generateSecureRandomFactor(16);
        // Encrypt the session key Hex String with Huawei's fixed sessionKeyPublicKey using
        // RSA/NONE/OAEPwithSHA-256andMGF1Padding algorithm, and then do base64 encoding to it.
        LogUtil.info(TAG, "sessionKey: " + sessionKey);
        String sessionKeyPublicKey =
            "MIIBojANBgkqhkiG9w0BAQEFAAOCAY8AMIIBigKCAYEAgBJB4usbO33Xg5vhJqfHJsMZj44f7rxpjRuPhGy37bUBjSLXN+dS6HpxnZwSVJCtmiydjl3Inq3Mzu4SCGxfb9RIjqRRfHA7ab5p3JnJVQfTEHMHy8XcABl6EPYIJMh26kztPOKU2Mkn6yhRaCurhVUD3n9bD8omiNrR4rg442AJlNamA7vgKs65AoqBuU4NBkGHg0VWWpEHCUx/xyX6hIwqc1aD7P2f62ZHsKpNZBOek/riWhaVx3dTAa9ZS+Av3IGLOZiplhYIow9f8dlWyqs8nff9FZoJO03QhXLvOORT+lPAkW6gFzaoeMaGb40HakkZn3uvlAEKrKrtR0rZEok+N1hnboaAu8oaKK0rF1W6iNrXcFrO0rcrCsFTVF8qCa/1dFmIXwUd2M6cUzT9W0YkNyb6ZBbwEhjwBL4DNW4JfeF2Dzj0eZYlSuYV7e7e1e+XEO8lwPLAiy4bEFAWCaeuDVIhbIoBaU6xHNVQoyzct98gaOYxE4mVDqAUVmhfAgMBAAE=";
        String encryptedKeyEncode = getEncryptedKey(sessionKey, sessionKeyPublicKey);
        LogUtil.info(TAG, "Encrypted sessionKey: " + encryptedKeyEncode);

        // Part 3: JWE IV
        // Generate a 12-byte iv. Then convert it to a Hex String, and then do base64 encoding to the Hex String.
        LogUtil.info(TAG, "Part 3:");
        byte[] iv = AesUtils.getIvByte(12);
        String ivHexStr = new String(Hex.encodeHex(iv, false));
        String ivEncode = Base64.encodeBase64URLSafeString(ivHexStr.getBytes(StandardCharsets.UTF_8));
        LogUtil.info(TAG, "Encoded iv: " + ivEncode);

        // Part 4: JWE Cipher Text
        // Encrypt the payload with sessionKey and iv using AES/GCM/NoPadding algorithm. Encode the cipher text into a
        // Hex String. Then do gzip compression and base64 encoding to the Hex String.
        LogUtil.info(TAG, "Part 4:");
        LogUtil.info(TAG, "payload: " + payload);
        String cipherTextEncode = getCipherText(payload, sessionKey, iv);
        LogUtil.info(TAG, "Compressed and encoded cipher text: " + cipherTextEncode);

        // Part 5: JWE Signature
        // Use your own private key to sign the content with SHA256withRSA, then do base64 encoding to it.
        // HMS wallet server will use the public key you provided on AGC to verify signatures.
        LogUtil.info(TAG, "Part 5:");
        String signature = getSignature(jwePrivateKey, sessionKey, payload, jweHeaderEncode, ivEncode);
        LogUtil.info(TAG, "signature: " + signature + "\n");

        // Combine all five parts together to form a valid JWE.
        StringBuilder stringBuilder = new StringBuilder().append(jweHeaderEncode)
            .append(".")
            .append(encryptedKeyEncode)
            .append(".")
            .append(ivEncode)
            .append(".")
            .append(cipherTextEncode)
            .append(".")
            .append(signature);
        return stringBuilder.toString();
    }

    private static Map<String, String> getHeader() {
        Map<String, String> jweHeader = new HashMap<>();
        jweHeader.put("alg", "RSA-OAEP");
        jweHeader.put("enc", "A128GCM");
        jweHeader.put("kid", "1");
        jweHeader.put("zip", "gzip");
        return jweHeader;
    }

    private static String getEncodeHeader(Map<String, String> jweHeader) {
        StringBuilder stringBuilder = new StringBuilder();
        String headerStr = stringBuilder.append("alg=")
            .append(jweHeader.get("alg"))
            .append(", enc=")
            .append(jweHeader.get("enc"))
            .append(", kid=")
            .append(jweHeader.get("kid"))
            .append(", zip=")
            .append(jweHeader.get("zip"))
            .toString();
        LogUtil.info(TAG, "Header before encoding: " + headerStr);
        // Do base64 encoding.
        return Base64.encodeBase64URLSafeString(headerStr.getBytes(StandardCharsets.UTF_8));
    }

    private static String getEncryptedKey(String sessionKey, String sessionKeyPublicKey) {
        try {
            String encryptedSessionKey = RsaUtil.encrypt(sessionKey.getBytes(StandardCharsets.UTF_8),
                sessionKeyPublicKey, "RSA/NONE/OAEPwithSHA-256andMGF1Padding");
            return Base64.encodeBase64URLSafeString(encryptedSessionKey.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new IllegalArgumentException("Encrypt session key failed. Error: " + e.getMessage());
        }
    }

    private static String getCipherText(String dataJson, String sessionKey, byte[] iv) {
        String payloadEncrypt = AesUtils.encryptByGcm(dataJson, sessionKey, iv);
        LogUtil.info(TAG, "Encrypted payload Hex String: " + payloadEncrypt);
        byte[] payLoadEncryptCompressByte = compress(payloadEncrypt.getBytes(StandardCharsets.UTF_8));
        return Base64.encodeBase64URLSafeString(payLoadEncryptCompressByte);
    }

    private static String getSignature(String jweSignPrivateKey, String sessionKey, String payLoadJson,
        String jweHeaderEncode, String ivEncode) {
        StringBuilder stringBuilder = new StringBuilder();
        String signContent = stringBuilder.append(jweHeaderEncode)
            .append(".")
            .append(sessionKey)
            .append(".")
            .append(ivEncode)
            .append(".")
            .append(payLoadJson)
            .toString();
        LogUtil.info(TAG, "Content to be signed: " + signContent);
        return RsaUtil.sign(signContent, jweSignPrivateKey);
    }

    /**
     * gzip Compress
     *
     * @param originalBytes Data to be compressed
     * @return Compressed data
     */
    public static byte[] compress(byte[] originalBytes) {
        if (originalBytes == null || originalBytes.length == 0) {
            return null;
        }
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
            GZIPOutputStream gzip = new GZIPOutputStream(out)) {
            gzip.write(originalBytes);
            gzip.finish();
            return out.toByteArray();
        } catch (Exception e) {
            LogUtil.info(TAG, e.getMessage());
            return null;
        }
    }

    /**
     * Generate random hex string.
     *
     * @param size string length.
     * @return the generated random hex string.
     */
    private static String generateSecureRandomFactor(int size) {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[size];
        random.nextBytes(bytes);
        return org.bouncycastle.util.encoders.Hex.toHexString(bytes);
    }
}
