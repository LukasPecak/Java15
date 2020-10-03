package org.lukas.javach;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItemInArray;

public class NestsTest {

    private static final Logger LOG = LoggerFactory.getLogger(NestsTest.class);
    private static Class<A> aClass;

    private final String memberNestsTest = "memberNestsTest";

    @BeforeAll
    static void beforeAllTests() {
        aClass = A.class;
        LOG.info("The nest host for the NestsTest.A class is {}", aClass.getNestHost());
        LOG.info("The nest member of the NestsTest.A class are {}", Arrays.toString(aClass.getNestMembers()));
    }

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
    void getHost_shouldReturnTheNestHost_always() {
        // given setup

        // when
        final Class<?> nestHostClass = aClass.getNestHost();

        // then
        assertThat(nestHostClass, is(sameInstance(nestHostClass)));
    }

    @Test
    void getNestMembers_shouldReturnTheArrayWithNestMembers_always() {
        // given setup

        // when
        final Class<?>[] nestMembers = aClass.getNestMembers();

        // then
        assertThat(nestMembers, hasItemInArray(NestsTest.class));
        assertThat(nestMembers, hasItemInArray(NestsTest.D.class));
        assertThat(nestMembers, hasItemInArray(NestsTest.A.class));
        assertThat(nestMembers, hasItemInArray(NestsTest.A.B.class));
        assertThat(nestMembers, hasItemInArray(NestsTest.A.C.class));
    }

    @Test
    void isNestmateOf_shouldReturnTrue_ifNestsTestClassIsNestmateOfClassA() {
        // given setup

        // when
        final boolean isNestsTestClassNestmateOfA = aClass.isNestmateOf(NestsTest.class);

        // then
        assertThat(isNestsTestClassNestmateOfA, is(true));
    }

    @Test
    void isNestmateOf_shouldReturnTrue_ifDClassIsNestmateOfClassA() {
        // given setup

        // when
        final boolean isDClassNestmateOfA = aClass.isNestmateOf(D.class);

        // then
        assertThat(isDClassNestmateOfA, is(true));
    }

    @Test
    void isNestmateOf_shouldReturnTrue_ifBClassIsNestmateOfClassA() {
        // given setup

        // when
        final boolean isBClassNestmateOfA = aClass.isNestmateOf(A.B.class);

        // then
        assertThat(isBClassNestmateOfA, is(true));
    }

    @Test
    void isNestmateOf_shouldReturnTrue_ifCClassIsNestmateOfClassA() {
        // given setup

        // when
        final boolean isCClassNestmateOfA = aClass.isNestmateOf(A.C.class);

        // then
        assertThat(isCClassNestmateOfA, is(true));
    }

    @Test
    void isNestmateOf_shouldReturnTrue_ifCheckOnTheSameClass() {
        // given setup

        // when
        final boolean isAClassNestmateOfA = aClass.isNestmateOf(A.class);

        // then
        assertThat(isAClassNestmateOfA, is(true));
    }

    @Test
    void isNestmateOf_shouldReturnFalse_ifClassDefinedInTheSameClassFileButNotANestedClass() {
        // given setup

        // when
        final boolean isEClassNestmateOfA = aClass.isNestmateOf(E.class);

        // then
        assertThat(isEClassNestmateOfA, is(false));
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
