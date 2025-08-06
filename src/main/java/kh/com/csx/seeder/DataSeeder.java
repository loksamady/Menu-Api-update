package kh.com.csx.seeder;
import kh.com.csx.request.RegisterRequest;
import kh.com.csx.service.auth.AuthenticationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final AuthenticationService service;

    public DataSeeder(AuthenticationService service) {
        this.service = service;
    }

    @Override
    public void run(String... args) throws Exception {
        // Check if the 'seed' argument is present
        if (args.length > 0 && "seed".equalsIgnoreCase(args[0])) {
            RegisterRequest request = RegisterRequest.builder().username("admin").password("123456").build();
            service.register(request);
            System.out.println("Seeded default users.");
        }
    }
}
