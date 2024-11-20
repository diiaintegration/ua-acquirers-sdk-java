package ua.gov.diia.client.sdk.model;

/**
 * List of all supported document types <br>
 * Refer the documentation to understand the operations can be done for each of them
 */
public class DocumentTypeConstant {
    /**
     * Паспорт громадянина України у формі ID-картки АБО біометричний закордонний паспорт
     * (залежно від того, що є в користувача в Дії, передається лише один з документів)
     * (рекомендований спосіб, коли треба отримати цифрову копію будь-якого паспорта клієнта)
     * <br><br>
     * Either citizen passport in ID-Card form or biometric foreign passport.
     * It's a recommended way to obtain a passport when it's type doesn't matter.
     */
    public static final String DOC_TYPE_PASSPORT = "passport";

    /**
     * Паспорт громадянина України у формі ID-картки
     * <br><br>
     * Citizen passport in ID-Card form
     */
    public static final String DOC_TYPE_INTERNAL_PASSPORT = "internal-passport";

    /**
     * Біометричний закордонний паспорт або закордонний паспорт
     * <br><br>
     * Biometric foreign passport or simple foreign passport
     */
    public static final String DOC_TYPE_FOREIGN_PASSPORT = "foreign-passport";

    /**
     * РНОКПП
     * <br><br>
     * Taxpayer card
     */
    public static final String DOC_TYPE_TAXPAYER_CARD = "taxpayer-card";

    /**
     * Довідка внутрішньо переміщеної особи (ВПО)
     * <br><br>
     * Internally displaced person certificate
     */
    public static final String DOC_TYPE_REFERENCE_INTERNALLY_DISPLACED_PERSON = "reference-internally-displaced-person";

    /**
     * Свідоцтво про народження дитини
     * <br><br>
     * Child's birth certificate
     */
    public static final String DOC_TYPE_BIRTH_CERTIFICATE = "birth-certificate";

    /**
     * Посвідчення водія
     * <br><br>
     * Driver license
     */
    public static final String DOC_TYPE_DRIVER_LICENSE = "driver-license";

    /**
     * Тех паспорт
     * <br><br>
     * Vehicle license
     */
    public static final String DOC_TYPE_VEHICLE_LICENSE = "vehicle-license";
}
