package utils;

public class FeatureContext {
    private static final ThreadLocal<String> currentFeatureName = new ThreadLocal<>();
    
    public static void setFeatureName(String featureName) {
        currentFeatureName.set(featureName);
    }
    
    public static String getFeatureName() {
        return currentFeatureName.get();
    }
    
    public static void clear() {
        currentFeatureName.remove();
    }
}