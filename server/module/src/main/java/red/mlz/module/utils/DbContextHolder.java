package red.mlz.module.utils;

class DbContextHolder {
    private static final ThreadLocal<String> context = new ThreadLocal<>();

    public static void setMaster() {
        context.set("master");
    }

    public static void setSlave() {
        context.set("slave");
    }

    public static String get() {
        return context.get();
    }
}
