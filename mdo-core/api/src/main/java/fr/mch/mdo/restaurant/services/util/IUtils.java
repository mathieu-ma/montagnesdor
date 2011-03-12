package fr.mch.mdo.restaurant.services.util;

import java.util.Locale;
import java.util.Map;

public interface IUtils
{
    Map<String, String> getSystemAvailableLanguages(Locale currentLocale);

    @SuppressWarnings("unchecked")
    <K extends Comparable,  V extends Comparable>  Map<K, V> sortedMapByValue(Map<K, V> sortableMap);
}
