package io.github._7isenko.SCP1985.jpa.object_types;

/**
 * @author 7isenko
 */
@SuppressWarnings("NonAsciiCharacters")
public enum LogStatus {
    В_ПОДГОТОВКЕ("В ПОДГОТОВКЕ"), ДЛЯ_ОГРАНИЧЕННОГО_ПОЛЬЗОВАНИЯ("ДЛЯ ОГРАНИЧЕННОГО ПОЛЬЗОВАНИЯ");
    private final String status;

    LogStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
