package config;

public interface ArgumentParser<T> {
    T parse() throws IllegalArgumentException;
}
