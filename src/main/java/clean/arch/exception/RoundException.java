package clean.arch.exception;

public class RoundException {
    public static class DuplicatedDrawnAtException extends RuntimeException {}
    public static class DuplicatedNameException extends RuntimeException {}
    public static class DrawnAtPastDateException extends RuntimeException {}
    public static class DrawnAtNotSaturdayException extends RuntimeException {}
    public static class NotFoundRoundException extends RuntimeException {}
}
