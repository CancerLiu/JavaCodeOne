package com.nettyonedemo.nettyrpcexprient.common;

/**
 * 指数rpc的输出
 */
public class ExpResponse {
    /**
     * 结果
     */
    private long value;

    /**
     * 耗时
     */
    private long costInNanos;

    public ExpResponse() {
    }

    public ExpResponse(long value, long costInNanos) {
        this.value = value;
        this.costInNanos = costInNanos;
    }

    public long getValue() {
        return value;
    }

    public ExpResponse setValue(long value) {
        this.value = value;
        return this;
    }

    public long getCostInNanos() {
        return costInNanos;
    }

    public ExpResponse setCostInNanos(long costInNanos) {
        this.costInNanos = costInNanos;
        return this;
    }
}
