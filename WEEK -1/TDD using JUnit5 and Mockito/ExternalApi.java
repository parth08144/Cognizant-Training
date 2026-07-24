public class ExternalApi {

    public String getData() {

        return "Real API Data";
    }

    public String getUserData(int userId) {

        return "Real User Data for ID: " + userId;
    }

    public boolean isServiceAvailable() {

        return true;
    }

    public boolean sendData(String data) {

        System.out.println("Sending data to real API: " + data);
        return true;
    }
}
