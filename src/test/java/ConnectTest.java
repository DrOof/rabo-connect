import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class ConnectTest {

    private static final String key = "";
    private static final String cert = "";

    @Test
    public void testConnect() throws GeneralSecurityException, IOException, InterruptedException {

        var in = "AAIkZmRlNjcwNDYtZWViZi00OGNiLWE1ZjktYzc5NTk3MWIyMzI19ZSpg0zBEtni7L_zH4GysDtzM8pTBn9TteuOIM6ywNvX_h1TrMpc_Qft5fqLvRyeqf1x_Kfs64pTFMEJWByPYX4vG9xyb2nVN1YYvEqNZYP7APB8jOUo2TEGSd5csQeGY-vybmf2YcwdDeQAW0WoHU-0TYYzEB-4UoRfAZP1pEkFAaJLYVpNjPY3xOauApE_CGAgsWbRc0o80xYCUJoU8er8_zBSJf2zcxpGSLm0Lv1rTBpHNfgpGcrTBbLWJ2NEbsHfuKzdwWj8xRODi5A_VSel6Oa31Rdv-5b4bSuddURToci8BFWCZeVZk0cOD9Ak5uQhGw9ank1vgiofDiqjcBtC4-3MniKSrQ1e_hYinFnGfE5b_5uXwi9eF16hrDHnFffsTDk8qVZ-lXL-1eEIOKHbuvmnDzWjoJblSuAXukZJugTxaDNRje1QK1_0sHu0mO9xfZS8aUehOcdwTKb7xBr_krZvmPqswQEXVdH0ZNo";
        var out = new Connect().connect( in );

        System.out.println( "-------- START RESPONSE --------" );
        System.out.println( out );
        System.out.println( "-------- END RESPONSE --------" );

    }

}
