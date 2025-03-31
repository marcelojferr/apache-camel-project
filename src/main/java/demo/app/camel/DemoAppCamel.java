package demo.app.camel;

import demo.app.camel.routes.MyRouteBuilder;
import org.apache.camel.main.Main;

public class DemoAppCamel {

    public static void main(String... args) throws Exception {
        Main main = new Main();
        main.configure().addRoutesBuilder(new MyRouteBuilder());
        main.run(args);
    }

}

