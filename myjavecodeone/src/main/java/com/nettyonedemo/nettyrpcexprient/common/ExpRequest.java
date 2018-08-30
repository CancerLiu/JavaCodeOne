package com.nettyonedemo.nettyrpcexprient.common;

/**
 * 指数rpc的输入
 */
public class ExpRequest {
    private int base;
    private int exp;

    public ExpRequest() {
    }

    public ExpRequest(int base, int exp) {
        this.base = base;
        this.exp = exp;
    }

    public int getBase() {
        return base;
    }

    public ExpRequest setBase(int base) {
        this.base = base;
        return this;
    }

    public int getExp() {
        return exp;
    }

    public ExpRequest setExp(int exp) {
        this.exp = exp;
        return this;
    }
}
