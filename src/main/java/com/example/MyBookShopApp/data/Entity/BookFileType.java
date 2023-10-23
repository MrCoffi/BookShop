package com.example.MyBookShopApp.data.Entity;

public enum BookFileType {
    PDF(".pdf"),EPUB(".epub"),FB2(".fb2");
    private final String fileExtensionString;

    BookFileType(String fileExtensionString) {
        this.fileExtensionString = fileExtensionString;
    }

    public static String getExtensionString(Long typeId){
        if (typeId == 1) {
            return BookFileType.PDF.fileExtensionString;
        } else if (typeId == 2) {
            return BookFileType.FB2.fileExtensionString;
        } else if (typeId == 3) {
            return BookFileType.EPUB.fileExtensionString;
        }
        return "";
    }
}
