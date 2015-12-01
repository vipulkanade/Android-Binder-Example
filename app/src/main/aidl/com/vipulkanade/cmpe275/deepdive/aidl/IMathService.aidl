// IMathService.aidl
package com.vipulkanade.cmpe275.deepdive.aidl;

// Declare any non-default types here with import statements
// Declare the communication interface which holds all of our exposed functions.
interface IMathService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     *
     * WE can pass values ALONG WITH in, out, or inout parameters.
     * Android Java Primitive datatypes (such as int, double, string, boolean, etc.) can only be passed in.
     */
    double Addition(in double input1, in double input2);
}
