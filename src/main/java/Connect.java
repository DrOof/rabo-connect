import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Connect {

    // TODO: Inject as config
    private String clientId = "fde67046-eebf-48cb-a5f9-c795971b2325";

    // TODO: Inject as config or load from keystore
    private String key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDaAI6hllIAiJthftLAUhMC2pfk97vrpXS4pKcRwgfIi9Rg+xa0Y3APjFbMYbx5cu70bByvrUMjEmniu/NkR6WN96sTy3PC5qEC29hLokMV4/TzmJGAUO+KpK2Z7ylZUd1qMkFngzl50mNsGzRX5D/BE+BJcMriCBK4sGIWm9DiK556zvF/Nv0j2cR8BHd2kI0OroYsPKWg7KSW0uVoxH7sGNeilVVGJYW7QMBo73OmitDQZvix/XzC6QBOoHxmTctIMzA6K1TW5ECtSKABg/KfWR3QdBdxcJCCxI1IkFupyvv3ygzSdEwW6YGVKWPG0eOtKs8SnXmdcjw6l9go4en5AgMBAAECggEAB8nsTqalwGIhFw8mbXuhNUFlGuek/arYLD6pv28swwQH7v0ZlxFUcCHF+iBl0PsDwZTZQ4ePtgGS6ehoLkWHCzb1lEv5E1YVG5qKNE2UUwRlfIyPakO6AzyV/UF3uzq7C+/GuXGNTKZxKewg5yD/DCFvKoCOpxu9u36FyqP/hw0SADVlmp35/zoPDPZzu1j4FiCo0pJ9LwJcHxeJHopNAKDw9k6I4z/grskdgupsGzK2BiGiQ/+wmmO68/6Xa6KWfpr1PQ6ODJHgzZsdGCVi6Ebaqlj6BbsYWxP6h3lrsGt+LmHBaN2jCD6cDp+lihqFgnm8hfdv0lmbPilp71EDfwKBgQD6U8PBzZtN8yXm5WuSNL+/8q5GjNmeTJBSo1gM6Y8vOT4QAE147LbVuVBDwyHPoSrNejePae6Q14PswjByT7B8DZ0OeQyGa3trrFg/ib7Vv4ZMvJqX9+WzBrzZsxTg7oCKHzmCR4vIRItKHH3zWnnhqlo8ic2AZ2O43cdJosbO1wKBgQDe8UODOLu0vnHohOKeUqF3w/ZOB2+83/jsYyUbSkzsGvHIwTjObuMUFTQvdMZ6IkIyJdfnDZIbvlBSD8tzL5iKFTNCK2nL1i4GiFr0CYLaHAlhJ5GEbTrTMDoJeBPerZq83HPrSa/Wb0xO18QTWsoVQPFfPFbbcQyI9ryJ2iIDrwKBgQC6kuAefG46ZPVk6K2KZUJdgDUgZC52a75NuW0RAqszmUiGiJM1g8ip9tq6BqAWrprGV0c93shusBKlzf5p1LdHXqYmeVY6gbWVhPipMrNHgN5KJ3BZv+w1yNnMsErpcxne2HL2hPjMJTpj3GSLkm2xIlTrNhIyl9ydlr7IRUhENQKBgQCvi6HxbXa/90WSJTCcIcxqla8X+dsOCf3jhJ3vQy4Wq5C+1wZ35fCAG8Ifq/+so9Ujz5CVqqXlmpF8TFuSs2OVNuRJsg14J4nOMwgLKIIUZAcurQ10DN5I9Kx+UEK1EFXLaHsORdNjMfgQDO2jn9WHrr9gkg6CdB2+qyoCEfS+mQKBgBW08lcy9V5RzRWb/v/jxsc7ovmgAhCJhDeV7dPbx4HbFeoQJlbA8g1thdcFlcatSGyNDbvNE1GPSd4NhkpRY6Hfv53kdEzjVkEtU8lUdL7HNVJqX7bU7oZlfbYcwxWQ1Gg8C1oLIAyEt71slQtdRiNYBRZTQe2F0wxbXnuUqLAw";
    private String cert = "MIIDkDCCAnigAwIBAgIEWs3AJDANBgkqhkiG9w0BAQsFADCBiTELMAkGA1UEBhMCTkwxEDAOBgNVBAgMB1V0cmVjaHQxEDAOBgNVBAcMB1V0cmVjaHQxETAPBgNVBAoMCFJhYm9iYW5rMRwwGgYDVQQLDBNPbmxpbmUgVHJhbnNhY3Rpb25zMSUwIwYDVQQDDBxQU0QyIEFQSSBQSSBTZXJ2aWNlcyBTYW5kYm94MB4XDTE4MDQxMTA3NTgyOFoXDTIzMDQxMTA3NTgyOFowgYkxCzAJBgNVBAYTAk5MMRAwDgYDVQQIDAdVdHJlY2h0MRAwDgYDVQQHDAdVdHJlY2h0MREwDwYDVQQKDAhSYWJvYmFuazEcMBoGA1UECwwTT25saW5lIFRyYW5zYWN0aW9uczElMCMGA1UEAwwcUFNEMiBBUEkgUEkgU2VydmljZXMgU2FuZGJveDCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBANoAjqGWUgCIm2F+0sBSEwLal+T3u+uldLikpxHCB8iL1GD7FrRjcA+MVsxhvHly7vRsHK+tQyMSaeK782RHpY33qxPLc8LmoQLb2EuiQxXj9POYkYBQ74qkrZnvKVlR3WoyQWeDOXnSY2wbNFfkP8ET4ElwyuIIEriwYhab0OIrnnrO8X82/SPZxHwEd3aQjQ6uhiw8paDspJbS5WjEfuwY16KVVUYlhbtAwGjvc6aK0NBm+LH9fMLpAE6gfGZNy0gzMDorVNbkQK1IoAGD8p9ZHdB0F3FwkILEjUiQW6nK+/fKDNJ0TBbpgZUpY8bR460qzxKdeZ1yPDqX2Cjh6fkCAwEAATANBgkqhkiG9w0BAQsFAAOCAQEAYL4iD6noMJAt63kDED4RB2mII/lssvHhcxuDpOm3Ims9urubFWEpvV5TgIBAxy9PBinOdjhO1kGJJnYi7F1jv1qnZwTV1JhYbvxv3+vk0jaiu7Ew7G3ASlzruXyMhN6t6jk9MpaWGl5Uw1T+gNRUcWQRR44g3ahQRIS/UHkaV+vcpOa8j186/1X0ULHfbcVQk4LMmJeXqNs8sBAUdKU/c6ssvj8jfJ4SfrurcBhY5UBTOdQOXTPY85aU3iFloerx7Oi9EHewxInOrU5XzqqTz2AQPXezexVeAQxP27lzqCmYC7CFiam6QBr06VebkmnPLfs76n8CDc1cwE6gUl0rMA==";

    public String connect( String in ) throws GeneralSecurityException, IOException, InterruptedException {

        var authorization                   = "Bearer " + in;
        var tppSignatureCertificate  = cert;
        var xIbmClientId             = clientId;
        var date                     = generateFormattedDate( Calendar.getInstance().getTime() );
        var digest                   = generateDigest( "" );
        var xRequestId               = generateRequestId();
        var signature                = generateSignature( date, digest, xRequestId );

        var request = HttpRequest.newBuilder()
                .uri( URI.create( "https://api-sandbox.rabobank.nl/openapi/sandbox/payments/account-information/ais/accounts" ) )
                .header( "Authorization",               authorization )
                .header( "TPP-Signature-Certificate",   tppSignatureCertificate )
                .header( "X-IBM-Client-Id",             xIbmClientId )
                .header( "Date",                        date )
                .header( "Digest",                      digest )
                .header( "X-Request-Id",                xRequestId )
                .header( "Signature",                   signature )
                .build();

        var out = produceHttpClient().send( request, HttpResponse.BodyHandlers.ofString( StandardCharsets.UTF_8 ) ).body();

        return out;

    }

    /**
     *
     * @return
     */
    private HttpClient produceHttpClient() {
        return HttpClient.newBuilder().build();
    }

    /**
     *
     * @return
     */
    private String generateRequestId() {
        return UUID.randomUUID().toString();
    }

    /**
     *
     * @param key
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    private RSAPrivateKey resolvePrivateKey( String key ) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return ( RSAPrivateKey ) KeyFactory.getInstance( "RSA" ).generatePrivate( new PKCS8EncodedKeySpec( Base64.getDecoder().decode( key ) ) );
    }

    /**
     *
     * @param body
     * @return
     * @throws NoSuchAlgorithmException
     */
    private String generateDigest( String body ) throws NoSuchAlgorithmException {
        return "sha-512=" + Base64.getEncoder().encodeToString( MessageDigest.getInstance( "SHA-512" ).digest( body.getBytes( StandardCharsets.UTF_8 ) ) );
    }

    /**
     *
     * @param date
     * @param digest
     * @param id
     * @return
     * @throws GeneralSecurityException
     */
    private String generateSignature( String date, String digest, String id ) throws GeneralSecurityException {

        // Step 1: Create content to sign.
        var writer = new StringWriter();
        var printer = new PrintWriter( writer );
        printer.print( "date: " + date + "\n" );
        printer.print( "digest: " + digest + "\n" );
        printer.print( "x-request-id: " + id );

        // Step 2: Create a signature ( SHA256/RSA )
        var sig = Signature.getInstance( "SHA512WithRSA" );
        sig.initSign( resolvePrivateKey( key ) );
        sig.update( writer.toString().getBytes( StandardCharsets.UTF_8 ) );

        var signature = Base64.getEncoder().encodeToString( sig.sign() );

        return MessageFormat.format( "keyId=\"{0}\", algorithm=\"{1}\", headers=\"{2}\", signature=\"{3}\"", new Object[] { "1523433508", "rsa-sha512", "date digest x-request-id", signature } );

    }

    /**
     *
     * @param date
     * @return
     */
    private String generateFormattedDate( Date date ) {

        DateFormat format = new SimpleDateFormat( "EEE, dd MMM yyyy HH:mm:ss", Locale.US );
        format.setTimeZone( TimeZone.getTimeZone( "GMT" ) );

        return format.format( date ) + " GMT";

    }
}
