package demo.app.camel;

import demo.app.camel.routes.MainRouteBuilder;
import org.apache.camel.main.Main;

public class DemoAppCamel {

    public static void main(String... args) throws Exception {
        Main main = new Main();
        main.configure().addRoutesBuilder(new MainRouteBuilder());
        main.run(args);
    }

}

