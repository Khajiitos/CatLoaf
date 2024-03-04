package me.khajiitos.catloaf.common.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class CatLoafConfigValues {

    public static abstract class Value<T> {
        private T value;
        private final T defaultValue;

        private Value(T defaultValue) {
            this.defaultValue = defaultValue;
            this.value = defaultValue;
        }

        public T get() {
            return this.value;
        }

        public void set(T value) {
            this.value = value;
        }

        public void setUnchecked(Object obj) {
            this.value = (T)obj;
        }

        public T getDefault() {
            return this.defaultValue;
        }

        public abstract T read(JsonElement jsonElement);
        public abstract JsonElement write();
    }

    public static class BooleanValue extends Value<Boolean> {
        public BooleanValue(Boolean defaultValue) {
            super(defaultValue);
        }

        @Override
        public Boolean read(JsonElement jsonElement) {
            return jsonElement.isJsonPrimitive() ? jsonElement.getAsJsonPrimitive().getAsBoolean() : this.getDefault();
        }

        @Override
        public JsonElement write() {
            return new JsonPrimitive(this.get());
        }
    }

    public static class IntValue extends Value<Integer> {
        private int min = Integer.MIN_VALUE;
        private int max = Integer.MAX_VALUE;

        public IntValue(Integer defaultValue) {
            super(defaultValue);
        }

        @Override
        public Integer get() {
            return clampToLimit(super.get());
        }

        @Override
        public Integer read(JsonElement jsonElement) {
            return clampToLimit(jsonElement.isJsonPrimitive() ? jsonElement.getAsJsonPrimitive().getAsInt() : this.getDefault());
        }

        @Override
        public JsonElement write() {
            return new JsonPrimitive(this.get());
        }

        public IntValue withMin(int min) {
            this.min = min;
            return this;
        }

        public IntValue withMax(int max) {
            this.max = max;
            return this;
        }

        private int clampToLimit(int value) {
            if (value > max) {
                return max;
            } else if (value < min) {
                return min;
            }

            return value;
        }

        public int getMin() {
            return min;
        }

        public int getMax() {
            return max;
        }
    }
}