package com.davih.cglib.practice.cglib;

import net.sf.cglib.proxy.*;

import java.lang.reflect.Method;

public class Main {

    public static void main(String[] args) {
        UserService target = new UserService();

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(UserService.class);

        enhancer.setCallbacks(new Callback[]{
                new MethodInterceptor() {
                    /**
                     *
                     * @param o proxy object
                     * @param method method
                     * @param objects method params
                     * @param methodProxy proxy method
                     * @return
                     * @throws Throwable
                     */
                    @Override
                    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                        System.out.println(" ...........before.............  ");
//						Object result = methodProxy.invoke(target, objects);
//						Object result=method.invoke(target, objects);
                        Object result = methodProxy.invokeSuper(o, objects);
//						Object result=method.invoke(o, objects);
                        System.out.println(" ...........after.............  ");
                        return result;
                    }
                },
                NoOp.INSTANCE
        });

        enhancer.setCallbackFilter(new CallbackFilter() {
            @Override
            public int accept(Method method) {
                if (method.getName().equals("test")) {
                    return 0;
                } else {
                    return 1;
                }

            }
        });

        UserService userService = (UserService) enhancer.create();
        userService.test();

    }
}
