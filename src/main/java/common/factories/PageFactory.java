package common.factories;

import common.CustomDriver;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public class PageFactory {
    private static final Map<Class<?>, Supplier<?>> pageRegistry = new ConcurrentHashMap<>();

    public static <T> void registerPage (Class<T> pageClass, Supplier<T> pageSupplier){
        pageRegistry.putIfAbsent(pageClass, pageSupplier);
    }

    public static <T> T getPage(Class<T> pageClass, CustomDriver driver)
    {
        Supplier<?> pageSupplier  = pageRegistry.get(pageClass);
        if(pageSupplier == null){
            throw  new IllegalArgumentException("Page class not registered " + pageClass.getName());
        }
        return (T) pageSupplier.get();
    }
}
