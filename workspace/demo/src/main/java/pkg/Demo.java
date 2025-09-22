package pkg;

import org.springframework.stereotype.Component;

// this is not scanned because it's not a child package to
// com.cisco.demo
@Component
public class Demo {
}
