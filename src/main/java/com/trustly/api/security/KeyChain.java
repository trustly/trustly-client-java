/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Trustly Group AB
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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.security.KeyException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
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

    private PrivateKey merchantPrivateKey;
    private PublicKey trustlyPublicKey;

    public KeyChain(boolean testEnvironment) {
        this(testEnvironment ? "/keys/trustly_test_public.pem" : "/keys/trustly_live_public.pem");
    }

    public KeyChain(File file) throws IOException {
        loadTrustlyPublicKey(Files.newInputStream(file.toPath()));
    }

    public KeyChain(String classPath) {
        loadTrustlyPublicKey(this.getClass().getResourceAsStream(classPath));
    }

    public KeyChain(InputStream inputStream) {
        loadTrustlyPublicKey(inputStream);
    }

    /**
     * Loads the merchant private key.
     *
     * @param privateKeyFilename path to and name of private key.
     * @param password           Password for the private key. "" or null if key requires no password.
     * @throws KeyException if key failed to load. For example if the path is incorrect.
     */
    void loadMerchantPrivateKey(final InputStream privateKey, final String password) throws KeyException {
        try {
//            final File privateKeyFile = new File(privateKeyFilename); // private key file in PEM format
            final Object object;
            try (final PEMParser pemParser = new PEMParser(new InputStreamReader(privateKey))) {
                object = pemParser.readObject();
            }

            final JcaPEMKeyConverter converter = new JcaPEMKeyConverter();

            if (object instanceof PrivateKeyInfo) {

                final PrivateKeyInfo privateKeyInfo = (PrivateKeyInfo) object;
                merchantPrivateKey = converter.getPrivateKey(privateKeyInfo);
            } else {

                final KeyPair kp;
                if (object instanceof PEMKeyPair) {
                    kp = converter.getKeyPair((PEMKeyPair) object);
                } else if (object instanceof PEMEncryptedKeyPair) { // Password required
                    final PEMDecryptorProvider decProv = new JcePEMDecryptorProviderBuilder().build(password.toCharArray());
                    kp = converter.getKeyPair(((PEMEncryptedKeyPair) object).decryptKeyPair(decProv));
                } else {
                    throw new IllegalArgumentException(String.format(
                        "Do not know how to handle pem object '%s' since it it not a PrivateKey, PEM KeyPair nor PEM Encrypted KeyPair",
                        object
                    ));
                }
                merchantPrivateKey = kp.getPrivate();
            }
        } catch (final IOException e) {
            throw new KeyException("Failed to load private key", e);
        }
    }

    /**
     * Loads the Trustly public key.
     *
     * @param is The input stream containing the trustly public key
     */
    private void loadTrustlyPublicKey(final InputStream is) {
        try {

            final PEMParser pemParser = new PEMParser(new InputStreamReader(is));
            final PemObject object = pemParser.readPemObject();

            final JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider(new BouncyCastleProvider());

            final byte[] encoded = object.getContent();

            final SubjectPublicKeyInfo subjectPublicKeyInfo = SubjectPublicKeyInfo.getInstance(ASN1Sequence.getInstance(encoded));

            trustlyPublicKey = converter.getPublicKey(subjectPublicKeyInfo);
        } catch (final IOException e) {
            throw new TrustlyAPIException("Failed to load Trustly public key", e);
        }
    }

    PrivateKey getMerchantPrivateKey() {
        return merchantPrivateKey;
    }

    PublicKey getTrustlyPublicKey() {
        return trustlyPublicKey;
    }
}
