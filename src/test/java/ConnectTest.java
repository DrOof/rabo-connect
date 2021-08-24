import org.junit.jupiter.api.Test;

public class ConnectTest {

    private static final String key = "";
    private static final String cert = "";

    @Test
    public void testConnect() {

        var in = "AAIkZWFlYmJmODUtZTJmOC00YWY0LWEzMmEtYmQ2MzM2ZDQ4MTJmkmdSIUQSZRkIlIGd0kjwsvr6slNCoMoWSfxA0eJ76t2P2nHpPp7YeVvNW_hh3NmEMPY1z1mEfO-s3EK4vw3YgkC5OBrM8fMSudVluCGIgEyVeA6GJBNB87HhG3T-zfcG5ugBD6Rh_Yye-JKWiNLj90V3--022My8rKRWG9NkkUb4SgyPkH3UGMIrPI2pBeEf60uh1OrE-dDE9qiPHRBnBhBXYu1nBf3WbqZjpuybETJVzm7v_KcsVzdfVldt2m2yp3mfAc7uq24p7ETNUVORs0iyzAx6kqF54gaOepPVxhe4la9Q-TfWYNhf6MhcNDwIeTTQrp48iaCiWMXP8lYfgOD2gnLIoQHx8tATJh5a8V4";
        var out = new Connect().connect( in );

        System.out.println( "-------- START RESPONSE --------" );
        System.out.println( out );
        System.out.println( "-------- END RESPONSE --------" );

    }

}
