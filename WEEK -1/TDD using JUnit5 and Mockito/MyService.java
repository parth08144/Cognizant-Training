public class MyService {

    private final ExternalApi externalApi;

    public MyService(ExternalApi externalApi) {
        this.externalApi = externalApi;
    }

    public String fetchData() {
        return externalApi.getData();
    }

    public String fetchUserData(int userId) {
        String rawData = externalApi.getUserData(userId);
        return "Processed: " + rawData;
    }

    public String checkHealth() {
        if (externalApi.isServiceAvailable()) {
            return "Service is UP";
        }
        return "Service is DOWN";
    }

    public boolean submitData(String data) {
        if (data == null || data.isEmpty()) {
            return false;
        }
        return externalApi.sendData(data);
    }
}
