# To run
```
mvn test \
-Djavax.net.ssl.keyStore=src/main/resources/keystore \
-Djavax.net.ssl.keyStorePassword=changeit \
-Djavax.net.ssl.trustStore=src/main/resources/truststore
-Djavax.net.ssl.trustStorePassword=changeit 
```

# How the keystore was created
```
openssl pkcs12 -export -in client.crt -inkey client.key -name api-sandbox.rabobank.nl -out client.p12 \
keytool -importkeystore -deststorepass changeit -destkeystore keystore -srckeystore client.p12 -srcstoretype PKCS12
```

# How the truststore was created (optional)
```
openssl s_client -host api-sandbox.rabobank.nl -port 443 -prexit -showcerts -servername api-sandbox.rabobank.nl \
touch ca.crt # copy certificates from previous command into crt file \
openssl x509 -in ca.crt -inform pem -out ca.der -outform der \
keytool -importcert -deststorepass changeit -keystore truststore -file ca.der 
```