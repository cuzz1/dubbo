/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.dubbo.common.utils;

/**
 * Helper Class for hold a value.
 * 在java中方法调用“以值调用（call by value）”，即是通过原始值的复制传递的（是使用的变量的复制，而不是原始值）
 * 如果形参是对象引用，此时形参引用改变了对象的域，或者调用了改变对象状态的方法，那么对于持有该对象引用的其他代码而言，该对象改变了。
 *
 * 也就是说，IN参数是Java因有的参数，而OUT、INOUT参数不是Java固有的。
 * Holder就提供了一个措施，为不可变的对象引用提供一个可变的包装，在java中支持引用传递。
 * 这样就使Java可以与支持INOUT、OUT参数的编程语言写的web service进行通信。
 *
 * @see <a href="https://www.cnblogs.com/growup/archive/2011/03/18/1988365.html">Holder类的使用理解</a>
 */
public class Holder<T> {

    private volatile T value;

    public void set(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }

}