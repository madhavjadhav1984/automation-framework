package helpers;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class EnumUtils {
    private EnumUtils(){

    }

    public static final class EnumNotFoundException extends RuntimeException{
        private <E extends Enum<E>> EnumNotFoundException(final Class<E> enumClass, final Object value){
            super(String.format("Enum %s does not have value '%s'. Valid values: %s",
                    enumClass,
                    value,
                    Arrays.stream(enumClass.getEnumConstants()).map(E::name).collect(Collectors.toList())));
        }
    }

    public static final class EnumMultipleFoundException extends RuntimeException{
        private <E extends Enum<E>> EnumMultipleFoundException(
                final Class<E> enumClass,
                final Object value,
                List<E> found){
            super(String.format(
                    "Enum %s has multiple entries for '%s': '%s'",
                    enumClass,
                    value,
                    found.stream().map(E::name).collect(Collectors.joining(","))
            ));
        }
    }

    public static <E extends Enum<E>> E find(
            final Class<E> enumClass,
            final Function<E, Object> getValue,
            final Object value){
        final List<E> found = Arrays.stream(enumClass.getEnumConstants())
                .filter(e -> Objects.equals(getValue.apply(e),value))
                .toList();

        if (found.size() == 1){
            return found.get(0);
        } else if(found.isEmpty()) {
            throw new EnumNotFoundException(enumClass, value);
        } else {
            throw new EnumMultipleFoundException(enumClass, value, found);
        }
    }
}
