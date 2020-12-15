package root;

/**
 * Test subject.
 */
public class Subject {
    public static int staticField = 0;
    public int field = 0;

    public Subject() {
    }

    public static void staticMethod() {
    }

    public void method() {
    }

    public class InnerClass {
    }

    public Subject anonClass = new InnerClass() {
        public String hello(){
            return "World!";
        }
    }
}
