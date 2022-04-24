package org.ucommerce.library.annotation.processor;

public interface CoreFooService {

    /**
     * This is a java-doc comment.
     * @param data
     */
    void methodA(MethodAData data);

    MethodBReturnData methodB(String someString);

    Foo getFoo(String id);

    void updateFoo(Foo foo);

    void deleteFoo(Foo foo);
}
