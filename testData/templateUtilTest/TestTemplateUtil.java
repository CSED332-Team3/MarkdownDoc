package templateUtilTest;

public class TestTemplateUtil extends ExtendsTest implements ImplementsTest {
    public int a;
    private boolean b;
    protected List<char> c;
    protected void test() {}

    /**
     * Test Doc Comment
     *
     * @return test return tag comment
     */
    public static char method() { return 'a'; }

    /**
     * @param c test parameter tag comment
     */
    private List<int> escape(List<char> c) {}
}