package cnpm.ergo.entity;

public class TestService {
    public static void main(String[] args) {
        // Gọi thẳng Service - Không cần thông qua Web/Tomcat
        cnpm.ergo.service.interfaces.ICustomerService service = new cnpm.ergo.service.implement.CustomerServiceImpl();

        Customer testC = new Customer();
        testC.setName("Test Chặng 1");
        testC.setEmail("service_test_" + System.currentTimeMillis() + "@gmail.com");
        testC.setPassword("123");
        testC.setPhone("0987654321");
        
        // Chú ý: Nếu hàm insert của bạn chưa tự gán Role, hãy gán ở đây để test xem DAO có chạy ko
        // Role role = em.find(Role.class, 1);
        // testC.setRole(role);

        System.out.println("Bắt đầu gọi Service...");
        boolean result = service.insert(testC);
        
        if(result) {
            System.out.println("==> CHẶNG 1 OK: Dữ liệu đã lưu qua Service!");
        } else {
            System.err.println("==> CHẶNG 1 LỖI: Service không lưu được. Kiểm tra Console để xem lỗi SQL.");
        }
    }
}