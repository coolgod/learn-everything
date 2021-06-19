import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicProxyTest {
    interface Interface {
        void hello();

        static void staticHello() {
            System.out.println("static hello");
        }
    }

    static class RealObject implements Interface {
        @Override
        public void hello() {
            Interface.staticHello();
            System.out.println("hello");
        }
    }

    static class DynamicProxyHandler implements InvocationHandler {
        private Object proxied;

        public DynamicProxyHandler(Object proxied) {
            this.proxied = proxied;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return method.invoke(proxied, args);
        }
    }

    public static void main(String[] args) {
        Interface proxy = (Interface) Proxy.newProxyInstance(
            Interface.class.getClassLoader(),
            new Class[]{ Interface.class },
            new DynamicProxyHandler(new RealObject())
        );

        proxy.hello();
    }
}
