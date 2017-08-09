/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Trustly Group AB
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.trustly.api.security;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.security.KeyException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMDecryptorProvider;
import org.bouncycastle.openssl.PEMEncryptedKeyPair;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.openssl.jcajce.JcePEMDecryptorProviderBuilder;
import org.bouncycastle.util.io.pem.PemObject;

import com.trustly.api.commons.exceptions.TrustlyAPIException;

public class KeyChain {
    private static final String TEST_TRUSTLY_PUBLIC_KEY_PATH = "/keys/test_trustly_public.pem";
    private static final String LIVE_TRUSTLY_PUBLIC_KEY_PATH = "/keys/trustly_public.pem"; 
    private static final String NEWLINE = System.getProperty("line.separator");

    private PrivateKey merchantPrivateKey;
    private PublicKey trustlyPublicKey;

    public KeyChain(boolean testEnvironment) {
        loadTrustlyPublicKey(testEnvironment);
    }

    /**
     * Loads the merchant private key
     * @param privateKey - private key or path to and name of private key.
     * @param password Password for the private key. "" or null if key requires no password.
     * @throws KeyException if key failed to load. For example if the path is incorrect.
     */
    public void loadMerchantPrivateKey(String privateKey, String password) throws KeyException {
        try {
        	PEMParser pemParser = null;
        	if (privateKey.contains(NEWLINE)) {
        		pemParser = new PEMParser(new StringReader(privateKey));
        	}
        	else {
        		File privateKeyFile = new File(privateKey); // private key file in PEM format
        		pemParser = new PEMParser(new FileReader(privateKeyFile));
        	}
            Object object = pemParser.readObject();

            PEMDecryptorProvider decProv = new JcePEMDecryptorProviderBuilder().build(password.toCharArray());
            JcaPEMKeyConverter converter = new JcaPEMKeyConverter();

            KeyPair kp;
            if (object instanceof PEMEncryptedKeyPair) { // Password required
                kp = converter.getKeyPair(((PEMEncryptedKeyPair) object).decryptKeyPair(decProv));
            } else {
                kp = converter.getKeyPair((PEMKeyPair) object);
            }

            merchantPrivateKey = kp.getPrivate();
        }
        catch (IOException e) {
            throw new KeyException("Failed to load private key", e);
        }
    }

    /**
     * Loads the Trustly public key.
     * @param testEnvironment whether to load the key for test environment or not.
     */
    public void loadTrustlyPublicKey(boolean testEnvironment) {
        try {
            BufferedReader br;
            if (testEnvironment) {
            	br =  new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(TEST_TRUSTLY_PUBLIC_KEY_PATH)));
            }
            else {
            	br =  new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(LIVE_TRUSTLY_PUBLIC_KEY_PATH)));
            }
            PEMParser pemParser = new PEMParser(br);
            PemObject object = pemParser.readPemObject();

            JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider( new BouncyCastleProvider());

            byte[] encoded = object.getContent();
            SubjectPublicKeyInfo subjectPublicKeyInfo = new SubjectPublicKeyInfo(
                    ASN1Sequence.getInstance(encoded));

            trustlyPublicKey = converter.getPublicKey(subjectPublicKeyInfo);
        }
        catch (IOException e) {
            throw new TrustlyAPIException("Failed to load Trustly public key", e);
        }
    }

    public PrivateKey getMerchantPrivateKey() {
        return merchantPrivateKey;
    }

    public PublicKey getTrustlyPublicKey() {
        return trustlyPublicKey;
    }

}
