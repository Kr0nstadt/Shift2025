package config;

public interface IArgumentParser<T> {
    T parse() throws IllegalArgumentException;
}
