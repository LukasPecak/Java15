package org.lukas.javach;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class NestsTest {

    private final String memberNestsTest = "memberNestsTest";

    @Test
    void accessToPrivateInstanceMembersInNestedClasses() {
        final A a = new A();
        assertThat(a.memberA, is(equalTo("member A")));

        final A.B b = a.new B();
        assertThat(b.memberB, is(equalTo("member B")));

        final A.C c = a.new C();
        assertThat(c.memberC, is(equalTo("member C")));

        // static
        final D d = new D();
        assertThat(d.memberD, is(equalTo("member D")));
    }

    @Test
    void accessToMemberClassesInsideTheSameClassFile() {
        final E e = new E();
        // the private instance filed is not visible
    }

    class A {
        private final String memberA = "member A";

        public void methodA() {
            System.out.println(memberNestsTest);
            System.out.println(memberA);
        }

        class B {
            private final String memberB = "member B";

            public void methodB() {
                System.out.println(memberNestsTest);
                System.out.println(memberA);
                System.out.println(memberB);
            }
        }

        private class C {
            private final String memberC = "member C";

            public void methodC() {
                System.out.println(memberNestsTest);
                System.out.println(memberA);
                System.out.println(memberC);
            }
        }
    }

    static class D {
        private final String memberD = "member D";

        public void methodD() {
            // cannot reference an instance field of the outer class inside a static class
            // an instanc of this class can exist without an instance of the outer class
            System.out.println(memberD);
        }
    }
}

class E {
    private String memberE = "member E";

    public void methodE() {
        final NestsTest nestsTest = new NestsTest();
        final NestsTest.A a = nestsTest.new A();
        // private member of object a is not visible in another class from the same class file
    }
}
