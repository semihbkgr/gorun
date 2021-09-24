public class Sleep {

    public static void main(String[] args) throws Exception {

        System.out.println("Start, timeMS: " + System.currentTimeMillis());
        Thread.sleep(2_000);
        System.out.println("End, timeMS: " + System.currentTimeMillis());

    }

}
